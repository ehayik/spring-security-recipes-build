package org.github.eljaiek.security.core;

import org.springframework.security.config.annotation.SecurityConfigurer;

import java.util.List;

public interface SecurityConfigurerProvider {

    List<SecurityConfigurer> getSecurityConfigurers();
}
