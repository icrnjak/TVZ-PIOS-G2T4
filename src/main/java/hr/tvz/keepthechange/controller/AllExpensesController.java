package hr.tvz.keepthechange.controller;

import hr.tvz.keepthechange.entity.Wallet;
import hr.tvz.keepthechange.service.TransactionService;
import hr.tvz.keepthechange.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Contains mappings related to "all expenses" view.
 */
@Controller
@RequestMapping("/allExpenses")
public class AllExpensesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AllExpensesController.class);
    private static final String VIEW_NAME = "allExpenses";
    private final WalletService walletService;
    private final TransactionService transactionService;

    public AllExpensesController(WalletService walletService, TransactionService transactionService) {
        this.walletService = walletService;
        this.transactionService = transactionService;
    }

    /**
     * Shows the "all expenses" view which contains all wallets and expenses.
     */
    @GetMapping
    public String showAllExpensesPage(Model model) {
        LOGGER.debug("Showing all expenses view");
        List<Wallet> wallets = walletService.findAllOrderByWalletName();
        for (Wallet w : wallets) {
            w.setTransactions(transactionService.getAllByWalletIdOrderByDateDesc(w.getId()));
        }
        model.addAttribute("wallets", wallets);
        return VIEW_NAME;
    }
}
