package com.cabletech.statistics.action;

import javax.servlet.http.*;

import org.apache.log4j.Logger;
import org.apache.struts.action.*;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.web.*;
import com.cabletech.statistics.beans.*;

public class ApproveRateAction extends StatisticsBaseDispatchAction{
    public ApproveRateAction(){
    }
    private Logger logger = Logger.getLogger("ApproveRateAction") ;

    /**
     * 查询计划审批通过率
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward getApproveRate( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
    	UserInfo userinfo = (UserInfo)request.getSession().getAttribute("LOGIN_USER");
    	
        ApproveRateBean bean = ( ApproveRateBean )form;
        if(userinfo.getType().equals("22")){
        	bean.setRegionid(userinfo.getRegionid());
        	bean.setContractorid(userinfo.getDeptID());
        	
        }
        logger.info("regionid"+bean.getRegionid());
        request.getParameter("regionid");
        logger.info("RR="+bean.getRegionid().substring(2,5));
        if(bean.getRegionid().substring(2,6).equals("0000")){
        	bean.setRegionid(null);
        }
        //bean.setContractorid(request.getParameter("workID"));
        ApproveRateBean resultBean = super.getService().getApproveRate( bean );
        request.getSession().setAttribute( "queryresult", resultBean );

        return mapping.findForward( "queryApproveRate" );

    }

}
