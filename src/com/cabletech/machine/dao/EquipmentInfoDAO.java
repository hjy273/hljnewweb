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
	 * 增加机房设备基本信息
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
			logger.error("增加机房设备基本信息出错:" + e.getMessage());
		}
		return false;
	}

/**
 * 批量增加
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
			logger.error("增加机房设备基本信息出错:" + e.getMessage());
		}
		return false;
	}
	/**
	 * 更新设备
	 * 
	 * @param bean
	 * @return -2 表示设备已存在，-1表示查询时出现底层异常，1表示更新成功
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
	 * 根据ID获得对应的equipmentInfoBean
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
			logger.info("获得equipment失败，id为:" + eid);
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
			logger.info("EquipmentInfoDAO->getAllEquipmentList 失败");
		}
		return null;
	}

	/**
	 * 删除设备
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
	 * 根据条件查询需要的equipment
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
			logger.error("获取设备列表出现异常:" + e.getMessage());
		}

		logger.info("EquipmentInfoDAO->queryEquipment->" + sql.toString());
		return equipments;
	}
}
