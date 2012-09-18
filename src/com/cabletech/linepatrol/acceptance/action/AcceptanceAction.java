package com.cabletech.linepatrol.acceptance.action;

import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.tags.services.DictionaryService;
import com.cabletech.commons.upload.module.UploadFileInfo;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.commons.web.ClientException;
import com.cabletech.linepatrol.acceptance.beans.ApplyBean;
import com.cabletech.linepatrol.acceptance.beans.CableBean;
import com.cabletech.linepatrol.acceptance.beans.ExamBean;
import com.cabletech.linepatrol.acceptance.beans.PipesBean;
import com.cabletech.linepatrol.acceptance.beans.QueryBean;
import com.cabletech.linepatrol.acceptance.beans.QueryReinspectBean;
import com.cabletech.linepatrol.acceptance.beans.RcBean;
import com.cabletech.linepatrol.acceptance.beans.RpBean;
import com.cabletech.linepatrol.acceptance.model.Apply;
import com.cabletech.linepatrol.acceptance.model.ApplyCable;
import com.cabletech.linepatrol.acceptance.model.ApplyPipe;
import com.cabletech.linepatrol.acceptance.model.CableResult;
import com.cabletech.linepatrol.acceptance.model.PayCable;
import com.cabletech.linepatrol.acceptance.model.PayPipe;
import com.cabletech.linepatrol.acceptance.model.PipeResult;
import com.cabletech.linepatrol.acceptance.service.AcceptanceConstant;
import com.cabletech.linepatrol.acceptance.service.ApplyManager;
import com.cabletech.linepatrol.acceptance.service.ApplyTaskManager;
import com.cabletech.linepatrol.acceptance.service.CableTaskManager;
import com.cabletech.linepatrol.acceptance.service.ExcelManager;
import com.cabletech.linepatrol.acceptance.service.PipeTaskManager;
import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;
import com.cabletech.linepatrol.overhaul.service.OverhaulManager;
import com.cabletech.linepatrol.resource.model.Pipe;
import com.cabletech.linepatrol.resource.model.PipeAddone;
import com.cabletech.linepatrol.resource.model.RepeaterSection;
import com.cabletech.linepatrol.resource.model.RepeaterSectionAddone;

public class AcceptanceAction extends BaseInfoBaseDispatchAction {

	// 开始验收申请
	public ActionForward applyLink(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		ApplyBean applyBean = new ApplyBean();
		applyBean.setApplicant(userInfo.getUserName());
		applyBean.setResourceType(AcceptanceConstant.CABLE);
		request.setAttribute("applyBean", applyBean);
		session.setAttribute("apply", new Apply());
		return mapping.findForward("applyLink");
	}

	// 开始复验申请
	public ActionForward reinspectLink(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");
		
		WebApplicationContext ctx=getWebApplicationContext();
		DictionaryService dictService = (DictionaryService)ctx.getBean("dictionaryService");
		
		ApplyBean applyBean = new ApplyBean();
		applyBean.setApplicant(userInfo.getUserName());
		applyBean.setResourceType(AcceptanceConstant.CABLE);

		session.setAttribute("cabletype", dictService.loadDictByAssortment("cabletype", null));
		session.setAttribute("pipeProperty", dictService.loadDictByAssortment("property_right", null));
		session.setAttribute("pipeType", dictService.loadDictByAssortment("pipe_type", null));

		request.setAttribute("applyBean", applyBean);
		session.setAttribute("apply", new Apply());
		return mapping.findForward("reinspect");
	}

	// 复验申请过滤资源
	public ActionForward filterInspect(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyTaskManager atm = (ApplyTaskManager) ctx
				.getBean("applyTaskManager");

		HttpSession session = request.getSession();
		QueryReinspectBean queryInBean = (QueryReinspectBean) form;
		String type = request.getParameter("type");

		if (type.equals("cable")) {
			session.setAttribute("cables", atm.loadReinspectCables(queryInBean));
			return mapping.findForward("cable");
		} else {
			session.setAttribute("pipes", atm.loadReinspectPipes(queryInBean));
			return mapping.findForward("pipe");
		}
	}

	// 复验中添加光缆任务--Ajax
	public ActionForward reinspectCable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyTaskManager atm = (ApplyTaskManager) ctx
				.getBean("applyTaskManager");

		String cableId = request.getParameter("objects");

		HttpSession session = request.getSession();
		List<ApplyCable> cables = (List<ApplyCable>) session.getAttribute("cables");
		Apply apply = (Apply) session.getAttribute("apply");
		session.setAttribute("apply", atm.addReinspectCable(apply, cables,
				cableId));

		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		return null;
	}

	// 复验中添加管道任务--Ajax
	public ActionForward reinspectPipe(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyTaskManager atm = (ApplyTaskManager) ctx.getBean("applyTaskManager");

		String pipeId = request.getParameter("objects");

		HttpSession session = request.getSession();
		List<ApplyPipe> pipes = (List<ApplyPipe>) session.getAttribute("pipes");
		Apply apply = (Apply) session.getAttribute("apply");
		session.setAttribute("apply", atm
				.addReinspectPipe(apply, pipes, pipeId));

		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		return null;
	}

	// 复验申请保存
	public ActionForward reinspect(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		Apply apply = (Apply) session.getAttribute("apply");
		ApplyBean applyBean = (ApplyBean) form;

		if (am.invalidApply(applyBean.getResourceType(), apply)) {
			return forwardErrorPage(mapping, request, "applyError");
		} else {
			String approver = request.getParameter("approver");

			am.reinspect(applyBean, apply, userInfo, approver);

			session.removeAttribute("cables");
			session.removeAttribute("pipes");
			session.removeAttribute("apply");
			log(request, "复验申请保存（工程名称为：" + applyBean.getName() + "）", "验收交维");
			return forwardInfoPage(mapping, request, "inspectApply");
		}
	}

	// 复验核准准备信息
	public ActionForward recheckLink(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");

		String id = request.getParameter("id");
		Apply apply = am.loadApply(id);
		request.getSession().setAttribute("apply", apply);
		return mapping.findForward("recheck");
	}

	// 复验核准
	public ActionForward recheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		String id = request.getParameter("id");
		am.recheck(id, userInfo);
		log(request, "复验核准（工程名称为：" + am.loadApply(id).getName() + "）", "验收交维");
		return forwardInfoPage(mapping, request, "recheckSuccess");
	}

	// 申请中导入光缆
	public ActionForward importCable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		HttpSession session = request.getSession();
		
		WebApplicationContext ctx = getWebApplicationContext();
		ExcelManager em = (ExcelManager) ctx.getBean("excelManager");
		DictionaryService dictService = (DictionaryService)ctx.getBean("dictionaryService");
		ApplyBean applyBean = (ApplyBean) form;
		FormFile file = applyBean.getFile();
		InputStream in = file.getInputStream();

		Apply apply = (Apply) session.getAttribute("apply");

		// 导入类型
		String type = request.getParameter("type");

		// 批量导入
		try {
			apply = em.importCable(apply, in, type);
		} catch (Exception e) {
			return forwardInfoPage(mapping, request, "importCableError");
		}
		request.setAttribute("error", apply.getErrorMsg());
		session.setAttribute("apply", apply);
		session.setAttribute("cabletype", dictService.loadDictByAssortment("cabletype", null));
		return mapping.findForward("cableList");
	}

	// 申请中导入管道
	public ActionForward importPipe(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ExcelManager em = (ExcelManager) ctx.getBean("excelManager");
		DictionaryService dictService = (DictionaryService)ctx.getBean("dictionaryService");
		ApplyBean applyBean = (ApplyBean) form;
		FormFile file = applyBean.getFile();
		InputStream in = file.getInputStream();

		HttpSession session = request.getSession();
		Apply apply = (Apply) session.getAttribute("apply");

		// 导入类型
		String type = request.getParameter("type");

		// 批量导入
		try {
			apply = em.importPipe(apply, in, type);
		} catch (Exception e) {
			return forwardInfoPage(mapping, request, "importPipeError");
		}
		request.setAttribute("error", apply.getErrorMsg());
		session.setAttribute("apply", apply);
		session.setAttribute("pipeProperty", dictService.loadDictByAssortment("property_right", null));
		session.setAttribute("pipeType", dictService.loadDictByAssortment("pipe_type", null));
		return mapping.findForward("pipeList");
	}

	// 申请中录入光缆
	public ActionForward recordCable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");

		DictionaryService dictService = (DictionaryService)ctx.getBean("dictionaryService");
		CableBean cableBean = (CableBean) form;
		ApplyCable cable = new ApplyCable();
		BeanUtil.copyProperties(cableBean, cable);
		String[] layingMethods = request.getParameterValues("layingmethod");
		cable.setLayingMethod(StringUtils.join(layingMethods, ","));

		HttpSession session = request.getSession();
		Apply apply = (Apply) session.getAttribute("apply");
		apply = am.addCable(apply, cable);
		request.setAttribute("error", apply.getErrorMsg());
		session.setAttribute("apply", apply);
		session.setAttribute("cabletype", dictService.loadDictByAssortment("cabletype", null));
		return mapping.findForward("recordResult");
	}
	
	// 申请中录入管道
	public ActionForward recordPipe(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");
		
		DictionaryService dictService = (DictionaryService)ctx.getBean("dictionaryService");
		PipesBean pipeBean = (PipesBean) form;
		ApplyPipe pipe = new ApplyPipe();
		BeanUtil.copyProperties(pipeBean, pipe);

		HttpSession session = request.getSession();
		Apply apply = (Apply) session.getAttribute("apply");
		apply = am.addPipe(apply, pipe);
		session.setAttribute("apply", apply);
		request.setAttribute("error", apply.getErrorMsg());
		session.setAttribute("pipeProperty", dictService.loadDictByAssortment("property_right", null));
		session.setAttribute("pipeType", dictService.loadDictByAssortment("pipe_type", null));
		return mapping.findForward("recordResult");
	}

	// 查看录入的光缆
	public ActionForward viewCable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");

		String id = request.getParameter("id");
		request.setAttribute("cable", am.loadCable(id));

		return mapping.findForward("viewCable");
	}

	// 查看录入的管道
	public ActionForward viewPipe(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");

		String id = request.getParameter("id");
		request.setAttribute("pipe", am.loadPipe(id));

		return mapping.findForward("viewPipe");
	}

	// 导出已录入的管道或光缆
	public ActionForward export(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");

		HttpSession session = request.getSession();
		Apply apply = (Apply) session.getAttribute("apply");

		if (apply.getCables().isEmpty() && apply.getPipes().isEmpty()) {
			return forwardInfoPage(mapping, request, "exportError");
		} else {
			am.export(apply, response);
			return null;
		}
	}

	// 导出交维信息
	public ActionForward excel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");

		HttpSession session = request.getSession();
		String id = request.getParameter("id");
		String type = request.getParameter("type");

		List<RepeaterSection> trunks = null;
		if (type.equals("session")) {
			trunks = (List<RepeaterSection>) session.getAttribute("result");
		} else {
			trunks = am.getTrunks(id);
		}

		if (trunks == null) {
			return forwardInfoPage(mapping, request, "noexportcables");
		} else {
			am.export(trunks, response);
			return null;
		}
	}

	// 保存申请
	public ActionForward apply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		Apply apply = (Apply) session.getAttribute("apply");
		ApplyBean applyBean = (ApplyBean) form;

		if (am.invalidApply(applyBean.getResourceType(), apply)) {
			return forwardErrorPage(mapping, request, "applyError");
		} else {
			BeanUtil.copyProperties(applyBean, apply);

			String approver = request.getParameter("approver");
			am.saveApply(apply, userInfo, approver);
			log(request, "保存申请（工程名称为：" + applyBean.getName() + "）", "验收交维");
			return forwardInfoPage(mapping, request, "AcceptanceApply");
		}
	}

	// 分配任务页面准备数据
	public ActionForward allotTasks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");

		String id = request.getParameter("id");

		request.getSession().setAttribute("id", id);
		request.getSession().setAttribute("apply", am.loadApply(id));
		request.getSession().setAttribute("dept", am.getDeptOptions());
		// 初始值
		request.getSession().setAttribute("type", "1");
		request.getSession().setAttribute("ids", "");
		request.getSession().setAttribute("names", "");
		request.getSession().setAttribute("specify",new HashMap<String, String>());

		return mapping.findForward("allotTasks");
	}

	// 分配任务页面--核准类型保存在session中
	public ActionForward changeType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		String type = request.getParameter("type");
		request.getSession().setAttribute("type", type);
		return null;
	}

	// 分配任务页面--选择时（代维名称和代维id保存在session中）
	public ActionForward saveChoose(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		String names = request.getParameter("names");
		String ids = request.getParameter("ids");
		request.getSession().setAttribute("names", names);
		request.getSession().setAttribute("ids", ids);
		return null;
	}

	// 分配任务页面--指定时（资源id和代维id保存在session中）
	public ActionForward saveSpecify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		String objectId = request.getParameter("objectId");
		String contractorId = request.getParameter("contractorId");

		Map<String, String> map = (Map<String, String>) request.getSession().getAttribute("specify");
		if (map.containsKey(objectId)) {
			map.remove(objectId);
		}
		if (StringUtils.isNotBlank(contractorId)) {
			map.put(objectId, contractorId);
		}
		request.getSession().setAttribute("specify", map);
		return null;
	}

	// 保存分配任务
	public ActionForward allot(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyTaskManager atm = (ApplyTaskManager) ctx.getBean("applyTaskManager");
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		String id = request.getParameter("id");
		String type = request.getParameter("type");//指定代维 选择代维
		String remark = request.getParameter("remark");
		Map<String, String> allots = (Map<String, String>)session.getAttribute("specify");
		String choose = request.getParameter("chooseContractor");

		if (type.equals(AcceptanceConstant.SPECIFY)) {
			Apply apply = (Apply) session.getAttribute("apply");
			if (apply.getResourceType().equals(AcceptanceConstant.CABLE)) {
				if (allots.size() != apply.getCables().size()) {
					return forwardErrorPage(mapping, request, "specifyError");
				}
			} else {
				if (allots.size() != apply.getPipes().size()) {
					return forwardErrorPage(mapping, request, "specifyError");
				}
			}
		} else {
			if (StringUtils.isBlank(choose)) {
				return forwardErrorPage(mapping, request, "chooseError");
			}
		}
		atm.saveApplyTask(userInfo, type,remark,id, allots, choose);

		request.getSession().removeAttribute("dept");
		String name = am.loadApply(id).getName();
		log(request, "保存分配任务（工程名称为：" + name + "）", "验收交维");
		return forwardInfoPage(mapping, request, "allotSuccess");
	}

	// 认领任务页面准备数据
	public ActionForward claimTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");

		String id = request.getParameter("id");

		request.getSession().setAttribute("choose", new HashSet<String>());
		request.getSession().setAttribute("apply", am.loadApply(id));
		return mapping.findForward("claimTask");
	}

	// 保存认领任务
	public ActionForward claim(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyTaskManager atm = (ApplyTaskManager) ctx.getBean("applyTaskManager");
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		String id = request.getParameter("id");
		String isClaim = request.getParameter("isClaim");
		Set<String> tasks = (Set<String>) request.getSession().getAttribute( "choose");
		if("no".equals(isClaim)){
			atm.claimTask(id, tasks, userInfo);
		}else{
			if (tasks.isEmpty()) {
				return forwardErrorPage(mapping, request, "claimError");
			} else {
				atm.claimTask(id, tasks, userInfo);
			}
		}
		String name = am.loadApply(id).getName();
		log(request, "保存认领任务（工程名称为：" + name + "）", "验收交维");
		return forwardInfoPage(mapping, request, "claimSuccess");
	}

	// 核准任务页面准备数据
	public ActionForward approveTasks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");
		ApplyTaskManager atm = (ApplyTaskManager) ctx.getBean("applyTaskManager");
		DictionaryService ds = (DictionaryService) ctx.getBean("dictionaryService");
		String id = request.getParameter("id");
		request.getSession().setAttribute("id", id);
		request.getSession().setAttribute("apply", am.loadApply(id));
		request.getSession().setAttribute("dept", atm.getDeptOptions(id));
		request.getSession().setAttribute("pipePropertys", ds.loadDictByAssortment("property_right",null));
		request.getSession().setAttribute("cableLevels", ds.loadDictByAssortment("cabletype",null));

		Map<String, List<String>> deptMap = atm.getObjectToTask(id);
		request.getSession().setAttribute("deptMap",atm.getDeptNameToTask(deptMap));
		request.getSession().setAttribute("numberMap",atm.getNumberToTask(deptMap));
		request.getSession().setAttribute("specify",atm.getChoosedTask(deptMap));
		return mapping.findForward("approveTasks");
	}

	// 保存核准任务
	public ActionForward check(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");
		ApplyTaskManager atm = (ApplyTaskManager) ctx.getBean("applyTaskManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		String id = request.getParameter("id");
		Map<String, String> map = (Map<String, String>) session.getAttribute("specify");
		if (!am.validateAllTaskChoosed(id, map.size())) {
			return forwardInfoPage(mapping, request, "specifyError");
		} else {
			atm.check(id, map, userInfo);
			Apply apply = am.loadApply(id);
			String name = apply.getName();
			log(request, "保存核准任务（工程名称为：" + name + "）", "验收交维");
		}

		return forwardInfoPage(mapping, request, "checkSuccess");
	}

	// 得到某一申请下的任务列表
	public ActionForward chooseTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyTaskManager atm = (ApplyTaskManager) ctx.getBean("applyTaskManager");

		String subflowId = request.getParameter("id");
		String type = request.getParameter("type");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		session.setAttribute("type", type);
		session.setAttribute("apply", atm.getAllRecordTasksList(subflowId,userInfo.getDeptID()));
		return mapping.findForward("chooseTask");
	}

	// 开始录入数据--包括初次录入和复验录入
	public ActionForward recordData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");
		ApplyTaskManager atm = (ApplyTaskManager) ctx.getBean("applyTaskManager");
		CableTaskManager ctm = (CableTaskManager) ctx.getBean("cableTaskManager");
		PipeTaskManager ptm = (PipeTaskManager) ctx.getBean("pipeTaskManager");

		String subflowId = request.getParameter("id");
		String objectId = request.getParameter("objectId");
		String type = request.getParameter("type");
		String id = am.getSubflow(subflowId).getApplyId();
		Apply apply = am.loadApply(id);

		request.setAttribute("id", subflowId);
		request.setAttribute("objectId", objectId);

		if (apply.getResourceType().equals(AcceptanceConstant.CABLE)) {
			// 页面准备数据
			RcBean rcBean = new RcBean();

			String times = ctm.loadCableResultFromCableId(id, objectId);
			ApplyCable cable = atm.loadCable(objectId);

			request.setAttribute("times", times);
			request.setAttribute("cable", cable);
			if (type.equals("false")) { // 复验
				rcBean = ctm.loadRcBean(id, objectId);
			}

			rcBean.setBuildUnit("北京移动");
			rcBean.setPlanDate(DateUtil.DateToString(cable.getPlanAcceptanceTime()));
			request.setAttribute("rcBean", rcBean);
			return mapping.findForward("recordCableData");
		} else {
			// 页面准备数据
			RpBean rpBean = new RpBean();

			String times = ptm.loadPipeResultFromPipeId(apply.getId(), objectId);
			ApplyPipe pipe = atm.loadPipe(objectId);
			request.setAttribute("times", times);
			request.setAttribute("pipe", pipe);
			if (type.equals("false")) { // 复验
				rpBean = ptm.loadRpBean(id, objectId);
			}

			rpBean.setBuildUnit("北京移动");
			rpBean.setPlanDate(DateUtil.DateToString(pipe
					.getPlanAcceptanceTime()));
			request.setAttribute("rpBean", rpBean);
			return mapping.findForward("recordPipeData");
		}
	}

	// 修改数据
	public ActionForward editData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");
		CableTaskManager ctm = (CableTaskManager) ctx.getBean("cableTaskManager");
		PipeTaskManager ptm = (PipeTaskManager) ctx.getBean("pipeTaskManager");

		String subflowId = request.getParameter("id");
		String id = am.getSubflow(subflowId).getApplyId();
		String objectId = request.getParameter("objectId");
		Apply apply = am.loadApply(id);

		request.setAttribute("id", subflowId);
		request.setAttribute("objectId", objectId);

		if (apply.getResourceType().equals(AcceptanceConstant.CABLE)) {
			request.setAttribute("rcBean", ctm.editRcBean(id, objectId));
			log(request, "修改光缆数据（工程名称为：" + apply.getName() + "）", "验收交维");
			return mapping.findForward("editCableData");
		} else {
			request.setAttribute("rpBean", ptm.editRpBean(id, objectId));
			log(request, "修改管道数据（工程名称为：" + apply.getName() + "）", "验收交维");
			return mapping.findForward("editPipeData");
		}
	}

	// 保存光缆录入数据
	public ActionForward saveCableData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyTaskManager atm = (ApplyTaskManager) ctx.getBean("applyTaskManager");
		CableTaskManager ctm = (CableTaskManager) ctx.getBean("cableTaskManager");
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		String subflowId = request.getParameter("id");
		String objectId = request.getParameter("objectId");

		RcBean rcBean = (RcBean) form;
		// 验证文件，已存在的文件放在OLDFILE里，新上传的文件放在FILES里
		List<FileItem> files = (List<FileItem>) session.getAttribute("FILES");
		List<UploadFileInfo> ufiles = (List<UploadFileInfo>) session.getAttribute("OLDFILE");
		int size = 0;
		if (files != null) {
			size += files.size();
		}
		if (ufiles != null) {
			size += ufiles.size();
		}

		if (rcBean.getHavePicture().equals("y") && size == 0) {
			return forwardInfoPage(mapping, request, "pictureNotNull");
		} else {
			ctm.saveCableRecord(userInfo, subflowId, objectId, rcBean, files);
			session.removeAttribute("FILES");
			session.setAttribute("apply", atm.getAllRecordTasksList(subflowId,userInfo.getDeptID()));
			String applyId = am.getSubflow(subflowId).getApplyId();
			String name = am.loadApply(applyId).getName();
			log(request, "保存光缆录入数据（工程名称为：" + name + "）", "验收交维");
			return forwardInfoPage(mapping, request, "recordCableSuccess");
		}
	}

	public ActionForward validateCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ExcelManager em = (ExcelManager) ctx.getBean("excelManager");

		String id = request.getParameter("id");
		String cableId = request.getParameter("cableId");
		String html = "";
		if (!em.validateCableNumber(id, cableId)) {
			html = "有重复的光缆编号";
		}
		this.outPrint(response, html);
		return null;
	}

	// 保存管道录入数据
	public ActionForward savePipeData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyTaskManager atm = (ApplyTaskManager) ctx.getBean("applyTaskManager");
		PipeTaskManager ptm = (PipeTaskManager) ctx.getBean("pipeTaskManager");
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		String subflowId = request.getParameter("id");
		String objectId = request.getParameter("objectId");

		RpBean rpBean = (RpBean) form;

		String applyId = am.getSubflow(subflowId).getApplyId();
		String name = am.loadApply(applyId).getName();

		ptm.savePipeRecord(userInfo, subflowId, objectId, rpBean);
		session.setAttribute("apply", atm.getAllRecordTasksList(subflowId,userInfo.getDeptID()));
		log(request, "保存管道录入数据（工程名称为：" + name + "）", "验收交维");
		return forwardInfoPage(mapping, request, "recordPipeSuccess");
	}

	// 查看光缆录入数据
	public ActionForward viewCableData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");
		CableTaskManager ctm = (CableTaskManager) ctx.getBean("cableTaskManager");

		String id = request.getParameter("id");
		String cableId = request.getParameter("cableId");

		PayCable payCable = ctm.loadPayCableFromCableId(id, cableId);
		RepeaterSection cable = ctm.loadCable(payCable.getCableId());
		RepeaterSectionAddone cableAddone = ctm.loadCableAddone(cable.getKid());
		List<CableResult> cableResults = ctm.getCableResults(cable.getKid());
		ApplyCable applyCable = am.loadCable(cableId);

		RcBean rcBean = new RcBean();
		BeanUtil.copyProperties(payCable, rcBean);
		BeanUtil.copyProperties(cable, rcBean);
		BeanUtil.copyProperties(cableAddone, rcBean);
		rcBean.setContractorId(applyCable.getContractorId());

		request.setAttribute("rcBean", rcBean);
		request.getSession().setAttribute("cableResults", cableResults);
		return mapping.findForward("viewCableData");
	}

	// 查看管道录入数据
	public ActionForward viewPipeData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");
		PipeTaskManager ptm = (PipeTaskManager) ctx.getBean("pipeTaskManager");

		String id = request.getParameter("id");
		String pipeId = request.getParameter("pipeId");

		PayPipe payPipe = ptm.loadPayPipeFromPipeId(id, pipeId);
		Pipe pipe = ptm.loadPipe(payPipe.getPipeId());
		PipeAddone pipeAddone = ptm.loadPipeAddone(pipe.getId());
		List<PipeResult> pipeResults = ptm.getPipeResults(pipe.getId());
		ApplyPipe applyPipe = am.loadPipe(pipeId);

		RpBean rpBean = new RpBean();
		BeanUtil.copyProperties(payPipe, rpBean);
		BeanUtil.copyProperties(pipe, rpBean);
		BeanUtil.copyProperties(pipeAddone, rpBean);
		rpBean.setContractorId(applyPipe.getContractorId());

		request.setAttribute("rpBean", rpBean);
		request.getSession().setAttribute("pipeResults", pipeResults);
		return mapping.findForward("viewPipeData");
	}

	// 提交审核保存，代维提交管道或光缆
	public ActionForward push(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyTaskManager atm = (ApplyTaskManager) ctx.getBean("applyTaskManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		String id = request.getParameter("id");
		String approver = request.getParameter("approver");

		// 验证所有资源均已录入
		Apply apply = (Apply) session.getAttribute("apply");
		if (atm.validateRecord(apply)) {
			atm.push(id, approver, userInfo);
		} else {
			return forwardInfoPage(mapping, request, "recordError");
		}
		log(request, "审核保存（工程名称为：" + apply.getName() + "）", "验收交维");
		return forwardInfoPage(mapping, request, "subSuccess");
	}

	// 查看光缆验收结果
	public ActionForward viewCableResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		CableTaskManager ctm = (CableTaskManager) ctx
				.getBean("cableTaskManager");

		HttpSession session = request.getSession();
		List<CableResult> cableResults = (List<CableResult>) session.getAttribute("cableResults");

		String id = request.getParameter("id");
		request.setAttribute("cableResult", ctm.findCableResult(cableResults,
				id));
		return mapping.findForward("viewCableResult");
	}

	// 查看管道验收结果
	public ActionForward viewPipeResult(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		PipeTaskManager ptm = (PipeTaskManager) ctx.getBean("pipeTaskManager");

		HttpSession session = request.getSession();
		List<PipeResult> pipeResults = (List<PipeResult>) session.getAttribute("pipeResults");

		String id = request.getParameter("id");
		request.setAttribute("pipeResult", ptm.findPipeResult(pipeResults, id));
		return mapping.findForward("viewPipeResult");
	}

	// 审核页面准备数据
	public ActionForward apporveLink(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyTaskManager atm = (ApplyTaskManager) ctx.getBean("applyTaskManager");

		String subflowId = request.getParameter("id");
		HttpSession session = request.getSession();

		session.setAttribute("subflowId", subflowId);
		session.setAttribute("apply", atm.getPassedTaskFromSubflow(subflowId));
		return mapping.findForward("approve");
	}

	// 保存审核
	public ActionForward approve(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyTaskManager atm = (ApplyTaskManager) ctx.getBean("applyTaskManager");
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		String subflowId = request.getParameter("id");
		String approveResult = request.getParameter("approveResult");
		String approveRemark = request.getParameter("approveRemark");

		atm.approve(subflowId, approveResult, approveRemark, userInfo);
//这里还能减少对数据库的操作吗？
		String applyId = am.getSubflow(subflowId).getApplyId();
		String name = am.loadApply(applyId).getName();
		if (approveResult.equals(AcceptanceConstant.PASSED)) {
			log(request, "审核通过（工程名称为：" + name + "）", "验收交维");
			return forwardInfoPage(mapping, request, "approvePass");
		} else {
			log(request, "审核未通过（工程名称为：" + name + "）", "验收交维");
			return forwardInfoPage(mapping, request, "approveNotPass");
		}
	}

	// 考核评分页面准备数据
	public ActionForward examLink(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyTaskManager atm = (ApplyTaskManager) ctx.getBean("applyTaskManager");

		String subflowId = request.getParameter("id");
		Apply apply = atm.getExamTask(subflowId);
		HttpSession session = request.getSession();
		session.setAttribute("apply", apply);

		return mapping.findForward("exam");
	}

	// 保存考核评分
	public ActionForward exam(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");
		ApplyTaskManager atm = (ApplyTaskManager) ctx.getBean("applyTaskManager");
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		String subflowId = request.getParameter("id");
		/*String score = request.getParameter("entityScore");
		String remark = request.getParameter("entityRemark");*/
		
		//获得日常考核对象
		AppraiseDailyBean appraiseDailyBean = (AppraiseDailyBean)request.getSession().getAttribute("appraiseDailyDailyBean");
		List<AppraiseDailyBean> speicalBeans=(List<AppraiseDailyBean>)request.getSession().getAttribute("appraiseDailySpecialBean");
		am.saveEvaluate(userInfo,  subflowId,appraiseDailyBean,speicalBeans);
		log(request,"考核评分（工程名称为：" + atm.getExamTask(subflowId).getName() + "）","验收交维");
		return forwardInfoPage(mapping, request, "examSuccess");
	}

	// 待办工作
	public ActionForward findToDoWork(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		super.setPageReset(request);
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");

		String processName = request.getParameter("process_name");
		String taskName = request.getParameter("task_name");
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		List<Apply> list = am.getToDoWork(userInfo, taskName, processName);
		request.setAttribute("task_name", taskName);
		request.setAttribute("process_name", processName);
		request.getSession().setAttribute("users", am.getUsers());
		request.getSession().setAttribute("result", list);
		request.getSession().setAttribute("number", list.size());
		return mapping.findForward("toDoWork");
	}

	public ActionForward getFinishedWork(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		super.setPageReset(request);
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		List<Apply> list = am.getFinishedWork(userInfo);
		request.getSession().setAttribute("users", am.getUsers());
		request.getSession().setAttribute("result", list);
		request.getSession().setAttribute("number", list.size());
		return mapping.findForward("finishedWork");
	}

	// 查询页面准备数据--扩展
	public ActionForward queryLink(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		
		WebApplicationContext ctx = getWebApplicationContext();
		OverhaulManager ohm = (OverhaulManager) ctx.getBean("overhaulManager");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List<Contractor> list = ohm.getContractors(userInfo);
		request.getSession().setAttribute("list", list);
		request.getSession().setAttribute("deptype", userInfo.getDeptype());
		request.getSession().setAttribute("conId", userInfo.getDeptID());
		request.getSession().setAttribute("conName", userInfo.getDeptName());
		
		// 清空session中的查询条件和查询结果
		request.getSession().removeAttribute("queryCondition");
		request.getSession().removeAttribute("result");
		return mapping.findForward("queryLink");
	}

	// 查询结果--通过QueryBean
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		QueryBean queryBean = (QueryBean) form;
		// 判断formbean中是否有查询需要的相关数据，如果没有则将FormBean中的数据发到session中，否则从session中取出以前的数据
		if (null == request.getParameter("isQuery")) {
			queryBean = (QueryBean) request.getSession().getAttribute("queryCondition");
		} else {
			request.getSession().setAttribute("queryCondition", queryBean);
		}
		if (queryBean == null) {
			queryBean = new QueryBean();
		}
		if(user.getDeptype().equals("2")){
			queryBean.setContractorId(user.getDeptID());
		}
		// 保持url到session中的S_BACK_URL变量
		super.setPageReset(request);
		request.getSession().setAttribute("result", am.query(queryBean));
		
		request.setAttribute("task_names", queryBean.getTaskNames());
		return mapping.findForward("result");
	}

	// 查询中继段
	public ActionForward queryFromCable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");
		DictionaryService ds = (DictionaryService) ctx.getBean("dictionaryService");
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		QueryBean queryBean = (QueryBean) form;
		// 判断formbean中是否有查询需要的相关数据，如果没有则将FormBean中的数据发到session中，否则从session中取出以前的数据
		if (null == request.getParameter("isQuery")) {
			queryBean = (QueryBean) request.getSession().getAttribute("queryCondition");
		} else {
			request.getSession().setAttribute("queryCondition", queryBean);
		}
		if (queryBean == null) {
			queryBean = new QueryBean();
		}
		// //保持url到session中的S_BACK_URL变量
		super.setPageReset(request);
		Map<String, String> property_right = ds.loadDictByAssortment("property_right", user.getRegionid());
		Map<String, String> cabletype = ds.loadDictByAssortment("cabletype",user.getRegionid());
		Map<String, String> layingmethod = ds.loadDictByAssortment("layingmethod", user.getRegionid());

		request.getSession().setAttribute("property_right", property_right);
		request.getSession().setAttribute("cabletype", cabletype);
		request.getSession().setAttribute("layingmethod", layingmethod);

		request.getSession().setAttribute("result", am.queryCable(queryBean));
		return mapping.findForward("cableResult");
	}

	// 查看详细
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");

		String id = request.getParameter("id");
		String iswin = request.getParameter("param");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");

		request.getSession().setAttribute("iswin", iswin);
		request.getSession().setAttribute("apply", am.loadApply(id, userInfo));
		return mapping.findForward("view");
	}

	// Ajax需要用到的值(分页，保存checkbox选中)
	public ActionForward setValue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		String value = request.getParameter("value");
		String type = request.getParameter("type");

		HttpSession session = request.getSession();
		Set<String> set = (Set<String>) session.getAttribute("choose");
		if (type.equals("true")) {
			set.add(value);
		} else {
			if (set.contains(value))
				set.remove(value);
		}
		return null;
	}

	// 载入派单流程图页面
	public ActionForward viewAcceptanceProcess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute( "LOGIN_USER");
		String processName = request.getParameter("process_name");
		String taskName = request.getParameter("task_name");
		String taskNames = request.getParameter("task_names");
		ApplyManager bo = (ApplyManager) ctx.getBean("applyManager");
		String condition = "";
		List waitHandleTaskNum = bo.queryForHandleAcceptanceNum(condition,userInfo);
		request.setAttribute("wait_allot_task_num", waitHandleTaskNum.get(0));
		request.setAttribute("wait_claim_task_num", waitHandleTaskNum.get(1));
		request.setAttribute("wait_task_approve_num", waitHandleTaskNum.get(2));
		request.setAttribute("wait_record_data_num", waitHandleTaskNum.get(3));
		request.setAttribute("wait_record_approve_num", waitHandleTaskNum.get(4));
		request.setAttribute("wait_evaluate_num", waitHandleTaskNum.get(5));
		request.setAttribute("process_name", processName);
		request.setAttribute("task_name", taskName);
		if (taskNames != null) {
			request.setAttribute("task_names", taskNames.split(","));
		}
		if (request.getParameter("forward") != null&& !"".equals(request.getParameter("forward").trim())) {
			return mapping.findForward(request.getParameter("forward"));
		}
		return mapping.findForward("view_acceptance_process");
	}

	/**
	 * 执行取消验收交维任务表单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancelAcceptanceForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String applyId = request.getParameter("apply_id");
		request.setAttribute("apply_id", applyId);
		return mapping.findForward("cancel_acceptance");
	}

	/**
	 * 执行取消验收交维任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward cancelAcceptance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");
		ApplyBean applyBean = (ApplyBean) form;
		am.cancelAcceptance(applyBean, userInfo);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		String script = "<script type='text/javascript'>";
		script += "window.opener.location.href='" + url + "';";
		script += "window.close();";
		script += "</script>";
		outPrint(response,script);
		return null;
		// return super.forwardInfoPageWithUrl(mapping, request,
		// "CANCEL_ACCEPTANCE_SUCCESS", url);
	}
}