package io.github.jhipster.security.management;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import static io.github.jhipster.security.management.ApiKeyAuthenticationFilter.*;
import static org.assertj.core.api.Java6Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class ApiKeyAuthenticationFilterTest {
    private static final String MANAGEMENT_API_KEY = "management-api-key";
    private static final String testUri = "/testUri";

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockFilterChain filterChain;

    @InjectMocks
    private ApiKeyAuthenticationFilter apiKeyAuthenticationFilter = new ApiKeyAuthenticationFilter(MANAGEMENT_API_KEY);

    @Before
    public void setup() {
        request = new MockHttpServletRequest();
        request.setRequestURI(testUri);
        response = new MockHttpServletResponse();
        filterChain = new MockFilterChain();
    }

    @After
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void shouldNotSetAuthenticationWithoutApiKey() throws Exception {
        apiKeyAuthenticationFilter.doFilter(request, response, filterChain);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertThat(authentication).isEqualTo(null);
    }

    @Test
    public void shouldSetAuthenticationWithCorrectApiKey() throws Exception {
        request.addHeader(AUTHORIZATION_HEADER, MANAGEMENT_API_KEY);
        apiKeyAuthenticationFilter.doFilter(request, response, filterChain);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        assertThat(user.getUsername()).isEqualTo(MANAGER);
        assertThat(user.getAuthorities()).contains(MANAGEMENT_AUTHORITY);
    }

    @Test
    public void shouldNotSetAuthenticationWithIncorrectApiKey() throws Exception {
        request.addHeader(AUTHORIZATION_HEADER, "incorrect-api-key");
        apiKeyAuthenticationFilter.doFilter(request, response, filterChain);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertThat(authentication).isEqualTo(null);
    }
}
