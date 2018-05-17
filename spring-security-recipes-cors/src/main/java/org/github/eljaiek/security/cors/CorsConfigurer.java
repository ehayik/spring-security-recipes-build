package org.github.eljaiek.security.cors;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@RequiredArgsConstructor
public class CorsConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final CorsFilter corsFilter;

    @Override
    public void configure(HttpSecurity http) {
        http.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
