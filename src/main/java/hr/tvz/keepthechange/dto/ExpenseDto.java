package hr.tvz.keepthechange.dto;

import hr.tvz.keepthechange.enumeration.ExpenseCategory;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Contains data of a form used for user registration.
 */
public class ExpenseDto {

    public Long id;

    @NotEmpty(message = "{msg.valid.name.notEmpty}")
    @Size(min = 3, max = 50, message = "{msg.valid.name.size}")
    public String name;

    @Column(name="price")
    @NotNull(message = "{msg.valid.price.notNull}")
    @DecimalMin(value = "10", message = "{msg.valid.price.decimalMin}")
    public BigDecimal value;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "{msg.valid.type.notNull}")
    @Column(name="expense_category")
    public ExpenseCategory expenseCategory;

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
}
