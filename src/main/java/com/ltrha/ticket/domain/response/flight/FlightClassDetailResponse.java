package com.ltrha.ticket.domain.response.flight;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightClassDetailResponse {

    private int flightId;
    private int airplaneFlightClassId;
    private String flightClass;
    private long fare;
    private long availableSeatCount;
    private long seatCount;
}
