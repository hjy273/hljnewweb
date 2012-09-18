package com.cabletech.linepatrol.commons.module;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

/**
 * LpReplyApprover entity. @author MyEclipse Persistence Tools
 */

public class ReplyApprover {

	public static final String TRANSFER_APPROVE_RESULT = "2";
	// Fields

	private String id;
	private String objectId;
	private String objectType;
	private String approverId;
	private String approverType;// 01表示为审核人 02转审人，03表示为抄送人员
	private String isTransferApproved;// 1表示转审过，0没有表示没有转审
	private String approverName;// 审核人名字
	private String finishReaded = "0";// 0表示抄送人没有进行阅读，1表示抄送人已经进行阅读

	// Constructors

	/** default constructor */
	public ReplyApprover() {
	}

	/** minimal constructor */
	public ReplyApprover(String id) {
		this.id = id;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getObjectId() {
		return this.objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectType() {
		return this.objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getApproverId() {
		return this.approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}

	public String getApproverType() {
		return this.approverType;
	}

	public void setApproverType(String approverType) {
		this.approverType = approverType;
	}

	public String getIsTransferApproved() {
		return this.isTransferApproved;
	}

	public void setIsTransferApproved(String isTransferApproved) {
		this.isTransferApproved = isTransferApproved;
	}

	/**
	 * 执行从用户请求中获取审核人列表信息
	 * 
	 * @param bean
	 * @return
	 */
	public static List<ReplyApprover> packageList(BaseCommonBean bean) {
		List<ReplyApprover> list = new ArrayList<ReplyApprover>();
		String approverIds = "";
		if (BaseCommonBean.APPROVE_READ.equals(bean.getApproverType())) {
			approverIds = bean.getApprover();
		}
		if (BaseCommonBean.APPROVE_MAN.equals(bean.getApproverType())) {
			approverIds = bean.getReader();
		}
		if (approverIds != null && !approverIds.equals("")) {
			String[] approverId = approverIds.split(",");
			ReplyApprover approver;
			for (int i = 0; approverId != null && i < approverId.length; i++) {
				approver = new ReplyApprover();
				approver.setObjectId(bean.getObjectId());
				approver.setObjectType(bean.getObjectType());
				approver.setApproverType(bean.getApproverType());
				approver.setApproverId(approverId[i]);
				approver.setIsTransferApproved(bean.getIsTransferApproved());
				list.add(approver);
			}
		}
		return list;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public String getFinishReaded() {
		return finishReaded;
	}

	public void setFinishReaded(String finishReaded) {
		this.finishReaded = finishReaded;
	}

}