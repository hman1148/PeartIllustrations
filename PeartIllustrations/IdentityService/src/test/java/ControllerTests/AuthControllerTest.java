// File: IdentityService/src/test/java/filters/LoginFilterTest.java
package ControllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import filters.LoginFilter;
import models.LoginRequestResponse.LoginRequest;
import models.User.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import services.JwtUtil;
import services.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class LoginFilterTest {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private UserService userService;
    private LoginFilter loginFilter;

    @BeforeEach
    void setUp() {
        authenticationManager = Mockito.mock(AuthenticationManager.class);
        jwtUtil = Mockito.mock(JwtUtil.class);
        userService = Mockito.mock(UserService.class);
        loginFilter = new LoginFilter(authenticationManager, jwtUtil, userService);
    }

    @Test
    void successfulAuthentication_ReturnsTokenAndUser() throws Exception {
        // Arrange
        Long userId = 1L;
        String username = "user";
        String password = "pass";
        String token = "jwt-token";
        String email = "test@test.com";
        String role = "USER";
        String stripeCustomerId = "stripe-customer-id";
        User user = new User(
                userId,
                username,
                password,
                email,
                stripeCustomerId,
                role,
                true
        );
        user.setUsername(username);

        LoginRequest loginRequest = new LoginRequest("user", "pass");
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);

        Authentication auth = new UsernamePasswordAuthenticationToken(username, password);

        Mockito.when(authenticationManager.authenticate(any())).thenReturn(auth);
        Mockito.when(jwtUtil.generateToken(username)).thenReturn(token);
        Mockito.when(userService.findByUsername(username)).thenReturn(user);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContentType("application/json");
        request.setContent(new ObjectMapper().writeValueAsBytes(loginRequest));
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Act
        Authentication result = loginFilter.attemptAuthentication(request, response);
        loginFilter.successfulAuthentication(request, response, null, auth);

        // Assert
        assertEquals(200, response.getStatus());
        String json = response.getContentAsString();
        // Optionally, assert JSON contains token and user fields
        assert json.contains(token);
        assert json.contains(username);
    }
}