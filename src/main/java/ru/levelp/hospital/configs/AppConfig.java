package ru.levelp.hospital.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "ru.levelp.hospital.dao")
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
