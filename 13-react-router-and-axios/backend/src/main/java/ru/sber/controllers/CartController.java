package ru.sber.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.entities.Cart;
import ru.sber.entities.Product;
import ru.sber.services.CartService;
import ru.sber.services.ProductService;

import java.net.URI;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("cart")
public class CartController {

    CartService cartService;
    ProductService productService;

    @Autowired
    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    /**
     * Добавляет товар в корзину
     *
     * @param idClient  Уникальный идентификатор клиента
     * @param idProduct Уникальный идентификатор продукта
     * @return Возвращает корзину с внесенными изменениями
     */
    @PostMapping("/{idClient}/product/{idProduct}")
    public ResponseEntity<Cart> addProductToCart(@PathVariable long idClient, @PathVariable Long idProduct, @RequestBody Product product) {

        log.info("Добавление в корзину товара с id: {} количеством {}", idProduct, product.getAmount());

        boolean recordInserted = cartService.addToCart(idClient, idProduct, product.getAmount());

        if (recordInserted) {
            return ResponseEntity.created(URI.create("cart/" + idClient + "/product/" + idProduct)).build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    /**
     * Изменяет количество товара в корзине
     *
     * @param idCart    Уникальный идентификатор корзины
     * @param idProduct Уникальный идентификатор продукта
     * @param product   Товар, у которого изменяется количество
     * @return Возвращает корзину с внесенными изменениями
     */
    @PutMapping("/{idCart}/product/{idProduct}")
    public ResponseEntity<Cart> updateProductAmountInCart(@PathVariable long idCart, @PathVariable long idProduct, @RequestBody Product product) {

        log.info("Изменение количества товара в корзине");

        boolean recordUpdated = cartService.updateProductAmount(idCart, idProduct, product.getAmount());

        if (recordUpdated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Удаляет товар из корзины
     *
     * @param idCart    Уникальный идентификатор корзины
     * @param idProduct Уникальный идентификатор продукта
     * @return Возвращает корзину с внесенными изменениями
     */
    @DeleteMapping("/{idCart}/product/{idProduct}")
    public ResponseEntity<?> deleteProduct(@PathVariable long idCart, @PathVariable long idProduct) {

        log.info("Удаление из корзины товара с id: {}", idProduct);

        boolean isDeleted = cartService.deleteProduct(idCart, idProduct);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

}
