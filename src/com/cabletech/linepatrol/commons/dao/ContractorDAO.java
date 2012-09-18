package com.cabletech.linepatrol.commons.dao;

import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.commons.module.ApproveInfo;

@Repository("contractorDao")
public class ContractorDAO extends HibernateDao<ApproveInfo, String> {

	public List getContractorList(String condition) {
		// TODO Auto-generated method stub
		String sql = " select contractorid,contractorid||'-'||contractorname as contractorinfo,contractorname, ";
		sql = sql + " contractorid as departid,contractorname as departname ";
		sql = sql + " from contractorinfo c ";
		sql = sql + " where (state is null or state<>'1') ";
		sql = sql + condition;
		return super.getJdbcTemplate().queryForBeans(sql);
	}

	public List getContractorUserList(String condition) {
		// TODO Auto-generated method stub
		String sql = " select u.userid,u.deptid,u.phone as mobile,u.position,u.username as name,u.userid||'-'||u.username as userinfo ";
		sql = sql + " from userinfo u,contractorinfo c ";
		sql = sql + " where u.deptid=c.contractorid ";
		sql = sql + " and (c.state is null or c.state<>'1')";
		// sql = sql + " and (u.state is null or u.state<>'1') ";
		sql = sql + condition;
		return super.getJdbcTemplate().queryForBeans(sql, null);
	}

	public List getContractorPersonList(String condition) {
		// TODO Auto-generated method stub
		/*
		 * String sql =
		 * " select u.id,u.contractorid,u.mobile,u.name,id||'-'||u.name||'-负责：'||u.jobinfo as userid,u.jobinfo "
		 * ; sql = sql + " from contractorperson u,contractorinfo c "; sql = sql
		 * + " where u.contractorid=c.contractorid "; sql = sql +
		 * " and (c.state is null or c.state<>'1')";
		 */
		String sql = " select u.userid id,u.deptid contractorid,u.phone mobile,u.username name,u.userid||'-'||u.username userid,u.position jobinfo";
		sql = sql + " from userinfo u,contractorinfo c ";
		sql = sql + " where u.deptid=c.contractorid ";
		sql = sql + " and (c.state is null or c.state<>'1')";
		// sql = sql + " and (u.state is null or u.state<>'1') ";
		sql = sql + condition;
		return super.getJdbcTemplate().queryForBeans(sql, null);
	}

	/**
	 * 执行根据代维单位编号获取代维单位名称信息
	 * 
	 * @param contractorId
	 *            状态编号
	 * @return String 代维单位名称信息
	 * @throws Exception
	 */
	public String getContractorName(String contractorId) {
		String sql = "select contractorname from contractorinfo where contractorid='"
				+ contractorId + "'";
		List list = super.getJdbcTemplate().queryForBeans(sql);
		if (list != null && list.size() > 0) {
			DynaBean bean = (DynaBean) list.get(0);
			String contractName = (String) bean.get("contractorname");
			return contractName;
		}
		return "";
	}
}
