package com.springbank.user.query.api;

import com.springbank.user.core.configuration.AxonConfig;
import com.springbank.user.core.configuration.MongoConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({AxonConfig.class, MongoConfig.class})
@SpringBootApplication
public class UserQueryApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserQueryApplication.class, args);
	}

}
