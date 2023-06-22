package ru.sber.proxies;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import ru.sber.models.Client;

import java.util.List;
import java.util.Optional;

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

    public Optional<Client> checkClient(String phone) {

        for (Client cl: clients) {
            if (phone.equals(cl.getPhone())) {
                return Optional.of(cl);
            }
        }

        return Optional.empty();

    }

}
