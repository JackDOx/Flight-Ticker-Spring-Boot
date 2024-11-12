package com.ltrha.ticket.domain.response.airplane;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AirplaneDetailResponse {
    private int id;
    private String model;
    private String brandName;
    private String airplaneCode;
    private List<AirplaneSeatOptionResponse> seatOptions;

}
