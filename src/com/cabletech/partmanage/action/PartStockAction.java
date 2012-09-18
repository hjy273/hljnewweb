package com.cabletech.partmanage.action;

import org.apache.struts.action.ActionForward;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import com.cabletech.power.CheckPower;
import org.apache.struts.action.ActionForm;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.partmanage.dao.*;
import com.cabletech.partmanage.beans.Part_requisitionBean;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import org.apache.log4j.Logger;
import java.text.DateFormat;
import java.sql.ResultSet;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.web.ClientException;

public class PartStockAction extends BaseDispatchAction{
    private static Logger logger = Logger.getLogger( PartStockAction.class.
                                   getName() );
    //显示所有待入库的申请单
    public ActionForward doStockShow( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80401" ) ){
            return mapping.findForward( "powererror" );
        }

        //获得当前用户的单位类型
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许入库的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            PartStockDao dao = new PartStockDao();
            List lReqInfo = dao.getAllReqForStock( userinfo.getDeptID() );
            request.getSession().setAttribute( "reqinfo", lReqInfo );
            request.getSession().setAttribute( "type", "stock2" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "显示所有待入库的申请单出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //显示一个申请单的详细信息，开始入库
    public ActionForward doStockPartForOneReq( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80401" ) ){
            return mapping.findForward( "powererror" );
        }

        Part_requisitionBean bean = ( Part_requisitionBean )form;
        Part_requisitionBean reqinfo = new Part_requisitionBean(); //传回页面的申请单基本信息
        List reqpartinfo = null;
        PartRequisitionDao dao = new PartRequisitionDao();
        String reid = request.getParameter( "reid" ); //获得要修改的申请单id

        try{
            //获得当前用户的单位类型
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                                "LOGIN_USER" );
            if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允的
                return forwardErrorPage( mapping, request, "partstockerror" );
            }

            String deptname = dao.getUserDeptName( userinfo ); //获得入库单位名称
            request.setAttribute( "deptname", deptname );
            request.setAttribute( "deptid", userinfo.getDeptID() );
            request.setAttribute( "username", userinfo.getUserName() );
            request.setAttribute( "userid", userinfo.getUserID() );

            //获得服务器的当前时间
            Date nowDate = new Date();
            DateFormat df = DateFormat.getDateInstance( DateFormat.DATE_FIELD );
            String data = df.format( nowDate );
            request.setAttribute( "date", data );

            //获得申请单基本信息
            reqinfo = dao.getOneReq( reid );
            request.setAttribute( "reqinfo", reqinfo );

            //获得申请单所申请的材料信息
            PartStockDao stockdao = new PartStockDao();
            reqpartinfo = stockdao.getReqPartForStock( reid );
            request.setAttribute( "reqpartinfo", reqpartinfo );
            request.getSession().setAttribute( "type", "stock20" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "在显示一个申请单的详细信息错误:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //执行入库操作
    /**入库规则:对一个申请单入库时,如果入库数量小于审批数量,该申请单要返回审批部门重新核实,
     *        在核实之前,对该申请单可以继续入库,
     * 		  在核实之后,也就是修改了相应的审批数量之后,该申请单旧入库完毕了.
     * **/
    public ActionForward doStockPart( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80401" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            PartStockDao stockDao = new PartStockDao();
            Part_requisitionBean bean = ( Part_requisitionBean )form;

            OracleIDImpl ora = new OracleIDImpl();
            String stockid = ora.getSeq( "Part_stock", 10 );
            bean.setStockid( stockid );

            String[] id = request.getParameterValues( "id" );
            String[] stocknumber = request.getParameterValues( "stocknumber" );
            //填写入库单
            if( !stockDao.doAddStock( bean ) ){
                return forwardErrorPage( mapping, request, "error" );
            }
            //填写入库_材料对应表
            if( !stockDao.doAddStock_BaseForStock( id, stocknumber, bean.getReid(), bean.getStockid(),
                bean.getcontractorid() ) ){
                return forwardErrorPage( mapping, request, "error" );
            }
            log( request, "材料管理", "材料入库操作" );
            return forwardInfoPage( mapping, request, "80402" );
        }
        catch( Exception e ){
            logger.error( "在执行入库操作出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //显示所有入库单
    public ActionForward dokShowStockInfo( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        boolean flag=false;
        if( CheckPower.checkPower( request.getSession(), "80402" )
        		|| CheckPower.checkPower( request.getSession(), "80403" )
            ||CheckPower.checkPower( request.getSession(), "80409" )){
            flag=true;
        }
        if(!flag)
            return mapping.findForward( "powererror" );
        request.getSession().removeAttribute("bean");
        //获得当前用户的单位类型
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        //if( userinfo.getType().equals( "12" ) ){ //如果是市移动公司是不允许的
        if( userinfo.getDeptype().equals( "1" )
            &&!userinfo.getRegionID().substring(2,6).equals("0000") ){ //如果是市移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            PartStockDao stockDao = new PartStockDao();
            String contractorid=userinfo.getDeptID();
            List lStockInfo=null;
            if(request.getParameter("querytype")!=null
                &&request.getParameter("querytype").equals("1")){
                contractorid = request.getParameter( "contractorid" );
                request.getSession().setAttribute( "type", "showstock1" );
                lStockInfo = stockDao.getAllStock( request );
            }else{
                request.getSession().setAttribute( "type", "stock1" );
                lStockInfo = stockDao.getAllStock( contractorid );
            }

            request.getSession().setAttribute("querytype","1");
            request.getSession().setAttribute( "stockinfo", lStockInfo );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "显示显示所有入库单出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //查看入库单,显示一个入库单的详细信息,
    public ActionForward doShowOneStock( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        boolean flag=false;
        if( CheckPower.checkPower( request.getSession(), "80402" )
        		|| CheckPower.checkPower( request.getSession(), "80403" )
            ||CheckPower.checkPower( request.getSession(), "80409" )){
            flag=true;
        }
        if(!flag)
            return mapping.findForward( "powererror" );
        Part_requisitionBean stockinfo = new Part_requisitionBean(); //传回页面的入库单基本信息
        List lStockPart = null;
        PartStockDao stockDao = new PartStockDao();
        String stockid = request.getParameter( "stockid" ); //获得要显示的入库单id

        try{
            //获得当前用户的单位类型
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                                "LOGIN_USER" );
            //if( userinfo.getType().equals( "12" ) ){ //如果是市移动公司是不允许的
            if( userinfo.getDeptype().equals( "1" )
                &&!userinfo.getRegionID().substring(2,6).equals("0000") ){ //如果是市移动公司是不允许的
                return forwardErrorPage( mapping, request, "partstockerror" );
            }
            stockinfo = stockDao.getOneStock( stockid ); //获得一个入库单的基本信息
            lStockPart = stockDao.getStockPartInfo( stockid );
            request.setAttribute( "stockinfo", stockinfo );
            request.setAttribute( "stockpartinfo", lStockPart );

            request.getSession().setAttribute( "type", "stock10" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "显示一个入库单的详细信息中出现错误:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //入库综合查询显示
    public ActionForward queryShowForSrock( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80403" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            PartStockDao stockdao = new PartStockDao();
            //获得入库人名单
            List lUser = stockdao.getUsernameForStockQUery( userinfo.getDeptID() );
            request.setAttribute( "stockuser", lUser );
            //获得入库对应申请单编号,申请原因列表
            List lReason_Reid = stockdao.getAllReidForStockQuery( userinfo.getDeptID() );
            request.setAttribute( "reqreid", lReason_Reid );

            request.getSession().setAttribute( "type", "stock3" );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "综合查询显示异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //入库单综合查询执行
    public ActionForward queryExecForStock( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80403" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        String contractorid = ( String )request.getSession().getAttribute( "LOGIN_USER_DEPT_ID" );

        try{
            Part_requisitionBean bean = ( Part_requisitionBean )form;
            bean.setContractorid( contractorid );
            PartStockDao stockDao = new PartStockDao();
            List lstockInfo = stockDao.getStockInfoForQuery( bean );

            request.getSession().setAttribute( "stockinfo", lstockInfo );
            request.getSession().setAttribute( "type", "stock1" );
            request.getSession().setAttribute( "bean", bean );
            super.setPageReset(request);
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "入库单综合查询执行异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //////////////////////新材料入库统计//////////////////
    //入库统计查询显示
    public ActionForward queryShowForStat( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80404" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );

        try{
            String contractorid = ( String )request.getSession().getAttribute(
                                  "LOGIN_USER_DEPT_ID" );
            String condition="";
            if(userinfo.getType().equals("11")){}
            if(userinfo.getType().equals("12")){
                condition+=" and regionid='"+userinfo.getRegionID()+"'";
            }
            if(userinfo.getType().equals("21")){
                condition+=" and con.contractorid in ("
                    +"         select contractorid from contractorinfo "
                    +"         where parentcontractorid='"+userinfo.getDeptID()+"' "
                    +"         or contractorid='"+userinfo.getDeptID()+"')";
            }
            if(userinfo.getType().equals("22")){
                condition+=" and con.contractorid='"+userinfo.getDeptID()+"'";
            }
            PartUseDao useDao = new PartUseDao();
            PartStockDao dao = new PartStockDao();
            //获得使用单位列表
            List lDept = dao.getDeptArrs( condition );
            //获得材料名称列表
            PartBaseInfoDao baseDao = new PartBaseInfoDao();
            List lPartName = baseDao.getAllNames( condition );
            //获得材料型号列表
            List lPartType = baseDao.getAllTypes( condition );
            //获得所有生产厂家列表
            List lFactory = baseDao.getAllFactorys( condition );
            //获得材料用途列表
            List lReason = null;
            if( userinfo.getDeptype().equals( "2" ) ){ //是代维单位,仅仅获得本公司的用途列表
                lReason = useDao.getReasonArr( contractorid );
            }
            else{ //是移动公司,获得所有用途列表
                lReason = useDao.getAllReasonArr( userinfo.getRegionID() );
            }
            request.setAttribute( "deptinfo", lDept );
            request.setAttribute( "nameinfo", lPartName );
            request.setAttribute( "typeinfo", lPartType );
            request.setAttribute( "factoryinfo", lFactory );
            request.setAttribute( "usereason", lReason );

            request.setAttribute( "usedept", userinfo.getDeptype() );
            request.setAttribute( "usetype", userinfo.getType() );

            request.getSession().setAttribute( "type", "stock4" );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "入库统计查询显示异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //统计查询执行
    public ActionForward dostat( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80404" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );

        try{
            String contractorid = ( String )request.getSession().getAttribute(
                                  "LOGIN_USER_DEPT_ID" );
            Part_requisitionBean bean = ( Part_requisitionBean )form;
            bean.setRegionid( userinfo.getRegionID() );
            PartStockDao dao = new PartStockDao();
            List lUseInfo = null;
            String condition="";
            if(userinfo.getType().equals("11")){}
            if(userinfo.getType().equals("12")){
                condition+=" and con.regionid='"+userinfo.getRegionID()+"'";
            }
            if(userinfo.getType().equals("21")){
                condition+=" and con.contractorid in ("
                    +"         select contractorid from contractorinfo "
                    +"         where parentcontractorid='"+userinfo.getDeptID()+"' "
                    +"         or contractorid='"+userinfo.getDeptID()+"')";
            }
            if(userinfo.getType().equals("22")){
                condition+=" and con.contractorid='"+userinfo.getDeptID()+"'";
            }
            //if( userinfo.getDeptype().equals( "1" ) ){ //是移动公司的查询
            //    lUseInfo = dao.getAllPartUse( bean );
            //}
            //else{ //是代维单位的查询
            //    lUseInfo = dao.getAllPartUse( bean, contractorid );
            //}
            lUseInfo=dao.getAllPartUse(condition,bean);
            request.getSession().setAttribute( "useinfo", lUseInfo );
            request.getSession().setAttribute( "type", "use70" );
            request.getSession().setAttribute( "bean", bean );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "入库查询执行异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


//////////////////////////////////// For OldStock Action///////////////////////////////
    //添加利旧材料显示
    public ActionForward addOldShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80501" ) ){
            return mapping.findForward( "powererror" );
        }

        PartRequisitionDao dao = new PartRequisitionDao();
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许添加材料申请的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }
        String deptname = dao.getUserDeptName( userinfo );
        request.setAttribute( "deptname", deptname );
        request.setAttribute( "deptid", userinfo.getDeptID() );
        request.setAttribute( "username", userinfo.getUserName() );
        request.setAttribute( "userid", userinfo.getUserID() );

        //获得服务器的当前时间
        Date nowDate = new Date();
        DateFormat df = DateFormat.getDateInstance( DateFormat.DATE_FIELD );
        String data = df.format( nowDate );
        request.setAttribute( "date", data );

        //获得材料信息,以供用户选择
        List lBaseInfo = dao.getAllInfo( userinfo.getRegionID() );
        request.setAttribute( "baseinfo", lBaseInfo );

        request.getSession().setAttribute( "type", "old2" );
        return mapping.findForward( "success" );
    }


    //执行添加利旧材料入库单
    public ActionForward addOLdStock( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80501" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            PartStockDao stockDao = new PartStockDao();
            Part_requisitionBean bean = ( Part_requisitionBean )form;
            OracleIDImpl ora = new OracleIDImpl();
            String oldid = ora.getSeq( "Part_oldstock", 10 );
            bean.setOldid( oldid );
            String[] id = request.getParameterValues( "id" );
            String[] oldnumber = request.getParameterValues( "oldnumber" );
            //判断是否没有利旧材料的数量，如果一个材料的数量都没有，则出错
            boolean flag = true;
            for( int i = 0; i < oldnumber.length; i++ ){
                if( oldnumber[i].equals( "0" ) || ("".equals(id[i].trim()))) {
                    flag = false;
                    break;
                }
            }
            if( !flag ){
                return forwardErrorPage( mapping, request, "80502e" );
            }

            //写入基本表
            if( !stockDao.addOldStockInfo( bean, id, oldnumber ) ){
                return forwardInfoPage( mapping, request, "error" );
            }
            log( request, "材料管理", "添加利旧材料入库单" );
            return forwardInfoPage( mapping, request, "80502" );
        }
        catch( Exception e ){
            logger.error( "在添加申请单中出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //显示所有利旧材料入库单
    public ActionForward showAllOldStock( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        boolean flag=false;
        if( CheckPower.checkPower( request.getSession(), "80502" )
        		|| CheckPower.checkPower( request.getSession(), "80503" )
            ||CheckPower.checkPower( request.getSession(), "80509" )){
            flag=true;
        }
        if(!flag)
            return mapping.findForward( "powererror" );

        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        //if( userinfo.getType().equals( "12" ) ){ //如果是市移动公司是不允许的
        if( userinfo.getDeptype().equals( "1" )
            &&!userinfo.getRegionID().substring(2,6).equals("0000") ){ //如果是市移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            PartStockDao stockDao = new PartStockDao();
            String contractorid = userinfo.getDeptID();
            List lOldInfo=null;
            if(request.getParameter("querytype")!=null
                &&request.getParameter("querytype").equals("1")){
                contractorid = request.getParameter( "contractorid" );
                request.getSession().setAttribute( "type", "showold1" );
                lOldInfo = stockDao.getAllOldStock( request );
            }else{
                request.getSession().setAttribute( "type", "old1" );
                lOldInfo = stockDao.getAllOldStock( contractorid );
            }

            request.getSession().setAttribute( "oldinfo", lOldInfo );
            request.getSession().setAttribute("querytype","1");
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "显示材料基本信息中显示所有信息出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //显示一个利旧材料入库单的详细信息
    public ActionForward showOneOfStock( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        boolean flag=false;
        if( CheckPower.checkPower( request.getSession(), "80502" )
        		|| CheckPower.checkPower( request.getSession(), "80503" )
            ||CheckPower.checkPower( request.getSession(), "80509" )){
            flag=true;
        }
        if(!flag)
            return mapping.findForward( "powererror" );

        Part_requisitionBean bean = ( Part_requisitionBean )form;
        Part_requisitionBean oldinfo = new Part_requisitionBean(); //传回页面的入库单基本信息
        List oldpartinfo = null;
        PartStockDao stockDao = new PartStockDao();
        String oldid = request.getParameter( "oldid" ); //获得要修改的申请单id

        try{
            //获得当前用户的单位名称
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                                "LOGIN_USER" );
            //if( userinfo.getType().equals( "12" ) ){ //如果是市移动公司是不允许的
            if( userinfo.getDeptype().equals( "1" )
                &&!userinfo.getRegionID().substring(2,6).equals("0000") ){ //如果是市移动公司是不允许的
                return forwardErrorPage( mapping, request, "partstockerror" );
            }
            //获得入库单基本信息
            oldinfo = stockDao.getOneOldStock( oldid );
            request.setAttribute( "oldinfo", oldinfo );

            //获得入库单所入库的材料信息
            oldpartinfo = stockDao.getPartOfOneOldStock( oldid );
            request.setAttribute( "oldpartinfo", oldpartinfo );
            request.getSession().setAttribute( "type", "old10" );

            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "在显示详细中出现错误:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //利旧材料入库综合查询显示
    public ActionForward queryShowForOldSrock( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80503" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            PartStockDao stockdao = new PartStockDao();
            //获得入库人名单
            List lUser = stockdao.getOldStockUsername( userinfo.getDeptID() );
            request.setAttribute( "olduser", lUser );
            //材料来源列表
            List lOldReason = stockdao.getOldStockOLdreason( userinfo.getDeptID() );
            request.setAttribute( "oldreason", lOldReason );

            request.getSession().setAttribute( "type", "old3" );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "综合查询显示异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //利旧材料入库单综合查询执行
    public ActionForward queryExecForOldStock( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80503" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        String contractorid = ( String )request.getSession().getAttribute( "LOGIN_USER_DEPT_ID" );
        try{
            Part_requisitionBean bean = ( Part_requisitionBean )form;
            bean.setContractorid( contractorid );
            PartStockDao stockDao = new PartStockDao();
            List lOldInfo = stockDao.getOldStockInfoForQuery( bean );

            request.getSession().setAttribute( "oldinfo", lOldInfo );
            request.getSession().setAttribute( "type", "old1" );
            request.getSession().setAttribute( "bean", bean );
            super.setPageReset(request);
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "利旧入库单综合查询执行异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //////////////////////利旧材料入库统计//////////////////
//利旧入库统计查询显示
    public ActionForward queryShowForOldStat( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80504" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );

        try{
            String contractorid = ( String )request.getSession().getAttribute(
                                  "LOGIN_USER_DEPT_ID" );
            String condition="";
            if(userinfo.getType().equals("11")){}
            if(userinfo.getType().equals("12")){
                condition+=" and regionid='"+userinfo.getRegionID()+"'";
            }
            if(userinfo.getType().equals("21")){
                condition+=" and con.contractorid in ("
                    +"         select contractorid from contractorinfo "
                    +"         where parentcontractorid='"+userinfo.getDeptID()+"' "
                    +"         or contractorid='"+userinfo.getDeptID()+"')";
            }
            if(userinfo.getType().equals("22")){
                condition+=" and con.contractorid='"+userinfo.getDeptID()+"'";
            }
            PartUseDao useDao = new PartUseDao();
            //获得使用单位列表
            List lDept = useDao.getDeptArrs( condition );
            //获得材料名称列表
            PartBaseInfoDao baseDao = new PartBaseInfoDao();
            List lPartName = baseDao.getAllNames( condition );
            //获得材料型号列表
            List lPartType = baseDao.getAllTypes( condition );
            //获得所有生产厂家列表
            List lFactory = baseDao.getAllFactorys( condition );
            //获得材料用途列表
            List lReason = null;
            if( userinfo.getDeptype().equals( "2" ) ){ //是代维单位,仅仅获得本公司的用途列表
                lReason = useDao.getReasonArr( contractorid );
            }
            else{ //是移动公司,获得所有用途列表
                lReason = useDao.getAllReasonArr( userinfo.getRegionID() );
            }
            request.setAttribute( "deptinfo", lDept );
            request.setAttribute( "nameinfo", lPartName );
            request.setAttribute( "typeinfo", lPartType );
            request.setAttribute( "factoryinfo", lFactory );
            request.setAttribute( "usereason", lReason );

            request.setAttribute( "usedept", userinfo.getDeptype() );
            request.setAttribute( "usetype", userinfo.getType() );

            request.getSession().setAttribute( "type", "old4" );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "利旧入库统计查询显示异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


//统计查询执行
    public ActionForward dostatOld( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80504" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );

        try{
            String contractorid = ( String )request.getSession().getAttribute(
                                  "LOGIN_USER_DEPT_ID" );
            Part_requisitionBean bean = ( Part_requisitionBean )form;
            bean.setRegionid( userinfo.getRegionID() );
            PartStockDao dao = new PartStockDao();
            List lUseInfo = null;
            String condition="";
            if(userinfo.getType().equals("11")){}
            if(userinfo.getType().equals("12")){
                condition+=" and con.regionid='"+userinfo.getRegionID()+"'";
            }
            if(userinfo.getType().equals("21")){
                condition+=" and con.contractorid in ("
                    +"         select contractorid from contractorinfo "
                    +"         where parentcontractorid='"+userinfo.getDeptID()+"' "
                    +"         or contractorid='"+userinfo.getDeptID()+"')";
            }
            if(userinfo.getType().equals("22")){
                condition+=" and con.contractorid='"+userinfo.getDeptID()+"'";
            }
            //if( userinfo.getDeptype().equals( "1" ) ){ //是移动公司的查询
            //    lUseInfo = dao.getAllPartUseOld( bean );
            //}
            //else{ //是代维单位的查询
            //    lUseInfo = dao.getAllPartUseOld( bean, contractorid );
            //}
            lUseInfo=dao.getAllPartUseOld(condition,bean);
            request.getSession().setAttribute( "useinfo", lUseInfo );
            request.getSession().setAttribute( "type", "old40" );
            request.getSession().setAttribute( "bean", bean );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "利旧入库查询执行异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    public ActionForward exportStockResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            Part_requisitionBean bean = null;
            if( request.getSession().getAttribute( "bean" ) != null ){
                bean = ( Part_requisitionBean )request.getSession().getAttribute( "bean" );
                logger.info( "获得查询条件bean。。。" );

                logger.info( "id：" + bean.getStockuserid() );
                logger.info( "对应申请单编号：" + bean.getReid() );
                logger.info( "开始时间：" + bean.getBegintime() );
                logger.info( "结束时间：" + bean.getEndtime() );
                if( !bean.getStockuserid().equals( "" ) ){
                    String sql = "select u.USERNAME from userinfo u  where u.USERID = '" + bean.getStockuserid() + "'";
                    QueryUtil query = new QueryUtil();
                    ResultSet rs = null;
                    rs = query.executeQuery( sql );
                    while( rs.next() ){
                        bean.setUsername( rs.getString( 1 ) );
                    }
                    logger.info( "入库人姓名：" + bean.getUsername() );
                }
                request.getSession().removeAttribute( "bean" );
            }
            PartExportDao dao = new PartExportDao();
            logger.info( " 创建dao" );
            List list = ( List )request.getSession().getAttribute( "stockinfo" );
            logger.info( "得到list" );
            dao.exportStockResult( list, bean, response );
            logger.info( "输出excel成功" );

            return null;
        }
        catch( Exception e ){
            logger.error( "导出信息报表出现异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    public ActionForward exportOldStockResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            Part_requisitionBean bean = null;
            if( request.getSession().getAttribute( "bean" ) != null ){
                bean = ( Part_requisitionBean )request.getSession().getAttribute( "bean" );
                logger.info( "获得查询条件bean。。。" );

                logger.info( "id：" + bean.getOlduserid() );
                logger.info( "材料来源：" + bean.getReid() );
                logger.info( "开始时间：" + bean.getBegintime() );
                logger.info( "结束时间：" + bean.getEndtime() );
                if( !bean.getOlduserid().equals( "" ) ){
                    String sql = "select u.USERNAME from userinfo u  where u.USERID = '" + bean.getOlduserid() + "'";
                    QueryUtil query = new QueryUtil();
                    ResultSet rs = null;
                    rs = query.executeQuery( sql );
                    while( rs.next() ){
                        bean.setUsername( rs.getString( 1 ) );
                    }
                    logger.info( "入库人姓名：" + bean.getUsername() );
                }
                request.getSession().removeAttribute( "bean" );
            }
            PartExportDao dao = new PartExportDao();
            logger.info( " 创建dao" );
            List list = ( List )request.getSession().getAttribute( "oldinfo" );
            logger.info( "得到list" );
            dao.exportOldStockResult( list, bean, response );
            logger.info( "输出excel成功" );

            return null;
        }
        catch( Exception e ){
            logger.error( "导出信息报表出现异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //导出维护材料入库单详细信息一览表
    public ActionForward exportStorkList( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
//        if( !CheckPower.checkPower( request.getSession(), "80403" ) ){
//            return mapping.findForward( "powererror" );
//        }
//        //获得当前用户的单位名称
//        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
//                            "LOGIN_USER" );
//        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
//            return forwardErrorPage( mapping, request, "partstockerror" );
//        }

        String contractorid = ( String )request.getSession().getAttribute( "LOGIN_USER_DEPT_ID" );

        try{

            String stockuserid = request.getParameter( "stockuserid" ); ;
            String reid = request.getParameter( "reid" );
            String begintime = request.getParameter( "begintime" );
            String endtime = request.getParameter( "endtime" );

            Part_requisitionBean bean = ( Part_requisitionBean )form;
            bean.setContractorid( contractorid );
            bean.setStockuserid( stockuserid );
            bean.setReid( reid );
            bean.setBegintime( begintime );
            bean.setEndtime( endtime );

            if( stockuserid != null ){
                String sql = "select u.USERNAME from userinfo u  where u.USERID = '" + bean.getStockuserid() + "'";
                QueryUtil query = new QueryUtil();
                ResultSet rs = null;
                rs = query.executeQuery( sql );
                while( rs.next() ){
                    bean.setUsername( rs.getString( 1 ) );
                }
                logger.info( "入库人姓名：" + bean.getUsername() );
            }
            PartStockDao stockDao = new PartStockDao();
            List list = stockDao.getStockList( bean );

            PartExportDao dao = new PartExportDao();
            dao.exportStockList( list, bean, response );

            return null;

        }
        catch( Exception e ){
            logger.error( "导出信息报表异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //导出利旧材料入库单详细信息一览表
    public ActionForward exportOldUseList( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
//        if( !CheckPower.checkPower( request.getSession(), "80403" ) ){
//            return mapping.findForward( "powererror" );
//        }
//        //获得当前用户的单位名称
//        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
//                            "LOGIN_USER" );
//        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
//            return forwardErrorPage( mapping, request, "partstockerror" );
//        }

        String contractorid = ( String )request.getSession().getAttribute( "LOGIN_USER_DEPT_ID" );

        try{

            String olduserid = request.getParameter( "olduserid" ); ;
            String reid = request.getParameter( "reid" );
            String begintime = request.getParameter( "begintime" );
            String endtime = request.getParameter( "endtime" );

            Part_requisitionBean bean = ( Part_requisitionBean )form;
            bean.setContractorid( contractorid );
            bean.setOlduserid( olduserid );
            bean.setReid( reid );
            bean.setBegintime( begintime );
            bean.setEndtime( endtime );
            if( olduserid != null ){
                String sql = "select u.USERNAME from userinfo u  where u.USERID = '" + bean.getOlduserid() + "'";
                QueryUtil query = new QueryUtil();
                ResultSet rs = null;
                rs = query.executeQuery( sql );
                while( rs.next() ){
                    bean.setUsername( rs.getString( 1 ) );
                }
                logger.info( "入库人姓名：" + bean.getUsername() );
            }
            PartStockDao stockDao = new PartStockDao();
            List list = stockDao.getOldUseList( bean );

            PartExportDao dao = new PartExportDao();
            dao.exportOldUseList( list, bean, response );

            return null;

        }
        catch( Exception e ){
            logger.error( "导出信息报表异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }

    /**
     * 查询所有新材料入库的表单
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward queryAllStock(ActionMapping mapping, ActionForm form,
        HttpServletRequest request,HttpServletResponse response)
        throws ClientException, Exception{
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );

        List regionList=null;
        List deptList=null;
        List conList=null;
        form = null;
        String region="select r.regionid,r.regionname "
                     +"from region r "
                     +"where (r.state is null or r.state<>'1') and substr(regionid,3,4)<>'1111'";
        String dept="select d.deptid,d.deptname,d.regionid "
                    +"from deptinfo d "
                    +"where (d.state is null or d.state<>'1')";
        String con="select c.contractorid ,c.contractorname ,c.regionid "
                   +"from contractorinfo c "
                   +"where (c.state is null or c.state<>'1')";
        if(userinfo.getType().equals("21")){
            con+=" and contractorid in ("
            +"   select contractorid from contractorinfo "
            +"   where parentcontractorid='"+userinfo.getDeptID()+"' or contractorid='"+userinfo.getDeptID()+"'"
            +" )";
    }
        if(userinfo.getType().equals("12")){
            con+=" and regionid='"+userinfo.getRegionID()+"'";
            dept+=" and regionid='"+userinfo.getRegionID()+"'";
        }
        if(userinfo.getType().equals("22")){
            con+=" and contractorid='"+userinfo.getDeptID()+"'";
        }
        dept+=" order by regionid";
        con+=" order by regionid";
        regionList = super.getDbService().queryBeans( region );
        deptList = super.getDbService().queryBeans( dept );
        conList = super.getDbService().queryBeans( con );
        request.setAttribute("regionlist",regionList);
        request.setAttribute("deptlist",deptList);
        request.setAttribute("conlist",conList);

        PartBaseInfoDao dao = new PartBaseInfoDao();
        List lName = dao.getAllName();
        List lType = dao.getAllType();
        request.setAttribute( "nameList", lName );
        request.setAttribute( "typeList", lType );

        return mapping.findForward("queryallstock");
    }

    /**
     * 查询所有利旧材料入库的表单
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward queryAllOldStock(ActionMapping mapping, ActionForm form,
        HttpServletRequest request,HttpServletResponse response)
        throws ClientException, Exception{
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );

        List regionList=null;
        List deptList=null;
        List conList=null;
        String region="select r.regionid,r.regionname "
                     +"from region r "
                     +"where (r.state is null or r.state<>'1') and substr(regionid,3,4)<>'1111'";
        String dept="select d.deptid,d.deptname,d.regionid "
                    +"from deptinfo d "
                    +"where (d.state is null or d.state<>'1')";
        String con="select c.contractorid ,c.contractorname ,c.regionid "
                   +"from contractorinfo c "
                   +"where (c.state is null or c.state<>'1')";
        if(userinfo.getType().equals("21")){
            con+=" and contractorid in ("
                +"   select contractorid from contractorinfo "
                +"   where parentcontractorid='"+userinfo.getDeptID()+"' or contractorid='"+userinfo.getDeptID()+"'"
                +" )";
        }
        if(userinfo.getType().equals("12")){
            con+=" and regionid='"+userinfo.getRegionID()+"'";
            dept+=" and regionid='"+userinfo.getRegionID()+"'";
        }
        if(userinfo.getType().equals("22")){
            con+=" and contractorid='"+userinfo.getDeptID()+"'";
        }
        dept+=" order by regionid";
        con+=" order by regionid";
        regionList = super.getDbService().queryBeans( region );
        deptList = super.getDbService().queryBeans( dept );
        conList = super.getDbService().queryBeans( con );
        request.setAttribute("regionlist",regionList);
        request.setAttribute("deptlist",deptList);
        request.setAttribute("conlist",conList);

        PartBaseInfoDao dao = new PartBaseInfoDao();
        List lName = dao.getAllName();
        List lType = dao.getAllType();
        request.setAttribute( "nameList", lName );
        request.setAttribute( "typeList", lType );

        return mapping.findForward("queryalloldstock");
    }

    public ActionForward exportStock( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            Part_requisitionBean bean = null;
            if( request.getSession().getAttribute( "bean" ) != null ){
                bean = ( Part_requisitionBean )request.getSession().getAttribute( "bean" );
                logger.info( "获得查询条件bean。。。" );
                logger.info( "材料名称：" + bean.getName());
                logger.info( "材料型号：" + bean.getType() );
                logger.info( "开始时间：" + bean.getBegintime() );
                logger.info( "结束时间：" + bean.getEndtime() );
                if( !bean.getcontractorid().equals( "" ) ){
                    String sql =
                        "select c.CONTRACTORNAME from contractorinfo c where c.CONTRACTORID = '" + bean.getcontractorid()
                        + "'";
                    QueryUtil query = new QueryUtil();
                    ResultSet rs = null;
                    rs = query.executeQuery( sql );
                    while( rs.next() ){
                        bean.setContractorname( rs.getString( 1 ) );
                    }
                    logger.info( "入库单位：" + bean.getContractorname() );
                }
                request.getSession().removeAttribute("bean");
            }
            PartExportDao dao = new PartExportDao();
            logger.info( " 创建dao" );
            List list = ( List )request.getSession().getAttribute( "useinfo" );
            logger.info( "得到list" );
            dao.ExportStock( list, bean, response );
            logger.info( "输出excel成功" );

            return null;
        }
        catch( Exception e ){
            logger.error( "导出材料使用一览表出现异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }

    public ActionForward exportOldStock( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            Part_requisitionBean bean = null;
            if( request.getSession().getAttribute( "bean" ) != null ){
                bean = ( Part_requisitionBean )request.getSession().getAttribute( "bean" );
                logger.info( "获得查询条件bean。。。" );
                logger.info( "材料名称：" + bean.getName());
                logger.info( "材料型号：" + bean.getType() );
                logger.info( "开始时间：" + bean.getBegintime() );
                logger.info( "结束时间：" + bean.getEndtime() );
                if( !bean.getcontractorid().equals( "" ) ){
                    String sql =
                        "select c.CONTRACTORNAME from contractorinfo c where c.CONTRACTORID = '" + bean.getcontractorid()
                        + "'";
                    QueryUtil query = new QueryUtil();
                    ResultSet rs = null;
                    rs = query.executeQuery( sql );
                    while( rs.next() ){
                        bean.setContractorname( rs.getString( 1 ) );
                    }
                    logger.info( "使用单位：" + bean.getContractorname() );
                }
                request.getSession().removeAttribute("bean");
            }
            PartExportDao dao = new PartExportDao();
            logger.info( " 创建dao" );
            List list = ( List )request.getSession().getAttribute( "useinfo" );
            logger.info( "得到list" );
            dao.ExportOldStock( list, bean, response );
            logger.info( "输出excel成功" );

            return null;
        }
        catch( Exception e ){
            logger.error( "导出材料使用一览表出现异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
}
