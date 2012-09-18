package com.cabletech.toolsmanage.dao;

import java.util.*;
import javax.servlet.http.*;

import org.apache.log4j.*;
import com.cabletech.commons.hb.*;
import com.cabletech.toolsmanage.beans.*;

public class ToolStorageDao{
    private ToolsInfoBean bean;
    private static Logger logger = Logger.getLogger( ToolStorageDao.class.
                                   getName() );

    public ToolStorageDao(){
    }


    public ToolStorageDao( ToolsInfoBean bean ){
        this.bean = bean;
    }


    /**
     * <br>功能:获得当前登陆代维单位的所有库存信息(代维单位)
     * <br>参数:请求
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getAllStorageForCon( HttpServletRequest request ){
        List useinfo = null;
        String contractorid = ( String )request.getSession().getAttribute( "LOGIN_USER_DEPT_ID" );
        try{
            String sql =
                " select b.NAME,b.UNIT,b.TYPE ,st.essenumber,st.shouldnumber,st.portmainnumber,st.mainnumber,con.contractorname "
                + " from toolstorage st,tool_base  b,contractorinfo con "
                + " where st.id = b.ID  and st.contractorid = con.contractorid and st.contractorid='"
                + contractorid + "' "
                + " order  by contractorname";
            QueryUtil query = new QueryUtil();
            useinfo = query.queryBeans( sql );
            return useinfo;
        }
        catch( Exception e ){
            logger.error( "在获得当前登陆代维单位的所有库存信息(代维单位)中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:获得所有库存信息(移动公司)
     * <br>参数:请求
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getAllStorageForDept( String regionid ){
        List useinfo = null;

        try{
            String sql =
                " select b.NAME,b.UNIT,b.TYPE ,st.essenumber,st.shouldnumber,st.portmainnumber,st.mainnumber,con.contractorname "
                + " from toolstorage st,tool_base b,contractorinfo con "
                + " where st.id = b.ID  and st.contractorid = con.contractorid  and st.regionid = '" + regionid + "' "
                + " order  by contractorname";
            QueryUtil query = new QueryUtil();
            useinfo = query.queryBeans( sql );
            return useinfo;
        }
        catch( Exception e ){
            logger.error( "在获得当前登陆代维单位的所有库存信息(移动公司)中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能：获得备件名称列表和id列表
     * <br>参数:指定单位的id
     * <br>返回值:获得成功返回List,否则返回 null;
     */
    public List getToolNameArr(){
        List lname = null;
        String sql = " select name,id from tool_base ";
        try{
            QueryUtil query = new QueryUtil();
            lname = query.queryBeans( sql );
            return lname;
        }
        catch( Exception e ){
            logger.error( "在获得备件名称列表和id列表中出错:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能：获得备件类型列表
     * <br>参数:指定单位的id
     * <br>返回值:获得成功返回List,否则返回 null;
     */
    public List getToolTypeArr(){
        List lType = null;
        String sql = " select distinct type from  tool_base ";
        try{
            QueryUtil query = new QueryUtil();
            lType = query.queryBeans( sql );
            return lType;
        }
        catch( Exception e ){
            logger.error( "获得备件类型列表出错:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能：获得代维单位列表
     * <br>参数:指定单位的id
     * <br>返回值:获得成功返回List,否则返回 null;
     */
    public List getContractorNameArr( String regionid ){
        List lname = null;
        String sql = " select contractorid,contractorname from contractorinfo where regionid='" + regionid + "'";
        try{
            QueryUtil query = new QueryUtil();
            lname = query.queryBeans( sql );
            return lname;
        }
        catch( Exception e ){
            logger.error( "获得代维单位列表出错:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:当是移动公司时,获得指定条件的所有库存信息
     * <br>参数:请求
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getStorageForDept( ToolsInfoBean bean ){
        List partinfo = null;
        try{
            String sql =
                " select b.id, b.NAME,b.UNIT,b.TYPE ,st.essenumber,st.shouldnumber,st.portmainnumber,st.mainnumber,con.contractorname "
                + " from toolstorage st,tool_base  b,contractorinfo con "
                + " where st.id = b.ID  and st.contractorid = con.contractorid  and st.regionid='" + bean.getRegionid()
                + "' ";
            if( !bean.getId().equals( "" ) && bean.getId() != null ){
                sql = sql + " and b.id='" + bean.getId() + "' ";
            }
            if( !bean.getType().equals( "" ) && bean.getType() != null ){
                sql = sql + " and b.type = '" + bean.getType() + "'";
            }
            if( !bean.getContractorid().equals( "" ) && bean.getContractorid() != null ){
                sql = sql + " and st.contractorid = '" + bean.getContractorid() + "'";
            }
            if( !bean.getEsselownumber().equals( "" ) && bean.getEsselownumber() != null ){
                sql = sql + " and st.essenumber >= " + Integer.parseInt( bean.getEsselownumber() );
            }
            if( !bean.getEssehighnumber().equals( "" ) && bean.getEssehighnumber() != null ){
                sql = sql + " and st.essenumber <= " + Integer.parseInt( bean.getEssehighnumber() );
            }
            if( !bean.getPortlownumber().equals( "" ) && bean.getPortlownumber() != null ){
                sql = sql + " and st.portmainnumber >= " + Integer.parseInt( bean.getPortlownumber() );
            }
            if( !bean.getPorthighnumber().equals( "" ) && bean.getPorthighnumber() != null ){
                sql = sql + " and st.portmainnumber <= " + Integer.parseInt( bean.getPorthighnumber() );
            }
            if( !bean.getMainlownumber().equals( "" ) && bean.getMainlownumber() != null ){
                sql = sql + " and st.mainnumber >= " + Integer.parseInt( bean.getMainlownumber() );
            }
            if( !bean.getMainhighnumber().equals( "" ) && bean.getMainhighnumber() != null ){
                sql = sql + " and st.mainnumber <= " + Integer.parseInt( bean.getMainhighnumber() );
            }
            sql = sql + " order by contractorname";
            QueryUtil query = new QueryUtil();
            partinfo = query.queryBeans( sql );
            return partinfo;
        }
        catch( Exception e ){
            logger.error( "当是移动公司时,获得指定条件的所有库存信息异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:当是代维单位时,获得指定条件的所有库存信息
     * <br>参数:请求
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getStorageForContractor( ToolsInfoBean bean ){
        List partinfo = null;
        try{
            String sql =
                " select b.id, b.NAME,b.UNIT,b.TYPE ,st.essenumber,st.shouldnumber,st.portmainnumber,st.mainnumber,con.contractorname "
                + " from toolstorage st,tool_base  b,contractorinfo con "
                + " where st.id = b.ID  and st.contractorid = con.contractorid  and st.contractorid='"
                + bean.getContractorid() + "' ";
            if( !bean.getId().equals( "" ) && bean.getId() != null ){
                sql = sql + " and b.id='" + bean.getId() + "' ";
            }
            if( !bean.getType().equals( "" ) && bean.getType() != null ){
                sql = sql + " and b.type = '" + bean.getType() + "'";
            }

            if( !bean.getEsselownumber().equals( "" ) && bean.getEsselownumber() != null ){
                sql = sql + " and st.essenumber >= " + Integer.parseInt( bean.getEsselownumber() );
            }
            if( !bean.getEssehighnumber().equals( "" ) && bean.getEssehighnumber() != null ){
                sql = sql + " and st.essenumber <= " + Integer.parseInt( bean.getEssehighnumber() );
            }
            if( !bean.getPortlownumber().equals( "" ) && bean.getPortlownumber() != null ){
                sql = sql + " and st.portmainnumber >= " + Integer.parseInt( bean.getPortlownumber() );
            }
            if( !bean.getPorthighnumber().equals( "" ) && bean.getPorthighnumber() != null ){
                sql = sql + " and st.portmainnumber <= " + Integer.parseInt( bean.getPorthighnumber() );
            }
            if( !bean.getMainlownumber().equals( "" ) && bean.getMainlownumber() != null ){
                sql = sql + " and st.mainnumber >= " + Integer.parseInt( bean.getMainlownumber() );
            }
            if( !bean.getMainhighnumber().equals( "" ) && bean.getMainhighnumber() != null ){
                sql = sql + " and st.mainnumber <= " + Integer.parseInt( bean.getMainhighnumber() );
            }
            sql = sql + " order by contractorname";
            QueryUtil query = new QueryUtil();
            partinfo = query.queryBeans( sql );
            return partinfo;
        }
        catch( Exception e ){
            logger.error( "当是代维单位时,获得指定条件的所有库存信息异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能：获得备件信息列表
     * <br>参数:指定单位的id
     * <br>返回值:获得成功返回List,否则返回 null;
     */
    public List getAllInfo(){
        List lType = null;
        String sql = " select id,name,type,unit,factory from  tool_base where state is null ";
        try{
            QueryUtil query = new QueryUtil();
            lType = query.queryBeans( sql );
            return lType;
        }
        catch( Exception e ){
            logger.error( "获得备件信息列表出错:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:初始化当前登陆代维单位的备件库存(执行方法:先删除记录然后写入)
     * <br>参数:基本信息bean,材料id数组,库存量数组,报修量数组,送修量数组
     * <br>返回值:获得成功返回true,否则返回 false;
     */
    public boolean initStorage( ToolsInfoBean bean, String[] id, String[] essenumber, String[] portmainnumber,
        String[] mainnumber ){
        String sql1 = ""; //删除的
        String sql2 = ""; //加入的

        try{
            UpdateUtil exec = new UpdateUtil();
            exec.setAutoCommitFalse();
            try{

                for( int i = 0; i < id.length; i++ ){ //逐个操作
                    sql1 = "delete from toolstorage where contractorid='" + bean.getContractorid() + "' and id='"
                           + id[i] + "'";
                    exec.executeUpdate( sql1 );
                    sql2 =
                        "insert into toolstorage (id,contractorid,essenumber,portmainnumber,mainnumber,shouldnumber,regionid) values('"
                        + id[i] + "','" + bean.getContractorid() + "',"
                        + Integer.parseInt( essenumber[i] ) + "," + Integer.parseInt( portmainnumber[i] ) + ","
                        + Integer.parseInt( mainnumber[i] ) + ","
                        + Integer.parseInt( essenumber[i] ) + " + " + Integer.parseInt( mainnumber[i] ) + ",'"
                        + bean.getRegionid() + "')";
                    exec.executeUpdate( sql2 );
                }
                exec.commit();
                exec.setAutoCommitTrue();
                return true;
            }
            catch( Exception e1 ){
                logger.error( "初始化出错:" + e1.getMessage() );
                exec.rollback();
                exec.setAutoCommitTrue();
                return false;
            }
        }
        catch( Exception e ){
            return false;
        }
    }

}
