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
import com.cabletech.linepatrol.commons.constant.CommonConstant;
import com.cabletech.linepatrol.material.beans.MaterialApplyBean;
import com.cabletech.linepatrol.material.beans.MaterialModelBean;
import com.cabletech.linepatrol.material.beans.MaterialTypeBean;
import com.cabletech.linepatrol.material.domain.MaterialAddress;
import com.cabletech.linepatrol.material.domain.MaterialInfo;
import com.cabletech.linepatrol.material.service.ConditionGenerate;
import com.cabletech.linepatrol.material.service.MaterialAddressBo;
import com.cabletech.linepatrol.material.service.MaterialApplyBo;
import com.cabletech.linepatrol.material.service.MaterialImportBo;
import com.cabletech.linepatrol.material.service.MaterialInfoBo;
import com.cabletech.linepatrol.material.service.MaterialModelBo;
import com.cabletech.linepatrol.material.service.MaterialTypeBo;

public class MaterialApplyAction extends BaseInfoBaseDispatchAction {
	private static Logger logger = Logger.getLogger(MaterialApplyAction.class
			.getName());
	private WebApplicationContext ctx;
	private UserInfo userInfo;
	private MaterialApplyBo applyBo;

	/**
	 * 执行初始化动作
	 * 
	 * @param request
	 */
	public void initialize(HttpServletRequest request) {
		ctx = getWebApplicationContext();
		userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		applyBo = (MaterialApplyBo) ctx.getBean("materialApplyBo");
	}

	/**
	 * 跳转到添加材料申请表单页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addMaterialApplyForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		// 获得服务器的当前时间
		Date nowDate = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = df.format(nowDate);
		request.setAttribute("date", date);
		request.setAttribute("username", userInfo.getUserName());
		request.setAttribute("userid", userInfo.getUserID());
		request.setAttribute("deptName", userInfo.getDeptName());

		MaterialTypeBo typeBo = (MaterialTypeBo) ctx.getBean("materialTypeBo");
		MaterialAddressBo addressBo = (MaterialAddressBo) ctx
				.getBean("materialAddressBo");
		MaterialTypeBean bean = new MaterialTypeBean();
		List typeList = typeBo.getMaterialTypes(bean, userInfo);
		MaterialAddress materialAddress = new MaterialAddress();
		materialAddress.setContractorid(userInfo.getDeptID());
		materialAddress.setAddress("");
		materialAddress.setId("");
		List addressList = addressBo.getPartaddressBean(materialAddress);
		request.setAttribute("typeList", typeList);
		request.setAttribute("addressList", addressList);
		return mapping.findForward("add_material_apply");
	}

	/**
	 * 跳转到材料申请修改表单页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modMaterialApplyForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String applyId = request.getParameter("apply_id");
		Map map = applyBo.viewMaterialApply(applyId);
		request.setAttribute("bean", map.get("apply_bean"));

		// 获得服务器的当前时间
		Date nowDate = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = df.format(nowDate);
		request.setAttribute("date", date);
		request.setAttribute("username", userInfo.getUserName());
		request.setAttribute("userid", userInfo.getUserID());
		request.setAttribute("deptName", userInfo.getDeptName());

		MaterialTypeBo typeBo = (MaterialTypeBo) ctx.getBean("materialTypeBo");
		MaterialModelBo modelBo = (MaterialModelBo) ctx
				.getBean("materialModelBo");
		MaterialInfoBo materialBo = (MaterialInfoBo) ctx
				.getBean("materialInfoBo");
		MaterialAddressBo addressBo = (MaterialAddressBo) ctx
				.getBean("materialAddressBo");
		MaterialTypeBean typeBean = new MaterialTypeBean();
		List typeList = typeBo.getMaterialTypes(typeBean, userInfo);
		MaterialModelBean modelBean = new MaterialModelBean();
		modelBean.setTypeID(-1);
		List modelList = modelBo.getMaterialModels(modelBean, userInfo);
		MaterialInfo materialInfo = new MaterialInfo();
		List materialList = materialBo.getPartBaseBean(materialInfo, userInfo);
		MaterialAddress materialAddress = new MaterialAddress();
		materialAddress.setContractorid(userInfo.getDeptID());
		materialAddress.setAddress("");
		materialAddress.setId("");
		List addressList = addressBo.getPartaddressBean(materialAddress);
		request.setAttribute("typeList", typeList);
		request.setAttribute("modelList", modelList);
		request.setAttribute("materialList", materialList);
		request.setAttribute("addressList", addressList);
		return mapping.findForward("mod_material_apply");
	}

	/**
	 * 跳转到材料申请审核表单页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward approveMaterialApplyForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String applyId = request.getParameter("apply_id");
		Map map = applyBo.viewMaterialApply(applyId);
		request.setAttribute("bean", map.get("apply_bean"));

		// 获得服务器的当前时间
		Date nowDate = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = df.format(nowDate);
		request.setAttribute("date", date);
		request.setAttribute("username", userInfo.getUserName());
		request.setAttribute("userid", userInfo.getUserID());
		request.setAttribute("deptName", userInfo.getDeptName());
		return mapping.findForward("approve_material_apply");
	}

	/**
	 * 跳转到材料申请转审表单页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward transferApproveMaterialApplyForm(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String applyId = request.getParameter("apply_id");
		Map map = applyBo.viewMaterialApply(applyId);
		request.setAttribute("bean", map.get("apply_bean"));

		// 获得服务器的当前时间
		Date nowDate = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = df.format(nowDate);
		request.setAttribute("date", date);
		request.setAttribute("username", userInfo.getUserName());
		request.setAttribute("userid", userInfo.getUserID());
		request.setAttribute("deptName", userInfo.getDeptName());
		return mapping.findForward("transfer_approve_material_apply");
	}

	/**
	 * 跳转到材料申请批阅表单页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward readApproveMaterialApplyForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String applyId = request.getParameter("apply_id");
		Map map = applyBo.viewMaterialApply(applyId);
		request.setAttribute("bean", map.get("apply_bean"));

		// 获得服务器的当前时间
		Date nowDate = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = df.format(nowDate);
		request.setAttribute("date", date);
		request.setAttribute("username", userInfo.getUserName());
		request.setAttribute("userid", userInfo.getUserID());
		request.setAttribute("deptName", userInfo.getDeptName());
		return mapping.findForward("read_approve_material_apply");
	}

	/**
	 * 跳转到材料申请查询表单页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryMaterialApplyForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		return mapping.findForward("query_material_apply");
	}

	/**
	 * 执行查看材料申请详细信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewMaterialApply(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String applyId = request.getParameter("apply_id");
		Map map = applyBo.viewMaterialApply(applyId);
		request.setAttribute("bean", map.get("apply_bean"));
		request.setAttribute("storage_id", map.get("storage_id"));
		return mapping.findForward("view_material_apply");
	}

	/**
	 * 执行添加材料申请信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addMaterialApply(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		MaterialApplyBean bean = (MaterialApplyBean) form;
		applyBo.addMaterialApply(bean, userInfo);
		String url = request.getContextPath()
				+ "/material_apply.do?method=addMaterialApplyForm";
		if (CommonConstant.SUBMITED.equals(bean.getIsSubmited())) {
			log(request,"添加材料申请信息（申请名称为："+bean.getTitle()+"）","材料管理");
			return super.forwardInfoPageWithUrl(mapping, request,
					"S_MT_APPLY_ADD", url);
		} else {
			log(request,"保存材料申请信息（申请名称为："+bean.getTitle()+"）","材料管理");
			return super.forwardInfoPageWithUrl(mapping, request,
					"S_MT_APPLY_SAVED", url);
		}
	}

	/**
	 * 执行修改材料申请信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modMaterialApply(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		MaterialApplyBean bean = (MaterialApplyBean) form;
		applyBo.modMaterialApply(bean, userInfo);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		if (CommonConstant.SUBMITED.equals(bean.getIsSubmited())) {
			log(request,"修改材料申请信息（申请名称为："+bean.getTitle()+"）","材料管理");
			return super.forwardInfoPageWithUrl(mapping, request,
					"S_MT_APPLY_MOD", url);
		} else {
			log(request,"保存材料申请信息（申请名称为："+bean.getTitle()+"）","材料管理");
			return super.forwardInfoPageWithUrl(mapping, request,
					"S_MT_APPLY_SAVED", url);
		}
	}

	/**
	 * 执行删除材料申请信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delMaterialApply(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String applyId = request.getParameter("apply_id");
		String title=applyBo.get(applyId).getTitle();
		applyBo.delMaterialApply(applyId);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		log(request,"删除材料申请信息（（申请名称为："+title+"））","材料管理");
		return super.forwardInfoPageWithUrl(mapping, request, "S_MT_APPLY_DEL",
				url);
	}

	/**
	 * 执行审核材料申请信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward approveMaterialApply(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		MaterialApplyBean bean = (MaterialApplyBean) form;
		applyBo.approveMaterialApply(bean, userInfo);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		if (CommonConstant.APPROVE_RESULT_NO.equals(bean.getApproveResult())) {
			log(request,"审核通过材料申请信息（申请名称为："+bean.getTitle()+"）","材料管理");
			return super.forwardInfoPageWithUrl(mapping, request,
					"S_MT_APPLY_APPROVE_NO", url);
		}
		if (CommonConstant.APPROVE_RESULT_PASS.equals(bean.getApproveResult())) {
			log(request,"审核未通过材料申请信息（申请名称为："+bean.getTitle()+"）","材料管理");
			return super.forwardInfoPageWithUrl(mapping, request,
					"S_MT_APPLY_APPROVE_YES", url);
		}
		log(request,"转审材料申请信息（申请名称为："+bean.getTitle()+"）","材料管理");
		return super.forwardInfoPageWithUrl(mapping, request,
				"S_MT_APPLY_APPROVE_TRANSFER", url);
	}

	/**
	 * 执行批阅材料申请信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward readApproveMaterialApply(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		MaterialApplyBean bean = (MaterialApplyBean) form;
		applyBo.readApproveMaterialApply(bean, userInfo);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		log(request,"批阅材料申请信息（申请名称为："+bean.getTitle()+"）","材料管理");
		return super.forwardInfoPageWithUrl(mapping, request,
				"S_MT_APPLY_APPROVE_READ", url);
	}

	/**
	 * 执行查询待办材料申请信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryWaitHandleMaterialApplyList(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		super.setPageReset(request);
		MaterialApplyBean bean = (MaterialApplyBean) form;
		String taskName = request.getParameter("task_name");
		List list = applyBo.queryWaitHandleMaterialApplyList(bean, userInfo,
				taskName);
		request.getSession().setAttribute("DATA_LIST", list);
		if (list != null && !list.isEmpty()) {
			request.getSession().setAttribute("WAIT_HANDLE_NUM",
					list.size() + "");
		} else {
			request.getSession().setAttribute("WAIT_HANDLE_NUM", "0");
		}
		request.setAttribute("task_name", taskName);
		return mapping.findForward("list_wait_handle_material_apply");
	}

	/**
	 * 执行查询已办材料申请信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryFinishHandledMaterialApplyList(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		super.setPageReset(request);
		String taskName = request.getParameter("task_name");
		String taskOutCome = request.getParameter("task_out_come");
		String beginTime = request.getParameter("begintime");
		String endTime = request.getParameter("endtime");
		List list = applyBo.queryFinishHandledMaterialApplyList(userInfo,
				taskName, taskOutCome, beginTime, endTime);
		request.getSession().setAttribute("DATA_LIST", list);
		request.setAttribute("begin_time", request.getParameter("begintime"));
		request.setAttribute("end_time", request.getParameter("endtime"));
		return mapping.findForward("list_finish_handled_material_apply");
	}

	/**
	 * 执行查询材料申请信息列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryMaterialApplyList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		super.setPageReset(request);
		MaterialApplyBean bean = (MaterialApplyBean) form;
		List list = applyBo.queryMaterialApplyList(bean, userInfo);
		request.getSession().setAttribute("DATA_LIST", list);
		return mapping.findForward("list_material_apply");
	}

	/**
	 * 执行查询某个材料申请的审核信息列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryMaterialApplyApproveInfoList(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String applyId = request.getParameter("apply_id");
		List list = applyBo.queryMaterialApplyApproveInfoList(applyId);
		request.setAttribute("approve_info_list", list);
		return mapping.findForward("list_apply_approve_info");
	}

	/**
	 * 执行显示材料申请流程图
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewMaterialApplyProcess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String taskName = request.getParameter("task_name");
		String condition = "";
		condition += ConditionGenerate.getUserQueryCondition(userInfo);
		List waitHandleTaskNum = applyBo.queryForHandleMaterialApplyTaskNum(
				condition, userInfo);
		request.setAttribute("wait_apply_task_num", waitHandleTaskNum.get(0));
		request.setAttribute("wait_apply_approve_task_num", waitHandleTaskNum
				.get(1));
		request.setAttribute("wait_put_storage_task_num", waitHandleTaskNum
				.get(2));
		request.setAttribute("wait_put_storage_confirm_task_num",
				waitHandleTaskNum.get(3));
		request.setAttribute("task_name", taskName);
		return mapping.findForward("view_material_apply_process");
	}

	/**
	 * 执行显示材料申请已办流程图
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewMaterialApplyHistoryProcess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String taskName = request.getParameter("task_name");
		String taskOutCome = request.getParameter("task_out_come");
		String condition = "";
		condition += ConditionGenerate.getUserQueryCondition(userInfo);
		String beginTime = request.getParameter("begintime");
		String endTime = request.getParameter("endtime");
		List waitHandleTaskNum = applyBo.queryForHandledMaterialApplyTaskNum(
				condition, userInfo, beginTime, endTime);
		request.setAttribute("applyed_task_num", waitHandleTaskNum.get(0));
		request.setAttribute("apply_approved_task_num", waitHandleTaskNum
				.get(1));
		request.setAttribute("putted_storage_task_num", waitHandleTaskNum
				.get(2));
		request.setAttribute("put_storage_confirmed_task_num",
				waitHandleTaskNum.get(3));
		request.setAttribute("task_name", taskName);
		request.setAttribute("task_out_come", taskOutCome);
		return mapping.findForward("view_material_apply_history_process");
	}

	/**
	 * 执行根据材料类型获取材料规格列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getMaterialModes(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		response.setContentType("text/json; charset=GBK");
		String id = request.getParameter("id");
		MaterialModelBo modelBo = (MaterialModelBo) ctx
				.getBean("materialModelBo");
		MaterialModelBean bean = new MaterialModelBean();
		bean.setTypeID(Integer.parseInt(id));
		List modellist = modelBo.getMaterialModels(bean, userInfo);
		logger.info("*********:" + modellist);
		JSONArray ja = JSONArray.fromObject(modellist);
		PrintWriter out = response.getWriter();
		out.write(ja.toString());
		out.flush();
		return null;
	}

	/**
	 * 执行根据材料规格获取材料信息列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getMaterials(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initialize(request);
		response.setContentType("text/json; charset=GBK");
		String id = request.getParameter("id");
		MaterialInfoBo materialBo = (MaterialInfoBo) ctx
				.getBean("materialInfoBo");
		MaterialInfo materialInfo = new MaterialInfo();
		materialInfo.setModelid(Integer.parseInt(id));
		List baselist = materialBo.getPartBaseBean(materialInfo, userInfo);
		JSONArray ja = JSONArray.fromObject(baselist);
		PrintWriter out = response.getWriter();
		out.write(ja.toString());
		out.flush();
		return null;
	}

	/**
	 * 执行导入材料申请中材料信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward importMaterial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		MaterialApplyBean applyBean = (MaterialApplyBean) form;
		FormFile file = applyBean.getFile();

		MaterialImportBo bo = (MaterialImportBo) ctx
				.getBean("materialImportBo");
		InputStream in = file.getInputStream();
		HttpSession session = request.getSession();

		try {
			Map map= bo.importMaterialApplyNumber(in);
			session.setAttribute("error", map.get("error_msg"));
			session.setAttribute("data", map.get("data_list"));
			logger.info(map);
		} catch (Exception e) {
			session.setAttribute("error","表格格式不正确");
			session.setAttribute("data", null);
		}
		PrintWriter out = response.getWriter();
		out.print("<script type=\"text/javascript\">");
		out.print("parent.loadImportMaterial();");
		out.print("parent.close();");
		out.print("</script>");
		out.close();
		return null;
	}

	/**
	 * 执行取消材料申请任务表单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancelMaterialApplyForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String applyId = request.getParameter("apply_id");
		request.setAttribute("apply_id", applyId);
		return mapping.findForward("cancel_material_apply");
	}

	/**
	 * 执行取消材料申请任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward cancelMaterialApply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		MaterialApplyBean bean = (MaterialApplyBean) form;
		// bean.setId(request.getParameter("cutId"));
		initialize(request);
		applyBo.cancelMaterialApply(bean, userInfo);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		PrintWriter out = response.getWriter();
		out.print("<script type='text/javascript'>");
		out.print("window.opener.location.href='" + url + "';");
		out.print("window.close();");
		out.print("</script>");
		return null;
		// return super.forwardInfoPageWithUrl(mapping, request,
		// "CANCEL_MATERIAL_APPLY_SUCCESS", url);
	}
}
