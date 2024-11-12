package com.ltrha.ticket.domain.request.airplane;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class AddAirplaneRequest {

    private String brandName;
    private String description;
    private String model;
    private int numOfSeat;
    private String origin;
    private String seatLayout;

    private List<AddAirplaneClassOptionRequest> airplaneClassOptions;





}
