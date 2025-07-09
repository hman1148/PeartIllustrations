package services;

import UserRepository.UserRepository;
import io.jsonwebtoken.Jwt;
import models.User.User;
import models.UserRequestResponse.UserCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public User createUser(UserCreateRequest user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        User newUser = new User(
                null,
                user.getUsername(),
                hashedPassword,
                user.getEmail(),
                user.getRole(), // Assuming roles are handled separately
                user.getStripeCustomerId(),
                true
        );

        return this.userRepository.save(newUser);
    }

    public Optional<User> findById(Long id) {
        return this.userRepository.findById(id);
    }


    // Update user details
    public User updateUser(User user) {
        User existingUser = this.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setEmailSubscriptionsActive(user.isEmailSubscriptionsActive());
        existingUser.setOrderHistory(user.getOrderHistory());
        existingUser.setShoppingCart(user.getShoppingCart());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        existingUser.setPassword(hashedPassword);
        return this.userRepository.save(user);
    }

    // Delete user by ID
    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

    public User getUserFromToken(String token) {
        String username = this.jwtUtil.extractUsername(token);
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
