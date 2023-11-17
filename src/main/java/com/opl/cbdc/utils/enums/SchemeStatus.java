package com.opl.cbdc.utils.enums;

public enum SchemeStatus {

	PENDING(1, "Pending"), APPROVED(2, "Approved"), INACTIVE(3, "Inactive"), SEND_TO_CHECKER(4, "Send_To_Checker"), REJECT(5, "Reject");

	private final Integer id;
	private final String name;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	private SchemeStatus(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public static SchemeStatus getById(Integer v) {
		for (SchemeStatus c : SchemeStatus.values()) {
			if (c.id.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v != null ? v.toString() : null);
	}

	public static SchemeStatus[] getAll() {
		return SchemeStatus.values();

	}

}
