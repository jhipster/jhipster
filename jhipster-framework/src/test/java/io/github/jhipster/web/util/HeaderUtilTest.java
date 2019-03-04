package io.github.jhipster.web.util;

import org.junit.Test;
import org.springframework.http.HttpHeaders;

import static org.junit.Assert.assertEquals;

public class HeaderUtilTest {

    @Test
    public void createAlert() {
        String message = "any.message";
        String param = "24";

        HttpHeaders headers = HeaderUtil.createAlert("myApp", message, param);
        assertEquals(message, headers.getFirst("X-myApp-alert"));
        assertEquals(param, headers.getFirst("X-myApp-params"));
    }

    @Test
    public void createEntityCreationAlertWithTranslation() {
        HttpHeaders headers = HeaderUtil.createEntityCreationAlert("myApp", true, "User", "2");
        assertEquals("myApp.User.created", headers.getFirst("X-myApp-alert"));
        assertEquals("2", headers.getFirst("X-myApp-params"));
    }

    @Test
    public void createEntityCreationAlertNoTranslation() {
        HttpHeaders headers = HeaderUtil.createEntityCreationAlert("myApp", false, "User", "2");
        assertEquals("A new User is created with identifier 2", headers.getFirst("X-myApp-alert"));
        assertEquals("2", headers.getFirst("X-myApp-params"));
    }

    @Test
    public void createEntityUpdateAlertWithTranslation() {
        HttpHeaders headers = HeaderUtil.createEntityUpdateAlert("myApp", true, "User", "2");
        assertEquals("myApp.User.updated", headers.getFirst("X-myApp-alert"));
        assertEquals("2", headers.getFirst("X-myApp-params"));
    }

    @Test
    public void createEntityUpdateAlertNoTranslation() {
        HttpHeaders headers = HeaderUtil.createEntityUpdateAlert("myApp", false, "User", "2");
        assertEquals("A User is updated with identifier 2", headers.getFirst("X-myApp-alert"));
        assertEquals("2", headers.getFirst("X-myApp-params"));
    }

    @Test
    public void createEntityDeletionAlertWithTranslation() {
        HttpHeaders headers = HeaderUtil.createEntityDeletionAlert("myApp", true, "User", "2");
        assertEquals("myApp.User.deleted", headers.getFirst("X-myApp-alert"));
        assertEquals("2", headers.getFirst("X-myApp-params"));
    }

    @Test
    public void createEntityDeletionAlertNoTranslation() {
        HttpHeaders headers = HeaderUtil.createEntityDeletionAlert("myApp", false, "User", "2");
        assertEquals("A User is deleted with identifier 2", headers.getFirst("X-myApp-alert"));
        assertEquals("2", headers.getFirst("X-myApp-params"));
    }

    @Test
    public void createFailureAlertWithTranslation() {
        HttpHeaders headers = HeaderUtil.createFailureAlert("myApp", true, "User", "404", "Failed to find user");
        assertEquals("error.404", headers.getFirst("X-myApp-error"));
        assertEquals("User", headers.getFirst("X-myApp-params"));
    }

    @Test
    public void createFailureAlertNoTranslation() {
        HttpHeaders headers = HeaderUtil.createFailureAlert("myApp", false, "User", "404", "Failed to find user");
        assertEquals("Failed to find user", headers.getFirst("X-myApp-error"));
        assertEquals("User", headers.getFirst("X-myApp-params"));
    }
}
