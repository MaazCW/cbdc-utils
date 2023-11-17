package com.opl.cbdc.utils.enums;

public enum ProposalStatus {
	
	COMPLETED(1, "Completed"), 
	SANCTIONED(2, "Sanctioned"), 
	DISBURSED(3, "Disbursed"),
	REJECT(4, "Reject"), 
	HOLD(5, "Hold"),
	HOLD_AFTER_SANCTIONED(6, "Hold After Sanction"), 
	REJECT_AFTER_SANCTIONED(7, "Reject After Sanction"), 
	PARTIAL_DISBURSED(8, "Partial Disburse"),
	SANCTIONED_BY_OTHER_BANK(9,"Sanctioned By Other Bank"),
	REJECT_OFFLINE_CONFIGURATION(10,"Reject Offline Configuration"),
	DIGITAL_SANCTION(11,"Digital Sanction"),
	EXPIRED(12,"Expired");

	private Integer id;
	private String value;

	private ProposalStatus(Integer id, String value) {
		this.id = id;
		this.value = value;
	}

	public Integer getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	public static ProposalStatus fromId(Integer v) {
		for (ProposalStatus c : ProposalStatus.values()) {
			if (c.id.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v != null ? v.toString() : null);
	}

	public static ProposalStatus[] getAll() {
		return ProposalStatus.values();
	}

}
