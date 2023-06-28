package ru.sber.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ShoppingCart {
    private long id;
    private List<Product> productsList;
    private String promoCode;
}
