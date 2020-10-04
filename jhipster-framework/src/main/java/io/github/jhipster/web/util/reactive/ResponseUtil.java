package io.github.jhipster.web.util.reactive;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

/**
 * Utility class for ResponseEntity creation in a reactive context.
 */
public interface ResponseUtil {
    /**
     * Wrap the mono into a {@link org.springframework.http.ResponseEntity} with an {@link org.springframework.http.HttpStatus#OK} status, or if it's empty, it
     * returns a {@link org.springframework.http.ResponseEntity} with {@link org.springframework.http.HttpStatus#NOT_FOUND}.
     *
     * @param <X>           type of the response
     * @param maybeResponse response to return if present
     * @return response containing {@code maybeResponse} if present or {@link org.springframework.http.HttpStatus#NOT_FOUND}
     */
    static <X> Mono<ResponseEntity<X>> wrapOrNotFound(Mono<X> maybeResponse) {
        return wrapOrNotFound(maybeResponse, null);
    }

    /**
     * Wrap the mono into a {@link org.springframework.http.ResponseEntity} with an {@link org.springframework.http.HttpStatus#OK} status with the headers, or if it's
     * empty, throws a {@link org.springframework.web.server.ResponseStatusException} with status {@link org.springframework.http.HttpStatus#NOT_FOUND}.
     *
     * @param <X>           type of the response
     * @param maybeResponse response to return if present
     * @param headers        headers to be added to the response
     * @return response containing {@code maybeResponse} if present or {@link org.springframework.http.HttpStatus#NOT_FOUND}
     */
    static <X> Mono<ResponseEntity<X>> wrapOrNotFound(Mono<X> maybeResponse, HttpHeaders headers) {
        return maybeResponse
            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
            .map(response -> ResponseEntity.ok().headers(headers).body(response));
    }
}
