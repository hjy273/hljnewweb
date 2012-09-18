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
     * <br>功能:获得当前用户的单位名称信息
     * <br>参数:当前用户对象
     * <br>返回值:返回当前用户的单位名称信息;
     */
    public String getUserDeptName( UserInfo userinfo ){
        String strDeptName = "";
        String sql = "";
        String[][] lDeptName = null;
        if( userinfo.getDeptype().equals( "2" ) ){ //是代维单位
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
            logger.error( "在获得用户单位名称中出错:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能：获得当前登陆代维单位的目标处理人信息
     * <br>参数:request
     * <br>返回值:获得成功返回List,否则返回 null;
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
            logger.error( "在获得当前登陆代维单位的目标处理人信息出错:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能：获得材料基本表的id,name,type,unit信息
     * <br>参数:无
     * <br>返回值:获得成功返回Vector,否则返回 null;
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
            logger.error( "在获得材料name,type,unit,id信息中出错:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:写入申请基本表,申请_材料对应表
     * <br>参数:
     * <br>返回值:获得成功返回true,否则返回 false;
     */
    public boolean doAddRequisition( Part_requisitionBean bean, String[] id, String[] renumber ){
        String sql1 = ""; //写入基本表的
        String sql2 = ""; //写入对应表的
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
                "','" + "待审批" + "','" + bean.getTargetman() + "','" + bean.getRegionid() + "')";
            try{
                exec.executeUpdate( sql1 ); //写入基本表
                for( int i = 0; i < id.length; i++ ){ //将材料信息分别写入对应表和库存表
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
                logger.error( "写入申请基本表,申请_材料对应表出错:" + e1.getMessage() );
                exec.rollback();
                exec.setAutoCommitTrue();
                return false;
            }
        }
        catch( Exception e ){
            logger.error( "写入申请基本表,申请_材料对应表出错出错:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>功能:删除材料申请表中一条信息
     * <br>参数:袋删申请单的id
     * <br>返回值:获得成功返回true,否则返回 false;
     */
    public boolean doDeleteReq( String reid ){
        try{
            String sql = "delete from part_req_partbase where id='" + reid + "'";
            UpdateUtil excu = new UpdateUtil();
            excu.executeUpdate( sql );
            return true;
        }
        catch( Exception e ){
            logger.error( "在删除材料申请单中发生异常:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>功能:获得当前登陆代维单位的所有材料申请表信息
     * <br>参数:代维单位id
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getAllReq( HttpServletRequest request ){
        List reqinfo = null;
        String contractorid = ( String )request.getSession().getAttribute( "LOGIN_USER_DEPT_ID" );
        try{
            //获得用户姓名,代维代维名称,申请时间,申请单编号,申请理由,申请备注,审批结果
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
            logger.error( "在显示申请单全部信息中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:获得当前登陆代维单位材料申请表信息
     * <br>参数:申请单id
     * <br>返回值:获得成功返回申请单对象,否则返回 NULL;
     **/
    public Part_requisitionBean getOneReq( String reid ){

        Part_requisitionBean bean = new Part_requisitionBean();
        ResultSet rst = null;
        try{
            //获得用户姓名,代维代维名称,申请时间,申请单编号,申请理由,申请备注,审批状态,
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
            logger.error( "在显示申请单全部信息中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:获得当前登陆代维单位,指定申请单编号的所有材料申请信息
     * <br>参数:申请单id
     * <br>返回值:获得成功返回List,否则返回 NULL;
     **/
    public List getReqPart( String reid, HttpServletRequest request ){

        List reqPart = null;
        String contractorid = ( String )request.getSession().getAttribute( "LOGIN_USER_DEPT_ID" );

        try{
            //获得用户姓名,代维代维名称,申请时间,申请单编号,申请理由,申请备注,审批数量,已入库数量
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
            logger.error( "在显示申请单全部信息中发生异常:" + e.getMessage() );
            return null;
        }
    }

    /**
     * <br>功能:获得当前登陆代维单位,指定申请单编号的所有材料申请信息
     * <br>参数:申请单id
     * <br>返回值:获得成功返回List,否则返回 NULL;
     **/
    public List getRequisitionPart( String reid, HttpServletRequest request ){

        List reqPart = null;
        String contractorid = request.getParameter("contractorid");

        try{
            //获得用户姓名,代维代维名称,申请时间,申请单编号,申请理由,申请备注,审批数量,已入库数量
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
            logger.error( "在显示申请单全部信息中发生异常:" + e.getMessage() );
            return null;
        }
    }

    /**
     * <br>功能:写入申请基本表,申请_材料对应表
     * <br>参数:
     * <br>返回值:获得成功返回true,否则返回 false;
     */
    public boolean doUpdateRequisition( Part_requisitionBean bean, String[] id, String[] renumber ){
        String sql1 = ""; //修改基本表的
        String sql2 = ""; //删除对应表的
        String sql3 = ""; //重新写入对应表的
        java.util.Date date = new java.util.Date();
        SimpleDateFormat dtFormat = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss" );
        String strDt = dtFormat.format( date );

        if( !this.valiReqForUp( bean.getReid() ) ){ //不能被删除返回
            return false;
        }

        try{
            UpdateUtil exec = new UpdateUtil();
            exec.setAutoCommitFalse();
            sql1 = "update Part_requisition "
                   + " set contractorid='" + bean.getcontractorid() + "',userid='" + bean.getUserid()
                   + "',reason='" + bean.getReason() + "',remark='" + bean.getRemark()
                   + "', targetman='"+bean.getTargetman() 
                   + "',time=" + "TO_DATE('" + strDt + "','YYYY-MM-DD HH24:MI:SS'), auditresult='待审批' "
                   + " where reid='" + bean.getReid() + "'";
            try{
                exec.executeUpdate( sql1 ); //修改基本表
                for( int i = 0; id!=null&&i < id.length; i++ ){ //重新写入对应表
                    if( !this.valiReq_partbaseExist( bean.getReid(), bean.getcontractorid(), id[i] ) ){ //如果不存在则加入
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
                logger.error( "更新时写入申请基本表,申请_材料对应表出错:" + e1.getMessage() );
                exec.rollback();
                exec.setAutoCommitTrue();
                return false;
            }
        }
        catch( Exception e ){
            logger.error( "更新出错:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>功能:检验指定的申请单能否进行修改操作,注意:一旦通过审批,不得进行修改和删除.
     * <br>参数:申请单id
     * <br>返回值:能修改返回true 不能修改返回 false;
     **/
    public boolean valiReqForUp( String reid ){
        ResultSet rst = null;
        try{
            String sql = "select req.AUDITRESULT from part_requisition req where reid='" + reid + "'";
            QueryUtil query = new QueryUtil();
            rst = query.executeQuery( sql );
            rst.next();
            if( !rst.getString( "auditresult" ).equals( "待审批" ) && !rst.getString( "auditresult" ).equals( "不予审批" ) ){
                rst.close();
                return false;
            }
            rst.close();
            return true;
        }
        catch( Exception e ){
            logger.error( "在显示申请单全部信息中发生异常:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>功能：检查申请单_材料表中对应申请单和相应单位的材料是否存在,
     * <br>参数:申请表的reid,代维单位contractorid,
     * <br>返回值:对应记录存在返回true,否则返回 false;
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
            logger.error( "在验证申请单-材料表是否存在相应记录中出错:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>功能：删除申请单_材料表中对应申请单所申请的材料信息,
     * <br>参数:申请表的reid
     * <br>返回值:对应记录存在返回true,否则返回 false;
     */
    public boolean deletPart_BaseInfo( String reid ){

        String sql = "delete  part_req_partbase where reid='" + reid + "'";
        try{
            UpdateUtil excu = new UpdateUtil();
            excu.executeUpdate( sql );
            return true;
        }
        catch( Exception e ){
            logger.error( "在删除申请单-材料表存在相应记录中出错:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>功能：删除申请单中指定申请单的所有信息,
     * <br>参数:申请表的reid
     * <br>返回值:删除成功存在返回true,否则返回 false;
     */
    public boolean deletReqInfo( String reid ){

        String sql = "delete  Part_requisition where reid='" + reid + "'";
        if( this.deletPart_BaseInfo( reid ) ){ //先删除对应表,删除成功后再删基本表
            try{
                UpdateUtil excu = new UpdateUtil();
                excu.executeUpdate( sql );
                return true;
            }
            catch( Exception e ){
                logger.error( "在删除申请单中出错:" + e.getMessage() );
                return false;
            }
        }
        else{
            return false;
        }
    }


    /**
     * <br>功能:获得指定代维单位所有填写过申请单的人员名称
     * <br>参数:指定代维单位的id
     * <br>返回值:获得成功返回List,否则返回 NULL;
     **/
    public List getUserOfReq( String contractorid ){

        List reqUser = null;
        try{
            //获得申请人的姓名,id
            String sql = "select distinct userinfo.userid,userinfo.username "
                         + " from part_requisition,userinfo "
                         + " where part_requisition.USERID = userinfo.USERID "
                         + "and part_requisition.contractorid='" + contractorid + "'";

            QueryUtil query = new QueryUtil();
            reqUser = query.queryBeans( sql );
            return reqUser;
        }
        catch( Exception e ){
            logger.error( "在获得指定代维单位所有填写过申请单的人员名称中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:获得指定代维单位所有申请原因
     * <br>参数:指定代维单位的id
     * <br>返回值:获得成功返回List,否则返回 NULL;
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
            logger.error( "在获得指定代维单位所有申请原因中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:综合查询,条件查询,
     * <br>参数:指定代维单位的id
     * <br>返回值:获得成功返回List,否则返回 NULL;
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
            logger.error( "综合查询,条件查询:" + e.getMessage() );
            return null;
        }
    }


    ///////////////////////////////审批////////////////////

    /**
     * <br>功能:获得所有待申批的申请表基本信息
     * <br>参数:
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getAllReqForAudit( String regionid ){
        List reqinfo = null;

        try{
            //获得申请人姓名,代维名称,申请时间,申请单编号,申请理由,申请备注,审批结果
            String sql =
                "select userinfo.username,contractorinfo.contractorname,TO_CHAR(part_requisition.time,'YYYY-MM-DD') time,"
                + "     part_requisition.reid,part_requisition.reason,part_requisition.remark,"
                + "     part_requisition.auditresult "
                + " from part_requisition ,userinfo ,contractorinfo "
                +
                " where part_requisition.CONTRACTORID=contractorinfo.CONTRACTORID and part_requisition.userid=userinfo.userid "
                + " and (part_requisition.auditresult='待审批' or part_requisition.auditresult='待审核') "
                + " and contractorinfo.regionid='" + regionid + "'"
                + " order by time desc";
            QueryUtil query = new QueryUtil();
            reqinfo = query.queryBeans( sql );
            return reqinfo;
        }
        catch( Exception e ){
            logger.error( "在显示待申请的申请单信息中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:获得指定申请单编号的所有材料申请信息
     * <br>参数:申请单id
     * <br>返回值:获得成功返回List,否则返回 NULL;
     **/
    public List getReqPartForAudit( String reid ){

        List reqPart = null;

        try{
            //获得材料id,材料名称,材料单位,材料类型,申请数量,审批数量
            String sql = "select base.id,base.name,base.unit,base.type,req.renumber, req.audnumber "
                         + " from part_baseinfo  base,part_req_partbase  req "
                         + " where req.id=base.id and req.reid='" + reid + "' "
                         + " order by name";
            QueryUtil query = new QueryUtil();
            reqPart = query.queryBeans( sql );
            return reqPart;
        }
        catch( Exception e ){
            logger.error( "在显示申请单全部信息中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:获得指定申请单编号的所有 未能全部入库的材料信息
     * <br>参数:申请单id
     * <br>返回值:获得成功返回List,否则返回 NULL;
     **/
    public List getReqPartForReAudit( String reid ){

        List reqPart = null;

        try{
            //获得材料id,材料名称,材料单位,材料类型,申请数量,审批数量,已入库数量
            String sql = "select base.id,base.name,base.unit,base.type,req.renumber, req.audnumber,req.stocknumber "
                         + " from part_baseinfo  base,part_req_partbase  req "
                         + " where req.id=base.id  and req.stocknumber < audnumber and req.reid='" + reid + "' "
                         + " order by name";
            QueryUtil query = new QueryUtil();
            reqPart = query.queryBeans( sql );
            return reqPart;
        }
        catch( Exception e ){
            logger.error( "在显示申请单全部信息中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:审核申请单,将申请材料对应表中的审批数量修改为入库数量,库存表中新品应有数量 = 库存表中新品应有数量 - (审批数量-已入库数量)
     * <br>参数:申请单id
     * <br>返回值:获得成功返回List,否则返回 NULL;
     **/
    public boolean doReAudit( String reid, String[] id ){

        String sql1 = ""; //修改库存的新品应有量
        String sql2 = ""; //修改申请_材料表审批量
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
                sql1 = "update part_requisition set auditresult='入库完毕' where reid='" + reid + "'";
                exec.executeUpdate( sql1 );
                exec.commit();
                exec.setAutoCommitTrue();
                return true;
            }
            catch( Exception e ){
                exec.rollback();
                exec.setAutoCommitTrue();
                logger.error( "在审核申请单发生异常:" + e.getMessage() );
                return false;
            }
        }
        catch( Exception e ){

            logger.error( "在审核申请单发生异常:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>功能：写入一条审批的基本信息
     * <br>参数:申请表的bean
     * <br>返回值:成功返回true,否则返回 false;
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
            logger.error( "写入审批单基本信息时出错: " + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>功能：向申请表_材料表表中写入审批信息,并将已入库数量设置为零,同时设置材料库存表中的新品应有数量
     * <br>参数:申请表的id,审批材料的id,审批数量
     * <br>返回值:成功返回true,否则返回 false;
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
            logger.error( "写入向申请单_材料表中写入审批信息时出错: " + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>功能:获得所有审批表信息
     * <br>参数:request
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getAllAudit( HttpServletRequest request ){
        List reqinfo = null;
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        String deptid = userinfo.getDeptID();
        try{
            //获得审批人姓名,审批时间,审批结果,审批备注,审批的申请单申请原因,申请单位名称
            String sql = "select us.username,TO_CHAR(req.AUDITTIME,'YYYY-MM-DD') time,req.auditresult,"
                         + " req.AUDITREMARK,req.reid,req.REASON,con.contractorname "
                         + " from part_requisition req,userinfo us,contractorinfo con "
                         +
                         " where req.AUDITUSERID=us.USERID and req.contractorid=con.contractorid and auditresult <> '待审批'"
                         + " and req.deptid='" + deptid + "'"
                         + " order by time desc";
//System.out.println("获得所有审批表信息SQL:" + sql);
            QueryUtil query = new QueryUtil();
            reqinfo = query.queryBeans( sql );
            return reqinfo;
        }
        catch( Exception e ){
            logger.error( "在获得所有审批表信息异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:获得指定申请单编号的审批信息
     * <br>参数:申请单id
     * <br>返回值:获得成功返回List,否则返回 NULL;
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
            logger.error( "在获得申请单的信息中异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:获得所有的审批人的姓名,id
     * <br>参数:
     * <br>返回值:获得成功返回List,否则返回 NULL;
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
            logger.error( "在获得所有的审批人的姓名中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:获得所有申请单位名称,id
     * <br>参数:
     * <br>返回值:获得成功返回List,否则返回 NULL;
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
            logger.error( "在获得所有申请单位名称中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:获得所有申请事由
     * <br>参数:
     * <br>返回值:获得成功返回List,否则返回 NULL;
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
            logger.error( "在获得所有申请单位名称中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:按条件综合查询审批单
     * <br>参数:form bean
     * <br>返回值:获得成功返回List,否则返回 NULL;
     **/
    public List getUserOfReq( Part_requisitionBean bean ){

        List reqUser = null;

        try{ //获得审批人姓名,审批时间,审批结果,审批备注,审批的申请单申请原因,申请单位名称     + " order by time ";
            String sql = "select us.username,TO_CHAR(req.AUDITTIME,'YYYY-MM-DD') time,req.auditresult, "
                         + " req.AUDITREMARK,req.reid,req.REASON,con.contractorname "
                         + " from part_requisition req,userinfo us,contractorinfo con "
                         +
                         " where req.AUDITUSERID=us.USERID and req.contractorid=con.contractorid and auditresult <> '待审批' ";
            if( !bean.getAudituserid().equals( "" ) && bean.getAudituserid() != null ){ //审批人
                sql = sql + " and us.username like '" + bean.getAudituserid() + "%'";
            }
            if( !bean.getAuditresult().equals( "" ) && bean.getAuditresult() != null ){ //审批结果
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
            logger.info( "获得tiaojian SQL:" + sql );
            reqUser = query.queryBeans( sql );
            return reqUser;
        }
        catch( Exception e ){
            logger.error( "在按条件综合查询审批单异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:在库存表中查找指定的记录是否存在
     * <br>参数:材料id 代维单位id
     * <br>返回值:存在返回true 不存在返回false;
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
            logger.error( "在在库存表中查找指定的记录是否存在中发生异常:" + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>功能:在申请表中根据指定的申请单编号查找相应的代维单位编号
     * <br>参数:申请单编号
     * <br>返回值:存在返回contracrotid 不存在返回null;
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
            logger.error( "在在申请表中根据指定的申请单编号查找相应的代维单位编号中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:根据申请单编号,材料编号,材料审批数量更新库存表的新品应有数量
     * <br>参数:申请单编号,材料编号,材料审批数量
     * <br>返回值:更新成功返回true,否则返回false;
     **/
    public boolean doUpStorageForNewshould( String reid, String id, String audnumber ){

        String sql = "";
        String contractorid = this.getContractorID( reid );
        try{
            if( this.valiExistForStorage( id, contractorid ) ){ //该代维单位相应的材料存在,要更新
                sql = "update part_storage set newshould=newshould + " + Integer.parseInt( audnumber )
                      + " where id='" + id + "' and contractorid='" + contractorid + "'";
            }
            else{ //不存在就添加
                sql = " insert into part_storage (id,contractorid,newesse,oldnumber,newshould)"
                      + " values ('" + id + "','" + contractorid + "',0,0," + Integer.parseInt( audnumber ) + ")";
            }

            UpdateUtil excu = new UpdateUtil();
            excu.executeUpdate( sql );
                      return true;
        }
        catch( Exception e ){
            logger.error( "在根据申请单编号,材料编号,材料审批数量更新库存表的新品应有数量中发生异常:" + e.getMessage() );
            return false;
        }
    }


    /**
     * 功能:将上传的文件保存为临时文件
     * 参数:bean,保存路径
     * 返回:保存成功返回真,否则返回假
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
     * 功能:获得上传文件的申请内容(有效部分)
     * 参数:bean,保存路径
     * 返回:获得成功返回List 否则返回null
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
     * <br>功能:获得当前登陆代维单位的所有材料申请表信息
     * <br>参数:代维单位id
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getAllRequisition( HttpServletRequest request ){
        List reqinfo = null;
        UserInfo userinfo=(UserInfo)request.getSession().getAttribute("LOGIN_USER");
        String deptid=userinfo.getDeptID();
        try{
            //获得用户姓名,代维代维名称,申请时间,申请单编号,申请理由,申请备注,审批结果
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
            logger.error( "在显示申请单全部信息中发生异常:" + e.getMessage() );
            return null;
        }
    }

    /**
     * <br>功能:获得所有审批表信息
     * <br>参数:request
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getAllAuditReq( HttpServletRequest request ){
        List reqinfo = null;
        UserInfo userinfo=(UserInfo)request.getSession().getAttribute("LOGIN_USER");
        String deptid=userinfo.getDeptID();
        try{
            //获得审批人姓名,审批时间,审批结果,审批备注,审批的申请单申请原因,申请单位名称
            String sql = "select us.username,TO_CHAR(req.AUDITTIME,'YYYY-MM-DD') time,req.auditresult,"
                         + " req.AUDITREMARK,req.reid,req.REASON,con.contractorname "
                         + " from part_requisition req,userinfo us,contractorinfo con ";
            QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );
            sqlBuild.addConstant(" req.AUDITUSERID=us.USERID and req.contractorid=con.contractorid and auditresult <> '待审批'");
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
            logger.error( "在获得所有审批表信息异常:" + e.getMessage() );
            return null;
        }
    }
}
