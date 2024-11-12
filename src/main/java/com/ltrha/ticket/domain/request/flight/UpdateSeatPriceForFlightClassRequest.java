package com.ltrha.ticket.domain.request.flight;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UpdateSeatPriceForFlightClassRequest {
    private Integer flightId;
    private int airplaneSeatOptionId;
    private long price;
}
