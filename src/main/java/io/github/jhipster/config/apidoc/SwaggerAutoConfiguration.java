/*
 * Copyright 2016-2017 the original author or authors from the JHipster project.
 *
 * This file is part of the JHipster project, see http://www.jhipster.tech/
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
import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.config.apidoc.customizer.JHipsterSwaggerCustomizer;
import io.github.jhipster.config.apidoc.customizer.SwaggerCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.DispatcherServlet;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.Servlet;
import java.nio.ByteBuffer;
import java.util.*;

import static io.github.jhipster.config.JHipsterConstants.SPRING_PROFILE_SWAGGER;
import static springfox.documentation.builders.PathSelectors.regex;
import static springfox.documentation.schema.AlternateTypeRules.DIRECT_SUBSTITUTION_RULE_ORDER;
import static springfox.documentation.schema.AlternateTypeRules.GENERIC_SUBSTITUTION_RULE_ORDER;

/**
 * Springfox Swagger configuration.
 * <p>
 * Warning! When having a lot of REST endpoints, Springfox can become a performance issue. In that case, you can use a
 * specific Spring profile for this class, so that only front-end developers have access to the Swagger view.
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass({
    ApiInfo.class,
    BeanValidatorPluginsConfiguration.class,
    Servlet.class,
    DispatcherServlet.class
})
@Profile(SPRING_PROFILE_SWAGGER)
@AutoConfigureAfter(JHipsterProperties.class)
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerAutoConfiguration {

    public static final String STARTING_MESSAGE = "Starting Swagger";
    public static final String STARTED_MESSAGE = "Started Swagger in {} ms";
    public static final String MANAGEMENT_TITLE_SUFFIX = "Management API";
    public static final String MANAGEMENT_GROUP_NAME = "management";
    public static final String MANAGEMENT_DESCRIPTION = "Management endpoints documentation";

    private final Logger log = LoggerFactory.getLogger(SwaggerAutoConfiguration.class);

    private final JHipsterProperties.Swagger properties;

    public SwaggerAutoConfiguration(JHipsterProperties jHipsterProperties) {
        this.properties = jHipsterProperties.getSwagger();
    }

    /**
     * Springfox configuration for the API Swagger docs.
     *
     * @return the Swagger Springfox configuration
     */
    @Bean
    @ConditionalOnMissingBean(name = "swaggerSpringfoxApiDocket")
    public Docket swaggerSpringfoxApiDocket(List<SwaggerCustomizer> swaggerCustomizers,
                                            List<AlternateTypeRule> alternateTypeRules) {
        log.debug(STARTING_MESSAGE);
        StopWatch watch = new StopWatch();
        watch.start();

        Docket docket = createDocket();

        // Apply all customizers
        swaggerCustomizers.forEach(customizer -> customizer.customize(docket));

        // Add AlternateTypeRules if available in spring bean factory.
        // Also you can add them in a customizer bean above.
        docket.alternateTypeRules(alternateTypeRules.toArray(new AlternateTypeRule[alternateTypeRules.size()]));

        watch.stop();
        log.debug(STARTED_MESSAGE, watch.getTotalTimeMillis());
        return docket;
    }

    /**
     * JHipster Swagger Customizer
     *
     * @return the Swagger Customizer of JHipster
     */
    @Bean
    public JHipsterSwaggerCustomizer jHipsterSwaggerCustomizer() {
        return new JHipsterSwaggerCustomizer(properties);
    }

    @Bean
    public AlternateTypeRule responseEntityAlternateTypeRule(TypeResolver typeResolver) {
        return AlternateTypeRules.newRule(
            typeResolver.resolve(ResponseEntity.class, WildcardType.class),
            typeResolver.resolve(WildcardType.class),
            GENERIC_SUBSTITUTION_RULE_ORDER);
    }

    @Bean
    public AlternateTypeRule byteBufferAlternateTypeRule(TypeResolver typeResolver) {
        return AlternateTypeRules.newRule(
            typeResolver.resolve(ByteBuffer.class),
            typeResolver.resolve(String.class),
            DIRECT_SUBSTITUTION_RULE_ORDER);
    }

    /**
     * Springfox configuration for the management endpoints (actuator) Swagger docs.
     *
     * @param appName               the application name
     * @param managementContextPath the path to access management endpoints
     * @return the Swagger Springfox configuration
     */
    @Bean
    @ConditionalOnClass(name = "org.springframework.boot.actuate.autoconfigure.ManagementServerProperties")
    @ConditionalOnProperty("management.context-path")
    @ConditionalOnExpression("'${management.context-path}'.length() > 0")
    @ConditionalOnMissingBean(name = "swaggerSpringfoxManagementDocket")
    public Docket swaggerSpringfoxManagementDocket(@Value("${spring.application.name:application}") String appName,
                                                   @Value("${management.context-path}") String managementContextPath) {
        ApiInfo apiInfo = new ApiInfo(
            StringUtils.capitalize(appName) + " " + MANAGEMENT_TITLE_SUFFIX,
            MANAGEMENT_DESCRIPTION,
            properties.getVersion(),
            "",
            ApiInfo.DEFAULT_CONTACT,
            "",
            "",
            new ArrayList<>()
        );

        return createDocket()
            .apiInfo(apiInfo)
            .groupName(MANAGEMENT_GROUP_NAME)
            .host(properties.getHost())
            .protocols(new HashSet<>(Arrays.asList(properties.getProtocols())))
            .forCodeGeneration(true)
            .directModelSubstitute(ByteBuffer.class, String.class)
            .genericModelSubstitutes(ResponseEntity.class)
            .select()
            .paths(regex(managementContextPath + ".*"))
            .build();
    }

    protected Docket createDocket() {
        return new Docket(DocumentationType.SWAGGER_2);
    }

}
