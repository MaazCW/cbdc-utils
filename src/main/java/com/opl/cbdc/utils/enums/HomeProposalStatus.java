package com.opl.cbdc.utils.enums;

public enum HomeProposalStatus {
	
	COMPLETED(1, "Completed"), 
	SANCTIONED(2, "Sanctioned"), 
	DISBURSED(3, "Disbursed"),
	REJECT(4, "Reject"), 
	HOLD(5, "Hold"); 

	private Integer id;
	private String value;

	private HomeProposalStatus(Integer id, String value) {
		this.id = id;
		this.value = value;
	}

	public Integer getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	public static HomeProposalStatus fromId(Integer v) {
		for (HomeProposalStatus c : HomeProposalStatus.values()) {
			if (c.id.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v != null ? v.toString() : null);
	}

	public static HomeProposalStatus[] getAll() {
		return HomeProposalStatus.values();
	}

}
