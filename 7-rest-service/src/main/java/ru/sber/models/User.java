package ru.sber.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private long id;
    private String name;
    private String login;
    private String password;
    private String email;
    private ShoppingCart cart;
}
