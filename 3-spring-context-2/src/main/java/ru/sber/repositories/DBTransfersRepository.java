package ru.sber.repositories;

import org.springframework.stereotype.Component;
import ru.sber.models.Client;

import java.math.BigDecimal;

@Component
public class DBTransfersRepository implements TransfersRepository {

    @Override
    public void addRecordToDB(Client client, BigDecimal sum) {
        System.out.printf("Добавление записи в БД. Перевод выполнен. Получатель: %s %s %s. Сумма %s руб. %n", client.getSurname(), client.getName(), client.getPatronymic(), sum);
    }

}
