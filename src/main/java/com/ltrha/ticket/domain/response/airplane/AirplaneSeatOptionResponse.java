package com.ltrha.ticket.domain.response.airplane;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AirplaneSeatOptionResponse {
    private int id;
    private String optionName;
    private String description;
}
