package com.greger.wigelltravels.service;

import com.greger.wigelltravels.dao.AddressRepository;
import com.greger.wigelltravels.entity.Address;
import com.greger.wigelltravels.entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        addressRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Address checkIfExistsInDatabaseIfNotSave(Address address, boolean autoSave) {

        String street = address.getStreet();
        int postalCode = address.getPostalCode();
        String city = address.getCity();
        System.out.println("###############################################################################");
        System.out.println("INKOMMANDE: " + address);

        Address addressFromDatabase = addressRepository.findAddressByStreetAndPostalCodeAndCity(street, postalCode, city);
        if (addressFromDatabase != null){
            System.out.println("FRÅN DB: " + addressFromDatabase);
            return addressFromDatabase;
        }
        address.setId(0);
        if (autoSave) {
            addressFromDatabase = save(address);
            System.out.println("SPARAD: " + addressFromDatabase);
            return addressFromDatabase;
        }

        System.out.println("###############################################################################");
        return address;
    }
}
