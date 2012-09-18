package com.cabletech.linepatrol.safeguard.workflow;

import org.springframework.stereotype.Service;

import com.cabletech.ctf.workflow.BaseWorkFlow;

@Service
public class SafeguardReplanSubWorkflowBO extends BaseWorkFlow {
	public static final String SAFEGUARDREPLANSUBWORKFLOW = "safeguardreplansubworkflow";
	public static final String REMAKE_GUARD_PLAN_TASK = "remake_guard_plan_task";
	public static final String REMAKE_GUARD_PLAN_APPROVE_TASK = "remake_guard_plan_approve_task";
	public SafeguardReplanSubWorkflowBO(){
		super(SAFEGUARDREPLANSUBWORKFLOW);
	}
}
