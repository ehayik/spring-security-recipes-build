package org.github.eljaiek.security.core;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Import(SecurityProblemSupport.class)
@ComponentScan("org.github.eljaiek.security.core")
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityRecipesConfigurer extends WebSecurityConfigurerAdapter {

    private final ApplicationContext context;

    private final SecurityProblemSupport problemSupport;

    @Override
    @SneakyThrows
    protected void configure(HttpSecurity http) {
        http.exceptionHandling()
                .authenticationEntryPoint(problemSupport)
                .accessDeniedHandler(problemSupport);
        applyAll(http);
    }

    private void applyAll(HttpSecurity http) {
        val providers = context.getBeansOfType(SecurityConfigurerProvider.class).values();
        providers.forEach(provider -> applyAll(http, provider.getSecurityConfigurers()));
    }

    @SneakyThrows
    private void applyAll(HttpSecurity http, List<SecurityConfigurer> configurers) {
        for (SecurityConfigurer configurer : configurers) {
            configurer.configure(http);
        }
    }
}
