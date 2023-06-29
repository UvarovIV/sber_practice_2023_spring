package ru.sber.services;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.exceptions.CartIsEmptyException;
import ru.sber.exceptions.OutOfStockException;
import ru.sber.exceptions.UserNotFoundException;
import ru.sber.models.Payment;
import ru.sber.proxies.BankAppProxy;
import ru.sber.repositories.UserRepository;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Сервис для оплаты
 */
@Service
public class LocalPaymentService implements PaymentService {

    public static final String JDBC = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=postgres";

    UserRepository userRepository;
    BankAppProxy bankAppProxy;
    JdbcTemplate jdbcTemplate;

    public LocalPaymentService(UserRepository userRepository, BankAppProxy bankAppProxy, JdbcTemplate jdbcTemplate) {
        this.userRepository = userRepository;
        this.bankAppProxy = bankAppProxy;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Рассчитывает стоимость товаров в корзине пользователя
     * @param userId Уникальный идентификатор пользователя
     * @return Возвращает полную стоимость корзины
     */
    private BigDecimal getSumPriceCart(long userId) {
        String countSumSql = """
                select sum(p.price * pc.amount) sum
                from clients c
                join products_carts pc on pc.id_cart = c.cart_id
                join products p on p.id = pc.id_product
                where c.id = ?;
                """;

        String checkUserSql = """ 
                select count(*) client
                from clients
                where id = ?;
                """;

        String updateAmountOfProductSql = """
                update products p 
                set amount = amount - (select products_carts.amount from products_carts where id_product = p.id and id_cart = ?)
                where id in (select id_product from products_carts where id_cart = ?)
                """;

        String getAmountOfProductSql = """
                select count(*)
                from products p
                join products_carts pc on p.id = pc.id_product and p.amount < pc.amount
                where id_cart=?;
                """;

        Integer userFound = jdbcTemplate.queryForObject(checkUserSql, Integer.class, userId);

        if (userFound < 1) {
            throw new UserNotFoundException("Пользователь не найден");
        }

        Integer count = jdbcTemplate.queryForObject(getAmountOfProductSql, Integer.class, userId);
        if (count > 0) {
            throw new OutOfStockException("Такого количества товара нет в наличии");
        }

        jdbcTemplate.update(updateAmountOfProductSql, userId, userId);
        Double sum = jdbcTemplate.queryForObject(countSumSql, Double.class, userId);

        if (sum == null) {
            throw new CartIsEmptyException("В корзине нет ни одного товара");
        }

        return BigDecimal.valueOf(sum);
    }

    @Override
    @Transactional
    public boolean pay(Payment payment) {

        BigDecimal sum = getSumPriceCart(payment.getUserId());
        BigDecimal amountOfMoney = bankAppProxy.getAmountOfMoneyInTheAccount(payment.getCardNumber());

        return amountOfMoney.compareTo(sum) >= 0;

    }
}
