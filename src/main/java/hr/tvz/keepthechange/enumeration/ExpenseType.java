package hr.tvz.keepthechange.enumeration;

public enum ExpenseType {
	EXPENSE("Trošak"),
	TRANSACTION("Transakcija");

	public String desc;

	public String getDesc() {
		return desc;
	}

	private ExpenseType(String s) {
		desc = s;
	}
}
