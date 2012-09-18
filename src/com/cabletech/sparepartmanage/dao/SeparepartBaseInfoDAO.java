package com.cabletech.sparepartmanage.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.cabletech.commons.config.ConfigPathUtil;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.HibernateDaoSupport;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.sparepartmanage.beans.SparepartBaseInfoBean;
import com.cabletech.sparepartmanage.beans.SparepartStorageBean;

public class SeparepartBaseInfoDAO extends HibernateDaoSupport{
	private static Logger logger = Logger.getLogger(SeparepartBaseInfoDAO.class
			.getName());

	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	public boolean  judgeIsHaveSparePart(SparepartBaseInfoBean bean,String act){
		StringBuffer sql = new StringBuffer();
		sql.append("select * from spare_part_baseinfo s where s.spare_part_name='"+bean.getSparePartName()+"' " );
		sql.append(" and s.spare_part_model='"+bean.getSparePartModel()+"' ");
		sql.append(" and s.software_version='"+bean.getSoftwareVersion()+"' ");
		sql.append(" and s.product_factory='"+bean.getProductFactory()+"' ");	
		if(act.equals("edit")){
			sql.append(" and tid !='"+bean.getSparePartId()+"'");
		}
		QueryUtil  query = null;
		List list = null;
		try{
			query = new QueryUtil();
			System.out.println("judgeIsHaveSparePart sql:"+sql.toString());
			list = query.queryBeans(sql.toString());
			if(list !=null && list.size()>0){
				return true;
			}
		}catch(Exception e){
			System.out.println("judgeIsHaveSparePart 出现异常:"+e.getMessage());
			e.getStackTrace();
		}
		return false;
	}
	

	public boolean addSeparepart(SparepartBaseInfoBean bean) {
		OracleIDImpl ora = new OracleIDImpl();
		String tid = ora.getSeq("spare_part_baseinfo", 20);
		bean.setSparePartId(tid);
		try {
			this.getHibernateTemplate().save(bean);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("增加备件出错:" + e.getMessage());
			return false;
		}
	}

	// 获取所有的生产厂商
	public List getAllFactory(String sql) {
		List factorys = null;
		try {
			factorys = this.getHibernateTemplate().find(sql);			
		} catch (Exception e) {
			logger.info("获取所有生产厂商出现异常："+e.getMessage());
			e.printStackTrace();
		}
		return factorys;
	}	
	/**
	 * 根据选中的厂商获取备件名称
	 * 
	 * @param fac
	 * @return
	 */	
	public List getNameByFactory(String fac) {
		String sql = "";
		QueryUtil query = null;
		List nameList = null;
		sql = "select distinct spb.spare_part_name from spare_part_baseinfo spb where spb.product_factory='"
			+ fac + "' order by spb.spare_part_name desc";
		try {
			query = new QueryUtil();
			nameList = query.queryBeans(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return nameList;
	}

	/**
	 * 根据备件的厂商、名称获取备件的型号
	 * 
	 * @param name
	 * @return
	 */
	public List getModelByName(String name, String fac) {
		String sql = "";
		QueryUtil query = null;
		List modelList = null;
		sql = "select distinct spb.spare_part_model from spare_part_baseinfo spb where spb.spare_part_name='"
			+ name
			+ "' and  spb.product_factory='"
			+ fac
			+ "'"
			+ " order by spb.spare_part_model desc";
		System.out.println(sql);
		try {
			query = new QueryUtil();
			modelList = query.queryBeans(sql);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据选中的备件名称获取备件型号:" + e.getMessage());
			return null;
		}
		return modelList;
	}

	/**
	 * 根据选中的型号获取软件版本
	 * 
	 * @param model
	 * @return
	 */
	public List getVersionByModel(String model, String fac, String name) {
		String sql = "";
		QueryUtil query = null;
		List versionList = null;
		sql = "select distinct spb.software_version from spare_part_baseinfo spb where spb.spare_part_model='"
			+ model
			+ "' and  spb.product_factory='"
			+ fac
			+ "' and spb.spare_part_name='"
			+ name
			+ "'"
			+ "order by spb.software_version desc";
		try {
			query = new QueryUtil();
			versionList = query.queryBeans(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			query.close();
		}
		return versionList;
	}

	/**
	 * 执行查询
	 * 
	 * @param bean
	 * @return
	 */
	public List doQuery(SparepartBaseInfoBean bean, HttpSession session) {
		String sql = "";
		QueryUtil query = null;
		sql = "select spb.tid, spb.spare_part_name, spb.spare_part_model, spb.software_version, spb.product_factory, spb.part_state  "
			+ " from spare_part_baseinfo spb where 1=1";
		String name = bean.getSparePartName();
		System.out.println(name + "=name");
		if (name != null && !name.equals("")) {
			sql += " and spb.spare_part_name='" + name + "'";
		}
		String model = bean.getSparePartModel();
		System.out.println(model + "=model");
		if (model != null && !model.equals("")) {
			sql += " and spb.spare_part_model='" + model + "'";
		}
		String softVersion = bean.getSoftwareVersion();
		System.out.println("softVersion:" + softVersion);
		if (softVersion != null && !softVersion.equals("")) {
			sql += " and spb.software_version='" + softVersion + "'";
		}
		String factory = bean.getProductFactory();
		System.out.println("factory:" + factory);
		if (factory != null && !factory.equals("")) {
			sql += " and spb.product_factory='" + factory + "'";
		}
		sql += "order by spb.spare_part_name desc";
		System.out.println("SeparepartBaseInfoDAO->doQuery:" + sql);
		session.setAttribute("spbQueryCon", sql);
		try {
			query = new QueryUtil();
			return query.queryBeans(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			query.close();
		}
	}

	/**
	 * 根据TID获取指定的备件详细信息
	 * 
	 * @param tid
	 * @return
	 */
	public SparepartBaseInfoBean getOneSparepart(String tid) {
		SparepartBaseInfoBean bean = new SparepartBaseInfoBean();
		String sql = "";
		QueryUtil query = null;
		ResultSet rs = null;
		sql = "select * from spare_part_baseinfo spb where spb.tid='" + tid
		+ "'";
		try {
			query = new QueryUtil();
			rs = query.executeQuery(sql);
			if (rs.next()) {
				bean.setSparePartId(rs.getString("tid"));
				bean.setSparePartName(rs.getString("spare_part_name"));
				bean.setSparePartModel(rs.getString("spare_part_model"));
				bean.setSoftwareVersion(rs.getString("software_version"));
				bean.setProductFactory(rs.getString("product_factory"));
				bean.setSparePartRemark(rs.getString("spare_part_remark"));
				bean.setSparePartState(rs.getString("part_state"));
			}
			return bean;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获得指定的备件信息出错" + e.getMessage());
			return null;
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			query.close();
		}
	}

	/**
	 * 修改备件
	 * 
	 * @param bean
	 * @return
	 */
	public boolean modifySeparepar(SparepartBaseInfoBean bean) {
		try {
			this.getHibernateTemplate().update(bean);
			return true;
		} catch (Exception e) {
			logger.error("修改备件出错" + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean judgeStorageIsHave(String tid){
		boolean falg = false;
		QueryUtil query = null;
		List list = new ArrayList();
		String sql = "select * from spare_part_storage s where s.spare_part_id='"+tid+"'";
		try{
			query = new QueryUtil();
			list = query.queryBeans(sql);
			if(list != null && list.size()>0){
				falg = true;
			}
		}catch(Exception e){
			e.getStackTrace();
		}
		return falg;
	}

	/**
	 * 根据TID删除指定的备件
	 * 
	 * @param tid
	 * @return
	 */
	public boolean delSeparepar(String tid) {
		try {
			SparepartBaseInfoBean bean = (SparepartBaseInfoBean) super.getHibernateTemplate().load(SparepartBaseInfoBean.class, tid);
			this.getHibernateTemplate().delete(bean);
			return true;
		} catch (Exception e) {
			logger.error("删除指定备件出错" + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 备件批量删除
	 * 
	 * @param tid
	 * @return
	 */
	public boolean delMore(String tid) {
		String sql = "delete from spare_part_baseinfo spb where spb.tid in ("
			+ tid + ")";
		UpdateUtil excu = null;
		logger.info("删除备件的sql："+sql);
		try {
			excu = new UpdateUtil();
			excu.executeUpdate(sql);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
	}

	/**
	 * 执行修改后的查询
	 * @param sql
	 * @return
	 */
	public List doQueryAfterMod(String sql) {
		QueryUtil query = null;
		try {
			query = new QueryUtil();
			return query.queryBeans(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List doQueryAfterMod(List ids) {
		String tids = splitList(ids);
		String sql = "select * from spare_part_baseinfo where tid in("+tids+")";
		QueryUtil query = null;
		try {
			query = new QueryUtil();
			System.out.println("doQueryAfterMod sql:"+sql);
			return query.queryBeans(sql);
		} catch (Exception e) {
			System.out.println("doQueryAfterMod 出现异常:"+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public String splitList(List list){
		String serials = "";
		for(int i =0;i<list.size();i++){
			if(i == list.size()-1){
				serials+= "'"+list.get(i)+"'";
			}else{
				serials+= "'"+list.get(i)+"',";
			}
		}
		return serials;
	}
	



	/**
	 * 更改备件状态
	 * 
	 * @param tid
	 * @param state
	 * @return
	 */
	public boolean modState(String tid, String state) {
		String sql = "";
		UpdateUtil excu = null;
		sql = "update spare_part_baseinfo spb set spb.PART_STATE ='"
			+ state + "' where spb.tid='" + tid + "'";
		try {
			excu = new UpdateUtil();
			excu.executeUpdate(sql);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("更改备件状态出错:" + e.getMessage());
			return false;
		}
	}

	public boolean modState(String tid, String state,UpdateUtil excu) {
		String sql = "";
		sql = "update spare_part_baseinfo spb set spb.PART_STATE ='"
			+ state + "' where spb.tid='" + tid + "'";
		try {
			excu.executeUpdate(sql);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("更改备件状态出错:" + e.getMessage());
			return false;
		}
	}

	public String getSpbName() {
		String name = "";
		String sql = "select distinct spb.spare_part_name from spare_part_baseinfo spb order by spb.spare_part_name desc";
		QueryUtil query = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		try {
			query = new QueryUtil();
			rs = query.executeQuery(sql);
			sb.append("[");
			while (rs.next()) {
				sb.append("['").append(rs.getString("spare_part_name")).append(
				"'],");
			}
			int length = sb.toString().length() - 1;
			name = sb.toString().substring(0, length) + "]";
			return name;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据备件的厂商、名称获取备件的型号
	 * 
	 * @param name
	 * @return
	 */
	public List getModelByNameForStockSave(String name, String fac) {
		String sql = "";
		QueryUtil query = null;
		List modelList = null;
		sql = "select distinct spb.spare_part_model,spb.tid from spare_part_baseinfo spb where spb.spare_part_name='"
			+ name
			+ "' and  spb.product_factory='"
			+ fac
			+ "'"
			+ " order by spb.spare_part_model desc";
		System.out.println(sql);
		try {
			query = new QueryUtil();
			modelList = query.queryBeans(sql);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据选中的备件名称获取备件型号:" + e.getMessage());
			return null;
		}
		return modelList;
	}
	
	public List getAllSeparepart() {
		String sql = "";
		QueryUtil query = null;
		sql = "select spb.tid,spb.spare_part_name, spb.spare_part_model, spb.software_version, spb.product_factory  "
				+ " from spare_part_baseinfo spb";
		try {
			query = new QueryUtil();
			return query.queryBeans(sql);
		} catch (Exception e) {
			e.printStackTrace();
			logger.equals("获取备件列表出错:" + e.getMessage());
			return null;
		} finally {
			query.close();
		}
	}




}
