package ru.sber.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, которое выбрасывается, если пользователь не найден
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Пользователь не существует")
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
