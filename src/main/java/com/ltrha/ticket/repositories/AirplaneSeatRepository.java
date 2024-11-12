package com.ltrha.ticket.repositories;

import com.ltrha.ticket.models.AirplaneSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AirplaneSeatRepository extends JpaRepository<AirplaneSeat, Integer> {
    public List<AirplaneSeat> findByAirplaneDetail_Id(int airplaneDetailId);
}
