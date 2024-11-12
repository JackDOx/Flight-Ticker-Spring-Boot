package com.ltrha.ticket.domain.response.flight;

import com.ltrha.ticket.models.AirportEntity;
import com.ltrha.ticket.models.LocationEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class FlightDetailResponse {
    private int id;

    private String flightCode;

    private int airlineId;
    private String airlineName;
    private String airlineCode;

    private int airplaneId;


    private List<FlightClassDetailResponse> flightClassDetails;
    //Date info
    private LocalDateTime departDate;
    private LocalDateTime estimatedArrivedDate;

    //Location info

    private AirportEntity departureAirport;

    private AirportEntity destinationAirport;

    private LocationEntity departureLocation;

    private LocationEntity destination;


}
