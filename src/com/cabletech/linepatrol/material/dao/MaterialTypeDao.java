package com.cabletech.linepatrol.material.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.Region;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.material.beans.MaterialTypeBean;
import com.cabletech.linepatrol.material.domain.MaterialType;

@Repository
public class MaterialTypeDao extends HibernateDao<MaterialType, Integer> {
	private static Logger logger = Logger.getLogger(MaterialTypeDao.class
			.getName());

	/**
	 * ִ�и��ݲ�ѯ������ȡ���Ϲ����Ϣ�б�
	 * 
	 * @param condition
	 *            String ��ѯ����
	 * @return List ������Ϣ�б�
	 * @throws Exception
	 */
	public List queryList(String condition) {
		// TODO Auto-generated method stub
		String sql = "select distinct t.id,t.typename,t.state,t.regionid ";
		sql = sql + " from LP_MT_TYPE t,contractorinfo c,region r ";
		sql = sql
				+ " where t.regionid=r.regionid and c.regionid=r.regionid and t.state='1' ";
		sql = sql + condition;
		logger.info("Execute sql:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * ��ѯ����
	 * 
	 * @param user
	 * @return
	 */
	public List<Region> getRegions(UserInfo user) {
		String sql = "select r.regionname,r.regionid from region r where r.state is null and r.regionid=?";
		logger.info("regionid:" + user.getRegionid());
		logger.info("��ѯ������Ϣ��" + sql);
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
	 * ��������id������������Ʋ�ѯ��������
	 * 
	 * @param regionID
	 * @param typeName
	 *            ������������
	 * @return
	 */
	public List getTypesByRIDAndIName(String regionID, String typeName) {
		String sql = "select * from lp_mt_type lt where lt.state=1 and lt.typename=? and lt.regionid=?";
		logger.info("typeName:" + typeName + "  regionID:" + regionID);
		logger.info("��������id������������Ʋ�ѯ��������:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, typeName, regionID);
	}

	/**
	 * �޸�ʱ��������id������������Ʋ�ѯ����
	 * 
	 * @param regionID
	 * @param itemName
	 * @return
	 */
	public List getMaterialTypesByBean(MaterialTypeBean bean) {
		String sql = "select * from lp_mt_type lt where lt.state=1 and lt.typename=? and lt.regionid=? and lt.id !=?";
		logger.info("typeName:" + bean.getTypeName() + "  regionID:"
				+ bean.getRegionID() + "  tid:" + bean.getTid());
		logger.info("��ѯ�������ͣ�" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, bean.getTypeName(),
				bean.getRegionID(), bean.getTid());
	}

	/**
	 * ����������Ŀ
	 * 
	 * @param bean
	 * @return
	 */
	public MaterialType addMeterialType(MaterialType type) {
		this.save(type);
		return type;
	}

	/**
	 * ����������ѯ��������
	 * 
	 * @param user
	 */
	public List getMaterialTypes(MaterialTypeBean bean, UserInfo user) {
		List list = new ArrayList();
		StringBuffer sb = new StringBuffer();
		String typeName = bean.getTypeName();
		String regionID = bean.getRegionID();
		try {
			sb.append("select lt.id,lt.typename,lt.regionid,r.regionname, ");
			sb
					.append("(select count(*) from lp_mt_model lm where lm.typeid=lt.id and lm.state='1') tnum ,");
			sb.append("(select count(*) from lp_mt_base base,lp_mt_model lm");
			sb
					.append(" where lm.typeid=lt.id and lm.state='1' and base.modelid=lm.id and base.state='1') bnum");
			sb.append(" from lp_mt_type lt,region r");
			sb
					.append(" where lt.state=1 and r.state is null and r.regionid=lt.regionid");
			if (typeName != null && !typeName.trim().equals("")) {
				sb.append(" and lt.typename like '%" + typeName + "%' ");
			}
			if (regionID != null && !regionID.trim().equals("")) {
				sb.append(" and lt.regionid='" + regionID + "'");
			}
			sb.append(" and lt.regionid='" + user.getRegionid() + "' ");
			sb.append(" order by lt.regionid,lt.typename");

			logger.info("��ѯ��������sql��" + sb.toString());
			list = this.getJdbcTemplate().queryForBeans(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * ����id��ѯ��������
	 * 
	 * @param id
	 * @return
	 */
	public List getMaterialTypById(String id) {
		List list = new ArrayList();
		QueryUtil util = null;
		String sql = "select * from lp_mt_type lt where lt.id='" + id + "'";
		try {
			util = new QueryUtil();
			logger.info("��ѯ�������ͣ�" + sql);
			list = util.queryBeans(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * �޸Ĳ�������
	 * 
	 * @param bean
	 * @return
	 */
	public void editMaterialType(MaterialType type) {
		this.save(type);
	}

	// TODO
	/**
	 * ����idɾ����������,ͬʱɾ�����²��Ϲ��,ɾ������µĲ���
	 * 
	 * @param id
	 * @return
	 */
	public void deleteType(String id) {
		/*
		 * Integer typeid = Integer.parseInt(id); List<MaterialType> lists =
		 * this.getMaterialTypById(id); MaterialType type = lists.get(0);
		 * type.setState("0"); this.save(type); String sql =
		 * "select id from lp_mt_model where typeid='" + id +"'";
		 * logger.info("���ģ�ͣ�" + sql); List modelList =
		 * this.getJdbcTemplate().queryForBeans(sql); String modelids =
		 * parseModelids(modelList); String hql =
		 * "update MaterialModel model set model.state='0' where model.tid=?";
		 * this.batchExecute(hql, modelids); if(modelids!=null
		 * &&modelids.trim().length()>0){ String hqlmaterial =
		 * "update MaterialInfo material set material.state='0' where material.modelid in(?)"
		 * ; this.batchExecute(hqlmaterial, modelids); }
		 */

		boolean flag = true;
		// UpdateUtil update = null;
		try {
			StringBuffer sbtype = new StringBuffer();
			sbtype.append("update  lp_mt_type ");
			sbtype.append("set state='0' ");
			sbtype.append(" where id='" + id + "'");
			/*
			 * update = new UpdateUtil(); update.setAutoCommitFalse();
			 */
			logger.info("ɾ���������ͣ�" + sbtype.toString());
			this.getJdbcTemplate().execute(sbtype.toString());
			// update.executeUpdate(sbtype.toString());
			StringBuffer models = new StringBuffer();
			models.append("select id from lp_mt_model ");
			models.append(" where typeid='" + id + "'");
			logger.info("��ѯ���Ϲ��" + models.toString());
			List list = this.getJdbcTemplate().queryForBeans(models.toString());
			// query.queryBeans(models.toString());
			String modelids = parseModelids(list);
			StringBuffer sbmodel = new StringBuffer();
			sbmodel.append("update  lp_mt_model ");
			sbmodel.append("set state='0' ");
			sbmodel.append(" where typeid='" + id + "'");
			logger.info("ɾ�����Ϲ��" + sbmodel.toString());
			/*
			 * update.executeUpdate(sbmodel.toString());
			 * update.setAutoCommitTrue();
			 */
			this.getJdbcTemplate().execute(sbmodel.toString());
			if (modelids != null && modelids.trim().length() > 0) {
				System.out.println("modelids=======================: "
						+ modelids);
				StringBuffer sb = new StringBuffer();
				sb.append("update  lp_MT_BASE ");
				sb.append("set state='0' ");
				sb.append(" where modelid in(" + modelids + ")");
				logger.info("ɾ�����ϣ�" + sb.toString());
				// update.executeUpdate(sb.toString());
				this.getJdbcTemplate().execute(sb.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// TODO
	/**
	 * ����listΪ�ַ��� �ԡ����������
	 */
	public String parseModelids(List list) {
		String modelids = "";
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				BasicDynaBean bean = (BasicDynaBean) list.get(i);
				BigDecimal id = (BigDecimal) bean.get("id");
				if (list.size() == 1 || i == list.size() - 1) {
					modelids += "" + id + "";
				} else {
					modelids += id + ",";
				}
			}
		}
		return modelids;
	}
}
