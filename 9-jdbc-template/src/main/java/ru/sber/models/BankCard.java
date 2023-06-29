package ru.sber.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Карта банка
 */
@Data
@AllArgsConstructor
public class BankCard {
    private long numberOfCard;
    private BigDecimal money;
}
