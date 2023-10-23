package com.greger.wigelltravels.service;

import com.greger.wigelltravels.entity.Destination;

import java.util.List;

public interface DestinationService {
    List<Destination> findAll();
    Destination findById(int id);
    Destination save(Destination destination);
    void deleteById(int id);
    Destination checkIfExistsInDatabaseIfNotSave(Destination destination);
}
