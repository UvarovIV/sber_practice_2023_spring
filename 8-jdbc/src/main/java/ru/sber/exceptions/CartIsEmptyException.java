package ru.sber.exceptions;

/**
 * Исключение, которое выбрасывается, если пользователь пытается оплатить пустую корзину
 */
public class CartIsEmptyException extends RuntimeException {
    public CartIsEmptyException(String message) {
        super(message);
    }
}
