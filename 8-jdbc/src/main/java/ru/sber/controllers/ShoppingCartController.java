package ru.sber.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.models.Product;
import ru.sber.models.ShoppingCart;
import ru.sber.repositories.ProductRepository;
import ru.sber.repositories.ShoppingCartRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("cart")
public class ShoppingCartController {

    ShoppingCartRepository shoppingCartRepository;
    ProductRepository productRepository;

    public ShoppingCartController(ShoppingCartRepository cartRepository, ProductRepository productRepository) {
        this.shoppingCartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    /**
     * Добавляет товар в корзину
     * @param idCart Уникальный идентификатор корзины
     * @param idProduct Уникальный идентификатор продукта
     * @return Возвращает корзину с внесенными изменениями
     */
    @PostMapping("/{idCart}/product/{idProduct}")
    public ResponseEntity<ShoppingCart> addProductToCart(@PathVariable long idCart, @PathVariable Long idProduct) {

        log.info("Добавление в корзину товара с id: {}", idProduct);

        Optional<ShoppingCart> cart = shoppingCartRepository.addToCart(idCart, idProduct);

        if (cart.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(cart.get());
        }

    }

    /**
     * Изменяет количество товара в корзине
     * @param idCart Уникальный идентификатор корзины
     * @param idProduct Уникальный идентификатор продукта
     * @param product Товар, у которого изменяется количество
     * @return Возвращает корзину с внесенными изменениями
     */
    @PutMapping("/{idCart}/product/{idProduct}")
    public ResponseEntity<ShoppingCart> updateProductAmountInCart(@PathVariable long idCart, @PathVariable long idProduct, @RequestBody Product product) {

        log.info("Изменение количества товара в корзине");

        Optional<ShoppingCart> cart = shoppingCartRepository.updateProductAmount(idCart, idProduct, product.getAmount());

        if (cart.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(cart.get());
        }
    }

    /**
     * Удаляет товар из корзины
     * @param idCart Уникальный идентификатор корзины
     * @param idProduct Уникальный идентификатор продукта
     * @return Возвращает корзину с внесенными изменениями
     */
    @DeleteMapping("/{idCart}/product/{idProduct}")
    public ResponseEntity<?> deleteProduct(@PathVariable long idCart, @PathVariable long idProduct) {

        log.info("Удаление из корзины товара с id: {}", idProduct);

        boolean isDeleted = shoppingCartRepository.deleteProduct(idCart, idProduct);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

}
