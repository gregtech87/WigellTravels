package com.greger.wigelltravels.service;

import com.greger.wigelltravels.dao.TripRepository;
import com.greger.wigelltravels.entity.Destination;
import com.greger.wigelltravels.entity.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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
        trip.setDestination(destinationService.checkIfExistsInDatabaseIfNotSave(trip.getDestination()));
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
        trip.setDestination(destinationService.findById(trip.getDestination().getId()));
        trip = save(trip);

        System.out.println("SPARAD: " + trip);
        System.out.println("###############################################################################");
        return trip;
    }

    @Override
    public List<Trip> findTripsByCustomerId(int customerId) {
        return tripRepository.findTripsByCustomerId(customerId);
    }
}
