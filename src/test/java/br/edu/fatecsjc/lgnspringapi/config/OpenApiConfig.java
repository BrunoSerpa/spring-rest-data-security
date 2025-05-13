package br.edu.fatecsjc.lgnspringapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenApiConfigTest {
    @Test
    void validateOpenAPIAnnotations() {
        OpenAPIDefinition apiDef = OpenApiConfig.class.getAnnotation(OpenAPIDefinition.class);
        SecurityScheme securityScheme = OpenApiConfig.class.getAnnotation(SecurityScheme.class);

        assertNotNull(apiDef, "OpenAPIDefinition annotation should be present");

        Info info = apiDef.info();
        assertEquals("OpenApi specification - LGN", info.title());
        assertEquals("1.0", info.version());
        assertEquals("Projeto Semestral de Qualidade de Software", info.description());
        assertEquals("Terms of service", info.termsOfService());

        Contact contact = info.contact();
        assertEquals("Bruno Serpa Pereira Carvalho", contact.name());
        assertEquals("brunospc2005@gmail.com", contact.email());
        assertEquals("http://brunoserpa.vercel.app/", contact.url());

        License license = info.license();
        assertEquals("MIT License", license.name());
        assertEquals("https://opensource.org/license/mit/", license.url());

        Server[] servers = apiDef.servers();
        assertEquals(1, servers.length);
        assertEquals("Local ENV", servers[0].description());
        assertEquals("http://localhost:8000", servers[0].url());

        assertNotNull(securityScheme, "SecurityScheme annotation should be present");
        assertEquals("bearerAuth", securityScheme.name());
        assertEquals("JWT auth description", securityScheme.description());
        assertEquals("bearer", securityScheme.scheme());
        assertEquals(SecuritySchemeType.HTTP, securityScheme.type());
        assertEquals("JWT", securityScheme.bearerFormat());
        assertEquals(SecuritySchemeIn.HEADER, securityScheme.in());
    }
}