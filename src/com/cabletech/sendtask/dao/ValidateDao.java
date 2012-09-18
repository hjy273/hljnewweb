package com.cabletech.sendtask.dao;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.sendtask.beans.SendTaskBean;

public class ValidateDao{
    Logger logger = Logger.getLogger( this.getClass().getName() );
    public ValidateDao(){
    }


    /**<br>功能:获得所有待验证的派单列表
     *  <br>参数:用户对象
     *  <br>返回:List
     * */
    public List getTaskToVali( UserInfo userinfo, String loginuserFlg ){
        List lreply = null;
        String sql = "";
//        if( userinfo.getDeptype().equals( "1" ) ){
//            sql = "select s.serialnumber, s.ID,us.USERNAME sendusername,TO_CHAR(s.SENDTIME,'yyyy-MM-dd HH24:MI') sendtime,s.SENDTYPE,　"
//                  + " TO_CHAR(s.PROCESSTERM,'yyyy-MM-dd HH24:MI') processterm,s.SENDTOPIC,ua.USERNAME acceptusername,SUBSTR(ad.WORKSTATE,2,6) workstate,con.contractorname acceptdeptname,"
//                  + " sr.ID replyid,TO_CHAR(sr.replytime,'yyyy-MM-dd HH24:MI') replytime,ur.USERNAME replyusername, ad.id subid "
//                  + " from sendtask s,sendtaskreply sr,userinfo us,userinfo ua,userinfo ur,contractorinfo con , sendtask_acceptdept ad"
//                  + " where s.SENDUSERID = us.USERID"
//                  + "       and ad.USEID = ua.USERID "
//                  //+ "       and s.REPLYID = sr.ID"
//                  + "  and s.id = ad.sendtaskid "
//                  + " and ad.id = sr.sendtaskid "
//                  + "       and sr.REPLYUSERID = ur.USERID"
//                  + "       and ad.DEPTID = con.contractorid"
//                  + "		and ad.workstate = '6待验证'"
//                  + "       and s.SENDDEPTID = '" + userinfo.getDeptID() + "'"
//                  + " order by sr.REPLYTIME desc,s.SENDTIME desc";
//
//        }
//        else{
//            sql = "select s.serialnumber, s.ID,us.USERNAME sendusername,TO_CHAR(s.SENDTIME,'yyyy-MM-dd HH24:MI') sendtime,s.SENDTYPE,　"
//                  +
//                " TO_CHAR(s.PROCESSTERM,'yyyy-MM-dd HH24:MI') processterm,s.SENDTOPIC,ua.USERNAME acceptusername,SUBSTR(ad.WORKSTATE,2,6) workstate,d.deptname acceptdeptname,"
//                  + " sr.ID replyid,TO_CHAR(sr.replytime,'yyyy-MM-dd HH24:MI') replytime,ur.USERNAME replyusername , ad.id subid"
//                  + " from sendtask s,sendtaskreply sr,userinfo us,userinfo ua,userinfo ur,deptinfo d , sendtask_acceptdept ad "
//                  + " where s.SENDUSERID = us.USERID"
//                  + "       and ad.USEID = ua.USERID "
//                  //+ "       and s.REPLYID = sr.ID"
//                  + "  and s.id = ad.sendtaskid "
//                  + " and ad.id = sr.sendtaskid "
//                  + "       and sr.REPLYUSERID = ur.USERID"
//                  + "       and ad.DEPTID = d.DEPTID"
//                  + "		and ad.workstate = '6待验证'"
//                  + "       and s.SENDDEPTID = '" + userinfo.getDeptID() + "'"
//                  + " order by sr.REPLYTIME desc,s.SENDTIME desc";
//
//        }
        
//        sql = "select s.serialnumber, s.ID,s.senduserid , us.USERNAME sendusername,TO_CHAR(s.SENDTIME,'yyyy-MM-dd HH24:MI') sendtime,s.SENDTYPE,　"
//            + " TO_CHAR(s.PROCESSTERM,'yyyy-MM-dd HH24:MI') processterm,s.SENDTOPIC,ua.USERNAME acceptusername,SUBSTR(ad.WORKSTATE,2,6) workstate,"
//            + " sr.ID replyid,TO_CHAR(sr.replytime,'yyyy-MM-dd HH24:MI') replytime,ur.USERNAME replyusername , ad.id subid"
//            + " from sendtask s,sendtaskreply sr,userinfo us,userinfo ua,userinfo ur, sendtask_acceptdept ad "
//            + " where s.SENDUSERID = us.USERID"
//            + "       and ad.USEID = ua.USERID "
//            + "  and s.id = ad.sendtaskid "
//            + " and ad.id = sr.sendtaskid "
//            + "       and sr.REPLYUSERID = ur.USERID"
//            + "		and ad.workstate = '6待验证'"
//            + "       and s.SENDDEPTID = '" + userinfo.getDeptID() + "' "
//            + " and s.senduserid ='" + userinfo.getUserID() + "' "
//            + " order by sr.REPLYTIME desc,s.SENDTIME desc";
        
        sql = "select s.serialnumber, s.ID,s.senduserid , us.USERNAME sendusername,TO_CHAR(s.SENDTIME,'yyyy-MM-dd HH24:MI') sendtime,s.SENDTYPE,　"
            + " round(to_number(sr.replytime + 2 - sysdate)*24,1) processterm,TO_CHAR(sr.replytime,'yyyy-MM-dd') replytime,"
            + " s.SENDTOPIC,SUBSTR(ad.WORKSTATE,2,6) workstate, ad.id subid,"
            + "  NVL((select c.contractorname from contractorinfo c where c.contractorid = s.senddeptid),"
            + " 	  (select de.deptname from deptinfo de where de.deptid = s.senddeptid))  senddeptname "
            + " from sendtask s,userinfo us, sendtaskreply sr,sendtask_acceptdept ad "
            + " where s.SENDUSERID = us.USERID(+)"         
            + "  and s.id = ad.sendtaskid(+) "
            + " and ad.id = sr.sendtaskid(+) "
            + "		and ad.workstate = '6待验证'"
            + "       and s.SENDDEPTID = '" + userinfo.getDeptID() + "' ";
        	// 2009-1-5 add by guixy 
        	if(loginuserFlg != null) {
        		sql += " and s.senduserid ='" + userinfo.getUserID() + "' ";
        	}
            //+ " and s.senduserid ='" + userinfo.getUserID() + "' "
           //sql  += " and sr.validateresult is null "
             sql += " order by sr.REPLYTIME desc,s.SENDTIME desc";

       // System.out.println( "SQL:" + sql );

        try{
            QueryUtil qu = new QueryUtil();
            lreply = qu.queryBeans( sql );
            return lreply;
        }
        catch( Exception ex ){
            logger.warn( "获得所有待验证的派单列表出错:" + ex.getMessage() );
            return null;
        }
    }

    /**
     * 统计部门的待验证单的个数、个人待验证单个数
     * @param userinfo
     * @return
     */
    public List getValidateCountList(UserInfo userinfo) {
        List countList = null;
        String sql = "";
        
        sql = "select deptnum, usernum from "
        	+ "	(select count(*) deptnum from ("
    	    + " 	select s.id from sendtask m left join sendtask_acceptdept s on m.id = s.sendtaskid "
    	    + " 	where  s.workstate='6待验证' and m.senddeptid = '" + userinfo.getDeptID() + "'"
    	 	+ " 	group by s.id )),"    	 	
    	 	+ "	(select count(*) usernum from ("
    	    + " 	select s.id from sendtask m left join sendtask_acceptdept s on m.id = s.sendtaskid "
    	    + " 	where  s.workstate='6待验证' and m.senduserid = '" + userinfo.getUserID() + "'"
    	    + "				and m.senddeptid = '" + userinfo.getDeptID() + "'"
    	 	+ " 	group by s.id ))";

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


    /**<br>功能:保存派单验证信息
     *  <br>参数:派单Bean
     *  <br>返回:填写成功返回true 否则返回 false
     * */
    public boolean saveValidate( SendTaskBean bean ){
        Date nowDate = new Date();
        DateFormat df = DateFormat.getDateTimeInstance();
        String date = df.format( nowDate );

        String valiremark = bean.getValidateremark().length() > 1024 ?bean.getValidateremark().substring( 0,
                             1024 ) : bean.getValidateremark();

        String sql1 ="update sendtaskreply set validatetime=TO_DATE('" + date + "','yyyy-MM-dd HH24:MI:SS'),"
                     + "validateuserid='" + bean.getValidateuserid() + "',validateresult='" + bean.getValidateresult() + "',"
                     + "validateacce='" + bean.getValidateacce() + "',validateremark='" + valiremark + "' "
                     + "where id='" + bean.getReplyid() + "'";

        String sql2 = "";
        if(bean.getValidateresult().equals("通过验证"))
           // sql2 = " update sendtask set workstate='9已存档' where id='" + bean.getId() + "'";
        	sql2 = " update sendtask_acceptdept set workstate='9已存档' where id='" + bean.getSubtaskid() + "'";
        else
            sql2 = " update sendtask_acceptdept set workstate='2待重做' where id='" + bean.getSubtaskid() + "'";

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
                logger.warn( "保存派单验证信息出错1:" + ex.getMessage() );
                up.rollback();
                return false;
            }
        }
        catch( Exception ex ){
            logger.warn( "保存派单验证信息出错:" + ex.getMessage() );
            return false;
        }
    }


    /**<br>功能:获得所有验证的派单列表
      *  <br>参数:用户对象
      *  <br>返回:受理人编号和名称的List
      * */
     public List getvalidateList( UserInfo userinfo ){
         List lsend = null;
         String sql = "select s.serialnumber, s.ID,us.USERNAME sendusername,TO_CHAR(s.SENDTIME,'yyyy-MM-dd HH24:MI') sendtime,"
       				+ " 	 TO_CHAR(s.PROCESSTERM,'yyyy-MM-dd HH24:MI') processterm,s.SENDTOPIC,TO_CHAR(sr.VALIDATETIME,'yyyy-MM-dd HH24:MI') validatetime,"
					+ "       sr.VALIDATERESULT,SUBSTR(ad.WORKSTATE,2,6) workstate,s.sendtype"
					+ " from  sendtask s,userinfo us,sendtaskreply sr, sendtask_acceptdept ad "
					+ " where s.SENDUSERID = us.USERID(+) "
					//+ "      and s.REPLYID = sr.ID"
					+ "  and s.id = ad.sendtaskid and ad.id = sr.sendtaskid "
					+ "      and (ad.WORKSTATE = '9已存档' or ad.WORKSTATE = '2待重做')"
					+ "      and s.SENDDEPTID = '" + userinfo.getDeptID() + "'"
         			+ " order by ad.WORKSTATE,s.SENDTIME desc ";

   //      System.out.println( "SQL:" + sql );

         try{
             QueryUtil qu = new QueryUtil();
             lsend = qu.queryBeans( sql );
             return lsend;
         }
         catch( Exception ex ){
             logger.warn( "获得所有回复的派单列表出错:" + ex.getMessage() );
             return null;
         }
     }





     /**<br>功能:获得派单验证的详细信息
      *  <br>参数:派单编号,用户bean
      *  <br>返回: 派单bean
      * */
     public SendTaskBean getOneValidate( String id, UserInfo userinfo ){
         SendTaskBean bean = new SendTaskBean();
         ResultSet rst;
         String sql = "select s.serialnumber, sr.ID,TO_CHAR(sr.VALIDATETIME,'yyyy-MM-dd HH24:MI') validatetime,"
         		    + "       sr.VALIDATERESULT,sr.VALIDATEACCE,sr.VALIDATEREMARK,u.USERNAME"
					+ " from sendtask s, sendtask_acceptdept ad,sendtaskreply sr,userinfo u"
					//+ " where  s.REPLYID = sr.ID"
					+ " where  ad.id = sr.sendtaskid and ad.sendtaskid = s.id"
					+ "       	and sr.VALIDATEUSERID = u.USERID(+) "
					+ "			and ad.ID = '" + id + "'";

   //      System.out.println( "SQL:" + sql );
         try{
             QueryUtil qu = new QueryUtil();
             rst = qu.executeQuery( sql );
             try{
                 if( rst != null && rst.next() ){
                     bean.setReplyid(rst.getString("id"));
                     bean.setValidatetime(rst.getString("validatetime"));
                     bean.setValidateresult(rst.getString("validateresult"));
                     bean.setValidateacce(rst.getString("validateacce"));
                     bean.setValidateremark(rst.getString("validateremark"));
                     bean.setValidateusername(rst.getString("username"));
                     bean.setSerialnumber(rst.getString("serialnumber"));
                     rst.close();
                     return bean;
                 }
                 rst.close();
                 return null;
             }
             catch( Exception ex ){
                 logger.warn( "获得派单验证的详细信息出错1:" + ex.getMessage() );
                 rst.close();
                 return null;
             }

         }
         catch( Exception ex ){
             logger.warn( "获得派单验证的详细信息出错:" + ex.getMessage() );
             return null;
         }
     }


     /**
      * <br>功能:条件查找派单信息
      * <br>参数:request
      * <br>返回值:获得成功返回List,否则返回 NULL;
      */
     public List queryValiList( UserInfo userinfo, SendTaskBean bean, HttpSession session ){
         List lsend = null;
         String sql = "select s.serialnumber, s.ID,us.USERNAME sendusername,s.SENDTOPIC,TO_CHAR(sr.replytime,'yyyy-MM-dd') replytime, TO_CHAR(sr.validatetime,'yyyy-MM-dd') validatetime,"
        	 + "  case when sr.replytime + 2 < sr.validatetime then  round(to_number(sr.replytime + 2 - sr.validatetime )*24,1) "
       		+ " else 0 end processterm ,"
                   + "      SUBSTR(ad.WORKSTATE,2,6) workstate,s.sendtype , ad.id subid ,"
                   + "  NVL((select c.contractorname from contractorinfo c where c.contractorid = s.senddeptid),"
                   + " 	  (select de.deptname from deptinfo de where de.deptid = s.senddeptid))  senddeptname "
                   + " from  sendtask s,userinfo us,sendtaskreply sr , sendtask_acceptdept ad "
                   + " where s.SENDUSERID = us.USERID(+)"
                   //+ "      and s.REPLYID = sr.ID"                   
                   + "  and s.id = ad.sendtaskid "
                   + "      and ad.id = sr.sendtaskid "
                   + "      and (ad.WORKSTATE = '9已存档' or ad.WORKSTATE = '2待重做')"
                   + "      and s.SENDDEPTID = '" + userinfo.getDeptID() + "'";

         try{
//             if( bean.getSendusername() != null && !bean.getSendusername().equals( "" ) ){
//                 sql = sql + " and us.USERNAME like '" + bean.getSendusername() + "%' ";
//             }
//             if( bean.getAcceptusername() != null && !bean.getAcceptusername().equals( "" ) ){
//                 sql = sql + "  and ua.USERNAME like '" + bean.getAcceptusername() + "%'  ";
//             }
             if( bean.getSendtype() != null && !bean.getSendtype().equals( "" ) ){
                 sql = sql + "  and s.SENDTYPE like '" + bean.getSendtype() + "%'  ";
             }
             if( bean.getSendtopic() != null && !bean.getSendtopic().equals( "" ) ){
                 sql = sql + "  and s.SENDTOPIC like '" + bean.getSendtopic() + "%'  ";
             }
             if( bean.getProcessterm() != null && !bean.getProcessterm().equals( "" ) ){
                if(bean.getProcessterm().equals("超期")){
                    sql +=  " AND s.PROCESSTERM < NVL (sr.replytime,SYSDATE)";
                } else{
                    sql +=  " AND s.PROCESSTERM >= NVL (sr.replytime,SYSDATE)";
                }
            }

             if( bean.getWorkstate() != null && !bean.getWorkstate().equals( "" ) ){
                 sql = sql + "  and ad.WORKSTATE like '" + bean.getWorkstate() + "%'  ";
             }
             if( bean.getValidateresult() != null && !bean.getValidateresult().equals( "" ) ){
                 sql = sql + "  and sr.validateresult like '" + bean.getValidateresult() + "%'  ";
             }

             if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                 sql = sql + " and sr.validatetime >=TO_DATE('" + bean.getBegintime()
                       + " 00:00:00','YYYY-MM-DD HH24:MI:SS ')";
             }
             if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                 sql = sql + " and sr.validatetime <= TO_DATE('" + bean.getEndtime()
                       + " 23:59:59','YYYY-MM-DD HH24:MI:SS')";
             }
             sql = sql +  " order by sr.VALIDATETIME desc , s.SENDTIME desc";
 //            System.out.println( "SQL:" + sql );
             QueryUtil query = new QueryUtil();
             lsend = query.queryBeans( sql );
             session.setAttribute("vlQueryCon", sql);
             return lsend;
         }
         catch( Exception e ){
             logger.error( "条件查找派单信息异常:" + e.getMessage() );
             return null;
         }
     }
     
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

/**<br>功能:获得派单单位名称,编号,处理单位名称.编号 派单人名称.编号,处理人名称,编号
 *  <br>参数:用户对象
 *  <br>返回:List
 * */
public SendTaskBean getInfo(  String id,UserInfo userinfo ){
    ResultSet rst;
    SendTaskBean bean = new SendTaskBean();
    String sql = "";
    if( userinfo.getDeptype().equals( "1" ) ){
        sql = "select s.serialnumber, s.SENDDEPTID,de.deptname senddeptname,"
               + "       s.SENDUSERID,ua.USERNAME  sendusername,"
               + "       ad.deptid acceptdeptid,"
               + " NVL((select c.contractorname from contractorinfo c where c.contractorid = ad.deptid), "
               + " 	  (select de.deptname from deptinfo de where de.deptid = ad.deptid))  acceptdeptname, "
               + "       ad.useid acceptuserid,ua.USERNAME acceptusername"
               + " from sendtask s,deptinfo de,userinfo us,userinfo ua ,sendtask_acceptdept ad "
               + " where s.SENDDEPTID = de.deptid"
               + "  and s.id = ad.sendtaskid "
               + "       and s.SENDUSERID = us.USERID(+) "
               //+ "       and ad.DEPTID = con.contractorid"
               + "       and ad.USEID = ua.USERID(+) "
               + "       and s.ID = '" + id + "'";
    }
    else{
        sql = "select s.serialnumber, s.SENDDEPTID,con.CONTRACTORNAME senddeptname,"
				+ "       s.SENDUSERID,ua.USERNAME  sendusername,"
				+ "       ad.deptid acceptdeptid,"
				+ " NVL((select c.contractorname from contractorinfo c where c.contractorid = ad.deptid), "
	            + " 	  (select de.deptname from deptinfo de where de.deptid = ad.deptid))  acceptdeptname, "
				+ "       ad.useid acceptuserid,ua.USERNAME acceptusername"
				+ " from sendtask s,contractorinfo con,userinfo us,userinfo ua, sendtask_acceptdept ad"
				+ " where s.SENDDEPTID = con.CONTRACTORID"
				+ "       and s.SENDUSERID = us.USERID(+)"
				   + "  and s.id = ad.sendtaskid "
				//+ "       and ad.DEPTID = de.DEPTID"
				+ "       and ad.USEID = ua.USERID(+) "
				+ "       and s.ID = '" + id + "'";

    }
 //   System.out.println( "SQL:" + sql );

    try{
        QueryUtil qu = new QueryUtil();
        rst = qu.executeQuery(sql);
        if(rst !=null && rst.next()){
            bean.setSenddeptid(rst.getString("senddeptid"));
            bean.setSenddeptname(rst.getString("senddeptname"));
            bean.setSenduserid(rst.getString("senduserid"));
            bean.setSendusername(rst.getString("sendusername"));
            bean.setAcceptdeptid(rst.getString("acceptdeptid"));
            bean.setAcceptdeptname(rst.getString("acceptdeptname"));
            bean.setAcceptuserid(rst.getString("acceptuserid"));
            bean.setAcceptusername(rst.getString("acceptusername"));
            bean.setSerialnumber(rst.getString("serialnumber"));
            rst.close();
            return bean;
        }
        return null;
    }
    catch( Exception ex ){
        logger.warn( "获得获得派单单位名称,编号,处理单位名称.编号 派单人名称.编号,处理人名称,编号出错:" + ex.getMessage() );
        return null;
    }
}

}
