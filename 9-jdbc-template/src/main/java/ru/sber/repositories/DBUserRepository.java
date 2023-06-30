package ru.sber.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.sber.models.Product;
import ru.sber.models.ShoppingCart;
import ru.sber.models.User;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

@Repository
public class DBUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DBUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long signUp(User user) {
        var insertSql = """
                insert into clients (name, username, password, cart_id, email)
                values (?, ?, ?, ?, ?);
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator preparedStatementCreator = connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setLong(4, generateCart());
            preparedStatement.setString(5, user.getEmail());

            return preparedStatement;
        };

        jdbcTemplate.update(preparedStatementCreator, keyHolder);

        return (long) (int) keyHolder.getKeys().get("id");
    }

    /**
     * Генерирует новую корзину
     * @return Возвращает id сгенерированной корзины
     */
    private long generateCart() {
        var insertSql = """
                insert into carts (promocode)
                values (?);
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator preparedStatementCreator = connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, "");

            return preparedStatement;
        };

        jdbcTemplate.update(preparedStatementCreator, keyHolder);

        return (long) (int) keyHolder.getKeys().get("id");
    }

    @Override
    public Optional<User> getUserById(long id) {
        var selectUserSql = """
                select *
                from clients
                where id = ?;
                """;

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(selectUserSql);
            prepareStatement.setInt(1, (int) id);
            return prepareStatement;
        };

        RowMapper<User> userRowMapper = (resultSet, rowNum) -> {
            int userId = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            int idCart = resultSet.getInt("cart_id");
            return new User(userId, name, null, null, email, getCartById(idCart).get());
        };

        return jdbcTemplate.query(preparedStatementCreator, userRowMapper).stream().findAny();

    }

    @Override
    public boolean checkUserExistence(long userId) {
        String checkUserSql = """
                select count(*)
                from clients
                where id = ?;
                """;

        int rows = jdbcTemplate.queryForObject(checkUserSql, Integer.class, userId);

        return rows < 1;
    }


    private Optional<ShoppingCart> getCartById(long idCart) {
        var selectProductsListSql = """
                select *
                from products_carts
                join products p on id_product = p.id
                where id_cart = ?
                """;

        var getCartSql = """
                select *
                from carts
                where id = ?
                """;

        PreparedStatementCreator productPreparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(selectProductsListSql);
            prepareStatement.setInt(1, (int) idCart);
            return prepareStatement;
        };

        PreparedStatementCreator cartPreparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(getCartSql);
            prepareStatement.setInt(1, (int) idCart);
            return prepareStatement;
        };

        RowMapper<Product> productRowMapper = (resultSet, rowNum) -> {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            BigDecimal price = BigDecimal.valueOf(resultSet.getDouble("price"));
            int amount = resultSet.getInt("amount");
            return new Product(id, name, price, amount);
        };

        RowMapper<ShoppingCart> cartRowMapper = (resultSet, rowNum) -> new ShoppingCart(idCart, jdbcTemplate.query(productPreparedStatementCreator, productRowMapper), "");
        return jdbcTemplate.query(cartPreparedStatementCreator, cartRowMapper).stream().findAny();

    }

    @Override
    public boolean deleteUserById(long id) {
        var deleteUserSql = """
                delete from products_uvarov_iv.clients
                where id = ?;
                """;
        var deleteCartSql = """
                delete from products_uvarov_iv.carts
                where id = ?;
                """;
        var deleteCartWithProductsSql = """
                delete from products_uvarov_iv.products_carts
                where id_cart = ?;
                """;

        jdbcTemplate.update(deleteCartWithProductsSql, id);
        var rows = jdbcTemplate.update(deleteUserSql, id);
        jdbcTemplate.update(deleteCartSql, id);
        return rows > 0;
    }
}
