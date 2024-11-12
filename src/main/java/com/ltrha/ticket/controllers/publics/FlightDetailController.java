package com.ltrha.ticket.controllers.publics;

import com.ltrha.ticket.constant.web.route.CommonRoute;
import com.ltrha.ticket.domain.response.ApiDataResponse;
import com.ltrha.ticket.domain.response.flight.FlightDetailResponse;
import com.ltrha.ticket.models.FlightDetailEntity;
import com.ltrha.ticket.modules.builders.ResponseEntityBuilder;
import com.ltrha.ticket.pagination.PaginationPage;
import com.ltrha.ticket.pagination.PaginationRequest;
import com.ltrha.ticket.service.impl.FlightDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = CommonRoute.API_BASE_URL + "/flights")
public class FlightDetailController {

    private final FlightDetailService flightDetailService;


    @GetMapping(path = "/all")
    public ResponseEntity<?> getFlightDetails() {
        var flights = flightDetailService.getMany();

        return ResponseEntity.ok(new ApiDataResponse<>(flights, true, "Get all flights details successfully"));


    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getFlightDetailById(@PathVariable int id) {
        var flightDetail = flightDetailService.findById(id);

        return ResponseEntity
                .ok(new ApiDataResponse<>(flightDetail, true, "Flight details with id " + id));
    }

    @GetMapping(path = "/search")
    public ResponseEntity<?> searchFlightDetailsByDepartureLocationAndDestination(
            @RequestParam(name = "from") int fromId,
            @RequestParam(name = "to") int toId,
            @RequestParam(name = "page", defaultValue = "1", required = false) int page,
            @RequestParam(name = "limit", defaultValue = "10", required = false) int limit
    ) {
        var paginationRequest = new PaginationRequest(page, limit);
        PaginationPage<FlightDetailResponse> flightsPage = flightDetailService.findByDepartureLocationIdAndDestination(fromId, toId, page, limit);

        return ResponseEntityBuilder
                .getBuilder()
                .setCode(HttpStatus.OK)
                .set("total", flightsPage.getTotalRecords())
                .set("data", flightsPage.getRecords())
                .build();
    }
}
