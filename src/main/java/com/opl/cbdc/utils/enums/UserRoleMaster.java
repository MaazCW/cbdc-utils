package com.opl.cbdc.utils.enums;

public enum UserRoleMaster {

	HEAD_OFFICE(5l, "Head Office"),
	BRANCH_OFFICE(6l,"Branch Officer"),
	BRANCH_MAKER(8l, "Branch Maker"),
	BRANCH_CHECKER(9l, "Branch Checker"), 
	ADMIN_MAKER(10l, "Admin Maker"),
	ADMIN_CHECKER(11l, "Admin Checker"), 
	SMECC(12l, "SMECC"), 
	RO(13l, "RO"),
	ZO(14l, "ZO"),
	MINISTRY(21l,"Ministry"),
	NODEL_AGENCY(22l,"Nodel Agency"),
	SUPER_ADMIN(23l,"Super Admin"),
	FACILITATOR(24l,"Facilitator"),
	ULB(27l, "Urban Local Body"),
	STATE_VERIFIER(28l, "State Verifier"),
	BANK_PARTNER(29l, "Bank Partner"),
	BANK_SUB_PARTNER(30l, "Bank Sub Partner"),
	BANK_LED_GENERATOR(31l, "Bank Led Generator");


	private Long id;
	private String value;

	private UserRoleMaster(Long id, String value) {
		this.id = id;
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	public static UserRoleMaster fromId(Long v) {
		for (UserRoleMaster c : UserRoleMaster.values()) {
			if (c.id.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v != null ? v.toString() : null);
	}

}
