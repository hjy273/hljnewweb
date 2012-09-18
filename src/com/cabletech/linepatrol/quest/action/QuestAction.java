package com.cabletech.linepatrol.quest.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.util.VelocityUtil;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.linepatrol.quest.beans.QuestionTable;
import com.cabletech.linepatrol.quest.module.QuestContractor;
import com.cabletech.linepatrol.quest.module.QuestGuidelineClass;
import com.cabletech.linepatrol.quest.module.QuestGuidelineItem;
import com.cabletech.linepatrol.quest.module.QuestGuidelineSort;
import com.cabletech.linepatrol.quest.module.QuestIssue;
import com.cabletech.linepatrol.quest.module.QuestReviewObject;
import com.cabletech.linepatrol.quest.module.QuestType;
import com.cabletech.linepatrol.quest.services.QuestBo;

public class QuestAction extends BaseInfoBaseDispatchAction {
	private static Logger logger = Logger.getLogger(QuestAction.class.getName());

	private QuestBo getQuestBo() {
		WebApplicationContext ctx = getWebApplicationContext();
		return (QuestBo) ctx.getBean("questBo");
	}

	//�����ʾ�ǰ������Ϣ
	public ActionForward addQuestForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Map map = getQuestBo().addQuestForm();
		List questReviewObjects = (List) map.get("questReviewObjects");
		List questTypes = (List) map.get("questTypes");
		request.setAttribute("questReviewObjects", questReviewObjects);
		request.setAttribute("questTypes", questTypes);
		return mapping.findForward("addQuest");
	}

	//���淢���ʾ���Ϣ
	public ActionForward addQuest(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String saveflag = request.getParameter("saveflag");//�������ֱ��滹���ݴ�
		String typeId = request.getParameter("queryType");//�ʾ�����
		String issueId = request.getParameter("issueId");//�ʾ�ID
		String issueName = request.getParameter("issueName");//�ʾ�����
		String companyId = request.getParameter("comIds");//��������
		String remark = request.getParameter("remark");//�ʾ�ע
		String conId = request.getParameter("contractorId");//�ʾ��ά��˾
		String[] itemIdArray = request.getParameterValues("itemId");//����ָ����IDS
		String mobileId = request.getParameter("mobileId");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String creator = userInfo.getUserID();//�ʾ�����
		try {
			if (QuestIssue.TEMPSAVE.equals(saveflag)) {
				//�ݴ�
				getQuestBo().tempSave(creator, typeId, issueId, issueName, conId, companyId, remark,
						QuestIssue.TEMPSAVE, itemIdArray);
				log(request, "�ݴ淢���ʾ���Ϣ���ʾ�����Ϊ��" + issueName + "��", "�ʾ����");
				return forwardInfoPage(mapping, request, "tempSaveQuestSuccess");
			} else {
				//����
				getQuestBo().addQuest(creator, typeId, issueId, issueName, conId, companyId, remark, itemIdArray,
						mobileId);
				log(request, "���淢���ʾ���Ϣ���ʾ�����Ϊ��" + issueName + "��", "�ʾ����");
				return forwardInfoPage(mapping, request, "addQuestSuccess");
			}
		} catch (ServiceException e) {
			logger.info("�ʾ�������������Ϣ��" + e.getMessage());
			return forwardErrorPage(mapping, request, "addQuestError");
		}
	}

	//��������ʾ��б�
	public ActionForward perfectIssueList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		List list = getQuestBo().perfectIssueList();
		request.getSession().setAttribute("list", list);
		return mapping.findForward("perfectIssueList");
	}

	//ɾ����ʱ������ʾ�
	public ActionForward deleteQuest(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		QuestBo qb = (QuestBo) ctx.getBean("questBo");
		String issueId = request.getParameter("issueId");
		try {
			String issueName = qb.get(issueId).getName();
			getQuestBo().deleteQuest(issueId);

			log(request, "ɾ����ʱ�����ʾ��ʾ�����Ϊ��" + issueName + "��", "�ʾ����");
			return forwardInfoPage(mapping, request, "deleteQuestSuccess");
		} catch (ServiceException e) {
			e.printStackTrace();
			logger.info("ɾ���ʾ����������Ϣ��" + e.getMessage());
			return forwardErrorPage(mapping, request, "deleteQuestError");
		}
	}

	//��ѯ�����б�
	public ActionForward querySortList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/json; charset=GBK");
		String classId = request.getParameter("classId");
		List sortList = getQuestBo().querySortList(classId);
		JSONArray ja = JSONArray.fromObject(sortList);
		outPrint(response,ja.toString());
		return null;
	}

	//��ѯ�������б�
	public ActionForward queryItemList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String issueId = request.getParameter("issueId");
		String sortId = request.getParameter("sortId");
		response.setContentType("text/json; charset=GBK");
		List itemList = getQuestBo().queryItemList(sortId, issueId);
		JSONArray ja = JSONArray.fromObject(itemList);
		outPrint(response,ja.toString());
		logger.info("�������" + ja.toString());
		return null;
	}

	//���������
	public ActionForward addItem(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String items = request.getParameter("items");
		String issueId = request.getParameter("issueId");
		getQuestBo().addItem(items, issueId);
		return null;
	}

	//��ʾ������
	public ActionForward showItem(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String issueId = request.getParameter("issueId");
		response.setContentType("text/json; charset=GBK");
		List itemList = getQuestBo().queryItemListByIssueId(issueId);
		JSONArray ja = JSONArray.fromObject(itemList);
		outPrint(response,ja.toString());
		logger.info("ͨ������ID����������" + ja.toString());
		return null;
	}

	//ɾ��������
	public ActionForward deleteItem(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String issueId = request.getParameter("issueId");
		String issueItemId = request.getParameter("issueItemId");
		response.setContentType("text/json; charset=GBK");
		getQuestBo().deleteItemByIssueItemId(issueId, issueItemId);
		List itemList = getQuestBo().queryItemListByIssueId(issueId);
		JSONArray ja = JSONArray.fromObject(itemList);
		outPrint(response,ja.toString());
		logger.info("ͨ������ID����������" + ja.toString());
		return null;
	}

	//����������
	public ActionForward addIssueItem(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String issueId = request.getParameter("issueId");
		StringBuffer buf = new StringBuffer("");
		buf.append("<script type='text/javascript'>");
		buf.append("window.onload=function(){");
		buf.append("window.location.href=\"/WebApp/questAction.do?method=editQuestForm&issueId=");
		buf.append(issueId);
		buf.append("\";}");
		buf.append("</script>");
		response.setCharacterEncoding("GBK");
		outPrint(response,buf.toString());
		System.out.println("*******buf.toString():" + buf.toString());
		return null;
	}

	public ActionForward addCompany(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String companyId = request.getParameter("companyId");
		List companys = getQuestBo().getCompanyInfoList();
		request.setAttribute("companys", companys);
		request.setAttribute("companyId", companyId);
		return mapping.findForward("addCompany");
	}

	public ActionForward addCompanyFinish(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String[] companyIds = request.getParameterValues("companyIdSel");
		Map map = getQuestBo().addCompanyFinish(companyIds);
		String comIds = (String) map.get("comIds");
		String companyNames = (String) map.get("companyNames");
		map.put("comIds", comIds);
		map.put("companyNames", companyNames);
		request.setAttribute("comIds", comIds);
		request.setAttribute("companyNames", companyNames);
		return mapping.findForward("closeAddCompany");
	}

	public ActionForward editQuestForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String issueIdPage = request.getParameter("issueId");
		Map map = getQuestBo().editQuestForm(issueIdPage);
		QuestIssue issue = (QuestIssue) map.get("questIssue");
		String companyId = (String) map.get("comIds");
		String comNames = (String) map.get("comNames");
		String issueId = issue.getId();
		String[] userIds = (String[]) map.get("userIds");
		List questTypes = (List) map.get("questTypes");
		request.setAttribute("issue", issue);
		request.setAttribute("companyId", companyId);
		request.setAttribute("comNames", comNames);
		request.setAttribute("issueId", issueId);
		request.setAttribute("questTypes", questTypes);
		request.setAttribute("userIds", userIds);
		return mapping.findForward("editQuest");
	}

	public ActionForward getIssueFeedbackList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String conId = userInfo.getDeptID();
		List list = getQuestBo().getIssueFeedbackList(conId);
		request.getSession().setAttribute("list", list);
		return mapping.findForward("issueFeedbackList");
	}

	public ActionForward addFeedbackIssueForm1(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String conId = userInfo.getDeptID();
		String issueId = request.getParameter("issueId");
		Map map = getQuestBo().addFeedbackIssueForm1(issueId, conId);
		QuestIssue issue = (QuestIssue) map.get("issue");
		String comIds = (String) map.get("comIds");
		String comNames = (String) map.get("comNames");
		request.setAttribute("issue", issue);
		request.setAttribute("comIds", comIds);
		request.setAttribute("comNames", comNames);
		return mapping.findForward("addFeedbackIssueForm1");
	}

	public ActionForward addFeedbackIssueForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String issueId = request.getParameter("issueId");
		String comIds = request.getParameter("comIds");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String conId = userInfo.getDeptID();
		Map map = getQuestBo().editFeedbackIssueForm(issueId, comIds, conId);
		QuestionTable table = (QuestionTable) map.get("table");
		String itemIds = (String) map.get("itemIds");
		String issueName = (String) map.get("issueName");
		String issueType = (String) map.get("issueType");
		Map issueResultMap = (Map) map.get("issueResultMap");
		Map context = new HashMap();
		context.put("issueId", issueId);
		context.put("table", table);
		context.put("comIds", comIds);
		context.put("itemIds", itemIds);
		context.put("issueName", issueName);
		context.put("issueType", issueType);
		context.put("issueResultMap", issueResultMap);
		try {
			VelocityUtil.rendWebResponse("com/cabletech/linepatrol/quest/vm/ques_result.vm", context, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ActionForward showRule(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String comId = request.getParameter("comId");
		String itemId = request.getParameter("itemId");
		Map map = getQuestBo().getRuleInfo(itemId);
		List ruleInfoList = (List) map.get("list");
		QuestGuidelineItem questitem = (QuestGuidelineItem) map.get("questitem");
		request.setAttribute("ruleInfoList", ruleInfoList);
		request.setAttribute("questitem", questitem);
		request.setAttribute("comId", comId);
		return mapping.findForward("showRule");
	}

	public ActionForward addFeedbackIssue(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String comIds = request.getParameter("comIds");
		String itemIds = request.getParameter("itemIds");
		String saveflag = request.getParameter("saveflag");
		String issueId = request.getParameter("issueId");
		String[] comIdArray = comIds.split(",");
		String[] itemIdArray = itemIds.split(",");
		String inputId;
		String inputValue;
		String conId = userInfo.getDeptID();
		String state = saveflag;
		//ɾ����ǰ���ʾ���
		try {
			getQuestBo().deleteIssueResultByIssueId(issueId, conId);
			//issue = getQuestBo().getQuestIssueByIssueId(issueId);
			for (int i = 0; i < comIdArray.length; i++) {
				for (int j = 0; j < itemIdArray.length; j++) {
					String gItemId = getQuestBo().getGItemIdByIssueIdAndItemId(issueId, itemIdArray[j]);
					inputId = comIdArray[i] + itemIdArray[j];
					inputValue = request.getParameter(inputId);
					if (inputValue != null) {
						if (QuestContractor.SAVE.equals(saveflag)) {
							getQuestBo().saveIssueResult(issueId, inputValue, comIdArray[i], gItemId, conId);
						} else {
							getQuestBo().tempSaveFeedbackIssue(issueId, inputValue, comIdArray[i], gItemId, conId);
						}
					}
				}
			}
			//�ı�״̬
			getQuestBo().changeStateByIssueIdAndConId(issueId, conId, state, userInfo, saveflag);

			if (QuestContractor.SAVE.equals(saveflag)) {
				log(request, "����ʾ������ʾ�������Ϊ��" + getQuestBo().get(issueId).getName() + "��", "�ʾ����");
				return forwardInfoPage(mapping, request, "addFeedbackIssueSuccess");
			} else {
				log(request, "�ݴ��ʾ������ʾ�������Ϊ��" + getQuestBo().get(issueId).getName() + "��", "�ʾ����");
				return forwardInfoPage(mapping, request, "tempSaveFeedbackIssueSuccess");
			}
		} catch (ServiceException e) {
			logger.info("�ʾ�������������Ϣ��" + e.getMessage());
			return forwardErrorPage(mapping, request, "addFeedbackIssueError");
		}
	}

	public ActionForward questFeedbackStat(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String issueId = request.getParameter("issueId");
		Map map = getQuestBo().questFeedbackStat(issueId);
		List list = (List) map.get("list");
		String comNum = (String) map.get("comNum");
		BasicDynaBean issueBean = (BasicDynaBean) map.get("issueBean");
		String issueName = (String) issueBean.get("questionnaire_name");
		request.setAttribute("list", list);
		request.setAttribute("comNum", comNum);
		request.setAttribute("issueBean", issueBean);
		request.setAttribute("issueName", issueName);
		return mapping.findForward("questFeedbackStat");
	}

	public ActionForward questFeedbackStatList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = getQuestBo().questFeedbackStatList();
		request.getSession().setAttribute("list", list);
		return mapping.findForward("questFeedbackStatList");
	}

	public ActionForward questComManagerList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = getQuestBo().questComManagerList();
		request.getSession().setAttribute("list", list);
		return mapping.findForward("questComManagerList");
	}

	public ActionForward addComForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("addCom");
	}

	public ActionForward judgeComExists(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String comName = request.getParameter("comName");
		String comId = request.getParameter("comId");
		String flag = getQuestBo().judgeComExists(comName, comId);
		StringBuffer sb = new StringBuffer();
		sb.append(flag);
		System.out.println("*******buf.toString():" + sb.toString());
		outPrint(response, sb.toString());
		
		return null;
	}

	public ActionForward editComForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String comId = request.getParameter("comId");
		QuestReviewObject comInfo = null;
		comInfo = getQuestBo().editComForm(comId);
		request.setAttribute("comInfo", comInfo);
		return mapping.findForward("editCom");
	}

	public ActionForward editCom(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String comName = request.getParameter("comName");
		String operator = request.getParameter("operator");
		try {
			getQuestBo().editCom(id, comName);
			if ("add".equals(operator)) {
				log(request, "��Ӳ������󣨲�����������Ϊ��" + comName + "��", "�ʾ����");
				return forwardInfoPage(mapping, request, "addComQuestSuccess");
			} else {
				log(request, "�޸Ĳ������󣨲�����������Ϊ��" + comName + "��", "�ʾ����");
				return forwardInfoPage(mapping, request, "editComQuestSuccess");
			}
		} catch (ServiceException e) {
			logger.info("�޸Ĳ����������������Ϣ��" + e.getMessage());
			return forwardErrorPage(mapping, request, "editComQuestError");
		}
	}

	public ActionForward deleteCom(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String comId = request.getParameter("comId");
		try {
			getQuestBo().deleteCom(comId);
			log(request, "ɾ����������", "�ʾ����");
			return forwardInfoPage(mapping, request, "deleteComQuestSuccess");
		} catch (ServiceException e) {
			logger.info("ɾ�������������������Ϣ��" + e.getMessage());
			return forwardErrorPage(mapping, request, "deleteComQuestError");
		}
	}

	public ActionForward questTypeManagerList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = getQuestBo().questTypeManagerList();
		request.getSession().setAttribute("list", list);
		return mapping.findForward("questTypeManagerList");
	}

	public ActionForward addTypeForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("addType");
	}

	public ActionForward judgeTypeExists(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String typeName = request.getParameter("typeName");
		String typeId = request.getParameter("typeId");
		String flag = getQuestBo().judgeTypeExists(typeName, typeId);
		StringBuffer sb = new StringBuffer();
		sb.append(flag);
		System.out.println("*******buf.toString():" + sb.toString());
		outPrint(response,sb.toString());
		
		return null;
	}

	public ActionForward editTypeForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String typeId = request.getParameter("typeId");
		//String remark = request.getParameter("remark");
		QuestType typeInfo = null;
		typeInfo = getQuestBo().editTypeForm(typeId);
		request.setAttribute("typeInfo", typeInfo);
		return mapping.findForward("editType");
	}

	public ActionForward editType(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String typeName = request.getParameter("typeName");
		String remark = request.getParameter("remark");
		String operator = request.getParameter("operator");
		try {
			getQuestBo().editType(id, typeName, remark);
			if ("add".equals(operator)) {
				log(request, "����ʾ����ͣ��ʾ���������Ϊ��" + typeName + "��", "�ʾ����");
				return forwardInfoPage(mapping, request, "addTypeQuestSuccess");
			} else {
				log(request, "�޸��ʾ����ͣ��ʾ���������Ϊ��" + typeName + "��", "�ʾ����");
				return forwardInfoPage(mapping, request, "editTypeQuestSuccess");
			}
		} catch (ServiceException e) {
			logger.info("�޸��ʾ����ͳ���������Ϣ��" + e.getMessage());
			return forwardErrorPage(mapping, request, "editTypeQuestError");
		}
	}

	public ActionForward deleteType(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String typeId = request.getParameter("typeId");
		try {
			String flag = getQuestBo().deleteType(typeId);
			if ("success".equals(flag)) {
				log(request, "ɾ��ָ������", "�ʾ����");
				return forwardInfoPage(mapping, request, "deleteTypeQuestSuccess");
			} else {
				log(request, "ɾ��ָ������ʧ��", "�ʾ����");
				return forwardInfoPage(mapping, request, "canntDeleteManagerItemSuccess");
			}
		} catch (ServiceException e) {
			logger.info("ɾ���ʾ����ͳ���������Ϣ��" + e.getMessage());
			return forwardErrorPage(mapping, request, "deleteTypeQuestError");
		}
	}

	public ActionForward questClassManagerList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = getQuestBo().questClassManagerList();
		request.getSession().setAttribute("list", list);
		return mapping.findForward("questClassManagerList");
	}

	public ActionForward judgeClassExists(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String className = request.getParameter("className");
		String typeId = request.getParameter("typeId");
		String classId = request.getParameter("classId");
		String flag = getQuestBo().judgeClassExists(typeId, className, classId);
		StringBuffer sb = new StringBuffer();
		sb.append(flag);
		outPrint(response, sb.toString());
		return null;
	}

	public ActionForward addClassForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List types = getQuestBo().addClassForm();
		request.setAttribute("types", types);
		return mapping.findForward("addClass");
	}

	public ActionForward editClassForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String classId = request.getParameter("classId");
		Map map = getQuestBo().editClassForm(classId);
		List types = (List) map.get("types");
		QuestGuidelineClass classInfo = (QuestGuidelineClass) map.get("gclass");
		request.setAttribute("classInfo", classInfo);
		request.setAttribute("types", types);
		return mapping.findForward("editClass");
	}

	public ActionForward editClass(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String typeId = request.getParameter("typeId");
		String className = request.getParameter("className");
		String remark = request.getParameter("remark");
		String operator = request.getParameter("operator");
		try {
			getQuestBo().editClass(id, typeId, className, remark);
			if ("add".equals(operator)) {
				log(request, "���ָ�����ͣ�ָ����������Ϊ��" + className + "��", "�ʾ����");
				return forwardInfoPage(mapping, request, "addClassQuestSuccess");
			} else {
				log(request, "�޸�ָ�����ͣ�ָ����������Ϊ��" + className + "��", "�ʾ����");
				return forwardInfoPage(mapping, request, "editClassQuestSuccess");
			}
		} catch (ServiceException e) {
			logger.info("�޸�ָ�����ͳ���������Ϣ��" + e.getMessage());
			return forwardErrorPage(mapping, request, "editClassQuestError");
		}
	}

	public ActionForward questSortManagerList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = getQuestBo().questSortManagerList();
		request.getSession().setAttribute("list", list);
		return mapping.findForward("questSortManagerList");
	}

	public ActionForward judgeSortExists(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String sortName = request.getParameter("sortName");
		String classId = request.getParameter("classId");
		String sortId = request.getParameter("sortId");
		String flag = getQuestBo().judgeSortExists(classId, sortName, sortId);
		StringBuffer sb = new StringBuffer();
		sb.append(flag);
		outPrint(response,sb.toString());
		return null;
	}

	public ActionForward addSortForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List qclasses = getQuestBo().addSortForm();
		request.setAttribute("qclasses", qclasses);
		return mapping.findForward("addSort");
	}

	public ActionForward editSortForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String sortId = request.getParameter("sortId");
		Map map = getQuestBo().editSortForm(sortId);
		List qclasses = (List) map.get("qclasses");
		QuestGuidelineSort sortInfo = (QuestGuidelineSort) map.get("sort");
		request.setAttribute("qclasses", qclasses);
		request.setAttribute("sortInfo", sortInfo);
		return mapping.findForward("editSort");
	}

	public ActionForward editSort(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String classId = request.getParameter("classId");
		String sortName = request.getParameter("sortName");
		String remark = request.getParameter("remark");
		String operator = request.getParameter("operator");
		try {
			getQuestBo().editSort(id, classId, sortName, remark);
			if ("add".equals(operator)) {
				log(request, "���ָ����ָࣨ���������Ϊ��" + sortName + "��", "�ʾ����");
				return forwardInfoPage(mapping, request, "addSortQuestSuccess");
			} else {
				log(request, "�޸�ָ����ָࣨ���������Ϊ��" + sortName + "��", "�ʾ����");
				return forwardInfoPage(mapping, request, "editSortQuestSuccess");
			}
		} catch (ServiceException e) {
			logger.info("�޸�ָ��������������Ϣ��" + e.getMessage());
			return forwardErrorPage(mapping, request, "editSortQuestError");
		}
	}

	public ActionForward questItemManagerList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = getQuestBo().questItemManagerList();
		request.getSession().setAttribute("list", list);
		return mapping.findForward("questItemManagerList");
	}

	public ActionForward judgeItemExists(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String itemName = request.getParameter("itemName");
		String sortId = request.getParameter("sortId");
		String itemId = request.getParameter("itemId");
		String flag = getQuestBo().judgeItemExists(sortId, itemName, itemId);
		StringBuffer sb = new StringBuffer();
		sb.append(flag);
		outPrint(response,sb.toString());
		return null;
	}

	public ActionForward addItemForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//String itemId = request.getParameter("itemId");
		List sorts = getQuestBo().addItemForm();
		request.setAttribute("sorts", sorts);
		return mapping.findForward("addItem");
	}

	public ActionForward editItemForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String itemId = request.getParameter("itemId");
		//String flag = request.getParameter("flag");
		Map map = getQuestBo().editItemForm(itemId);
		List sorts = (List) map.get("sorts");
		List rules = (List) map.get("rules");
		QuestGuidelineItem itemInfo = (QuestGuidelineItem) map.get("item");
		request.setAttribute("sorts", sorts);
		request.setAttribute("itemInfo", itemInfo);
		request.setAttribute("rules", rules);
		//request.setAttribute("flag", flag);
		return mapping.findForward("editItem");
	}

	public ActionForward editItem(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String sortId = request.getParameter("sortId");
		String itemName = request.getParameter("itemName");
		String options = request.getParameter("options");
		String remark = request.getParameter("remark");
		//String operator = request.getParameter("operator");
		//String flag = request.getParameter("flag");
		String[] ruleAddName = request.getParameterValues("ruleAddName");
		try {
			getQuestBo().addManagerItem(id, sortId, itemName, options, remark, ruleAddName);
			//if("add".equals(operator) || "add".equals(flag)){
			//return forwardInfoPage(mapping, request,"addItemQuestSuccess");
			//}else{
			log(request, "�޸�ָ���ָ��������Ϊ��" + itemName + "��", "�ʾ����");
			return forwardInfoPage(mapping, request, "editItemQuestSuccess");
			//}
		} catch (ServiceException e) {
			logger.info("�޸�ָ�������������Ϣ��" + e.getMessage());
			return forwardErrorPage(mapping, request, "editItemQuestError");
		}
	}

	public ActionForward addManagerItem(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String sortId = request.getParameter("sortId");
		String itemName = request.getParameter("itemName");
		String options = request.getParameter("options");
		String remark = request.getParameter("remark");
		//String operator = request.getParameter("operator");
		//String flag = request.getParameter("flag");
		String[] ruleAddName = request.getParameterValues("ruleAddName");
		try {
			getQuestBo().addManagerItem(id, sortId, itemName, options, remark, ruleAddName);
			log(request, "���ָ���ָ��������Ϊ��" + itemName + "��", "�ʾ����");
			return forwardInfoPage(mapping, request, "addItemQuestSuccess");
		} catch (ServiceException e) {
			logger.info("���ָ�������������Ϣ��" + e.getMessage());
			return forwardErrorPage(mapping, request, "editItemQuestError");
		}
	}

	public ActionForward addRuleForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String itemId = request.getParameter("itemId");
		String sortId = request.getParameter("sortId");
		String itemName = request.getParameter("itemName");
		String options = request.getParameter("options");
		String remark = request.getParameter("remark");
		itemId = getQuestBo().addRuleForm(itemId, sortId, itemName, options, remark);
		request.setAttribute("itemId", itemId);
		return mapping.findForward("addRule");
	}

	public ActionForward addRule(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String itemId = request.getParameter("itemId");
		String ruleName = request.getParameter("ruleName");
		String mark = request.getParameter("mark");
		List rules = getQuestBo().addRule(itemId, ruleName, mark);
		request.setAttribute("itemId", itemId);
		request.setAttribute("rules", rules);
		return mapping.findForward("addRule");
	}

	public ActionForward deleteManagerRule(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ruleId = request.getParameter("ruleId");
		getQuestBo().deleteManagerRule(ruleId);
		return null;
	}

	public ActionForward addManagerRule(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String itemId = request.getParameter("itemId");
		String ruleName = request.getParameter("ruleName");
		getQuestBo().addManagerRule(itemId, ruleName);
		return null;
	}

	public ActionForward deleteManagerItem(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		QuestBo bo = (QuestBo) ctx.getBean("questBo");
		String itemId = request.getParameter("itemId");
		try {
			String flag = getQuestBo().deleteItemByItemId(itemId);
			if ("success".equals(flag)) {
				log(request, "ɾ��ָ����", "�ʾ����");
				return forwardInfoPage(mapping, request, "deleteManagerItemSuccess");
			} else {
				log(request, "ɾ��ָ����ʧ��", "�ʾ����");
				return forwardInfoPage(mapping, request, "canntDeleteManagerItemSuccess");
			}
		} catch (ServiceException e) {
			logger.info("ɾ��ָ�������������Ϣ��" + e.getMessage());
			return forwardErrorPage(mapping, request, "deleteManagerItemError");
		}
	}

	public ActionForward deleteSort(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String sortId = request.getParameter("sortId");
		try {
			String flag = getQuestBo().deleteSortBySortId(sortId);
			if ("success".equals(flag)) {
				log(request, "ɾ��ָ�����", "�ʾ����");
				return forwardInfoPage(mapping, request, "deleteSortQuestSuccess");
			} else {
				log(request, "ɾ��ָ�����ʧ��", "�ʾ����");
				return forwardInfoPage(mapping, request, "canntDeleteManagerItemSuccess");
			}
		} catch (ServiceException e) {
			logger.info("ɾ��ָ��������������Ϣ��" + e.getMessage());
			return forwardErrorPage(mapping, request, "deleteSortQuestError");
		}
	}

	public ActionForward deleteClass(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String classId = request.getParameter("classId");
		try {
			String flag = getQuestBo().deleteClassByClassId(classId);
			if ("success".equals(flag)) {
				log(request, "ɾ��ָ�����", "�ʾ����");
				return forwardInfoPage(mapping, request, "deleteClassQuestSuccess");
			} else {
				log(request, "ɾ��ָ�����ʧ��", "�ʾ����");
				return forwardInfoPage(mapping, request, "canntDeleteManagerItemSuccess");
			}
		} catch (ServiceException e) {
			logger.info("ɾ��ָ��������������Ϣ��" + e.getMessage());
			return forwardErrorPage(mapping, request, "deleteClassQuestError");
		}
	}

	public ActionForward queryRuleListByItemId(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/json; charset=GBK");
		String itemId = request.getParameter("itemId");
		List sortList = getQuestBo().queryRuleListByItemId(itemId);
		JSONArray ja = JSONArray.fromObject(sortList);
		outPrint(response,ja.toString());
		return null;
	}

	public ActionForward loadQuestIssueItem(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String queryType = request.getParameter("queryType");
		String itemIds = request.getParameter("itemIds");
		request.setAttribute("queryType", queryType);
		request.setAttribute("itemIds", itemIds);
		return mapping.findForward("loadQuestIssueItem");
	}

	public ActionForward showAllQuestIssueItem(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String queryType = request.getParameter("queryType");
		String existItemIds = request.getParameter("existItemIds");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String jsonText = getQuestBo().showAllQuestIssueItem(userInfo, queryType, existItemIds);
		response.setHeader("X-JSON", jsonText);
		outPrint(response,jsonText);
		
		return null;
	}

	public ActionForward windowOnloadData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int rownum = 0;
		String issueId = request.getParameter("issueId");
		List<BasicDynaBean> itemList = getQuestBo().queryItemBeanListByIssueId(issueId);
		StringBuffer sb = new StringBuffer();
		if (itemList != null && itemList.size() > 0) {
			sb.append("<table width='90%' align='center' id='showItemTable'>");
			//�����һ��
			sb.append("<tr class='trcolor'>");
			sb.append("<td>");
			sb.append("ָ�����");
			sb.append("</td>");
			sb.append("<td>");
			sb.append("ָ�����");
			sb.append("</td>");
			sb.append("<td>");
			sb.append("ָ����");
			sb.append("</td>");
			sb.append("<td>");
			sb.append("����");
			sb.append("</td>");
			sb.append("</tr>");
			if (itemList != null && itemList.size() > 0) {
				for (BasicDynaBean bean : itemList) {
					rownum++;
					sb.append("<tr class='trcolor' id='");
					sb.append(rownum);
					sb.append("'>");
					sb.append("<td>");
					sb.append(bean.get("class_name"));
					sb.append("<input type='checkbox' style='display:none;' checked=checked name='itemId' value='");
					sb.append(bean.get("id"));
					sb.append("' type='text'>");
					sb.append("</td>");
					sb.append("<td>");
					sb.append(bean.get("sort_name"));
					sb.append("</td>");
					sb.append("<td>");
					sb.append(bean.get("item_name"));
					sb.append("</td>");
					sb.append("<td>");
					sb.append("<a onclick=deleteItem('");
					sb.append(rownum);
					sb.append("') style='cursor:pointer;color:blue;'>ɾ��</a>");
					sb.append("</td>");
					sb.append("</tr>");
				}
			}
			sb.append("</table>");
		}
		response.setCharacterEncoding("GBK");
		outPrint(response,sb.toString());
		return null;
	}

	public ActionForward getQuestIssueIds(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String selectValue = request.getParameter("selectValue");
		int rownum = 0;
		Map map = getQuestBo().getQuestIssueIds(selectValue);
		//String itemIds = (String)map.get("itemIds");
		List<BasicDynaBean> itemList = (List) map.get("itemList");
		if (selectValue != null && !"".equals(selectValue)) {
			StringBuffer sb = new StringBuffer();
			sb.append("<table width='90%' align='center' id='showItemTable'>");
			//�����һ��
			sb.append("<tr class='trcolor'>");
			sb.append("<td>");
			sb.append("ָ�����");
			sb.append("</td>");
			sb.append("<td>");
			sb.append("ָ�����");
			sb.append("</td>");
			sb.append("<td>");
			sb.append("ָ����");
			sb.append("</td>");
			sb.append("<td>");
			sb.append("����");
			sb.append("</td>");
			sb.append("</tr>");
			if (itemList != null && itemList.size() > 0) {
				for (BasicDynaBean bean : itemList) {
					rownum++;
					sb.append("<tr class='trcolor' id='");
					sb.append(rownum);
					sb.append("'>");
					sb.append("<td>");
					sb.append(bean.get("class_name"));
					sb.append("<input type='checkbox' style='display:none;' checked=checked name='itemId' value='");
					sb.append(bean.get("id"));
					sb.append("' type='text'>");
					sb.append("</td>");
					sb.append("<td>");
					sb.append(bean.get("sort_name"));
					sb.append("</td>");
					sb.append("<td>");
					sb.append(bean.get("item_name"));
					sb.append("</td>");
					sb.append("<td>");
					sb.append("<a onclick=deleteItem('");
					sb.append(rownum);
					sb.append("') style='cursor:pointer;color:blue;'>ɾ��</a>");
					sb.append("</td>");
					sb.append("</tr>");
				}
			}
			sb.append("</table>");
//			StringBuffer buf = new StringBuffer("");
//			buf.append("<script type=\"text/javascript\">");
//			buf.append("parent.document.getElementById('showItemDiv').innerHTML=\"");
//			buf.append(sb.toString().replaceAll("\n", "").replaceAll("\r", ""));
//			buf.append("\";");
//			buf.append("parent.win.close();");
//			buf.append("</script>");
//			System.out.println(buf.toString());
//			response.setCharacterEncoding("GBK");
//			outPrint(response,sb.toString());
			
			
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());
			out.flush();
			
		}
		
		
		
		return null;
	}

	public ActionForward queryIssueByConditionForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//���session�еĲ�ѯ�����Ͳ�ѯ���
		request.getSession().removeAttribute("queryCondition");
		request.getSession().removeAttribute("query_list");
		return mapping.findForward("queryIssueByConditionForm");
	}

	public ActionForward queryIssueByCondition(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String issueName = request.getParameter("issueName");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		Map map = new HashMap();
		map.put("issueName", issueName);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		if (null == request.getParameter("isQuery")) {
			map = (Map) request.getSession().getAttribute("queryCondition");
			issueName = (String) map.get("issueName");
			beginTime = (String) map.get("beginTime");
			endTime = (String) map.get("endTime");
		} else {
			request.getSession().setAttribute("queryCondition", map);
		}
		List list = getQuestBo().queryIssueByCondition(issueName, beginTime, endTime);
		request.getSession().setAttribute("query_list", list);
		super.setPageReset(request);
		return mapping.findForward("queryIssueByConditionList");
	}

	public ActionForward viewQuestIssue(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String issueId = request.getParameter("issueId");
		Map map = getQuestBo().viewQuestIssue(issueId);
		BasicDynaBean bean = (BasicDynaBean) map.get("bean");
		String compNames = (String) map.get("compNames");
		String conName = (String) map.get("conNames");
		List list = (List) map.get("list");
		request.setAttribute("bean", bean);
		request.setAttribute("compNames", compNames);
		request.setAttribute("list", list);
		request.setAttribute("conName", conName);
		return mapping.findForward("viewQuestIssue");
	}
}
