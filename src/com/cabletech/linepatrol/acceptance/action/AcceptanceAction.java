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

	// ��ʼ��������
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

	// ��ʼ��������
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

	// �������������Դ
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

	// ��������ӹ�������--Ajax
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

	// ��������ӹܵ�����--Ajax
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

	// �������뱣��
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
			log(request, "�������뱣�棨��������Ϊ��" + applyBean.getName() + "��", "���ս�ά");
			return forwardInfoPage(mapping, request, "inspectApply");
		}
	}

	// �����׼׼����Ϣ
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

	// �����׼
	public ActionForward recheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		String id = request.getParameter("id");
		am.recheck(id, userInfo);
		log(request, "�����׼����������Ϊ��" + am.loadApply(id).getName() + "��", "���ս�ά");
		return forwardInfoPage(mapping, request, "recheckSuccess");
	}

	// �����е������
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

		// ��������
		String type = request.getParameter("type");

		// ��������
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

	// �����е���ܵ�
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

		// ��������
		String type = request.getParameter("type");

		// ��������
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

	// ������¼�����
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
	
	// ������¼��ܵ�
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

	// �鿴¼��Ĺ���
	public ActionForward viewCable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");

		String id = request.getParameter("id");
		request.setAttribute("cable", am.loadCable(id));

		return mapping.findForward("viewCable");
	}

	// �鿴¼��Ĺܵ�
	public ActionForward viewPipe(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");

		String id = request.getParameter("id");
		request.setAttribute("pipe", am.loadPipe(id));

		return mapping.findForward("viewPipe");
	}

	// ������¼��Ĺܵ������
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

	// ������ά��Ϣ
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

	// ��������
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
			log(request, "�������루��������Ϊ��" + applyBean.getName() + "��", "���ս�ά");
			return forwardInfoPage(mapping, request, "AcceptanceApply");
		}
	}

	// ��������ҳ��׼������
	public ActionForward allotTasks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");

		String id = request.getParameter("id");

		request.getSession().setAttribute("id", id);
		request.getSession().setAttribute("apply", am.loadApply(id));
		request.getSession().setAttribute("dept", am.getDeptOptions());
		// ��ʼֵ
		request.getSession().setAttribute("type", "1");
		request.getSession().setAttribute("ids", "");
		request.getSession().setAttribute("names", "");
		request.getSession().setAttribute("specify",new HashMap<String, String>());

		return mapping.findForward("allotTasks");
	}

	// ��������ҳ��--��׼���ͱ�����session��
	public ActionForward changeType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		String type = request.getParameter("type");
		request.getSession().setAttribute("type", type);
		return null;
	}

	// ��������ҳ��--ѡ��ʱ����ά���ƺʹ�άid������session�У�
	public ActionForward saveChoose(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		String names = request.getParameter("names");
		String ids = request.getParameter("ids");
		request.getSession().setAttribute("names", names);
		request.getSession().setAttribute("ids", ids);
		return null;
	}

	// ��������ҳ��--ָ��ʱ����Դid�ʹ�άid������session�У�
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

	// �����������
	public ActionForward allot(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyTaskManager atm = (ApplyTaskManager) ctx.getBean("applyTaskManager");
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		String id = request.getParameter("id");
		String type = request.getParameter("type");//ָ����ά ѡ���ά
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
		log(request, "����������񣨹�������Ϊ��" + name + "��", "���ս�ά");
		return forwardInfoPage(mapping, request, "allotSuccess");
	}

	// ��������ҳ��׼������
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

	// ������������
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
		log(request, "�����������񣨹�������Ϊ��" + name + "��", "���ս�ά");
		return forwardInfoPage(mapping, request, "claimSuccess");
	}

	// ��׼����ҳ��׼������
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

	// �����׼����
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
			log(request, "�����׼���񣨹�������Ϊ��" + name + "��", "���ս�ά");
		}

		return forwardInfoPage(mapping, request, "checkSuccess");
	}

	// �õ�ĳһ�����µ������б�
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

	// ��ʼ¼������--��������¼��͸���¼��
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
			// ҳ��׼������
			RcBean rcBean = new RcBean();

			String times = ctm.loadCableResultFromCableId(id, objectId);
			ApplyCable cable = atm.loadCable(objectId);

			request.setAttribute("times", times);
			request.setAttribute("cable", cable);
			if (type.equals("false")) { // ����
				rcBean = ctm.loadRcBean(id, objectId);
			}

			rcBean.setBuildUnit("�����ƶ�");
			rcBean.setPlanDate(DateUtil.DateToString(cable.getPlanAcceptanceTime()));
			request.setAttribute("rcBean", rcBean);
			return mapping.findForward("recordCableData");
		} else {
			// ҳ��׼������
			RpBean rpBean = new RpBean();

			String times = ptm.loadPipeResultFromPipeId(apply.getId(), objectId);
			ApplyPipe pipe = atm.loadPipe(objectId);
			request.setAttribute("times", times);
			request.setAttribute("pipe", pipe);
			if (type.equals("false")) { // ����
				rpBean = ptm.loadRpBean(id, objectId);
			}

			rpBean.setBuildUnit("�����ƶ�");
			rpBean.setPlanDate(DateUtil.DateToString(pipe
					.getPlanAcceptanceTime()));
			request.setAttribute("rpBean", rpBean);
			return mapping.findForward("recordPipeData");
		}
	}

	// �޸�����
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
			log(request, "�޸Ĺ������ݣ���������Ϊ��" + apply.getName() + "��", "���ս�ά");
			return mapping.findForward("editCableData");
		} else {
			request.setAttribute("rpBean", ptm.editRpBean(id, objectId));
			log(request, "�޸Ĺܵ����ݣ���������Ϊ��" + apply.getName() + "��", "���ս�ά");
			return mapping.findForward("editPipeData");
		}
	}

	// �������¼������
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
		// ��֤�ļ����Ѵ��ڵ��ļ�����OLDFILE����ϴ����ļ�����FILES��
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
			log(request, "�������¼�����ݣ���������Ϊ��" + name + "��", "���ս�ά");
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
			html = "���ظ��Ĺ��±��";
		}
		this.outPrint(response, html);
		return null;
	}

	// ����ܵ�¼������
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
		log(request, "����ܵ�¼�����ݣ���������Ϊ��" + name + "��", "���ս�ά");
		return forwardInfoPage(mapping, request, "recordPipeSuccess");
	}

	// �鿴����¼������
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

	// �鿴�ܵ�¼������
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

	// �ύ��˱��棬��ά�ύ�ܵ������
	public ActionForward push(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyTaskManager atm = (ApplyTaskManager) ctx.getBean("applyTaskManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		String id = request.getParameter("id");
		String approver = request.getParameter("approver");

		// ��֤������Դ����¼��
		Apply apply = (Apply) session.getAttribute("apply");
		if (atm.validateRecord(apply)) {
			atm.push(id, approver, userInfo);
		} else {
			return forwardInfoPage(mapping, request, "recordError");
		}
		log(request, "��˱��棨��������Ϊ��" + apply.getName() + "��", "���ս�ά");
		return forwardInfoPage(mapping, request, "subSuccess");
	}

	// �鿴�������ս��
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

	// �鿴�ܵ����ս��
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

	// ���ҳ��׼������
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

	// �������
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
//���ﻹ�ܼ��ٶ����ݿ�Ĳ�����
		String applyId = am.getSubflow(subflowId).getApplyId();
		String name = am.loadApply(applyId).getName();
		if (approveResult.equals(AcceptanceConstant.PASSED)) {
			log(request, "���ͨ������������Ϊ��" + name + "��", "���ս�ά");
			return forwardInfoPage(mapping, request, "approvePass");
		} else {
			log(request, "���δͨ������������Ϊ��" + name + "��", "���ս�ά");
			return forwardInfoPage(mapping, request, "approveNotPass");
		}
	}

	// ��������ҳ��׼������
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

	// ���濼������
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
		
		//����ճ����˶���
		AppraiseDailyBean appraiseDailyBean = (AppraiseDailyBean)request.getSession().getAttribute("appraiseDailyDailyBean");
		List<AppraiseDailyBean> speicalBeans=(List<AppraiseDailyBean>)request.getSession().getAttribute("appraiseDailySpecialBean");
		am.saveEvaluate(userInfo,  subflowId,appraiseDailyBean,speicalBeans);
		log(request,"�������֣���������Ϊ��" + atm.getExamTask(subflowId).getName() + "��","���ս�ά");
		return forwardInfoPage(mapping, request, "examSuccess");
	}

	// ���칤��
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

	// ��ѯҳ��׼������--��չ
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
		
		// ���session�еĲ�ѯ�����Ͳ�ѯ���
		request.getSession().removeAttribute("queryCondition");
		request.getSession().removeAttribute("result");
		return mapping.findForward("queryLink");
	}

	// ��ѯ���--ͨ��QueryBean
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		QueryBean queryBean = (QueryBean) form;
		// �ж�formbean���Ƿ��в�ѯ��Ҫ��������ݣ����û����FormBean�е����ݷ���session�У������session��ȡ����ǰ������
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
		// ����url��session�е�S_BACK_URL����
		super.setPageReset(request);
		request.getSession().setAttribute("result", am.query(queryBean));
		
		request.setAttribute("task_names", queryBean.getTaskNames());
		return mapping.findForward("result");
	}

	// ��ѯ�м̶�
	public ActionForward queryFromCable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");
		DictionaryService ds = (DictionaryService) ctx.getBean("dictionaryService");
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		QueryBean queryBean = (QueryBean) form;
		// �ж�formbean���Ƿ��в�ѯ��Ҫ��������ݣ����û����FormBean�е����ݷ���session�У������session��ȡ����ǰ������
		if (null == request.getParameter("isQuery")) {
			queryBean = (QueryBean) request.getSession().getAttribute("queryCondition");
		} else {
			request.getSession().setAttribute("queryCondition", queryBean);
		}
		if (queryBean == null) {
			queryBean = new QueryBean();
		}
		// //����url��session�е�S_BACK_URL����
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

	// �鿴��ϸ
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

	// Ajax��Ҫ�õ���ֵ(��ҳ������checkboxѡ��)
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

	// �����ɵ�����ͼҳ��
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
	 * ִ��ȡ�����ս�ά�����
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
	 * ִ��ȡ�����ս�ά����
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