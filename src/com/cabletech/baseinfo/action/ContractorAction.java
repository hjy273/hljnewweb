package com.cabletech.baseinfo.action;

import java.util.*;
import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;
import com.cabletech.baseinfo.beans.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.baseinfo.services.ContractorBO;
import com.cabletech.commons.beans.*;
import com.cabletech.commons.hb.*;
import com.cabletech.commons.sqlbuild.*;
import com.cabletech.commons.web.*;
import com.cabletech.power.*;
import java.sql.ResultSet;

public class ContractorAction extends BaseInfoBaseDispatchAction{

    private static Logger logger =
        Logger.getLogger( ContractorAction.class );
    /**
     * 添加代维单位信息
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ClientException
     * @throws Exception
     * @return ActionForward
     */
    public ActionForward addContractor( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        /*if( !CheckPower.checkPower( request.getSession(), "70302" ) ){
            return mapping.findForward( "powererror" );
        }
*/
        ContractorBean bean = ( ContractorBean )form;
        Contractor data = new Contractor();
        BeanUtil.objectCopy( bean, data );

        data.setContractorID( super.getDbService().getSeq( "contractorinfo", 10 ) );

        //说明，如果该代维单位无上级单位，则从页面取得该值为-1;此时，将上级单位设为本身
        if( data.getParentcontractorid().equals( " " ) ){
            data.setParentcontractorid( " " );
        }

        super.getService().createContractor( data );

        //Log
        log( request, " 增加代维单位（单位名称为："+bean.getContractorName()+"） ", " 基础资料管理 " );

        return forwardInfoPage( mapping, request, "0011" );
    }


    public ActionForward loadContractor( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        /*if( !CheckPower.checkPower( request.getSession(), "70304" ) ){
            return mapping.findForward( "powererror" );
        }*/
        Contractor data = super.getService().loadContractor( request.
                          getParameter( "id" ) );
        ContractorBean bean = new ContractorBean();
        BeanUtil.objectCopy( data, bean );
        request.setAttribute( "contractorBean", bean );
        request.getSession().setAttribute( "contractorBean", bean );
        return mapping.findForward( "updateContractor" );
    }


    /**
     * 检索
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ClientException
     * @throws Exception
     * @return ActionForward
     */
    public ActionForward queryContractor( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
    	ContractorBean bean = (ContractorBean)form;
    	 request.getSession().setAttribute("S_BACK_URL",null); //
	        request.getSession().setAttribute("theQueryBean", bean);//
        /*if( !CheckPower.checkPower( request.getSession(), "70303" ) ){
            return mapping.findForward( "powererror" );
        }*/
        String sql = "select a.contractorID, a.contractorName, decode(c.contractorName,null,'无',c.contractorname) parentcontractorid, a.linkmanInfo, a.pactBeginDate, a.pactTerm, b.regionname regionid,b.regionid conregionid,decode(a.depttype,null,'无','2','代维单位','3','监理单位') depttype,(select 'edit' from contract ct where ct.year-1=to_char(sysdate,'yyyy') and ct.contractor_id=a.contractorid) flag from contractorinfo a, region b, (select contractorid, contractorname from contractorinfo) c";

        QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );

        sqlBuild.addConstant( "a.regionid = b.regionid(+)" );
        sqlBuild.addAnd();
        sqlBuild.addConstant( "a.parentcontractorid = c.contractorid(+)" );

        sqlBuild.addConditionAnd( "a.contractorid = {0}",
            ( ( ContractorBean )form ).getContractorID() );
        sqlBuild.addConditionAnd( "a.contractorname like {0}",
            "%" +
            ( ( ContractorBean )form ).getContractorName() +
            "%" );
        sqlBuild.addConditionAnd( "a.regionid = {0}",
            ( ( ContractorBean )form ).getRegionid() );

        sqlBuild.addConditionAnd( "a.parentcontractorid={0}",
            ( ( ContractorBean )form ).getParentcontractorid() );

        sqlBuild.addConditionAnd( "a.linkmaninfo like {0}",
            "%" + ( ( ContractorBean )form ).getLinkmanInfo() +
            "%" );
        String principalInfo = ( ( ContractorBean )form ).getPrincipalInfo();
        if(principalInfo != null){
	        sqlBuild.addConditionAnd( "a.principalinfo like {0}",
	            "%" + ( ( ContractorBean )form ).getPrincipalInfo() +
	            "%" );
        }
        sqlBuild.addConstant( " and a.state is null" );
        //区域限定
        sqlBuild.addTableRegion( "a.regionid",
            super.getLoginUserInfo( request ).getRegionid() );

        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( !userinfo.getDeptype().equals( "1" ) ){ //如果是代维单位只能看到本单位的信息
            if( userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
                sqlBuild.addConstant( " and a.contractorid in (SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid = parentcontractorid START WITH contractorid = '"
                    + userinfo.getDeptID() + "')" );
            }
            else{
                sqlBuild.addConditionAnd( "a.contractorid = {0}", userinfo.getDeptID() );
            }
        }
        sql = sqlBuild.toSql();
        sql += " order by depttype asc, contractorname asc";
        logger.info( "检索代维单位信息 :" + sql );
        List list = super.getDbService().queryBeans( sql );
        request.getSession().setAttribute( "queryresult", list );
        super.setPageReset(request);
        return mapping.findForward( "querycontractorresult" );
    }


    public ActionForward updateContractor( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        /*if( !CheckPower.checkPower( request.getSession(), "70304" ) ){
            return mapping.findForward( "powererror" );
        }*/
        Contractor data = new Contractor();
        ContractorBean bean=( ContractorBean )form;
        BeanUtil.objectCopy( bean, data );

        super.getService().updateContractor( data );

        //Log
        log( request, " 更新代维单位（单位名称为："+bean.getContractorName()+"） ", " 基础资料管理 " );
        String strQueryString = getTotalQueryString("method=queryContractor&contractorName=",(ContractorBean)request.getSession().getAttribute("theQueryBean"));
  		 Object[] args = getURLArgs("/WebApp/contractorAction.do",strQueryString,(String)request.getSession().getAttribute( "S_BACK_URL" ));
  		 return forwardInfoPage( mapping, request, "0012",null,args);

    }


    /**
     * 删除
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward deleteContractor( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        /*if( !CheckPower.checkPower( request.getSession(), "70305" ) ){
            return mapping.findForward( "powererror" );
        }*/
    	ContractorBO contractorBO=new ContractorBO();
        ResultSet rst = null;
        String id = request.getParameter( "id" );
        String sql = "update contractorinfo set state='1' where contractorid='" + id + "'";
        String sqlc = "select count(*) aa from userinfo u where  u.state is null and u.DEPTID='" + id  + "'";
        String sqlp = "select count(*) aa from patrolman_son_info p where p.state is null and  p.PARENTID='" + id + "' ";
        String name=contractorBO.loadContractor(id).getContractorName();
        try{
            QueryUtil qu = new QueryUtil();
            rst = qu.executeQuery(sqlc);
            if(rst != null && rst.next()){
               if(!rst.getString("aa").equals("0")){
                   rst.close();
                   return forwardInfoPage( mapping, request, "00013haveuser" );
               }
            }
            rst = qu.executeQuery(sqlp);
            if(rst != null && rst.next()){
               if(!rst.getString("aa").equals("0")){
                   rst.close();
                   return forwardInfoPage( mapping, request, "00013havepatrol" );
               }
            }
 
            UpdateUtil exec = new UpdateUtil();
            exec.executeUpdate( sql );
            log( request, " 删除代维单位（单位名称为："+name+"）", " 基础资料管理 " );
            String strQueryString = getTotalQueryString("method=queryContractor&contractorName=",(ContractorBean)request.getSession().getAttribute("theQueryBean"));
   		 Object[] args = getURLArgs("/WebApp/contractorAction.do",strQueryString,(String)request.getSession().getAttribute( "S_BACK_URL" ));
   		 return forwardInfoPage( mapping, request, "0013",null,args);
        }
        catch( Exception e ){
            if(rst != null){
                rst.close();
            }
            logger.warn( "删除代维单位异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
    
    public String getTotalQueryString(String queryString,ContractorBean bean){
    	if (bean.getContractorName()!= null){
    		queryString = queryString + bean.getContractorName();
    	}
    	if (bean.getContractorID()!= null){
    		queryString = queryString + "&contractorID=" + bean.getContractorID();
    	}
    	if (bean.getRegionid() != null){
    		queryString = queryString + "&regionid=" + bean.getRegionid();
    	}
    	if (bean.getLinkmanInfo()!= null){
    		queryString = queryString + "&linkmanInfo=" + bean.getLinkmanInfo();
    	}
    	return queryString;
    }

    public ActionForward exportContractorResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            logger.info( " 创建dao" );
            List list = ( List )request.getSession().getAttribute( "queryresult" );
            logger.info( "得到list" );
            super.getService().exportContractorResult( list, response );
            logger.info( "输出excel成功" );
            return null;
        }
        catch( Exception e ){
            logger.error( "导出信息报表出现异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
}
