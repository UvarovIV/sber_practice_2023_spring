package ru.sber.proxies;

import org.springframework.stereotype.Component;
import ru.sber.exceptions.CardIsNotExistException;
import ru.sber.models.BankCard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class BankAppProxy implements BankAppProxyInterface {

    List<BankCard> cards = new ArrayList<>(List.of(
            new BankCard(123456, BigDecimal.valueOf(1500)),
            new BankCard(123444, BigDecimal.valueOf(700)),
            new BankCard(143456, BigDecimal.valueOf(10500))
    )) ;

    @Override
    public BigDecimal getAmountOfMoneyInTheAccount(long numberOfCard) {
        for (var card: cards) {
            if (card.getNumberOfCard() == numberOfCard) {
                return card.getMoney();
            }
        }
        throw new CardIsNotExistException("Такой карты не существует");
    }
}
