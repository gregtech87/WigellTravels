package com.greger.wigelltravels.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "destination")
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "destination_id")
    private int id;
    @Column(name = "hotell_name")
    private String hotellName;
    @Column(name = "price_per_week")
    private double pricePerWeek;
    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;


    public Destination() {
    }

    public Destination(String hotellName, double pricePerWeek, String city, String country) {
        this.hotellName = hotellName;
        this.pricePerWeek = pricePerWeek;
        this.city = city;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHotellName() {
        return hotellName;
    }

    public void setHotellName(String hotellName) {
        this.hotellName = hotellName;
    }

    public double getPricePerWeek() {
        return pricePerWeek;
    }

    public void setPricePerWeek(double pricePerWeek) {
        this.pricePerWeek = pricePerWeek;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Destination{" +
                "id=" + id +
                ", hotellName='" + hotellName + '\'' +
                ", pricePerWeek=" + pricePerWeek +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}