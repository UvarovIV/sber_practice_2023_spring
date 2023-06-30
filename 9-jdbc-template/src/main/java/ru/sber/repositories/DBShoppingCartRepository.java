package ru.sber.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.sber.exceptions.CartIsEmptyException;
import ru.sber.exceptions.IdNotFoundException;
import ru.sber.exceptions.OutOfStockException;
import ru.sber.exceptions.UserNotFoundException;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class DBShoppingCartRepository implements ShoppingCartRepository {

    ProductRepository productRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DBShoppingCartRepository(ProductRepository productRepository, JdbcTemplate jdbcTemplate) {
        this.productRepository = productRepository;
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public boolean addToCart(long idClient, long idProduct, int amount) {

        var addToCartSql = """
                insert into products_carts
                values(DEFAULT, ?, ?, ?);
                """;
        var updateAmountSql = """
                update products_carts
                set amount = amount + ?
                where id_cart = ? and id_product = ?;
                """;

        int rows = jdbcTemplate.update(updateAmountSql, amount, idClient, idProduct);
        if (rows == 0) {
            return jdbcTemplate.update(addToCartSql, idProduct, idClient, amount) > 0;
        }
        return true;
    }

    @Override
    public boolean updateProductAmount(long idClient, long idProduct, int amount) {
        var updateProductAmountSql = """
                update products_carts set amount = ?
                where id_product = ? and id_cart = ?;
                """;

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var preparedStatement = connection.prepareStatement(updateProductAmountSql);
            preparedStatement.setDouble(1, amount);
            preparedStatement.setLong(2, idProduct);
            preparedStatement.setLong(3, idClient);

            return preparedStatement;
        };

        int rows = jdbcTemplate.update(preparedStatementCreator);

        return rows > 0;

    }

    @Override
    public boolean deleteProduct(long idClient, long idProduct) {
        var deleteProductSql = """
                delete from products_carts
                where id_cart = ? and id_product = ?;
                """;

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var preparedStatement = connection.prepareStatement(deleteProductSql);
            preparedStatement.setLong(1, idClient);
            preparedStatement.setLong(2, idProduct);

            return preparedStatement;
        };

        int rows = jdbcTemplate.update(preparedStatementCreator);

        return rows > 0;

    }

    @Override
    public BigDecimal getSumPriceCart(long userId) {
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
}
