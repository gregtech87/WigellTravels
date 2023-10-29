//package com.greger.wigelltravels.entity;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "location")
//public class Location {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "location_id")
//    private int id;
//    @Column(name = "city")
//    private String city;
//    @Column(name = "country")
//    private String country;
//
//    public Location() {
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public String getCountry() {
//        return country;
//    }
//
//    public void setCountry(String country) {
//        this.country = country;
//    }
//
//    @Override
//    public String toString() {
//        return "Location{" +
//                "id=" + id +
//                ", city='" + city + '\'' +
//                ", country='" + country + '\'' +
//                '}';
//    }
//}
