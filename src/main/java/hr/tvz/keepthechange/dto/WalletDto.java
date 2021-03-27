package hr.tvz.keepthechange.dto;

/**
 * Contains data of a form used for wallet details.
 */
public class WalletDto {
    public Long id;
    public String walletName;
    public String balance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
