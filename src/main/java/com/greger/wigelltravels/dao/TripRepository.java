package com.greger.wigelltravels.dao;

import com.greger.wigelltravels.entity.Address;
import com.greger.wigelltravels.entity.Destination;
import com.greger.wigelltravels.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Integer> {
//    @Query("SELECT t FROM Trip t WHERE t.departureDate = :date AND t.destination = :destination")
//    Trip findAddressByStreetAndPostalCodeAndCity(@Param("date") String date, @Param("destination")int destination);

//    @Query("SELECT c.customerId, t.tripId, t.departureDate, d.id, d.city, d.country, d.hotellName " +
//            "FROM Customer c " +
//            "JOIN c.trips t " +
//            "JOIN t.destination d " +
//            "WHERE c.customerId = :customerId AND t.departureDate = :departureDate")
@Query("SELECT t FROM Customer c JOIN c.trips t WHERE c.customerId = :customerId AND t.departureDate = :departureDate")
Trip findTripByCustomerIdAndDepartureDate(@Param("departureDate") String departureDate, @Param("customerId") int customerId);

    @Query("SELECT t FROM Customer c JOIN c.trips t WHERE c.customerId = :customerId")
//    @Query("SELECT c.customerId, t " +
//            "FROM Customer c " +
//            "JOIN CustomTripJoinTable ct ON c.customerId = ct.customerId " +
//            "JOIN Trip t ON ct.tripId = t.tripId WHERE c.customerId = :customerId")
    List<Trip> findTripsByCustomerId(int customerId);
}
