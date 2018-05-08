package org.github.eljaiek.security.jwt;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ClaimsMapperAdapter implements ClaimsMapper {

    private static final String AUTHORITIES_KEY = "auth";
    private final String claimSeparator;

    @Override
    public Map<String, Object> asClaims(Authentication authentication) {
        if (authentication == null) return null;
        val claims = new HashMap<String, Object>();
        putAuthorities(claims, authentication.getAuthorities());
        return claims;
    }

    protected void putAuthorities(Map<String, Object> claims, Collection<? extends GrantedAuthority> authorities) {
        val auth = authorities
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(claimSeparator));
        claims.put(AUTHORITIES_KEY, auth);
    }

    @Override
    public Authentication asAuthentication(Claims claims) {
        if (claims == null) return null;
        val authorities = getAuthorities(claims);
        val principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    protected Collection<? extends GrantedAuthority> getAuthorities(Claims claims) {
        return Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(claimSeparator))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
    }
}
