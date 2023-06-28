package ru.sber.repositories;

import org.springframework.stereotype.Repository;
import ru.sber.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Работа с пользователями
 */
@Repository
public class LocalUserRepository implements UserRepository {

    private List<User> users;
    private final ShoppingCartRepository shoppingCartRepository;

    public LocalUserRepository(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        users = new ArrayList<>(List.of(
                new User(1, "NekoBro", "NBro1111", "123456",
                        "pppsasqwas@gmail.com", shoppingCartRepository.createShoppingCart()),
                new User(2, "dkdkdkdkd", "WhoAmI", "654321",
                "pppsasqwas@gmail.com", shoppingCartRepository.createShoppingCart())
        ));
    }

    @Override
    public long signUp(User user) {
        long id = generateId();
        user.setId(id);
        user.setCart(shoppingCartRepository.createShoppingCart());
        users.add(user);
        return id;
    }

    @Override
    public Optional<User> getUserById(long id) {
        return users.stream()
                .filter(product -> product.getId() == id)
                .findAny();
    }

    @Override
    public boolean deleteUserById(long id) {
        return users.removeIf(user -> user.getId() == id);
    }

    private long generateId() {
        Random random = new Random();
        int low = 1;
        int high = 1_000_000;
        return random.nextLong(high - low) + low;
    }
}
