package com.greger.wigelltravels.service;

import com.greger.wigelltravels.dao.AddressRepository;
import com.greger.wigelltravels.dao.CustomerRepository;
import com.greger.wigelltravels.dao.TripRepository;
import com.greger.wigelltravels.entity.Customer;
import com.greger.wigelltravels.entity.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{


    private CustomerRepository customerRepository;
    private TripService tripService;
    private AddressService addressService;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, TripService tripService, AddressService addressService) {
        this.customerRepository = customerRepository;
        this.tripService = tripService;
        this.addressService = addressService;
    }


    @Override
    public List<Customer> findAll() {
        List<Customer> customerList = customerRepository.findAll();
        for (Customer c : customerList){
            for (Trip t: c.getTrips()) {
                try {
                    t = tripService.makeSureCurrencyIsUpdated(t, true);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return customerList;
    }

    @Override
    public Customer findById(int id) {
        Optional<Customer> c = customerRepository.findById(id);
        Customer customer;
        if (c.isPresent()){
            customer = c.get();
            for (Trip t: customer.getTrips()){
                try {
                    t = tripService.makeSureCurrencyIsUpdated(t, true);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        else {
            throw new RuntimeException("Customer with id: " + id + " could not be found!");
        }
        return customer;
    }

    @Override
    @Transactional
    public Customer save(Customer customer) {
        System.out.println("HEJ HOPP GUMMI SNOPP: "+customer);
        customer.setAddress(addressService.checkIfExistsInDatabaseIfNotSave(customer.getAddress(), true));
        customer.setTrips(tripService.inspectTripList(customer.getTrips(), customer.getCustomerId()));
        return customerRepository.save(customer);
    }

    @Override
    public Customer update(int id, Customer customer) {
        Customer customerFromDb = findById(id);
        customerFromDb.setUserName(customer.getUserName());
        customerFromDb.setPassword(customer.getPassword());
        customerFromDb.setFirstName(customer.getFirstName());
        customerFromDb.setLastName(customer.getLastName());
        customerFromDb.setAddress(addressService.checkIfExistsInDatabaseIfNotSave(customer.getAddress(), true));
        customerFromDb.setTrips(tripService.inspectTripList(customer.getTrips(),id));
        customerFromDb.setEmail(customer.getEmail());
        customerFromDb.setPhone(customer.getPhone());
        customerFromDb.setDateOfBirth(customer.getDateOfBirth());
        return customerFromDb;
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Customer customer = findById(id);
        List<Trip> tripList = customer.getTrips();
        List<Trip> tripListForRemoval = new ArrayList<>();
        for (int i = tripList.size()-1; i >= 0; i--){
            tripListForRemoval.add(tripList.get(i));
            tripList.remove(i);
        }
        save(customer);
        for (Trip t : tripListForRemoval){
            tripService.deleteById(t.getTripId());
        }
        customerRepository.deleteById(id);
    }
}
