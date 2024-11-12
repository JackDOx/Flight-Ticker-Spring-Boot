package com.ltrha.ticket.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class FlightSeatUtil {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class SeatInfo{
        private int row;
        private int column;

    }
    private static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static String getSeatCode(int row, int column) {
        return row + "-" + alphabet.charAt(column);
    }



}
