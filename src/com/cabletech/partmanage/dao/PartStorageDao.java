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
     * <br>功能:获得当前登陆代维单位的所有库存信息(代维单位)
     * <br>参数:请求
     * <br>返回值:获得成功返回List,否则返回 NULL;
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
            logger.error( "在获得当前登陆代维单位的所有库存信息(代维单位)中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:获得当前区域的所有库存信息(移动公司)
     * <br>参数:请求
     * <br>返回值:获得成功返回List,否则返回 NULL;   //
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
            logger.error( "在获得当前区域代维单位的所有库存信息(移动公司)中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能：获得I材料名称列表和id列表
     * <br>参数:
     * <br>返回值:获得成功返回List,否则返回 null;
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
            logger.error( "在获得I材料名称列表和id列表中出错:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能：获得I材料类型列表
     * <br>参数:指定单位的id
     * <br>返回值:获得成功返回List,否则返回 null;
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
            logger.error( "在获得I材料类型列表中出错:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能：获得代维单位名称列表和id列表
     * <br>参数:request
     * <br>返回值:获得成功返回List,否则返回 null;
     */
    public List getContractorNameArr( HttpServletRequest request ){
        List lname = null;
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        String sql = "";
        //市移动用户
        if( userinfo.getDeptype().equals( "1" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
            sql = " select contractorid,contractorname from contractorinfo where state is null and regionid='"
                  + userinfo.getRegionID() + "'";
        }
        //是代维用户
        if( userinfo.getDeptype().equals( "2" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
            sql = " select contractorid,contractorname from contractorinfo where state is null and contractorid='"
                  + userinfo.getDeptID() + "'";
        }
        //省代维用户
        if( userinfo.getDeptype().equals( "2" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
            sql =
                " select contractorid,contractorname from contractorinfo where  state is null and parentcontractorid='"
                + userinfo.getDeptID() + "'";
        }
        //省移动用户
        if( userinfo.getDeptype().equals( "1" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
            sql = " select contractorid,contractorname from contractorinfo where state is null";
        }
        try{
            QueryUtil query = new QueryUtil();
            lname = query.queryBeans( sql );
            return lname;
        }
        catch( Exception e ){
            logger.error( "在获得代维单位名称列表和id列表出错:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:当是移动公司时,获得指定条件的所有库存信息
     * <br>参数:请求
     * <br>返回值:获得成功返回List,否则返回 NULL;
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
            logger.info( "查询库存sql：" + sql );
            partinfo = query.queryBeans( sql );
            return partinfo;
        }
        catch( Exception e ){
            logger.error( "在获得当前登陆代维单位的所有库存信息中发生异常:" + e.getMessage() );
            return null;
        }
    }

    /**
     * <br>功能:当是代维单位时,获得指定条件的所有库存信息
     * <br>参数:请求
     * <br>返回值:获得成功返回List,否则返回 NULL;
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
            logger.info( "查询库存sql: " + sql );
            partinfo = query.queryBeans( sql );
            return partinfo;
        }
        catch( Exception e ){
            logger.error( "在获得当前登陆代维单位的所有库存信息中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:初始化当前登陆代维单位的材料库存(执行方法:先删除记录然后写入)
     * <br>参数:基本信息bean,材料id数组,旧材料数量oldnumber数组,新材料数量newesse数组
     * <br>返回值:获得成功返回true,否则返回 false;
     */
    public boolean initStorage( Part_requisitionBean bean, String[] id, String[] oldnumber, String[] newesse ){
        String sql1 = ""; //删除的
        String sql2 = ""; //加入的

        try{
            UpdateUtil exec = new UpdateUtil();
            exec.setAutoCommitFalse();
            try{

                for( int i = 0; i < id.length; i++ ){ //逐个操作
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
                logger.error( "初始化出错:" + e1.getMessage() );
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
     * <br>功能:获得当前登陆代维单位的所有库存信息(代维单位)
     * <br>参数:请求
     * <br>返回值:获得成功返回List,否则返回 NULL;
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
            logger.error( "在获得当前登陆代维单位的所有库存信息(代维单位)中发生异常:" + e.getMessage() );
            return null;
        }
    }

    /**
     * <br>功能:获得当前区域的所有库存信息(移动公司)
     * <br>参数:请求
     * <br>返回值:获得成功返回List,否则返回 NULL;   //
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
            logger.error( "在获得当前区域代维单位的所有库存信息(移动公司)中发生异常:" + e.getMessage() );
            return null;
        }
    }
}
