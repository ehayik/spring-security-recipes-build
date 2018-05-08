package org.github.eljaiek.security.basic;

import lombok.val;
import org.github.eljaiek.security.core.SecurityRecipesConfigurer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Collections;

@SpringBootApplication
@Import(SecurityRecipesConfigurer.class)
public class TestApplication {

    @Bean
    public UserDetailsService userDetailsService(){
        val authority = new SimpleGrantedAuthority("ROLE_USER");
        val userDetails = new User("user", "password", Collections.singletonList(authority));
        return new InMemoryUserDetailsManager(Collections.singletonList(userDetails));
    }
}
