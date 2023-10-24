package com.greger.wigelltravels.service;

import com.greger.wigelltravels.dao.AddressRepository;
import com.greger.wigelltravels.dao.CustomerRepository;
import com.greger.wigelltravels.dao.TripRepository;
import com.greger.wigelltravels.entity.Customer;
import com.greger.wigelltravels.entity.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return customerRepository.findAll();
    }

    @Override
    public Customer findById(int id) {
        Optional<Customer> c = customerRepository.findById(id);
        Customer customer;
        if (c.isPresent()){
            customer = c.get();
//            List<Trip> tripListFromDb = tripService.findTripsByCustomerId(customer.getCustomerId());
//            if (tripListFromDb != null){
//                customer.setTrips(tripListFromDb);
//                System.out.println("@@@: " + tripListFromDb.size());
//            }
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
    @Transactional
    public void deleteById(int id) {
        customerRepository.deleteById(id);
    }
}
