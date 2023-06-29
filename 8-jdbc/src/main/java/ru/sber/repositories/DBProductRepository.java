package ru.sber.repositories;

import org.springframework.stereotype.Repository;
import ru.sber.models.Product;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DBProductRepository implements ProductRepository {

    public static final String JDBC = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=postgres";

    @Override
    public long addNewProduct(Product product) {
        var addNewProductSql = """
                insert into products_uvarov_iv.product (name, price, count)
                values (?, ?, ?);
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             var addNewProductPrepareStatement = connection.prepareStatement(addNewProductSql, Statement.RETURN_GENERATED_KEYS)) {

            addNewProductPrepareStatement.setString(1, product.getName());
            addNewProductPrepareStatement.setDouble(2, product.getPrice().doubleValue());
            addNewProductPrepareStatement.setInt(3, product.getAmount());

            addNewProductPrepareStatement.executeUpdate();

            ResultSet rs = addNewProductPrepareStatement.getGeneratedKeys();
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
    public Optional<Product> findById(long productId) {
        var findProductByIdSql = """
                select * from products_uvarov_iv.product
                where id = ?;
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             var findProductByIdPrepareStatement = connection.prepareStatement(findProductByIdSql)) {
            findProductByIdPrepareStatement.setLong(1, productId);

            var resultSet = findProductByIdPrepareStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                Product product = new Product(id, name, BigDecimal.valueOf(price), 0);

                return Optional.of(product);
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> findAll(String productName) {
        var findAllSql = """
                select * from products_uvarov_iv.product
                where name like ?;
                """;
        List<Product> products = new ArrayList<>();

        try (var connection = DriverManager.getConnection(JDBC);
             var findAllPrepareStatement = connection.prepareStatement(findAllSql)) {
            findAllPrepareStatement.setString(1, "%" + (productName == null ? "" : productName) + "%");

            var resultSet = findAllPrepareStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                Product product = new Product(id, name, BigDecimal.valueOf(price), 0);

                products.add(product);
            }

            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(long id) {
        var deleteByIdSql = """
                delete from products_uvarov_iv.product
                where id = ?;
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             var deleteByIdPrepareStatement = connection.prepareStatement(deleteByIdSql)) {
            deleteByIdPrepareStatement.setLong(1, id);

            var rows = deleteByIdPrepareStatement.executeUpdate();

            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Product product) {
        var updateProductSql = """
                update products_uvarov_iv.product
                set
                name = ?,
                price = ?
                where id = ?;
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             var updateProductPrepareStatement = connection.prepareStatement(updateProductSql)) {
            updateProductPrepareStatement.setString(1, product.getName());
            updateProductPrepareStatement.setDouble(2, product.getPrice().doubleValue());
            updateProductPrepareStatement.setLong(3, product.getId());

            var rows = updateProductPrepareStatement.executeUpdate();

            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
