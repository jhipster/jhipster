package io.github.jhipster.config.apidoc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;

import static io.github.jhipster.config.JHipsterConstants.SPRING_PROFILE_API_DOCS;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
    classes = SpringfoxAutoconfigurationTest.TestApp.class,
    properties = {
        "spring.liquibase.enabled=false",
        "security.basic.enabled=false",
        "jhipster.api-docs.default-include-pattern=/scanned/.*",
        "jhipster.api-docs.host=test.jhipster.com",
        "jhipster.api-docs.protocols=http,https",
        "jhipster.api-docs.title=test title",
        "jhipster.api-docs.description=test description",
        "jhipster.api-docs.version=test version",
        "jhipster.api-docs.terms-of-service-url=test tos url",
        "jhipster.api-docs.contact-name=test contact name",
        "jhipster.api-docs.contact-email=test contact email",
        "jhipster.api-docs.contact-url=test contact url",
        "jhipster.api-docs.license=test license name",
        "jhipster.api-docs.license-url=test license url",
        "jhipster.api-docs.servers[0].url=test server url",
        "management.endpoints.web.base-path=/management",
        "spring.application.name=testApp"

    })
@ActiveProfiles(SPRING_PROFILE_API_DOCS)
@AutoConfigureMockMvc
public class SpringfoxAutoconfigurationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void generatesOAS() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
            .andExpect((status().isOk()))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.info.title").value("test title"))
            .andExpect(jsonPath("$.info.description").value("test description"))
            .andExpect(jsonPath("$.info.version").value("test version"))
            .andExpect(jsonPath("$.info.termsOfService").value("test tos url"))
            .andExpect(jsonPath("$.info.contact.name").value("test contact name"))
            .andExpect(jsonPath("$.info.contact.url").value("test contact url"))
            .andExpect(jsonPath("$.info.contact.email").value("test contact email"))
            .andExpect(jsonPath("$.info.license.name").value("test license name"))
            .andExpect(jsonPath("$.info.license.url").value("test license url"))
            .andExpect(jsonPath("$.paths./scanned/test").exists())
            .andExpect(jsonPath("$.paths./not-scanned/test").doesNotExist())
            // TODO: fix bug in Springfox
            //.andExpect(jsonPath("$.servers.[*].url").value(hasItem("test server url")))
            .andExpect(jsonPath("$.servers.[*].url").value(hasItem("http://localhost:80")));
    }

    @Test
    void generatesSwaggerV2() throws Exception {
        mockMvc.perform(get("/v2/api-docs"))
            .andExpect((status().isOk()))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.paths./scanned/test").exists())
            .andExpect(jsonPath("$.host").value("test.jhipster.com"))
            .andExpect(jsonPath("$.schemes").value(hasItems("http", "https")));
    }

    @Test
    void setsPageParameters() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
            .andExpect((status().isOk()))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.paths./scanned/test.get.parameters[?(@.name == 'page')]").exists())
            .andExpect(jsonPath("$.paths./scanned/test.get.parameters[?(@.name == 'page')].in").value("query"))
            .andExpect(jsonPath("$.paths./scanned/test.get.parameters[?(@.name == 'page')].schema.type").value("integer"))
            .andExpect(jsonPath("$.paths./scanned/test.get.parameters[?(@.name == 'size')]").exists())
            .andExpect(jsonPath("$.paths./scanned/test.get.parameters[?(@.name == 'size')].in").value("query"))
            .andExpect(jsonPath("$.paths./scanned/test.get.parameters[?(@.name == 'size')].schema.type").value("integer"))
            .andExpect(jsonPath("$.paths./scanned/test.get.parameters[?(@.name == 'sort')]").exists())
            .andExpect(jsonPath("$.paths./scanned/test.get.parameters[?(@.name == 'sort')].in").value("query"))
            .andExpect(jsonPath("$.paths./scanned/test.get.parameters[?(@.name == 'sort')].schema.type").value("array"))
            .andExpect(jsonPath("$.paths./scanned/test.get.parameters[?(@.name == 'sort')].schema.items.type").value("string"));
    }

    @Test
    void generatesManagementOAS() throws Exception {
        mockMvc.perform(get("/v3/api-docs?group=management"))
            .andExpect((status().isOk()))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.info.title").value("TestApp Management API"))
            .andExpect(jsonPath("$.info.description").value("Management endpoints documentation"))
            .andExpect(jsonPath("$.info.version").value("test version"))
            .andExpect(jsonPath("$.info.termsOfService").doesNotExist())
            .andExpect(jsonPath("$.info.contact").isEmpty())
            .andExpect(jsonPath("$.info.license").isEmpty())
            .andExpect(jsonPath("$.paths./management/health").exists())
            .andExpect(jsonPath("$.paths./scanned/test").doesNotExist())
            // TODO: fix bug in Springfox
            //.andExpect(jsonPath("$.servers.[*].url").value(hasItem("test server url")))
            .andExpect(jsonPath("$.servers.[*].url").value(hasItem("http://localhost:80")));
    }

    @Test
    void generatesManagementSwaggerV2() throws Exception {
        mockMvc.perform(get("/v2/api-docs?group=management"))
            .andExpect((status().isOk()))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.paths./management/health").exists())
            .andExpect(jsonPath("$.host").value("test.jhipster.com"))
            .andExpect(jsonPath("$.schemes").value(hasItems("http", "https")));    }

    @SpringBootApplication(
        scanBasePackages = "io.github.jhipster.config.apidoc",
        exclude = {
            SecurityAutoConfiguration.class,
            ManagementWebSecurityAutoConfiguration.class,
            DataSourceAutoConfiguration.class,
            DataSourceTransactionManagerAutoConfiguration.class,
            HibernateJpaAutoConfiguration.class
        })
    @Controller
    static class TestApp {
        @GetMapping("/scanned/test")
        public void scanned(Pageable pageable) {
        }

        @GetMapping("/not-scanned/test")
        public void notscanned(Pageable pageable) {
        }
    }

}


