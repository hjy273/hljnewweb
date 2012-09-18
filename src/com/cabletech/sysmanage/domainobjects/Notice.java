package com.cabletech.sysmanage.domainobjects;

import java.sql.Clob;
import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

public class Notice extends BaseDomainObject{
	public static final String CANCEL_STATE = "1";
	private String id;
	private String title;// title
	// private String content;//内容
	private Clob content;
	private String isissue;// 是否发布
	private String regionid;// 区域
	private String issueperson;// 发布人
	private Date issuedate;// 发布时间
	private String fileinfo; // 附件
	private String type; // 公告类型
	private String grade;// 级别
	private String isread;// 是否已读
	private Date meetTime;
	private Date meetEndTime;
	private String meetPerson;
	private String meetAddress;
	private String acceptUserIds;

	private String contentString;
	private String sendMethod;
	private Date beginDate;
	private Date endDate;
	private String sendIntervalType;
	private String sendTimeSpace;
	private Date oldMeetTime;
	private Date oldMeetEndTime;
	private String oldMeetPerson;
	private String oldMeetAddress;
	private String isCanceled;

	public String getContentString() {
		return contentString;
	}

	public void setContentString(String contentString) {
		this.contentString = contentString;
	}

	public String getIsread() {
		return isread;
	}

	public void setIsread(String isread) {
		this.isread = isread;
	}

	public String getFileinfo() {
		return fileinfo;
	}

	public void setFileinfo(String fileinfo) {
		this.fileinfo = fileinfo;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Clob getContent() {
		return content;
	}

	public void setContent(Clob content) {
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsissue() {
		return isissue;
	}

	public void setIsissue(String isissue) {
		this.isissue = isissue;
	}

	public Date getIssuedate() {
		return issuedate;
	}

	public void setIssuedate(Date issuedate) {
		this.issuedate = issuedate;
	}

	public String getIssueperson() {
		return issueperson;
	}

	public void setIssueperson(String issueperson) {
		this.issueperson = issueperson;
	}

	public String getRegionid() {
		return regionid;
	}

	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String toString() {
		return this.id + " title:" + this.title + "content:" + this.content
				+ "isissue:" + this.isissue;
	}

	public Date getMeetTime() {
		return meetTime;
	}

	public void setMeetTime(Date meetTime) {
		this.meetTime = meetTime;
	}

	public String getMeetPerson() {
		return meetPerson;
	}

	public void setMeetPerson(String meetPerson) {
		this.meetPerson = meetPerson;
	}

	public String getAcceptUserIds() {
		return acceptUserIds;
	}

	public void setAcceptUserIds(String acceptUserIds) {
		this.acceptUserIds = acceptUserIds;
	}

	public Date getMeetEndTime() {
		return meetEndTime;
	}

	public void setMeetEndTime(Date meetEndTime) {
		this.meetEndTime = meetEndTime;
	}

	public String getMeetAddress() {
		return meetAddress;
	}

	public void setMeetAddress(String meetAddress) {
		this.meetAddress = meetAddress;
	}

	public String getSendMethod() {
		return sendMethod;
	}

	public void setSendMethod(String sendMethod) {
		this.sendMethod = sendMethod;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getSendIntervalType() {
		return sendIntervalType;
	}

	public void setSendIntervalType(String sendIntervalType) {
		this.sendIntervalType = sendIntervalType;
	}

	public String getSendTimeSpace() {
		return sendTimeSpace;
	}

	public void setSendTimeSpace(String sendTimeSpace) {
		this.sendTimeSpace = sendTimeSpace;
	}

	public Date getOldMeetTime() {
		return oldMeetTime;
	}

	public void setOldMeetTime(Date oldMeetTime) {
		this.oldMeetTime = oldMeetTime;
	}

	public Date getOldMeetEndTime() {
		return oldMeetEndTime;
	}

	public void setOldMeetEndTime(Date oldMeetEndTime) {
		this.oldMeetEndTime = oldMeetEndTime;
	}

	public String getOldMeetPerson() {
		return oldMeetPerson;
	}

	public void setOldMeetPerson(String oldMeetPerson) {
		this.oldMeetPerson = oldMeetPerson;
	}

	public String getOldMeetAddress() {
		return oldMeetAddress;
	}

	public void setOldMeetAddress(String oldMeetAddress) {
		this.oldMeetAddress = oldMeetAddress;
	}

	public String getIsCanceled() {
		return isCanceled;
	}

	public void setIsCanceled(String isCanceled) {
		this.isCanceled = isCanceled;
	}
}
