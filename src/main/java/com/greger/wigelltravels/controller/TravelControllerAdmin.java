package com.greger.wigelltravels.controller;

import com.greger.wigelltravels.CurrencyConverter;
import com.greger.wigelltravels.entity.Customer;
import com.greger.wigelltravels.entity.Destination;
import com.greger.wigelltravels.entity.Trip;
import com.greger.wigelltravels.service.CustomerService;
import com.greger.wigelltravels.service.DestinationService;
import com.greger.wigelltravels.service.TripService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//@CrossOrigin(origins = "http://127.0.0.1:5500/index.html", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class TravelControllerAdmin {

    private final CustomerService customerService;
    private final DestinationService destinationService;
    private final TripService tripService;

    public TravelControllerAdmin(CustomerService customerService, DestinationService destinationService, TripService tripService) {
        this.customerService = customerService;
        this.destinationService = destinationService;
        this.tripService = tripService;
    }

//        @GetMapping("/username")
//    public String currentUserName(Authentication authentication) {
//            System.out.println(authentication.getAuthorities());
//        return authentication.getAuthorities().toString();
//    }
    @GetMapping("/customers")
    public List<Customer> findAll() {
        return customerService.findAllCustomers();
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomerById(@PathVariable int id) {
        return customerService.findCustomerById(id);
    }

    @GetMapping("/destination/{id}")
    public Destination getDestinationById(@PathVariable int id) {
        return destinationService.findById(id);
    }

    @PostMapping("/customers")
    public Customer saveTrip(@RequestBody Customer customer) {
        if (customer.getCustomerId() > 0) {
            customer.setCustomerId(0);
        }
        return customerService.saveCustomer(customer);
    }

    @PutMapping("/customers/{id}")
    public Customer updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

    @DeleteMapping("/customers/{id}")
    public String deleteCustomer(@PathVariable int id) {
        customerService.deleteCustomerById(id);
        return ("Customer with id: " + id + " has been deleted!");
    }

    @DeleteMapping("/trips/{id}")
    public String deleteCustomerTrip(@PathVariable int id) {
        tripService.deleteById(id);
        return ("Trip with id: " + id + " has been deleted!");
    }

    @PostMapping("/destination")
    public Destination saveDestination(@RequestBody Destination destination) {
        if (destination.getId() > 0) {
            destination.setId(0);
        }
        return destinationService.save(destination);
    }

    @PutMapping("/destination/{id}")
    public Destination updateDestination(@PathVariable int id, @RequestBody Destination destination) {
        return destinationService.updateDestination(id, destination, false);
    }

    @DeleteMapping("/destination/{id}")
    public String deleteById(@PathVariable int id) {
        return (destinationService.deleteById(id));
    }

    @GetMapping("/alltrips")
    public List<Trip> getAllTrips() {
        return tripService.findAll();
    }

    @GetMapping("/currency/{total}/{currancy}")
    public Double getCurrency(@PathVariable double total, @PathVariable String currancy) throws IOException {
        return CurrencyConverter.SekToRequestedCurrency(total, currancy);
    }
}



