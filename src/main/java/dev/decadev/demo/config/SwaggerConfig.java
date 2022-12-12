package dev.decadev.demo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Spring Boot Security Demo API",
                version = "1.0",
                contact = @Contact(
                        name = "Bobo Martin", email = "martinbobo92@gmail.com", url = "www.bmtonweriyai.netlify.app"
                ),
                termsOfService = "Terms Of Service",
                description = "Spring Boot Security Demo API Documentation"
        )
)
public class SwaggerConfig {

    @Bean
    public OpenAPI customizeOpenApi(){
        final String securitySchemaName = "bearerAuth";

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemaName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemaName, new SecurityScheme()
                                .name(securitySchemaName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }

}
