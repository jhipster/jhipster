package io.github.jhipster.web.util;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import static org.assertj.core.api.Assertions.assertThat;

public class HeaderUtilTest {

    @Test
    public void createAlert() {
        String message = "any.message";
        String param = "24";

        HttpHeaders headers = HeaderUtil.createAlert("myApp", message, param);
        assertThat(headers.getFirst("X-myApp-alert")).isEqualTo(message);
        assertThat(headers.getFirst("X-myApp-params")).isEqualTo(param);
    }

    @Test
    public void createEntityCreationAlertWithTranslation() {
        HttpHeaders headers = HeaderUtil.createEntityCreationAlert("myApp", true, "User", "2");
        assertThat(headers.getFirst("X-myApp-alert")).isEqualTo("myApp.User.created");
        assertThat(headers.getFirst("X-myApp-params")).isEqualTo("2");
    }

    @Test
    public void createEntityCreationAlertNoTranslation() {
        HttpHeaders headers = HeaderUtil.createEntityCreationAlert("myApp", false, "User", "2");
        assertThat(headers.getFirst("X-myApp-alert")).isEqualTo("A new User is created with identifier 2");
        assertThat(headers.getFirst("X-myApp-params")).isEqualTo("2");
    }

    @Test
    public void createEntityUpdateAlertWithTranslation() {
        HttpHeaders headers = HeaderUtil.createEntityUpdateAlert("myApp", true, "User", "2");
        assertThat(headers.getFirst("X-myApp-alert")).isEqualTo("myApp.User.updated");
        assertThat(headers.getFirst("X-myApp-params")).isEqualTo("2");
    }

    @Test
    public void createEntityUpdateAlertNoTranslation() {
        HttpHeaders headers = HeaderUtil.createEntityUpdateAlert("myApp", false, "User", "2");
        assertThat(headers.getFirst("X-myApp-alert")).isEqualTo("A User is updated with identifier 2");
        assertThat(headers.getFirst("X-myApp-params")).isEqualTo("2");
    }

    @Test
    public void createEntityDeletionAlertWithTranslation() {
        HttpHeaders headers = HeaderUtil.createEntityDeletionAlert("myApp", true, "User", "2");
        assertThat(headers.getFirst("X-myApp-alert")).isEqualTo("myApp.User.deleted");
        assertThat(headers.getFirst("X-myApp-params")).isEqualTo("2");
    }

    @Test
    public void createEntityDeletionAlertNoTranslation() {
        HttpHeaders headers = HeaderUtil.createEntityDeletionAlert("myApp", false, "User", "2");
        assertThat(headers.getFirst("X-myApp-alert")).isEqualTo("A User is deleted with identifier 2");
        assertThat(headers.getFirst("X-myApp-params")).isEqualTo("2");
    }

    @Test
    public void createFailureAlertWithTranslation() {
        HttpHeaders headers = HeaderUtil.createFailureAlert("myApp", true, "User", "404", "Failed to find user");
        assertThat(headers.getFirst("X-myApp-error")).isEqualTo("error.404");
        assertThat(headers.getFirst("X-myApp-params")).isEqualTo("User");
    }

    @Test
    public void createFailureAlertNoTranslation() {
        HttpHeaders headers = HeaderUtil.createFailureAlert("myApp", false, "User", "404", "Failed to find user");
        assertThat(headers.getFirst("X-myApp-error")).isEqualTo("Failed to find user");
        assertThat(headers.getFirst("X-myApp-params")).isEqualTo("User");
    }
}
