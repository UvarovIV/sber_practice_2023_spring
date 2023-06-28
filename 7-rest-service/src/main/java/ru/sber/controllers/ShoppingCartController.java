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
    public ResponseEntity<ShoppingCart> addProduct(@PathVariable long idCart, @PathVariable Long idProduct) {

        log.info("Добавление в корзину товара с id: {}", idProduct);

        List<Optional<?>> optionalList = shoppingCartRepository.addToCart(idCart, idProduct);

        if (checkCartAndProductIsPresent(optionalList)) {

            ShoppingCart cart = (ShoppingCart) optionalList.get(0).get();

            return ResponseEntity.ok().body(cart);

        } else {
            return ResponseEntity.notFound().build();
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
    public ResponseEntity<ShoppingCart> updateAmount(@PathVariable long idCart, @PathVariable long idProduct, @RequestBody Product product) {

        log.info("Изменение количества товара в корзине");

        List<Optional<?>> optionalList = shoppingCartRepository.updateProductAmount(idCart, idProduct, product.getAmount());

        if (checkCartAndProductIsPresent(optionalList)) {

            ShoppingCart cart = (ShoppingCart) optionalList.get(0).get();

            return ResponseEntity.ok().body(cart);

        } else {
            return ResponseEntity.notFound().build();
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

    private boolean checkCartAndProductIsPresent(List<Optional<?>> optionalList) {

        boolean cartIsPresent = optionalList.get(0).isPresent();
        boolean productIsPresent = optionalList.get(1).isPresent();

        return cartIsPresent && productIsPresent;

    }



}
