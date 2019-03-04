/*
 * Copyright 2016-2017 the original author or authors from the JHipster project.
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

import com.fasterxml.classmate.TypeResolver;
import io.github.jhipster.test.LogbackRecorder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.plugin.core.SimplePluginRegistry;
import springfox.documentation.schema.JacksonEnumTypeDeterminer;
import springfox.documentation.schema.TypeNameExtractor;
import springfox.documentation.spi.schema.TypeNameProviderPlugin;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SwaggerPluginsAutoConfigurationTest {

    private SwaggerPluginsAutoConfiguration.SpringPagePluginConfiguration pagePluginConfig;
    private LogbackRecorder recorder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        pagePluginConfig = new SwaggerPluginsAutoConfiguration.SpringPagePluginConfiguration();

        recorder = LogbackRecorder.forClass(SwaggerAutoConfiguration.class).reset().capture("ALL");
    }

    @After
    public void teardown() {
        recorder.release();
    }

    @Test
    public void testPageableParameterBuilderPlugin() {
        TypeResolver resolver = new TypeResolver();
        List<TypeNameProviderPlugin> plugins = new LinkedList<>();
        TypeNameExtractor extractor = new TypeNameExtractor(resolver, SimplePluginRegistry.create(plugins), new JacksonEnumTypeDeterminer());
        PageableParameterBuilderPlugin plugin = pagePluginConfig.pageableParameterBuilderPlugin(extractor, resolver);
        assertThat(plugin.getResolver()).isEqualTo(resolver);
        assertThat(plugin.getNameExtractor()).isEqualTo(extractor);
    }
}
