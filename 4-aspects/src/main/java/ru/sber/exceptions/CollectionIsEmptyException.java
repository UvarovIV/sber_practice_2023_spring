package ru.sber.exceptions;

public class CollectionIsEmptyException extends RuntimeException {
    public CollectionIsEmptyException(String message) {
        super(message);
    }
}
