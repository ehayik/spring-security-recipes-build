package org.github.eljaiek.security.basic;

import lombok.SneakyThrows;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.github.eljaiek.security.core.SecurityRecipeUtils.BASIC_PREFIX;
import static org.github.eljaiek.security.core.SecurityRecipeUtils.encode;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = TestApplication.class)
public class AuthenticationIT {

    @Autowired
    private MockMvc mvc;

    @Test
    @SneakyThrows
    public void authenticationShouldReturn403() {
        mvc.perform(get("/users/me"))
                .andExpect(unauthenticated());
    }

    @Test
    @SneakyThrows
    public void authenticationShouldFailWithoutCSRFToken() {
        val credentials = BASIC_PREFIX + encode("user:password");
        mvc.perform(get("/users/me")
                .header(AUTHORIZATION, credentials)
                .with(csrf()))
                .andExpect(unauthenticated());
    }

    @Test
    @SneakyThrows
    public void authenticationShouldFailWithInvalidCSRFToken() {
        val credentials = BASIC_PREFIX + encode("user:password");
        mvc.perform(get("/users/me")
                .header(AUTHORIZATION, credentials)
                .with(csrf().useInvalidToken()))
                .andExpect(unauthenticated());
    }

    @Test
    @SneakyThrows
    @WithUserDetails
    public void authenticationShouldReturn200() {
        val credentials = BASIC_PREFIX + encode("user:password");
        mvc.perform(get("/users/me")
                .header(AUTHORIZATION, credentials)
                .with(csrf()))
                .andExpect(authenticated());
    }
}
