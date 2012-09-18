package com.cabletech.linepatrol.material.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.linepatrol.material.service.MaterialYearStatBo;

/**
 * 材料年统计
 * @author fjj
 *
 */
public class MaterialYearStatAction extends BaseInfoBaseDispatchAction{
	private static Logger logger = Logger.getLogger(MaterialYearStatAction.class.getName());

	private MaterialYearStatBo getMaterialYearStatService(){
		WebApplicationContext ctx = getWebApplicationContext();
		return (MaterialYearStatBo)ctx.getBean("materialYearStatBo");
	}
	
	/**
	 * 转到年统计页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward materialStateForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String deptype = user.getDeptype();
		if(deptype.equals("1")){//移动
			List cons = getMaterialYearStatService().getConsByDeptId(user);
			request.setAttribute("cons",cons);
		}
		return mapping.findForward("materialStateYearForm");
	}


	/**
	 * 查询年统计
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward materialYearStat(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String contraid = request.getParameter("contraid");
		String year = request.getParameter("year");
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String deptype = user.getDeptype();
		if(deptype.equals("2")){
			contraid = user.getDeptID();
		}
		List list = getMaterialYearStatService().statYearMT(contraid,year);
		request.getSession().setAttribute("mtstatyear", list);
		return mapping.findForward("materialStateYearList");
	}





	/**
	 * 执行导出Excel
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportYearStatList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = (List) request.getSession().getAttribute("mtstatyear");
		getMaterialYearStatService().exportStorage(list, response);
		return null;
	}

}
