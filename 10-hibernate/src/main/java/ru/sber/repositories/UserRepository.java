package ru.sber.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.entities.User;

/**
 * Хранилище с данными о пользователях
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
