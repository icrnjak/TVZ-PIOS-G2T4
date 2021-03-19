package hr.tvz.keepthechange.service;

import hr.tvz.keepthechange.dto.UserRegistrationDto;
import hr.tvz.keepthechange.entity.Authority;
import hr.tvz.keepthechange.entity.User;
import hr.tvz.keepthechange.entity.Wallet;
import hr.tvz.keepthechange.enumeration.Roles;
import hr.tvz.keepthechange.repository.AuthorityRepository;
import hr.tvz.keepthechange.repository.UserRepository;
import hr.tvz.keepthechange.repository.WalletRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * {@link User} service class.
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final WalletRepository walletRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository, WalletRepository walletRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.walletRepository = walletRepository;
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
     * Creates {@link Authority} entity and inserts it into the database.
     *
     * <p>
     * Password is encoded using a {@link #passwordEncoder} and user is enabled by default.
     * </p>
     *
     * @param userDto user data which we are inserting into a database
     * @return inserted {@link User}
     */
    public User registerNewUser(UserRegistrationDto userDto) {
        final User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEnabled(true);
        saveAuthority(userDto);
        createWallet(userDto);
        return userRepository.save(user);
    }

    /**
     * Gets logged in user.
     * @return username
     */
    public String getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        return user.getUsername();
    }

    /**
     * Creates {@link Authority} for {@link User}.
     * @param userDto user data which we are inserting into a database
     */
    private void saveAuthority(UserRegistrationDto userDto) {
        final Authority authority = new Authority();
        authority.setUsername(userDto.getUsername());
        authority.setAuthority(Roles.ROLE_USER);
        authorityRepository.save(authority);
    }

    /**
     * Creates {@link Wallet} for {@link User}.
     * @param userDto user data which we are inserting into a database
     */
    private void createWallet(UserRegistrationDto userDto) {
        final Wallet wallet = new Wallet();
        wallet.setWalletName(userDto.getWalletName());
        wallet.setCreateDate(LocalDate.now());
        wallet.setUsername(userDto.getUsername());
        walletRepository.save(wallet);
    }

}
