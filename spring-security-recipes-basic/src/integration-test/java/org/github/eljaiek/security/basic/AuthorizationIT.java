package org.github.eljaiek.security.basic;

import lombok.SneakyThrows;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletResponse;

import static org.github.eljaiek.security.core.SecurityRecipeUtils.BASIC_PREFIX;
import static org.github.eljaiek.security.core.SecurityRecipeUtils.encode;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = TestApplication.class)
public class AuthorizationIT {

    @Autowired
    private MockMvc mvc;

    @Test
    @SneakyThrows
    public void authorizationShouldReturn404() {
        mvc.perform(get("/unknow"))
                .andExpect(status().isNotFound());
    }

    @SneakyThrows
    private HttpServletResponse autenticate() {
        val credentials = BASIC_PREFIX + encode("user:password");
        return mvc.perform(get("/users/me")
                .header(AUTHORIZATION, credentials)
                .with(csrf())).andReturn().getResponse();
    }

}
