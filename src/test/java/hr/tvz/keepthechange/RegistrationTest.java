package hr.tvz.keepthechange;

import hr.tvz.keepthechange.dto.UserRegistrationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Registration Test class.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String TEST_USERNAME = "testUsername";
    private static final String TEST_PASSWORD = "testPass";

    /**
     * Test for performing registration. After successful registration, login is performed to check registration validity.
     * @throws Exception exception
     */
    @Test
    public void performRegistrationTest() throws Exception {
        this.mockMvc
                .perform(post("/registration").with(csrf())
                .param("username", TEST_USERNAME)
                .param("password", TEST_PASSWORD)
                .param("passwordConfirm", TEST_PASSWORD)
                .param("walletName", "testWallet"))
                .andExpect(redirectedUrl("/login"));

        this.mockMvc
                .perform(formLogin().user(TEST_USERNAME).password(TEST_PASSWORD))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/index"))
                .andExpect(authenticated().withUsername(TEST_USERNAME));
    }

    /**
     * Test for performing registration with errors.
     * @throws Exception exception
     */
    @Test
    public void performRegistrationErrorTest() throws Exception {
        this.mockMvc
                .perform(post("/registration").with(csrf())
                        .param("username", TEST_USERNAME)
                        .param("password", TEST_PASSWORD)
                        .param("passwordConfirm", "")
                        .param("walletName", "testWallet"))
                .andExpect(view().name("registration"));
    }


    /**
     * Test for accessing registration form.
     * @throws Exception exception
     */
    @Test
    public void performRegistrationRedirect() throws Exception {
        this.mockMvc
                .perform(get("/registration").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }
}
