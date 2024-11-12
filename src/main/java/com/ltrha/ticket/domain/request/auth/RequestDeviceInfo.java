package com.ltrha.ticket.domain.request.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestDeviceInfo {
    private String deviceType;
    private String deviceId;


}
