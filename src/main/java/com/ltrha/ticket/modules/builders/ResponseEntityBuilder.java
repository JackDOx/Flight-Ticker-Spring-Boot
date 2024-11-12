package com.ltrha.ticket.modules.builders;

import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Getter
public class ResponseEntityBuilder {
    private final Map<String, Object> map = new HashMap<>();
    private final Map<String, String> headers = new HashMap<>();

    private ResponseEntityBuilder() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        map.put("timestamp", timestamp);
        this.setCode(200);
    }

    public static ResponseEntityBuilder getBuilder() {
        return new ResponseEntityBuilder();
    }

    public ResponseEntityBuilder setCode(final int code) {
        map.put("code", code);
        setSuccess(code < 400);
        return this;
    }
    public ResponseEntityBuilder data(final Object data) {
        map.put("data", data);
        return this;
    }

    public ResponseEntityBuilder setHeader(final String key, final String value) {
        headers.put(key, value);
        return this;
    }

    public ResponseEntityBuilder setCode(final HttpStatus code) {
        this.setCode(code.value());
        return this;
    }

    public ResponseEntityBuilder setMessage(final String message) {
        map.put("message", message);
        return this;
    }

    public ResponseEntityBuilder set(final String key, final Object value) {
        map.put(key, value);
        return this;
    }

    public ResponseEntityBuilder setSuccess(final boolean isSuccess) {
        map.put("status", isSuccess? "success": "error");
        return this;
    }

    public ResponseEntityBuilder setDetails(final Object details) {
        map.put("details", details);
        return this;
    }

    public ResponseEntity<?> build() {
        if (map.get("message") == null && map.get("code") != null)
            map.put("message", HttpStatus.valueOf((Integer) map.get("code")).getReasonPhrase());
        int code = (Integer) map.get("code");
        return ResponseEntity.status(HttpStatus.valueOf(code))
                .headers(new HttpHeaders() {{
                    headers.forEach(this::add);
                }})
                .body((map));
    }
}