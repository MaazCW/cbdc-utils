package com.opl.cbdc.utils.enums;

public enum AgriApplicantStage {

	MCQ(1, "Required Basic Document"), 
	IDENTITY_VERIFICATION(2, "Identity Verification"), 
	PERSONAL_DETAILS(3, "Personal Details"),
	ITR(4, "ITR"), 
	BANK_STATEMENT(5, "Bank Statement"),
	LOAN_DETAILS(6, "Loan Details"),
	BUREAU_DETAILS(7, "Bureau Details"),
	CREDIT_DETAILS(8, "Credit Details"), 
	UPLOAD_DOCUMENTS(9, "Upload Documents"),
	APPLICANT_DETAILS(10, "Applicant Details"), 
	MATCHES(11, "Matches"),
	BRANCH_SELECTION(12, "Branch Selection"), 
	COMPLETED(13, "Completed"),
	FINANCIAL_DETAILS(14, "Financial Details"),
	CANCELLED(49, "Cancelled"),
	APPLICATION_EXPIRE(50, "Application Expire");

	private Integer id;
	private String value;

	private AgriApplicantStage(Integer id, String value) {
		this.id = id;
		this.value = value;
	}

	public Integer getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	public static AgriApplicantStage fromId(Integer v) {
		for (AgriApplicantStage c : AgriApplicantStage.values()) {
			if (c.id.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v != null ? v.toString() : null);
	}

	public static AgriApplicantStage[] getAll() {
		return AgriApplicantStage.values();
	}

}
