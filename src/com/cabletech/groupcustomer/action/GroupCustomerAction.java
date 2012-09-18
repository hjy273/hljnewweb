package com.cabletech.groupcustomer.action;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.groupcustomer.bean.GroupCustomerBean;
import com.cabletech.groupcustomer.dao.ExportGroupCustomerDao;
import com.cabletech.groupcustomer.dao.GroupCustomerDao;
import com.cabletech.partmanage.action.PartRequisitionAction;
import com.cabletech.watchinfo.templates.WatchDetailTemplate;

public class GroupCustomerAction extends BaseDispatchAction{
	
    private static Logger logger = Logger.getLogger( GroupCustomerAction.class.
            getName() );
    
    /**
     * 显示填写客户资料页面
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     */
    public ActionForward showGroupCustomerAdd( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        GroupCustomerDao dao = new GroupCustomerDao();
        try{
        	// 取得所有的区域列表
            List regionList = dao.getRegionList();
            request.setAttribute( "regionlist", regionList );
            request.getSession().setAttribute( "type", "1" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "显示填写集团客户资料页面出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
       
    /**
     * 保存集团客户资料
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     */
    public ActionForward addGroupCustomer( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
    	GroupCustomerBean bean = (GroupCustomerBean)form;
        GroupCustomerDao dao = new GroupCustomerDao();
        // 保存
        if(!dao.addCustomer(bean)){
            return forwardErrorPage( mapping, request, "error" );
        }

        return forwardInfoPage( mapping, request, "150101" );
      
    }

	/**
     * 导入单模板下载
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     */
    public ActionForward downloadTemplet(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response ) {
    	
    	response.reset();
		response.setContentType("application/vnd.ms-excel");
		try {
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String("集团客户导入模板.xls".getBytes(), "iso-8859-1"));
			OutputStream out = response.getOutputStream();

			WatchDetailTemplate template = new WatchDetailTemplate(servlet
					.getServletContext().getRealPath("/upload")
					+ "/groupcustomertemplate.xls");
			template.write(out);
	    	
		} catch (Exception e) {
			logger.warn("下载集团客户导入模板出错：" + e.getMessage());
			e.printStackTrace();
		}
		return null;
		
    }
    
    /**
     * 导入客户资料数据处理
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     */
    public ActionForward upLoadShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){

        GroupCustomerDao dao = new GroupCustomerDao();
    	GroupCustomerBean bean = ( GroupCustomerBean )form;
    	
    	// 判断导入文件类型
        String filename = bean.getFile().getFileName();
        if( filename.equals( "" ) || filename == null ){
            return forwardErrorPage( mapping, request, "fileerror" );
        }
        if( !filename.substring( filename.length() - 3, filename.length() ).equals( "xls" ) ){
            return forwardErrorPage( mapping, request, "structerror" );
        }

        // 取得导入临时文件的存入路径
        String path = servlet.getServletContext().getRealPath( "/upload" );
       
        // 保存客户资料
        if(!dao.saveExcelGroupcustomerInfo(bean, path)){
            return forwardErrorPage( mapping, request, "error" );
        }
        // 成功转向增加页面        
        return forwardInfoPage( mapping, request, "150101up" );
    }
    
    
    /**
     * 显示查询客户资料页面
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     */
    public ActionForward showQuery( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        GroupCustomerDao dao = new GroupCustomerDao();
        try{
        	
            // 取得登录人员的信息
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
            // 省代维
        	if(UserInfo.PROVINCE_MUSER_TYPE.equals(userinfo.getType())) {
        		// 取得所有的区域列表
                List regionList = dao.getRegionList();
                request.setAttribute( "regionlist", regionList );
        	}
            
            request.getSession().setAttribute( "type", "2" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "显示客户资料查询条件页面出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
    
    /**
     * 条件查询客户资料
     * */
    public ActionForward doQuery( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        GroupCustomerDao dao = new GroupCustomerDao();
        GroupCustomerBean bean = (GroupCustomerBean)form;
        try{  
            // 取得登录人员的信息
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        	// 取得所有的区域列表
            List lcustomer = dao.queryGroupCustomer(bean, userinfo, request.getSession());
            request.getSession().setAttribute( "customer", lcustomer );
            request.getSession().setAttribute( "type", "3" );
            this.setPageReset(request);
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "显示客户资料查询条件页面出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
    /**
     * 显示客户资料信息
     * */
    public ActionForward showOneCustomer( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        GroupCustomerDao dao = new GroupCustomerDao();
        
        // 取得客户ID
        String id = request.getParameter("id");
        
        try{
        	// 取得所有的区域列表
            GroupCustomerBean customerinfo = dao.getOneCustomer(id);
            request.getSession().setAttribute( "customeBean", customerinfo );
            request.getSession().setAttribute( "type", "4" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "显示客户资料查询条件页面出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
    /**
     * 返回
     * */
    public ActionForward doBack( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){        
        try{
        	String querysql = String.valueOf(request.getSession().getAttribute("querysql"));
        	// 取不到session中的sql语句，转向错误页面
        	if("null".equals(querysql)) {
        		return forwardErrorPage( mapping, request, "error" );
        	}
        	QueryUtil qu = new QueryUtil();
            List lcustomer = qu.queryBeans( querysql );;
            request.getSession().setAttribute( "customer", lcustomer );
            request.getSession().setAttribute( "type", "3" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "显示客户资料查询条件页面出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
    /**
     * 显示修改客户资料信息
     * */
    public ActionForward showUpCustomer( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        GroupCustomerDao dao = new GroupCustomerDao();
        
        // 取得客户ID
        String id = request.getParameter("id");
        
        try{
        	// 取得修改的客户详细资料
            GroupCustomerBean customerinfo = dao.getOneCustomer(id);
            
           
            
            // 将客户资料放入到form中
            try {
            	BeanUtil.objectCopy(customerinfo, form);
            } catch(Exception e) {
            	e.printStackTrace();
            }       
            List lregion = dao.getRegionList();
            request.setAttribute( "regionlist", lregion );
            request.getSession().setAttribute( "type", "5" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "显示客户资料查询条件页面出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
    /**
     * 保存修改的客户资料信息
     * */
    public ActionForward upCustomer( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        GroupCustomerDao dao = new GroupCustomerDao();
        
        // 取得修过的客户资料form
        GroupCustomerBean hform = (GroupCustomerBean)form;
        
        
        try{
        	// 执行更新
            if(!dao.updateCustomer(hform)) {
            	return forwardErrorPage( mapping, request, "error" );
            }
             
            return forwardInfoPage( mapping, request, "150102" );
        }
        catch( Exception e ){
            logger.error( "显示客户资料查询条件页面出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
    /**
     * 删除单个客户资料信息
     * */
    public ActionForward delCustomer( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        GroupCustomerDao dao = new GroupCustomerDao();
        
        // 取得要删除的客户资料id
        String id = request.getParameter("id");
        
        
        try{
        	// 执行更新
            if(!dao.delCustomer(id)) {
            	return forwardErrorPage( mapping, request, "error" );
            }
             
            return forwardInfoPage( mapping, request, "150102d" );
        }
        catch( Exception e ){
            logger.error( "显示客户资料查询条件页面出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
    
    /**
     * 批量删除客户资料信息
     * */
    public ActionForward delCustomers( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        GroupCustomerDao dao = new GroupCustomerDao();
        
        // 取得要删除的客户资料id
        String[] id = request.getParameterValues("ifCheck");
        
        
        try{
        	// 执行删除
            if(!dao.delCustomers(id)) {
            	return forwardErrorPage( mapping, request, "error" );
            }
             
            return forwardInfoPage( mapping, request, "150102d" );
        }
        catch( Exception e ){
            logger.error( "显示客户资料查询条件页面出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
    /**
     * 导出派单信息一览表
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     */
    public ActionForward exportGroupCustomerResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            logger.info( " 创建dao" );
            List list = ( List )request.getSession().getAttribute( "customer" );
            logger.info( "得到list" );
            ExportGroupCustomerDao exdao = new ExportGroupCustomerDao();
            if( exdao.exportGroupCustomerResult( list, response ) ){
                logger.info( "输出excel成功" );
            }
            return null;
        }
        catch( Exception e ){
            logger.error( "导出派单信息一览表出现异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
}