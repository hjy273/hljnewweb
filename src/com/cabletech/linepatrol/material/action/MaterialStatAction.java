package com.cabletech.linepatrol.material.action;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.linepatrol.commons.services.ContractorBO;
import com.cabletech.linepatrol.material.beans.MaterialStatBean;
import com.cabletech.linepatrol.material.service.MaterialStatBo;
import com.cabletech.linepatrol.material.templates.MaterialStatTemplate;

/**
 * 材料月统计
 * 
 * @author cable
 * 
 */
public class MaterialStatAction extends BaseInfoBaseDispatchAction {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private WebApplicationContext ctx;

	/**
	 * 转到月统计页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward materialStatForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		ctx = getWebApplicationContext();
		String deptype = user.getDeptype();
		if (deptype.equals("1")) {// 移动
			ContractorBO bo = (ContractorBO) ctx.getBean("contractorBo");
			String condition = " and c.regionid='" + user.getRegionID() + "' ";
			List list = bo.getContractorList(condition);
			request.getSession().setAttribute("contractor_list", list);
			logger.info(list.size() + "=========================");
		}
		request.getSession().removeAttribute("result_map");
		return mapping.findForward("query_material_stat");
	}

	/**
	 * 转到月统计列表页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward statMaterial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ctx = getWebApplicationContext();
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		MaterialStatBean bean = (MaterialStatBean) form;
		String []quarters=request.getParameterValues("quarters");
		if(quarters!=null){
			String quarter=quarters[0];
			for(int i=1;i<quarters.length;i++){
				quarter+=","+quarters[i];
			}
			bean.setQuarter(quarter);
		}
		MaterialStatBo bo = (MaterialStatBo) ctx.getBean("materialStatBo");
		Map materialStatResultMap = bo.materialStat(user, bean);
		request.getSession().setAttribute("result_map", materialStatResultMap);
		return mapping.findForward("list_material_stat");
	}

	/**
	 * 导出材料信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportMaterialStat(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map materialStatResultMap = (Map) request.getSession().getAttribute(
				"result_map");
		MaterialStatBo bo = (MaterialStatBo) ctx.getBean("materialStatBo");
		OutputStream out;
		out = response.getOutputStream();
		initResponse(response, "材料使用统计信息一览表.xls");
		MaterialStatTemplate template = bo
				.exportMaterialStatResult(materialStatResultMap);
		template.write(out);
		return null;
	}

	private void initResponse(HttpServletResponse response, String outfilename)
			throws Exception {
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(outfilename.getBytes(), "iso-8859-1"));

	}
}
