package ru.levelp.hospital;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Configuration
@ComponentScan(basePackages = "ru.levelp.hospital")
public class TestConfig {

    @Bean
    public EntityManagerFactory factory() {

         return Persistence.createEntityManagerFactory("TestPersistenceUnit");
    }


//    @Bean
//    public EntityManager manager(EntityManagerFactory factory) {
//        return factory.createEntityManager();
//    }
}
