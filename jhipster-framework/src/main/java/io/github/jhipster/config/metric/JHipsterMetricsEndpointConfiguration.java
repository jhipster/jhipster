package io.github.jhipster.config.metric;

import io.github.jhipster.config.JHipsterProperties;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsEndpointAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(Timed.class)
@AutoConfigureAfter(MetricsEndpointAutoConfiguration.class)
public class JHipsterMetricsEndpointConfiguration {

    @Bean
    @ConditionalOnBean({ MeterRegistry.class, JHipsterProperties.class })
    @ConditionalOnMissingBean
    @ConditionalOnEnabledEndpoint
    public JHipsterMetricsEndpoint jHipsterMetricsEndpoint(MeterRegistry meterRegistry, JHipsterProperties jHipsterProperties) {
        return new JHipsterMetricsEndpoint(meterRegistry, jHipsterProperties);
    }
}
