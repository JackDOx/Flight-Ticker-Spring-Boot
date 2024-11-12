package com.ltrha.ticket.controllers.advices;


import com.ltrha.ticket.domain.response.ApiResponse;
import com.ltrha.ticket.exception.FlightTicketRuntimeException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(value = FlightTicketRuntimeException.class)
    @ResponseBody
    public ResponseEntity<?> handler(final FlightTicketRuntimeException e, final HttpServletRequest request) {

        return ResponseEntity
                .status(e.getStatus())
                .body(new ApiResponse(false, e.getMessage()))
                ;
    }
}
