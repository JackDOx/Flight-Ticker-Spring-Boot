package com.ltrha.ticket.controllers.admin;


import com.ltrha.ticket.constant.web.route.CommonRoute;
import com.ltrha.ticket.constant.web.route.UserRoute;
import com.ltrha.ticket.domain.response.ApiDataResponse;
import com.ltrha.ticket.models.UserEntity;
import com.ltrha.ticket.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = {CommonRoute.API_ADMIN_BASE_URL +  UserRoute.BASE_URL})
public class AdminUserController {
    private final UserService userService;


    @GetMapping(path = "/get/{page}")
    public ResponseEntity<?> getMany(@PathVariable(name = "page") int pageIndex) {
        List<UserEntity> users = new ArrayList<>();


        return ResponseEntity
                .ok(new ApiDataResponse<List<UserEntity>>(users, true, ""));

    }

//    @GetMapping(path = {"/all"})
//    public ResponseEntity<?> getAll() {
//        var users = userService.getManyUser();
//
//
//        return ResponseEntity.ok()
//                .body(users)
//                ;
//    }
}
