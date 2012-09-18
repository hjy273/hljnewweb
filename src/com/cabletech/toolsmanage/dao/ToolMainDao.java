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
     * <br>����:д�뱸��������Ϣ,�������ޱ�,����_������Ӧ��,����
     * <br>����:������Ϣbean,����id����,������������
     * <br>����ֵ:��óɹ�����true,���򷵻� false;
     */
    public boolean addMianInfo( ToolsInfoBean bean, String[] id, String[] enumber ){
        String sql1 = ""; //д��������
        String sql2 = ""; //д���Ӧ���
        String sql3 = ""; //д������
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
                   + "TO_DATE('" + strDt + "','YYYY-MM-DD HH24:MI:SS'),'" + bean.getMainremark() + "','����')";
            try{
                exec.executeUpdate( sql1 ); //д�������
                for( int i = 0; i < id.length; i++ ){ //��������Ϣ�ֱ�д���Ӧ��Ϳ���
                    sql2 = "insert into toolmaintain_toolbase (mainid,id,enumber) values ('"
                           + mainid + "','" + id[i] + "'," + Integer.parseInt( enumber[i] ) + ")"; //д���Ӧ��
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
                logger.error( "д�뱨����Ϣ����:" + e1.getMessage() );
                exec.rollback();
                exec.setAutoCommitTrue();
                return false;
            }
        }
        catch( Exception e ){
            logger.error( "д�뱸��������Ϣ,�������ޱ�,����_������Ӧ��,�������:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>����:��õ�ǰ��½��ά��λ�����б�����Ϣ
     * <br>����:request
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     */
    public List getAllMain( HttpServletRequest request ){
        List useinfo = null;
        String contractorid = ( String )request.getSession().getAttribute( "LOGIN_USER_DEPT_ID" );
        try{
            String sql =
                " select  tm.MAINID,tm.CONTRACTORID,TO_CHAR(tm.time,'YYYY-MM-DD HH24') time,tm.MAINREMARK,u.USERNAME "
                + " from toolmaintain tm,userinfo u "
                + " where tm.USERID=u.userid and type='����' "
                + " and tm.CONTRACTORID='" + contractorid + "' "
                + " order by time desc";
    //        //System.out.println( "SQL:" + sql );
            QueryUtil query = new QueryUtil();
            useinfo = query.queryBeans( sql );
            return useinfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ�õ�ǰ��½��ά��λ�����б�����Ϣ�쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:���ָ�����޵���Ϣ
     * <br>����:���޵�id
     * <br>����ֵ:��óɹ����ض���,���򷵻� NULL;
     */
    public ToolsInfoBean getOneUse( String mainid ){
        ToolsInfoBean bean = new ToolsInfoBean();
        ResultSet rst = null;

        String sql =
            " select  tm.MAINID,TO_CHAR(tm.time,'YYYY-MM-DD HH24') time,tm.MAINREMARK,u.USERNAME,con.CONTRACTORNAME "
            + " from toolmaintain tm,userinfo u,contractorinfo con  "
            + " where tm.USERID=u.userid  and con.contractorid= tm.contractorid and type='����' "
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
            logger.error( "���ָ�����޵���Ϣ�쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:���ָ�����޵��ı�����Ϣ
     * <br>����:���޵�id
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
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
            logger.error( "���ָ�����޵��ı�����Ϣ�쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:�޸ı���������Ϣ,�������ޱ�,����_������Ӧ��,����
     * <br>����:������Ϣbean,����id����,������������
     * <br>����ֵ:��óɹ�����true,���򷵻� false;
     */
    public boolean upMianInfo( ToolsInfoBean bean, String[] id, String[] enumber, String[] oldenumber ){
        String sql1 = ""; //д��������
        String sql2 = ""; //д���Ӧ���
        String sql3 = ""; //д������
        String sql4 = ""; //ɾ���������
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
                exec.executeUpdate( sql1 ); //���»�����
                for( int i = 0; i < id.length; i++ ){ //���¶�Ӧ��Ϳ���
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
                logger.error( "���±�����Ϣ����:" + e1.getMessage() );
                exec.rollback();
                exec.setAutoCommitTrue();
                return false;
            }
        }
        catch( Exception e ){
            logger.error( "���±���������Ϣ,�������ޱ�,����_������Ӧ��,�������:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>����:ɾ������������Ϣ,�������ޱ�,����_������Ӧ��,����
     * <br>����:�������޵����
     * <br>����ֵ:��óɹ�����true,���򷵻� false;
     */
    public boolean deleMain( String contractorid, String mainid ){
        String sql1 = ""; //�޸Ŀ���
        String sql2 = ""; //ɾ����Ӧ���
        String sql3 = ""; //ɾ���������
        String[][] shouldUp = this.getShouldUpNumber( contractorid, mainid );
        if( shouldUp == null ){
            return false;
        }

        try{
            UpdateUtil exec = new UpdateUtil();
            exec.setAutoCommitFalse();
            try{
                //���¿���
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
                logger.error( "���±�����Ϣ����:" + e1.getMessage() );
                exec.rollback();
                exec.setAutoCommitTrue();
                return false;
            }
        }
        catch( Exception e ){
            logger.error( "���±���������Ϣ,�������ޱ�,����_������Ӧ��,�������:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>����:���ָ����λ��,��������������Ӧ�޸ĵ�ֵ,����id
     * <br>����:��λid,���޵�id
     * <br>����ֵ:��óɹ�����true,���򷵻� false;
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
            logger.error( "���ָ����λ��,��������������Ӧ�޸ĵ�ֵ,����id�쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>���ܣ����ָ����λ���޲������б�
     * <br>����:ָ����λ��id
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
     */
    public List getUserArr( String contractorid ){
        List lUser = null;
        String sql = " select distinct tm.userid,u.username "
                     + " from toolmaintain tm,userinfo u "
                     + " where tm.userid=u.userid and tm.CONTRACTORID ='" + contractorid + "' and type='����'";

        try{
            QueryUtil query = new QueryUtil();
            lUser = query.queryBeans( sql );
            return lUser;
        }
        catch( Exception e ){
            logger.error( "���ָ����λ���޲������б����:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>���ܣ����ָ����λ����ԭ���б�
     * <br>����:ָ����λ��id
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
     */
    public List getRemarkArr( String contractorid ){
        List lUser = null;
        String sql = " select distinct mainremark "
                     + " from toolmaintain "
                     + " where  mainremark is not null and CONTRACTORID ='" + contractorid + "' and type='����'";

        try{
            QueryUtil query = new QueryUtil();
            lUser = query.queryBeans( sql );
            return lUser;
        }
        catch( Exception e ){
            logger.error( "���ָ����λ����ԭ���б����:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:��ָ��������õ�ǰ��½��ά��λ�����б�����Ϣ
     * <br>����:��ά��λid
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     */
    public List getAllMainForSearch( ToolsInfoBean bean ){
        List useinfo = null;
        try{
            String sql =
                " select  tm.MAINID,tm.CONTRACTORID,TO_CHAR(tm.time,'YYYY-MM-DD HH24') time,tm.MAINREMARK,u.USERNAME "
                + " from toolmaintain tm,userinfo u "
                + " where tm.USERID=u.userid and type='����' "
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
            logger.error( "��ָ��������õ�ǰ��½��ά��λ�����б�����Ϣ�쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>���ܣ����ָ����λ���ޱ����б�
     * <br>����:ָ����λ��id
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
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
            logger.error( "���ָ����λ���ޱ����б����:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:��õ�ǰ��½��ά��λ����ָ�����������б�����Ϣ
     * <br>����:request
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     */
    public List getTool_Main( HttpServletRequest request ){
        List useinfo = null;
        String contractorid = ( String )request.getSession().getAttribute( "LOGIN_USER_DEPT_ID" );
        String id = request.getParameter( "id" );
        try{
            String sql =
                " select  tm.MAINID,tm.CONTRACTORID,TO_CHAR(tm.time,'YYYY-MM-DD HH24') time,tm.MAINREMARK,u.USERNAME "
                + " from toolmaintain tm,userinfo u "
                + " where tm.USERID=u.userid and type='����' "
                + " and tm.CONTRACTORID='" + contractorid + "' "
                + " and mainid in( select mainid from toolmaintain_toolbase where id='" + id + "' "
                + "               and contractorid='" + contractorid + "')"
                + " order by time desc";
            QueryUtil query = new QueryUtil();
            useinfo = query.queryBeans( sql );
            return useinfo;
        }
        catch( Exception e ){
            logger.error( "��õ�ǰ��½��ά��λ����ָ�����������б����쳣:" + e.getMessage() );
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
                + " where tm.USERID=u.userid and con.contractorid = tm.contractorid and type='����' "
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

            //��ò�����Ϣ���������������������������λ������ͺš���ⵥid
            String sql2 = " select tm.mainid,tm.ID,tm.ENUMBER,tb.name,tb.type,tb.unit,tb.STYLE,tb.FACTORY "
                          + " from toolmaintain_toolbase tm,tool_base tb "
                          + " where tm.ID = tb.ID "
                          + " and mainid in ( select tm.mainid "
                          + " from toolmaintain tm,userinfo u "
                          + " where tm.USERID=u.userid and type='����' "
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
            logger.error( "�ڰ��������������ⵥ��Ϣ�����쳣:" + e.getMessage() );
            return null;
        }
    }
}
