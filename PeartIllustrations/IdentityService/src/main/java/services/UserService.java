package services;

import UserRepository.UserRepository;
import models.User.User;
import models.UserRequestResponse.UserCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    public Optional<User> findByUsername(String username) {
        return this.userRepository.findAll().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
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

}
