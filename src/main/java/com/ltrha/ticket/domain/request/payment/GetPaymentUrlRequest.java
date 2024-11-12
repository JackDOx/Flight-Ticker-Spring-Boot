package com.ltrha.ticket.domain.request.payment;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class GetPaymentUrlRequest {
    private String productId;
    private String productType;
    private String paymentMethod;
    private long totalPrice;
    private String paymentReturnUrl;
}
