package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@EnableTransactionManagement
@Configuration
public class TransactionConfig {

    /* Spring provides an Interface -- PlatformTransactionManager for managing transactions
    * which needs an impl of MongoDatabaseFactory.
    *
    * In case of single DB connection if we are providing db details in application properties
    * Spring automatically creates an Impl of MongoDatabaseFactory Interface -- SimpleMongoClientDatabaseFactory at app startup.
    *  */

    @Bean
    public PlatformTransactionManager platformTransactionManager(MongoDatabaseFactory mongoDatabaseFactory){
        return new MongoTransactionManager(mongoDatabaseFactory);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
