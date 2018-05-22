package org.github.eljaiek.security.cors;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Slf4j
@RequiredArgsConstructor
public class CorsConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final CorsSecurityProperties properties;

    @Override
    @SneakyThrows
    public void configure(HttpSecurity http) {
        val source = new UrlBasedCorsConfigurationSource();
        val config = properties.getCorsConfiguration();

        if (config.getAllowedOrigins() != null && !config.getAllowedOrigins().isEmpty()) {
            log.debug("Registering CORS filter");
            properties.getPaths().forEach(path -> source.registerCorsConfiguration(path, config));
        }

        http.cors().configurationSource(source);
    }
}
