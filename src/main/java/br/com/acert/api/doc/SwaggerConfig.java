package br.com.acert.api.doc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    protected OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"))
                )
                .info(new Info()
                        .title("API Desafio Acert")
                        .description(
                                "<pre>"
                                        + "=========================================================================<br>"
                                        + "<br>"
                                        + "Sistema simplificado para o controle de delivery de restaurante<br>"
                                        + "Acesso controlado por JWT.<br>"
                                        + "<br>"
                                        + "Para login e gerar o token JWT, utilize:<br>"
                                        + "- usuário: user@email.com  | senha: 123456<br>"
                                        + "<br>"
                                        + "Crie seu próprio usuário se desejar."
                                        + "========================================================================="
                                        + "</pre>")

                        .contact(new Contact()
                                .name("Felipe Mattos")
                                .email("fabramattos@gmail.com"))
                );
    }
}
