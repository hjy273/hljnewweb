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
	 * ����������ѯ��Ҫ��equipment
	 * @param params
	 * @return
	 */
	public  List queryEquipment(String sql){
		return dao.queryEquipment(sql);
	}
	
	/**
	 * 
	 * @param bean
	 * @return-2 ��ʾ�豸�Ѵ��ڣ�-1��ʾ��ѯʱ���ֵײ��쳣��1��ʾ���³ɹ�
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
