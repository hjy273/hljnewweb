package com.cabletech.linepatrol.overhaul.beans;

import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;

/**
 * ������Ŀ�ճ�����
 * 
 * @author liusq
 * 
 */
public class OverHaulExamBean extends AppraiseDailyBean {

	private static final long serialVersionUID = -6466126722517147680L;

	private String projectName; // ������Ŀ����

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
}
