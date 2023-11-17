package com.opl.cbdc.utils.common;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by dhaval.panchal on 15-Oct-19.
 */
public class UserCreationUtil {

    public static final String COULD_NOT_LIST = "Couldn't list";
    public static final String SUCCESSFULLY_LISTED = "Successfully Listed";

    public static final Integer MSME = 1;
    public static final Integer PERSONAL_LOAN = 3;
    public static final Integer HOME_LOAN = 5;
    public static final Integer EDUCATION_LOAN = 9;
    public static final Integer AUTO_LOAN = 8;
    public static final Integer MUDRA_LOAN = 10;
    public static final Integer EDFS_LOAN = 21;
    public static final Integer EVFS_LOAN = 22;

    public static final Long BRANCH_EXCEL_LIST_FOR_MSME = 631l;
    public static final Long USER_EXCEL_LIST_FOR_MSME = 632l;
    public static final Long TIER_EXCEL_LIST_FOR_MSME = 633l;

    public static final Long BRANCH_EXCEL_LIST_FOR_ML = 634l;
    public static final Long USER_EXCEL_LIST_FOR_ML = 635l;
    public static final Long TIER_EXCEL_LIST_FOR_ML = 636l;

    public static final Long BRANCH_EXCEL_LIST_FOR_PL = 637l;
    public static final Long USER_EXCEL_LIST_FOR_PL = 638l;
    public static final Long TIER_EXCEL_LIST_FOR_PL = 639l;


    public static final Long BRANCH_EXCEL_LIST_FOR_AL = 643l;
    public static final Long USER_EXCEL_LIST_FOR_AL = 644l;
    public static final Long TIER_EXCEL_LIST_FOR_AL = 645l;

    public static final Long BRANCH_EXCEL_LIST_FOR_EL = 680L;
    public static final Long USER_EXCEL_LIST_FOR_EL = 681L;
    public static final Long TIER_EXCEL_LIST_FOR_EL = 682L;

    public static final Long BRANCH_EXCEL_LIST_FOR_HL = 683l;
    public static final Long USER_EXCEL_LIST_FOR_HL = 684l;
    public static final Long TIER_EXCEL_LIST_FOR_HL = 685l;
    
    public static final Long BRANCH_EXCEL_LIST_FOR_BL = 691l; // Loans for bussiness activity
    public static final Long USER_EXCEL_LIST_FOR_BL = 692l;
    public static final Long TIER_EXCEL_LIST_FOR_BL = 693l;
    
    public static final Long BRANCH_EXCEL_LIST_FOR_COMMON=705l;
    public static final Long USER_EXCEL_LIST_FOR_COMMON=717l;
    public static final Long TIER_EXCEL_LIST_FOR_COMMON=741l;
    public static final Long FACILITATOR_EXCEL_LIST=744l;
    public static final Long ULB_EXCEL_LIST=747l;

    public static final List<String> URLS_BRFORE_LOGIN  = new ArrayList<String>(8);

    public static final String INVALID_REQUEST = "Invalid Request";
    public static final String INVALID_REQUEST_MSG = "Invalid Request {}";
    public static final String COULD_NOT_PARSE_JSON_MSG = "Could Not Parse Json {}";
    public static final String DOCUMENT_PARSE_ERROR_IN_SERVER_SIDE_VALIDATION = "Document Parse Error in Server Side Validation {}";
    public static final String EXCEPTION = "Exception : ";
    public static final Long NABARD_LENDER_EXCEL_LIST_FOR_COMMON = 794L;
    public static final Long NABARD_USERS_EXCEL_LIST_FOR_COMMON = 795L;
    public static final Long NABARD_SHG_EXCEL_LIST_FOR_COMMON = 796L;

    public static String convertRoleName(String roleName){
        switch (roleName){
            case "Branch_Maker":
                return "8";
            case "Branch_Checker/Approver":
                return "9";
            case "Branch_Checker":
                return "9";
            case "Branch_Approver":
                return "9";
            case "Admin_Checker":
                return "11";
            case "Admin_Maker":
                return "10";
            case "Head_Office":
                return "5";
            default:
                return null;
        }
    }

}
