package ru.sber.services;

import ru.sber.models.Payment;

public interface PaymentService {

    boolean pay(Payment payment);
}
