package hr.tvz.keepthechange.validator;

import hr.tvz.keepthechange.dto.UserRegistrationDto;
import hr.tvz.keepthechange.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

/**
 * Performs validation on {@link UserRegistrationDto} before completing the registration process.
 */
@Component
public class UserRegistrationValidator implements Validator {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRegistrationValidator.class);
    public static final String USERNAME = "username";
    public static final String PASSWORD_CONFIRM = "passwordConfirm";
    private final UserService userService;

    public UserRegistrationValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRegistrationDto.class.isAssignableFrom(clazz);
    }

    /**
     * Validates the given {@link UserRegistrationDto} object.
     * <p>
     * Given object must:
     *     <ol>
     *         <li>have an unique username</li>
     *         <li>have a password that matches confirmPassword</li>
     *     </ol>
     *     <br/>
     *     If the given object already has validation errors which violate javax.validation annotations in
     *     {@link UserRegistrationDto}, this method returns to the caller without performing additional validation.
     * </p>
     *
     * @param target {@link UserRegistrationDto} object on which the validation is being performed
     * @param errors validation errors
     */
    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            LOGGER.debug("User registration dto already has errors");
            return;
        }

        LOGGER.debug("Validating user registration dto");
        UserRegistrationDto user = (UserRegistrationDto) target;

        // Username must be available
        if (userService.existsByUsername(user.getUsername())) {
            LOGGER.debug("Username {} is already taken", user.getUsername());
            errors.rejectValue(USERNAME, "msg.username.taken");
        }

        // Passwords must match
        if (!Objects.equals(user.getPassword(), user.getPasswordConfirm())) {
            LOGGER.debug("Passwords do not match");
            errors.rejectValue(PASSWORD_CONFIRM, "msg.password.do.not.match");
        }
    }
}
