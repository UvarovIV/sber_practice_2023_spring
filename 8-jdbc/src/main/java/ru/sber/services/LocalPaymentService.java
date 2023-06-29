package ru.sber.services;

import org.springframework.stereotype.Service;
import ru.sber.exceptions.CartIsEmptyException;
import ru.sber.exceptions.NotEnoughMoneyException;
import ru.sber.exceptions.UserNotFoundException;
import ru.sber.models.Payment;
import ru.sber.models.Product;
import ru.sber.models.ShoppingCart;
import ru.sber.models.User;
import ru.sber.proxies.BankAppProxy;
import ru.sber.repositories.UserRepository;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для оплаты
 */
@Service
public class LocalPaymentService implements PaymentService {

    public static final String JDBC = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=postgres";

    UserRepository userRepository;
    BankAppProxy bankAppProxy;

    public LocalPaymentService(UserRepository userRepository, BankAppProxy bankAppProxy) {
        this.userRepository = userRepository;
        this.bankAppProxy = bankAppProxy;
    }

    private BigDecimal getSumPriceCart(long userId) {
        String selectSum = """
                select sum(p.price * pc.count) sum
                from products_uvarov_iv.client c
                join products_uvarov_iv.product_client pc on pc.id_cart = c.cart_id
                join products_uvarov_iv.product p on p.id = pc.id_product
                where c.id = ?;
                """;

        String countUser = """ 
                select count(*) client
                from products_uvarov_iv.client
                where id = ?;
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             var selectSumStatement = connection.prepareStatement(selectSum);
             var countUserStatement = connection.prepareStatement(countUser)) {

            countUserStatement.setLong(1, userId);
            selectSumStatement.setLong(1, userId);

            var act = countUserStatement.executeQuery();
            if (act.next()) {
                int userFound = act.getInt("client");
                if (userFound == 0) {
                    throw new UserNotFoundException("Пользователь не найден");
                }
            }
            var resultProducts = selectSumStatement.executeQuery();
            if (resultProducts.next()) {
                double sum = resultProducts.getDouble("sum");
                if (sum != 0) {
                    return BigDecimal.valueOf(sum);
                }
            }
            throw new CartIsEmptyException("В корзине нет ни одного товара");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean pay(Payment payment) {

        BigDecimal sum = getSumPriceCart(payment.getUserId());
        BigDecimal amountOfMoney = bankAppProxy.getAmountOfMoneyInTheAccount(payment.getCardNumber());

        return amountOfMoney.compareTo(sum) >= 0;

    }
}
