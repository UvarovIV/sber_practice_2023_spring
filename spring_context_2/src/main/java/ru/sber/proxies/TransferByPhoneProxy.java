package ru.sber.proxies;

import org.springframework.stereotype.Component;
import ru.sber.models.Client;

import java.math.BigDecimal;

@Component
public class TransferByPhoneProxy {

    public void transfer(Client client, BigDecimal sum) {
        System.out.printf("Перевод клиенту: %s %s на сумму %s выполнен %n", client.getSurname(), client.getName(), sum);
    }



}
