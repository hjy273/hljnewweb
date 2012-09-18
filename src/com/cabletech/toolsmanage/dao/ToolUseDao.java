package com.cabletech.toolsmanage.dao;

import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.http.*;

import org.apache.log4j.*;
import com.cabletech.commons.generatorID.impl.*;
import com.cabletech.commons.hb.*;
import com.cabletech.toolsmanage.beans.*;

public class ToolUseDao{
    private ToolsInfoBean bean;
    private static Logger logger = Logger.getLogger( ToolUseDao.class.
                                   getName() );

    public ToolUseDao( ToolsInfoBean bean ){
        this.bean = bean;
    }


    public ToolUseDao(){
    }


    /**
     * <br>���ܣ����ָ����λ��汸���Ļ�����Ϣ�ʹ������
     * <br>����:ָ����λ��id
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
     */
    public List getToolInfo( String contractorid ){
        List lInfo = null;
        String sql = "select distinct st.ID,st.ESSENUMBER,b.NAME,b.TYPE,b.UNIT "
                     + " from toolstorage st,tool_base b "
                     + " where st.ID = b.ID and st.CONTRACTORID ='" + contractorid + "'";
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
     * <br>����:д�뱸��������Ϣ,�������ñ�,����_������Ӧ��,����
     * <br>����:������Ϣbean,����id����,������������
     * <br>����ֵ:��óɹ�����true,���򷵻� false;
     */
    public boolean addUseInfo( ToolsInfoBean bean, String[] id, String[] enumber ){
        String sql1 = ""; //д��������
        String sql2 = ""; //д���Ӧ���
        String sql3 = ""; //д������
        java.util.Date date = new java.util.Date();
        SimpleDateFormat dtFormat = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss" );
        String strDt = dtFormat.format( date );

        OracleIDImpl ora = new OracleIDImpl();
        String useid = ora.getSeq( "tooluse", 10 );

        try{
            UpdateUtil exec = new UpdateUtil();
            exec.setAutoCommitFalse();
            sql1 = "insert into tooluse (useid,contractorid,userid,time,usename,useunit,useremark) values ('"
                   + useid + "','" + bean.getContractorid() + "','" + bean.getUserid() + "',"
                   + "TO_DATE('" + strDt + "','YYYY-MM-DD HH24:MI:SS'),'" + bean.getUsename() + "','" + bean.getUseunit()
                   + "','"
                   + bean.getUseremark() + "')";
            try{
                exec.executeUpdate( sql1 ); //д�������
                for( int i = 0; i < id.length; i++ ){ //��������Ϣ�ֱ�д���Ӧ��Ϳ���
                    sql2 = "insert into tooluse_toolbase (useid,id,enumber,bnumber) values ('"
                           + useid + "','" + id[i] + "'," + Integer.parseInt( enumber[i] ) + "," + "0 )"; //д���Ӧ��
                    exec.executeUpdate( sql2 );

                    sql3 = " update toolstorage set essenumber = essenumber - " + Integer.parseInt( enumber[i] )
                           + "  where contractorid='" + bean.getContractorid() + "' and id ='" + id[i] + "'";
                    exec.executeUpdate( sql3 );
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
            logger.error( "д�뱸��������Ϣ,�������ñ�,����_������Ӧ��,�����г���:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>����:��õ�ǰ��½��ά��λ������������Ϣ
     * <br>����:request
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     */
    public List getAllUse( HttpServletRequest request ){
        List useinfo = null;
        String contractorid = ( String )request.getSession().getAttribute( "LOGIN_USER_DEPT_ID" );
        try{
            String sql = "select distinct use.useid,use.useunit,con.CONTRACTORNAME,u.username,use.USENAME,TO_CHAR(use.time,'YYYY-MM-DD HH24') time,use.USEREMARK "
                         + " from tooluse use,contractorinfo con,userinfo u "
                         + " where use.USERID = u.USERID and use.CONTRACTORID = con.CONTRACTORID "
                         + " and use.CONTRACTORID='" + contractorid + "' "
                         + " order by time desc";
            QueryUtil query = new QueryUtil();
            useinfo = query.queryBeans( sql );
            return useinfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ�õ�ǰ��½��ά��λ������������Ϣ�з����쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:���ָ�����õ���Ϣ
     * <br>����:���õ�id
     * <br>����ֵ:��óɹ����ض���,���򷵻� NULL;
     */
    public ToolsInfoBean getOneUse( String useid ){
        ToolsInfoBean bean = new ToolsInfoBean();
        ResultSet rst = null;

        String sql = "select use.useid,u.USERNAME, con.CONTRACTORNAME,TO_CHAR(use.time,'YYYY-MM-DD HH24') time,use.usename,use.useunit,use.USEREMARK "
                     + " from tooluse use,contractorinfo con,userinfo u "
                     + " where use.USERID = u.USERID and use.CONTRACTORID = con.CONTRACTORID "
                     + " and use.useid='" + useid + "' ";
        try{
            QueryUtil query = new QueryUtil();
            rst = query.executeQuery( sql );
            rst.next();
            bean.setUseid( rst.getString( "useid" ) );
            bean.setUsername( rst.getString( "username" ) );
            bean.setContractorname( rst.getString( "contractorname" ) );
            bean.setTime( rst.getString( "time" ) );
            bean.setUsename( rst.getString( "usename" ) );
            bean.setUseunit( rst.getString( "useunit" ) );
            bean.setUseremark( rst.getString( "useremark" ) );
            rst.close();
            return bean;
        }
        catch( Exception e ){
            logger.error( "�ڻ��ָ�����õ���Ϣ�з����쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:���ָ�����õ��ı�����Ϣ
     * <br>����:���õ�id
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     */
    public List getToolsOfOneUse( String useid ){
        List partinfo = null;
        try{
            String sql = " select use.ID,use.USEID,use.ENUMBER,use.bnumber,b.NAME,b.UNIT,b.TYPE "
                         + " from tooluse_toolbase use,tool_base b "
                         + " where use.id = b.id and useid='" + useid + "'";
            QueryUtil query = new QueryUtil();
            partinfo = query.queryBeans( sql );
            return partinfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ��ָ�����õ��ı�����Ϣ�з����쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>���ܣ����ָ����λ���ò������б�
     * <br>����:ָ����λ��id
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
     */
    public List getUserArr( String contractorid ){
        List lUser = null;
        String sql = " select distinct use.userid,u.username "
                     + " from tooluse use,userinfo u "
                     + " where use.userid=u.userid and use.CONTRACTORID ='" + contractorid + "'";

        try{
            QueryUtil query = new QueryUtil();
            lUser = query.queryBeans( sql );
            return lUser;
        }
        catch( Exception e ){
            logger.error( "�ڻ��ָ����λ���ò������б��г���:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>���ܣ����ָ����λ�ı����������б�
     * <br>����:ָ����λ��id
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
     */
    public List getUseNameArr( String contractorid ){
        List lUser = null;
        String sql = " select distinct use.usename "
                     + " from tooluse use "
                     + " where use.usename is not null and use.CONTRACTORID ='" + contractorid + "'";

        try{
            QueryUtil query = new QueryUtil();
            lUser = query.queryBeans( sql );
            return lUser;
        }
        catch( Exception e ){
            logger.error( "�ڻ��ָ����λ�ı����������б��г���:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>���ܣ����ָ����λ�ı������õ�λ�б�
     * <br>����:ָ����λ��id
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
     */
    public List getUseUnitArr( String contractorid ){
        List lUser = null;
        String sql = " select distinct use.useunit "
                     + " from tooluse use "
                     + " where use.useunit is not null and use.CONTRACTORID ='" + contractorid + "'";

        try{
            QueryUtil query = new QueryUtil();
            lUser = query.queryBeans( sql );
            return lUser;
        }
        catch( Exception e ){
            logger.error( "�ڻ��ָ����λ�ı������õ�λ�б��г���:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>���ܣ����ָ����λ�ı�������ԭ���б�
     * <br>����:ָ����λ��id
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
     */
    public List getUseRemarkArr( String contractorid ){
        List lUser = null;
        String sql = " select distinct use.useremark "
                     + " from tooluse use "
                     + " where use.useremark is not null and  use.CONTRACTORID ='" + contractorid + "'";

        try{
            QueryUtil query = new QueryUtil();
            lUser = query.queryBeans( sql );
            return lUser;
        }
        catch( Exception e ){
            logger.error( "�ڻ��ָ����λ�ı�������ԭ���б��г���:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:��ָ��������õ�ǰ��½��ά��λ������������Ϣ
     * <br>����:��ά��λid
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     */
    public List getAllUseForSearch( ToolsInfoBean bean ){
        List useinfo = null;
        try{
            String sql = "select distinct use.useid,use.useunit,con.CONTRACTORNAME,u.username,use.USENAME,TO_CHAR(use.time,'YYYY-MM-DD HH24') time,use.USEREMARK "
                         + " from tooluse use,contractorinfo con,userinfo u "
                         + " where use.USERID = u.USERID and use.CONTRACTORID = con.CONTRACTORID "
                         + " and use.CONTRACTORID='" + bean.getContractorid() + "' ";
            if( !bean.getUserid().equals( "" ) && bean.getUserid() != null ){
                sql = sql + " and use.userid = '" + bean.getUserid() + "' ";
            }
            if( !bean.getUseunit().equals( "" ) && bean.getUseunit() != null ){
                sql = sql + " and use.useunit= '" + bean.getUseunit() + "'  ";
            }
            if( !bean.getUsename().equals( "" ) && bean.getUsename() != null ){
                sql = sql + " and use.usename = '" + bean.getUsename() + "'  ";
            }
            if( !bean.getUseremark().equals( "" ) && bean.getUseremark() != null ){
                sql = sql + " and use.useremark = '" + bean.getUseremark() + "'  ";
            }
            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                sql = sql + " and use.time >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
            }
            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                sql = sql + " and use.time <= TO_DATE('" + bean.getEndtime() + "','YYYY-MM-DD')";
            }
            if( !bean.getBack().equals( "" ) && bean.getBack() != null ){
                if( bean.getBack().equals( "1" ) ){ //ȫ���黹
                    sql = sql + " and use.useid in(select distinct useid from tooluse_toolbase where enumber=bnumber)";
                }
                if( bean.getBack().equals( "2" ) ){ //��δ�黹
                    sql = sql + " and use.useid in(select distinct useid from tooluse_toolbase where enumber>bnumber)";
                }
            }
            sql = sql + " order by  time desc ";
            logger.info("sql:"+sql);
            QueryUtil query = new QueryUtil();
            useinfo = query.queryBeans( sql );
            return useinfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ�õ�ǰ��½��ά��λ�����г�����Ϣ�з����쳣:" + e.getMessage() );
            return null;
        }
    }


    ////////////////////////////////////
    /**
     * <br>����:��ָ��������õ�ǰ��½��ά��λ�����д��黹���õ���Ϣ
     * <br>����:��ά��λid
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     */
    public List getAllUseForBack( ToolsInfoBean bean ){
        List useinfo = null;
        try{
            String sql = "select distinct use.useid,use.useunit,con.CONTRACTORNAME,u.username,use.USENAME,TO_CHAR(use.time,'YYYY-MM-DD HH24') time,use.USEREMARK "
                         + " from tooluse use,contractorinfo con,userinfo u "
                         + " where use.USERID = u.USERID and use.CONTRACTORID = con.CONTRACTORID "
                         + " and use.CONTRACTORID='" + bean.getContractorid() + "' ";
            if( !bean.getUserid().equals( "" ) && bean.getUserid() != null ){
                sql = sql + " and use.userid = '" + bean.getUserid() + "' ";
            }
            if( !bean.getUseunit().equals( "" ) && bean.getUseunit() != null ){
                sql = sql + " and use.useunit= '" + bean.getUseunit() + "'  ";
            }
            if( !bean.getUsename().equals( "" ) && bean.getUsename() != null ){
                sql = sql + " and use.usename = '" + bean.getUsename() + "'  ";
            }
            if( !bean.getUseremark().equals( "" ) && bean.getUseremark() != null ){
                sql = sql + " and use.useremark = '" + bean.getUseremark() + "'  ";
            }
            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                sql = sql + " and use.time >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
            }
            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                sql = sql + " and use.time <= TO_DATE('" + bean.getEndtime() + "','YYYY-MM-DD')";
            }
            sql = sql + " and use.useid in(select distinct useid from tooluse_toolbase where enumber>bnumber)";
            sql = sql + " order by  time desc ";
            logger.info("sql:"+sql);
            QueryUtil query = new QueryUtil();
            useinfo = query.queryBeans( sql );
            return useinfo;
        }
        catch( Exception e ){
            logger.error( "�ڰ�ָ��������õ�ǰ��½��ά��λ�����д��黹���õ���Ϣ�з����쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:д�뱸����������Ϣ,�������ñ�,����_������Ӧ��,����
     * <br>����:������Ϣbean,����id����,������������
     * <br>����ֵ:��óɹ�����true,���򷵻� false;
     */
    public boolean addBackInfo( ToolsInfoBean bean, String[] id, String[] bnumber ){
        String sql1 = ""; //д��������
        String sql2 = ""; //д���Ӧ���
        String sql3 = ""; //д������
        java.util.Date date = new java.util.Date();
        SimpleDateFormat dtFormat = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss" );
        String strDt = dtFormat.format( date );
        try{
            UpdateUtil exec = new UpdateUtil();
            exec.setAutoCommitFalse();
            sql1 = "update tooluse set buserid='" + bean.getUserid() + "',bname='" + bean.getBname() + "',bunit='"
                   + bean.getBunit()
                   + "',bremark='" + bean.getBremark() + "',btime=TO_DATE('" + strDt + "','YYYY-MM-DD HH24:MI:SS') "
                   + " where useid='" + bean.getUseid() + "'";
            try{
                exec.executeUpdate( sql1 ); //д�������
                for( int i = 0; i < id.length; i++ ){ //��������Ϣ�ֱ�д���Ӧ��Ϳ���
                    sql2 = "update tooluse_toolbase set bnumber= bnumber +" + Integer.parseInt( bnumber[i] )
                           + " where useid='" + bean.getUseid() + "' and id='" + id[i] + "'"; //д���Ӧ��
                    exec.executeUpdate( sql2 );

                    sql3 = " update toolstorage set essenumber = essenumber + " + Integer.parseInt( bnumber[i] )
                           + "  where contractorid='" + bean.getContractorid() + "' and id ='" + id[i] + "'";
                    exec.executeUpdate( sql3 );
                }
                exec.commit();
                exec.setAutoCommitTrue();
                return true;
            }
            catch( Exception e1 ){
                logger.error( "д�뷵����Ϣ����:" + e1.getMessage() );
                exec.rollback();
                exec.setAutoCommitTrue();
                return false;
            }
        }
        catch( Exception e ){
            logger.error( "д��д�뱸����������Ϣ,�������ñ�,����_������Ӧ��,�����г���:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>���ܣ����ָ����λ�Ĵ����������б�
     * <br>����:ָ����λ��id
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
     */
    public List getToolForShouldBack( String contractorid ){
        List lUser = null;
        String sql =
            "select s.id,b.name,b.unit,b.type,b.STYLE,b.type,b.TOOLUSE,(s.SHOULDNUMBER - s.ESSENUMBER) enumber "
            + " from toolstorage s,tool_base b "
            + " where s.id=b.id and s.SHOULDNUMBER>s.ESSENUMBER and s.CONTRACTORID ='" + contractorid + "'";

        try{
            QueryUtil query = new QueryUtil();
            lUser = query.queryBeans( sql );
            return lUser;
        }
        catch( Exception e ){
            logger.error( "���ָ����λ�Ĵ����������б����:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>���ܣ����ݴ�����������Ų������漰�����õ�
     * <br>����:ָ����λ��id
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
     */
    public List getAllShouldBackUse( ToolsInfoBean bean ){
        List lUser = null;
        String sql =
            " select u.username,tb.useid,tb.useunit,tb.usename,TO_CHAR(tb.time,'YYYY-MM-DD HH24') time,tb.useremark "
            + " from tooluse tb,userinfo u "
            + " where tb.userid = u.userid  and tb.contractorid='" + bean.getContractorid() + "' and  useid in("
            + "        select distinct useid from tooluse_toolbase where enumber>bnumber and id ='" + bean.getId()
            + "')";
        try{
            QueryUtil query = new QueryUtil();
            lUser = query.queryBeans( sql );
            return lUser;
        }
        catch( Exception e ){
            logger.error( "���ݴ�����������Ų������漰�����õ�:" + e.getMessage() );
            return null;
        }
    }

    public List getUseList( ToolsInfoBean bean ){
        List lSearchStock = null;
        List reqPart = null;
        try{
            String sql = "select distinct use.useid,use.useunit,con.CONTRACTORNAME,u.username,use.USENAME,TO_CHAR(use.time,'YYYY-MM-DD HH24') time,use.USEREMARK "
                         + " from tooluse use,contractorinfo con,userinfo u "
                         + " where use.USERID = u.USERID and use.CONTRACTORID = con.CONTRACTORID "
                         + " and use.CONTRACTORID='" + bean.getContractorid() + "' ";
            if( !bean.getUserid().equals( "" ) && bean.getUserid() != null ){
                sql = sql + " and use.userid = '" + bean.getUserid() + "' ";
            }
            if( !bean.getUseunit().equals( "" ) && bean.getUseunit() != null ){
                sql = sql + " and use.useunit= '" + bean.getUseunit() + "'  ";
            }
            if( !bean.getUsename().equals( "" ) && bean.getUsename() != null ){
                sql = sql + " and use.usename = '" + bean.getUsename() + "'  ";
            }
            if( !bean.getUseremark().equals( "" ) && bean.getUseremark() != null ){
                sql = sql + " and use.useremark = '" + bean.getUseremark() + "'  ";
            }
            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                sql = sql + " and use.time >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
            }
            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                sql = sql + " and use.time <= TO_DATE('" + bean.getEndtime() + "','YYYY-MM-DD')";
            }
            if( !bean.getBack().equals( "" ) && bean.getBack() != null ){
                if( bean.getBack().equals( "1" ) ){ //ȫ���黹
                    sql = sql + " and use.useid in(select distinct useid from tooluse_toolbase where enumber=bnumber)";
                }
                if( bean.getBack().equals( "2" ) ){ //��δ�黹
                    sql = sql + " and use.useid in(select distinct useid from tooluse_toolbase where enumber>bnumber)";
                }
            }
            sql = sql + " order by  time desc ";

      //      //System.out.println( "query sql<<<<<<<<<<<<<<:" + sql );
            QueryUtil query = new QueryUtil();
            lSearchStock = query.queryBeans( sql );

            //��ò�����Ϣ���������������������������λ������ͺš���ⵥid
            String sql2 = " select use.ID,use.USEID,use.ENUMBER,use.bnumber,b.NAME,b.UNIT,b.TYPE "
                         + " from tooluse_toolbase use,tool_base b "
                         + " where use.id = b.id "
                         + " and use.useid in ( select use.useid   "
                         + " from tooluse use,contractorinfo con,userinfo u "
                            + " where use.USERID = u.USERID and use.CONTRACTORID = con.CONTRACTORID "
                            + " and use.CONTRACTORID='" + bean.getContractorid() + "' ";
               if( !bean.getUserid().equals( "" ) && bean.getUserid() != null ){
                   sql2 = sql2 + " and use.userid = '" + bean.getUserid() + "' ";
               }
               if( !bean.getUseunit().equals( "" ) && bean.getUseunit() != null ){
                   sql2 = sql2 + " and use.useunit= '" + bean.getUseunit() + "'  ";
               }
               if( !bean.getUsename().equals( "" ) && bean.getUsename() != null ){
                   sql2 = sql2 + " and use.usename = '" + bean.getUsename() + "'  ";
               }
               if( !bean.getUseremark().equals( "" ) && bean.getUseremark() != null ){
                   sql2 = sql2 + " and use.useremark = '" + bean.getUseremark() + "'  ";
               }
               if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                   sql2 = sql2 + " and use.time >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
               }
               if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                   sql2 = sql2 + " and use.time <= TO_DATE('" + bean.getEndtime() + "','YYYY-MM-DD')";
               }
               if( !bean.getBack().equals( "" ) && bean.getBack() != null ){
                   if( bean.getBack().equals( "1" ) ){ //ȫ���黹
                       sql2 = sql2 + " and use.useid in(select distinct useid from tooluse_toolbase where enumber=bnumber)";
                   }
                   if( bean.getBack().equals( "2" ) ){ //��δ�黹
                       sql2 = sql2 + " and use.useid in(select distinct useid from tooluse_toolbase where enumber>bnumber)";
                   }
            }
            sql2 += " ) ";
    //        //System.out.println( "query sql2<<<<<<<<<<<<<<:" + sql2 );
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
//    getAllShouldBackUse( bean )
}
