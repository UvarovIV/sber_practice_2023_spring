package ru.sber.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Payment {
    BigDecimal sum;
    long idUser;
}
