package com.greger.wigelltravels.dao;

import com.greger.wigelltravels.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Integer> {
}
