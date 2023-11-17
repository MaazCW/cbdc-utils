package com.opl.cbdc.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum STPStages {
	//STP
	NOT_INITIATED(1, "Not Initiated","","Not Initiated"), 
	INITIATED(2, "Initiated","","STP Initiated"), 
	LOAN_AGREEMENT(3, "Loan Agreement","LOAN_AGREEMENT","Loan Agreement"),
	ACCOUNT_OPEN_REQUEST_FAILED(4, "Account Open Request Failed","","Account Open Request Failed"),
	ACCOUNT_OPENED(5, "Account Opened","GRANTLOAN","Noting Pending"),
	FRUIT_TRANSACTION_COMPLETE(6, "Fruit Transaction Complete","","Noting Complete"),
	FRUIT_TRANSACTION_FAILED(7, "Fruit Transaction Failed","","Noting Failed"),
	GENERATE_OFFER_STP(8, "Generate Offer Stp","OFFER","Generate Offer Stp"),
	SET_OFFER_STP(9, "Set Offer Stp","","Set Offer Stp"),
	NESL_FAILED(17, "NeSL Failed","","NeSL Failed"),
	NESL_SUCCESS(18, "NeSL Success","","NeSL Success"),

	
	//BCG
	DE_DUP_INITIATE(11, "Dedup Initiate","",""), 
	OPT_VERIFIED(12, "Otp Verified","",""), 
	INTIMATE_START(13, "Intimate Api Start","",""),
	INTIMATE_ERROR(14, "Intimate Api Error","",""), 
	GENERATE_OFFER(15, "Generate Offer","",""),
	FINAL_ACCEPTANCE(16, "Final Acceptance","",""),
	
	
	//AGRI STACK
	CONSENT_REQUEST(601, "Post Consent Requests","",""),
	CONSENT_RESPONSE(602, "Post Consent Request Acks","",""),
	CHECK_CONSENT_STATUS(603, "Post Consent Status","",""),
	UPDATE_CONSENT_STATUS(604, "Put Consent Status","",""),
	POST_DATA_REQUEST(605, "Post Data Requests","",""),
	POST_DATA_RESPONSE(606, "Post Data Request Acks","",""),
	POST_DATA_STATUS(607, "Post Data Status","",""),
	AGRI_GET_DATA(608, "Get Data","",""),	
	GET_CONSENT_ARTIFACT(609, "Get consent artifacts","","");

	private Integer id;
	private String value;
	private String stpEnumValue;
	private String displayName;



	public static STPStages fromId(Integer v) {
		for (STPStages c : STPStages.values()) {
			if (c.id.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v != null ? v.toString() : null);
	}

	public static STPStages fromName(String name) {
		for (STPStages stage : STPStages.values()) {
			if (stage.name().equalsIgnoreCase(name)) {
				return stage;
			}
		}
		return null;
	}
	public static STPStages fromStpEnumValue(String name) {
		for (STPStages stage : STPStages.values()) {
			if (stage.getStpEnumValue().equalsIgnoreCase(name)) {
				return stage;
			}
		}
		return null;
	}
	
	public static STPStages[] getAll() {
		return STPStages.values();
	}
}
