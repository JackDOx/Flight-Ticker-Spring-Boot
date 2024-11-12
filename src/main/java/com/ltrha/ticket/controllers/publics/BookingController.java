package com.ltrha.ticket.controllers.publics;

import com.ltrha.ticket.config.security.CurrentUser;
import com.ltrha.ticket.config.security.UserPrincipal;
import com.ltrha.ticket.constant.web.route.CommonRoute;
import com.ltrha.ticket.domain.request.payment.BookingPaymentRequest;
import com.ltrha.ticket.domain.request.payment.GetPaymentUrlRequest;
import com.ltrha.ticket.domain.request.ticket.BookTicketRequest;
import com.ltrha.ticket.domain.request.ticket.CancelBookingRequest;
import com.ltrha.ticket.domain.request.ticket.CheckinRequest;
import com.ltrha.ticket.domain.response.ApiResponse;
import com.ltrha.ticket.domain.response.ticket.BookTicketResponse;
import com.ltrha.ticket.event.booking.PaymentCompleteEvent;
import com.ltrha.ticket.models.UserEntity;
import com.ltrha.ticket.modules.builders.ResponseEntityBuilder;
import com.ltrha.ticket.service.impl.BookingService;
import com.ltrha.ticket.service.impl.PaymentService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.text.MessageFormat;
import java.util.UUID;


@RequiredArgsConstructor
@RestController
@Controller
@RequestMapping(path = CommonRoute.API_BASE_URL + "/ticket")
public class BookingController {

    private final BookingService bookingService;
    private final PaymentService paymentService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<?> getTicketById(@PathVariable UUID id) {

        bookingService.getTickerById(id);
        return ResponseEntity.ok()
                .body("");
    }

    @PostMapping(path = "/book")
    public ResponseEntity<?> bookTicket( @CurrentUser UserPrincipal userPrincipal, @RequestBody BookTicketRequest bookTicketRequest) {

        BookTicketResponse response = bookingService.bookTicket(bookTicketRequest, userPrincipal);

        return ResponseEntityBuilder.getBuilder()
                .setMessage("Book complete. Proceed to payment")
                .set("booking-info", response)
                .build();

    }

    @PostMapping(path = "/to-payment")
    public ResponseEntity<?> getPaymentUrl(@RequestBody BookingPaymentRequest request) {
        String paymentUrl = bookingService.getBookingPaymentUrl(request);

        return ResponseEntityBuilder.getBuilder()
                .setCode(HttpStatus.SC_MOVED_PERMANENTLY)
                .setMessage("Redirect to payment page")
                .set("paymentUrl", paymentUrl)
                .build();

    }

    @PostMapping(path = "/checkin")
    public ResponseEntity<?> checkin(@RequestBody CheckinRequest checkinRequest) {
        bookingService.checkin(checkinRequest);
        return ResponseEntity.ok().body(new ApiResponse(true, "Checkin successfully!"));
    }

    @PostMapping(path = "/cancel")
    public ResponseEntity<?> cancelTicket(@RequestBody CancelBookingRequest request, @CurrentUser UserPrincipal userPrincipal) {
        bookingService.cancelTicket(request, userPrincipal);
        return ResponseEntity.ok().body(new ApiResponse(true, "Cancel ticket successfully!"));
    }

    @PostMapping(path = "/sendSeatEvent")
    public ResponseEntity<?> sendSeatEvent(@RequestBody String message) {
        bookingService.produceSeatReservedEvent(message);
        return ResponseEntity.ok().body(new ApiResponse(true, "Send seat event successfully!"));
    }

}
