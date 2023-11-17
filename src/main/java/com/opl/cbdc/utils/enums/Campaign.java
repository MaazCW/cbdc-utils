package com.opl.cbdc.utils.enums;

public enum Campaign {

	BANK_SPECIFIC(2l, "Bank specific", 2), MARKETPLACE(1l, "Marketplace", 1), BOTH(3l, "Both", 3);

	private final Long id;
	private final Integer intId;
	private final String value;

	Campaign(Long id, String value, Integer intId) {
		this.id = id;
		this.value = value;
		this.intId = intId;
	}

	public Long getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	public Integer getIntId() {
		return intId;
	}

	public static Campaign getById(Integer id) {
		switch (id) {
		case 1:
			return MARKETPLACE;
		case 2:
			return BANK_SPECIFIC;
		case 3:
			return BOTH;

		default:
			return null;
		}
	}

	public static Campaign[] getAll() {
		return Campaign.values();

	}
}
