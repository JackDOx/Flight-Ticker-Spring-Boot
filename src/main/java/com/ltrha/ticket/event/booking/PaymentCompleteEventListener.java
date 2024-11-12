package com.ltrha.ticket.event.booking;


import com.ltrha.ticket.service.impl.BookingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentCompleteEventListener {
    private final BookingService bookingService;
    private final ApplicationEventPublisher eventPublisher;
    private final Logger logger = LoggerFactory.getLogger(PaymentCompleteEventListener.class);

    @KafkaListener(topics = "payment-complete", groupId = "booking-service-group")
    public void listen(PaymentCompleteEvent event) {
        logger.info("Payment complete event received: {}", event);
        bookingService.handleBookingPaymentCompleteEvent(event);


    }

}
