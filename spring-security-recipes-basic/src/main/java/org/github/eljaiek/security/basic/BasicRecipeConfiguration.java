package org.github.eljaiek.security.basic;

import org.github.eljaiek.security.core.SecurityConfigurerProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurer;

import java.util.Collections;
import java.util.List;

@Configuration
public class BasicRecipeConfiguration implements SecurityConfigurerProvider {

    @Override
    public List<SecurityConfigurer> getSecurityConfigurers() {
        return Collections.singletonList(new HttpBasicConfigurer());
    }
}
