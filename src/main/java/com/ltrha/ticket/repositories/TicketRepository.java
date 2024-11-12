package com.ltrha.ticket.repositories;

import com.ltrha.ticket.constant.business.TicketBookingStatusCode;
import com.ltrha.ticket.models.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface TicketRepository extends JpaRepository<TicketEntity, UUID> {

    public List<TicketEntity> findByBookedById(String id);

    Optional<TicketEntity> findByIdAndBookedById(UUID ticketId, String id);

    @Query("SELECT t FROM TicketEntity t JOIN FETCH t.flightDetail JOIN FETCH t.airplaneClassOption WHERE t.id = :ticketId")
    Optional<TicketEntity> findByIdFetchAll(UUID ticketId);

    void deleteByStatus(int status);

    @Query("SELECT t FROM TicketEntity t WHERE t.createdDate < :minimalDate AND t.status = :status")
    List<TicketEntity> findTicketWithCreatedDateBefore(LocalDateTime minimalDate, int status);
}
