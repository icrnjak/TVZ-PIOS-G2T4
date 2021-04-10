package hr.tvz.keepthechange.enumeration;

/**
 * TransactionCategory enumeration.
 */
public enum TransactionCategory {
	HEALTH("Health"),
	FUN("Fun"),
	SHOPPING("Shopping"),
	GROCERIES("Groceries"),
	UTILITY("Utility"),
	GENERAL("General"),
	TRANSPORT("Transport");

	public String desc;

	public String getDesc() {
		return desc;
	}

	TransactionCategory(String s) {
		desc = s;
	}
}
