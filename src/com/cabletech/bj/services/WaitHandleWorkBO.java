package com.cabletech.bj.services;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.ContractorBO;
import com.cabletech.bj.dao.WaitHandleWorkDao;
import com.cabletech.linepatrol.acceptance.model.Apply;
import com.cabletech.linepatrol.acceptance.service.ApplyManager;
import com.cabletech.linepatrol.appraise.beans.AppraiseResultBean;
import com.cabletech.linepatrol.appraise.module.AppraiseResult;
import com.cabletech.linepatrol.appraise.module.AppraiseYearResult;
import com.cabletech.linepatrol.appraise.service.AppraiseConstant;
import com.cabletech.linepatrol.appraise.service.AppraiseMonthBO;
import com.cabletech.linepatrol.appraise.service.AppraiseYearResultBO;
import com.cabletech.linepatrol.cut.services.CutManager;
import com.cabletech.linepatrol.dispatchtask.services.DispatchTaskBO;
import com.cabletech.linepatrol.drill.services.DrillTaskBo;
import com.cabletech.linepatrol.drill.workflow.DrillWorkflowBO;
import com.cabletech.linepatrol.hiddanger.model.HiddangerRegist;
import com.cabletech.linepatrol.hiddanger.service.HiddangerRegistManager;
import com.cabletech.linepatrol.hiddanger.workflow.HiddangerWorkflowBO;
import com.cabletech.linepatrol.maintenance.service.TestPlanBO;
import com.cabletech.linepatrol.maintenance.workflow.MaintenanceWorkflowBO;
import com.cabletech.linepatrol.material.service.MaterialApplyBo;
import com.cabletech.linepatrol.overhaul.model.OverHaul;
import com.cabletech.linepatrol.overhaul.service.OverhaulManager;
import com.cabletech.linepatrol.project.domain.ProjectRemedyApply;
import com.cabletech.linepatrol.project.service.RemedyApplyManager;
import com.cabletech.linepatrol.safeguard.services.SafeguardTaskBo;
import com.cabletech.linepatrol.safeguard.workflow.SafeguardWorkflowBO;
import com.cabletech.linepatrol.trouble.workflow.TroubleWorkflowBO;
import com.cabletech.menu.domainobjects.FirstMenu;
import com.cabletech.menu.domainobjects.SecondlyMenu;
import com.cabletech.menu.services.MenuService;

@Service
@Transactional
public class WaitHandleWorkBO {
	@Resource(name = "troubleWorkflowBO")
	private TroubleWorkflowBO troubleWfBo;

	@Resource(name = "dispatchTaskBO")
	private DispatchTaskBO dispatchTaskBO;

	@Resource(name = "materialApplyBo")
	private MaterialApplyBo materialApplyBo;

	@Resource(name = "maintenanceWorkflowBO")
	private MaintenanceWorkflowBO maintenanceWFBO;
	
	@Resource(name = "testPlanBO")
	private TestPlanBO testPlanBO;
	
	@Resource(name = "hiddangerWorkflowBO")
	private HiddangerWorkflowBO hideDangerWfBo;
	
	@Resource(name = "hiddangerRegistManager")
	private HiddangerRegistManager hiddangerRegistManager;

	@Resource(name = "cutManager")
	private CutManager cutManager;

	@Resource(name = "waitHandleWorkDao")
	private WaitHandleWorkDao dao;

	@Resource(name = "safeguardWorkflowBO")
	private SafeguardWorkflowBO safeguardWfBo;

	@Resource(name = "drillWorkflowBO")
	private DrillWorkflowBO drillWfBo;
	
	@Resource(name = "drillTaskBo")
	private DrillTaskBo drillTaskBo;
	
	@Resource(name = "safeguardTaskBo")
	private SafeguardTaskBo safeguardTaskBo;
	
	@Resource(name = "applyManager")
	private ApplyManager applyManager;
	
	@Resource(name = "overhaulManager")
	private OverhaulManager overhaulManager;
	
	@Resource(name = "remedyApplyManager")
	private RemedyApplyManager remedyApplyManager;
	
	@Resource(name = "appraiseMonthBO")
	private AppraiseMonthBO appraiseBO;
	@Resource(name ="appraiseYearResultBO")
	private AppraiseYearResultBO appraiseYearResultBO;
	public void processMenuVector(Vector oneMenuVector, UserInfo userInfo)
			throws Exception {
		Vector vector = new Vector();
		int waitHandleCutNumber = 0;
		int waitHandleHideDangerNumber = 0;
		int waitHandleTroubleNumber = 0;
		int waitHandleYMPlanNumber = 0;
		int waitHandleMaterialApplyNumber = 0;
		int waitHandleMaterialUserdNumber = 0;
		int waitHandleSendTaskNumber = 0;
		int testPlanNumber = 0;
		int waitHandleSafeguardNumber = 0;
		int waitHandleDrillNumber = 0;
		int waitHandleAcceptanceNumber = 0;
		int waitHandleProjectNumber = 0;
		int waitHandleOverHaulNumber = 0;
		int waitHandleAppraiseMonthNumber = 0;
		int waitHandleAppraiseSpecialNumber = 0;
		int waitHandleAppraiseYearNumber = 0;
		int waitHandleAppraiseYearEndNumber = 0;
		if (oneMenuVector != null) {
			Iterator oneMenuIterator = oneMenuVector.iterator();
			Vector twoMenuVector;
			FirstMenu firstMenu;
			SecondlyMenu secondMenu;
			MenuService menuService = new MenuService();
			twoMenuVector = menuService.getSecondlyMenu(userInfo);
			while (oneMenuIterator != null && oneMenuIterator.hasNext()) {
				firstMenu = (FirstMenu) oneMenuIterator.next();
				firstMenu.setWaitHandleNumber("0");
				if (twoMenuVector != null && !twoMenuVector.isEmpty()) {
					for (int i = 0; i < twoMenuVector.size(); i++) {
						secondMenu = (SecondlyMenu) twoMenuVector.elementAt(i);
						if (secondMenu != null
								&& secondMenu.getParentid() != null) {
							if (secondMenu.getParentid().equals(
									firstMenu.getId())) {
								firstMenu.setSubMenuId(secondMenu.getId());
								break;
							}
						}
					}
				}
				// 故障管理
				if (firstMenu.getId().equals("21")) {
					waitHandleTroubleNumber = getWaitHandleTroubleNumber(userInfo);
					firstMenu.setWaitHandleNumber(Integer
							.toString(waitHandleTroubleNumber));
					firstMenu.setSubMenuId("2101");
				}
				// 隐患盯防
				if (firstMenu.getId().equals("22")) {
					waitHandleHideDangerNumber = getWaitHandleHideDangerNumber(userInfo);
					firstMenu.setWaitHandleNumber(Integer
							.toString(waitHandleHideDangerNumber));
					firstMenu.setSubMenuId("2201");
				}
				// 割接管理
				if (firstMenu.getId().equals("23")) {
					waitHandleCutNumber = getWaitHandleCutNumber(userInfo);
					firstMenu.setWaitHandleNumber(Integer
							.toString(waitHandleCutNumber));
					firstMenu.setSubMenuId("2301");
				}
				// 巡检管理
				if (firstMenu.getId().equals("2")) {
					waitHandleYMPlanNumber = getWaitHandleYMPlanNumber(userInfo);
					firstMenu.setWaitHandleNumber(Integer
							.toString(waitHandleYMPlanNumber));
					// firstMenu.setSubMenuId("205");
				}
				// 材料管理
				if (firstMenu.getId().equals("8")) {
					waitHandleMaterialApplyNumber = getWaitHandleMaterialApplyNumber(userInfo);
					// waitHandleMaterialUserdNumber =
					// getWaitHandleMaterialUsedNumber(userInfo);
					firstMenu.setWaitHandleNumber(Integer
							.toString(waitHandleMaterialApplyNumber
									+ waitHandleMaterialUserdNumber));
					firstMenu.setSubMenuId("815");
				}
				// 任务派单
				if (firstMenu.getId().equals("11")) {
					waitHandleSendTaskNumber = getWaitHandleSendTaskNumber(userInfo);
					firstMenu.setWaitHandleNumber(Integer
							.toString(waitHandleSendTaskNumber));
					firstMenu.setSubMenuId("11002");
				}
				// 技术测试
				if (firstMenu.getId().equals("25")) {
					testPlanNumber = getWaitHandleTestPlanNumber(userInfo);
					firstMenu.setWaitHandleNumber(Integer
							.toString(testPlanNumber));
					firstMenu.setSubMenuId("2501");
				}
				// 演练
				if (firstMenu.getId().equals("24")) {
					waitHandleDrillNumber = getWaitHandleDrillNumber(userInfo);
					firstMenu.setWaitHandleNumber(Integer
							.toString(waitHandleDrillNumber));
					firstMenu.setSubMenuId("2401");
				}
				// 保障管理
				if (firstMenu.getId().equals("26")) {
					waitHandleSafeguardNumber = getWaitHandleSafeguardNumber(userInfo);
					firstMenu.setWaitHandleNumber(Integer
							.toString(waitHandleSafeguardNumber));
					firstMenu.setSubMenuId("2601");
				}
				//验收交维
				if (firstMenu.getId().equals("27")) {
					waitHandleAcceptanceNumber = getWaitHandleAcceptanceNumber(userInfo);
					firstMenu.setWaitHandleNumber(Integer
							.toString(waitHandleAcceptanceNumber));
					firstMenu.setSubMenuId("2702");
				}
				//工程管理
				if (firstMenu.getId().equals("28")) {
					waitHandleProjectNumber = getWaitHandleProjectNumber(userInfo);
					firstMenu.setWaitHandleNumber(Integer
							.toString(waitHandleProjectNumber));
					firstMenu.setSubMenuId("2802");
				}
				//大修项目
				if (firstMenu.getId().equals("31")) {
					waitHandleOverHaulNumber = getWaitHandleOverHaulNumber(userInfo);
					firstMenu.setWaitHandleNumber(Integer
							.toString(waitHandleOverHaulNumber));
					firstMenu.setSubMenuId("3132");
				}
				//月度考核
				if (firstMenu.getId().equals("32")) {
					waitHandleAppraiseMonthNumber = getWaitHandleAppraiseMonthNumber(userInfo);
					firstMenu.setWaitHandleNumber(Integer
							.toString(waitHandleAppraiseMonthNumber));
					firstMenu.setSubMenuId("3204");
				}
				//专项考核
				if (firstMenu.getId().equals("33")) {
					waitHandleAppraiseSpecialNumber = getWaitHandleAppraiseSpecialNumber(userInfo);
					firstMenu.setWaitHandleNumber(Integer
							.toString(waitHandleAppraiseSpecialNumber));
					firstMenu.setSubMenuId("3303");
				}
				//年终检查
				if (firstMenu.getId().equals("34")) {
					waitHandleAppraiseYearEndNumber = getWaitHandleAppraiseYearEndNumber(userInfo);
					firstMenu.setWaitHandleNumber(Integer
							.toString(waitHandleAppraiseYearEndNumber));
					firstMenu.setSubMenuId("3404");
				}
				//年度考核
				if (firstMenu.getId().equals("35")) {
					waitHandleAppraiseYearNumber = getWaitHandleAppraiseYearNumber(userInfo);
					firstMenu.setWaitHandleNumber(Integer
							.toString(waitHandleAppraiseYearNumber));
					firstMenu.setSubMenuId("3502");
				}
				vector.add(firstMenu);
			}
		}
		oneMenuVector = vector;
	}


	private int getWaitHandleAppraiseYearNumber(UserInfo userInfo) {
		AppraiseResultBean appraiseResultBean=new AppraiseResultBean();
		if(userInfo.getDeptype().equals("1")){
			appraiseResultBean.setConfirmResult("'"+AppraiseConstant.STATE_WAIT_SEND+"','"+AppraiseConstant.STATE_VERIFY_NOT_PASS+"'");
		}else{
			appraiseResultBean.setConfirmResult("'"+AppraiseConstant.STATE_WAIT_VERIFY+"'");
		}
		List<AppraiseYearResult> appraiseYearResults = appraiseYearResultBO.getResultByAppraiseResultBean(appraiseResultBean);
		return appraiseYearResults.size();
	}

	private int getWaitHandleAppraiseYearEndNumber(UserInfo userInfo) {
		String appraiseType=AppraiseConstant.APPRAISE_YEAREND;
		int size=0;
		size = getAppraiseSize(userInfo, appraiseType);
		return size;
	}

	private int getWaitHandleAppraiseSpecialNumber(UserInfo userInfo) {
		String appraiseType=AppraiseConstant.APPRAISE_SPECIAL;
		int size=0;
		size = getAppraiseSize(userInfo, appraiseType);
		return size;
	}

	private int getWaitHandleAppraiseMonthNumber(UserInfo userInfo) {
		String appraiseType=AppraiseConstant.APPRAISE_MONTH;
		int size=0;
		size = getAppraiseSize(userInfo, appraiseType);
		return size;
	}


	private int getAppraiseSize(UserInfo userInfo, String appraiseType) {
		int size;
		AppraiseResultBean appraiseResultBean=new AppraiseResultBean();
		appraiseResultBean.setType(appraiseType);
		if(userInfo.getDeptype().equals("1")){
			appraiseResultBean.setConfirmResult("'"+AppraiseConstant.STATE_WAIT_SEND+"','"+AppraiseConstant.STATE_VERIFY_NOT_PASS+"'");
		}else{
			appraiseResultBean.setConfirmResult("'"+AppraiseConstant.STATE_WAIT_VERIFY+"'");
			appraiseResultBean.setContractorId(userInfo.getDeptID());
		}
		List<AppraiseResult> appraiseResults=appraiseBO.getAppraiseResultByBean(appraiseResultBean);
		size=appraiseResults.size();
		return size;
	}

	private int getWaitHandleAcceptanceNumber(UserInfo userInfo) {
		List<Apply> list = applyManager.getToDoWork(userInfo, "", "");
		return list.size();
	}
	
	public int getWaitHandleProjectNumber(UserInfo userInfo) {
		List<ProjectRemedyApply> list = remedyApplyManager.getToDoWork(userInfo, "");
		return list.size();
	}
	
	public int getWaitHandleOverHaulNumber(UserInfo userInfo) {
		List<OverHaul> list = overhaulManager.getToDoWork(userInfo, "");
		return list.size();
	}

	/**
	 * 根据登陆用户获得待办的保障数量
	 * 
	 * @param userInfo
	 * @return
	 */
	private int getWaitHandleSafeguardNumber(UserInfo userInfo) {
		int waitHandleSafeguardNumber = 0;
		String condition = "";
		if("2".equals(userInfo.getDeptype())){
			condition = " and taskCon.contractor_id='" + userInfo.getDeptID() + "'";
		}
		List list = safeguardTaskBo.getAgentList(userInfo, condition, "");
		if(list != null && list.size() > 0){
			waitHandleSafeguardNumber = list.size();
		}
		// if ("1".equals(userInfo.getDeptype())) {
		/*waitHandleSafeguardNumber = safeguardWfBo.queryForHandleNumber(userInfo
				.getUserID(), "", "");*/
		/*
		 * } if ("2".equals(userInfo.getDeptype())) { waitHandleSafeguardNumber
		 * = safeguardWfBo.queryForHandleNumber( userInfo.getDeptID(), "", "");
		 * }
		 */
		return waitHandleSafeguardNumber;
	}

	/**
	 * 根据登陆用户获得待办的保障数量
	 * 
	 * @param userInfo
	 * @return
	 */
	private int getWaitHandleDrillNumber(UserInfo userInfo) {
		int waitHandleDrillNumber = 0;
		String condition = "";
		if("2".equals(userInfo.getDeptype())){
			condition = " and taskcon.contractor_id='" + userInfo.getDeptID() + "'";
		}
		List list = drillTaskBo.getAgentList(userInfo, condition, "");
		if(list != null && list.size() > 0){
			waitHandleDrillNumber = list.size();
		}
		// if ("1".equals(userInfo.getDeptype())) {
		/*waitHandleDrillNumber = drillWfBo.queryForHandleNumber(userInfo
				.getUserID(), "", "");*/
		/*
		 * } if ("2".equals(userInfo.getDeptype())) { waitHandleDrillNumber =
		 * drillWfBo.queryForHandleNumber(userInfo .getDeptID(), "", ""); }
		 */
		return waitHandleDrillNumber;
	}

	/**
	 * 根据当前登录用户信息获取待办的割接数量
	 * 
	 * @param userInfo
	 * @return
	 */
	public int getWaitHandleCutNumber(UserInfo userInfo) {
		int waitHandleCutNumber = cutManager.queryForHandleNumber(userInfo
				.getUserID());
		return waitHandleCutNumber;
	}

	/**
	 * 根据当前登录用户信息获取待办的隐患数量
	 * 
	 * @param userInfo
	 * @return
	 */
	public int getWaitHandleHideDangerNumber(UserInfo userInfo) {
//		int waitHandleHideDangerNumber = 0;
//		if ("1".equals(userInfo.getDeptype())) {
//			waitHandleHideDangerNumber = hideDangerWfBo.queryForHandleNumber(
//					userInfo.getUserID(), "", "");
//		}
//		if ("2".equals(userInfo.getDeptype())) {
//			waitHandleHideDangerNumber = hideDangerWfBo.queryForHandleNumber(
//					userInfo.getDeptID(), "", "");
//		}
//		return waitHandleHideDangerNumber;
		
		List<HiddangerRegist> list = hiddangerRegistManager.getToDoWork(userInfo, "");
		return list == null ? 0 : list.size();
	}

	/**
	 * 根据当前登录用户信息获取待办的故障数量
	 * 
	 * @param userInfo
	 * @return
	 */
	public int getWaitHandleTroubleNumber(UserInfo userInfo) {
		int waitHandleTroubleNumber = 0;
		String userid = userInfo.getUserID();
		List list = troubleWfBo.findWaitReplys(userInfo, userid, "");
		if (list != null && list.size() > 0) {
			waitHandleTroubleNumber = list.size();
		}
		/*
		 * int waitHandleTroubleNumber = troubleWfBo.queryForHandleNumber(
		 * userInfo, "", "");
		 */
		/*
		 * if ("1".equals(userInfo.getDeptype())) { waitHandleTroubleNumber =
		 * troubleWfBo.queryForHandleNumber(userInfo.getUserID(), "", ""); } if
		 * ("2".equals(userInfo.getDeptype())) { //waitHandleTroubleNumber =
		 * troubleWfBo.queryForHandleNumber(userInfo.getDeptID(), "", ""); }
		 */
		return waitHandleTroubleNumber;
	}

	/**
	 * 根据当前登录用户信息获取待办的技术维护数量
	 * 
	 * @param userInfo
	 * @return
	 */
	public int getWaitHandleTestPlanNumber(UserInfo userInfo) {
		int waitHandleTroubleNumber = 0;
	/*	waitHandleTroubleNumber = maintenanceWFBO.queryForHandleNumber(userInfo
				.getUserID(), "", "");*/
		List list = testPlanBO.getWaitWork(userInfo,"");
		if(list!=null){
			waitHandleTroubleNumber = list.size();
		}
		return waitHandleTroubleNumber;
	}

	/**
	 * 根据当前登录用户信息获取待办的年月计划数量
	 * 
	 * @param userInfo
	 * @return
	 */
	public int getWaitHandleYMPlanNumber(UserInfo userInfo) {
		int waitHandleYMPlanNumber = 0;
		if ("1".equals(userInfo.getDeptype())) {
			String condition = " and exists ( ";
			condition = condition + " select regionid from region r ";
			condition = condition + " where ymp.regionid=r.regionid ";
			condition = condition + " start with r.regionid='";
			condition = condition + userInfo.getRegionid();
			condition = condition + "'";
			condition = condition + " connect by prior ";
			condition = condition + " r.regionid=r.parentregionid ";
			condition = condition + ")";
			List list = dao.getWaitApprovedYMPlanList(condition);
			if (list != null) {
				waitHandleYMPlanNumber = list.size();
			}
		}
		return waitHandleYMPlanNumber;
	}

	/**
	 * 根据当前登录用户信息获取待办的巡检计划数量
	 * 
	 * @param userInfo
	 * @return
	 */
	public int getWaitHandlePlanNumber(UserInfo userInfo) {
		int waitHandlePlanNumber = 0;
		String condition = " and regionid in ( ";
		condition += " select regionid from region ";
		condition += " start with regionid='" + userInfo.getRegionid() + "' ";
		condition += " connect by prior regionid=parentregionid) ";
		List list = dao.getWaitApprovedPlanList(condition);
		if (list != null) {
			waitHandlePlanNumber = list.size();
		}
		return waitHandlePlanNumber;
	}

	/**
	 * 根据当前登录用户信息获取待办的材料入库申请数量
	 * 
	 * @param userInfo
	 * @return
	 */
	public int getWaitHandleMaterialApplyNumber(UserInfo userInfo) {
		int waitHandleMaterialApplyNumber = 0;
		// String condition = "and exists( ";
		// condition = condition + " select r.regionid from region r ";
		// condition = condition + " where u.regionid=r.regionid ";
		// condition = condition + " start with r.regionid='";
		// condition = condition + userInfo.getRegionid();
		// condition = condition + "'";
		// condition = condition
		// + " connect by prior r.regionid=r.parentregionid ";
		// condition = condition + ")";
		// if ("1".equals(userInfo.getDeptype())) {
		// condition += " and mt.approver_id='" + userInfo.getUserID() + "' ";
		// }
		// if ("2".equals(userInfo.getDeptype())) {
		// condition += " and mt.creator='" + userInfo.getUserID() + "' ";
		// }
		// List list = dao.getWaitApprovedMaterialApplyList(condition);
		// if (list != null) {
		// waitHandleMaterialApplyNumber = list.size();
		// }
		List list = materialApplyBo.queryWaitHandleMaterialApplyList(null,
				userInfo, "");
		if (list != null) {
			waitHandleMaterialApplyNumber = list.size();
		}
		return waitHandleMaterialApplyNumber;
	}

	/**
	 * 根据当前登录用户信息获取待办的材料盘点申请数量
	 * 
	 * @param userInfo
	 * @return
	 */
	public int getWaitHandleMaterialUsedNumber(UserInfo userInfo) {
		int waitHandleMaterialUserdNumber = 0;
		String condition = "and exists( ";
		condition = condition + " select r.regionid from region r ";
		condition = condition + " where u.regionid=r.regionid ";
		condition = condition + " start with r.regionid='";
		condition = condition + userInfo.getRegionid();
		condition = condition + "'";
		condition = condition
				+ " connect by prior r.regionid=r.parentregionid ";
		condition = condition + ")";
		if ("1".equals(userInfo.getDeptype())) {
			condition += " and mt.approver_id='" + userInfo.getUserID() + "' ";
		}
		if ("2".equals(userInfo.getDeptype())) {
			condition += " and mt.creator='" + userInfo.getUserID() + "' ";
		}
		List list = dao.getWaitApprovedMaterialUsedList(condition);
		if (list != null) {
			waitHandleMaterialUserdNumber = list.size();
		}
		return waitHandleMaterialUserdNumber;
	}

	/**
	 * 根据当前登录用户信息获取待办的派单数量
	 * 
	 * @param userInfo
	 * @return
	 */
	public int getWaitHandleSendTaskNumber(UserInfo userInfo) {
		int waitHandleSendTaskNumber = 0;
		List list = dispatchTaskBO.queryForHandleDispatchTask("", "", userInfo);
		if (list != null) {
			waitHandleSendTaskNumber = list.size();
		}
		return waitHandleSendTaskNumber;
	}
}
