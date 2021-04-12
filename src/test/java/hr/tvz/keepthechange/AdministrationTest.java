package hr.tvz.keepthechange;

import hr.tvz.keepthechange.dto.UserRegistrationDto;
import hr.tvz.keepthechange.entity.Wallet;
import hr.tvz.keepthechange.service.UserService;
import hr.tvz.keepthechange.service.WalletService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Administration Test class.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class AdministrationTest {

	private static final String TEST_USERNAME = "testUsername";
	private static final String TEST_PASSWORD = "testPass";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserService userService;

	@Autowired
	private WalletService walletService;

	/**
	 * Test loading
	 * @throws Exception exception
	 */
	@Test
	public void testGetMethod() throws Exception {
		this.mockMvc
			.perform(get("/administration").with(user("admin").password("adminpass")
			.roles("USER", "ADMIN")).with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("administration"));
	}

	/**
	 * Test wallet reset and delete.
	 * @throws Exception exception
	 */
	@Test
	public void testWalletResetAndDelete() throws Exception{

		final UserRegistrationDto registrationDto = new UserRegistrationDto();
		registrationDto.setPassword(TEST_PASSWORD);
		registrationDto.setPasswordConfirm(TEST_PASSWORD);
		registrationDto.setUsername(TEST_USERNAME);
		registrationDto.setWalletName("testWallet");

		userService.registerNewUser(registrationDto);
		final Wallet wallet = walletService.findFirstByUsername(TEST_USERNAME).orElseThrow();

		this.mockMvc
			.perform(get("/administration/resetWallet/" + wallet.getId()).with(user("admin").password("adminpass")
			.roles("USER", "ADMIN")).with(csrf()))
			.andExpect(redirectedUrl("/administration"));

		this.mockMvc
				.perform(get("/administration/deleteWallet/" + wallet.getId()).with(user("admin").password("adminpass")
						.roles("USER", "ADMIN")).with(csrf()))
				.andExpect(redirectedUrl("/administration"));
	}





}