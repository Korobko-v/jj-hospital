package ru.levelp.hospital.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.levelp.hospital.daoimpl.DoctorDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "ru.levelp.hospital.daoimpl")
public class AppConfig {
//    @Bean
//    public EntityManager entityManager(EntityManagerFactory factory) {
//        return factory.createEntityManager();
//}

//    @Bean
//    public EntityManagerFactory factory() {
//        return Persistence.createEntityManagerFactory("TestPersistenceUnit");
//    }
//    @Bean
//    public ObjectMapper objectMapper() {
//        return new ObjectMapper();
//    }
}
