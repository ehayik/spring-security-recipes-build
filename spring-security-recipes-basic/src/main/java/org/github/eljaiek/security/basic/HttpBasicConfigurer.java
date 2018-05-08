package org.github.eljaiek.security.basic;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

public class HttpBasicConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Override
    public void configure(HttpSecurity http) throws Exception {
       http.httpBasic()
               .and()
               .logout()
               .and()
               .csrf()
               .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }
}
