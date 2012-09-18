package com.cabletech.partmanage.dao;

import java.util.*;
import javax.servlet.http.*;

import org.apache.log4j.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.hb.*;
import com.cabletech.partmanage.beans.*;
import com.cabletech.commons.sqlbuild.QuerySqlBuild;

public class PartStorageDao{
    private Part_requisitionBean bean;
    private static Logger logger = Logger.getLogger( PartStorageDao.class.
                                   getName() );

    public PartStorageDao(){
    }


    public PartStorageDao( Part_requisitionBean bean ){
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
            String sql = " select b.NAME,b.UNIT,b.TYPE ,st.NEWESSE,st.OLDNUMBER,st.newshould,con.contractorname "
                         + " from part_storage st,part_baseinfo b,contractorinfo con "
                         + " where st.id = b.ID  and st.contractorid = con.contractorid and st.contractorid='"
                         + contractorid + "' "
                         + " order  by name ";
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
     * <br>����:��õ�ǰ��������п����Ϣ(�ƶ���˾)
     * <br>����:����
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;   //
     */
    public List getAllStorageForDept( String regionid ){
        List useinfo = null;

        try{
            String sql = " select b.NAME,b.UNIT,b.TYPE ,st.NEWESSE,st.OLDNUMBER,st.newshould,con.contractorname "
                         + " from part_storage st,part_baseinfo b,contractorinfo con "
                         + " where st.id = b.ID  and st.contractorid = con.contractorid and con.regionid='" + regionid
                         + "'  "
                         + " order  by contractorname";
            QueryUtil query = new QueryUtil();
            useinfo = query.queryBeans( sql );
            return useinfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ�õ�ǰ�����ά��λ�����п����Ϣ(�ƶ���˾)�з����쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>���ܣ����I���������б��id�б�
     * <br>����:
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
     */
    public List getPartNameArr( String regoinid ){
        List lname = null;
        String sql = " select name,id from part_baseinfo where state is null and regionid='" + regoinid + "'";
        try{
            QueryUtil query = new QueryUtil();
            lname = query.queryBeans( sql );
            return lname;
        }
        catch( Exception e ){
            logger.error( "�ڻ��I���������б��id�б��г���:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>���ܣ����I���������б�
     * <br>����:ָ����λ��id
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
     */
    public List getPartTypeArr( String regionid ){
        List lType = null;
        String sql = " select distinct type from part_baseinfo  where state is null and regionid ='" + regionid + "'";
        try{
            QueryUtil query = new QueryUtil();
            lType = query.queryBeans( sql );
            return lType;
        }
        catch( Exception e ){
            logger.error( "�ڻ��I���������б��г���:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>���ܣ���ô�ά��λ�����б��id�б�
     * <br>����:request
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
     */
    public List getContractorNameArr( HttpServletRequest request ){
        List lname = null;
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        String sql = "";
        //���ƶ��û�
        if( userinfo.getDeptype().equals( "1" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
            sql = " select contractorid,contractorname from contractorinfo where state is null and regionid='"
                  + userinfo.getRegionID() + "'";
        }
        //�Ǵ�ά�û�
        if( userinfo.getDeptype().equals( "2" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
            sql = " select contractorid,contractorname from contractorinfo where state is null and contractorid='"
                  + userinfo.getDeptID() + "'";
        }
        //ʡ��ά�û�
        if( userinfo.getDeptype().equals( "2" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
            sql =
                " select contractorid,contractorname from contractorinfo where  state is null and parentcontractorid='"
                + userinfo.getDeptID() + "'";
        }
        //ʡ�ƶ��û�
        if( userinfo.getDeptype().equals( "1" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
            sql = " select contractorid,contractorname from contractorinfo where state is null";
        }
        try{
            QueryUtil query = new QueryUtil();
            lname = query.queryBeans( sql );
            return lname;
        }
        catch( Exception e ){
            logger.error( "�ڻ�ô�ά��λ�����б��id�б����:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:�����ƶ���˾ʱ,���ָ�����������п����Ϣ
     * <br>����:����
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     */
    public List getStorageForDept( Part_requisitionBean bean,
        String newlownumber,
        String newhignumber,
        String oldlownumber,
        String oldhignumber ){
        List partinfo = null;
        try{
            String sql = " select b.NAME,b.UNIT,b.TYPE ,st.NEWESSE,st.newshould,st.OLDNUMBER,con.contractorname "
                         + " from part_storage st,part_baseinfo b,contractorinfo con"
                         + " where st.id = b.ID and con.contractorid=st.contractorid  and con.regionid='"
                         + bean.getRegionid() + "' ";
            if( !bean.getId().equals( "" ) && bean.getId() != null ){
                sql = sql + " and con.CONTRACTORID='" + bean.getId() + "' ";
            }
            if( !bean.getName().equals( "" ) && bean.getName() != null ){
                sql = sql + " and b.name='" + bean.getName() + "' ";
            }
            if( !bean.getType().equals( "" ) && bean.getType() != null ){
                sql = sql + " and b.type = '" + bean.getType() + "'";
            }
            if( !bean.getcontractorid().equals( "" ) && bean.getcontractorid() != null ){
                sql = sql + " and st.contractorid = '" + bean.getcontractorid() + "'";
            }
            if( !newlownumber.equals( "" ) && newlownumber != null ){
                sql = sql + " and st.newesse >= " + Integer.parseInt( newlownumber );
            }
            if( !newhignumber.equals( "" ) && newhignumber != null ){
                sql = sql + " and st.newesse <= " + Integer.parseInt( newhignumber );
            }
            if( !oldlownumber.equals( "" ) && oldlownumber != null ){
                sql = sql + " and st.oldnumber >= " + Integer.parseInt( oldlownumber );
            }
            if( !oldhignumber.equals( "" ) && oldhignumber != null ){
                sql = sql + " and st.oldnumber <= " + Integer.parseInt( oldhignumber );
            }
            sql = sql + " order by contractorname";
            QueryUtil query = new QueryUtil();
            logger.info( "��ѯ���sql��" + sql );
            partinfo = query.queryBeans( sql );
            return partinfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ�õ�ǰ��½��ά��λ�����п����Ϣ�з����쳣:" + e.getMessage() );
            return null;
        }
    }

    /**
     * <br>����:���Ǵ�ά��λʱ,���ָ�����������п����Ϣ
     * <br>����:����
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     */
    public List getStorageForContractor( Part_requisitionBean bean,
        String newlownumber,
        String newhignumber,
        String oldlownumber,
        String oldhignumber,
        String contractorid ){
        List partinfo = null;
        try{
            String sql = " select b.NAME,b.UNIT,b.TYPE ,st.NEWESSE,st.newshould,st.OLDNUMBER,con.contractorname "
                         + " from part_storage st,part_baseinfo b,contractorinfo con"
                         + " where st.id = b.ID and con.contractorid = st.contractorid and st.contractorid='"
                         + contractorid + "'";
            if( !bean.getName().equals( "" ) && bean.getName() != null ){
                sql = sql + " and b.name='" + bean.getName() + "' ";
            }
            if( !bean.getType().equals( "" ) && bean.getType() != null ){
                sql = sql + " and b.type = '" + bean.getType() + "'";
            }
            if( !bean.getcontractorid().equals( "" ) && bean.getcontractorid() != null ){
                sql = sql + " and st.contractorid = '" + bean.getcontractorid() + "'";
            }
            if( !newlownumber.equals( "" ) && newlownumber != null ){
                sql = sql + " and st.newesse >= " + Integer.parseInt( newlownumber );
            }
            if( !newhignumber.equals( "" ) && newhignumber != null ){
                sql = sql + " and st.newesse <= " + Integer.parseInt( newhignumber );
            }
            if( !oldlownumber.equals( "" ) && oldlownumber != null ){
                sql = sql + " and st.oldnumber >= " + Integer.parseInt( oldlownumber );
            }
            if( !oldhignumber.equals( "" ) && oldhignumber != null ){
                sql = sql + " and st.oldnumber <= " + Integer.parseInt( oldhignumber );
            }
            sql = sql + " order by contractorname";
            QueryUtil query = new QueryUtil();
            logger.info( "��ѯ���sql: " + sql );
            partinfo = query.queryBeans( sql );
            return partinfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ�õ�ǰ��½��ά��λ�����п����Ϣ�з����쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:��ʼ����ǰ��½��ά��λ�Ĳ��Ͽ��(ִ�з���:��ɾ����¼Ȼ��д��)
     * <br>����:������Ϣbean,����id����,�ɲ�������oldnumber����,�²�������newesse����
     * <br>����ֵ:��óɹ�����true,���򷵻� false;
     */
    public boolean initStorage( Part_requisitionBean bean, String[] id, String[] oldnumber, String[] newesse ){
        String sql1 = ""; //ɾ����
        String sql2 = ""; //�����

        try{
            UpdateUtil exec = new UpdateUtil();
            exec.setAutoCommitFalse();
            try{

                for( int i = 0; i < id.length; i++ ){ //�������
                    sql1 = "delete from part_storage where contractorid='" + bean.getcontractorid() + "' and id='"
                           + id[i] + "'";
                    exec.executeUpdate( sql1 );

                    sql2 = "insert into part_storage (id,contractorid,newesse,oldnumber,newshould) values('" + id[i]
                           + "','" + bean.getcontractorid()
                           + "'," + Integer.parseInt( newesse[i] ) + "," + Integer.parseInt( oldnumber[i] ) + ","
                           + Integer.parseInt( newesse[i] ) + ")";
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

    /**
     * <br>����:��õ�ǰ��½��ά��λ�����п����Ϣ(��ά��λ)
     * <br>����:����
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     */
    public List getAllStorageForContractor(HttpServletRequest request ){
        List useinfo = null;
        UserInfo userinfo=(UserInfo)request.getSession().getAttribute("LOGIN_USER");
        String deptid=userinfo.getDeptID();
        try{
            String sql = " select b.NAME,b.UNIT,b.TYPE ,st.NEWESSE,st.OLDNUMBER,st.newshould,con.contractorname "
                         + " from part_storage st,part_baseinfo b,contractorinfo con ";
            QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );
            sqlBuild.addConstant(" st.id = b.ID  and st.contractorid = con.contractorid ");
            sqlBuild.addConditionAnd("st.regionid={0}",request.getParameter("regionid"));
            sqlBuild.addConditionAnd(" st.contractorid={0}",request.getParameter("contractorid"));
            sqlBuild.addConstant(" and ( st.id in ("
                +"                     select id from part_baseinfo where 1=1 ");
            sqlBuild.addConditionAnd(" name={0} ",request.getParameter("name"));
            sqlBuild.addConditionAnd(" type={0} ",request.getParameter("type"));
            sqlBuild.addConstant("     )"
                +"                 )");
            if(userinfo.getType().equals("21")){
                sqlBuild.addConditionAnd( " st.contractorid in ("
                    + "                         select contractorid from contractorinfo "
                    + "                         where parentcontractorid={0} and (state is null or state<>'1')"
                    + "                    )", deptid );
            }
            if(userinfo.getType().equals("12")){
                sqlBuild.addConditionAnd("st.regionid={0}",userinfo.getRegionID());
            }
            if(userinfo.getType().equals("22")){
                sqlBuild.addConditionAnd("st.contractorid={0}",userinfo.getDeptID());
            }
            if( !request.getParameter("newlownumber").equals( "" ) && request.getParameter("newlownumber") != null ){
                sqlBuild.addConditionAnd( " st.newesse >={0} ", Integer.parseInt( request.getParameter( "newlownumber" ) ) );
            }
            if( !request.getParameter("newhignumber").equals( "" ) && request.getParameter("newhignumber") != null ){
                sqlBuild.addConditionAnd( " st.newesse <={0} ", Integer.parseInt( request.getParameter( "newhignumber" ) ) );
            }
            if( !request.getParameter("oldlownumber").equals( "" ) && request.getParameter("oldlownumber") != null ){
                sqlBuild.addConditionAnd( " st.oldnumber >={0} ",
                    Integer.parseInt( request.getParameter( "oldlownumber" ) ) );
            }
            if( !request.getParameter("oldhignumber").equals( "" ) && request.getParameter("oldhignumber") != null ){
                sqlBuild.addConditionAnd( " st.oldnumber <={0} ",
                    Integer.parseInt( request.getParameter( "oldhignumber" ) ) );
            }
            sqlBuild.addConstant(" order by name");
            QueryUtil query = new QueryUtil();
            //System.out.println(sqlBuild.toSql());
            useinfo = query.queryBeans( sqlBuild.toSql() );
            return useinfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ�õ�ǰ��½��ά��λ�����п����Ϣ(��ά��λ)�з����쳣:" + e.getMessage() );
            return null;
        }
    }

    /**
     * <br>����:��õ�ǰ��������п����Ϣ(�ƶ���˾)
     * <br>����:����
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;   //
     */
    public List getAllStorageForDepart(HttpServletRequest request){
        List useinfo = null;
        UserInfo userinfo=(UserInfo)request.getSession().getAttribute("LOGIN_USER");
        String deptid=userinfo.getDeptID();
        try{
            String sql = " select b.NAME,b.UNIT,b.TYPE ,st.NEWESSE,st.OLDNUMBER,st.newshould,con.contractorname "
                         + " from part_storage st,part_baseinfo b,contractorinfo con ";
            QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );
            sqlBuild.addConstant("st.id = b.ID  and st.contractorid = con.contractorid ");
            sqlBuild.addConditionAnd(" con.regionid={0}",request.getParameter("regionid"));
            sqlBuild.addConstant(" and ( st.id in ("
                +"                     select id from part_baseinfo where 1=1 ");
            sqlBuild.addConditionAnd(" name={0} ",request.getParameter("name"));
            sqlBuild.addConditionAnd(" type={0} ",request.getParameter("type"));
            sqlBuild.addConstant("     )"
                +"                 )");
            if(userinfo.getType().equals("21")){
                sqlBuild.addConditionAnd( " st.contractorid in ("
                    + "                         select contractorid from contractorinfo "
                    + "                         where parentcontractorid={0} and (state is null or state<>'1')"
                    + "                    )", deptid );
            }
            if(userinfo.getType().equals("12")){
                sqlBuild.addConditionAnd("st.regionid={0}",userinfo.getRegionID());
            }
            if(userinfo.getType().equals("22")){
                sqlBuild.addConditionAnd("st.contractorid={0}",userinfo.getDeptID());
            }
            if( !request.getParameter("newlownumber").equals( "" ) && request.getParameter("newlownumber") != null ){
                sqlBuild.addConditionAnd( " st.newesse >={0} ", Integer.parseInt( request.getParameter( "newlownumber" ) ) );
            }
            if( !request.getParameter("newhignumber").equals( "" ) && request.getParameter("newhignumber") != null ){
                sqlBuild.addConditionAnd( " st.newesse <={0} ", Integer.parseInt( request.getParameter( "newhignumber" ) ) );
            }
            if( !request.getParameter("oldlownumber").equals( "" ) && request.getParameter("oldlownumber") != null ){
                sqlBuild.addConditionAnd( " st.oldnumber >={0} ",
                    Integer.parseInt( request.getParameter( "oldlownumber" ) ) );
            }
            if( !request.getParameter("oldhignumber").equals( "" ) && request.getParameter("oldhignumber") != null ){
                sqlBuild.addConditionAnd( " st.oldnumber <={0} ",
                    Integer.parseInt( request.getParameter( "oldhignumber" ) ) );
            }
            sqlBuild.addConstant(" order by contractorname");
            QueryUtil query = new QueryUtil();
            //System.out.println(sqlBuild.toSql());
            useinfo = query.queryBeans( sqlBuild.toSql() );
            return useinfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ�õ�ǰ�����ά��λ�����п����Ϣ(�ƶ���˾)�з����쳣:" + e.getMessage() );
            return null;
        }
    }
}
