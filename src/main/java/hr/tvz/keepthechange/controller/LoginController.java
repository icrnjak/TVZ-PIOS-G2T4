package hr.tvz.keepthechange.controller;

import hr.tvz.keepthechange.dto.UserLoginDto;
import hr.tvz.keepthechange.entity.User;
import hr.tvz.keepthechange.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);
    public static final String USER_LOGIN_DTO = "userLoginDto";
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showLoginPage(Model model) {
        //model.addAttribute(USER_REGISTRATION_DTO, new UserRegistrationDto());
        return "login";
    }

    @PostMapping
    public String performUserLogin(@Validated @ModelAttribute(USER_LOGIN_DTO) UserLoginDto userLoginDto) {

        User newUser = userService.loginUser(userLoginDto);
        LOGGER.debug("Newly inserted user: {}", newUser);
        return "redirect:/index";
    }
}
