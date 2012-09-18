package com.cabletech.partmanage.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.web.ClientException;
import com.cabletech.linecut.services.LineCutReService;
import com.cabletech.partmanage.beans.Part_requisitionBean;
import com.cabletech.partmanage.dao.PartBaseInfoDao;
import com.cabletech.partmanage.dao.PartExportDao;
import com.cabletech.partmanage.dao.PartUseDao;
import com.cabletech.power.CheckPower;

public class PartUseAction extends BaseDispatchAction{
    private static Logger logger = Logger.getLogger( PartUseAction.class.
                                   getName() );

    //添加显示
    public ActionForward addUseShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80301" ) ){
            return mapping.findForward( "powererror" );
        }

        PartUseDao dao = new PartUseDao();
        //获得当前用户的单位名称LOGIN_USER_DEPT_NAME
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        String deptname = ( String )request.getSession().getAttribute( "LOGIN_USER_DEPT_NAME" ); ;
        request.setAttribute( "deptname", deptname );
        request.setAttribute( "deptid", userinfo.getDeptID() );
        request.setAttribute( "username", userinfo.getUserName() );
        request.setAttribute( "userid", userinfo.getUserID() );

        //获得服务器的当前时间
        Date nowDate = new Date();
        DateFormat df = DateFormat.getDateInstance( DateFormat.DATE_FIELD );
        String data = df.format( nowDate );
        request.setAttribute( "date", data );

        //获得该单位库中已经存放的材料信息,以供用户选择
        List lBaseInfo = dao.getSrockedPartInfo( userinfo.getDeptID() );
        request.setAttribute( "baseinfo", lBaseInfo );

        request.getSession().setAttribute( "type", "use2" );
        return mapping.findForward( "success" );
    }


    //执行出库单
    public ActionForward addUse( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80301" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            PartUseDao dao = new PartUseDao();
            Part_requisitionBean bean = ( Part_requisitionBean )form;
            OracleIDImpl ora = new OracleIDImpl();
            String useid = ora.getSeq( "part_use", 10 );
            bean.setUseid( useid );
            String[] id = request.getParameterValues( "id" );
            String[] usenewnumber = request.getParameterValues( "usenewnumber" );
            String[] useoldnumber = request.getParameterValues( "useoldnumber" );
            
            // add by guixy 取得材料用途信息
            String[] linecutStr = request.getParameterValues("useobjectstr1");
            String[] changeStr = request.getParameterValues("useobjectstr2");
            String objid = ora.getSeq("part_useobject_baseinfo",10);
            
            if( !dao.addUseInfo( bean, id, useoldnumber, usenewnumber, linecutStr,changeStr,objid)) {
                return forwardInfoPage( mapping, request, "error" );
            }
            log( request, "材料管理", "添加出库单" );
            return forwardInfoPage( mapping, request, "80302" );
        }
        catch( Exception e ){
            logger.error( "在执行出库单中出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //显示所有出库单
    public ActionForward showAllUse( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        boolean flag=false;
        if( CheckPower.checkPower( request.getSession(), "80302" )
        		|| CheckPower.checkPower( request.getSession(), "80303" )
            ||CheckPower.checkPower( request.getSession(), "80309" )){
            flag=true;
        }
        if(!flag)
            return mapping.findForward( "powererror" );

        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        //if( userinfo.getType().equals( "12" ) ){ //如果是市移动公司是不允许的
        /*
        if( userinfo.getDeptype().equals( "1" )
            &&!userinfo.getRegionID().substring(2,6).equals("0000") ){ //如果是市移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }
        */

        try{
            PartUseDao dao = new PartUseDao();
            if(request.getParameter("querytype")!=null
                &&request.getParameter("querytype").equals("1")){
                List lReqInfo = dao.getAllUsePart( request );
                request.getSession().setAttribute( "useinfo", lReqInfo );
                request.getSession().setAttribute( "type", "showuse1" );

           }else{
                List lReqInfo = dao.getAllUse( request );
                request.getSession().setAttribute( "useinfo", lReqInfo );
                request.getSession().setAttribute( "type", "use1" );
            }
            request.getSession().setAttribute("querytype","1");
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "显示所有出库单信息出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //显示一个入库单的详细信息
    public ActionForward showOneUse( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        boolean flag=false;
        if( CheckPower.checkPower( request.getSession(), "80302" )
        		|| CheckPower.checkPower( request.getSession(), "80303" )
            ||CheckPower.checkPower( request.getSession(), "80309" )){
            flag=true;
        }
        if(!flag)
            return mapping.findForward( "powererror" );

        Part_requisitionBean bean = ( Part_requisitionBean )form;
        Part_requisitionBean useinfo = new Part_requisitionBean(); //传回页面的出库单基本信息
        List usepartinfo = null;
        PartUseDao dao = new PartUseDao();
        String useid = request.getParameter( "useid" ); //获得要修改的出库单id

        try{
            //获得当前用户的单位名称
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                                "LOGIN_USER" );
            //if( userinfo.getType().equals( "12" ) ){ //如果是市移动公司是不允许的
            /*
            if( userinfo.getDeptype().equals( "1" )
                &&!userinfo.getRegionID().substring(2,6).equals("0000") ){ //如果是市移动公司是不允许的
                return forwardErrorPage( mapping, request, "partstockerror" );
            }
            */
            //获得入库单基本信息
            useinfo = dao.getOneUse( useid ); ;
            request.setAttribute( "useinfo", useinfo );

            //获得入库单所入库的材料信息
            usepartinfo = dao.getPartOfOneUse( useid );
            request.setAttribute( "usepartinfo", usepartinfo );
            request.getSession().setAttribute( "type", "use10" );

            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "在显示详细中出现错误:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //综合查询显示
    public ActionForward queryShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80303" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        /*
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }
        */
        PartUseDao useDao = new PartUseDao();
        try{
        	List lReason = null;
        	if ("1".equals(userinfo.getDeptype())) {
        		// 移动用户 
        		List lDept = useDao.getConDeptInfo(userinfo);
        		request.setAttribute( "condept", lDept );
			} else {
				// 代维用户
				String contractorid = ( String )request.getSession().getAttribute(
                "LOGIN_USER_DEPT_ID" );
				
				//获得入库人名单
				List lUser = useDao.getUserArr( contractorid );
				request.setAttribute( "useuser", lUser );
				//获得材料用途列表
				lReason = useDao.getReasonArr( contractorid );
			}
            
            request.setAttribute( "usereason", lReason );
            request.getSession().setAttribute( "type", "use3" );
            super.setPageReset(request);
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "综合查询显示异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //综合查询执行
    public ActionForward queryExec( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80303" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        /*
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }
        */
        Part_requisitionBean bean = ( Part_requisitionBean )form;
        try{
        	String contractorid = null;
        	if (!"1".equals(userinfo.getDeptype())) {
				contractorid = ( String )request.getSession().getAttribute(
                				"LOGIN_USER_DEPT_ID" );
				bean.setContractorid( contractorid );
			}                        
           
            PartUseDao dao = new PartUseDao();

            List lUseInfo = dao.getAllUseForSearch( bean,userinfo );
            request.getSession().setAttribute( "useinfo", lUseInfo );
            request.getSession().setAttribute( "type", "use1" );
            request.getSession().setAttribute("bean",bean);
            super.setPageReset(request);
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "综合查询执行异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //////////////////////使用统计//////////////////
    //使用统计查询显示
    public ActionForward queryShowForStat( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80307" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        try{
            String contractorid = ( String )request.getSession().getAttribute(
                                  "LOGIN_USER_DEPT_ID" );
            PartUseDao useDao = new PartUseDao();
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
            
            // 2009-5-14 by guixy start
			//获取线路级别
    		LineCutReService service = new LineCutReService();
			List levelList = service.getLineLevle();
			request.setAttribute("levelInfo", levelList);
			// 2009-5-14 by guixy end
                        
            request.setAttribute( "deptinfo", lDept );
            request.setAttribute( "nameinfo", lPartName );
            request.setAttribute( "typeinfo", lPartType );
            request.setAttribute( "factoryinfo", lFactory );
            request.setAttribute( "usereason", lReason );

            request.setAttribute( "usedept", userinfo.getDeptype() );
            request.setAttribute( "usetype", userinfo.getType() );

            request.getSession().setAttribute( "type", "use7" );
            super.setPageReset(request);
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "使用统计查询显示异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //统计查询执行
    public ActionForward dostat( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
    	String levanName="";
    	String sublineName="";
    	String sublineIdName="";
    	String linechangename="";
    	String [] levanTem = null;
    	String [] sublineNameTem = null;
    	String [] sublineIdNameTem = null;
    	String [] linechangenameTem = null;
        if( !CheckPower.checkPower( request.getSession(), "80307" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );

        try{
            String contractorid = ( String )request.getSession().getAttribute(
                                  "LOGIN_USER_DEPT_ID" );
            Part_requisitionBean bean = ( Part_requisitionBean )form;
            if(bean.getTotaltype() == null || "".equals(bean.getTotaltype())){
            	bean = (Part_requisitionBean) request.getSession().getAttribute("partbean");
            }
            
            Part_requisitionBean partbean=new Part_requisitionBean();
            partbean.setTotaltype(bean.getTotaltype());
            partbean.setSubline(bean.getSubline());
            partbean.setLevel(bean.getLevel());
            partbean.setSublineId(bean.getSublineId());
            partbean.setLinechangename(bean.getLinechangename());
            partbean.setCutchangename(bean.getCutchangename());
            partbean.setContractorid(bean.getContractorId());
            partbean.setBegintime(bean.getBegintime());
            partbean.setEndtime(bean.getEndtime());
            request.getSession().setAttribute("partbean", partbean);
            
            if("2".equals(bean.getTotaltype())){
            	levanName = bean.getLevel();
            	sublineName = bean.getSubline();
            	sublineIdName = bean.getSublineId();
            	linechangename = bean.getLinechangename();
            	if(levanName!=null && !"".equals(levanName)){
            		levanTem = levanName.split(",");
            		bean.setLevel(levanTem[0]);
            	}
            	if(sublineName != null && !"".equals(sublineName)){
            		sublineNameTem = sublineName.split(",");
            		bean.setSubline(sublineNameTem[0]);
            	}
            	if(sublineIdName != null && !"".equals(sublineIdName)){
            		sublineIdNameTem = sublineIdName.split(",");
            		bean.setSublineId(sublineIdNameTem[0]);
            	}
            	if(linechangename != null && !"".equals(linechangename)){
            		linechangenameTem = linechangename.split(",");
            		bean.setLinechangename(linechangenameTem[0]);
            	}
            }
            if("3".equals(bean.getTotaltype())){
            	levanName = bean.getLevel();
            	if(levanName!=null && !"".equals(levanName)){
            		levanTem = levanName.split(",");
            		bean.setLevel(levanTem[0]);
            	}
            }
            bean.setRegionid( userinfo.getRegionID() );
            PartUseDao dao = new PartUseDao();
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
            
            if(levanTem != null){
            	bean.setLevel(levanTem[1]);
            }
            if(sublineNameTem != null){
            	bean.setSubline(sublineNameTem[1]);
            }
            if(sublineIdNameTem != null){
            	bean.setSublineId(sublineIdNameTem[1]);
            }
            if(linechangenameTem != null){
            	bean.setLinechangename(linechangenameTem[1]);
            }
            
            request.getSession().setAttribute( "useinfo", lUseInfo );
            request.getSession().setAttribute( "type", "use70" );
            request.getSession().setAttribute("bean",bean);
            super.setPageReset(request);
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "综合查询执行异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
    
    //查找某个统计材料详细信息 jixf
    
    public ActionForward doShowOneForPart( ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response ){
    	String partId = request.getParameter("partId");
    	Part_requisitionBean bean = (Part_requisitionBean)request.getSession().getAttribute("partbean");
    	
    	Part_requisitionBean partbean=new Part_requisitionBean();
        partbean.setTotaltype(bean.getTotaltype());
        partbean.setSubline(bean.getSubline());
        partbean.setLevel(bean.getLevel());
        partbean.setSublineId(bean.getSublineId());
        partbean.setLinechangename(bean.getLinechangename());
        partbean.setCutchangename(bean.getCutchangename());
        partbean.setContractorid(bean.getContractorId());
        partbean.setBegintime(bean.getBegintime());
        partbean.setEndtime(bean.getEndtime());
        request.getSession().setAttribute("partbean", partbean);
        
        String levanName = bean.getLevel();
    	String sublineName = bean.getSubline();
    	String sublineIdName = bean.getSublineId();
    	String linechangename = bean.getLinechangename();
    	if(levanName!=null && !"".equals(levanName)){
    		String[] levanTem = levanName.split(",");
    		bean.setLevel(levanTem[0]);
    	}
    	if(sublineName != null && !"".equals(sublineName)){
    		String[] sublineNameTem = sublineName.split(",");
    		bean.setSubline(sublineNameTem[0]);
    	}
    	if(sublineIdName != null && !"".equals(sublineIdName)){
    		String[] sublineIdNameTem = sublineIdName.split(",");
    		bean.setSublineId(sublineIdNameTem[0]);
    	}
    	if(linechangename != null && !"".equals(linechangename)){
    		String[] linechangenameTem = linechangename.split(",");
    		bean.setLinechangename(linechangenameTem[0]);
    	}
        
    	PartUseDao dao = new PartUseDao();    
    	BasicDynaBean partinfo = dao.getOnPartById(partId);
    	List useinfo = dao.getOnePartUse(partId,bean);
    	request.getSession().setAttribute("userInfo", useinfo);
    	request.setAttribute("partinfo", partinfo);
    	request.getSession().setAttribute("type", "use700");
    	return mapping.findForward("success");
//    	return null;
    }


    //////////////////////退库处理//////////////////
    //退库_材料用途选择显示
    public ActionForward showBackReason( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
//        if( !CheckPower.checkPower( request.getSession(), "80306" ) ){
//            return mapping.findForward( "powererror" );
//        }
//        //获得当前用户的单位名称
//        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
//                            "LOGIN_USER" );
//        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
//            return forwardErrorPage( mapping, request, "partstockerror" );
//        }
//
//        try{
//            String contractorid = ( String )request.getSession().getAttribute(
//                                  "LOGIN_USER_DEPT_ID" );
//            PartUseDao useDao = new PartUseDao();
//            //获得材料用途列表
//            List lReason = null;
//            lReason = useDao.getReasonArrForBack( contractorid );
//            request.setAttribute( "usereason", lReason );
//
//            request.getSession().setAttribute( "type", "use6" );
//            return mapping.findForward( "success" );
//
//        }
//        catch( Exception e ){
//            logger.error( "退库_材料用途选择显示异常:" + e.getMessage() );
//            return forwardErrorPage( mapping, request, "error" );
//        }
    	response.setContentType("text/html; charset=utf-8");
    	String contractorid = (String) request.getSession().getAttribute(
				"LOGIN_USER_DEPT_ID");
		PartUseDao useDao = new PartUseDao();
		// 获得材料用途列表
		List lReason = null;
		lReason = useDao.getReasonArrForBack(contractorid);

		StringBuffer sb = new StringBuffer();
		sb.append("[");
		DynaBean row;
		for (int i = 0; i < lReason.size(); i++) {
			row = (DynaBean) lReason.get(i);
			sb.append("['").append(String.valueOf(row.get("usereason")))
					.append("--").append(String.valueOf(row.get("useid")))
					.append("'],");
		}
			
		
		int length = sb.toString().length() - 1;
		String str = sb.toString().substring(0,length) + "]";
		PrintWriter out;
		try {
			out = response.getWriter();
			out.write(str);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
    }


    //退库_材料输入显示
    public ActionForward showBackPart( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80306" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            PartUseDao useDao = new PartUseDao();
            Part_requisitionBean useinfo = new Part_requisitionBean(); //传回页面的出库单基本信息
            List usepartinfo = null;
            PartUseDao dao = new PartUseDao();
            String useid = request.getParameter("useid"); //获得要修改的出库单id
            System.out.println(useid);
            if(useid == null) {
            	//	 出库单选择的有误
            	return forwardErrorPage( mapping, request, "80306e" );
            }
            int index = useid.indexOf("--");
            if(index == -1) {
            	// 出库单选择的有误
            	return forwardErrorPage( mapping, request, "80306e" );
            }
            // 前台传来的是"用途"+"--"+"出库id"
            useid = useid.substring(index+2, useid.length());
            //获得入库单基本信息
            useinfo = dao.getOneUse( useid ); ;
            request.setAttribute( "useinfo", useinfo );

            if(useinfo == null || "".equals(useinfo.getUseid())) {
            	// 出库单选择的有误
            	return forwardErrorPage( mapping, request, "80306e" );
            }
            
            //获得入库单所入库的材料信息
            usepartinfo = dao.getPartOfOneUse( useid );
            request.setAttribute( "usepartinfo", usepartinfo );

            request.getSession().setAttribute( "type", "use60" );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "//退库_材料输入显示异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //开始退库
    public ActionForward doBackStock( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80306" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            PartUseDao dao = new PartUseDao();
            String useid = request.getParameter( "useid" );
            String contractorid = userinfo.getDeptID();
            String[] id = request.getParameterValues( "id" );
            String[] newbacknumber = request.getParameterValues( "newbacknumber" );
            String[] oldbacknumber = request.getParameterValues( "oldbacknumber" );
            if( !dao.backStock( contractorid, useid, id, newbacknumber, oldbacknumber ) ){
                return forwardErrorPage( mapping, request, "error" );
            }
            log( request, "材料管理", "材料退库" );
            return forwardInfoPage( mapping, request, "80306" );
        }
        catch( Exception e ){
            logger.error( "在开始退库中出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //材料使用一览表查询结果输出到Excel表
    public ActionForward exportUse( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            Part_requisitionBean bean = null;
            if( request.getSession().getAttribute( "bean" ) != null ){
                bean = ( Part_requisitionBean )request.getSession().getAttribute( "bean" );
                logger.info( "获得查询条件bean。。。" );
                if("1".equals(bean.getTotaltype())){	                
	                logger.info( "材料名称：" + bean.getName());
	                logger.info( "材料型号：" + bean.getType() );
	                logger.info( "生产厂家：" + bean.getFactory());
	                logger.info( "材料用途：" + bean.getUsereason() );
                }else if("2".equals(bean.getTotaltype())){
                	logger.info("线路级别：" + bean.getLevel());
                	logger.info("光缆线段：" + bean.getSubline()) ;
                	logger.info("中继段：" + bean.getSublineId()) ;
                	logger.info("割接名称：" + bean.getLinechangename()) ;
                }else if("3".equals(bean.getTotaltype())){
                	logger.info("线路级别: " + bean.getLevel());
                	logger.info("工程名称: " + bean.getCutchangename()) ;
                }
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
//                request.getSession().removeAttribute("bean");
            }
            PartExportDao dao = new PartExportDao();
            logger.info( " 创建dao" );
            List list = ( List )request.getSession().getAttribute( "useinfo" );
            logger.info( "得到list" );
            dao.ExportUse( list, bean, response );
            logger.info( "输出excel成功" );
           
            return null;
        }
        catch( Exception e ){
            logger.error( "导出材料使用一览表出现异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    public ActionForward exportUseResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            Part_requisitionBean bean = null;
            if(request.getSession().getAttribute("bean") != null){
                bean = ( Part_requisitionBean )request.getSession().getAttribute( "bean" );
                logger.info("获得查询条件bean。。。");
                logger.info("id："+bean.getUseuserid()+"出库原因："+bean.getUsereason()+"开始日期："+bean.getBegintime() + "结束日期："+bean.getEndtime());
                if( !bean.getUseuserid().equals( "" ) ){
                    String sql = "select u.USERNAME from userinfo u  where u.USERID = '" + bean.getUseuserid() + "'";
                    QueryUtil query = new QueryUtil();
                    ResultSet rs = null;
                    rs = query.executeQuery( sql );
                    while( rs.next() ){
                        bean.setUsername( rs.getString( 1 ) );
                    }
                }
                request.getSession().removeAttribute("bean");
            }

            PartExportDao dao = new PartExportDao();
            logger.info( " 创建dao" );
            List list = ( List )request.getSession().getAttribute( "useinfo" );
            logger.info( "得到list" );
            dao.exportUseResult( list, bean, response );
            logger.info( "输出excel成功" );

            return null;
        }
        catch( Exception e ){
            logger.error( "导出信息报表表出现异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }

    //导出维护材料出库单详细信息一览表
    public ActionForward exportUseList( ActionMapping mapping, ActionForm form,
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

            String useuserid = request.getParameter( "useuserid" );;
            String usereason = request.getParameter( "usereason" );
            String begintime = request.getParameter( "begintime" );
            String endtime = request.getParameter( "endtime" );

            Part_requisitionBean bean = ( Part_requisitionBean )form;
            bean.setContractorid( contractorid );
            bean.setUseuserid(useuserid);
            bean.setUsereason(usereason);
            bean.setBegintime(begintime);
            bean.setEndtime(endtime);
            if( useuserid != null ){
                String sql = "select u.USERNAME from userinfo u  where u.USERID = '" + bean.getUseuserid() + "'";
                QueryUtil query = new QueryUtil();
                ResultSet rs = null;
                rs = query.executeQuery( sql );
                while( rs.next() ){
                    bean.setUsername( rs.getString( 1 ) );
                }
                logger.info( "出库人姓名：" + bean.getUsername() );
                }
            PartUseDao useDao = new PartUseDao();
            List list = useDao.getUseList( bean );

            PartExportDao dao = new PartExportDao();
            dao.exportUseList(list, bean, response);

            return null;

        }
        catch( Exception e ){
            logger.error( "导出信息报表异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }

    /**
     * 查询所有材料使用信息的表单
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward queryAllUse(ActionMapping mapping, ActionForm form,
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

        return mapping.findForward("queryalluse");
    }
    
    // 选择用途模式页面
    public ActionForward showUseChange(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,HttpServletResponse response)
            throws ClientException, Exception{
    	// 登录人员的部门ID
    	UserInfo userinfo = ( UserInfo )request.getSession().getAttribute("LOGIN_USER" );
    	String deptId = userinfo.getDeptID();
    	
    	// 取得选择用途的标志
    	String useFlg = request.getParameter("flg");
    	request.setAttribute("flg", useFlg);
    	String sql = "";
    	List returnList = null;
 //   	if ("1".equals(useFlg)) {
    		// 割接
    		
//    		sql = "select reid id,name name from LINE_CUTINFO where AUDITRESULT='通过审批' and ISARCHIVE = '已经审批' " 
//				+ "and CONTRACTORID = '" + deptId + "' order by REID";	
			//获取线路级别
    		LineCutReService service = new LineCutReService();
			List levelList = service.getLineLevle();
			request.setAttribute("linelevelList", levelList);
//		} else {
			// 修缮
//			sql = "select id id,changename name from CHANGEINFO where APPROVERESULT = '通过审定' and  step='B2' " 
//				+ " and APPLYUNIT = '" + deptId + "' order by id";
//			returnList = super.getDbService().queryBeans(sql);
//		}
    	    	
    	
    	
    	// 将选择用途材料对应隐藏域的ID
    	String hiddenObjId = request.getParameter("obj");
    	request.setAttribute("hiddenobjname", hiddenObjId);
    	// 取得出库材料的数量
    	String sumNum = request.getParameter("sumnum");
    	request.setAttribute("sum",sumNum);
    	
    	request.setAttribute( "returnList", returnList );
    	
    	return mapping.findForward("changeuse");
    }
    
    /**
     * 根据线段选择的割接
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward getCutNameBySublineid(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json; charset=GBK");
		String sublineid = request.getParameter("sublineId");
		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
        "LOGIN_USER" );
//		String sql = "select reid id,name name from LINE_CUTINFO where AUDITRESULT='通过审批' and ISARCHIVE = '已经审批' " 
//			+ "and CONTRACTORID = '" + userinfo.getDeptID() + "' order by REID";
		String sql;
		String deptid = userinfo.getDeptID();
		if(userinfo.getDeptype().equals("1")) {
			sql = "select l.reid id, l.name from line_cutinfo l,deptinfo d where subStr(l.SUBLINEID,0,8)='" + sublineid + "'" +
			"  and l.isarchive != '待审批' and l.isarchive !='已经审批' and l.deptid='" + deptid + "' and l.deptid = d.deptid";
		} else {
			sql = "select l.reid id, l.name from line_cutinfo l,deptinfo d where subStr(l.SUBLINEID,0,8)='" + sublineid + "'" +
			"  and l.isarchive != '待审批' and l.isarchive !='已经审批' and l.contractorid='" + deptid + "' and l.deptid = d.deptid";
		}
		try {
			List returnList = super.getDbService().queryBeans(sql);
			JSONArray ja = JSONArray.fromObject(returnList);
			PrintWriter out = response.getWriter();
			out.write(ja.toString());
			out.flush();
			out.close();
			System.out.println(ja.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
    
    /**
     * 根据线段级别选择的修缮
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward getLinechangeNameByLevel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json; charset=UTF-8");
		String levelId = request.getParameter("levelId");
		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
        "LOGIN_USER" );
		String sql;
		if(userinfo.getDeptype().equals("1")) {
			sql = "select id id,changename name from CHANGEINFO where APPROVERESULT = '通过审定' and  step='B2' " 
				+ " and regionid = '" + userinfo.getRegionid() + "'"
				+ " and lineclass = '" + levelId + "'"
				+ " order by id";
		} else {
			sql = "select id id,changename name from CHANGEINFO where APPROVERESULT = '通过审定' and  step='B2' " 
				+ " and APPLYUNIT = '" + userinfo.getDeptID() + "'" 
				+ " and lineclass = '" + levelId + "'"
				+ " order by id";
		}
		try {
			List returnList = super.getDbService().queryBeans(sql);
			JSONArray ja = JSONArray.fromObject(returnList);
			PrintWriter out = response.getWriter();
			out.write(ja.toString());
			out.flush();
			out.close();
			System.out.println(ja.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
    
    // 选择用途模式页面
    public ActionForward displayUse(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,HttpServletResponse response)
            throws ClientException, Exception {
    	// 取得出库单ID
    	String useId = request.getParameter("useid");
    	String baseId = request.getParameter("baseid");
    	// sql语句
    	String sql = "select obj.id, obj.objectnum , l.name objname , l.reid reid "
    				 + " from part_useobject_baseinfo obj "
    				 // + " left join part_baseinfo b on obj.baseid = b.id  "
    				 + " left join LINE_CUTINFO l on l.reid = obj.objectid "
    				 + " where obj.objecttype = '0' and obj.useid = '" + useId + "'"
    				 + " and obj.baseid = '" + baseId + "'";
    	List linecutList = super.getDbService().queryBeans(sql);
    	sql = "select obj.id, obj.objectnum , c.changename objname , c.id reid "
			 + " from part_useobject_baseinfo obj "
			// + " left join part_baseinfo b on obj.baseid = b.id  "
			 + " left join changeinfo c on c.id = obj.objectid "
			 + " where obj.objecttype = '1' and obj.useid = '" + useId + "'"
    	 	+ " and obj.baseid = '" + baseId + "'";
    	List chaneList = super.getDbService().queryBeans(sql);
    	
    	// 将数据放入rquest中。
    	
    	request.setAttribute( "linecutList", linecutList );
    	request.setAttribute( "chaneList", chaneList );
    	return mapping.findForward("showchangeuse");
    }
    
    public static void main(String[] args) {
		String str = "aaaa--bb";
		System.out.println(str.substring(str.indexOf("--")+2, str.length()));
	}
}
