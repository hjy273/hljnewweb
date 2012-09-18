package com.cabletech.linepatrol.appraise.module;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * 考核评分详细
 * 
 * @author liusq
 * 
 */
public class AppraiseMonthStatDetail extends BaseDomainObject {

	private static final long serialVersionUID = -6630109687223445309L;

	private List<AppraiseMonthStatContent> contentList = new ArrayList<AppraiseMonthStatContent>(); // 考核详细
	private double averageScore; // 平均分
	private int totalScore; // 总分

	public List<AppraiseMonthStatContent> getContentList() {
		return contentList;
	}

	public void setContentList(List<AppraiseMonthStatContent> resultList) {
		this.contentList = resultList;
	}

	public double getAverageScore() {
		return averageScore;
	}

	public void setAverageScore(double averageScore) {
		this.averageScore = averageScore;
	}

	public float getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public void addContentList(AppraiseMonthStatContent content) {
		contentList.add(content);
	}

	/**
	 * 计算总分和平均分
	 */
	public void calculateScore() {
		if (contentList != null) {
			int total = 0;
			for (int i = 0; i < contentList.size(); i++) {
				AppraiseMonthStatContent content = contentList.get(i);
				total += content.getScore();
			}

			double average = ((double) total) / (contentList.size());
			this.totalScore = total;
			average = Double.parseDouble(new DecimalFormat("##.00").format(average));
			this.averageScore = average;
		}
	}
}
