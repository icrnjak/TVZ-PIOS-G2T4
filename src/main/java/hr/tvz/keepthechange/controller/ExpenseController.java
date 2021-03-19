package hr.tvz.keepthechange.controller;

import hr.tvz.keepthechange.dto.ExpenseDto;
import hr.tvz.keepthechange.enumeration.ExpenseCategory;
import hr.tvz.keepthechange.enumeration.ExpenseType;
import hr.tvz.keepthechange.service.ExpenseService;
import hr.tvz.keepthechange.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Contains mappings related to expenses.
 */
@Controller
public class ExpenseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);
	private final ExpenseService expenseService;
	private final WalletService walletService;

	public ExpenseController(ExpenseService expenseService, WalletService walletService) {
		this.expenseService = expenseService;
		this.walletService = walletService;
	}


	/**
	 * Creates new expense if there are no validation errors
	 *
	 * @param expenseDto expense form data
	 * @param errors validation errors
	 * @param model model
	 * @return expense info view
	 */
	@PostMapping("expense/new")
	public String createExpense(@Validated ExpenseDto expenseDto, Errors errors, Model model) {
		if(errors.hasErrors()) {
			model.addAttribute("expenseDto", expenseDto);
			model.addAttribute("type", ExpenseType.values());
			model.addAttribute("category", ExpenseCategory.values());
			LOGGER.info("Form was completed with errors. Redirecting back to form");
			return "expense";
		} else {

			model.addAttribute("expense", expenseService.saveExpense(expenseDto));
			model.addAttribute("wallet", walletService.calculateWallet());
			LOGGER.info("Expense " + expenseDto.getName() + " saved to database");
			return "expenseInfo";
		}
	}

	/**
	 * Initializes from for new expense.
	 * @param model model
	 * @return expense view
	 */
	@GetMapping("expense/new")
	public String showExpense(Model model) {
		model.addAttribute("expenseDto", new ExpenseDto());
		model.addAttribute("type", ExpenseType.values());
		model.addAttribute("category", ExpenseCategory.values());
		return "expense";
	}

	/**
	 * Gets expense by Id and populated form with data. Used for expense update.
	 *
	 * @param model model
	 * @param id expense id
	 * @return expense view
	 */
	@GetMapping("expense/{id}")
	public String editExpense(Model model, @PathVariable("id") Long id) {

		model.addAttribute("expenseDto", expenseService.getExpensesById(id));
		model.addAttribute("category", ExpenseCategory.values());
		model.addAttribute("type", ExpenseType.values());
		return "expense";
	}

	/**
	 * Used for deleting expenses by id.
	 *
	 * @param model model
	 * @param id expense id
	 * @return redirect to index
	 */
	@GetMapping("expense/delete/{id}")
	public String deleteExpense(Model model, @PathVariable("id") Long id) {
		expenseService.deleteExpenseById(id);
		LOGGER.info("Expense with id" + id + " deleted from database");
		return "redirect:/index";
	}
}
