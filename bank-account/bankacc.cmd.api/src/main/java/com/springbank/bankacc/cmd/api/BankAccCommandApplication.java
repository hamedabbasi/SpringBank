package com.springbank.bankacc.cmd.api;

import com.springbank.bankacc.core.configuration.AxonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import(value = {AxonConfig.class})
// The reason that we have excluded data source config is that we
// will only connect to the data jpa in the query side. that basically
// be a mysql bank account database.
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class BankAccCommandApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankAccCommandApplication.class, args);
    }

}
