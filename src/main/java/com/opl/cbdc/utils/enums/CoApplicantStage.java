package com.opl.cbdc.utils.enums;

public enum CoApplicantStage {

	IDENTITY_VERIFICATION(1, "Identity Verification"), 
	PERSONAL_DETAILS(2, "Personal Details"),
	CONSENT(3, "Consent"), 
	BUREAU_DETAILS(4, "Bureau Details"), 
	ITR(5, "ITR"), 
	BANK_STATEMENT(6, "Bank Statement"), 
	CREDIT_DETAILS(7, "Credit Details"), 
	UPLOAD_DOCUMENTS(8, "Upload Documents"),
	COMPLETED(9, "Completed"),
	CANCELLED(49, "Cancelled"),
	APPLICATION_EXPIRE(50, "Application Expire");

	private Integer id;
	private String value;

	private CoApplicantStage(Integer id, String value) {
		this.id = id;
		this.value = value;
	}

	public Integer getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	public static CoApplicantStage fromId(Integer v) {
		for (CoApplicantStage c : CoApplicantStage.values()) {
			if (c.id.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v != null ? v.toString() : null);
	}

	public static CoApplicantStage[] getAll() {
		return CoApplicantStage.values();
	}

}
