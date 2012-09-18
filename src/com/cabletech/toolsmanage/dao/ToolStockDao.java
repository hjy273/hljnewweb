package com.cabletech.toolsmanage.dao;

import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.http.*;

import org.apache.log4j.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.generatorID.impl.*;
import com.cabletech.commons.hb.*;
import com.cabletech.toolsmanage.beans.*;

public class ToolStockDao{
    private static Logger logger = Logger.getLogger( ToolStockDao.class.
                                   getName() );
    public ToolStockDao(){
    }


    /**
     * <br>功能：获得备件基本表的id,name,type,unit信息
     * <br>参数:无
     * <br>返回值:获得成功返回List,否则返回 null;
     */
    public List getAllInfo( String regionid ){
        List lInfo = null;
        String sql = "select id,name,type,unit from tool_base where state is null and regionid='" + regionid
                     + "' order  by name";
        try{
            QueryUtil query = new QueryUtil();
            lInfo = query.queryBeans( sql );
            return lInfo;
        }
        catch( Exception e ){
            logger.error( "在获得备件基本表的id,name,type,unit信息中出错:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能：获得所有仓库保管人的id,name信息
     * <br>参数:无
     * <br>返回值:获得成功返回List,否则返回 null;
     */
    public List getAllpatrolid( String contractorid ){
        List lInfo = null;
        String sql = "select patrolid,patrolname from patrolman_son_info "
                     + " where ParentID = '" + contractorid + "'"
                     + " order by patrolname";
        try{
            QueryUtil query = new QueryUtil();
            lInfo = query.queryBeans( sql );
            return lInfo;
        }
        catch( Exception e ){
            logger.error( "获得所有仓库保管人的id,name信息中出错:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:写入备件入库基本表,入库对应表,库存表
     * <br>参数:
     * <br>返回值:获得成功返回true,否则返回 false;
     */
    public boolean doAddStock( ToolsInfoBean bean, String[] id, String[] enumber, String regionid ){
        OracleIDImpl ora = new OracleIDImpl();
        String stockid = ora.getSeq( "toolstock", 10 );

        java.util.Date date = new java.util.Date();
        SimpleDateFormat dtFormat = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss" );
        String strDt = dtFormat.format( date );

        String sql1 = ""; //写入基本表的
        String sql2 = ""; //写入对应表的
        String sql3 = ""; //写入库存表的

        try{
            UpdateUtil exec = new UpdateUtil();
            exec.setAutoCommitFalse();
            sql1 = "insert into toolstock (stockid,userid,contractorid,time,adress,patrolid,remark,type) values ('"
                   + stockid + "','" + bean.getUserid() + "','" + bean.getContractorid() + "'," + "TO_DATE('" + strDt
                   + "','YYYY-MM-DD HH24:MI:SS'),'"
                   + bean.getAdress() + "','" + bean.getPatrolid() + "','" + bean.getRemark() + "','入库')";
            try{
                exec.executeUpdate( sql1 ); //写入基本表
                for( int i = 0; i < id.length; i++ ){ //将信息分别写入对应表和库存表
                    sql2 = "insert into toolstock_toolbase (stockid,id,enumber) values ('"
                           + stockid + "','" + id[i] + "'," + Integer.parseInt( enumber[i] ) + ")";
                    exec.executeUpdate( sql2 );
                    if( true == this.valiToolStorageExist( bean.getContractorid(), id[i] ) ){
                        sql3 = "update toolstorage set essenumber = essenumber + " + Integer.parseInt( enumber[i] )
                               + ",shouldnumber=shouldnumber+" + Integer.parseInt( enumber[i] )
                               + " where id='" + id[i] + "' and contractorid='" + bean.getContractorid() + "'";
                    }
                    else{
                        sql3 =
                            "insert into toolstorage (id,contractorid,essenumber,shouldnumber,portmainnumber,mainnumber,regionid) values ('"
                            + id[i] + "','" + bean.getContractorid() + "'," + Integer.parseInt( enumber[i] ) + ","
                            + Integer.parseInt( enumber[i] ) + ",0,0,'" + regionid + "')";
                    }
                    exec.executeUpdate( sql3 );
                }
                exec.commit();
                exec.setAutoCommitTrue();
                return true;
            }
            catch( Exception e1 ){
                logger.error( "写入入库对应表,库存表出错:" + e1.getMessage() );
                exec.rollback();
                exec.setAutoCommitTrue();
                return false;
            }
        }
        catch( Exception e ){
            logger.error( "写入写入备件入库基本表,入库对应表,库存表出错:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>功能：检查备件库存中指定单位/指定备件的记录是否存在,
     * <br>参数:备件id,代维单位contractorid,
     * <br>返回值:对应记录存在返回true,否则返回 false;
     */
    private boolean valiToolStorageExist( String contractorid, String id ){

        ResultSet rst = null;
        String sql = "select count(*) aa from  toolstorage"
                     + " where id='" + id + "' and contractorid='" + contractorid + "'";
        try{
            QueryUtil excu = new QueryUtil();
            rst = excu.executeQuery( sql );
            rst.next();
            if( rst.getInt( "aa" ) > 0 ){
                rst.close();
                return true;
            }
            else{
                rst.close();
                return false;
            }
        }
        catch( Exception e ){
            logger.error( "在检查备件库存中指定单位/指定备件的记录是否存在出错:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>功能:获得当前登陆代维单位的所有备件入库单信息
     * <br>参数:request
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getAllStock( HttpServletRequest request ){
        List reqinfo = null;
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        String contractorid = ( String )userinfo.getDeptID();
        try{
            //获得入库人姓名,入库时间,入库单位,保管人,存放地点
            String sql =
                "select st.stockid,use.USERNAME,TO_CHAR(st.TIME,'YYYY-MM-DD HH24') time,con.CONTRACTORNAME, st.PATROLID,st.ADRESS "
                + " from toolstock st,userinfo use,contractorinfo con  "
                +
                " where st.CONTRACTORID=con.CONTRACTORID and st.USERID = use.USERID  and type='入库' and st.contractorid='"
                + contractorid + "' "
                + " order by time desc";
            ////System.out.println("SQL:" + sql);
            QueryUtil query = new QueryUtil();
            reqinfo = query.queryBeans( sql );
            return reqinfo;
        }
        catch( Exception e ){
            logger.error( "在获得当前登陆代维单位的所有备件入库单信息发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:获得指定入库单信息
     * <br>参数:入库单id
     * <br>返回值:获得成功返回申请单对象,否则返回 NULL;
     **/
    public ToolsInfoBean getOneStock( String stockid ){

        ToolsInfoBean bean = new ToolsInfoBean();
        ResultSet rst = null;
        try{
            //获得入库人,入库单位,入库时间,存放地点,保管人,备注信息,
            String sql = "select st.stockid,use.USERNAME,st.patrolid patrolname,TO_CHAR(st.TIME,'YYYY-MM-DD HH24') time,con.CONTRACTORNAME,st.ADRESS,st.remark "
                         + " from toolstock st,userinfo use,contractorinfo con "
                         +
                         " where st.CONTRACTORID=con.CONTRACTORID and st.USERID = use.USERID  and type='入库'  and stockid='"
                         + stockid + "'";
            ////System.out.println("SQL:" + sql);
            QueryUtil query = new QueryUtil();
            rst = query.executeQuery( sql );
            rst.next();
            bean.setUseid( rst.getString( "username" ) );
            bean.setStockid( rst.getString( "stockid" ) );
            bean.setTime( rst.getString( "time" ) );
            bean.setFactory( rst.getString( "contractorname" ) );
            bean.setPatrolname( rst.getString( "patrolname" ) );
            bean.setAdress( rst.getString( "adress" ) );
            bean.setRemark( rst.getString( "remark" ) );
            rst.close();
            return bean;
        }
        catch( Exception e ){
            logger.error( "在获得指定入库单信息中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:获得指定入库单的所有入库备件信息
     * <br>参数:入库单id
     * <br>返回值:获得成功返回List,否则返回 NULL;
     **/
    public List getStockTools( String stockid ){
        List reqPart = null;
        try{
            String sql = "select b.ID,b.NAME,b.UNIT,b.TYPE,st.ENUMBER "
                         + " from toolstock_toolbase st,tool_base b "
                         + " where st.ID = b.id  and st.stockid='" + stockid + "'";
            QueryUtil query = new QueryUtil();
            reqPart = query.queryBeans( sql );
            return reqPart;
        }
        catch( Exception e ){
            logger.error( "在获得指定入库单的所有入库备件信息中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:获得指定代维单位的入库人列表
     * <br>参数:代维id
     * <br>返回值:获得成功返回List,否则返回 NULL;
     **/
    public List getAllUsers( String contractorid ){
        List reqPart = null;
        try{
            String sql = "select distinct st.userid,us.username "
                         + " from toolstock st,userinfo us "
                         + " where st.USERID = us.USERID  and type = '入库' and contractorid='" + contractorid + "'";
            QueryUtil query = new QueryUtil();
            reqPart = query.queryBeans( sql );
            return reqPart;
        }
        catch( Exception e ){
            logger.error( "在获得指定代维单位的入库人列表中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:获得指定代维单位的存放地点列表
     * <br>参数:代维id
     * <br>返回值:获得成功返回List,否则返回 NULL;
     **/
    public List getAllAdress( String contractorid ){
        List reqPart = null;
        try{
            String sql = "select distinct adress "
                         + " from toolstock "
                         + " where type = '入库'  and adress is not null and contractorid='" + contractorid + "'";
            QueryUtil query = new QueryUtil();
            reqPart = query.queryBeans( sql );
            return reqPart;
        }
        catch( Exception e ){
            logger.error( "在获得指定代维单位的存放地点列表中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:获得指定代维单位的保管人列表
     * <br>参数:代维id
     * <br>返回值:获得成功返回List,否则返回 NULL;
     **/
    public List getAllPatrid( String contractorid ){
        List reqPart = null;
        try{
            String sql = " select distinct st.patrolid "
                         + " from toolstock st "
                         + " where st.patrolid is not null"
                         + " and  type = '入库' and contractorid='" + contractorid + "'";
            QueryUtil query = new QueryUtil();
            reqPart = query.queryBeans( sql );
            return reqPart;
        }
        catch( Exception e ){
            logger.error( "在获得指定代维单位的保管人列表中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:综合查询,条件查询,
     * <br>参数:指定代维单位的id
     * <br>返回值:获得成功返回List,否则返回 NULL;
     **/
    public List doSearchStock( ToolsInfoBean bean ){
        List lSearchStock = null;
        String sql =
            "select st.stockid,use.USERNAME,TO_CHAR(st.TIME,'YYYY-MM-DD HH24') time,con.CONTRACTORNAME, st.PATROLID,st.ADRESS "
            + " from toolstock st,userinfo use,contractorinfo con  "
            +
            " where st.CONTRACTORID=con.CONTRACTORID and st.USERID = use.USERID  and type='入库' and st.contractorid='"
            + bean.getContractorid() + "' ";

        /**
         "select st.stockid,use.USERNAME,TO_CHAR(st.TIME,'YYYY-MM-DD HH24') time,con.CONTRACTORNAME,patr.PATROLNAME,st.ADRESS "
         + " from toolstock st,userinfo use,contractorinfo con,patrolman_son_info patr "
         +
         " where st.CONTRACTORID=con.CONTRACTORID and st.USERID = use.USERID and st.PATROLID = patr.PATROLID and type='入库' "
         + " and st.contractorid='" + bean.getContractorid() + "' ";*/

        if( bean.getUserid() != null && !bean.getUserid().equals( "" ) ){
            sql = sql + " and st.userid ='" + bean.getUserid() + "' ";
        }
        if( bean.getAdress() != null && !bean.getAdress().equals( "" ) ){
            sql = sql + "  and st.adress='" + bean.getAdress() + "'  ";
        }
        if( bean.getPatrolid() != null && !bean.getPatrolid().equals( "" ) ){
            sql = sql + "  and st.patrolid='" + bean.getPatrolid() + "'  ";
        }
        if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
            sql = sql + " and st.time >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
        }
        if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
            sql = sql + " and st.time <= TO_DATE('" + bean.getEndtime() + "','YYYY-MM-DD')";
        }
        sql = sql + "  order by time desc";
        // //System.out.println("SQL:" + sql);
        try{
            QueryUtil query = new QueryUtil();
            lSearchStock = query.queryBeans( sql );
            return lSearchStock;
        }
        catch( Exception e ){
            logger.error( "综合查询,条件查询:" + e.getMessage() );
            return null;
        }
    }


    //////////////////////////以下是报废操作//////////////////////////////////////////////////////////////////
    /**
     * <br>功能：获得库存表中指定代维单位备件id,name,type,unit,essenumber,shouldnumber,portmainnumber,mainnumber信息
     * <br>参数:无
     * <br>返回值:获得成功返回List,否则返回 null;
     */
    public List getAllInfoByCon( String contractorid ){
        List lInfo = null;
        String sql =
            " select  st.ESSENUMBER,st.SHOULDNUMBER,st.PORTMAINNUMBER,st.MAINNUMBER,b.ID,b.NAME,b.TYPE,b.UNIT "
            + " from toolstorage st,tool_base b "
            + " where st.ID=b.id and st.contractorid='" + contractorid + "'";
        try{
            QueryUtil query = new QueryUtil();
            lInfo = query.queryBeans( sql );
            return lInfo;
        }
        catch( Exception e ){
            logger.error( "在获得库存表中指定代维单位备件id,name,type,unit,essenumber,shouldnumber,portmainnumber,mainnumber信息中出错:"
                + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:写入备件入库报废基本表,入库报废对应表,库存表
     * <br>参数:
     * <br>返回值:获得成功返回true,否则返回 false;
     */
    public boolean doAddRevoke( ToolsInfoBean bean, String[] id, String[] enumber ){
        OracleIDImpl ora = new OracleIDImpl();
        String stockid = ora.getSeq( "toolstock", 10 );

        java.util.Date date = new java.util.Date();
        SimpleDateFormat dtFormat = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss" );
        String strDt = dtFormat.format( date );

        String sql1 = ""; //写入基本表的
        String sql2 = ""; //写入对应表的
        String sql3 = ""; //写入库存表的

        try{
            UpdateUtil exec = new UpdateUtil();
            exec.setAutoCommitFalse();
            sql1 = "insert into toolstock (stockid,userid,contractorid,time,remark,type) values ('"
                   + stockid + "','" + bean.getUserid() + "','" + bean.getContractorid() + "',"
                   + "TO_DATE('" + strDt + "','YYYY-MM-DD HH24:MI:SS'),'" + bean.getRemark() + "','报废')";
            try{
                exec.executeUpdate( sql1 ); //写入基本表
                for( int i = 0; i < id.length; i++ ){ //将信息分别写入对应表和库存表
                    sql2 = "insert into toolstock_toolbase (stockid,id,enumber) values ('"
                           + stockid + "','" + id[i] + "'," + Integer.parseInt( enumber[i] ) + ")";
                    exec.executeUpdate( sql2 );
                    if( true == this.valiToolStorageExist( bean.getContractorid(), id[i] ) ){
                        sql3 = "update toolstorage set essenumber = essenumber -" + Integer.parseInt( enumber[i] )
                               + ",shouldnumber=shouldnumber-" + Integer.parseInt( enumber[i] )
                               + " where id='" + id[i] + "' and contractorid='" + bean.getContractorid() + "'";
                    }
                    else{
                        return false;
                    }
                    exec.executeUpdate( sql3 );
                }
                exec.commit();
                exec.setAutoCommitTrue();
                return true;
            }
            catch( Exception e1 ){
                logger.error( "写入入库对应表,库存表出错:" + e1.getMessage() );
                exec.rollback();
                exec.setAutoCommitTrue();
                return false;
            }
        }
        catch( Exception e ){
            logger.error( "写入写入备件入库基本表,入库对应表,库存表出错:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>功能:获得当前登陆代维单位的所有备件报废单信息
     * <br>参数:request
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getAllRevoke( HttpServletRequest request ){
        List reqinfo = null;
        String contractorid = ( String )request.getSession().getAttribute( "LOGIN_USER_DEPT_ID" );
        try{
            //获得报废 操作人,报废单位,报废时间,报废数量
            String sql =
                " select st.stockid,use.USERNAME,TO_CHAR(st.TIME,'YYYY-MM-DD HH24') time,con.CONTRACTORNAME,st.remark "
                + " from toolstock st,userinfo use,contractorinfo con "
                +
                " where st.CONTRACTORID=con.CONTRACTORID and st.USERID = use.USERID and type='报废' and st.contractorid='"
                + contractorid + "' "
                + " order by time desc ";
            QueryUtil query = new QueryUtil();
            reqinfo = query.queryBeans( sql );
            return reqinfo;
        }
        catch( Exception e ){
            logger.error( "在获得当前登陆代维单位的所有备件报废单信息发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:获得指定报废单信息
     * <br>参数:报废单id
     * <br>返回值:获得成功返回报废对象,否则返回 NULL;
     **/
    public ToolsInfoBean getOneRevoke( String stockid ){

        ToolsInfoBean bean = new ToolsInfoBean();
        ResultSet rst = null;
        try{

            String sql =
                "select st.stockid,use.USERNAME,TO_CHAR(st.TIME,'YYYY-MM-DD HH24') time,con.CONTRACTORNAME,st.remark "
                + " from toolstock st,userinfo use,contractorinfo con "
                +
                " where st.CONTRACTORID=con.CONTRACTORID and st.USERID = use.USERID  and stockid='" + stockid + "'";
            QueryUtil query = new QueryUtil();
            rst = query.executeQuery( sql );
            rst.next();
            bean.setUseid( rst.getString( "username" ) );
            bean.setStockid( rst.getString( "stockid" ) );
            bean.setTime( rst.getString( "time" ) );
            bean.setFactory( rst.getString( "contractorname" ) );
            bean.setRemark( rst.getString( "remark" ) );
            rst.close();
            return bean;
        }
        catch( Exception e ){
            logger.error( "在获得指定报废单信息中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:获得指定代维单位的报废操作人列表
     * <br>参数:代维id
     * <br>返回值:获得成功返回List,否则返回 NULL;
     **/
    public List getAllUsersForRevoke( String contractorid ){
        List reqPart = null;
        try{
            String sql = "select distinct st.userid,us.username "
                         + " from toolstock st,userinfo us "
                         + " where st.USERID = us.USERID and type='报废' and contractorid='" + contractorid + "'";
            QueryUtil query = new QueryUtil();
            reqPart = query.queryBeans( sql );
            return reqPart;
        }
        catch( Exception e ){
            logger.error( "在获得指定代维单位的报废操作人列表发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:获得指定代维单位的报废原因列表
     * <br>参数:代维id
     * <br>返回值:获得成功返回List,否则返回 NULL;
     **/
    public List getAllRemarkForRevoke( String contractorid ){
        List reqPart = null;
        try{
            String sql = "select distinct remark "
                         + " from toolstock "
                         + " where  type='报废'  and  contractorid='" + contractorid + "'";
            QueryUtil query = new QueryUtil();
            reqPart = query.queryBeans( sql );
            return reqPart;
        }
        catch( Exception e ){
            logger.error( "在获得指定代维单位的报废原因列表发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:综合查询,条件查询,
     * <br>参数:指定代维单位的id
     * <br>返回值:获得成功返回List,否则返回 NULL;
     **/
    public List doSearchStockForRevoke( ToolsInfoBean bean ){
        List lSearchStock = null;
        String sql =
            "select st.stockid,use.USERNAME,TO_CHAR(st.TIME,'YYYY-MM-DD HH24') time,con.CONTRACTORNAME,st.remark "
            + " from toolstock st,userinfo use,contractorinfo con "
            +
            " where st.CONTRACTORID=con.CONTRACTORID and st.USERID = use.USERID and type='报废' "
            + " and st.contractorid='" + bean.getContractorid() + "' ";

        if( bean.getUserid() != null && !bean.getUserid().equals( "" ) ){
            sql = sql + " and st.userid ='" + bean.getUserid() + "' ";
        }
        if( bean.getRemark() != null && !bean.getRemark().equals( "" ) ){
            sql = sql + "  and st.remark='" + bean.getRemark() + "'  ";
        }
        if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
            sql = sql + " and st.time >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
        }
        if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
            sql = sql + " and st.time <= TO_DATE('" + bean.getEndtime() + "','YYYY-MM-DD')";
        }
        sql = sql + "  order by time desc";
        try{
            QueryUtil query = new QueryUtil();
            lSearchStock = query.queryBeans( sql );
            return lSearchStock;
        }
        catch( Exception e ){
            logger.error( "综合查询,条件查询:" + e.getMessage() );
            return null;
        }
    }


    /**
     * 获得指定的入库单信息和材料信息
     * @param bean Part_requisitionBean
     * @return List
     */
    public List getStockList( ToolsInfoBean bean ){
        List lSearchStock = null;
        List reqPart = null;
        try{
            String sql =
                "select st.stockid,use.USERNAME,TO_CHAR(st.TIME,'YYYY-MM-DD HH24') time,con.CONTRACTORNAME, st.remark ,st.PATROLID,st.ADRESS "
                + " from toolstock st,userinfo use,contractorinfo con  "
                +
                " where st.CONTRACTORID=con.CONTRACTORID and st.USERID = use.USERID  and type='入库' and st.contractorid='"
                + bean.getContractorid() + "' ";

            if( bean.getUserid() != null && !bean.getUserid().equals( "" ) ){
                sql = sql + " and st.userid ='" + bean.getUserid() + "' ";
            }
            if( bean.getAdress() != null && !bean.getAdress().equals( "" ) ){
                sql = sql + "  and st.adress='" + bean.getAdress() + "'  ";
            }
            if( bean.getPatrolid() != null && !bean.getPatrolid().equals( "" ) ){
                sql = sql + "  and st.patrolid='" + bean.getPatrolid() + "'  ";
            }
            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                sql = sql + " and st.time >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
            }
            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                sql = sql + " and st.time <= TO_DATE('" + bean.getEndtime() + "','YYYY-MM-DD')";
            }
            sql = sql + "  order by time desc";
   //         //System.out.println( "query sql<<<<<<<<<<<<<<:" + sql );
            QueryUtil query = new QueryUtil();
            lSearchStock = query.queryBeans( sql );

            //获得材料信息：材料数量、入库数量、计量单位、规格型号、入库单id


            String sql2 = "select st.STOCKID, b.ID,b.NAME,b.UNIT,b.TYPE,st.ENUMBER "
                         + " from toolstock_toolbase st,tool_base b "
                         + " where st.ID = b.id  "
                         + " and st.stockid in ( select st.stockid "
                         + " from toolstock st,userinfo use,contractorinfo con  "
                         +
                         " where st.CONTRACTORID=con.CONTRACTORID and st.USERID = use.USERID  and type='入库' and st.contractorid='"
                         + bean.getContractorid() + "' ";

                     if( bean.getUserid() != null && !bean.getUserid().equals( "" ) ){
                         sql2 = sql2 + " and st.userid ='" + bean.getUserid() + "' ";
                     }
                     if( bean.getAdress() != null && !bean.getAdress().equals( "" ) ){
                         sql2 = sql2 + "  and st.adress='" + bean.getAdress() + "'  ";
                     }
                     if( bean.getPatrolid() != null && !bean.getPatrolid().equals( "" ) ){
                         sql2 = sql2 + "  and st.patrolid='" + bean.getPatrolid() + "'  ";
                     }
                     if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                         sql2 = sql2 + " and st.time >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
                     }
                     if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                         sql2 = sql2 + " and st.time <= TO_DATE('" + bean.getEndtime() + "','YYYY-MM-DD')";
            }
            sql2 += " ) ";
  //          //System.out.println( "query sql2<<<<<<<<<<<<<<:" + sql2 );
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

    public List getRevokeList( ToolsInfoBean bean ){
        List lSearchStock = null;
        List reqPart = null;
        try{
           String sql =
               "select st.stockid,use.USERNAME,TO_CHAR(st.TIME,'YYYY-MM-DD HH24') time,con.CONTRACTORNAME,st.remark "
               + " from toolstock st,userinfo use,contractorinfo con "
               +
               " where st.CONTRACTORID=con.CONTRACTORID and st.USERID = use.USERID and type='报废' "
               + " and st.contractorid='" + bean.getContractorid() + "' ";

           if( bean.getUserid() != null && !bean.getUserid().equals( "" ) ){
               sql = sql + " and st.userid ='" + bean.getUserid() + "' ";
           }
           if( bean.getRemark() != null && !bean.getRemark().equals( "" ) ){
               sql = sql + "  and st.remark='" + bean.getRemark() + "'  ";
           }
           if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
               sql = sql + " and st.time >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
           }
           if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
               sql = sql + " and st.time <= TO_DATE('" + bean.getEndtime() + "','YYYY-MM-DD')";
           }
           sql = sql + "  order by time desc";

  //          //System.out.println( "query sql<<<<<<<<<<<<<<:" + sql );
            QueryUtil query = new QueryUtil();
            lSearchStock = query.queryBeans( sql );

            //获得材料信息：材料数量、入库数量、计量单位、规格型号、入库单id
            String sql2 = "select b.ID,b.NAME,b.UNIT,b.TYPE,st.ENUMBER,st.stockid "
                         + " from toolstock_toolbase st,tool_base b "
                         + " where st.ID = b.id  "
                         + " and st.stockid in (select st.stockid "
                         + " from toolstock st,userinfo use,contractorinfo con "
                         +
                         " where st.CONTRACTORID=con.CONTRACTORID and st.USERID = use.USERID and type='报废' "
                         + " and st.contractorid='" + bean.getContractorid() + "' ";

                     if( bean.getUserid() != null && !bean.getUserid().equals( "" ) ){
                         sql2 = sql2 + " and st.userid ='" + bean.getUserid() + "' ";
                     }
                     if( bean.getRemark() != null && !bean.getRemark().equals( "" ) ){
                         sql2 = sql2 + "  and st.remark='" + bean.getRemark() + "'  ";
                     }
                     if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                         sql2 = sql2 + " and st.time >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
                     }
                     if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                         sql2 = sql2 + " and st.time <= TO_DATE('" + bean.getEndtime() + "','YYYY-MM-DD')";
           }
            sql2 += " ) ";
  //          //System.out.println( "query sql2<<<<<<<<<<<<<<:" + sql2 );
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
}
//
