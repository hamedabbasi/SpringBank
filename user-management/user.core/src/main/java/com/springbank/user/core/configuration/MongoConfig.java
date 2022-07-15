package com.springbank.user.core.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection"})
public class MongoConfig {

    @Autowired
    private MongoDatabaseFactory mongoDatabaseFactory;

    @Autowired
    private MongoMappingContext mongoMappingContext;

    @Bean
    public MappingMongoConverter mappingMongoConverter() {
        DefaultDbRefResolver defaultDbRefResolver = new DefaultDbRefResolver(mongoDatabaseFactory);
        MappingMongoConverter mappingMongoConverter = new MappingMongoConverter(defaultDbRefResolver, mongoMappingContext);
        mappingMongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return mappingMongoConverter;
    }
}
