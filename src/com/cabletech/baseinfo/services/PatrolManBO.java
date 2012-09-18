package com.cabletech.baseinfo.services;

import com.cabletech.baseinfo.dao.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.base.*;
import org.apache.struts.util.LabelValueBean;
import com.cabletech.commons.hb.QueryUtil;
import org.apache.commons.beanutils.BasicDynaBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Vector;
import org.apache.log4j.Logger;

public class PatrolManBO extends BaseBisinessObject{
    private static Logger logger = Logger.getLogger("PatrolManBO");
    PatrolManDAOImpl dao = new PatrolManDAOImpl();

    public void addPatrolMan( PatrolMan data ) throws Exception{
        dao.addPatrolMan( data );
    }


    public void removePatrolMan( PatrolMan data ) throws Exception{
        dao.removePatrolMan( data );
    }


    public PatrolMan loadPatrolMan( String id ) throws Exception{
        return dao.findById( id );
    }


    public PatrolMan updatePatrolMan( PatrolMan patrolMan ) throws Exception{
        return dao.updatePatrolMan( patrolMan );
    }


    public String createQuerySqu( UserInfo userinfo ){
        String sql = "";
        //市移动用户
        if( userinfo.getDeptype().equals( "1" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
            sql =
                "select a.patrolID, a.patrolName, decode(a.sex,'1','男','2','女', '男') sex, a.phone, a.mobile, a.postalcode, a.identityCard, a.familyAddress, a.workRecord, nvl(b.contractorname,'') parentID, a.jobInfo, decode(a.jobState,'1','在岗','2','休假','3','离职','在岗') jobState, a.remark from patrolmaninfo a, contractorinfo b where a.parentid = b.contractorid(+) and a.state is null and a.regionid='"
                + userinfo.getRegionID() + "'";
        }
        //代维单位用户
        if( userinfo.getDeptype().equals( "2" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
            sql =
                "select a.patrolID, a.patrolName, decode(a.sex,'1','男','2','女', '男') sex, a.phone, a.mobile, a.postalcode, a.identityCard, a.familyAddress, a.workRecord, nvl(b.contractorname,'') parentID, a.jobInfo, decode(a.jobState,'1','在岗','2','休假','3','离职','在岗') jobState, a.remark from patrolmaninfo a, contractorinfo b where a.parentid = b.contractorid(+) and a.state is null and a.PARENTID ='"
                + userinfo.getDeptID() + "'";
        }
        //省移动用户
        if( userinfo.getDeptype().equals( "1" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
            sql = "select a.patrolID, a.patrolName, decode(a.sex,'1','男','2','女', '男') sex, a.phone, a.mobile, a.postalcode, a.identityCard, a.familyAddress, a.workRecord, nvl(b.contractorname,'') parentID, a.jobInfo, decode(a.jobState,'1','在岗','2','休假','3','离职','在岗') jobState, a.remark from patrolmaninfo a, contractorinfo b where a.parentid = b.contractorid(+) and a.state is null";
        }
        //省代维用户
        if( userinfo.getDeptype().equals( "2" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
            sql = "select a.patrolID, a.patrolName, decode(a.sex,'1','男','2','女', '男') sex, a.phone, a.mobile, a.postalcode, a.identityCard, a.familyAddress, a.workRecord, nvl(b.contractorname,'') parentID, a.jobInfo, decode(a.jobState,'1','在岗','2','休假','3','离职','在岗') jobState, a.remark from patrolmaninfo a, contractorinfo b where a.parentid = b.contractorid(+) and a.state is null and  a.PARENTID in ( SELECT   contractorid FROM contractorinfo CONNECT BY PRIOR contractorid = parentcontractorid START WITH contractorid ='"
                  + userinfo.getDeptID() + "')";
        }
        return sql;

    }
    public List getPatrol( UserInfo userinfo ){

        String sql = "select  patrolname,patrolid from patrolmaninfo where state is null ";
        QueryUtil query = null;
        BasicDynaBean dynaBean = null;
        Vector resultVct = new Vector();
        ArrayList lableList = new ArrayList();

        //市级用户
        if( userinfo.getDeptype().equals( "1" ) &&  !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
             sql += "  and regionid='"+userinfo.getRegionID()+"'";
             sql += "  order by patrolname ";  //add by steven Mar 12,2007 for the purpose of sorting
                 logger.info("syPatrol sql: "+sql);
         }
         //市代维用户
        if( userinfo.getDeptype().equals( "2" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
            sql += " and regionid='"+userinfo.getRegionID()+"' and parentid='" + userinfo.getDeptID() + "'";
            sql += "  order by patrolname ";  //add by steven Mar 12,2007 for the purpose of sorting
            logger.info("sdPatrol sql: "+sql);
        }
        //省代维用户
        if( userinfo.getDeptype().equals( "2" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
            sql += " and PARENTID in ( SELECT   contractorid FROM contractorinfo CONNECT BY PRIOR contractorid = parentcontractorid START WITH contractorid ='"
                  + userinfo.getDeptID() + "')";
            sql += "  order by patrolname ";  //add by steven Mar 12,2007 for the purpose of sorting
              logger.info("sdwPatrol sql: "+sql);
        }

        logger.info("Patrol sql: "+sql);
        try{
            query = new QueryUtil();
            Iterator it = query.queryBeans( sql ).iterator();
            while( it.hasNext() ){
                dynaBean = ( BasicDynaBean )it.next();
                lableList.add( new LabelValueBean( ( String ) ( dynaBean.get( "patrolname" ) ),
                    ( String ) ( dynaBean.get( "patrolid" ) ) ) );
                //lableList.add(dynaBean);
            }
            resultVct.add( lableList );
            logger.info( resultVct );
            return resultVct;
        }
        catch( Exception ex ){
            logger.error( "加载巡检维护组（人）信息时出错：" + ex.getMessage() );
            return null;
        }
    }


}
