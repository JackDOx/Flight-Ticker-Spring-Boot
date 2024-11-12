package com.ltrha.ticket.repositories;

import com.ltrha.ticket.models.AirplaneDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirplaneRepository extends JpaRepository<AirplaneDetail, Integer> {
}
