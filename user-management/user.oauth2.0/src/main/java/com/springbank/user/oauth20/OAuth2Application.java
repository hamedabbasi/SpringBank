package com.springbank.user.oauth20;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@EnableAuthorizationServer
@SpringBootApplication
public class OAuth2Application {

	public static void main(String[] args) {
		SpringApplication.run(OAuth2Application.class, args);
	}

}
