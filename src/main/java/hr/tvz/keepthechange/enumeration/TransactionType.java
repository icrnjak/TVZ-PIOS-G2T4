package hr.tvz.keepthechange.enumeration;

/**
 * TransactionType enumeration
 */
public enum TransactionType {
	EXPENSE("Trošak"),
	INCOME("Uplata");

	public String desc;

	public String getDesc() {
		return desc;
	}

	private TransactionType(String s) {
		desc = s;
	}
}
