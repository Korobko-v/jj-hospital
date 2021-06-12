package ru.levelp.hospital.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Configuration
@ComponentScan(basePackages = "ru.levelp.hospital")
public class AppConfig {
    @Bean
    public EntityManager manager(EntityManagerFactory factory) {
        return factory.createEntityManager();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
