package controllers;

import models.GenericResponse.ItemResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    /**
     * Handles user login requests.
     * This method is a placeholder for actual login logic.
     *
     * @return ResponseEntity with a success message
     */
    @PostMapping("/logout")
    public ResponseEntity<ItemResponse<String>> logout() {
        SecurityContextHolder.clearContext();
        ItemResponse<String> response = new ItemResponse<>("Logout successful", "User logged out successfully", true);
        return ResponseEntity.ok(response);
    }
}
