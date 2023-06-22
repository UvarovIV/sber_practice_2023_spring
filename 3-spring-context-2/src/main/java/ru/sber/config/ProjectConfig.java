package ru.sber.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
        basePackages = {"ru.sber.proxies", "ru.sber.repositories", "ru.sber.services"}
)
public class ProjectConfig {}
