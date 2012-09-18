package com.cabletech.linepatrol.commons.module;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cabletech.commons.base.BaseDomainObject;
import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

/**
 * LpApproveInfo entity. @author MyEclipse Persistence Tools
 */

public class ReturnMaterial extends BaseDomainObject {

	// Fields

	private String id;
	private String objectId;
	private String useType;
	private String materialId;
	private String storageAddressId;
	private String storageType;
	private double returnNumber;

	// Constructors

	/** default constructor */
	public ReturnMaterial() {
	}

	/** minimal constructor */
	public ReturnMaterial(String id) {
		this.id = id;
	}

	/** full constructor */
	public ReturnMaterial(String id, String objectId, String useType,
			String materialId, String storageAddressId, String storageType,
			double usedNumber) {
		this.id = id;
		this.objectId = objectId;
		this.useType = useType;
		this.materialId = materialId;
		this.storageAddressId = storageAddressId;
		this.storageType = storageType;
		this.returnNumber = usedNumber;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public String getStorageAddressId() {
		return storageAddressId;
	}

	public void setStorageAddressId(String storageAddressId) {
		this.storageAddressId = storageAddressId;
	}

	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	public double getReturnNumber() {
		return returnNumber;
	}

	public void setReturnNumber(double returnNumber) {
		this.returnNumber = returnNumber;
	}

	/**
	 * 执行从用户请求中获取回收材料列表信息
	 * 
	 * @param objectId
	 * @param useType
	 * @param request
	 * @return
	 */
	public static List<ReturnMaterial> packageList(BaseCommonBean bean,
			String useType) {
		List<ReturnMaterial> list = new ArrayList<ReturnMaterial>();
		String[] materialId = bean.getMaterialRecycle();
		String[] storageType = bean.getMaterialStorageTypeRecycle();
		String[] storageAddressId = bean.getMaterialStorageAddrRecycle();
		String[] returnNumberStr = bean.getMaterialUseNumberRecycle();
		ReturnMaterial oneMaterial;
		for (int i = 0; materialId != null && i < materialId.length; i++) {
			oneMaterial = new ReturnMaterial();
			oneMaterial.setObjectId(bean.getObjectId());
			oneMaterial.setUseType(useType);
			oneMaterial.setMaterialId(materialId[i]);
			oneMaterial.setStorageAddressId(storageAddressId[i]);
			oneMaterial.setStorageType(storageType[i]);
			oneMaterial.setReturnNumber(Double.parseDouble(returnNumberStr[i]));
			list.add(oneMaterial);
		}

		return list;
	}
}