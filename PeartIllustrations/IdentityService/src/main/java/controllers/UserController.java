package controllers;

import models.GenericResponse.ItemResponse;
import models.User.User;
import models.UserRequestResponse.UserCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

        // Create a new user and hash password


    }
}
