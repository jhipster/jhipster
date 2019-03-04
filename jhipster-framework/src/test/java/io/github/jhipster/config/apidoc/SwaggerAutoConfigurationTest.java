/*
 * Copyright 2016-2019 the original author or authors from the JHipster project.
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

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.config.JHipsterProperties.Swagger;
import io.github.jhipster.config.apidoc.customizer.JHipsterSwaggerCustomizer;
import io.github.jhipster.config.apidoc.customizer.SwaggerCustomizer;
import io.github.jhipster.test.LogbackRecorder;
import io.github.jhipster.test.LogbackRecorder.Event;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.Nullable;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class SwaggerAutoConfigurationTest {

    private Swagger properties;
    private SwaggerAutoConfiguration config;
    private ApiSelectorBuilder builder;
    private LogbackRecorder recorder;

    @Captor
    private ArgumentCaptor<ApiInfo> infoCaptor;

    @Captor
    private ArgumentCaptor<Predicate<String>> pathsCaptor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        final JHipsterProperties jHipsterProperties = new JHipsterProperties();
        properties = jHipsterProperties.getSwagger();
        properties.setHost("test.host.org");
        properties.setProtocols(new String[]{"http", "https"});
        properties.setTitle("test title");
        properties.setDescription("test description");
        properties.setVersion("6.6.6");
        properties.setTermsOfServiceUrl("http://test.host.org/terms");
        properties.setContactName("test contact");
        properties.setContactEmail("test@host.org");
        properties.setContactUrl("http://test.host.org/contact");
        properties.setLicense("free as in beer");
        properties.setLicenseUrl("http://test.host.org/license");
        properties.setUseDefaultResponseMessages(false);

        config = new SwaggerAutoConfiguration(jHipsterProperties) {
            @Override
            protected Docket createDocket() {
                Docket docket = spy(super.createDocket());
                when(docket.select()).thenReturn(builder = spy(new ApiSelectorBuilder(docket)));
                return docket;
            }
        };

        recorder = LogbackRecorder.forClass(SwaggerAutoConfiguration.class).reset().capture("ALL");
    }

    @After
    public void teardown() {
        recorder.release();
    }

    @Test
    public void testSwaggerSpringfoxApiDocket() {
        List<SwaggerCustomizer> customizers = Lists.newArrayList(new JHipsterSwaggerCustomizer(properties));
        Docket docket = config.swaggerSpringfoxApiDocket(customizers, new NullProvider<>());

        verify(docket, never()).groupName(anyString());
        verify(docket).host(properties.getHost());
        verify(docket).protocols(new HashSet<>(Arrays.asList(properties.getProtocols())));

        verify(docket).apiInfo(infoCaptor.capture());
        ApiInfo info = infoCaptor.getValue();
        assertThat(info.getTitle()).isEqualTo(properties.getTitle());
        assertThat(info.getDescription()).isEqualTo(properties.getDescription());
        assertThat(info.getVersion()).isEqualTo(properties.getVersion());
        assertThat(info.getTermsOfServiceUrl()).isEqualTo(properties.getTermsOfServiceUrl());
        assertThat(info.getContact().getName()).isEqualTo(properties.getContactName());
        assertThat(info.getContact().getEmail()).isEqualTo(properties.getContactEmail());
        assertThat(info.getContact().getUrl()).isEqualTo(properties.getContactUrl());
        assertThat(info.getLicense()).isEqualTo(properties.getLicense());
        assertThat(info.getLicenseUrl()).isEqualTo(properties.getLicenseUrl());
        assertThat(info.getVendorExtensions()).isEmpty();

        verify(docket).useDefaultResponseMessages(properties.isUseDefaultResponseMessages());
        verify(docket).forCodeGeneration(true);
        verify(docket).directModelSubstitute(ByteBuffer.class, String.class);
        verify(docket).genericModelSubstitutes(ResponseEntity.class);

        verify(docket).select();
        verify(builder).paths(pathsCaptor.capture());
        Predicate<String> paths = pathsCaptor.getValue();
        assertThat(paths.apply("/api/foo")).isEqualTo(true);
        assertThat(paths.apply("/foo/api")).isEqualTo(false);

        verify(builder).build();

        List<Event> events = recorder.play();
        assertThat(events).hasSize(2);

        Event event0 = events.get(0);
        assertThat(event0.getLevel()).isEqualTo("DEBUG");
        assertThat(event0.getMessage()).isEqualTo(SwaggerAutoConfiguration.STARTING_MESSAGE);
        assertThat(event0.getThrown()).isNull();

        Event event1 = events.get(1);
        assertThat(event1.getLevel()).isEqualTo("DEBUG");
        assertThat(event1.getMessage()).isEqualTo(SwaggerAutoConfiguration.STARTED_MESSAGE);
        assertThat(event1.getThrown()).isNull();
    }

    @Test
    public void testSwaggerSpringfoxManagementDocket() {
        Docket docket = config.swaggerSpringfoxManagementDocket(properties.getTitle(), "/foo/");

        verify(docket).groupName(SwaggerAutoConfiguration.MANAGEMENT_GROUP_NAME);
        verify(docket).host(properties.getHost());
        verify(docket).protocols(new HashSet<>(Arrays.asList(properties.getProtocols())));

        verify(docket).apiInfo(infoCaptor.capture());
        ApiInfo info = infoCaptor.getValue();
        assertThat(info.getTitle()).isEqualTo(StringUtils.capitalize(properties.getTitle()) + " " +
            SwaggerAutoConfiguration.MANAGEMENT_TITLE_SUFFIX);
        assertThat(info.getDescription()).isEqualTo(SwaggerAutoConfiguration.MANAGEMENT_DESCRIPTION);
        assertThat(info.getVersion()).isEqualTo(properties.getVersion());
        assertThat(info.getTermsOfServiceUrl()).isEqualTo("");
        assertThat(info.getContact().getName()).isEqualTo(ApiInfo.DEFAULT_CONTACT.getName());
        assertThat(info.getContact().getEmail()).isEqualTo(ApiInfo.DEFAULT_CONTACT.getEmail());
        assertThat(info.getContact().getUrl()).isEqualTo(ApiInfo.DEFAULT_CONTACT.getUrl());
        assertThat(info.getLicense()).isEqualTo("");
        assertThat(info.getLicenseUrl()).isEqualTo("");
        assertThat(info.getVendorExtensions()).isEmpty();

        verify(docket).useDefaultResponseMessages(properties.isUseDefaultResponseMessages());
        verify(docket).forCodeGeneration(true);
        verify(docket).directModelSubstitute(ByteBuffer.class, String.class);
        verify(docket).genericModelSubstitutes(ResponseEntity.class);

        verify(docket).select();
        verify(builder).paths(pathsCaptor.capture());
        Predicate<String> paths = pathsCaptor.getValue();
        assertThat(paths.apply("/api/foo")).isEqualTo(false);
        assertThat(paths.apply("/foo/api")).isEqualTo(true);

        verify(builder).build();
    }

    static class NullProvider<T> implements ObjectProvider<T> {

        @Nullable
        @Override
        public T getObject(Object... args) throws BeansException {
            return null;
        }

        @Nullable
        @Override
        public T getIfAvailable() throws BeansException {
            return null;
        }

        @Nullable
        @Override
        public T getIfUnique() throws BeansException {
            return null;
        }

        @Nullable
        @Override
        public T getObject() throws BeansException {
            return null;
        }
    }
}
