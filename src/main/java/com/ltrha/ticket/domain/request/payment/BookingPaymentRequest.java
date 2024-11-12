package com.ltrha.ticket.domain.request.payment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookingPaymentRequest {
    private String bookingId;
    private String paymentMethod;
    private String paymentReturnUrl;

}
