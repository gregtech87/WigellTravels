package com.greger.wigelltravels.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "destination")
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private int id;
    @Column(name = "hotell_name")
    private String hotellName;
    @Column(name = "price_per_week")
    private double pricePerWeek;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;
}
