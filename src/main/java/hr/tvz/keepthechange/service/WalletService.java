package hr.tvz.keepthechange.service;

import hr.tvz.keepthechange.dto.WalletDto;
import hr.tvz.keepthechange.entity.Transaction;
import hr.tvz.keepthechange.entity.Wallet;
import hr.tvz.keepthechange.enumeration.TransactionType;
import hr.tvz.keepthechange.repository.TransactionRepository;
import hr.tvz.keepthechange.repository.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * {@link Wallet} service class.
 */
@Service
public class WalletService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WalletService.class);
    private final WalletRepository walletRepository;
    private final UserService userService;
    private final TransactionRepository transactionRepository;

    public WalletService(WalletRepository walletRepository, UserService userService, TransactionRepository transactionRepository) {
        this.walletRepository = walletRepository;
        this.userService = userService;
        this.transactionRepository = transactionRepository;
    }

    /**
     * Calculate wallet balance.
     * @return balance in string format (frontend compatibility)
     */
    public WalletDto calculateWallet() {
        final Wallet wallet = walletRepository.findByUsername(userService.getLoggedInUser()).get(0);
        WalletDto walletDto = new WalletDto();
        List<Transaction> expenses = transactionRepository.findByWalletIdAndTransactionType(wallet.getId(), TransactionType.EXPENSE);
        List<Transaction> transactions = transactionRepository.findByWalletIdAndTransactionType(wallet.getId(), TransactionType.INCOME);

        BigDecimal sum = expenses.stream().map(Transaction::getValue).reduce(BigDecimal.ZERO, BigDecimal::add).multiply(new BigDecimal(-1));
        sum = sum.add(transactions.stream().map(Transaction::getValue).reduce(BigDecimal.ZERO, BigDecimal::add));

        walletDto.setId(wallet.getId());
        walletDto.setWalletName(wallet.getWalletName());
        walletDto.balance = sum.toString();

        return walletDto;
    }

    public Optional<Wallet> findFirstByUsername(String username) {
        return walletRepository.findFirstByUsername(username);
    }

    public List<Wallet> findAllOrderByWalletName(){
        return walletRepository.findAllByOrderByWalletName();
    }

    /**
     * Deletes a wallet and its transactions. It also disables the account of the wallets owner.
     *
     * @param id id of a wallet which is being deleted
     * @return number of deleted wallets (1 or 0)
     */
    @Transactional
    public int deleteById(Long id) {
        return walletRepository.findById(id)
                .map(wallet -> {
                    walletRepository.deleteById(id);
                    LOGGER.debug("Deleted wallet {} and its transactions", id);
                    userService.disableUserByUsername(wallet.getUsername());
                    LOGGER.debug("User {} disabled", wallet.getUsername());
                    return 1;
                }).orElse(0);
    }

}
