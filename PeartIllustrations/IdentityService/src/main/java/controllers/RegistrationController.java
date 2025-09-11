package controllers;

import models.GenericResponse.ItemResponse;
import models.User.User;
import models.UserRequestResponse.UserCreateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import services.UserService;

@Controller
@RequestMapping("/api/registration")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Handles user registration requests.
     *
     * @param userCreateRequest The request body containing user details.
     * @return ResponseEntity with the created user or an error message.
     */
    @PostMapping("/register")
    public ResponseEntity<ItemResponse<User>> registerUser(@RequestBody UserCreateRequest userCreateRequest) {
        if (userCreateRequest == null || userCreateRequest.getUsername() == null || userCreateRequest.getPassword() == null) {
            return ResponseEntity.badRequest().body(new ItemResponse<>(null, "Invalid user data", false));
        }

        try {
            User user = userService.createUser(userCreateRequest);
            return ResponseEntity.ok(new ItemResponse<>(user, "User created successfully", true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ItemResponse<>(null, "Error creating user: " + e.getMessage(), false));
        }
    }
}