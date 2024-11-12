package com.ltrha.ticket.domain.request.flight;

import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateFlightRequest {
    private Integer flightId;
    private String flightCode;
    private List<UpdateSeatPriceForFlightClassRequest> updateSeatFaresForFlightClassRequest;
    //Date info
    private LocalDateTime departDate;
    private LocalDateTime estimatedArrivedDate;

    //Location info
    private Integer departureAirportId;
    private Integer destinationAirportId;

    private Integer departureLocationId;
    private Integer destinationId;

}
