package hr.tvz.keepthechange.enumeration;

/**
 * ExpenseType enumeration
 */
public enum ExpenseType {
	EXPENSE("Trošak"),
	INCOME("Uplata");

	public String desc;

	public String getDesc() {
		return desc;
	}

	private ExpenseType(String s) {
		desc = s;
	}
}
