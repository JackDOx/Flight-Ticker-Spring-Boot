package com.ltrha.ticket.repositories;

import com.ltrha.ticket.models.AirportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<AirportEntity, Integer> {
}
