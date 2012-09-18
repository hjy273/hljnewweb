package com.cabletech.partmanage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.commons.hb.HibernateDaoSupport;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.linepatrol.material.beans.MaterialTypeBean;
import com.cabletech.partmanage.beans.MaterialInfoBean;

public class MaterialInfoDao extends HibernateDaoSupport {
	private static Logger logger = Logger.getLogger(MaterialInfoDao.class
			.getName());

	/**
	 * 根据类型获取规格
	 * 
	 * @return
	 */
	public List getModelByTypeId(String id) {
		List list = new ArrayList();
		String sql = "select a.id,a.modelname from lp_mt_model a ,lp_mt_type b where a.typeid=b.id and b.id="
				+ id ;
		try {
			QueryUtil query = new QueryUtil();
			list = query.queryBeans(sql);
		} catch (Exception e) {
			logger.error("根据类型获取规格 error:::::::::::" + e);
		}
		return list;
	}

	/**
	 * 获取规格信息
	 * 
	 * @return
	 */
	public List getModelList() {
		List list = new ArrayList();
		String sql = "select a.id,a.modelname from lp_mt_model a where a.state = '1'";
		try {
			QueryUtil query = new QueryUtil();
			list = query.queryBeans(sql);
		} catch (Exception e) {
			logger.error("query error:::::::::::" + e);
		}
		return list;
	}

	/**
	 * 获取类型
	 * 
	 * @return
	 */
	public List getTypeList() {
		List list = new ArrayList();
		String sql = "select a.id,a.typename from lp_mt_type a where a.state = '1'";
		try {
			QueryUtil query = new QueryUtil();
			list = query.queryBeans(sql);
		} catch (Exception e) {
			logger.error("query error:::::::::::" + e);
		}
		return list;
	}
	/**
	 * 修改时根据区域id与材料类型名称查询材料
	 * @param regionID
	 * @param itemName
	 * @return
	 */
	public List getMaterialTypesByBean(MaterialInfoBean bean){
		List list = new ArrayList();
		QueryUtil util = null;
		String sql = "select * from lp_mt_base lt where lt.state=1 and lt.name='"+bean.getName()+"' and lt.modelid='"+bean.getModelid()+"' and lt.id !='"+bean.getId()+"'";
		try {
			util = new QueryUtil();
			logger.info("查询材料类型："+sql);
			list = util.queryBeans(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}
	/**
	 * 修改时判断材料类型名称是否重复
	 * @param regionID
	 * @param itemName
	 * @return
	 */
	public boolean isHaveMaterialType(MaterialInfoBean bean){
		boolean flag=false;
		List list = getMaterialTypesByBean(bean);
		if(list==null || list.size()==0){
			flag = true;
		}
		return flag;
	}
	/**
	 * 根据材料名称与材料规格名称查询材料规格
	 * @param regionID
	 * @param typeName  材料类型名称
	 * @return
	 */
	public List getMByMAndMName(String name,String modelid){
		List list = new ArrayList();
		QueryUtil util = null;
		String sql = "select * from lp_mt_base lm where lm.state=1 and lm.name='"+name+"' and lm.modelid='"+modelid+"'";
		try {
			util = new QueryUtil();
			logger.info("查询材料信息："+sql);
			list = util.queryBeans(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}
	/**
	 * 增加时判断材料信息名称是否重复
	 * @param regionID
	 * @param itemName
	 * @return
	 */
	public boolean isHaveMaterialName(String name,String modelid){
		boolean flag=false;
		List list = getMByMAndMName(name, modelid);
		if(list==null || list.size()==0){
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
	public boolean addPartBase(MaterialInfoBean bean) {
		String sql = "insert into lp_mt_base(id,name,modelid,price,factory,remark,state) values(?,?,?,?,?,?,'1')";
		try {
			Connection conn = getSession().connection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, bean.getId());
			stmt.setString(2, bean.getName());
			stmt.setInt(3, bean.getModelid());
			stmt.setFloat(4, bean.getPrice());
			stmt.setString(5, bean.getFactory());		
			stmt.setString(6, bean.getRemark());
			stmt.executeUpdate();
			conn.commit();
			return true;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}

	/**
	 * 查询所有材料信息；
	 * 
	 * @param bean
	 * @return
	 */
	public List getPartBaseBean(MaterialInfoBean bean) {
		List list = new ArrayList();
		String sql = "select a.id,a.name,a.modelid,b.modelname,to_char(a.price) price,a.factory,a.remark from lp_mt_base a,lp_mt_model b where b.id = a.modelid ";
		if (bean != null) {
			if (bean.getId() != null && !"".equals(bean.getId())) {
				sql += " and a.id='" + bean.getId() + "'";
			}
			if (bean.getName() != null && !"".equals(bean.getName())) {
				sql += " and a.name like '" + "%" + bean.getName() + "%" + "'";
			}
			if (bean.getFactory() != null && !"".equals(bean.getFactory())) {
				sql += " and a.factory like '" + "%" + bean.getFactory() + "%"
						+ "'";
			}
			if (bean.getModelid() != 0) {
				sql += " and a.modelid='" + bean.getModelid() + "'";
			}
		}
		sql += " and a.state ='1' order by a.id desc";
		System.out.println("查询材料信息sql:" + sql);
		try {
			QueryUtil query = new QueryUtil();
			list = query.queryBeans(sql);
		} catch (Exception e) {
			logger.error("query lp_mt_model error:" + e);
		}
		return list;
	}

	/**
	 * 删除材料信息；
	 * 
	 * @param id
	 * @return
	 */
	public boolean deletePartbaseById(String id) {
		String sql = "update lp_mt_base a set a.state = '0' where id='"
				+ id + "'";
		try {
			UpdateUtil exec = new UpdateUtil();
			exec.setAutoCommitFalse();
			exec.executeUpdate(sql);
			exec.commit();
			exec.setAutoCommitTrue();
			return true;
		} catch (Exception e) {
			logger.error("delete lp_mt_model error:" + e);
			return false;
		}
	}

	/**
	 * 载入材料信息
	 * 
	 * @param id
	 * @param bean
	 * @return
	 */
	public MaterialInfoBean getPartBaseById(String id, MaterialInfoBean bean) {
		String sql = "select lt.id typeid, a.id,a.name,a.modelid,a.factory,a.remark from lp_mt_base a,lp_mt_type lt,lp_mt_model lm " +
				" where lt.id=lm.typeid and a.modelid=lm.id and a.id='"+ id + "'";
		ResultSet rs = null;
		try {
			QueryUtil query = new QueryUtil();
			logger.info("查询材料信息："+sql);
			rs = query.executeQuery(sql);
			if (rs.next()) {
				bean.setId(rs.getString("id"));
				bean.setName(rs.getString("name"));
				bean.setModelid(rs.getInt("modelid"));
				bean.setFactory(rs.getString("factory"));
				bean.setRemark(rs.getString("remark"));
				bean.setTypeid(rs.getString("typeid"));
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
		return bean;
	}

	/**
	 * 修改材料信息
	 * 
	 * @param bean
	 * @return
	 */
	public boolean updatePartBase(MaterialInfoBean bean) {
		String sql = "update lp_mt_base set name=?,modelid=?,factory=?,remark=? where id=?";
		try {
			Connection conn = getSession().connection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(5, bean.getId());
			stmt.setString(1, bean.getName());
			stmt.setInt(2, bean.getModelid());
			stmt.setString(3, bean.getFactory());
			stmt.setString(4, bean.getRemark());
			stmt.executeUpdate();
			conn.commit();
			System.out.println("sql::::::::::::::::" + sql);
			return true;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}
}
