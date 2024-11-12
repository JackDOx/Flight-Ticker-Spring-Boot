package com.ltrha.ticket.controllers.publics;

import com.ltrha.ticket.constant.web.route.UserRoute;
import com.ltrha.ticket.constant.web.route.CommonRoute;
import com.ltrha.ticket.domain.request.user.UpdateUserDetailsRequest;
import com.ltrha.ticket.domain.response.ApiResponse;
import com.ltrha.ticket.modules.builders.ResponseEntityBuilder;
import com.ltrha.ticket.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = CommonRoute.API_BASE_URL + UserRoute.BASE_URL)
@RequiredArgsConstructor
public class UserController {

    Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;


    @GetMapping(path = {"/{id}"})
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        var user = userService.getUserById(id);


        return ResponseEntity.ok()
                .body(user)
                ;
    }

//    @PostMapping(path = {"/update"})
//    public ResponseEntity<?> update(@RequestBody UpdateUserDetailsRequest request) {
//
//        log.info("Updating user: " + request.getId());
//        userService.updateMe(request);
//
//        return ResponseEntity.ok(new ApiResponse(true, "Update user detail successfully"));
//    }

    @GetMapping(path = {"/me"})
    public ResponseEntity<?> getMe() {
        var user = userService.getMe();

        return ResponseEntity.ok()
                .body(user)
                ;
    }

    @GetMapping(path = {"/me/tickets"})
    public ResponseEntity<?> getMyTickets() {
        var tickets = userService.getMyTickets();

        return ResponseEntityBuilder
                .getBuilder()
                .setCode(HttpStatus.OK)
                .set("data", tickets)
                .set("status", "success" )
                .build()
                ;
    }

    @GetMapping(path = {"/me/ticket"})
    public ResponseEntity<?> getMyTicketById(@RequestParam UUID id) {
        var ticket = userService.getMyTicketById(id);

        return ResponseEntityBuilder
                .getBuilder()
                .setCode(HttpStatus.OK)
                .set("data", ticket)
                .set("status", "success" )
                .build()
                ;
    }
}
