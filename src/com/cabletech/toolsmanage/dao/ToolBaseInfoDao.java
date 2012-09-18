package com.cabletech.toolsmanage.dao;

import java.sql.*;
import java.util.*;

import org.apache.log4j.*;
import com.cabletech.commons.generatorID.impl.*;
import com.cabletech.commons.hb.*;
import com.cabletech.toolsmanage.beans.*;

public class ToolBaseInfoDao{
    private ToolsInfoBean baseInfo;
    private static Logger logger = Logger.getLogger( ToolBaseInfoDao.class.
                                   getName() );

    public ToolBaseInfoDao(){
    }


    public ToolBaseInfoDao( ToolsInfoBean bean ){
        this.baseInfo = bean;
    }


    /**
     *<br>功能:向备件基本表中插入一条信息
     *<br> 参数:无
     *<br>返回值:获得成功返回true,否则返回 false;
     */
    public boolean addToolBaseInfo(){

        OracleIDImpl ora = new OracleIDImpl();
        String id = ora.getSeq( "tool_base", 10 );
        if( id.equals( "" ) ){
            return false;
        }
        String strToolUse = baseInfo.getTooluse();
        strToolUse = strToolUse.length() > 512 ? strToolUse.substring( 0, 510 ) : strToolUse;
        String strRemark = baseInfo.getRemark();
        strRemark = strRemark.length() > 512 ? strRemark.substring( 0, 510 ) : strRemark;
        baseInfo.setUnit( baseInfo.getUnit() == null ? " " : baseInfo.getUnit() );
        baseInfo.setType( baseInfo.getType() == "" ? " " : baseInfo.getType() );
        try{
            String sql =
                "insert into tool_base (id,name,source,style,type,factory,factorysn,isasset,assetsn,tooluse,remark,unit,regionid) values ('"
                + id + "','" + baseInfo.getName() + "','" + baseInfo.getSource() + "','" + baseInfo.getStyle() + "','"
                + baseInfo.getType() + "','" + baseInfo.getFactory() + "','" + baseInfo.getFactorysn() + "','"
                + baseInfo.getIsasset() + "','"
                + baseInfo.getAssetsn() + "','" + strToolUse + "','" + strRemark + "','" + baseInfo.getUnit() + "','"
                + baseInfo.getRegionid() + "')";
            UpdateUtil excu = new UpdateUtil();
            excu.executeUpdate( sql );
            return true;
        }
        catch( Exception e ){
            logger.error( "向备件基本表中插入一条信息发生异常:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>功能：获得备件基本表部分字段信息
     * <br>参数:无
     * <br>返回值:获得成功返回List,否则返回 null;
     */
    public List getAllInfo( String regionid ){
        List lInfo = null;
        String sql = "select id,name,unit,type,factory,source from tool_base where regionid='" + regionid
                     + "' and state is null  order by name";
    //    //System.out.println( "SQL:" + sql );
        try{
            QueryUtil query = new QueryUtil();
            lInfo = query.queryBeans( sql );
            return lInfo;
        }
        catch( Exception e ){
            logger.error( "获得备件基本表部分字段信息出错:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能：获得单个备件的部分信息
     * <br>参数:备件id号
     * <br>返回值:获得成功返回Vector,否则返回 null;
     **/
    public ToolsInfoBean getOneInfo( String id ){
        ToolsInfoBean info = new ToolsInfoBean();
        ResultSet rst = null;
        String sql = "select * from tool_base  where id='" + id + "'";
        try{
            QueryUtil query = new QueryUtil();
            rst = query.executeQuery( sql );
            rst.next();
            info.setId( rst.getString( "id" ).trim() );
            info.setName( rst.getString( "name" ).trim() );
            info.setSource( rst.getString( "source" ) );
            info.setStyle( rst.getString( "style" ) );
            info.setType( rst.getString( "type" ) );
            info.setUnit( rst.getString( "unit" ) );
            info.setRemark( rst.getString( "remark" ) );
            info.setFactory( rst.getString( "factory" ) );
            info.setFactorysn( rst.getString( "factorysn" ) );
            info.setIsasset( rst.getString( "isasset" ) );
            info.setAssetsn( rst.getString( "assetsn" ) );
            info.setTooluse( rst.getString( "tooluse" ) );
            rst.close();
            return info;
        }
        catch( Exception e ){
            logger.error( "在获得单个备件基本信息中出错:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:更新备件基本表中一条信息
     * <br>参数:无
     * <br>返回值:获得成功返回true,否则返回 false;
     */
    public boolean updateToolBaseInfo(){
        try{
            String strToolUse = baseInfo.getTooluse();
            strToolUse = strToolUse.length() > 512 ? strToolUse.substring( 0, 510 ) : strToolUse;
            String strRemark = baseInfo.getRemark();
            strRemark = strRemark.length() > 512 ? strRemark.substring( 0, 510 ) : strRemark;
            if( baseInfo.getIsasset().equals( "否" ) ){
                baseInfo.setAssetsn( "" );
            }

            String sql = "update tool_base set name='" + baseInfo.getName() + "',type='" + baseInfo.getType()
                         + "',unit='" + baseInfo.getUnit() + "',remark='" + strRemark + "',factory='"
                         + baseInfo.getFactory()
                         + "',source='" + baseInfo.getSource() + "',style='" + baseInfo.getStyle() + "',factorysn='"
                         + baseInfo.getFactorysn()
                         + "',isasset='" + baseInfo.getIsasset() + "',assetsn='" + baseInfo.getAssetsn()
                         + "',tooluse='" + strToolUse
                         + "' where id='" + baseInfo.getId() + "'";
            UpdateUtil excu = new UpdateUtil();
            excu.executeUpdate( sql );
            return true;
        }
        catch( Exception e ){
            logger.error( "更新备件基本表中一条信息发生异常:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>功能:检查一条备件信息能否被删除,
     * <br>参数:id
     * <br>返回值:能删除返回true,否则返回 false;
     */
    public boolean valiForDele( String id ){
        ResultSet rst = null;
        try{

            String sql = "select count(*) aa "
                         +
                         " from toolstorage st,toolmaintain_toolbase main,toolstock_toolbase stock,tooluse_toolbase use"
                         + " where st.id='" + id + "' or main.id='" + id + "' or stock.id='" + id + "' or use.id='"
                         + id + "'";
            QueryUtil query = new QueryUtil();
            rst = query.executeQuery( sql );
            rst.next();
            if( rst.getString( "aa" ).equals( "0" ) ){
                rst.close();
                return true;
            }
            else{
                rst.close();
                return false;
            }
        }
        catch( Exception e ){
            logger.error( "在检查一条备件信息能否被删除中发生异常:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>功能:删除备件基本表中一条信息
     * <br>参数:id
     * <br>返回值:获得成功返回true,否则返回 false;
     */
    public boolean deleteToolBaseInfo( String id ){
        try{

            String sql = "update tool_base set state='1' where id='" + id + "'";
            UpdateUtil excu = new UpdateUtil();
            excu.executeUpdate( sql );
            return true;
        }
        catch( Exception e ){
            logger.error( "在删除备件基本表中一条信息中发生异常:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>功能：获得所有备件名称
     * <br>参数:无
     * <br>返回值:获得成功返回Vector,否则返回 null;
     */
    public List getAllName( String regionid ){
        List lInfo = null;
        String sql =
            "select distinct name, id from tool_base where name is not null and state is null and regionid = '"
            + regionid + "'  order by name";
        try{
            QueryUtil query = new QueryUtil();
            lInfo = query.queryBeans( sql );
            return lInfo;
        }
        catch( Exception e ){
            logger.error( "在获得所有备件名称中出错:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能：获得所有备件来源
     * <br>参数:无
     * <br>返回值:获得成功返回Vector,否则返回 null;
     */
    public List getAllSource( String regionid ){
        List lInfo = null;
        String sql =
            "select distinct source from tool_base  where source is not null and state is null and regionid = '"
            + regionid + "'  order by source";
        try{
            QueryUtil query = new QueryUtil();
            lInfo = query.queryBeans( sql );
            return lInfo;
        }
        catch( Exception e ){
            logger.error( "在获得所有备件来源中出错:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能：获得所有备件类型
     * <br>参数:无
     * <br>返回值:获得成功返回Vector,否则返回 null;
     */
    public List getAllStyle( String regionid ){
        List lInfo = null;
        String sql = "select distinct style from tool_base where style is not null and state is null and regionid='"
                     + regionid + "' order by style";
        try{
            QueryUtil query = new QueryUtil();
            lInfo = query.queryBeans( sql );
            return lInfo;
        }
        catch( Exception e ){
            logger.error( "在获得所有备件类型中出错:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能：获得所有备件型号
     * <br>参数:无
     * <br>返回值:获得成功返回Vector,否则返回 null;
     */
    public List getAllType( String regionid ){
        List lInfo = null;
        String sql = "select distinct type from tool_base where type is not null and state is null and regionid= '"
                     + regionid + "' order by type";
        try{
            QueryUtil query = new QueryUtil();
            lInfo = query.queryBeans( sql );
            return lInfo;
        }
        catch( Exception e ){
            logger.error( "在获得所有备件型号中出错:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能：获得所有备件生产厂家
     * <br>参数:无
     * <br>返回值:获得成功返回Vector,否则返回 null;
     */
    public List getAllFactory( String regionid ){
        List lInfo = null;
        String sql =
            "select distinct factory from tool_base where factory is not null and state is null and regionid ='"
            + regionid + "' order by factory";
        try{
            QueryUtil query = new QueryUtil();
            lInfo = query.queryBeans( sql );
            return lInfo;
        }
        catch( Exception e ){
            logger.error( "在获得所有备件生产厂家中出错:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能：查询,获得符合条件的备件信息
     * <br>参数:无
     * <br>返回值:获得成功返回Vector,否则返回 null;
     */
    public List DoQurey( ToolsInfoBean bean ){
        List lSegInfo = null;
        String sql = "select distinct id,name,unit,type,factory,source from tool_base  where 2>1 ";
        if( bean.getName() != null && ! ( bean.getName().equals( "" ) ) ){
            sql = sql + " and name='" + bean.getName() + "'  ";
        }
        if( bean.getType() != null && !bean.getType().equals( "" ) ){
            sql = sql + " and type='" + bean.getType() + "' ";
        }
        if( bean.getFactory() != null && !bean.getFactory().equals( "" ) ){
            sql = sql + " and factory = '" + bean.getFactory() + "' ";
        }
        if( bean.getSource() != null && !bean.getSource().equals( "" ) ){
            sql = sql + " and source = '" + bean.getSource() + "' ";
        }
        if( bean.getStyle() != null && !bean.getStyle().equals( "" ) ){
            sql = sql + " and style = '" + bean.getStyle() + "' ";
        }
        if( bean.getIsasset() != null && !bean.getIsasset().equals( "" ) ){
            sql = sql + " and isasset = '" + bean.getIsasset() + "' ";
        }
        sql = sql + "  and state is null and regionid= '" + bean.getRegionid() + "'  order by name";
        try{
            QueryUtil query = new QueryUtil();
            lSegInfo = query.queryBeans( sql );
            return lSegInfo;
        }
        catch( Exception e ){
            logger.error( "在查询,获得符合条件的备件信息中出错:" + e.getMessage() );
            return null;
        }
    }
}
