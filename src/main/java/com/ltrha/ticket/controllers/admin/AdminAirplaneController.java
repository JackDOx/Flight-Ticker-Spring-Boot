package com.ltrha.ticket.controllers.admin;


import com.ltrha.ticket.domain.request.airplane.AddAirplaneRequest;
import com.ltrha.ticket.modules.builders.ResponseEntityBuilder;
import com.ltrha.ticket.service.impl.AirplaneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/admin/airplanes")
public class AdminAirplaneController {
    private final AirplaneService airplaneService;
    @PostMapping(path = "/create")
    public ResponseEntity<?> createAirplane(@RequestBody AddAirplaneRequest addAirplaneClassOptionRequest){
        airplaneService.addAirplane(addAirplaneClassOptionRequest);
        return ResponseEntityBuilder.getBuilder()
                .setSuccess(true)
                .build();

    }

}
