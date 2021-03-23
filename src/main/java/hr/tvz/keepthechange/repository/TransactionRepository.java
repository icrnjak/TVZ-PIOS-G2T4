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
	Iterable<Transaction> findByWalletIdAndNameIgnoreCase(Long id, String name);
	Iterable<Transaction> findByWalletIdAndTransactionCategory(Long id, TransactionCategory transactionCategory);
	List<Transaction> findByWalletIdOrderByDateDesc(Long id);
	Iterable<Transaction> findByWalletIdAndDate(Long id, Date date);
}
