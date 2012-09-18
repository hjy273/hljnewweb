package com.cabletech.linepatrol.appraise.action;

import com.cabletech.linepatrol.appraise.service.AppraiseConstant;

public class AppraiseTableYearEndAction extends AppraiseTableAction {

	@Override
	public void setTableType() {
		super.tableType=AppraiseConstant.APPRAISE_YEAREND;

	}

	@Override
	public void setTypeName() {
		super.typeName=AppraiseConstant.TYPE_YEAREND;
	}

}
