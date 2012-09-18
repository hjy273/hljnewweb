package com.cabletech.linepatrol.trouble.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.Depart;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.trouble.module.TroubleReply;
import com.cabletech.linepatrol.trouble.services.TroubleConstant;

@Repository
public class TroubleReplyDAO extends HibernateDao<TroubleReply, String> {
	//private static final String QYERY_WAIT="";


	public TroubleReply getTroubleReply(String id){
		TroubleReply reply =get(id);
		initObject(reply);
		return reply;
	}

	/**
	 * 代维查询待反馈故障单
	 * @return
	 */
	public List findWaitReplys(String condition){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		StringBuffer seltr= new StringBuffer();
		seltr.append("select distinct t.id, t.trouble_id,t.trouble_name,unit.id unitid,");
		seltr.append("unit.process_unit_id, t.trouble_send_man,t.send_man_id ,");
		seltr.append("to_char(t.trouble_start_time,'yyyy-mm-dd hh24:mi:ss') trouble_start_time,");
		seltr.append("decode(trouble_state,'0','待反馈','1','待关闭','20','待审核','30','审核不通过','31','审核通过','40','待考核','50','完成') as trouble_state");
		seltr.append(",trouble_state state,to_char(t.trouble_send_time,'yyyy-mm-dd hh24:mi:ss') trouble_send_time");
		seltr.append(",decode(is_great_trouble,'1','是','0','否','否') AS is_great_trouble,'' as flow_state,'' as havereply ");
		StringBuffer sb = new StringBuffer();
		sb.append(seltr.toString());
		sb.append(",reply.id replyid,'' as isread");
		sb.append(" from lp_trouble_info t,lp_trouble_process_unit unit,lp_trouble_reply reply ");
		sb.append(" where reply.trouble_id=t.id and unit.trouble_id=t.id ");
		sb.append(condition);
		StringBuffer sbf = new StringBuffer();
		sbf.append(" union ");
		sbf.append(seltr.toString());
		sbf.append(",'' replyid,'' as isread");
		sbf.append(" from lp_trouble_info t,lp_trouble_process_unit unit ");
		sbf.append(" where unit.trouble_id=t.id ");
		sbf.append(" and t.id not in(select r.trouble_id from lp_trouble_reply r)");
		sbf.append(condition);
		sql.append(sb.toString()+sbf.toString());
		//sb.append(" and unit.process_unit_id=?");
		//values.add(deptid); 
		sql.append(" order by trouble_send_time desc");
		return this.getJdbcTemplate().queryForBeans(sql.toString(), values.toArray());
	}
	
	

	/**
	 * 根据故障id查询主办的反馈单
	 * @param troubleid
	 * @return
	 */
	public TroubleReply getReplyByTroubleId(String troubleid){
		String hql = "from TroubleReply t where t.troubleId='"+troubleid+"' and confirmResult="+TroubleConstant.REPLY_ROLE_HOST;
		Session session = getSession();
		Query query = session.createQuery(hql);       
		return (TroubleReply) query.uniqueResult();
	}

	/**
	 * 查询是否填写反馈单
	 * @param troubleid 故障id
	 * @param contractorId 处理单位id
	 * @return
	 */
	public TroubleReply getReplyByTroubleIAndContractorID(String troubleid,String contractorId){
		String hql = "from TroubleReply t where t.troubleId='"+troubleid+"' and t.contractorId='"+contractorId+"'";
		Session session = getSession();
		Query query = session.createQuery(hql);       
		return (TroubleReply) query.uniqueResult();
	}

	/**
	 * 根据故障id查询所有反馈单
	 * @param troubleid
	 * @param join 是否为协办
	 * @return
	 */
	public List<TroubleReply> getReplysByTroubleId(String troubleid,String join){
		String hql = "from TroubleReply t where t.troubleId='"+troubleid+"'";
		if(join!=null && join.equals("join")){
			hql+=" and confirmResult='"+TroubleConstant.REPLY_ROLE_JOIN+"'";
		}
		Session session = getSession();
		Query query = session.createQuery(hql);       
		return query.list();
	}


	/**
	 * 根据指定的故障派单编号来
	 * 判断主办反馈单是否审核通过
	 * @param troubleid 故障派单编号
	 * @return true 审核通过
	 */
	public boolean judgeAllApproved(String troubleid){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select * ");
		sb.append(" from lp_trouble_reply reply ");
		sb.append(" where reply.approve_state= ? ");
		values.add(TroubleConstant.REPLY_APPROVE_PASS);
		sb.append(" and reply.confirm_result=? and reply.trouble_id=?");
		values.add(TroubleConstant.REPLY_ROLE_HOST);
		values.add(troubleid);
		List list = this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
		if(list== null || list.size()==0){
			return false;
		}
		return true;
	}


	/**
	 *  根据指定的故障派单编号来判断反馈单是否全部反馈
	 * @param toroubleid
	 * @return true:表示不存在
	 */
	public boolean judgeExistReplyAll(String troubleid){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select u.process_unit_id from lp_trouble_process_unit u");
		sb.append(" where u.trouble_id=? ");
		values.add(troubleid);
		sb.append(" and u.process_unit_id not in (select r.contractor_id from  lp_trouble_reply r where r.trouble_id=? ");
		values.add(troubleid);
		sb.append(")");
		List list = this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
		if(list== null || list.size()==0){
			return true;
		}
		return false;
	}

	/**
	 * 
	 *
	 */
	public boolean judgeReply(String userid){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select r.confirm_result from lp_reply_approver a,lp_trouble_reply r");
		sb.append(" where a.object_id=r.id and object_type=? and approver_id=?");
		values.add(TroubleConstant.LP_TROUBLE_REPLY);
		values.add(userid);
		List list = this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
		if(list== null || list.size()==0){
			for(int i = 0;i<list.size();i++){
				BasicDynaBean bean = (BasicDynaBean) list.get(i);
				String cr = (String)bean.get("confirm_result");
				//if(cr)
			}
		}
		return false;
	}


	/**
	 * 判断故障反馈单是否有主办
	 * @param troubleid
	 * @return true 有主办
	 */
	public boolean judgeReplyAllRoleHaveHost(String troubleid){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select reply.confirm_result cf from lp_trouble_reply reply,lp_trouble_info t ");
		sb.append("where t.id = reply.trouble_id  and t.id=?");
		values.add(troubleid);
		List list = this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
		if(list!= null && list.size()>0){
			for(int i = 0;i<list.size();i++){
				BasicDynaBean bean = (BasicDynaBean) list.get(i);
				String cf = (String) bean.get("cf");
				if(cf !=null && cf.equals(TroubleConstant.REPLY_ROLE_HOST)){
					return true;
				}
			}
		}
		return false;
	}


	/**
	 * 根据故障反馈信息来判断该故障反馈信息是否已经存在。
	 * @param replyInfo
	 * @return true:不存在  false：已经存在
	 */
	public boolean judgeExistReply(TroubleReply replyInfo){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select id  from  lp_trouble_reply r");
		sb.append(" where r.trouble_id=? ");
		values.add(replyInfo.getTroubleId());
		sb.append(" and r.contractor_id=?");
		values.add(replyInfo.getContractorId());
		List list = this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
		if(list== null || list.size()==0){
			return true;
		}
		return false;
	}



	/**
	 * 根据指定的故障派单编号来判断是否所有的反馈单都已经考核评估。
	 * @param troubleid 故障派单编号
	 * @return
	 */
	public boolean judgeAllEvaluated(String troubleid){

		return false;
	}

	/**
	 * 根据指定的故障反馈信息来判断是否对该反馈信息进行考核评估。
	 * @param replyInfo
	 * @return
	 */
	public boolean judgeNeedEvaluate(TroubleReply replyInfo){
		return false;

	}

	/**
	 * 根据指定的反馈单编号来写入需要考核评估信息。
	 * @param replyid
	 * @return
	 */
	public boolean writeNeedEvaluate(String replyid){
		return false;
	}

	/**
	 * 根据指定的反馈单编号来写入不需要考核评估信息。
	 * @param replyid
	 * @return
	 */
	public boolean writeNotNeedEvaluate(String replyid){
		return false;
	}


	/**
	 * 反馈单对应的审核人信息列表
	 * @param replyid
	 * @return
	 */
	public List approverList(String replyid){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select * ");
		sb.append(" from lp_trouble_reply r,lp_reply_approver ar");
		sb.append(" where r.id=ar.object_id and r.id=?");
		values.add(replyid);
		return  this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}

	/**
	 * 反馈单对应的光缆信息列表
	 * @param replyid
	 * @return
	 */
	public List cableList(String replyid){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select c.kid, c.segmentname,cable.trouble_cable_line_id lineid ");
		sb.append(" from lp_trouble_reply r,lp_trouble_cable_info cable,repeatersection c ");
		sb.append(" where c.kid =cable.trouble_cable_line_id and r.id=cable.trouble_reply_id and r.id=?");
		values.add(replyid);
		return  this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}


	/**
	 * 反馈单对应的使用材料信息列表
	 * @return
	 */
	public List getMaterialList(String replyid){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select * ");
		sb.append(" from lp_trouble_reply r,lp_material_used mtuse");
		sb.append(" where r.id=mtuse.object_id and r.id=?");
		values.add(replyid);
		return  this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}

	/**
	 * 反馈单对应的回收材料信息列表
	 * @return
	 */
	public List getMaterialListReturn(String replyid){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select * ");
		sb.append(" from lp_trouble_reply r,lp_material_return mtreturn");
		sb.append(" where r.id=mtreturn.object_id and r.id=?");
		values.add(replyid);
		return  this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}

	/**
	 * 反馈单对应隐患信息列表
	 * @param replyid
	 * @return
	 */
	public BasicDynaBean getAccidentByReplyId(String replyid){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select hidd.id,hidd.name,hidd.x,hidd.y ");
		sb.append(" from lp_trouble_reply r,lp_trouble_accidents lp_acc,lp_hiddanger_regist hidd ");
		sb.append(" where r.id=lp_acc.trouble_reply_id and lp_acc.accident_id=hidd.id and r.id=?");
		values.add(replyid);
		List accident =  this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
		if(accident!=null && accident.size()>0){
			return (BasicDynaBean) accident.get(0);
		}
		return null;
	}


	/**
	 * 故障派单对应的处理过程信息列表
	 * @param troubleid 故障id
	 * @param conid 代维id
	 * @return
	 */
	public List getTroubleProcessByCondition(String troubleid,String conid){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		String selsql = getSelProcessSql();
		sb.append(selsql);
		sb.append(" from lp_trouble_info t,lp_trouble_process pro,terminalinfo ter");
		sb.append(" where t.id=pro.trouble_id and pro.trouble_id=?");
		values.add(troubleid);
		sb.append(" and ter.deviceid=pro.arrive_terminal_id");
		sb.append(" and ter.contractorid=?");
		values.add(conid);
		logger.info(" 故障派单对应的处理过程信息列表================== "+sb.toString());
		return  this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}


	/**
	 * 故障派单对应的处理过程信息列表
	 * @param troubleid
	 * @return
	 */
	public List getTroubleProcessList(String troubleid){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		String selsql = getSelProcessSql();
		sb.append(selsql);
		sb.append(" from lp_trouble_info t,lp_trouble_process pro");
		sb.append(" where t.id=pro.trouble_id and pro.trouble_id=?");
		values.add(troubleid);
		return  this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}

	/**
	 * selquery
	 * @return
	 */
	public String getSelProcessSql(){
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct pro.id proid,pro.start_address,");
		sb.append("to_char(pro.start_time_ref,'yy/MM/dd hh24:mi:ss') start_time_ref,");
		sb.append("pro.arrive_proess_man, pro.arrive_terminal_id,");
		sb.append("to_char(pro.arrive_time_ref,'yy/mm/dd hh24:mi:ss') arrive_time_ref,");
		sb.append(" to_char(pro.arrive_trouble_time_ref,'yy/mm/dd hh24:mi:ss') arrive_trouble_time_ref,");
		sb.append(" to_char(pro.find_trouble_time_ref,'yy/mm/dd hh24:mi:ss') find_trouble_time_ref, ");  
		sb.append(" to_char(pro.return_time_ref,'yy/mm/dd hh24:mi:ss') return_time_ref,");
		sb.append(" to_char(pro.start_time,'yy/mm/dd hh24:mi:ss') start_time,");
		sb.append(" to_char(pro.arrive_time,'yy/mm/dd hh24:mi:ss') arrive_time,");
		sb.append(" to_char(pro.arrive_trouble_time,'yy/mm/dd hh24:mi:ss') arrive_trouble_time,");
		sb.append(" to_char(pro.find_trouble_time,'yy/mm/dd hh24:mi:ss') find_trouble_time,");
		sb.append(" to_char(pro.return_time,'yy/mm/dd hh24:mi:ss') return_time,");
		sb.append(" to_number(pro.start_address_gps_x) start_address_gps_x,");
		sb.append(" to_number(pro.start_address_gps_y) start_address_gps_y,");
		sb.append(" to_number(pro.arrive_trouble_gps_x) arrive_trouble_gps_x,");
		sb.append(" to_number(pro.arrive_trouble_gps_y) arrive_trouble_gps_y");
		return sb.toString();
	}


	/**
	 * 反馈单(主办)对应的处理过程信息列表
	 * @param replyid
	 * @return
	 */
	public List getProcessList(String replyid){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select * ");
		sb.append(" from lp_trouble_info t,lp_trouble_reply r,lp_trouble_process pro,contractorinfo c");
		sb.append(" terminalinfo t ");
		sb.append(" where t.id=pro.trouble_id and t.id=r.trouble_id and pro.arrive_terminal_id=t.terminalid ");
		sb.append(" and c.contractorid=t.contractorid and r.id=?");
		values.add(replyid);
		return  this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}

	/**
	 * 反馈单对应的处理人员信息列表
	 * @param replyid
	 * @return
	 */
	public List getProcessManList(String replyid){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select * ");
		sb.append(" from lp_trouble_reply r,lp_trouble_processer proer");
		sb.append(" where proer.trouble_reply_id=r.id and r.id=?");
		values.add(replyid);
		return  this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}

	/**
	 * 根据用户id查询部门名称
	 * @param userid
	 * @return
	 */
	public String getDeptNameByUserId(String userid){
		String deptname = "";
		try{
			UserInfo u =getUserInfoByUserId(userid);
			String deptype = u.getDeptype();
			List<Object> values = new ArrayList<Object>();
			StringBuffer sb = new StringBuffer(); 
			if(deptype.equals("1")){
				sb.append(" select  d.deptname deptname");
				sb.append(" from userinfo u,deptinfo d where u.deptid=d.deptid and u.userid=?");
				values.add(userid);
			}
			if(deptype.equals("2")){
				sb.append(" select  c.contractorname deptname ");
				sb.append(" from userinfo u,contractorinfo c where u.deptid=c.contractorid and u.userid=?");
				values.add(userid);
			}
			List list =  this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
			if(list !=null && list.size()>0){
				BasicDynaBean bean = (BasicDynaBean) list.get(0);
				deptname = (String) bean.get("deptname");
			}
			return deptname;
		}catch(Exception e){
			e.getStackTrace();
			return null;
		}
	}


	/**
	 * 查询待考核的代办列表
	 * @return
	 */
	public List findWaitExamTroubles(UserInfo user){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct t.id,t.trouble_name,t.trouble_send_man,to_char(t.trouble_start_time,'yyyy-mm-dd hh24:mi:ss') trouble_start_time,");
		sb.append("decode(trouble_state,'0','待反馈','20','待审核','30','审核不通过','31','审核通过','40','待考核','50','完成') as trouble_state");
		sb.append(",trouble_state state,to_char(t.trouble_send_time,'yyyy-mm-dd hh24:mi:ss') trouble_send_time");
		sb.append(",decode(is_great_trouble,'1','是','0','否','否') AS is_great_trouble,'' as flow_state ");
		sb.append(" from lp_trouble_info t,lp_trouble_process_unit unit ");
		sb.append("  where unit.trouble_id=t.id and t.trouble_state=?");
		values.add(TroubleConstant.TROUBLE_REPLY_WATI_EXAM);
		sb.append(" and t.region_id=?");
		values.add(user.getRegionID());
		sb.append(" order by trouble_start_time desc");
		return this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}

	/**
	 * 得到故障类型名称
	 * @param replyid 反馈单
	 * @return
	 *//*
	public String getTroubleType(String replyid){
		String lable="";
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select df.lable from lp_trouble_reply reply,dictionary_formitem df ");
		sb.append("where reply.trouble_type=df.code and df.assortment_id=?");
		values.add(TroubleConstant.ASSORTMENT_TROUBLE_TYPE);
		sb.append(" and reply.id=?");
		values.add(replyid);
		List list =  this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
		if(list!=null && list.size()>0){
			BasicDynaBean bean = (BasicDynaBean)list.get(0);
			lable = (String) bean.get("lable");
		}
			return lable;
	}*/


	/**
	 * 得到故障原因名称
	 * @param troubleid 故障id
	 * @return
	 */
	public String getTroubleReasonName(String troubleid){
		String lable="";
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select df.lable from lp_trouble_info t,dictionary_formitem df ");
		sb.append("where t.trouble_reason_id=df.code and df.assortment_id=?");
		values.add(TroubleConstant.ASSORTMENT_TROUBLE_REASON);
		sb.append(" and t.id=?");
		values.add(troubleid);
		List list =  this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
		if(list!=null && list.size()>0){
			BasicDynaBean bean = (BasicDynaBean)list.get(0);
			lable = (String) bean.get("lable");
		}
		return lable;
	}

	/**
	 * 根据用户id查询用户
	 * @param userid
	 * @return
	 */
	public UserInfo getUserInfoByUserId(String userid){
		String hql=" from UserInfo u where userID='"+userid+"'";
		UserInfo user = (UserInfo)getSession().createQuery(hql).uniqueResult();
		return user;
	}


	public Contractor getContractorByConId(String deptid){
		String hql=" from Contractor c where contractorID='"+deptid+"'";
		Contractor c = (Contractor)getSession().createQuery(hql).uniqueResult();
		return  c;
	}

	public Depart getDepartByDepartId(String deptid){
		String hql=" from Depart c where deptID='"+deptid+"'";
		Depart d = (Depart)getSession().createQuery(hql).uniqueResult();
		return  d;
	}

}
