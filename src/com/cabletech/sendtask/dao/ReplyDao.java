package com.cabletech.sendtask.dao;

import org.apache.log4j.Logger;
import java.util.List;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.hb.*;
import com.cabletech.sendtask.beans.SendTaskBean;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import java.util.Date;
import java.text.DateFormat;
import java.sql.*;

import javax.servlet.http.HttpSession;

public class ReplyDao {
	Logger logger = Logger.getLogger(this.getClass().getName());

	public ReplyDao() {
	}

	/**<br>功能:获得所有待回复的派单列表
	 *  <br>参数:用户对象
	 *  <br>返回:List
	 * */
	public List getTaskToReply(UserInfo userinfo) {
		List lreply = null;
		String sql = "";
//		if (userinfo.getDeptype().equals("1")) {
//			sql = "select s.serialnumber, s.ID,TO_CHAR(s.SENDTIME,'yyyy-mm-dd HH24:MI') sendtime,s.SENDTYPE,TO_CHAR(s.PROCESSTERM,'yyyy-MM-dd HH24:MI') processterm,s.SENDTOPIC,"
//					+ " us.USERNAME,ua.USERNAME usernameacce,con.contractorname senddeptname, SUBSTR(ad.WORKSTATE,2,6) workstate , ad.id subid"
//					+ " from sendtask s,userinfo us,userinfo ua,contractorinfo con, sendtask_acceptdept ad "
//					+ " where s.SENDUSERID=us.USERID(+) and s.id = ad.sendtaskid and ad.USEID = ua.USERID(+) " 
//					+ " and s.senddeptid=con.contractorid "
//					+ " and  ad.deptid ='"
//					+ userinfo.getDeptID()
//					+ "' and (ad.workstate='3待回复' or ad.workstate='2待重做')"
//					+ " order by s.SENDTIME desc";
//		} else {
//			sql = "select s.serialnumber, s.ID,TO_CHAR(s.SENDTIME,'yyyy-mm-dd HH24:MI') sendtime,s.SENDTYPE,TO_CHAR(s.PROCESSTERM,'yyyy-MM-dd HH24:MI') processterm,s.SENDTOPIC,"
//					+ " us.USERNAME,ua.USERNAME usernameacce,de.deptname senddeptname,SUBSTR(ad.WORKSTATE,2,6) workstate  , ad.id subid"
//					+ " from sendtask s,userinfo us,userinfo ua,deptinfo de, sendtask_acceptdept ad "
//					+ " where s.SENDUSERID=us.USERID(+) and s.id = ad.sendtaskid and ad.USEID = ua.USERID(+) " 
//					+ " and s.senddeptid=de.deptid "
//					+ " and  ad.deptid ='"
//					+ userinfo.getDeptID()
//					+ "'  and (ad.workstate='3待回复' or ad.workstate='2待重做')"
//					+ " order by s.SENDTIME desc";
//
//		}
		sql = "select s.serialnumber, s.ID,s.SENDTYPE,round(to_number(s.processterm - sysdate)*24,1) processterm ,s.SENDTOPIC,"
			+ " ua.USERNAME usernameacce,SUBSTR(ad.WORKSTATE,2,6) workstate  , ad.id subid, ad.useid acceptuserid,"
            + " NVL((select c.contractorname from contractorinfo c where c.contractorid = s.senddeptid), "
            + " 	  (select de.deptname from deptinfo de where de.deptid = s.senddeptid))  senddeptname "
			+ " from sendtask s,userinfo ua,sendtask_acceptdept ad "
			+ " where  s.id = ad.sendtaskid and ad.USEID = ua.USERID(+) " 
			+ " and  ad.deptid ='"
			+ userinfo.getDeptID()
			+ "'  and (ad.workstate='3待回复' or ad.workstate='2待重做')"
			+ " order by s.SENDTIME desc";

		//  System.out.println( "SQL:" + sql );

		try {
			QueryUtil qu = new QueryUtil();
			lreply = qu.queryBeans(sql);
			return lreply;
		} catch (Exception ex) {
			logger.warn("获得所有待回复的派单列表出错:" + ex.getMessage());
			return null;
		}
	}
	
	/**<br>功能:获得登录人员所有待回复的派单列表
	 *  <br>参数:用户对象
	 *  <br>返回:List
	 * */
	public List getLoginUserTaskToReply(UserInfo userinfo) {
		List lreply = null;
		String sql  = "select s.serialnumber, s.ID,s.SENDTYPE,round(to_number(s.processterm - sysdate)*24,1) processterm ,s.SENDTOPIC,"
					+ " ua.USERNAME usernameacce,SUBSTR(ad.WORKSTATE,2,6) workstate  , ad.id subid, ad.useid acceptuserid,"
					+ " NVL((select c.contractorname from contractorinfo c where c.contractorid = s.senddeptid), "
					+ " 	  (select de.deptname from deptinfo de where de.deptid = s.senddeptid))  senddeptname "
					+ " from sendtask s,userinfo ua,sendtask_acceptdept ad "
					+ " where  s.id = ad.sendtaskid and ad.USEID = ua.USERID(+) " 
					+ " and  ad.useid ='"
					+ userinfo.getUserID()
					+ "'  and (ad.workstate='3待回复' or ad.workstate='2待重做')"
					+ " order by s.SENDTIME desc";
		try {
			QueryUtil qu = new QueryUtil();
			lreply = qu.queryBeans(sql);
			return lreply;
		} catch (Exception ex) {
			logger.warn("获得所有待回复的派单列表出错:" + ex.getMessage());
			return null;
		}
	}
	
	/**
     * 统计部门的待回复单的个数、个人待回复单个数
     * @param userinfo
     * @return
     */
    public List getReplyCountList(UserInfo userinfo) {
        List countList = null;
        String sql = "";
        
        sql = "select deptnum, usernum from "
        	+ "	(select count(*) deptnum from ("
    	    + " 	select s.id from sendtask m left join sendtask_acceptdept s on m.id = s.sendtaskid "
    	    + " 	where  (s.workstate='3待回复' or s.workstate='2待重做') and s.deptid = '" + userinfo.getDeptID() + "'"
    	 	+ " 	group by s.id )),"
    	 	+ " (select count(*) usernum from sendtask_acceptdept  " 
    	 	+ " where (workstate='3待回复' or workstate='2待重做') and useid = '" + userinfo.getUserID() + "')";

        try{
            QueryUtil qu = new QueryUtil();
            countList = qu.queryBeans( sql );
            return countList;
        }
        catch( Exception ex ){
            logger.warn( "获得所有待签收的派单列表出错:" + ex.getMessage() );
            return null;
        }
    	
    }

	/**<br>功能:保存派单回复信息
	 *  <br>参数:派单Bean
	 *  <br>返回:填写成功返回true 否则返回 false
	 * */
	public boolean saveReply(SendTaskBean bean) {

		Date nowDate = new Date();
		DateFormat df = DateFormat.getDateTimeInstance();
		String date = df.format(nowDate);

		String replyremark = bean.getReplyremark().length() > 1024 ? bean
				.getReplyremark().substring(0, 1024) : bean.getReplyremark();
		String sql1;
		// 如果回复表中存在记录的，证明是验过不通过，打回来的记录的，所以更新数据库表，否则新增一条记录
		if(bean.getReplyid() != null && !("".equals(bean.getReplyid()))) {
			sql1 = "update sendtaskreply set replytime = TO_DATE('" + date + "','yyyy-MM-dd HH24:MI:SS'),"
			       + "replyuserid = '" +  bean.getReplyuserid() + "', "
			       + "replyresult = '" + bean.getReplyresult() + "', "
			       + "replyacce = '" + bean.getReplyacce() + "', "
			       + "replyremark = '" + bean.getReplyremark() + "' "
			       + "where id = '" + bean.getReplyid() + "'";
		} else {
			OracleIDImpl ora = new OracleIDImpl();
			String id = ora.getSeq("sendtaskreply", 10);
			if (id.equals("")) {
				return false;
			}
			sql1 = "insert into sendtaskreply (id,replytime,replyuserid,replyresult,replyacce,replyremark,sendtaskid) values ('"
				+ id
				+ "',TO_DATE('"
				+ date
				+ "','yyyy-MM-dd HH24:MI:SS'),'"
				+ bean.getReplyuserid()
				+ "','"
				+ bean.getReplyresult()
				+ "','"
				+ bean.getReplyacce()
				+ "','"
				+ replyremark
				+ "','"
				+ bean.getSubtaskid() + "')";
		}
		String sql2 = "";
//		sql2 = "update sendtask set replyid='" + id
//				+ "',workstate='6待验证' where id='" + bean.getId() + "'";
		sql2 = "update SENDTASK_ACCEPTDEPT set workstate='6待验证' where id='" + bean.getSubtaskid() + "'";

		//       System.out.println( "SQL:" + sql1 );
		try {
			UpdateUtil up = new UpdateUtil();
			up.setAutoCommitFalse();
			try {
				up.executeUpdate(sql1);
				up.executeUpdate(sql2);
				up.commit();
				up.setAutoCommitTrue();
				return true;
			} catch (Exception ex) {
				logger.warn("保存派单回复信息出错1:" + ex.getMessage());
				up.rollback();
				return false;
			}
		} catch (Exception ex) {
			logger.warn("保存派单回复信息出错:" + ex.getMessage());
			return false;
		}
	}

	/**<br>功能:获得所有回复的派单列表
	 *  <br>参数:用户对象
	 *  <br>返回:受理人编号和名称的List
	 * */
	public List getreplyList(UserInfo userinfo) {
		List lsend = null;
		String sql = "";
//		if (userinfo.getDeptype().equals("1")) {
//			sql = "select s.serialnumber, s.ID,TO_CHAR(s.SENDTIME,'yyyy-mm-dd HH24:MI') sendtime,s.SENDTYPE,TO_CHAR(s.PROCESSTERM,'yyyy-MM-dd HH24:MI') processterm,s.SENDTOPIC,"
//					+ " us.USERNAME,ua.USERNAME usernameacce,con.contractorname senddeptname, "
//					+ " SUBSTR(ad.WORKSTATE,2,6) workstate,TO_CHAR(sr.replytime,'yyyy-MM-dd HH24:MI') time,sr.replyresult, ad.id subid"
//					+ " from sendtask s,userinfo us,userinfo ua,contractorinfo con,sendtaskreply sr, sendtask_acceptdept ad "
//					+ " where s.SENDUSERID=us.USERID(+) and s.id = ad.sendtaskid and ad.USEID = ua.USERID(+)"
//					+ " and s.senddeptid=con.contractorid "
//					//+ " and s.replyid = sr.ID"
//					+ " and ad.id = sr.sendtaskid "
//					+ " and  ad.deptid ='"
//					+ userinfo.getDeptID()
//					+ "' and (ad.workstate='6待验证' or ad.workstate='2待重做' or ad.workstate='9已存档' )"
//					+ " order by sr.replytime desc,s.SENDTIME desc";
//		} else {
//			sql = "select s.serialnumber, s.ID,TO_CHAR(s.SENDTIME,'yyyy-mm-dd HH24:MI') sendtime,s.SENDTYPE,TO_CHAR(s.PROCESSTERM,'yyyy-MM-dd HH24:MI') processterm,s.SENDTOPIC,"
//					+ " us.USERNAME,ua.USERNAME usernameacce,de.deptname senddeptname,"
//					+ " SUBSTR(ad.WORKSTATE,2,6) workstate,TO_CHAR(sr.replytime,'yyyy-MM-dd HH24:MI') time,sr.replyresult, ad.id subid"
//					+ " from sendtask s,userinfo us,userinfo ua,deptinfo de,sendtaskreply sr, sendtask_acceptdept ad "
//					+ " where s.SENDUSERID=us.USERID(+) and s.id = ad.sendtaskid and ad.USEID = ua.USERID(+)" 
//					+ " and s.senddeptid=de.deptid "
//					//+ " and s.replyid = sr.ID"
//					+ " and ad.id = sr.sendtaskid "
//					+ " and  ad.acceptdeptid ='"
//					+ userinfo.getDeptID()
//					+ "'  and (ad.workstate='6待验证' or ad.workstate='2待重做' or ad.workstate='9已存档')"
//					+ " order by sr.replytime desc,s.SENDTIME desc";
//
//		}
		
		sql = "select s.serialnumber, s.ID,TO_CHAR(s.SENDTIME,'yyyy-mm-dd HH24:MI') sendtime,s.SENDTYPE,TO_CHAR(s.PROCESSTERM,'yyyy-MM-dd HH24:MI') processterm,s.SENDTOPIC,"
			+ " us.USERNAME,ua.USERNAME usernameacce,ad.useid acceptuserid, "
			+ " SUBSTR(ad.WORKSTATE,2,6) workstate,TO_CHAR(sr.replytime,'yyyy-MM-dd HH24:MI') time,sr.replyresult, ad.id subid"
			+ " from sendtask s,userinfo us,userinfo ua,sendtaskreply sr, sendtask_acceptdept ad "
			+ " where s.SENDUSERID=us.USERID(+) and s.id = ad.sendtaskid and ad.USEID = ua.USERID(+)" 
			+ " and ad.id = sr.sendtaskid "
			+ " and  ad.acceptdeptid ='"
			+ userinfo.getDeptID()
			+ "'  and (ad.workstate='6待验证' or ad.workstate='2待重做' or ad.workstate='9已存档')"
			+ " order by sr.replytime desc,s.SENDTIME desc";

		//System.out.println( "SQL:" + sql );

		try {
			QueryUtil qu = new QueryUtil();
			lsend = qu.queryBeans(sql);
			return lsend;
		} catch (Exception ex) {
			logger.warn("获得所有回复的派单列表出错:" + ex.getMessage());
			return null;
		}
	}

	/**<br>功能:获得派单回复的详细信息
	 *  <br>参数:派单编号,用户bean
	 *  <br>返回: 派单bean
	 * */
	public SendTaskBean getOneSendTask(String id, UserInfo userinfo) {
		SendTaskBean bean = new SendTaskBean();
		ResultSet rst;
		String sql = "select sr.ID,TO_CHAR(sr.REPLYTIME,'yyyy-MM-dd HH24:MI') replytime,"
				+ " sr.REPLYRESULT,sr.replyremark,sr.REPLYACCE,u.USERNAME,sr.replyuserid"
//				+ " from sendtask s,sendtaskreply sr,userinfo u "
//				+ " where  s.REPLYID = sr.ID"
//				+ " and sr.REPLYUSERID = u.USERID "
//				+ " and s.ID = '"
//				+ id
//				+ "'";
				+ " from SENDTASK_ACCEPTDEPT ad , sendtaskreply sr,userinfo u "
				+ " where  sr.sendtaskid = ad.id "
				+ " and sr.REPLYUSERID = u.USERID(+) "
				+ " and ad.ID = '"
				+ id
				+ "'";
		//    System.out.println( "SQL:" + sql );
		try {
			QueryUtil qu = new QueryUtil();
			rst = qu.executeQuery(sql);
			try {
				if (rst != null && rst.next()) {
					bean.setReplyid(rst.getString("id"));
					bean.setReplytime(rst.getString("replytime"));
					bean.setReplyresult(rst.getString("replyresult"));
					bean.setReplyremark(rst.getString("replyremark"));
					bean.setReplyacce(rst.getString("replyacce"));
					bean.setReplyusername(rst.getString("username"));
					bean.setReplyuserid(rst.getString("replyuserid"));
					rst.close();
					return bean;
				}
				rst.close();
				return null;

			} catch (Exception ex) {
				logger.warn("获得待签收派单的详细信息出错1:" + ex.getMessage());
				rst.close();
				return null;
			}

		} catch (Exception ex) {
			logger.warn("获得待签收派单的详细信息出错:" + ex.getMessage());
			return null;
		}
	}

	//
	/**<br>功能:修改派单的回复
	 *  <br>参数:派单Bean
	 *  <br>返回:填写成功返回true 否则返回 false
	 * */
	public boolean upReply(SendTaskBean bean) {

		Date nowDate = new Date();
		DateFormat df = DateFormat.getDateTimeInstance();
		String date = df.format(nowDate);

		String replyremark = bean.getReplyremark().length() > 1024 ? bean
				.getReplyremark().substring(0, 1024) : bean.getReplyremark();
		bean.setReplyremark(replyremark);
		if (bean.getWorkstate().equals("待重做"))
			return saveReply(bean);//如果已经进行了验证,是验证没有通过,则在回复中添加一条新的记录,同时修改基本表
		else {//如果是没有经过验证的修改,则进行修改
			String sql1 = "update sendtaskreply set replytime=TO_DATE('" + date
					+ "','yyyy-MM-dd HH24:MI:SS')," + "replyuserid='"
					+ bean.getReplyuserid() + "',replyresult='"
					+ bean.getReplyresult() + "'," + "replyacce ='"
					+ bean.getReplyacce() + "',replyremark='"
					+ bean.getReplyremark() + "'" + " where id='"
					+ bean.getReplyid() + "'";

			//       System.out.println( "SQL:" + sql1 );
			try {
				UpdateUtil up = new UpdateUtil();
				up.setAutoCommitFalse();
				try {
					up.executeUpdate(sql1);
					up.commit();
					up.setAutoCommitTrue();
					return true;
				} catch (Exception ex) {
					logger.warn("修改派单回复信息出错1:" + ex.getMessage());
					up.rollback();
					return false;
				}
			} catch (Exception ex) {
				logger.warn("修改派单回复信息出错:" + ex.getMessage());
				return false;
			}
		}
	}

	/**
	 * <br>功能:条件查找派单信息
	 * <br>参数:request
	 * <br>返回值:获得成功返回List,否则返回 NULL;
	 */
	public List queryReplyList(UserInfo userinfo, SendTaskBean bean,
			HttpSession session) {
		List lsend = null;
		String sql = "";
//		if (userinfo.getDeptype().equals("1")) {
//			sql = "select s.serialnumber, s.ID,TO_CHAR(s.SENDTIME,'yyyy-mm-dd HH24:MI') sendtime,s.SENDTYPE,TO_CHAR(s.PROCESSTERM,'yyyy-MM-dd HH24:MI') processterm,s.SENDTOPIC,"
//					+ " us.USERNAME,ua.USERNAME usernameacce,con.contractorname senddeptname, "
//					+ " SUBSTR(ad.WORKSTATE,2,6) workstate,TO_CHAR(sr.replytime,'yyyy-MM-dd HH24:MI') time,sr.replyresult, ad.id subid"
//					+ " from sendtask s,userinfo us,userinfo ua,contractorinfo con,sendtaskreply sr , sendtask_acceptdept ad "
//					+ " where s.SENDUSERID=us.USERID(+) and s.id = ad.sendtaskid and ad.USEID = ua.USERID(+) " 
//					+ " and s.senddeptid=con.contractorid "
//					//+ " and s.replyid = sr.ID"
//					+ " and ad.id = sr.sendtaskid "
//					+ " and  ad.deptid ='"
//					+ userinfo.getDeptID()
//					+ "' and (ad.workstate='6待验证' or ad.workstate='2待重做' or ad.workstate='9已存档' )";
//		} else {
//			sql = "select s.serialnumber, s.ID,TO_CHAR(s.SENDTIME,'yyyy-mm-dd HH24:MI') sendtime,s.SENDTYPE,TO_CHAR(s.PROCESSTERM,'yyyy-MM-dd HH24:MI') processterm,s.SENDTOPIC,"
//					+ " us.USERNAME,ua.USERNAME usernameacce,de.deptname senddeptname,"
//					+ " SUBSTR(ad.WORKSTATE,2,6) workstate,TO_CHAR(sr.replytime,'yyyy-MM-dd HH24:MI') time,sr.replyresult ,ad.id subid"
//					+ " from sendtask s,userinfo us,userinfo ua,deptinfo de,sendtaskreply sr , sendtask_acceptdept ad  "
//					+ " where s.SENDUSERID=us.USERID(+) and s.id = ad.sendtaskid and ad.USEID = ua.USERID(+) "
//					+ " and s.senddeptid=de.deptid "
//					//+ " and s.replyid = sr.ID"
//					+ " and ad.id = sr.sendtaskid "
//					+ " and  ad.deptid ='"
//					+ userinfo.getDeptID()
//					+ "'  and (ad.workstate='6待验证' or ad.workstate='2待重做' or ad.workstate='9已验证' )";
//
//		}
		
		
		sql = "select s.serialnumber, s.ID,s.SENDTYPE,s.SENDTOPIC,"
			+ "  case when s.PROCESSTERM < sr.replytime then  round(to_number(s.processterm - sr.replytime )*24,1) "
      		+ " else 0 end processterm ,"
			+ " ua.USERNAME usernameacce,ad.useid acceptuserid,"
			+ " SUBSTR(ad.WORKSTATE,2,6) workstate,ad.id subid,"
			+ "  NVL((select c.contractorname from contractorinfo c where c.contractorid = s.senddeptid),"
			+ " 	  (select de.deptname from deptinfo de where de.deptid = s.senddeptid))  senddeptname "
			+ " from sendtask s,userinfo ua,sendtaskreply sr , sendtask_acceptdept ad  "
			+ " where s.id = ad.sendtaskid and ad.USEID = ua.USERID(+) "	
			+ " and ad.id = sr.sendtaskid "
			+ " and  ad.deptid ='"
			+ userinfo.getDeptID()
			+ "'  and (ad.workstate='6待验证' or ad.workstate='2待重做' or ad.workstate='9已验证' )";

		//      System.out.println( "SQL:" + sql );

		try {
//			if (bean.getSendusername() != null
//					&& !bean.getSendusername().equals("")) {
//				sql = sql + " and us.USERNAME like '" + bean.getSendusername()
//						+ "%' ";
//			}
//			if (bean.getAcceptusername() != null
//					&& !bean.getAcceptusername().equals("")) {
//				sql = sql + "  and ua.USERNAME like '"
//						+ bean.getAcceptusername() + "%'  ";
//			}
			if (bean.getSendtype() != null && !bean.getSendtype().equals("")) {
				sql = sql + "  and s.SENDTYPE like '" + bean.getSendtype()
						+ "%'  ";
			}
			if (bean.getSendtopic() != null && !bean.getSendtopic().equals("")) {
				sql = sql + "  and s.SENDTOPIC like '" + bean.getSendtopic()
						+ "%'  ";
			}
			if (bean.getProcessterm() != null
					&& !bean.getProcessterm().equals("")) {
				if (bean.getProcessterm().equals("超期")) {
					sql +=  " AND s.PROCESSTERM < NVL (sr.replytime,SYSDATE)";
				} else {
					sql +=  " AND s.PROCESSTERM >= NVL (sr.replytime,SYSDATE)";
				}
			}

			if (bean.getWorkstate() != null && !bean.getWorkstate().equals("")) {
				sql = sql + "  and ad.WORKSTATE like '" + bean.getWorkstate()
						+ "%'  ";
			}
			if (bean.getResult() != null && !bean.getResult().equals("")) {
				sql = sql + "  and sr.replyresult like '" + bean.getResult()
						+ "%'  ";
			}

			if (bean.getBegintime() != null && !bean.getBegintime().equals("")) {
				sql = sql + " and sr.replytime >=TO_DATE('"
						+ bean.getBegintime()
						+ " 00:00:00','YYYY-MM-DD HH24:MI:SS ')";
			}
			if (bean.getEndtime() != null && !bean.getEndtime().equals("")) {
				sql = sql + " and sr.replytime <= TO_DATE('"
						+ bean.getEndtime()
						+ " 23:59:59','YYYY-MM-DD HH24:MI:SS')";
			}
			sql = sql + " order by sr.replytime desc,s.SENDTIME desc";

			//System.out.println( "SQL:" + sql );
			session.setAttribute("replayQueryCon", sql);
			QueryUtil query = new QueryUtil();
			lsend = query.queryBeans(sql);
			return lsend;
		} catch (Exception e) {
			logger.error("条件查找派单信息异常:" + e.getMessage());
			return null;
		}
	}
	
	/**
	 * 修改后返回
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
		} finally {
			query.close();
		}
	}
}
