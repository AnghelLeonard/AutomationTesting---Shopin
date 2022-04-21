package org.shopin.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@SpringBootApplication
@ComponentScan({"org.shopin.*", "org.shopin.admin"})
@EnableJpaRepositories({"org.shopin.dao", "org.shopin.admin"})
@EntityScan({"org.shopin.model", "org.shopin.admin"})
@EnableTransactionManagement
@EnableCaching
public class MainApplicationConfig {

    public static void main(String[] args) {
        SpringApplication.run(MainApplicationConfig.class, args);
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}
