package hr.tvz.keepthechange.enumeration;

/**
 * ExpenseCategory enumeration.
 */
public enum ExpenseCategory {
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

	ExpenseCategory(String s) {
		desc = s;
	}
}
