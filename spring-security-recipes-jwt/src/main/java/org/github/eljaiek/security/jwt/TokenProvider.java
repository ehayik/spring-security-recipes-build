package org.github.eljaiek.security.jwt;

import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface TokenProvider {

    String create(Authentication authentication);

    Optional<Authentication> validate(String jwt);
}
