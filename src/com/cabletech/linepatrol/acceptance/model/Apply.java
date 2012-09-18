package com.cabletech.linepatrol.acceptance.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.cabletech.commons.util.DateUtil;

public class Apply {
	public static final String CANCELED_STATE = "999";
	
	private String id;
	private String assign;
	private String code;    
	private String name;              
	private String applicant;          
	private Date applyDate;        
	private String resourceType;  
	private String processState;
	private String processInstanceId; 
	private String subflowId;
	private String subProcessState;
	private String errorMsg;
	private String flowTaskName;
	private Integer resourceNumber;
	private Integer recordNumber;
	private Integer notRecordNumber;
	private Integer passedNumber;
	private Integer notPassedNumber;
	private String contractorNames;
	private String remark;
	private Set<ApplyCable> cables = new HashSet<ApplyCable>();
	private Set<ApplyPipe> pipes = new HashSet<ApplyPipe>();
	
	private String cancelUserId = "";
	private String cancelUserName = "";
	private String cancelReason = "";
	private Date cancelTime;
	private String cancelTimeDis;
	
	private String contractorId;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAssign() {
		return assign;
	}
	public void setAssign(String assign) {
		this.assign = assign;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSubflowId() {
		return subflowId;
	}
	public void setSubflowId(String subflowId) {
		this.subflowId = subflowId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getApplicant() {
		return applicant;
	}
	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getProcessState() {
		return processState;
	}
	public void setProcessState(String processState) {
		this.processState = processState;
	}
	public Set<ApplyCable> getCables() {
		return cables;
	}
	public void setCables(Set<ApplyCable> cables) {
		this.cables = cables;
	}
	public void addCable(ApplyCable applyCable){
		cables.add(applyCable);
	}
	public void clearCable(){
		cables.clear();
	}
	public Set<ApplyPipe> getPipes() {
		return pipes;
	}
	public void setPipes(Set<ApplyPipe> pipes) {
		this.pipes = pipes;
	}
	public void addPipe(ApplyPipe applyPipe){
		pipes.add(applyPipe);
	}
	public void clearPipe(){
		pipes.clear();
	}
	public void pushPipeList(List<ApplyPipe> list){
		clearPipe();
		for(ApplyPipe pipe : list){
			addPipe(pipe);
		}
	}
	public void pushCableList(List<ApplyCable> list){
		clearCable();
		for(ApplyCable cable : list){
			addCable(cable);
		}
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public void clearErrorMsg(){
		this.errorMsg = "";
	}
	public String getSubProcessState() {
		return subProcessState;
	}
	public void setSubProcessState(String subProcessState) {
		this.subProcessState = subProcessState;
	}
	public String getFlowTaskName() {
		return flowTaskName;
	}
	public void setFlowTaskName(String flowTaskName) {
		this.flowTaskName = flowTaskName;
	}
	public Integer getResourceNumber() {
		return resourceNumber;
	}
	public void setResourceNumber(Integer resourceNumber) {
		this.resourceNumber = resourceNumber;
	}
	public Integer getPassedNumber() {
		return passedNumber;
	}
	public void setPassedNumber(Integer passedNumber) {
		this.passedNumber = passedNumber;
	}
	public String getContractorNames() {
		return contractorNames;
	}
	public void setContractorNames(String contractorNames) {
		this.contractorNames = contractorNames;
	}
	public Integer getRecordNumber() {
		return recordNumber;
	}
	public void setRecordNumber(Integer recordNumber) {
		this.recordNumber = recordNumber;
	}
	public Integer getNotRecordNumber() {
		return notRecordNumber;
	}
	public void setNotRecordNumber(Integer notRecordNumber) {
		this.notRecordNumber = notRecordNumber;
	}
	public Integer getNotPassedNumber() {
		return notPassedNumber;
	}
	public void setNotPassedNumber(Integer notPassedNumber) {
		this.notPassedNumber = notPassedNumber;
	}
	public String getCancelUserId() {
		return cancelUserId;
	}
	public void setCancelUserId(String cancelUserId) {
		this.cancelUserId = cancelUserId;
	}
	public String getCancelUserName() {
		return cancelUserName;
	}
	public void setCancelUserName(String cancelUserName) {
		this.cancelUserName = cancelUserName;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public Date getCancelTime() {
		return cancelTime;
	}
	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
		this.cancelTimeDis=DateUtil.DateToString(cancelTime, "yyyy/MM/dd HH:mm:ss");
	}
	public String getCancelTimeDis() {
		return cancelTimeDis;
	}
	public void setCancelTimeDis(String cancelTimeDis) {
		this.cancelTimeDis = cancelTimeDis;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void removeCable(String cableId){
		if(!cables.isEmpty()){
			for (Iterator iterator = cables.iterator(); iterator.hasNext();) {
				ApplyCable applyCable = (ApplyCable) iterator.next();
				if(cableId.equals(applyCable.getId())){
					cables.remove(applyCable);
					break;
				}
			}
		}
	}
	
	public void removePipe(String pipeId) {
		if(!pipes.isEmpty()){
			for (Iterator iterator = pipes.iterator(); iterator.hasNext();) {
				ApplyPipe applyPipe = (ApplyPipe) iterator.next();
				if(pipeId.equals(applyPipe.getId())){
					pipes.remove(applyPipe);
					break;
				}
			}
		}
	}
	public String getContractorId() {
		return contractorId;
	}
	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}
}
