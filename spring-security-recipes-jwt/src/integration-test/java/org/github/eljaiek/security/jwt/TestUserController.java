package org.github.eljaiek.security.jwt;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class TestUserController {

    @GetMapping("/me")
    @PreAuthorize("isFullyAuthenticated()")
    public UserResource getMe(Principal principal) {
        val auth = (Authentication) principal;
        return new UserResource(((User) auth.getPrincipal()).getUsername());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<UserResource> getUsers() {
        return Collections.emptyList();
    }

    @PostMapping("/owner")
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public void save() {
        log.info("User saved");
    }
}
