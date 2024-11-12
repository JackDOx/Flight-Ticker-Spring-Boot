package com.ltrha.ticket.service.impl;

import com.ltrha.ticket.domain.request.payment.GetPaymentUrlRequest;
import com.ltrha.ticket.event.booking.PaymentCompleteEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    public static final String PAYMENT_ID_PARAM = "paymentId";
    public static final String STATUS_PARAM = "status";
    public static final String BOOKING_ID_PARAM = "bookingId";
    public static final String PAYMENT_METHOD_PARAM = "payment_method";
    public static final String PAYMENT_PRICE_PARAM = "payment_price";

    public static final int successPaymentStatusCode = 11;


    private final KafkaTemplate<String, Object> kafkaTemplate;

    public String getPaymentUrl(GetPaymentUrlRequest request) {
        UUID paymentId = UUID.randomUUID();
        String paymentUrl = MessageFormat.format(
                "http://localhost:8080/payment/payment-return?" +
                        "paymentId={0}&status={1}&bookingId={2}&paymentMethod={3}&paymentPrice={4}&paymentReturnUrl={5}",
                paymentId,
                PaymentService.successPaymentStatusCode,
                request.getProductId(),
                "MOMO",
                request.getTotalPrice(),
                URLEncoder.encode(request.getPaymentReturnUrl(), Charset.defaultCharset())

        );

        return paymentUrl;

    }

    public void payByMomoReturnHandler(Map<String, String> params) {

        //Produce payment complete event
        if (Integer.parseInt(params.get(STATUS_PARAM)) != (successPaymentStatusCode)) {
            return;
        }
        kafkaTemplate.send("payment-complete", PaymentCompleteEvent.builder()
                .bookingId(params.get(BOOKING_ID_PARAM))
                .paymentAmount(params.get(PAYMENT_PRICE_PARAM))
                .paymentMethod(params.get(PAYMENT_METHOD_PARAM))
                .paymentStatus(params.get(STATUS_PARAM))
                .paymentId(params.get(PAYMENT_ID_PARAM))
                .build());
    }

    private void momo() {
        // Option 1

    }
}
