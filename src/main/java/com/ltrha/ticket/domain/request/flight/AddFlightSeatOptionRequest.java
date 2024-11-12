package com.ltrha.ticket.domain.request.flight;

import com.ltrha.ticket.models.FlightDetailEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddFlightSeatOptionRequest {
    private int flightId;
    private String seatType;
    private long baseFare;
    private String description;
    private int totalSeat;

    private List<String> seatCodes;

}
