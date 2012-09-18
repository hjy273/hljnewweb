package com.cabletech.partmanage.dao;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.commons.sqlbuild.QuerySqlBuild;
import com.cabletech.partmanage.beans.Part_requisitionBean;

public class PartUseDao{
    private static Logger logger = Logger.getLogger( PartUseDao.class.
                                   getName() );
    private Part_requisitionBean bean;
    public PartUseDao(){

    }


    public PartUseDao( Part_requisitionBean bean ){
        this.bean = bean;
    }


    /**
     * <br>���ܣ����ָ����λ�����ϵĻ�����Ϣ�ʹ������
     * <br>����:ָ����λ��id
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
     */
    public List getSrockedPartInfo( String contractorid ){
        List lInfo = null;
        String sql = " select distinct st.id,st.NEWESSE,st.OLDNUMBER,b.NAME,b.TYPE,b.UNIT "
                     + " from part_storage st,part_baseinfo b "
                     + " where st.ID = b.id and st.CONTRACTORID ='" + contractorid + "'"
                     // add by guixy ɸ�����;ɿ�涼Ϊ�յ�
                     + "and (st.newesse > 0 or st.oldnumber > 0)";
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
     * <br>����:д����ϳ�����Ϣ,�������ⵥ��,����_���϶�Ӧ��,����
     * <br>����:���ϻ�����Ϣbean,����id����,����useoldnumber����,����usenewnumber����
     * <br>����ֵ:��óɹ�����true,���򷵻� false;
     */
    public boolean addUseInfo( Part_requisitionBean bean, String[] id, String[] useoldnumber, String[] usenewnumber, 
    						String[] linecutStr, String[] changeStr, String objId){
        String sql1 = ""; //д��������
        String sql2 = ""; //д���Ӧ���
        String sql3 = ""; //д������
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDt = sdf.format(date);

        try{
        	String[][] tmp = null;
        	String sql4;
        	OracleIDImpl ora = new OracleIDImpl();
        	
            UpdateUtil exec = new UpdateUtil();
            exec.setAutoCommitFalse();
            sql1 = "insert into part_use (useid,contractorid,useuserid,usetime,usereason,useremark) values ('"
                   + bean.getUseid() + "','" + bean.getcontractorid() + "','" + bean.getUseuserid() + "',"
                   + "TO_DATE('" + strDt + "','yyyy-MM-dd hh24:mi:ss'),'" + bean.getUsereason() + "','"
                   + bean.getUseremark() + "')";
            try{
                exec.executeUpdate( sql1 ); //д�������
                for( int i = 0; i < id.length; i++ ){ //��������Ϣ�ֱ�д���Ӧ��Ϳ���

                    sql2 = "insert into part_use_baseinfo (useid,id,usenewnumber,useoldnumber) values ('"
                           + bean.getUseid() + "','" + id[i] + "'," + Integer.parseInt( usenewnumber[i] ) + ","
                           + Integer.parseInt( useoldnumber[i] )
                           + ")"; //д���Ӧ��
                    exec.executeUpdate( sql2 );
                    sql3 = " update part_storage set newesse = newesse - " + Integer.parseInt( usenewnumber[i] )
                           + ",oldnumber=oldnumber - " + Integer.parseInt( useoldnumber[i] )
                           + ",newshould = newshould - " + Integer.parseInt( usenewnumber[i] )
                           + "  where contractorid='" + bean.getcontractorid() + "' and id ='" + id[i] + "'";
                    exec.executeUpdate( sql3 );
                    // д���϶�Ӧ����;�� add by guixy 2008-11-20
                    // ���
                    tmp = pareUseobjstr(linecutStr[i]);
                    if (tmp != null) {                    	
						for (int j = 0; j < tmp.length; j++) {
							if (tmp[j][0] == null) {
								continue;								
							}
							sql4 = "insert into part_useobject_baseinfo(id, useid, baseid, objectid, objectnum, objecttype) values("
								+ "'" + objId + "', "
								+ "'" + bean.getUseid() + "',"
								+ "'" + id[i] + "', "
								+ "'" + tmp[j][0] + "', "
								+ tmp[j][1] + ", '0')" ;
							exec.executeUpdate( sql4 );
						}
					}
                    // ����
                    tmp = pareUseobjstr(changeStr[i]);
                    if (tmp != null) {                    	
						for (int j = 0; j < tmp.length; j++) {
							if (tmp[j][0] == null) {
								continue;								
							}
							sql4 = "insert into part_useobject_baseinfo(id, useid, baseid, objectid, objectnum, objecttype) values("
								+ "'" + objId + "', "
								+ "'" + bean.getUseid() + "',"
								+ "'" + id[i] + "', "
								+ "'" + tmp[j][0] + "', "
								+ tmp[j][1] + ", '1')" ;
							exec.executeUpdate( sql4 );
						}
					}
                }
                exec.commit();
                exec.setAutoCommitTrue();
                return true;
            }
            catch( Exception e1 ){
                logger.error( "д�������Ϣ����:" + e1.getMessage() );
                exec.rollback();
                exec.setAutoCommitTrue();
                return false;
            }
        }
        catch( Exception e ){
            logger.error( "д��д����ϳ�����Ϣ,�������ⵥ��,����_���϶�Ӧ��,�����г���:" + e.getMessage() );
            return false;
        }
    }
    
    /**
     * ����������;�ַ���
     * @param useobjstr
     * @return
     */
    public String[][] pareUseobjstr(String useobjstr) {
    	if("".equals(useobjstr) || ";".equals(useobjstr)) {
    		return null;
    	}
    	String[] element = useobjstr.split(";");  
    	String[][] objArray = new String[element.length][2];
		for (int i = 0; i < element.length; i++) {
			if(!"".equals(element[i].trim())) {
    			
				String[] e = element[i].split(":");
				objArray[i][0] = e[0].split("#")[1];
				objArray[i][1] = e[1];
			//	objArray[i][2] = i+""; // 0��ʾ��ӣ�1������
			}
		}
		
    	return objArray;
    	
    }


    /**
     * <br>����:��õ�ǰ��½��ά��λ�����г�����Ϣ
     * <br>����:��ά��λid
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     */
    public List getAllUse( HttpServletRequest request ){
        List useinfo = null;
        UserInfo userinfo=(UserInfo)request.getSession().getAttribute("LOGIN_USER");
        String deptid = ( String )request.getSession().getAttribute( "LOGIN_USER_DEPT_ID" );
        try{
            String sql = "select distinct use.useid,con.CONTRACTORNAME,u.USERNAME,TO_CHAR(use.usetime,'YYYY-MM-DD') usetime,use.USEREASON,use.USEREMARK "
                         + " from part_use use,contractorinfo con,userinfo u "
                         + " where use.USEUSERID = u.USERID and use.CONTRACTORID = con.CONTRACTORID ";
            if(userinfo.getType()!=null&&userinfo.getType().equals("22"))
                sql+= " and use.CONTRACTORID='" + deptid + "' ";
            if(userinfo.getType()!=null&&userinfo.getType().equals("12"))
                sql+=" and use.contractorid in (select contractorid from contractorinfo where regionid='"+userinfo.getRegionID()+"')";
            if(userinfo.getType()!=null&&userinfo.getType().equals("21"))
                sql+=" and use.contractorid in (select contractorid from contractorinfo where parentcontractorid='"+deptid+"' or contractorid='"+deptid+"')";

            sql+= " order by usetime desc";
            QueryUtil query = new QueryUtil();
            useinfo = query.queryBeans( sql );
            return useinfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ�õ�ǰ��½��ά��λ�����г�����Ϣ�з����쳣:" + e.getMessage() );
            return null;
        }
    }

    /**
     * <br>����:��õ�ǰ��½��ά��λ�����г�����Ϣ
     * <br>����:��ά��λid
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     */
    public List getAllUsePart( HttpServletRequest request ){
        List useinfo = null;
        UserInfo userinfo=(UserInfo)request.getSession().getAttribute("LOGIN_USER");
        String deptid=userinfo.getDeptID();
        try{
            String sql = "select distinct use.useid,con.CONTRACTORNAME,u.USERNAME,TO_CHAR(use.usetime,'YYYY-MM-DD') usetime,use.USEREASON,use.USEREMARK "
                         + " from part_use use,contractorinfo con,userinfo u ";
            QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );
            sqlBuild.addConstant(" use.USEUSERID = u.USERID and use.CONTRACTORID = con.CONTRACTORID ");
            sqlBuild.addConditionAnd("con.regionid={0}",request.getParameter("regionid"));
            sqlBuild.addConditionAnd(" use.contractorid={0}",request.getParameter("contractorid"));
            sqlBuild.addConstant(" and ( use.useid in ("
                +"                   select useid from part_stock_baseinfo where id in ("
                +"                     select id from part_baseinfo where 1=1 ");
            sqlBuild.addConditionAnd(" name={0} ",request.getParameter("name"));
            sqlBuild.addConditionAnd(" type={0} ",request.getParameter("type"));
            sqlBuild.addConstant("     )"
               +"                   )"
               +"                 )");
            if(userinfo.getType().equals("21")){
                sqlBuild.addConditionAnd( " use.contractorid in ("
                    + "                         select contractorid from contractorinfo "
                    + "                         where parentcontractorid={0} and (state is null or state<>'1')"
                    + "                    )", deptid );
            }
            if(userinfo.getType().equals("12")){
                sqlBuild.addConditionAnd("use.contractorid in ("
                    +"                      select contractorid from contractorinfo "
                    +"                      where regionid={0} and (state is null or state<>'1')"
                    +"                    )",userinfo.getRegionID());
            }
            if(userinfo.getType().equals("22")){
                sqlBuild.addConditionAnd("user.contractorid={0}",userinfo.getDeptID());
            }
            sqlBuild.addConstant(" order by usetime desc");
            QueryUtil query = new QueryUtil();
            //System.out.println(sqlBuild.toSql());
            useinfo = query.queryBeans( sqlBuild.toSql() );
            return useinfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ�õ�ǰ��½��ά��λ�����г�����Ϣ�з����쳣:" + e.getMessage() );
            return null;
        }
    }

    /**
     * <br>����:���ָ����ⵥ��Ϣ
     * <br>����:��ⵥid
     * <br>����ֵ:��óɹ����ض���,���򷵻� NULL;
     */
    public Part_requisitionBean getOneUse( String useid ){
        Part_requisitionBean bean = new Part_requisitionBean();
        ResultSet rst = null;

        String sql = "select distinct use.useid,con.CONTRACTORNAME,u.USERNAME,TO_CHAR(use.usetime,'yyyy-MM-dd hh24:mi:ss') usetime,use.USEREASON,use.USEREMARK "
                     + " from part_use use,contractorinfo con,userinfo u "
                     + " where use.USEUSERID = u.USERID and use.CONTRACTORID = con.CONTRACTORID "
                     + " and use.useid='" + useid + "' ";
        try{
        	logger.info("�鿴���ⵥsql:"+sql);
            QueryUtil query = new QueryUtil();
            rst = query.executeQuery( sql );
            rst.next();
            bean.setUseid( rst.getString( "useid" ) );
            bean.setContractorname( rst.getString( "contractorname" ) );
            bean.setUsername( rst.getString( "username" ) );
            bean.setUsetime( rst.getString( "usetime" ) );
            bean.setUsereason( rst.getString( "usereason" ) );
            bean.setUseremark( rst.getString( "useremark" ) );
            rst.close();
            return bean;
        }
        catch( Exception e ){
            logger.error( "�ڻ��ָ����ⵥ��Ϣ�з����쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:���ָ�����ⵥ�Ĳ�����Ϣ
     * <br>����:���ⵥid
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     */
    public List getPartOfOneUse( String useid ){
        List partinfo = null;
        try{
            String sql = "select ub.USEID,ub.id,ub.USENEWNUMBER,ub.useoldnumber,b.name,b.type,b.unit "
                         + " from part_use_baseinfo ub,part_baseinfo b "
                         + " where ub.id = b.id and ub.useid='" + useid + "' "
                         + " order by b.name desc";
            QueryUtil query = new QueryUtil();
            partinfo = query.queryBeans( sql );
            return partinfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ��ָ�����ⵥ�Ĳ�����Ϣ�з����쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>���ܣ����ָ����λ������Ա�б�
     * <br>����:ָ����λ��id
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
     */
    public List getUserArr( String contractorid ){
        List lUser = null;
        String sql = " select distinct use.useuserid,u.username "
                     + " from part_use use,userinfo u "
                     + " where use.useuserid=u.userid and use.CONTRACTORID ='" + contractorid + "'";

        try{
            QueryUtil query = new QueryUtil();
            lUser = query.queryBeans( sql );
            return lUser;
        }
        catch( Exception e ){
            logger.error( "�ڻ��ָ����λ������Ա�б��г���:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>���ܣ����ָ����λ������;�б�
     * <br>����:ָ����λ��id
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
     */
    public List getReasonArr( String contractorid ){
        List lUser = null;
        String sql = null;
        if (contractorid == null) {
        	sql = " select distinct usereason from part_use ";
		} else {
			sql = " select distinct usereason from part_use  where CONTRACTORID ='" + contractorid + "'";
		}

        try{
            QueryUtil query = new QueryUtil();
            lUser = query.queryBeans( sql );
            return lUser;
        }
        catch( Exception e ){
            logger.error( "�ڻ��ָ����λ������;�б��г���:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>���ܣ�������е�λ������;�б�
     * <br>����:
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
     */
    public List getAllReasonArr( String regionid ){
        List lUser = null;
        String sql =
            " select distinct p.usereason from part_use p,contractorinfo con where con.contractorid=p.contractorid "
            + " and con.regionid='" + regionid + "'";
        try{
            QueryUtil query = new QueryUtil();
            lUser = query.queryBeans( sql );
            return lUser;
        }
        catch( Exception e ){
            logger.error( "�ڻ�����е�λ������;�б��г���:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:��ָ��������õ�ǰ��½��ά��λ�����г�����Ϣ
     * <br>����:��ά��λid
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     */
    public List getAllUseForSearch( Part_requisitionBean bean ){
        List useinfo = null;
        try{
            String sql = "select distinct use.useid,con.CONTRACTORNAME,u.USERNAME,TO_CHAR(use.usetime,'YYYY-MM-DD') usetime,use.USEREASON,use.USEREMARK "
                         + " from part_use use,contractorinfo con,userinfo u "
                         + " where use.USEUSERID = u.USERID and use.CONTRACTORID = con.CONTRACTORID "
                         + " and use.CONTRACTORID='" + bean.getcontractorid() + "' ";
            if( !bean.getUseuserid().equals( "" ) && bean.getUseuserid() != null ){
                sql = sql + " and u.username like '" + bean.getUseuserid() + "%' ";
            }
            if( !bean.getUsereason().equals( "" ) && bean.getUsereason() != null ){
                sql = sql + " and use.usereason like '" + bean.getUsereason() + "%'  ";
            }
            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                sql = sql + " and use.usetime >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
            }
            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                sql = sql + " and use.usetime <= TO_DATE('" + bean.getEndtime() + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
            }
            sql = sql + " order by  usetime desc ";
            QueryUtil query = new QueryUtil();
            useinfo = query.queryBeans( sql );
            return useinfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ�õ�ǰ��½��ά��λ�����г�����Ϣ�з����쳣:" + e.getMessage() );
            return null;
        }
    }

    public List getAllUseForSearch( Part_requisitionBean bean ,UserInfo userInfo){
        List useinfo = null;
        try{
            String sql = "select distinct use.useid,con.CONTRACTORNAME,u.USERNAME,TO_CHAR(use.usetime,'YYYY-MM-DD') usetime,use.USEREASON,use.USEREMARK "
                         + " from part_use use,contractorinfo con,userinfo u "
                         + " where use.USEUSERID = u.USERID and use.CONTRACTORID = con.CONTRACTORID ";
                         //+ " and use.CONTRACTORID='" + bean.getcontractorid() + "' ";
            
      
            // modify by guixy 2008-12-6
            if("1".equals(userInfo.getDeptype())) {
                if (bean.getcontractorid() == null || "".equals(bean.getcontractorid().trim()) ) { 
                   sql+=" and use.contractorid in (select contractorid from contractorinfo where regionid='"+userInfo.getRegionID()+"')";
    			} else {
    			   sql+= " and use.CONTRACTORID='" + bean.getcontractorid() + "' ";
    			}
            	
            } else {
            	if(userInfo.getType()!=null&&userInfo.getType().equals("22"))
                    sql+= " and use.CONTRACTORID='" + bean.getcontractorid() + "' ";
                if(userInfo.getType()!=null&&userInfo.getType().equals("12"))
                    sql+=" and use.contractorid in (select contractorid from contractorinfo where regionid='"+userInfo.getRegionID()+"')";
                if(userInfo.getType()!=null&&userInfo.getType().equals("21"))
                    sql+=" and use.contractorid in (select contractorid from contractorinfo where parentcontractorid='"+bean.getcontractorid()+"' or contractorid='"+bean.getcontractorid()+"')";
            }

            if( !bean.getUseuserid().equals( "" ) && bean.getUseuserid() != null ){
                sql = sql + " and u.username like '" + bean.getUseuserid() + "%' ";
            }
            if( !bean.getUsereason().equals( "" ) && bean.getUsereason() != null ){
                sql = sql + " and use.usereason like '" + bean.getUsereason() + "%'  ";
            }
            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                sql = sql + " and use.usetime >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
            }
            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                sql = sql + " and use.usetime <= TO_DATE('" + bean.getEndtime() + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
            }
            sql = sql + " order by  usetime desc ";
            QueryUtil query = new QueryUtil();
            useinfo = query.queryBeans( sql );
            return useinfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ�õ�ǰ��½��ά��λ�����г�����Ϣ�з����쳣:" + e.getMessage() );
            return null;
        }
    }

    //////////////////////////ʹ��ͳ��/////////////////////////////
    /**
     * <br>���ܣ���ò���ʹ�ñ��������漰�ĵ�λ���ƺ͵�λid�б�
     * <br>����:
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
     */
    public List getDeptArr( String regionid ){
        List lUser = null;
        String sql = "select distinct us.CONTRACTORID,con.CONTRACTORNAME "
                     + "from part_use us,contractorinfo con "
                     + " where us.CONTRACTORID = con.CONTRACTORID and (con.state is null or con.state<>'1')";
        sql+=" and regionid='"+regionid+"'";
        try{
            QueryUtil query = new QueryUtil();
            lUser = query.queryBeans( sql );
            return lUser;
        }
        catch( Exception e ){
            logger.error( "�ڻ�ò���ʹ�ñ��������漰�ĵ�λ���ƺ͵�λid�б��г���:" + e.getMessage() );
            return null;
        }
    }

    /**
     * <br>����:��ָ��������õ�ǰ��½��ά��λ�� ����ʹ�����ͳ��(��ά��λ�Ĳ�ѯ)
     * <br>����:��ά��λid,bean
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     */
    public List getAllPartUse( Part_requisitionBean bean, String contractorid ){
        List useinfo = null;
        try{
            String sql =
                "select distinct us.ID,con.CONTRACTORNAME,b.NAME,b.TYPE,b.UNIT,us.USENEWNUMBER,us.USEOLDNUMBER,TO_CHAR(pus.USETIME,'yyyy-MM-dd') usetime "
                + " from part_use_baseinfo us,part_use pus,part_baseinfo b,contractorinfo con "
                + " where us.ID = b.id and us.USEID = pus.USEID and con.CONTRACTORID = pus.CONTRACTORID "
                + " and pus.CONTRACTORID='" + contractorid + "' ";
            if( !bean.getName().equals( "" ) && bean.getName() != null ){
                sql = sql + " and b.name = '" + bean.getName() + "' ";
            }
            if( !bean.getType().equals( "" ) && bean.getType() != null ){
                sql = sql + " and b.type = '" + bean.getType() + "'  ";
            }
            if( !bean.getFactory().equals( "" ) && bean.getFactory() != null ){
                sql = sql + " and b.factory = '" + bean.getFactory() + "'  ";
            }
            if( !bean.getUsereason().equals( "" ) && bean.getUsereason() != null ){
                sql = sql + " and pus.usereason = '" + bean.getUsereason() + "'  ";
            }
            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                sql = sql + " and pus.usetime >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
            }
            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                sql = sql + " and pus.usetime <= TO_DATE('" + bean.getEndtime() + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
            }
            sql = sql + " order by con.CONTRACTORNAME,usetime,b.NAME ";
            logger.warn("SQL:" + sql);
            QueryUtil query = new QueryUtil();
            useinfo = query.queryBeans( sql );
            return useinfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ�õ�ǰ��½��ά��λ�����г�����Ϣ�з����쳣:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:��ָ��������� ����ʹ�����ͳ��(�ƶ���˾�Ĳ�ѯ)
     * <br>����:��ά��λid,bean
     * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
     */
    public List getAllPartUse( Part_requisitionBean bean ){
        List useinfo = null;
        try{
            String sql =
                "select distinct us.ID,con.CONTRACTORNAME,b.NAME,b.TYPE,b.UNIT,us.USENEWNUMBER,us.USEOLDNUMBER,TO_CHAR(pus.USETIME,'yyyy-MM-dd') usetime "
                + " from part_use_baseinfo us,part_use pus,part_baseinfo b,contractorinfo con "
                +
                " where us.ID = b.id and us.USEID = pus.USEID and con.CONTRACTORID = pus.CONTRACTORID and con.regionid='"
                + bean.getRegionid() + "' ";
            if( !bean.getcontractorid().equals( "" ) && bean.getcontractorid() != null ){
                sql = sql + " and pus.contractorid = '" + bean.getcontractorid() + "' ";
            }
            if( !bean.getName().equals( "" ) && bean.getName() != null ){
                sql = sql + " and b.name = '" + bean.getName() + "' ";
            }
            if( !bean.getType().equals( "" ) && bean.getType() != null ){
                sql = sql + " and b.type = '" + bean.getType() + "'  ";
            }
            if( !bean.getFactory().equals( "" ) && bean.getFactory() != null ){
                sql = sql + " and b.factory = '" + bean.getFactory() + "'  ";
            }
            if( !bean.getUsereason().equals( "" ) && bean.getUsereason() != null ){
                sql = sql + " and pus.usereason = '" + bean.getUsereason() + "'  ";
            }
            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                sql = sql + " and pus.usetime >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
            }
            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                sql = sql + " and pus.usetime <= TO_DATE('" + bean.getEndtime() + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
            }
            sql = sql + " order by  con.CONTRACTORNAME,usetime,b.NAME ";
            QueryUtil query = new QueryUtil();
            useinfo = query.queryBeans( sql );
            return useinfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ�õ�ǰ��½��ά��λ�����г�����Ϣ��(yidong)�����쳣:" + e.getMessage() );
            return null;
        }
    }


    ////////////////�˿⴦��////////////////
    /**
     * <br>���ܣ����ָ����λ������;�б�(Ϊ�˿�ʹ�õ�)
     * <br>����:ָ����λ��id
     * <br>����ֵ:��óɹ�����List,���򷵻� null;
     */
    public List getReasonArrForBack( String contractorid ){
        List lUser = null;
        String sql = " select useid, usereason from part_use  where CONTRACTORID ='" + contractorid + "'";
        try{
            QueryUtil query = new QueryUtil();
            lUser = query.queryBeans( sql );
            return lUser;
        }
        catch( Exception e ){
            logger.error( "�ڻ��ָ����λ������;�б�(Ϊ�˿�ʹ�õ�)�г���:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>����:�˿⴦��,���³��ⵥ_���϶�Ӧ��ĳ�������,���¿�����Ϳ������ƷӦ����
     * <br>����:���ϻ�����Ϣbean,����id����,����useoldnumber����,����usenewnumber����
     * <br>����ֵ:��óɹ�����true,���򷵻� false;
     */
    public boolean backStock( String contractorid, String useid, String[] id, String[] newbacknumber,
        String[] oldbacknumber ){
        String sql1 = ""; //���³��ⵥ_���϶�Ӧ��
        String sql2 = ""; //���¿�����Ϳ������ƷӦ����
        try{
            UpdateUtil exec = new UpdateUtil();
            exec.setAutoCommitFalse();
            try{
                for( int i = 0; i < id.length; i++ ){
                    sql1 = "update part_use_baseinfo set usenewnumber = usenewnumber - "
                           + Integer.parseInt( newbacknumber[i] )
                           + ", useoldnumber = useoldnumber - " + Integer.parseInt( oldbacknumber[i] )
                           + " where useid = '" + useid + "' and id = '" + id[i] + "'";
                    exec.executeUpdate( sql1 );

                    sql2 = "update part_storage set newesse = newesse + " + Integer.parseInt( newbacknumber[i] )
                           + ",oldnumber = oldnumber + "
                           + Integer.parseInt( oldbacknumber[i] ) + ",newshould = newshould  + "
                           + Integer.parseInt( newbacknumber[i] )
                           + " where id='" + id[i] + "' and contractorid = '" + contractorid + "'";
                    exec.executeUpdate( sql2 );
                }
                exec.commit();
                exec.setAutoCommitTrue();
                return true;
            }
            catch( Exception e1 ){
                logger.error( "�˿⴦�����:" + e1.getMessage() );
                exec.rollback();
                exec.setAutoCommitTrue();
                return false;
            }
        }
        catch( Exception e ){
            logger.error( "�˿⴦��,���³��ⵥ_���϶�Ӧ��ĳ�������,���¿�����Ϳ������ƷӦ�����г���:" + e.getMessage() );
            return false;
        }
    }

    /**
     * ���ָ���ĳ��ⵥ��Ϣ�Ͳ�����Ϣ
     * @param bean Part_requisitionBean
     * @return List
     */
    public List getUseList( Part_requisitionBean bean ){
        List Useinfo = null;
        List Partinfo = null;
        try{
            //���
            String sql = "select distinct use.useid,con.CONTRACTORNAME,u.USERNAME,TO_CHAR(use.usetime,'YYYY-MM-DD') usetime,use.USEREASON,use.USEREMARK "
                         + " from part_use use,contractorinfo con,userinfo u "
                         + " where use.USEUSERID = u.USERID and use.CONTRACTORID = con.CONTRACTORID "
                         + " and use.CONTRACTORID='" + bean.getcontractorid() + "' ";
            if( !bean.getUseuserid().equals( "" ) && bean.getUseuserid() != null ){
                sql = sql + " and use.useuserid = '" + bean.getUseuserid() + "' ";
            }
            if( !bean.getUsereason().equals( "" ) && bean.getUsereason() != null ){
                sql = sql + " and use.usereason = '" + bean.getUsereason() + "'  ";
            }
            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                sql = sql + " and use.usetime >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
            }
            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                sql = sql + " and use.usetime <= TO_DATE('" + bean.getEndtime() + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
            }
            sql = sql + " order by  usetime desc ";
//            System.out.println( "query sql<<<<<<<<<<<<<<:" + sql );
            QueryUtil query = new QueryUtil();
            Useinfo = query.queryBeans( sql );

            //��ò�����Ϣ���������ơ��²��ϳ��⡢�ɲ��ϳ��⡢������λ������ͺ�
            String sql2 = "select ub.USEID,ub.id,ub.USENEWNUMBER,ub.useoldnumber,b.name,b.type,b.unit "
                         + " from part_use_baseinfo ub,part_baseinfo b "
                         + " where ub.id = b.id and "
                         + " ub.useid in( select use.useid "
                         + " from part_use use,contractorinfo con,userinfo u "
                         + " where use.USEUSERID = u.USERID and use.CONTRACTORID = con.CONTRACTORID "
                         + " and use.CONTRACTORID='" + bean.getcontractorid() + "' ";

            if( !bean.getUseuserid().equals( "" ) && bean.getUseuserid() != null ){
                sql2 = sql2 + " and use.useuserid = '" + bean.getUseuserid() + "' ";
            }
            if( !bean.getUsereason().equals( "" ) && bean.getUsereason() != null ){
                sql2 = sql2 + " and use.usereason = '" + bean.getUsereason() + "'  ";
            }
            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                sql2 = sql2 + " and use.usetime >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
            }
            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                sql2 = sql2 + " and use.usetime <= TO_DATE('" + bean.getEndtime() + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
            }
            sql2 += " ) ";
  //          System.out.println( "query sql2<<<<<<<<<<<<<<:" + sql2 );
            Partinfo = query.queryBeans( sql2 );

            List list = (List)new ArrayList() ;
            list.add(Useinfo);
            list.add(Partinfo);

            return list;
        }
        catch( Exception e ){
            logger.error( "�ڰ��������������ⵥ��Ϣ�����쳣:" + e.getMessage() );
            return null;
        }
    }

    public List getDeptArrs( String condition ){
        List lUser = null;
        String sql = "select distinct us.CONTRACTORID,con.CONTRACTORNAME "
                     + "from part_use us,contractorinfo con "
                     + " where us.CONTRACTORID = con.CONTRACTORID and (con.state is null or con.state<>'1')";
        sql+=condition;
        try{
            QueryUtil query = new QueryUtil();
            lUser = query.queryBeans( sql );
            return lUser;
        }
        catch( Exception e ){
            logger.error( "�ڻ�ò���ʹ�ñ��������漰�ĵ�λ���ƺ͵�λid�б��г���:" + e.getMessage() );
            return null;
        }
    }

    public List getAllPartUse( String condition , Part_requisitionBean bean ){
        List useinfo = null;
        String totaltype = bean.getTotaltype();
        String sql="";
        try{
        	//ͳ��ȫ������
        	if("1".equals(totaltype)){
	            sql =
	                "select b.id,us.USEID,con.CONTRACTORNAME,b.NAME,b.TYPE,b.UNIT,us.USENEWNUMBER,us.USEOLDNUMBER,TO_CHAR(pus.USETIME,'yyyy-MM-dd') usetime "
	                + " from part_use_baseinfo us,part_use pus,part_baseinfo b,contractorinfo con "
	                + " where us.ID = b.id and us.USEID = pus.USEID and con.CONTRACTORID = pus.CONTRACTORID "+condition;
	            if( bean.getContractorId()!=null&&!bean.getContractorId().equals("")){
	                sql+=" and con.contractorid='"+bean.getContractorId()+"'";
	            }
	            if( !bean.getName().equals( "" ) && bean.getName() != null ){
	                sql = sql + " and b.name = '" + bean.getName() + "' ";
	            }
	            if( !bean.getType().equals( "" ) && bean.getType() != null ){
	                sql = sql + " and b.type = '" + bean.getType() + "'  ";
	            }
	            if( !bean.getFactory().equals( "" ) && bean.getFactory() != null ){
	                sql = sql + " and b.factory = '" + bean.getFactory() + "'  ";
	            }
	            if( !bean.getUsereason().equals( "" ) && bean.getUsereason() != null ){
	                sql = sql + " and pus.usereason = '" + bean.getUsereason() + "'  ";
	            }
	            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
	                sql = sql + " and pus.usetime >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
	            }
	            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
	                sql = sql + " and pus.usetime <= TO_DATE('" + bean.getEndtime() + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
	            }
	            sql = sql + " order by usetime desc, con.CONTRACTORNAME ,b.NAME  ";
	            QueryUtil query = new QueryUtil();
	            useinfo = query.queryBeans( sql );
        	}
        	//ͳ�Ƹ�Ӳ���
        	if("2".equals(totaltype)){
        		 sql =
 	                "select b.id,b.NAME,b.TYPE,con.CONTRACTORNAME,b.UNIT,sum(puob.objectnum) USENEWNUMBER"
 	                + " from part_use_baseinfo us,part_use pus,part_baseinfo b,contractorinfo con,part_useobject_baseinfo puob,LINE_CUTINFO lc,SUBLINEINFO sl,LINEINFO l,PATROLMANINFO pm,LINECLASSDIC lcs "
 	                + " where puob.baseid=b.id and us.ID = b.id and us.USEID = pus.USEID and con.CONTRACTORID = pus.CONTRACTORID and pus.useid=puob.useid and puob.objectid = lc.reid and sl.sublineid = lc.sublineid and lcs.id = pm.PARENTID " 
 	                +	"and l.lineid = sl.lineid and pm.patrolid = sl.patrolid and lc.isarchive != '������' and lc.isarchive !='�Ѿ�����' and lc.contractorid='0000000001'"+condition;
 	            if( bean.getContractorId()!=null&&!bean.getContractorId().equals("")){
 	                sql+=" and con.contractorid='"+bean.getContractorId()+"'";
 	            }
 	            if(bean.getLinechangename()!=null && !"".equals(bean.getLinechangename())){
 	            	sql+=" and lc.reid ='"+bean.getLinechangename()+"'";
 	            }else if(bean.getSublineId()!=null && !"".equals(bean.getSublineId())){
 	            	sql+=" and sl.sublineid='"+bean.getSublineId()+"'";
 	            }else if(bean.getSubline()!=null && !"".equals(bean.getSubline())){
 	            	sql+=" and l.lineid= '"+bean.getSubline() + "'" ;
 	            }else if(bean.getLevel()!=null && !"".equals(bean.getLevel())){
 	            	sql+=" and lcs.code='"+bean.getLevel()+"'";
 	            }
 	            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
	                sql = sql + " and pus.usetime >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
	            }
	            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
	                sql = sql + " and pus.usetime <= TO_DATE('" + bean.getEndtime() + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
	            }
 	            sql = sql + " group by b.id,b.name ,b.type,con.CONTRACTORNAME,b.UNIT , puob.baseid ";
	            QueryUtil query = new QueryUtil();
	            useinfo = query.queryBeans( sql );
        	}
        	//ͳ�����ɲ���
        	if("3".equals(totaltype)){
        		sql =
	                "select b.id,b.NAME,b.TYPE,con.CONTRACTORNAME,b.UNIT,sum(puob.objectnum) USENEWNUMBER "
	                + " from part_use_baseinfo us,part_use pus,part_baseinfo b,contractorinfo con,part_useobject_baseinfo puob,CHANGEINFO cg "
	                + " where puob.baseid=b.id and us.ID = b.id and us.USEID = pus.USEID and con.CONTRACTORID = pus.CONTRACTORID and puob.useid=pus.useid and cg.id=puob.objectid "+condition;
	            if( bean.getContractorId()!=null&&!bean.getContractorId().equals("")){
	                sql+=" and con.contractorid='"+bean.getContractorId()+"'";
	            }
	            if(bean.getLevel()!=null && !"".equals(bean.getLevel())){
	            	sql+= " and cg.LINECLASS='" + bean.getLevel() + "'";
	            }
	            if(bean.getCutchangename()!=null && !"".equals(bean.getCutchangename().trim())){
	            	sql += " and cg.CHANGENAME like '%" + bean.getCutchangename().trim()+"%'";
	            }
	            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
	                sql = sql + " and pus.usetime >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
	            }
	            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
	                sql = sql + " and pus.usetime <= TO_DATE('" + bean.getEndtime() + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
	            }
 	            sql = sql + " group by b.id,b.name,b.type,con.CONTRACTORNAME,b.UNIT , puob.baseid ";
	            QueryUtil query = new QueryUtil();
	            logger.info("��ѯά������ͳ��sql��"+sql);
	            useinfo = query.queryBeans( sql );
        	}
            return useinfo;
        }
        catch( Exception e ){
            logger.error( "�ڻ�õ�ǰ��½��ά��λ�����г�����Ϣ��(yidong)�����쳣:" + e.getMessage() );
            return null;
        }
    }
    
    
    //��õ������ϱ�ʹ�õ���Ϣ�б�  jixf
    
    public List getOnePartUse(String partId,Part_requisitionBean bean){
    	List useinfo = null;
    	String totaltype = bean.getTotaltype();
    	String sql="";
    	try{
    		if("2".equals(totaltype)){
	    		sql="select pub.useid,puob.objectnum usenewnumber, lc.name, pu.usereason, TO_CHAR(pu.USETIME,'YYYY-MM-DD HH24:MI:SS') usetime "
	    			+ "from PART_USE pu,PART_USE_BASEINFO pub,part_useobject_baseinfo puob ,LINE_CUTINFO lc,SUBLINEINFO sl,LINEINFO l,LINECLASSDIC lcs,PATROLMANINFO pm "
	    			+ "where lc.reid=puob.objectid and sl.sublineid = lc.sublineid and l.lineid = sl.lineid and lcs.id = pm.PARENTID "
	    			+ "and pm.patrolid = sl.patrolid and pub.useid=pu.useid and puob.objecttype='0' and puob.useid=pu.useid and pub.id='"+partId+"'";
	    		if(bean.getLinechangename() != null && !"".equals(bean.getLinechangename())){
	    			sql += " and lc.reid ='"+bean.getLinechangename()+"'";
	    		}else if(bean.getSublineId()!=null && !"".equals(bean.getSublineId())){
 	            	sql+=" and sl.sublineid='"+bean.getSublineId()+"'";
 	            }else if(bean.getSubline()!=null && !"".equals(bean.getSubline())){
 	            	sql+=" and l.lineid= '"+bean.getSubline() + "'" ;
 	            }else if(bean.getLevel()!=null && !"".equals(bean.getLevel())){
 	            	sql+=" and lcs.code='"+bean.getLevel()+"'";
 	            }
	    		if(bean.getBegintime()!=null && !"".equals(bean.getBegintime())){
	    			sql = sql + " and pu.usetime >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
	    		}
	    		if(bean.getEndtime() != null && !"".equals(bean.getEndtime())){
	    			sql = sql + " and pu.usetime <= TO_DATE('" + bean.getEndtime() + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
	    		}
	    		QueryUtil query = new QueryUtil();
	            useinfo = query.queryBeans( sql );
            
    		}
    		if("3".equals(totaltype)){
    			sql="select pub.useid,puob.objectnum usenewnumber, cg.changename name, pu.usereason, TO_CHAR(pu.USETIME,'YYYY-MM-DD HH24:MI:SS') usetime " 
    				+ "from PART_USE pu,PART_USE_BASEINFO pub,part_useobject_baseinfo puob,CHANGEINFO cg " 
    			+ "where pub.id=puob.baseid and pub.useid=pu.useid and puob.useid=pu.useid and pub.id='" + partId + "' and puob.objecttype='1' and cg.id=puob.objectid";
    			if(bean.getLevel()!=null && !"".equals(bean.getLevel())){
	            	sql+= " and cg.LINECLASS='" + bean.getLevel() + "'";
	            }
    			if(bean.getCutchangename() != null && !"".equals(bean.getContractorname())){
    				sql += " and cg.changename='" + bean.getCutchangename() + "'";
    			}
    			if(bean.getBegintime()!=null && !"".equals(bean.getBegintime())){
	    			sql = sql + " and pu.usetime >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
	    		}
	    		if(bean.getEndtime() != null && !"".equals(bean.getEndtime())){
	    			sql = sql + " and pu.usetime <= TO_DATE('" + bean.getEndtime() + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
	    		}
	    		logger.info("ȡ�õ������ϵ�ʹ�������" + sql);
    			QueryUtil query = new QueryUtil();
	            useinfo = query.queryBeans( sql );
    		}
            return useinfo;
    	}catch(Exception e){
    		logger.error( "�ڻ�õ�ǰ�������г�����Ϣ��(yidong)�����쳣:" + e.getMessage() );
            return null;
    	}
    	
    }
    
    //��õ������ϵĻ�����Ϣ  jixf
    
    public BasicDynaBean getOnPartById(String partid){
    	BasicDynaBean partInfo=null;
    	try{
    		String sql = "select id,name,unit,type,factory,regionid from part_baseinfo where id='" + partid + "'";
    		QueryUtil query = new QueryUtil();
    		List partList=query.queryBeans(sql);
    		if(partList.size() != 0){
    			partInfo = (BasicDynaBean) partList.get(0);
    		}
    	}catch(Exception e){
    		logger.error( "�ڻ�õ�ǰ������Ϣ��(yidong)�����쳣:" + e.getMessage() );
    	}
    	return partInfo;
    }
    
    /**
     * ȡ�����д�ά������Ϣ
     * @return
     */
    public List getConDeptInfo(UserInfo userinfo) {
    	String sql = "select c.CONTRACTORID deptid,c.CONTRACTORNAME deptname from contractorinfo c where c.state is null and c.regionid='"
  			+ userinfo.getRegionID() + "'";
        try{
            QueryUtil qu = new QueryUtil();
            List lDept = qu.queryBeans( sql );
            return lDept;
        }
        catch( Exception ex ){
            logger.warn( "����������б����:" + ex.getMessage() );
            return null;
        }
    }
}
