package ru.sber.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Товар
 */
@Data
@AllArgsConstructor
public class Product {
    private long id;
    private String name;
    private BigDecimal price;
    private int amount;
}
