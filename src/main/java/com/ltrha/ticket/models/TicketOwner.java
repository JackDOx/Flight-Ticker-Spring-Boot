package com.ltrha.ticket.models;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Builder
public class TicketOwner {
    private String name;
    private String dob;
    private String email;
    private String phoneNumber;
    private String address;
    private String city;
    private String country;
    private String identityCard;

}
