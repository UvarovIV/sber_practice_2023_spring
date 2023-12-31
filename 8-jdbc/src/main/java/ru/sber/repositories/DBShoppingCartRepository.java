package ru.sber.repositories;

import org.springframework.stereotype.Repository;
import ru.sber.exceptions.IdNotFoundException;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class DBShoppingCartRepository implements ShoppingCartRepository {

    public static final String JDBC = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=postgres";

    ProductRepository productRepository;

    public DBShoppingCartRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public boolean addToCart(long idClient, long idProduct, int amount) {

        var addToCartSql = """
                insert into products_uvarov_iv.products_carts
                values(DEFAULT, ?, ?, ?);
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             var addToCartPrepareStatement = connection.prepareStatement(addToCartSql, Statement.RETURN_GENERATED_KEYS)) {
            var product = productRepository.findById(idProduct);

            if (product.isPresent()) {
                addToCartPrepareStatement.setLong(1, idProduct);
                addToCartPrepareStatement.setLong(2, getIdCart(idClient));
                addToCartPrepareStatement.setInt(3, amount);

                addToCartPrepareStatement.executeUpdate();
                return addToCartPrepareStatement.getGeneratedKeys().next();
            } else {
                return false;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Получает id корзины по id пользователя
     * @param idClient Уникальный идентификатор клиента
     * @return Возвращает id корзины
     */
    public long getIdCart(long idClient) {
        var getIdCartSql = """
                select * from products_uvarov_iv.clients
                where id = ?;
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             var getIdCartPrepareStatement = connection.prepareStatement(getIdCartSql)) {
            getIdCartPrepareStatement.setLong(1, idClient);

            var resultSet = getIdCartPrepareStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getLong("cart_id");
            }
            throw new IdNotFoundException("Ошибка при получении идентификатора корзины");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateProductAmount(long idClient, long idProduct, int amount) {
        var updateProductAmountSql = """
                update products_uvarov_iv.products_carts set amount = ?
                where id_product = ? and id_cart = ?;
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             var updateProductAmountPrepareStatement = connection.prepareStatement(updateProductAmountSql)) {
            updateProductAmountPrepareStatement.setDouble(1, amount);
            updateProductAmountPrepareStatement.setLong(2, idProduct);
            updateProductAmountPrepareStatement.setLong(3, getIdCart(idClient));

            var rows = updateProductAmountPrepareStatement.executeUpdate();

            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteProduct(long idClient, long idProduct) {
        var deleteProductSql = """
                delete from products_uvarov_iv.products_carts
                where id_cart = ? and id_product = ?;
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             var deleteProductPrepareStatement = connection.prepareStatement(deleteProductSql)) {
            deleteProductPrepareStatement.setLong(1, getIdCart(idClient));
            deleteProductPrepareStatement.setLong(2, idProduct);

            var rows = deleteProductPrepareStatement.executeUpdate();

            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
