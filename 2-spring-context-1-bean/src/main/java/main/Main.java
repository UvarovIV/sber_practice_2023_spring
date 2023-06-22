package main;

import beans.Person;
import config.ProjectConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        Person p = context.getBean(Person.class);
        System.out.println(p);
        System.out.println(p.getCat());
        System.out.println(p.getDog());
        System.out.println(p.getFirstParrot());
        System.out.println(p.getSecondParrot());
        System.out.println(p.getName());
    }
}
