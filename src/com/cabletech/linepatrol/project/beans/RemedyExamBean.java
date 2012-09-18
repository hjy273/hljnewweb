package com.cabletech.linepatrol.project.beans;

import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;

/**
 * 工程管理日常考核
 * @author liusq
 *
 */
public class RemedyExamBean extends AppraiseDailyBean {

	private static final long serialVersionUID = -8369897101205999038L;

	private String projectName;//工程名称

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
}
