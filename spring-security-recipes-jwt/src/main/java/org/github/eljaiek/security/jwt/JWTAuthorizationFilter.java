package org.github.eljaiek.security.jwt;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import static org.github.eljaiek.security.core.SecurityRecipeUtils.BEARER_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
public class JWTAuthorizationFilter extends GenericFilterBean {

    private final TokenProvider tokenProvider;

    @Override
    @SneakyThrows
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) {
        val jwt = resolveToken((HttpServletRequest) request);
        tokenProvider.validate(jwt).ifPresent(auth -> SecurityContextHolder.getContext().setAuthentication(auth));
        filterChain.doFilter(request, response);
    }

    private static String resolveToken(HttpServletRequest request) {
        val bearerToken = request.getHeader(AUTHORIZATION);
        val isPresent = StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX);
        return isPresent ? bearerToken.replace(BEARER_PREFIX, "") : null;
    }
}
