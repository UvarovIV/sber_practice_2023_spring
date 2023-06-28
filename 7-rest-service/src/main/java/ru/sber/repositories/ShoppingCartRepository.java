package ru.sber.repositories;

import ru.sber.models.ShoppingCart;

import java.util.List;
import java.util.Optional;

/**
 * Класс, отвечающий за работу с корзиной
 */
public interface ShoppingCartRepository {

    /**
     * Создает новую корзину для покупок
     * @return Возвращает созданную корзину
     */
    ShoppingCart createShoppingCart();

    /**
     * Производит поиск корзины по id
     * @param id Уникальный идентификатор корзины
     * @return Возвращает результат поиска
     */
    Optional<ShoppingCart> findById(long id);

    /**
     * Добавляет товар в корзину
     * @param idCart Уникальный идентификатор корзины
     * @param idProduct Уникальный идентификатор продукта
     * @return Возвращает корзину с внесенными изменениями
     */
    List<Optional<?>> addToCart(long idCart, long idProduct);

    /**
     *
     * @param idCart Уникальный идентификатор корзины
     * @param idProduct Уникальный идентификатор товара
     * @param amount Новое количество товара
     * @return Возвращает корзину с внесенными изменениями
     */
    List<Optional<?>> updateProductAmount(long idCart, long idProduct, int amount);

    /**
     * Удаляет товар из корзины
     * @param idCart Уникальный идентификатор корзины
     * @param idProduct Уникальный идентификатор товара
     * @return Возвращает корзину с внесенными изменениями
     */
    boolean deleteProduct(long idCart, long idProduct);

}
