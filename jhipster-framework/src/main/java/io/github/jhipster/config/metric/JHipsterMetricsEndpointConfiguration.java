package io.github.jhipster.config.metric;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsEndpointAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>JHipsterMetricsEndpointConfiguration class.</p>
 */
@Configuration
@ConditionalOnClass(Timed.class)
@AutoConfigureAfter(MetricsEndpointAutoConfiguration.class)
public class JHipsterMetricsEndpointConfiguration {

    /**
     * <p>jHipsterMetricsEndpoint.</p>
     *
     * @param meterRegistry a {@link io.micrometer.core.instrument.MeterRegistry} object.
     * @return a {@link io.github.jhipster.config.metric.JHipsterMetricsEndpoint} object.
     */
    @Bean
    @ConditionalOnBean({MeterRegistry.class})
    @ConditionalOnMissingBean
    @ConditionalOnAvailableEndpoint
    public JHipsterMetricsEndpoint jHipsterMetricsEndpoint(MeterRegistry meterRegistry) {
        return new JHipsterMetricsEndpoint(meterRegistry);
    }
}
