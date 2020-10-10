/*
 * Copyright 2016-2020 the original author or authors from the JHipster project.
 *
 * This file is part of the JHipster project, see https://www.jhipster.tech/
 * for more information.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.jhipster.config.apidoc;

import static io.github.jhipster.config.JHipsterConstants.SPRING_PROFILE_SWAGGER;
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

@SpringBootTest(
    classes = SwaggerAutoConfigurationTest.TestApp.class,
    properties = {
        "spring.liquibase.enabled=false",
        "security.basic.enabled=false",
        "jhipster.swagger.default-include-pattern=/scanned/.*",
        "jhipster.swagger.host=test.jhipster.com",
        "jhipster.swagger.protocols=http,https",
        "jhipster.swagger.title=test title",
        "jhipster.swagger.description=test description",
        "jhipster.swagger.version=test version",
        "jhipster.swagger.terms-of-service-url=test tos url",
        "jhipster.swagger.contact-name=test contact name",
        "jhipster.swagger.contact-email=test contact email",
        "jhipster.swagger.contact-url=test contact url",
        "jhipster.swagger.license=test license name",
        "jhipster.swagger.license-url=test license url",
        "management.endpoints.web.base-path=/management",
        "spring.application.name=testApp"

    })
@ActiveProfiles(SPRING_PROFILE_SWAGGER)
@AutoConfigureMockMvc
public class SwaggerAutoConfigurationTest {

    @Autowired
    private MockMvc mockMvc;

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
