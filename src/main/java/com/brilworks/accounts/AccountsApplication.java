package com.brilworks.accounts;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@OpenAPIDefinition(info = @Info(title = "Authorization", version = "3.0", description = "User Details"))
@SpringBootApplication
public class AccountsApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AccountsApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(AccountsApplication.class, args);
    }

}
