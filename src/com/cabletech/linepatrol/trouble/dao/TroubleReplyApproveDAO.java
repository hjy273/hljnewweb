package com.cabletech.linepatrol.trouble.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.commons.constant.CommonConstant;
import com.cabletech.linepatrol.commons.module.ApproveInfo;
import com.cabletech.linepatrol.trouble.module.TroubleReply;
import com.cabletech.linepatrol.trouble.services.TroubleConstant;

@Repository
public class TroubleReplyApproveDAO extends HibernateDao<ApproveInfo, String> {



	/**
	 * ���Ϸ������
	 * @return
	 */
	public List getReplyWaitApproves(String deptid){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select reply.id,c.contractorname,u.username,info.trouble_start_time,info.trouble_send_time");
		sb.append(" ,decode(is_great_trouble,'1','��','0','��','��') AS is_great_trouble,");
		sb.append(" '�����' as reply.approve_state ");
		sb.append(" from lp_trouble_info info,lp_trouble_reply reply,lp_reply_approver approver,");
		sb.append(" contractorinfo c,userinfo u ");
		sb.append(" where info.id=reply.trouble_id and reply.id=approver.object_id and reply.approve_state=?");
		values.add(TroubleConstant.REPLY_APPROVE_WAIT);
		sb.append(" and reply.contractor_id=c.contractorid and u.userid= info.send_man_id");
		return this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}

	/**
	 * ���ݹ���id��ѯ��������Ϣ
	 * @param condition
	 * @param userid useridΪ�ղ�ѯʱ����userid��Ϊ�գ��ƶ����ʱ��ѯ
	 * @param act �ж��ǲ���ƽ̨���
	 * @return
	 */

	public List getReplyInfos(String troubleid,String userid,String act){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(selReplySql());
		if(act!=null && act.equals("edit_dispatch_task")){
			act="dispatch";
		}
		if(userid!=null&&!"".equals(userid)){
			if(act!=null && act.equals("dispatch")){//ƽ̨��˲鿴
				sb.append(",lp_trouble_info t ");
			}else if(act!=null && act.equals("reply_task")){
				
			}else{
				sb.append(",lp_reply_approver a");
			}
		}
		sb.append(" where r.contractor_id=c.contractorid ");
		sb.append("  and  r.trouble_id=?");
		values.add(troubleid);
		if(userid!=null&&!"".equals(userid)){
			if(act!=null && act.equals("dispatch")){
				sb.append(" and t.id = r.trouble_id and t.send_man_id=?");
				values.add(userid);
				sb.append(" and r.approve_state=?");
				values.add(TroubleConstant.REPLY_APPROVE_DISPATCH);
			}else if(act!=null && act.equals("reply_task")){
				sb.append(" and r.reply_man_id=?");
				values.add(userid);
			}else{
				sb.append(" and a.object_id=r.id and a.approver_id=?");
				values.add(userid);
				sb.append(" and a.object_type=? and finish_readed !=?");
				values.add(TroubleConstant.LP_TROUBLE_REPLY);
				values.add(CommonConstant.FINISH_READED);
			}
		}
		
		logger.info("getInfos=========== "+sb.toString());
		return this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}

	/**
	 * �鿴��������ϸ
	 * @param user
	 * @param troubleid
	 */
	public List viewReplys(UserInfo user,String troubleid ){
		String deptype = user.getDeptype();
		String deptid = user.getDeptID();
		String userid = user.getUserID();
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(selReplySql());
		sb.append(",lp_trouble_info t ");
		sb.append(" where r.contractor_id=c.contractorid and t.id = r.trouble_id ");
		sb.append("  and  r.trouble_id=?");
		values.add(troubleid);
		if(deptype.equals("2")){
			sb.append("  and  (r.contractor_id=? or t.send_man_id=?)");
			values.add(deptid);
			values.add(userid);
		}
	//	logger.info("ssssssssssssssss=============== "+sb.toString());
		return this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}


	/**
	 * �����˲鿴����
	 * @param user
	 * @param troubleid
	 */
	public List viewReplysByReads(UserInfo user,String troubleid ){
		String userid = user.getUserID();
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(selReplySql());
		sb.append(",lp_reply_approver a ");
		sb.append(" where r.contractor_id=c.contractorid ");
		sb.append("  and  r.trouble_id=?");
		values.add(troubleid);
		sb.append(" and a.object_id=r.id and a.approver_id=?");
		values.add(userid);
		sb.append(" and a.object_type=? and finish_readed !=?");
		values.add(TroubleConstant.LP_TROUBLE_REPLY);
		values.add(CommonConstant.FINISH_READED);
		sb.append(" and a.approver_type=?");
		values.add(CommonConstant.APPROVE_READ);
	//	logger.info("�����˲鿴����s=============== "+sb.toString());
		return this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}
	
	
	/**
	 * ����˲鿴����
	 * @param user
	 * @param troubleid
	 */
	public List viewReplysByApproves(UserInfo user,String troubleid ){
		String userid = user.getUserID();
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(selReplySql());
		sb.append(",lp_reply_approver a ");
		sb.append(" where r.contractor_id=c.contractorid ");
		sb.append("  and  r.trouble_id=?");
		values.add(troubleid);
		sb.append(" and a.object_id=r.id and a.approver_id=?");
		values.add(userid);
		sb.append(" and a.object_type=? and finish_readed !=?");
		values.add(TroubleConstant.LP_TROUBLE_REPLY);
		values.add(CommonConstant.FINISH_READED);
		sb.append(" and a.is_transfer_approved=0");
		//logger.info("����˲鿴����s=============== "+sb.toString());
		return this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}
	
	/**
	 * �ж��ǲ����Լ��ɷ��ĵ���
	 * 
	 */
	public boolean getDispatchTrouble(UserInfo user,String troubleid ){
		String hql=" from TroubleInfo t where t.sendManId=? and t.id=?";
		List trouble = this.find(hql,user.getUserID(),troubleid);
		if(trouble!=null && trouble.size()>0){
			return true;
		}
		return false;
	}


	public String selReplySql(){
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct r.id,c.contractorid, c.contractorname,");
		sb.append(" r.reply_submit_time,r.approve_state state,");
		sb.append(" decode(r.approve_state,'0','�����','01','��ƽ̨��׼', ");
		sb.append(" '1','��˲�ͨ��','2','���ͨ��','00','��ʱ����') as approve_state");
		sb.append(",decode(r.confirm_result,'0','Э��','1','����') as confirm_result,");
		sb.append(" r.reply_remark,confirm_result cr ,'' as isread ");
		sb.append(" from contractorinfo c,lp_trouble_reply r ");
		return sb.toString();
	}


	/**
	 * �õ��������Լ����ϵ���Ϣ
	 * @param replyid
	 * @return
	 */
	public BasicDynaBean getTrouble(String replyid){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select t.id tid,r.id rid, t.trouble_id,u.username,t.trouble_send_man,to_char(t.reply_deadline,'yyyy-mm-dd hh24:mi:ss') as reply_deadline, ");
		sb.append(" NVL(t.trouble_reason_id,'') trouble_reason_id ,");
		sb.append(" to_char(t.trouble_send_time,'yyyy-mm-dd hh24:mi:ss') trouble_send_time, ");
		sb.append(" to_char(t.trouble_start_time,'yyyy-mm-dd hh24:mi:ss') trouble_start_time,region_id,");
		sb.append(" c.contractorname deptname,u.deptid,");
		sb.append(" to_char(reply_submit_time,'yyyy-mm-dd hh24:mi:ss') reply_submit_time,");
		sb.append("  decode(t.is_great_trouble,'1','��','0','��','��') AS is_great_trouble,");
		sb.append(" to_char(trouble_end_time,'yyyy-mm-dd hh24:mi:ss') trouble_end_time,");
		sb.append(" decode(t.trouble_type,'1','��֪','0','Ѳ��') AS trouble_type ");
		sb.append(" from contractorinfo c,userinfo u,userinfo u1,lp_trouble_info t,lp_trouble_reply r");
		sb.append(" where t.send_man_id=u1.userid and u1.deptid=c.contractorid and u.userid = r.reply_man_id");
		sb.append(" and t.id=r.trouble_id and r.id=?");
		values.add(replyid);
		sb.append(" union ");
		sb.append("select t.id tid,r.id rid, t.trouble_id,u.username,t.trouble_send_man,to_char(t.reply_deadline,'yyyy-mm-dd hh24:mi:ss') as reply_deadline,");
		sb.append(" NVL(t.trouble_reason_id,'') trouble_reason_id ,");
		sb.append(" to_char(t.trouble_send_time,'yyyy-mm-dd hh24:mi:ss') trouble_send_time ,");
		sb.append(" to_char(t.trouble_start_time,'yyyy-mm-dd hh24:mi:ss') trouble_start_time,region_id,");
		sb.append(" d.deptname deptname,u.deptid,");
		sb.append(" to_char(reply_submit_time,'yyyy-mm-dd hh24:mi:ss') reply_submit_time,");
		sb.append(" decode(t.is_great_trouble,'1','��','0','��','��') AS is_great_trouble,");
		sb.append(" to_char(trouble_end_time,'yyyy-mm-dd hh24:mi:ss') trouble_end_time,");
		sb.append(" decode(t.trouble_type,'1','��֪','0','Ѳ��') AS trouble_type ");
		sb.append(" from deptinfo d,userinfo u,userinfo u1,lp_trouble_info t,lp_trouble_reply r");
		sb.append(" where  t.send_man_id=u1.userid and u1.deptid=d.deptid and u.userid = r.reply_man_id");
		sb.append(" and t.id=r.trouble_id and r.id=?");
		values.add(replyid);
		List list = this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
		if(list !=null && list.size()>0){
			return (BasicDynaBean) list.get(0);
		}
		return null;
	}

	/**
	 * ��ѯ�������������ʷ
	 * @param replyid
	 * @return
	 */
	public List getApproveHistorys(String replyid){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select appinfo.id,u.username,to_char(approve_time,'yyyy-mm-dd hh24:mi:ss')  approve_time,approve_remark,");
		sb.append(" decode(approve_result,'0','��ͨ��','1','ͨ��','2','ת��') AS approve_result");
		sb.append(" from lp_approve_info appinfo,userinfo u");
		sb.append(" where appinfo.approver_id=u.userid and appinfo.object_id=?");
		values.add(replyid);
		sb.append(" and object_type=?");
		values.add(TroubleConstant.LP_TROUBLE_REPLY);
		return this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}

	/**
	 * ��ѯ�����˵Ĺ��ϵķ�����
	 * @param troubleid ���ϵ�ϵͳid
	 * @return
	 */
	public List getWaitExamTroubleReplys(String troubleid,String userid){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select distinct r.id,c.contractorname,r.approve_state state,");
		sb.append(" to_char(r.reply_submit_time,'yyyy-mm-dd hh24:mi:ss') reply_submit_time,");
		sb.append(" decode(confirm_result,'0','Э��','1','����') AS confirm_result,confirm_result cr");
		sb.append(" from lp_trouble_reply r,contractorinfo c ");
		if(userid!=null&&!"".equals(userid)){
			sb.append(",lp_reply_approver a");
		}
		sb.append(" where r.contractor_id=c.contractorid and r.trouble_id=?");
		values.add(troubleid);
		if(userid!=null&&!"".equals(userid)){
			sb.append(" and a.object_id=r.id and a.approver_id=?");
			values.add(userid);
		}
		return this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}
	

	/**
	 * ����ָ���Ĺ����ɵ������
	 * �ж��Ƿ����еķ��������Ѿ����ͨ��
	 * @param troubleid �����ɵ����
	 * @return
	 */
	public boolean judgeAllApproved(String troubleid){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select * ");
		sb.append(" from lp_trouble_reply reply ");
		sb.append(" where reply.approve_state= ? ");
		values.add(TroubleConstant.REPLY_APPROVE_NO);
		sb.append(" and reply.trouble_id=?");
		values.add(troubleid);
		List list = this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
		if(list== null || list.size()==0){
			return true;
		}
		return false;
	}


	/**
	 * ����ָ���Ĺ����ɵ�������ж��Ƿ����û�н��з����Ĵ�ά��λ
	 * @param toroubleid
	 * @return
	 */
	public boolean judgeExistNoReply(String troubleid){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select u.process_unit_id from lp_trouble_process_unit u");
		sb.append("where u.trouble_id=? ");
		values.add(troubleid);
		sb.append(" not exists (select r.contractor_id from  lp_trouble_reply r where r.trouble_id=? ");
		values.add(troubleid);
		sb.append(")");
		List list = this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
		if(list== null || list.size()==0){
			return true;
		}
		return false;
	}


	/**
	 * ���ݹ��Ϸ�����Ϣ���жϸù��Ϸ�����Ϣ�Ƿ��Ѿ����ڡ�
	 * @param replyInfo
	 * @return true:������  false���Ѿ�����
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
	 * ����ָ���Ĺ����ɵ�������ж��Ƿ����еķ��������Ѿ�����������
	 * @param troubleid �����ɵ����
	 * @return
	 */
	public boolean judgeAllEvaluated(String troubleid){

		return false;
	}

	/**
	 * ����ָ���Ĺ��Ϸ�����Ϣ���ж��Ƿ�Ը÷�����Ϣ���п���������
	 * @param replyInfo
	 * @return
	 */
	public boolean judgeNeedEvaluate(TroubleReply replyInfo){
		return false;

	}

	/**
	 * ����ָ���ķ����������д����Ҫ����������Ϣ��
	 * @param replyid
	 * @return
	 */
	public boolean writeNeedEvaluate(String replyid){
		return false;
	}

	/**
	 * ����ָ���ķ����������д�벻��Ҫ����������Ϣ��
	 * @param replyid
	 * @return
	 */
	public boolean writeNotNeedEvaluate(String replyid){
		return false;
	}
}
