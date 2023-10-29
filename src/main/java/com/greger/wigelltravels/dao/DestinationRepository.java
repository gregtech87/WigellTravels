package com.greger.wigelltravels.dao;

import com.greger.wigelltravels.entity.Address;
import com.greger.wigelltravels.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DestinationRepository extends JpaRepository<Destination, Integer> {

    @Query("SELECT d FROM Destination d WHERE d.hotellName = :hotell_name AND d.city = :city AND d.country = :country")
    Destination findDestinationByHotellNameAndCityAndCountry(@Param("hotell_name") String hotellName, @Param("city") String city, @Param("country") String country);
}
