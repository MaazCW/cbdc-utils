package com.opl.cbdc.utils.enums;

public enum HomeApplicantStage {

	MCQ(1, "Required Basic Document"), 
	IDENTITY_VERIFICATION(2, "Identity Verification"), 
	PERSONAL_DETAILS(3, "Personal Details"),
	CONSENT(4, "Consent"), 
	ITR(5, "ITR"), 
	BANK_STATEMENT(6, "Bank Statement"), 
	LOAN_DETAILS(7, "Loan Details"),
	CREDIT_DETAILS(8, "Credit Details"), 
	UPLOAD_DOCUMENTS(9, "Upload Documents"),
	CO_APPLICANT_CREATION(10, "Co-Applicant Creation"), 
	STAGE_LIST(11, "Stage List"), 
	MATCHES(12, "Matches"),
	BRANCH_SELECTION(13, "Branch Selection"), 
	COMPLETED(14, "Completed"),
	CANCELLED(49, "Cancelled"),
	APPLICATION_EXPIRE(50, "Application Expire");

	private Integer id;
	private String value;

	private HomeApplicantStage(Integer id, String value) {
		this.id = id;
		this.value = value;
	}

	public Integer getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	public static HomeApplicantStage fromId(Integer v) {
		for (HomeApplicantStage c : HomeApplicantStage.values()) {
			if (c.id.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v != null ? v.toString() : null);
	}

	public static HomeApplicantStage[] getAll() {
		return HomeApplicantStage.values();
	}

}
