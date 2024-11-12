package com.ltrha.ticket.domain.request.airplane;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddAirplaneClassOptionRequest {

    private int airplaneId;
    private String className;
    private int numOfSeat;


}
