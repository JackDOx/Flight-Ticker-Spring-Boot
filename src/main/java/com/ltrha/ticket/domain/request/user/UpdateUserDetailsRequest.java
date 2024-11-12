package com.ltrha.ticket.domain.request.user;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
public class UpdateUserDetailsRequest {
    private UUID id;
    private String fullName;
    private String phoneNumber;
    private LocalDate dob;
}
