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

package io.github.jhipster.config;

/**
 * JHipster constants.
 */
public interface JHipsterConstants {

    // Spring profiles for development, test and production, see https://www.jhipster.tech/profiles/
    /** Constant <code>SPRING_PROFILE_DEVELOPMENT="dev"</code> */
    String SPRING_PROFILE_DEVELOPMENT = "dev";
    /** Constant <code>SPRING_PROFILE_TEST="test"</code> */
    String SPRING_PROFILE_TEST = "test";
    /** Constant <code>SPRING_PROFILE_PRODUCTION="prod"</code> */
    String SPRING_PROFILE_PRODUCTION = "prod";
    /** Spring profile used when deploying with Spring Cloud (used when deploying to CloudFoundry)
        Constant <code>SPRING_PROFILE_CLOUD="cloud"</code> */
    String SPRING_PROFILE_CLOUD = "cloud";
    /** Spring profile used when deploying to Heroku
        Constant <code>SPRING_PROFILE_HEROKU="heroku"</code> */
    String SPRING_PROFILE_HEROKU = "heroku";
    /** Spring profile used when deploying to Amazon ECS
        Constant <code>SPRING_PROFILE_AWS_ECS="aws-ecs"</code> */
    String SPRING_PROFILE_AWS_ECS = "aws-ecs";
    /** Spring profile used when deploying to Microsoft Azure
     Constant <code>SPRING_PROFILE_AZURE="azure"</code> */
    String SPRING_PROFILE_AZURE = "azure";
    /** Spring profile used to enable OpenAPI doc generation
        Constant <code>SPRING_PROFILE_API_DOCS="api-docs"</code> */
    String SPRING_PROFILE_API_DOCS = "api-docs";
    /** Spring profile used to disable running liquibase
        Constant <code>SPRING_PROFILE_NO_LIQUIBASE="no-liquibase"</code> */
    String SPRING_PROFILE_NO_LIQUIBASE = "no-liquibase";
    /** Spring profile used when deploying to Kubernetes and OpenShift
        Constant <code>SPRING_PROFILE_K8S="k8s"</code> */
    String SPRING_PROFILE_K8S = "k8s";
}
