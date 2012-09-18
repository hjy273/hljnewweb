package com.cabletech.partmanage.dao;

import com.cabletech.partmanage.beans.Part_requisitionBean;
import java.util.*;

import com.cabletech.commons.hb.QueryUtil;
import java.sql.*;
import java.text.*;
import com.cabletech.commons.hb.UpdateUtil;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.cabletech.commons.sqlbuild.QuerySqlBuild;
import com.cabletech.baseinfo.domainobjects.UserInfo;

public class PartStockDao{
    private static Logger logger = Logger.getLogger( PartStockDao.class.
                                   getName() );
    private Part_requisitionBean bean;

    public PartStockDao(){
    }


    public PartStockDao( Part_requisitionBean bean ){
        this.bean = bean;
    }


    /**
     * <br>功能:获得所有待入库的申请表基本信息
     * <br>参数:
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getAllReqForStock( String contractorid ){
        List reqinfo = null;

        try{
            //获得申请人姓名,代维名称,申请时间,申请单编号,申请理由,申请备注,审批结果
            String sql =
                " select distinct part_requisition.reid,userinfo.username,contractorinfo.contractorname,TO_CHAR(part_requisition.time,'YYYY-MM-DD') time,"
                + "       part_requisition.reason,part_requisition.remark,  part_requisition.auditresult "
                + " from part_requisition ,userinfo ,contractorinfo "
                +
                " where part_requisition.CONTRACTORID=contractorinfo.CONTRACTORID and part_requisition.userid=userinfo.userid"
                + "      and (part_requisition.auditresult='通过审批' or  part_requisition.auditresult='待审核' ) "
                +
                "  	 and part_requisition.reid in (select distinct reid from part_req_partbase where stocknumber < audnumber  )"
                + " and part_requisition.contractorid = '" + contractorid + "'"
                + " order by time desc";
            QueryUtil query = new QueryUtil();
            reqinfo = query.queryBeans( sql );
            return reqinfo;
        }
        catch( Exception e ){
            logger.error( "在获得所有待入库的申请表基本信息中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:获得指定申请单编号的所有材料申请信息(包含了已入库数量)
     * <br>参数:申请单id
     * <br>返回值:获得成功返回List,否则返回 NULL;
     **/
    public List getReqPartForStock( String reid ){
        List reqPart = null;
        try{
            //获得材料id,材料名称,材料单位,材料类型,申请数量,审批数量,已入库数量
            String sql =
                "select base.id,base.name,base.unit,base.type,req.renumber, req.audnumber,(req.audnumber - req.stocknumber) stocknumber"
                + " from part_baseinfo  base,part_req_partbase  req "
                + " where req.id=base.id and req.reid='" + reid + "' "
                + " order by name";
//            System.out.println("获得指定申请单编号的所有材料申请信息(包含了已入库数量):" + sql);
            QueryUtil query = new QueryUtil();
            reqPart = query.queryBeans( sql );
            return reqPart;
        }
        catch( Exception e ){
            logger.error( "在获得指定申请单编号的所有材料申请信息中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能：写入一条入库的基本信息
     * <br>参数:申请表的bean
     * <br>返回值:成功返回true,否则返回 false;
     */
    public boolean doAddStock( Part_requisitionBean bean ){
        java.util.Date date = new java.util.Date();
        SimpleDateFormat dtFormat = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss" );
        String strDt = dtFormat.format( date );

        String sql =
            "insert into part_stock (stockid,contractorid,stockuserid,stocktime,reid,stockaddress,keeperid) values ('"
            + bean.getStockid() + "','" + bean.getcontractorid() + "','" + bean.getStockuserid()
            + "',TO_DATE('" + strDt
            + "','YYYY-MM-DD HH24:MI:SS'),'"
            + bean.getReid() + "','" + bean.getStockaddress() + "','" + bean.getKeeperid() + "')";
        try{
            UpdateUtil excu = new UpdateUtil();
            excu.executeUpdate( sql );
            return true;
        }
        catch( Exception e ){
            logger.error( "写入一条入库的基本信息时出错: " + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>功能：向入库_材料对应表中写入信息,同时更新申请单_材料对应表的已经入库字段,更新库存表中新品实际数量
     * <br>参数:申请表的id,审批材料的id,审批数量
     * <br>返回值:成功返回true,否则返回 false;
     */
    public boolean doAddStock_BaseForStock( String[] id, String[] stocknumber, String reid, String stockid,
        String contractorid ){
        String sql = "";
        if( id.length != stocknumber.length ){
            return false;
        }
        try{
            UpdateUtil excu = new UpdateUtil();
            for( int i = 0; i < id.length; i++ ){
                if( !stocknumber[i].equals( "0" ) && stocknumber[i] != null ){
                    sql = "insert into Part_stock_baseinfo (stockid,id,stocknumber) values('"
                          + stockid + "','" + id[i] + "'," + Integer.parseInt( stocknumber[i] ) + ")";
                    excu.executeUpdate( sql );
                    excu = new UpdateUtil();
                    this.doUpdateReq_base( id[i], reid, stocknumber[i] );
                    this.doUpdateStorageNewEsse( id[i], contractorid, stocknumber[i] );
                }
            }
            this.valiAndSetAudState( reid ); //佛山需求,如果不用返回注释掉就行
            return true;
        }
        catch( Exception e ){
            logger.error( "向入库_材料对应表中写入信息,同时更新申请单_材料对应表的已经入库字段,更新库存表中新品实际数量出错: " + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>功能：在入库记录写入完毕后,检查申请_材料对应表中已入库数量是否小于审批数量,
     * 			如果小于审批数量,则置该申请单为待审核状态,需要返回审批单位进行入库审核,
     * <br>参数:申请表的id,审批材料的id,审批数量
     * <br>返回值:成功返回true,否则返回 false;
     */
    private boolean valiAndSetAudState( String reid ){
        ResultSet rst = null;
        String sql1 = " select count(id) aa from part_req_partbase where stocknumber < audnumber and reid='" + reid
                      + "'";
        String sql2 = " update part_requisition set auditresult = '待审核' where reid='" + reid + "' ";
        String sql3 = " update part_requisition set auditresult = '入库完毕' where reid='" + reid + "' ";

        try{
            UpdateUtil excu = new UpdateUtil();
            QueryUtil query = new QueryUtil();
            rst = query.executeQuery( sql1 );
            if( rst != null && rst.next() ){
                if( rst.getString( "aa" ).equals( "0" ) ){
                    excu.executeUpdate( sql3 );
                }
                else{
                    excu.executeUpdate( sql2 );
                }
            }
            rst.close();
            return true;
        }
        catch( Exception e ){
            logger.error( "在入库记录写入完毕后,检查申请_材料对应表中已入库数量是否小于审批数量段时出错: " + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>功能：更新申请单_材料对应表中的已经入库字段
     * <br>参数:申请表的id,审批材料的id,审批数量
     * <br>返回值:成功返回true,否则返回 false;
     */
    private boolean doUpdateReq_base( String id, String reid, String stocknumber ){
        String sql = "update part_req_partbase set stocknumber=stocknumber + " + Integer.parseInt( stocknumber )
                     + " where reid='" + reid + "'  and id='" + id + "'";
        try{
            UpdateUtil excu = new UpdateUtil();
            excu.executeUpdate( sql );
            return true;
        }
        catch( Exception e ){
            logger.error( "更新申请单_材料对应表中的已经入库字段时出错: " + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>功能：更新库存表中新品实际数量
     * <br>参数:申请表的id,审批材料的id,审批数量
     * <br>返回值:成功返回true,否则返回 false;
     */
    private boolean doUpdateStorageNewEsse( String id, String contractorid, String stocknumber ){
        String sql = "update Part_storage set newesse=newesse + " + Integer.parseInt( stocknumber )
                     + " where id='" + id + "'  and contractorid='" + contractorid + "'";
        try{
            UpdateUtil excu = new UpdateUtil();
            excu.executeUpdate( sql );

            return true;
        }
        catch( Exception e ){
            logger.error( "更新库存表中新品实际数量时出错: " + e.getMessage() );
            return false;
        }
    }


    /**
     * <br>功能:获得所有入库单信息
     * <br>参数:request
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getAllStock( String contractorid ){
        List Stockinfo = null;

        try{
            //获得入库单id,入库人姓名,入库时间,代维单位名称,对应申请单编号,申请的原因
            String sql = "select st.stockid,TO_CHAR(st.STOCKTIME,'YYYY-MM-DD') stocktime,st.REID, "
                         + "     us.username,con.contractorname,re.REASON  "
                         + " from part_stock st, userinfo us, contractorinfo con, part_requisition re "
                         +
                         "  where st.STOCKUSERID = us.USERID and st.contractorid = con.contractorid and st.REID = re.REID "
                         + "      and st.contractorid='" + contractorid + "'"
                         + "  order by stocktime";

            QueryUtil query = new QueryUtil();
            Stockinfo = query.queryBeans( sql );
            return Stockinfo;
        }
        catch( Exception e ){
            logger.error( "在显示申请单全部信息中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:获得一个入库单基本信息
     * <br>参数:request
     * <br>返回值:获得成功返回Part_requisitionBean,否则返回 NULL;
     */
    public Part_requisitionBean getOneStock( String stockid ){
        Part_requisitionBean stock = new Part_requisitionBean();
        ResultSet rst = null;
        try{
            //获得入库单id,入库人姓名,入库时间,代维单位名称,对应申请单编号,申请的原因
            String sql =
                "select st.stockid,TO_CHAR(st.STOCKTIME,'YYYY-MM-DD HH24:MI:SS') stocktime,st.REID,st.stockaddress,st.keeperid, "
                + "     us.username,con.contractorname,re.REASON  "
                + " from part_stock st, userinfo us, contractorinfo con, part_requisition re "
                +
                "  where st.STOCKUSERID = us.USERID and st.contractorid = con.contractorid and st.REID = re.REID "
                + " and  stockid='" + stockid + "'";
            QueryUtil query = new QueryUtil();
            rst = query.executeQuery( sql );
            rst.next();
            stock.setStockid( rst.getString( "stockid" ) );
            stock.setStocktime( rst.getString( "stocktime" ) );
            stock.setReid( rst.getString( "reid" ) );
            stock.setUsername( rst.getString( "username" ) );
            stock.setContractorname( rst.getString( "contractorname" ) );
            stock.setReason( rst.getString( "reason" ) );
            stock.setStockaddress( rst.getString( "stockaddress" ) );
            stock.setKeeperid( rst.getString( "keeperid" ) );
            rst.close();
            return stock;
        }
        catch( Exception e ){
            logger.error( "在获得一个入库单基本信息中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:获得一个入库单所入库的材料信息
     * <br>参数:入库单id
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getStockPartInfo( String stockid ){
        List Stockinfo = null;

        try{
            //
            String sql = "select  b.NAME,b.unit,b.type,b.remark,st.stocknumber "
                         + " from part_stock_baseinfo st,part_baseinfo b"
                         + " where st.ID = b.id and stockid='" + stockid + "'";
            QueryUtil query = new QueryUtil();
            Stockinfo = query.queryBeans( sql );
            return Stockinfo;
        }
        catch( Exception e ){
            logger.error( "在获得一个入库单所入库的材料信息中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:获得入库操作人列表
     * <br>参数:
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getUsernameForStockQUery( String contractorid ){
        List lUserName = null;

        try{
            String sql =
                "select distinct st.stockuserid,us.username from part_stock st,userinfo us where st.stockuserid = us.userid "
                + "  and st.contractorid='" + contractorid + "'";
            QueryUtil query = new QueryUtil();
            lUserName = query.queryBeans( sql );
            return lUserName;
        }
        catch( Exception e ){
            logger.error( "在获得入库操作人列表中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:获得入库对应申请单编号,申请原因列表
     * <br>参数:
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getAllReidForStockQuery( String contractorid ){
        List lReid_Reason = null;

        try{
            String sql =
                "select distinct st.reid,req.reason from part_stock st,part_requisition req where st.REID = req.reid"
                + " and st.contractorid='" + contractorid + "'";
            QueryUtil query = new QueryUtil();
            lReid_Reason = query.queryBeans( sql );
            return lReid_Reason;
        }
        catch( Exception e ){
            logger.error( "在获得入库对应申请单编号,申请原因列表中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:按条件获得所有入库单信息
     * <br>参数:request
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getStockInfoForQuery( Part_requisitionBean bean ){
        List Stockinfo = null;

        try{
            //获得入库单id,入库人姓名,入库时间,代维单位名称,对应申请单编号,申请的原因
            String sql = "select st.stockid,TO_CHAR(st.STOCKTIME,'YYYY-MM-DD') stocktime,st.REID, "
                         + "     us.username,con.contractorname,re.REASON  "
                         + " from part_stock st, userinfo us, contractorinfo con, part_requisition re "
                         +
                         "  where st.STOCKUSERID = us.USERID and st.contractorid = con.contractorid and st.REID = re.REID "
                         + " and st.contractorid = '" + bean.getcontractorid() + "'";

            if( bean.getStockuserid() != null && !bean.getStockuserid().equals( "" ) ){
                sql = sql + " and us.username like '" + bean.getStockuserid() + "%' ";
            }
            if( bean.getReid() != null && !bean.getReid().equals( "" ) ){
                sql = sql + "  and st.reid='" + bean.getReid() + "'  ";
            }
            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                sql = sql + " and st.stocktime >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD ')";
            }
            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                sql = sql + " and st.stocktime <= TO_DATE('" + bean.getEndtime() + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
            }
            sql = sql + "  order by st.stocktime desc";
            logger.info("SQL:" + sql);
            QueryUtil query = new QueryUtil();
            Stockinfo = query.queryBeans( sql );
            return Stockinfo;
        }
        catch( Exception e ){
            logger.error( "在按条件获得所有入库单信息发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:按指定条件获得当前登陆代维单位的 材料入库情况统计(代维单位的查询)
     * <br>参数:代维单位id,bean
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getAllPartUse( Part_requisitionBean bean, String contractorid ){
        List useinfo = null;
        try{
            String sql = "SELECT DISTINCT psb.ID,con.CONTRACTORNAME,pb.NAME,pb.TYPE,pb.UNIT,psb.STOCKNUMBER,TO_CHAR(ps.STOCKTIME ,'yyyy-MM-dd') stocktime"
                         + " FROM PART_STOCK_BASEINFO psb,PART_STOCK ps,PART_BASEINFO pb,CONTRACTORINFO con "
                         + " WHERE psb.ID = pb.ID"
                         + "       AND psb.STOCKID = ps.STOCKID "
                         + "       AND con.CONTRACTORID = ps.CONTRACTORID "
                         + " and ps.CONTRACTORID='" + contractorid + "' ";
            if( !bean.getcontractorid().equals( "" ) && bean.getcontractorid() != null ){
                sql = sql + " and ps.contractorid = '" + bean.getcontractorid() + "' ";
            }
            if( !bean.getName().equals( "" ) && bean.getName() != null ){
                sql = sql + " and pb.name = '" + bean.getName() + "' ";
            }
            if( !bean.getType().equals( "" ) && bean.getType() != null ){
                sql = sql + " and pb.type = '" + bean.getType() + "'  ";
            }
            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                sql = sql + " and ps.stocktime >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
            }
            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                sql = sql + " and ps.stocktime <= TO_DATE('" + bean.getEndtime() + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
            }
            sql = sql + " ORDER BY con.CONTRACTORNAME,pb.NAME,stocktime DESC ";
            //System.out.println( "SQL:" + sql );

            QueryUtil query = new QueryUtil();
            useinfo = query.queryBeans( sql );
            return useinfo;
        }
        catch( Exception e ){
            logger.error( "在获得当前登陆代维单位的所有材料入库情况统计信息中发生异常:" + e.getMessage() );
            return null;
        }
    }

    /**
     * <br>功能:按指定条件获得 材料入库情况统计(移动公司的查询)
     * <br>参数:代维单位id,bean
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getAllPartUse( Part_requisitionBean bean ){
        List useinfo = null;
        try{
            String sql = "SELECT DISTINCT psb.ID,con.CONTRACTORNAME,pb.NAME,pb.TYPE,pb.UNIT,psb.STOCKNUMBER,TO_CHAR(ps.STOCKTIME ,'yyyy-MM-dd') stocktime"
                         + " FROM PART_STOCK_BASEINFO psb,PART_STOCK ps,PART_BASEINFO pb,CONTRACTORINFO con "
                         + " WHERE psb.ID = pb.ID"
                         + "       AND psb.STOCKID = ps.STOCKID "
                         + "       AND con.CONTRACTORID = ps.CONTRACTORID "
                         + "       AND con.regionid='" + bean.getRegionid() + "'";

            if( !bean.getcontractorid().equals( "" ) && bean.getcontractorid() != null ){
                sql = sql + " and ps.contractorid = '" + bean.getcontractorid() + "' ";
            }
            if( !bean.getName().equals( "" ) && bean.getName() != null ){
                sql = sql + " and pb.name = '" + bean.getName() + "' ";
            }
            if( !bean.getType().equals( "" ) && bean.getType() != null ){
                sql = sql + " and pb.type = '" + bean.getType() + "'  ";
            }
            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                sql = sql + " and ps.stocktime >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
            }
            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                sql = sql + " and ps.stocktime <= TO_DATE('" + bean.getEndtime() + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
            }
            sql = sql + " ORDER BY con.CONTRACTORNAME,pb.NAME,stocktime DESC ";
            //System.out.println( "SQL:" + sql );
            QueryUtil query = new QueryUtil();
            useinfo = query.queryBeans( sql );
            return useinfo;
        }
        catch( Exception e ){
            logger.error( "在获得当前登陆代维单位的所有入库信息中(yidong)发生异常:" + e.getMessage() );
            return null;
        }
    }


/////////////////////////For OldStock ///////////////////

    /**
     * <br>功能:写入利旧入库信息,包括利旧基本表和利旧_材料对应表
     * <br>参数:材料表基本信息bean,材料id数组,入库数量oldnumber数组
     * <br>返回值:获得成功返回true,否则返回 false;
     */
    public boolean addOldStockInfo( Part_requisitionBean bean, String[] id, String[] oldnumber ){
        String sql1 = ""; //写入基本表的
        String sql2 = ""; //写入对应表的
        String sql3 = ""; //写入库存表的
        java.util.Date date = new java.util.Date();
        SimpleDateFormat dtFormat = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss" );
        String strDt = dtFormat.format( date );

        try{
            UpdateUtil exec = new UpdateUtil();
            exec.setAutoCommitFalse();
            sql1 = "insert into part_oldstock (oldid,contractorid,olduserid,oldtime,oldreason,oldremark) values ('"
                   + bean.getOldid() + "','" + bean.getcontractorid() + "','" + bean.getOlduserid() + "',"
                   + "TO_DATE('" + strDt + "','YYYY-MM-DD HH24:MI:SS'),'" + bean.getOldreason() + "','"
                   + bean.getOldremark() + "')";
            try{
                exec.executeUpdate( sql1 ); //写入基本表
                for( int i = 0; i < id.length; i++ ){ //将材料信息分别写入对应表和库存表
                    sql2 = "insert into part_oldstock_baseinfo (oldid,id,oldnumber) values ('"
                           + bean.getOldid() + "','" + id[i] + "'," + Integer.parseInt( oldnumber[i] )
                           + ")";
                    exec.executeUpdate( sql2 );

                    if( this.valiExistForStorage( id[i], bean.getcontractorid() ) ){ // 库存中有该单位的该材料,要更新
                        sql3 = " update part_storage set oldnumber = oldnumber + " + Integer.parseInt( oldnumber[i] )
                               + "  where contractorid='" + bean.getcontractorid() + "' and id ='" + id[i] + "'";
                    }
                    else{
                        sql3 = "insert into part_storage (id,contractorid,newesse,oldnumber,newshould) values ('"
                               + id[i] + "','" + bean.getcontractorid() + "',0," + Integer.parseInt( oldnumber[i] )
                               + ",0)";
                    }
                    exec.executeUpdate( sql3 );
                }
                exec.commit();
                exec.setAutoCommitTrue();
                return true;
            }
            catch( Exception e1 ){
                logger.error( "写入利旧入库信息出错:" + e1.getMessage() );
                exec.rollback();
                exec.setAutoCommitTrue();
                return false;
            }
        }
        catch( Exception e ){
            logger.error( "写入利旧入库信息,包括利旧基本表和利旧_材料对应表中出错:" + e.getMessage() );
            return false;
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
     * <br>功能:获得当前登陆代维单位的所有利旧材料入库单信息
     * <br>参数:request
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getAllOldStock( String contractorid ){
        List oldStock = null;
        try{
            //
            String sql = "select old.OLDID,con.CONTRACTORNAME,us.USERNAME,TO_CHAR(old.OLDTIME,'YYYY-MM-DD') oldtime,old.OLDREASON,old.OLDREMARK "
                         + " from part_oldstock old,contractorinfo con,userinfo us "
                         + " where old.CONTRACTORID = con.CONTRACTORID and old.OLDUSERID = us.USERID "
                         + " and old.contractorid = '" + contractorid + "'"
                         + " order by old.oldtime";
            QueryUtil query = new QueryUtil();
            oldStock = query.queryBeans( sql );
            return oldStock;
        }
        catch( Exception e ){
            logger.error( "在获得当前登陆代维单位的所有利旧材料入库单信息中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:获得指定利旧材料入库单信息
     * <br>参数:利旧材料入库单编号
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public Part_requisitionBean getOneOldStock( String oldid ){
        List oldStock = null;
        Part_requisitionBean oldinfo = new Part_requisitionBean();
        ResultSet rst = null;
        String sql = "select old.OLDID,con.CONTRACTORNAME,us.USERNAME,TO_CHAR(old.OLDTIME,'YYYY-MM-DD HH24:MI:SS') oldtime,old.OLDREASON,old.OLDREMARK "
                     + " from part_oldstock old,contractorinfo con,userinfo us "
                     + " where old.CONTRACTORID = con.CONTRACTORID and old.OLDUSERID = us.USERID "
                     + " and old.oldid = '" + oldid + "'";
        try{
            QueryUtil query = new QueryUtil();
            rst = query.executeQuery( sql );
            rst.next();
            oldinfo.setOldid( rst.getString( "oldid" ) );
            oldinfo.setContractorname( rst.getString( "contractorname" ) );
            oldinfo.setOldtime( rst.getString( "oldtime" ) );
            oldinfo.setOldreason( rst.getString( "oldreason" ) );
            oldinfo.setOldremark( rst.getString( "oldremark" ) );
            oldinfo.setUsername( rst.getString( "username" ) );
            rst.close();
            return oldinfo;
        }
        catch( Exception e ){
            logger.error( "在获得当前登陆代维单位的所有利旧材料入库单信息中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:获得利旧材料入库单的入库材料信息
     * <br>参数:request
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getPartOfOneOldStock( String oldid ){
        List oldStock = null;
        try{
            String sql = "select  old.id,old.oldnumber,base.name,base.type,base.unit "
                         + " from part_oldstock_baseinfo old,part_baseinfo base "
                         + " where old.ID =base.id and oldid='" + oldid + "'"
                         + " order by base.name";
            QueryUtil query = new QueryUtil();
            oldStock = query.queryBeans( sql );
            return oldStock;
        }
        catch( Exception e ){
            logger.error( "在获得当前登陆代维单位的所有利旧材料入库单信息中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:获得利旧材料入库单的入库人列表
     * <br>参数:
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getOldStockUsername( String contractorid ){
        List oldStock = null;
        try{
            String sql =
                "select distinct old.olduserid,us.username from part_oldstock  old,userinfo us where old.olduserid = us.userid"
                + " and old.contractorid='" + contractorid + "'";
            QueryUtil query = new QueryUtil();
            oldStock = query.queryBeans( sql );
            return oldStock;
        }
        catch( Exception e ){
            logger.error( "在获得利旧材料入库单的入库人列表中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:获得利旧材料入库单的材料来源列表
     * <br>参数:
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getOldStockOLdreason( String contractorid ){
        List oldStock = null;
        try{
            String sql = "select distinct oldreason from part_oldstock  where part_oldstock.contractorid='"
                         + contractorid + "'";
            QueryUtil query = new QueryUtil();
            oldStock = query.queryBeans( sql );
            return oldStock;
        }
        catch( Exception e ){
            logger.error( "在获得利旧材料入库单的材料来源列表中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:按条件获得所有利旧入库单信息
     * <br>参数:request
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getOldStockInfoForQuery( Part_requisitionBean bean ){
        List lOldStockinfo = null;

        try{
            String sql = "select old.OLDID,con.CONTRACTORNAME,us.USERNAME,TO_CHAR(old.OLDTIME,'YYYY-MM-DD') oldtime,old.OLDREASON,old.OLDREMARK "
                         + " from part_oldstock old,contractorinfo con,userinfo us "
                         + " where old.CONTRACTORID = con.CONTRACTORID and old.OLDUSERID = us.USERID "
                         + " and old.contractorid = '" + bean.getcontractorid() + "'";

            if( bean.getOlduserid() != null && !bean.getOlduserid().equals( "" ) ){
                sql = sql + " and us.username like '" + bean.getOlduserid() + "%' ";
            }
            if( bean.getOldreason() != null && !bean.getOldreason().equals( "" ) ){
                sql = sql + "  and old.oldreason like '" + bean.getOldreason() + "%'  ";
            }
            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                sql = sql + " and old.oldtime >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
            }
            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                sql = sql + " and old.oldtime <= TO_DATE('" + bean.getEndtime() + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
            }
            sql = sql + "  order by oldtime desc";
            QueryUtil query = new QueryUtil();
            lOldStockinfo = query.queryBeans( sql );
            return lOldStockinfo;
        }
        catch( Exception e ){
            logger.error( "在按条件获得所有利旧入库单信息发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * 获得指定的入库单信息和材料信息
     * @param bean Part_requisitionBean
     * @return List
     */
    public List getStockList( Part_requisitionBean bean ){
        List Stockinfo = null;
        List Partinfo = null;
        try{
            //获得入库单id,入库人姓名,入库时间,代维单位名称,对应申请单编号,申请的原因
            String sql = "select st.stockid,TO_CHAR(st.STOCKTIME,'YYYY-MM-DD') stocktime,st.REID, "
                         + "     us.username,con.contractorname,re.REASON  "
                         + " from part_stock st, userinfo us, contractorinfo con, part_requisition re "
                         +
                         "  where st.STOCKUSERID = us.USERID and st.contractorid = con.contractorid and st.REID = re.REID "
                         + " and st.contractorid = '" + bean.getcontractorid() + "'";

            if( bean.getStockuserid() != null && !bean.getStockuserid().equals( "" ) ){
                sql = sql + " and st.stockuserid ='" + bean.getStockuserid() + "' ";
            }
            if( bean.getReid() != null && !bean.getReid().equals( "" ) ){
                sql = sql + "  and st.reid='" + bean.getReid() + "'  ";
            }
            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                sql = sql + " and st.stocktime >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
            }
            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                sql = sql + " and st.stocktime <= TO_DATE('" + bean.getEndtime() + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
            }
            sql = sql + "  order by st.stocktime desc";
            logger.info( "query sql<<<<<<<<<<<<<<:" + sql );
            QueryUtil query = new QueryUtil();
            Stockinfo = query.queryBeans( sql );

            //获得材料信息：材料数量、入库数量、计量单位、规格型号、入库单id

            String sql2 = "select  b.NAME,b.unit,b.type,b.remark,st.stocknumber, st.STOCKID " +
                          "from part_stock_baseinfo st, part_baseinfo b " +
                          "where st.ID = b.id and " +
                          "st.STOCKID in( select st.stockid " +
                          "from part_stock st, userinfo us, contractorinfo con, part_requisition re " +
                          "where st.STOCKUSERID = us.USERID " +
                          "and st.contractorid = con.contractorid " +
                          "and st.REID = re.REID " +
                          "and st.contractorid = '" + bean.getcontractorid() + "'";

            if( bean.getStockuserid() != null && !bean.getStockuserid().equals( "" ) ){
                sql2 = sql2 + " and st.stockuserid ='" + bean.getStockuserid() + "' ";
            }
            if( bean.getReid() != null && !bean.getReid().equals( "" ) ){
                sql2 = sql2 + "  and st.reid='" + bean.getReid() + "'  ";
            }
            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                sql2 = sql2 + " and st.stocktime >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
            }
            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                sql2 = sql2 + " and st.stocktime <= TO_DATE('" + bean.getEndtime() + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
            }
            sql2 += " ) ";
            logger.info( "query sql2<<<<<<<<<<<<<<:" + sql2 );
            Partinfo = query.queryBeans( sql2 );

            List list = ( List )new ArrayList();
            list.add( Stockinfo );
            list.add( Partinfo );

            return list;
        }
        catch( Exception e ){
            logger.error( "在按条件获得所有入库单信息发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * 获得指定的入库单信息和材料信息
     * @param bean Part_requisitionBean
     * @return List
     */
    public List getOldUseList( Part_requisitionBean bean ){
        List OldStockinfo = null;
        List Partinfo = null;
        try{
            //获得入库单id,入库人姓名,入库时间,代维单位名称,对应申请单编号,申请的原因
            String sql = "select distinct  old.OLDID,con.CONTRACTORNAME,us.USERNAME,TO_CHAR(old.OLDTIME,'YYYY-MM-DD') oldtime,old.OLDREASON,old.OLDREMARK "
                         + " from part_oldstock old,contractorinfo con,userinfo us "
                         + " where old.CONTRACTORID = con.CONTRACTORID and old.OLDUSERID = us.USERID "
                         + " and old.contractorid = '" + bean.getcontractorid() + "'";

            if( bean.getOlduserid() != null && !bean.getOlduserid().equals( "" ) ){
                sql = sql + " and old.olduserid ='" + bean.getOlduserid() + "' ";
            }
            if( bean.getOldreason() != null && !bean.getOldreason().equals( "" ) ){
                sql = sql + "  and old.oldreason='" + bean.getOldreason() + "'  ";
            }
            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                sql = sql + " and old.oldtime >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
            }
            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                sql = sql + " and old.oldtime <= TO_DATE('" + bean.getEndtime() + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
            }
            sql = sql + "  order by oldtime desc";
            //         System.out.println( "query sql<<<<<<<<<<<<<<:" + sql );
            QueryUtil query = new QueryUtil();
            OldStockinfo = query.queryBeans( sql );

            //获得材料信息：材料数量、入库数量、计量单位、规格型号、入库单id
            String sql2 = "select  old.oldid, old.id, old.oldnumber, base.name, base.type, base.unit "
                          + " from part_oldstock_baseinfo old, part_baseinfo base "
                          + " where old.ID =base.id "
                          + " and old.id in ( select old.id "
                          + " from part_oldstock old,contractorinfo con,userinfo us "
                          + " where old.CONTRACTORID = con.CONTRACTORID and old.OLDUSERID = us.USERID "
                          + " and old.contractorid = '" + bean.getcontractorid() + "'";

            if( bean.getOlduserid() != null && !bean.getOlduserid().equals( "" ) ){
                sql2 = sql2 + " and old.olduserid ='" + bean.getOlduserid() + "' ";
            }
            if( bean.getOldreason() != null && !bean.getOldreason().equals( "" ) ){
                sql2 = sql2 + "  and old.oldreason='" + bean.getOldreason() + "'  ";
            }
            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                sql2 = sql2 + " and old.oldtime >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
            }
            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                sql2 = sql2 + " and old.oldtime <= TO_DATE('" + bean.getEndtime() + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
            }
            sql2 += " ) ";
            //           System.out.println( "query sql2<<<<<<<<<<<<<<:" + sql2 );
            Partinfo = query.queryBeans( sql2 );

            List list = ( List )new ArrayList();
            list.add( OldStockinfo );
            list.add( Partinfo );

            return list;
        }
        catch( Exception e ){
            logger.error( "在按条件获得所有入库单信息发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:按指定条件获得当前登陆代维单位的 利旧材料入库情况统计(代维单位的查询)
     * <br>参数:代维单位id,bean
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getAllPartUseOld( Part_requisitionBean bean, String contractorid ){
        List useinfo = null;
        try{
            String sql = "SELECT DISTINCT psb.ID,con.CONTRACTORNAME,pb.NAME,pb.TYPE,pb.UNIT,psb.OLDNUMBER,TO_CHAR(ps.OLDTIME ,'yyyy-MM-dd') stocktime"
                         + " FROM PART_OLDSTOCK_BASEINFO psb,PART_OLDSTOCK ps,PART_BASEINFO pb,CONTRACTORINFO con "
                         + " WHERE psb.ID = pb.ID"
                         + "       AND psb.OLDID = ps.OLDID "
                         + "       AND con.CONTRACTORID = ps.CONTRACTORID "

                         + " and ps.CONTRACTORID='" + contractorid + "' ";
            if( !bean.getcontractorid().equals( "" ) && bean.getcontractorid() != null ){
                sql = sql + " and ps.contractorid = '" + bean.getcontractorid() + "' ";
            }
            if( !bean.getName().equals( "" ) && bean.getName() != null ){
                sql = sql + " and pb.name = '" + bean.getName() + "' ";
            }
            if( !bean.getType().equals( "" ) && bean.getType() != null ){
                sql = sql + " and pb.type = '" + bean.getType() + "'  ";
            }
            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                sql = sql + " and ps.oldtime >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
            }
            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                sql = sql + " and ps.oldtime <= TO_DATE('" + bean.getEndtime() + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
            }

            sql = sql + " ORDER BY con.CONTRACTORNAME,pb.NAME,stocktime DESC ";
            //System.out.println( "SQL:" + sql );

            QueryUtil query = new QueryUtil();
            useinfo = query.queryBeans( sql );
            return useinfo;
        }
        catch( Exception e ){
            logger.error( "在获得当前登陆代维单位的所有材料入库情况统计信息中发生异常:" + e.getMessage() );
            return null;
        }
    }


    /**
     * <br>功能:按指定条件获得 利旧材料入库情况统计(移动公司的查询)
     * <br>参数:代维单位id,bean
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getAllPartUseOld( Part_requisitionBean bean ){
        List useinfo = null;
        try{
            String sql = "SELECT DISTINCT psb.ID,con.CONTRACTORNAME,pb.NAME,pb.TYPE,pb.UNIT,psb.OLDNUMBER,TO_CHAR(ps.OLDTIME ,'yyyy-MM-dd') stocktime"
                         + " FROM PART_OLDSTOCK_BASEINFO psb,PART_OLDSTOCK ps,PART_BASEINFO pb,CONTRACTORINFO con "
                         + " WHERE psb.ID = pb.ID"
                         + "       AND psb.OLDID = ps.OLDID "
                         + "       AND con.CONTRACTORID = ps.CONTRACTORID "
                         + "       AND con.regionid='" + bean.getRegionid() + "'";

            if( !bean.getcontractorid().equals( "" ) && bean.getcontractorid() != null ){
                sql = sql + " and ps.contractorid = '" + bean.getcontractorid() + "' ";
            }
            if( !bean.getName().equals( "" ) && bean.getName() != null ){
                sql = sql + " and pb.name = '" + bean.getName() + "' ";
            }
            if( !bean.getType().equals( "" ) && bean.getType() != null ){
                sql = sql + " and pb.type = '" + bean.getType() + "'  ";
            }
            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                sql = sql + " and ps.oldtime >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
            }
            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                sql = sql + " and ps.oldtime <= TO_DATE('" + bean.getEndtime() + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
            }
            sql = sql + " ORDER BY con.CONTRACTORNAME,pb.NAME,stocktime DESC ";
            //System.out.println( "SQL:" + sql );
            QueryUtil query = new QueryUtil();
            useinfo = query.queryBeans( sql );
            return useinfo;
        }
        catch( Exception e ){
            logger.error( "在获得当前登陆代维单位的所有入库信息中(yidong)发生异常:" + e.getMessage() );
            return null;
        }
    }

    /**
     * <br>功能:获得所有入库单信息
     * <br>参数:request
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getAllStock( HttpServletRequest request ){
        List Stockinfo = null;
        UserInfo userinfo=(UserInfo)request.getSession().getAttribute("LOGIN_USER");
        String deptid=userinfo.getDeptID();
        try{
            //获得入库单id,入库人姓名,入库时间,代维单位名称,对应申请单编号,申请的原因
            String sql = "select st.stockid,TO_CHAR(st.STOCKTIME,'YYYY-MM-DD') stocktime,st.REID, "
                         + "     us.username,con.contractorname,re.REASON  "
                         + " from part_stock st, userinfo us, contractorinfo con, part_requisition re ";
            QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );
            sqlBuild.addConstant("  st.STOCKUSERID = us.USERID and st.contractorid = con.contractorid and st.REID = re.REID ");
            sqlBuild.addConditionAnd("con.regionid={0}",request.getParameter("regionid"));
            sqlBuild.addConditionAnd(" st.contractorid={0}",request.getParameter("contractorid"));
            sqlBuild.addConstant(" and ( st.stockid in ("
                +"                   select stockid from part_stock_baseinfo where id in ("
                +"                     select id from part_baseinfo where 1=1");
            sqlBuild.addConditionAnd(" name={0} ",request.getParameter("name"));
            sqlBuild.addConditionAnd(" type={0} ",request.getParameter("type"));
            sqlBuild.addConstant("     )"
                +"                   )"
                +"                 )");
            if(userinfo.getType().equals("21")){
                sqlBuild.addConditionAnd( " st.contractorid in ("
                    + "                         select contractorid from contractorinfo "
                    + "                         where parentcontractorid={0} and (state is null or state<>'1')"
                    + "                    )", deptid );
            }
            if(userinfo.getType().equals("12")){
                sqlBuild.addConditionAnd("st.contractorid in ("
                    +"                      select contractorid from contractorinfo "
                    +"                      where regionid={0} and (state is null or state<>'1')"
                    +"                    )",userinfo.getRegionID());
            }
            if(userinfo.getType().equals("22")){
                sqlBuild.addConditionAnd("st.contractorid={0}",userinfo.getDeptID());
            }
            sqlBuild.addConstant(" order by stocktime");
            QueryUtil query = new QueryUtil();
            //System.out.println(sqlBuild.toSql());
            Stockinfo = query.queryBeans( sqlBuild.toSql() );
            return Stockinfo;
        }
        catch( Exception e ){
            logger.error( "在显示申请单全部信息中发生异常:" + e.getMessage() );
            return null;
        }
    }
    
    public List getDeptArrs( String condition ){
        List lUser = null;
        String sql = "select distinct ps.CONTRACTORID,con.CONTRACTORNAME "
                     + "from PART_STOCK ps,contractorinfo con "
                     + " where ps.CONTRACTORID = con.CONTRACTORID and (con.state is null or con.state<>'1')";
        sql+=condition;
        try{
            QueryUtil query = new QueryUtil();
            lUser = query.queryBeans( sql );
            return lUser;
        }
        catch( Exception e ){
            logger.error( "在获得材料使用表中所有涉及的单位名称和单位id列表中出错:" + e.getMessage() );
            return null;
        }
    }

    /**
     * <br>功能:获得当前登陆代维单位的所有利旧材料入库单信息
     * <br>参数:request
     * <br>返回值:获得成功返回List,否则返回 NULL;
     */
    public List getAllOldStock(HttpServletRequest request){
        List oldStock = null;
        UserInfo userinfo=(UserInfo)request.getSession().getAttribute("LOGIN_USER");
        String deptid=userinfo.getDeptID();
        try{
            //
            String sql = "select old.OLDID,con.CONTRACTORNAME,us.USERNAME,TO_CHAR(old.OLDTIME,'YYYY-MM-DD') oldtime,old.OLDREASON,old.OLDREMARK "
                         + " from part_oldstock old,contractorinfo con,userinfo us ";
            QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );
            sqlBuild.addConstant(" old.CONTRACTORID = con.CONTRACTORID and old.OLDUSERID = us.USERID ");
            sqlBuild.addConditionAnd("con.regionid={0}",request.getParameter("regionid"));
            sqlBuild.addConditionAnd(" old.contractorid={0}",request.getParameter("contractorid"));
            sqlBuild.addConstant(" and ( old.oldid in ("
                +"                   select oldid from part_oldstock_baseinfo where id in ("
                +"                     select id from part_baseinfo where 1=1 ");
            sqlBuild.addConditionAnd(" name={0} ",request.getParameter("name"));
            sqlBuild.addConditionAnd(" type={0} ",request.getParameter("type"));
            sqlBuild.addConstant("     )"
                +"                   )"
                +"                 )");
            if(userinfo.getType().equals("21")){
                sqlBuild.addConditionAnd( " old.contractorid in ("
                    + "                         select contractorid from contractorinfo "
                    + "                         where parentcontractorid={0} and (state is null or state<>'1')"
                    + "                    )", deptid );
            }
            if(userinfo.getType().equals("12")){
                sqlBuild.addConditionAnd("old.contractorid in ("
                    +"                      select contractorid from contractorinfo "
                    +"                      where regionid={0} and (state is null or state<>'1')"
                    +"                    )",userinfo.getRegionID());
            }
            if(userinfo.getType().equals("22")){
                sqlBuild.addConditionAnd("old.contractorid={0}",userinfo.getDeptID());
            }
            sqlBuild.addConstant(" order by old.oldtime");
            QueryUtil query = new QueryUtil();
            //System.out.println(sqlBuild.toSql());
            oldStock = query.queryBeans( sqlBuild.toSql() );
            return oldStock;
        }
        catch( Exception e ){
            logger.error( "在获得当前登陆代维单位的所有利旧材料入库单信息中发生异常:" + e.getMessage() );
            return null;
        }
    }

    public List getAllPartUse(  String condition , Part_requisitionBean bean){
        List useinfo = null;
        try{
            String sql = "SELECT psb.ID,con.CONTRACTORNAME,pb.NAME,pb.TYPE,pb.UNIT,psb.STOCKNUMBER,TO_CHAR(ps.STOCKTIME ,'yyyy-MM-dd') stocktime"
                         + " FROM PART_STOCK_BASEINFO psb,PART_STOCK ps,PART_BASEINFO pb,CONTRACTORINFO con "
                         + " WHERE psb.ID = pb.ID"
                         + "       AND psb.STOCKID = ps.STOCKID "
                         + "       AND con.CONTRACTORID = ps.CONTRACTORID "+condition;
            if( bean.getContractorId()!=null&&!bean.getContractorId().equals("")){
                sql+=" and con.contractorid='"+bean.getContractorId()+"'";
            }
            if( !bean.getName().equals( "" ) && bean.getName() != null ){
                sql = sql + " and pb.name = '" + bean.getName() + "' ";
            }
            if( !bean.getType().equals( "" ) && bean.getType() != null ){
                sql = sql + " and pb.type = '" + bean.getType() + "'  ";
            }
            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                sql = sql + " and ps.stocktime >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
            }
            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                sql = sql + " and ps.stocktime <= TO_DATE('" + bean.getEndtime() + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
            }
            sql = sql + " ORDER BY con.CONTRACTORNAME,pb.NAME,stocktime DESC ";
            //System.out.println( "SQL:" + sql );

            QueryUtil query = new QueryUtil();
            useinfo = query.queryBeans( sql );
            return useinfo;
        }
        catch( Exception e ){
            logger.error( "在获得当前登陆代维单位的所有材料入库情况统计信息中发生异常:" + e.getMessage() );
            return null;
        }
    }

    public List getAllPartUseOld( String condition , Part_requisitionBean bean ){
        List useinfo = null;
        try{
            String sql = "SELECT psb.ID,con.CONTRACTORNAME,pb.NAME,pb.TYPE,pb.UNIT,psb.OLDNUMBER,TO_CHAR(ps.OLDTIME ,'yyyy-MM-dd') stocktime"
                         + " FROM PART_OLDSTOCK_BASEINFO psb,PART_OLDSTOCK ps,PART_BASEINFO pb,CONTRACTORINFO con "
                         + " WHERE psb.ID = pb.ID"
                         + "       AND psb.OLDID = ps.OLDID "
                         + "       AND con.CONTRACTORID = ps.CONTRACTORID "+condition;
            if( bean.getContractorId()!=null&&!bean.getContractorId().equals("")){
                sql+=" and con.contractorid='"+bean.getContractorId()+"'";
            }
            if( !bean.getName().equals( "" ) && bean.getName() != null ){
                sql = sql + " and pb.name = '" + bean.getName() + "' ";
            }
            if( !bean.getType().equals( "" ) && bean.getType() != null ){
                sql = sql + " and pb.type = '" + bean.getType() + "'  ";
            }
            if( bean.getBegintime() != null && !bean.getBegintime().equals( "" ) ){
                sql = sql + " and ps.oldtime >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
            }
            if( bean.getEndtime() != null && !bean.getEndtime().equals( "" ) ){
                sql = sql + " and ps.oldtime <= TO_DATE('" + bean.getEndtime() + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
            }
            sql = sql + " ORDER BY con.CONTRACTORNAME,pb.NAME,stocktime DESC ";
            //System.out.println( "SQL:" + sql );
            QueryUtil query = new QueryUtil();
            useinfo = query.queryBeans( sql );
            return useinfo;
        }
        catch( Exception e ){
            logger.error( "在获得当前登陆代维单位的所有入库信息中(yidong)发生异常:" + e.getMessage() );
            return null;
        }
    }
}
