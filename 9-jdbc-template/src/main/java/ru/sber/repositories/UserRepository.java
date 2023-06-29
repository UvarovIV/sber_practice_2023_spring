package ru.sber.repositories;

import ru.sber.models.User;

import java.util.Optional;

/**
 * Работа с данными о пользователе
 */
public interface UserRepository {

    /**
     * Регистрирует пользователя
     * @param user Данные о пользователе
     * @return Возвращает идентификатор созданного пользователя
     */
    long signUp(User user);

    /**
     * Производит поиск пользователя по id
     * @param id Уникальный идентификатор пользователя
     * @return Возвращает найденного пользователя
     */
    Optional<User> getUserById(long id);

    /**
     * Удаляет пользователя по id
     * @param id Уникальный идентификатор пользователя
     * @return Возвращает true при удачном удалении и false, если пользователя не существует
     */
    boolean deleteUserById(long id);

}
