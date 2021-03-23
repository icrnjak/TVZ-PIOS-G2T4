package hr.tvz.keepthechange.controller;

import hr.tvz.keepthechange.entity.Transaction;
import hr.tvz.keepthechange.entity.Wallet;
import hr.tvz.keepthechange.enumeration.TransactionCategory;
import hr.tvz.keepthechange.service.TransactionService;
import hr.tvz.keepthechange.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Contains mappings related to the home/index page.
 */
@Controller
@RequestMapping(value = {"/", "/index"})
public class IndexController {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);
    private static final String VIEW_NAME = "index";
    private static final String NAME_PARAM = "name";
    private static final String DATE_PARAM = "date";
    private static final String CATEGORY_PARAM = "category";
    private static final String TRANSACTIONS = "transactions";
    private final WalletService walletService;
    private final TransactionService transactionService;

    public IndexController(WalletService walletService, TransactionService transactionService) {
        this.walletService = walletService;
        this.transactionService = transactionService;
    }

    @InitBinder
    protected void binder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), false));
    }

    @ModelAttribute
    public void addModelAttributes(Model model) {
        LOGGER.debug("Adding wallet status and transaction categories to model");
        model.addAttribute("walletStatus", walletService.calculateWallet());
        model.addAttribute("transactionCategories", TransactionCategory.values());
    }

    @GetMapping
    public String showIndex(Model model, Principal principal) {
        LOGGER.debug("Showing index page");
        Wallet wallet = walletService.findFirstByUsername(principal.getName()).orElseThrow();
        List<Transaction> transactions = transactionService.getAllByWalletIdOrderByDateDesc(wallet.getId());
        model.addAttribute(TRANSACTIONS, transactions);
        return VIEW_NAME;
    }

    @GetMapping(params = NAME_PARAM)
    public String searchByName(@RequestParam(NAME_PARAM) String name,
                               Model model,
                               Principal principal) {
        LOGGER.debug("Searching transactions by name = {}", name);
        Wallet wallet = walletService.findFirstByUsername(principal.getName()).orElseThrow();
        List<Transaction> transactions = transactionService.getAllByWalletIdAndNameContainsIgnoreCase(wallet.getId(), name);
        model.addAttribute(TRANSACTIONS, transactions);
        return VIEW_NAME;
    }

    @GetMapping(params = CATEGORY_PARAM)
    public String searchByName(@RequestParam(CATEGORY_PARAM) TransactionCategory category,
                               Model model,
                               Principal principal) {
        LOGGER.debug("Searching transactions by category = {}", category);
        Wallet wallet = walletService.findFirstByUsername(principal.getName()).orElseThrow();
        List<Transaction> transactions = transactionService.getAllByWalletIdAndTransactionCategory(wallet.getId(), category);
        model.addAttribute(TRANSACTIONS, transactions);
        return VIEW_NAME;
    }

    @GetMapping(params = DATE_PARAM)
    public String searchByName(@RequestParam(DATE_PARAM) Date date,
                               Model model,
                               Principal principal) {
        LOGGER.debug("Searching transactions by date = {}", date);
        Wallet wallet = walletService.findFirstByUsername(principal.getName()).orElseThrow();
        List<Transaction> transactions = transactionService.getAllByWalletIdAndDate(wallet.getId(), date);
        model.addAttribute(TRANSACTIONS, transactions);
        return VIEW_NAME;
    }
}
