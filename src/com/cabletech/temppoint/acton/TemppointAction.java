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
	 * ���뵥ģ������
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
					+ new String("��ʱ����Ϣ����ģ��.xls".getBytes(), "iso-8859-1"));
			OutputStream out = response.getOutputStream();

			WatchDetailTemplate template = new WatchDetailTemplate(servlet
					.getServletContext().getRealPath("/upload")
					+ "/temppointtemplate.xls");
			template.write(out);

		} catch (Exception e) {
			logger.warn("������ʱ�㵼��ģ�����" + e.getMessage());
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * ת��ɹ�ҳ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showTemppointAdd(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// �ɹ�ת������ҳ��
		return mapping.findForward("success");
	}

	/**
	 * ����ͻ��������ݴ���
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

		// �жϵ����ļ�����
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

		// ȡ�õ�����ʱ�ļ��Ĵ���·��
		String path = servlet.getServletContext().getRealPath("/upload");

		// ����ͻ�����
		if (!dao.saveTemppointData(bean, path, userinfo.getRegionID())) {
			return forwardErrorPage(mapping, request, "error");
		}
		// �ɹ�ת������ҳ��
		return forwardInfoPage(mapping, request, "101101");
	}
}
