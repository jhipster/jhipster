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

package io.github.jhipster.config.apidoc.customizer;

import org.springframework.http.ResponseEntity;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.spring.web.plugins.Docket;

import java.nio.ByteBuffer;

/**
 * @author jearton
 * @since 2017/12/14
 */
public class BuildInSwaggerCustomizer implements SwaggerCustomizer {

    private final AlternateTypeRule[] alternateTypeRules;

    public BuildInSwaggerCustomizer(AlternateTypeRule... alternateTypeRules) {
        this.alternateTypeRules = alternateTypeRules;
    }

    @Override
    public void customize(Docket docket) {
        docket.forCodeGeneration(true)
            .directModelSubstitute(ByteBuffer.class, String.class)
            .genericModelSubstitutes(ResponseEntity.class)
            .alternateTypeRules(alternateTypeRules);
    }
}
