package ru.sber.proxies;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import ru.sber.models.Client;

import java.util.List;

@Component
public class BankClientsProxy {

    private List<Client> clients;

    public BankClientsProxy() {
        clients = List.of(
                new Client(1, "Игорь", "Уваров", "Владимирович", "89212341212"),
                new Client(2, "Андрей", "Кузнецов", "Олегович", "89216761789"),
                new Client(3, "Виктор", "Шерстнев", "Алексеевич", "89322345217")
        );
    }

    public Client checkClient(String phone) {

        for (Client c: clients) {
            if (phone.equals(c.getPhone())) {
                return c;
            }
        }

        return null;

    }

}
