package org.github.eljaiek.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.val;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public class ClaimsMapperAdapterTest {

    private final ClaimsMapperAdapter mapper = new ClaimsMapperAdapter(":");

    @Test
    public void asClaims() {
        val auth = createAuthentication();
        assertThat(mapper.asClaims(auth))
                .containsExactly(entry("auth", "ROLE_USER:ROLE_ADMIN"));
    }

    private Authentication createAuthentication() {
        val auth = new ArrayList<GrantedAuthority>(2);
        auth.add(new SimpleGrantedAuthority("ROLE_USER"));
        auth.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return new UsernamePasswordAuthenticationToken("user", "password", auth);
    }

    @Test
    public void asAuthentication() {
        val auth = mapper.asAuthentication(createClaims());
        assertThat(auth.getPrincipal()).isInstanceOf(UserDetails.class);
        val user = (User) auth.getPrincipal();
        assertThat(user).hasFieldOrPropertyWithValue("username", "user");
        assertThat(auth.getAuthorities()).hasSize(2);
    }

    private Claims createClaims() {
        val claims = new DefaultClaims();
        claims.setSubject("user");
        claims.put("auth", "ROLE_USER:ROLE_ADMIN");
        return claims;
    }
}