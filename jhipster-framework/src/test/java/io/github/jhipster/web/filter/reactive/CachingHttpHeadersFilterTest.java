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

package io.github.jhipster.web.filter.reactive;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CachingHttpHeadersFilterTest {

    private long ttl = TimeUnit.DAYS.toMillis(2);
    private CachingHttpHeadersFilter filter = new CachingHttpHeadersFilter(ttl);

    @Test
    public void cacheHeadersSetWhenPathMatches() {
        long now = System.currentTimeMillis();
        WebFilterChain filterChain = (filterExchange) -> {
            try {
                HttpHeaders headers = filterExchange.getResponse().getHeaders();
                assertEquals("cache", headers.getPragma());
                assertEquals("max-age=172800000, public", headers.getCacheControl());
                assertThat(headers.getExpires() - now).isBetween(ttl - 1000, ttl + 1000);
            } catch (AssertionError ex) {
                return Mono.error(ex);
            }
            return Mono.empty();
        };
        MockServerWebExchange exchange = MockServerWebExchange.from(
            MockServerHttpRequest.get("/app/foo")
        );
        this.filter.filter(exchange, filterChain).block();
    }

    @Test
    public void cacheHeadersNotSetWhenPathDoesntMatch() {
        WebFilterChain filterChain = (filterExchange) -> {
            try {
                HttpHeaders headers = filterExchange.getResponse().getHeaders();
                assertNull(headers.getPragma());
                assertNull(headers.getCacheControl());
                assertEquals(-1, headers.getExpires());
            } catch (AssertionError ex) {
                return Mono.error(ex);
            }
            return Mono.empty();
        };
        MockServerWebExchange exchange = MockServerWebExchange.from(
            MockServerHttpRequest.get("/foo/foo")
        );
        this.filter.filter(exchange, filterChain).block();
    }

}
