package com.cabletech.machine.services;

import java.util.List;

import com.cabletech.machine.beans.EquipmentInfoBean;
import com.cabletech.machine.dao.EquipmentInfoDAO;

public class EquipmentInfoBO {
	private EquipmentInfoDAO dao = new EquipmentInfoDAO();
	
	public boolean addEquipment(EquipmentInfoBean bean, String eid) {
		return dao.addEquipment(bean , eid);
	}
	
	
	public boolean addEquipment(List list) {
		return dao.addEquipment(list);
	}
	/**
	 * 根据条件查询需要的equipment
	 * @param params
	 * @return
	 */
	public  List queryEquipment(String sql){
		return dao.queryEquipment(sql);
	}
	
	/**
	 * 
	 * @param bean
	 * @return-2 表示设备已存在，-1表示查询时出现底层异常，1表示更新成功
	 */
	public int updateEquipment(EquipmentInfoBean bean){
		return dao.updateEquipment(bean);
	}
	
	public EquipmentInfoBean getEquipmentById(String eid){
		return dao.getEquipmentById(eid);
	}
	
	public boolean delEquipment(String eid){
		return dao.delEquipment(eid);
	}
}
