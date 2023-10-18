package com.greger.wigelltravels.service;

import com.greger.wigelltravels.dao.AddressRepository;
import com.greger.wigelltravels.entity.Address;
import com.greger.wigelltravels.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService{

    private AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }


    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public Address findById(int id) {
        Optional<Address> a = addressRepository.findById(id);
        Address address;
        if (a.isPresent()){
            address = a.get();
        }
        else {
            throw new RuntimeException("Address with id: " + id + " could not be found!");
        }
        return address;
    }

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public void deleteById(int id) {
        addressRepository.deleteById(id);
    }
}
