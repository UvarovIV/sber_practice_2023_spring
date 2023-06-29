package ru.sber.exceptions;

/**
 * Исключение, которое выбрасывается, если банковской карты не существует
 */
public class CardIsNotExistException extends RuntimeException {
    public CardIsNotExistException(String message) {
        super(message);
    }
}
