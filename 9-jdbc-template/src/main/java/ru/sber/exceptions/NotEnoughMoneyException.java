package ru.sber.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, которое выбрасывается, если недостаточно средств при покупке
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Средств на счету недостаточно")
public class NotEnoughMoneyException extends RuntimeException {
    public NotEnoughMoneyException(String message) {
        super(message);
    }
}
