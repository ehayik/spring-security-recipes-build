package org.github.eljaiek.security.jwt;

import org.springframework.security.core.AuthenticationException;

public class InvalidCredentialsFormatException extends AuthenticationException {

    public InvalidCredentialsFormatException() {
        super("Invalid credentials format. Correct format should be 'Basic user:password'.");
    }

    public InvalidCredentialsFormatException(String msg) {
        super(msg);
    }

    public InvalidCredentialsFormatException(String msg, Throwable t) {
        super(msg, t);
    }
}
