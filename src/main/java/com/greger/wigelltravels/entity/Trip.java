package com.greger.wigelltravels.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private int tripId;
//    @Column(name = "customer_id")
//    private int customerId;
    @Column(name = "departure_date")
    private String departureDate;
    @Column(name = "no_of_weeks")
    private int numberOfWeeks;
    @Column(name = "total_price_SEK")
    private double totalPriceSek;
    @Column(name = "total_price_PLN")
    private double totalPricePln;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "destination_id")
    private Destination destination;

//    @ManyToMany
//    @JoinTable(name = "customer_trips",
//            joinColumns = {@JoinColumn(name = "trip_id")},
//            inverseJoinColumns = {@JoinColumn(name = "customer_id")})
//    private List<Customer> customers = new ArrayList<>();

    public Trip() {
    }

    public Trip(String departureDate, int numberOfWeeks, double totalPriceSek, double totalPricePln) {
        this.departureDate = departureDate;
        this.numberOfWeeks = numberOfWeeks;
        this.totalPriceSek = totalPriceSek;
        this.totalPricePln = totalPricePln;
    }

    public Trip(String departureDate, int numberOfWeeks, double totalPriceSek, double totalPricePln, Destination destination) {
        this.departureDate = departureDate;
        this.numberOfWeeks = numberOfWeeks;
        this.totalPriceSek = totalPriceSek;
        this.totalPricePln = totalPricePln;
        this.destination = destination;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int id) {
        this.tripId = id;
    }

//    public int getCustomerId() {
//        return customerId;
//    }
//
//    public void setCustomerId(int customerId) {
//        this.customerId = customerId;
//    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public int getNumberOfWeeks() {
        return numberOfWeeks;
    }

    public void setNumberOfWeeks(int numberOfWeeks) {
        this.numberOfWeeks = numberOfWeeks;
    }

    public double getTotalPriceSek() {
        return totalPriceSek;
    }

    public void setTotalPriceSek(double totalPriceSek) {
        this.totalPriceSek = totalPriceSek;
    }

    public double getTotalPricePln() {
        return totalPricePln;
    }

    public void setTotalPricePln(double totalPricePln) {
        this.totalPricePln = totalPricePln;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

//    public List<Customer> getCustomers() {
//        return customers;
//    }
//
//    public void setCustomers(List<Customer> customers) {
//        this.customers = customers;
//    }

    @Override
    public String toString() {
        return "Trip{" +
                "tripId=" + tripId +
//                ", customerId=" + customerId +
                ", departureDate='" + departureDate + '\'' +
                ", numberOfWeeks=" + numberOfWeeks +
                ", totalPriceSek=" + totalPriceSek +
                ", totalPricePln=" + totalPricePln +
                ", destination=" + destination +
//                ", customers=" + customers +
                '}';
    }
}
