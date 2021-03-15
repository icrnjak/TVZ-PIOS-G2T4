package hr.tvz.keepthechange.service;

import hr.tvz.keepthechange.dto.ExpenseDto;
import hr.tvz.keepthechange.entity.Expense;
import hr.tvz.keepthechange.entity.User;
import hr.tvz.keepthechange.entity.Wallet;
import hr.tvz.keepthechange.enumeration.ExpenseType;
import hr.tvz.keepthechange.repository.ExpenseRepository;
import hr.tvz.keepthechange.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

/**
 * {@link User} service class.
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

	public Expense saveExpense(ExpenseDto expenseDto) {

        final Date date = new Date();
        Expense expense = new Expense();
        expense.setDate(date);
        expense.setExpenseType(ExpenseType.EXPENSE);
        expense.setName(expenseDto.getName());
        expense.setValue(expenseDto.getValue());
        expense.setExpenseCategory(expenseDto.getExpenseCategory());
        expense.setId(expenseDto.getId());

        Wallet wallet = getWalletFromDB();

		expense.setWalletId(wallet.getId());

		return  expenseRepository.save(expense);
	}

	private void addExpenseToWallet(Expense expense, Wallet wallet, ArrayList<Expense> exps) {
			exps.add(expense);
			wallet.setExpenses(exps);
	}

	private ArrayList<Expense> getExpensesFromDB(Wallet wallet, ExpenseType type) {
		return expenseRepository.findByWalletIdAndExpenseType(wallet.getId(), type);
	}

	private Wallet getWalletFromDB() {
        return walletRepository.findByUsername(userService.getLoggedInUser()).get(0);
	}

	public Expense getExpensesById(Long id) {
        return expenseRepository.findById(id).get();
    }



}
