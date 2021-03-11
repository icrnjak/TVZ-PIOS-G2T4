package hr.tvz.keepthechange.service;

import hr.tvz.keepthechange.dto.UserRegistrationDto;
import hr.tvz.keepthechange.entity.User;
import hr.tvz.keepthechange.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * {@link User} service class.
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Checks if a {@link User} with the given username exists.
     *
     * @param username username with which we are performing a check
     * @return {@code true} if it exists, {@code false} otherwise
     */
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Maps {@link UserRegistrationDto} to a {@link User} entity and inserts it into the database.
     *
     * <p>
     * Password is encoded using a {@link #passwordEncoder} and user is enabled by default.
     * </p>
     *
     * @param userDto user data which we are inserting into a database
     * @return inserted {@link User}
     */
    public User registerNewUser(UserRegistrationDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEnabled(true);
        return userRepository.save(user);
    }
}
