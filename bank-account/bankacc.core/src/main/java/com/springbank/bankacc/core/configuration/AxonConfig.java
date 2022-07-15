package com.springbank.bankacc.core.configuration;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.MongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.extensions.mongo.eventsourcing.tokenstore.MongoTokenStore;
import org.axonframework.serialization.Serializer;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

    @Value("${spring.data.mongodb.host:127.0.0.1}")
    private String mongoHost;
    @Value("${spring.data.mongodb.port:27017}")
    private int mongoPort;
    @Value("${spring.data.mongodb.database:bank}")
    private String mongoDatabase;

    @Bean
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(String.format("mongodb://%s:%s/%s", mongoHost, mongoPort, mongoDatabase));
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
        return MongoClients.create(mongoClientSettings);
    }

    // 'mongoTemplate' bean exists. use another name
    @Bean
    public MongoTemplate axonMongoTemplate() {
        return DefaultMongoTemplate.builder().mongoDatabase(mongoClient()).build();
    }

    @Bean
    public TokenStore axonTokenStore(Serializer serializer) {
        return MongoTokenStore.builder()
                .mongoTemplate(axonMongoTemplate())
                .serializer(serializer)
                //.serializer(XStreamSerializer.builder().xStream(mySerializer()).build())
                .build();
    }

    @Bean
    public EventStorageEngine storageEngine(MongoClient client) {
        return MongoEventStorageEngine.builder()
                .mongoTemplate(
                        DefaultMongoTemplate.builder()
                                .mongoDatabase(client)
                                .build())
                //.eventSerializer(XStreamSerializer.builder().xStream(mySerializer()).build())
                //.snapshotSerializer(XStreamSerializer.builder().xStream(mySerializer()).build())
                .build();
    }

    @Bean
    public EmbeddedEventStore embeddedEventStore(EventStorageEngine eventStorageEngine, AxonConfiguration axonConfiguration) {
        return EmbeddedEventStore.builder()
                .storageEngine(eventStorageEngine)
                .messageMonitor(axonConfiguration.messageMonitor(EventStore.class, "eventStore"))
                .build();
    }

    // My own code to fix AxonConfigurationException: A default XStreamSerializer is used for snapshots, without specifying the security context
    /*@Bean
    public XStream mySerializer() {
        XStream xstream = new XStream();
        // clear out existing permissions and set own ones
        xstream.addPermission(NoTypePermission.NONE);
        // allow some basics
        xstream.addPermission(NullPermission.NULL);
        xstream.addPermission(PrimitiveTypePermission.PRIMITIVES);
        xstream.allowTypeHierarchy(Collection.class);
        XStream.setupDefaultSecurity(xstream); // to be removed after 1.5
        // allow any type from the same package
        xstream.allowTypesByWildcard(new String[]{"com.springbank.user.**"});
        return xstream;
    }*/

    @Bean
    public XStream mySerializer() {
        XStream xstream = new XStream();
        // clear out existing permissions and set own ones
        xstream.addPermission(AnyTypePermission.ANY);
        XStream.setupDefaultSecurity(xstream); // to be removed after 1.5
        return xstream;
    }

}
