package com.ltrha.ticket.repositories;

import com.ltrha.ticket.models.AirplaneClassOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirplaneSeatOptionRepository extends JpaRepository<AirplaneClassOption, Integer> {
}
