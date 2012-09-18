package com.cabletech.linepatrol.material.action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.linepatrol.material.beans.MaterialStockRecordBean;
import com.cabletech.linepatrol.material.service.MaterialStockBo;
import com.cabletech.linepatrol.material.templates.MaterialStockTmplate;

/**
 * 材料库存管理
 * 
 * @author fjj
 * 
 */
public class MaterialStockAction extends BaseInfoBaseDispatchAction {
	private static Logger logger = Logger.getLogger(MaterialStockAction.class
			.getName());

	private MaterialStockBo getMaterialStockService() {
		WebApplicationContext ctx = getWebApplicationContext();
		return (MaterialStockBo) ctx.getBean("materialStockBo");
	}

	/**
	 * 转到按代维查询材料库存管理页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryMaterialStrockByConForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MaterialStockBo materialStockBo = getMaterialStockService();
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String deptype = user.getDeptype();
		String deptid = user.getDeptID();
		List list = new ArrayList();
		if (deptype.equals("1")) {// 移动
			List cons = materialStockBo.getConsByDeptId(user);
			list = materialStockBo.getAllMTAddr();
			request.setAttribute("cons", cons);
		}
		if (deptype.equals("2")) {// 代维
			list = materialStockBo.getMTAddrByConId(deptid);
		}
		request.setAttribute("address", list);
		return mapping.findForward("queryMaterialStockByConForm");
	}

	/**
	 * 转到按材料查询材料库存管理页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryMaterialStrockByMTForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String regionid = user.getRegionid();
		List types = getMaterialStockService().getMTTypes(regionid);
		request.setAttribute("types", types);
		return mapping.findForward("queryMaterialStockByMTForm");
	}

	/**
	 * 转到查询材料出入库记录表单页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryMaterialStockRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MaterialStockBo materialStockBo = getMaterialStockService();
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String regionid = user.getRegionid();
		List types = materialStockBo.getMTTypes(regionid);
		String deptype = user.getDeptype();
		String deptid = user.getDeptID();
		List list = new ArrayList();
		if (deptype.equals("1")) {// 移动
			List cons = materialStockBo.getConsByDeptId(user);
			list = materialStockBo.getAllMTAddr();
			request.getSession().setAttribute("contractorList", cons);
		}
		if (deptype.equals("2")) {// 代维
			list = materialStockBo.getMTAddrByConId(deptid);
		}
		request.getSession().setAttribute("addressList", list);
		request.getSession().setAttribute("typeList", types);
		request.getSession().removeAttribute("RECORD_DATA_MAP");
		return mapping.findForward("material_stock_record_query");
	}

	/**
	 * 执行查询材料出入库信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward listMaterialStockRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		super.setPageReset(request);
		MaterialStockBo materialStockBo = getMaterialStockService();
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		MaterialStockRecordBean bean = (MaterialStockRecordBean) form;
		Map map = materialStockBo.getMaterialStockRecordMap(bean, user);
		request.getSession().setAttribute("RECORD_DATA_MAP", map);
		request.setAttribute("use_type", bean.getUseType());
		return mapping.findForward("material_stock_record_list");
	}

	/**
	 * 执行导出材料出入库信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportMaterialStockRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map map = (Map) request.getSession().getAttribute("RECORD_DATA_MAP");
		MaterialStockBo materialStockBo = getMaterialStockService();
		String useType = request.getParameter("use_type");
		OutputStream out;
		out = response.getOutputStream();
		initResponse(response, "材料出入库信息一览表.xls");
		MaterialStockTmplate template = materialStockBo
				.exportMaterialStockRecord(map, useType);
		template.write(out);
		return null;
	}

	/**
	 * ajax 根据代维查询地址
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getAddressByCon(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/json; charset=GBK");
		String id = request.getParameter("contractorid");
		List address = getMaterialStockService().getMTAddrByConId(id);
		JSONArray ja = JSONArray.fromObject(address);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(ja.toString());
			out.flush();
			logger.info(ja.toString() + "名称");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return null;
	}

	/**
	 * ajax 根据材料类型查询材料规格
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getModelByType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json; charset=GBK");
		String typeid = request.getParameter("typeid");
		List types = getMaterialStockService().getModelByType(typeid);
		JSONArray ja = JSONArray.fromObject(types);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(ja.toString());
			out.flush();
			logger.info(ja.toString() + "名称");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return null;
	}

	/**
	 * ajax 根据材料规格查询材料
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getMTByModel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json; charset=GBK");
		String modelid = request.getParameter("modelid");
		List materials = getMaterialStockService().getMTByModel(modelid);
		JSONArray ja = JSONArray.fromObject(materials);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(ja.toString());
			out.flush();
			logger.info(ja.toString() + "材料名称");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return null;
	}

	/**
	 * 根据代维查询条件查询库存信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getMarterialStocksByCon(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String contraid = request.getParameter("contractorid");
		String addrID = request.getParameter("addrID");
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String deptype = user.getDeptype();
		if (deptype.equals("2")) {
			contraid = user.getDeptID();
		}
		List list = getMaterialStockService().getMarterialStocksByCon(deptype,
				contraid, addrID);
		request.getSession().setAttribute("marterialStocksCon", list);

		return mapping.findForward("marterialStocksCon");
	}

	/**
	 * 根据材料查询条件查询库存信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getMarterialStocksByMT(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String typeid = request.getParameter("typeid");
		String modelid = request.getParameter("modelid");
		String mtid = request.getParameter("mtid");
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		List list = getMaterialStockService().getMarterialStocksByMT(user,
				typeid, modelid, mtid);
		request.getSession().setAttribute("marterialStocksMT", list);
		return mapping.findForward("marterialStocksMT");
	}

	/**
	 * 执行导出Excel
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportStoksList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String act = request.getParameter("p");
		String newstock = request.getParameter("new");
		String oldstock = request.getParameter("old");
		String sumstock = request.getParameter("sum");
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String deptype = user.getDeptype();
		List list = null;
		if (act != null && act.equals("con")) {
			list = (List) request.getSession().getAttribute(
					"marterialStocksCon");
		}
		if (act != null && act.equals("mt")) {
			list = (List) request.getSession()
					.getAttribute("marterialStocksMT");
		}
		getMaterialStockService().exportStorage(list, deptype, newstock,
				oldstock, sumstock, response);
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
