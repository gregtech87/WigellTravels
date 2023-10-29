package com.greger.wigelltravels.controller;

import com.greger.wigelltravels.entity.Customer;
import com.greger.wigelltravels.entity.Destination;
import com.greger.wigelltravels.service.CustomerService;
import com.greger.wigelltravels.service.DestinationService;
import com.greger.wigelltravels.service.TripService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TravelControllerAdmin {

    private CustomerService customerService;
    private TripService tripService;
    private DestinationService destinationService;

    public TravelControllerAdmin(CustomerService customerService, TripService tripService, DestinationService destinationService) {
        this.customerService = customerService;
        this.tripService = tripService;
        this.destinationService = destinationService;
    }

    @GetMapping("/customers")
    public List<Customer> findAll() {
        return customerService.findAllCustomers();
    }

    @PostMapping("/customers")
    public Customer saveCustomer(@RequestBody Customer customer) {
        if (customer.getCustomerId() > 0){
            customer.setCustomerId(0);
        }
        return customerService.saveCustomer(customer);
    }

    @PutMapping("/customers/{id}")
    public Customer updateCustomer(@PathVariable int id, @RequestBody Customer customer){
        return customerService.updateCustomer(id, customer);
    }

    @DeleteMapping("/customers/{id}")
    public String deleteCustomer(@PathVariable int id) {
        customerService.deleteCustomerById(id);
        return ("Customer with id: " + id + " has been deleted!");
    }

    @PostMapping("/destination")
    public Destination saveDestination(@RequestBody Destination destination) {
        if (destination.getId() > 0){
            destination.setId(0);
        }
        return destinationService.save(destination);
    }

    @PutMapping("/destination/{id}")
    public Destination updateDestination(@PathVariable int id, @RequestBody Destination destination){
        return destinationService.updateDestination(id, destination);
    }

    @DeleteMapping("/destination/{id}")
    public String deleteById(@PathVariable int id){
        return (destinationService.deleteById(id));
    }
}
