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

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.linepatrol.commons.constant.CommonConstant;
import com.cabletech.linepatrol.material.beans.MaterialModelBean;
import com.cabletech.linepatrol.material.beans.MaterialPutStorageBean;
import com.cabletech.linepatrol.material.beans.MaterialTypeBean;
import com.cabletech.linepatrol.material.domain.MaterialAddress;
import com.cabletech.linepatrol.material.domain.MaterialInfo;
import com.cabletech.linepatrol.material.service.MaterialAddressBo;
import com.cabletech.linepatrol.material.service.MaterialApplyBo;
import com.cabletech.linepatrol.material.service.MaterialImportBo;
import com.cabletech.linepatrol.material.service.MaterialInfoBo;
import com.cabletech.linepatrol.material.service.MaterialModelBo;
import com.cabletech.linepatrol.material.service.MaterialPutStorageBo;
import com.cabletech.linepatrol.material.service.MaterialTypeBo;

public class MaterialPutStorageAction extends BaseInfoBaseDispatchAction {
	private static Logger logger = Logger
			.getLogger(MaterialPutStorageAction.class.getName());
	private WebApplicationContext ctx;
	private UserInfo userInfo;
	private MaterialApplyBo applyBo;
	private MaterialPutStorageBo putStorageBo;

	/**
	 * 执行初始化动作
	 * 
	 * @param request
	 */
	public void initialize(HttpServletRequest request) {
		ctx = getWebApplicationContext();
		userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		applyBo = (MaterialApplyBo) ctx.getBean("materialApplyBo");
		putStorageBo = (MaterialPutStorageBo) ctx
				.getBean("materialPutStorageBo");
	}

	/**
	 * 跳转到填写材料入库表单页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addMaterialPutStorageForm(ActionMapping mapping,
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
		return mapping.findForward("add_material_put_storage");
	}

	/**
	 * 跳转到修改材料入库表单页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modMaterialPutStorageForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String applyId = request.getParameter("apply_id");
		Map map = applyBo.viewMaterialApply(applyId);
		request.setAttribute("applyBean", map.get("apply_bean"));
		String putStorageId = request.getParameter("put_storage_id");
		map = putStorageBo.viewMaterialPutStorage(putStorageId);
		request.setAttribute("bean", map.get("put_storage_bean"));

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
		return mapping.findForward("mod_material_put_storage");
	}

	/**
	 * 跳转到审核材料入库表单页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward approveMaterialPutStorageForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String applyId = request.getParameter("apply_id");
		Map map = applyBo.viewMaterialApply(applyId);
		request.setAttribute("applyBean", map.get("apply_bean"));
		String putStorageId = request.getParameter("put_storage_id");
		map = putStorageBo.viewMaterialPutStorage(putStorageId);
		request.setAttribute("bean", map.get("put_storage_bean"));

		// 获得服务器的当前时间
		Date nowDate = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = df.format(nowDate);
		request.setAttribute("date", date);
		request.setAttribute("username", userInfo.getUserName());
		request.setAttribute("userid", userInfo.getUserID());
		request.setAttribute("deptName", userInfo.getDeptName());
		return mapping.findForward("approve_material_put_storage");
	}

	/**
	 * 跳转到转审材料入库表单页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward transferApproveMaterialPutStorageForm(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String applyId = request.getParameter("apply_id");
		Map map = applyBo.viewMaterialApply(applyId);
		request.setAttribute("applyBean", map.get("apply_bean"));
		String putStorageId = request.getParameter("put_storage_id");
		map = putStorageBo.viewMaterialPutStorage(putStorageId);
		request.setAttribute("bean", map.get("put_storage_bean"));

		// 获得服务器的当前时间
		Date nowDate = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = df.format(nowDate);
		request.setAttribute("date", date);
		request.setAttribute("username", userInfo.getUserName());
		request.setAttribute("userid", userInfo.getUserID());
		request.setAttribute("deptName", userInfo.getDeptName());
		return mapping.findForward("transfer_approve_material_put_storage");
	}

	/**
	 * 跳转到批阅材料入库表单页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward readApproveMaterialPutStorageForm(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String applyId = request.getParameter("apply_id");
		Map map = applyBo.viewMaterialApply(applyId);
		request.setAttribute("applyBean", map.get("apply_bean"));
		String putStorageId = request.getParameter("put_storage_id");
		map = putStorageBo.viewMaterialPutStorage(putStorageId);
		request.setAttribute("bean", map.get("put_storage_bean"));

		// 获得服务器的当前时间
		Date nowDate = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = df.format(nowDate);
		request.setAttribute("date", date);
		request.setAttribute("username", userInfo.getUserName());
		request.setAttribute("userid", userInfo.getUserID());
		request.setAttribute("deptName", userInfo.getDeptName());
		return mapping.findForward("read_approve_material_put_storage");
	}

	/**
	 * 执行查看材料入库详细信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewMaterialPutStorage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String applyId = request.getParameter("apply_id");
		Map map = applyBo.viewMaterialApply(applyId);
		request.setAttribute("applyBean", map.get("apply_bean"));
		String putStorageId = request.getParameter("put_storage_id");
		map = putStorageBo.viewMaterialPutStorage(putStorageId);
		request.setAttribute("bean", map.get("put_storage_bean"));
		return mapping.findForward("view_material_put_storage");
	}

	/**
	 * 执行添加材料入库信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addMaterialPutStorage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		MaterialPutStorageBean bean = (MaterialPutStorageBean) form;
		putStorageBo.addMaterialPutStorage(bean, userInfo);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		if (CommonConstant.SUBMITED.equals(bean.getIsSubmited())) {
			log(request,"添加材料入库信息（申请名称为："+bean.getTitle()+"）","材料管理");
			return super.forwardInfoPageWithUrl(mapping, request,
					"S_MT_PUT_STORAGE_ADD", url);
		} else {
			log(request,"暂存材料入库信息（申请名称为："+bean.getTitle()+"）","材料管理");
			return super.forwardInfoPageWithUrl(mapping, request,
					"S_MT_PUT_STORAGE_SAVED", url);
		}
	}

	/**
	 * 执行修改材料入库信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modMaterialPutStorage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		MaterialPutStorageBean bean = (MaterialPutStorageBean) form;
		putStorageBo.modMaterialPutStorage(bean, userInfo);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		if (CommonConstant.SUBMITED.equals(bean.getIsSubmited())) {
			log(request,"修改材料入库信息（申请名称为："+bean.getTitle()+"）","材料管理");
			return super.forwardInfoPageWithUrl(mapping, request,
					"S_MT_PUT_STORAGE_MOD", url);
		} else {
			log(request,"暂存材料入库信息（申请名称为："+bean.getTitle()+"）","材料管理");
			return super.forwardInfoPageWithUrl(mapping, request,
					"S_MT_PUT_STORAGE_SAVED", url);
		}
	}

	/**
	 * 执行删除材料入库信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delMaterialPutStorage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String putStorageId = request.getParameter("put_storage_id");
		putStorageBo.delMaterialPutStorage(putStorageId);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		log(request,"删除材料入库信息","材料管理");
		return super.forwardInfoPageWithUrl(mapping, request,
				"S_MT_PUT_STORAGE_DEL", url);
	}

	/**
	 * 执行审核材料入库信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward approveMaterialPutStorage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		MaterialPutStorageBean bean = (MaterialPutStorageBean) form;
		putStorageBo.approveMaterialPutStorage(bean, userInfo);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		if (CommonConstant.APPROVE_RESULT_NO.equals(bean.getApproveResult())) {
			log(request,"审核通过材料入库信息（申请名称为："+bean.getTitle()+"）","材料管理");
			return super.forwardInfoPageWithUrl(mapping, request,
					"S_MT_PUT_STORAGE_APPROVE_NO", url);
		}
		if (CommonConstant.APPROVE_RESULT_PASS.equals(bean.getApproveResult())) {
			log(request,"审核未通过材料入库信息（申请名称为："+bean.getTitle()+"）","材料管理");
			return super.forwardInfoPageWithUrl(mapping, request,
					"S_MT_PUT_STORAGE_APPROVE_YES", url);
		}
		log(request,"转审材料入库信息（申请名称为："+bean.getTitle()+"）","材料管理");
		return super.forwardInfoPageWithUrl(mapping, request,
				"S_MT_PUT_STORAGE_APPROVE_TRANSFER", url);
	}

	/**
	 * 执行批阅材料入库信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward readApproveMaterialPutStorage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		MaterialPutStorageBean bean = (MaterialPutStorageBean) form;
		putStorageBo.readApproveMaterialPutStorage(bean, userInfo);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		log(request,"批阅材料入库信息（申请名称为："+bean.getTitle()+"）","材料管理");
		return super.forwardInfoPageWithUrl(mapping, request,
				"S_MT_PUT_STORAGE_APPROVE_READ", url);
	}

	/**
	 * 执行查询材料入库列表信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listMaterialPutStorage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		super.setPageReset(request);
		MaterialPutStorageBean bean = (MaterialPutStorageBean) form;
		List list = putStorageBo.queryMaterialPutStorageList(bean, userInfo);
		request.getSession().setAttribute("DATA_LIST", list);
		return mapping.findForward("list_material_put_storage");
	}

	/**
	 * 执行查询材料入库审核信息列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryMaterialPutStorageApproveInfoList(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String putStorageId = request.getParameter("put_storage_id");
		List list = putStorageBo
				.queryMaterialPutStorageApproveInfoList(putStorageId);
		request.setAttribute("approve_info_list", list);
		return mapping.findForward("list_put_storage_approve_info");
	}

	/**
	 * 执行导入材料信息
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
		MaterialPutStorageBean applyBean = (MaterialPutStorageBean) form;
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
