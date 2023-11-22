package com.book.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
        .components(new Components()
            .addSecuritySchemes("bearer-key", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
        .info(new Info()
        .title("Books API")
        .description("A books API that implemented with Spring Boot and Java 21.")
        .contact(new Contact()
        .name("Faisal Ramadhan")
        .url("https://github.com/Kolong-Meja/Books-RestAPI/tree/public-v1"))
        .license(new License().name("GNU General Public License 3.0").url("https://www.gnu.org/licenses/gpl-3.0.en.html#license-text"))
        .version("0.0.1"));
    }
}
