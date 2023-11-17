package com.opl.cbdc.utils.enums;

public enum BusinessType {

	EXISTING_BUSINESS(1l, "Business Loan", "eb", 1), 
	PERSONAL_LOAN(3l, "Personal Loan", "pl", 2),
	HOME_LOAN(5l, "Home Loan", "hl", 2), 
	EDUCATION_LOAN(9l, "Education Loan", "el", 2), 
	MUDRA_LOAN(10l, "Mudra Loan", "ml", 1),
	AGRI_LOAN(11l, "Agri Loan", "al", 11),
	LIVELIHOOD_LOAN(12l, "Livelihood Loan", "ll", 12),
	AGRI_LOAN_KISAN_CREDIT_CARD(14l,"Agri Loan- Kisan Credit Card","al",14);
	

	private Long id;
	private String value;
	private String code;
	private Integer profileBusinessType;

	private BusinessType(Long id, String value, String code, Integer profileBusinessType) {
		this.id = id;
		this.value = value;
		this.code = code;
		this.profileBusinessType = profileBusinessType;
	}

	public Long getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	public String getCode() {
		return code;
	}
	public Integer getProfileBusinessType() {
		return profileBusinessType;
	}
	public void setProfileBusinessType(Integer profileBusinessType) {
		this.profileBusinessType = profileBusinessType;
	}

	public static BusinessType fromId(Long v) {
		for (BusinessType c : BusinessType.values()) {
			if (c.id.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v != null ? v.toString() : null);
	}

	public static BusinessType[] getAll() {
		return BusinessType.values();
	}

}
