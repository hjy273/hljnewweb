package com.cabletech.temppoint.acton;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.temppoint.beans.TemppointBean;
import com.cabletech.temppoint.dao.TemppointDao;
import com.cabletech.watchinfo.templates.WatchDetailTemplate;

public class TemppointAction extends BaseDispatchAction {
	private static Logger logger = Logger.getLogger(TemppointAction.class
			.getName());

	/**
	 * 导入单模板下载
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward downloadTemplet(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		response.reset();
		response.setContentType("application/vnd.ms-excel");
		try {
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String("临时点信息导入模板.xls".getBytes(), "iso-8859-1"));
			OutputStream out = response.getOutputStream();

			WatchDetailTemplate template = new WatchDetailTemplate(servlet
					.getServletContext().getRealPath("/upload")
					+ "/temppointtemplate.xls");
			template.write(out);

		} catch (Exception e) {
			logger.warn("下载临时点导入模板出错：" + e.getMessage());
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 转向成功页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showTemppointAdd(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// 成功转向增加页面
		return mapping.findForward("success");
	}

	/**
	 * 导入客户资料数据处理
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward upLoadTemppoint(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		TemppointDao dao = new TemppointDao();
		TemppointBean bean = (TemppointBean) form;

		// 判断导入文件类型
		String filename = bean.getFile().getFileName();

		logger.info(filename);

		if (filename.equals("") || filename == null) {
			return forwardErrorPage(mapping, request, "fileerror");
		}
		if (!filename.substring(filename.length() - 3, filename.length())
				.equals("xls")) {
			return forwardErrorPage(mapping, request, "structerror");
		}

		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");

		// 取得导入临时文件的存入路径
		String path = servlet.getServletContext().getRealPath("/upload");

		// 保存客户资料
		if (!dao.saveTemppointData(bean, path, userinfo.getRegionID())) {
			return forwardErrorPage(mapping, request, "error");
		}
		// 成功转向增加页面
		return forwardInfoPage(mapping, request, "101101");
	}
}
