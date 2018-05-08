package org.github.eljaiek.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class ExtensionsConfigurer {

    private final JWTSecurityProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationMapper authenticationMapper() {
        return new AuthenticationMapperAdapter(properties.getClaimSeparator());
    }

    @Bean
    @ConditionalOnMissingBean
    public ClaimsMapper claimsMapper() {
        return new ClaimsMapperAdapter(properties.getClaimSeparator());
    }

    @Bean
    @ConditionalOnMissingBean
    public TokenProvider tokenProvider(ClaimsMapper claimsMapper) {
        return new TokenProviderImpl(properties, claimsMapper);
    }
}
