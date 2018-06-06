package org.github.eljaiek.security.jwt;

import lombok.*;
import org.github.eljaiek.security.core.SecurityRecipeUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

import static org.github.eljaiek.security.core.SecurityRecipeUtils.BASIC_PREFIX;

@RequiredArgsConstructor
public class AuthenticationMapperAdapter implements AuthenticationMapper {

    protected static final int USER_INDEX = 0;

    protected static final int PASSWORD_INDEX = 1;

    @Getter(AccessLevel.PROTECTED)
    private final String claimSeparator;

    @Override
    public Authentication asAuthentication(@NonNull HttpServletRequest request) {
        val header = request.getHeader(HttpHeaders.AUTHORIZATION);
        val credentials = header.replace(BASIC_PREFIX, "");
        val arr = decode(credentials).split(claimSeparator);
        assertCredentials(arr);
        val auth = new UsernamePasswordAuthenticationToken(
                arr[USER_INDEX],
                arr[PASSWORD_INDEX],
                Collections.emptyList()
        );

        auth.setDetails(new WebAuthenticationDetails(request));
        return auth;
    }

    protected String decode(String encodedAuth) {
        return SecurityRecipeUtils.decode(encodedAuth);
    }

    private void assertCredentials(String[] credentials) {
        if (credentials.length < 2) {
            throw new InvalidCredentialsFormatException();
        }
    }
}
