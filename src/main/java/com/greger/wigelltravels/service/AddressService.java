package com.greger.wigelltravels.service;

import com.greger.wigelltravels.entity.Address;

import java.util.List;

public interface AddressService {
    List<Address> findAll();
    Address findById(int id);
    Address save(Address address);
    void deleteById(int id);
}
