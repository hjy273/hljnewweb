package com.cabletech.pipemanage.action;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.pipemanage.bean.PipeSegmentBean;
import com.cabletech.pipemanage.dao.ExportDao;
import com.cabletech.pipemanage.dao.PipeSegmentDao;
import com.cabletech.watchinfo.templates.WatchDetailTemplate;

/**
 * 管道段处理Action
 * 
 * @author Ding Hui Zhen date:2009-08-19
 */
public class PipeSegmentAction extends BaseInfoBaseDispatchAction {

	private static Logger logger = Logger.getLogger(PipeSegmentAction.class
			.getName());

	/**
	 * 查询管道段表单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryPipeSegmentForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String regionid = userinfo.getRegionID();
		PipeSegmentDao dao = new PipeSegmentDao();
		List contractorlist = dao.getContractor(regionid);
		List pointList = dao.getPointInfo(regionid);
		request.setAttribute("contractorlist", contractorlist);
		request.setAttribute("pointList", pointList);
		return mapping.findForward("queryPipeSegmentForm");
	}

	/**
	 * 增加管道段表单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addPipeSegmentForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String regionid = userinfo.getRegionID();
		PipeSegmentDao dao = new PipeSegmentDao();
		List contractorlist = dao.getContractor(regionid);
		List pointList = dao.getPointInfo(regionid);
		request.setAttribute("contractorlist", contractorlist);
		request.setAttribute("pointList", pointList);
		return mapping.findForward("addPipeSegmentForm");
	}

	/**
	 * 增加管道段信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addPipeSegment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PipeSegmentBean bean = (PipeSegmentBean) form;
		PipeSegmentDao dao = new PipeSegmentDao();

		bean.setId(this.getDbService().getSeq("pipeinfo", 20));
		boolean isSuccess = dao.addPipeSegment(bean);
		if (isSuccess) {
			return forwardInfoPage(mapping, request, "90806");
		} else {
			return forwardErrorPage(mapping, request, "error");
		}
	}

	/**
	 * 查询管道段
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryPipeSegment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Object isReturn = request.getParameter("isReturn");
		PipeSegmentBean bean = (PipeSegmentBean) form;
		request.getSession().setAttribute("S_BACK_URL",null); //
        request.getSession().setAttribute("theQueryBean", bean);//
		PipeSegmentDao dao = new PipeSegmentDao();
		List pipeSegmentList;
		if (isReturn == null) {
			PipeSegmentBean querybean = (PipeSegmentBean) request.getSession()
					.getAttribute("querybean");
			pipeSegmentList = dao.getPipeSegmentBean(querybean);
		}else{
			pipeSegmentList = dao.getPipeSegmentBean(bean);
			request.getSession().setAttribute("querybean", bean);
		}
		request.getSession().setAttribute("pipeSegmentList", pipeSegmentList);
		this.setPageReset(request);
		return mapping.findForward("pipeSegmentList");
	}

	/**
	 * 删除管道段
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deletePipeSegment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		PipeSegmentDao dao = new PipeSegmentDao();
		boolean isSuccess = dao.deletePipeSegmentById(id);
		if (isSuccess) {
			 String strQueryString = getTotalQueryString("method=queryPipeSegment&pipename=",(PipeSegmentBean)request.getSession().getAttribute("theQueryBean"));
			 Object[] args = getURLArgs("/WebApp/PipeSegmentAction.do",strQueryString,(String)request.getSession().getAttribute( "S_BACK_URL" ));
			 return forwardInfoPage( mapping, request, "90808",null,args);
		} else {
			return forwardErrorPage(mapping, request, "error");
		}
	}

	/**
	 * 编辑管道段表单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editPipeSegmentForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PipeSegmentBean bean = (PipeSegmentBean) form;
		String id = request.getParameter("id");
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String regionid = userinfo.getRegionID();
		PipeSegmentDao dao = new PipeSegmentDao();
		List contractorlist = dao.getContractor(regionid);
		List pointList = dao.getPointInfo(regionid);
		request.setAttribute("contractorlist", contractorlist);
		request.setAttribute("pointList", pointList);
		bean = dao.getPipeSegmentById(id, bean);
		return mapping.findForward("editPipeSegmentForm");
	}

	/**
	 * 修改/更新管道段信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updatePipeSegment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PipeSegmentBean bean = (PipeSegmentBean) form;
		PipeSegmentDao dao = new PipeSegmentDao();
		boolean isSuccess = dao.updatePipeSegment(bean);
		if (isSuccess) {
			 String strQueryString = getTotalQueryString("method=queryPipeSegment&pipename=",(PipeSegmentBean)request.getSession().getAttribute("theQueryBean"));
			 Object[] args = getURLArgs("/WebApp/PipeSegmentAction.do",strQueryString,(String)request.getSession().getAttribute( "S_BACK_URL" ));
			 return forwardInfoPage( mapping, request, "90807",null,args);
		} else {
			return forwardErrorPage(mapping, request, "error");
		}
	}
	
	public String getTotalQueryString(String queryString,PipeSegmentBean bean){
    	if (bean.getPipename()!= null){
    		queryString = queryString + bean.getPipename();
    	}
    	if (bean.getPipeno()!= null){
    		queryString = queryString + "&pipeno=" + bean.getPipeno();
    	}
    	if (bean.getId() != null){
    		queryString = queryString + "&id=" + bean.getId();
    	}
    	if (bean.getPipetype()!= null){
    		queryString = queryString + "&pipetype=" + bean.getPipetype();
    	}
    	if (bean.getApoint()!= null){
    		queryString = queryString + "&aponit=" + bean.getApoint();
    	}
    	if (bean.getZpoint()!= null){
    		queryString = queryString + "&zponit=" + bean.getZpoint();
    	}
    	return queryString;
    }
	
	
	/**
	 * 导出管道段表单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportPipeSegmentResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PipeSegmentBean bean = (PipeSegmentBean) request.getSession()
				.getAttribute("querybean");
		try {
			logger.info(" 创建dao");
			PipeSegmentDao dao = new PipeSegmentDao();
			List list = dao.getExportInfo(bean);
			;
			logger.info("得到list");
			ExportDao exdao = new ExportDao();
			if (exdao.exportInfo(list, response)) {
				logger.info("输出excel成功");
			}
			return null;
		} catch (Exception e) {
			logger.error("导出管道段信息一览表出现异常:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
	}

	public ActionForward downloadTemplet(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		response.reset();
		response.setContentType("application/vnd.ms-excel");
		try {
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String("管道段信息导入模板.xls".getBytes(), "iso-8859-1"));
			OutputStream out = response.getOutputStream();

			WatchDetailTemplate template = new WatchDetailTemplate(servlet
					.getServletContext().getRealPath("/upload")
					+ "/pipeinfolist.xls");
			template.write(out);

		} catch (Exception e) {
			logger.warn("下载管道段导入模板出错：" + e.getMessage());
			e.printStackTrace();
		}
		return null;

	}

	public ActionForward upLoadShow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		PipeSegmentDao dao = new PipeSegmentDao();
		PipeSegmentBean bean = (PipeSegmentBean) form;

		// 判断导入文件类型
		String filename = bean.getFile().getFileName();
		if (filename.equals("") || filename == null) {
			return forwardErrorPage(mapping, request, "fileerror");
		}
		if (!filename.substring(filename.length() - 3, filename.length())
				.equals("xls")) {
			return forwardErrorPage(mapping, request, "structerror");
		}

		// 取得导入临时文件的存入路径
		String path = servlet.getServletContext().getRealPath("/upload");

		// 保存管道段信息
		if (!dao.saveExcelGroupcustomerInfo(bean, path)) {
			return forwardErrorPage(mapping, request, "error");
		}
		// 成功转向增加页面
		return forwardInfoPage(mapping, request, "90806up");
	}

}
