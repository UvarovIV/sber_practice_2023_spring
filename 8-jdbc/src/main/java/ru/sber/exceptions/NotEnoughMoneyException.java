package ru.sber.exceptions;

/**
 * Исключение, которое выбрасывается, если недостаточно средств при покупке
 */
public class NotEnoughMoneyException extends RuntimeException {
    public NotEnoughMoneyException(String message) {
        super(message);
    }
}
