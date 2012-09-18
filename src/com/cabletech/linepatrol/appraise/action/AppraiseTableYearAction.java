package com.cabletech.linepatrol.appraise.action;

import com.cabletech.linepatrol.appraise.service.AppraiseConstant;

public class AppraiseTableYearAction extends AppraiseTableAction {

	@Override
	public void setTableType() {
		super.tableType=AppraiseConstant.APPRAISE_YEAR;

	}

	@Override
	public void setTypeName() {
		super.typeName=AppraiseConstant.TYPE_YEAR;

	}

}
