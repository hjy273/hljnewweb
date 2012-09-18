package com.cabletech.linepatrol.maintenance.module;

import java.util.Date;
import java.util.List;

import org.apache.commons.fileupload.FileItem;

import com.cabletech.commons.base.BaseDomainObject;
import com.cabletech.commons.upload.module.UploadFileInfo;
import com.cabletech.linepatrol.maintenance.beans.TestCableDataBean;
import com.cabletech.uploadfile.UploadFile;

/**
 * 纤芯录入数据信息表
 */

public class TestChipData extends BaseDomainObject  {

	// Fields

	private String id;
	private String testCableDataId;//备纤录入数据系统编号
	private String chipSeq;//纤序
	private String isUsed;//是否在用
	private String isEligible;//是否合格
	private String isSave;//是否保存
	private String attenuationConstant;//衰减常数
	private String testRemark;
	private Date createTime;
	
    private List<FileItem> attachments;
    List<UploadFileInfo> oldfiles;//从数据库中查询的附件
    
    private TestCoreData coreData;
    private TestCoreLength corelength;
    private TestDecayConstant decay;
    private TestEndWaste endwaste;
    private List<TestConnectorWaste> conwaste;
    private List<TestExceptionEvent> exceptionEvent;
    private TestOtherAnalyse otherAnalyse;
    
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTestCableDataId() {
		return testCableDataId;
	}
	public void setTestCableDataId(String testCableDataId) {
		this.testCableDataId = testCableDataId;
	}
	public String getChipSeq() {
		return chipSeq;
	}
	public void setChipSeq(String chipSeq) {
		this.chipSeq = chipSeq;
	}
	public String getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}
	public String getIsEligible() {
		return isEligible;
	}
	public void setIsEligible(String isEligible) {
		this.isEligible = isEligible;
	}
	public String getIsSave() {
		return isSave;
	}
	public void setIsSave(String isSave) {
		this.isSave = isSave;
	}
	public String getAttenuationConstant() {
		return attenuationConstant;
	}
	public void setAttenuationConstant(String attenuationConstant) {
		this.attenuationConstant = attenuationConstant;
	}
	public String getTestRemark() {
		return testRemark;
	}
	public void setTestRemark(String testRemark) {
		this.testRemark = testRemark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public List<FileItem> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<FileItem> attachments) {
		this.attachments = attachments;
	}
	public List<UploadFileInfo> getOldfiles() {
		return oldfiles;
	}
	public void setOldfiles(List<UploadFileInfo> oldfiles) {
		this.oldfiles = oldfiles;
	}
	public TestCoreData getCoreData() {
		return coreData;
	}
	public void setCoreData(TestCoreData coreData) {
		this.coreData = coreData;
	}
	
	public TestCoreLength getCorelength() {
		return corelength;
	}
	public void setCorelength(TestCoreLength corelength) {
		this.corelength = corelength;
	}
	public TestDecayConstant getDecay() {
		return decay;
	}
	public void setDecay(TestDecayConstant decay) {
		this.decay = decay;
	}
	
	public TestEndWaste getEndwaste() {
		return endwaste;
	}
	public void setEndwaste(TestEndWaste endwaste) {
		this.endwaste = endwaste;
	}
	public List<TestConnectorWaste> getConwaste() {
		return conwaste;
	}
	public void setConwaste(List<TestConnectorWaste> conwaste) {
		this.conwaste = conwaste;
	}
	public List<TestExceptionEvent> getExceptionEvent() {
		return exceptionEvent;
	}
	public void setExceptionEvent(List<TestExceptionEvent> exceptionEvent) {
		this.exceptionEvent = exceptionEvent;
	}
	public TestOtherAnalyse getOtherAnalyse() {
		return otherAnalyse;
	}
	public void setOtherAnalyse(TestOtherAnalyse otherAnalyse) {
		this.otherAnalyse = otherAnalyse;
	}


}