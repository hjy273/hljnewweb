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
     * <br>���ܣ���ñ����������id,name,type,unit��Ϣ
     * <br>����:��
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
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
            logger.error( "�ڻ�ñ����������id,name,type,unit��Ϣ�г���:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>���ܣ�������вֿⱣ���˵�id,name��Ϣ
     * <br>����:��
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
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
            logger.error( "������вֿⱣ���˵�id,name��Ϣ�г���:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:д�뱸����������,����Ӧ��,����
     * <br>����:
     * <br>����ֵ:��óɹ�����true,���򷵻� false;
     */
    public boolean doAddStock( ToolsInfoBean bean, String[] id, String[] enumber, String regionid ){
        OracleIDImpl ora = new OracleIDImpl();
        String stockid = ora.getSeq( "toolstock", 10 );

        java.util.Date date = new java.util.Date();
        SimpleDateFormat dtFormat = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss" );
        String strDt = dtFormat.format( date );

        String sql1 = ""; //д��������
        String sql2 = ""; //д���Ӧ���
        String sql3 = ""; //д������

        try{
            UpdateUtil exec = new UpdateUtil();
            exec.setAutoCommitFalse();
            sql1 = "insert into toolstock (stockid,userid,contractorid,time,adress,patrolid,remark,type) values ('"
                   + stockid + "','" + bean.getUserid() + "','" + bean.getContractorid() + "'," + "TO_DATE('" + strDt
                   + "','YYYY-MM-DD HH24:MI:SS'),'"
                   + bean.getAdress() + "','" + bean.getPatrolid() + "','" + bean.getRemark() + "','���')";
            try{
                exec.executeUpdate( sql1 ); //д�������
                for( int i = 0; i < id.length; i++ ){ //����Ϣ�ֱ�д���Ӧ��Ϳ���
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
                logger.error( "д������Ӧ��,�������:" + e1.getMessage() );
                exec.rollback();
                exec.setAutoCommitTrue();
                return false;
            }
        }
        catch( Exception e ){
            logger.error( "д��д�뱸����������,����Ӧ��,�������:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>���ܣ���鱸�������ָ����λ/ָ�������ļ�¼�Ƿ����,
     * <br>����:����id,��ά��λcontractorid,
     * <br>����ֵ:��Ӧ��¼���ڷ���true,���򷵻� false;
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
            logger.error( "�ڼ�鱸�������ָ����λ/ָ�������ļ�¼�Ƿ���ڳ���:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>����:��õ�ǰ��½��ά��λ�����б�����ⵥ��Ϣ
     * <br>����:request
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     */
    public List getAllStock( HttpServletRequest request ){
        List reqinfo = null;
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        String contractorid = ( String )userinfo.getDeptID();
        try{
            //������������,���ʱ��,��ⵥλ,������,��ŵص�
            String sql =
                "select st.stockid,use.USERNAME,TO_CHAR(st.TIME,'YYYY-MM-DD HH24') time,con.CONTRACTORNAME, st.PATROLID,st.ADRESS "
                + " from toolstock st,userinfo use,contractorinfo con  "
                +
                " where st.CONTRACTORID=con.CONTRACTORID and st.USERID = use.USERID  and type='���' and st.contractorid='"
                + contractorid + "' "
                + " order by time desc";
            ////System.out.println("SQL:" + sql);
            QueryUtil query = new QueryUtil();
            reqinfo = query.queryBeans( sql );
            return reqinfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ�õ�ǰ��½��ά��λ�����б�����ⵥ��Ϣ�����쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:���ָ����ⵥ��Ϣ
     * <br>����:��ⵥid
     * <br>����ֵ:��óɹ��������뵥����,���򷵻� NULL;
     **/
    public ToolsInfoBean getOneStock( String stockid ){

        ToolsInfoBean bean = new ToolsInfoBean();
        ResultSet rst = null;
        try{
            //��������,��ⵥλ,���ʱ��,��ŵص�,������,��ע��Ϣ,
            String sql = "select st.stockid,use.USERNAME,st.patrolid patrolname,TO_CHAR(st.TIME,'YYYY-MM-DD HH24') time,con.CONTRACTORNAME,st.ADRESS,st.remark "
                         + " from toolstock st,userinfo use,contractorinfo con "
                         +
                         " where st.CONTRACTORID=con.CONTRACTORID and st.USERID = use.USERID  and type='���'  and stockid='"
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
            logger.error( "�ڻ��ָ����ⵥ��Ϣ�з����쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:���ָ����ⵥ��������ⱸ����Ϣ
     * <br>����:��ⵥid
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
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
            logger.error( "�ڻ��ָ����ⵥ��������ⱸ����Ϣ�з����쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:���ָ����ά��λ��������б�
     * <br>����:��άid
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     **/
    public List getAllUsers( String contractorid ){
        List reqPart = null;
        try{
            String sql = "select distinct st.userid,us.username "
                         + " from toolstock st,userinfo us "
                         + " where st.USERID = us.USERID  and type = '���' and contractorid='" + contractorid + "'";
            QueryUtil query = new QueryUtil();
            reqPart = query.queryBeans( sql );
            return reqPart;
        }
        catch( Exception e ){
            logger.error( "�ڻ��ָ����ά��λ��������б��з����쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:���ָ����ά��λ�Ĵ�ŵص��б�
     * <br>����:��άid
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     **/
    public List getAllAdress( String contractorid ){
        List reqPart = null;
        try{
            String sql = "select distinct adress "
                         + " from toolstock "
                         + " where type = '���'  and adress is not null and contractorid='" + contractorid + "'";
            QueryUtil query = new QueryUtil();
            reqPart = query.queryBeans( sql );
            return reqPart;
        }
        catch( Exception e ){
            logger.error( "�ڻ��ָ����ά��λ�Ĵ�ŵص��б��з����쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:���ָ����ά��λ�ı������б�
     * <br>����:��άid
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     **/
    public List getAllPatrid( String contractorid ){
        List reqPart = null;
        try{
            String sql = " select distinct st.patrolid "
                         + " from toolstock st "
                         + " where st.patrolid is not null"
                         + " and  type = '���' and contractorid='" + contractorid + "'";
            QueryUtil query = new QueryUtil();
            reqPart = query.queryBeans( sql );
            return reqPart;
        }
        catch( Exception e ){
            logger.error( "�ڻ��ָ����ά��λ�ı������б��з����쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:�ۺϲ�ѯ,������ѯ,
     * <br>����:ָ����ά��λ��id
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     **/
    public List doSearchStock( ToolsInfoBean bean ){
        List lSearchStock = null;
        String sql =
            "select st.stockid,use.USERNAME,TO_CHAR(st.TIME,'YYYY-MM-DD HH24') time,con.CONTRACTORNAME, st.PATROLID,st.ADRESS "
            + " from toolstock st,userinfo use,contractorinfo con  "
            +
            " where st.CONTRACTORID=con.CONTRACTORID and st.USERID = use.USERID  and type='���' and st.contractorid='"
            + bean.getContractorid() + "' ";

        /**
         "select st.stockid,use.USERNAME,TO_CHAR(st.TIME,'YYYY-MM-DD HH24') time,con.CONTRACTORNAME,patr.PATROLNAME,st.ADRESS "
         + " from toolstock st,userinfo use,contractorinfo con,patrolman_son_info patr "
         +
         " where st.CONTRACTORID=con.CONTRACTORID and st.USERID = use.USERID and st.PATROLID = patr.PATROLID and type='���' "
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
            logger.error( "�ۺϲ�ѯ,������ѯ:" + e.getMessage() );
            return null;
        }
    }


    //////////////////////////�����Ǳ��ϲ���//////////////////////////////////////////////////////////////////
    /**
     * <br>���ܣ���ÿ�����ָ����ά��λ����id,name,type,unit,essenumber,shouldnumber,portmainnumber,mainnumber��Ϣ
     * <br>����:��
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
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
            logger.error( "�ڻ�ÿ�����ָ����ά��λ����id,name,type,unit,essenumber,shouldnumber,portmainnumber,mainnumber��Ϣ�г���:"
                + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:д�뱸����ⱨ�ϻ�����,��ⱨ�϶�Ӧ��,����
     * <br>����:
     * <br>����ֵ:��óɹ�����true,���򷵻� false;
     */
    public boolean doAddRevoke( ToolsInfoBean bean, String[] id, String[] enumber ){
        OracleIDImpl ora = new OracleIDImpl();
        String stockid = ora.getSeq( "toolstock", 10 );

        java.util.Date date = new java.util.Date();
        SimpleDateFormat dtFormat = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss" );
        String strDt = dtFormat.format( date );

        String sql1 = ""; //д��������
        String sql2 = ""; //д���Ӧ���
        String sql3 = ""; //д������

        try{
            UpdateUtil exec = new UpdateUtil();
            exec.setAutoCommitFalse();
            sql1 = "insert into toolstock (stockid,userid,contractorid,time,remark,type) values ('"
                   + stockid + "','" + bean.getUserid() + "','" + bean.getContractorid() + "',"
                   + "TO_DATE('" + strDt + "','YYYY-MM-DD HH24:MI:SS'),'" + bean.getRemark() + "','����')";
            try{
                exec.executeUpdate( sql1 ); //д�������
                for( int i = 0; i < id.length; i++ ){ //����Ϣ�ֱ�д���Ӧ��Ϳ���
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
                logger.error( "д������Ӧ��,�������:" + e1.getMessage() );
                exec.rollback();
                exec.setAutoCommitTrue();
                return false;
            }
        }
        catch( Exception e ){
            logger.error( "д��д�뱸����������,����Ӧ��,�������:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>����:��õ�ǰ��½��ά��λ�����б������ϵ���Ϣ
     * <br>����:request
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     */
    public List getAllRevoke( HttpServletRequest request ){
        List reqinfo = null;
        String contractorid = ( String )request.getSession().getAttribute( "LOGIN_USER_DEPT_ID" );
        try{
            //��ñ��� ������,���ϵ�λ,����ʱ��,��������
            String sql =
                " select st.stockid,use.USERNAME,TO_CHAR(st.TIME,'YYYY-MM-DD HH24') time,con.CONTRACTORNAME,st.remark "
                + " from toolstock st,userinfo use,contractorinfo con "
                +
                " where st.CONTRACTORID=con.CONTRACTORID and st.USERID = use.USERID and type='����' and st.contractorid='"
                + contractorid + "' "
                + " order by time desc ";
            QueryUtil query = new QueryUtil();
            reqinfo = query.queryBeans( sql );
            return reqinfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ�õ�ǰ��½��ά��λ�����б������ϵ���Ϣ�����쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:���ָ�����ϵ���Ϣ
     * <br>����:���ϵ�id
     * <br>����ֵ:��óɹ����ر��϶���,���򷵻� NULL;
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
            logger.error( "�ڻ��ָ�����ϵ���Ϣ�з����쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:���ָ����ά��λ�ı��ϲ������б�
     * <br>����:��άid
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     **/
    public List getAllUsersForRevoke( String contractorid ){
        List reqPart = null;
        try{
            String sql = "select distinct st.userid,us.username "
                         + " from toolstock st,userinfo us "
                         + " where st.USERID = us.USERID and type='����' and contractorid='" + contractorid + "'";
            QueryUtil query = new QueryUtil();
            reqPart = query.queryBeans( sql );
            return reqPart;
        }
        catch( Exception e ){
            logger.error( "�ڻ��ָ����ά��λ�ı��ϲ������б����쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:���ָ����ά��λ�ı���ԭ���б�
     * <br>����:��άid
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     **/
    public List getAllRemarkForRevoke( String contractorid ){
        List reqPart = null;
        try{
            String sql = "select distinct remark "
                         + " from toolstock "
                         + " where  type='����'  and  contractorid='" + contractorid + "'";
            QueryUtil query = new QueryUtil();
            reqPart = query.queryBeans( sql );
            return reqPart;
        }
        catch( Exception e ){
            logger.error( "�ڻ��ָ����ά��λ�ı���ԭ���б����쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:�ۺϲ�ѯ,������ѯ,
     * <br>����:ָ����ά��λ��id
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     **/
    public List doSearchStockForRevoke( ToolsInfoBean bean ){
        List lSearchStock = null;
        String sql =
            "select st.stockid,use.USERNAME,TO_CHAR(st.TIME,'YYYY-MM-DD HH24') time,con.CONTRACTORNAME,st.remark "
            + " from toolstock st,userinfo use,contractorinfo con "
            +
            " where st.CONTRACTORID=con.CONTRACTORID and st.USERID = use.USERID and type='����' "
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
            logger.error( "�ۺϲ�ѯ,������ѯ:" + e.getMessage() );
            return null;
        }
    }


    /**
     * ���ָ������ⵥ��Ϣ�Ͳ�����Ϣ
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
                " where st.CONTRACTORID=con.CONTRACTORID and st.USERID = use.USERID  and type='���' and st.contractorid='"
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

            //��ò�����Ϣ���������������������������λ������ͺš���ⵥid


            String sql2 = "select st.STOCKID, b.ID,b.NAME,b.UNIT,b.TYPE,st.ENUMBER "
                         + " from toolstock_toolbase st,tool_base b "
                         + " where st.ID = b.id  "
                         + " and st.stockid in ( select st.stockid "
                         + " from toolstock st,userinfo use,contractorinfo con  "
                         +
                         " where st.CONTRACTORID=con.CONTRACTORID and st.USERID = use.USERID  and type='���' and st.contractorid='"
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
            logger.error( "�ڰ��������������ⵥ��Ϣ�����쳣:" + e.getMessage() );
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
               " where st.CONTRACTORID=con.CONTRACTORID and st.USERID = use.USERID and type='����' "
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

            //��ò�����Ϣ���������������������������λ������ͺš���ⵥid
            String sql2 = "select b.ID,b.NAME,b.UNIT,b.TYPE,st.ENUMBER,st.stockid "
                         + " from toolstock_toolbase st,tool_base b "
                         + " where st.ID = b.id  "
                         + " and st.stockid in (select st.stockid "
                         + " from toolstock st,userinfo use,contractorinfo con "
                         +
                         " where st.CONTRACTORID=con.CONTRACTORID and st.USERID = use.USERID and type='����' "
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
            logger.error( "�ڰ��������������ⵥ��Ϣ�����쳣:" + e.getMessage() );
            return null;
        }
    }
}
//
