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

package io.github.jhipster.security.uaa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * <p>LoadBalancedResourceDetails class.</p>
 */
@ConditionalOnMissingBean
public class LoadBalancedResourceDetails extends ClientCredentialsResourceDetails {

    /** Constant <code>EXCEPTION_MESSAGE="Returning an invalid URI: {}"</code> */
    public static final String EXCEPTION_MESSAGE = "Returning an invalid URI: {}";

    private final Logger log = LoggerFactory.getLogger(LoadBalancedResourceDetails.class);

    private String tokenServiceId;

    private LoadBalancerClient loadBalancerClient;

    /**
     * <p>Constructor for LoadBalancedResourceDetails.</p>
     *
     * @param loadBalancerClient a {@link org.springframework.cloud.client.loadbalancer.LoadBalancerClient} object.
     */
    public LoadBalancedResourceDetails(LoadBalancerClient loadBalancerClient) {
        this.loadBalancerClient = loadBalancerClient;
    }

    /** {@inheritDoc} */
    @Override
    public String getAccessTokenUri() {
        if (loadBalancerClient != null && tokenServiceId != null && !tokenServiceId.isEmpty()) {
            try {
                return loadBalancerClient.reconstructURI(
                    loadBalancerClient.choose(tokenServiceId),
                    new URI(super.getAccessTokenUri())
                ).toString();
            } catch (URISyntaxException e) {
                log.error(EXCEPTION_MESSAGE, e.getMessage());

                return super.getAccessTokenUri();
            }
        } else {
            return super.getAccessTokenUri();
        }
    }

    /**
     * <p>Getter for the field <code>tokenServiceId</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getTokenServiceId() {
        return this.tokenServiceId;
    }

    /**
     * <p>Setter for the field <code>tokenServiceId</code>.</p>
     *
     * @param tokenServiceId a {@link java.lang.String} object.
     */
    public void setTokenServiceId(String tokenServiceId) {
        this.tokenServiceId = tokenServiceId;
    }
}
