package ru.sber.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.entities.Product;
import ru.sber.services.ProductService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Добавляет новый товар
     *
     * @param product Добавляемый товар
     * @return Возвращает статус добавления товара
     */
    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        long productId = productService.addNewProduct(product);
        if (productId == -1) {
            return ResponseEntity.badRequest().body("Не все значения заполнены");
        }
        log.info("Добавление товара {}", product);
        return ResponseEntity.created(URI.create("products/" + productId)).build();
    }

    /**
     * Выдает все товары по фильтру
     *
     * @param name Название товара (фильтр)
     * @return Возвращает список найденных товаров
     */
    @GetMapping
    public List<Product> getProducts(@RequestParam(required = false) String name) {

        if (name == null) {
            name = "";
            log.info("Вывод всех товаров");
        } else {
            log.info("Поиск товаров по имени {}", name);
        }

        return productService.findAllByName(name);
    }

    /**
     * Получение товара по id
     *
     * @param id Идентификатор для поиска
     * @return Возвращает найденный товар
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProducts(@PathVariable long id) {
        log.info("Получение товара по id");
        Optional<Product> product = productService.findById(id);

        if (product.isPresent()) {
            return ResponseEntity.ok().body(product.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Обновление информации о товаре
     *
     * @param product Информация об обновленном товаре
     * @return Возвращает обновленный товар
     */
    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {
        if (productService.update(product)) {
            log.info("Обновление информации о товаре");
            return ResponseEntity.ok().body(product);
        }
        return ResponseEntity.badRequest().body("Заполнены не все значения");
    }

    /**
     * Удаление товара по id
     *
     * @param id Идентификатор товара
     * @return Возвращает статус удаления товара
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        log.info("Удаление продукта по id");
        boolean isDeleted = productService.deleteById(id);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
