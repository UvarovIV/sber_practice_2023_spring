package ru.sber.exceptions;

public class StringIsEmptyException extends RuntimeException {
    public StringIsEmptyException(String message) {
        super(message);
    }
}
