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

package io.github.jhipster.web.filter.reactive;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.security.web.server.csrf.DefaultCsrfToken;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class CookieCsrfFilterTest {

    private static final String CSRF_COOKIE_NAME = "XSRF-TOKEN";
    private static final String TEST_URL = "http://domain1.com/test.html";

    private CookieCsrfFilter filter = new CookieCsrfFilter();

    @Test
    public void cookieSetInResponse() {
        final String token = "test_token";
        WebFilterChain filterChain = (filterExchange) -> {
            try {
                ResponseCookie cookie = filterExchange.getResponse().getCookies().getFirst(CSRF_COOKIE_NAME);
                assertThat(cookie).isNotNull();
                assertThat(cookie.getName()).isEqualTo(CSRF_COOKIE_NAME);
                assertThat(cookie.getValue()).isEqualTo(token);
                assertThat(cookie.getPath()).isEqualTo("/");
                assertThat(cookie.getMaxAge()).isEqualTo(Duration.ofSeconds(-1));
                assertThat(cookie.isHttpOnly()).isFalse();
                assertThat(cookie.isSecure()).isFalse();
            } catch (AssertionError ex) {
                return Mono.error(ex);
            }
            return Mono.empty();
        };
        MockServerWebExchange exchange = MockServerWebExchange.from(
            MockServerHttpRequest.post(TEST_URL)
        );
        exchange.getAttributes().put(CsrfToken.class.getName(), Mono.just(new DefaultCsrfToken(CSRF_COOKIE_NAME, "_csrf", token)));
        this.filter.filter(exchange, filterChain).block();
    }

    @Test
    public void cookieNotSetIfTokenInRequest() {
        WebFilterChain filterChain = (filterExchange) -> {
            try {
                assertThat(filterExchange.getResponse().getCookies().getFirst(CSRF_COOKIE_NAME)).isNull();
            } catch (AssertionError ex) {
                return Mono.error(ex);
            }
            return Mono.empty();
        };
        MockServerWebExchange exchange = MockServerWebExchange.from(
            MockServerHttpRequest
                .post(TEST_URL)
                .cookie(new HttpCookie(CSRF_COOKIE_NAME, "csrf_token"))
        );
        exchange.getAttributes().put(CsrfToken.class.getName(), Mono.just(new DefaultCsrfToken(CSRF_COOKIE_NAME, "_csrf", "some token")));
        this.filter.filter(exchange, filterChain).block();
    }

    @Test
    public void cookieNotSetIfNotInAttributes() {
        WebFilterChain filterChain = (filterExchange) -> {
            try {
                assertThat(filterExchange.getResponse().getCookies().getFirst(CSRF_COOKIE_NAME)).isNull();;
            } catch (AssertionError ex) {
                return Mono.error(ex);
            }
            return Mono.empty();
        };
        MockServerWebExchange exchange = MockServerWebExchange.from(
            MockServerHttpRequest.post(TEST_URL)
        );
        this.filter.filter(exchange, filterChain).block();
    }
}
