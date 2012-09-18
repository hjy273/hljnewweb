package com.cabletech.linepatrol.material.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.material.domain.MaterialInfo;
import com.cabletech.linepatrol.remedy.dao.MaterialDao;

@Repository
public class MaterialInfoDao extends HibernateDao<MaterialInfo, Integer> {
	private static Logger logger = Logger.getLogger(MaterialInfoDao.class
			.getName());

	/**
	 * 执行判断修缮申请材料列表中材料否存在
	 * 
	 * @param materialList
	 *            List 修缮申请材料列表
	 * @return boolean 修缮申请材料列表中材料是否存在
	 * @throws Exception
	 */
	public boolean judgeExistMaterial(List materialList) throws Exception {
		String baseSql = "select id from LP_MT_BASE where state='1' ";
		String sql = "";
		List list;
		for (int i = 0; materialList != null && i < materialList.size(); i++) {
			sql = baseSql + " and id='" + materialList.get(i) + "' ";
			logger.info("Execute sql:" + sql);
			list = super.getJdbcTemplate().queryForBeans(sql);
			if (list == null || list.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 执行根据查询条件获取材料信息列表
	 * 
	 * @param condition
	 *            String 查询条件
	 * @return List 材料信息列表
	 * @throws Exception
	 */
	public List queryList(String condition) {
		// TODO Auto-generated method stub
		String sql = "select distinct t.id,t.name,t.modelid,to_char(t.price) as price,t.factory,t.state,tt.id, ";
		sql += "t.name||'（'||mt.modelname||'）（'||tt.typename ||'）' as material_name ";
		sql = sql + " from LP_MT_BASE t,LP_MT_MODEL mt,LP_MT_TYPE tt, ";
		sql = sql + " contractorinfo c,region r ";
		sql = sql
				+ " where t.modelid=mt.id and mt.typeid=tt.id and tt.regionid=r.regionid ";
		sql = sql + " and r.regionid=c.regionid and t.state='1' ";
		sql = sql + condition;
		logger.info("Execute sql:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * 执行根据查询条件获取材料信息列表
	 * 
	 * @param materialId
	 *            String 查询条件
	 * @return List 材料信息列表
	 * @throws Exception
	 */
	public String getMaterialName(String materialId) {
		// TODO Auto-generated method stub
		String sql = "select distinct t.id,t.name,t.modelid,to_char(t.price) as price,t.factory,t.state,tt.id, ";
		sql += "t.name||'（'||mt.modelname||'）（'||tt.typename ||'）' as material_name";
		sql = sql + " from LP_MT_BASE t,LP_MT_MODEL mt,LP_MT_TYPE tt ";
		sql = sql + " where t.modelid=mt.id and mt.typeid=tt.id ";
		sql = sql + " and t.id='" + materialId + "' ";
		logger.info("Execute sql:" + sql);
		List list = super.getJdbcTemplate().queryForBeans(sql);
		String materialName = "";
		if (list != null && !list.isEmpty()) {
			materialName = (String) ((DynaBean) list.get(0))
					.get("material_name");
		}
		return materialName;
	}

	/**
	 * 根据类型获取规格
	 * 
	 * @param user
	 * 
	 * @return
	 */
	public List getModelByTypeId(String id, UserInfo user) {
		String sql = "select a.id,a.modelname from LP_mt_model a ,LP_mt_type b where a.typeid=b.id and a.state='1' and b.id=?";
		sql += " and b.regionid='" + user.getRegionid() + "' ";
		logger.info("id:" + id);
		logger.info("SQL:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, id);
	}

	/**
	 * 获取规格信息
	 * 
	 * @param user
	 * 
	 * @return
	 */
	public List getModelList(UserInfo user) {
		String sql = "select a.id,a.modelname from LP_mt_model a,lp_mt_type lt ";
		sql += " where a.state = '1' and a.typeid=lt.id ";
		sql += " and lt.regionid='" + user.getRegionid() + "' ";
		logger.info("sql:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * 获取类型
	 * 
	 * @param user
	 * 
	 * @return
	 */
	public List getTypeList(UserInfo user) {
		String sql = "select a.id,a.typename from LP_mt_type a where a.state = '1'";
		sql += " and a.regionid='" + user.getRegionid() + "' ";
		logger.info("sql:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * 修改时根据区域id与材料类型名称查询材料
	 * 
	 * @param regionID
	 * @param itemName
	 * @return
	 */
	public List getMaterialTypesByBean(MaterialInfo materialInfo) {
		String sql = "select * from LP_mt_base lt where lt.state=1 and lt.name='"
				+ materialInfo.getName()
				+ "' and lt.modelid='"
				+ materialInfo.getModelid()
				+ "' and lt.id !='"
				+ materialInfo.getId() + "'";
		logger.info("查询材料类型：" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * 修改时判断材料类型名称是否重复
	 * 
	 * @param regionID
	 * @param itemName
	 * @return
	 */
	public boolean isHaveMaterialType(MaterialInfo materialInfo) {
		boolean flag = false;
		List list = getMaterialTypesByBean(materialInfo);
		if (list == null || list.size() == 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 根据材料名称与材料规格名称查询材料规格
	 * 
	 * @param regionID
	 * @param typeName
	 *            材料类型名称
	 * @return
	 */
	public List getMByMAndMName(String name, String modelid) {
		String sql = "select * from LP_mt_base lm where lm.state=1 and lm.name='"
				+ name + "' and lm.modelid='" + modelid + "'";
		logger.info("sql:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * 增加时判断材料信息名称是否重复
	 * 
	 * @param regionID
	 * @param itemName
	 * @return
	 */
	public boolean isHaveMaterialName(String name, String modelid) {
		boolean flag = false;
		List list = getMByMAndMName(name, modelid);
		if (list == null || list.size() == 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 添加材料信息
	 * 
	 * @param bean
	 * @return
	 */
	public void addPartBase(MaterialInfo materialInfo) {
		String sql = "insert into lp_mt_base(id,name,modelid,factory,remark,state) values('"
				+ materialInfo.getId()
				+ "','"
				+ materialInfo.getName()
				+ "','"
				+ materialInfo.getModelid()
				+ "','"
				+ materialInfo.getFactory()
				+ "','" + materialInfo.getRemark() + "','1')";
		logger.info("添加材料信息：" + sql);
		this.getJdbcTemplate().execute(sql);
	}

	/**
	 * 查询所有材料信息；
	 * 
	 * @param user
	 * 
	 * @param bean
	 * @return
	 */
	public List getPartBaseBean(MaterialInfo materialInfo, UserInfo user) {
		String sql = "select a.id,a.name,a.modelid,b.modelname,a.factory,a.remark,B.typeid,LT.typename ";
		sql += " from LP_mt_base a,LP_mt_model b,lp_mt_type lt ";
		sql += " where b.id = a.modelid and b.typeid=lt.id ";
		if (materialInfo != null) {
			if (materialInfo.getId() != null
					&& !"".equals(materialInfo.getId())) {
				sql += " and a.id='" + materialInfo.getId() + "'";
			}
			if (materialInfo.getName() != null
					&& !"".equals(materialInfo.getName())) {
				sql += " and a.name like '" + "%" + materialInfo.getName()
						+ "%" + "'";
			}
			if (materialInfo.getFactory() != null
					&& !"".equals(materialInfo.getFactory())) {
				sql += " and a.factory like '" + "%"
						+ materialInfo.getFactory() + "%" + "'";
			}
			if (materialInfo.getModelid() != 0) {
				sql += " and a.modelid='" + materialInfo.getModelid() + "'";
			}
		}
		sql += " and lt.regionid='" + user.getRegionid() + "' ";
		sql += " and a.state ='1' order by a.id desc";
		logger.info("查询材料信息sql:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * 删除材料信息；
	 * 
	 * @param id
	 * @return
	 */
	public void deletePartbaseById(String id) {
		String sql = "update LP_mt_base a set a.state = '0' where id='" + id
				+ "'";
		this.getJdbcTemplate().execute(sql);
	}

	/**
	 * 载入材料信息
	 * 
	 * @param id
	 * @param bean
	 * @return
	 */
	public MaterialInfo getPartBaseById(String id, MaterialInfo materialInfo) {
		String sql = "select lt.id typeid, a.id,a.name,a.modelid,a.factory,a.remark from LP_mt_base a,LP_mt_type lt,LP_mt_model lm "
				+ " where lt.id=lm.typeid and a.modelid=lm.id and a.id='"
				+ id
				+ "'";
		ResultSet rs = null;
		try {
			QueryUtil query = new QueryUtil();
			logger.info("查询材料信息：" + sql);
			rs = query.executeQuery(sql);
			if (rs.next()) {
				materialInfo.setId(rs.getString("id"));
				materialInfo.setName(rs.getString("name"));
				materialInfo.setModelid(rs.getInt("modelid"));
				materialInfo.setFactory(rs.getString("factory"));
				materialInfo.setRemark(rs.getString("remark"));
				materialInfo.setPrice(rs.getString("typeid"));
			}
			rs.close();
		} catch (Exception e) {
			logger.error(e);
			try {
				rs.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return materialInfo;
	}

	/**
	 * 修改材料信息
	 * 
	 * @param bean
	 * @return
	 */
	public void updatePartBase(MaterialInfo materialInfo) {
		String sql = "update LP_mt_base set name='" + materialInfo.getName()
				+ "',modelid='" + materialInfo.getModelid() + "',factory='"
				+ materialInfo.getFactory() + "',remark='"
				+ materialInfo.getRemark() + "' where id='"
				+ materialInfo.getId() + "'";
		this.getJdbcTemplate().execute(sql);
	}
}
