package com.ltrha.ticket.domain.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {
    private boolean success;
    private String message;

    public ApiResponse(final boolean isSuccess, final String message) {
        this.setSuccess(isSuccess);
        this.setMessage(message);
    }
}
