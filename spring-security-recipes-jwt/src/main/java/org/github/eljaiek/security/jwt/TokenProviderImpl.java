package org.github.eljaiek.security.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
final class TokenProviderImpl implements TokenProvider {

    private final JWTSecurityProperties properties;
    private final ClaimsMapper claimsMapper;

    @Override
    public final String create(Authentication authentication) {
        return Jwts.builder()
                .setSubject(authentication.getName())
                .addClaims(claimsMapper.asClaims(authentication))
                .signWith(properties.getAlgorithm(), properties.getSecretKey())
                .setExpiration(calculateExpirationDate())
                .compact();
    }

    private Date calculateExpirationDate() {
        LocalDateTime expiration = LocalDateTime
                .now()
                .plusSeconds(properties.getExpiresIn());
        return Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public Optional<Authentication> validate(String jwt) {
        Optional<Authentication> auth = Optional.empty();

        if (StringUtils.hasText(jwt)) {
            val claims = parse(jwt);
            auth = Optional.ofNullable(claimsMapper.asAuthentication(claims));
        }

        return auth;
    }

    private Claims parse(String jwt) {
        Claims claims = null;

        try {
            claims = Jwts.parser()
                         .setSigningKey(properties.getSecretKey())
                         .parseClaimsJws(jwt)
                         .getBody();
        } catch (SignatureException e) {
            log.info("Invalid JWT signature.");
            log.trace("Invalid JWT signature trace: {}", e);
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
            log.trace("Invalid JWT token trace: {}", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            log.trace("Expired JWT token trace: {}", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: {}", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace: {}", e);
        }

        return claims;
    }


}
