package org.github.eljaiek.security.cors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.eljaiek.security.core.SecurityConfigurerProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(CorsSecurityProperties.class)
public class CorsRecipeConfiguration {

    private final CorsSecurityProperties properties;

    @ConditionalOnProperty(
            prefix = "eljaiek.security.recipes.cors",
            name = "enabled",
            matchIfMissing = true,
            havingValue = "true"
    )
    @Bean
    public SecurityConfigurerProvider securityConfigurerProvider() {
        log.debug("Registering CORS security configurer provider");
        return () -> Collections.singletonList(new CorsConfigurer(properties));
    }
}
