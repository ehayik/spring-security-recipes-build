package org.github.eljaiek.security.jwt;

import lombok.val;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.github.eljaiek.security.core.SecurityRecipeUtils.BASIC_PREFIX;
import static org.github.eljaiek.security.core.SecurityRecipeUtils.BEARER_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

final class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationMapper authenticationMapper;

    private final TokenProvider tokenProvider;

    JWTAuthenticationFilter(AuthenticationManager authenticationManager,
                            AuthenticationMapper authenticationMapper,
                            TokenProvider tokenProvider) {
        setAuthenticationManager(authenticationManager);
        this.authenticationMapper = authenticationMapper;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        val authHeader = request.getHeader(AUTHORIZATION);

        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith(BASIC_PREFIX)) {
            throw new BadCredentialsException("AUTHORIZATION header required");
        }

        val auth = authenticationMapper.asAuthentication(request);
        return getAuthenticationManager().authenticate(auth);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        val compactToken = tokenProvider.create(authResult);
        response.setHeader(AUTHORIZATION, BEARER_PREFIX + compactToken);
    }
}
