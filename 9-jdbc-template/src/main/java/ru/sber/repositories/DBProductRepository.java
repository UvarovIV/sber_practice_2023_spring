package ru.sber.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.sber.models.Product;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
public class DBProductRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DBProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long addNewProduct(Product product) {
        var addNewProductSql = """
                insert into products (name, price, amount)
                values (?, ?, ?);
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator preparedStatementCreator = connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(addNewProductSql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice().doubleValue());
            preparedStatement.setInt(3, product.getAmount());
            return preparedStatement;
        };

        jdbcTemplate.update(preparedStatementCreator, keyHolder);

        return (long) (int) keyHolder.getKeys().get("id");

    }

    @Override
    public Optional<Product> findById(long productId) {
        var findProductByIdSql = """
                select * from products
                where id = ?;
                """;

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var preparedStatement = connection.prepareStatement(findProductByIdSql);
            preparedStatement.setLong(1, productId);

            return preparedStatement;
        };

        RowMapper<Product> productRowMapper = getProductRowMapper();

        List<Product> products = jdbcTemplate.query(preparedStatementCreator, productRowMapper);

        return products.stream().findFirst();
    }

    @Override
    public List<Product> findAll(String productName) {
        var findAllSql = """
                select * from products
                where name like ?;
                """;

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(findAllSql);
            prepareStatement.setString(1, "%" + (productName == null ? "" : productName) + "%");

            return prepareStatement;
        };

        RowMapper<Product> productRowMapper = getProductRowMapper();

        return jdbcTemplate.query(preparedStatementCreator, productRowMapper);

    }

    private static RowMapper<Product> getProductRowMapper() {
        return (resultSet, rowNum) -> {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            double price = resultSet.getDouble("price");
            int amount = resultSet.getInt("amount");
            return new Product(id, name, BigDecimal.valueOf(price), amount);
        };
    }

    @Override
    public boolean deleteById(long id) {
        var deleteByIdSql = """
                delete from products
                where id = ?;
                """;

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(deleteByIdSql);
            prepareStatement.setLong(1, id);

            return prepareStatement;
        };

        int rows = jdbcTemplate.update(preparedStatementCreator);

        return rows > 0;
    }

    @Override
    public boolean update(Product product) {
        var updateProductSql = """
                update products
                set
                name = ?,
                price = ?,
                amount = ?
                where id = ?;
                """;

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(updateProductSql);
            prepareStatement.setString(1, product.getName());
            prepareStatement.setDouble(2, product.getPrice().doubleValue());
            prepareStatement.setInt(3, product.getAmount());
            prepareStatement.setLong(4, product.getId());

            return prepareStatement;
        };

        int rows = jdbcTemplate.update(preparedStatementCreator);

        return rows > 0;
    }

}
