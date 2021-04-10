package hr.tvz.keepthechange;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.hamcrest.core.IsCollectionContaining;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.persistence.Index;

import org.hamcrest.collection.HasItemInArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import hr.tvz.keepthechange.controller.IndexController;
import hr.tvz.keepthechange.entity.Transaction;
import hr.tvz.keepthechange.entity.Wallet;
import hr.tvz.keepthechange.enumeration.TransactionCategory;
import hr.tvz.keepthechange.enumeration.TransactionType;
import hr.tvz.keepthechange.service.TransactionService;


@SpringBootTest
@AutoConfigureMockMvc
public class AllExpensesTest {

	@Autowired
	private MockMvc mockMvc;



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