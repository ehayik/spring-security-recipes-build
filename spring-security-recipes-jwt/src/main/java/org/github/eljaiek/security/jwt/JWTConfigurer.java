package org.github.eljaiek.security.jwt;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JWTConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final AuthenticationManager authenticationManager;

    private final AuthenticationMapper authenticationMapper;

    private final TokenProvider tokenProvider;

    private final JWTSecurityProperties properties;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .headers()
                .frameOptions()
                .disable()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(createJwtAuthenticationFilter())
                .addFilterBefore(new JWTAuthorizationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
    }

    @SneakyThrows
    private JWTAuthenticationFilter createJwtAuthenticationFilter() {
        val filter =  new JWTAuthenticationFilter(authenticationManager, authenticationMapper, tokenProvider);
        filter.setFilterProcessesUrl(properties.getLoginPath());
        return filter;
    }
}
