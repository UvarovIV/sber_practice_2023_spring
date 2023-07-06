package ru.sber.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, которое выбрасывается, если банковской карты не существует
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Карты с таким номером не существует")
public class CardIsNotExistException extends RuntimeException {
}
