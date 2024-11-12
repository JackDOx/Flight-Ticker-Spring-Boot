package com.ltrha.ticket.service.impl;

import com.ltrha.ticket.config.security.UserPrincipal;
import com.ltrha.ticket.domain.mapper.TicketMapper;
import com.ltrha.ticket.domain.mapper.UserMapper;
import com.ltrha.ticket.domain.response.ticket.TicketResponse;
import com.ltrha.ticket.domain.response.UserDetailResponse;
import com.ltrha.ticket.domain.response.user.UserResponse;
import com.ltrha.ticket.exception.FlightTicketRuntimeException;
import com.ltrha.ticket.models.UserEntity;
import com.ltrha.ticket.repositories.TicketRepository;
import com.ltrha.ticket.service.impl.client.UserServiceClient;
import com.ltrha.ticket.utils.auth.AuthenticationUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    //    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final UserServiceClient userServiceClient;

    private final UserMapper userMapper;

    public UserDetailResponse getUserById(UUID id) {
        UserEntity user = userServiceClient.getUser(id.toString())
                .orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, ""));

        return new UserDetailResponse(id, user.getFullName(), user.getEmail(), user.getPhoneNumber(), user.getDob());

    }

//    @CacheEvict(value = "users", allEntries = true)
//    public void updateMe(UpdateUserDetailsRequest request) {
//
//        var currentUser = AuthenticationUtils.getCurrentUser();
//        if (currentUser.isEmpty()) {
//            throw new FlightTicketRuntimeException(HttpStatus.UNAUTHORIZED, "Unauthorized");
//        }
//
//
//        UserEntity user = userServiceClient
//                .getUser(currentUser.get().getId().toString())
//                .orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "Cannot find user"));
//
//        //Need validation
//        user.setDob(request.getDob());
//        user.setFullName(request.getFullName());
//        user.setPhoneNumber(request.getPhoneNumber());
//
//        try {
//            userRepository.save(user);
//
//        } catch (Exception e) {
//            log.info(e.getMessage());
//            throw new FlightTicketRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "System error!");
//        }
//
//
//    }

//    public List<UserEntity> getManyUser() {
//
//        try {
//            var users = userRepository.findAll();
//            return users;
//        } catch (Exception e) {
//            log.info(e.getMessage());
//            throw new FlightTicketRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "System error!");
//        }
//
//    }

    public UserResponse getMe() {
        UserPrincipal currentUser = AuthenticationUtils
                .getCurrentUser()
                .orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.UNAUTHORIZED, "Unauthorized"));
        var user = userServiceClient.getUser(currentUser.getId().toString())
                .orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "User not found"));
        return userMapper.toUserResponse(user);
    }

    public List<TicketResponse> getMyTickets() {
        var currentUser = AuthenticationUtils.getCurrentUser();
        if (currentUser.isEmpty()) {
            throw new FlightTicketRuntimeException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        var userEntity = userServiceClient.getUser(currentUser.get().getId().toString())
                .orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "User not found"));

        var tickets = ticketRepository.findByBookedById(currentUser.get().getId().toString());
        var ticketsResponses = tickets
                .stream().map(
                        (ticket) -> ticketMapper.toTicketResponse(ticket, userEntity)).
                toList();
        return ticketsResponses;
    }

    public TicketResponse getMyTicketById(UUID ticketId) {
        var currentUser = AuthenticationUtils.getCurrentUser();
        if (currentUser.isEmpty()) {
            throw new FlightTicketRuntimeException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        var userEntity = userServiceClient.getUser(currentUser.get().getId().toString())
                .orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "User not found"));
        var ticket = ticketRepository.findByIdAndBookedById(ticketId, currentUser.get().getId().toString())
                .orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "Ticket not found"));
        return ticketMapper.toTicketResponse(ticket, userEntity);
    }
}
