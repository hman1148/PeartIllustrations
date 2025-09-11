package ServicesTest;

import UserRepository.UserRepository;
import components.JwtUtil;
import models.User.User;
import models.UserRequestResponse.UserCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import services.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, jwtUtil);
    }

    @Test
    void createUser_shouldReturnNewUser_whenValidRequest() {
        UserCreateRequest request = new UserCreateRequest(
                "testuser",
                "password123",
                "test@example.com",
                "stripe_123"
        );

        try (MockedStatic<BCryptPasswordEncoder> mockedEncoder = mockStatic(BCryptPasswordEncoder.class)) {
            BCryptPasswordEncoder mockEncoder = mock(BCryptPasswordEncoder.class);
            mockedEncoder.when(BCryptPasswordEncoder::new).thenReturn(mockEncoder);
            when(mockEncoder.encode("password123")).thenReturn("hashedPassword");

            // Mock the repository to return the user that was passed to save()
            when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
                User userToSave = invocation.getArgument(0);
                // Simulate database setting the ID
                return new User(1L, userToSave.getUsername(), userToSave.getPassword(),
                        userToSave.getEmail(), userToSave.getRole(),
                        userToSave.getStripeCustomerId(), userToSave.isEmailSubscriptionsActive());
            });

            User result = userService.createUser(request);

            assertNotNull(result);
            assertEquals("testuser", result.getUsername());
            assertEquals("hashedPassword", result.getPassword());
            assertEquals("test@example.com", result.getEmail());
            assertEquals("USER", result.getRole());
            assertEquals("stripe_123", result.getStripeCustomerId());
            assertTrue(result.isEmailSubscriptionsActive());
            verify(userRepository).save(any(User.class));
        }
    }

    @Test
    void findById_shouldReturnUser_whenUserExists() {
        Long userId = 1L;
        User expectedUser = new User(userId, "testuser", "hashedPassword", "test@example.com", "USER", "stripe_123", true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        Optional<User> result = userService.findById(userId);

        assertTrue(result.isPresent());
        assertEquals(expectedUser, result.get());
        verify(userRepository).findById(userId);
    }

    @Test
    void findById_shouldReturnEmpty_whenUserDoesNotExist() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<User> result = userService.findById(userId);

        assertFalse(result.isPresent());
        verify(userRepository).findById(userId);
    }

    @Test
    void updateUser_shouldReturnUpdatedUser_whenUserExists() {
        User existingUser = new User(1L, "olduser", "oldPassword", "old@example.com", "USER", "stripe_123", true);
        User updateUser = new User(1L, "newuser", "newPassword", "new@example.com", "ADMIN", "stripe_456", false);

        try (MockedStatic<BCryptPasswordEncoder> mockedEncoder = mockStatic(BCryptPasswordEncoder.class)) {
            BCryptPasswordEncoder mockEncoder = mock(BCryptPasswordEncoder.class);
            mockedEncoder.when(BCryptPasswordEncoder::new).thenReturn(mockEncoder);
            when(mockEncoder.encode("newPassword")).thenReturn("hashedNewPassword");
            when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
            when(userRepository.save(any(User.class))).thenReturn(updateUser);

            User result = userService.updateUser(updateUser);

            assertNotNull(result);
            // Verify the existing user was updated correctly
            assertEquals("newuser", existingUser.getUsername());
            assertEquals("new@example.com", existingUser.getEmail());
            assertEquals("hashedNewPassword", existingUser.getPassword());
            assertFalse(existingUser.isEmailSubscriptionsActive());

            verify(userRepository).findById(1L);
            verify(userRepository).save(updateUser);
        }
    }

    @Test
    void updateUser_shouldThrowException_whenUserDoesNotExist() {
        User updateUser = new User(1L, "newuser", "newPassword", "new@example.com", "ADMIN", "stripe_456", false);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.updateUser(updateUser);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository).findById(1L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void deleteUser_shouldCallRepositoryDelete() {
        Long userId = 1L;

        userService.deleteUser(userId);

        verify(userRepository).deleteById(userId);
    }

    @Test
    void getUserFromToken_shouldReturnUser_whenTokenValid() {
        String token = "validToken";
        String username = "testuser";
        User expectedUser = new User(1L, username, "hashedPassword", "test@example.com", "USER", "stripe_123", true);

        when(jwtUtil.extractUsername(token)).thenReturn(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(expectedUser));

        User result = userService.getUserFromToken(token);

        assertNotNull(result);
        assertEquals(expectedUser, result);
        verify(jwtUtil).extractUsername(token);
        verify(userRepository).findByUsername(username);
    }

    @Test
    void getUserFromToken_shouldThrowException_whenUserNotFound() {
        String token = "validToken";
        String username = "nonexistentuser";

        when(jwtUtil.extractUsername(token)).thenReturn(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserFromToken(token);
        });

        assertEquals("User not found", exception.getMessage());
        verify(jwtUtil).extractUsername(token);
        verify(userRepository).findByUsername(username);
    }

    @Test
    void findByUsername_shouldReturnUser_whenUserExists() {
        String username = "testuser";
        User expectedUser = new User(1L, username, "hashedPassword", "test@example.com", "USER", "stripe_123", true);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(expectedUser));

        User result = userService.findByUsername(username);

        assertNotNull(result);
        assertEquals(expectedUser, result);
        verify(userRepository).findByUsername(username);
    }

    @Test
    void findByUsername_shouldThrowException_whenUserNotFound() {
        String username = "nonexistentuser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.findByUsername(username);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository).findByUsername(username);
    }
}