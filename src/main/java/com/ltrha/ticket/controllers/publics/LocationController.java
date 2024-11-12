package com.ltrha.ticket.controllers.publics;


import com.ltrha.ticket.models.LocationEntity;
import com.ltrha.ticket.modules.builders.ResponseEntityBuilder;
import com.ltrha.ticket.pagination.PaginationPage;
import com.ltrha.ticket.pagination.PaginationRequest;
import com.ltrha.ticket.repositories.LocationRepository;
import com.ltrha.ticket.service.impl.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;

@RequestMapping(path = "/api/location")
@RequiredArgsConstructor
@RestController
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/get")
    public ResponseEntity<?> getMany(@RequestParam int page, @RequestParam int limit) {

        PaginationPage<LocationEntity> responsePage = locationService.getMany(page, limit);

        return ResponseEntityBuilder.getBuilder()
                .setCode(HttpStatus.OK)
                .set("data", responsePage.getRecords())
                .set("total", responsePage.getTotalRecords())
                .build()
                ;
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {

        List<LocationEntity> locations = locationService.getAll();

        return ResponseEntityBuilder.getBuilder()
                .setCode(HttpStatus.OK)
                .set("data", locations)
                .set("total", locations.size())
                .build()
                ;
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String name, @RequestParam int limit) {
        var locations = locationService.searchByName(name, limit, 1).getRecords();
        return ResponseEntityBuilder.getBuilder()
                .setCode(HttpStatus.OK)
                .set("data", locations)
                .set("total", locations.size())
                .build()
                ;
    }

}
