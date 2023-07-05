package ru.sber.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, которое выбрасывается, если попытаться удалить из списка товаров товар,
 * который всё ещё находится в корзине у пользователя
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Товар ещё находится в корзине одного из пользователей")
public class ProductStillUseException extends RuntimeException {
}
