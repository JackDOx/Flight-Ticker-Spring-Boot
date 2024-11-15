package com.ltrha.ticket.repositories;

import com.ltrha.ticket.models.AirlineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirlineRepository extends JpaRepository<AirlineEntity, Integer> {

}
