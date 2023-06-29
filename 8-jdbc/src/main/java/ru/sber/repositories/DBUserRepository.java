package ru.sber.repositories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.sber.models.Product;
import ru.sber.models.ShoppingCart;
import ru.sber.models.User;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Repository
public class DBUserRepository implements UserRepository {

    public static final String JDBC = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=postgres";

    @Override
    public long signUp(User user) {
        var insertSql = """
                INSERT INTO products_uvarov_iv.client (name, username, password, cart_id, email)
                VALUES (?, ?, ?, ?, ?);
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1, user.getName());
            prepareStatement.setString(2, user.getLogin());
            prepareStatement.setString(3, user.getPassword());
            prepareStatement.setLong(4, generateCart());
            prepareStatement.setString(5, user.getEmail());

            prepareStatement.executeUpdate();

            ResultSet rs = prepareStatement.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new RuntimeException("Ошибка при получении идентификатора");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private long generateCart() {
        var insertSql = """
                INSERT INTO products_uvarov_iv.cart (promocode)
                VALUES (?);
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1, "");
            prepareStatement.executeUpdate();

            ResultSet rs = prepareStatement.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new RuntimeException("Ошибка при получении идентификатора");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> getUserById(long id) {
        var selectUser = """
                select * 
                from products_uvarov_iv.client
                where id = ?;
                """;
        var selectCart = """
                select *
                from products_uvarov_iv.product_client pc
                join products_uvarov_iv.product p on pc.id_product = p.id
                where pc.id_cart = ?;
                """;
        List<Product> productList = new ArrayList<>();
        try (var connection = DriverManager.getConnection(JDBC);
             var selectUserStatement = connection.prepareStatement(selectUser);
             var selectCartStatement = connection.prepareStatement(selectCart)) {

            selectUserStatement.setLong(1, id);

            var resultSet = selectUserStatement.executeQuery();

            if (resultSet.next()) {
                var idUser = resultSet.getInt("id");
                var name = resultSet.getString("name");
                var email = resultSet.getString("email");
                var idCart = resultSet.getInt("cart_id");

                selectCartStatement.setLong(1, idCart);
                var resultProducts = selectCartStatement.executeQuery();
                while (resultProducts.next()) {
                    int idProduct = resultProducts.getInt("p.id");
                    String nameProduct = resultProducts.getString("p.name");
                    BigDecimal priceProduct = BigDecimal.valueOf(resultProducts.getDouble("price"));
                    int amount = resultProducts.getInt("pc.count");
                    productList.add(new Product(idProduct, nameProduct, priceProduct, amount));
                }
                ShoppingCart cart = new ShoppingCart((long) idCart, productList, "");
                return Optional.of(new User(idUser, name, "", "", email, cart));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean deleteUserById(long id) {
        var deleteUser = """
                delete from products_uvarov_iv.client
                where id = ?;
                """;
        var deleteCart = """
                delete from products_uvarov_iv.cart
                where id = ?;
                """;
        var deleteCartWithProducts = """
                delete from products_uvarov_iv.product_client
                where id_cart = ?;
                """;
        var selectCartId = """
                select cart_id
                from products_uvarov_iv.client
                where id = ?;
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             var selectCartIdStatement = connection.prepareStatement(selectCartId);
             var deleteCartWithProductsStatement = connection.prepareStatement(deleteCartWithProducts);
             var deleteCartStatement = connection.prepareStatement(deleteCart);
             var deleteUserStatement = connection.prepareStatement(deleteUser)) {

            selectCartIdStatement.setLong(1, id);
            deleteUserStatement.setLong(1, id);

            var resultSet = selectCartIdStatement.executeQuery();

            if (resultSet.next()) {

                long cartId = resultSet.getLong("cart_id");
                deleteCartWithProductsStatement.setLong(1, cartId);
                deleteCartStatement.setLong(1, cartId);
                deleteCartWithProductsStatement.executeUpdate();
                int rows = deleteUserStatement.executeUpdate();
                deleteCartStatement.executeUpdate();
                return rows > 0;

            } else {
                throw new RuntimeException();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
