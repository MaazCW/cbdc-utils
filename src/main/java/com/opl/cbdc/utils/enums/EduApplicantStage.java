package com.opl.cbdc.utils.enums;

public enum EduApplicantStage {

	MCQ(1, "Required Basic Document"), 
	IDENTITY_VERIFICATION(2, "Identity Verification"), 
	PERSONAL_DETAILS(3, "Personal Details"),
	CONSENT(4, "Consent"), 
	BUREAU_DETAILS(5, "Bureau Details"), 
	ITR(6, "ITR"), 
	BANK_STATEMENT(7, "Bank Statement"), 
	LOAN_DETAILS(8, "Loan Details"),
	CREDIT_DETAILS(9, "Credit Details"), 
	UPLOAD_DOCUMENTS(10, "Upload Documents"),
	CO_APPLICANT_CREATION(11, "Co-Applicant Creation"), 
	STAGE_LIST(12, "Stage List"), 
	MATCHES(13, "Matches"),
	BRANCH_SELECTION(14, "Branch Selection"), 
	COMPLETED(15, "Completed"),
	CANCELLED(49, "Cancelled"),
	APPLICATION_EXPIRE(50, "Application Expire");

	private Integer id;
	private String value;

	private EduApplicantStage(Integer id, String value) {
		this.id = id;
		this.value = value;
	}

	public Integer getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	public static EduApplicantStage fromId(Integer v) {
		for (EduApplicantStage c : EduApplicantStage.values()) {
			if (c.id.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v != null ? v.toString() : null);
	}

	public static EduApplicantStage[] getAll() {
		return EduApplicantStage.values();
	}

}
