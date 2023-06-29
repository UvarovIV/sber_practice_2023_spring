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

    /**
     * Совершает платеж
     * @param payment Платеж
     * @return Возвращает статус выполнения операции
     */
    @PostMapping
    public ResponseEntity<?> pay(@RequestBody Payment payment) {

        boolean isPay = localPaymentService.pay(payment);

        if (isPay) {
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.badRequest().body("Средств на счету недостаточно");
        }

    }
}
