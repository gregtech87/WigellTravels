package com.greger.wigelltravels.service;

import com.greger.wigelltravels.dao.CustomerRepository;
import com.greger.wigelltravels.dao.TripRepository;
import com.greger.wigelltravels.entity.Customer;
import com.greger.wigelltravels.entity.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{


    private CustomerRepository customerRepository;
    private TripRepository tripRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, TripRepository tripRepository) {
        this.customerRepository = customerRepository;
        this.tripRepository = tripRepository;
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
        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        customerRepository.deleteById(id);
    }
}
