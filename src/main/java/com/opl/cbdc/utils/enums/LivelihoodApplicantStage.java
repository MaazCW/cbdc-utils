package com.opl.cbdc.utils.enums;

public enum LivelihoodApplicantStage {

	MCQ(1, "Required Basic Document"),
	SELF_HELP_GROUP_DETAILS(2, "Self Help Group Details"),// SHG / Personal details
	IDENTITY_VERIFICATION(3, "Identity Verification"),
	BUREAU_DETAILS(4, "Bureau Details"),
	CREDIT_DETAILS(5, "Credit Details"),
	LOAN_DETAILS(6, "Loan Details"),
	UPLOAD_DOCUMENTS(7, "Upload Documents"),
	APPLICANT_DETAILS(8, "Applicant Details"), 
	MATCHES(9, "Matches"),
	BRANCH_SELECTION(10, "Branch Selection"), 
	COMPLETED(11, "Completed"),
	PENDING_FOR_ULB(12,"Pending Form Ulb"),
    PERSONAL_DETAILS(13,"Personal Details"),
	ITR(14,"ITR"),
	BANK_STATEMENT(15,"Bank Statement"),
	CANCELLED(49, "Cancelled"),
	APPLICATION_EXPIRE(50, "Application Expire");
	

	private Integer id;
	private String value;

	private LivelihoodApplicantStage(Integer id, String value) {
		this.id = id;
		this.value = value;
	}

	public Integer getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	public static LivelihoodApplicantStage fromId(Integer v) {
		for (LivelihoodApplicantStage c : LivelihoodApplicantStage.values()) {
			if (c.id.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v != null ? v.toString() : null);
	}

	public static LivelihoodApplicantStage[] getAll() {
		return LivelihoodApplicantStage.values();
	}

}
