package com.opl.cbdc.utils.enums;

public enum UserTypeMaster {
	FUNDSEEKER(1l, "FundSeeker"), 
	FUNDPROVIDER(2l, "Fund Provider"),
	NODEL_AGENCY(3l, "Nodel Agency"),
	MINISTRY(4l, "Ministry"),
	ADMIN_PANEL(5l, "Admin Panel"),
	MASTER_ADMIN(6l, "Master Admin"),
	FACILITATOR(7l, "Facilitator"),
	ULB(8l, "Urban Local Body"),
	BANK_PARTNER(9l,"Partner"),
	NABARD_ADMIN_USER(11l,"Nabard Admin User"),
	NABARD_BANKER_USER(12l,"Nabard Banker User"),
	ADMINSTRATOR(13l,"Administrator");

	private Long id;
	private String value;

	private UserTypeMaster(Long id, String value) {
		this.id = id;
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	public static UserTypeMaster fromId(Long v) {
		for (UserTypeMaster c : UserTypeMaster.values()) {
			if (c.id.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v != null ? v.toString() : null);
	}
}
