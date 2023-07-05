package ru.sber.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Пользователь
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clients")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @NotEmpty(message = "Имя не заполнено")
    private String name;

    @Column(nullable = false)
    @NotEmpty(message = "Email не заполнен")
    private String email;

    @Column(nullable = false)
    @NotEmpty(message = "Логин не заполнен")
    private String login;

    @Column(nullable = false)
    @NotEmpty(message = "Пароль не заполнен")
    private String password;
}
