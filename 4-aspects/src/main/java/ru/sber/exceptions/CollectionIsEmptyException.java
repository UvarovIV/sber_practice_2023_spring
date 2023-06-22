package ru.sber.exceptions;

/**
 * Класс для исключения, выбрасываемого при пустой коллекции в аргументах
 */
public class CollectionIsEmptyException extends RuntimeException {
    public CollectionIsEmptyException(String message) {
        super(message);
    }
}
