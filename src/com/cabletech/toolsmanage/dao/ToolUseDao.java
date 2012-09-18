package com.cabletech.toolsmanage.dao;

import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.http.*;

import org.apache.log4j.*;
import com.cabletech.commons.generatorID.impl.*;
import com.cabletech.commons.hb.*;
import com.cabletech.toolsmanage.beans.*;

public class ToolUseDao{
    private ToolsInfoBean bean;
    private static Logger logger = Logger.getLogger( ToolUseDao.class.
                                   getName() );

    public ToolUseDao( ToolsInfoBean bean ){
        this.bean = bean;
    }


    public ToolUseDao(){
    }


    /**
     * <br>功能：获得指定单位库存备件的基本信息和存放数量
     * <br>参数:指定单位的id
     * <br>返回值:获得成功返回List,否则返回 null;
     */
    public List getToolInfo( String contractorid ){
        List lInfo = null;
        String sql = "select distinct st.ID,st.ESSENUMBER,b.NAME,b.TYPE,b.UNIT "
                     + " from toolstorage st,tool_base b "
                     + " where st.ID = b.ID and st.CONTRACTORID ='" + contractorid + "'";
        try{
            QueryUtil query = new QueryUtil();
            lInfo = query.queryBeans( sql );
            return lInfo;
        }
        catch( Exception e ){
            logger.error( "在获得指定单位库存备件的基本信息和存放数量中出错:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:写入备件领用信息,包括领用表,领用_备件对应表,库存表
     * <br>参数:备件信息bean,备件id数组,领用数量数组
     * <br>返回值:获得成功返回true,否则返回 false;
     */
    public boolean addUseInfo( ToolsInfoBean bean, String[] id, String[] enumber ){
        String sql1 = ""; //写入基本表的
        String sql2 = ""; //写入对应表的
        String sql3 = ""; //写入库存表的
        java.util.Date date = new java.util.Date();
        SimpleDateFormat dtFormat = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss" );
        String strDt = dtFormat.format( date );

        OracleIDImpl ora = new OracleIDImpl();
        String useid = ora.getSeq( "tooluse", 10 );

        try{
            UpdateUtil exec = new UpdateUtil();
            exec.setAutoCommitFalse();
            sql1 = "insert into tooluse (useid,contractorid,userid,time,usename,useunit,useremark) values ('"
                   + useid + "','" + bean.getContractorid() + "','" + bean.getUserid() + "',"
                   + "TO_DATE('" + strDt + "','YYYY-MM-DD HH24:MI:SS'),'" + bean.getUsename() + "','" + bean.getUseunit()
                   + "','"
                   + bean.getUseremark() + "')";
            try{
                exec.executeUpdate( sql1 ); //写入基本表
                for( int i = 0; i < id.length; i++ ){ //将领用信息分别写入对应表和库存表
                    sql2 = "insert into tooluse_toolbase (useid,id,enumber,bnumber) values ('"
                           + useid + "','" + id[i] + "'," + Integer.parseInt( enumber[i] ) + "," + "0 )"; //写入对应表
                    exec.executeUpdate( sql2 );

                    sql3 = " update toolstorage set essenumber = essenumber - " + Integer.parseInt( enumber[i] )
                           + "  where contractorid='" + bean.getContractorid() + "' and id ='" + id[i] + "'";
                    exec.executeUpdate( sql3 );
                }
                exec.commit();
                exec.setAutoCommitTrue();
                return true;
            }
            catch( Exception e1 ){
                logger.error( "写入领用信息出错:" + e1.getMessage() );
                exec.rollback();
                exec.setAutoCommitTrue();
                return false;
            }
        }
        catch( Exception e ){
            logger.error( "写入备件领用信息,包括领用表,领用_备件对应表,库存表中出错:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>功能:获得当前登陆代维单位的所有领用信息
     * <br>参数:request
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getAllUse( HttpServletRequest request ){
        List useinfo = null;
        String contractorid = ( String )request.getSession().getAttribute( "LOGIN_USER_DEPT_ID" );
        try{
            String sql = "select distinct use.useid,use.useunit,con.CONTRACTORNAME,u.username,use.USENAME,TO_CHAR(use.time,'YYYY-MM-DD HH24') time,use.USEREMARK "
                         + " from tooluse use,contractorinfo con,userinfo u "
                         + " where use.USERID = u.USERID and use.CONTRACTORID = con.CONTRACTORID "
                         + " and use.CONTRACTORID='" + contractorid + "' "
                         + " order by time desc";
            QueryUtil query = new QueryUtil();
            useinfo = query.queryBeans( sql );
            return useinfo;
        }
        catch( Exception e ){
            logger.error( "在获得当前登陆代维单位的所有领用信息中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:获得指定领用单信息
     * <br>参数:领用单id
     * <br>返回值:获得成功返回对象,否则返回 NULL;
     */
    public ToolsInfoBean getOneUse( String useid ){
        ToolsInfoBean bean = new ToolsInfoBean();
        ResultSet rst = null;

        String sql = "select use.useid,u.USERNAME, con.CONTRACTORNAME,TO_CHAR(use.time,'YYYY-MM-DD HH24') time,use.usename,use.useunit,use.USEREMARK "
                     + " from tooluse use,contractorinfo con,userinfo u "
                     + " where use.USERID = u.USERID and use.CONTRACTORID = con.CONTRACTORID "
                     + " and use.useid='" + useid + "' ";
        try{
            QueryUtil query = new QueryUtil();
            rst = query.executeQuery( sql );
            rst.next();
            bean.setUseid( rst.getString( "useid" ) );
            bean.setUsername( rst.getString( "username" ) );
            bean.setContractorname( rst.getString( "contractorname" ) );
            bean.setTime( rst.getString( "time" ) );
            bean.setUsename( rst.getString( "usename" ) );
            bean.setUseunit( rst.getString( "useunit" ) );
            bean.setUseremark( rst.getString( "useremark" ) );
            rst.close();
            return bean;
        }
        catch( Exception e ){
            logger.error( "在获得指定领用单信息中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:获得指定领用单的备件信息
     * <br>参数:领用单id
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getToolsOfOneUse( String useid ){
        List partinfo = null;
        try{
            String sql = " select use.ID,use.USEID,use.ENUMBER,use.bnumber,b.NAME,b.UNIT,b.TYPE "
                         + " from tooluse_toolbase use,tool_base b "
                         + " where use.id = b.id and useid='" + useid + "'";
            QueryUtil query = new QueryUtil();
            partinfo = query.queryBeans( sql );
            return partinfo;
        }
        catch( Exception e ){
            logger.error( "在获得指定领用单的备件信息中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能：获得指定单位领用操作人列表
     * <br>参数:指定单位的id
     * <br>返回值:获得成功返回List,否则返回 null;
     */
    public List getUserArr( String contractorid ){
        List lUser = null;
        String sql = " select distinct use.userid,u.username "
                     + " from tooluse use,userinfo u "
                     + " where use.userid=u.userid and use.CONTRACTORID ='" + contractorid + "'";

        try{
            QueryUtil query = new QueryUtil();
            lUser = query.queryBeans( sql );
            return lUser;
        }
        catch( Exception e ){
            logger.error( "在获得指定单位领用操作人列表中出错:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能：获得指定单位的备件领用人列表
     * <br>参数:指定单位的id
     * <br>返回值:获得成功返回List,否则返回 null;
     */
    public List getUseNameArr( String contractorid ){
        List lUser = null;
        String sql = " select distinct use.usename "
                     + " from tooluse use "
                     + " where use.usename is not null and use.CONTRACTORID ='" + contractorid + "'";

        try{
            QueryUtil query = new QueryUtil();
            lUser = query.queryBeans( sql );
            return lUser;
        }
        catch( Exception e ){
            logger.error( "在获得指定单位的备件领用人列表中出错:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能：获得指定单位的备件领用单位列表
     * <br>参数:指定单位的id
     * <br>返回值:获得成功返回List,否则返回 null;
     */
    public List getUseUnitArr( String contractorid ){
        List lUser = null;
        String sql = " select distinct use.useunit "
                     + " from tooluse use "
                     + " where use.useunit is not null and use.CONTRACTORID ='" + contractorid + "'";

        try{
            QueryUtil query = new QueryUtil();
            lUser = query.queryBeans( sql );
            return lUser;
        }
        catch( Exception e ){
            logger.error( "在获得指定单位的备件领用单位列表中出错:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能：获得指定单位的备件领用原因列表
     * <br>参数:指定单位的id
     * <br>返回值:获得成功返回List,否则返回 null;
     */
    public List getUseRemarkArr( String contractorid ){
        List lUser = null;
        String sql = " select distinct use.useremark "
                     + " from tooluse use "
                     + " where use.useremark is not null and  use.CONTRACTORID ='" + contractorid + "'";

        try{
            QueryUtil query = new QueryUtil();
            lUser = query.queryBeans( sql );
            return lUser;
        }
        catch( Exception e ){
            logger.error( "在获得指定单位的备件领用原因列表中出错:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:按指定条件获得当前登陆代维单位的所有领用信息
     * <br>参数:代维单位id
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getAllUseForSearch( ToolsInfoBean bean ){
        List useinfo = null;
        try{
            String sql = "select distinct use.useid,use.useunit,con.CONTRACTORNAME,u.username,use.USENAME,TO_CHAR(use.time,'YYYY-MM-DD HH24') time,use.USEREMARK "
                         + " from tooluse use,contractorinfo con,userinfo u "
                         + " where use.USERID = u.USERID and use.CONTRACTORID = con.CONTRACTORID "
                         + " and use.CONTRACTORID='" + bean.getContractorid() + "' ";
            if( !bean.getUserid().equals( "" ) && bean.getUserid() != null ){
                sql = sql + " and use.userid = '" + bean.getUserid() + "' ";
            }
            if( !bean.getUseunit().equals( "" ) && bean.getUseunit() != null ){
                sql = sql + " and use.useunit= '" + bean.getUseunit() + "'  ";
            }
            if( !bean.getUsename().equals( "" ) && bean.getUsename() != null ){
                sql = sql + " and use.usename = '" + bean.getUsename() + "'  ";
            }
            if( !bean.getUseremark().equals( "" ) && bean.getUseremark() != null ){
                sql = sql + " and use.useremark = '" + bean.getUseremark() + "'  ";
            }
            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                sql = sql + " and use.time >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
            }
            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                sql = sql + " and use.time <= TO_DATE('" + bean.getEndtime() + "','YYYY-MM-DD')";
            }
            if( !bean.getBack().equals( "" ) && bean.getBack() != null ){
                if( bean.getBack().equals( "1" ) ){ //全部归还
                    sql = sql + " and use.useid in(select distinct useid from tooluse_toolbase where enumber=bnumber)";
                }
                if( bean.getBack().equals( "2" ) ){ //尚未归还
                    sql = sql + " and use.useid in(select distinct useid from tooluse_toolbase where enumber>bnumber)";
                }
            }
            sql = sql + " order by  time desc ";
            logger.info("sql:"+sql);
            QueryUtil query = new QueryUtil();
            useinfo = query.queryBeans( sql );
            return useinfo;
        }
        catch( Exception e ){
            logger.error( "在获得当前登陆代维单位的所有出库信息中发生异常:" + e.getMessage() );
            return null;
        }
    }


    ////////////////////////////////////
    /**
     * <br>功能:按指定条件获得当前登陆代维单位的所有待归还领用单信息
     * <br>参数:代维单位id
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getAllUseForBack( ToolsInfoBean bean ){
        List useinfo = null;
        try{
            String sql = "select distinct use.useid,use.useunit,con.CONTRACTORNAME,u.username,use.USENAME,TO_CHAR(use.time,'YYYY-MM-DD HH24') time,use.USEREMARK "
                         + " from tooluse use,contractorinfo con,userinfo u "
                         + " where use.USERID = u.USERID and use.CONTRACTORID = con.CONTRACTORID "
                         + " and use.CONTRACTORID='" + bean.getContractorid() + "' ";
            if( !bean.getUserid().equals( "" ) && bean.getUserid() != null ){
                sql = sql + " and use.userid = '" + bean.getUserid() + "' ";
            }
            if( !bean.getUseunit().equals( "" ) && bean.getUseunit() != null ){
                sql = sql + " and use.useunit= '" + bean.getUseunit() + "'  ";
            }
            if( !bean.getUsename().equals( "" ) && bean.getUsename() != null ){
                sql = sql + " and use.usename = '" + bean.getUsename() + "'  ";
            }
            if( !bean.getUseremark().equals( "" ) && bean.getUseremark() != null ){
                sql = sql + " and use.useremark = '" + bean.getUseremark() + "'  ";
            }
            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                sql = sql + " and use.time >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
            }
            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                sql = sql + " and use.time <= TO_DATE('" + bean.getEndtime() + "','YYYY-MM-DD')";
            }
            sql = sql + " and use.useid in(select distinct useid from tooluse_toolbase where enumber>bnumber)";
            sql = sql + " order by  time desc ";
            logger.info("sql:"+sql);
            QueryUtil query = new QueryUtil();
            useinfo = query.queryBeans( sql );
            return useinfo;
        }
        catch( Exception e ){
            logger.error( "在按指定条件获得当前登陆代维单位的所有待归还领用单信息中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:写入备件返还用信息,包括领用表,领用_备件对应表,库存表
     * <br>参数:备件信息bean,备件id数组,返还数量数组
     * <br>返回值:获得成功返回true,否则返回 false;
     */
    public boolean addBackInfo( ToolsInfoBean bean, String[] id, String[] bnumber ){
        String sql1 = ""; //写入基本表的
        String sql2 = ""; //写入对应表的
        String sql3 = ""; //写入库存表的
        java.util.Date date = new java.util.Date();
        SimpleDateFormat dtFormat = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss" );
        String strDt = dtFormat.format( date );
        try{
            UpdateUtil exec = new UpdateUtil();
            exec.setAutoCommitFalse();
            sql1 = "update tooluse set buserid='" + bean.getUserid() + "',bname='" + bean.getBname() + "',bunit='"
                   + bean.getBunit()
                   + "',bremark='" + bean.getBremark() + "',btime=TO_DATE('" + strDt + "','YYYY-MM-DD HH24:MI:SS') "
                   + " where useid='" + bean.getUseid() + "'";
            try{
                exec.executeUpdate( sql1 ); //写入基本表
                for( int i = 0; i < id.length; i++ ){ //将返还信息分别写入对应表和库存表
                    sql2 = "update tooluse_toolbase set bnumber= bnumber +" + Integer.parseInt( bnumber[i] )
                           + " where useid='" + bean.getUseid() + "' and id='" + id[i] + "'"; //写入对应表
                    exec.executeUpdate( sql2 );

                    sql3 = " update toolstorage set essenumber = essenumber + " + Integer.parseInt( bnumber[i] )
                           + "  where contractorid='" + bean.getContractorid() + "' and id ='" + id[i] + "'";
                    exec.executeUpdate( sql3 );
                }
                exec.commit();
                exec.setAutoCommitTrue();
                return true;
            }
            catch( Exception e1 ){
                logger.error( "写入返还信息出错:" + e1.getMessage() );
                exec.rollback();
                exec.setAutoCommitTrue();
                return false;
            }
        }
        catch( Exception e ){
            logger.error( "写入写入备件返还用信息,包括领用表,领用_备件对应表,库存表中出错:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>功能：获得指定单位的待返还备件列表
     * <br>参数:指定单位的id
     * <br>返回值:获得成功返回List,否则返回 null;
     */
    public List getToolForShouldBack( String contractorid ){
        List lUser = null;
        String sql =
            "select s.id,b.name,b.unit,b.type,b.STYLE,b.type,b.TOOLUSE,(s.SHOULDNUMBER - s.ESSENUMBER) enumber "
            + " from toolstorage s,tool_base b "
            + " where s.id=b.id and s.SHOULDNUMBER>s.ESSENUMBER and s.CONTRACTORID ='" + contractorid + "'";

        try{
            QueryUtil query = new QueryUtil();
            lUser = query.queryBeans( sql );
            return lUser;
        }
        catch( Exception e ){
            logger.error( "获得指定单位的待返还备件列表出错:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能：根据待返还备件编号查找所涉及的领用单
     * <br>参数:指定单位的id
     * <br>返回值:获得成功返回List,否则返回 null;
     */
    public List getAllShouldBackUse( ToolsInfoBean bean ){
        List lUser = null;
        String sql =
            " select u.username,tb.useid,tb.useunit,tb.usename,TO_CHAR(tb.time,'YYYY-MM-DD HH24') time,tb.useremark "
            + " from tooluse tb,userinfo u "
            + " where tb.userid = u.userid  and tb.contractorid='" + bean.getContractorid() + "' and  useid in("
            + "        select distinct useid from tooluse_toolbase where enumber>bnumber and id ='" + bean.getId()
            + "')";
        try{
            QueryUtil query = new QueryUtil();
            lUser = query.queryBeans( sql );
            return lUser;
        }
        catch( Exception e ){
            logger.error( "根据待返还备件编号查找所涉及的领用单:" + e.getMessage() );
            return null;
        }
    }

    public List getUseList( ToolsInfoBean bean ){
        List lSearchStock = null;
        List reqPart = null;
        try{
            String sql = "select distinct use.useid,use.useunit,con.CONTRACTORNAME,u.username,use.USENAME,TO_CHAR(use.time,'YYYY-MM-DD HH24') time,use.USEREMARK "
                         + " from tooluse use,contractorinfo con,userinfo u "
                         + " where use.USERID = u.USERID and use.CONTRACTORID = con.CONTRACTORID "
                         + " and use.CONTRACTORID='" + bean.getContractorid() + "' ";
            if( !bean.getUserid().equals( "" ) && bean.getUserid() != null ){
                sql = sql + " and use.userid = '" + bean.getUserid() + "' ";
            }
            if( !bean.getUseunit().equals( "" ) && bean.getUseunit() != null ){
                sql = sql + " and use.useunit= '" + bean.getUseunit() + "'  ";
            }
            if( !bean.getUsename().equals( "" ) && bean.getUsename() != null ){
                sql = sql + " and use.usename = '" + bean.getUsename() + "'  ";
            }
            if( !bean.getUseremark().equals( "" ) && bean.getUseremark() != null ){
                sql = sql + " and use.useremark = '" + bean.getUseremark() + "'  ";
            }
            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                sql = sql + " and use.time >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
            }
            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                sql = sql + " and use.time <= TO_DATE('" + bean.getEndtime() + "','YYYY-MM-DD')";
            }
            if( !bean.getBack().equals( "" ) && bean.getBack() != null ){
                if( bean.getBack().equals( "1" ) ){ //全部归还
                    sql = sql + " and use.useid in(select distinct useid from tooluse_toolbase where enumber=bnumber)";
                }
                if( bean.getBack().equals( "2" ) ){ //尚未归还
                    sql = sql + " and use.useid in(select distinct useid from tooluse_toolbase where enumber>bnumber)";
                }
            }
            sql = sql + " order by  time desc ";

      //      //System.out.println( "query sql<<<<<<<<<<<<<<:" + sql );
            QueryUtil query = new QueryUtil();
            lSearchStock = query.queryBeans( sql );

            //获得材料信息：材料数量、入库数量、计量单位、规格型号、入库单id
            String sql2 = " select use.ID,use.USEID,use.ENUMBER,use.bnumber,b.NAME,b.UNIT,b.TYPE "
                         + " from tooluse_toolbase use,tool_base b "
                         + " where use.id = b.id "
                         + " and use.useid in ( select use.useid   "
                         + " from tooluse use,contractorinfo con,userinfo u "
                            + " where use.USERID = u.USERID and use.CONTRACTORID = con.CONTRACTORID "
                            + " and use.CONTRACTORID='" + bean.getContractorid() + "' ";
               if( !bean.getUserid().equals( "" ) && bean.getUserid() != null ){
                   sql2 = sql2 + " and use.userid = '" + bean.getUserid() + "' ";
               }
               if( !bean.getUseunit().equals( "" ) && bean.getUseunit() != null ){
                   sql2 = sql2 + " and use.useunit= '" + bean.getUseunit() + "'  ";
               }
               if( !bean.getUsename().equals( "" ) && bean.getUsename() != null ){
                   sql2 = sql2 + " and use.usename = '" + bean.getUsename() + "'  ";
               }
               if( !bean.getUseremark().equals( "" ) && bean.getUseremark() != null ){
                   sql2 = sql2 + " and use.useremark = '" + bean.getUseremark() + "'  ";
               }
               if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                   sql2 = sql2 + " and use.time >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
               }
               if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                   sql2 = sql2 + " and use.time <= TO_DATE('" + bean.getEndtime() + "','YYYY-MM-DD')";
               }
               if( !bean.getBack().equals( "" ) && bean.getBack() != null ){
                   if( bean.getBack().equals( "1" ) ){ //全部归还
                       sql2 = sql2 + " and use.useid in(select distinct useid from tooluse_toolbase where enumber=bnumber)";
                   }
                   if( bean.getBack().equals( "2" ) ){ //尚未归还
                       sql2 = sql2 + " and use.useid in(select distinct useid from tooluse_toolbase where enumber>bnumber)";
                   }
            }
            sql2 += " ) ";
    //        //System.out.println( "query sql2<<<<<<<<<<<<<<:" + sql2 );
            reqPart = query.queryBeans( sql2 );

            List list = (List)new ArrayList() ;
            list.add(lSearchStock);
            list.add(reqPart);

            return list;
        }
        catch( Exception e ){
            logger.error( "在按条件获得所有入库单信息发生异常:" + e.getMessage() );
            return null;
        }
    }
//    getAllShouldBackUse( bean )
}
