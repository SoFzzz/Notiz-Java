package com.notiz.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoAuditing
@EnableMongoRepositories(basePackages = "com.notiz.repository")
public class MongoConfig {
    // MongoAuditing relies on @CreatedDate and @LastModifiedDate in BaseDocument
}
