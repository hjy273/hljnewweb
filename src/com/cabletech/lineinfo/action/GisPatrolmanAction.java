package com.cabletech.lineinfo.action;

import java.util.*;
import javax.servlet.http.*;

import org.apache.commons.beanutils.*;
import org.apache.struts.action.*;
import com.cabletech.baseinfo.beans.*;
import com.cabletech.commons.sqlbuild.*;
import com.cabletech.commons.web.*;

public class GisPatrolmanAction extends com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction{
    public GisPatrolmanAction(){
    }


    /**
     * 取得巡检员信息
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ClientException
     * @throws Exception
     * @return ActionForward
     */
    public ActionForward getPatrolmanInfo( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        String sPID = request.getParameter( "sPID" );
        String sql = "select a.patrolID patrolid, a.patrolName patrolname, nvl(b.contractorname,'') contractor, nvl(a.JOBINFO,' ') jobinfo, nvl(c.password,'') password, nvl(c.terminalid,'') terminalid, nvl(c.SIMNUMBER,'') sim, nvl(decode(c.STATE,'00','创建线路','01','日常巡检','创建线路'),'') state from patrolmaninfo a, contractorinfo b, terminalinfo c where a.parentid = b.contractorid(+) and a.patrolid = c.ownerid(+) and c.SIMNUMBER='" +
                     sPID + "'";

        QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );

        List list = super.getDbService().queryBeans( sqlBuild.toSql() );

        Hashtable ht = new Hashtable();
        ht.put( "hasrecord", "0" );

        if( list.size() > 0 ){

            BasicDynaBean innerbean = ( BasicDynaBean )list.get( 0 );

            ht.put( "patrolid", ( String )innerbean.get( "patrolid" ) );
            ht.put( "patrolname", ( String )innerbean.get( "patrolname" ) );
            ht.put( "contractor", ( String )innerbean.get( "contractor" ) );
            ht.put( "jobinfo", ( String )innerbean.get( "jobinfo" ) );
            ht.put( "terminalid", ( String )innerbean.get( "terminalid" ) );
            ht.put( "password", ( String )innerbean.get( "password" ) );
            ht.put( "sim", ( String )innerbean.get( "sim" ) );
            ht.put( "state", ( String )innerbean.get( "state" ) );

            ht.put( "hasrecord", "1" );

        }

        request.setAttribute( "pmBean", ht );
        return mapping.findForward( "gisPatrolManInfo" );
    }


    /**
     * 转入短信编辑发送界面
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ClientException
     * @throws Exception
     * @return ActionForward
     */

    public ActionForward toSMS( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        PatrolManBean bean = ( PatrolManBean )form;

        String patrolid = request.getParameter( "patrolID" );
        String sql = "select * from terminalinfo where ownerid = '" + patrolid +
                     "'";

        Iterator it;
        BasicDynaBean bdb;
        Hashtable ht = new Hashtable();

        it = super.getDbService().queryBeans( sql ).iterator();
        while( it.hasNext() ){

            bdb = ( BasicDynaBean )it.next();
            //取得终端id，并截取后4位
            String terminalid = ( String )bdb.get( "terminalid" );

            int idL = terminalid.length();
            terminalid = terminalid.substring( idL - 4, idL );

            ht.put( "terminalid", terminalid );
            ht.put( "password", ( String )bdb.get( "password" ) );
            ht.put( "simnumber", ( String )bdb.get( "simnumber" ) );
        }

        ht.put( "patrolid", bean.getPatrolID() );
        ht.put( "patrolname", bean.getPatrolName() );
        ht.put( "patrolmobile", bean.getMobile() );

        request.setAttribute( "smsPro", ht );

        return mapping.findForward( "toSMS" );
    }


    /**
     * 载入可用的（已经被分配手持巡检设备）巡检员
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward loadPatrolAvailable( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        String sql = "select a.PATROLNAME PATROLNAME, b.SIMNUMBER SIMNUMBER from PATROLMANINFO a, TERMINALINFO b where a.PATROLID = b.OWNERID ";
        QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );

        String regionid = super.getLoginUserInfo( request ).getRegionID();
        sqlBuild.addConditionAnd( "a.REGIONID = {0}", regionid );

        sql = sqlBuild.toSql();
        Vector resultVct = super.getDbService().queryVector( sql, "" );

        request.getSession().setAttribute( "resultVct", resultVct );

        return mapping.findForward( "gisPatrolList" );
    }

}
