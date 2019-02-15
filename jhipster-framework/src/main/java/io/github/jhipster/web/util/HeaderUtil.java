package io.github.jhipster.web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

/**
 * Utility class for HTTP headers creation.
 */
public final class HeaderUtil {

    private static final Logger log = LoggerFactory.getLogger(HeaderUtil.class);

    public static final String APPLICATION_NAME = "JHIPSTER";

    private HeaderUtil() {
    }

    public static HttpHeaders createAlert(String message, String param) {
        return createAlert(APPLICATION_NAME, message, param);
    }

    public static HttpHeaders createAlert(String applicationName, String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-" + applicationName + "-alert", message);
        headers.add("X-" + applicationName + "-params", param);
        return headers;
    }

    public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
        return createEntityCreationAlert(APPLICATION_NAME, entityName, param);
    }

    public static HttpHeaders createEntityCreationAlert(String applicationName, String entityName, String param) {
        return createAlert(applicationName + "." + entityName + ".created", param);
    }

    public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
        return createEntityUpdateAlert(APPLICATION_NAME, entityName, param);
    }

    public static HttpHeaders createEntityUpdateAlert(String applicationName, String entityName, String param) {
        return createAlert(applicationName + "." + entityName + ".updated", param);
    }

    public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
        return createEntityDeletionAlert(APPLICATION_NAME, entityName, param);
    }

    public static HttpHeaders createEntityDeletionAlert(String applicationName, String entityName, String param) {
        return createAlert(applicationName + "." + entityName + ".deleted", param);
    }

    public static HttpHeaders createFailureAlert(String entityName, String errorKey, String defaultMessage) {
        return createFailureAlert(APPLICATION_NAME, entityName, errorKey, defaultMessage);
    }

    public static HttpHeaders createFailureAlert(String applicationName, String entityName, String errorKey, String defaultMessage) {
        log.error("Entity processing failed, {}", defaultMessage);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-" + applicationName + "-error", "error." + errorKey);
        headers.add("X-" + applicationName + "-params", entityName);
        return headers;
    }
}
