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
     *<br>����:�򱸼��������в���һ����Ϣ
     *<br> ����:��
     *<br>����ֵ:��óɹ�����true,���򷵻� false;
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
            logger.error( "�򱸼��������в���һ����Ϣ�����쳣:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>���ܣ���ñ������������ֶ���Ϣ
     * <br>����:��
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
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
            logger.error( "��ñ������������ֶ���Ϣ����:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>���ܣ���õ��������Ĳ�����Ϣ
     * <br>����:����id��
     * <br>����ֵ:��óɹ�����Vector,���򷵻� null;
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
            logger.error( "�ڻ�õ�������������Ϣ�г���:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:���±�����������һ����Ϣ
     * <br>����:��
     * <br>����ֵ:��óɹ�����true,���򷵻� false;
     */
    public boolean updateToolBaseInfo(){
        try{
            String strToolUse = baseInfo.getTooluse();
            strToolUse = strToolUse.length() > 512 ? strToolUse.substring( 0, 510 ) : strToolUse;
            String strRemark = baseInfo.getRemark();
            strRemark = strRemark.length() > 512 ? strRemark.substring( 0, 510 ) : strRemark;
            if( baseInfo.getIsasset().equals( "��" ) ){
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
            logger.error( "���±�����������һ����Ϣ�����쳣:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>����:���һ��������Ϣ�ܷ�ɾ��,
     * <br>����:id
     * <br>����ֵ:��ɾ������true,���򷵻� false;
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
            logger.error( "�ڼ��һ��������Ϣ�ܷ�ɾ���з����쳣:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>����:ɾ��������������һ����Ϣ
     * <br>����:id
     * <br>����ֵ:��óɹ�����true,���򷵻� false;
     */
    public boolean deleteToolBaseInfo( String id ){
        try{

            String sql = "update tool_base set state='1' where id='" + id + "'";
            UpdateUtil excu = new UpdateUtil();
            excu.executeUpdate( sql );
            return true;
        }
        catch( Exception e ){
            logger.error( "��ɾ��������������һ����Ϣ�з����쳣:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>���ܣ�������б�������
     * <br>����:��
     * <br>����ֵ:��óɹ�����Vector,���򷵻� null;
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
            logger.error( "�ڻ�����б��������г���:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>���ܣ�������б�����Դ
     * <br>����:��
     * <br>����ֵ:��óɹ�����Vector,���򷵻� null;
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
            logger.error( "�ڻ�����б�����Դ�г���:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>���ܣ�������б�������
     * <br>����:��
     * <br>����ֵ:��óɹ�����Vector,���򷵻� null;
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
            logger.error( "�ڻ�����б��������г���:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>���ܣ�������б����ͺ�
     * <br>����:��
     * <br>����ֵ:��óɹ�����Vector,���򷵻� null;
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
            logger.error( "�ڻ�����б����ͺ��г���:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>���ܣ�������б�����������
     * <br>����:��
     * <br>����ֵ:��óɹ�����Vector,���򷵻� null;
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
            logger.error( "�ڻ�����б������������г���:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>���ܣ���ѯ,��÷��������ı�����Ϣ
     * <br>����:��
     * <br>����ֵ:��óɹ�����Vector,���򷵻� null;
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
            logger.error( "�ڲ�ѯ,��÷��������ı�����Ϣ�г���:" + e.getMessage() );
            return null;
        }
    }
}
