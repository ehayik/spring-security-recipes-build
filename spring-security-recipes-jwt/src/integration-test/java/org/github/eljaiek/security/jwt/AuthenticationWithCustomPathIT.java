package org.github.eljaiek.security.jwt;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("custom-path")
public class AuthenticationWithCustomPathIT extends AuthenticationTestTemplate {

    @Override
    protected String getAuthenticationPath() {
        return "/auth";
    }
}
