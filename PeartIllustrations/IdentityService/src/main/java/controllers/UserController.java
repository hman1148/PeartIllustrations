package controllers;

import models.GenericResponse.ItemResponse;
import models.GenericResponse.ItemsResponse;
import models.User.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import services.UserService;

@Controller
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieves all users.
     *
     * @return ResponseEntity with a list of users or an error message.
     */
    public ResponseEntity<ItemsResponse<User>> getAllUsers() {
        try {
            return ResponseEntity.ok(new ItemsResponse<>(this.userService.getAllUsers(), "Users retrieved successfully", true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ItemsResponse<>(null, "Error retrieving users: " + e.getMessage(), false));
        }
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return ResponseEntity with the user or an error message.
     */
    @GetMapping("{id}")
    public ResponseEntity<ItemResponse<User>> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(new ItemResponse<>(user, "User retrieved successfully", true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ItemResponse<>(null, "Error retrieving user: " + e.getMessage(), false));
        }
    }

    /**
     * Updates a user's information.
     *
     * @param id            The ID of the user to update.
     * @param updateRequest The request body containing updated user details.
     * @return ResponseEntity with the updated user or an error message.
     */
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

    /**
     * Retrieves the currently authenticated user based on the provided token.
     *
     * @param authHeader The Authorization header containing the Bearer token.
     * @return ResponseEntity with the current user or an error message.
     */
    @GetMapping("/me")
    public ResponseEntity<ItemResponse<User>> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            User user = this.userService.getUserFromToken(token);
            return ResponseEntity.ok(new ItemResponse<User>(user, "User retrieved successfully", true));
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(new ItemResponse<>(null, "Error retrieving user: " + ex.getMessage(), false));
        }
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id The ID of the user to delete.
     * @return ResponseEntity with a success message or an error message.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<ItemResponse<String>> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(new ItemResponse<>("User deleted successfully", "User deleted successfully", true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ItemResponse<>(null, "Error deleting user: " + e.getMessage(), false));
        }
    }
}
