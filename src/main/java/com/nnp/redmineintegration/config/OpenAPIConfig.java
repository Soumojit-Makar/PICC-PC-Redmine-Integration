package com.nnp.redmineintegration.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI 3.0 / Swagger UI Configuration for PICC-PC-Redmine-Integration.
 */
@Configuration
public class OpenAPIConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    @Value("${openapi.server.url:}")
    private String externalServerUrl;

    @Bean
    public OpenAPI redmineOpenAPI() {
        Contact contact = new Contact()
                .name("Nubo Native Platform Team")
                .email("contribution@nubons.com")
                .url("https://github.com/Nubo-Native-Platform/PICC-PC-Redmine-Integration");

        License license = new License()
                .name("Apache License 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0");

        Info info = new Info()
                .title("PICC-PC-Redmine-Integration REST API")
                .version("0.0.1-SNAPSHOT")
                .contact(contact)
                .description("Enterprise REST API integration microservice for Redmine. "
                        + "Provides automated user provisioning, project lifecycle management, "
                        + "role and membership governance, issue tracking, and metadata synchronization "
                        + "for the Nubo Native Platform (NNP).")
                .termsOfService("https://github.com/Nubo-Native-Platform/PICC-PC-Redmine-Integration/blob/main/LICENSE")
                .license(license);

        Server localServer = new Server()
                .url("http://localhost:" + serverPort)
                .description("Local Development Server");

        SecurityScheme apiKeyScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.QUERY)
                .name("apiKey")
                .description("Redmine REST API Access Key passed as query parameter");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("apiKey");

        List<Server> servers;
        if (externalServerUrl != null && !externalServerUrl.isBlank()) {
            Server externalServer = new Server()
                    .url(externalServerUrl)
                    .description("Deployment Server");
            servers = List.of(localServer, externalServer);
        } else {
            servers = List.of(localServer);
        }

        return new OpenAPI()
                .info(info)
                .servers(servers)
                .components(new Components().addSecuritySchemes("apiKey", apiKeyScheme))
                .addSecurityItem(securityRequirement);
    }
}
