package ru.sber.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.entities.User;
import ru.sber.repositories.UserRepository;

import java.util.Optional;

/**
 * Сервис для взаимодействия с пользователями
 */
@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final CartService cartService;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository, CartService cartService) {
        this.userRepository = userRepository;
        this.cartService = cartService;
    }

    @Override
    public long signUp(User user) {
        if (user.getName() == null || user.getEmail() == null
                || user.getLogin() == null || user.getPassword() == null) {
            return -1;
        }
        return userRepository.save(user).getId();
    }

    @Override
    public Optional<User> getUserById(long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public boolean checkUserExistence(long userId) {
        return userRepository.existsById(userId);
    }

    @Override
    @Transactional
    public boolean deleteUserById(long userId) {
        if (checkUserExistence(userId)) {
            cartService.clearCart(userId);
            userRepository.deleteById(userId);
            return true;
        }
        return false;

    }
}
