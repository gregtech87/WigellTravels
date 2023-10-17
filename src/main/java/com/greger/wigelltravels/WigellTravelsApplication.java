package com.greger.wigelltravels;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@SpringBootApplication
public class WigellTravelsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WigellTravelsApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
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
        };
    }
}
