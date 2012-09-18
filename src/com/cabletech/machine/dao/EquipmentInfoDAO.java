package com.cabletech.machine.dao;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.machine.beans.EquipmentInfoBean;

public class EquipmentInfoDAO {
	private static Logger logger = Logger.getLogger(EquipmentInfoDAO.class
			.getName());

	/**
	 * ���ӻ����豸������Ϣ
	 * 
	 * @param bean
	 * @return
	 */
	public boolean addEquipment(EquipmentInfoBean bean, String eid) {
		//OracleIDImpl ora = new OracleIDImpl();
		//String[] eid = ora.getSeq("equipment_info", 20);
		String sql = "insert into equipment_info(eid,equipment_name,contractor_id,layer) values('"
				+ eid
				+ "','"
				+ bean.getEquipmentName()
				+ "','"
				+ bean.getContractorId() + "','" + bean.getLayer() + "')";
		UpdateUtil update = null;
		logger.info(sql);
		try {
			update = new UpdateUtil();
			update.executeUpdate(sql);
			return true;
		} catch (Exception e) {
			logger.error("���ӻ����豸������Ϣ����:" + e.getMessage());
		}
		return false;
	}

/**
 * ��������
 * @param list
 * @return
 */	
	public boolean addEquipment(List list) {
		OracleIDImpl ora = new OracleIDImpl();
		String[] eids = ora.getSeqs("equipment_info", 20, list.size());
		
		UpdateUtil update = null;
		try {
			Iterator iter = list.iterator();
			update = new UpdateUtil();
			update.setAutoCommitFalse();
			int i = 0;
			while(iter.hasNext()){
				EquipmentInfoBean bean = (EquipmentInfoBean)iter.next();
				String sql = "insert into equipment_info(eid,equipment_name,contractor_id,layer) values('"
					+ eids[i++]
					+ "','"
					+ bean.getEquipmentName()
					+ "','"
					+ bean.getContractorId() + "','" + bean.getLayer() + "')";
				update.executeUpdate(sql);
			}
			update.commit();
			update.setAutoCommitTrue();
			return true;
		} catch (Exception e) {
			update.rollback();
			logger.error("���ӻ����豸������Ϣ����:" + e.getMessage());
		}
		return false;
	}
	/**
	 * �����豸
	 * 
	 * @param bean
	 * @return -2 ��ʾ�豸�Ѵ��ڣ�-1��ʾ��ѯʱ���ֵײ��쳣��1��ʾ���³ɹ�
	 */
	public int updateEquipment(EquipmentInfoBean bean) {

		String validateSql = "select t.equipment_name,t.contractor_id,t.layer from equipment_info t where t.equipment_name='"
				+ bean.getEquipmentName()
				+ "' and t.contractor_id='"
				+ bean.getContractorId()
				+ "' and t.layer='"
				+ bean.getLayer()
				+ "'";
		try {
			QueryUtil queryUtil = new QueryUtil();

			logger.info("select equipment sql=" + validateSql);
			List list = queryUtil.queryBeans(validateSql);
			if (list != null && list.size() > 0)
				return -2;
			else{
				String sql = "update equipment_info set equipment_name='"
					+ bean.getEquipmentName() + "',contractor_id='"
					+ bean.getContractorId() + "',layer='" + bean.getLayer()
					+ "' where eid = '" + bean.getEid() + "'";
				
				UpdateUtil exec = new UpdateUtil();
				exec.executeUpdate(sql);
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("EquipmentInfoDao->updateEquipment->update equipment error");
			return -1;
		}
	}

	/**
	 * ����ID��ö�Ӧ��equipmentInfoBean
	 * 
	 * @param eid
	 * @return
	 */
	public EquipmentInfoBean getEquipmentById(String eid) {
		String sql = "select t.eid,t.equipment_name,t.contractor_id,t.layer from equipment_info t where t.eid='"
				+ eid + "'";

		try {
			QueryUtil queryUtil = new QueryUtil();
			ResultSet rs = queryUtil.executeQuery(sql);
			EquipmentInfoBean bean = null;
			while (rs.next()) {
				bean = new EquipmentInfoBean();
				bean.setEid(rs.getString("eid"));
				bean.setEquipmentName(rs.getString("equipment_name"));
				bean.setContractorId(rs.getString("contractor_id"));
				bean.setLayer(rs.getString("layer"));
			}
			return bean;
		} catch (Exception e) {
			logger.info("���equipmentʧ�ܣ�idΪ:" + eid);
		}
		return null;
	}

	public List getAllEquipmentList() {
		String sql = "select t.eid,t.equipment_name,t.contractor_id,t.layer from equipment_info t";
		try {
			QueryUtil queryUtil = new QueryUtil();
			List list = queryUtil.queryBeans(sql);
			return list;
		} catch (Exception e) {
			logger.info("EquipmentInfoDAO->getAllEquipmentList ʧ��");
		}
		return null;
	}

	/**
	 * ɾ���豸
	 * 
	 * @param eid
	 * @return
	 */
	public boolean delEquipment(String eid) {
		String sql = "delete equipment_info where eid='" + eid + "'";
		try {
			UpdateUtil exec = new UpdateUtil();
			exec.executeUpdate(sql);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * ����������ѯ��Ҫ��equipment
	 * 
	 * @param params
	 * @return
	 */
	public List queryEquipment(String sql) {
		List equipments = null;
		try {
			QueryUtil qu = new QueryUtil();
			equipments = qu.queryBeans(sql);
		} catch (Exception e) {
			logger.error("��ȡ�豸�б�����쳣:" + e.getMessage());
		}

		logger.info("EquipmentInfoDAO->queryEquipment->" + sql.toString());
		return equipments;
	}
}
