package com.ltrha.ticket.constant.business;

public class TicketBookingStatusCode {
    public static int PENDING = 1;
    public static int BOOKED = 2;

    public static int CHECKED_IN = 3;

    public static int FLIGHT_COMPLETE = 4;
    public static int CANCELLED = 0;

    public static String getStatus(int status) {
        switch (status) {
            case 1:
                return "Pending";
            case 2:
                return "Booked";
            case 3:
                return "Checked In";
            case 0:
                return "Cancelled";
            default:
                return "Unknown";
        }
    }
}
