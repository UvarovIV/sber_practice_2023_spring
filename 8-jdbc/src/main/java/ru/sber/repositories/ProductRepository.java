package ru.sber.repositories;

import ru.sber.models.Product;

import java.util.List;
import java.util.Optional;

/**
 * Хранилище с данными о товарах
 */
public interface ProductRepository {
    /**
     * Добавляет новый товар
     * @param product Информация о товаре
     * @return Возвращает идентификатор созданного товара
     */
    long addNewProduct(Product product);

    /**
     * Производит поиск товара по id
     * @param id Уникальный идентификатор товара
     * @return Возвращает результат поиска
     */
    Optional<Product> findById(long id);

    /**
     * Находит все товары по заданному названию
     * @param name Название товара, который нужно найти
     * @return Возвращает список найденных товаров
     */
    List<Product> findAll(String name);

    /**
     * Обновляет информацию о товаре
     * @param product Товар, информацию о котором нужно обновить
     * @return Возвращает успешность обновления информации
     */
    boolean update(Product product);

    /**
     * Удаляет товар по id
     * @param id Уникальный идентификатор товара
     * @return Возвращает успешность удаления товара
     */
    boolean deleteById(long id);
}
