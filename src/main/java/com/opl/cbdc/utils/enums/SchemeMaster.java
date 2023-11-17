package com.opl.cbdc.utils.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hitesh.suthar
 *
 */

public enum SchemeMaster {
	ALL_SCHEME(-1L, -1L, "All Scheme", "All", -1),
	CENTRAL_SECTOR_INTEREST_SUBSIDY(1L, 8L, "Central Sector Interest Subsidy", "CSIS", 9), 
	PADHO_PRADESH(2L, 8L, "Padho Pardesh", "PDP", 9), 
	DR_AMBEDKAR_CENTRAL_SECTOR_SCHEME(3L, 8L, "Dr. Ambedkar Central Sector Scheme", "DACS", 9),
	PRADHAN_MANTRI_AWAS_YOJANA(4L, 3L, "Pradhan Mantri Awas Yojana - Urban", "PMAY-U", 5), 
	PRIME_MINISTER_EMPLOYMENT_GENERATION_PROGRAMME(5L, 4L, "Prime Minister's Employment Generation Programme", "PMEGP", 1),
	AGRICLINICS_AGRIBUSINESS_CENTERS_SCHEME(6L, 6L, "Agri Clinics And Agri Business Centers Scheme", "ACABC", 11), 
	AGRICULTURAL_MARKETING_INFRASTRUCTURE(7L, 6L, "Agricultural Marketing Infrastructure", "AMI", 11),
	WEAVER_CREDIT_CARD(8L, 4L, "Weaver Mudra Scheme", "WMS", 1), 
	PRADHAN_MANTRI_MUDRA_YOJNA(9L, 4L, "Pradhan Mantri MUDRA Yojna", "PMMY", 1),
	PM_SVANIDHI(10L, 4L, "Pradhan Mantri Street Vendor Aatmanirbhar Nidhi Scheme", "PMSVANidhi", 1),
	DEENDAYAL_ANTYODAYA_YOJANA_NATIONAL_URBAN_LIVELIHOODS_MISSION(11L, 5L, "Deendayal Antyodaya Yojana - National Urban Livelihoods Mission", "NULM", 12),
	SELF_EMPLOYMENT_SCHEME_FOR_REHABILITATION_OF_MANUAL_SCAVENGERS(12L, 4L, "Self Employment Scheme for Rehabilitation of Manual Scavengers", "SRMS", 1),
	STANDUP_INDIA(13L, 4L, "Stand Up India Scheme", "SUIS", 1),
	DEENDAYAL_ANTYODAYA_YOJANA_NATIONAL_RURAL_LIVELIHOODS_MISSION(14L, 5L, "Deendayal Antyodaya Yojana - National Rural Livelihoods Mission", "NRLM", 12),
	AGRICULTURE_INFRASTRUCTURE_FUND(15L, 6L, "Agriculture Infrastructure Fund", "AIF", 11),
	KISAN_CREDIT_CARD(16L, 9L, "Kisan Credit Card", "KCC", 11);

	private final Long id;
	private final Long loanMasterId;
	private final String name;
	private final String shortName;
	private final Integer bussinessTypeId;

	private SchemeMaster(Long id, Long loanMasterId, String name, String shortName, Integer bussinessTypeId) {
		this.id = id;
		this.loanMasterId = loanMasterId;
		this.name = name;
		this.shortName = shortName;
		this.bussinessTypeId = bussinessTypeId;
	}

	public Long getId() {
		return id;
	}

	public Long getLoanMasterId() {
		return loanMasterId;
	}

	public String getName() {
		return name;
	}

	public String getShortName() {
		return shortName;
	}

	public Integer getBussinessTypeId() {
		return bussinessTypeId;
	}

	public static SchemeMaster getById(Long v) {
		for (SchemeMaster c : SchemeMaster.values()) {
			if (c.id.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v != null ? v.toString() : null);
	}
	public static SchemeMaster getByCode(String v) {
		for (SchemeMaster c : SchemeMaster.values()) {
			if (c.shortName.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v != null ? v.toString() : null);
	}

	public static SchemeMaster[] getAll() {
		return SchemeMaster.values();
	}

	public static List<SchemeMaster> getAllByLoanId(Long loanTypeId) {
		List<SchemeMaster> list = new ArrayList<>();
		for (SchemeMaster c : SchemeMaster.values()) {
			if (c.loanMasterId.equals(loanTypeId)) {
				list.add(c);
			}
		}
		return list;
	}

	public static Long getLoanIdFromScheme(Long schemeId) {
		for (SchemeMaster c : SchemeMaster.values()) {
			if (c.id.equals(schemeId)) {
				return c.getLoanMasterId();
			}
		}
		return null;
	}
	
	public static SchemeMaster getByLoanMasterId(Long v) {
		for (SchemeMaster c : SchemeMaster.values()) {
			if (c.loanMasterId.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v != null ? v.toString() : null);
	}

}
