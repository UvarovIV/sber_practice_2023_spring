package ru.sber.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sber.models.Payment;
import ru.sber.services.LocalPaymentService;

@Slf4j
@RestController
@RequestMapping("payment")
public class PaymentController {
    LocalPaymentService localPaymentService;

    public PaymentController(LocalPaymentService localPaymentService) {
        this.localPaymentService = localPaymentService;
    }

    @PostMapping
    public ResponseEntity<?> pay(@RequestBody Payment payment) {
        log.info("Совершение платежа {}", payment);
        boolean isPay = localPaymentService.pay(payment);
        if (isPay) {
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
