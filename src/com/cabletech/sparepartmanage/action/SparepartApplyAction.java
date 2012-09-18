package com.cabletech.sparepartmanage.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
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

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.power.CheckPower;
import com.cabletech.sparepartmanage.beans.SparepartApplyBean;
import com.cabletech.sparepartmanage.beans.SparepartAuditingBean;
import com.cabletech.sparepartmanage.domainobjects.SparepartApplyF;
import com.cabletech.sparepartmanage.domainobjects.SparepartApplyS;
import com.cabletech.sparepartmanage.domainobjects.SparepartConstant;
import com.cabletech.sparepartmanage.services.SeparepartBaseInfoService;
import com.cabletech.sparepartmanage.services.SparepartApplyBO;
import com.cabletech.sparepartmanage.services.SparepartStorageBO;

/**
 * SparepartApplyAction 备件申请Action 完成备件使用申请、送修申请或者报废申请的添加、修改、删除和查询
 * 
 */
public class SparepartApplyAction extends BaseDispatchAction {
	private static Logger logger = Logger.getLogger(SparepartApplyAction.class.getName());
	private SeparepartBaseInfoService basebo = new SeparepartBaseInfoService();
	private SparepartApplyBO bo = new SparepartApplyBO();

	/**
	 * 备件申请的添加表单
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward addApplyForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		if (!CheckPower.checkPower(request.getSession(), "90707")) {
			return mapping.findForward("powererror");
		}
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String name = request.getParameter("name");
		String baseid = request.getParameter("baseid");
		List serialNums = bo.findSerialNumsByBaseId(baseid,user.getDeptID(),SparepartConstant.CONTRACTOR_WAIT_USE);
		List usedPositions = bo.getusedPositions(baseid) ;//查处备件的使用位置
		List patrolgroups = bo.patrolgroupsByDeptID(user.getDeptID());//得到部门下的巡检维护组
		List patrolgroupsC = bo.patrolgroupsByDeptIDChange(user.getDeptID(),baseid);
		//List serialNumsRun = bo.findSerialNumsByBaseId(baseid,user.getDeptID(),SparepartConstant.IS_RUNNING);
		/*List serialNumsRun = new ArrayList();
		if(usedPositions!=null && usedPositions.size()>0){
			BasicDynaBean bDB = (BasicDynaBean) usedPositions.get(0);
			String positon = (String) bDB.get("storage_position");
			serialNumsRun = bo.findSerialNumsByBaseId(baseid,user.getDeptID(),SparepartConstant.IS_RUNNING,positon);
		}*/
		request.setAttribute("sparePartId", baseid);
		request.setAttribute("serialNums",serialNums);
		request.setAttribute("user_name", user.getUserName());
		request.setAttribute("user_id", user.getUserID());
		request.setAttribute("dept_id", user.getDeptID());
		request.setAttribute("dept_name", user.getDeptName());
		request.setAttribute("sparepartName", name);
		request.setAttribute("patrolgroups",patrolgroups);
		request.setAttribute("patrolgroupsC",patrolgroupsC);
		request.setAttribute("usedPositions",usedPositions);
		//request.setAttribute("serialNumsRun",serialNumsRun);
		return mapping.findForward("addApplyForm");
	}

	/**
	 * 备件申请的添加(在申请时只修改备件的状态)
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward addApply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		if (!CheckPower.checkPower(request.getSession(), "90707")) {
			return mapping.findForward("powererror");
		}
		String[] serialNums = request.getParameterValues("serialId");
		String[] serialNumUses = request.getParameterValues("usedserialId");
		SparepartApplyBean bean = (SparepartApplyBean) form;
		if(bean.getUseMode().equals("01")){
			serialNumUses=null;
			String patrolgroupId =request.getParameter("patrolgroupRId");
			bean.setPatrolgroupId(patrolgroupId.trim());
		}
		if(bean.getUseMode().equals("02")){
			String patrolgroupId =request.getParameter("patrolgroupCId");
			bean.setPatrolgroupId(patrolgroupId.trim());
		}
		SparepartApplyF applyF = this.initApplyF(bean);
		try{
			String id = bo.addSparePartApplyF(applyF);	
			List SpareInfos = bo.getSpareInfosBySerialNums(serialNums);														
			List applySs = this.initApplyS(SpareInfos,serialNumUses,id);
			bo.addSparePartApplyS(applySs);	
			if(serialNumUses!=null && serialNumUses.length>0){//如果为更换使用
				bo.updateUsedSparepartState(serialNumUses);
			}
			//bo.updateSparepartState(serialNums,bean.getApplyUsePosition());	//更新备件状态，以及保存位置,取出位置
			bo.updateSparepartState(serialNums);//只修改备件状态，其余不修改，在审核通过时修改
			return super.forwardInfoPage(mapping, request, "9070201ok");
		} catch(Exception e){
			e.getStackTrace();
		}
		return super.forwardErrorPage(mapping, request, "9070201err");
	}

	public SparepartApplyF initApplyF(SparepartApplyBean bean){
		SparepartApplyF applyF = new SparepartApplyF();
		applyF.setApplyDate(new Date());
		applyF.setSparePartId(bean.getSparePartId());
		applyF.setApplyPerson(bean.getApplyPerson());
		applyF.setApplyRemark(bean.getApplyRemark());
		if(bean.getUseMode().equals("01")){//直接使用
			applyF.setApplyUsePosition(bean.getApplyUsePosition());
		}else{
			applyF.setApplyUsePosition(bean.getTakenOutStorage());
		}
		applyF.setAuditingState("00");
		applyF.setPatrolgroupId(bean.getPatrolgroupId());
		applyF.setReplaceType(bean.getReplaceType());
		applyF.setUsedPosition(bean.getUsedPosition());
		applyF.setUseMode(bean.getUseMode());
		applyF.setUseNumber(bean.getUseNumber());
		return applyF;

	}
	public List initApplyS(List SpareInfos,String[] serialNumUses,String id){
		List list = new ArrayList();
		for(int i = 0;i<SpareInfos.size();i++){
			BasicDynaBean basicBean =  (BasicDynaBean) SpareInfos.get(i);
			String storageId = (String) basicBean.get("tid");
			String takenOutStorage = (String) basicBean.get("taken_out_storage");
			String serialNumber = (String)basicBean.get("serial_number");
			SparepartApplyS applyS = new SparepartApplyS();
			applyS.setFtid(id);			
			applyS.setSerialNumber(serialNumber);
			applyS.setTakenOutStorage(takenOutStorage);
			applyS.setStorageId(storageId);
			if(serialNumUses !=null && serialNumUses.length>0){
				applyS.setUsedSerialNumber(serialNumUses[i]);
			}else{
				applyS.setUsedSerialNumber("");
			}
			list.add(applyS);
		}

		return list;		
	}
	/**
	 *  修改备件申请
	 *  把原来的状态全部还原，然后像一个新申请重写加数据，申请表父表跟新，子表删除重建
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editApply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		if (!CheckPower.checkPower(request.getSession(), "90707")) {
			return mapping.findForward("powererror");
		}
		String[] serialNums = request.getParameterValues("serialId");//得到新的要申请使用的备件
		String[] serialNumUses = request.getParameterValues("usedserialId");//得到要被更换的备件
		String applyFId = request.getParameter("applyFId");
		SparepartApplyBean bean = (SparepartApplyBean) form;
		if(bean.getUseMode().equals("01")){
		//	String patrolgroupRId = request.getParameter("patrolgroupRId");
			//System.out.println("patrolgroupRId " +patrolgroupRId);
			//bean.setPatrolgroupId(patrolgroupRId);
			serialNumUses=null;
		}
		if(bean.getUseMode().equals("02")){
			//String patrolgroupCId =request.getParameter("patrolgroupCId");
			//System.out.println("patrolgroupCId " +patrolgroupCId);
			//bean.setPatrolgroupId(patrolgroupCId);
		}		
		try{
			List oldSerials = new ArrayList();
			List oldUsedSerials = new ArrayList();;
			List applyss = (List) request.getSession().getAttribute("applySs");//得到原来的备件序列号
			if(applyss !=null && applyss.size()>0){
				for(int i = 0;i<applyss.size();i++){
					SparepartApplyS applys = (SparepartApplyS) applyss.get(i);
					String serial = applys.getSerialNumber();
					String usedSerial = applys.getUsedSerialNumber();
					oldSerials.add(serial);
					oldUsedSerials.add(usedSerial);
				}
			}
			bo.updatSparepartState(oldSerials, SparepartConstant.CONTRACTOR_WAIT_USE);
			bo.updatSparepartState(oldUsedSerials, SparepartConstant.IS_RUNNING);
			bo.deleteApplyS(applyFId);
			/*	if(serialNumUses!=null || serialNumUses.length>0){//更换使用
				bean.setApplyUsePosition(null);
			}*/
			SparepartApplyF applyF = this.initApplyF(bean);
			applyF.setTid(applyFId);
			bo.updateSparePartApplyF(applyF);

			bo.updateSparepartState(serialNums);
			if(serialNumUses!=null && serialNumUses.length>0){
				bo.updateUsedSparepartState(serialNumUses);
			}
			List SpareInfos = bo.getSpareInfosBySerialNums(serialNums);														
			List applySs = this.initApplyS(SpareInfos,serialNumUses,applyFId);
			bo.addSparePartApplyS(applySs);	

			Map param = new HashMap();
			param.clear();
			param.put("storage_id", bean.getTakenOutStorage());
			return super.forwardInfoPageWithParam(mapping, request,	"9070202ok", param);
		} catch(Exception e){
			System.out.println("修改备件申请出现异常："+e.getMessage());
			e.getStackTrace();
		}
		Map param = new HashMap();
		param.clear();
		//	param.put("apply_id", bean.getTid());
		return super.forwardErrorPageWithParam(mapping, request,"9070202err", param);
	}
	
	/**
	 * 根据巡检组查询维护组
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPositionByPatrolgroup(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/json; charset=GBK");
		String patrolgroupId = request.getParameter("patrolgroupCId");		
		String baseid = request.getParameter("baseid");
		//String state = request.getParameter("state");
		List positons = bo.getPositonsByPatrolgroupIdByChange(patrolgroupId,baseid);
		JSONArray ja = JSONArray.fromObject(positons);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(ja.toString());
			out.flush();
			logger.info(ja.toString() + " 位置");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
		
		return null;
	}

	
	/**
	 * 显示代维用户的申请备件中的查询
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showQueryForApply(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("showQueryForApply");
	}

	/**
	 * 按条件查找备件申请信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doQueryForApply(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String serial_number = request.getParameter("serial_number");
		String res = request.getParameter("auditing_result");
		String storage_position = request.getParameter("storage_position");
		String begintime = request.getParameter("begintime");
		String endtime = request.getParameter("endtime");
		String condition = "";
		if ("1".equals(request.getParameter("reset_query"))) {
			if ("03".equals(res)) {
				condition += (" and appf.auditing_state ='00'");
			}
			if (res == null || "".equals(res)) {

			}
			if ("01".equals(res) || "02".equals(res)) {
				condition += ("   and appf.auditing_state='" + res + "'");
			}
			if (serial_number != null && !serial_number.equals("")) {
				condition += (" and apps.serial_number like '%"
						+ serial_number + "%'");
			}
			if (storage_position != null && !storage_position.equals("")) {
				condition += (" and appf.apply_use_position like '%"
						+ storage_position.trim() + "%'");
			}
			if (begintime != null && !begintime.equals("")) {
				condition = condition + " and appf.apply_date >=TO_DATE('"
				+ begintime + "','YYYY-MM-DD')";
			}
			if (endtime != null && !endtime.equals("")) {
				condition = condition + " and appf.apply_date <= TO_DATE('" + endtime
				+ " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
			}
			request.getSession().setAttribute("QUERY_CONDITION", condition);
		} else {
			condition = (String) request.getSession().getAttribute(
			"QUERY_CONDITION");
			if (condition == null || condition.equals("")) {
				condition = "";
			}
		}

		List list = bo.doQueryforAu(condition);
		request.getSession().setAttribute("APPLY_LIST", list);
		request.getSession().setAttribute("EXPORT_APPLY_LIST", list);
		super.setPageReset(request);
		return mapping.findForward("applyQueryList");
	}
	/**
	 * 导出查询到的备件申请列表
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward exportApplyList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = (List) request.getSession().getAttribute("EXPORT_APPLY_LIST");
		bo.exportStorage(list, response);
		return null;
	}

	/**
	 * 查看备件申请的详细信息(代维)
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward viewOneApplyInfoForApply(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//String applySId = request.getParameter("apply_id");
		String applyFId = request.getParameter("apply_id");
		String viewMethod = request.getParameter("view_method");
		SparepartApplyBean bean = new SparepartApplyBean();		
		SparepartApplyF  applyF = bo.getApplyF(applyFId);
		//SparepartApplyS  applyS = bo.getApplyS(applySId);
		List applySs = bo.getApplySs(applyFId);

		//备件名称 备件序列号 申请部门 使用方式 更换类型 被更换备件序列号 申请使用位置 申请人 申请备注
		bean.setApplyDate(applyF.getApplyDate());
		bean.setApplyPerson(applyF.getApplyPerson());
		bean.setApplyRemark(applyF.getApplyRemark());
		bean.setApplyUsePosition(applyF.getApplyUsePosition());
		bean.setAuditingState(applyF.getAuditingState());
		bean.setReplaceType(applyF.getReplaceType());
		bean.setSparePartId(applyF.getSparePartId());
		bean.setUsedPosition(applyF.getUsedPosition());
		bean.setUsedSparePartId(applyF.getSparePartId());
		bean.setUseMode(applyF.getUseMode());
		bean.setTakenOutStorage(applyF.getApplyUsePosition());
		bean.setPatrolgroupId(applyF.getPatrolgroupId());
		String applyperson = bo.getUserNameById(bean.getApplyPerson());
		bean.setApplyPerson(applyperson);
		String sparepartname = basebo.getOneSparepart(bean.getSparePartId()).getSparePartName();
		bean.setSparePartName(sparepartname);
		bean.setUsedSparePartName(sparepartname);
		String patrolgroupName = bo.getPatrolgroupNameById(applyF.getPatrolgroupId());
		bean.setPatrolgroupName(patrolgroupName);
		request.setAttribute("one_apply", bean);
		request.setAttribute("SparepartApplyBean", bean);
		request.getSession().setAttribute("applySs", applySs);
		request.setAttribute("view_method", viewMethod);
		SparepartAuditingBean auditing = bo.findAuditByApplyId(applyFId);
		request.setAttribute("auditing",auditing);
		String act = request.getParameter("act");
		if(act !=null && act.equals("update")){
			UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");			
			String baseid = request.getParameter("baseid");

			List usedPositions = bo.getusedPositionsByEdit(baseid,applyFId) ;//查处备件的使用位置

			List patrolgroups = bo.patrolgroupsByDeptID(user.getDeptID());//得到部门下的巡检维护组
			List patrolgroupsC = bo.patrolgroupsByDeptIDChange(user.getDeptID(),baseid);
			int m = 0;
			if(applySs !=null && applySs.size()>0){
				for(int i = 0;i<applySs.size();i++){
					SparepartApplyS applys = (SparepartApplyS) applySs.get(i);
					if(applys.getUsedSerialNumber()!=null && !applys.getUsedSerialNumber().equals("")){
						m++;
					}
				}
				if(m==0){//直接使用的				
					/*List usedPosition= bo.getusedPositions(baseid) ;
					if(usedPosition!=null && usedPosition.size()>0){
						BasicDynaBean bDB = (BasicDynaBean) usedPosition.get(0);
						String positon = (String) bDB.get("storage_position");
						List serialNumsRun = bo.findSerialNumsByBaseId(baseid,user.getDeptID(),SparepartConstant.IS_RUNNING,positon);
						request.setAttribute("serialNumsRun",serialNumsRun);
						request.setAttribute("usedPosition",usedPosition);//直接使用的只可以查询状态为运行的
					}*/
					request.setAttribute("usedPosition","s");
				}else{
					request.setAttribute("usedPositions",usedPositions);
					String applyUsePosition = bean.getApplyUsePosition();
					System.out.println("applyUsePosition======="+applyUsePosition);
					List serialNums = bo.getSerialNmuByPositon(applyUsePosition, baseid,applySs);
					request.setAttribute("editSerialNums",serialNums);
				}
			}	
			request.setAttribute("patrolgroupsC",patrolgroupsC);
			request.setAttribute("sparePartId", baseid);		
			request.setAttribute("user_name", user.getUserName());
			request.setAttribute("user_id", user.getUserID());
			request.setAttribute("dept_id", user.getDeptID());
			request.setAttribute("dept_name", user.getDeptName());
			request.setAttribute("patrolgroups",patrolgroups);
			request.setAttribute("applyFId",applyFId);
			return mapping.findForward("editApplyForm");
		}
		return mapping.findForward("viewOneApplyInfoForAppply");
	}

	/**
	 * 显示代维申请使用的备件的列表，方便移动审核
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listWaitAuditingApplyForAu(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = bo.listWaitAuditingApplyForAu();
		request.getSession().setAttribute("APPLY_LIST", list);
		request.setAttribute("method", "listWaitAuditingApply");
		request.getSession().setAttribute("EXPORT_APPLY_LIST", list);
		super.setPageReset(request);
		return mapping.findForward("listApply");
	}

	/**
	 * 移动用户查询审核申请信息列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doQueryForAu(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		String serialNumber = request.getParameter("serial_number");
		String storagePosition = request.getParameter("storage_position");
		String begintime = request.getParameter("begintime");
		String endtime = request.getParameter("endtime");
		String condition = "";
		String res = request.getParameter("auditing_result");
		if ("1".equals(request.getParameter("reset_query"))) {
			if ("03".equals(res)) {
				condition += (" and appf.auditing_state='00'");
			}
			if (res == null || "".equals(res)) {

			}
			if ("00".equals(res) || "01".equals(res) || "02".equals(res)) {
				condition += ("   and appf.auditing_state='" + res + "'");
			}
			if (serialNumber != null && !serialNumber.equals("")) {
				condition += (" and apps.serial_number like '%"
						+ serialNumber + "%'");
			}
			if (storagePosition != null && !storagePosition.equals("")) {
				condition += (" and appf.apply_use_position like '%"
						+ storagePosition + "%'");
			}
			if (begintime != null && !begintime.equals("")) {
				String[] begtime = begintime.split("/");
				String beg = "";
				for(int i = 0 ; i < begtime.length; i++) {
					beg += begtime[i] + "-";
				}
				beg = beg.substring(0, beg.length()-1);
				condition = condition + " and appf.apply_date >=TO_DATE('"
				+ begintime + "','YYYY-MM-DD')";
			}
			if (endtime != null && !endtime.equals("")) {
				String[] etime = endtime.split("/");
				String end = "";
				for(int i = 0 ; i < etime.length; i++) {
					end += etime[i] + "-";
				}
				end = end.substring(0, end.length()-1);
				condition = condition + " and appf.apply_date <= TO_DATE('" + endtime
				+ " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
			}
			request.getSession().setAttribute("QUERY_CONDITION", condition);
		} else {
			condition = (String) request.getSession().getAttribute(
			"QUERY_CONDITION");
			if (condition == null || condition.equals("")) {
				condition = "";
			}
		}
		System.out.println(condition + "======");
		List list = bo.doQueryforAu(condition);
		request.getSession().setAttribute("APPLY_LIST", list);
		request.getSession().setAttribute("EXPORT_APPLY_LIST", list);
		return mapping.findForward("listApply");
	}

	/**
	 * 备件申请的删除
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward delApply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		if (!CheckPower.checkPower(request.getSession(), "90707")) {
			return mapping.findForward("powererror");
		}
		String applyfId = request.getParameter("apply_id");
		SparepartApplyF applyF = bo.getApplyF(applyfId);
		Map param = new HashMap();
		param.clear();
		param.put("storage_id", applyF.getSparePartId());
		try{
			List applyss = bo.getApplySs(applyfId);
			if(applyss != null && applyss.size()>0){
				for(int i = 0;i<applyss.size();i++){
					SparepartApplyS applys = (SparepartApplyS)applyss.get(i);
					String seriNums = applys.getSerialNumber();
					String usedNums = applys.getUsedSerialNumber();
					bo.updatSparepartState(seriNums,SparepartConstant.CONTRACTOR_WAIT_USE);//更新状态
					if(usedNums !=null && usedNums.trim().length()>0){
						bo.updatSparepartState(usedNums,SparepartConstant.IS_RUNNING);//更新状态
					}
					bo.deleteApplyS(applys);//删除子申请表表
				}
			}
			bo.deleteApplyF(applyF);//删除父申请表表
			return super.forwardInfoPageWithParam(mapping, request,"9070203ok", param);
		}catch(Exception e){
			e.getStackTrace();
		}
		return super.forwardErrorPageWithParam(mapping, request,"9070203err", param);
	}

}
