package com.cabletech.baseinfo.services;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.beans.TroubleCodeBean;
import com.cabletech.baseinfo.beans.TroubleTypeBean;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.commons.sm.*;

public class TroubleCodeBo extends BaseBisinessObject{

    private Logger logger = Logger.getLogger( "TroubleCodeBo" );

    /**
     * 获取region 内的隐患类型以及隐患码。
     * @param regionid
     * @return  List数组对象
     */
    public List get_TroubleTypeObject( String regionid ){
        String sql = "SELECT tc.ID, tc.TROUBLECODE, tc.TROUBLETYPE, tc.troublename, "
                     +
                     "      decode(tc.emergencylevel,1,'轻微',2,'中度',3,'严重') emergencylevel, tc.remark, tt.code, tt.typename, tc.regionid "
                     + " FROM TROUBLECODE tc, TROUBLETYPE tt "
                     + " WHERE tc.TROUBLETYPE = tt.ID "
                     + "AND (tc.regionid in(SELECT     regionid"
                     + "                    FROM REGION "
                     + "                    CONNECT BY PRIOR regionid = parentregionid "
                     + "                    START WITH regionid = '" + regionid + "') "
                     + " OR SUBSTR(tc.REGIONID,3,6)='0000')"
                     + " order by tt.code";
        logger.info( "SQL:" + sql );
        QueryUtil query;
        List tt;
        try{
            query = new QueryUtil();
            tt = query.queryBeans( sql );
            return tt;

        }
        catch( Exception e ){
            logger.error( "查找所有的故障代码出错:" + e.getMessage() );
            return null;
        }
    }


    /**
     * 获得指定区域和省级区域下的隐患类型
     * */
    public List getTroubleType( String regionid ){
        String sql = "SELECT t.id code,t.TYPENAME "
                     + " FROM TROUBLETYPE t "
                     + " WHERE t.REGIONID='" + regionid + "' OR SUBSTR(t.REGIONID,3,6)='0000'";
        QueryUtil qu;
        List list;
        try{
            qu = new QueryUtil();
            list = qu.queryBeans( sql );
            return list;
        }
        catch( Exception ex ){
            logger.error( "获得隐患类型出错:" + ex.getMessage() );
            return null;
        }
    }


    /**
     * 获得所有隐患类型
     * */
    public List getAllTroubleType(){
        String sql = "SELECT t.id CODE,t.TYPENAME ,t.code typecode"
                     + " FROM TROUBLETYPE t ";
        QueryUtil qu;
        List list;
        try{
            qu = new QueryUtil();
            list = qu.queryBeans( sql );
            return list;
        }
        catch( Exception ex ){
            logger.error( "获得所有隐患类型出错:" + ex.getMessage() );
            return null;
        }
    }


    /**
     * 获得所有隐患码
     * */
    public List getAllTroubleCode(){
        String sql = " SELECT t.TROUBLECODE,t.TROUBLENAME FROM TROUBLECODE t";
        QueryUtil qu;
        List list;
        try{
            qu = new QueryUtil();
            list = qu.queryBeans( sql );
            return list;
        }
        catch( Exception ex ){
            logger.error( "获得所有隐患码出错:" + ex.getMessage() );
            return null;
        }
    }


    /**
     * 添加隐患码
     * */
    public boolean add_TroubleCode( TroubleCodeBean bean ){
        String sql = "INSERT INTO TROUBLECODE(ID,TROUBLECODE,troublename,"
                     + "troubletype,EMERGENCYLEVEL,REGIONID,NOTICECYCLE,HANDLECYCLE) values('"
                     + bean.getId() + "','" + bean.getTroublecode() + "','"
                     + bean.getTroublename() + "','" + bean.getTroubletype() + "','"
                     + bean.getEmergencylevel() + "','" + bean.getRegionid() + "',"//+bean.getMustHandle()+"',"
                     +bean.getNoticeCycle()+","+bean.getHandleCycle()+")";
        logger.info( "SQL:" + sql );
        try{
            UpdateUtil up = new UpdateUtil();
            up.executeUpdate( sql );
            return true;
        }
        catch( Exception ex ){
            logger.error( "添加隐患码出错:" + ex.getMessage() );
            return false;
        }
    }


    /**
     * 获得隐患码以供编辑
     * */
    public TroubleCodeBean get_TroubleCode( String id ){
        String sql = "SELECT tc.ID,tc.TROUBLECODE,tc.TROUBLENAME,tc.EMERGENCYLEVEL,"
                     + "     tt.TYPENAME,tc.TROUBLETYPE,tc.MUSTHANDLE,tc.NOTICECYCLE,tc.HANDLECYCLE "
                     + " FROM troublecode tc,troubletype tt "
                     + " WHERE tc.TROUBLETYPE = tt.ID "
                     + "       AND tc.ID = '" + id + "'";
        logger.info( "SQL:" + sql );
        ResultSet rst;
        TroubleCodeBean bean = new TroubleCodeBean();
        try{
            QueryUtil qu = new QueryUtil();
            rst = qu.executeQuery( sql );
            if( rst.next() ){
                bean.setId( rst.getString( "id" ) );
                bean.setTroublecode( rst.getString( "troublecode" ) );
                bean.setTroublename( rst.getString( "troublename" ) );
                bean.setEmergencylevel( rst.getString( "emergencylevel" ) );
                bean.setTypename( rst.getString( "typename" ) );
                bean.setTroubletype( rst.getString( "troubletype" ) );
                bean.setMustHandle(rst.getString("musthandle"));
                bean.setNoticeCycle(Integer.valueOf(rst.getString("noticecycle")));
                bean.setHandleCycle(Integer.valueOf(rst.getString("handlecycle")));
            }
            rst.close();
            return bean;
        }
        catch( Exception ex ){
            logger.error( "获得隐患码以供编辑出错:" + ex.getMessage() );
            return null;
        }
    }


    /**
     * 编辑隐患码
     * */
    public boolean edit_TroubleCode( TroubleCodeBean bean ){
        String sql = "update troublecode set troublecode='" + bean.getTroublecode() + "',troublename='"
                     + bean.getTroublename() + "',troubletype='" + bean.getTroubletype() + "',emergencylevel='"
                     + bean.getEmergencylevel() + "',"//+"musthandle='"+bean.getMustHandle()+"',"
                     +"noticecycle="+bean.getNoticeCycle()+",handlecycle="+bean.getHandleCycle()
                     + " where id='" + bean.getId() + "'";
        logger.info( "SQL:" + sql );
        try{
            UpdateUtil up = new UpdateUtil();
            up.executeUpdate( sql );
            return true;
        }
        catch( Exception ex ){
            logger.error( "编辑隐患码出错:" + ex.getMessage() );
            return false;
        }
    }


    /**
     * 删除隐患码
     * */
    public boolean delete_TroubleCode( TroubleCodeBean bean ){
        String sql = "delete from troublecode  where id='" + bean.getId() + "'";
        logger.info( "SQL:" + sql );
        try{
            UpdateUtil up = new UpdateUtil();
            up.executeUpdate( sql );
            return true;
        }
        catch( Exception ex ){
            logger.error( "删除隐患码出错:" + ex.getMessage() );
            return false;
        }
    }


////////////////////////////////////////////////////
    /**
     * 获取region 内的隐患类型。
     * @param regionid
     * @return  List数组对象
     */
    public List get_AllTroubleType( String regionid ){
        String sql = " SELECT t.CODE,t.ID,t.REGIONID,t.TYPENAME "
                     + " FROM TROUBLETYPE t "
                     + " WHERE t.regionid IN(SELECT     regionid "
                     + "                    FROM REGION  "
                     + "                     CONNECT BY PRIOR regionid = parentregionid  "
                     + "                     START WITH regionid = '" + regionid + "')"
                     + "      OR SUBSTR(t.REGIONID,3,6)='0000'"
                     + " order by t.code";
        logger.info( "SQL:" + sql );
        QueryUtil query;
        List tt;
        try{
            query = new QueryUtil();
            tt = query.queryBeans( sql );
            return tt;

        }
        catch( Exception e ){
            logger.error( "获取region 内的隐患类型出错:" + e.getMessage() );
            return null;
        }
    }


    /**
     * 添加隐患类型
     * */
    public boolean add_TroubleType( TroubleCodeBean bean ){
        String sql = "insert into troubletype(id,code,typename,regionid) values('"
                     + bean.getId() + "','" + bean.getTroublecode() + "','" + bean.getTroublename() + "','"
                     + bean.getRegionid() + "')";
        logger.info( "SQL:" + sql );
        try{
            UpdateUtil up = new UpdateUtil();
            up.executeUpdate( sql );
            return true;
        }
        catch( Exception ex ){
            logger.error( "添加隐患类型出错:" + ex.getMessage() );
            return false;
        }
    }


    /**
     * 获得隐患类型以供编辑
     * */
    public TroubleCodeBean get_TroubleType( String id ){
        String sql = " SELECT t.ID,t.code,t.typename,t.regionid "
                     + "    FROM TROUBLETYPE t"
                     + "    WHERE t.ID='" + id + "'";

        logger.info( "SQL:" + sql );
        ResultSet rst;
        TroubleCodeBean bean = new TroubleCodeBean();
        try{
            QueryUtil qu = new QueryUtil();
            rst = qu.executeQuery( sql );
            if( rst.next() ){
                bean.setId( rst.getString( "id" ) );
                bean.setTroublecode( rst.getString( "code" ) );
                bean.setTroublename( rst.getString( "typename" ) );
            }
            rst.close();
            return bean;
        }
        catch( Exception ex ){
            logger.error( "获得隐患类型以供编辑出错:" + ex.getMessage() );
            return null;
        }
    }


    /**
     * 编辑隐患类型
     * */
    public boolean edit_TroubleType( TroubleCodeBean bean ){
        String sql = "update troubletype set typename='" + bean.getTroublename() + "' "
                     + " where id='" + bean.getId() + "'";
        logger.info( "SQL:" + sql );
        try{
            UpdateUtil up = new UpdateUtil();
            up.executeUpdate( sql );
            return true;
        }
        catch( Exception ex ){
            logger.error( "编辑隐患类型出错:" + ex.getMessage() );
            return false;
        }
    }


    /**
     * 删除隐患类型
     * */
    public boolean delete_TroubleType( TroubleCodeBean bean ){
        String sql = "delete from troubletype  where id='" + bean.getId() + "'";
        logger.info( "SQL:" + sql );
        try{
            UpdateUtil up = new UpdateUtil();
            up.executeUpdate( sql );
            return true;
        }
        catch( Exception ex ){
            logger.error( "删除隐患类型出错:" + ex.getMessage() );
            return false;
        }
    }


    /**
     * 下传故障码
     * */

    public boolean sendTroubleCodeNew( UserInfo userinfo, String[] simnumber, String setnumber ){
        List codeList = this.get_TroubleCodeForSend(userinfo.getRegionID());
        List typeList = this.get_TroubleTpeForSend(userinfo.getRegionID());
        for(int i=0;i<typeList.size();i++){
            ArrayList tempArr = new  ArrayList();
            TroubleTypeBean typeBean =(TroubleTypeBean) typeList.get(i);
            for(int j=0;j<codeList.size();j++){
                TroubleCodeBean codeBean = (TroubleCodeBean)codeList.get(j);
                if(codeBean.getCode().equals(typeBean.getCode())){
                   tempArr.add(codeBean);
                }
            }
            ((TroubleTypeBean) typeList.get(i)).setTroublecode(tempArr);
        }


        TroubleTypeBean[] beanArray = new TroubleTypeBean[typeList.size()];
        for( int i = 0; i < typeList.size(); i++ ){
            beanArray[i] = ( TroubleTypeBean )typeList.get( i );
        }

        SendSMRMI.updateTroubleMenuForSome( beanArray, userinfo, simnumber );

        return true;
    }


    /**
     * 获取region 内的隐患类型以及隐患码。
     * @param regionid
     * @return  List数组对象
     */
    private List get_TroubleCodeForSend( String regionid ){
        String sql = "SELECT tc.ID, tc.TROUBLECODE, tc.TROUBLETYPE, tc.troublename, "
                     + " tt.code, tt.typename "
                     + " FROM TROUBLECODE tc, TROUBLETYPE tt "
                     + " WHERE tc.TROUBLETYPE = tt.ID "
                     + "AND (tc.regionid in(SELECT     regionid"
                     + "                    FROM REGION "
                     + "                    CONNECT BY PRIOR regionid = parentregionid "
                     + "                    START WITH regionid = '" + regionid + "') "
                     + " OR SUBSTR(tc.REGIONID,3,6)='0000')"
                     + " order by tt.code";
        logger.info( "SQL:" + sql );
        QueryUtil query;
        List tt = new ArrayList();
        ResultSet rst;
        try{
            query = new QueryUtil();
            rst = query.executeQuery( sql );
            while( rst != null && rst.next() ){
                TroubleCodeBean bean = new TroubleCodeBean();
                bean.setTroublecode( rst.getString( "troublecode" ) );
                bean.setTroublename( rst.getString( "troublename" ) );
                bean.setCode( rst.getString( "code" ) );
                tt.add( bean );
            }
            rst.close();
            return tt;
        }
        catch( Exception e ){
            logger.error( "查找所有的故障代码出错:" + e.getMessage() );
            return null;
        }
    }


    /**
     * 获取region 内的隐患类型以及隐患码。
     * @param regionid
     * @return  List数组对象
     */
    private List get_TroubleTpeForSend( String regionid ){
        String sql = " select DISTINCT tt.code,tt.typename "
                     + " FROM TROUBLECODE tc, TROUBLETYPE tt "
                     + " WHERE tc.TROUBLETYPE = tt.ID "
                     + "AND (tc.regionid in(SELECT     regionid"
                     + "                    FROM REGION "
                     + "                    CONNECT BY PRIOR regionid = parentregionid "
                     + "                    START WITH regionid = '" + regionid + "') "
                     + " OR SUBSTR(tc.REGIONID,3,6)='0000')"
                     + " order by tt.code";
        logger.info( "SQL:" + sql );
        QueryUtil query;
        List tt = new ArrayList();
        ResultSet rst;
        try{
            query = new QueryUtil();
            rst = query.executeQuery( sql );
            while( rst != null && rst.next() ){
                TroubleTypeBean bean = new TroubleTypeBean();
                bean.setCode( rst.getString( "code" ) );
                bean.setTypename( rst.getString( "typename" ) );
                tt.add( bean );
            }
            rst.close();
            return tt;
        }
        catch( Exception e ){
            logger.error( "查找所有的故障代码出错:" + e.getMessage() );
            return null;
        }
    }

}
