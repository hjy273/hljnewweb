package com.cabletech.bj.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.bj.services.OnLineBO;
import com.cabletech.commons.web.ClientException;

/**
 * ����Ѳ��Ա
 * @author Administrator
 *
 */
public class OnLineAction extends BaseInfoBaseDispatchAction {
	/**
	 *  ����Ѳ��Ա
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward loadTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try{
			UserInfo user = (UserInfo)request.getSession().getAttribute("LOGIN_USER");
			OnLineBO bo = new OnLineBO();
			String jsonOnlineman = bo.getOnlinePatrolman(user);
			response.setContentType("text/html; charset=GBK");
			PrintWriter out = response.getWriter();
			out.println(jsonOnlineman);
			out.flush();
		}catch(Exception e){
			System.out.println("��ȡ����Ѳ��Ա�����쳣:"+e.getMessage());
			e.getStackTrace();
		}
		return null;
	}


}
