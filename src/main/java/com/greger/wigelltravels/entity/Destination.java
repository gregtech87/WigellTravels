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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;

    public Destination() {
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Destination{" +
                "id=" + id +
                ", hotellName='" + hotellName + '\'' +
                ", pricePerWeek=" + pricePerWeek +
                ", location=" + location +
                '}';
    }
}

