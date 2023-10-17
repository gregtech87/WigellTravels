package com.greger.wigelltravels.dao;

import com.greger.wigelltravels.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressDAO extends JpaRepository<Address, Long> {
}
