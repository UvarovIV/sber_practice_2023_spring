package ru.sber.repositories;

import org.springframework.stereotype.Repository;

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
                insert into products_uvarov_iv.product_client
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
                throw new RuntimeException("Ошибка при получении идентификатора 1");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

    public long getIdCart(long idClient) {
        var getIdCartSql = """
                select * from products_uvarov_iv.client
                where id = ?;
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             var getIdCartPrepareStatement = connection.prepareStatement(getIdCartSql)) {
            getIdCartPrepareStatement.setLong(1, idClient);

            var resultSet = getIdCartPrepareStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getLong(5);
            }
            throw new RuntimeException("Ошибка при получении идентификатора корзины");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateProductAmount(long idClient, long idProduct, int amount) {
        var updateProductAmountSql = """
                update products_uvarov_iv.product_client set count = ?
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
                delete from products_uvarov_iv.product_client
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
