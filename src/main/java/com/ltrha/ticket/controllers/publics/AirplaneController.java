package com.ltrha.ticket.controllers.publics;


import com.ltrha.ticket.constant.web.route.CommonRoute;
import com.ltrha.ticket.service.impl.AirplaneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(CommonRoute.API_BASE_URL +"/airplanes")
public class AirplaneController {

    private final AirplaneService airplaneService;
    @GetMapping("/get/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        var airplane = airplaneService.findById(id);

        return ResponseEntity.ok(airplane);

    }
}
