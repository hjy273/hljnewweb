package com.cabletech.linepatrol.maintenance.action;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.linepatrol.commons.module.ApproveInfo;
import com.cabletech.linepatrol.commons.module.ReplyApprover;
import com.cabletech.linepatrol.maintenance.beans.TestYearPlanBean;
import com.cabletech.linepatrol.maintenance.module.TestYearPlan;
import com.cabletech.linepatrol.maintenance.module.TestYearPlanTask;
import com.cabletech.linepatrol.maintenance.module.TestYearPlanTrunk;
import com.cabletech.linepatrol.maintenance.service.MainTenanceConstant;
import com.cabletech.linepatrol.maintenance.service.TestPlanBO;
import com.cabletech.linepatrol.maintenance.service.TestYearPlanBO;

/**
 * ��ƻ�
 * 
 * @author Administrator
 * 
 */
public class TestYearPlanAction extends BaseDispatchAction {
	Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * ��ѯ����ƻ�����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getWaitHandelYearPlans(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		TestYearPlanBO bo = (TestYearPlanBO) ctx.getBean("testYearPlanBO");
		UserInfo user = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
		List list = bo.getWaitHandelYearPlans(user);
		request.getSession().setAttribute("yearplans",list);
		return mapping.findForward("waitHandelYearPlans");
	}

	/**
	 * ת���ƶ���ƻ��Ľ���
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addYearPlanForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		request.getSession().setAttribute("planTasks",null);
		return mapping.findForward("addYearPlanForm");
	}
	
	/**
	 *��Ӳ�������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addYearPlanTask(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String testTimes = request.getParameter("testTimes");
		if(testTimes!=null && !"".equals(testTimes)){
			request.setAttribute("testTimes",testTimes);
		}
		return mapping.findForward("addYearPlanTaskForm");
	}

	/**
	 * ������ƻ�������Ϣ��session
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addPlanTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String trunkNames = request.getParameter("textarea");
		//System.out.println("trunkName============"+trunkName);
		TestYearPlanBean bean = (TestYearPlanBean)form;
		TestYearPlanTask task = new TestYearPlanTask();
		task.setTrunkNames(trunkNames);
		try {
			String cableLevel = bean.getCableLevel();
			Map<String,TestYearPlanTask> planTasks = (HashMap) request.getSession().getAttribute("planTasks");
			response.setCharacterEncoding("GBK");
			if(planTasks==null){
				planTasks = new HashMap<String,TestYearPlanTask>();
			}else{
				String oldCableLevel = request.getParameter("oldCableLevel");				
				planTasks.remove(oldCableLevel);
				if(planTasks.containsKey(cableLevel)){//�жϹ��¼����Ƿ��ظ�
					outPrint(response,"1");
					return null;
				}
			}
			BeanUtil.objectCopy(bean, task);
			planTasks.put(cableLevel,task);
			//	String taskid = bean.getId();
			flushPlanTask(request,response,planTasks);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void flushPlanTask(HttpServletRequest request,HttpServletResponse response,
			Map<String,TestYearPlanTask> planTasks) {
		try {
			request.getSession().setAttribute("planTasks", planTasks);
			StringBuffer sb = new StringBuffer();
			if(planTasks!=null && planTasks.size()>0){
				sb.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
				sb.append("<tr>");
				sb.append("<td width=\"15%\" >���¼���</td>");
				sb.append("<td width=\"20%\">���ǰ���Դ���</td>");
				sb.append(" <td width=\"15%\">������Դ���</td>");
				sb.append("<td width=\"15%\">����м̶�����</td>");
			//	sb.append(" <td width=\"40%\">����м̶�</td>");
				sb.append("<td width=\"15%\">����</td>");
				sb.append("</tr>");
				Set set = planTasks.keySet();
				Iterator ite = set.iterator();
				WebApplicationContext ctx = getWebApplicationContext();
				TestYearPlanBO planBO = (TestYearPlanBO) ctx.getBean("testYearPlanBO");
				while(ite.hasNext()){
					String key = (String) ite.next();
					TestYearPlanTask planTask = planTasks.get(key);
					String cableLevel = planTask.getCableLevel();
					String cableLabel = planBO.getCableLevelNameByCode(cableLevel);
					int preTestNum = planTask.getPreTestNum();
					int applyNum = planTask.getApplyNum();
					int trunkNum = 0;
					String trunkName = planTask.getTrunkNames();
					if(trunkName==null){
						trunkName="";
					}else{
						List<String> trunks = Arrays.asList(trunkName.split(","));
						trunkNum = trunks.size();
					}
					sb.append("<tr>");
					sb.append("<td>"+cableLabel+"</td>");
					sb.append("<td>"+preTestNum+"</td>");
					sb.append("<td>"+applyNum+"</td>");
					sb.append("<td>"+trunkNum+"</td>");
					//sb.append("<td>"+trunkName+"</td>");
					sb.append("<td><a href=\"javascript:editPlanTask("+cableLevel+");\">�޸�</a>");
					sb.append("|<a href=\"javascript:deleteTask("+cableLevel+");\">ɾ��</a></td>");
					sb.append("</tr>");
				}
				sb.append("</table>");
			}
			outPrint(response,sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * ת���޸ļƻ�����ҳ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editYearTaskForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String cableLevel = request.getParameter("cableLevel");
		Map<String,TestYearPlanTask> planTasks = (HashMap) request.getSession().getAttribute("planTasks");
		TestYearPlanTask task = planTasks.get(cableLevel);
		request.setAttribute("planTask",task);
		return mapping.findForward("editYearTaskForm");
	}

	/**
	 * ɾ����ƻ�����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public void deleteTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String cableLevel = request.getParameter("cableLevel");
		Map<String,TestYearPlanTask> planTasks = (HashMap) request.getSession().getAttribute("planTasks");
		if(planTasks.containsKey(cableLevel)){
			planTasks.remove(cableLevel);
		}
		flushPlanTask(request,response,planTasks);
	}


	/**
	 * ������ƻ��Լ���ƻ����񡣵����ݿ�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveYearPlan(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		TestYearPlanBean bean = (TestYearPlanBean)form;
		WebApplicationContext ctx = getWebApplicationContext();
		TestYearPlanBO bo = (TestYearPlanBO) ctx.getBean("testYearPlanBO");
		Map<String,TestYearPlanTask> planTasks = (HashMap) request.getSession().getAttribute("planTasks");
		UserInfo user = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
		try{
			String conid = user.getDeptID();
			String year = bean.getYear();
			String act = request.getParameter("act");
			boolean isHave = true;
			if(act!=null && act.equals("edit")){
				String planid = request.getParameter("id");
				isHave = bo.judgeYearPlanIsHave(year,conid,planid);
			}else{
				isHave = bo.judgeYearPlanIsHave(year,conid,"");
			}
			if(isHave){
				return forwardInfoPage(mapping, request, "25addYearPlanRepeat");	
			}
			TestYearPlan plan = bo.addTestYearPlan(bean, user,planTasks);
			if(act!=null && act.equals("edit")){
				log(request, "�޸���ƻ����ƻ�����Ϊ��"+plan.getPlanName()+"��", "����ά�� ");
				return forwardInfoPage(mapping, request, "25editYearPlanOK");	
			}
			log(request, "������ƻ����ƻ�����Ϊ��"+plan.getPlanName()+"��", "����ά�� ");
			return forwardInfoPage(mapping, request, "25addYearPlanOK");	
		}catch(Exception e){
			e.getStackTrace();
		}
		return forwardErrorPage(mapping, request, "error");
	}


	/**
	 *�鿴��ƻ�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewYearPlanForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String planid = request.getParameter("planid");
		String query = request.getParameter("query");
		String isread = request.getParameter("isread");
		WebApplicationContext ctx = getWebApplicationContext();
		TestYearPlanBO bo = (TestYearPlanBO) ctx.getBean("testYearPlanBO");
		TestYearPlan plan  = bo.getYearPlanById(planid);
		Map<String,TestYearPlanTask> planTasks = bo.getPlanTasksByPlanId(planid);
		List<ReplyApprover> approvers = bo.getApprovers(planid);
		String contraName = bo.getConNameByContractId(plan.getContractorId());
		String userName = bo.getUserNameByUserId(plan.getCreatorId());
		request.setAttribute("contraName",contraName);
		request.setAttribute("userName",userName);
		request.setAttribute("plan",plan);
		request.setAttribute("planTasks",planTasks);
		request.setAttribute("approvers",approvers);
		List approveInfos = bo.getApproveHistorys(planid);
		request.setAttribute("approveInfos",approveInfos);
		request.setAttribute("query",query);
		request.setAttribute("isread",isread);
		return mapping.findForward("viewYearTaskForm");
	}
	
	/**
	 *�鿴���������Ӧ�ı���м̶���Ϣ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewChangeCables(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String taskid = request.getParameter("taskid");
		WebApplicationContext ctx = getWebApplicationContext();
		TestYearPlanBO bo = (TestYearPlanBO) ctx.getBean("testYearPlanBO");
		List<TestYearPlanTrunk> trunkLists = bo.getTrunksByTaskId(taskid);
		if(trunkLists!=null && trunkLists.size()>0){
			String trunkNames = bo.trunksListTOTrunkName(trunkLists);
			request.setAttribute("trunkNames", trunkNames);
		}
		return mapping.findForward("viewChangeCables");
	}
	
	
	/**
	 *�޸���ƻ�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editYearPlanForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String planid = request.getParameter("planid");
		WebApplicationContext ctx = getWebApplicationContext();
		TestYearPlanBO bo = (TestYearPlanBO) ctx.getBean("testYearPlanBO");
		TestYearPlan plan  = bo.getYearPlanById(planid);
		Map<String,TestYearPlanTask> planTasks = bo.getPlanTasksByPlanId(planid);
	//	List<ReplyApprover> approvers = bo.getApprovers(planid);
	//	String contraName = bo.getConNameByContractId(plan.getContractorId());
	//	String userName = bo.getUserNameByUserId(plan.getCreatorId());
	//	request.setAttribute("contraName",contraName);
	//	request.setAttribute("userName",userName);
		request.getSession().setAttribute("plan",plan);
	//	request.setAttribute("planTasks",planTasks);
	    request.getSession().setAttribute("planTasks",planTasks);
		//request.setAttribute("approvers",approvers);
		return mapping.findForward("editYearPlanForm");
	}
	
	
	

	/**
	 *ɾ����ƻ�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward deleteYearPlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			String planid = request.getParameter("planid");
			WebApplicationContext ctx = getWebApplicationContext();
			TestYearPlanBO bo = (TestYearPlanBO) ctx.getBean("testYearPlanBO");
			String testTimes=bo.get(planid).getTestTimes();
			bo.deleteYearPlan(planid);
			log(request, "ɾ����ƻ����ƻ�����Ϊ��"+testTimes+"��", "����ά��");
			return forwardInfoPage(mapping, request, "25deleteYearPlanOK");
		}catch(Exception e){
			e.getStackTrace();
		}
		return forwardErrorPage(mapping, request, "error");
	}

	/**
	 *�����ƻ�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward approverPlanForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String planid = request.getParameter("planid");
		String act = request.getParameter("act");
		request.setAttribute("act",act);
		WebApplicationContext ctx = getWebApplicationContext();
		TestYearPlanBO bo = (TestYearPlanBO) ctx.getBean("testYearPlanBO");
		TestYearPlan plan  = bo.getYearPlanById(planid);
		Map<String,TestYearPlanTask> planTasks = bo.getPlanTasksByPlanId(planid);
	//	List<ReplyApprover> approvers = bo.getApprovers(planid);
		String contraName = bo.getConNameByContractId(plan.getContractorId());
		String userName = bo.getUserNameByUserId(plan.getCreatorId());
		request.setAttribute("contraName",contraName);
		request.setAttribute("userName",userName);
		request.setAttribute("plan",plan);
		request.setAttribute("planTasks",planTasks);
	//	request.setAttribute("approvers",approvers);
		List approveInfos = bo.getApproveHistorys(planid);
		request.setAttribute("approveInfos",approveInfos);
		return mapping.findForward("approverPlanForm");
	}
	
	/**
	 * ������˲��Լƻ�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward approveYearPlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String planid =request.getParameter("planid");
		String mobiles = request.getParameter("mobiles");
		String approveResult = request.getParameter("approveResult");
		String approveRemark = request.getParameter("approveRemark");
		WebApplicationContext ctx = getWebApplicationContext();
		TestYearPlanBO bo = (TestYearPlanBO) ctx.getBean("testYearPlanBO");
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		ApproveInfo approve = new ApproveInfo();
		approve.setApproveResult(approveResult);
		approve.setApproveRemark(approveRemark);
		approve.setObjectId(planid);
		String act= request.getParameter("act");
		TestYearPlan plan=bo.getYearPlanById(planid);
		if(act!=null && act.equals("transfer")){
			String approver = request.getParameter("approver");
			approve.setApproverId(approver);
			approve.setApproveResult(MainTenanceConstant.APPROVE_RESULT_TRANSFER);
		}else{
			approve.setApproverId(userinfo.getUserID());
		}
		try {
			bo.addApproveYearPlan(userinfo,approve,planid,mobiles);
			if(act!=null && act.equals("transfer")){
				log(request, "ת����ƻ����ƻ�����Ϊ��"+plan.getPlanName()+"��", "����ά�� ");
				return this.forwardInfoPage(mapping, request, "approveYearPlanTransferOK");
			}
			if(approveResult.equals(MainTenanceConstant.APPROVE_RESULT_NO)){//��ͨ��
				log(request, "�����ƻ�δͨ�����ƻ�����Ϊ��"+plan.getPlanName()+"��", "����ά�� ");
				return this.forwardInfoPage(mapping, request, "approveYearPlanNoPass");
			}
			log(request, "�����ƻ�ͨ�����ƻ�����Ϊ��"+plan.getPlanName()+"��", "����ά�� ");
			return this.forwardInfoPage(mapping, request, "approveYearPlanPass");

		} catch (Exception e) {
			logger.error("�����ƻ�ʧ��:"+e.getMessage());
			e.printStackTrace();
		}
		return this.forwardErrorPage(mapping, request, "error");
	}
	
	/**
	 * ת����ѯ��ƻ���ҳ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryYearPlanForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		String deptype = user.getDeptype();
		if(deptype.equals("1")){
			List<Contractor> cons = planBO.getContractors(user);
			request.getSession().setAttribute("cons",cons);
		}
		request.getSession().removeAttribute("queryCondition");
		request.getSession().removeAttribute("list");
		return mapping.findForward("queryYearPlanForm");
	}
	
	
	/**
	 * ��ѯ��ƻ�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getYearPlans(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TestYearPlanBean bean = (TestYearPlanBean)form;
		if(null==request.getParameter("isQuery")){
			bean=(TestYearPlanBean)request.getSession().getAttribute("queryCondition");
		}else{
			request.getSession().setAttribute("queryCondition", bean);
		}
		if(bean==null){
			bean=new TestYearPlanBean();
		}
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		TestYearPlanBO bo = (TestYearPlanBO) ctx.getBean("testYearPlanBO");
		List list = bo.getYearPlans(user, bean);
		request.getSession().setAttribute("list",list);
		super.setPageReset(request);
		return mapping.findForward("yearPlans");
	}

	
	/**
	 *���Ķ�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward readPlan(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String planid = request.getParameter("planid");
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		TestYearPlanBO bo = (TestYearPlanBO) ctx.getBean("testYearPlanBO");
		bo.updateReader(user, planid);
		return forwardInfoPage(mapping, request, "testYearPlanReaded");
	}

}