package filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.GenericResponse.ItemResponse;
import models.LoginRequestResponse.LoginRequest;
import models.LoginRequestResponse.LoginResponse;
import models.User.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import services.JwtUtil;
import services.UserService;

import java.io.IOException;
import java.util.Optional;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {


    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public LoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        // Set the URL for the login endpoint
        setFilterProcessesUrl("/api/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        try {
            String username = authResult.getName();
            String token = jwtUtil.generateToken(username);
            Optional<User> user = userService.findByUsername(username);

            User foundUser = user.orElseThrow(() -> new RuntimeException("User not found"));
            LoginResponse loginResponse = new LoginResponse(token, foundUser);

            ItemResponse<LoginResponse> itemResponse = new ItemResponse<>(loginResponse, "Login successful", true);
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(), itemResponse);
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ItemResponse<String> errorResponse = new ItemResponse<>(null, "Login failed: " + ex.getMessage(), false);
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
        }

    }
}
