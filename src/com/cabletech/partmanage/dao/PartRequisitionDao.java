package com.cabletech.partmanage.dao;

import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.http.*;

import org.apache.commons.beanutils.*;
import org.apache.log4j.*;
import org.apache.struts.upload.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.exceltemplates.*;
import com.cabletech.commons.hb.*;
import com.cabletech.partmanage.beans.*;
import com.cabletech.commons.sqlbuild.QuerySqlBuild;

public class PartRequisitionDao{
    private static Logger logger = Logger.getLogger( PartRequisitionDao.class.
                                   getName() );
    private Part_requisitionBean bean;
    public PartRequisitionDao(){

    }


    public PartRequisitionDao( Part_requisitionBean bean ){
        this.bean = bean;
    }


    /**
     * <br>����:��õ�ǰ�û��ĵ�λ������Ϣ
     * <br>����:��ǰ�û�����
     * <br>����ֵ:���ص�ǰ�û��ĵ�λ������Ϣ;
     */
    public String getUserDeptName( UserInfo userinfo ){
        String strDeptName = "";
        String sql = "";
        String[][] lDeptName = null;
        if( userinfo.getDeptype().equals( "2" ) ){ //�Ǵ�ά��λ
            sql = "select CONTRACTORNAME from CONTRACTORINFO where CONTRACTORID='" + userinfo.getDeptID() + "'";
        }
        else{
            sql = "select deptname from deptinfo where deptid='" + userinfo.getDeptID() + "'";
        }
        try{
            QueryUtil query = new QueryUtil();
            lDeptName = query.executeQueryGetArray( sql, "" );
            return lDeptName[0][0];
        }
        catch( Exception e ){
            logger.error( "�ڻ���û���λ�����г���:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>���ܣ���õ�ǰ��½��ά��λ��Ŀ�괦������Ϣ
     * <br>����:request
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
     */
    public List getTargetman( HttpServletRequest request ){
        List targetman = null;
        try{
            UserInfo user = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
            String sql = "select userid,username from userinfo where deptype='1' and regionid='" + user.getRegionID()
                         + "'";
    //        System.out.println( "SL:" + sql );

            QueryUtil query = new QueryUtil();
            targetman = query.queryBeans( sql );
            return targetman;
        }
        catch( Exception e ){
            logger.error( "�ڻ�õ�ǰ��½��ά��λ��Ŀ�괦������Ϣ����:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>���ܣ���ò��ϻ������id,name,type,unit��Ϣ
     * <br>����:��
     * <br>����ֵ:��óɹ�����Vector,���򷵻� null;
     */
    public List getAllInfo( String regionid ){
        List lInfo = null;
        String sql = "select id,name,type,unit from part_baseinfo  where state is null and regionid='" + regionid
                     + "'  order by id desc, name";
        //System.out.println("SL:" + sql);
        try{
            QueryUtil query = new QueryUtil();
            lInfo = query.queryBeans( sql );
            return lInfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ�ò���name,type,unit,id��Ϣ�г���:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:д�����������,����_���϶�Ӧ��
     * <br>����:
     * <br>����ֵ:��óɹ�����true,���򷵻� false;
     */
    public boolean doAddRequisition( Part_requisitionBean bean, String[] id, String[] renumber ){
        String sql1 = ""; //д��������
        String sql2 = ""; //д���Ӧ���
        java.util.Date date = new java.util.Date();
        SimpleDateFormat dtFormat = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss" );
        String strDt = dtFormat.format( date );

        try{
            UpdateUtil exec = new UpdateUtil();
            exec.setAutoCommitFalse();
            sql1 =
                "insert into Part_requisition (reid,contractorid,userid,time,reason,remark,auditresult,targetman,regionid) values ('"
                + bean.getReid() + "','" + bean.getcontractorid() + "','" + bean.getUserid() + "',"
                + "TO_DATE('" + strDt + "','YYYY-MM-DD HH24:MI:SS'),'" + bean.getReason() + "','" + bean.getRemark() +
                "','" + "������" + "','" + bean.getTargetman() + "','" + bean.getRegionid() + "')";
            try{
                exec.executeUpdate( sql1 ); //д�������
                for( int i = 0; i < id.length; i++ ){ //��������Ϣ�ֱ�д���Ӧ��Ϳ���
                    sql2 = "insert into part_req_partbase (reid,contractorid,id,renumber,audnumber) values ('"
                           + bean.getReid() + "','" + bean.getcontractorid() + "','" + id[i] + "',"
                           + Float.parseFloat( renumber[i].toString() ) + ",0)";

                    if( !renumber[i].equals( "0" ) && renumber[i] != null ){
                        exec.executeUpdate( sql2 );
                    }
                    //System.out.println(sql2);
                }

                exec.commit();
                exec.setAutoCommitTrue();
                return true;
            }
            catch( Exception e1 ){
                logger.error( "д�����������,����_���϶�Ӧ�����:" + e1.getMessage() );
                exec.rollback();
                exec.setAutoCommitTrue();
                return false;
            }
        }
        catch( Exception e ){
            logger.error( "д�����������,����_���϶�Ӧ��������:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>����:ɾ�������������һ����Ϣ
     * <br>����:��ɾ���뵥��id
     * <br>����ֵ:��óɹ�����true,���򷵻� false;
     */
    public boolean doDeleteReq( String reid ){
        try{
            String sql = "delete from part_req_partbase where id='" + reid + "'";
            UpdateUtil excu = new UpdateUtil();
            excu.executeUpdate( sql );
            return true;
        }
        catch( Exception e ){
            logger.error( "��ɾ���������뵥�з����쳣:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>����:��õ�ǰ��½��ά��λ�����в����������Ϣ
     * <br>����:��ά��λid
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     */
    public List getAllReq( HttpServletRequest request ){
        List reqinfo = null;
        String contractorid = ( String )request.getSession().getAttribute( "LOGIN_USER_DEPT_ID" );
        try{
            //����û�����,��ά��ά����,����ʱ��,���뵥���,��������,���뱸ע,�������
            String sql =
                "select userinfo.username,contractorinfo.contractorname,TO_CHAR(part_requisition.time,'YYYY-MM-DD') time,"
                + " part_requisition.reid,part_requisition.reason,part_requisition.remark,"
                + " part_requisition.auditresult "
                + " from part_requisition ,userinfo ,contractorinfo "
                +
                " where part_requisition.CONTRACTORID=contractorinfo.CONTRACTORID and part_requisition.userid=userinfo.userid "
                + " and part_requisition.CONTRACTORID='" + contractorid + "' "
                + " order by time desc";
            QueryUtil query = new QueryUtil();
            reqinfo = query.queryBeans( sql );
            return reqinfo;
        }
        catch( Exception e ){
            logger.error( "����ʾ���뵥ȫ����Ϣ�з����쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:��õ�ǰ��½��ά��λ�����������Ϣ
     * <br>����:���뵥id
     * <br>����ֵ:��óɹ��������뵥����,���򷵻� NULL;
     **/
    public Part_requisitionBean getOneReq( String reid ){

        Part_requisitionBean bean = new Part_requisitionBean();
        ResultSet rst = null;
        try{
            //����û�����,��ά��ά����,����ʱ��,���뵥���,��������,���뱸ע,����״̬,
            String sql =
                "select userinfo.username,contractorinfo.contractorname,TO_CHAR(part_requisition.time,'YYYY-MM-DD HH24:MI:SS') time,targetman, us.username targetmanname,"
                + " part_requisition.reid,part_requisition.reason,part_requisition.remark,part_requisition.auditresult ,part_requisition.userid useid,"
                + " TO_CHAR(part_requisition.audittime,'YYYY-MM-DD') audittime,part_requisition.audituserid,part_requisition.auditremark "
                + " from part_requisition ,userinfo ,contractorinfo ,userinfo us "
                + " where part_requisition.CONTRACTORID=contractorinfo.CONTRACTORID and part_requisition.userid=userinfo.userid "
                + " and part_requisition.targetman = us.userid and part_requisition.reid='" + reid + "' "
                + " order by time desc";

            QueryUtil query = new QueryUtil();
            rst = query.executeQuery( sql );
            rst.next();

            bean.setUsername( rst.getString( "username" ) );
            bean.setContractorname( rst.getString( "contractorname" ) );
            bean.setTime( rst.getString( "time" ) );
            bean.setReid( rst.getString( "reid" ) );
            bean.setReason( rst.getString( "reason" ) );
            bean.setRemark( rst.getString( "remark" ) );
            bean.setAuditresult( rst.getString( "auditresult" ) );
            bean.setAudituserid(rst.getString("audituserid"));
            bean.setUseid( rst.getString( "useid" ) );
            bean.setAudittime( rst.getString( "audittime" ) );
            bean.setAuditremark( rst.getString( "auditremark" ) );
            bean.setTargetman(rst.getString("targetman"));
            bean.setTargetmanname(rst.getString("targetmanname"));
            rst.close();
            return bean;
        }
        catch( Exception e ){
            logger.error( "����ʾ���뵥ȫ����Ϣ�з����쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:��õ�ǰ��½��ά��λ,ָ�����뵥��ŵ����в���������Ϣ
     * <br>����:���뵥id
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     **/
    public List getReqPart( String reid, HttpServletRequest request ){

        List reqPart = null;
        String contractorid = ( String )request.getSession().getAttribute( "LOGIN_USER_DEPT_ID" );

        try{
            //����û�����,��ά��ά����,����ʱ��,���뵥���,��������,���뱸ע,��������,���������
            String sql = "select base.id,base.name,base.unit,base.type,req.renumber, req.audnumber,req.stocknumber "
                         + " from part_baseinfo  base,part_req_partbase  req "
                         + " where req.id=base.id and req.reid='" + reid + "' and req.CONTRACTORID ='" + contractorid
                         + "' "
                         + " order by name";
            QueryUtil query = new QueryUtil();
            reqPart = query.queryBeans( sql );
            return reqPart;
        }
        catch( Exception e ){
            logger.error( "����ʾ���뵥ȫ����Ϣ�з����쳣:" + e.getMessage() );
            return null;
        }
    }

    /**
     * <br>����:��õ�ǰ��½��ά��λ,ָ�����뵥��ŵ����в���������Ϣ
     * <br>����:���뵥id
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     **/
    public List getRequisitionPart( String reid, HttpServletRequest request ){

        List reqPart = null;
        String contractorid = request.getParameter("contractorid");

        try{
            //����û�����,��ά��ά����,����ʱ��,���뵥���,��������,���뱸ע,��������,���������
            String sql = "select base.id,base.name,base.unit,base.type,req.renumber, req.audnumber,req.stocknumber "
                         + " from part_baseinfo  base,part_req_partbase  req "
                         + " where req.id=base.id and req.reid='" + reid + "' and req.CONTRACTORID ='" + contractorid
                         + "' "
                         + " order by name";
            QueryUtil query = new QueryUtil();
            reqPart = query.queryBeans( sql );
            return reqPart;
        }
        catch( Exception e ){
            logger.error( "����ʾ���뵥ȫ����Ϣ�з����쳣:" + e.getMessage() );
            return null;
        }
    }

    /**
     * <br>����:д�����������,����_���϶�Ӧ��
     * <br>����:
     * <br>����ֵ:��óɹ�����true,���򷵻� false;
     */
    public boolean doUpdateRequisition( Part_requisitionBean bean, String[] id, String[] renumber ){
        String sql1 = ""; //�޸Ļ������
        String sql2 = ""; //ɾ����Ӧ���
        String sql3 = ""; //����д���Ӧ���
        java.util.Date date = new java.util.Date();
        SimpleDateFormat dtFormat = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss" );
        String strDt = dtFormat.format( date );

        if( !this.valiReqForUp( bean.getReid() ) ){ //���ܱ�ɾ������
            return false;
        }

        try{
            UpdateUtil exec = new UpdateUtil();
            exec.setAutoCommitFalse();
            sql1 = "update Part_requisition "
                   + " set contractorid='" + bean.getcontractorid() + "',userid='" + bean.getUserid()
                   + "',reason='" + bean.getReason() + "',remark='" + bean.getRemark()
                   + "', targetman='"+bean.getTargetman() 
                   + "',time=" + "TO_DATE('" + strDt + "','YYYY-MM-DD HH24:MI:SS'), auditresult='������' "
                   + " where reid='" + bean.getReid() + "'";
            try{
                exec.executeUpdate( sql1 ); //�޸Ļ�����
                for( int i = 0; id!=null&&i < id.length; i++ ){ //����д���Ӧ��
                    if( !this.valiReq_partbaseExist( bean.getReid(), bean.getcontractorid(), id[i] ) ){ //��������������
                        sql3 = "insert into part_req_partbase (reid,contractorid,id,renumber,audnumber) values ('"
                               + bean.getReid() + "','" + bean.getcontractorid() + "','" + id[i] + "',"
                               + Integer.parseInt( renumber[i] ) + ",0)";

                    }
                    else{
                        sql3 = "update part_req_partbase set renumber=" + Integer.parseInt( renumber[i] )
                               + ", audnumber=0 "
                               + " where reid='" + bean.getReid() + "' and id='" + id[i] + "'";
                    }
                    exec.executeUpdate( sql3 );
                }
                sql3="delete from part_req_partbase where 1=1";
                if(id!=null){
                    sql3+=" and id not in (";
                    for(int i=0;i<id.length;i++)
                        sql3+="'"+id[i]+"',";
                    sql3+="'0') and reid='"+bean.getReid()+"'";
                }
                exec.executeUpdate(sql3);
                exec.commit();
                exec.setAutoCommitTrue();
                return true;
            }
            catch( Exception e1 ){
                logger.error( "����ʱд�����������,����_���϶�Ӧ�����:" + e1.getMessage() );
                exec.rollback();
                exec.setAutoCommitTrue();
                return false;
            }
        }
        catch( Exception e ){
            logger.error( "���³���:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>����:����ָ�������뵥�ܷ�����޸Ĳ���,ע��:һ��ͨ������,���ý����޸ĺ�ɾ��.
     * <br>����:���뵥id
     * <br>����ֵ:���޸ķ���true �����޸ķ��� false;
     **/
    public boolean valiReqForUp( String reid ){
        ResultSet rst = null;
        try{
            String sql = "select req.AUDITRESULT from part_requisition req where reid='" + reid + "'";
            QueryUtil query = new QueryUtil();
            rst = query.executeQuery( sql );
            rst.next();
            if( !rst.getString( "auditresult" ).equals( "������" ) && !rst.getString( "auditresult" ).equals( "��������" ) ){
                rst.close();
                return false;
            }
            rst.close();
            return true;
        }
        catch( Exception e ){
            logger.error( "����ʾ���뵥ȫ����Ϣ�з����쳣:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>���ܣ�������뵥_���ϱ��ж�Ӧ���뵥����Ӧ��λ�Ĳ����Ƿ����,
     * <br>����:������reid,��ά��λcontractorid,
     * <br>����ֵ:��Ӧ��¼���ڷ���true,���򷵻� false;
     */
    private boolean valiReq_partbaseExist( String reid, String contractorid, String id ){

        ResultSet rst = null;
        String sql = "select count(*) aa from  part_req_partbase"
                     + " where reid='" + reid + "' and contractorid='" + contractorid
                     + "' and id='" + id + "'";
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
            logger.error( "����֤���뵥-���ϱ��Ƿ������Ӧ��¼�г���:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>���ܣ�ɾ�����뵥_���ϱ��ж�Ӧ���뵥������Ĳ�����Ϣ,
     * <br>����:������reid
     * <br>����ֵ:��Ӧ��¼���ڷ���true,���򷵻� false;
     */
    public boolean deletPart_BaseInfo( String reid ){

        String sql = "delete  part_req_partbase where reid='" + reid + "'";
        try{
            UpdateUtil excu = new UpdateUtil();
            excu.executeUpdate( sql );
            return true;
        }
        catch( Exception e ){
            logger.error( "��ɾ�����뵥-���ϱ������Ӧ��¼�г���:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>���ܣ�ɾ�����뵥��ָ�����뵥��������Ϣ,
     * <br>����:������reid
     * <br>����ֵ:ɾ���ɹ����ڷ���true,���򷵻� false;
     */
    public boolean deletReqInfo( String reid ){

        String sql = "delete  Part_requisition where reid='" + reid + "'";
        if( this.deletPart_BaseInfo( reid ) ){ //��ɾ����Ӧ��,ɾ���ɹ�����ɾ������
            try{
                UpdateUtil excu = new UpdateUtil();
                excu.executeUpdate( sql );
                return true;
            }
            catch( Exception e ){
                logger.error( "��ɾ�����뵥�г���:" + e.getMessage() );
                return false;
            }
        }
        else{
            return false;
        }
    }


    /**
     * <br>����:���ָ����ά��λ������д�����뵥����Ա����
     * <br>����:ָ����ά��λ��id
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     **/
    public List getUserOfReq( String contractorid ){

        List reqUser = null;
        try{
            //��������˵�����,id
            String sql = "select distinct userinfo.userid,userinfo.username "
                         + " from part_requisition,userinfo "
                         + " where part_requisition.USERID = userinfo.USERID "
                         + "and part_requisition.contractorid='" + contractorid + "'";

            QueryUtil query = new QueryUtil();
            reqUser = query.queryBeans( sql );
            return reqUser;
        }
        catch( Exception e ){
            logger.error( "�ڻ��ָ����ά��λ������д�����뵥����Ա�����з����쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:���ָ����ά��λ��������ԭ��
     * <br>����:ָ����ά��λ��id
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     **/
    public List getReasonOfReq( String contractorid ){

        List reqUser = null;
        try{

            String sql = "select distinct reason  from part_requisition where contractorid='" + contractorid + "'";

            QueryUtil query = new QueryUtil();
            reqUser = query.queryBeans( sql );
            return reqUser;
        }
        catch( Exception e ){
            logger.error( "�ڻ��ָ����ά��λ��������ԭ���з����쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:�ۺϲ�ѯ,������ѯ,
     * <br>����:ָ����ά��λ��id
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     **/
    public List doSeatchReq( Part_requisitionBean bean, String[] id ){
        List lSearchReq = null;
        String sql =
            "select distinct part_requisition.reid,part_requisition.auditresult,contractorinfo.contractorname,userinfo.username, "
            + " TO_CHAR(part_requisition.time,'YYYY-MM-DD') time,part_requisition.reason,part_requisition.remark "
            + " from part_requisition,part_req_partbase,contractorinfo,userinfo "
            + " where part_requisition.reid=part_req_partbase.reid and contractorinfo.contractorid=part_requisition.contractorid and userinfo.userid=part_requisition.userid ";

        if( bean.getUserid() != null && !bean.getUserid().equals( "" ) ){
            sql = sql + " and userinfo.username like '" + bean.getUserid() + "%' ";
        }
        if( bean.getAuditresult() != null && !bean.getAuditresult().equals( "" ) ){
            sql = sql + "  and part_requisition.auditresult='" + bean.getAuditresult() + "'  ";
        }
        if( bean.getReason() != null && !bean.getReason().equals( "" ) ){
            sql = sql + "  and part_requisition.reason like '" + bean.getReason() + "%'  ";
        }
        if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
            sql = sql + " and part_requisition.time >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
        }
        if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
            sql = sql + " and part_requisition.time <= TO_DATE('" + bean.getEndtime() + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
        }

        sql = sql + " and part_requisition.contractorid='" + bean.getcontractorid() + "' order by time desc";
         logger.info("SQL:" + sql);

        try{
            QueryUtil query = new QueryUtil();
            lSearchReq = query.queryBeans( sql );
            return lSearchReq;
        }
        catch( Exception e ){
            logger.error( "�ۺϲ�ѯ,������ѯ:" + e.getMessage() );
            return null;
        }
    }


    ///////////////////////////////����////////////////////

    /**
     * <br>����:������д�����������������Ϣ
     * <br>����:
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     */
    public List getAllReqForAudit( String regionid ){
        List reqinfo = null;

        try{
            //�������������,��ά����,����ʱ��,���뵥���,��������,���뱸ע,�������
            String sql =
                "select userinfo.username,contractorinfo.contractorname,TO_CHAR(part_requisition.time,'YYYY-MM-DD') time,"
                + "     part_requisition.reid,part_requisition.reason,part_requisition.remark,"
                + "     part_requisition.auditresult "
                + " from part_requisition ,userinfo ,contractorinfo "
                +
                " where part_requisition.CONTRACTORID=contractorinfo.CONTRACTORID and part_requisition.userid=userinfo.userid "
                + " and (part_requisition.auditresult='������' or part_requisition.auditresult='�����') "
                + " and contractorinfo.regionid='" + regionid + "'"
                + " order by time desc";
            QueryUtil query = new QueryUtil();
            reqinfo = query.queryBeans( sql );
            return reqinfo;
        }
        catch( Exception e ){
            logger.error( "����ʾ����������뵥��Ϣ�з����쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:���ָ�����뵥��ŵ����в���������Ϣ
     * <br>����:���뵥id
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     **/
    public List getReqPartForAudit( String reid ){

        List reqPart = null;

        try{
            //��ò���id,��������,���ϵ�λ,��������,��������,��������
            String sql = "select base.id,base.name,base.unit,base.type,req.renumber, req.audnumber "
                         + " from part_baseinfo  base,part_req_partbase  req "
                         + " where req.id=base.id and req.reid='" + reid + "' "
                         + " order by name";
            QueryUtil query = new QueryUtil();
            reqPart = query.queryBeans( sql );
            return reqPart;
        }
        catch( Exception e ){
            logger.error( "����ʾ���뵥ȫ����Ϣ�з����쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:���ָ�����뵥��ŵ����� δ��ȫ�����Ĳ�����Ϣ
     * <br>����:���뵥id
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     **/
    public List getReqPartForReAudit( String reid ){

        List reqPart = null;

        try{
            //��ò���id,��������,���ϵ�λ,��������,��������,��������,���������
            String sql = "select base.id,base.name,base.unit,base.type,req.renumber, req.audnumber,req.stocknumber "
                         + " from part_baseinfo  base,part_req_partbase  req "
                         + " where req.id=base.id  and req.stocknumber < audnumber and req.reid='" + reid + "' "
                         + " order by name";
            QueryUtil query = new QueryUtil();
            reqPart = query.queryBeans( sql );
            return reqPart;
        }
        catch( Exception e ){
            logger.error( "����ʾ���뵥ȫ����Ϣ�з����쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:������뵥,��������϶�Ӧ���е����������޸�Ϊ�������,��������ƷӦ������ = ��������ƷӦ������ - (��������-���������)
     * <br>����:���뵥id
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     **/
    public boolean doReAudit( String reid, String[] id ){

        String sql1 = ""; //�޸Ŀ�����ƷӦ����
        String sql2 = ""; //�޸�����_���ϱ�������
        try{
            UpdateUtil exec = new UpdateUtil();
            exec.setAutoCommitFalse();
            try{
                for( int i = 0; i < id.length; i++ ){
                    sql1 = "update part_storage "
                           + " set newshould= newshould - "
                           + "                (select audnumber - stocknumber from  part_req_partbase req where reid='"
                           + reid + "' and id = '" + id[i] + "') "

                           + " where  part_storage.id = '" + id[i] + "'";
                    exec.executeUpdate( sql1 );
                    sql2 = "update part_req_partbase set audnumber= stocknumber where reid = '" + reid + "' and id='"
                           + id[i] + "'";
                    exec.executeUpdate( sql2 );
                }
                sql1 = "update part_requisition set auditresult='������' where reid='" + reid + "'";
                exec.executeUpdate( sql1 );
                exec.commit();
                exec.setAutoCommitTrue();
                return true;
            }
            catch( Exception e ){
                exec.rollback();
                exec.setAutoCommitTrue();
                logger.error( "��������뵥�����쳣:" + e.getMessage() );
                return false;
            }
        }
        catch( Exception e ){

            logger.error( "��������뵥�����쳣:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>���ܣ�д��һ�������Ļ�����Ϣ
     * <br>����:������bean
     * <br>����ֵ:�ɹ�����true,���򷵻� false;
     */
    public boolean doAddAudit( Part_requisitionBean bean ){
        java.util.Date date = new java.util.Date();
        SimpleDateFormat dtFormat = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss" );
        String strDt = dtFormat.format( date );

        String sql = "update Part_requisition set audituserid='" + bean.getUserid()
                     + "',audittime=" + "TO_DATE('" + strDt + "','YYYY-MM-DD HH24:MI:SS')"
                     + ",auditresult='" + bean.getAuditresult()
                     + "',auditremark='" + bean.getAuditremark() + "',deptid='" + bean.getDeptid() + "' "
                     + " where reid='" + bean.getReid() + "'";
        try{
            UpdateUtil excu = new UpdateUtil();
            excu.executeUpdate( sql );
            return true;
        }
        catch( Exception e ){
            logger.error( "д��������������Ϣʱ����: " + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>���ܣ��������_���ϱ����д��������Ϣ,�����������������Ϊ��,ͬʱ���ò��Ͽ����е���ƷӦ������
     * <br>����:������id,�������ϵ�id,��������
     * <br>����ֵ:�ɹ�����true,���򷵻� false;
     */
    public boolean doAddReq_PartForAudit( String[] id, String[] audnumber, String reid ){
        String sql = "";
        try{
            UpdateUtil excu = new UpdateUtil();
            for( int i = 0; i < audnumber.length; i++ ){
                sql = "update part_req_partbase set audnumber=" + Integer.parseInt( audnumber[i] )
                      + ",stocknumber=0"
                      + " where reid='" + reid + "' and id='" + id[i] + "'";
                excu.executeUpdate( sql );
                excu = new UpdateUtil();
                this.doUpStorageForNewshould( reid, id[i], audnumber[i] );
            }
            return true;
        }
        catch( Exception e ){
            logger.error( "д�������뵥_���ϱ���д��������Ϣʱ����: " + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>����:���������������Ϣ
     * <br>����:request
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     */
    public List getAllAudit( HttpServletRequest request ){
        List reqinfo = null;
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        String deptid = userinfo.getDeptID();
        try{
            //�������������,����ʱ��,�������,������ע,���������뵥����ԭ��,���뵥λ����
            String sql = "select us.username,TO_CHAR(req.AUDITTIME,'YYYY-MM-DD') time,req.auditresult,"
                         + " req.AUDITREMARK,req.reid,req.REASON,con.contractorname "
                         + " from part_requisition req,userinfo us,contractorinfo con "
                         +
                         " where req.AUDITUSERID=us.USERID and req.contractorid=con.contractorid and auditresult <> '������'"
                         + " and req.deptid='" + deptid + "'"
                         + " order by time desc";
//System.out.println("���������������ϢSQL:" + sql);
            QueryUtil query = new QueryUtil();
            reqinfo = query.queryBeans( sql );
            return reqinfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ��������������Ϣ�쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:���ָ�����뵥��ŵ�������Ϣ
     * <br>����:���뵥id
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     **/
    public Part_requisitionBean getAuditInfo( String reid ){

        ResultSet rst = null;
        Part_requisitionBean bean = new Part_requisitionBean();
        try{

            String sql =
                "   select req.AUDITREMARK,req.AUDITRESULT,TO_CHAR(req.AUDITTIME,'YYYY-MM-DD') audittime,us.username,de.deptname "
                + " from part_requisition req,userinfo us,deptinfo de "
                + " where req.audituserid=us.userid and de.deptid=req.deptid and req.reid='" + reid + "'";
            QueryUtil query = new QueryUtil();
            rst = query.executeQuery( sql );
            rst.next();
            bean.setAuditremark( rst.getString( "auditremark" ).trim() );
            bean.setAuditresult( rst.getString( "auditresult" ).trim() );
            bean.setAudittime( rst.getString( "audittime" ).trim() );
            bean.setDeptname( rst.getString( "deptname" ) );
            bean.setUsername( rst.getString( "username" ).trim() );
            rst.close();
            return bean;
        }
        catch( Exception e ){
            logger.error( "�ڻ�����뵥����Ϣ���쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:������е������˵�����,id
     * <br>����:
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     **/
    public List getUserOfAudit( String deptid ){

        List reqUser = null;
        try{
            String sql =
                "select distinct req.audituserid,us.username from part_requisition req,userinfo us where req.AUDITUSERID = us.USERID  and req.deptid='"
                + deptid + "'";
            QueryUtil query = new QueryUtil();
            reqUser = query.queryBeans( sql );
            return reqUser;
        }
        catch( Exception e ){
            logger.error( "�ڻ�����е������˵������з����쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:����������뵥λ����,id
     * <br>����:
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     **/
    public List getDeptName( String deptid ){

        List reqUser = null;
        try{
            String sql = "select distinct con.contractorid, con.CONTRACTORNAME  from part_requisition req,contractorinfo con where req.CONTRACTORID = con.CONTRACTORID and con.state is null "
                         + " and req.deptid ='" + deptid + "'";
            QueryUtil query = new QueryUtil();
            reqUser = query.queryBeans( sql );
            return reqUser;
        }
        catch( Exception e ){
            logger.error( "�ڻ���������뵥λ�����з����쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:���������������
     * <br>����:
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     **/
    public List getAllReqReason( String deptid ){

        List reqReason = null;
        try{
            String sql = "select distinct reason from part_requisition where part_requisition.deptid='" + deptid + "'";
            QueryUtil query = new QueryUtil();
            reqReason = query.queryBeans( sql );
            return reqReason;
        }
        catch( Exception e ){
            logger.error( "�ڻ���������뵥λ�����з����쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:�������ۺϲ�ѯ������
     * <br>����:form bean
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     **/
    public List getUserOfReq( Part_requisitionBean bean ){

        List reqUser = null;

        try{ //�������������,����ʱ��,�������,������ע,���������뵥����ԭ��,���뵥λ����     + " order by time ";
            String sql = "select us.username,TO_CHAR(req.AUDITTIME,'YYYY-MM-DD') time,req.auditresult, "
                         + " req.AUDITREMARK,req.reid,req.REASON,con.contractorname "
                         + " from part_requisition req,userinfo us,contractorinfo con "
                         +
                         " where req.AUDITUSERID=us.USERID and req.contractorid=con.contractorid and auditresult <> '������' ";
            if( !bean.getAudituserid().equals( "" ) && bean.getAudituserid() != null ){ //������
                sql = sql + " and us.username like '" + bean.getAudituserid() + "%'";
            }
            if( !bean.getAuditresult().equals( "" ) && bean.getAuditresult() != null ){ //�������
                sql = sql + " and req.auditresult='" + bean.getAuditresult() + "' ";
            }
            if( !bean.getReason().equals( "" ) && bean.getReason() != null ){
                sql = sql + " and req.reason like '" + bean.getReason() + "%' ";
            }
            if( !bean.getcontractorid().equals( "" ) && bean.getcontractorid() != null ){
                sql = sql + " and req.contractorid='" + bean.getcontractorid() + "' ";
            }
            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                sql = sql + " and req.audittime >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
            }
            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                sql = sql + " and  req.audittime <= TO_DATE('" + bean.getEndtime() + " 23:59:59 ','YYYY-MM-DD hh24:mi:ss')";
            }

            sql = sql + " and  req.deptid='" + bean.getDeptid() + "' order by time desc";
            QueryUtil query = new QueryUtil();
            logger.info( "���tiaojian SQL:" + sql );
            reqUser = query.queryBeans( sql );
            return reqUser;
        }
        catch( Exception e ){
            logger.error( "�ڰ������ۺϲ�ѯ�������쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:�ڿ����в���ָ���ļ�¼�Ƿ����
     * <br>����:����id ��ά��λid
     * <br>����ֵ:���ڷ���true �����ڷ���false;
     **/
    public boolean valiExistForStorage( String id, String contractorid ){
        ResultSet rst = null;
        String sql = "select count(*) aa from  part_storage"
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
            logger.error( "���ڿ����в���ָ���ļ�¼�Ƿ�����з����쳣:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>����:��������и���ָ�������뵥��Ų�����Ӧ�Ĵ�ά��λ���
     * <br>����:���뵥���
     * <br>����ֵ:���ڷ���contracrotid �����ڷ���null;
     **/
    public String getContractorID( String reid ){
        ResultSet rst = null;
        String sql = "select contractorid from  part_requisition"
                     + " where reid='" + reid + "'";
        try{
            QueryUtil excu = new QueryUtil();
            rst = excu.executeQuery( sql );
            rst.next();
            return rst.getString( "contractorid" ).trim();
        }
        catch( Exception e ){
            logger.error( "����������и���ָ�������뵥��Ų�����Ӧ�Ĵ�ά��λ����з����쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:�������뵥���,���ϱ��,���������������¿������ƷӦ������
     * <br>����:���뵥���,���ϱ��,������������
     * <br>����ֵ:���³ɹ�����true,���򷵻�false;
     **/
    public boolean doUpStorageForNewshould( String reid, String id, String audnumber ){

        String sql = "";
        String contractorid = this.getContractorID( reid );
        try{
            if( this.valiExistForStorage( id, contractorid ) ){ //�ô�ά��λ��Ӧ�Ĳ��ϴ���,Ҫ����
                sql = "update part_storage set newshould=newshould + " + Integer.parseInt( audnumber )
                      + " where id='" + id + "' and contractorid='" + contractorid + "'";
            }
            else{ //�����ھ����
                sql = " insert into part_storage (id,contractorid,newesse,oldnumber,newshould)"
                      + " values ('" + id + "','" + contractorid + "',0,0," + Integer.parseInt( audnumber ) + ")";
            }

            UpdateUtil excu = new UpdateUtil();
            excu.executeUpdate( sql );
                      return true;
        }
        catch( Exception e ){
            logger.error( "�ڸ������뵥���,���ϱ��,���������������¿������ƷӦ�������з����쳣:" + e.getMessage() );
            return false;
        }
    }


    /**
     * ����:���ϴ����ļ�����Ϊ��ʱ�ļ�
     * ����:bean,����·��
     * ����:����ɹ�������,���򷵻ؼ�
     * **/
    public boolean saveFile( Part_requisitionBean hform, String path ){

        String dir = path;
        FormFile file = hform.getFile();
        String filename = file.getFileName();
        String filesize = Integer.toString( file.getFileSize() ) + "bytes";
        if( file == null ){
            return false;
        }
        File temfile = new File( dir + "\\temp.xls" );
        if( temfile.exists() ){
            temfile.delete();
        }
        try{
            InputStream streamIn = file.getInputStream();
            OutputStream streamOut = new FileOutputStream( dir + "\\temp.xls" );
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while( ( bytesRead = streamIn.read( buffer, 0, 8192 ) ) != -1 ){
                streamOut.write( buffer, 0, bytesRead );
            }
            streamOut.close();
            streamIn.close();
            return true;
        }
        catch( Exception e ){
            logger.error( "upload error:" + e.getMessage() );
            return false;
        }
    }


    /**
     * ����:����ϴ��ļ�����������(��Ч����)
     * ����:bean,����·��
     * ����:��óɹ�����List ���򷵻�null
     * **/
    public Vector getUpInfo( Part_requisitionBean hform, String path ){
        Vector lUpInfo = new Vector();
        List lReadInfo = null;
        List lPartInfo = null;
        if( !this.saveFile( hform, path ) ){
            return null;
        }
        ReadExcle read = new ReadExcle( path + "\\temp.xls" );
        PartBaseInfoDao partDao = new PartBaseInfoDao();
        try{
            lReadInfo = read.getExcleContent();
            lPartInfo = partDao.getAllInfo( hform.getRegionid() );
            Part_requisitionBean outBean = null;

            for( int i = 0; i < lReadInfo.size(); i++ ){
                for( int j = 0; j < lPartInfo.size(); j++ ){
                    DynaBean dBean = ( DynaBean )lPartInfo.get( j );
                    String name = ( String )PropertyUtils.getSimpleProperty( dBean, "name" );
                    if( ( ( HashMap )lReadInfo.get( i ) ).get( "name" ).equals( name ) ){
                        outBean = new Part_requisitionBean();
                        outBean.setId( ( String )PropertyUtils.getSimpleProperty( dBean, "id" ) );
                        outBean.setName( ( String )PropertyUtils.getSimpleProperty( dBean, "name" ) );
                        outBean.setType( ( String )PropertyUtils.getSimpleProperty( dBean, "type" ) );
                        outBean.setUnit( ( String )PropertyUtils.getSimpleProperty( dBean, "unit" ) );
                        outBean.setRenumber( ( String ) ( ( HashMap )lReadInfo.get( i ) ).get( "number" ) );
                        lUpInfo.add( outBean );

                    }
                }
            }
            return lUpInfo;
        }
        catch( Exception e ){
            logger.error( "" + e.getMessage() );
            return null;
        }
    }

    /**
     * <br>����:��õ�ǰ��½��ά��λ�����в����������Ϣ
     * <br>����:��ά��λid
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     */
    public List getAllRequisition( HttpServletRequest request ){
        List reqinfo = null;
        UserInfo userinfo=(UserInfo)request.getSession().getAttribute("LOGIN_USER");
        String deptid=userinfo.getDeptID();
        try{
            //����û�����,��ά��ά����,����ʱ��,���뵥���,��������,���뱸ע,�������
            String sql =
                "select userinfo.username,contractorinfo.contractorname,TO_CHAR(part_requisition.time,'YYYY-MM-DD') time,"
                + " part_requisition.reid,part_requisition.reason,part_requisition.remark,"
                + " part_requisition.auditresult,contractorinfo.contractorid "
                + " from part_requisition ,userinfo ,contractorinfo ";
            QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );
            sqlBuild.addConstant(" part_requisition.CONTRACTORID=contractorinfo.CONTRACTORID and part_requisition.userid=userinfo.userid ");
            sqlBuild.addConditionAnd("part_requisition.regionid={0}",request.getParameter("regionid"));
            sqlBuild.addConditionAnd(" contractorinfo.contractorid={0}",request.getParameter("contractorid"));
            sqlBuild.addConstant(" and ( reid in ("
                +"                   select reid from part_req_partbase where id in ("
                +"                     select id from part_baseinfo where 1=1 ");
            sqlBuild.addConditionAnd(" name={0} ",request.getParameter("name"));
            sqlBuild.addConditionAnd(" type={0} ",request.getParameter("type"));
            sqlBuild.addConstant("     )"
                +"                   )"
                +"                 )");
            if(userinfo.getType().equals("21")){
                sqlBuild.addConditionAnd( " part_requisition.contractorid in ("
                    + "                         select contractorid from contractorinfo "
                    + "                         where parentcontractorid={0} and (state is null or state<>'1')"
                    + "                    )", deptid );
            }
            if(userinfo.getType().equals("12")){
                sqlBuild.addConditionAnd("part_requisition.regionid={0}",userinfo.getRegionID());
            }
            if(userinfo.getType().equals("22")){
                sqlBuild.addConditionAnd("part_requisition.contractorid={0}",userinfo.getDeptID());
            }
            sqlBuild.addConstant(" order by time desc");
            QueryUtil query = new QueryUtil();
            //System.out.println(sqlBuild.toSql());
            reqinfo = query.queryBeans( sqlBuild.toSql() );
            return reqinfo;
        }
        catch( Exception e ){
            logger.error( "����ʾ���뵥ȫ����Ϣ�з����쳣:" + e.getMessage() );
            return null;
        }
    }

    /**
     * <br>����:���������������Ϣ
     * <br>����:request
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     */
    public List getAllAuditReq( HttpServletRequest request ){
        List reqinfo = null;
        UserInfo userinfo=(UserInfo)request.getSession().getAttribute("LOGIN_USER");
        String deptid=userinfo.getDeptID();
        try{
            //�������������,����ʱ��,�������,������ע,���������뵥����ԭ��,���뵥λ����
            String sql = "select us.username,TO_CHAR(req.AUDITTIME,'YYYY-MM-DD') time,req.auditresult,"
                         + " req.AUDITREMARK,req.reid,req.REASON,con.contractorname "
                         + " from part_requisition req,userinfo us,contractorinfo con ";
            QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );
            sqlBuild.addConstant(" req.AUDITUSERID=us.USERID and req.contractorid=con.contractorid and auditresult <> '������'");
            sqlBuild.addConditionAnd("req.regionid={0}",request.getParameter("regionid"));
            sqlBuild.addConditionAnd(" req.deptid={0}",request.getParameter("deptid"));
            sqlBuild.addConstant(" and ( req.reid in ("
                +"                   select reid from part_req_partbase where id in ("
                +"                     select id from part_baseinfo where 1=1 ");
            sqlBuild.addConditionAnd(" name={0} ",request.getParameter("name"));
            sqlBuild.addConditionAnd(" type={0} ",request.getParameter("type"));
            sqlBuild.addConstant("     )"
                +"                   )"
                +"                 )");
            if(userinfo.getType().equals("21")){
                sqlBuild.addConditionAnd( " req.contractorid in ("
                    + "                         select contractorid from contractorinfo "
                    + "                         where parentcontractorid={0} and (state is null or state<>'1')"
                    + "                    )", deptid );
            }
            if(userinfo.getType().equals("12")){
                sqlBuild.addConditionAnd("req.regionid={0}",userinfo.getRegionID());
            }
            if(userinfo.getType().equals("22")){
                sqlBuild.addConditionAnd("req.contractorid={0}",userinfo.getDeptID());
            }
            sqlBuild.addConstant(" order by time desc");
            //System.out.println(sqlBuild.toSql());
            QueryUtil query = new QueryUtil();
            reqinfo = query.queryBeans( sqlBuild.toSql() );
            return reqinfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ��������������Ϣ�쳣:" + e.getMessage() );
            return null;
        }
    }
}
