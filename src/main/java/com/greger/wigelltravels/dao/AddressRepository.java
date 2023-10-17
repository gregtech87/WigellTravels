package com.greger.wigelltravels.dao;

import com.greger.wigelltravels.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
