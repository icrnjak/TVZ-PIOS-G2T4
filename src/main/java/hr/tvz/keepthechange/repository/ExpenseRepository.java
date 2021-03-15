package hr.tvz.keepthechange.repository;



import hr.tvz.keepthechange.entity.Expense;
import hr.tvz.keepthechange.enumeration.ExpenseCategory;
import hr.tvz.keepthechange.enumeration.ExpenseType;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Date;

public interface ExpenseRepository extends CrudRepository<Expense, Long> {
	
	ArrayList<Expense> findByWalletIdAndExpenseType(Long id, ExpenseType type);
	Iterable<Expense> findByWalletIdAndNameIgnoreCase(Long id, String name);
	Iterable<Expense> findByWalletIdAndExpenseCategory(Long id, ExpenseCategory expenseCategory);
	Iterable<Expense> findByWalletId(Long id);
	Iterable<Expense> findByWalletIdAndDate(Long id, Date date);
}
