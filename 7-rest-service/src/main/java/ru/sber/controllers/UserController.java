package ru.sber.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.models.AbridgedUser;
import ru.sber.models.User;
import ru.sber.repositories.UserRepository;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public long signUp(@RequestBody User user) {
        log.info("Регистрация пользователя {}", user);
        return userRepository.signUp(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbridgedUser> getUserById(@PathVariable long id) {

        log.info("Выводим данные о пользователе с id: {}", id);

        Optional<User> user = userRepository.getUserById(id);

        if (user.isPresent()) {
            return ResponseEntity.ok().body(new AbridgedUser(user.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {

        log.info("Удаляем пользователя с id: {}", id);

        boolean isDeleted = userRepository.deleteUserById(id);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
