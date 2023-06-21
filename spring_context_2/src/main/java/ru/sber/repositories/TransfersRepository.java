package ru.sber.repositories;

import ru.sber.models.Client;

import java.math.BigDecimal;

public interface TransfersRepository {
    public void addRecordToDB(Client client, BigDecimal sum);
}
