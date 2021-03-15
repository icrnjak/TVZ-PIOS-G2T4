package hr.tvz.keepthechange.service;

import hr.tvz.keepthechange.dto.WalletDto;
import hr.tvz.keepthechange.entity.Expense;
import hr.tvz.keepthechange.entity.User;
import hr.tvz.keepthechange.entity.Wallet;
import hr.tvz.keepthechange.enumeration.ExpenseType;
import hr.tvz.keepthechange.repository.ExpenseRepository;
import hr.tvz.keepthechange.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * {@link User} service class.
 */
@Service
public class WalletService {
    private final WalletRepository walletRepository;
    private final UserService userService;
    private final ExpenseRepository expenseRepository;

    public WalletService(WalletRepository walletRepository, UserService userService, ExpenseRepository expenseRepository) {
        this.walletRepository = walletRepository;
        this.userService = userService;
        this.expenseRepository = expenseRepository;
    }

    public WalletDto calculateWallet() {
        final Wallet wallet = walletRepository.findByUsername(userService.getLoggedInUser()).get(0);
        WalletDto walletDto = new WalletDto();
        BigDecimal sum = new BigDecimal(0);
        List<Expense> expenses = expenseRepository.findByWalletIdAndExpenseType(wallet.getId(), ExpenseType.EXPENSE);
        List<Expense> transactions = expenseRepository.findByWalletIdAndExpenseType(wallet.getId(), ExpenseType.TRANSACTION);

        sum = expenses.stream().map(Expense::getValue).reduce(BigDecimal.ZERO, BigDecimal::add).multiply(new BigDecimal(-1));
        sum = sum.add(transactions.stream().map(Expense::getValue).reduce(BigDecimal.ZERO, BigDecimal::add));

        walletDto.setWalletName(wallet.getWalletName());
        walletDto.balance = sum.toString();

        return walletDto;
    }

}
