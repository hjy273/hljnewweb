package com.cabletech.sysmanage.beans;

import java.util.List;

import com.cabletech.commons.util.DateUtil;
import com.cabletech.commons.util.StringUtils;
import com.cabletech.uploadfile.formbean.BaseFileFormBean;

public class NoticeBean extends BaseFileFormBean {

	private String id;
	private String title;// title
	private String contentString;// 内容
	private String isissue;// 是否发布
	private String regionid;// 区域
	private String issueperson;// 发布人
	private String issuedate;// 发布时间
	private String fileinfo; // 附件
	private String type; // 公告类型
	private String grade;// 级别
	private String isread;
	private String begindate = "";
	private String enddate = "";
	private String meetTime;
	private String meetEndTime;
	private String meetTimeDate;
	private String meetEndTimeDate;
	private String meetTimeTime;
	private String meetEndTimeTime;
	private String meetPerson;
	private String meetPersonName;
	private String meetAddress;
	private String acceptUserIds;
	private String acceptUserNames;
	private String mobileIds;
	private String sendMethod;
	private String beginDate;
	private String endDate;
	private String sendIntervalType;
	private String sendTimeSpace;
	private String oldMeetTime;
	private String oldMeetEndTime;
	private String oldMeetPerson;
	private String oldMeetAddress;
	private String acceptUserTels;
	private String isCanceled;

	public NoticeBean() {
		super();
		issuedate = DateUtil.getNowDateString();
		// begindate = DateUtil.getNowDateString() +" 00:00:00";
		// enddate = DateUtil.getNowDateString()+" 23:59:59";
	}

	public String getBegindate() {
		return begindate;
	}

	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getContentString() {
		return contentString;
	}

	public void setContentString(String contentString) {
		this.contentString = contentString;
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
		return this.id + " title:" + this.title + "content:"
				+ this.contentString + "isissue:" + this.isissue;
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

	public String getIsread() {
		return isread;
	}

	public void setIsread(String isread) {
		this.isread = isread;
	}

	public String getIssuedate() {
		return issuedate;
	}

	public void setIssuedate(String issuedate) {
		this.issuedate = issuedate;
	}

	public String getMeetTime() {
		return meetTime;
	}

	public void setMeetTime(String meetTime) {
		this.meetTime = meetTime;
	}

	public String getMeetPerson() {
		return meetPerson;
	}

	public void setMeetPerson(String meetPerson) {
		this.meetPerson = meetPerson;
	}

	public String getMeetTimeDate() {
		return meetTimeDate;
	}

	public void setMeetTimeDate(String meetTimeDate) {
		this.meetTimeDate = meetTimeDate;
	}

	public String getMeetTimeTime() {
		return meetTimeTime;
	}

	public void setMeetTimeTime(String meetTimeTime) {
		this.meetTimeTime = meetTimeTime;
	}

	public String getAcceptUserIds() {
		return acceptUserIds;
	}

	public void setAcceptUserIds(String acceptUserIds) {
		this.acceptUserIds = acceptUserIds;
	}

	public String getMobileIds() {
		return mobileIds;
	}

	public void setMobileIds(String mobileIds) {
		this.mobileIds = mobileIds;
	}

	public String getAcceptUserNames() {
		return acceptUserNames;
	}

	public void setAcceptUserNames(String acceptUserNames) {
		this.acceptUserNames = acceptUserNames;
	}

	public String getMeetEndTime() {
		return meetEndTime;
	}

	public void setMeetEndTime(String meetEndTime) {
		this.meetEndTime = meetEndTime;
	}

	public String getMeetEndTimeDate() {
		return meetEndTimeDate;
	}

	public void setMeetEndTimeDate(String meetEndTimeDate) {
		this.meetEndTimeDate = meetEndTimeDate;
	}

	public String getMeetEndTimeTime() {
		return meetEndTimeTime;
	}

	public void setMeetEndTimeTime(String meetEndTimeTime) {
		this.meetEndTimeTime = meetEndTimeTime;
	}

	public String getMeetAddress() {
		return meetAddress;
	}

	public void setMeetAddress(String meetAddress) {
		this.meetAddress = meetAddress;
	}

	public String getMeetPersonName() {
		return meetPersonName;
	}

	public void setMeetPersonName(String meetPersonName) {
		this.meetPersonName = meetPersonName;
	}

	public String getSendMethod() {
		return sendMethod;
	}

	public void setSendMethod(String sendMethod) {
		this.sendMethod = sendMethod;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
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

	public String getOldMeetTime() {
		return oldMeetTime;
	}

	public void setOldMeetTime(String oldMeetTime) {
		this.oldMeetTime = oldMeetTime;
	}

	public String getOldMeetEndTime() {
		return oldMeetEndTime;
	}

	public void setOldMeetEndTime(String oldMeetEndTime) {
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

	public boolean judgeMeetSame() {
		String format = "yyyy/MM/dd HH:mm:ss";
		if (!this.oldMeetAddress.equals(this.meetAddress)) {
			return false;
		}
		List oldPerson = StringUtils.string2List(this.oldMeetPerson, ",");
		List newPerson = StringUtils.string2List(this.meetPerson, ",");
		String person;
		boolean flag = true;
		for (int i = 0; oldPerson != null && i < oldPerson.size(); i++) {
			person = (String) oldPerson.get(i);
			if (person != null && !newPerson.contains(person)) {
				flag = false;
				break;
			}
		}
		for (int i = 0; newPerson != null && i < newPerson.size(); i++) {
			person = (String) newPerson.get(i);
			if (person != null && !oldPerson.contains(person)) {
				flag = false;
				break;
			}
		}
		if (!flag) {
			return false;
		}
		if (!DateUtil.Str2UtilDate(this.oldMeetTime, format).equals(
				DateUtil.Str2UtilDate(this.meetTime, format))) {
			return false;
		}
		if (!DateUtil.Str2UtilDate(this.oldMeetEndTime, format).equals(
				DateUtil.Str2UtilDate(this.meetEndTime, format))) {
			return false;
		}
		return true;
	}

	public String getAcceptUserTels() {
		return acceptUserTels;
	}

	public void setAcceptUserTels(String acceptUserTels) {
		this.acceptUserTels = acceptUserTels;
	}

	public String getIsCanceled() {
		return isCanceled;
	}

	public void setIsCanceled(String isCanceled) {
		this.isCanceled = isCanceled;
	}
}
