package ru.sber.models;

import lombok.Data;

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
