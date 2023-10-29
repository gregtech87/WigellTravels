package com.greger.wigelltravels.service;

import com.greger.wigelltravels.entity.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressService {
    List<Address> findAll();
    Address findAddressById(int id);
    Address updateAddress(int id, Address address);
    Address saveAddress(Address address);
    void deleteAddressById(int id);
    Address findAddressByStreetAndPostalCodeAndCity(String street, int postalCode, String city);
    Address checkIfExistsInDatabaseIfNotSave(Address address, boolean autoSave);

}
