package com.ltrha.ticket.domain.request.airplane;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddAirplaneSeatRequest {
    private int airplaneId;
    private int airplaneClassOptionId;
    private String seatNumber;
    private boolean isAvailable;

}
