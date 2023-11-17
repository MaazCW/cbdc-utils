package com.opl.cbdc.utils.enums;

public enum SwmsConstitution {
	INDIVIDUAL(1, "Individual"), 
	NONINDIVIDUAL(2, "NonIndividual");

	private Integer id;
	private String value;

	private SwmsConstitution(Integer id, String value) {
		this.id = id;
		this.value = value;
	}

	public Integer getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	public static SwmsConstitution fromId(Integer v) {
		for (SwmsConstitution c : SwmsConstitution.values()) {
			if (c.id.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v != null ? v.toString() : null);
	}

	public static SwmsConstitution[] getAll() {
		return SwmsConstitution.values();
	}
}
