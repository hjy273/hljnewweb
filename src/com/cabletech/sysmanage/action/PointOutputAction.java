package com.cabletech.sysmanage.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.config.UserType;
import com.cabletech.power.CheckPower;
import com.cabletech.sysmanage.beans.PointOutputBean;
import com.cabletech.sysmanage.services.PointOutputBO;

public class PointOutputAction  extends BaseInfoBaseDispatchAction {
	private Logger logger = Logger.getLogger( this.getClass().getName() );
	private PointOutputBO bo = null;
    /**
     * ʡ�ƶ������ƶ��û�����Ѳ��㼰������Ϣ����
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward queryPointOutput( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
		// �ж��Ƿ��е���Ѳ����Ȩ��
		if (!CheckPower.checkPower(request.getSession(), "72801")) {
			return mapping.findForward("powererror");
		}
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        bo = new PointOutputBO();
        try {
        	if (UserType.PROVINCE.equals(userInfo.getType())) { // ʡ�ƶ��û�
			  List regionList = bo.getRegionList();
			  request.getSession().setAttribute("regioninfo", regionList);
        	}
			return mapping.findForward("queryPointOutput");
		} catch (Exception e) {
			logger.error("��ѯ������Ϣʱ����:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
    }
    
    
    //����Ѳ��㼰������Ϣ
    public ActionForward outPutPointInfo( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response){
    	PointOutputBean bean = ( PointOutputBean )form;
    	//String regionID = bean.getRegionID();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		bo = new PointOutputBO();
		try {
			String sql = bo.getPatrolPointSql(userInfo,bean);
			//List list = bo.getRegionList();
			super.getService().exportPatrolPointInfo(sql, response);
			logger.info("���excel�ɹ�");
			return null;
		} catch (Exception e) {
			logger.error("����Ѳ�����Ϣ����:" + e.getMessage());
			e.printStackTrace();
			return forwardErrorPage(mapping, request, "error");
		}
//        try {
//			bo.outputPointInfo(userInfo);
//        	return forwardInfoPage( mapping, request, "72801ok" );
//		} catch (Exception e) {
//			logger.error("����Ѳ�����Ϣ����:" + e.getMessage());
//			return forwardErrorPage(mapping, request, "72801error");
//		}
    }
}
