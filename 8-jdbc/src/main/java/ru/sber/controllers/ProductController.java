package ru.sber.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.models.Product;
import ru.sber.repositories.ProductRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Добавляет новый товар
     * @param product Добавляемый товар
     * @return Возвращает статус добавления товара
     */
    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        log.info("Добавление товара {}", product);

        return ResponseEntity.created(URI.create("products/" + productRepository.addNewProduct(product))).build();
    }

    /**
     * Выдает все товары по фильтру
     * @param name Название товара (фильтр)
     * @return Возвращает список найденных товаров
     */
    @GetMapping
    public List<Product> getProducts(@RequestParam(required = false) String name) {
        log.info("Поиск товаров по имени {}", name);

        return productRepository.findAll(name);
    }

    /**
     * Получение товара по id
     * @param id Идентификатор для поиска
     * @return Возвращает найденный товар
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProducts(@PathVariable long id) {
        log.info("Получение товара по id");
        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            return ResponseEntity.ok().body(product.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Обновление информации о товаре
     * @param product Информация об обновленном товаре
     * @return Возвращает обновленный товар
     */
    @PutMapping
    public Product updateProduct(@RequestBody Product product) {
        log.info("Обновление информации о товаре");
        productRepository.update(product);

        return product;
    }

    /**
     * Удаление товара по id
     * @param id Идентификатор товара
     * @return Возвращает статус удаления товара
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        log.info("Удаление продукта по id");
        boolean isDeleted = productRepository.deleteById(id);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
