package ru.lesson.springBootProject.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//SpringBootApplication анотация аккумулирует следущие аннотации:
//              Configuration,
//              EnableAutoConfiguration(Tells Spring Boot to start adding beans based on classpath [like DispatcherServlet by spring-mvc in classpath]),
//              ComponentScan (default scan root package)

@SpringBootApplication(scanBasePackages = "ru.lesson.springBootProject.*")
public class SpringBootProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootProjectApplication.class, args);
    }
}
