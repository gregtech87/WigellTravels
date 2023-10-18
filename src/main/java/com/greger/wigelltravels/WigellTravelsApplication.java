package com.greger.wigelltravels;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.greger.wigelltravels.entity.*;
import com.greger.wigelltravels.service.CustomerService;
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
    CommandLineRunner commandLineRunner(CustomerService customerService) {
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







            Destination destination = new Destination();
            destination.setId(6);
            destination.setHotellName("D - HOTELLL");
            destination.setPricePerWeek(555);
            destination.setCity("cccitty");
            destination.setCountry("CCOuntry");

            Trip trip = new Trip();
            trip.setTripId(2);
            trip.setDepartureDate("date");
            trip.setDestination(destination);
            trip.setNumberOfWeeks(3);


            Trip trip1 = trip;

            List<Trip> tripList = new ArrayList<>();
            tripList.add(trip);
            tripList.add(trip1);

            Customer customer = new Customer();
            customer.setFirstName("afsdfsdf");
            customer.setLastName("ssss");
            customer.setDateOfBirth("AA");
            customer.setEmail("email");
            customer.setUserName("username");
            customer.setPassword("password");
            customer.setAddress(new Address(2, "street", 111, "city"));
            customer.setTrips(tripList);

            System.out.println(customer);
//            customerService.save(customer);

        };





    }


}
