package com.greger.wigelltravels.dao;

import com.greger.wigelltravels.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinationRepository extends JpaRepository<Destination, Integer> {
}
