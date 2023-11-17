package com.opl.cbdc.utils.enums;

public enum ThirdPartyServiceProvider {
	KARZA(1, "KARZA"), AUTHBRIDGE(2, "AUTHBRIDGE"),DIGIO(3, "DIGIO"),NSDL(4, "NSDL");

	private Integer id;
	private String name;

	public Integer getId() {
		return id;
	}

	public String getName() {
		
		return name;
	}

	private ThirdPartyServiceProvider(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public static ThirdPartyServiceProvider fromId(Integer v) {
		for (ThirdPartyServiceProvider c : ThirdPartyServiceProvider.values()) {
			if (c.id.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v != null ? v.toString() : null);
	}

	public static ThirdPartyServiceProvider[] getAll() {
		return ThirdPartyServiceProvider.values();
	}
}
