package com.webapp.ftm.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {
    @Bean
    OpenAPI newOpenApi() {
        return new OpenAPI().info(new Info().title("FTM-web-application-document-api")
                .version("v1.0.0"));
    }
}
