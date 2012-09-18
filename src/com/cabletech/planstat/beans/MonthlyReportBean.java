package com.cabletech.planstat.beans;

import com.cabletech.commons.base.BaseBean;
import com.cabletech.commons.util.DateUtil;

public class MonthlyReportBean  extends BaseBean{
    public MonthlyReportBean(){
        endYear = DateUtil.getNowYearStr();
    }
    private String endYear;
    private String endMonth;
	public String getEndYear() {
		return endYear;
	}
	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}
	public String getEndMonth() {
		return endMonth;
	}
	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}

}
