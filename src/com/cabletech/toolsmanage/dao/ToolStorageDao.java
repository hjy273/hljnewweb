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
     * <br>����:��õ�ǰ��½��ά��λ�����п����Ϣ(��ά��λ)
     * <br>����:����
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
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
            logger.error( "�ڻ�õ�ǰ��½��ά��λ�����п����Ϣ(��ά��λ)�з����쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:������п����Ϣ(�ƶ���˾)
     * <br>����:����
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
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
            logger.error( "�ڻ�õ�ǰ��½��ά��λ�����п����Ϣ(�ƶ���˾)�з����쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>���ܣ���ñ��������б��id�б�
     * <br>����:ָ����λ��id
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
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
            logger.error( "�ڻ�ñ��������б��id�б��г���:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>���ܣ���ñ��������б�
     * <br>����:ָ����λ��id
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
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
            logger.error( "��ñ��������б����:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>���ܣ���ô�ά��λ�б�
     * <br>����:ָ����λ��id
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
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
            logger.error( "��ô�ά��λ�б����:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:�����ƶ���˾ʱ,���ָ�����������п����Ϣ
     * <br>����:����
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
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
            logger.error( "�����ƶ���˾ʱ,���ָ�����������п����Ϣ�쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:���Ǵ�ά��λʱ,���ָ�����������п����Ϣ
     * <br>����:����
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
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
            logger.error( "���Ǵ�ά��λʱ,���ָ�����������п����Ϣ�쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>���ܣ���ñ�����Ϣ�б�
     * <br>����:ָ����λ��id
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
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
            logger.error( "��ñ�����Ϣ�б����:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:��ʼ����ǰ��½��ά��λ�ı������(ִ�з���:��ɾ����¼Ȼ��д��)
     * <br>����:������Ϣbean,����id����,���������,����������,����������
     * <br>����ֵ:��óɹ�����true,���򷵻� false;
     */
    public boolean initStorage( ToolsInfoBean bean, String[] id, String[] essenumber, String[] portmainnumber,
        String[] mainnumber ){
        String sql1 = ""; //ɾ����
        String sql2 = ""; //�����

        try{
            UpdateUtil exec = new UpdateUtil();
            exec.setAutoCommitFalse();
            try{

                for( int i = 0; i < id.length; i++ ){ //�������
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
                logger.error( "��ʼ������:" + e1.getMessage() );
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
