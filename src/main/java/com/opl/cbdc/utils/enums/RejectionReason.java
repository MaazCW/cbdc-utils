package com.opl.cbdc.utils.enums;

public enum RejectionReason {
	
	CUSTOMER_NOT_REACHABLE("Customer not reachable"),
	CUSTOMER_IS_NOT_READY_TO_PROVIDE_COLLATERAL_SECURITY("Customer is not ready to provide collateral security"),
	CUSTOMER_WANTS_HIGHER_LOAN_AMOUNT_THAN_HIS_OR_HER_ELIGIBILITY("Customer wants higher loan amount than his or her eligibility"),
	CUSTOMER_PROVIDED_WRONG_INFORMATION_OR_DATA_ON_THE_PORTAL("Customer provided wrong information or Data on the portal"),
	DELINQUENCY_OBSERVED_IN_CUSTOMERS_CREDIT_BUREAU_REPORT("Delinquency observed in customers credit bureau report"),
	CUSTOMER_IS_LOCATED_OUTSIDE_BRANCH_JURISDICTION_OR_SERVICE_AREA("Customer is located outside branch jurisdiction or service area"),
	CUSTOMER_IS_MAINTAINING_ACCOUNT_WITH_ANOTHER_BANK("Customer is maintaining account with another bank"),
	CUSTOMER_HAS_AVAILED_OTHER_FACILITIES_FROM_OTHER_BANK("Customer has availed other facilities from other bank"),
	CUSTOMER_NOT_INTERESTED_IN_AVAILING_LOAN("Customer not interested in availing loan"),
	CUSTOMER_NOT_ABLE_TO_PROVIDE_REQUIRED_DOCUMENTS("Customer not able to provide required documents"),
	CUSTOMER_NOT_ELIGIBLE_UNDER_THE_SCHEME("Customer not eligible under the scheme"),
	LOAN_PURPOSE_DOES_NOT_ELIGIBLE_UNDER_THE_SCHEME("Loan purpose does not eligible under the scheme"),
	CUSTOMER_INCOME_IS_NOT_AS_PER_SCHEME_CRITERIA("Customer income is not as per scheme criteria"),
	DISALLOWED_BY_BANK("Bank - Product Not Matched & Not Accepting Referred Application");  // change message according to jira-NP-3515
	  
	private String value;

	private RejectionReason(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static RejectionReason[] getAll() {
		return RejectionReason.values();
	}

}
