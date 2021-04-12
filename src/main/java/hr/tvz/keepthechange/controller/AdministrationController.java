package hr.tvz.keepthechange.controller;

import hr.tvz.keepthechange.entity.Transaction;
import hr.tvz.keepthechange.entity.Wallet;
import hr.tvz.keepthechange.service.TransactionService;
import hr.tvz.keepthechange.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Contains mappings related to wallet administration.
 */
@Controller
@RequestMapping("/administration")
public class AdministrationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdministrationController.class);
    private static final String VIEW_NAME = "administration";
    private final WalletService walletService;
    private final TransactionService transactionService;

    public AdministrationController(WalletService walletService, TransactionService transactionService) {
        this.walletService = walletService;
        this.transactionService = transactionService;
    }

    /**
     * Shows the "all expenses" view which contains all wallets and expenses.
     * @param model template model
     * @return view name
     */
    @GetMapping
    public String showAdminPage(Model model) {
        LOGGER.debug("Showing admin page");
        List<Wallet> wallets = walletService.findAllOrderByWalletName();
        model.addAttribute("wallets", wallets);
        return VIEW_NAME;
    }

    /**
     * Delete all {@link Transaction} objects for {@link Wallet} with specified ID
     * @param id {@link Wallet} id
     * @return view name
     */
    @GetMapping("/resetWallet/{id}")
    public String resetWallet(@PathVariable Long id) {
        LOGGER.info("Resetting wallet with ID = {}", id);
        int deletedCount = transactionService.deleteAllByWalletId(id);
        LOGGER.debug("Deleted {} transactions for wallet with ID = {}", deletedCount, id);
        return "redirect:/administration";
    }

    /**
     * Delete {@link Wallet} with specified ID
     * @param id {@link Wallet} id
     * @return view name
     */
    @GetMapping("/deleteWallet/{id}")
    public String deleteWallet(@PathVariable Long id) {
        LOGGER.info("Deleting wallet with ID = {}", id);
        int deletedCount = walletService.deleteById(id);
        LOGGER.debug("Deleted {} wallets with ID = {}", deletedCount, id);
        return "redirect:/administration";
    }
}
