package ru.sber.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.models.Payment;
import ru.sber.proxies.BankAppProxy;
import ru.sber.repositories.ProductRepository;
import ru.sber.repositories.ShoppingCartRepository;

import java.math.BigDecimal;

/**
 * Сервис для оплаты
 */
@Service
public class LocalPaymentService implements PaymentService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
    private final BankAppProxy bankAppProxy;

    @Autowired
    public LocalPaymentService(ShoppingCartRepository shoppingCartRepository,  ProductRepository productRepository, BankAppProxy bankAppProxy) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
        this.bankAppProxy = bankAppProxy;
    }

    @Override
    @Transactional
    public boolean pay(Payment payment) {

        int userId = (int) payment.getUserId();

        BigDecimal sum = shoppingCartRepository.getSumPriceCart(userId);
        BigDecimal amountOfMoney = bankAppProxy.getAmountOfMoneyInTheAccount(payment.getCardNumber());

        if (amountOfMoney.compareTo(sum) >= 0) {
            productRepository.updateAmountOfProductsAfterPurchase(userId);
            shoppingCartRepository.clearCart(userId);
            return true;
        }

        return false;

    }
}
