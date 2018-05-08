package org.github.eljaiek.security.jwt;

import lombok.SneakyThrows;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.github.eljaiek.security.core.SecurityRecipeUtils.BASIC_PREFIX;
import static org.github.eljaiek.security.core.SecurityRecipeUtils.encode;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
public class AuthorizationIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void authorizationShouldReturn404() {
        val response = restTemplate.getForEntity("/unknow", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void authorizationShouldPreAuthorizePrincipal() {
        val request = buildRequest();
        val response = restTemplate.exchange("/users/me", HttpMethod.GET, request, UserResource.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getUsername()).isEqualTo("user");
    }

    @SneakyThrows
    private HttpEntity buildRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION, BASIC_PREFIX + encode("user:password"));
        val request = new HttpEntity(headers);
        val response = restTemplate.postForEntity("/login", request, Object.class);
        val token = response.getHeaders().get(AUTHORIZATION).iterator().next();
        headers = new HttpHeaders();
        headers.add(AUTHORIZATION, token);
        return new HttpEntity(headers);
    }

    @Test
    public void authorizationShouldPreAuthorizeAuthority() {
        val request = buildRequest();
        val response = restTemplate.exchange("/users/me", HttpMethod.GET, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void authorizationShouldNotPreAuthorizeAuthority() {
        val request = buildRequest();
        val response = restTemplate.postForEntity("/users/owner", request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @SneakyThrows
    public void authorizationShouldExpire() {
        val request = buildRequest();
        Thread.sleep(6000);
        val response = restTemplate.exchange("/users/me", HttpMethod.GET, request, UserResource.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
