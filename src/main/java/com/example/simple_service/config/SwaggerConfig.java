package com.example.simple_service.config;

import com.example.simple_service.utils.swagger.SwaggerInfo;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${swagger-api.version}")
    private String swaggerApiVersion;

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info()
                .title(SwaggerInfo.TITLE)
                .description(SwaggerInfo.DESCRIPTION)
                .version(swaggerApiVersion));
    }

}
