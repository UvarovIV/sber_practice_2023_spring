package ru.sber.exceptions;

/**
 * Класс для исключения, выбрасываемого при NULL значении аргумента
 */
public class ArgumentIsNullException extends RuntimeException {
    public ArgumentIsNullException(String message) {
        super(message);
    }

}
