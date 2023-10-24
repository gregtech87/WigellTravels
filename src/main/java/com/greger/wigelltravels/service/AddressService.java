package com.greger.wigelltravels.service;

import com.greger.wigelltravels.entity.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressService {
    List<Address> findAll();
    Address findById(int id);
    Address save(Address address);
    void deleteById(int id);
    Address checkIfExistsInDatabaseIfNotSave(Address address, boolean autoSave);

}
