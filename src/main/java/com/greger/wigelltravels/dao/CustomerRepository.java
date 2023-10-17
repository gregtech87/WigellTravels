package com.greger.wigelltravels.dao;

import com.greger.wigelltravels.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
