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

import java.security.Principal;
import java.util.List;

/**
 * Contains mappings related to the home/index page.
 */
@Controller
public class IndexController {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);
    private final WalletService walletService;
    private final TransactionService transactionService;

    public IndexController(WalletService walletService, TransactionService transactionService) {
        this.walletService = walletService;
        this.transactionService = transactionService;
    }

    @GetMapping(value = {"/", "/index"})
    public String showIndex(Model model, Principal principal) {
        Wallet wallet = walletService.findFirstByUsername(principal.getName()).orElseThrow();
        List<Transaction> transactions = transactionService.getAllByWalletIdOrderByDateDesc(wallet.getId());

        model.addAttribute("walletStatus", walletService.calculateWallet());
        model.addAttribute("transactions", transactions);
        return "index";
    }
}
