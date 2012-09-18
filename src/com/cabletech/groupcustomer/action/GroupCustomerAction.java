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
     * ��ʾ��д�ͻ�����ҳ��
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
        	// ȡ�����е������б�
            List regionList = dao.getRegionList();
            request.setAttribute( "regionlist", regionList );
            request.getSession().setAttribute( "type", "1" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "��ʾ��д���ſͻ�����ҳ�����:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
       
    /**
     * ���漯�ſͻ�����
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
        // ����
        if(!dao.addCustomer(bean)){
            return forwardErrorPage( mapping, request, "error" );
        }

        return forwardInfoPage( mapping, request, "150101" );
      
    }

	/**
     * ���뵥ģ������
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
					+ new String("���ſͻ�����ģ��.xls".getBytes(), "iso-8859-1"));
			OutputStream out = response.getOutputStream();

			WatchDetailTemplate template = new WatchDetailTemplate(servlet
					.getServletContext().getRealPath("/upload")
					+ "/groupcustomertemplate.xls");
			template.write(out);
	    	
		} catch (Exception e) {
			logger.warn("���ؼ��ſͻ�����ģ�����" + e.getMessage());
			e.printStackTrace();
		}
		return null;
		
    }
    
    /**
     * ����ͻ��������ݴ���
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
    	
    	// �жϵ����ļ�����
        String filename = bean.getFile().getFileName();
        if( filename.equals( "" ) || filename == null ){
            return forwardErrorPage( mapping, request, "fileerror" );
        }
        if( !filename.substring( filename.length() - 3, filename.length() ).equals( "xls" ) ){
            return forwardErrorPage( mapping, request, "structerror" );
        }

        // ȡ�õ�����ʱ�ļ��Ĵ���·��
        String path = servlet.getServletContext().getRealPath( "/upload" );
       
        // ����ͻ�����
        if(!dao.saveExcelGroupcustomerInfo(bean, path)){
            return forwardErrorPage( mapping, request, "error" );
        }
        // �ɹ�ת������ҳ��        
        return forwardInfoPage( mapping, request, "150101up" );
    }
    
    
    /**
     * ��ʾ��ѯ�ͻ�����ҳ��
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
        	
            // ȡ�õ�¼��Ա����Ϣ
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
            // ʡ��ά
        	if(UserInfo.PROVINCE_MUSER_TYPE.equals(userinfo.getType())) {
        		// ȡ�����е������б�
                List regionList = dao.getRegionList();
                request.setAttribute( "regionlist", regionList );
        	}
            
            request.getSession().setAttribute( "type", "2" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "��ʾ�ͻ����ϲ�ѯ����ҳ�����:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
    
    /**
     * ������ѯ�ͻ�����
     * */
    public ActionForward doQuery( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        GroupCustomerDao dao = new GroupCustomerDao();
        GroupCustomerBean bean = (GroupCustomerBean)form;
        try{  
            // ȡ�õ�¼��Ա����Ϣ
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        	// ȡ�����е������б�
            List lcustomer = dao.queryGroupCustomer(bean, userinfo, request.getSession());
            request.getSession().setAttribute( "customer", lcustomer );
            request.getSession().setAttribute( "type", "3" );
            this.setPageReset(request);
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "��ʾ�ͻ����ϲ�ѯ����ҳ�����:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
    /**
     * ��ʾ�ͻ�������Ϣ
     * */
    public ActionForward showOneCustomer( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        GroupCustomerDao dao = new GroupCustomerDao();
        
        // ȡ�ÿͻ�ID
        String id = request.getParameter("id");
        
        try{
        	// ȡ�����е������б�
            GroupCustomerBean customerinfo = dao.getOneCustomer(id);
            request.getSession().setAttribute( "customeBean", customerinfo );
            request.getSession().setAttribute( "type", "4" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "��ʾ�ͻ����ϲ�ѯ����ҳ�����:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
    /**
     * ����
     * */
    public ActionForward doBack( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){        
        try{
        	String querysql = String.valueOf(request.getSession().getAttribute("querysql"));
        	// ȡ����session�е�sql��䣬ת�����ҳ��
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
            logger.error( "��ʾ�ͻ����ϲ�ѯ����ҳ�����:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
    /**
     * ��ʾ�޸Ŀͻ�������Ϣ
     * */
    public ActionForward showUpCustomer( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        GroupCustomerDao dao = new GroupCustomerDao();
        
        // ȡ�ÿͻ�ID
        String id = request.getParameter("id");
        
        try{
        	// ȡ���޸ĵĿͻ���ϸ����
            GroupCustomerBean customerinfo = dao.getOneCustomer(id);
            
           
            
            // ���ͻ����Ϸ��뵽form��
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
            logger.error( "��ʾ�ͻ����ϲ�ѯ����ҳ�����:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
    /**
     * �����޸ĵĿͻ�������Ϣ
     * */
    public ActionForward upCustomer( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        GroupCustomerDao dao = new GroupCustomerDao();
        
        // ȡ���޹��Ŀͻ�����form
        GroupCustomerBean hform = (GroupCustomerBean)form;
        
        
        try{
        	// ִ�и���
            if(!dao.updateCustomer(hform)) {
            	return forwardErrorPage( mapping, request, "error" );
            }
             
            return forwardInfoPage( mapping, request, "150102" );
        }
        catch( Exception e ){
            logger.error( "��ʾ�ͻ����ϲ�ѯ����ҳ�����:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
    /**
     * ɾ�������ͻ�������Ϣ
     * */
    public ActionForward delCustomer( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        GroupCustomerDao dao = new GroupCustomerDao();
        
        // ȡ��Ҫɾ���Ŀͻ�����id
        String id = request.getParameter("id");
        
        
        try{
        	// ִ�и���
            if(!dao.delCustomer(id)) {
            	return forwardErrorPage( mapping, request, "error" );
            }
             
            return forwardInfoPage( mapping, request, "150102d" );
        }
        catch( Exception e ){
            logger.error( "��ʾ�ͻ����ϲ�ѯ����ҳ�����:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
    
    /**
     * ����ɾ���ͻ�������Ϣ
     * */
    public ActionForward delCustomers( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        GroupCustomerDao dao = new GroupCustomerDao();
        
        // ȡ��Ҫɾ���Ŀͻ�����id
        String[] id = request.getParameterValues("ifCheck");
        
        
        try{
        	// ִ��ɾ��
            if(!dao.delCustomers(id)) {
            	return forwardErrorPage( mapping, request, "error" );
            }
             
            return forwardInfoPage( mapping, request, "150102d" );
        }
        catch( Exception e ){
            logger.error( "��ʾ�ͻ����ϲ�ѯ����ҳ�����:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
    /**
     * �����ɵ���Ϣһ����
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
            logger.info( " ����dao" );
            List list = ( List )request.getSession().getAttribute( "customer" );
            logger.info( "�õ�list" );
            ExportGroupCustomerDao exdao = new ExportGroupCustomerDao();
            if( exdao.exportGroupCustomerResult( list, response ) ){
                logger.info( "���excel�ɹ�" );
            }
            return null;
        }
        catch( Exception e ){
            logger.error( "�����ɵ���Ϣһ��������쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
}