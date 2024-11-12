package com.ltrha.ticket.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseStatus;


@Getter
@Setter
public class FlightTicketRuntimeException extends  RuntimeException{
    private HttpStatus status;
    private String message;

    public FlightTicketRuntimeException(HttpStatus httpStatus, String message){
        this.status = httpStatus;
        this.message = message;
    }

}
