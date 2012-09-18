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
	 * 执行根据查询条件获取材料规格信息列表
	 * 
	 * @param condition
	 *            String 查询条件
	 * @return List 材料信息列表
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
	 * 查询区域
	 * 
	 * @param user
	 * @return
	 */
	public List<Region> getRegions(UserInfo user) {
		String sql = "select r.regionname,r.regionid from region r where r.state is null and r.regionid=?";
		logger.info("regionid:" + user.getRegionid());
		logger.info("查询区域信息：" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, user.getRegionid());
	}

	/**
	 * 查询区域Name
	 * 
	 * @param id
	 * @return
	 */
	public String getRegionNameById(String regionId) {
		String regionName = "";
		String sql = "select r.regionname from region r where r.regionid=?";
		logger.info("regionId" + regionId);
		logger.info("查询区域：" + sql);
		List list = this.getJdbcTemplate().queryForBeans(sql, regionId);
		if (list != null && list.size() > 0) {
			BasicDynaBean bean = (BasicDynaBean) list.get(0);
			regionName = (String) bean.get("regionname");
		}
		return regionName;
	}

	/**
	 * 根据区域id与材料类型名称查询材料类型
	 * 
	 * @param regionID
	 * @param typeName
	 *            材料类型名称
	 * @return
	 */
	public List getTypesByRIDAndIName(String regionID, String typeName) {
		String sql = "select * from lp_mt_type lt where lt.state=1 and lt.typename=? and lt.regionid=?";
		logger.info("typeName:" + typeName + "  regionID:" + regionID);
		logger.info("根据区域id与材料类型名称查询材料类型:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, typeName, regionID);
	}

	/**
	 * 修改时根据区域id与材料类型名称查询材料
	 * 
	 * @param regionID
	 * @param itemName
	 * @return
	 */
	public List getMaterialTypesByBean(MaterialTypeBean bean) {
		String sql = "select * from lp_mt_type lt where lt.state=1 and lt.typename=? and lt.regionid=? and lt.id !=?";
		logger.info("typeName:" + bean.getTypeName() + "  regionID:"
				+ bean.getRegionID() + "  tid:" + bean.getTid());
		logger.info("查询材料类型：" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, bean.getTypeName(),
				bean.getRegionID(), bean.getTid());
	}

	/**
	 * 保存修缮项目
	 * 
	 * @param bean
	 * @return
	 */
	public MaterialType addMeterialType(MaterialType type) {
		this.save(type);
		return type;
	}

	/**
	 * 根据条件查询材料类型
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

			logger.info("查询材料类型sql：" + sb.toString());
			list = this.getJdbcTemplate().queryForBeans(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 根据id查询材料类型
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
			logger.info("查询材料类型：" + sql);
			list = util.queryBeans(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 修改材料类型
	 * 
	 * @param bean
	 * @return
	 */
	public void editMaterialType(MaterialType type) {
		this.save(type);
	}

	// TODO
	/**
	 * 根据id删除材料类型,同时删除其下材料规格,删除规格下的材料
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
		 * logger.info("获得模型：" + sql); List modelList =
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
			logger.info("删除材料类型：" + sbtype.toString());
			this.getJdbcTemplate().execute(sbtype.toString());
			// update.executeUpdate(sbtype.toString());
			StringBuffer models = new StringBuffer();
			models.append("select id from lp_mt_model ");
			models.append(" where typeid='" + id + "'");
			logger.info("查询材料规格：" + models.toString());
			List list = this.getJdbcTemplate().queryForBeans(models.toString());
			// query.queryBeans(models.toString());
			String modelids = parseModelids(list);
			StringBuffer sbmodel = new StringBuffer();
			sbmodel.append("update  lp_mt_model ");
			sbmodel.append("set state='0' ");
			sbmodel.append(" where typeid='" + id + "'");
			logger.info("删除材料规格：" + sbmodel.toString());
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
				logger.info("删除材料：" + sb.toString());
				// update.executeUpdate(sb.toString());
				this.getJdbcTemplate().execute(sb.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// TODO
	/**
	 * 解析list为字符串 以‘’引起参数
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
