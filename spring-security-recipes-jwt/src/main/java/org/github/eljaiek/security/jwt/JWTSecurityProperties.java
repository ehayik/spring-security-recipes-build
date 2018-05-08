package org.github.eljaiek.security.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("eljaiek.security.recipes.jwt")
class JWTSecurityProperties {
    private String secretKey;
    private String claimSeparator = ":";
    private String loginPath = "/login";
    private long expiresIn = 3600;
    private SignatureAlgorithm algorithm = SignatureAlgorithm.HS512;
}
