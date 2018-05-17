package org.github.eljaiek.security.cors;

import org.github.eljaiek.security.core.SecurityRecipesConfigurer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(SecurityRecipesConfigurer.class)
public class TestApplication {

}
