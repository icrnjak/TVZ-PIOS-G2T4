package hr.tvz.keepthechange.enumeration;

/**
 * TransactionType enumeration
 */
public enum TransactionType {
	EXPENSE("Expense"),
	INCOME("Income");

	public String desc;

	public String getDesc() {
		return desc;
	}

	private TransactionType(String s) {
		desc = s;
	}
}
