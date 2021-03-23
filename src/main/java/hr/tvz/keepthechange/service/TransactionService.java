package hr.tvz.keepthechange.service;

import hr.tvz.keepthechange.dto.TransactionDto;
import hr.tvz.keepthechange.entity.Transaction;
import hr.tvz.keepthechange.entity.Wallet;
import hr.tvz.keepthechange.enumeration.TransactionType;
import hr.tvz.keepthechange.repository.TransactionRepository;
import hr.tvz.keepthechange.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * {@link Transaction} service class.
 */
@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final UserService userService;

    public TransactionService(TransactionRepository transactionRepository, WalletRepository walletRepository, UserService userService) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
        this.userService = userService;
    }

    /**
     * Insert or update {@link Transaction} object.
     * @param transactionDto {@link TransactionDto} object
     * @return {@link Transaction} object
     */
	public Transaction saveTransaction(TransactionDto transactionDto) {

        final Date date = new Date();
        Transaction transaction = new Transaction();
        transaction.setDate(date);
        transaction.setTransactionType(transactionDto.getTransactionType());
        transaction.setName(transactionDto.getName());
        transaction.setValue(transactionDto.getValue());
        transaction.setTransactionCategory(transactionDto.getTransactionCategory());
        transaction.setId(transactionDto.getId());

        Wallet wallet = getWallet();

		transaction.setWalletId(wallet.getId());

		return  transactionRepository.save(transaction);
	}

    /**
     * Gets {@link Transaction} object by id.
     * @param id {@link Transaction} id
     * @return {@link Transaction} object
     */
	public Transaction getTransactionsById(Long id) {
        return transactionRepository.findById(id).get();
    }

    /**
     * Delete {@link Transaction} object by id.
     * @param id {@link Transaction} id
     */
    public void deleteTransactionById(Long id) {
	    transactionRepository.deleteById(id);
	}

    /**
     * Get all transactions from user's wallet.
     * @param walletId {@link Wallet} id.
     * @return list of {@link Transaction} objects
     */
    public List<Transaction> getAllTransactions(Long walletId) {
	    return transactionRepository.findByWalletIdAndTransactionType(walletId, TransactionType.EXPENSE);
	}

    /**
     * Get all transactions from user's wallet.
     * @param walletId {@link Wallet} id.
     * @return list of {@link Transaction} objects
     */
    public List<Transaction> getAllIncomes(Long walletId) {
        return transactionRepository.findByWalletIdAndTransactionType(walletId, TransactionType.INCOME);
    }


    /**
     * Gets {@link Wallet} object by username.
     * @return {@link Wallet} object
     */
    private Wallet getWallet() {
        return walletRepository.findByUsername(userService.getLoggedInUser()).get(0);
    }

    public List<Transaction> getAllByWalletIdOrderByDateDesc(Long walletId) {
        return transactionRepository.findByWalletIdOrderByDateDesc(walletId);
    }
}