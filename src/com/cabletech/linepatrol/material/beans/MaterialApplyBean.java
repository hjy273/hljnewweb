package com.cabletech.linepatrol.material.beans;

import org.apache.struts.upload.FormFile;

import com.cabletech.commons.base.BaseBean;

public class MaterialApplyBean extends BaseBean {
	private FormFile file;

	private String id;// 材料入库id

	private String applyId;

	private String contractorId;

	private String creator;// 申请人

	private String creatorName;// 申请人

	private String createDate;// 创建时间

	private String approverId;// 审核人

	private String readerId;

	private String approverName;// 审核人

	private String remark;// 用途

	private String type;// 申请单类型

	private String title;// 申请单标题

	private String[] materialTypeId;

	private String[] materialModelId;

	private String[] materialId;// 材料id

	private String[] addressId;// 存放地址id

	private String[] materialTypeName;

	private String[] materialModelName;

	private String[] materialName;// 材料id

	private String[] addressName;// 存放地址id

	private String[] materialUnit;

	private String[] count;// 数量

	private String state;

	private String approveResult;

	private String approveRemark;

	private String beginTime;

	private String endTime;

	private String isSubmited;
	
	private String cancelUserId = "";
	private String cancelUserName = "";
	private String cancelReason = "";
	private String cancelTime;

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getApproverId() {
		return approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}

	public String getReaderId() {
		return readerId;
	}

	public void setReaderId(String readerId) {
		this.readerId = readerId;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String[] materialId) {
		this.materialId = materialId;
	}

	public String[] getAddressId() {
		return addressId;
	}

	public void setAddressId(String[] addressId) {
		this.addressId = addressId;
	}

	public String[] getCount() {
		return count;
	}

	public void setCount(String[] count) {
		this.count = count;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getApproveResult() {
		return approveResult;
	}

	public void setApproveResult(String approveResult) {
		this.approveResult = approveResult;
	}

	public String getApproveRemark() {
		return approveRemark;
	}

	public void setApproveRemark(String approveRemark) {
		this.approveRemark = approveRemark;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getContractorId() {
		return contractorId;
	}

	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}

	public String[] getMaterialTypeId() {
		return materialTypeId;
	}

	public void setMaterialTypeId(String[] materialTypeId) {
		this.materialTypeId = materialTypeId;
	}

	public String[] getMaterialModelId() {
		return materialModelId;
	}

	public void setMaterialModelId(String[] materialModelId) {
		this.materialModelId = materialModelId;
	}

	public String[] getMaterialUnit() {
		return materialUnit;
	}

	public void setMaterialUnit(String[] materialUnit) {
		this.materialUnit = materialUnit;
	}

	public String[] getMaterialTypeName() {
		return materialTypeName;
	}

	public void setMaterialTypeName(String[] materialTypeName) {
		this.materialTypeName = materialTypeName;
	}

	public String[] getMaterialModelName() {
		return materialModelName;
	}

	public void setMaterialModelName(String[] materialModelName) {
		this.materialModelName = materialModelName;
	}

	public String[] getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String[] materialName) {
		this.materialName = materialName;
	}

	public String[] getAddressName() {
		return addressName;
	}

	public void setAddressName(String[] addressName) {
		this.addressName = addressName;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getIsSubmited() {
		return isSubmited;
	}

	public void setIsSubmited(String isSubmited) {
		this.isSubmited = isSubmited;
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

	public String getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(String cancelTime) {
		this.cancelTime = cancelTime;
	}
}
