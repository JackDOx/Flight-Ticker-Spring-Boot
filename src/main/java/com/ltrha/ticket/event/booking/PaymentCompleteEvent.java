package com.ltrha.ticket.event.booking;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentCompleteEvent {
    private String bookingId;
    private String paymentId;
    private String paymentStatus;
    private String paymentMethod;
    private String paymentAmount;
}
