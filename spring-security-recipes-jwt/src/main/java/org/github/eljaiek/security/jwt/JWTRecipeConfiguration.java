package org.github.eljaiek.security.jwt;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.github.eljaiek.security.core.SecurityConfigurerProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurer;

import java.util.Collections;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Import(ExtensionsConfigurer.class)
@EnableConfigurationProperties(JWTSecurityProperties.class)
public class JWTRecipeConfiguration implements SecurityConfigurerProvider {

    private final AuthenticationManager authenticationManager;

    private final AuthenticationMapper authenticationMapper;

    private final TokenProvider tokenProvider;

    private final JWTSecurityProperties properties;


    @Override
    public List<SecurityConfigurer> getSecurityConfigurers() {
        val configurer = new JWTConfigurer(authenticationManager, authenticationMapper,
                tokenProvider, properties);
        return Collections.singletonList(configurer);
    }
}
