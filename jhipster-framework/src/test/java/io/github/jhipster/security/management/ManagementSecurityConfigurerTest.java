package io.github.jhipster.security.management;

import javax.servlet.Filter;

import io.github.jhipster.web.test.TestController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static io.github.jhipster.security.management.ApiKeyAuthenticationFilter.*;
import static io.github.jhipster.security.management.ManagementSecurityConfigurerTest.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestController.class)
public class ManagementSecurityConfigurerTest {

    static final String MANAGEMENT_API_KEY = "managementApiKey";

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity(new ApiKeyAuthenticationFilter(MANAGEMENT_API_KEY)))
            .build();
    }

    @Test
    public void shouldFailWithoutApiKey() throws Exception {
        mvc.perform(get("/management/test"))
            .andExpect(status().isOk());
    }

    @Test
    public void shouldPassWithCorrectApiKey() throws Exception {
        mvc.perform(get("/management/test").header(AUTHORIZATION_HEADER, MANAGEMENT_API_KEY))
            .andExpect(status().isOk());
    }
}
