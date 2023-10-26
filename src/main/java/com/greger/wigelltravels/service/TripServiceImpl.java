package com.greger.wigelltravels.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.greger.wigelltravels.CurrencyConverter;
import com.greger.wigelltravels.dao.TripRepository;
import com.greger.wigelltravels.entity.Customer;
import com.greger.wigelltravels.entity.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
        List<Trip> trips = tripRepository.findAll();
        List<Trip> inspectedListOfTrips = new ArrayList<>();
        for (Trip t : trips) {
            try {
                t = makeSureCurrencyIsUpdated(t, true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            inspectedListOfTrips.add(t);
        }
        return inspectedListOfTrips;
    }


    @Override
    public Trip findById(int id) {
        Optional<Trip> t = tripRepository.findById(id);
        Trip trip;
        if (t.isPresent()) {
            trip = t.get();
            try {
                makeSureCurrencyIsUpdated(trip, true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("Trip with id: " + id + " could not be found!");
        }
        return trip;
    }

    @Override
    @Transactional
    public Trip save(Trip trip) {
        trip.setDestination(destinationService.checkIfExistsInDatabaseIfNotSave(trip.getDestination(), true));
        trip.setTotalPriceSEK(trip.getDestination().getPricePerWeek() * trip.getNumberOfWeeks());
        try {
//            trip.setTotalPricePLN(currencyConverterSekToRequestedCurrency(trip.getTotalPriceSEK(), "PLN"));
            trip.setTotalPricePLN(CurrencyConverter.SekToRequestedCurrency(trip.getTotalPriceSEK(), "PLN"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tripRepository.save(trip);
    }

    @Override
    public Trip update(int id, Trip trip) {
        Trip tripFromDb = findById(id);
        tripFromDb.setDestination(destinationService.checkIfExistsInDatabaseIfNotSave(trip.getDestination(), true));
        tripFromDb.setNumberOfWeeks(trip.getNumberOfWeeks());
        tripFromDb.setTotalPriceSEK(trip.getTotalPriceSEK());
        tripFromDb.setTotalPricePLN(trip.getTotalPricePLN());
        tripFromDb.setDepartureDate(trip.getDepartureDate());
        return save(tripFromDb);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Trip trip = findById(id);
        Customer customer = tripRepository.findCustomerByTripId(id);
        if (customer != null) {
            customer.getTrips().remove(trip);
        }
        trip.setDestination(null);
        tripRepository.deleteById(id);
    }


    @Override
    public List<Trip> inspectTripList(List<Trip> tripList, int customerId) {
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("INKOMMANDE: " + tripList);
        List<Trip> inspectedTripList = new ArrayList<>();
        for (Trip trip : tripList) {
            Trip t = checkIfExistsInDatabaseIfNotSave(trip, customerId, true);

            if (!inspectedTripList.contains(t)) {
                inspectedTripList.add(t);
            }
        }
        System.out.println("KONTROLLERAD: " + tripList);
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        return inspectedTripList;
    }


    @Override
    public List<Trip> findTripsByCustomerId(int customerId) {
        return tripRepository.findTripsByCustomerId(customerId);
    }

    private Trip checkIfExistsInDatabaseIfNotSave(Trip trip, int customerId, boolean autoSave) {

        System.out.println("###################### #########################################################");
        System.out.println("INKOMMANDE: " + trip);

        Trip tripFromDatabase = tripRepository.findTripByCustomerIdAndDepartureDate(trip.getTripId(), customerId);
        if (tripFromDatabase != null) {
            if (!trip.equals(tripFromDatabase)) {
                tripFromDatabase.setDepartureDate(trip.getDepartureDate());
                tripFromDatabase.setNumberOfWeeks(trip.getNumberOfWeeks());
                tripFromDatabase.setTotalPriceSEK(trip.getTotalPriceSEK());
                tripFromDatabase.setTotalPricePLN(trip.getTotalPricePLN());
                tripFromDatabase.setDestination(destinationService.checkIfExistsInDatabaseIfNotSave(trip.getDestination(), true));
                System.out.println("MODDED");
            }
            System.out.println("FRÅN DB: " + tripFromDatabase);
            return tripFromDatabase;
        }
        trip.setTripId(0);
        trip.setDestination(destinationService.checkIfExistsInDatabaseIfNotSave(trip.getDestination(), true));
        if (autoSave) {
            trip = save(trip);
        }

        System.out.println("SPARAD: " + trip);
        System.out.println("###############################################################################");
        return trip;
    }

    @Override
    @Transactional
    public Trip makeSureCurrencyIsUpdated(Trip trip, boolean autoSave) throws IOException {
        double totSEK = trip.getDestination().getPricePerWeek() * trip.getNumberOfWeeks();
//        double totPLN = currencyConverterSekToRequestedCurrency(totSEK, "PLN");
        double totPLN = CurrencyConverter.SekToRequestedCurrency(totSEK, "PLN");

        if (trip.getTotalPricePLN() != totPLN) {
            trip.setTotalPriceSEK(Math.round(totSEK));
            trip.setTotalPricePLN(totPLN);
            if (autoSave) {
                return save(trip);
            }
        }

        return trip;
    }

    private double currencyConverterSekToRequestedCurrency(double sek, String requestedCurrency) throws IOException {

        String url_str = "https://open.er-api.com/v6/latest/SEK";

        URL url = new URL(url_str);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject jsonobj = root.getAsJsonObject();

        Map map = new Gson().fromJson(jsonobj.get("rates"), Map.class);
        double currency = (double) map.get(requestedCurrency);
        double calculatedCurrency = sek * currency;

        return Math.round(calculatedCurrency);

    }
}
