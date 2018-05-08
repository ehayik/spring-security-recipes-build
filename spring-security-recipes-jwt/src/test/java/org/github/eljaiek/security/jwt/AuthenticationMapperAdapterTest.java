package org.github.eljaiek.security.jwt;

import lombok.val;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.github.eljaiek.security.core.SecurityRecipeUtils.BASIC_PREFIX;
import static org.github.eljaiek.security.core.SecurityRecipeUtils.encode;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthenticationMapperAdapterTest {

    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private final AuthenticationMapperAdapter authFactory = new AuthenticationMapperAdapter(":");

    @Mock
    private HttpServletRequest request;

    @Test
    public void asAuthentication() {
        val credentials = BASIC_PREFIX + encode(USER + ":" + PASSWORD);
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(credentials);
        val auth = authFactory.asAuthentication(request);
        assertThat(auth.getPrincipal())
                .isEqualTo(USER);
        assertThat(auth.getCredentials())
                .isEqualTo(PASSWORD);
    }

    @Test
    public void asAuthenticationThrowInvalidCredentialsFormatException() {
        val credentials = BASIC_PREFIX + encode(USER);
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(credentials);
        assertThatThrownBy(() -> authFactory.asAuthentication(request))
                .isExactlyInstanceOf(InvalidCredentialsFormatException.class);
    }
}