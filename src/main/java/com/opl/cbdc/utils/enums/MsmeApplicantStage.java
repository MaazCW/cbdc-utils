package com.opl.cbdc.utils.enums;

public enum MsmeApplicantStage {

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
	MUDRA_GSTIN_LIST_DETAILS(14, "Mudra GSTIN List Details"),
	MUDRA_GST_DETAILS(15, "Mudra GST Details"),
	MUDRA_BUSINESS_DETAILS(16, "Business Details"),
	MUDRA_ASSOCIATE_CONCERN(17, "Associate Concerns"),
	MUDRA_BUSINESS_LOAN_DETAILS(18, "Business Loan Details"),
	CANCELLED(49, "Cancelled"),
	APPLICATION_EXPIRE(50, "Application Expire");

	private Integer id;
	private String value;

	private MsmeApplicantStage(Integer id, String value) {
		this.id = id;
		this.value = value;
	}

	public Integer getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	public static MsmeApplicantStage fromId(Integer v) {
		for (MsmeApplicantStage c : MsmeApplicantStage.values()) {
			if (c.id.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v != null ? v.toString() : null);
	}

	public static MsmeApplicantStage[] getAll() {
		return MsmeApplicantStage.values();
	}

}
