package com.greger.wigelltravels;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.greger.wigelltravels.entity.*;
import com.greger.wigelltravels.service.AddressService;
import com.greger.wigelltravels.service.CustomerService;
import com.greger.wigelltravels.service.DestinationService;
import com.greger.wigelltravels.service.TripService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class WigellTravelsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WigellTravelsApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CustomerService customerService, TripService tripService, DestinationService destinationService, AddressService addressService) {
        return runner -> {
            // Setting URL
            String url_str = "https://open.er-api.com/v6/latest/SEK";

// Making Request
            URL url = new URL(url_str);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

// Convert to JSON
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject jsonobj = root.getAsJsonObject();

            int asd;


// Accessing object
            String req_result = jsonobj.get("result").getAsString();
            System.out.println(req_result);

            Map map = new Gson().fromJson(jsonobj.get("rates"), Map.class);
            System.out.println(map.get("SEK"));
            System.out.println(map.get("PLN"));
            System.out.println(map);
            System.out.println(map.size());
            System.out.println(map.values());
            double zloty = (double) map.get("PLN");
            System.out.println(zloty);



//            Customer customer1 = new Customer();
//            customer1.setAddress(new Address(0,"street", 88888, "cityy"));
//            System.out.println(customer1);
//            customerService.save(customer1);

//            Customer customer2 = new Customer();
//            customer2.setUserName("XXXXXXXXXXXXXXX");
//            customer2.setAddress(addressService.findById(4));
//            customerService.save(customer2);
//
//
            Address address = addressService.findById(4);
//            address.setId(0);
            System.out.println("************************* "+address);


            Trip trip = tripService.findById(1);
            trip.setDestination(destinationService.findById(1));
            System.out.println(trip);


            Trip trip1 = tripService.findById(2);
            trip1.setDestination(destinationService.findById(2));
            System.out.println(trip1);

            Trip trip2 = new Trip();
            trip2.setDestination(destinationService.findById(5));

            List<Trip> tripList = new ArrayList<>();
            tripList.add(trip);
            tripList.add(trip1);
            tripList.add(trip2);

            System.out.println(tripList);
            Customer customer = new Customer();
            customer.setPhone(445);
            customer.setFirstName("afsdfsdf");
            customer.setLastName("ssss");
            customer.setDateOfBirth("AA");
            customer.setEmail("email");
            customer.setUserName("username");
            customer.setPassword("password");
            customer.setAddress(address);
            customer.setTrips(tripList);

            System.out.println(customer);
            customerService.save(customer);



        };





    }


}
