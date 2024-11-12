package com.ltrha.ticket.service.impl;


import com.ltrha.ticket.exception.FlightTicketRuntimeException;
import com.ltrha.ticket.models.LocationEntity;
import com.ltrha.ticket.pagination.PaginationPage;
import com.ltrha.ticket.pagination.PaginationRequest;
import com.ltrha.ticket.repositories.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class LocationService {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private final LocationRepository locationRepository;

    public PaginationPage<LocationEntity> getMany(int page, int limit) {

        try {
            var pageRequest = new PaginationRequest(page, limit);
            var locationPage = locationRepository.findAll(pageRequest);
            return PaginationPage.fromPage(locationPage);
        } catch (Exception e) {
            logger.warn("Cannot get locations", e);
            throw new FlightTicketRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot get locations");
        }

    }

    public List<LocationEntity> getAll() {
        try {
            return locationRepository.findAll();
        } catch (Exception e) {
            logger.warn("Cannot get locations", e);
            throw new FlightTicketRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot get locations");
        }
    }

    public PaginationPage<LocationEntity> searchByName(String name, int limit, int page) {
        try {
            PaginationRequest paginationRequest = new PaginationRequest(page, limit);
            var responsePage = locationRepository.findByLocationNameContainingIgnoreCase(name, paginationRequest);
            return PaginationPage.fromPage(responsePage);
        } catch (Exception e) {
            throw new FlightTicketRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot get locations");
        }

    }

}
