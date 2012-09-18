package com.cabletech.sendtask.dao;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.sendtask.beans.SendTaskBean;
import org.apache.commons.beanutils.DynaBean;

public class SendTaskDao {
	Logger logger = Logger.getLogger(this.getClass().getName());

	public SendTaskDao() {
	}

	/**<br>����:���ָ���û��ĵ绰����
	 *  <br>����:�û����
	 *  <br>����:ָ���û��ĵ绰����
	 * */
	public String getSendPhone(String userid) {

		String sql = "select phone from userinfo where userid='" + userid + "'";
		//  System.out.println( "SQL:" + sql );
		try {
			QueryUtil qu = new QueryUtil();
			String[][] temp = qu.executeQueryGetArray(sql, "");
			if (temp[0][0].length() != 11) {
				return null;
			}
			return temp[0][0];
		} catch (Exception ex) {
			logger.warn("��û��ָ���û��ĵ绰�������:" + ex.getMessage());
			return null;
		}
	}

	/**<br>����:����������б�
	 *  <br>����:�û�����
	 *  <br>����:�����ű�ź����Ƶ�List
	 * */
	public List getAcceptdept(UserInfo userinfo) {
		List lDept = null;
		String sql = "";
		//        if( userinfo.getDeptype().equals( "2" ) ){
		//            sql = "select d.DEPTID,d.DEPTNAME from deptinfo d where d.state is null and d.regionid='"
		//                  + userinfo.getRegionID() + "'";
		//        }
		//        else{
		//            sql =
		//                "select c.CONTRACTORID deptid,c.CONTRACTORNAME deptname from contractorinfo c where c.state is null and c.regionid='"
		//                + userinfo.getRegionID() + "'";
		//        }

		// guixy modify by 2008-12-02 ����Ҫ���ά֮�䡢�ƶ����ż�����ɵ�
		sql = "select * from "
				+ "(select d.DEPTID,d.DEPTNAME from deptinfo d where d.state is null and d.regionid='"
				+ userinfo.getRegionID()
				+ "')"
				+ " union "
				+ "(select c.CONTRACTORID deptid,c.CONTRACTORNAME deptname from contractorinfo c where c.state is null and c.regionid='"
				+ userinfo.getRegionID() + "')";
		try {
			QueryUtil qu = new QueryUtil();
			lDept = qu.queryBeans(sql);
			return lDept;
		} catch (Exception ex) {
			logger.warn("����������б����:" + ex.getMessage());
			return null;
		}
	}

	/**<br>����:����������б�
	 *  <br>����:�û�����
	 *  <br>����:�����˱�ź����Ƶ�List
	 * */
	public List getAcceptUser(UserInfo userinfo) {
		List luser = null;
		String sql = "";
		//        if( userinfo.getDeptype().equals( "2" ) ){
		//            sql = "select u.USERID,u.USERNAME,u.deptid from userinfo u where u.state is null and u.deptype='1' and u.regionid='"
		//                  + userinfo.getRegionID() + "'"; ;
		//        }
		//        else{
		//            sql = "select u.USERID,u.USERNAME,u.deptid from userinfo u where u.state is null and u.deptype='2' and u.regionid='"
		//                  + userinfo.getRegionID() + "'"; ;
		//        }

		// guixy modify by 2008-12-02 ����Ҫ���ά֮�䡢�ƶ����ż�����ɵ�
		sql = "select u.USERID,u.USERNAME,u.deptid from userinfo u where u.state is null and (u.deptype='1' or u.deptype='2') and u.regionid='"
				+ userinfo.getRegionID() + "'";

		try {
			QueryUtil qu = new QueryUtil();
			luser = qu.queryBeans(sql);
			return luser;
		} catch (Exception ex) {
			logger.warn("����������б����:" + ex.getMessage());
			return null;
		}
	}

	/**<br>����:��д�ɵ�
	 *  <br>����:�ɵ�Bean
	 *  <br>����:��д�ɹ�����true ���򷵻� false
	 * */
	public boolean sendTask(SendTaskBean bean, String[] deptId, String[] userId) {
		OracleIDImpl ora = new OracleIDImpl();
		String id = ora.getSeq("sendtask", 10);
		if (id.equals("")) {
			return false;
		}
		Date nowDate = new Date();
		DateFormat df = DateFormat.getDateTimeInstance();
		String date = df.format(nowDate);

		String sendtext = bean.getSendtext().length() > 1024 ? bean
				.getSendtext().substring(0, 1024) : bean.getSendtext();
		UpdateUtil up = null;
		try {
			up = new UpdateUtil();
			up.setAutoCommitFalse();
			String sql = "insert into sendtask (id,sendtime,senduserid,senddeptid,sendtype,processterm,sendtopic,sendtext,sendacce,regionid,serialnumber) "
					+ " values ('"
					+ id
					+ "',TO_DATE('"
					+ date
					+ "','yyyy-mm-dd HH24:MI:SS'),'"
					+ bean.getSenduserid()
					+ "','"
					+ bean.getSenddeptid()
					+ "','"
					+ bean.getSendtype()
					+ "',"
					+ " TO_DATE('"
					+ bean.getProcessterm()
					+ "','yyyy-MM-dd HH24:MI:SS'),'"
					+ bean.getSendtopic()
					+ "','"
					+ sendtext
					+ "','"
					+ bean.getSendacce()
					+ "' , '"
					+ bean.getRegionid() + "', '" + getSerialnumber() + "')";
			up.executeUpdate(sql);
			String subSql;
			String subId;
			for (int i = 0; i < userId.length; i++) {
				subId = ora.getSeq("sendtask_acceptdept", 15);
				// modify by guixy 2008-12-01 ����״̬
				subSql = "insert into sendtask_acceptdept(id, sendtaskid, deptid, useid, workstate) values ("
						+ "'"
						+ subId
						+ "' , "
						+ "'"
						+ id
						+ "' , "
						+ "'"
						+ deptId[i] + "' , " + "'" + userId[i] + "' , '1��ǩ��')";
				up.executeUpdate(subSql);
			}

			up.commit();
			up.setAutoCommitTrue();
			return true;
		} catch (Exception ex) {
			logger.warn("��д�ɵ�����:" + ex.getMessage());
			if (up != null) {
				up.rollback();
				up.setAutoCommitTrue();
			}

			return false;
		}
	}

	/**<br>����:����ɵ��б�
	 *  <br>����:�û�����
	 *  <br>����:�����˱�ź����Ƶ�List
	 * */
	public List getSendList(UserInfo userinfo) throws Exception {
		List lsend = null;
		String sql = "select s.serialnumber, s.ID,TO_CHAR(s.SENDTIME,'yyyy-mm-dd HH24:MI') sendtime,s.SENDTYPE,TO_CHAR(s.PROCESSTERM,'yyyy-MM-dd HH24:MI:SS') processterm,s.SENDTOPIC,"
				+ " SUBSTR(ad.WORKSTATE,2,6) workstate,us.USERNAME,ua.USERNAME usernameacce,NVL(se.RESULT, '��ǩ��') result , ad.id subid"
				+ " from sendtask s,userinfo us,userinfo ua,sendtaskendorse se, sendtask_acceptdept ad "
				+ " where s.SENDUSERID=us.USERID(+) and ad.USEID = ua.USERID(+) and s.id = ad.sentaskid(+) and ad.id = se.sendtaskid(+) "
				+ " and  us.DEPTID='"
				+ userinfo.getDeptID()
				+ "'"
				+ " order by ad.WORKSTATE,s.SENDTIME ";
		//System.out.println( "SQL:" + sql );

		try {
			QueryUtil qu = new QueryUtil();
			lsend = qu.queryBeans(sql);
			return lsend;
		} catch (Exception ex) {
			logger.warn("��û���ɵ��б����:" + ex.getMessage());
			return null;
		}
	}

	/**<br>����:����ɵ�����ϸ��Ϣ
	 *  <br>����:�ɵ����
	 *  <br>����:
	 * */
	public SendTaskBean getOneSendTask(String id, UserInfo userinfo,
			String subid) {
		SendTaskBean bean = new SendTaskBean();
		ResultSet rst;
		String sql = "";
		//        if( userinfo.getDeptype().equals( "2" ) ){
		//            sql = "SELECT s.serialnumber,  s.ID, TO_CHAR (s.sendtime, 'yyyy-mm-dd HH24:MI') sendtime,"
		//                  + " s.sendtype,  TO_CHAR (s.processterm, 'yyyy-mm-dd HH24:MI') processterm, s.sendtopic,s.SENDTEXT,s.SENDACCE,ad.deptid acceptdeptid ,ad.useid acceptuserid,"
		//                  + " SUBSTR (ad.workstate, 2, 6) workstate, us.username,d.deptname acceptdeptname,"
		//                  + " ua.username usernameacce, NVL (se.RESULT, '��ǩ��') RESULT ,ad.id subid"
		//                  + " FROM sendtask s, userinfo us, userinfo ua, sendtaskendorse se,deptinfo d, sendtask_acceptdept ad "
		//                  + " WHERE s.senduserid = us.userid(+)"
		//                  + "      AND ad.useid = ua.userid(+)"
		//                  + " and s.id = ad.sendtaskid(+) "
		//                  + " and ad.id = se.sendtaskid(+) "
		//                 // + "      AND s.endorseid = se.ID(+)"
		//                  + "      and ad.DEPTID= d.DEPTID(+) "
		//                  + "      AND s.id = '" + id + "'"
		//                  + " and ad.id = '" + subid + "'";
		//        }
		//        else{
		//            sql = "SELECT  s.serialnumber, s.ID, TO_CHAR (s.sendtime, 'yyyy-mm-dd HH24:MI') sendtime,"
		//                  + " s.sendtype, TO_CHAR (s.processterm, 'yyyy-mm-dd HH24:MI') processterm, s.sendtopic,s.SENDTEXT,s.SENDACCE,ad.deptid acceptdeptid ,ad.useid acceptuserid,"
		//                  + " SUBSTR (ad.workstate, 2, 6) workstate, us.username,c.contractorname acceptdeptname,"
		//                  + " ua.username usernameacce, NVL (se.RESULT, '��ǩ��') RESULT, ad.id subid"
		//                  + " FROM sendtask s, userinfo us, userinfo ua, sendtaskendorse se,contractorinfo c, sendtask_acceptdept ad "
		//                  + " WHERE s.senduserid = us.userid(+)"
		//                  + "      AND ad.useid = ua.userid(+)"
		//                  + " and s.id = ad.sendtaskid(+) "
		//                  + " and ad.id = se.sendtaskid(+) "
		//                  //+ "      AND s.endorseid = se.ID(+)"
		//                  + "      and ad.DEPTID= c.contractorid(+) "
		//                  + "      AND s.id = '" + id + "'"
		//                  + " and ad.id = '" + subid + "'";
		//            
		//        }
		sql = "SELECT  s.serialnumber, s.ID, TO_CHAR (s.sendtime, 'yyyy-mm-dd HH24:MI') sendtime,"
				+ " s.sendtype, TO_CHAR (s.processterm, 'yyyy-mm-dd HH24:MI') processterm, s.sendtopic,s.SENDTEXT,s.SENDACCE,ad.deptid acceptdeptid ,ad.useid acceptuserid,"
				+ " SUBSTR (ad.workstate, 2, 6) workstate, us.username,"
				+ " NVL((select c.contractorname from contractorinfo c where c.contractorid = ad.deptid),"
				+ " 	  (select de.deptname from deptinfo de where de.deptid = ad.deptid))  acceptdeptname ,"
				+ " ua.username usernameacce, NVL (se.RESULT, '��ǩ��') RESULT, ad.id subid"
				+ " FROM sendtask s, userinfo us, userinfo ua, sendtaskendorse se, sendtask_acceptdept ad "
				+ " WHERE s.senduserid = us.userid(+)"
				+ "      AND ad.useid = ua.userid(+)"
				+ " and s.id = ad.sendtaskid(+) "
				+ " and ad.id = se.sendtaskid(+) "
				//+ "      AND s.endorseid = se.ID(+)"
				// + "      and ad.DEPTID= c.contractorid(+) "
				+ "      AND s.id = '" + id + "'";
		if (subid != null && !"null".equals(subid)) {
			sql = sql + " and ad.id = '" + subid + "'";
		}

		//      System.out.println( "SQL:" + sql );

		try {
			QueryUtil qu = new QueryUtil();
			rst = qu.executeQuery(sql);
			try {
				if (rst != null && rst.next()) {
					bean.setId(rst.getString("id"));
					bean.setSendtime(rst.getString("sendtime"));
					bean.setSendtype(rst.getString("sendtype"));
					bean.setProcessterm(rst.getString("processterm"));
					bean.setSendtopic(rst.getString("sendtopic"));
					bean.setSendtext(rst.getString("sendtext"));
					bean.setSendacce(rst.getString("sendacce"));
					bean.setWorkstate(rst.getString("workstate"));
					bean.setUsername(rst.getString("username"));
					bean.setUsernameacce(rst.getString("usernameacce"));
					bean.setResult(rst.getString("result"));
					bean.setAcceptdeptname(rst.getString("acceptdeptname"));
					bean.setAcceptdeptid(rst.getString("acceptdeptid"));
					bean.setAcceptuserid(rst.getString("acceptuserid"));
					bean.setSerialnumber(rst.getString("serialnumber"));
					bean.setSubtaskid(rst.getString("subid"));
					rst.close();
					return bean;
				}
				rst.close();
				return null;

			} catch (Exception ex) {
				logger.warn("����ɵ�����ϸ��Ϣ����1:" + ex.getMessage());
				rst.close();
				return null;
			}

		} catch (Exception ex) {
			logger.warn("����ɵ�����ϸ��Ϣ����:" + ex.getMessage());
			return null;
		}
	}

	/**
	 * ȡ���ӱ���ܵ�����
	 * @param sendtaskid
	 * @return
	 */
	public List getAcceptDept(String sendtaskid) {
		String sql = "select d.deptname, s.deptid,s.useid , u.username, SUBSTR(s.WORKSTATE,2,6) workstate from sendtask m "
				+ " left join sendtask_acceptdept s on m.id = s.sendtaskid "
				+ " left join userinfo u on s.useid = u.userid "
				+ " left join deptinfo d on s.deptid = d.deptid "
				+ " where m.id = '" + sendtaskid + "'";
		try {
			QueryUtil qu = new QueryUtil();
			List list = qu.queryBeans(sql);
			return list;
		} catch (Exception ex) {
			logger.warn("��û���ɵ��б����:" + ex.getMessage());
			return null;
		}

	}

	/**<br>����:����ɵ���ǩ����Ϣ
	 *  <br>����:�ɵ����
	 *  <br>����:
	 * */
	public SendTaskBean getEendorseOfSendTask(String id) {
		SendTaskBean bean = new SendTaskBean();
		ResultSet rst;
		String sql = "select e.ID,TO_CHAR(e.TIME,'yyyy-MM-dd HH24:MI') time,"
				+ " e.DEPTID,e.USERID,e.REMARK,e.RESULT,e.ACCE,e.SENDTASKID,us.username  "
				//                     + " from sendtaskendorse e,sendtask s "
				//                    + " where e.ID=s.ENDORSEID and s.ID='" + id + "'";
				+ " from sendtaskendorse e,sendtask_acceptdept ad, userinfo us "
				+ " where e.sendtaskid = ad.id and e.userid = us.userid(+) and ad.ID='"
				+ id + "'";

		try {
			QueryUtil qu = new QueryUtil();
			rst = qu.executeQuery(sql);
			try {
				if (rst != null && rst.next()) {
					bean.setId(rst.getString("id"));
					bean.setTime(rst.getString("time"));
					bean.setDeptid(rst.getString("deptid"));
					bean.setUserid(rst.getString("userid"));
					bean.setRemark(rst.getString("remark"));
					bean.setResult(rst.getString("result"));
					bean.setAcce(rst.getString("acce"));
					bean.setEndorseusername(rst.getString("username"));
					rst.close();
					return bean;
				}
				rst.close();
				return null;

			} catch (Exception ex) {
				logger.warn("����ɵ�����ϸ��Ϣ����1:" + ex.getMessage());
				rst.close();
				return null;
			}

		} catch (Exception ex) {
			logger.warn("����ɵ�����ϸ��Ϣ����:" + ex.getMessage());
			return null;
		}
	}

	/**
	 * <br>����:���������ɵ���Ϣ
	 * <br>����:request
	 * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List querySendList(UserInfo userinfo, SendTaskBean bean,
			HttpSession session) {
		List lsend = null;
		String sql = "select s.serialnumber, s.ID,s.SENDTYPE,s.SENDTOPIC,"
				+ "  case when (ad.workstate = '1��ǩ��' or ad.workstate = '3���ظ�') then round(to_number(s.processterm - sysdate)*24,1) "
				+ " 	when s.PROCESSTERM < r.replytime then  round(to_number(s.processterm - r.replytime )*24,1) "
				+ " else 0 end processterm ,"
				+ " SUBSTR(ad.WORKSTATE,2,6) workstate, ad.id subid , ua.USERNAME usernameacce, "
				+ "  NVL((select c.contractorname from contractorinfo c where c.contractorid = s.senddeptid),"
				+ " 	  (select de.deptname from deptinfo de where de.deptid = s.senddeptid))  senddeptname "
				+ " from sendtask s,userinfo ua, userinfo us, sendtask_acceptdept ad,sendtaskreply r  "//,sendtaskreply r
				+ " where s.id = ad.sendtaskid(+) and ad.id = r.sendtaskid(+)"
				+ " and  ad.useid = ua.userid(+) and  s.senduserid = us.userid(+) and s.senddeptid='"
				+ userinfo.getDeptID() + "'";
		try {
            if( bean.getSendusername() != null && !bean.getSendusername().equals( "" ) ){
                sql = sql + " and us.USERNAME like '%" + bean.getSendusername() + "%' ";
            }
//            if( bean.getAcceptusername() != null && !bean.getAcceptusername().equals( "" ) ){
//                sql = sql + "  and ua.USERNAME like '" + bean.getAcceptusername() + "%'  ";
//            }
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
				if (bean.getProcessterm().equals("����")) {
					sql += " AND s.PROCESSTERM < NVL (r.replytime,SYSDATE)";
				} else {
					sql += " AND s.PROCESSTERM >= NVL (r.replytime,SYSDATE)";
				}
			}
			if (bean.getWorkstate() != null && !bean.getWorkstate().equals("")) {
				sql = sql + "  and ad.WORKSTATE like '" + bean.getWorkstate()
						+ "%'  ";
			}

			if (bean.getBegintime() != null && !bean.getBegintime().equals("")) {
				sql = sql + " and s.SENDTIME >=TO_DATE('" + bean.getBegintime()
						+ " 00:00:00','YYYY-MM-DD HH24:MI:SS ')";
			}
			if (bean.getEndtime() != null && !bean.getEndtime().equals("")) {
				sql = sql + " and s.SENDTIME <= TO_DATE('" + bean.getEndtime()
						+ " 23:59:59','YYYY-MM-DD HH24:MI:SS')";
			}
			sql = sql + " order by s.SENDTIME desc , s.id";

			//System.out.println( "SQL:" + sql );
			session.setAttribute("sendTaskQueryCon", sql);
			QueryUtil query = new QueryUtil();
			lsend = query.queryBeans(sql);
			return lsend;
		} catch (Exception e) {
			logger.error("���������ɵ���Ϣ�쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * �޸ĳɹ��󷵻ز�ѯ���
	 * @param sql
	 * @return
	 */
	public List queryAfterMod(String sql) {
		QueryUtil query = null;
		try {
			query = new QueryUtil();
			return query.queryBeans(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**<br>����:�޸��ɵ�
	 *  <br>����:�ɵ�Bean
	 *  <br>����:��д�ɹ�����true ���򷵻� false
	 * */
	public boolean upSendTask(SendTaskBean bean) {

		Date nowDate = new Date();
		DateFormat df = DateFormat.getDateTimeInstance();
		String date = df.format(nowDate);

		String sendtext = bean.getSendtext().length() > 1024 ? bean
				.getSendtext().substring(0, 1024) : bean.getSendtext();
		String sql = "";
		UpdateUtil up = null;
		try {
			up = new UpdateUtil();
			up.setAutoCommitFalse();

			// ɾ���ӱ������ٲ���
			//String delSubSql = "delete from sendtask_acceptdept where sendtaskid = '" + bean.getId() + "'";
			// up.executeUpdate( delSubSql );

			String subSql;
			String subId;
			OracleIDImpl ora = new OracleIDImpl();

			if (bean.getFlag().equals("1")) {
				// ����
				sql = "update sendtask set senduserid='" + bean.getSenduserid()
						+ "',senddeptid='" + bean.getSenddeptid()
						+ "',sendtime=TO_DATE('" + date
						+ "','yyyy-mm-dd HH24:MI:SS'),sendtype='"
						+ bean.getSendtype() + "',processterm=TO_DATE('"
						+ bean.getProcessterm()
						+ "','yyyy-MM-dd HH24:MI:SS'),sendtopic='"
						+ bean.getSendtopic() + "',sendtext='" + sendtext
						+ "',sendacce='" + bean.getSendacce()
						+ "',acceptdeptid='" + bean.getAcceptdeptid()
						+ "',acceptuserid='" + bean.getAcceptuserid()
						+ "',endorseid=''" + " where id='" + bean.getId() + "'";
				up.executeUpdate(sql);
				// �ӱ�
				//    for (int i = 0; i < userId.length; i++) {
				//	subId = ora.getSeq( "sendtask_acceptdept", 15 );
				//		        	subSql = "insert into sendtask_acceptdept(id, sendtaskid, deptid, useid, workstate) values ("
				//		        			+ "'" + subId + "' , "
				//		        			+ "'" + bean.getId() + "' , "
				//		        			+ "'" + deptId[i] + "' , "
				//		        			+ "'" + userId[i] + "', '9�Ѵ浵')";
				//				}
			} else {
				// ����
				sql = "update sendtask set senduserid='" + bean.getSenduserid()
						+ "',senddeptid='" + bean.getSenddeptid()
						+ "',sendtime=TO_DATE('" + date
						+ "','yyyy-mm-dd HH24:MI:SS'),sendtype='"
						+ bean.getSendtype() + "',processterm=TO_DATE('"
						+ bean.getProcessterm()
						+ "','yyyy-MM-dd HH24:MI:SS'),sendtopic='"
						+ bean.getSendtopic() + "',sendtext='" + sendtext
						+ "',sendacce='" + bean.getSendacce()
						+ "',acceptdeptid='" + bean.getAcceptdeptid()
						+ "',acceptuserid='" + bean.getAcceptuserid()
						+ "', endorseid=''" + " where id='" + bean.getId()
						+ "'";
				up.executeUpdate(sql);
				// �ӱ�
				//	            for (int i = 0; i < userId.length; i++) {
				//		        	subId = ora.getSeq( "sendtask_acceptdept", 15 );
				//		        	subSql = "insert into sendtask_acceptdept(id, sendtaskid, deptid, useid, workstate) values ("
				//		        			+ "'" + subId + "' , "
				//		        			+ "'" + bean.getId() + "' , "
				//		        			+ "'" + deptId[i] + "' , "
				//		        			+ "'" + userId[i] + "' , '1��ǩ��')";
				//		        	up.executeUpdate( subSql );
				//				}

			}
			// �����ӱ�
			subSql = "update sendtask_acceptdept set deptid = '"
					+ bean.getAcceptdeptid() + "', useid = '"
					+ bean.getAcceptuserid() + "'  where id = '"
					+ bean.getSubtaskid() + "'";
			up.executeUpdate(subSql);

			up.commit();
			up.setAutoCommitTrue();
			return true;
		} catch (Exception ex) {
			logger.warn("�޸��ɵ�����:" + ex.getMessage());
			up.rollback();
			up.setAutoCommitTrue();
			return false;
		}
	}

	/**
	 * �޸Ĺ�������ˮ��
	 * @param sql
	 * @return
	 */
	public String getSerialnumber() {
		// ȡ�õ�ǰ����
		Calendar date = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String sysDateStr = df.format(date.getTime());
		QueryUtil query = null;
		String numStr = sysDateStr;
		try {
			String sqlStr = "select serialnumber from sendTask where serialnumber like '"
					+ sysDateStr + "%' " + " order by serialnumber desc";
			query = new QueryUtil();
			List tmpList = query.queryBeans(sqlStr);
			if (tmpList.size() == 0) {
				numStr += "0001";
			} else {
				String tmpStr = ((DynaBean) tmpList.get(0)).get("serialnumber")
						.toString();
				int tmpNum = Integer.parseInt(tmpStr.substring(8, 12)) + 1;

				// ǰ�油����
				for (int i = String.valueOf(tmpNum).length(); i < 4; i++) {
					numStr += "0";
				}
				numStr += tmpNum;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return numStr;
	}

	/**
	 * <br>����:������ѯͳ��
	 * <br>����:request
	 * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List allQueryTotalList(UserInfo userinfo, SendTaskBean bean,
			HttpSession session) {
		List totalList = null;
		String sql = "select s.serialnumber, s.ID,s.SENDTYPE,s.SENDTOPIC,"
				+ "TO_CHAR(s.PROCESSTERM,'yyyy-MM-dd HH24:MI') processterm,SUBSTR(ad.WORKSTATE,2,6) workstate,"
				+ "TO_CHAR(sr.validatetime,'yyyy-MM-dd HH24:MI') validatetime, us.username acceusername, "
				+ "TO_CHAR(sr.replytime,'yyyy-MM-dd HH24:MI') replytime,ad.id subid , "
				+ " NVL((select c.contractorname from contractorinfo c where c.contractorid = s.senddeptid), "
				+ " 	  (select de.deptname from deptinfo de where de.deptid = s.senddeptid))  senddeptname, "
				+ " case when (ad.workstate = '1��ǩ��' or ad.workstate = '3���ظ�')  and s.PROCESSTERM < sysdate "
				+ " then round(to_number(s.processterm - sysdate)*24)  "
				+ " when s.PROCESSTERM < sr.replytime then  round(to_number(s.processterm - sr.replytime)*24) "
				+ " else 0 end replyovertime, "
				+ " case when sr.validatetime > sr.replytime + 2 then round(to_number(sr.replytime+2 - sr.validatetime)*24,1)  else 0 end validovertime "
				+ " from sendtask s,sendtask_acceptdept ad , sendtaskreply sr , userinfo us, userinfo ua "
				+ " where s.id = ad.sendtaskid(+) "
				+ " and ad.id = sr.sendtaskid(+)  "
				+ " and ad.useid = us.USERID(+) "
				+ " and s.senduserid = ua.USERID(+) "

		;
		try {
			// �ɵ���
			if (bean.getSendusername() != null
					&& !"".equals(bean.getSendusername())) {
				sql = sql + " and ua.USERNAME like '%" + bean.getSendusername()
						+ "%' ";
			}
			//        	if(bean.getSenduserid() != null && !("".equals(bean.getSenduserid()))) {
			//        		sql = sql + " and s.senduserid = '" + bean.getSenduserid() + "' ";
			//        	}
			// ��������
			if (bean.getSendtype() != null && !bean.getSendtype().equals("")) {
				sql = sql + "  and s.SENDTYPE like '" + bean.getSendtype()
						+ "%'  ";
			}
			// ����
			if (bean.getSendtopic() != null && !bean.getSendtopic().equals("")) {
				sql = sql + "  and s.SENDTOPIC like '" + bean.getSendtopic()
						+ "%'  ";
			}
			// �ɵ�����
			if (bean.getSenddeptid() != null
					&& !("".equals(bean.getSenddeptid()))) {
				sql = sql + " and s.senddeptid = '" + bean.getSenddeptid()
						+ "' ";
			}
			// ������
			if (bean.getAcceptdeptid() != null
					&& !("".equals(bean.getAcceptdeptid()))) {
				sql = sql + " and ad.deptid = '" + bean.getAcceptdeptid()
						+ "' ";
			}
			// �ɵ���ʼʱ��
			if (bean.getBegintime() != null && !bean.getBegintime().equals("")) {
				sql = sql + " and s.SENDTIME >=TO_DATE('" + bean.getBegintime()
						+ " 00:00:00','YYYY-MM-DD HH24:MI:SS ')";
			}
			if (bean.getEndtime() != null && !bean.getEndtime().equals("")) {
				sql = sql + " and s.SENDTIME <= TO_DATE('" + bean.getEndtime()
						+ " 23:59:59','YYYY-MM-DD HH24:MI:SS')";
			}

			// ����״̬
			if (bean.getWorkstate() != null
					&& !("".equals(bean.getWorkstate()))) {
				if ("�ظ���ʱ".equals(bean.getWorkstate())) {
					sql = sql
							+ " and (((ad.workstate = '1��ǩ��' or ad.workstate = '3���ظ�')  and s.PROCESSTERM < sysdate) ";
					sql = sql + " or (s.PROCESSTERM < sr.replytime)) ";
				} else if ("�ظ�����ʱ".equals(bean.getWorkstate())) {
					sql = sql
							+ " and (((ad.workstate = '1��ǩ��' or ad.workstate = '3���ظ�')  and s.PROCESSTERM >= sysdate) ";
					sql = sql + " or (s.PROCESSTERM >= sr.replytime)) ";
				} else if ("��֤��ʱ".equals(bean.getWorkstate())) {
					sql = sql + " and sr.validatetime > sr.replytime + 2 ";
				} else if ("��֤����ʱ".equals(bean.getWorkstate())) {
					sql = sql + " and sr.validatetime <= sr.replytime + 2 ";
				}
			}
			sql = sql + " order by s.SENDTIME desc";

			session.setAttribute("alltotalsql", sql);
			QueryUtil query = new QueryUtil();
			totalList = query.queryBeans(sql);
			return totalList;
		} catch (Exception e) {
			logger.error("���������ɵ���Ϣ�쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * <br>����:���˹���������ѯͳ�� ����ǰ�ĸ��˹���ͳ�ƣ���û�ã�
	 * <br>����:request
	 * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List QueryPersonList(UserInfo userinfo, SendTaskBean bean,
			HttpSession session) {
		List totalList = null;
		String sql = "select s.serialnumber, s.ID,s.SENDTYPE,s.SENDTOPIC, us.username acceusername,"
				+ "TO_CHAR(s.PROCESSTERM,'yyyy-MM-dd HH24:MI') processterm,SUBSTR(ad.WORKSTATE,2,6) workstate,"
				+ "TO_CHAR(sr.replytime,'yyyy-MM-dd HH24:MI') replytime,ad.id subid , "
				//+ " round(to_number(sysdate - s.processterm)*24) overtimenum "
				+ " case when (ad.workstate = '1��ǩ��' or ad.workstate = '3���ظ�')  and s.PROCESSTERM < sysdate "
				+ " then round(to_number(s.processterm - sysdate)*24,1)  "
				+ " when s.PROCESSTERM < sr.replytime then  round(to_number(s.processterm - sr.replytime)*24) "
				+ " else 0 end overtimenum, "
				+ " NVL((select c.contractorname from contractorinfo c where c.contractorid = s.senddeptid), "
				+ " 	  (select de.deptname from deptinfo de where de.deptid = s.senddeptid))  senddeptname "
				+ " from sendtask s,sendtask_acceptdept ad , sendtaskreply sr , userinfo us "
				+ " where s.id = ad.sendtaskid(+) "
				+ " and ad.id = sr.sendtaskid(+)  "
				+ " and ad.useid = us.USERID(+) "
				+ " and (s.senduserid = '"
				+ userinfo.getUserID()
				+ "' or ad.useid = '"
				+ userinfo.getUserID() + "') ";

		try {
			if (bean.getSenduserid() != null
					&& !("".equals(bean.getSenduserid()))) {
				sql = sql + " and s.senduserid = '" + bean.getSenduserid()
						+ "' ";
			}
			// ��������
			if (bean.getSendtype() != null && !bean.getSendtype().equals("")) {
				sql = sql + "  and s.SENDTYPE like '" + bean.getSendtype()
						+ "%'  ";
			}
			// �ɵ���ʼʱ��
			if (bean.getBegintime() != null && !bean.getBegintime().equals("")) {
				sql = sql + " and s.SENDTIME >=TO_DATE('" + bean.getBegintime()
						+ " 00:00:00','YYYY-MM-DD HH24:MI:SS ')";
			}
			if (bean.getEndtime() != null && !bean.getEndtime().equals("")) {
				sql = sql + " and s.SENDTIME <= TO_DATE('" + bean.getEndtime()
						+ " 23:59:59','YYYY-MM-DD HH24:MI:SS')";
			}
			sql = sql + " order by s.SENDTIME desc";

			session.setAttribute("personsql", sql);
			QueryUtil query = new QueryUtil();
			totalList = query.queryBeans(sql);
			return totalList;
		} catch (Exception e) {
			logger.error("���������ɵ���Ϣ�쳣:" + e.getMessage());
			return null;
		}
	}

	public List getPersonTaskInfo(SendTaskBean bean, String queryFlg, String userid, 
			HttpSession session) {
		List totalList = null;
		// ��ʱ����Ļظ���sql���
		String sql = "select s.serialnumber, s.ID,s.SENDTYPE,s.SENDTOPIC, us.username acceusername,"
				+ "TO_CHAR(s.PROCESSTERM,'yyyy-MM-dd HH24:MI') processterm,SUBSTR(ad.WORKSTATE,2,6) workstate,"
				+ "TO_CHAR(sr.replytime,'yyyy-MM-dd HH24:MI') replytime,ad.id subid , TO_CHAR(sr.validatetime,'yyyy-MM-dd HH24:MI') validatetime,"
				+ " case when (ad.workstate = '1��ǩ��' or ad.workstate = '3���ظ�')  and s.PROCESSTERM < sysdate "
				+ " then round(to_number(s.processterm - sysdate)*24,1)  "
				+ " when s.PROCESSTERM < sr.replytime then  round(to_number(s.processterm - sr.replytime)*24,1) "
				+ " else 0 end overtimenum, "
				+ " NVL((select c.contractorname from contractorinfo c where c.contractorid = s.senddeptid), "
				+ " 	  (select de.deptname from deptinfo de where de.deptid = s.senddeptid))  senddeptname "
				+ " from sendtask s,sendtask_acceptdept ad ,  sendtaskreply sr , userinfo us " //sendtaskendorse en,
				+ " where s.id = ad.sendtaskid(+) "
				//+ " and ad.id = en.sendtaskid(+)  "
				+ " and ad.id = sr.sendtaskid(+)  "
				+ " and ad.useid = us.USERID(+) "	;
		// ��ʱ���������֤��ʱ��sql���
		String sql2 = "select s.serialnumber, s.ID,s.SENDTYPE,s.SENDTOPIC, us.username acceusername,"
			+ "TO_CHAR(s.PROCESSTERM,'yyyy-MM-dd HH24:MI') processterm,SUBSTR(ad.WORKSTATE,2,6) workstate,"
			+ "TO_CHAR(sr.replytime,'yyyy-MM-dd HH24:MI') replytime,ad.id subid , TO_CHAR(sr.validatetime,'yyyy-MM-dd HH24:MI') validatetime,"
			+ " case when ad.workstate = '6����֤' and sr.replytime + 2 < Sysdate "
			+ " then round(to_number(sr.replytime + 2- sysdate)*24,2)  "
			+ " when sr.replytime+2 < sr.validatetime then  round(to_number(sr.replytime+2 - sr.validatetime)*24,2) "
			+ " else 0 end overtimenum, "
			+ " NVL((select c.contractorname from contractorinfo c where c.contractorid = s.senddeptid), "
			+ " 	  (select de.deptname from deptinfo de where de.deptid = s.senddeptid))  senddeptname "
			+ " from sendtask s,sendtask_acceptdept ad ,  sendtaskreply sr , userinfo us "  // sendtaskendorse en,
			+ " where s.id = ad.sendtaskid(+) "
			//+ " and ad.id = en.sendtaskid(+)  "
			+ " and ad.id = sr.sendtaskid(+)  "
			+ " and ad.useid = us.USERID(+) "	;
		
		// ȡ�ò�ѯ����
		String begintime = bean.getBegintime();
		String endtime = bean.getEndtime();
		String taskType = bean.getSendtype();
		if("0".equals(queryFlg)) {
			// �ɵ�
			sql += " and s.senduserid = '" + userid + "'";
			// �ɵ���ʼʱ��
			if ( begintime != null && !"".equals(begintime)) {
				sql += " and s.SENDTIME >=TO_DATE('" + begintime
						+ " 00:00:00','YYYY-MM-DD HH24:MI:SS ')";
			}
			if (endtime != null && !"".equals(endtime)) {
				sql += " and s.SENDTIME <= TO_DATE('" + endtime
						+ " 23:59:59','YYYY-MM-DD HH24:MI:SS')";
			}
			if(taskType != null && !"".equals(taskType)) {
				sql += " and s.sendtype = '" + taskType + "'";				
			}
		} else if("1".equals(queryFlg)) {
			// ��ǩ��
			sql += " and ad.useid = '" + userid + "' and ad.workstate = '1��ǩ��' ";
			// �ɵ���ʼʱ��
			if ( begintime != null && !"".equals(begintime)) {
				sql += " and s.SENDTIME >=TO_DATE('" + begintime
						+ " 00:00:00','YYYY-MM-DD HH24:MI:SS ')";
			}
			if (endtime != null && !"".equals(endtime)) {
				sql += " and s.SENDTIME <= TO_DATE('" + endtime
						+ " 23:59:59','YYYY-MM-DD HH24:MI:SS')";
			}
			if(taskType != null && !"".equals(taskType)) {
				sql += " and s.sendtype = '" + taskType + "'";				
			}
		} else if("2".equals(queryFlg)) {
			// ���ظ�
			sql += " and ad.useid = '" + userid + "' and (ad.workstate = '3���ظ�' or ad.workstate='2������' ) ";
			// �ɵ���ʼʱ��
			if ( begintime != null && !"".equals(begintime)) {
				sql += " and s.SENDTIME >=TO_DATE('" + begintime
						+ " 00:00:00','YYYY-MM-DD HH24:MI:SS ')";
			}
			if (endtime != null && !"".equals(endtime)) {
				sql += " and s.SENDTIME <= TO_DATE('" + endtime
						+ " 23:59:59','YYYY-MM-DD HH24:MI:SS')";
			}
			if(taskType != null && !"".equals(taskType)) {
				sql += " and s.sendtype = '" + taskType + "'";				
			}
		} else if("3".equals(queryFlg)) {
			// ����֤
			sql2 += " and s.senduserid = '" + userid + "' and ad.workstate = '6����֤' ";
			// �ɵ���ʼʱ��
			if ( begintime != null && !"".equals(begintime)) {
				sql2 += " and s.SENDTIME >=TO_DATE('" + begintime
						+ " 00:00:00','YYYY-MM-DD HH24:MI:SS ')";
			}
			if (endtime != null && !"".equals(endtime)) {
				sql2 += " and s.SENDTIME <= TO_DATE('" + endtime
						+ " 23:59:59','YYYY-MM-DD HH24:MI:SS')";
			}
			if(taskType != null && !"".equals(taskType)) {
				sql2 += " and s.sendtype = '" + taskType + "'";				
			}
			sql = sql2;
		} else if("11".equals(queryFlg)) {
			sql = "select s.serialnumber, s.ID,s.SENDTYPE,s.SENDTOPIC, us.username acceusername,"
				+ "TO_CHAR(s.PROCESSTERM,'yyyy-MM-dd HH24:MI') processterm,SUBSTR(ad.WORKSTATE,2,6) workstate,"
				+ "TO_CHAR(sr.replytime,'yyyy-MM-dd HH24:MI') replytime,ad.id subid ,  TO_CHAR(sr.validatetime,'yyyy-MM-dd HH24:MI') validatetime,"
				+ " case when (ad.workstate = '1��ǩ��' or ad.workstate = '3���ظ�')  and s.PROCESSTERM < sysdate "
				+ " then round(to_number(s.processterm - sysdate)*24,1)  "
				+ " when s.PROCESSTERM < sr.replytime then  round(to_number(s.processterm - sr.replytime)*24,1) "
				+ " else 0 end overtimenum, "
				+ " NVL((select c.contractorname from contractorinfo c where c.contractorid = s.senddeptid), "
				+ " 	  (select de.deptname from deptinfo de where de.deptid = s.senddeptid))  senddeptname "
				+ " from sendtask s,sendtask_acceptdept ad , sendtaskendorse en, sendtaskreply sr , userinfo us "
				+ " where s.id = ad.sendtaskid(+) "
				+ " and ad.id = en.sendtaskid(+)  "
				+ " and ad.id = sr.sendtaskid(+)  "
				+ " and ad.useid = us.USERID(+) and en.result != '��ǩ' "	;
			// ��ǩ��
			sql += " and en.userid = '" + userid + "' ";
			// ��ʼʱ��
			if ( begintime != null && !"".equals(begintime)) {
				sql += " and en.time >=TO_DATE('" + begintime
						+ " 00:00:00','YYYY-MM-DD HH24:MI:SS ')";
			}
			if (endtime != null && !"".equals(endtime)) {
				sql += " and en.time <= TO_DATE('" + endtime
						+ " 23:59:59','YYYY-MM-DD HH24:MI:SS')";
			}
			if(taskType != null && !"".equals(taskType)) {
				sql += " and s.sendtype = '" + taskType + "'";				
			}
		} else if("12".equals(queryFlg)) {
			// �ѻظ�
			sql += " and sr.replyuserid = '" + userid + "' ";
			// ��ʼʱ��
			if ( begintime != null && !"".equals(begintime)) {
				sql += " and sr.replytime >=TO_DATE('" + begintime
						+ " 00:00:00','YYYY-MM-DD HH24:MI:SS ')";
			}
			if (endtime != null && !"".equals(endtime)) {
				sql += " and sr.replytime <= TO_DATE('" + endtime
						+ " 23:59:59','YYYY-MM-DD HH24:MI:SS')";
			}
			if(taskType != null && !"".equals(taskType)) {
				sql += " and s.sendtype = '" + taskType + "'";				
			}
		} else if("13".equals(queryFlg)) {
			// ����֤
			sql2 += " and sr.validateuserid = '" + userid + "'";
			// ��֤��ʼʱ��
			if ( begintime != null && !"".equals(begintime)) {
				sql2 += " and sr.validatetime >=TO_DATE('" + begintime
						+ " 00:00:00','YYYY-MM-DD HH24:MI:SS ')";
			}
			if (endtime != null && !"".equals(endtime)) {
				sql2 += " and sr.validatetime <= TO_DATE('" + endtime
						+ " 23:59:59','YYYY-MM-DD HH24:MI:SS')";
			}
			if(taskType != null && !"".equals(taskType)) {
				sql2 += " and s.sendtype = '" + taskType + "'";				
			}
			sql = sql2;
		} else if("21".equals(queryFlg)) {
			// �ظ���ʱ
			if(taskType != null && !"".equals(taskType)) {
				sql += " and s.sendtype = '" + taskType + "'";				
			}
			sql += " and (( sr.replyuserid = '" + userid + "' and sr.replytime > s.processterm ";
			// ��ʼʱ��
			if ( begintime != null && !"".equals(begintime)) {
				sql += " and sr.replytime >=TO_DATE('" + begintime
						+ " 00:00:00','YYYY-MM-DD HH24:MI:SS ')";
			}
			if (endtime != null && !"".equals(endtime)) {
				sql += " and sr.replytime <= TO_DATE('" + endtime
						+ " 23:59:59','YYYY-MM-DD HH24:MI:SS')";
			}
			sql += ") or (";
			sql += "ad.useid = '" + userid + "'  and (ad.workstate = '1��ǩ��'  or ad.workstate = '3���ظ�') and sysdate > s.processterm ";
			// �ɵ���ʼʱ��
			if ( begintime != null && !"".equals(begintime)) {
				sql2 += " and s.SENDTIME >=TO_DATE('" + begintime
						+ " 00:00:00','YYYY-MM-DD HH24:MI:SS ')";
			}
			if (endtime != null && !"".equals(endtime)) {
				sql2 += " and s.SENDTIME <= TO_DATE('" + endtime
						+ " 23:59:59','YYYY-MM-DD HH24:MI:SS')";
			}
			sql += "))";		
		} else if("22".equals(queryFlg)) {
			// ��֤��ʱ
			if(taskType != null && !"".equals(taskType)) {
				sql2 += " and s.sendtype = '" + taskType + "'";				
			}
			// ͳ����Ա��֤���ĳ�ʱ
			sql2 += " and ((sr.validateuserid = '" + userid + "' and sr.replytime+2 < sr.validatetime and ad.workstate != '2������' ";
			// ��֤��ʼʱ��
			if ( begintime != null && !"".equals(begintime)) {
				sql2 += " and sr.validatetime >=TO_DATE('" + begintime
						+ " 00:00:00','YYYY-MM-DD HH24:MI:SS ')";
			}
			if (endtime != null && !"".equals(endtime)) {
				sql2 += " and sr.validatetime <= TO_DATE('" + endtime
						+ " 23:59:59','YYYY-MM-DD HH24:MI:SS')";
			}
			
			sql2 += ") or (";
			// ����ͳ����Ա��֤��û��ʱ��
			sql2 += " s.senduserid = '" + userid + "' and ad.workstate = '6����֤' and sr.replytime + 2 < Sysdate ";
			// ��ʼʱ��
			if ( begintime != null && !"".equals(begintime)) {
				sql2 += " and s.SENDTIME >=TO_DATE('" + begintime
						+ " 00:00:00','YYYY-MM-DD HH24:MI:SS ')";
			}
			if (endtime != null && !"".equals(endtime)) {
				sql2 += " and s.SENDTIME <= TO_DATE('" + endtime
						+ " 23:59:59','YYYY-MM-DD HH24:MI:SS')";
			}
			sql2 += ")) ";	
			sql = sql2;
		}

		try {
			sql += " order by s.SENDTIME desc";
			session.setAttribute("personsql", sql);
			QueryUtil query = new QueryUtil();
			totalList = query.queryBeans(sql);
			return totalList;
		} catch (Exception e) {
			logger.error("���������ɵ���Ϣ�쳣:" + e.getMessage());
			return null;
		}
	}
	
	/**
	 * ���˹�����Ϣͳ��
	 * @param userinfo
	 * @param bean
	 * @return
	 */
	public List personTotal(UserInfo userinfo, SendTaskBean bean,
			HttpSession session) {
		List totalList = null;
		// �ɳ�ȥ������ͳ��
		String sql1 = "select count(*) sendnum from sendtask s left join sendtask_acceptdept ad on s.id = ad.sendtaskid ";
		// �ɳ�ȥ����ʱ�ĸ���ͳ��
		String sql2 = "select count(*) sendovernum from sendtask s left join sendtask_acceptdept ad on s.id = ad.sendtaskid "
				+ " left join sendtaskreply sr on ad.id = sr.sendtaskid "
				+ "where (((ad.workstate = '1��ǩ��' or ad.workstate = '3���ظ�')  and s.PROCESSTERM < sysdate) or (s.PROCESSTERM < sr.replytime)) ";
		// ��¼��Ա�ظ�������ͳ��
		String sql3 = "select count(*) replynum from sendtask s left join sendtask_acceptdept ad on s.id = ad.sendtaskid ";
		// ��¼��Ա�ظ�����ʱ�ĸ���ͳ��
		String sql4 = "select count(*) replyovernum from sendtask s left join sendtask_acceptdept ad on s.id = ad.sendtaskid "
				+ " left join sendtaskreply sr on ad.id = sr.sendtaskid "
				+ "where (((ad.workstate = '1��ǩ��' or ad.workstate = '3���ظ�')  and s.PROCESSTERM < sysdate) or (s.PROCESSTERM < sr.replytime)) ";

		// ҳ���ϵ��������
		String con = "";
		if (bean.getSendtype() != null && !"".equals(bean.getSendtype())) {
			con = " and s.SENDTYPE = '" + bean.getSendtype() + "' ";
		}
		// �ɵ���ʼʱ��
		if (bean.getBegintime() != null && !bean.getBegintime().equals("")) {
			con = con + " and s.SENDTIME >=TO_DATE('" + bean.getBegintime()
					+ " 00:00:00','YYYY-MM-DD HH24:MI:SS ')";
		}
		if (bean.getEndtime() != null && !bean.getEndtime().equals("")) {
			con = con + " and s.SENDTIME <= TO_DATE('" + bean.getEndtime()
					+ " 23:59:59','YYYY-MM-DD HH24:MI:SS')";

		}

		// ���sql���
		String sql = "select * from (" + sql1 + " where s.senduserid = '"
				+ userinfo.getUserID() + "' " + con + ")," + "(" + sql2
				+ " and s.senduserid = '" + userinfo.getUserID() + "' " + con
				+ ")," + "(" + sql3 + " where ad.useid = '"
				+ userinfo.getUserID() + "' " + con + ")," + "(" + sql4
				+ " and ad.useid = '" + userinfo.getUserID() + "' " + con + ")";

		session.setAttribute("persontotalsql", sql);

		try {
			QueryUtil query = new QueryUtil();
			totalList = query.queryBeans(sql);
			return totalList;
		} catch (Exception e) {
			logger.error("��������ͳ������������Ϣ�쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * ���˹���������Ϣͳ��
	 * @param userinfo
	 * @param bean
	 * @return
	 */
	public List personTotalNum(SendTaskBean bean,
			HttpSession session) {
		List totalList = null;		

		// ҳ���ϵ��������
		String con = "";
		// ����
		String type = bean.getSendtype();
		if (type != null && !"".equals(type)) {
			con = " s.SENDTYPE = '" + type + "' ";
		}
		// �ɵ���ʼʱ��
		String startTime = bean.getBegintime();
		if ( startTime != null && !"".equals(startTime)) {
			if("".equals(con)) {
				con = " s.SENDTIME >=TO_DATE('" + startTime
				+ " 00:00:00','YYYY-MM-DD HH24:MI:SS ')";
			} else {
				con = con + " and s.SENDTIME >=TO_DATE('" + startTime
				+ " 00:00:00','YYYY-MM-DD HH24:MI:SS ')";
			}
		}
		// �ɵ�����ʱ��
		String endTime = bean.getEndtime();
		if (endTime != null && !"".equals(endTime)) {
			if("".equals(con)) {
				con = " s.SENDTIME <= TO_DATE('" + endTime
				+ " 23:59:59','YYYY-MM-DD HH24:MI:SS')";
			} else {
				con = con + " and s.SENDTIME <= TO_DATE('" + endTime
				+ " 23:59:59','YYYY-MM-DD HH24:MI:SS')";
			}
		}

		// �ɵ�
		String sqlSend = "select s.senduserid userid, count(s.senduserid) sendnum from sendtask s left join sendtask_acceptdept ad on s.id = ad.sendtaskid ";
		if(!"".equals(con)) {
			sqlSend = sqlSend + " where " + con;
		}
		sqlSend += " group by  s.senduserid ";

		// ��ǩ��
		String sqlEn1 = "select ad.useid userid, count(ad.useid) endorsenum from sendtask s left join sendtask_acceptdept ad on s.id = ad.sendtaskid "
				+ " where ad.workstate = '1��ǩ��' ";
		if(!"".equals(con)) {
			sqlEn1 = sqlEn1 + " and " + con;
		}
		sqlEn1 += " group by ad.useid ";

		// ���ظ�
		String sqlRe1 = "select ad.useid userid, count(ad.useid) replynum from sendtask s left join sendtask_acceptdept ad on s.id = ad.sendtaskid"
				+ " where (ad.workstate = '3���ظ�' or ad.workstate='2������' ) ";
		if(!"".equals(con)) {
			sqlRe1 = sqlRe1 + " and " + con;
		}
		sqlRe1 += " group by ad.useid ";

		// ����֤
		String sqlVa1 = "select s.senduserid userid, count(s.senduserid) valitnum from sendtask s left join sendtask_acceptdept ad on s.id = ad.sendtaskid "
				+ " where ad.workstate = '6����֤' ";
		if(!"".equals(con)) {
			sqlVa1 = sqlVa1 + " and " + con;
		}
		sqlVa1 += " group by s.senduserid ";

		// ��ǩ��
		String sqlEn2 = "select en.userid userid, count(en.userid) ennum from sendtask s left join sendtask_acceptdept ad on s.id = ad.sendtaskid "
				+ " left join sendtaskendorse en on ad.id = en.sendtaskid  where en.userid is not null and en.result != '��ǩ'";
		// ����
		if (type != null && !"".equals(type)) {
			sqlEn2 += " and s.SENDTYPE = '" + type + "' ";
		}
		// ǩ�տ�ʼʱ��
		if ( startTime != null && !"".equals(startTime)) {			
			sqlEn2 += " and en.time >= TO_DATE('" + startTime + " 00:00:00','YYYY-MM-DD HH24:MI:SS ')";			
		}
		// ǩ�ս���ʱ��
		if (endTime != null && !"".equals(endTime)) {			
			sqlEn2 += " and en.time <= TO_DATE('" + endTime + " 23:59:59','YYYY-MM-DD HH24:MI:SS')";			
		}
		sqlEn2 += " group by en.userid ";

		// �ѻظ�
		String sqlRe2 = "select sr.replyuserid userid , count(sr.replyuserid) renum from sendtask s left join sendtask_acceptdept ad on s.id = ad.sendtaskid"
				+ " left join sendtaskreply sr on ad.id = sr.sendtaskid  where sr.replyuserid is not null ";	
		// ����
		if (type != null && !"".equals(type)) {
			sqlRe2 += " and s.SENDTYPE = '" + type + "' ";
		}
		// �ظ���ʼʱ��
		if ( startTime != null && !"".equals(startTime)) {			
			sqlRe2 += " and sr.replytime >=TO_DATE('" + startTime + " 00:00:00','YYYY-MM-DD HH24:MI:SS ')";			
		}
		// �ظ�����ʱ��
		if (endTime != null && !"".equals(endTime)) {			
			sqlRe2 += " and sr.replytime <= TO_DATE('" + endTime + " 23:59:59','YYYY-MM-DD HH24:MI:SS')";			
		}
		sqlRe2 += " group by sr.replyuserid ";

		// ����֤
		String sqlVa2 = "select sr.validateuserid userid, count(sr.validateuserid) vanum from sendtask s left join sendtask_acceptdept ad on s.id = ad.sendtaskid "
				+ " left join sendtaskreply sr on ad.id = sr.sendtaskid  where sr.validateuserid is not null ";
		// ����
		if (type != null && !"".equals(type)) {
			sqlVa2 += " and s.SENDTYPE = '" + type + "' ";
		}
		// ��֤��ʼʱ��
		if ( startTime != null && !"".equals(startTime)) {			
			sqlVa2 += " and sr.validatetime >=TO_DATE('" + startTime + " 00:00:00','YYYY-MM-DD HH24:MI:SS ')";			
		}
		// ��֤����ʱ��
		if (endTime != null && !"".equals(endTime)) {			
			sqlVa2 += " and sr.validatetime <= TO_DATE('" + endTime + " 23:59:59','YYYY-MM-DD HH24:MI:SS')";			
		}
		sqlVa2 += " group by sr.validateuserid ";

		// �ظ���ʱ
		String sqlOverRe = "select userid , sum(overtimereply) overtimereply from ( "
				+ " select userid , overtimereply from ( "
				+ " select ad.useid userid , count(ad.useid) overtimereply from sendtask s left join sendtask_acceptdept ad on s.id = ad.sendtaskid "
				+ " where (ad.workstate = '1��ǩ��'  or ad.workstate = '3���ظ�') and sysdate > s.processterm  ";
		if(!"".equals(con)) {
			sqlOverRe += " and " + con;
		}
		
		sqlOverRe += " group by ad.useid ) union ( "
				+ " select sr.replyuserid userid , count(sr.replyuserid) overtimereply from sendtask s left join sendtask_acceptdept ad on s.id = ad.sendtaskid "
				+ " left join sendtaskreply sr on ad.id = sr.sendtaskid where sr.replytime > s.processterm and sr.replytime is not null  ";
		// ����
		if (type != null && !"".equals(type)) {
			sqlOverRe += " and s.SENDTYPE = '" + type + "' ";
		}
		// �ظ���ʼʱ��
		if ( startTime != null && !"".equals(startTime)) {			
			sqlOverRe += " and sr.replytime >=TO_DATE('" + startTime + " 00:00:00','YYYY-MM-DD HH24:MI:SS ')";			
		}
		// �ظ�����ʱ��
		if (endTime != null && !"".equals(endTime)) {			
			sqlOverRe += " and sr.replytime <= TO_DATE('" + endTime + " 23:59:59','YYYY-MM-DD HH24:MI:SS')";			
		}
		sqlOverRe += " group by sr.replyuserid ) ) group by userid";

		// ��֤��ʱ
		String sqlOverVa = "select userid , sum(overtimevalid)  overtimevalid from ("
				+ " select userid, overtimevalid from ("
				+ " select s.senduserid userid , count(s.senduserid) overtimevalid from sendtask s left join sendtask_acceptdept ad on s.id = ad.sendtaskid "
				+ " left join sendtaskreply sr on ad.id = sr.sendtaskid where ad.workstate = '6����֤' and sr.replytime + 2 < Sysdate  ";
		if(!"".equals(con)) {
			sqlOverVa += " and " + con;
		}
		sqlOverVa += " group by s.senduserid ) union ( "
				+ " select sr.validateuserid , count(sr.validateuserid) overtimevalid from sendtask s left join sendtask_acceptdept ad on s.id = ad.sendtaskid "
				+ " left join sendtaskreply sr on ad.id = sr.sendtaskid "
				+ " where sr.replytime+2 < sr.validatetime  and ad.workstate != '2������'";
		// ����
		if (type != null && !"".equals(type)) {
			sqlOverVa += " and s.SENDTYPE = '" + type + "' ";
		}
		// ��֤��ʼʱ��
		if ( startTime != null && !"".equals(startTime)) {			
			sqlOverVa += " and sr.validatetime >=TO_DATE('" + startTime + " 00:00:00','YYYY-MM-DD HH24:MI:SS ')";			
		}
		// ��֤����ʱ��
		if (endTime != null && !"".equals(endTime)) {			
			sqlOverVa += " and sr.validatetime <= TO_DATE('" + endTime + " 23:59:59','YYYY-MM-DD HH24:MI:SS')";			
		}
		sqlOverVa += " group by sr.validateuserid ) ) group by userid ";
		
		String sql = "select u.userid, u.username , NVL(en1.endorsenum,0) endorsenum, NVL(re1.replynum,0) replynum,  NVL(va1.valitnum, 0) valitnum, "
			   + " NVL(send.sendnum,0) sendnum, NVL(en2.ennum,0) ennum, NVL(re2.renum,0) renum, NVL(va2.vanum,0) vanum, NVL(overre.overtimereply,0) overtimereply, "
			   + " NVL(overva.overtimevalid,0) overtimevalid from userinfo u left join ";
		// �ɵ�
		sql += "(" + sqlSend + ") send on u.userid = send.userid" ;
		// ��ǩ��
		sql += " left join (" + sqlEn1 + ") en1 on u.userid = en1.userid ";
		// ���ظ�
		sql += " left join (" + sqlRe1 + ") re1 on u.userid = re1.userid ";
		// ����֤
		sql += " left join (" + sqlVa1 + ") va1 on u.userid = va1.userid ";
		// ��ǩ��
		sql += " left join (" + sqlEn2 + ") en2 on u.userid = en2.userid ";
		// �ѻظ�
		sql += " left join (" + sqlRe2 + ") re2 on u.userid = re2.userid ";
		// ����֤
		sql += " left join (" + sqlVa2 + ") va2 on u.userid = va2.userid ";
		// �ظ���ʱ
		sql += " left join (" + sqlOverRe + ") overre on u.userid = overre.userid ";
		// ��֤��ʱ
		sql += " left join (" + sqlOverVa + ") overva on u.userid = overva.userid ";
		// ��������
		sql += " where u.state is null ";
		// ����
		String deptid = bean.getSenddeptid();
		if(deptid != null && !"".equals(deptid)) {
			sql += " and u.deptid = '" + deptid + "' ";
		}
		// ������
		String userid = bean.getSenduserid();
		if(userid != null && !"".equals(userid)) {
			sql += " and u.userid = '" + userid + "' ";
		}
		sql += " order by u.userid ";
		session.setAttribute("personnumtotalsql", sql );
		try {
			QueryUtil query = new QueryUtil();
			totalList = query.queryBeans(sql);			
		} catch (Exception e) {
			logger.error("������������ͳ������������Ϣ�쳣:" + e.getMessage());			
		}
		
		return totalList;
	}

	/**
	 * ���Ź�����Ϣͳ��
	 * @param userinfo
	 * @param bean
	 * @return
	 */
	public List deptTotal(UserInfo userinfo, SendTaskBean bean, HttpSession session) {

		// �������
		String conSql = null;

		// ͳ�����ݿ��в����ɵ�����
		StringBuffer sendSql = new StringBuffer();
		sendSql
				.append("select senddeptid, count(senddeptid) sendnum from sendtask ");
		if (bean.getBegintime() != null && !"".equals(bean.getBegintime())) {
			conSql = "Where sendtime >= to_date('" + bean.getBegintime()
					+ "','yyyy-MM-dd') ";
		}
		if (bean.getEndtime() != null && !"".equals(bean.getEndtime())) {
			if (conSql != null) {
				conSql += " and sendtime <= to_date('" + bean.getEndtime()
						+ "','yyyy-MM-dd')";
			} else {
				conSql = " where sendtime <= to_date('" + bean.getEndtime()
						+ "','yyyy-MM-dd')";
			}

		}
		if (bean.getSendtype() != null && !"".equals(bean.getSendtype())) {
			if (conSql != null) {
				conSql += " and sendtype like '" + bean.getSendtype() + "%'";
			} else {
				conSql = " where sendtype like '" + bean.getSendtype() + "%'";
			}
		}
		if (conSql != null) {
			sendSql.append(conSql);
		}

		sendSql.append("group by senddeptid ");

		// ͳ�����ݿ��лظ�����������
		StringBuffer replySql = new StringBuffer();
		replySql
				.append("select sum(r.replynum) replynum , sum(nooverr.replynum) nooverreplynum, u.deptid from userinfo u ,");
		// �ظ�����
		replySql
				.append(" (select replyuserid, count(*) replynum from sendtask s ");
		replySql
				.append("  join sendtask_acceptdept ad on s.id = ad.sendtaskid ");
		replySql.append("  join sendtaskreply sr on ad.id = sr.sendtaskid ");
		conSql = null;
		if (bean.getBegintime() != null && !"".equals(bean.getBegintime())) {
			conSql = "Where sr.replytime >= to_date('" + bean.getBegintime()
					+ "','yyyy-MM-dd') ";
		}
		if (bean.getEndtime() != null && !"".equals(bean.getEndtime())) {
			if (conSql != null) {
				conSql += " and sr.replytime <= to_date('" + bean.getEndtime()
						+ "','yyyy-MM-dd')";
			} else {
				conSql = " where sr.replytime <= to_date('" + bean.getEndtime()
						+ "','yyyy-MM-dd')";
			}

		}
		if (bean.getSendtype() != null && !"".equals(bean.getSendtype())) {
			if (conSql != null) {
				conSql += " and s.sendtype like '" + bean.getSendtype() + "%'";
			} else {
				conSql = "  where s.sendtype like '" + bean.getSendtype()
						+ "%'";
			}
		}
		if (conSql != null) {
			replySql.append(conSql);
		}
		replySql.append(" group by sr.replyuserid) r, ");
		// ����ʱ�ظ��Ĳ���
		replySql
				.append(" (select replyuserid, count(*) replynum from sendtask s ");
		replySql
				.append("  join sendtask_acceptdept ad on s.id = ad.sendtaskid ");
		replySql.append("  join sendtaskreply sr on ad.id = sr.sendtaskid ");
		replySql.append(" where s.processterm >= sr.replytime");
		conSql = "";
		if (bean.getBegintime() != null && !"".equals(bean.getBegintime())) {
			conSql = " and sr.replytime >= to_date('" + bean.getBegintime()
					+ "','yyyy-MM-dd') ";
		}
		if (bean.getEndtime() != null && !"".equals(bean.getEndtime())) {
			conSql += " and sr.replytime <= to_date('" + bean.getEndtime()
					+ "','yyyy-MM-dd')";
		}
		if (bean.getSendtype() != null && !"".equals(bean.getSendtype())) {
			conSql += " and s.sendtype like '" + bean.getSendtype() + "%'";
		}
		replySql.append(conSql);

		replySql.append(" group by sr.replyuserid ) nooverr ");
		replySql
				.append(" where u.userid = r.replyuserid and u.userid = nooverr.replyuserid group by u.deptid ");

		// ͳ�����ݿ�����֤����������
		StringBuffer validSql = new StringBuffer();
		validSql
				.append("select sum(r.validatenum) validnum , sum(nooverr.validatenum) noovervalidnum, u.deptid from userinfo u ,");
		// ��֤���ĵ���
		validSql
				.append(" (select validateuserid, count(*) validatenum from sendtask s ");
		validSql
				.append("  join sendtask_acceptdept ad on s.id = ad.sendtaskid ");
		validSql.append("  join sendtaskreply sr on ad.id = sr.sendtaskid ");
		conSql = null;
		if (bean.getBegintime() != null && !"".equals(bean.getBegintime())) {
			conSql = "Where sr.validatetime >= to_date('" + bean.getBegintime()
					+ "','yyyy-MM-dd') ";
		}
		if (bean.getEndtime() != null && !"".equals(bean.getEndtime())) {
			if (conSql != null) {
				conSql += " and sr.validatetime <= to_date('"
						+ bean.getEndtime() + "','yyyy-MM-dd')";
			} else {
				conSql = " where sr.validatetime <= to_date('"
						+ bean.getEndtime() + "','yyyy-MM-dd')";
			}

		}
		if (bean.getSendtype() != null && !"".equals(bean.getSendtype())) {
			if (conSql != null) {
				conSql += " and s.sendtype like '" + bean.getSendtype() + "%'";
			} else {
				conSql = " where s.sendtype like '" + bean.getSendtype() + "%'";
			}
		}

		if (conSql != null) {
			validSql.append(conSql);
		}
		validSql.append(" group by sr.validateuserid) r, ");
		// ����ʱ��֤�Ĳ���
		validSql
				.append(" (select validateuserid, count(*) validatenum from sendtask s ");
		validSql
				.append("  join sendtask_acceptdept ad on s.id = ad.sendtaskid ");
		validSql.append("  join sendtaskreply sr on ad.id = sr.sendtaskid ");
		validSql.append(" where sr.replytime + 2 >= sr.validatetime ");
		conSql = "";
		if (bean.getBegintime() != null && !"".equals(bean.getBegintime())) {
			conSql += " and sr.validatetime >= to_date('" + bean.getBegintime()
					+ "','yyyy-MM-dd') ";
		}
		if (bean.getEndtime() != null && !"".equals(bean.getEndtime())) {
			conSql += " and sr.validatetime <= to_date('" + bean.getEndtime()
					+ "','yyyy-MM-dd')";
		}
		if (bean.getSendtype() != null && !"".equals(bean.getSendtype())) {
			conSql += " and s.sendtype like '" + bean.getSendtype() + "%'";
		}

		validSql.append(conSql);
		validSql.append(" group by sr.validateuserid) nooverr ");
		validSql
				.append(" where u.userid = r.validateuserid and u.userid = nooverr.validateuserid group by u.deptid  ");

		// ͳ�����ݿ�����ظ�������
		StringBuffer replysumSql = new StringBuffer();
		replysumSql
				.append("select ad.deptid, count(ad.deptid) replysum from sendtask s ");
		replysumSql
				.append("  left join sendtask_acceptdept ad on s.id = ad.sendtaskid ");
		replysumSql
				.append("  left join sendtaskreply sr on ad.id = sr.sendtaskid ");
		conSql = null;
		if (bean.getBegintime() != null && !"".equals(bean.getBegintime())) {
			conSql = "Where sr.replytime >= to_date('" + bean.getBegintime()
					+ "','yyyy-MM-dd') ";
		}
		if (bean.getEndtime() != null && !"".equals(bean.getEndtime())) {
			if (conSql != null) {
				conSql += " and sr.replytime <= to_date('" + bean.getEndtime()
						+ "','yyyy-MM-dd')";
			} else {
				conSql = " where sr.replytime <= to_date('" + bean.getEndtime()
						+ "','yyyy-MM-dd')";
			}

		}
		if (bean.getSendtype() != null && !"".equals(bean.getSendtype())) {
			if (conSql != null) {
				conSql += " and s.sendtype like '" + bean.getSendtype() + "%'";
			} else {
				conSql = " where s.sendtype like '" + bean.getSendtype() + "%'";
			}
		}
		if (conSql != null) {
			replysumSql.append(conSql);
		}
		replysumSql.append(" group by ad.deptid ");

		// ͳ�����ݿ�������֤��������
		StringBuffer validsumSql = new StringBuffer();
		validsumSql
				.append("select s.senddeptid, count(s.senddeptid) validsum from sendtask s ");
		validsumSql
				.append(" left join sendtask_acceptdept ad on s.id = ad.sendtaskid  ");
		validsumSql
				.append("  left join sendtaskreply sr on ad.id = sr.sendtaskid ");
		validsumSql
				.append(" where (ad.workstate = '6����֤' or ad.workstate = '9�Ѵ浵' or ad.workstate='2������') ");
		conSql = null;
		if (bean.getBegintime() != null && !"".equals(bean.getBegintime())) {
			conSql = "and sr.validatetime >= to_date('" + bean.getBegintime()
					+ "','yyyy-MM-dd') ";
		}
		if (bean.getEndtime() != null && !"".equals(bean.getEndtime())) {
			if (conSql != null) {
				conSql += " and sr.validatetime <= to_date('"
						+ bean.getEndtime() + "','yyyy-MM-dd')";
			} else {
				conSql = " and sr.validatetime <= to_date('"
						+ bean.getEndtime() + "','yyyy-MM-dd')";
			}
		}
		if (bean.getSendtype() != null && !"".equals(bean.getSendtype())) {
			if (conSql != null) {
				conSql += " and s.sendtype like '" + bean.getSendtype() + "%'";
			} else {
				conSql = " and s.sendtype like '" + bean.getSendtype() + "%'";
			}

		}

		if (conSql != null) {
			validsumSql.append(conSql);
		}
		validsumSql.append(" group by s.senddeptid");

		// ��ѯͳ���ƶ����ű��sql
		StringBuffer deptSql = new StringBuffer();
		deptSql
				.append("select d.deptid, d.deptname, NVL(send.sendnum,0) sendnum, NVL(reply.replynum,0) replynum, NVL(valid.validnum,0) validnum,");
		deptSql
				.append("NVL(reply.nooverreplynum,0) nooverreplynum, NVL(valid.noovervalidnum,0) noovervalidnum,");
		deptSql
				.append("case when (replysum != null or replysum != 0) and nooverreplynum != 0 then nooverreplynum/replysum else 0 end replypercent, ");
		deptSql
				.append("case when (validsum != null or validsum != 0) and noovervalidnum != 0 then noovervalidnum/validsum else 0 end validpercent, NVL(replysum.replysum,0) replysum , NVL(validsum.validsum,0) validsum ");
		deptSql.append("  from deptinfo d , ");
		deptSql.append("(");
		deptSql.append(sendSql);
		deptSql.append(") send, ");
		deptSql.append("(");
		deptSql.append(replySql);
		deptSql.append(") reply, ");
		deptSql.append("(");
		deptSql.append(validSql);
		deptSql.append(") valid, ");
		deptSql.append("(");
		deptSql.append(replysumSql);
		deptSql.append(") replysum, ");
		deptSql.append("(");
		deptSql.append(validsumSql);
		deptSql.append(") validsum ");
		deptSql
				.append(" where d.deptid = send.senddeptid(+) and d.deptid = reply.deptid(+) and d.deptid = valid.deptid(+) ");
		deptSql
				.append(" and d.deptid = replysum.deptid(+) and d.deptid = validsum.senddeptid(+)");
		deptSql.append(" and d.regionid = '" + userinfo.getRegionID() + "'");
		if (bean.getDeptid() != null && !"".equals(bean.getDeptid())) {
			deptSql.append(" and d.deptid = '" + bean.getDeptid() + "'");
		}
		deptSql.append(" and d.state is null");

		// ��ѯͳ�ƴ�ά���ű��sql
		StringBuffer contractorSql = new StringBuffer();
		contractorSql
				.append("select con.contractorid deptid, con.contractorname deptname, NVL(send.sendnum,0) sendnum, NVL(reply.replynum,0) replynum, NVL(valid.validnum,0) validnum,");
		contractorSql
				.append("NVL(reply.nooverreplynum,0) nooverreplynum, NVL(valid.noovervalidnum,0) noovervalidnum,");
		contractorSql
				.append("case when (replysum != null or replysum != 0) and nooverreplynum != 0 then nooverreplynum/replysum else 0 end replypercent,  ");
		contractorSql
				.append("case when (validsum != null or validsum != 0) and noovervalidnum != 0 then noovervalidnum/validsum else 0 end validpercent, NVL(replysum.replysum,0) replysum , NVL(validsum.validsum,0) validsum ");
		contractorSql.append("  from contractorinfo con , ");
		contractorSql.append("(");
		contractorSql.append(sendSql);
		contractorSql.append(") send, ");
		contractorSql.append("(");
		contractorSql.append(replySql);
		contractorSql.append(") reply, ");
		contractorSql.append("(");
		contractorSql.append(validSql);
		contractorSql.append(") valid, ");
		contractorSql.append("(");
		contractorSql.append(replysumSql);
		contractorSql.append(") replysum, ");
		contractorSql.append("(");
		contractorSql.append(validsumSql);
		contractorSql.append(") validsum ");
		contractorSql
				.append(" where con.contractorid = send.senddeptid(+) and con.contractorid = reply.deptid(+) and con.contractorid = valid.deptid(+) ");
		contractorSql
				.append(" and con.contractorid = replysum.deptid(+) and con.contractorid = validsum.senddeptid(+) ");
		contractorSql.append(" and con.regionid = '" + userinfo.getRegionID()
				+ "'");
		if (bean.getDeptid() != null && !"".equals(bean.getDeptid())) {
			contractorSql.append(" and con.contractorid = '" + bean.getDeptid()
					+ "'");
		}
		contractorSql.append(" and con.state is null");

		// �ۺϲ�ѯsql
		StringBuffer sql = new StringBuffer();
		sql
				.append("select deptid, deptname, sendnum,  replynum,  validnum, nooverreplynum,noovervalidnum,replypercent, validpercent, replysum, validsum  from (");
		sql.append(deptSql);
		sql.append(" ) union ( ");
		sql.append(contractorSql);
		sql.append(")");

		// ��sql��䱣��
		//System.out.println("sql: " + sql);

		List totalList = null;
		try {
			QueryUtil query = new QueryUtil();
			totalList = query.queryBeans(sql.toString());
		} catch (Exception e) {
			logger.error("���Ź���ͳ������������Ϣ�쳣:" + e.getMessage());
		}

		return totalList;
	}

}
