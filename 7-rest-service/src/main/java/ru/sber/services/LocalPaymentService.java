package ru.sber.services;

import org.springframework.stereotype.Service;
import ru.sber.exceptions.NotEnoughMoneyException;
import ru.sber.models.Payment;
import ru.sber.models.User;
import ru.sber.repositories.LocalUserRepository;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Сервис по оплате товаров
 */
@Service
public class LocalPaymentService implements PaymentService {

    LocalUserRepository localUserRepository;

    public LocalPaymentService(LocalUserRepository localUserRepository) {
        this.localUserRepository = localUserRepository;
    }

    @Override
    public boolean pay(Payment payment) {
        Optional<User> user = localUserRepository.getUserById(payment.getIdUser());
        if (user.isPresent()
                && user.get().getCart() != null
                && user.get().getCart().getProductsList() != null) {
            BigDecimal fullPrice = user.get().getCart().getProductsList().stream()
                    .map(product -> product.getPrice().multiply(BigDecimal.valueOf(product.getAmount())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            if (payment.getSum() != null) {
                if (payment.getSum().compareTo(fullPrice) == 1) {
                    return true;
                } else {
                    throw new NotEnoughMoneyException("Недостаточно средств для оплаты товара");
                }
            }
        }
        return false;
    }
}
