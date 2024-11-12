package com.ltrha.ticket.repositories;

import com.ltrha.ticket.models.LocationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocationRepository extends JpaRepository<LocationEntity, Integer> {
    Page<LocationEntity> findAll(Pageable pageable);

    Page<LocationEntity> findByLocationNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);
}
