package ru.sber.services;

import org.springframework.stereotype.Service;
import ru.sber.exceptions.CartIsEmptyException;
import ru.sber.exceptions.UserNotFoundException;
import ru.sber.models.Payment;
import ru.sber.proxies.BankAppProxy;
import ru.sber.repositories.UserRepository;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    /**
     * Рассчитывает стоимость товаров в корзине пользователя
     * @param userId Уникальный идентификатор пользователя
     * @return Возвращает полную стоимость корзины
     */
    private BigDecimal getSumPriceCart(long userId) {
        String countSumSql = """
                select sum(p.price * pc.count) sum
                from products_uvarov_iv.client c
                join products_uvarov_iv.product_client pc on pc.id_cart = c.cart_id
                join products_uvarov_iv.product p on p.id = pc.id_product
                where c.id = ?;
                """;

        String checkUserSql = """ 
                select count(*) client
                from products_uvarov_iv.client
                where id = ?;
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             var countSumStatement = connection.prepareStatement(countSumSql);
             var checkUserStatement = connection.prepareStatement(checkUserSql)) {

            countSumStatement.setLong(1, userId);
            checkUserStatement.setLong(1, userId);

            var check = checkUserStatement.executeQuery();
            if (check.next()) {
                int userFound = check.getInt("client");
                if (userFound == 0) {
                    throw new UserNotFoundException("Пользователь не найден");
                }
            }
            var resultProducts = countSumStatement.executeQuery();
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
