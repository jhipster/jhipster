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

package io.github.jhipster.config.info;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Arrays;
import java.util.List;

/**
 * An {@link InfoContributor} that exposes the list of active spring profiles.
 */
public class ActiveProfilesInfoContributor implements InfoContributor {

    private static final String ACTIVE_PROFILES = "activeProfiles";
    private final List<String> profiles;

    public ActiveProfilesInfoContributor(ConfigurableEnvironment environment) {
        this.profiles = Arrays.asList(environment.getActiveProfiles());
    }

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail(ACTIVE_PROFILES, this.profiles);
    }
}
