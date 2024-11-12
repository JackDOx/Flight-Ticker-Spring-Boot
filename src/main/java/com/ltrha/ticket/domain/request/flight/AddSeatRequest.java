package com.ltrha.ticket.domain.request.flight;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddSeatRequest {
    private int numOfRow;
    private int numOfColumn;
}
