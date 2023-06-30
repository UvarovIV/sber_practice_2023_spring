package ru.sber.repositories;

import java.math.BigDecimal;

/**
 * Класс, отвечающий за работу с корзиной
 */
public interface ShoppingCartRepository {

    /**
     * Добавляет товар в корзину
     *
     * @param idClient  Уникальный идентификатор клиента
     * @param idProduct Уникальный идентификатор продукта
     * @return Возвращает корзину с внесенными изменениями
     */
    boolean addToCart(long idClient, long idProduct, int amount);

    /**
     * Обновляет количество товара в корзине
     *
     * @param idClient  Уникальный идентификатор клиента
     * @param idProduct Уникальный идентификатор товара
     * @param amount    Новое количество товара
     * @return Возвращает корзину с внесенными изменениями
     */
    boolean updateProductAmount(long idClient, long idProduct, int amount);

    /**
     * Удаляет товар из корзины
     *
     * @param idClient  Уникальный идентификатор клиента
     * @param idProduct Уникальный идентификатор товара
     * @return Возвращает корзину с внесенными изменениями
     */
    boolean deleteProduct(long idClient, long idProduct);

    /**
     * Рассчитывает стоимость товаров в корзине пользователя
     * @param userId Уникальный идентификатор пользователя
     * @return Возвращает полную стоимость корзины
     */
    BigDecimal getSumPriceCart(long userId);

}
