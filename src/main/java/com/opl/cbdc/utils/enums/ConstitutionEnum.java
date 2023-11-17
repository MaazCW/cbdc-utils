package com.opl.cbdc.utils.enums;

public enum ConstitutionEnum {
	
	ASSOCIATION_OF_PERSONS(1, "Association of Persons (AOP)"), 
	BODY_OF_INDIVIDUALS(2, "Body of Individuals (BOI) "), 
	PRIVATE_LIMITED(3, "Private Limited"),
	PUBLIC_LTD_UNLISTED(4, "Public Ltd - Unlisted"), 
	ONE_PERSON_COMPANY(5, "One Person Company"),
	LIMITED_LIABILITY_PARTNERSHIP(6, "Limited Liability Partnership"), 
	PARTNERSHIP(7, "Partnership"), 
	LIMITED_LIABILITY_PARTNERSHIP_LLP(8, "Limited Liability Partnership (LLP)"),
	GOVERNMENT_ENTITY(9, "Government Entity"),
	OTHERS(10, "Others"),
	HUF(11, "HUF"),
	SOLE_PROPRIETORSHIP(12, "Sole-Proprietorship"),
	TRUST(13, "Trust");

	private Integer id;
	private String value;

	private ConstitutionEnum(Integer id, String value) {
		this.id = id;
		this.value = value;
	}

	public Integer getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	public static ConstitutionEnum fromId(Integer v) {
		for (ConstitutionEnum c : ConstitutionEnum.values()) {
			if (c.id.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v != null ? v.toString() : null);
	}

	public static ConstitutionEnum[] getAll() {
		return ConstitutionEnum.values();
	}
	
	public static ConstitutionEnum getById(Integer id) {
		switch (id) {
		case 1:
			return ASSOCIATION_OF_PERSONS;

		case 2:
			return BODY_OF_INDIVIDUALS;
			
		case 3:
			return PRIVATE_LIMITED;
			
		case 4:
			return PUBLIC_LTD_UNLISTED;

		case 5:
			return ONE_PERSON_COMPANY;
			
		case 6:
			return LIMITED_LIABILITY_PARTNERSHIP;

		case 7:
			return PARTNERSHIP;
			
		case 8:
			return LIMITED_LIABILITY_PARTNERSHIP_LLP;
		
		case 9:
			return GOVERNMENT_ENTITY;

		case 10:
			return OTHERS;
			
		case 11:
			return HUF;

		case 12:
			return SOLE_PROPRIETORSHIP;
			
		case 13:
			return TRUST;

		default:
			return null;
		}
	}

}
