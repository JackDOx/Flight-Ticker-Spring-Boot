package com.ltrha.ticket.controllers.admin;


import com.ltrha.ticket.constant.web.route.CommonRoute;
import com.ltrha.ticket.domain.request.flight.AddFlightRequest;
import com.ltrha.ticket.domain.request.flight.UpdateFlightRequest;
import com.ltrha.ticket.domain.response.ApiResponse;
import com.ltrha.ticket.models.FlightDetailEntity;
import com.ltrha.ticket.service.impl.FlightDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = {CommonRoute.API_ADMIN_BASE_URL + "/flights"})
public class AdminFlightDetailController {

    private final FlightDetailService flightDetailService;

    @GetMapping(path = "/all")
    private ResponseEntity<?> getAllFlights(){
        return ResponseEntity.ok(flightDetailService.getAllFlights());
    }

    @PostMapping(path = "/create")
    private ResponseEntity<?> addFlight(@RequestBody AddFlightRequest addFlightRequest){
        flightDetailService.addFlight(addFlightRequest);

        return ResponseEntity.ok(new ApiResponse(true, "Add flight successfully"));
    }
    @PostMapping(path = "/update")
    private ResponseEntity<?> updateFlight(@RequestBody UpdateFlightRequest updateFlightRequest){

        flightDetailService.updateFlightDetail(updateFlightRequest);

        return ResponseEntity.ok(new ApiResponse(true, "Update flight successfully"));
    }
}
