package ru.sber.services;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.exceptions.CartIsEmptyException;
import ru.sber.exceptions.OutOfStockException;
import ru.sber.exceptions.UserNotFoundException;
import ru.sber.models.Payment;
import ru.sber.proxies.BankAppProxy;
import ru.sber.repositories.ShoppingCartRepository;
import ru.sber.repositories.UserRepository;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Сервис для оплаты
 */
@Service
public class LocalPaymentService implements PaymentService {

    public static final String JDBC = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=postgres";

    ShoppingCartRepository shoppingCartRepository;
    BankAppProxy bankAppProxy;


    public LocalPaymentService(BankAppProxy bankAppProxy, ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.bankAppProxy = bankAppProxy;

    }

    @Override
    @Transactional
    public boolean pay(Payment payment) {

        BigDecimal sum = shoppingCartRepository.getSumPriceCart(payment.getUserId());
        BigDecimal amountOfMoney = bankAppProxy.getAmountOfMoneyInTheAccount(payment.getCardNumber());

        return amountOfMoney.compareTo(sum) >= 0;

    }
}
