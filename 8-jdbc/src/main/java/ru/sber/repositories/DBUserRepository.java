package ru.sber.repositories;

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

@Repository
public class DBUserRepository implements UserRepository {

    public static final String JDBC = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=postgres";

    @Override
    public long signUp(User user) {
        var insertSql = """
                insert into products_uvarov_iv.clients (name, username, password, cart_id, email)
                values (?, ?, ?, ?, ?);
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

    /**
     * Генерирует новую корзину
     * @return Возвращает id сгенерированной корзины
     */
    private long generateCart() {
        var insertSql = """
                insert into products_uvarov_iv.carts (promocode)
                values (?);
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
        var selectUserSql = """
                select *
                from products_uvarov_iv.clients
                where id = ?;
                """;
        var selectCartSql = """
                select p.id, p.name, p.price, pc.amount
                from products_uvarov_iv.products_carts pc
                join products_uvarov_iv.products p on pc.id_product = p.id
                where pc.id_cart = ?;
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             var selectUserStatement = connection.prepareStatement(selectUserSql);
             var selectCartStatement = connection.prepareStatement(selectCartSql)) {

            selectUserStatement.setLong(1, id);

            var resultSet = selectUserStatement.executeQuery();

            if (resultSet.next()) {
                var idUser = resultSet.getInt("id");
                var name = resultSet.getString("name");
                var email = resultSet.getString("email");
                var idCart = resultSet.getInt("cart_id");

                selectCartStatement.setLong(1, idCart);
                var resultProducts = selectCartStatement.executeQuery();
                List<Product> productList = new ArrayList<>();

                while (resultProducts.next()) {
                    int idProduct = resultProducts.getInt("id");
                    String nameProduct = resultProducts.getString("name");
                    BigDecimal priceProduct = BigDecimal.valueOf(resultProducts.getDouble("price"));
                    int amount = resultProducts.getInt("amount");
                    productList.add(new Product(idProduct, nameProduct, priceProduct, amount));
                }


                ShoppingCart cart = new ShoppingCart(idCart, productList, "");
                User user = new User(idUser, name, "", "", email, cart);
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
        var selectCartIdSql = """
                select cart_id
                from products_uvarov_iv.clients
                where id = ?;
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             var selectCartIdStatement = connection.prepareStatement(selectCartIdSql);
             var deleteCartWithProductsStatement = connection.prepareStatement(deleteCartWithProductsSql);
             var deleteCartStatement = connection.prepareStatement(deleteCartSql);
             var deleteUserStatement = connection.prepareStatement(deleteUserSql)) {

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
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
