package com.cabletech.linepatrol.commons.module;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;


/**
 ���ŷ�����ʷ(����)
 * @author 
 *
 */
public class SMHistory extends BaseDomainObject {
	
    private String id;
    private String simIds ;//��������ã��ָ�
    private Date sendTime ;
    private String sendState;// 0��ʾδ���ͣ�1��ʾ�Ѿ�����
    private String sendContent;
    private String smType;//��Ӧ��ҵ�����ƣ�������
    private String businessModule;//ҵ��ģ�飺
    private String objectId;
    
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSimIds() {
		return simIds;
	}
	public void setSimIds(String simIds) {
		this.simIds = simIds;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public String getSendState() {
		return sendState;
	}
	public void setSendState(String sendState) {
		this.sendState = sendState;
	}
	public String getSendContent() {
		return sendContent;
	}
	public void setSendContent(String sendContent) {
		this.sendContent = sendContent;
	}
	public String getSmType() {
		return smType;
	}
	public void setSmType(String smType) {
		this.smType = smType;
	}
	public String getBusinessModule() {
		return businessModule;
	}
	public void setBusinessModule(String businessModule) {
		this.businessModule = businessModule;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
}
