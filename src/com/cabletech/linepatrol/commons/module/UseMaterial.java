package com.cabletech.linepatrol.commons.module;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cabletech.commons.base.BaseDomainObject;
import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

/**
 * LpApproveInfo entity. @author MyEclipse Persistence Tools
 */

public class UseMaterial extends BaseDomainObject {

	// Fields

	private String id;
	private String objectId;
	private String useType;
	private String materialId;
	private String storageAddressId;
	private String storageType;
	private double usedNumber;

	// Constructors

	/** default constructor */
	public UseMaterial() {
	}

	/** minimal constructor */
	public UseMaterial(String id) {
		this.id = id;
	}

	/** full constructor */
	public UseMaterial(String id, String objectId, String useType,
			String materialId, String storageAddressId, String storageType,
			double usedNumber) {
		this.id = id;
		this.objectId = objectId;
		this.useType = useType;
		this.materialId = materialId;
		this.storageAddressId = storageAddressId;
		this.storageType = storageType;
		this.usedNumber = usedNumber;
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

	public double getUsedNumber() {
		return usedNumber;
	}

	public void setUsedNumber(double usedNumber) {
		this.usedNumber = usedNumber;
	}

	/**
	 * 执行从用户请求中获取使用材料列表信息
	 * 
	 * @param bean
	 * @param useType
	 * @return
	 */
	public static List<UseMaterial> packageList(String useType,
			BaseCommonBean bean) {
		List<UseMaterial> list = new ArrayList<UseMaterial>();
		String[] materialId = bean.getMaterialUse();
		String[] storageType = bean.getMaterialStorageTypeUse();
		String[] storageAddressId = bean.getMaterialStorageAddrUse();
		String[] usedNumberStr = bean.getMaterialUseNumberUse();
		UseMaterial oneMaterial;
		for (int i = 0; materialId != null && i < materialId.length; i++) {
			oneMaterial = new UseMaterial();
			oneMaterial.setObjectId(bean.getObjectId());
			oneMaterial.setUseType(useType);
			oneMaterial.setMaterialId(materialId[i]);
			oneMaterial.setStorageAddressId(storageAddressId[i]);
			oneMaterial.setStorageType(storageType[i]);
			oneMaterial.setUsedNumber(Double.parseDouble(usedNumberStr[i]));
			list.add(oneMaterial);
		}

		return list;
	}
}