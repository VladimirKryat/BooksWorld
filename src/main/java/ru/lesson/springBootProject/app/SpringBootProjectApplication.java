package ru.lesson.springBootProject.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Properties;

//SpringBootApplication анотация аккумулирует следущие аннотации:
//              Configuration,
//              EnableAutoConfiguration(Tells Spring Boot to start adding beans based on classpath [like DispatcherServlet by spring-mvc in classpath]),
//              ComponentScan (default scan root package)

@SpringBootApplication(scanBasePackages = "ru.lesson.springBootProject")
@EntityScan(basePackages = "ru.lesson.springBootProject.models")
@EnableJpaRepositories(basePackages = "ru.lesson.springBootProject.repositories")
public class SpringBootProjectApplication {

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder(8);
    }
    public static void main(String[] args) {
        SpringApplication.run(SpringBootProjectApplication.class, args);
    }
}
