package ru.sber.exceptions;

/**
 * Класс для исключения, выбрасываемого при пустой строке в аргументах
 */
public class StringIsEmptyException extends RuntimeException {
    public StringIsEmptyException(String message) {
        super(message);
    }
}
