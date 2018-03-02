package io.github.jhipster.security.ssl;

import io.github.jhipster.config.JHipsterProperties;
import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.test.util.ReflectionTestUtils;
import org.xnio.OptionMap;

import static org.assertj.core.api.Assertions.assertThat;

public class UndertowSSLConfigurationTest {

    private JHipsterProperties properties;

    @Before
    public void setup() {
        properties = new JHipsterProperties();
    }

    @Test
    public void testUndertowSSLConfigurationOK() {
        //Prepare
        UndertowServletWebServerFactory undertowServletWebServerFactory = new UndertowServletWebServerFactory();
        properties.getHttp().setUseUndertowUserCipherSuitesOrder(true);

        //Execute
        UndertowSSLConfiguration undertowSSLConfiguration = new UndertowSSLConfiguration(undertowServletWebServerFactory, properties);

        //Verify
        Undertow.Builder builder = Undertow.builder();
        undertowServletWebServerFactory.getBuilderCustomizers().forEach(c -> c.customize(builder));
        OptionMap.Builder serverOptions = (OptionMap.Builder) ReflectionTestUtils.getField(builder, "socketOptions");
        assertThat(undertowServletWebServerFactory).isNotNull();
        assertThat(serverOptions.getMap().get(UndertowOptions.SSL_USER_CIPHER_SUITES_ORDER)).isTrue();
    }

    @Test
    public void testUndertowSSLConfigurationKO() {
        //Prepare
        UndertowServletWebServerFactory undertowServletWebServerFactory = new UndertowServletWebServerFactory();
        properties.getHttp().setUseUndertowUserCipherSuitesOrder(false);

        //Execute
        UndertowSSLConfiguration undertowSSLConfiguration = new UndertowSSLConfiguration(undertowServletWebServerFactory, properties);

        //Verify
        Undertow.Builder builder = Undertow.builder();
        undertowServletWebServerFactory.getBuilderCustomizers().forEach(c -> c.customize(builder));
        OptionMap.Builder serverOptions = (OptionMap.Builder) ReflectionTestUtils.getField(builder, "socketOptions");
        assertThat(undertowServletWebServerFactory).isNotNull();
        assertThat(serverOptions.getMap().get(UndertowOptions.SSL_USER_CIPHER_SUITES_ORDER)).isNull();
    }

}
