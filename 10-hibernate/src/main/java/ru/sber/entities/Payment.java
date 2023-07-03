package ru.sber.entities;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Информация о платеже
 */
@Data
@AllArgsConstructor
public class Payment {
    @NotNull(message = "Не указан номер карты")
    Long cardNumber;
    @NotNull(message = "Не указан User Id")
    Long userId;
}
