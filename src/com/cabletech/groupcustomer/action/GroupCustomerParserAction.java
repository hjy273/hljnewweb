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
     * 显示集团客户分析查询条件页面
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
            // 取得登录人员的信息
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
            // 省代维
        	if(UserInfo.PROVINCE_MUSER_TYPE.equals(userinfo.getType())) {
        		// 取得所有的区域列表
                List regionList = dao.getRegionList();
                request.setAttribute( "regionlist", regionList );
        	}
            request.getSession().setAttribute( "type", "1" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "显示集团客户分析查询条件页面出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }

    /**
     * 执行集团客户分析
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
    	// 取得登录人员的信息
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        try{
        	// 取得要查询分析的条件
        	GroupCustomerBean bean = (GroupCustomerBean)form;;
        	// 取得分析的客户信息
        	List customerDataList = dao.getCustomerParserData(bean, userinfo);
        	
        	// 取得查询条件对应的总客户数
        	int customerCountNum = dao.getCustomerParserCount(bean, userinfo);
        	
        	// 计算覆盖率    
        	String parserpercent ;
        	if(customerCountNum != 0) {
        		parserpercent = ((int)(customerDataList.size()/(customerCountNum*1.0)*100)) + "%";    
        	} else {
        		parserpercent = "0%";     
        	}
        	
        	// 查询条件
        	request.getSession().setAttribute("querybean", bean);
        	
        	// 放入分析完的客户信息
        	request.getSession().setAttribute( "parserlist", customerDataList );
        	// 覆盖客户总数
        	request.getSession().setAttribute("parsernum", customerDataList.size()+"");
        	// 客户总数
        	request.getSession().setAttribute("customernum", customerCountNum+"");
        	// 覆盖率
        	request.getSession().setAttribute("parserpercent", parserpercent);
            request.getSession().setAttribute( "type", "2" );
            this.setPageReset(request);
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "执行集团客户分析出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
    /**
     * 导出客户分析信息一览表
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
            logger.info( " 创建dao" );
            List list = ( List )request.getSession().getAttribute( "parserlist" );
            String customerNumStr = (String)request.getSession().getAttribute("customernum");
            String parserpercent = (String)request.getSession().getAttribute("parserpercent");
            
            //  查询条件
            GroupCustomerBean bean = (GroupCustomerBean)request.getSession().getAttribute("querybean");
            
            logger.info( "得到list" );
            ExportGroupCustomerDao exdao = new ExportGroupCustomerDao();
            if( exdao.exportGroupCustomerParserResult( list, response, customerNumStr, parserpercent, bean ) ){
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
