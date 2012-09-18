package com.cabletech.linepatrol.material.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.material.beans.MaterialModelBean;
import com.cabletech.linepatrol.material.domain.MaterialModel;

@Repository
public class MaterialModelDao extends HibernateDao<MaterialModel, Integer> {
	private static Logger logger = Logger.getLogger(MaterialModelDao.class
			.getName());

	/**
	 * ִ�и��ݲ�ѯ������ȡ����������Ϣ�б�
	 * 
	 * @param condition
	 *            String ��ѯ����
	 * @return List ����������Ϣ�б�
	 * @throws Exception
	 */
	public List queryList(String condition) {
		// TODO Auto-generated method stub
		String sql = "select distinct t.id,t.typeid,t.modelname,t.unit,t.state ";
		sql = sql
				+ " from LP_MT_MODEL t,LP_MT_TYPE mt,contractorinfo c,region r ";
		sql = sql
				+ " where t.typeid=mt.id and mt.regionid=r.regionid and r.regionid=c.regionid and t.state='1' ";
		sql = sql + condition;
		logger.info("Execute sql:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * �����û�regionid�õ���������
	 * 
	 * @param user
	 * @return
	 */
	public List getTypesByRegionID(UserInfo user) {
		String sql = "select lt.id,lt.typename from LP_mt_type lt where lt.state=1 "
				+ "and lt.regionid=? order by lt.typename";
		logger.info("regionid:" + user.getRegionid());
		logger.info("��ѯ�������ͣ�" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, user.getRegionid());
	}

	/**
	 * ��ѯ����
	 * 
	 * @param user
	 * @return
	 */
	public List getRegions(UserInfo user) {
		String sql = "select r.regionname,r.regionid from region r where r.state is null and r.regionid=?";
		logger.info("regionid" + user.getRegionid());
		logger.info("��ѯ����" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, user.getRegionid());
	}

	/**
	 * ��ѯ����Name
	 * 
	 * @param id
	 * @return
	 */
	public String getRegionNameById(String regionId) {
		String regionName = "";
		String sql = "select r.regionname from region r where r.regionid=?";
		logger.info("regionId" + regionId);
		logger.info("��ѯ����" + sql);
		List list = this.getJdbcTemplate().queryForBeans(sql, regionId);
		if (list != null && list.size() > 0) {
			BasicDynaBean bean = (BasicDynaBean) list.get(0);
			regionName = (String) bean.get("regionname");
		}
		return regionName;
	}

	/**
	 * ���ݲ�����������Ϲ�����Ʋ�ѯ���Ϲ��
	 * 
	 * @param regionID
	 * @param typeName
	 *            ������������
	 * @return
	 */
	public List getModelssByTIDAndMName(int typeid, String modelname) {
		String sql = "select * from LP_mt_model lm where lm.state=1 and lm.typeid=? and lm.modelname=?";
		logger.info("typeid:" + typeid + "  " + "modelname:" + modelname);
		logger.info("��ѯ���Ϲ��" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, typeid, modelname);

	}

	/**
	 * �޸�ʱ��������id����Ϲ�����Ʋ�ѯ���
	 * 
	 * @param regionID
	 * @param itemName
	 * @return
	 */
	public List getMaterialModelsByBean(MaterialModelBean bean) {
		String sql = "select * from LP_mt_model lm where lm.state=1 "
				+ "and lm.modelname=? and lm.typeid=? and lm.id !=?";
		logger.info("modelname:" + bean.getModelName() + "  deptid:"
				+ bean.getTypeID() + "  tid:" + bean.getTid());
		logger.info("��ѯ���Ϲ��" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, bean.getModelName(),
				bean.getTypeID(), bean.getTid());
	}

	/**
	 * ������Ϲ��
	 * 
	 * @param model
	 * @return
	 */
	public void addMaterialModel(MaterialModel model) {
		boolean flag = true;
		String sql = "insert into LP_mt_model(id,typeid,modelname,unit,state,remark)"
				+ " values("
				+ model.getTid()
				+ ","
				+ model.getTypeID()
				+ ",'"
				+ model.getModelName()
				+ "','"
				+ model.getUnit()
				+ "',"
				+ 1
				+ ",'" + model.getRemark() + "')";
		logger.info("���Ӳ��Ϲ��" + sql);
		this.getJdbcTemplate().execute(sql);
	}

	/**
	 * ����������ѯ���Ϲ��
	 * 
	 * @param user
	 */
	public List getMaterialModels(MaterialModelBean bean, UserInfo user) {
		StringBuffer sb = new StringBuffer();
		String modelName = bean.getModelName();
		int typeID = bean.getTypeID();
		sb.append("select lm.id,lm.modelname,lm.unit,lt.typename,lm.typeid ,");
		sb
				.append("(select count(*) from LP_mt_base base where base.modelid=lm.id and base.state='1') bnum");
		sb.append(" from LP_mt_model lm,LP_mt_type lt");
		sb.append(" where lm.state=1 and lm.typeid=lt.id ");
		if (modelName != null && !modelName.trim().equals("")) {
			sb.append(" and lm.modelname like '%" + modelName + "%' ");
		}
		if (typeID != -1) {
			sb.append(" and lm.typeid=" + typeID + "");
		}
		sb.append(" and lt.regionid='" + user.getRegionid() + "' ");
		sb.append(" order by lm.typeid,lm.modelname");

		logger.info("��ѯ���Ϲ��sql��" + sb.toString());
		return this.getJdbcTemplate().queryForBeans(sb.toString());
	}

	/**
	 * ����id��ѯ���Ϲ��
	 * 
	 * @param id
	 * @return
	 */
	public List getMaterialModelById(String id) {
		String sql = "select lm.id,lm.typeid,lm.modelname,lm.unit,lm.remark,lt.typename from "
				+ "LP_mt_type lt,LP_mt_model lm "
				+ " where lm.typeid=lt.id and lm.id='" + id + "'";
		logger.info("��ѯ���Ϲ��" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * �޸Ĳ��Ϲ��
	 * 
	 * @param bean
	 * @return
	 */
	public void editMaterialModel(MaterialModel model) {
		StringBuffer sb = new StringBuffer();
		sb.append("update  LP_mt_model ");
		sb.append("set modelname='" + model.getModelName() + "',typeid="
				+ model.getTypeID() + ",");
		sb.append(" remark='" + model.getRemark() + "' ,unit='"
				+ model.getUnit() + "'");
		sb.append(" where id='" + model.getTid() + "'");
		logger.info("�޸Ĳ��Ϲ��" + sb.toString());
		this.getJdbcTemplate().execute(sb.toString());
	}

	/**
	 * ����idɾ�����Ϲ��,ͬʱɾ�����²���
	 * 
	 * @param id
	 * @return
	 */
	public void deleteModel(String id) {
		StringBuffer sb = new StringBuffer();
		sb.append("update  LP_mt_model ");
		sb.append("set state='0' ");
		sb.append(" where id='" + id + "'");
		logger.info("ɾ�����Ϲ��" + sb.toString());
		this.getJdbcTemplate().execute(sb.toString());
		StringBuffer sbtype = new StringBuffer();
		sbtype.append("update  LP_MT_BASE ");
		sbtype.append("set state='0' ");
		sbtype.append(" where modelid='" + id + "'");
		this.getJdbcTemplate().execute(sbtype.toString());
	}

}
