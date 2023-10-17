package com.greger.wigelltravels.service;

import com.greger.wigelltravels.entity.Trip;

import java.util.List;

public interface TripService {
    List<Trip> findAll();
    Trip findById(int id);
    Trip save(Trip trip);
    void deleteById(int id);
}
