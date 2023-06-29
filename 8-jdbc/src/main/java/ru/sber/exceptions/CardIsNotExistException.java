package ru.sber.exceptions;

public class CardIsNotExistException extends RuntimeException {
    public CardIsNotExistException(String message) {
        super(message);
    }
}
