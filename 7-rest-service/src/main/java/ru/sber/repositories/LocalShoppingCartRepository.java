package ru.sber.repositories;

import org.springframework.stereotype.Repository;
import ru.sber.models.Product;
import ru.sber.models.ShoppingCart;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Работа с корзиной для покупок
 */
@Repository
public class LocalShoppingCartRepository implements ShoppingCartRepository {

    List<ShoppingCart> shoppingCarts = new ArrayList<>();
    ProductRepository productRepository;

    public LocalShoppingCartRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ShoppingCart createShoppingCart() {
        List<Product> products = new ArrayList<>();
        ShoppingCart shoppingCart = new ShoppingCart(generateId(), products, "");
        shoppingCarts.add(shoppingCart);
        return shoppingCart;
    }

    @Override
    public Optional<ShoppingCart> findById(long id) {
        return shoppingCarts.stream()
                .filter(cart -> cart.getId() == id)
                .findAny();
    }

    @Override
    public List<Optional<?>> addToCart(long idCart, long idProduct) {

        Optional<ShoppingCart> cart = findById(idCart);
        Optional<Product> product = productRepository.findById(idProduct);

        if (cart.isPresent() && product.isPresent()) {
            cart.get().getProductsList().add(product.get());
        }

        return List.of(cart, product);
    }

    @Override
    public List<Optional<?>> updateProductAmount(long idCart, long idProduct, int amount) {

        Optional<ShoppingCart> cart = findById(idCart);
        Optional<Product> product = Optional.empty();

        if (cart.isPresent()) {

            List<Product> products = cart.get().getProductsList();

            product = products.stream().filter(p -> p.getId() == idProduct).findAny();

            if (product.isPresent()) {
                product.get().setAmount(amount);
            }
        }

        return List.of(cart, product);
    }

    @Override
    public boolean deleteProduct(long idCart, long idProduct) {

        Optional<ShoppingCart> cart = findById(idCart);

        if (cart.isPresent()) {

            List<Product> products = cart.get().getProductsList();
            return products.removeIf(product -> product.getId() == idProduct);

        }

        return false;
    }

    private long generateId() {
        Random random = new Random();
        int low = 1;
        int high = 1_000_000;
        return random.nextLong(high - low) + low;
    }


}
