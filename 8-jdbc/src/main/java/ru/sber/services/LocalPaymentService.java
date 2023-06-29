package ru.sber.services;

import org.springframework.stereotype.Service;
import ru.sber.exceptions.NotEnoughMoneyException;
import ru.sber.models.Payment;
import ru.sber.models.Product;
import ru.sber.models.ShoppingCart;
import ru.sber.models.User;
import ru.sber.repositories.LocalUserRepository;
import ru.sber.repositories.UserRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для оплаты
 */
@Service
public class LocalPaymentService implements PaymentService {

    UserRepository userRepository;

    public LocalPaymentService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean pay(Payment payment) {

        Optional<User> user = userRepository.getUserById(payment.getUserId());

        if (user.isPresent()) {

            ShoppingCart cart = user.get().getCart();
            List<Product> productList = cart.getProductsList();

            if (productList != null) {

                BigDecimal fullPrice = productList.stream()
                        .map(product -> product.getPrice().multiply(BigDecimal.valueOf(product.getAmount())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                if (payment.getSum() != null) {
                    if (payment.getSum().compareTo(fullPrice) >= 0) {
                        return true;
                    } else {
                        throw new NotEnoughMoneyException("Недостаточно средств для оплаты товара");
                    }
                }
            }
        }
        return false;
    }
}
