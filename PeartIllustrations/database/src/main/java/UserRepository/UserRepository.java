package UserRepository;

import models.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a user by their username.
     *
     * @param username The username of the user to find.
     * @return An Optional containing the found user, or empty if not found.
     */
    Optional<User> findByUsername(String username);

    /**
     * Checks if a user exists by their username.
     *
     * @param id The username to check.
     * @return User if a user with the given username exists, false otherwise.
     */
    User getUserById(Long id);

    /**
     * Deletes a user by their ID.
     *
     * @param id The ID of the user to delete.
     */
    void deleteById(Long id);

    /**
     * Retrieves all users from the database.
     *
     * @return A list of all users.
     */
    List<User> getAllUsers();
}
