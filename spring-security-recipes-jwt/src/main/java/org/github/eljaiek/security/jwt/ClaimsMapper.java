package org.github.eljaiek.security.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

import java.util.Map;

public interface ClaimsMapper {

    Map<String, Object> asClaims(Authentication authentication);

    Authentication asAuthentication(Claims claims);
}
