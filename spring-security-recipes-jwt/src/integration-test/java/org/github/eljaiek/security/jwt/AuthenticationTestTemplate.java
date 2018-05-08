package org.github.eljaiek.security.jwt;

import lombok.SneakyThrows;
import lombok.val;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.github.eljaiek.security.core.SecurityRecipeUtils.BASIC_PREFIX;
import static org.github.eljaiek.security.core.SecurityRecipeUtils.encode;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

abstract class AuthenticationTestTemplate {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private MockMvc mvc;

    protected abstract String getAuthenticationPath();

    @Test
    @SneakyThrows
    public void authenticationShouldReturn401() {
        mvc.perform(MockMvcRequestBuilders
                .post(getAuthenticationPath())).andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    @SneakyThrows
    @WithUserDetails
    public void authenticationShouldReturn200() {
        val credentials = BASIC_PREFIX + encode("user:password");
        mvc.perform(MockMvcRequestBuilders
                .post(getAuthenticationPath())
                .header(AUTHORIZATION, credentials))
                .andExpect(status().isOk());
    }
}
