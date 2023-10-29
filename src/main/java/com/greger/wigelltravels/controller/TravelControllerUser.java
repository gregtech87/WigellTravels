package com.greger.wigelltravels.controller;

import com.greger.wigelltravels.entity.Destination;
import com.greger.wigelltravels.entity.Trip;
import com.greger.wigelltravels.service.DestinationService;
import com.greger.wigelltravels.service.TripService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

 @RestController
@RequestMapping("/api/v1")
 public class TravelControllerUser {

    private TripService tripService;
    private DestinationService destinationService;

    public TravelControllerUser(TripService tripService, DestinationService destinationService) {
        this.tripService = tripService;
        this.destinationService = destinationService;
    }

    @GetMapping("/trips")
    public List<Destination> findAllDestinations() {
        return destinationService.findAll();
    }

    @PostMapping("/trips")
    public Trip saveTrip(@RequestBody Trip trip) {
        if (trip.getTripId() > 0){
            trip.setTripId(0);
        }
        return tripService.save(trip);
    }

     @PutMapping("/trips/{id}")
     public Trip updateTrip(@PathVariable int id, @RequestBody Trip trip) {
         return tripService.update(id, trip);
     }

     @GetMapping("/trips/{id}")
     public Trip getTrip(@PathVariable int id) {
         return tripService.findById(id);
     }

}