package ru.sber;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sber.config.ProjectConfig;
import ru.sber.services.ApplicationService;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        ApplicationService applicationService = context.getBean(ApplicationService.class);
        applicationService.transfer("89212341212", BigDecimal.valueOf(1500));
        System.out.println();
        applicationService.transfer("89212341215", BigDecimal.valueOf(1500));
    }



}
