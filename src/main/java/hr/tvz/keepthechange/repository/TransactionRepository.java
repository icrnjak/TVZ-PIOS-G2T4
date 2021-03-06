package hr.tvz.keepthechange.repository;

import hr.tvz.keepthechange.entity.Transaction;
import hr.tvz.keepthechange.enumeration.TransactionCategory;
import hr.tvz.keepthechange.enumeration.TransactionType;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Contains methods for performing database queries related to {@link Transaction} entities.
 */
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

	ArrayList<Transaction> findByWalletIdAndTransactionType(Long id, TransactionType type);
	List<Transaction> findByWalletIdOrderByDateDesc(Long id);
	List<Transaction> findByWalletIdAndNameContainsIgnoreCase(Long id, String name);
	List<Transaction> findByWalletIdAndTransactionCategory(Long id, TransactionCategory transactionCategory);
	List<Transaction> findByWalletIdAndDate(Long id, Date date);
	List<Transaction> findByDateBetween(Date from, Date to);

	int deleteAllByWalletId(Long walletId);
}
