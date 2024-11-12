package com.ltrha.ticket.domain.request.flight;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddFlightRequest {
    private String flightCode;

    private int airlineId;
    private int airplaneId;
    //Seat info

    private List<UpdateSeatPriceForFlightClassRequest> seatFaresForFlightClass;
    //Date info
    private String departDate;
    private String estimatedArrivedDate;

    //Location info
    private Integer departureAirportId;
    private Integer destinationAirportId;

    private Integer departureLocationId;
    private Integer destinationId;

}
