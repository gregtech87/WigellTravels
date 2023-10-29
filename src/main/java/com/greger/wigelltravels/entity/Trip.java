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
    private double totalPriceSEK;
    @Column(name = "total_price_PLN")
    private double totalPricePLN;

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

    public Trip(String departureDate, int numberOfWeeks, double totalPriceSEK, double totalPricePLN) {
        this.departureDate = departureDate;
        this.numberOfWeeks = numberOfWeeks;
        this.totalPriceSEK = totalPriceSEK;
        this.totalPricePLN = totalPricePLN;
    }

    public Trip(String departureDate, int numberOfWeeks, double totalPriceSEK, double totalPricePLN, Destination destination) {
        this.departureDate = departureDate;
        this.numberOfWeeks = numberOfWeeks;
        this.totalPriceSEK = totalPriceSEK;
        this.totalPricePLN = totalPricePLN;
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

    public double getTotalPriceSEK() {
        return totalPriceSEK;
    }

    public void setTotalPriceSEK(double totalPriceSek) {
        this.totalPriceSEK = totalPriceSek;
    }

    public double getTotalPricePLN() {
        return totalPricePLN;
    }

    public void setTotalPricePLN(double totalPricePln) {
        this.totalPricePLN = totalPricePln;
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
                ", totalPriceSek=" + totalPriceSEK +
                ", totalPricePln=" + totalPricePLN +
                ", destination=" + destination +
//                ", customers=" + customers +
                '}';
    }
}
