package org.github.eljaiek.security.jwt;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationMapper {

    Authentication asAuthentication(HttpServletRequest request);
}
