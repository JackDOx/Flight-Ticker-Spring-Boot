package com.ltrha.ticket.domain.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


public class ApiDataResponse<T> extends ApiResponse {
    private T data;

    public ApiDataResponse(T data, boolean success, String message) {
        super(success, message);
        this.data = data;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        data = data;
    }
}
