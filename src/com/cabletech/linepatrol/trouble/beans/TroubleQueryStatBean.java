package com.cabletech.linepatrol.trouble.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.cabletech.commons.base.BaseBean;

/**
 * ²éÑ¯Í³¼Æ
 * @author 
 *
 */
public class TroubleQueryStatBean extends BaseBean{
	private String startTimeBegin;
	private String startTimeEnd;
	private String contractorid;
	private String approveResult;
	private String trunks;
	private String eomscode;
	private String[] termiAddr;
	private String[] troublReason;
	private String[] troubleType;
	private String[] isGreatTrouble;
	private String taskNames;
	private String[] taskStates;
	private String troubleState;
	
	public String getStartTimeBegin() {
		return startTimeBegin;
	}
	public void setStartTimeBegin(String startTimeBegin) {
		this.startTimeBegin = startTimeBegin;
	}
	public String getStartTimeEnd() {
		return startTimeEnd;
	}
	public void setStartTimeEnd(String startTimeEnd) {
		this.startTimeEnd = startTimeEnd;
	}
	public String getContractorid() {
		return contractorid;
	}
	public void setContractorid(String contractorid) {
		this.contractorid = contractorid;
	}
	public String getApproveResult() {
		return approveResult;
	}
	public void setApproveResult(String approveResult) {
		this.approveResult = approveResult;
	}
	public String getTrunks() {
		return trunks;
	}
	public void setTrunks(String trunks) {
		this.trunks = trunks;
	}
	public String[] getTermiAddr() {
		return termiAddr;
	}
	public void setTermiAddr(String[] termiAddr) {
		this.termiAddr = termiAddr;
	}
	public String[] getTroublReason() {
		return troublReason;
	}
	public void setTroublReason(String[] troublReason) {
		this.troublReason = troublReason;
	}
	public String[] getTroubleType() {
		return troubleType;
	}
	public void setTroubleType(String[] troubleType) {
		this.troubleType = troubleType;
	}
	public String getEomscode() {
		return eomscode;
	}
	public void setEomscode(String eomscode) {
		this.eomscode = eomscode;
	}
	public String[] getIsGreatTrouble() {
		return isGreatTrouble;
	}
	public void setIsGreatTrouble(String[] isGreatTrouble) {
		this.isGreatTrouble = isGreatTrouble;
	}
	public String getTaskNames() {
		return taskNames;
	}
	public void setTaskNames(String taskNames) {
		this.taskNames = taskNames;
	}
	public String[] getTaskStates() {
		return taskStates;
	}
	public void setTaskStates(String[] taskStates) {
		this.taskStates = taskStates;
		this.taskNames = "";
		for (int i = 0; taskStates != null && i < taskStates.length; i++) {
			this.taskNames += taskStates[i];
			if (i < taskStates.length - 1) {
				this.taskNames += ",";
			}
		}
	}
	public String getTroubleState() {
		return troubleState;
	}
	public void setTroubleState(String troubleState) {
		this.troubleState = troubleState;
	}
	
}
