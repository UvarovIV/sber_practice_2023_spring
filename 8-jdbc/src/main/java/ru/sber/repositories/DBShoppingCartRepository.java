package ru.sber.repositories;

import org.springframework.stereotype.Repository;
import ru.sber.models.Product;
import ru.sber.models.ShoppingCart;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DBShoppingCartRepository implements ShoppingCartRepository {

    public static final String JDBC = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=postgres";

    ProductRepository productRepository;

    public DBShoppingCartRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ShoppingCart createShoppingCart() {
        var insertCartSql = """
                insert into products_uvarov_iv.cart(promocode)
                values (?);
                """;
        try (var connection = DriverManager.getConnection(JDBC);
             var insertCartPrepareStatement = connection.prepareStatement(insertCartSql, Statement.RETURN_GENERATED_KEYS)) {

            insertCartPrepareStatement.setString(1, "");
            insertCartPrepareStatement.executeUpdate();

            ResultSet rs = insertCartPrepareStatement.getGeneratedKeys();

            if (rs.next()) {
                return new ShoppingCart(rs.getLong(1), null, null);
            } else {
                throw new RuntimeException("Ошибка при получении идентификатора");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<ShoppingCart> findById(long id) {
        var selectCart = """
                select * 
                from products_uvarov_iv.cart
                where id = ?;
                """;
        var selectCartProducts = """
                select *
                from products_uvarov_iv.product_client pc
                join products_uvarov_iv.product p on pc.id_product = p.id
                where pc.id_cart = ?;
                """;
        List<Product> productList = new ArrayList<>();
        try (var connection = DriverManager.getConnection(JDBC);
             var selectCartStatement = connection.prepareStatement(selectCart);
             var selectCartProductsStatement = connection.prepareStatement(selectCartProducts)) {

            selectCartStatement.setLong(1, id);

            var resultSet = selectCartStatement.executeQuery();

            if (resultSet.next()) {

                selectCartProductsStatement.setLong(1, id);
                var resultProducts = selectCartProductsStatement.executeQuery();
                while (resultProducts.next()) {
                    int idProduct = resultProducts.getInt("p.id");
                    String nameProduct = resultProducts.getString("p.name");
                    BigDecimal priceProduct = BigDecimal.valueOf(resultProducts.getDouble("price"));
                    int amount = resultProducts.getInt("pc.count");
                    productList.add(new Product(idProduct, nameProduct, priceProduct, amount));
                }

                return Optional.of(new ShoppingCart(id, productList, ""));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<ShoppingCart> addToCart(long idCart, long idProduct) {

        var insertToCart = """
                insert into products_uvarov_iv.product_client
                values(DEFAULT, ?, ?, 1)
                """;

        Optional<ShoppingCart> cart = findById(idCart);
        Optional<Product> product = productRepository.findById(idProduct);

        if (cart.isPresent() && product.isPresent()) {
            try (var connection = DriverManager.getConnection(JDBC);
            var insertToCartStatement = connection.prepareStatement(insertToCart)) {
                insertToCartStatement.setLong(1, idProduct);
                insertToCartStatement.setLong(2, idCart);
                insertToCartStatement.executeUpdate();
                cart.get().getProductsList().add(product.get());
                return cart;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<ShoppingCart> updateProductAmount(long idCart, long idProduct, int amount) {
        return Optional.empty();
    }

    @Override
    public boolean deleteProduct(long idCart, long idProduct) {
        return false;
    }
}
