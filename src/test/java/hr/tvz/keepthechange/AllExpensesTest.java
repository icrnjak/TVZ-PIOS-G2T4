package hr.tvz.keepthechange;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;;
import org.springframework.test.web.servlet.MockMvc;

import hr.tvz.keepthechange.enumeration.TransactionCategory;
import hr.tvz.keepthechange.enumeration.TransactionType;

/**
 * All expenses test class.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class AllExpensesTest {

	@Autowired
	private MockMvc mockMvc;

	/**
	 * Test retrieving all wallets and transactions.
	 * @throws Exception exception
	 */
	@Test
	public void testPostMethodExpense() throws Exception {

		this.mockMvc
			.perform(post("/transaction/new").with(user("admin").password("adminpass")
			.roles("USER", "ADMIN")).with(csrf())
			.param("name", "testTrosak3")
			.param("transactionType", String.valueOf(TransactionType.EXPENSE))
            .param("transactionCategory", String.valueOf(TransactionCategory.GROCERIES))
			.param("value", "150"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("transaction"))
			.andExpect(model().attributeExists("wallet"))
			.andExpect(view().name("transactionInfo"));
		this.mockMvc
			.perform(get("/allExpenses").with(user("admin").password("adminpass")
			.roles("USER", "ADMIN")))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("wallets"))
			.andExpect(view().name("allExpenses"));
		
	}
}