package com.cabletech.linepatrol.appraise.action;

import com.cabletech.linepatrol.appraise.service.AppraiseConstant;



public class AppraiseTableMonthAction extends AppraiseTableAction {
	private static final long serialVersionUID = 1L;
	@Override
	public void setTableType() {
		super.tableType=AppraiseConstant.APPRAISE_MONTH;
	}
	@Override
	public void setTypeName() {
		super.typeName=AppraiseConstant.TYPE_MONTH;
		
	}
}
