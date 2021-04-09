package hr.tvz.keepthechange.controller;

import hr.tvz.keepthechange.entity.Wallet;
import hr.tvz.keepthechange.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Contains mappings related to wallet administration.
 */
@Controller
public class AdministrationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdministrationController.class);
    private static final String VIEW_NAME = "administration";
    private final WalletService walletService;

    public AdministrationController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/administration")
    public String showAdminPage(Model model) {
        LOGGER.debug("Showing admin page");
        List<Wallet> wallets = walletService.findAllOrderByWalletName();
        model.addAttribute("wallets", wallets);
        return VIEW_NAME;
    }
}
