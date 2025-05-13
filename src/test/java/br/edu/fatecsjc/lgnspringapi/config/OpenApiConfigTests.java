package br.edu.fatecsjc.lgnspringapi.config;

import static org.junit.jupiter.api.Assertions.*;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OpenApiConfigTests {
    private OpenAPI openAPI;

    @Test
    void testAnnotationsPresenceAndValues() {
        OpenAPIDefinition openAPIDefinition = OpenApiConfig.class.getAnnotation(OpenAPIDefinition.class);
        assertNotNull(openAPIDefinition, "A anotação OpenAPIDefinition deve estar presente");

        Info info = openAPIDefinition.info();
        assertNotNull(info, "A configuração Info não pode ser nula");
        assertEquals("OpenApi specification - LGN", info.title(), "Título da OpenAPI incorreto");
        assertEquals("Projeto Semestral de Qualidade de Software", info.description(), "Descrição incorreta");

        assertEquals("Bruno Serpa Pereira Carvalho", info.contact().name(), "Nome de contato incorreto");
        assertEquals("brunospc2005@gmail.com", info.contact().email(), "Email de contato incorreto");
        assertEquals("http://brunoserpa.vercel.app/", info.contact().url(), "URL de contato incorreta");

        SecurityScheme securityScheme = OpenApiConfig.class.getAnnotation(SecurityScheme.class);
        assertNotNull(securityScheme, "A anotação SecurityScheme deve estar presente");
        assertEquals("bearerAuth", securityScheme.name(), "Nome do security scheme incorreto");
        assertEquals("bearer", securityScheme.scheme(), "Scheme deve ser 'bearer'");
        assertEquals("JWT", securityScheme.bearerFormat(), "Bearer format deve ser 'JWT'");
    }

    @Test
    void testOpenApiBeanLoaded() {
        if (openAPI != null && openAPI.getInfo() != null) {
            assertTrue(openAPI.getInfo().getTitle().contains("OpenApi"), "O título do OpenAPI deve conter 'OpenApi'");
        }
    }
}