package com.cabletech.linepatrol.appraise.action;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.linepatrol.appraise.service.AppraiseConstant;

public class AppraiseTableSpecialAction extends AppraiseTableAction {

	@Override
	public void setTableType() {
		super.tableType=AppraiseConstant.APPRAISE_SPECIAL;
	}

	@Override
	public void setTypeName() {
		super.typeName=AppraiseConstant.TYPE_SPECIAL;
		
	}
	
}
