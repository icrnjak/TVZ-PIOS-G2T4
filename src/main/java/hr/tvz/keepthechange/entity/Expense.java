package hr.tvz.keepthechange.entity;

import hr.tvz.keepthechange.enumeration.ExpenseCategory;
import hr.tvz.keepthechange.enumeration.ExpenseType;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a database entity from the EXPENSE table.
 */
@Entity
@Table(name = "expense")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "{msg.valid.name.notEmpty}")
    private String name;

    @Column(name="price")
    private BigDecimal value;

    @Enumerated(EnumType.STRING)
    @Column(name="expense_category")
    private ExpenseCategory expenseCategory;

    @Column(name="create_date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name="wallet_id")
    private Long walletId;

    @Column(name="expense_type")
    @Enumerated(EnumType.STRING)
    private ExpenseType expenseType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public ExpenseCategory getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(ExpenseCategory expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(ExpenseType type) {
        this.expenseType = type;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", expenseCategory=" + expenseCategory +
                ", date=" + date +
                ", walletId=" + walletId +
                ", expenseType=" + expenseType +
                '}';
    }

    public String convertDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy.");
        return dateFormat.format(date);
    }
}
