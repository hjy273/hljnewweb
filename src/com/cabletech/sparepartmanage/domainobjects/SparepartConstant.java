package com.cabletech.sparepartmanage.domainobjects;

public class SparepartConstant {
    public static final String MOBILE_WAIT_USE = "01";//01表示为移动备用状态

    public static final String CONTRACTOR_WAIT_USE = "02";//02表示为代维单位备用状态

    public static final String IS_RUNNING = "03";//03表示为运行状态

    public static final String IS_MENDING = "04";//04表示为送修状态

    public static final String IS_DISCARDED = "05";//05表示为报废状态  
    
    public static final String IS_APPLY = "06";//06表示为已被申请
    
    public static final String IS_REPLACE = "07";//07表示为已被替换

    public static final String PATROL_GIVE_BACK = "01";

    public static final String CONTRACTOR_GIVE_BACK = "02";
    
    public static final String AUDITING_WATIE = "00";//待审核

    public static final String AUDITING_PASSED = "01";//审核通过

    public static final String AUDITING_NOTPASSED = "02";//审核未通过
    
    public static final String NOT_AUDITING = "00";
    
    public static final String USE_DIRECT = "01";//直接使用

    public static final String USE_UPDATE = "02";//更换使用

    public static final String REPLACE_NO_ACT = "01";//退还

    public static final String REPLACE_MEND = "02";//送修

    public static final String REPLACE_DISCARD = "03";//报废

    public static final String ALLOW_DELETE = "0";

    public static final String NOT_ALLOW_DELETE = "1";

    public static final String STRING_MEND = "送修";

    public static final String STRING_DISCARD = "报废";

}
