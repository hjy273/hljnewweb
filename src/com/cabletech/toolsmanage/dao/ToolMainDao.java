package com.cabletech.toolsmanage.dao;

import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.http.*;

import org.apache.log4j.*;
import com.cabletech.commons.generatorID.impl.*;
import com.cabletech.commons.hb.*;
import com.cabletech.toolsmanage.beans.*;

public class ToolMainDao{
    private ToolsInfoBean bean;
    private static Logger logger = Logger.getLogger( ToolMainDao.class.
                                   getName() );

    public ToolMainDao( ToolsInfoBean bean ){
        this.bean = bean;
    }


    public ToolMainDao(){
    }


    /**
     * <br>功能:写入备件报修信息,包括报修表,报修_备件对应表,库存表
     * <br>参数:备件信息bean,备件id数组,报修数量数组
     * <br>返回值:获得成功返回true,否则返回 false;
     */
    public boolean addMianInfo( ToolsInfoBean bean, String[] id, String[] enumber ){
        String sql1 = ""; //写入基本表的
        String sql2 = ""; //写入对应表的
        String sql3 = ""; //写入库存表的
        java.util.Date date = new java.util.Date();
        SimpleDateFormat dtFormat = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss" );
        String strDt = dtFormat.format( date );

        OracleIDImpl ora = new OracleIDImpl();
        String mainid = ora.getSeq( "toolmaintain", 10 );

        try{
            UpdateUtil exec = new UpdateUtil();
            exec.setAutoCommitFalse();
            sql1 = "insert into toolmaintain (mainid,contractorid,userid,time,mainremark,type) values ('"
                   + mainid + "','" + bean.getContractorid() + "','" + bean.getUserid() + "',"
                   + "TO_DATE('" + strDt + "','YYYY-MM-DD HH24:MI:SS'),'" + bean.getMainremark() + "','报修')";
            try{
                exec.executeUpdate( sql1 ); //写入基本表
                for( int i = 0; i < id.length; i++ ){ //将报修信息分别写入对应表和库存表
                    sql2 = "insert into toolmaintain_toolbase (mainid,id,enumber) values ('"
                           + mainid + "','" + id[i] + "'," + Integer.parseInt( enumber[i] ) + ")"; //写入对应表
                    exec.executeUpdate( sql2 );

                    sql3 = " update toolstorage set portmainnumber = portmainnumber + " + Integer.parseInt( enumber[i] )
                           + "  where contractorid='" + bean.getContractorid() + "' and id ='" + id[i] + "'";
                    exec.executeUpdate( sql3 );
                }
                exec.commit();
                exec.setAutoCommitTrue();
                return true;
            }
            catch( Exception e1 ){
                logger.error( "写入报修信息出错:" + e1.getMessage() );
                exec.rollback();
                exec.setAutoCommitTrue();
                return false;
            }
        }
        catch( Exception e ){
            logger.error( "写入备件报修信息,包括报修表,报修_备件对应表,库存表出错:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>功能:获得当前登陆代维单位的所有报修信息
     * <br>参数:request
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getAllMain( HttpServletRequest request ){
        List useinfo = null;
        String contractorid = ( String )request.getSession().getAttribute( "LOGIN_USER_DEPT_ID" );
        try{
            String sql =
                " select  tm.MAINID,tm.CONTRACTORID,TO_CHAR(tm.time,'YYYY-MM-DD HH24') time,tm.MAINREMARK,u.USERNAME "
                + " from toolmaintain tm,userinfo u "
                + " where tm.USERID=u.userid and type='报修' "
                + " and tm.CONTRACTORID='" + contractorid + "' "
                + " order by time desc";
    //        //System.out.println( "SQL:" + sql );
            QueryUtil query = new QueryUtil();
            useinfo = query.queryBeans( sql );
            return useinfo;
        }
        catch( Exception e ){
            logger.error( "在获得当前登陆代维单位的所有报修信息异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:获得指定报修单信息
     * <br>参数:报修单id
     * <br>返回值:获得成功返回对象,否则返回 NULL;
     */
    public ToolsInfoBean getOneUse( String mainid ){
        ToolsInfoBean bean = new ToolsInfoBean();
        ResultSet rst = null;

        String sql =
            " select  tm.MAINID,TO_CHAR(tm.time,'YYYY-MM-DD HH24') time,tm.MAINREMARK,u.USERNAME,con.CONTRACTORNAME "
            + " from toolmaintain tm,userinfo u,contractorinfo con  "
            + " where tm.USERID=u.userid  and con.contractorid= tm.contractorid and type='报修' "
            + " and tm.mainid='" + mainid + "' ";
        try{
            QueryUtil query = new QueryUtil();
            rst = query.executeQuery( sql );
            rst.next();
            bean.setMainid( rst.getString( "mainid" ) );
            bean.setTime( rst.getString( "time" ) );
            bean.setMainremark( rst.getString( "mainremark" ) );
            bean.setUsername( rst.getString( "username" ) );
            bean.setContractorname( rst.getString( "contractorname" ) );
            rst.close();
            return bean;
        }
        catch( Exception e ){
            logger.error( "获得指定报修单信息异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:获得指定报修单的备件信息
     * <br>参数:报修单id
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getToolsOfOneUse( String mainid ){
        List partinfo = null;
        try{
            String sql = " select tm.ID,tm.ENUMBER,tb.name,tb.type,tb.unit,tb.STYLE,tb.FACTORY "
                         + " from toolmaintain_toolbase tm,tool_base tb "
                         + " where tm.ID = tb.ID and mainid='" + mainid + "'";
            QueryUtil query = new QueryUtil();
            partinfo = query.queryBeans( sql );
            return partinfo;
        }
        catch( Exception e ){
            logger.error( "获得指定报修单的备件信息异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:修改备件报修信息,包括报修表,报修_备件对应表,库存表
     * <br>参数:备件信息bean,备件id数组,报修数量数组
     * <br>返回值:获得成功返回true,否则返回 false;
     */
    public boolean upMianInfo( ToolsInfoBean bean, String[] id, String[] enumber, String[] oldenumber ){
        String sql1 = ""; //写入基本表的
        String sql2 = ""; //写入对应表的
        String sql3 = ""; //写入库存表的
        String sql4 = ""; //删除基本表的
        java.util.Date date = new java.util.Date();
        SimpleDateFormat dtFormat = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss" );
        String strDt = dtFormat.format( date );

        try{
            UpdateUtil exec = new UpdateUtil();
            exec.setAutoCommitFalse();
            sql1 = " update toolmaintain set userid='" + bean.getUserid() + "',time = "
                   + "TO_DATE('" + strDt + "','YYYY-MM-DD HH24:MI:SS'),mainremark ='" + bean.getMainremark() + "'"
                   + " where mainid ='" + bean.getMainid() + "'";
            try{
                exec.executeUpdate( sql1 ); //更新基本表
                for( int i = 0; i < id.length; i++ ){ //更新对应表和库存表
                    if( Integer.parseInt( enumber[i] ) == 0 ){
                        sql2 = "delete from toolmaintain_toolbase where mainid='" + bean.getMainid() + "' and id='"
                               + id[i] + "'";
                    }
                    else{
                        sql2 = "update toolmaintain_toolbase set enumber = " + Integer.parseInt( enumber[i] )
                               + " where mainid='" + bean.getMainid() + "' and id='" + id[i] + "'";
                    }
                    exec.executeUpdate( sql2 );

                    sql3 = " update toolstorage set portmainnumber = portmainnumber - "
                           + Integer.parseInt( oldenumber[i] ) + " + " + Integer.parseInt( enumber[i] )
                           + "  where contractorid='" + bean.getContractorid() + "' and id ='" + id[i] + "'";
                    exec.executeUpdate( sql3 );
                }
                sql4 = "delete from toolmaintain where mainid='" + bean.getMainid()
                       + "' and 0=(select count(*) from toolmaintain_toolbase "
                       + " where mainid = '" + bean.getMainid() + "')";
                exec.executeUpdate( sql4 );
                exec.commit();
                exec.setAutoCommitTrue();
                return true;
            }
            catch( Exception e1 ){
                logger.error( "更新报修信息出错:" + e1.getMessage() );
                exec.rollback();
                exec.setAutoCommitTrue();
                return false;
            }
        }
        catch( Exception e ){
            logger.error( "更新备件报修信息,包括报修表,报修_备件对应表,库存表出错:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>功能:删除备件报修信息,包括报修表,报修_备件对应表,库存表
     * <br>参数:备件报修单编号
     * <br>返回值:获得成功返回true,否则返回 false;
     */
    public boolean deleMain( String contractorid, String mainid ){
        String sql1 = ""; //修改库存表
        String sql2 = ""; //删除对应表的
        String sql3 = ""; //删除基本表的
        String[][] shouldUp = this.getShouldUpNumber( contractorid, mainid );
        if( shouldUp == null ){
            return false;
        }

        try{
            UpdateUtil exec = new UpdateUtil();
            exec.setAutoCommitFalse();
            try{
                //更新库存表
                for( int i = 0; i < shouldUp.length; i++ ){
                    sql1 = " update toolstorage set portmainnumber=" + Integer.parseInt( shouldUp[i][1] )
                           + " where id='" + shouldUp[i][0] + "' and contractorid='" + contractorid + "'";
                    exec.executeUpdate( sql1 );
                }
                sql2 = "delete from toolmaintain_toolbase where mainid = '" + mainid + "'";
                sql3 = "delete from toolmaintain where mainid='" + mainid + "'";
                exec.executeUpdate( sql2 );
                exec.executeUpdate( sql3 );
                exec.commit();
                exec.setAutoCommitTrue();
                return true;
            }
            catch( Exception e1 ){
                logger.error( "更新报修信息出错:" + e1.getMessage() );
                exec.rollback();
                exec.setAutoCommitTrue();
                return false;
            }
        }
        catch( Exception e ){
            logger.error( "更新备件报修信息,包括报修表,报修_备件对应表,库存表出错:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>功能:获得指定单位的,备件库存表报修数量应修改的值,备件id
     * <br>参数:单位id,报修单id
     * <br>返回值:获得成功返回true,否则返回 false;
     */
    private String[][] getShouldUpNumber( String contractorid, String mainid ){
        String[][] shouldUp;
        try{
            String sql = " select  ts.ID,ts.PORTMAINNUMBER - tb.ENUMBER "
                         + " from toolstorage ts,toolmaintain_toolbase tb "
                         + " where ts.ID = tb.id and ts.CONTRACTORID ='" + contractorid + "'  and tb.MAINID='" + mainid
                         + "'";
            QueryUtil query = new QueryUtil();
            shouldUp = query.executeQueryGetArray( sql, "" );
            return shouldUp;
        }
        catch( Exception e ){
            logger.error( "获得指定单位的,备件库存表报修数量应修改的值,备件id异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能：获得指定单位报修操作人列表
     * <br>参数:指定单位的id
     * <br>返回值:获得成功返回List,否则返回 null;
     */
    public List getUserArr( String contractorid ){
        List lUser = null;
        String sql = " select distinct tm.userid,u.username "
                     + " from toolmaintain tm,userinfo u "
                     + " where tm.userid=u.userid and tm.CONTRACTORID ='" + contractorid + "' and type='报修'";

        try{
            QueryUtil query = new QueryUtil();
            lUser = query.queryBeans( sql );
            return lUser;
        }
        catch( Exception e ){
            logger.error( "获得指定单位报修操作人列表出错:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能：获得指定单位报修原因列表
     * <br>参数:指定单位的id
     * <br>返回值:获得成功返回List,否则返回 null;
     */
    public List getRemarkArr( String contractorid ){
        List lUser = null;
        String sql = " select distinct mainremark "
                     + " from toolmaintain "
                     + " where  mainremark is not null and CONTRACTORID ='" + contractorid + "' and type='报修'";

        try{
            QueryUtil query = new QueryUtil();
            lUser = query.queryBeans( sql );
            return lUser;
        }
        catch( Exception e ){
            logger.error( "获得指定单位报修原因列表出错:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:按指定条件获得当前登陆代维单位的所有报修信息
     * <br>参数:代维单位id
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getAllMainForSearch( ToolsInfoBean bean ){
        List useinfo = null;
        try{
            String sql =
                " select  tm.MAINID,tm.CONTRACTORID,TO_CHAR(tm.time,'YYYY-MM-DD HH24') time,tm.MAINREMARK,u.USERNAME "
                + " from toolmaintain tm,userinfo u "
                + " where tm.USERID=u.userid and type='报修' "
                + " and tm.CONTRACTORID='" + bean.getContractorid() + "' ";

            if( !bean.getUserid().equals( "" ) && bean.getUserid() != null ){
                sql = sql + " and tm.userid = '" + bean.getUserid() + "' ";
            }
            if( !bean.getMainremark().equals( "" ) && bean.getMainremark() != null ){
                sql = sql + " and tm.mainremark = '" + bean.getMainremark() + "'  ";
            }
            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                sql = sql + " and tm.time >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
            }
            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                sql = sql + " and tm.time <= TO_DATE('" + bean.getEndtime() + "','YYYY-MM-DD')";
            }
            sql = sql + " order by time desc";
            QueryUtil query = new QueryUtil();
            useinfo = query.queryBeans( sql );
            return useinfo;
        }
        catch( Exception e ){
            logger.error( "按指定条件获得当前登陆代维单位的所有报修信息异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能：获得指定单位报修备件列表
     * <br>参数:指定单位的id
     * <br>返回值:获得成功返回List,否则返回 null;
     */
    public List getMainToolAll( String contractorid ){
        List lMainTool = null;
        String sql = " select ts.id,ts.portmainnumber,tb.name,tb.type,tb.UNIT,tb.FACTORY,tb.STYLE,tb.TOOLUSE "
                     + " from toolstorage ts,tool_base tb "
                     + " where ts.id = tb.id and CONTRACTORID ='" + contractorid + "' and portmainnumber > 0";

        try{
            QueryUtil query = new QueryUtil();
            lMainTool = query.queryBeans( sql );
            return lMainTool;
        }
        catch( Exception e ){
            logger.error( "获得指定单位报修备件列表出错:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:获得当前登陆代维单位包含指定备件的所有报修信息
     * <br>参数:request
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getTool_Main( HttpServletRequest request ){
        List useinfo = null;
        String contractorid = ( String )request.getSession().getAttribute( "LOGIN_USER_DEPT_ID" );
        String id = request.getParameter( "id" );
        try{
            String sql =
                " select  tm.MAINID,tm.CONTRACTORID,TO_CHAR(tm.time,'YYYY-MM-DD HH24') time,tm.MAINREMARK,u.USERNAME "
                + " from toolmaintain tm,userinfo u "
                + " where tm.USERID=u.userid and type='报修' "
                + " and tm.CONTRACTORID='" + contractorid + "' "
                + " and mainid in( select mainid from toolmaintain_toolbase where id='" + id + "' "
                + "               and contractorid='" + contractorid + "')"
                + " order by time desc";
            QueryUtil query = new QueryUtil();
            useinfo = query.queryBeans( sql );
            return useinfo;
        }
        catch( Exception e ){
            logger.error( "获得当前登陆代维单位包含指定备件的所有报修异常:" + e.getMessage() );
            return null;
        }
    }


    public List getMainList( ToolsInfoBean bean ){
        List lSearchStock = null;
        List reqPart = null;
        try{
            String sql =
                " select  tm.MAINID,tm.CONTRACTORID,TO_CHAR(tm.time,'YYYY-MM-DD HH24') time,tm.MAINREMARK,u.USERNAME, con.CONTRACTORNAME "
                + " from toolmaintain tm,userinfo u, contractorinfo con "
                + " where tm.USERID=u.userid and con.contractorid = tm.contractorid and type='报修' "
                + " and tm.CONTRACTORID='" + bean.getContractorid() + "' ";

            if( !bean.getUserid().equals( "" ) && bean.getUserid() != null ){
                sql = sql + " and tm.userid = '" + bean.getUserid() + "' ";
            }
            if( !bean.getMainremark().equals( "" ) && bean.getMainremark() != null ){
                sql = sql + " and tm.mainremark = '" + bean.getMainremark() + "'  ";
            }
            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                sql = sql + " and tm.time >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
            }
            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                sql = sql + " and tm.time <= TO_DATE('" + bean.getEndtime() + "','YYYY-MM-DD')";
            }
            sql = sql + " order by time desc";

 //           //System.out.println( "query sql<<<<<<<<<<<<<<:" + sql );
            QueryUtil query = new QueryUtil();
            lSearchStock = query.queryBeans( sql );

            //获得材料信息：材料数量、入库数量、计量单位、规格型号、入库单id
            String sql2 = " select tm.mainid,tm.ID,tm.ENUMBER,tb.name,tb.type,tb.unit,tb.STYLE,tb.FACTORY "
                          + " from toolmaintain_toolbase tm,tool_base tb "
                          + " where tm.ID = tb.ID "
                          + " and mainid in ( select tm.mainid "
                          + " from toolmaintain tm,userinfo u "
                          + " where tm.USERID=u.userid and type='报修' "
                          + " and tm.CONTRACTORID='" + bean.getContractorid() + "' ";

            if( !bean.getUserid().equals( "" ) && bean.getUserid() != null ){
                sql2 = sql2 + " and tm.userid = '" + bean.getUserid() + "' ";
            }
            if( !bean.getMainremark().equals( "" ) && bean.getMainremark() != null ){
                sql2 = sql2 + " and tm.mainremark = '" + bean.getMainremark() + "'  ";
            }
            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                sql2 = sql2 + " and tm.time >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
            }
            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                sql2 = sql2 + " and tm.time <= TO_DATE('" + bean.getEndtime() + "','YYYY-MM-DD')";
            }
            sql2 += " ) ";
 //           //System.out.println( "query sql2<<<<<<<<<<<<<<:" + sql2 );
            reqPart = query.queryBeans( sql2 );

            List list = ( List )new ArrayList();
            list.add( lSearchStock );
            list.add( reqPart );

            return list;
        }
        catch( Exception e ){
            logger.error( "在按条件获得所有入库单信息发生异常:" + e.getMessage() );
            return null;
        }
    }
}
