package com.ltrha.ticket.controllers.publics;

import com.ltrha.ticket.modules.builders.ResponseEntityBuilder;
import com.ltrha.ticket.service.impl.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payment")
public class PaymentController {


    private final PaymentService paymentService;




    @GetMapping("/payment-return")
    public ResponseEntity<?> paymentReturn(
            @RequestParam Map<String, String> params) {

        //Payment service call this method after payment is done
        paymentService.payByMomoReturnHandler(params);

        String returnUrl = params.get("paymentReturnUrl");

        //Server notify payment service that it has received payment result
        return ResponseEntityBuilder.getBuilder()
                .setCode(HttpStatus.OK)
                .set("paymentReturnUrl", returnUrl)
                .setMessage("Payment complete")
                .build();
    }
}
