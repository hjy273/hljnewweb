package com.cabletech.linepatrol.material.action;

import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.linepatrol.material.beans.LinePatrolManagerBean;
import com.cabletech.linepatrol.material.domain.LinePatrolManager;
import com.cabletech.linepatrol.material.service.LinePatrolManagerBo;
import com.cabletech.linepatrol.material.service.MaterialImportBo;

public class LinePatrolManagerAction extends BaseInfoBaseDispatchAction {

	private static Logger logger = Logger
			.getLogger(LinePatrolManagerAction.class.getName());

	/**
	 * 获得linePatrolManagerBo对象
	 */
	private LinePatrolManagerBo getLinePatrolManagerService() {
		WebApplicationContext ctx = getWebApplicationContext();
		return (LinePatrolManagerBo) ctx.getBean("linePatrolManagerBo");
	}

	/**
	 * 添加材料申请信息加载页面(监理单位信息、材料信息、存放地点信息)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addLinePatrolRe(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		LinePatrolManagerBo linePatrolManagerBo = getLinePatrolManagerService();

		request.setAttribute("username", userinfo.getUserName());
		request.setAttribute("userid", userinfo.getUserID());
		request.setAttribute("deptName", userinfo.getDeptName());

		// 获得服务器的当前时间
		Date nowDate = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = df.format(nowDate);
		request.setAttribute("date", date);

		// 获得监理单位
		// List contractList = linePatrolManagerBo.getContractList();
		// request.setAttribute("contractList", contractList);

		// 获得材料信息
		List partList = linePatrolManagerBo.getLinePatrolList();
		request.setAttribute("partList", partList);

		// 获得存放地点信息
		List addresList = linePatrolManagerBo.getAddressList(userinfo
				.getDeptID());
		request.setAttribute("addresList", addresList);

		logger.info("userinfo:" + userinfo);
		logger.info("data:" + date);
		// logger.info("contractList:" + contractList);
		logger.info("partList:" + partList);
		logger.info("addressList:" + addresList);

		request.getSession().setAttribute("data", null);

		return mapping.findForward("addLinePatrolRe");
	}

	/**
	 * 添加材料申请信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addLinePatrolInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userids = request.getParameter("userids");
		String mobiles = request.getParameter("mobiles");
		LinePatrolManagerBean bean = (LinePatrolManagerBean) form;
		LinePatrolManager linePatrolManager = new LinePatrolManager();
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String id = this.getDbService().getSeq("LINEPATROL_MT_NEW", 10);
		bean.setId(id);
		bean.setCerator(userinfo.getUserID());
		BeanUtil.objectCopy(bean, linePatrolManager);
		LinePatrolManagerBo linePatrolManagerBo = getLinePatrolManagerService();
		if (linePatrolManagerBo.addLinePatrolInfo(linePatrolManager, userinfo,
				userids, mobiles)) {
			return forwardInfoPage(mapping, request, "80101");
		} else {
			return forwardErrorPage(mapping, request, "error");
		}
	}

	/**
	 * 跳转到材料申请查询页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryLinePatrolRe(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("queryLinePatrolRe");
	}

	/**
	 * 查询材料申请信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryLinePatrolInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LinePatrolManagerBean bean = (LinePatrolManagerBean) form;
		LinePatrolManager linePatrolManager = new LinePatrolManager();
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		request.getSession().setAttribute("S_BACK_URL", null); //
		request.getSession().setAttribute("theQueryBean", bean);//
		BeanUtil.objectCopy(bean, linePatrolManager);
		LinePatrolManagerBo linePatrolManagerBo = getLinePatrolManagerService();
		List linePatrolList = linePatrolManagerBo.queryLinePatrol(
				linePatrolManager, userinfo.getUserID());
		request.getSession().setAttribute("linePatrolList", linePatrolList);
		request.setAttribute("type", userinfo.getDeptype());
		super.setPageReset(request);
		return mapping.findForward("queryLinePatrolInfo");
	}

	/**
	 * 根据材料申请单id查询材料申请单详细信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewLinePatrolByID(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LinePatrolManagerBean bean = (LinePatrolManagerBean) form;
		String id = request.getParameter("id");
		LinePatrolManagerBo linePatrolManagerBo = getLinePatrolManagerService();
		bean = linePatrolManagerBo.viewLinePatrolById(id, bean);
		request.setAttribute("bean", bean);
		LinePatrolManagerBean lbean = new LinePatrolManagerBean();
		lbean = linePatrolManagerBo.getLineAssessDept1(lbean, id);
		request.setAttribute("lbean", lbean);
		return mapping.findForward("viewLinePatrolByID");
	}

	/**
	 * 根据id删除材料申请单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteLinePatrol(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		LinePatrolManagerBo linePatrolManagerBo = getLinePatrolManagerService();
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		if (linePatrolManagerBo.delLinePatrolById(id)) {
			return forwardInfoPageWithUrl(mapping, request, "801del", url);
		} else {
			return forwardErrorPageWithUrl(mapping, request, "error", url);
		}
	}

	/**
	 * 编辑材料申请单信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editLinePatrol(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LinePatrolManagerBean bean = (LinePatrolManagerBean) form;
		String id = request.getParameter("id");
		LinePatrolManagerBo linePatrolManagerBo = getLinePatrolManagerService();
		bean = linePatrolManagerBo.getLinePatrolById(id, bean);
		request.setAttribute("bean", bean);
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");

		if (userinfo.getDeptype().equals("1")) { // 如果是移动公司是不允许添加材料申请的
			return forwardErrorPage(mapping, request, "partstockerror");
		}
		request.setAttribute("username", userinfo.getUserName());
		request.setAttribute("userid", userinfo.getUserID());
		request.setAttribute("deptName", userinfo.getDeptName());

		// 获得服务器的当前时间
		Date nowDate = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// DateFormat.getDateInstance(DateFormat.DATE_FIELD);
		String data = df.format(nowDate);
		request.setAttribute("date", data);

		// 获得监理单位
		// List contractList = linePatrolManagerBo.getContractList();
		// request.setAttribute("contractList", contractList);

		// 获得材料信息
		List partList = linePatrolManagerBo.getLinePatrolList();
		request.setAttribute("partList", partList);

		// 获得存放地点信息
		List addresList = linePatrolManagerBo.getAddressList(userinfo
				.getDeptID());
		request.setAttribute("addresList", addresList);

		List modellist = linePatrolManagerBo.getPatrolModellist();
		List baseList = linePatrolManagerBo.getPatorlBaselist();
		request.setAttribute("modellist", modellist);
		request.setAttribute("baseList", baseList);

		LinePatrolManagerBean lbean = new LinePatrolManagerBean();
		lbean = linePatrolManagerBo.getLineAssessDept1(lbean, id);
		request.setAttribute("lbean", lbean);

		request.getSession().setAttribute("data", null);
		
		return mapping.findForward("editLinePatrol");
	}

	/**
	 * 拼凑查询条件
	 * 
	 * @param queryString
	 * @param bean
	 * @return
	 */
	public String getTotalQueryString(String queryString,
			LinePatrolManagerBean bean) {
		if (bean.getType() != null) {
			queryString = queryString + bean.getType();
		}
		if (bean.getBegintime() != null) {
			queryString = queryString + "&getBegintime=" + bean.getBegintime();
		}
		if (bean.getEndtime() != null) {
			queryString = queryString + "&id=" + bean.getEndtime();
		}
		return queryString;
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modLinePatrolInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userids = request.getParameter("userids");
		String mobiles = request.getParameter("mobiles");
		LinePatrolManagerBean bean = (LinePatrolManagerBean) form;
		LinePatrolManagerBo linePatrolManagerBo = getLinePatrolManagerService();
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");

		bean.setCerator(userinfo.getUserID());
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		if (linePatrolManagerBo.modLinePatrolInfo(bean, userinfo, userids,
				mobiles)) {
			return forwardInfoPageWithUrl(mapping, request, "801mod", url);
		} else {
			return forwardErrorPageWithUrl(mapping, request, "error", url);
		}
	}

	/**
	 * 查询需审批的材料申请
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryLinePatorAssess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LinePatrolManagerBean bean = (LinePatrolManagerBean) form;
		LinePatrolManagerBo linePatrolManagerBo = getLinePatrolManagerService();
		request.getSession().setAttribute("S_BACK_URL", null); //
		request.getSession().setAttribute("theQueryBean", bean);//
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		request.setAttribute("type", userinfo.getDeptype());
		super.setPageReset(request);
		if (userinfo.getDeptype().equals("1")) {
			List assesslist = linePatrolManagerBo.getAssessByDep2(userinfo
					.getUserID());
			request.getSession().setAttribute("assesslist", assesslist);
			return mapping.findForward("linePatorAssess3List");
		} else if (userinfo.getDeptype().equals("3")) {
			List assesslist = linePatrolManagerBo.getAssessByDepart3(userinfo
					.getDeptID());
			request.getSession().setAttribute("assesslist", assesslist);
			return mapping.findForward("linePatorAssess3List");
		} else {
			return forwardErrorPage(mapping, request, "partstockerror");
		}

	}

	public ActionForward checkLinePatrolByCon(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LinePatrolManagerBean bean = (LinePatrolManagerBean) form;
		String id = request.getParameter("id");
		LinePatrolManagerBo linePatrolManagerBo = getLinePatrolManagerService();
		bean = linePatrolManagerBo.viewLinePatrolById(id, bean);

		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");

		request.setAttribute("username", userinfo.getUserName());
		request.setAttribute("userid", userinfo.getUserID());
		request.setAttribute("deptname", userinfo.getDeptName());
		// 获得服务器的当前时间
		Date nowDate = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// DateFormat.getDateInstance(DateFormat.DATE_FIELD);
		String data = df.format(nowDate);
		request.setAttribute("date", data);
		// if(userinfo.getDeptype().equals("1")){
		bean = linePatrolManagerBo.getLineAssess(bean, id);
		// }
		LinePatrolManagerBean lbean = new LinePatrolManagerBean();
		lbean = linePatrolManagerBo.getLineAssessDept1(lbean, id);
		request.setAttribute("lbean", lbean);
		request.setAttribute("bean", bean);
		request.setAttribute("type", userinfo.getDeptype());

		List users = linePatrolManagerBo.getUserInfos();
		request.setAttribute("users", users);
		return mapping.findForward("checkLinePatrolByCon");
	}

	/**
	 * 添加材料审核信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addLinePatrolAssessInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userids = request.getParameter("userids");
		System.out.println("userids============================== " + userids);

		LinePatrolManagerBean bean = (LinePatrolManagerBean) form;
		LinePatrolManagerBo linePatrolManagerBo = getLinePatrolManagerService();
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String id = this.getDbService().getSeq("LINEPATROL_MT_ASSESS", 10);
		bean.setId(id);
		// bean.setAssesor(userinfo.getUserID());
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		if (linePatrolManagerBo
				.addLinePatrolAssessInfo(bean, userinfo, userids)) {
			if ("1".equals(bean.getState())) {
				return forwardInfoPageWithUrl(mapping, request, "815add", url);
			} else {
				return forwardInfoPageWithUrl(mapping, request, "815add1", url);
			}
		} else {
			return forwardErrorPageWithUrl(mapping, request, "error", url);
		}
	}

	public ActionForward queryParolManager(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		request.setAttribute("type", userinfo.getDeptype());
		return mapping.findForward("queryParolManager");
	}

	public ActionForward queryParolManagerList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LinePatrolManagerBean bean = (LinePatrolManagerBean) form;
		LinePatrolManagerBo linePatrolManagerBo = getLinePatrolManagerService();
		request.getSession().setAttribute("S_BACK_URL", null); //
		request.getSession().setAttribute("theQueryBean", bean);//
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		request.setAttribute("type", "query");
		request.setAttribute("stype", userinfo.getDeptype());

		if (userinfo.getDeptype().equals("1")) {
			List assesslist = linePatrolManagerBo.getListByDepartFor1(bean);
			request.getSession().setAttribute("assesslist", assesslist);
			return mapping.findForward("linePatorAssess3List");
		} else if (userinfo.getDeptype().equals("3")) {
			List assesslist = linePatrolManagerBo.getListByDepart3(userinfo
					.getDeptID(), bean);
			request.getSession().setAttribute("assesslist", assesslist);
			return mapping.findForward("linePatorAssess3List");
		} else {
			return forwardErrorPage(mapping, request, "partstockerror");
		}
	}

	public ActionForward lookLinePatrolByCon(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LinePatrolManagerBean bean = (LinePatrolManagerBean) form;
		String id = request.getParameter("id");
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		LinePatrolManagerBo linePatrolManagerBo = getLinePatrolManagerService();
		bean = linePatrolManagerBo.viewLinePatrolById(id, bean);
		bean = linePatrolManagerBo.getLineAssess(bean, id);
		request.setAttribute("type", userinfo.getDeptype());
		// if(userinfo.getDeptype().equals("1")){
		LinePatrolManagerBean lbean = new LinePatrolManagerBean();
		lbean = linePatrolManagerBo.getLineAssessDept1(lbean, id);
		request.setAttribute("lbean", lbean);
		// }
		System.out.println("tttttttttttttttttttt   ");

		request.setAttribute("bean", bean);
		return mapping.findForward("lookLinePatrolByCon");
	}

	public ActionForward getModelById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/json; charset=GBK");
		String id = request.getParameter("id");
		LinePatrolManagerBo linePatrolManagerBo = getLinePatrolManagerService();
		List modellist = linePatrolManagerBo.getLinePatrolModelByID(id);
		logger.info("*********:" + modellist);
		JSONArray ja = JSONArray.fromObject(modellist);
		PrintWriter out = response.getWriter();
		out.write(ja.toString());
		out.flush();
		return null;
	}

	public ActionForward getPatorlBaseById(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/json; charset=GBK");
		String id = request.getParameter("id");
		LinePatrolManagerBo linePatrolManagerBo = getLinePatrolManagerService();
		List baselist = linePatrolManagerBo.getPatorlBaseById(id);
		JSONArray ja = JSONArray.fromObject(baselist);
		PrintWriter out = response.getWriter();
		out.write(ja.toString());
		out.flush();
		return null;
	}

	// 申请中导入材料申请
	public ActionForward importMaterial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		LinePatrolManagerBean applyBean = (LinePatrolManagerBean) form;
		FormFile file = applyBean.getFile();

		MaterialImportBo bo = (MaterialImportBo) ctx
				.getBean("materialImportBo");
		InputStream in = file.getInputStream();
		HttpSession session = request.getSession();

		Map map = bo.importMaterialApplyNumber(in);

		request.setAttribute("error", map.get("error_msg"));
		session.setAttribute("data", map.get("data_list"));
		logger.info(map);

		PrintWriter out = response.getWriter();
		out.print("<script type=\"text/javascript\">");
		out.print("parent.loadImportMaterial();");
		out.print("parent.close();");
		out.print("</script>");
		out.close();
		return null;
	}
}
