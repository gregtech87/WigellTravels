package com.greger.wigelltravels.service;

import com.greger.wigelltravels.entity.Destination;

import java.util.List;

public interface DestinationService {
    List<Destination> findAll();
    Destination findById(int id);
    Destination save(Destination destination);
    Destination updateDestination(int id, Destination destination, boolean randomize);
    String deleteById(int id);
    Destination checkIfExistsInDatabaseIfNotSave(Destination destination, boolean autoSave);
}
