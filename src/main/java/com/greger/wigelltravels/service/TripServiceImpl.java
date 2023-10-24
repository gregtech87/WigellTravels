package com.greger.wigelltravels.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.greger.wigelltravels.dao.TripRepository;
import com.greger.wigelltravels.entity.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TripServiceImpl implements TripService {

    private TripRepository tripRepository;
    private DestinationService destinationService;

    @Autowired
    public TripServiceImpl(TripRepository tripRepository, DestinationService destinationService) {
        this.tripRepository = tripRepository;
        this.destinationService = destinationService;
    }

    @Override
    public List<Trip> findAll() {
        return tripRepository.findAll();
    }

    @Override
    public Trip findById(int id) {
        Optional<Trip> t = tripRepository.findById(id);
        Trip trip;
        if (t.isPresent()) {
            trip = t.get();
        } else {
            throw new RuntimeException("Trip with id: " + id + " could not be found!");
        }
        return trip;
    }

    @Override
    @Transactional
    public Trip save(Trip trip) {
        trip.setDestination(destinationService.checkIfExistsInDatabaseIfNotSave(trip.getDestination(), true));
//        trip.getTotalPriceSek(calculateTotalPrice(trip.getDestination().getPricePerWeek(), trip.getNumberOfWeeks()));
        System.out.println("TOTPRIS: " + trip.getTotalPriceSek());
        trip.setTotalPriceSek(trip.getDestination().getPricePerWeek() * trip.getNumberOfWeeks());
        System.out.println("TOTPRIS: " + trip.getTotalPriceSek());
        try {
            trip.setTotalPricePln(currencyConverterSekToPln(trip.getTotalPriceSek()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tripRepository.save(trip);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        tripRepository.deleteById(id);
    }


    @Override
    public List<Trip> inspectTripList(List<Trip> tripList, int customerId) {
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("INKOMMANDE: " + tripList);
        List<Trip> inspectedTripList = new ArrayList<>();
        for (Trip trip : tripList) {
            Trip t = checkIfExistsInDatabaseIfNotSave(trip, customerId);

            if (!inspectedTripList.contains(t)) {
                inspectedTripList.add(t);
            }
        }
        System.out.println("KONTROLLERAD: " + tripList);
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        return inspectedTripList;
    }


    private Trip checkIfExistsInDatabaseIfNotSave(Trip trip, int customerId) {

        String date = trip.getDepartureDate();
        int destination = trip.getDestination().getId();
        System.out.println("###################### #########################################################");
        System.out.println("INKOMMANDE: " + trip);

        Trip tripFromDatabase = tripRepository.findTripByCustomerIdAndDepartureDate(date, customerId);
        if (tripFromDatabase != null) {
            System.out.println("FRÅN DB: " + tripFromDatabase);
            return tripFromDatabase;
        }
        trip.setTripId(0);
        trip.setDestination(destinationService.checkIfExistsInDatabaseIfNotSave(trip.getDestination(), true));
        trip = save(trip);

        System.out.println("SPARAD: " + trip);
        System.out.println("###############################################################################");
        return trip;
    }

    @Override
    public List<Trip> findTripsByCustomerId(int customerId) {
        return tripRepository.findTripsByCustomerId(customerId);
    }

    private double currencyConverterSekToPln(double sek) throws IOException {

        String url_str = "https://open.er-api.com/v6/latest/SEK";

        URL url = new URL(url_str);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject jsonobj = root.getAsJsonObject();

        Map map = new Gson().fromJson(jsonobj.get("rates"), Map.class);
        double zloty = (double) map.get("PLN");
        double pln = sek / zloty;
        System.out.println("CONVERTED: " + Math.round(pln));
        return Math.round(pln);
    }
}
