package hr.tvz.keepthechange.enumeration;

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

	private ExpenseCategory(String s) {
		desc = s;
	}
}
