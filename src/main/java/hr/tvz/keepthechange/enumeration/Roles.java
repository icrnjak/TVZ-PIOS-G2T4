package hr.tvz.keepthechange.enumeration;

/**
 * Roles enumeration
 */
public enum Roles {
	ROLE_USER("ROLE_USER"),
	ROLE_ADMIN("ROLE_ADMIN");

	public String desc;

	public String getDesc() {
		return desc;
	}

	private Roles(String s) {
		desc = s;
	}
}
