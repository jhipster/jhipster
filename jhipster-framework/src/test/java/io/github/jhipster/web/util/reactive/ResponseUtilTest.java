package io.github.jhipster.web.util.reactive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ResponseUtilTest {

    private static final String HEADER_NAME = "X-Test";
    private static final String HEADER_VALUE = "FooBar";

    private Mono<Integer> monoYes;
    private Mono<Integer> monoNo;
    private HttpHeaders headers;

    @BeforeEach
    public void setup() {
        monoYes = Mono.just(42);
        monoNo = Mono.empty();
        headers = new HttpHeaders();
        headers.add(HEADER_NAME, HEADER_VALUE);
    }

    @Test
    public void testMonoYesWithoutHeaders() {
        ResponseEntity<Integer> response = ResponseUtil.wrapOrNotFound(monoYes).block();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(42);
        assertThat(response.getHeaders()).isEmpty();
    }

    @Test
    public void testMonoNoWithoutHeaders() {
        Mono<ResponseEntity<Integer>> response = ResponseUtil.wrapOrNotFound(monoNo);
        assertThatExceptionOfType(ResponseStatusException.class).isThrownBy(response::block);
    }

    @Test
    public void testMonoYesWithHeaders() {
        ResponseEntity<Integer> response = ResponseUtil.wrapOrNotFound(monoYes, headers).block();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(42);
        assertThat(response.getHeaders()).hasSize(1);
        assertThat(response.getHeaders().get(HEADER_NAME)).hasSize(1);
        assertThat(response.getHeaders().get(HEADER_NAME).get(0)).isEqualTo(HEADER_VALUE);
    }

    @Test
    public void testMonoNoWithHeaders() {
        Mono<ResponseEntity<Integer>> response = ResponseUtil.wrapOrNotFound(monoNo, headers);
        assertThatExceptionOfType(ResponseStatusException.class).isThrownBy(response::block);
    }

}
