package ru.sber.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.sber.exceptions.IdNotFoundException;

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
}
