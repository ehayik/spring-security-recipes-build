package org.github.eljaiek.security.cors;

import lombok.Data;
import lombok.val;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;
import java.util.List;

import static org.springframework.web.cors.CorsConfiguration.ALL;

@Data
@ConfigurationProperties("eljaiek.security.recipes.cors")
public final class CorsSecurityProperties {

    private boolean enabled = true;

    private List<String> paths = Collections.singletonList("/**");

    private List<String> allowedOrigins = Collections.singletonList(ALL);

    private List<String> allowedMethods = Collections.singletonList(ALL);

    private List<String> allowedHeaders = Collections.singletonList(ALL);

    private List<String> exposedHeaders;

    private boolean allowCredentials = true;

    private long maxAge = 1800;

    public CorsConfiguration getCorsConfiguration() {
        val config = new CorsConfiguration();
        config.setAllowedOrigins(allowedOrigins);
        config.setAllowedMethods(allowedMethods);
        config.setAllowedHeaders(allowedHeaders);
        config.setExposedHeaders(exposedHeaders);
        config.setMaxAge(maxAge);
        return config;
    }
}
