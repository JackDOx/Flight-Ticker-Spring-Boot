package com.ltrha.ticket.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UserDetailResponse {
    private UUID id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private LocalDate dob;

}
