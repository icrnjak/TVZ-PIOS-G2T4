package hr.tvz.keepthechange.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Represents a database entity from the WALLET table.
 */
@Entity
@Table(name = "wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name="name")
    private String walletName;

    @Column(name="create_date")
    private LocalDate createDate;

    @Column(name="username")
    private String username;

    @OneToMany(mappedBy = "wallet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Transaction> transactions;

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

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "id=" + id +
                ", name='" + walletName + '\'' +
                ", createDate=" + createDate +
                ", Username='" + username + '\'' +
                '}';
    }
}
