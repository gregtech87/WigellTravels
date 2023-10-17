package com.greger.wigelltravels.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "destination_id")
    private int id;
    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;
}
