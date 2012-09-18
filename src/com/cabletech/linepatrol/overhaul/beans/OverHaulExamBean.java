package com.cabletech.linepatrol.overhaul.beans;

import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;

/**
 * 大修项目日常考核
 * 
 * @author liusq
 * 
 */
public class OverHaulExamBean extends AppraiseDailyBean {

	private static final long serialVersionUID = -6466126722517147680L;

	private String projectName; // 大修项目名称

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
}
