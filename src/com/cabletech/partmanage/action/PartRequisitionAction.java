package com.cabletech.partmanage.action;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.dao.UserInfoDAOImpl;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.sm.SendSMRMI;
import com.cabletech.commons.web.ClientException;
import com.cabletech.partmanage.beans.Part_requisitionBean;
import com.cabletech.partmanage.dao.PartBaseInfoDao;
import com.cabletech.partmanage.dao.PartExportDao;
import com.cabletech.partmanage.dao.PartRequisitionDao;
import com.cabletech.power.CheckPower;
import com.cabletech.watchinfo.templates.WatchDetailTemplate;

public class PartRequisitionAction extends BaseDispatchAction{
    private static Logger logger = Logger.getLogger( PartRequisitionAction.class.
                                   getName() );
    //修改显示
    public ActionForward upshow( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80104" ) ){
            return mapping.findForward( "powererror" );
        }

        Part_requisitionBean bean = ( Part_requisitionBean )form;
        Part_requisitionBean reqinfo = new Part_requisitionBean(); //传回页面的申请单基本信息
        List reqpartinfo = null;
        PartRequisitionDao dao = new PartRequisitionDao();
        String reid = request.getParameter( "reid" ); //获得要修改的申请单id

        try{
            //能否修改
            if( !dao.valiReqForUp( reid ) ){
                return forwardErrorPage( mapping, request, "80104e" );
            }
            //获得当前用户的单位名称
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                                "LOGIN_USER" );
            if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许修改材料申请的
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
            //String data = DateUtil.DateToString(nowDate);
            String date = df.format( nowDate );
            request.setAttribute( "date", date );

            //获得材料信息,以供用户选择
            List lBaseInfo = dao.getAllInfo( userinfo.getRegionID() );
            request.setAttribute( "baseinfo", lBaseInfo );
            //获得申请单基本信息
            reqinfo = dao.getOneReq( reid );
            request.setAttribute( "reqinfo", reqinfo );
            // add by guixy 
            // 获得目标处理人，供用户选择.
            List ltarget = dao.getTargetman( request );
            request.setAttribute( "ltargetman", ltarget );
            // add by guixy 
            
            //获得申请单所申请的材料信息
            reqpartinfo = dao.getReqPart( reid, request );
            request.setAttribute( "reqpartinfo", reqpartinfo );
            request.getSession().setAttribute( "type", "4" );

            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "在修改显示中出现错误:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //执行修改
    public ActionForward upRequisition( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        //权限检查
        if( !CheckPower.checkPower( request.getSession(), "80104" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            PartRequisitionDao dao = new PartRequisitionDao();
            Part_requisitionBean bean = ( Part_requisitionBean )form;
            String contractorid = ( String )request.getSession().getAttribute( "LOGIN_USER_DEPT_ID" );
            bean.setContractorid( contractorid );
            String[] id = request.getParameterValues( "id" );
            String[] renumber = request.getParameterValues( "renumber" );

            //执行修改操作
            if( !dao.doUpdateRequisition( bean, id, renumber ) ){ //更新申请单-材料对应表
                return forwardErrorPage( mapping, request, "error" );
            }
            log( request, "材料管理", "修改材料申请单" );
            return forwardInfoPage( mapping, request, "80104" );
        }
        catch( Exception e ){
            logger.error( "在更新申请单中出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //显示所有申请单
    public ActionForward showAllRequisition( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        boolean flag=false;
        if( CheckPower.checkPower( request.getSession(), "80102" )
        	|| CheckPower.checkPower( request.getSession(), "80103" )
            ||CheckPower.checkPower( request.getSession(), "80109" )){
            flag=true;
        }
        if(!flag){
            return mapping.findForward( "powererror" );
        }

        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        //if( userinfo.getType().equals( "12" ) ){ //如果是市移动公司是不允许查看材料申请的
        if( userinfo.getDeptype().equals( "1" )
            &&!userinfo.getRegionID().substring(2,6).equals("0000") ){ //如果是市移动公司是不允许查看材料申请的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }
        try{
            PartRequisitionDao dao = new PartRequisitionDao();
            
            if(request.getParameter("querytype")!=null
                &&request.getParameter("querytype").equals("1")){
                List lReqInfo = dao.getAllRequisition( request );
                request.getSession().setAttribute( "reqinfo", lReqInfo );
                request.getSession().setAttribute( "type", "showall01" );
            }
            else{
                List lReqInfo = dao.getAllReq( request );
                request.getSession().setAttribute( "reqinfo", lReqInfo );
                request.getSession().setAttribute( "type", "1" );
            }
            request.getSession().setAttribute("querytype","1");
            //super.setPageReset(request);
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "显示所有申请单出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //添加显示
    public ActionForward addReShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80101" ) ){
            return mapping.findForward( "powererror" );
        }

        PartRequisitionDao dao = new PartRequisitionDao();
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许添加材料申请的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }
        try{
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
            //获得目标处理人，供用户选择.
            List ltarget = dao.getTargetman( request );
            request.setAttribute( "ltargetman", ltarget );
            //获得材料信息,以供用户选择
            List lBaseInfo = dao.getAllInfo( userinfo.getRegionID() );
            request.setAttribute( "baseinfo", lBaseInfo );
            request.getSession().setAttribute( "type", "2" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.warn( "显示异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    /**
     *  执行添加申请单
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     */
    public ActionForward addRequisition( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        /**验证权限**/
        if( !CheckPower.checkPower( request.getSession(), "80101" ) ){
            return mapping.findForward( "powererror" );
        }
        // 获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            PartRequisitionDao dao = new PartRequisitionDao();
            Part_requisitionBean bean = ( Part_requisitionBean )form;
            bean.setRegionid( userinfo.getRegionID() );
            OracleIDImpl ora = new OracleIDImpl();
            String reid = ora.getSeq( "Part_requisition", 10 ); //获取id
            bean.setReid( reid );

            String[] id = request.getParameterValues( "id" );
            String[] renumber = request.getParameterValues( "renumber" );
            // 判断是否没有申请材料的数量，如果一个材料的数量都没有，则返回信息提示用户
            boolean flag = false;
            // 材料重复选择的标志
            boolean flg = false;
            for( int i = 0; i < renumber.length; i++ ){
            	flg = false;
                if( renumber[i].equals( "0" ) || id[i].trim().equals( "" )){
                    flag = false;
                    break;
                }
                for (int j = 0; j < id.length; j++) {
					if(i != j) {
						if(id[i].equals(id[j])) {
							flg = true;
							break;
						}
					}
				}
                if(flg) {
                	break;
                }
            }
            if( flag ){
                return forwardErrorPage( mapping, request, "80102e" );
            }
            if(flg) {
            	return forwardErrorPage( mapping, request, "80102e2" );
            }

            if( !dao.doAddRequisition( bean, id, renumber ) ){
                //System.out.println( "Error :" );
                return forwardErrorPage( mapping, request, "error" );
            }
            /** 发送短信**/
            String reason = bean.getReason();
            String str = subString(reason,20,"......");
            StringBuffer sb = new StringBuffer();
            sb.append("<").append(str).append(">").
            append(request.getSession().getAttribute( "LOGIN_USER_DEPT_NAME" )).
            append("代维单位的材料工单等待您的签收和处理。");
            if( request.getSession().getAttribute( "isSendSm" ).equals( "send" ) ){
                String objectman = new UserInfoDAOImpl().findById( request.getParameter( "targetman" ) ).getPhone();
                if( objectman != null && !objectman.equals( "" ) ){
                    String msg = sb.toString();
                    SendSMRMI.sendNormalMessage( userinfo.getUserID(), objectman, msg, "00" );
                    System.out.println( msg );
                }
            }
            log( request, "材料管理", "添加材料申请单" );
            return forwardInfoPage( mapping, request, "80102" );
        }
        catch( Exception e ){
            e.printStackTrace();
            logger.error( "在添加申请单中出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //显示一个申请单的详细信息
    public ActionForward showOneInfo( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        boolean flag=false;
        if( CheckPower.checkPower( request.getSession(), "80102" )
        	||CheckPower.checkPower( request.getSession(), "80103" )
            ||CheckPower.checkPower( request.getSession(), "80109" )){
            flag=true;
        }
        if(!flag)
            return mapping.findForward( "powererror" );

        Part_requisitionBean bean = ( Part_requisitionBean )form;
        Part_requisitionBean reqinfo = new Part_requisitionBean(); //传回页面的申请单基本信息
        List reqpartinfo = null;
        PartRequisitionDao dao = new PartRequisitionDao();
        String reid = request.getParameter( "reid" ); //获得要修改的申请单id

        try{
            //获得当前用户的单位名称
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                                "LOGIN_USER" );
            //if( userinfo.getType().equals( "12" ) ){ //如果是市移动公司是不允许查看材料申请的
            if( userinfo.getDeptype().equals( "1" )
                &&!userinfo.getRegionID().substring(2,6).equals("0000") ){ //如果是市移动公司是不允许查看材料申请的
                return forwardErrorPage( mapping, request, "partstockerror" );
            }
            String deptname = dao.getUserDeptName( userinfo );
            request.setAttribute( "deptname", deptname );
            request.setAttribute( "deptid", userinfo.getDeptID() );
            request.setAttribute( "username", userinfo.getUserName() );
            request.setAttribute( "userid", userinfo.getUserID() );

            //获得申请单基本信息
            reqinfo = dao.getOneReq( reid );
            if( reqinfo.getAudituserid()!=null&&!reqinfo.getAudituserid().equals( "" ) ){
                String sql =
                    "select username from userinfo where userid = '" + reqinfo.getAudituserid()+ "'";
                QueryUtil query = new QueryUtil();
                ResultSet rs = null;
                rs = query.executeQuery( sql );
                while( rs.next() ){
                    reqinfo.setAuditusername( rs.getString( 1 ) );
                }
                logger.info( "审批人：" + reqinfo.getAuditusername() );
            }
            request.setAttribute( "reqinfo", reqinfo );

            if(request.getParameter("querytype")!=null
                &&request.getParameter("querytype").equals("1")){
                //获得申请单所申请的材料信息
                reqpartinfo = dao.getRequisitionPart( reid, request );
                request.setAttribute( "reqpartinfo", reqpartinfo );
                request.getSession().setAttribute( "type", "showall10" );
            }
            else{
                //获得申请单所申请的材料信息
                reqpartinfo = dao.getReqPart( reid, request );
                request.setAttribute( "reqpartinfo", reqpartinfo );
                request.getSession().setAttribute( "type", "10" );
            }
            request.getSession().setAttribute("basebean",reqinfo);
            request.getSession().setAttribute("partsbean",reqpartinfo);
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "在显示详细中出现错误:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //执行删除一个申请单
    public ActionForward delOneReqInfo( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        //权限检查
        if( !CheckPower.checkPower( request.getSession(), "80105" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            PartRequisitionDao dao = new PartRequisitionDao();
            String reid = request.getParameter( "reid" );

            if( dao.valiReqForUp( reid ) ){ //检查能否删除,能删除
                dao.deletReqInfo( reid );
                log( request, "材料管理", "删除材料申请单" );
                return forwardInfoPage( mapping, request, "80105" );
            }
            else{
                return forwardErrorPage( mapping, request, "80105e" );
            }
        }
        catch( Exception e ){
            logger.error( "在删除申请单中出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //综合查询显示
    public ActionForward queryShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80103" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            String contractorid = ( String )request.getSession().getAttribute(
                                  "LOGIN_USER_DEPT_ID" );
            
            PartBaseInfoDao dao = new PartBaseInfoDao();
            PartRequisitionDao reqDao = new PartRequisitionDao();
            //获得申请人名单
            List lUser = reqDao.getUserOfReq( contractorid );
            request.setAttribute( "requser", lUser );
            //获得申请原因列表
            List lReason = reqDao.getReasonOfReq( contractorid );
            request.setAttribute( "reqreason", lReason );
            request.getSession().setAttribute( "type", "3" );
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
        if( !CheckPower.checkPower( request.getSession(), "80103" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            String contractorid = ( String )request.getSession().getAttribute(
                                  "LOGIN_USER_DEPT_ID" );
            Part_requisitionBean bean = ( Part_requisitionBean )form;
            bean.setContractorid( contractorid );
            PartRequisitionDao dao = new PartRequisitionDao();
            
            String[] id = request.getParameterValues( "id" );
            List lReqInfo = dao.doSeatchReq( bean, id );
            request.getSession().setAttribute( "reqinfo", lReqInfo );
            request.getSession().setAttribute( "type", "1" );
            request.getSession().setAttribute( "bean", bean );
            super.setPageReset(request);
            request.setAttribute("queryApply","query");
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "综合查询执行异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //添加导入处理
    public ActionForward upLoadShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80101" ) ){
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
        //获得导入文件信息并传回页面供用户修改
        Part_requisitionBean bean = ( Part_requisitionBean )form;
        bean.setRegionid( userinfo.getRegionID() );
        String filename = bean.getFile().getFileName();
        if( filename.equals( "" ) || filename == null ){
            return forwardErrorPage( mapping, request, "fileerror" );
        }
        if( !filename.substring( filename.length() - 3, filename.length() ).equals( "xls" ) ){
            return forwardErrorPage( mapping, request, "structerror" );
        }

        String path = servlet.getServletContext().getRealPath( "/upload" );
        Vector vUpInfo = dao.getUpInfo( bean, path );
        if( vUpInfo == null ){
            return forwardErrorPage( mapping, request, "error" );
        }
        //获得目标处理人，供用户选择.
        List ltarget = dao.getTargetman( request );
        request.setAttribute( "ltargetman", ltarget );

        request.setAttribute( "reqpartinfo", vUpInfo );
        request.getSession().setAttribute( "type", "upshow" );
        return mapping.findForward( "success" );
    }

    /**
     * 申请单模板下载
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward downloadTemplet(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response ) {
    	
    	response.reset();
		response.setContentType("application/vnd.ms-excel");
		try {
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String("申请单模板.xls".getBytes(), "iso-8859-1"));
			OutputStream out = response.getOutputStream();

			WatchDetailTemplate template = new WatchDetailTemplate(servlet
					.getServletContext().getRealPath("/upload")
					+ "/temple.xls");
			template.write(out);
	    	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
    }

    // ///////////////////////////////////////////////审批

    //显示所有待审批的申请单
    public ActionForward doAuditShow( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){       
        //获得当前用户的单位类型
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "2" ) ){ //如果是代维单位是不允许审批的
            return forwardErrorPage( mapping, request, "partauditerror" );
        }
        try{
            PartRequisitionDao dao = new PartRequisitionDao();
            List lReqInfo = dao.getAllReqForAudit( userinfo.getRegionID() );
            request.getSession().setAttribute( "reqinfo", lReqInfo );
            request.getSession().setAttribute( "type", "audit2" );
            super.setPageReset(request);
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "显示审批材料申请单中出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //显示一个申请单的详细信息，开始审批
    public ActionForward doAuditShowOne( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){        

        Part_requisitionBean bean = ( Part_requisitionBean )form;
        Part_requisitionBean reqinfo = new Part_requisitionBean(); //传回页面的申请单基本信息
        List reqpartinfo = null;
        PartRequisitionDao dao = new PartRequisitionDao();
        String reid = request.getParameter( "reid" ); //获得要修改的申请单id

        try{
            //获得当前用户的单位类型
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                                "LOGIN_USER" );
            if( userinfo.getDeptype().equals( "2" ) ){ //如果是代维单位是不允审批的
                return forwardErrorPage( mapping, request, "partauditerror" );
            }

            String deptname = dao.getUserDeptName( userinfo ); //获得审批单位名称
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
            reqpartinfo = dao.getReqPartForAudit( reid );
            request.setAttribute( "reqpartinfo", reqpartinfo );
            request.getSession().setAttribute( "type", "audit20" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "在显示待审批申请单详细信息中出现错误:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //执行审批
    public ActionForward auditRequisition( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
       
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "2" ) ){ //如果是代维单位是不允许的
            return forwardErrorPage( mapping, request, "partauditerror" );
        }

        try{
            PartRequisitionDao dao = new PartRequisitionDao();
            Part_requisitionBean bean = ( Part_requisitionBean )form;
            bean.setDeptid( userinfo.getDeptID() );

            String[] id = request.getParameterValues( "id" );
            String[] audnumber = request.getParameterValues( "audnumber" );
            //填写审批单
            if( !dao.doAddAudit( bean ) ){
                return forwardInfoPage( mapping, request, "error" );
            }
            //如果没有通过,不用填写对应表,
            if( bean.getAuditresult().equals( "不予审批" ) ){
                log( request, "材料管理", "审批材料申请单,不予审批" );
                return forwardInfoPage( mapping, request, "80202" );
            }
            //填写审批材料单
            if( !dao.doAddReq_PartForAudit( id, audnumber, bean.getReid() ) ){
                return forwardErrorPage( mapping, request, "error" );
            }

            log( request, "材料管理", "审批材料申请单,通过审批" );
            return forwardInfoPage( mapping, request, "80202" );
        }
        catch( Exception e ){
            logger.error( "在执行审批中出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //显示一个申请单的详细信息，开始审核
    public ActionForward doShowOneForReAudit( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
      

        Part_requisitionBean bean = ( Part_requisitionBean )form;
        Part_requisitionBean reqinfo = new Part_requisitionBean(); //传回页面的申请单基本信息
        List reqpartinfo = null;
        PartRequisitionDao dao = new PartRequisitionDao();
        String reid = request.getParameter( "reid" ); //获得要修改的申请单id

        try{
            //获得当前用户的单位类型
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                                "LOGIN_USER" );
            if( userinfo.getDeptype().equals( "2" ) ){ //如果是代维单位是不允审批的
                return forwardErrorPage( mapping, request, "partauditerror" );
            }

            String deptname = dao.getUserDeptName( userinfo ); //获得审批单位名称
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

            //获得申请单所申请的材料信息--审核
            reqpartinfo = dao.getReqPartForReAudit( reid );
            request.setAttribute( "reqpartinfo", reqpartinfo );
            request.getSession().setAttribute( "type", "audit200" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "在显示待审批申请单详细信息中出现错误:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //执行审核
    public ActionForward doReAuditRequisition( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
       
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "2" ) ){ //如果是代维单位是不允许的
            return forwardErrorPage( mapping, request, "partauditerror" );
        }
        try{
            PartRequisitionDao dao = new PartRequisitionDao();
            String reid = request.getParameter( "reid" );
            String[] id = request.getParameterValues( "id" );
            //开始修改审批数量和库存中新品应有数量
            if( !dao.doReAudit( reid, id ) ){
                return forwardErrorPage( mapping, request, "error" );
            }
            log( request, "材料管理", "审批材料申请单,重新审核" );
            return forwardInfoPage( mapping, request, "802020" );
        }
        catch( Exception e ){
            logger.error( "在执行审批中出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //显示所有审批单
    public ActionForward showAllAudit( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        boolean flag=false;
        if( CheckPower.checkPower( request.getSession(), "80201" )
            ||CheckPower.checkPower( request.getSession(), "80209" )){
            flag=true;
        }
        if(!flag)
            return mapping.findForward( "powererror" );

        //获得当前用户的单位类型
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "2" ) ){ //如果是代维单位是不允许的
            return forwardErrorPage( mapping, request, "partauditerror" );
        }

        try{
            PartRequisitionDao dao = new PartRequisitionDao();
            
            if(request.getParameter("querytype")!=null
                &&request.getParameter("querytype").equals("1")){
                List lReqInfo = dao.getAllAuditReq( request );
                request.getSession().setAttribute( "reqinfo", lReqInfo );
                request.getSession().setAttribute( "type", "showaudit1" );
            }else{
                List lReqInfo = dao.getAllAudit( request );
                request.getSession().setAttribute( "reqinfo", lReqInfo );
                request.getSession().setAttribute( "type", "audit1" );
            }
            request.setAttribute( "deptname", request.getSession().getAttribute( "LOGIN_USER_DEPT_NAME" ) );
            request.getSession().setAttribute("querytype","1");
            super.setPageReset(request);
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "显示所有审批单出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //查看审批单,显示一个申请单的详细信息,
    public ActionForward doShowOneAudit( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        boolean flag=false;
        if( CheckPower.checkPower( request.getSession(), "80201" )
            ||CheckPower.checkPower( request.getSession(), "80209" )){
            flag=true;
        }
        if(!flag)
            return mapping.findForward( "powererror" );

        Part_requisitionBean bean = ( Part_requisitionBean )form;
        Part_requisitionBean reqinfo = new Part_requisitionBean(); //传回页面的申请单基本信息
        List reqpartinfo = null;
        PartRequisitionDao dao = new PartRequisitionDao();
        String reid = request.getParameter( "reid" ); //获得要修改的申请单id

        try{
            //获得当前用户的单位类型
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                                "LOGIN_USER" );
            if( userinfo.getDeptype().equals( "2" ) ){ //如果是代维单位是不允审批的
                return forwardErrorPage( mapping, request, "partauditerror" );
            }

            String deptname = dao.getUserDeptName( userinfo ); //获得审批单位名称
            request.setAttribute( "deptname", deptname );
            request.setAttribute( "deptid", userinfo.getDeptID() );
            request.setAttribute( "username", userinfo.getUserName() );
            request.setAttribute( "userid", userinfo.getUserID() );

            //获得服务器的当前时间
            Date nowDate = new Date();
            DateFormat df = DateFormat.getDateInstance( DateFormat.DATE_FIELD );
            String data = df.format( nowDate );
            request.setAttribute( "date", data );
            //获得审批单的基本信息
            Part_requisitionBean lAuditInfo = dao.getAuditInfo( reid );
            request.setAttribute( "auditinfo", lAuditInfo );

            //获得申请单基本信息
            reqinfo = dao.getOneReq( reid );
            request.setAttribute( "reqinfo", reqinfo );

            //获得申请单所申请的材料信息
            reqpartinfo = dao.getReqPartForAudit( reid );
            request.setAttribute( "reqpartinfo", reqpartinfo );
            request.getSession().setAttribute( "type", "audit10" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "在显示待审批申请单详细信息中出现错误:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //审批综合查询显示
    public ActionForward queryShowForAudit( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80203" ) ){
            return mapping.findForward( "powererror" );
        }

        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "2" ) ){ //如果是代维单位是不允许的
            return forwardErrorPage( mapping, request, "partauditerror" );
        }

        try{
            PartRequisitionDao dao = new PartRequisitionDao();
            //获得审批人名单
            List lUser = dao.getUserOfAudit( userinfo.getDeptID() );
            request.setAttribute( "audituser", lUser );
            //获得所有申请原因列表
            List lReason = dao.getAllReqReason( userinfo.getDeptID() );
            request.setAttribute( "reqreason", lReason );
            //获得所有申请单位名称
            List lDeptName = dao.getDeptName( userinfo.getDeptID() );
            request.setAttribute( "deptname", lDeptName );
            request.getSession().setAttribute( "type", "audit3" );
            super.setPageReset(request);
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "综合查询显示异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //审批综合查询执行
    public ActionForward queryExecForAudit( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80203" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "2" ) ){ //
            return forwardErrorPage( mapping, request, "partauditerror" );
        }

        try{
            Part_requisitionBean bean = ( Part_requisitionBean )form;
            PartRequisitionDao dao = new PartRequisitionDao();
            bean.setDeptid( userinfo.getDeptID() );
            List lAuditInfo = dao.getUserOfReq( bean );

            request.getSession().setAttribute( "reqinfo", lAuditInfo );
            request.getSession().setAttribute( "deptname", request.getSession().getAttribute( "LOGIN_USER_DEPT_NAME" ) );
            request.getSession().setAttribute( "type", "audit1" );
            request.getSession().setAttribute( "bean", bean );
            super.setPageReset(request);
            request.setAttribute("audit1","query");
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "审批综合查询执行异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //维护材料审批单一览表查询结果输出到Excel表
    public ActionForward exportRequisiton( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            Part_requisitionBean bean = null;
            if( request.getSession().getAttribute( "bean" ) != null ){
                bean = ( Part_requisitionBean )request.getSession().getAttribute( "bean" );
                logger.info( "获得查询条件bean。。。" );

                logger.info( "id：" + bean.getAudituserid() );
                logger.info( "审批状态：" + bean.getAuditresult() );
                logger.info( "申请单位：" + bean.getcontractorid() );
                logger.info( "申请原因：" + bean.getReason() );
                logger.info( "开始时间：" + bean.getBegintime() );
                logger.info( "结束时间：" + bean.getEndtime() );

                String sql =
                    "select u.USERNAME, c.CONTRACTORNAME from userinfo u, contractorinfo c  where u.USERID = '"
                    + bean.getAudituserid() + "' and c.CONTRACTORID = '" + bean.getcontractorid() + "'";
                logger.info( sql );
                QueryUtil query = new QueryUtil();
                ResultSet rs = null;
                rs = query.executeQuery( sql );
                while( rs.next() ){
                    bean.setUsername( rs.getString( 1 ) );
                    bean.setContractorname( rs.getString( 2 ) );
                }
                logger.info( "申请人姓名：" + bean.getUsername() );
                logger.info( "申请单位：" + bean.getContractorname() );

                request.getSession().removeAttribute( "bean" );
            }
            PartExportDao dao = new PartExportDao();
            logger.info( " 创建dao" );
            List list = ( List )request.getSession().getAttribute( "reqinfo" );
            logger.info( "得到list" );
            dao.ExportRequisition( list, bean, response );
            logger.info( "输出excel成功" );
            return null;
        }
        catch( Exception e ){
            logger.error( "导出维护材料审批单一览表出现异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    public ActionForward exportRequisitonResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            Part_requisitionBean bean = null;
            if( request.getSession().getAttribute( "bean" ) != null ){
                bean = ( Part_requisitionBean )request.getSession().getAttribute( "bean" );
                logger.info( "获得查询条件bean。。。" );

                logger.info( "id：" + bean.getUserid() );
                logger.info( "审批状态：" + bean.getAuditresult() );
                logger.info( "申请原因：" + bean.getReason() );
                logger.info( "开始时间：" + bean.getBegintime() );
                logger.info( "结束时间：" + bean.getEndtime() );
                if( !bean.getUserid().equals( "" ) ){
                    String sql = "select u.USERNAME from userinfo u  where u.USERID = '" + bean.getUserid() + "'";
                    QueryUtil query = new QueryUtil();
                    ResultSet rs = null;
                    rs = query.executeQuery( sql );
                    while( rs.next() ){
                        bean.setUsername( rs.getString( 1 ) );
                    }
                    logger.info( "申请人姓名：" + bean.getUsername() );
                    request.getSession().removeAttribute( "bean" );
                }
            }
            PartExportDao dao = new PartExportDao();
            logger.info( " 创建dao" );
            List list = ( List )request.getSession().getAttribute( "reqinfo" );
            logger.info( "得到list" );
            dao.ExportRequisitionResult( list, bean, response );
            logger.info( "输出excel成功" );

            return null;
        }

        catch( Exception e ){
            logger.error( "导出信息报表出现异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }

    /**
     * 查询所有材料申请的表单
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward queryAllRequisition(ActionMapping mapping, ActionForm form,
        HttpServletRequest request,HttpServletResponse response)
        throws ClientException, Exception{

        if( !CheckPower.checkPower( request.getSession(), "80109" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        //if( !userinfo.getType().equals( "11" ) ){ //
        if(!userinfo.getDeptype().equals("1")&&!userinfo.getRegionID().substring(2,6).equals("0000")){
            return forwardErrorPage( mapping, request, "partauditerror" );
        }

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

        return mapping.findForward("queryallrequisition");
    }

    /**
     * 查询所有材料审批的表单
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward queryAllAudit(ActionMapping mapping, ActionForm form,
        HttpServletRequest request,HttpServletResponse response)
        throws ClientException, Exception{

        if( !CheckPower.checkPower( request.getSession(), "80209" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        //if( userinfo.getType().equals( "12" ) ){ //如果是市移动公司是不允许查看材料申请的
        if( !userinfo.getDeptype().equals( "1" )
            &&!userinfo.getRegionID().substring(2,6).equals("0000") ){ //如果是市移动公司是不允许查看材料申请的
            return forwardErrorPage( mapping, request, "partauditerror" );
        }

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

        return mapping.findForward("queryallaudit");
    }

    public ActionForward exportOneRequisition( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            Part_requisitionBean bean = null;
            List partsbean=null;
            if( request.getSession().getAttribute( "basebean" ) != null ){
                bean = ( Part_requisitionBean )request.getSession().getAttribute( "basebean" );
                logger.info( "获得查询条件bean。。。" );
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
                    logger.info( "单位名称：" + bean.getContractorname() );
                }
                if( bean.getAudituserid()!=null&&!bean.getAudituserid().equals( "" ) ){
                    String sql =
                        "select username from userinfo where userid = '" + bean.getAudituserid()+ "'";
                    QueryUtil query = new QueryUtil();
                    ResultSet rs = null;
                    rs = query.executeQuery( sql );
                    while( rs.next() ){
                        bean.setAuditusername( rs.getString( 1 ) );
                    }
                    logger.info( "审批人：" + bean.getAuditusername() );
                }
               // request.getSession().removeAttribute("basebean");
            }
            if(request.getSession().getAttribute("partsbean")!=null){
                partsbean=(List)request.getSession().getAttribute("partsbean");
            }
            PartExportDao dao = new PartExportDao();
            logger.info( " 创建dao" );
            List list = ( List )request.getSession().getAttribute( "useinfo" );
            logger.info( "得到list" );
            dao.ExportRequisition( list, bean, partsbean, response );
            logger.info( "输出excel成功" );

            return null;
        }
        catch( Exception e ){
            logger.error( "导出材料使用一览表出现异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
    /**
     * 按字节截取字符串
     * @param str//传入的字符串
     * @param endCount//要截取的字节数
     * @param more//如果多余你要截取的字节数，将在字符串后追加显示的字符串
     * @return
     */
    private String subString(String str, int endCount, String more) {
		int resultLength = 0;
		String resultStr = "";
		if(str == null || str.trim().length() == 0) {
			return "";
		} else {
			char[] tempChars = str.toCharArray();
			int index=0;
			while(index < tempChars.length && endCount > resultLength) {
				String s1 = str.valueOf(tempChars[index]);
				byte[] b = s1.getBytes();
				resultLength += b.length;
				resultStr += tempChars[index];
				index++;
			}
		}
		if (endCount == resultLength || (endCount == resultLength - 1)) {
			resultStr += more;
		}
		return resultStr;
	}
}
