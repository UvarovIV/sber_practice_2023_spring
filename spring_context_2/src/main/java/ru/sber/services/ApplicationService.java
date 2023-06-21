package ru.sber.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sber.models.Client;
import ru.sber.proxies.BankClientsProxy;
import ru.sber.proxies.TransferByPhoneProxy;
import ru.sber.repositories.TransfersRepository;

import java.math.BigDecimal;

@Component
public class ApplicationService {
    private BankClientsProxy bankClientsProxy;
    private TransferByPhoneProxy transferByPhoneProxy;
    private TransfersRepository transfersRepository;

    @Autowired
    public ApplicationService(BankClientsProxy bankClientsProxy, TransferByPhoneProxy transferByPhoneProxy, TransfersRepository transfersRepository) {
        this.bankClientsProxy = bankClientsProxy;
        this.transferByPhoneProxy = transferByPhoneProxy;
        this.transfersRepository = transfersRepository;
    }

    public void transfer(String phone, BigDecimal sum) {
        Client client = bankClientsProxy.checkClient(phone);
        if (client != null) {
            System.out.println("Перевод возможен");
            transferByPhoneProxy.transfer(client, sum);
            transfersRepository.addRecordToDB(client, sum);
        } else {
            System.out.println("Этот человек не является клиентом");
        }
    }

}
