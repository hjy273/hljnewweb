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

public class EndorseTaskDao{
    Logger logger = Logger.getLogger( this.getClass().getName() );
    public EndorseTaskDao(){
    }


    /**<br>����:������д�ǩ�յ��ɵ��б�
     *  <br>����:�û�����
     *  <br>����:�����˱�ź����Ƶ�List
     * */
    public List getTaskToEndorse( UserInfo userinfo ){
        List lsend = null;
        String sql = "";
//        if( userinfo.getDeptype().equals( "1" ) ){
//            sql =
//                "select s.serialnumber, s.ID,TO_CHAR(s.SENDTIME,'yyyy-mm-dd HH24:MI') sendtime,s.SENDTYPE,TO_CHAR(s.PROCESSTERM,'yyyy-MM-dd HH24:MI') processterm,s.SENDTOPIC,"
//                + " us.USERNAME,ua.USERNAME usernameacce,con.contractorname senddeptname, ad.id subid "
//                + " from sendtask s,userinfo us,userinfo ua,contractorinfo con , sendtask_acceptdept ad "
//                +
//                " where s.SENDUSERID=us.USERID(+) and s.id = ad.sendtaskid(+) and ad.useid = ua.userid(+) " 
//                + " and s.senddeptid=con.contractorid "
//                + " and  ad.deptid ='" + userinfo.getDeptID() + "' and ad.workstate='1��ǩ��'"
//                + " order by s.SENDTIME desc";
//        }
//        else{
//            sql =
//                "select s.serialnumber, s.ID,TO_CHAR(s.SENDTIME,'yyyy-mm-dd HH24:MI') sendtime,s.SENDTYPE,TO_CHAR(s.PROCESSTERM,'yyyy-MM-dd HH24:MI') processterm,s.SENDTOPIC,"
//                + " us.USERNAME,ua.USERNAME usernameacce,de.deptname senddeptname, ad.id subid "
//                + " from sendtask s,userinfo us,userinfo ua,deptinfo de ,sendtask_acceptdept ad"
//                + " where s.SENDUSERID=us.USERID(+) and s.id = ad.sendtaskid(+) and ad.useid = ua.userid(+) " 
//                + " and s.senddeptid=de.deptid "
//                + " and  ad.deptid ='" + userinfo.getDeptID() + "'  and ad.workstate='1��ǩ��'"
//                + " order by s.SENDTIME desc";
//
//        }

//        System.out.println( "SQL:" + sql );
        
        sql =  "select s.serialnumber, s.ID,s.SENDTYPE,s.SENDTOPIC,"
          + "  round(to_number(s.processterm - sysdate)*24,1) processterm ,"
    	  + " ua.USERNAME usernameacce, ad.id subid ,ad.useid acceptuserid, "
    	  + " NVL((select c.contractorname from contractorinfo c where c.contractorid = s.senddeptid),"
    	  + " 	  (select de.deptname from deptinfo de where de.deptid = s.senddeptid))  senddeptname "
    	  + " from sendtask s,userinfo ua ,sendtask_acceptdept ad"
    	  + " where  s.id = ad.sendtaskid(+) and ad.useid = ua.userid(+) " 
    	  + " and  ad.deptid ='" + userinfo.getDeptID() + "'  and ad.workstate='1��ǩ��'"
    	  + " order by s.SENDTIME desc";

        try{
            QueryUtil qu = new QueryUtil();
            lsend = qu.queryBeans( sql );
            return lsend;
        }
        catch( Exception ex ){
            logger.warn( "������д�ǩ�յ��ɵ��б����:" + ex.getMessage() );
            return null;
        }
    }
    
    /**<br>����:������д�ǩ�յ��ɵ��б�
     *  <br>����:�û�����
     *  <br>����:�����˱�ź����Ƶ�List
     * */
    public List getLoginUserTaskToEndorse( UserInfo userinfo ){
        List lsend = null;
        String sql =  "select s.serialnumber, s.ID,s.SENDTYPE,s.SENDTOPIC,"
        		   + "  round(to_number(s.processterm - sysdate)*24,1) processterm ,"
        		   + " ua.USERNAME usernameacce, ad.id subid ,ad.useid acceptuserid, "
        		   + " NVL((select c.contractorname from contractorinfo c where c.contractorid = s.senddeptid),"
        		   + " 	  (select de.deptname from deptinfo de where de.deptid = s.senddeptid))  senddeptname "
        		   + " from sendtask s,userinfo ua ,sendtask_acceptdept ad"
        		   + " where  s.id = ad.sendtaskid(+) and ad.useid = ua.userid(+) " 
        		   + " and  ad.useid ='" + userinfo.getUserID() + "'  and ad.workstate='1��ǩ��'"
        		   + " order by s.SENDTIME desc";

        try{
            QueryUtil qu = new QueryUtil();
            lsend = qu.queryBeans( sql );
            return lsend;
        }
        catch( Exception ex ){
            logger.warn( "������д�ǩ�յ��ɵ��б����:" + ex.getMessage() );
            return null;
        }
    }
    
    /**
     * ͳ�Ʋ��ŵĴ�ǩ���ĸ��������˴�ǩ������
     * @param userinfo
     * @return
     */
    public List getEndorseCountList(UserInfo userinfo) {
        List countList = null;
        String sql = "";
        
        sql = "select deptnum, usernum from "
        	+ "	(select count(*) deptnum from ("
    	    + " 	select s.id from sendtask m left join sendtask_acceptdept s on m.id = s.sendtaskid "
    	    + " 	where  s.workstate='1��ǩ��' and s.deptid = '" + userinfo.getDeptID() + "'"
    	 	+ " 	group by s.id )),"
    	 	+ " (select count(*) usernum from sendtask_acceptdept  " 
    	 	+ " where workstate='1��ǩ��' and useid = '" + userinfo.getUserID() + "')";

        try{
            QueryUtil qu = new QueryUtil();
            countList = qu.queryBeans( sql );
            return countList;
        }
        catch( Exception ex ){
            logger.warn( "������д�ǩ�յ��ɵ��б����:" + ex.getMessage() );
            return null;
        }
    	
    }

    
   public String getOneSendTaskState(String subid) {
    	 String workstate = "";
    	 ResultSet rs = null;
    	 QueryUtil qu = null;
    	 String sql = "select s.workstate from sendtask_acceptdept s where s.id='" + subid + "'";
    	 try {
			qu = new QueryUtil();
			rs = qu.executeQuery(sql);
			if(rs.next()) {
				workstate = rs.getString("workstate");
			}
			return workstate;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			qu.close();
		}
     }
    //TO_CHAR(s.PROCESSTERM,'yyyy-MM-dd HH24:MI') processterm
    public List getOneSendTaskInfo(String subid) {
    	String sql = "";
    	QueryUtil qu = null;
    	sql = "select id replyid, TO_CHAR(st.VALIDATETIME,'yyyy-MM-dd HH24:MI') VALIDATETIME, st.validateremark,st.VALIDATEACCE,st.VALIDATERESULT from sendtaskreply st where st.sendtaskid='" + subid 
    	+ "' order by VALIDATETIME desc";
    	try {
			qu = new QueryUtil();
			return qu.queryBeans(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			qu.close();
		}
    }
    
    //sendtaskreply sendtaskid sendtask id
    /*public SendTaskBean getOneSendTaskByState(String id, UserInfo userinfo) {
    	SendTaskBean bean = new SendTaskBean();
        ResultSet rst;
        String sql = "";
        if( userinfo.getDeptype().equals( "2" ) ){
            sql = "SELECT   s.ID,s.senduserid, TO_CHAR (s.sendtime, 'yyyy-mm-dd HH24:MI') sendtime,"
                  + " s.sendtype, TO_CHAR(s.PROCESSTERM,'yyyy-MM-dd HH24:MI') processterm, s.sendtopic,s.SENDTEXT,s.SENDACCE,s.acceptdeptid,s.acceptuserid,"
                  + " SUBSTR (s.workstate, 2, 6) workstate, us.username,de.deptname senddeptname,"
                  + " ua.username usernameacce, NVL (se.RESULT, '��ǩ��') RESULT,st.validateremark,st.VALIDATEACCE "
                  + " FROM sendtask s, userinfo us, userinfo ua, sendtaskendorse se,deptinfo de, sendtaskreply st "
                  + " WHERE s.senduserid = us.userid(+) and st.sendtaskid='" + id + "'"
                  + "      AND s.acceptuserid = ua.userid(+)"
                  + "      AND s.endorseid = se.ID(+)"
                  + "      AND s.senddeptid = de.deptid(+)"
                  + "      AND s.id = '" + id + "'";
        }
        else{
            sql = "SELECT   s.ID,s.senduserid, TO_CHAR (s.sendtime, 'yyyy-mm-dd HH24:MI') sendtime,"
                  + " s.sendtype, TO_CHAR(s.PROCESSTERM,'yyyy-MM-dd HH24:MI') processterm, s.sendtopic,s.SENDTEXT,s.SENDACCE,s.acceptdeptid,s.acceptuserid,"
                  + " SUBSTR (s.workstate, 2, 6) workstate, us.username,con.contractorname senddeptname,"
                  + " ua.username usernameacce, NVL (se.RESULT, '��ǩ��') RESULT,st.validateremark,st.VALIDATEACCE "
                  + " FROM sendtask s, userinfo us, userinfo ua, sendtaskendorse se,contractorinfo con, sendtaskreply st"
                  + " WHERE s.senduserid = us.userid(+) and st.sendtaskid='" + id + "'"
                  + "      AND s.acceptuserid = ua.userid(+)"
                  + "      AND s.endorseid = se.ID(+)"
                  + "      AND s.senddeptid = con.contractorid(+)"
                  + "      AND s.id = '" + id + "'";
        }

        System.out.println( "SQL������:" + sql );

        try{
            QueryUtil qu = new QueryUtil();
            rst = qu.executeQuery( sql );
            try{
                if( rst != null && rst.next() ){
                    bean.setId( rst.getString( "id" ) );
                    bean.setSendtime( rst.getString( "sendtime" ) );
                    bean.setSendtype( rst.getString( "sendtype" ) );
                    bean.setProcessterm( rst.getString( "processterm" ) );
                    bean.setSendtopic( rst.getString( "sendtopic" ) );
                    bean.setSendtext( rst.getString( "sendtext" ) );
                    bean.setSendacce( rst.getString( "sendacce" ) );
                    bean.setWorkstate( rst.getString( "workstate" ) );
                    bean.setUsername( rst.getString( "username" ) );
                    bean.setUsernameacce( rst.getString( "usernameacce" ) );
                    bean.setResult( rst.getString( "result" ) );
                    //bean.setAcceptdeptname( rst.getString( "acceptdeptname" ) );
                    bean.setAcceptdeptid( rst.getString( "acceptdeptid" ) );
                    bean.setAcceptuserid( rst.getString( "acceptuserid" ) );
                    bean.setSenduserid( rst.getString( "senduserid" ) );
                    bean.setSenddeptname( rst.getString( "senddeptname" ) );
                    bean.setValidateremark(rst.getString("validateremark"));
                    bean.setValidateacce(rst.getString("VALIDATEACCE"));
                    rst.close();
                    return bean;
                }
                rst.close();
                return null;

            }
            catch( Exception ex ){
                logger.warn( "��ô�ǩ���ɵ�����ϸ��Ϣ����1:" + ex.getMessage() );
                rst.close();
                return null;
            }

        }
        catch( Exception ex ){
            logger.warn( "��ô�ǩ���ɵ�����ϸ��Ϣ����:" + ex.getMessage() );
            return null;
        }
    }*/
    
    /**<br>����:��ô�ǩ���ɵ�����ϸ��Ϣ
     *  <br>����:�ɵ����
     *  <br>����:
     * */
    public SendTaskBean getOneSendTask( String id, UserInfo userinfo, String subid ){
        SendTaskBean bean = new SendTaskBean();
        ResultSet rst;
        String sql = "";
//        if( userinfo.getDeptype().equals( "2" ) ){
//            sql = "SELECT  s.serialnumber, s.ID, ad.id subid , s.senduserid, TO_CHAR (s.sendtime, 'yyyy-mm-dd HH24:MI') sendtime,"
//                  + " s.sendtype, TO_CHAR(s.PROCESSTERM,'yyyy-MM-dd HH24:MI') processterm, s.sendtopic,s.SENDTEXT,s.SENDACCE,s.acceptdeptid,s.acceptuserid,"
//                  + " SUBSTR (ad.workstate, 2, 6) workstate, us.username,de.deptname senddeptname,"
//                  + " ua.username usernameacce, NVL (se.RESULT, '��ǩ��') RESULT, TO_CHAR(se.TIME,'yyyy-MM-dd HH24:MI') time"
//                  + " FROM sendtask s, userinfo us, userinfo ua, sendtaskendorse se,deptinfo de, sendtask_acceptdept ad "
//                  + " WHERE s.senduserid = us.userid(+)"
//                  + " and s.id = ad.sendtaskid(+)"
//                  + "      AND ad.useid = ua.userid(+)"
//                  //+ "      AND s.endorseid = se.ID(+)"
//                  + " and ad.id = se.sendtaskid(+)" 
//                  + "      AND s.senddeptid = de.deptid(+)"
//                  + "      AND s.id = '" + id + "'";
//                  if(subid != null && !"null".equals(subid)) {
//                	  sql = sql + " and ad.id = '" + subid + "'";
//                  }
//                  
//            		
//        }
//        else{
//            sql = "SELECT  s.serialnumber, s.ID, ad.id subid, s.senduserid, TO_CHAR (s.sendtime, 'yyyy-mm-dd HH24:MI') sendtime,"
//                  + " s.sendtype, TO_CHAR(s.PROCESSTERM,'yyyy-MM-dd HH24:MI') processterm, s.sendtopic,s.SENDTEXT,s.SENDACCE,s.acceptdeptid,s.acceptuserid,"
//                  + " SUBSTR (ad.workstate, 2, 6) workstate, us.username,con.contractorname senddeptname,"
//                  + " ua.username usernameacce, NVL (se.RESULT, '��ǩ��') RESULT, TO_CHAR(se.TIME,'yyyy-MM-dd HH24:MI') time"
//                  + " FROM sendtask s, userinfo us, userinfo ua, sendtaskendorse se,contractorinfo con, sendtask_acceptdept ad"
//                  + " WHERE s.senduserid = us.userid(+)"
//                  + " and s.id = ad.sendtaskid(+)"
//                  + "      AND ad.useid = ua.userid(+)"
//                  //+ "      AND s.endorseid = se.ID(+)"
//                  + " and ad.id = se.sendtaskid(+)" 
//                  + "      AND s.senddeptid = con.contractorid(+)"
//                  + "      AND s.id = '" + id + "'";
//                  if(subid != null && !"null".equals(subid)) {
//                	  sql = sql + " and ad.id = '" + subid + "'";
//                  }
//        }
        
        
        sql = "SELECT  s.serialnumber, s.ID, ad.id subid, s.senduserid, TO_CHAR (s.sendtime, 'yyyy-mm-dd HH24:MI') sendtime,"
            + " s.sendtype, TO_CHAR(s.PROCESSTERM,'yyyy-MM-dd HH24:MI') processterm, s.sendtopic,s.SENDTEXT,s.SENDACCE, "
            + " ad.deptid acceptdeptid,ad.useid acceptuserid,"
            + " SUBSTR (ad.workstate, 2, 6) workstate, us.username,"
            + " NVL((select c.contractorname from contractorinfo c where c.contractorid = s.senddeptid),"
            + " 	  (select de.deptname from deptinfo de where de.deptid = s.senddeptid))  senddeptname, "
            + " NVL((select c.contractorname from contractorinfo c where c.contractorid = ad.deptid),"
            + " 	  (select de.deptname from deptinfo de where de.deptid = ad.deptid))  acceptdeptname, "
            + " ua.username usernameacce, NVL (se.RESULT, '��ǩ��') RESULT, TO_CHAR(se.TIME,'yyyy-MM-dd HH24:MI') time"
            + " FROM sendtask s, userinfo us, userinfo ua, sendtaskendorse se, sendtask_acceptdept ad"
            + " WHERE s.senduserid = us.userid(+)"
            + " and s.id = ad.sendtaskid(+)"
            + "      AND ad.useid = ua.userid(+)"
            //+ "      AND s.endorseid = se.ID(+)"
            + " and ad.id = se.sendtaskid(+)" 
            + "      AND s.id = '" + id + "'";
            if(subid != null && !"null".equals(subid)) {
          	  sql = sql + " and ad.id = '" + subid + "'";
            }


        //System.out.println( "SQL:" + sql );

        try{
            QueryUtil qu = new QueryUtil();
            rst = qu.executeQuery( sql );
            try{
                if( rst != null && rst.next() ){
                    bean.setId( rst.getString( "id" ) );
                    bean.setSubtaskid(rst.getString("subid"));
                    bean.setSendtime( rst.getString( "sendtime" ) );
                    bean.setSendtype( rst.getString( "sendtype" ) );
                    bean.setProcessterm( rst.getString( "processterm" ) );
                    bean.setSendtopic( rst.getString( "sendtopic" ) );
                    bean.setSendtext( rst.getString( "sendtext" ) );
                    bean.setSendacce( rst.getString( "sendacce" ) );
                    bean.setWorkstate( rst.getString( "workstate" ) );
                    bean.setUsername( rst.getString( "username" ) );
                    bean.setUsernameacce( rst.getString( "usernameacce" ) );
                    bean.setResult( rst.getString( "result" ) );
                    bean.setAcceptdeptname( rst.getString( "acceptdeptname" ) );
                    bean.setAcceptdeptid( rst.getString( "acceptdeptid" ) );
                    bean.setAcceptuserid( rst.getString( "acceptuserid" ) );
                    bean.setSenduserid( rst.getString( "senduserid" ) );
                    bean.setSenddeptname( rst.getString( "senddeptname" ) );
                    bean.setSerialnumber(rst.getString("serialnumber"));
                    bean.setTime(rst.getString("time"));
                    rst.close();
                    return bean;
                }
                rst.close();
                return null;

            }
            catch( Exception ex ){
                logger.warn( "��ô�ǩ���ɵ�����ϸ��Ϣ����1:" + ex.getMessage() );
                rst.close();
                return null;
            }

        }
        catch( Exception ex ){
            logger.warn( "��ô�ǩ���ɵ�����ϸ��Ϣ����:" + ex.getMessage() );
            return null;
        }
    }


    /**<br>����:�����ɵ�ǩ����Ϣ
     *  <br>����:�ɵ�Bean
     *  <br>����:��д�ɹ�����true ���򷵻� false
     * */
    public boolean saveEndorse( SendTaskBean bean ){
        OracleIDImpl ora = new OracleIDImpl();
        String id = ora.getSeq( "sendtaskendorse", 10 );
        if( id.equals( "" ) ){
            return false;
        }

        Date nowDate = new Date();
        DateFormat df = DateFormat.getDateTimeInstance();
        String date = df.format( nowDate );

        String remark = bean.getRemark().length() > 1024 ? bean.getRemark().substring( 0,
                        1024 ) : bean.getRemark();

        String sql1 = "insert into sendtaskendorse (id,time,deptid,userid,result,remark,acce,sendtaskid) values('"
                      + id + "', TO_DATE('" + date + "','yyyy-MM-dd HH24:MI:SS'),'" + bean.getDeptid() + "','"
                      + bean.getUserid() + "','"
                      + bean.getResult() + "','" + remark + "','" + bean.getAcce() + "','" + bean.getSubtaskid() + "')";
        String sql2 = "";
        if( bean.getResult().equals( "ǩ��" ) ){
            //sql2 = "update sendtask set endorseid='" + id + "',workstate='3���ظ�' where id='" + bean.getId() + "'";
        	sql2 = "update sendtask_acceptdept set workstate='3���ظ�' where id='" + bean.getSubtaskid() + "'";
        }
        else{
            //sql2 = "update sendtask set endorseid='" + id + "',workstate='0������' where id='" + bean.getId() + "'";
            sql2 = "update sendtask_acceptdept set workstate='0������' where id='" + bean.getSubtaskid() + "'";
        }

        //System.out.println( "SQL:" + sql1 );
        try{
            UpdateUtil up = new UpdateUtil();
            up.setAutoCommitFalse();
            try{
                up.executeUpdate( sql1 );
                up.executeUpdate( sql2 );
                up.commit();
                up.setAutoCommitTrue();
                return true;
            }
            catch( Exception ex ){
                logger.warn( "�����ɵ�ǩ����Ϣ����1:" + ex.getMessage() );
                up.rollback();
                return false;
            }
        }
        catch( Exception ex ){
            logger.warn( "�����ɵ�ǩ����Ϣ����:" + ex.getMessage() );
            return false;
        }
    }


    /**<br>����:�������ǩ�յ��ɵ��б�
     *  <br>����:�û�����
     *  <br>����:�����˱�ź����Ƶ�List
     * */
    public List getendorseList( UserInfo userinfo ){
        List lsend = null;
        String sql = "";
//        if( userinfo.getDeptype().equals( "1" ) ){
//            sql =
//                "select s.serialnumber,s.ID,TO_CHAR(s.SENDTIME,'yyyy-mm-dd HH24:MI') sendtime,s.SENDTYPE,TO_CHAR(s.PROCESSTERM,'yyyy-MM-dd HH24:MI') processterm,s.SENDTOPIC,"
//                + " us.USERNAME,ua.USERNAME usernameacce,con.contractorname senddeptname, "
//                + " SUBSTR(ad.WORKSTATE,2,6) workstate,TO_CHAR(en.TIME,'yyyy-MM-dd HH24:MI') time,en.RESULT , ad.id subid "
//                + " from sendtask s,userinfo us,userinfo ua,contractorinfo con,sendtaskendorse en , sendtask_acceptdept ad"
//                +
//                " where s.SENDUSERID=us.USERID(+) and s.id = ad.sendtaskid(+) and ad.useid = ua.userid(+)  and s.senddeptid=con.contractorid "
//                //+ " and s.ENDORSEID = en.ID"
//                + " and ad.id = en.sendtaskid(+) "
//                + " and  ad.deptid ='" + userinfo.getDeptID() + "' and (ad.workstate 	!= '1��ǩ��' )"
//                + " order by ad.workstate,s.SENDTIME desc";
//        }
//        else{
//            sql =
//                "select s.serialnumber,s.ID,TO_CHAR(s.SENDTIME,'yyyy-mm-dd HH24:MI') sendtime,s.SENDTYPE,TO_CHAR(s.PROCESSTERM,'yyyy-MM-dd HH24:MI') processterm,s.SENDTOPIC,"
//                + " us.USERNAME,ua.USERNAME usernameacce,de.deptname senddeptname,"
//                + " SUBSTR(ad.WORKSTATE,2,6) workstate,TO_CHAR(en.TIME,'yyyy-MM-dd HH24:MI') time,en.RESULT, ad.id subid "
//                + " from sendtask s,userinfo us,userinfo ua,deptinfo de,sendtaskendorse en, sendtask_acceptdept ad "
//                + " where s.SENDUSERID=us.USERID(+) and s.id = ad.sendtaskid(+) and ad.useid = ua.userid(+) and s.senddeptid=de.deptid "
//                //+ " and s.ENDORSEID = en.ID"
//                + " and ad.id = en.sendtaskid(+) "
//                + " and  ad.deptid ='" + userinfo.getDeptID() + "'  and (ad.workstate 	!= '1��ǩ��' )"
//                + " order by s.workstate,s.SENDTIME desc";
//
//        }
        
        sql = "select s.serialnumber,s.ID,TO_CHAR(s.SENDTIME,'yyyy-mm-dd HH24:MI') sendtime,s.SENDTYPE,TO_CHAR(s.PROCESSTERM,'yyyy-MM-dd HH24:MI') processterm,s.SENDTOPIC,"
        	+ " us.USERNAME,ua.USERNAME usernameacce, "
        	//+ " NVL((select c.contractorname from contractorinfo c where c.contractorid = s.senddeptid),"
            //+ " 	  (select de.deptname from deptinfo de where de.deptid = s.senddeptid))  senddeptname, "
        	+ " SUBSTR(ad.WORKSTATE,2,6) workstate,TO_CHAR(en.TIME,'yyyy-MM-dd HH24:MI') time,en.RESULT, ad.id subid "
        	+ " from sendtask s,userinfo us,userinfo ua,sendtaskendorse en, sendtask_acceptdept ad "
        	+ " where s.SENDUSERID=us.USERID(+) and s.id = ad.sendtaskid(+) and ad.useid = ua.userid(+) "
            + " and ad.id = en.sendtaskid(+) "
            + " and  ad.deptid ='" + userinfo.getDeptID() + "'  and (ad.workstate 	!= '1��ǩ��' )"
            + " order by s.SENDTIME desc";

        //System.out.println( "�������SQL:" + sql );

        try{
            QueryUtil qu = new QueryUtil();
            lsend = qu.queryBeans( sql );
            return lsend;
        }
        catch( Exception ex ){
            logger.warn( "�������ǩ�յ��ɵ��б����:" + ex.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:���������ɵ���Ϣ
     * <br>����:request
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     */
    public List queryendorseList( UserInfo userinfo, SendTaskBean bean, HttpSession session){
        List lsend = null;
        String sql="";
//        if( userinfo.getDeptype().equals( "1" ) ){
//            sql =
//                "select s.serialnumber, s.ID,TO_CHAR(s.SENDTIME,'yyyy-mm-dd HH24:MI') sendtime,s.SENDTYPE,TO_CHAR(s.PROCESSTERM,'yyyy-MM-dd HH24:MI') processterm,s.SENDTOPIC,"
//                + " us.USERNAME,ua.USERNAME usernameacce,con.contractorname senddeptname, "
//                + " SUBSTR(ad.WORKSTATE,2,6) workstate,TO_CHAR(en.TIME,'yyyy-MM-dd HH24:MI') time,en.RESULT , ad.id subid "
//                + " from sendtask s,userinfo us,userinfo ua,contractorinfo con,sendtaskendorse en , sendtask_acceptdept ad "
//                +
//                " where s.SENDUSERID=us.USERID(+) and s.id = ad.sendtaskid(+) and ad.useid = ua.userid(+) " 
//                + " and s.senddeptid=con.contractorid "
//                //+ " and s.ENDORSEID = en.ID"
//                + " and ad.id = en.sendtaskid(+) "
//                + " and  ad.deptid ='" + userinfo.getDeptID() + "' and (ad.workstate='3���ظ�' or ad.workstate='0������') ";
//
//        }
//        else{
//            sql =
//                "select s.serialnumber, s.ID,TO_CHAR(s.SENDTIME,'yyyy-mm-dd HH24:MI') sendtime,s.SENDTYPE,TO_CHAR(s.PROCESSTERM,'yyyy-MM-dd HH24:MI') processterm,s.SENDTOPIC,"
//                + " us.USERNAME,ua.USERNAME usernameacce,de.deptname senddeptname,"
//                + " SUBSTR(ad.WORKSTATE,2,6) workstate,TO_CHAR(en.TIME,'yyyy-MM-dd HH24:MI') time,en.RESULT, ad.id subid "
//                + " from sendtask s,userinfo us,userinfo ua,deptinfo de,sendtaskendorse en, sendtask_acceptdept ad "
//                + " where s.SENDUSERID=us.USERID(+) and s.id = ad.sendtaskid(+) and ad.useid = ua.userid(+) " 
//                + " and s.senddeptid=de.deptid "
//               // + " and s.ENDORSEID = en.ID"
//                + " and ad.id = en.sendtaskid(+) "
//                + " and  ad.deptid ='" + userinfo.getDeptID() + "'  and (ad.workstate='3���ظ�' or ad.workstate='0������')";
//        }
        
      sql =
    	  "select s.serialnumber, s.ID,s.SENDTYPE,s.SENDTOPIC,"
      		+ "  case when (ad.workstate = '1��ǩ��' or ad.workstate = '3���ظ�') then round(to_number(s.processterm - sysdate)*24,1) "
      		+ " 	when s.PROCESSTERM < r.replytime then  round(to_number(s.processterm - r.replytime )*24,1) "
      		+ " else 0 end processterm ,"
      		+ " ua.USERNAME usernameacce,"
      		+ " SUBSTR(ad.WORKSTATE,2,6) workstate,ad.id subid, "
      		 + "  NVL((select c.contractorname from contractorinfo c where c.contractorid = s.senddeptid),"
             + " 	  (select de.deptname from deptinfo de where de.deptid = s.senddeptid))  senddeptname "
      		+ " from sendtask s,userinfo ua, sendtask_acceptdept ad, sendtaskreply r, sendtaskendorse en "
      		+ " where s.id = ad.sendtaskid(+) and ad.useid = ua.userid(+) " 
      		+ " and ad.id = r.sendtaskid(+) and ad.id = en.sendtaskid(+) "
      		+ " and  ad.deptid ='" + userinfo.getDeptID() + "' and (ad.workstate='3���ظ�' or ad.workstate='0������') ";


        try{
//            if( bean.getSendusername() != null && !bean.getSendusername().equals( "" ) ){
//                sql = sql + " and us.USERNAME like '" + bean.getSendusername() + "%' ";
//            }
//            if( bean.getAcceptusername() != null && !bean.getAcceptusername().equals( "" ) ){
//                sql = sql + "  and ua.USERNAME like '" + bean.getAcceptusername() + "%'  ";
//            }
            if( bean.getSendtype() != null && !bean.getSendtype().equals( "" ) ){
                sql = sql + "  and s.SENDTYPE like '" + bean.getSendtype() + "%'  ";
            }
            if( bean.getSendtopic() != null && !bean.getSendtopic().equals( "" ) ){
                sql = sql + "  and s.SENDTOPIC like '" + bean.getSendtopic() + "%'  ";
            }
            if( bean.getProcessterm() != null && !bean.getProcessterm().equals( "" ) ){
                if(bean.getProcessterm().equals("����")){
                    sql += " AND s.PROCESSTERM < NVL (r.replytime,SYSDATE)";
                } else{
                    sql +=  " AND s.PROCESSTERM >= NVL (r.replytime,SYSDATE)";
                }
            }

            if( bean.getWorkstate() != null && !bean.getWorkstate().equals( "" ) ){
                sql = sql + "  and ad.WORKSTATE like '" + bean.getWorkstate() + "%'  ";
            }
            if( bean.getResult() != null && !bean.getResult().equals( "" ) ){
               sql = sql + "  and en.result like '" + bean.getResult() + "%'  ";
           }


            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                sql = sql + " and en.time >=TO_DATE('" + bean.getBegintime()
                      + " 00:00:00','YYYY-MM-DD HH24:MI:SS ')";
            }
            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                sql = sql + " and en.time <= TO_DATE('" + bean.getEndtime() + " 23:59:59','YYYY-MM-DD HH24:MI:SS')";
            }
            sql = sql + " order by s.SENDTIME desc";;

            //System.out.println( "��ѯSQL:" + sql );
            session.setAttribute("endorseTaskCon", sql);
            QueryUtil query = new QueryUtil();
            lsend = query.queryBeans( sql );
            return lsend;
        }
        catch( Exception e ){
            logger.error( "���������ɵ���Ϣ�쳣:" + e.getMessage() );
            return null;
        }
    }
    
    /**
     * ��ʾ���ĺ󷵻صĲ�ѯ����
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

    public static void main(String[] args) {
    	

	}

}
