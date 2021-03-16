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

    @OneToMany(cascade = CascadeType.ALL, targetEntity = Expense.class, fetch = FetchType.LAZY)
    @JoinTable(name="expense", joinColumns = @JoinColumn(name="walletid"))
    private List<Expense> expenses;

    @OneToMany(cascade = CascadeType.ALL,targetEntity = Expense.class, fetch = FetchType.LAZY)
    @JoinTable(name="expense", joinColumns = @JoinColumn(name="walletid"))
    private List<Expense> transactions;

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

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public List<Expense> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Expense> transactions) {
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
