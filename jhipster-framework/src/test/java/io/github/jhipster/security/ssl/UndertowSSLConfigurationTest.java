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

package io.github.jhipster.security.ssl;

import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import org.junit.Test;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.test.util.ReflectionTestUtils;
import org.xnio.OptionMap;

import static org.assertj.core.api.Assertions.assertThat;

public class UndertowSSLConfigurationTest {

    @Test
    public void testUndertowSSLConfigurationOK() {
        //Prepare
        UndertowServletWebServerFactory undertowServletWebServerFactory = new UndertowServletWebServerFactory();

        //Execute
        UndertowSSLConfiguration undertowSSLConfiguration = new UndertowSSLConfiguration(undertowServletWebServerFactory);

        //Verify
        Undertow.Builder builder = Undertow.builder();
        undertowServletWebServerFactory.getBuilderCustomizers().forEach(c -> c.customize(builder));
        OptionMap.Builder serverOptions = (OptionMap.Builder) ReflectionTestUtils.getField(builder, "socketOptions");
        assertThat(undertowServletWebServerFactory).isNotNull();
        assertThat(serverOptions.getMap().get(UndertowOptions.SSL_USER_CIPHER_SUITES_ORDER)).isTrue();
    }

}
