package com.cabletech.toolsmanage.dao;

import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.http.*;

import org.apache.log4j.*;
import com.cabletech.commons.generatorID.impl.*;
import com.cabletech.commons.hb.*;
import com.cabletech.toolsmanage.beans.*;

public class ToolToMainDao{
    private ToolsInfoBean bean;
    private static Logger logger = Logger.getLogger( ToolToMainDao.class.
                                   getName() );

    public ToolToMainDao( ToolsInfoBean bean ){
        this.bean = bean;
    }


    public ToolToMainDao(){
    }


    /**
     * <br>���ܣ����ָ����λ��汸�����Ѿ����޵Ļ�����Ϣ�ʹ������
     * <br>����:ָ����λ��id
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
     */
    public List getToolInfo( String contractorid ){
        List lInfo = null;
        String sql = "select distinct st.ID,st.portmainnumber,b.NAME,b.TYPE,b.UNIT "
                     + " from toolstorage st,tool_base b "
                     + " where st.ID = b.ID and st.CONTRACTORID ='" + contractorid + "' and portmainnumber>0";
        try{
            QueryUtil query = new QueryUtil();
            lInfo = query.queryBeans( sql );
            return lInfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ��ָ����λ��汸���Ļ�����Ϣ�ʹ�������г���:" + e.getMessage() );
            return null;
        }
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
            sql1 = "insert into toolmaintain (mainid,contractorid,userid,time,mainremark,type,mainname,mainadd,mainphone,mainunit) values ('"
                   + mainid + "','" + bean.getContractorid() + "','" + bean.getUserid() + "',"
                   + "TO_DATE('" + strDt + "','YYYY-MM-DD HH24:MI:SS'),'" + bean.getMainremark() + "','����','"
                   + bean.getMainname() + "','" + bean.getMainadd() + "','" + bean.getMainphone() + "','"
                   + bean.getMainunit() + "')";
            try{
                exec.executeUpdate( sql1 ); //д�������
                for( int i = 0; i < id.length; i++ ){ //��������Ϣ�ֱ�д���Ӧ��Ϳ���
                    sql2 = "insert into toolmaintain_toolbase (mainid,id,enumber) values ('"
                           + mainid + "','" + id[i] + "'," + Integer.parseInt( enumber[i] ) + ")"; //д���Ӧ��
                    exec.executeUpdate( sql2 );
    //                //System.out.println( "SQL2:" + sql2 );

                    sql3 = " update toolstorage set mainnumber = mainnumber + " + Integer.parseInt( enumber[i] )
                           + ", essenumber = essenumber - " + Integer.parseInt( enumber[i] )
                           + ", portmainnumber = portmainnumber-" + Integer.parseInt( enumber[i] )
                           + "  where contractorid='" + bean.getContractorid() + "' and id ='" + id[i] + "'";
                    exec.executeUpdate( sql3 );
   //                 //System.out.println( "SQL3:" + sql3 );
                }
                exec.commit();
                exec.setAutoCommitTrue();
                return true;
            }
            catch( Exception e1 ){
                logger.error( "д��������Ϣ����:" + e1.getMessage() );
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
     * <br>����:��õ�ǰ��½��ά��λ������������Ϣ
     * <br>����:request
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     */
    public List getAllMain( HttpServletRequest request ){
        List useinfo = null;
        String contractorid = ( String )request.getSession().getAttribute( "LOGIN_USER_DEPT_ID" );
        try{
            String sql =
                " select  tm.MAINID,tm.mainname,tm.mainunit,tm.mainphone,tm.CONTRACTORID,TO_CHAR(tm.time,'YYYY-MM-DD HH24') time,tm.MAINREMARK,u.USERNAME "
                + " from toolmaintain tm,userinfo u "
                + " where tm.USERID=u.userid and type='����' "
                + " and tm.CONTRACTORID='" + contractorid + "' "
                + " order by time desc";
            QueryUtil query = new QueryUtil();
            useinfo = query.queryBeans( sql );
            return useinfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ�õ�ǰ��½��ά��λ������������Ϣ�쳣:" + e.getMessage() );
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
            " select  tm.MAINID,TO_CHAR(tm.time,'YYYY-MM-DD HH24') time,tm.MAINREMARK,u.USERNAME,con.CONTRACTORNAME,"
            + " tm.mainname,tm.mainunit,tm.mainadd,tm.mainphone "
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
            bean.setMainname( rst.getString( "mainname" ) );
            bean.setMainunit( rst.getString( "mainunit" ) );
            bean.setMainadd( rst.getString( "mainadd" ) );
            bean.setMainphone( rst.getString( "mainphone" ) );
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
     * <br>���ܣ����ָ����λ���޵�ά�޵�λ�б�
     * <br>����:ָ����λ��id
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
     */
    public List getMainunitArr( String contractorid ){
        List lUser = null;
        String sql = " select distinct mainunit "
                     + " from toolmaintain "
                     + " where mainunit is not null and CONTRACTORID ='" + contractorid + "' and type='����'";

        try{
            QueryUtil query = new QueryUtil();
            lUser = query.queryBeans( sql );
            return lUser;
        }
        catch( Exception e ){
            logger.error( "���ָ����λ���޵�ά�޵�λ�б����:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>���ܣ����ָ����λ�������б�
     * <br>����:ָ����λ��id
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
     */
    public List getMainNameArr( String contractorid ){
        List lUser = null;
        String sql = " select distinct mainname "
                     + " from toolmaintain "
                     + " where mainname is not null and CONTRACTORID ='" + contractorid + "' and type='����'";

        try{
            QueryUtil query = new QueryUtil();
            lUser = query.queryBeans( sql );
            return lUser;
        }
        catch( Exception e ){
            logger.error( "���ָ����λ�������б����:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:��ָ��������õ�ǰ��½��ά��λ������������Ϣ
     * <br>����:��ά��λid
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     */
    public List getAllMainForSearch( ToolsInfoBean bean ){
        List useinfo = null;
        try{
            String sql = " select  tm.MAINID,tm.mainname,tm.mainunit,tm.mainphone,tm.CONTRACTORID,TO_CHAR(tm.time,'YYYY-MM-DD HH24') time,tm.MAINREMARK,u.USERNAME "
                         + " from toolmaintain tm,userinfo u "
                         + " where tm.USERID=u.userid and type='����' "
                         + " and tm.CONTRACTORID='" + bean.getContractorid() + "' ";

            if( !bean.getUserid().equals( "" ) && bean.getUserid() != null ){
                sql = sql + " and tm.userid = '" + bean.getUserid() + "' ";
            }
            if( !bean.getMainunit().equals( "" ) && bean.getMainunit() != null ){
                sql = sql + " and tm.mainunit = '" + bean.getMainunit() + "'  ";
            }
            if( !bean.getMainname().equals( "" ) && bean.getMainname() != null ){
                sql = sql + " and tm.mainname = '" + bean.getMainname() + "'  ";
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
            logger.error( "��ָ��������õ�ǰ��½��ά��λ������������Ϣ�쳣:" + e.getMessage() );
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
        String sql = " select ts.id,ts.mainnumber,tb.name,tb.type,tb.UNIT,tb.FACTORY,tb.STYLE,tb.TOOLUSE "
                     + " from toolstorage ts,tool_base tb "
                     + " where ts.id = tb.id and CONTRACTORID ='" + contractorid + "' and mainnumber>0";

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
     * <br>����:��õ�ǰ��½��ά��λ����ָ������������������Ϣ
     * <br>����:request
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     */
    public List getTool_Main( HttpServletRequest request ){
        List useinfo = null;
        String contractorid = ( String )request.getSession().getAttribute( "LOGIN_USER_DEPT_ID" );
        String id = request.getParameter( "id" );
        try{
            String sql = //
                " select  tm.MAINID,tm.CONTRACTORID,TO_CHAR(tm.time,'YYYY-MM-DD HH24') time,tm.MAINREMARK,u.USERNAME,tm.mainname,tm.mainunit,tm.mainphone"
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
            logger.error( "��õ�ǰ��½��ά��λ����ָ�����������������쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>���ܣ����ָ����λ��汸�����Ѿ����޵Ļ�����Ϣ����������
     * <br>����:ָ����λ��id
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
     */
    public List getToolBackInfo( String contractorid ){
        List lInfo = null;
        String sql = "select distinct st.ID,st.mainnumber,b.NAME,b.TYPE,b.UNIT "
                     + " from toolstorage st,tool_base b "
                     + " where st.ID = b.ID and st.CONTRACTORID ='" + contractorid + "' and mainnumber>0";
        try{
            QueryUtil query = new QueryUtil();
            lInfo = query.queryBeans( sql );
            return lInfo;
        }
        catch( Exception e ){
            logger.error( "���ָ����λ��汸�����Ѿ����޵Ļ�����Ϣ��������������:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:д�뱸�����������Ϣ,�������ޱ�,����_������Ӧ��,����
     * <br>����:������Ϣbean,����id����,������������
     * <br>����ֵ:��óɹ�����true,���򷵻� false;
     */
    public boolean addMianInfoBack( ToolsInfoBean bean, String[] id, String[] enumber ){
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
            sql1 = "insert into toolmaintain (mainid,contractorid,userid,time,mainremark,type,mainname,mainadd,mainphone,mainunit) values ('"
                   + mainid + "','" + bean.getContractorid() + "','" + bean.getUserid() + "',"
                   + "TO_DATE('" + strDt + "','YYYY-MM-DD HH24:MI:SS'),'" + bean.getMainremark() + "','���','"
                   + bean.getMainname() + "','" + bean.getMainadd() + "','" + bean.getMainphone() + "','"
                   + bean.getMainunit() + "')";
            try{
                exec.executeUpdate( sql1 ); //д�������
                for( int i = 0; i < id.length; i++ ){ //��������Ϣ�ֱ�д���Ӧ��Ϳ���
                    sql2 = "insert into toolmaintain_toolbase (mainid,id,enumber) values ('"
                           + mainid + "','" + id[i] + "'," + Integer.parseInt( enumber[i] ) + ")"; //д���Ӧ��
                    exec.executeUpdate( sql2 );

                    sql3 = " update toolstorage set mainnumber = mainnumber - " + Integer.parseInt( enumber[i] )
                           + ", essenumber = essenumber + " + Integer.parseInt( enumber[i] )
                           + "  where contractorid='" + bean.getContractorid() + "' and id ='" + id[i] + "'";
                    exec.executeUpdate( sql3 );
                }
                exec.commit();
                exec.setAutoCommitTrue();
                return true;
            }
            catch( Exception e1 ){
                logger.error( "д�����������Ϣ����:" + e1.getMessage() );
                exec.rollback();
                exec.setAutoCommitTrue();
                return false;
            }
        }
        catch( Exception e ){
            logger.error( "д�뱸�����������Ϣ,�������ޱ�,����_������Ӧ��,�������:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>����:д�뱸�����ޱ�����Ϣ,�������ޱ�,����_������Ӧ��,����
     * <br>����:������Ϣbean,����id����,������������
     * <br>����ֵ:��óɹ�����true,���򷵻� false;
     */
    public boolean addMianInfoDele( ToolsInfoBean bean, String[] id, String[] enumber ){
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
            sql1 = "insert into toolmaintain (mainid,contractorid,userid,time,mainremark,type,mainname,mainadd,mainphone,mainunit) values ('"
                   + mainid + "','" + bean.getContractorid() + "','" + bean.getUserid() + "',"
                   + "TO_DATE('" + strDt + "','YYYY-MM-DD HH24:MI:SS'),'" + bean.getMainremark() + "','����','"
                   + bean.getMainname() + "','" + bean.getMainadd() + "','" + bean.getMainphone() + "','"
                   + bean.getMainunit() + "')";
            try{
                exec.executeUpdate( sql1 ); //д�������
                for( int i = 0; i < id.length; i++ ){ //��������Ϣ�ֱ�д���Ӧ��Ϳ���
                    sql2 = "insert into toolmaintain_toolbase (mainid,id,enumber) values ('"
                           + mainid + "','" + id[i] + "'," + Integer.parseInt( enumber[i] ) + ")"; //д���Ӧ��
                    exec.executeUpdate( sql2 );

                    sql3 = " update toolstorage set mainnumber = mainnumber - " + Integer.parseInt( enumber[i] )
                           + ", shouldnumber = shouldnumber -" + Integer.parseInt( enumber[i] )
                           + "  where contractorid='" + bean.getContractorid() + "' and id ='" + id[i] + "'";
                    exec.executeUpdate( sql3 );
                }
                exec.commit();
                exec.setAutoCommitTrue();
                return true;
            }
            catch( Exception e1 ){
                logger.error( "д�����ޱ�����Ϣ����:" + e1.getMessage() );
                exec.rollback();
                exec.setAutoCommitTrue();
                return false;
            }
        }
        catch( Exception e ){
            logger.error( "д�뱸�����ޱ�����Ϣ,�������ޱ�,����_������Ӧ��,�������:" + e.getMessage() );
            return false;
        }
    }
    public List getToMainList( ToolsInfoBean bean ){
        List lSearchStock = null;
        List reqPart = null;
        try{
            String sql = " select  tm.MAINID,tm.mainname,tm.mainunit,tm.mainphone, tm.mainadd ,tm.CONTRACTORID,TO_CHAR(tm.time,'YYYY-MM-DD HH24') time,tm.MAINREMARK,u.USERNAME , con.CONTRACTORNAME "
                         + " from toolmaintain tm,userinfo u, contractorinfo con  "
                         + " where tm.USERID=u.userid and con.contractorid= tm.contractorid and type='����' "
                         + " and tm.CONTRACTORID='" + bean.getContractorid() + "' ";

            if( !bean.getUserid().equals( "" ) && bean.getUserid() != null ){
                sql = sql + " and tm.userid = '" + bean.getUserid() + "' ";
            }
            if( !bean.getMainunit().equals( "" ) && bean.getMainunit() != null ){
                sql = sql + " and tm.mainunit = '" + bean.getMainunit() + "'  ";
            }
            if( !bean.getMainname().equals( "" ) && bean.getMainname() != null ){
                sql = sql + " and tm.mainname = '" + bean.getMainname() + "'  ";
            }
            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                sql = sql + " and tm.time >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
            }
            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                sql = sql + " and tm.time <= TO_DATE('" + bean.getEndtime() + "','YYYY-MM-DD')";
            }
            sql = sql + " order by time desc";
//            //System.out.println( "query sql<<<<<<<<<<<<<<:" + sql );
            QueryUtil query = new QueryUtil();
            lSearchStock = query.queryBeans( sql );

            //��ò�����Ϣ���������������������������λ������ͺš���ⵥid
            String sql2 = " select tm.mainid, tm.ID,tm.ENUMBER,tb.name,tb.type,tb.unit,tb.STYLE,tb.FACTORY "
                         + " from toolmaintain_toolbase tm,tool_base tb "
                         + " where tm.ID = tb.ID "
                         + " and mainid in ( select tm.mainid"
                         + " from toolmaintain tm,userinfo u "
                         + " where tm.USERID=u.userid and type='����' "
                         + " and tm.CONTRACTORID='" + bean.getContractorid() + "' ";

            if( !bean.getUserid().equals( "" ) && bean.getUserid() != null ){
                sql2 = sql2 + " and tm.userid = '" + bean.getUserid() + "' ";
            }
            if( !bean.getMainunit().equals( "" ) && bean.getMainunit() != null ){
                sql2 = sql2 + " and tm.mainunit = '" + bean.getMainunit() + "'  ";
            }
            if( !bean.getMainname().equals( "" ) && bean.getMainname() != null ){
                sql2 = sql2 + " and tm.mainname = '" + bean.getMainname() + "'  ";
            }
            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                sql2 = sql2 + " and tm.time >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
            }
            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                sql2 = sql2 + " and tm.time <= TO_DATE('" + bean.getEndtime() + "','YYYY-MM-DD')";
            }
            sql2 += " ) ";
//            //System.out.println( "query sql2<<<<<<<<<<<<<<:" + sql2 );
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
