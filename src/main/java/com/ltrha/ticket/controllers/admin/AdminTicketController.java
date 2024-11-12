package com.ltrha.ticket.controllers.admin;


import com.ltrha.ticket.constant.web.route.CommonRoute;
import com.ltrha.ticket.service.impl.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = {CommonRoute.API_ADMIN_BASE_URL + "/ticket"})
public class AdminTicketController {

    private final BookingService bookingService;
//    @GetMapping(path = "/all")
//    private ResponseEntity<?> getAllTickets(){
//        return ResponseEntity.ok(bookingService.getAllTickets());
//    }
}
