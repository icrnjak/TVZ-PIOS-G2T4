package hr.tvz.keepthechange.enumeration;

/**
 * TransactionCategory enumeration.
 */
public enum TransactionCategory {
	HEALTH("Zdravlje"),
	FUN("Zabava"),
	SHOPPING("Kupovina"),
	GROCERIES("Namirnice"),
	UTILITY("Komunalne usluge"),
	GENERAL("Općenito"),
	TRANSPORT("Prijevoz");

	public String desc;

	public String getDesc() {
		return desc;
	}

	TransactionCategory(String s) {
		desc = s;
	}
}
