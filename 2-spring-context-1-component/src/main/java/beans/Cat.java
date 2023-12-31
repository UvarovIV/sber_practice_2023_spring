package beans;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Cat {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @PostConstruct
    public void setName() {
        this.name = "Kirara";
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                '}';
    }
}
