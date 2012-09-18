package com.cabletech.groupcustomer.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.groupcustomer.bean.GroupCustomerBean;
import com.cabletech.groupcustomer.dao.ExportGroupCustomerDao;
import com.cabletech.groupcustomer.dao.GroupCustomerDao;
import com.cabletech.groupcustomer.dao.GroupCustomerParserDao;
import com.cabletech.partmanage.action.PartRequisitionAction;

public class GroupCustomerParserAction extends BaseDispatchAction{
	
    private static Logger logger = Logger.getLogger( PartRequisitionAction.class.
            getName() );

    /**
     * ��ʾ���ſͻ�������ѯ����ҳ��
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     */
    public ActionForward showQueryCustomerParser( ActionMapping mapping, ActionForm form,
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
            request.getSession().setAttribute( "type", "1" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "��ʾ���ſͻ�������ѯ����ҳ�����:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }

    /**
     * ִ�м��ſͻ�����
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doCustomerParser( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
    	GroupCustomerParserDao dao = new GroupCustomerParserDao();
    	// ȡ�õ�¼��Ա����Ϣ
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        try{
        	// ȡ��Ҫ��ѯ����������
        	GroupCustomerBean bean = (GroupCustomerBean)form;;
        	// ȡ�÷����Ŀͻ���Ϣ
        	List customerDataList = dao.getCustomerParserData(bean, userinfo);
        	
        	// ȡ�ò�ѯ������Ӧ���ܿͻ���
        	int customerCountNum = dao.getCustomerParserCount(bean, userinfo);
        	
        	// ���㸲����    
        	String parserpercent ;
        	if(customerCountNum != 0) {
        		parserpercent = ((int)(customerDataList.size()/(customerCountNum*1.0)*100)) + "%";    
        	} else {
        		parserpercent = "0%";     
        	}
        	
        	// ��ѯ����
        	request.getSession().setAttribute("querybean", bean);
        	
        	// ���������Ŀͻ���Ϣ
        	request.getSession().setAttribute( "parserlist", customerDataList );
        	// ���ǿͻ�����
        	request.getSession().setAttribute("parsernum", customerDataList.size()+"");
        	// �ͻ�����
        	request.getSession().setAttribute("customernum", customerCountNum+"");
        	// ������
        	request.getSession().setAttribute("parserpercent", parserpercent);
            request.getSession().setAttribute( "type", "2" );
            this.setPageReset(request);
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "ִ�м��ſͻ���������:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
    /**
     * �����ͻ�������Ϣһ����
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     */
    public ActionForward exportGroupCustomerParserResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            logger.info( " ����dao" );
            List list = ( List )request.getSession().getAttribute( "parserlist" );
            String customerNumStr = (String)request.getSession().getAttribute("customernum");
            String parserpercent = (String)request.getSession().getAttribute("parserpercent");
            
            //  ��ѯ����
            GroupCustomerBean bean = (GroupCustomerBean)request.getSession().getAttribute("querybean");
            
            logger.info( "�õ�list" );
            ExportGroupCustomerDao exdao = new ExportGroupCustomerDao();
            if( exdao.exportGroupCustomerParserResult( list, response, customerNumStr, parserpercent, bean ) ){
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
