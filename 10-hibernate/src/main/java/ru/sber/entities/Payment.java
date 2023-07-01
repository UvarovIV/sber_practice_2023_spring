package ru.sber.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Информация о платеже
 */
@Data
@AllArgsConstructor
public class Payment {
    long cardNumber;
    long userId;
}
