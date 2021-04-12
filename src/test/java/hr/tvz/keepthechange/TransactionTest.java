package hr.tvz.keepthechange;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import hr.tvz.keepthechange.enumeration.TransactionCategory;
import hr.tvz.keepthechange.enumeration.TransactionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import hr.tvz.keepthechange.entity.Transaction;
import hr.tvz.keepthechange.service.TransactionService;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Objects;


@SpringBootTest
@AutoConfigureMockMvc
public class TransactionTest {

	@Autowired
	private MockMvc mockMvc;

	/**
	 * Test for transaction form.
	 * @throws Exception exception
	 */
	@Test
	public void testGetMethod() throws Exception {
		this.mockMvc
			.perform(get("/transaction/new").with(user("admin").password("adminpass")
			.roles("USER", "ADMIN")))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("transactionDto"))
			.andExpect(model().attributeExists("type"))
            .andExpect(model().attributeExists("category"))
			.andExpect(view().name("transaction"));
	}

	/**
	 * Test creating new income transaction.
	 * @throws Exception exception
	 */
	@Test
	public void testPostMethodTransaction() throws Exception {
		
		//test for errors
		this.mockMvc
		.perform(post("/transaction/new").with(user("admin").password("adminpass")
		.roles("USER", "ADMIN")).with(csrf()))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("transactionDto"))
		.andExpect(view().name("transaction"));
		this.mockMvc
			.perform(post("/transaction/new").with(user("admin").password("adminpass")
			.roles("USER", "ADMIN")).with(csrf())
			.param("name", "test")
			.param("transactionType", String.valueOf(TransactionType.INCOME))
            .param("transactionCategory", String.valueOf(TransactionCategory.GROCERIES))
			.param("value", "50"))
			.andExpect(status().isOk())
            .andExpect(model().attributeExists("transaction"))
			.andExpect(model().attributeExists("wallet"))
			.andExpect(view().name("transactionInfo"));
            
	}

	/**
	 * Test creating new expense.
	 * @throws Exception exception.
	 */
    @Test
	public void testPostMethodExpense() throws Exception {
		
		//test for errors
		this.mockMvc
		.perform(post("/transaction/new").with(user("admin").password("adminpass")
		.roles("USER", "ADMIN")).with(csrf()))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("transactionDto"))
		.andExpect(view().name("transaction"));
		this.mockMvc
			.perform(post("/transaction/new").with(user("admin").password("adminpass")
			.roles("USER", "ADMIN")).with(csrf())
			.param("name", "testTrosak")
			.param("transactionType", String.valueOf(TransactionType.EXPENSE))
            .param("transactionCategory", String.valueOf(TransactionCategory.GROCERIES))
			.param("value", "50"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("transaction"))
			.andExpect(model().attributeExists("wallet"))
			.andExpect(view().name("transactionInfo"));
	}

	/**
	 * Test transaction edit.
	 * @throws Exception exception
	 */
	@Test
	public void testTransactionEdit() throws Exception {
		//Create new Transaction
		final Transaction transaction = getNewTransaction();

		//Test edit
		this.mockMvc
				.perform(get("/transaction/" + transaction.getId()).with(user("admin").password("adminpass")
						.roles("USER", "ADMIN")).with(csrf()))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("transactionDto"))
				.andExpect(model().attributeExists("category"))
				.andExpect(model().attributeExists("type"))
				.andExpect(view().name("transaction"));
	}

	/**
	 * Test transaction delete.
	 * @throws Exception exception.
	 */
	@Test
	public void testTransactionDelete() throws Exception {
		final Transaction transaction = getNewTransaction();

		//Test edit
		this.mockMvc
				.perform(get("/transaction/delete/" + transaction.getId()).with(user("admin").password("adminpass")
						.roles("USER", "ADMIN")).with(csrf()))
				.andExpect(redirectedUrl("/index"));
	}

	/**
	 * Creates new transaction for testing.
	 * @return {@link Transaction} object
	 * @throws Exception exception
	 */
	private Transaction getNewTransaction() throws Exception {
		//Create new Transaction
		final MvcResult result = this.mockMvc
				.perform(post("/transaction/new").with(user("admin").password("adminpass")
						.roles("USER", "ADMIN")).with(csrf())
						.param("name", "testTrosak")
						.param("transactionType", String.valueOf(TransactionType.EXPENSE))
						.param("transactionCategory", String.valueOf(TransactionCategory.GROCERIES))
						.param("value", "50"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("transaction"))
				.andExpect(model().attributeExists("wallet"))
				.andExpect(view().name("transactionInfo"))
				.andReturn();

		//Get created transaction
		return (Transaction) Objects.requireNonNull(result.getModelAndView()).getModel().get("transaction");
	}

}