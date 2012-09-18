package com.cabletech.linepatrol.acceptance.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.linepatrol.acceptance.beans.PipesBean;
import com.cabletech.linepatrol.acceptance.service.AcceptancePipeStatManager;

/**
 * 验收交维管道统计Action类
 * @author liusq
 *
 */
public class AcceptancePipeStatAction extends BaseInfoBaseDispatchAction {
	private static final long serialVersionUID = -7832233877085229522L;

	/**
	 * 转向验收交维管道统计界面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardPipeStatPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.getSession().setAttribute("list", null);
		return mapping.findForward("forwardPipeStatPage");
	}
	
	/**
	 * 管道统计
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward pipeStatList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PipesBean bean = (PipesBean)form;
		WebApplicationContext ctx = getWebApplicationContext();
		AcceptancePipeStatManager am = (AcceptancePipeStatManager) ctx.getBean("acceptancePipeStatManager");
		List<Object> list = am.getPipeStatList(bean);
		request.getSession().setAttribute("list", list);
		return mapping.findForward("forwardPipeStatPage");
	}
}
