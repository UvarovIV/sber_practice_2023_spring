package beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Person {
    private String name;
    private Parrot firstParrot, secondParrot;
    private Dog dog;
    private Cat cat;

    @Autowired
    public Person(Parrot firstParrot, Parrot secondParrot, Dog dog, Cat cat) {
        this.firstParrot = firstParrot;
        this.secondParrot = secondParrot;
        this.dog = dog;
        this.cat = cat;
    }

    @PostConstruct
    public void setName() {
        this.name = "Vladimir";
        this.firstParrot.setName("Alex");
        this.secondParrot.setName("Dandy");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Parrot getFirstParrot() {
        return firstParrot;
    }

    public void setFirstParrot(Parrot firstParrot) {
        this.firstParrot = firstParrot;
    }

    public Parrot getSecondParrot() {
        return secondParrot;
    }

    public void setSecondParrot(Parrot secondParrot) {
        this.secondParrot = secondParrot;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public Cat getCat() {
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", firstParrot=" + firstParrot +
                ", secondParrot=" + secondParrot +
                ", dog=" + dog +
                ", cat=" + cat +
                '}';
    }
}
