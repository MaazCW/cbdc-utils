package com.opl.cbdc.utils.enums;

public enum NULMConstitution {
	SHG(1, "Self Help Group"), 
	INDIVIDUAL(2, "Individual");

	private Integer id;
	private String value;

	private NULMConstitution(Integer id, String value) {
		this.id = id;
		this.value = value;
	}

	public Integer getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	public static NULMConstitution fromId(Integer v) {
		for (NULMConstitution c : NULMConstitution.values()) {
			if (c.id.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v != null ? v.toString() : null);
	}

	public static NULMConstitution[] getAll() {
		return NULMConstitution.values();
	}
}
