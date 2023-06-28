package ru.sber.models;

import lombok.Data;

/**
 * Пользователь с ограниченным количеством полей
 */
@Data
public class AbridgedUser {
    private long id;
    private String name;
    private String email;
    private ShoppingCart cart;

    public AbridgedUser(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.cart = user.getCart();
    }
}
