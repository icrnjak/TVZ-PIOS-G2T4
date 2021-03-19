package hr.tvz.keepthechange.service;

import hr.tvz.keepthechange.dto.ExpenseDto;
import hr.tvz.keepthechange.entity.Expense;
import hr.tvz.keepthechange.entity.Wallet;
import hr.tvz.keepthechange.enumeration.ExpenseType;
import hr.tvz.keepthechange.repository.ExpenseRepository;
import hr.tvz.keepthechange.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * {@link Expense} service class.
 */
@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final WalletRepository walletRepository;
    private final UserService userService;

    public ExpenseService(ExpenseRepository expenseRepository, WalletRepository walletRepository, UserService userService) {
        this.expenseRepository = expenseRepository;
        this.walletRepository = walletRepository;
        this.userService = userService;
    }

    /**
     * Insert or update {@link Expense} object.
     * @param expenseDto {@link ExpenseDto} object
     * @return {@link Expense} object
     */
	public Expense saveExpense(ExpenseDto expenseDto) {

        final Date date = new Date();
        Expense expense = new Expense();
        expense.setDate(date);
        expense.setExpenseType(expenseDto.getExpenseType());
        expense.setName(expenseDto.getName());
        expense.setValue(expenseDto.getValue());
        expense.setExpenseCategory(expenseDto.getExpenseCategory());
        expense.setId(expenseDto.getId());

        Wallet wallet = getWallet();

		expense.setWalletId(wallet.getId());

		return  expenseRepository.save(expense);
	}

    /**
     * Gets {@link Expense} object by id.
     * @param id {@link Expense} id
     * @return {@link Expense} object
     */
	public Expense getExpensesById(Long id) {
        return expenseRepository.findById(id).get();
    }

    /**
     * Delete {@link Expense} object by id.
     * @param id {@link Expense} id
     */
    public void deleteExpenseById(Long id) {
	    expenseRepository.deleteById(id);
	}

    /**
     * Get all expenses from user's wallet.
     * @param walletId {@link Wallet} id.
     * @return list of {@link Expense} objects
     */
    public List<Expense> getAllExpenses(Long walletId) {
	    return expenseRepository.findByWalletIdAndExpenseType(walletId, ExpenseType.EXPENSE);
	}

    /**
     * Get all expenses from user's wallet.
     * @param walletId {@link Wallet} id.
     * @return list of {@link Expense} objects
     */
    public List<Expense> getAllIncomes(Long walletId) {
        return expenseRepository.findByWalletIdAndExpenseType(walletId, ExpenseType.INCOME);
    }


    /**
     * Gets {@link Wallet} object by username.
     * @return {@link Wallet} object
     */
    private Wallet getWallet() {
        return walletRepository.findByUsername(userService.getLoggedInUser()).get(0);
    }
}
