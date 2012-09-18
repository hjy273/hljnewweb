/***
 *
 * MtUsedAssessDao.java
 * @auther <a href="kww@mail.cabletech.com.cn">kww</a>
 * 2009-10-10
 **/

package com.cabletech.linepatrol.material.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.material.beans.MtUsedAssessBean;
import com.cabletech.linepatrol.material.beans.MtUsedBean;
import com.cabletech.linepatrol.material.domain.MtUsedAssess;

@Repository
public class MtUsedAssessDao extends HibernateDao<MtUsedAssess, String> {

	private static Logger logger = Logger.getLogger(MtUsedAssessDao.class
			.getName());

	/**
	 * '1 通过 0 不通过'
	 * 
	 * @param bean
	 * @return
	 */
	public boolean saveMtUsedAssessBean(MtUsedAssessBean bean) {
		String sqlString = "insert into LP_MT_USED_ASSESS(ID,MTUSEDID,ASSESSOR,ASSESSDATE,STATE,REMARK,type) values("
				+ bean.getAid()
				+ ","
				+ bean.getMtusedid()
				+ ",'"
				+ bean.getAssessor()
				+ "',to_date('"
				+ bean.getAssessdate()
				+ "','YYYY-MM-DD HH24:mi:ss'),'"
				+ bean.getState()
				+ "','"
				+ bean.getType() + "')";
		UpdateUtil update = null;
		try {
			update = new UpdateUtil();
			update.executeUpdate(sqlString);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("MtUsedAssessDao->saveMtUsedAssessBean error!", e);
			return false;
		}
	}

	public boolean saveMtUsedAssessBean(MtUsedAssessBean bean,
			MtUsedBean usedBean) {
		String sqlString = "insert into LP_MT_USED_ASSESS(ID,MTUSEDID,ASSESSOR,ASSESSDATE,STATE,REMARK,type) values("
				+ bean.getAid()
				+ ","
				+ bean.getMtusedid()
				+ ",'"
				+ bean.getAssessor()
				+ "',to_date('"
				+ bean.getAssessdate()
				+ "','YYYY-MM-DD HH24:mi:ss'),'"
				+ bean.getState()
				+ "','"
				+ bean.getRemark() + "','" + bean.getType() + "')";

		String updateString = "update LP_mt_used set state='"
				+ usedBean.getState() + "' where id=" + usedBean.getMid();

		UpdateUtil update_2 = null;
		UpdateUtil update_1 = null;
		try {
			update_2 = new UpdateUtil();
			update_1 = new UpdateUtil();
			System.out.println(sqlString);
			update_2.executeUpdate(sqlString);
			System.out.println(updateString);
			update_1.executeUpdate(updateString);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			update_1.rollback();
			update_2.rollback();
			logger.warn("MtUsedAssessDao->saveMtUsedAssessBean error!", e);
			return false;
		}
	}

	// public List getMtUsedId(int id){
	//		
	// String sqlString = "select
	// l.type,l.mtusedid,c.contractorname,l.id,to_char(l.assessdate,'YYYY-MM-DD
	// HH24:mi:ss') as assessdate,l.remark,t.username,l.state from
	// contractorinfo c,LP_mt_used_assess l,userinfo t where
	// c.contractorid=t.deptid and t.userid=l.assessor and l.type='0' and
	// l.mtusedid="+id
	// +" union all select l.type,l.mtusedid,c.deptname as
	// contractorname,l.id,to_char(l.assessdate,'YYYY-MM-DD HH24:mi:ss') as
	// assessdate,l.remark,t.username,l.state from deptinfo c,LP_mt_used_assess
	// l,userinfo t where c.deptid=t.deptid and t.userid=l.assessor and
	// l.type='1' and l.mtusedid="+id +" order by id";
	// ;
	// try{
	// QueryUtil util = new QueryUtil();
	// List list = util.queryBeans(sqlString);
	// return list;
	// }catch (Exception e) {
	// e.printStackTrace();
	// logger.warn("MtUsedDao->getId error!",e);
	// }
	// return null;
	// }

	public List getUnionMtUsedId(int id) {
		String sql = "select l.type,l.mtusedid,c.contractorname,l.id,to_char(l.assessdate,'YYYY-MM-DD HH24:mi:ss') as assessdate,"
				+ "l.remark,t.username,l.state from contractorinfo c,LP_mt_used_assess l,userinfo t "
				+ "where c.contractorid=t.deptid and t.userid=l.assessor and l.type='0' and l.mtusedid=? "
				+ "union all select l.type,l.mtusedid,c.deptname as contractorname,l.id,to_char(l.assessdate,'YYYY-MM-DD HH24:mi:ss') as assessdate,"
				+ "l.remark,t.username,l.state from deptinfo c,LP_mt_used_assess l,userinfo t "
				+ "where c.deptid=t.deptid and t.userid=l.assessor and l.type='1' and l.mtusedid=? order by id";
		logger.info("id:" + id);
		logger.info("由id获得盘点信息：" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, id, id);
	}

	public List getMtUsedId(int id, String type) {
		String sql = "select l.mtusedid,c.contractorname,l.id,to_char(l.assessdate,'YYYY-MM-DD HH24:mi:ss') as assessdate,l.remark,t.username,l.state " +
				"from contractorinfo c,LP_mt_used_assess l,userinfo t " +
				"where c.contractorid=t.deptid and t.userid=l.assessor and l.mtusedid=? and l.type=? order by id";
		logger.info("id:" + id + "  type:" + type);
		logger.info("" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, id, type);
	}

	public List getMobileMtUsedId(int id) {
		String sql = "select l.mtusedid,c.deptname,l.id,to_char(l.assessdate,'YYYY-MM-DD HH24:mi:ss') as assessdate,l.remark,t.username,l.state " +
				"from deptinfo c,LP_mt_used_assess l,userinfo t " +
				"where c.deptid=t.deptid and t.userid=l.assessor and l.mtusedid=? and l.type='1' order by id";
		logger.info("id:" + id);
		logger.info("" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, id);
	}

	// public List getConditionList(int id){
	//		
	// String sqlString = "select c.*,l.id,to_char(l.createtime,'yyyy-mm') as
	// mtcreatetime,l.remark,t.* from contractorinfo c,LP_mt_used_assess
	// l,userinfo t where and c.contractorid=t.deptid and t.userid=l.assessor
	// and l.mtusedid="+id;
	// try{
	// QueryUtil util = new QueryUtil();
	// List list = util.queryBeans(sqlString);
	// return list;
	// }catch (Exception e) {
	// e.printStackTrace();
	// logger.warn("MtUsedDao->getId error!",e);
	// return new ArrayList();
	// }
	// }

}
