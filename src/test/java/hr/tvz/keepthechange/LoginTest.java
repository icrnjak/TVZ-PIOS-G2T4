package hr.tvz.keepthechange;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Login Test class.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test successful login.
     * @throws Exception exception
     */
    @Test
    public void loginSuccessTest() throws Exception {
        this.mockMvc
                .perform(formLogin().user("admin").password("adminpass"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/index"))
                .andExpect(authenticated().withUsername("admin"));
    }

    /**
     * Test for failed login
     * @throws Exception exception
     */
    @Test
    public void loginFailedTest() throws Exception {
        this.mockMvc
                .perform(formLogin().user("admin").password("wrongPass"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/login?error"))
                .andExpect(unauthenticated());
    }


}
