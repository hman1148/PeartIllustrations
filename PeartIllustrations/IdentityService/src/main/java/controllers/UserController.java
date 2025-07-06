package controllers;

import models.GenericResponse.ItemResponse;
import models.User.User;
import models.UserRequestResponse.UserCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import services.UserService;

@Controller
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ItemResponse<User>> registerUser(@RequestBody UserCreateRequest createRequest) {
        if (createRequest == null || createRequest.getUsername() == null || createRequest.getPassword() == null) {
            return ResponseEntity.badRequest().body(new ItemResponse<>(null, "Invalid user data", false));
        }

        try {
            User user = userService.createUser(createRequest);
            return ResponseEntity.ok(new ItemResponse<>(user, "User created successfully", true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ItemResponse<>(null, "Error creating user: " + e.getMessage(), false));
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<ItemResponse<User>> updateUser(@PathVariable Long id, @RequestBody User updateRequest) {
        if (updateRequest == null) {
            return ResponseEntity.badRequest().body(new ItemResponse<>(null, "Invalid user data", false));
        }

        try {
            User updatedUser = userService.updateUser(updateRequest);
            return ResponseEntity.ok(new ItemResponse<>(updatedUser, "User updated successfully", true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ItemResponse<>(null, "Error updating user: " + e.getMessage(), false));
        }
    }
}
