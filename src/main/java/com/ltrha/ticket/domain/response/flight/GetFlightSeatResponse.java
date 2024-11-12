package com.ltrha.ticket.domain.response.flight;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetFlightSeatResponse {
    private int id;
    private String seatCode;
    private boolean isAvailable;
    private String flightClass;

}
