package com.greger.wigelltravels.dao;

import com.greger.wigelltravels.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    @Query("SELECT a FROM Address a WHERE a.street = :street AND a.postalCode = :postalCode AND a.city = :city")
    Address findAddressByStreetAndPostalCodeAndCity(@Param("street") String street, @Param("postalCode") int postalCode, @Param("city") String city);
}
