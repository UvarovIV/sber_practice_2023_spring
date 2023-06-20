package config;

import beans.Cat;
import beans.Dog;
import beans.Parrot;
import beans.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {
    @Bean
    Parrot firstParrot() {
        Parrot parrot = new Parrot();
        parrot.setName("Alex");
        return parrot;
    }
    @Bean
    Parrot secondParrot() {
        Parrot parrot = new Parrot();
        parrot.setName("Dandy");
        return parrot;
    }
    @Bean
    Cat cat() {
        Cat cat = new Cat();
        cat.setName("Kirara");
        return cat;
    }
    @Bean
    Dog dog() {
        Dog dog = new Dog();
        dog.setName("Pochita");
        return dog;
    }
    @Bean
    Person person() {
        Person person = new Person();
        person.setName("Vladimir");
        person.setDog(dog());
        person.setCat(cat());
        person.setFirstParrot(firstParrot());
        person.setSecondParrot(secondParrot());
        return person;
    }

}
