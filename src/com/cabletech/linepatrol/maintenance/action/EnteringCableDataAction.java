package com.cabletech.linepatrol.maintenance.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.linepatrol.maintenance.beans.TestCableDataBean;
import com.cabletech.linepatrol.maintenance.module.TestCableData;
import com.cabletech.linepatrol.maintenance.module.TestChipData;
import com.cabletech.linepatrol.maintenance.module.TestCoreData;
import com.cabletech.linepatrol.maintenance.module.TestData;
import com.cabletech.linepatrol.maintenance.module.TestPlan;
import com.cabletech.linepatrol.maintenance.module.TestPlanLine;
import com.cabletech.linepatrol.maintenance.module.TestProblem;
import com.cabletech.linepatrol.maintenance.service.TestPlanBO;
import com.cabletech.linepatrol.maintenance.service.TestPlanLineBO;
import com.cabletech.linepatrol.maintenance.service.TestPlanLineDataBO;
import com.cabletech.linepatrol.resource.model.RepeaterSection;

/**
 * 中继段录入数据
 * @author Administrator
 *
 */
public class EnteringCableDataAction extends BaseDispatchAction {
	Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * 转到中继段测试数据结果列表页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward enteringDateListForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute("allenter", null);
		String planid = request.getParameter("planid");
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		TestPlanLineDataBO lineDataBO = (TestPlanLineDataBO)ctx.getBean("testPlanLineDataBO");
		TestPlan plan = planBO.getTestPlanById(planid);
		UserInfo user = planBO.getUser(plan.getCreatorId());
		String conid = plan.getContractorId();
		Contractor c = planBO.getContratroById(conid);
		request.getSession().setAttribute("plan",plan);
		request.getSession().setAttribute("contraName",c.getContractorName());
		request.getSession().setAttribute("userName",user.getUserName());
		List planlines = planBO.getPlanLinesByPlanId(planid);
		request.getSession().setAttribute("lines", planlines);
		boolean allenter = planBO.judgeAllEnteringLine (planid);
		request.getSession().setAttribute("allenter", allenter);
		TestData data = lineDataBO.getDataByPlanId(planid);
		request.getSession().setAttribute("dataid",null);
		if(data!=null){
			request.getSession().setAttribute("dataid", data.getId());
		}
		return mapping.findForward("enteringCableDateListForm");
	}

	/**
	 * 转到录入中继段数据页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addEnteringCableForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute("planChips", null);
		request.getSession().setAttribute("cableProblems", null);
		String lineid = request.getParameter("lineid");
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanLineBO lineBO = (TestPlanLineBO) ctx.getBean("testPlanLineBO");
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		TestPlanLine line = lineBO.getTestPlanLineById(lineid);
		RepeaterSection res = planBO.getRepeaterSection(line.getCablelineId());
		String coreNum = res.getCoreNumber();
		line.setCablelineName(res.getSegmentname());
		request.setAttribute("repeat", res);
		request.setAttribute("line",line);
		request.setAttribute("coreNumber",coreNum);
		if(coreNum!=null && !"".equals(coreNum)){
			Map<Object,TestChipData> planChips = new HashMap<Object,TestChipData>();
			int num = Integer.parseInt(coreNum);
			TestChipData data;
			for(int i = 1;i<=num;i++){
				data = new TestChipData();
				data.setChipSeq(i+"");
				data.setAttenuationConstant("");
				data.setIsEligible("1");//合格
				data.setIsUsed("0");//不再用
				data.setIsSave("0");//未保存
				planChips.put(i,data);
			}
			request.getSession().setAttribute("planChips",planChips);
		}
		return mapping.findForward("enteringCableForm");
	}



	/**
	 * 转到录入纤芯数据的页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addChipDataForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute("FILES",null);
		return mapping.findForward("addChipDataForm");
	}

	/**
	 * 保存纤芯信息到session
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addChipData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TestCableDataBean bean = (TestCableDataBean)form;
		//	TestChipData chip = new TestChipData();
		//TestCoreData coreData = null;
		TestChipData chip = new TestChipData();
		try {
			String chipSeq = bean.getChipSeq();
			Map<Object,TestChipData> planChips = (HashMap) request.getSession().getAttribute("planChips");
			List<FileItem> files = (List<FileItem>) request.getSession().getAttribute("FILES");
			response.setCharacterEncoding("GBK");
			if(planChips==null){
				planChips = new HashMap<Object,TestChipData>();
			}else{
				String oldchipseq = request.getParameter("oldchipseq");
				if(oldchipseq!=null && !"".equals(oldchipseq)){
					int seq = Integer.parseInt(oldchipseq);
					chip = planChips.get(seq);
					//	coreData = chipdata.getCoreData();
					//planChips.remove(seq);
				}
			}
			BeanUtil.objectCopy(bean, chip);
			//chip.setCoreData(coreData);
			chip.setAttachments(files);
			String isUsed = chip.getIsUsed();
			if(isUsed.equals("1")){
				chip.setIsEligible("");
				chip.setIsSave("");
			}
			int newSeq = 0;
			if(chipSeq!=null && !"".equals(chipSeq)){
				newSeq = Integer.parseInt(chipSeq);
			}
			planChips.put(newSeq,chip);
			flushChip(request,response,planChips);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void flushChip(HttpServletRequest request,HttpServletResponse response,
			Map<Object,TestChipData> planChips) {
		try {
			request.getSession().setAttribute("planChips", planChips);
			StringBuffer sb = new StringBuffer();
			if(planChips!=null && planChips.size()>0){
				Object[]   chipKey   =     planChips.keySet().toArray();   
				Arrays.sort(chipKey);   
				sb.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
				sb.append("<tr>");
				sb.append("<td width=\"7%\" >纤序</td>");
				sb.append("<td width=\"17%\">衰减常数dB/km</td>");
				sb.append(" <td width=\"11%\">是否合格</td>");
				sb.append(" <td width=\"11%\">是否保存</td>");
				sb.append(" <td width=\"12%\">是否在用</td>");
				sb.append("<td width=\"28%\">&nbsp;&nbsp;说明</td>");
				sb.append("<td width=\"14%\">操作</td>");
				sb.append("</tr>");
				Set set = planChips.keySet();
				Iterator ite = set.iterator();
				int nonConformity = 0;
				for   (int   i   =   0;   i   <   chipKey.length;   i++)   {   
					Object key = ( Object) chipKey[i];
					TestChipData chipdata = planChips.get(key);
					String chipseq = chipdata.getChipSeq();
					String remark = chipdata.getTestRemark();
					String att = chipdata.getAttenuationConstant();
					String isused = chipdata.getIsUsed();
					if(remark==null){
						remark="";
					}
					sb.append("<tr>");
					sb.append("<td>"+chipseq+"</td>");
					if(att==null || att.equals("null")){
						att="";
					}
					String isUsed = chipdata.getIsUsed();
					String isEligible = chipdata.getIsEligible();
					if(isEligible!=null && isEligible.equals("0")){
						nonConformity++;
					}
					if(isUsed.equals("1")){
						sb.append("<td>"+""+"</td>");
						sb.append("<td>"+" "+"</td>");
						sb.append("<td>"+" "+"</td>");
					}else{
						sb.append("<td>"+att+"</td>");
						String elig = isEligible.equals("1")?"合格":"不合格";
						sb.append("<td>"+elig+"</td>");
						String save = chipdata.getIsSave().equals("1")?"保存":"未保存";
						sb.append("<td>"+save+"</td>");
					}
					String used = isused.equals("1")?"在用":"不在用";
					sb.append("<td>"+used+"</td>");
					sb.append("<td>"+remark+"</td>");
					boolean state = (att==null || att.equals("null") || att.equals(""));
					if(state && !isused.equals("1")){
						sb.append("<td><a href=\"javascript:updateChipData("+chipseq+");\">录入</a></td>");
					}else{
						sb.append("<td><a href=\"javascript:updateChipData("+chipseq+");\">修改</a>");
						if(!isused.equals("1")){
							TestCoreData core = chipdata.getCoreData();
							if(core!=null && core.getBaseStation()!=null){
								sb.append("|<a href=\"javascript:addAnalyse("+chipseq+");\">修改数据分析</a></td>");
							}else{
								sb.append("|<a href=\"javascript:addAnalyse("+chipseq+");\">数据分析</a></td>");
							}
						}
					}
					//	sb.append("|<a href=\"javascript:deleteChip("+chipseq+");\">删除</a></td>");
					sb.append("</tr>");
				}
				sb.append("<input id=\"nonConformity\" name=\"nonConformity\" type=\"hidden\" value="+nonConformity+">");
				sb.append("</table>");
			}
			outPrint(response,sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 数据分析页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward analyseDataForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("index");
		String act = request.getParameter("act");
		String trunkName = request.getParameter("cableLineName");
		int newSeq = 0;
		if(id!=null && !"".equals(id)){
			newSeq = Integer.parseInt(id);
		}
		Map<Object,TestChipData> planChips = (HashMap) request.getSession().getAttribute("planChips");
		TestChipData data = planChips.get(newSeq);
		//List<FileItem> files = data.getAttachments();
		//String fileNames = lineDataBO.getFileNames(files);
		TestCoreData coreData = data.getCoreData();
		request.setAttribute("chipdata", data);
		//request.setAttribute("fileNames",fileNames);
		request.setAttribute("trunkName", trunkName);
		if(act!=null && act.equals("view")){
			return mapping.findForward("viewAnalyseDataForm");
		}
		if(coreData!=null){
			return mapping.findForward("editAnalyseDataForm");
		}
		return mapping.findForward("analyseDataForm");
	}

	/***
	 * 保存数据分析道session
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addAnalyseData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanLineDataBO lineDataBO = (TestPlanLineDataBO)ctx.getBean("testPlanLineDataBO");
		TestCableDataBean bean = (TestCableDataBean)form;
		try {
			Map<Object,TestChipData> planChips = (HashMap) request.getSession().getAttribute("planChips");
			response.setCharacterEncoding("GBK");
			String chiseq = request.getParameter("chiseq");
			int seq=0;
			if(chiseq!=null && !"".equals(chiseq)){
				seq = Integer.parseInt(chiseq);
			}
			TestChipData data = planChips.get(seq);
			data = lineDataBO.addAnalyToChipData(bean, data);
		//	String script = "<script>parent.close();</script>";
			//TODO
		//	response.getWriter().write(script);
			flushChip(request,response,planChips);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}



	/**
	 * 转到增加问题中继段的页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addProblemForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("addProblemForm");
	}

	/**
	 * 问题中继段保存到session
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addCableProblem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TestCableDataBean bean = (TestCableDataBean)form;
		TestProblem problem = new TestProblem();
		try {
			List<TestProblem> cableProblems = (List) request.getSession().getAttribute("cableProblems");
			response.setCharacterEncoding("GBK");
			if(cableProblems==null){
				cableProblems = new ArrayList<TestProblem>();
			}
			String in = request.getParameter("index");
			if(in!=null && !"".equals(in)){
				int index = Integer.parseInt(in);
				cableProblems.remove(index);
			}
			BeanUtil.objectCopy(bean, problem);
			cableProblems.add(problem);
			flushProblem(request,response,cableProblems);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void flushProblem(HttpServletRequest request,HttpServletResponse response,List<TestProblem> cableProblems) {
		try{
			request.getSession().setAttribute("cableProblems", cableProblems);
			StringBuffer sb = new StringBuffer();
			if(cableProblems!=null && cableProblems.size()>0){
				sb.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
				sb.append("<tr>");
				sb.append("<td width=\"7%\" >序号</td>");
				sb.append("<td width=\"35%\">问题描述</td>");
				sb.append(" <td width=\"35%\">处理跟踪说明</td>");
				sb.append(" <td width=\"10%\">状态</td>");
				sb.append(" <td width=\"13%\">操作</td>");
				sb.append("</tr>");
				int i = 1;
				for(TestProblem pro:cableProblems){
					String descri = pro.getProblemDescription();
					String comment = pro.getProcessComment();
					if(comment==null){
						comment="";
					}
					sb.append("<tr>");
					sb.append("<td>"+i+"</td>");
					sb.append("<td>"+descri+"</td>");
					sb.append("<td>"+comment+"</td>");
					String state = pro.getProblemState().equals("1")?"已解决":"未解决";
					sb.append("<td>"+state+"</td>");
					sb.append("<td><a href=\"javascript:updateProblem("+(i-1)+");\">修改</a>");
					sb.append("|<a href=\"javascript:deleteProblem("+(i-1)+");\">删除</a></td>");
					sb.append("</tr>");
					i++;
				}
				sb.append("</table>");
			}
			outPrint(response,sb.toString());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存线路信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addEnteringCableData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TestCableDataBean bean = (TestCableDataBean)form;
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanLineDataBO lineDataBO = (TestPlanLineDataBO)ctx.getBean("testPlanLineDataBO");
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String tempstate = request.getParameter("tempstate");
		String lineId = request.getParameter("lineId");
		try {
			Map<Object,TestChipData> chips = (HashMap) request.getSession().getAttribute("planChips");
			List<TestProblem> problems = (List) request.getSession().getAttribute("cableProblems");
			String act = request.getParameter("act");
			if(act!=null && act.equals("edit")){
				lineDataBO.updateCableData(bean,chips,problems,user,tempstate,lineId);
			}else{
				TestCableData data = lineDataBO.saveLineData(bean,user,chips,problems,tempstate);
			}
			String planid = bean.getTestPlanId();
			List planlines = planBO.getPlanLinesByPlanId(planid);
			request.getSession().setAttribute("lines", planlines);
			response.setCharacterEncoding("GBK");
			String script = "<script>window.parent.location.href=window.parent.location.href;parent.close();</script>";
			response.getWriter().write(script);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 转到修改中继段数据页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editEnteringCableForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String lineid = request.getParameter("lineid");
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanLineBO lineBO = (TestPlanLineBO) ctx.getBean("testPlanLineBO");
		TestPlanLineDataBO lineDataBO = (TestPlanLineDataBO)ctx.getBean("testPlanLineDataBO");
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		TestPlanLine line =  lineBO.getTestPlanLineById(lineid);
		RepeaterSection res = planBO.getRepeaterSection(line.getCablelineId());
		line.setCablelineName(res.getSegmentname());
		List tester = planBO.getUsers(userinfo,"");
		request.setAttribute("repeat", res);
		request.setAttribute("line",line);
		request.setAttribute("testMan",tester);
		String planid = line.getTestPlanId();
		String lid = line.getCablelineId();
		TestCableData data = lineDataBO.getLineDataByPlanIdAndLineId(planid, lid);
		String dataid= data.getId();
		request.setAttribute("data",data);
		List<TestProblem> problems = lineDataBO.getProblemsByPlanIdAndLineId(planid, lid);
		Map<Object,TestChipData> chips = lineDataBO.getChipsByCableDataId(dataid);
		request.getSession().setAttribute("cableProblems", problems);
		request.getSession().setAttribute("planChips", chips);
		request.setAttribute("coreNumber",res.getCoreNumber());
		int nonConformity = lineDataBO.getNonConformityChipNum(dataid);
		request.setAttribute("nonConformity",nonConformity);
		return mapping.findForward("editEnteringCableForm");
	}

	/**
	 * 转到修改问题记录页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editProblemForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String in = request.getParameter("index");
		int index = Integer.parseInt(in);
		List<TestProblem> cableProblems = (List) request.getSession().getAttribute("cableProblems");
		TestProblem problem = cableProblems.get(index);
		request.setAttribute("problem",problem);
		request.setAttribute("index",index);
		return mapping.findForward("editProblemForm");
	}


	/**
	 * 转到纤芯录入数据页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editChipForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute("FILES",null);
		String id = request.getParameter("chipSeq");
		Map<Object,TestChipData> chips = (HashMap) request.getSession().getAttribute("planChips");
		int newSeq = 0;
		if(id!=null && !"".equals(id)){
			newSeq = Integer.parseInt(id);
		}
		TestChipData chipdata = chips.get(newSeq);
		request.setAttribute("chipdata",chipdata);
		return mapping.findForward("editChipForm");
	}

	/**
	 * 删除纤芯数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public void deleteCable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String chipSeq = request.getParameter("chipSeq");
		Map<Object,TestChipData> chips = (HashMap) request.getSession().getAttribute("planChips");
		int newSeq = 0;
		if(chipSeq!=null && !"".equals(chipSeq)){
			newSeq = Integer.parseInt(chipSeq);
		}
		if(chips.containsKey(newSeq)){
			chips.remove(newSeq);
		}
		flushChip(request,response,chips);
	}

	/**
	 * 删除问题列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public void deleteProblem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String in = request.getParameter("index");
		int index = Integer.parseInt(in);
		List<TestProblem> cableProblems = (List) request.getSession().getAttribute("cableProblems");
		cableProblems.remove(index);
		//	request.getSession().setAttribute("cableProblems", cableProblems);		
		flushProblem(request,response,cableProblems);
	}

	/**
	 * 保存备纤录入数据的审核人
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveEnteringApprove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
			String approvers = request.getParameter("approvers");
			String mobiles = request.getParameter("mobiles");
			String reads = request.getParameter("reads");
			String rmobiles = request.getParameter("rmobiles");
			String testDataId = request.getParameter("dataid");
			String planid = request.getParameter("planid");
			WebApplicationContext ctx = getWebApplicationContext();
			TestPlanLineDataBO lineDataBO = (TestPlanLineDataBO)ctx.getBean("testPlanLineDataBO");
			TestPlanBO testPlanBO =(TestPlanBO) ctx.getBean("testPlanBO");
			lineDataBO.saveApprove(user,testDataId,planid,approvers, mobiles,reads,rmobiles);
			log(request, "数据录入（计划名称为："+testPlanBO.getTestPlanById(planid).getTestPlanName()+"）", "技术维护 ");
		}catch(Exception e){
			logger.error("保存备纤数据录入审核人出现异常:"+e.getMessage());
			e.printStackTrace();
		}
		return forwardInfoPage(mapping, request, "25saveEnteringApproveOK");
	}



}