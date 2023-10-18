package com.greger.wigelltravels.service;

import com.greger.wigelltravels.dao.TripRepository;
import com.greger.wigelltravels.entity.Address;
import com.greger.wigelltravels.entity.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TripServiceImpl implements TripService{

    private TripRepository tripRepository;

    @Autowired
    public TripServiceImpl(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Override
    public List<Trip> findAll() {
        return tripRepository.findAll();
    }

    @Override
    public Trip findById(int id) {
        Optional<Trip> t = tripRepository.findById(id);
        Trip trip;
        if (t.isPresent()){
            trip = t.get();
        }
        else {
            throw new RuntimeException("Trip with id: " + id + " could not be found!");
        }
        return trip;
    }

    @Override
    public Trip save(Trip trip) {
        return tripRepository.save(trip);
    }

    @Override
    public void deleteById(int id) {
        tripRepository.deleteById(id);
    }
}
