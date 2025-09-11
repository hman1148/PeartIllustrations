package services;

import UserRepository.UserRepository;
import components.JwtUtil;
import models.User.User;
import models.UserRequestResponse.UserCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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

    /** Create a new user
     *
     * @param user UserCreateRequest object containing user details
     * @return Created User object
     */
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

    /** Find user by ID
     *
     * @param id User ID
     * @return Optional User object
     */
    public Optional<User> findById(Long id) {
        return this.userRepository.findById(id);
    }

    /** Update user details
     *
     * @param user User object with updated details
     * @return Updated User object
     */
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

    /** Delete user by ID
     *
     * @param id User ID
     */
    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

    /** Get user from JWT token
     *
     * @param token JWT token
     * @return User object
     */
    public User getUserFromToken(String token) {
        String username = this.jwtUtil.extractUsername(token);
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    /** Find user by username
     *
     * @param username Username
     * @return User object
     */
    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    /** Get all users
     *
     * @return List of User objects
     */
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    /** Delete user by ID
     *
     * @param id User ID
     */
    public void deleteUserById(Long id) {
        this.userRepository.deleteById(id);
    }

    /** Get user by ID
     *
     * @param id User ID
     * @return User object
     */
    public User getUserById(Long id) {
        return this.userRepository.getUserById(id);
    }
}
