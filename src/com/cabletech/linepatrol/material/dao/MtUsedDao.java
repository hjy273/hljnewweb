/***
 *
 * MtUsedDao.java
 * @auther <a href="kww@mail.cabletech.com.cn">kww</a>
 * 2009-10-10
 **/

package com.cabletech.linepatrol.material.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.material.beans.MtUsedBean;
import com.cabletech.linepatrol.material.domain.MtUsed;

/**
 * 材料 apply Dao
 * 
 * @author kww
 * 
 */
@Repository
public class MtUsedDao extends HibernateDao<MtUsed, String> {

	private static Logger logger = Logger.getLogger(MtUsedDao.class.getName());

	/**
	 * 得到所有移动用户
	 * 
	 * @return
	 */
	public List getUserInfos() {
		String sql = "select userid,username from userinfo u,deptinfo d where u.deptid = d.deptid and u.state is null and d.state is null order by userid";
		logger.info("得到所有移动用户:" + sql);
		List list = this.getJdbcTemplate().queryForBeans(sql);
		return list != null ? list : new ArrayList();
	}

	/**
	 * 获得公司信息；2：代表监理公司 3:代维公司
	 * 
	 * @param type
	 * @return
	 */
	public List getAllContractor(String type) {
		String sql = "select t.contractorname,t.contractorid from contractorinfo t where t.depttype=?";
		logger.info("type:" + type);
		logger.info("获得公司信息:" + sql);
		List list = this.getJdbcTemplate().queryForBeans(sql, type);
		return list != null ? list : new ArrayList();
	}

	public BasicDynaBean getId(int id) {
		String sql = "select l.id,to_char(l.createtime,'YYYY-MM') as createtime,"
				+ "l.remark,t.username,t.deptid as creator_contractor_id,approver_id,ui.username as approver_name "
				+ "from LP_mt_used l,userinfo t,userinfo ui "
				+ "where t.userid=l.creator and ui.userid=l.approver_id and l.id=?";
		logger.info("id:" + id);
		logger.info("" + sql);
		List list = this.getJdbcTemplate().queryForBeans(sql, id);
		return (BasicDynaBean) (list.size() > 0 ? list.get(0) : null);
	}

	public BasicDynaBean getApplyAndApproveBeanId(int id) {
		String sql = "select l.id,to_char(l.createtime,'YYYY-MM') as createtime,c.contractorname,c.contractorid,"
				+ "l.remark,t.username,t.deptid as creator_contractor_id "
				+ "from LP_mt_used l,userinfo t,contractorinfo c "
				+ "where t.userid=l.creator and t.deptid=c.contractorid and l.id=?";
		logger.info("id:" + id);
		logger.info("" + sql);
		List list = this.getJdbcTemplate().queryForBeans(sql, id);
		return (BasicDynaBean) (list.size() > 0 ? list.get(0) : null);
	}

	/**
	 * 0:apply ;1移动公司 agree
	 * 
	 * @param bean
	 * @return
	 */
	public boolean saveMtUesdBean(MtUsedBean bean) {
		String sqlString = "insert into LP_mt_used(id,contractorid,createtime,creator,remark,state,approver_id) values("
				+ bean.getMid()
				+ ",'"
				+ bean.getContractorid()
				+ "',to_date('"
				+ bean.getCreatetime()
				+ "','YYYY-MM'),'"
				+ bean.getCreator()
				+ "','"
				+ bean.getRemark()
				+ "','"
				+ bean.getState()
				+ "','"
				+ bean.getApproverId() + "')";
		try {
			System.out.println(sqlString);
			UpdateUtil util = new UpdateUtil();
			util.executeUpdate(sqlString);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("MtusedDao->saveMtUesdBean error!", e);
			return false;
		}
	}

	/**
	 * 编辑材料申请
	 * 
	 * @param bean
	 * @return
	 */
	public boolean editMtUesdBean(MtUsedBean bean) {

		String sqlString = "update LP_mt_used set contractorid = '"
				+ bean.getContractorid() + "'" + ", createtime = to_date('"
				+ bean.getCreatetime() + "','YYYY-MM')"
				+ ", state = '0', remark = '" + bean.getRemark() + "'"
				+ ",approver_id='" + bean.getApproverId() + "' where id = "
				+ bean.getId();
		try {
			logger.info("MtusedDao->editMtUesdBean sql:" + sqlString);
			UpdateUtil util = new UpdateUtil();
			util.executeUpdate(sqlString);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("MtusedDao->editMtUesdBean error!", e);
			return false;
		}
	}

	/**
	 * 删除材料申请
	 * 
	 * @param bean
	 * @return
	 */
	public boolean delMtUesdBean(int id) {

		String sqlString = "delete LP_mt_used where id = " + id;
		try {
			logger.info("MtusedDao->delMtUesdBean sql:" + sqlString);
			UpdateUtil util = new UpdateUtil();
			util.executeUpdate(sqlString);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("MtusedDao->delMtUesdBean error!", e);
			return false;
		}
	}

	/**
	 * update state 0:apply ;1移动公司 agree
	 * 
	 * @param bean
	 * @return
	 */
	public boolean updateMtUesdBeanState(MtUsedBean bean) {
		String sqlString = "update LP_mt_used set state='" + bean.getState()
				+ "' where id=" + bean.getMid();
		try {
			UpdateUtil util = new UpdateUtil();
			util.executeUpdate(sqlString);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("MtusedDao->updateMtUesdBeanState error!", e);
			return false;
		}
	}

	/**
	 * 
	 * @param controler
	 * @return
	 */
	public List getConditionByState0(String controler) {
		String sql = "select to_char(l.createtime,'YYYY-MM') as createtime,l.id,l.state,l.remark,t.username,c.contractorname,c.contractorid "
				+ "from LP_mt_used l,userinfo t,contractorinfo c "
				+ "where t.userid=l.creator and c.contractorid=t.deptid and (l.state='0' or l.state='3') "
				+ "order by l.createtime";
		logger.info("controler:" + controler);
		logger.info("" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, controler);
	}

	/**
	 * 
	 * @param userId
	 * @param controler
	 * @return
	 */
	public List getConditionByStateAndUserid(String state, String userId) {
		String sql = "select to_char(l.createtime,'YYYY-MM') as createtime,l.id,l.state,l.remark,c.contractorname,t.username "
				+ "from LP_mt_used l,userinfo t,contractorinfo c "
				+ "where  t.userid=l.creator and c.contractorid=t.deptid and l.approver_id='"
				+ userId + "' and l.state=? " + "order by l.createtime";
		logger.info("state:" + state);
		logger.info("" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, state);
	}

	/**
	 * 
	 * @param controler
	 * @return
	 */
	public List getConditionByState(String state) {
		String sql = "select to_char(l.createtime,'YYYY-MM') as createtime,l.id,l.state,l.remark,c.contractorname,t.username "
				+ "from LP_mt_used l,userinfo t,contractorinfo c "
				+ "where t.userid=l.creator and c.contractorid=t.deptid and l.state=? order by l.createtime";
		logger.info("state:" + state);
		logger.info("" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, state);
	}

	/**
	 * 根据代维和申请单提交时间来查询
	 * 
	 * @param contractorname
	 * @param createtime
	 * @param state
	 * @return
	 */
	public List getConditionByMobile(String contractorname, String createtime,
			String state) {
		String sql = "select to_char(l.createtime,'YYYY-MM') as createtime,l.id,l.state,l.remark,c.contractorname,t.username ";
		sql = sql
				+ " from LP_mt_used l,userinfo t,contractorinfo c where t.userid=l.creator and c.contractorid=t.deptid and l.state='"
				+ state + "'";
		if (contractorname != null && !"".equals(contractorname)) {
			sql = sql + " and c.contractorname like '%" + contractorname + "%'";
		}
		if (createtime != null && !"".equals(createtime)) {
			sql = sql + " and l.createtime=to_date('" + createtime
					+ "','YYYY-MM')";
		}
		sql = sql + " order by l.createtime";
		logger.info("根据代维和申请单提交时间来查询:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * 
	 * @param controler
	 * @return
	 */
	public List getApplyBy0And1(String userid) {
		String sql = "select to_char(l.createtime,'YYYY-MM') as createtime,l.id,l.state,l.remark,c.contractorname,t.username "
				+ "from LP_mt_used l,userinfo t,contractorinfo c "
				+ "where  t.userid=l.creator and (l.state='0' or l.state='1') and c.contractorid=t.deptid and l.creator=? "
				+ "order by l.createtime";
		logger.info("userid:" + userid);
		logger.info("" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, userid);
	}

	public List getThisMonthNewStorageList(String contractorId, String month) {
		String sql = "select to_char(m.materialid) as materialid,to_char(sum(m.count)) as new_storage ";
		sql = sql + " from ( ";
		sql = sql + " select s.* from lp_mt_storage s,lp_approve_info a ";
		sql = sql
				+ " where s.id=a.object_id and a.object_type='LP_MT_STORAGE' and a.approve_result='1' ";
		sql = sql + " ) storage,lp_mt_storage_item m,userinfo u ";
		sql = sql
				+ " where storage.id=m.materialid and storage.creator=u.userid ";
		sql = sql + " and u.deptid='" + contractorId + "' ";
		sql = sql + " and storage.createdate>=to_date('" + month
				+ "-1','yyyy-mm-dd') ";
		sql = sql + " and storage.createdate<add_months(to_date('" + month
				+ "-1','yyyy-mm-dd'),1) ";
		sql = sql + " group by m.materialid";
		logger.info("执行sql:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	public List getThisMonthAllotInList(String contractorId, String month) {
		String sql = "select to_char(m.materialid) as materialid,to_char(sum(m.oldstock+m.newstock)) as allot_in_storage from LP_MT_CHANGE_ITE m,LP_MT_ALLOT allot ";
		sql = sql + " where m.allotid=allot.id ";
		sql = sql + " and m.newcontractorid='" + contractorId + "' ";
		sql = sql + " and m.oldcontractorid<>'" + contractorId + "' ";
		sql = sql + " and allot.changedate>=to_date('" + month
				+ "-1','yyyy-mm-dd') ";
		sql = sql + " and allot.changedate<add_months(to_date('" + month
				+ "-1','yyyy-mm-dd'),1) ";
		sql = sql + " group by m.materialid";
		logger.info("执行sql:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	public List getThisMonthAllotOutList(String contractorId, String month) {
		// TODO Auto-generated method stub
		String sql = "select to_char(m.materialid) as materialid,to_char(sum(m.oldstock+m.newstock)) as allot_out_storage from LP_MT_CHANGE_ITE m,LP_MT_ALLOT allot ";
		sql = sql + " where m.allotid=allot.id ";
		sql = sql + " and m.oldcontractorid='" + contractorId + "' ";
		sql = sql + " and m.newcontractorid<>'" + contractorId + "' ";
		sql = sql + " and allot.changedate>=to_date('" + month
				+ "-1','yyyy-mm-dd') ";
		sql = sql + " and allot.changedate<add_months(to_date('" + month
				+ "-1','yyyy-mm-dd'),1) ";
		sql = sql + " group by m.materialid";
		logger.info("执行sql:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	public List getThisMonthRemedyList(String contractorId, String month) {
		// TODO Auto-generated method stub
		String sql = "select to_char(m.materialid) as materialid,to_char(sum(m.materialcount)) as remedy_storage from LP_remedy_bal_material m,LP_remedy remedy ";
		sql = sql + " where m.remedyid=remedy.id ";
		sql = sql + " and remedy.contractorid='" + contractorId + "' ";
		sql = sql + " and remedy.state>='301' ";
		sql = sql + " and remedy.remedydate>=to_date('" + month
				+ "-1','yyyy-mm-dd') ";
		sql = sql + " and remedy.remedydate<add_months(to_date('" + month
				+ "-1','yyyy-mm-dd'),1) ";
		sql = sql + " group by m.materialid ";
		sql = sql + " union ";
		sql = sql
				+ " select to_char(m.materialid) as materialid,to_char(sum(m.materialcount)) as remedy_storage from LP_remedy_material m,LP_remedy remedy ";
		sql = sql + " where m.remedyid=remedy.id ";
		sql = sql + " and remedy.contractorid='" + contractorId + "' ";
		sql = sql + " and remedy.state='201' ";
		sql = sql + " and remedy.remedydate>=to_date('" + month
				+ "-1','yyyy-mm-dd') ";
		sql = sql + " and remedy.remedydate<add_months(to_date('" + month
				+ "-1','yyyy-mm-dd'),1) ";
		sql = sql + " group by m.materialid";
		logger.info("执行sql:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	public List getLastMonthStorageList(String contractorId, String month) {
		// TODO Auto-generated method stub
		String sql = "select to_char(used_stock.materialid) as materialid,to_char(used_stock.real_stock) as last_month_storage from LP_MT_USED_STOCK used_stock,LP_MT_USED used,userinfo u ";
		sql = sql + " where used_stock.mtusedid=used.id ";
		sql = sql + " and used.state='4' ";
		sql = sql + " and used.creator=u.userid ";
		sql = sql + " and u.deptid='" + contractorId + "' ";
		sql = sql + " and used.createtime=add_months(last_day(to_date('"
				+ month + "-01','yyyy-mm-dd'))+1,-2) ";
		sql = sql + " order by used.createtime desc";
		logger.info("执行sql:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	public String getMaterialName(String materialId) {
		// TODO Auto-generated method stub
		String sql = "select mb.name||'（'||mt.typename||'）（'||mm.modelname||'）' as material_name ";
		sql = sql + " from LP_MT_BASE mb,LP_MT_MODEL mm,LP_MT_TYPE mt ";
		sql = sql + " where mb.modelid=mm.id and mm.typeid=mt.id ";
		sql = sql + " and mb.id=" + materialId + " ";
		logger.info("执行sql:" + sql);
		List list = this.getJdbcTemplate().queryForBeans(sql);
		if (list != null && list.size() > 0) {
			DynaBean bean = (DynaBean) list.get(0);
			return (String) bean.get("material_name");
		}
		return null;
	}

	public boolean judgeExistMtUsed(String createtime, String contractorId,
			String id) {
		// TODO Auto-generated method stub
		String sql = "select t.contractorid from LP_MT_USED t,userinfo u ";
		sql = sql + " where u.userid=t.creator and u.deptid='" + contractorId
				+ "' ";
		sql = sql + " and t.createtime=to_date('" + createtime
				+ "-01','yyyy-mm-dd') ";
		sql = sql + " and id<>'" + id + "' ";
		logger.info("执行sql:" + sql);
		List list = this.getJdbcTemplate().queryForBeans(sql);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}
}
