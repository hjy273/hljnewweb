package com.cabletech.partmanage.dao;

import java.sql.*;
import java.util.*;

import org.apache.log4j.*;
import com.cabletech.commons.generatorID.impl.*;
import com.cabletech.commons.hb.*;
import com.cabletech.partmanage.beans.*;
import org.apache.commons.beanutils.BasicDynaBean;
import javax.servlet.http.HttpServletRequest;
import com.cabletech.commons.sqlbuild.QuerySqlBuild;
import com.cabletech.baseinfo.domainobjects.UserInfo;

/**
 *
 * ���ϻ�����Ϣ��CRUD
 *
 */
public class PartBaseInfoDao{
    private static Logger logger = Logger.getLogger( PartBaseInfoDao.class.
                                   getName() );
    private Part_baseInfoBean baseInfo;

    /**
     * <br>����:�ò��ϻ�����Ϣ�����ʼ��һ������Ķ���
     * <br>����:���ϻ�����Ϣ����:Part_baseInfoBean baseInfo
     * <br>����ֵ:
     */
    public PartBaseInfoDao( Part_baseInfoBean baseInfo ){
        this.baseInfo = baseInfo;
    }


    public PartBaseInfoDao(){
        this.baseInfo = baseInfo;
    }


    /**
     * <br>����:����ϻ������в���һ����Ϣ
     * <br>����:��
     * <br>����ֵ:��óɹ�����true,���򷵻� false;
     */
    public boolean addPartBaseInfo(){

        OracleIDImpl ora = new OracleIDImpl();
        String id = ora.getSeq( "Part_baseInfo", 10 );
        if( id.equals( "" ) ){
            return false;
        }

        String strRemark = baseInfo.getRemark();
        strRemark = strRemark.length() > 512 ? strRemark.substring( 0, 510 ) : strRemark;

        try{
            String sql = "insert into part_baseinfo (id,name,unit,type,remark,factory,regionid) values ('"
                         + id + "','" + baseInfo.getName() + "','"
                         + baseInfo.getUnit() + "','" + baseInfo.getType() + "','"
                         + strRemark + "','" + baseInfo.getFactory() + "','" + baseInfo.getRegionid() + "')";
            UpdateUtil excu = new UpdateUtil();
            excu.executeUpdate( sql );
            return true;
        }
        catch( Exception e ){
            logger.error( "��д����ϻ�����Ϣ�з����쳣:" + e.getMessage() );
            return false;
        }
    }



    /**
     * <br>���ܣ���ò��ϻ�����������Ϣ
     * <br>����:��
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
     */
    public List getAllInfo( String regionid ){
        List lInfo = null;
        String sql = "select * from part_baseinfo where state is  null and regionid='" + regionid + "' order by name";
        try{
            QueryUtil query = new QueryUtil();
            lInfo = query.queryBeans( sql );
            //lInfo=query.executeQueryGetStringVector(sql,"");
            return lInfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ�����в��ϻ�����Ϣ�г���:" + e.getMessage() );
            return null;
        }
    }

    /**
     * <br>���ܣ���õ������ϻ�����Ϣ
     * <br>����:����id��
     * <br>����ֵ:��óɹ�����Vector,���򷵻� null;
     **/
    public Part_baseInfoBean getOneInfo( String id ){
        Part_baseInfoBean info = new Part_baseInfoBean();
        ResultSet rst = null;
        String sql = "select * from part_baseinfo  where id='" + id + "'";
        try{
            QueryUtil query = new QueryUtil();
            rst = query.executeQuery( sql );
            rst.next();
            info.setId( rst.getString( "id" ).trim() );
            info.setName( rst.getString( "name" ).trim() );
            info.setType( rst.getString( "type" ) );
            info.setUnit( rst.getString( "unit" ) );
            info.setRemark( rst.getString( "remark" ) );
            info.setFactory( rst.getString( "factory" ) );
            rst.close();
            return info;
        }
        catch( Exception e ){
            try{
                rst.close();
            }catch(Exception er){
                logger.error("�رս�����쳣��"+er.getMessage());
            }
            logger.error( "�ڻ�õ������ϻ�����Ϣ�г���:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:���²��ϻ�������һ����Ϣ
     * <br>����:��
     * <br>����ֵ:��óɹ�����true,���򷵻� false;
     */
    public boolean updatePartBaseInfo(){
        try{
            String strRemark = baseInfo.getRemark();
            strRemark = strRemark.length() > 512 ? strRemark.substring( 0, 510 ) : strRemark;

            String sql = "update part_baseinfo set name='" + baseInfo.getName() + "',type='" + baseInfo.getType()
                         + "',unit='"
                         + baseInfo.getUnit() + "',remark='" + strRemark + "',factory='" + baseInfo.getFactory()
                         + "' where id='" + baseInfo.getId() + "'";
            UpdateUtil excu = new UpdateUtil();
            excu.executeUpdate( sql );
            return true;
        }
        catch( Exception e ){
            logger.error( "��д����ϻ�����Ϣ�з����쳣:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>����:���һ��������Ϣ�ܷ�ɾ��,
     * <br>����:ɾ���ϵ�id
     * <br>����ֵ:��ɾ������true,���򷵻� false;
     */
    public boolean valiForDele( String id ){
        ResultSet rst = null;
        try{

//            String sql = "select count(*) aa "
//						+ " from part_req_partbase req,part_baseinfo b,part_stock_baseinfo st, "
//						+ " part_use_baseinfo us,part_oldstock_baseinfo old,part_storage "
//						+ " where req.id = b.id and st.id=b.id  and us.id=b.id and old.id=b.id and part_storage.id=b.id "
// 						+ " and b.id='" + id + "'";
            String sql = "select count(*) aa from "
                         + " (select id from part_oldstock_baseinfo union "
                         + "  select id from part_req_partbase union "
                         + "  select id from part_stock_baseinfo union "
                         + "  select id from part_storage union"
                         + "  select id from part_use_baseinfo )"
                         + "  where id='" + id + "'";
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
            try{
                rst.close();
            }catch(Exception er){
                logger.error("valiForDele() �رս�����쳣��"+er.getMessage());
            }
            logger.error( "�ڼ��һ��������Ϣ�ܷ�ɾ���з����쳣:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>����:ɾ�����ϻ�������һ����Ϣ
     * <br>����:ɾ���ϵ�id
     * <br>����ֵ:��óɹ�����true,���򷵻� false;
     */
    public boolean deletePartBaseInfo( String id ){
        try{
            String sql="select id from part_storage where (newesse>0 or oldnumber>0) and id='"+id+"'";
            QueryUtil query=new QueryUtil();
            ResultSet rs=query.executeQuery(sql);
            if(rs!=null&&rs.next()){
                if(rs.getString("id")!=null&&!rs.getString("id").equals(""))
                    return false;
            }
            sql = "update part_baseInfo set state = '1'  where id='" + id + "'";
            UpdateUtil excu = new UpdateUtil();
            logger.info("SQL: "+ sql);
            excu.executeUpdate( sql );
            return true;
        }
        catch( Exception e ){
            logger.error( "��ɾ�����ϻ�����Ϣ�з����쳣:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>���ܣ�������в�������
     * <br>����:��
     * <br>����ֵ:��óɹ�����Vector,���򷵻� null;
     */
    public List getAllName( String regionid ){
        List lInfo = null;
        String sql = "select distinct name, id from part_baseinfo where state is null and regionid='" + regionid
                     + "' order by name";
        try{
            QueryUtil query = new QueryUtil();
            lInfo = query.queryBeans( sql );
            return lInfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ�����в��������г���:" + e.getMessage() );
            return null;
        }
    }

    /**
     * <br>���ܣ�������в�������
     * <br>����:��
     * <br>����ֵ:��óɹ�����Vector,���򷵻� null;
     */
    public List getAllName(){
        List lInfo = null;
        String sql = "select id,regionid,name from part_baseinfo where state is null order by name";
        try{
            QueryUtil query = new QueryUtil();
            lInfo=query.queryBeans(sql);
            return lInfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ�����в��������г���:" + e.getMessage() );
            return null;
        }
    }

    /**
     * <br>���ܣ����������������
     * <br>����:��
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
     */
    public List getAllFactory( String regionid ){
        List lInfo = null;
        String sql =
            "select distinct factory from part_baseinfo where factory is not null and state is null and regionid='"
            + regionid + "' order by factory";
        try{
            QueryUtil query = new QueryUtil();
            lInfo = query.queryBeans( sql );
            return lInfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ���������������г���:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>���ܣ�������в�������
     * <br>����:��
     * <br>����ֵ:��óɹ�����Vector,���򷵻� null;
     */
    public List getAllType( String regionid ){
        List lInfo = null;
        String sql = "select distinct type from part_baseinfo where type is not null and state is null and regionid='"
                     + regionid + "' order by type";
        try{
            QueryUtil query = new QueryUtil();
            lInfo = query.queryBeans( sql );
            return lInfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ�����в��������г���:" + e.getMessage() );
            return null;
        }
    }

    /**
     * <br>���ܣ�������в�������
     * <br>����:��
     * <br>����ֵ:��óɹ�����Vector,���򷵻� null;
     */
    public List getAllType(){
        List lInfo = null;
        String sql = "select type,regionid,id,name from part_baseinfo where type is not null and state is null  order by type";
        try{
            QueryUtil query = new QueryUtil();
            lInfo = query.queryBeans( sql );
            return lInfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ�����в��������г���:" + e.getMessage() );
            return null;
        }
    }

    /**
     * <br>���ܣ�ģ����ѯ,��÷��������Ĳ�����Ϣ
     * <br>����:��
     * <br>����ֵ:��óɹ�����Vector,���򷵻� null;
     */
    public List getSegInfo_Blur( Part_baseInfoBean bean ){
        List lSegInfo = null;
        String sql = "select * from part_baseinfo  where 2>1  and state is null and regionid='" + bean.getRegionid()
                     + "'";
        if( bean.getName() != null && !bean.getName().equals( "" ) ){
            sql = sql + " and name like'" + bean.getName() + "%'  ";
        }
        if( bean.getType() != null && !bean.getType().equals( "" ) ){
            sql = sql + " and type like'" + bean.getType() + "%' ";
        }
        if( bean.getFactory() != null && !bean.getFactory().equals( "" ) ){
            sql = sql + " and factory like'" + bean.getFactory() + "%' ";
        }
        sql = sql + " order by name";

        try{
            QueryUtil query = new QueryUtil();
            lSegInfo = query.queryBeans( sql );
            return lSegInfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ�����в��ϻ�����Ϣ�г���:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>���ܣ���ȷ��ѯ,��÷��������Ĳ�����Ϣ
     * <br>����:��
     * <br>����ֵ:��óɹ�����Vector,���򷵻� null;
     */
    public List getSegInfo_Rigor( Part_baseInfoBean bean ){
        List lSegInfo = null;
        String sql = "select * from part_baseinfo  where 2>1 and state is null and regionid ='" + bean.getRegionid()
                     + "'";
        if( bean.getName() != null && ! ( bean.getName().equals( "" ) ) ){
            sql = sql + " and name='" + bean.getName() + "'  ";
        }
        if( bean.getType() != null && !bean.getType().equals( "" ) ){
            sql = sql + " and type='" + bean.getType() + "' ";
        }
        if( bean.getFactory() != null && !bean.getFactory().equals( "" ) ){
            sql = sql + " and factory = '" + bean.getFactory() + "' ";
        }
        sql = sql + " order by name";
        try{
            QueryUtil query = new QueryUtil();
            lSegInfo = query.queryBeans( sql );
            return lSegInfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ�����в��ϻ�����Ϣ�г���:" + e.getMessage() );
            return null;
        }
    }

    /**
     * <br>���ܣ���ò��ϻ�����������Ϣ
     * <br>����:��
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
     */
    public List getAllInfo( HttpServletRequest request ){
        List lInfo = null;
        UserInfo userinfo=(UserInfo)request.getSession().getAttribute("LOGIN_USER");
        String deptid=userinfo.getDeptID();
        String sql = "select * from part_baseinfo";
        QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );
        sqlBuild.addConstant(" (state is  null or state<>'1') ");
        sqlBuild.addConditionAnd(" regionid={0} ",request.getParameter("regionid"));
        sqlBuild.addConditionAnd(" name={0} ",request.getParameter("name"));
        sqlBuild.addConditionAnd(" type={0} ",request.getParameter("type"));
        if(userinfo.getType().equals("21")){
            sqlBuild.addConditionAnd( " regionid in ("
                + "                       select regionid from contractorinfo "
                + "                       where contractorid in ("
                + "                         select contractorid from contractorinfo "
                + "                         where parentcontractorid={0}"
                + "                       ) and (state is null or state<>'1')"
                + "                    )", deptid );
        }
        if(userinfo.getType().equals("12")){
            sqlBuild.addConditionAnd("regionid={0}",userinfo.getRegionID());
        }
        if(userinfo.getType().equals("22")){
            sqlBuild.addConditionAnd("regionid in ("
                +"                      select regionid from contractorinfo "
                +"                      where contractorid={0} and (state is null or state<>'1')"
                +"                    )",userinfo.getDeptID());
        }
        sqlBuild.addConstant(" order by name");
        try{
            QueryUtil query = new QueryUtil();
            //System.out.println(sqlBuild.toSql());
            lInfo = query.queryBeans( sqlBuild.toSql() );
            //lInfo=query.executeQueryGetStringVector(sql,"");
            return lInfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ�����в��ϻ�����Ϣ�г���:" + e.getMessage() );
            return null;
        }
    }

    public List getAllNames( String condition ){
        List lInfo = null;
        String sql = "select distinct name, id from part_baseinfo where (state is null or state<>'1') ";
        sql+=" and regionid in (select regionid from contractorinfo con where 1=1 "+condition+")";
        sql+= " order by name";
        try{
            QueryUtil query = new QueryUtil();
            lInfo = query.queryBeans( sql );
            return lInfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ�����в��������г���:" + e.getMessage() );
            return null;
        }
    }

    public List getAllTypes( String condition ){
        List lInfo = null;
        String sql = "select distinct type from part_baseinfo where type is not null and (state is null or state<>'1')";
        sql+=" and regionid in (select regionid from contractorinfo con where 1=1 "+condition+")";
        sql+= " order by type";
        try{
            QueryUtil query = new QueryUtil();
            lInfo = query.queryBeans( sql );
            return lInfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ�����в��������г���:" + e.getMessage() );
            return null;
        }
    }

    public List getAllFactorys( String condition ){
        List lInfo = null;
        String sql ="select distinct factory from part_baseinfo where factory is not null and (state is null or state<>'1')";
        sql+=" and regionid in (select regionid from contractorinfo con where 1=1 "+condition+")";
        sql+=" order by factory";
        try{
            QueryUtil query = new QueryUtil();
            lInfo = query.queryBeans( sql );
            return lInfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ���������������г���:" + e.getMessage() );
            return null;
        }
    }
}
