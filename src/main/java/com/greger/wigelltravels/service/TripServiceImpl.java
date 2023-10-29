package com.greger.wigelltravels.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.greger.wigelltravels.CurrencyConverter;
import com.greger.wigelltravels.dao.TripRepository;
import com.greger.wigelltravels.entity.Customer;
import com.greger.wigelltravels.entity.Trip;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private final Logger logger = LogManager.getLogger("MyLogger");
    private TripRepository tripRepository;
    private TripDestinationService tripDestinationService;

    @Autowired
    public TripServiceImpl(TripRepository tripRepository, TripDestinationService tripDestinationService) {
        this.tripRepository = tripRepository;
        this.tripDestinationService = tripDestinationService;
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
        Trip trip = new Trip();
        if (t.isPresent()) {
            trip = t.get();
            try {
                makeSureCurrencyIsUpdated(trip, true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return trip;
    }

    @Override
    @Transactional
    public Trip save(Trip trip) {
        trip.setDestination(tripDestinationService.checkIfExistsInDatabaseIfNotSave(trip.getDestination(), true));
        trip.setTotalPriceSEK(trip.getDestination().getPricePerWeek() * trip.getNumberOfWeeks());
        try {
            trip.setTotalPricePLN(CurrencyConverter.SekToRequestedCurrency(trip.getTotalPriceSEK(), "PLN"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Trip savedTrip = tripRepository.save(trip);
        logger.info("Trip saved: " + savedTrip);
        return savedTrip;
    }

    @Override
    public Trip update(int id, Trip trip) {
        Trip tripFromDb = findById(id);
        Trip newTrip = new Trip();
        newTrip.setTripId(tripFromDb.getTripId());
        newTrip.setDestination(tripDestinationService.checkIfExistsInDatabaseIfNotSave(trip.getDestination(), true));
        newTrip.setNumberOfWeeks(trip.getNumberOfWeeks());
        newTrip.setTotalPriceSEK(trip.getTotalPriceSEK());
        newTrip.setTotalPricePLN(trip.getTotalPricePLN());
        newTrip.setDepartureDate(trip.getDepartureDate());
        logger.info("Trip edited \nFrom: " + tripFromDb + "\nTo: " + newTrip);
        return save(newTrip);
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
        List<Trip> inspectedTripList = new ArrayList<>();
        for (Trip trip : tripList) {
            Trip t = checkIfExistsInDatabaseIfNotSave(trip, customerId, true);

            if (!inspectedTripList.contains(t)) {
                inspectedTripList.add(t);
            }
        }
        return inspectedTripList;
    }


    @Override
    public List<Trip> findTripsByCustomerId(int customerId) {
        return tripRepository.findTripsByCustomerId(customerId);
    }

    private Trip checkIfExistsInDatabaseIfNotSave(Trip trip, int customerId, boolean autoSave) {
        if (trip.getTripId() > 0) {
            return update(trip.getTripId(), trip);
        }
        Trip tripFromDatabase = tripRepository.findTripByCustomerIdAndDepartureDate(trip.getTripId(), customerId);
        if (tripFromDatabase != null) {
            if (!trip.equals(tripFromDatabase)) {
                tripFromDatabase.setDepartureDate(trip.getDepartureDate());
                tripFromDatabase.setNumberOfWeeks(trip.getNumberOfWeeks());
                tripFromDatabase.setTotalPriceSEK(trip.getTotalPriceSEK());
                tripFromDatabase.setTotalPricePLN(trip.getTotalPricePLN());
                tripFromDatabase.setDestination(tripDestinationService.checkIfExistsInDatabaseIfNotSave(trip.getDestination(), true));
            }
            return tripFromDatabase;
        }

        trip.setDestination(tripDestinationService.checkIfExistsInDatabaseIfNotSave(trip.getDestination(), true));
        if (autoSave) {
            trip = save(trip);
        }
        return trip;
    }

    @Override
    @Transactional
    public Trip makeSureCurrencyIsUpdated(Trip trip, boolean autoSave) throws IOException {
        double totSEK = trip.getDestination().getPricePerWeek() * trip.getNumberOfWeeks();
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
}
