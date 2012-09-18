package com.cabletech.linepatrol.dispatchtask.beans;

import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;

/**
 * 派单日常考核
 * 
 * @author liusq
 * 
 */
public class DispatchExamBean extends AppraiseDailyBean {

	private static final long serialVersionUID = -7763243133801812876L;

	private String sendtopic;	//派单名称

	public String getSendtopic() {
		return sendtopic;
	}

	public void setSendtopic(String sendtopic) {
		this.sendtopic = sendtopic;
	}
}
