package com.cabletech.linepatrol.dispatchtask.beans;

import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;

/**
 * �ɵ��ճ�����
 * 
 * @author liusq
 * 
 */
public class DispatchExamBean extends AppraiseDailyBean {

	private static final long serialVersionUID = -7763243133801812876L;

	private String sendtopic;	//�ɵ�����

	public String getSendtopic() {
		return sendtopic;
	}

	public void setSendtopic(String sendtopic) {
		this.sendtopic = sendtopic;
	}
}
