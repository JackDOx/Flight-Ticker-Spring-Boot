package com.ltrha.ticket.repositories;

import com.ltrha.ticket.models.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

    public RoleEntity findByName(String name);
}
