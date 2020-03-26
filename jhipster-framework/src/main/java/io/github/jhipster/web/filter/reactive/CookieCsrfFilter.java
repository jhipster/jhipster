package io.github.jhipster.web.filter.reactive;

import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;

/**
 * <p>CookieCsrfFilter class.</p>
 */
public class CookieCsrfFilter implements WebFilter {

    private static final String CSRF_COOKIE_NAME = "XSRF-TOKEN";

    /** {@inheritDoc} */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (exchange.getRequest().getCookies().get(CSRF_COOKIE_NAME) != null) {
            return chain.filter(exchange);
        }
        return Mono.just(exchange)
            .publishOn(Schedulers.boundedElastic())
            .flatMap(it -> it.getAttributeOrDefault(CsrfToken.class.getName(), Mono.<CsrfToken>empty()))
            .doOnNext(token -> {
                ResponseCookie cookie = ResponseCookie.from(CSRF_COOKIE_NAME, token.getToken())
                    .maxAge(-1)
                    .httpOnly(false)
                    .path(getRequestContext(exchange.getRequest()))
                    .secure(Optional.ofNullable(exchange.getRequest().getSslInfo()).isPresent())
                    .build();
                exchange.getResponse().getCookies().add(CSRF_COOKIE_NAME, cookie);
            })
            .then(Mono.defer(() -> chain.filter(exchange)));
    }

    private String getRequestContext(ServerHttpRequest request) {
        String contextPath = request.getPath().contextPath().value();
        return StringUtils.hasLength(contextPath) ? contextPath : "/";
    }
}

