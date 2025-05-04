package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * We can also put annotation @EnableTransactionManagement here
 * if we are defining bean of PlatformTransactionManager in Main class. But for organizing we have put its config in TransactionConfig :)!
 *
 */
@SpringBootApplication
@EnableScheduling
public class JournalApp
{
    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(JournalApp.class, args);
        ConfigurableEnvironment configurableEnvironment = context.getEnvironment();
        System.out.println("Current Env: "+configurableEnvironment.getActiveProfiles()[0]);

        System.out.println("Redis IP  : "+configurableEnvironment.getProperty("spring.redis.host"));
        System.out.println("Redis Port: "+configurableEnvironment.getProperty("spring.redis.port"));
    }
}
