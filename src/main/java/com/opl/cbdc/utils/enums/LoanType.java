package com.opl.cbdc.utils.enums;

public enum LoanType {

	HOME_LOAN(3, "Home Loan", "Home Loan", "Home Loan", "गृह ऋण"), EDUCATION_LOAN(8, "Education Loan", "Education Loan", "Education Loan", "शिक्षा ऋण"), WORKING_CAPITAL(1, "Working Capital", "Working Capital", "", ""),
	TERM_LOAN(2, "Term Loan", "Term Loan", "", ""), BUSINESS_ACTIVITY(4, "Business Activity", "Business Activity", "Business Activity Loan", "व्यावसायिक गतिविधि ऋण"),
	AGRICULTURE_INFRASTRUCTURE(6, "Agriculture Infrastructure", "Agriculture Infrastructure", "Agri Infrastructure Loan", "कृषि अवसंरचना ऋण"), LIVELIHOODS_LOAN(5, "Livelihoods Loans", "Livelihoods Loans", "Livelihood Loan", "आजीविका ऋण"),
	AGRI_LOAN_KISAL_CREDIT_CARD(9, "Agri Loan- Kisan Credit Card", "Agri Loan- Kisan Credit Card", "Agri Loan- Kisan Credit Card", "कृषि अवसंरचना ऋण");

	private final Integer id;
	private final String value;
	private final String description;
	private final String displayName;
	private final String displayNameHn;

	LoanType(Integer id, String value, String description, String displayName, String displayNameHn) {
		this.id = id;
		this.value = value;
		this.description = description;
		this.displayName = displayName;
		this.displayNameHn = displayNameHn;
	}

	public Integer getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getDisplayNameHn() {
		return displayNameHn;
	}

	public static LoanType getById(Integer v) {
		for (LoanType c : LoanType.values()) {
			if (c.id.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v != null ? v.toString() : null);
	}

	public static LoanType[] getAll() {
		return LoanType.values();

	}

}
