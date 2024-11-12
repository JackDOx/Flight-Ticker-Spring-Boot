package com.ltrha.ticket.controllers.publics;

import com.ltrha.ticket.modules.builders.ResponseEntityBuilder;
import com.ltrha.ticket.service.impl.FlightSeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/seat")
@RequiredArgsConstructor
public class FlightSeatController {


    private final FlightSeatService flightSeatService;

    @GetMapping(path = "/info")
    public ResponseEntity<?> getFlightSeatsInfo(@RequestParam String flightCode) {
        return ResponseEntityBuilder
                .getBuilder()
                .set("data", flightSeatService.getFlightSeatsInfoByFlightCode(flightCode))
                .build();
    }

}
