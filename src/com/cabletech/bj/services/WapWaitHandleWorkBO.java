package com.cabletech.bj.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.bj.dao.WaitHandleWorkDao;
import com.cabletech.linepatrol.acceptance.workflow.AcceptanceFlow;
import com.cabletech.linepatrol.cut.services.CutManager;
import com.cabletech.linepatrol.dispatchtask.services.DispatchTaskBO;
import com.cabletech.linepatrol.drill.services.DrillTaskBo;
import com.cabletech.linepatrol.hiddanger.model.HiddangerRegist;
import com.cabletech.linepatrol.hiddanger.service.HiddangerRegistManager;
import com.cabletech.linepatrol.hiddanger.workflow.HiddangerWorkflowBO;
import com.cabletech.linepatrol.maintenance.service.TestPlanBO;
import com.cabletech.linepatrol.maintenance.workflow.MaintenanceWorkflowBO;
import com.cabletech.linepatrol.material.service.MaterialApplyBo;
import com.cabletech.linepatrol.safeguard.workflow.SafeguardWorkflowBO;
import com.cabletech.linepatrol.trouble.workflow.TroubleWorkflowBO;
import com.cabletech.menu.domainobjects.FirstMenu;
import com.cabletech.menu.domainobjects.SecondlyMenu;
import com.cabletech.menu.services.MenuService;

@Service
@Transactional
public class WapWaitHandleWorkBO {
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

	@Resource(name = "drillTaskBo")
	private DrillTaskBo drillTaskBo;
	@Resource(name = "acceptanceFlow")
	private AcceptanceFlow acceptanceFWBo;

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
					// waitHandleTroubleNumber =
					// getWaitHandleTroubleNumber(userInfo);
					// firstMenu.setWaitHandleNumber(Integer
					// .toString(waitHandleTroubleNumber));
					// firstMenu.setSubMenuId("2101");
				}
				// 隐患盯防
				if (firstMenu.getId().equals("22")) {
					// waitHandleHideDangerNumber =
					// getWaitHandleHideDangerNumber(userInfo);
					// firstMenu.setWaitHandleNumber(Integer
					// .toString(waitHandleHideDangerNumber));
					// firstMenu.setSubMenuId("2201");
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
					// waitHandleYMPlanNumber =
					// getWaitHandleYMPlanNumber(userInfo);
					// firstMenu.setWaitHandleNumber(Integer
					// .toString(waitHandleYMPlanNumber));
					// firstMenu.setSubMenuId("205");
				}
				// 材料管理
				if (firstMenu.getId().equals("8")) {
					// waitHandleMaterialApplyNumber =
					// getWaitHandleMaterialApplyNumber(userInfo);
					// waitHandleMaterialUserdNumber =
					// getWaitHandleMaterialUsedNumber(userInfo);
					// firstMenu.setWaitHandleNumber(Integer
					// .toString(waitHandleMaterialApplyNumber
					// + waitHandleMaterialUserdNumber));
					// firstMenu.setSubMenuId("815");
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
					// testPlanNumber = getWaitHandleTestPlanNumber(userInfo);
					// firstMenu.setWaitHandleNumber(Integer
					// .toString(testPlanNumber));
					// firstMenu.setSubMenuId("2501");
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
					// waitHandleSafeguardNumber =
					// getWaitHandleSafeguardNumber(userInfo);
					// firstMenu.setWaitHandleNumber(Integer
					// .toString(waitHandleSafeguardNumber));
					// firstMenu.setSubMenuId("2601");
				}
				if (firstMenu.getId().equals("27")) {
					// waitHandleAcceptanceNumber =
					// getWaitHandleAcceptanceNumber(userInfo);
					// firstMenu.setWaitHandleNumber(Integer
					// .toString(waitHandleAcceptanceNumber));
					// firstMenu.setSubMenuId("2702");
				}
				vector.add(firstMenu);
			}
		}
		oneMenuVector = vector;
	}

	public int getWaitHandleAcceptanceNumber(UserInfo userInfo) {
		int waitHandleAcceptanceNumber = 0;
		int waitHandleAcceptanceNumberForDept = acceptanceFWBo
				.queryForHandleNumber(userInfo.getDeptID(), "", "");
		int waitHandleAcceptanceNumberForUserId = acceptanceFWBo
				.queryForHandleNumber(userInfo.getUserID(), "", "");
		waitHandleAcceptanceNumber = waitHandleAcceptanceNumberForDept
				+ waitHandleAcceptanceNumberForUserId;
		return waitHandleAcceptanceNumber;
	}

	/**
	 * 根据登陆用户获得待办的保障数量
	 * 
	 * @param userInfo
	 * @return
	 */
	public int getWaitHandleSafeguardNumber(UserInfo userInfo) {
		int waitHandleSafeguardNumber = 0;
		// if ("1".equals(userInfo.getDeptype())) {
		waitHandleSafeguardNumber = safeguardWfBo.queryForHandleNumber(userInfo
				.getUserID(), "", "");
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
	public int getWaitHandleDrillNumber(UserInfo userInfo) {
		int waitHandleDrillNumber = 0;
		List list = new ArrayList();
		list.addAll(drillTaskBo.getAgentList(userInfo, "",
				"approve_drill_proj_task"));
		list.addAll(drillTaskBo.getAgentList(userInfo, "",
				"approve_change_drill_proj_task"));
		list.addAll(drillTaskBo.getAgentList(userInfo, "",
				"approve_drill_summary_task"));
		if (list != null) {
			waitHandleDrillNumber = list.size();
		}
		return waitHandleDrillNumber;
	}

	/**
	 * 根据当前登录用户信息获取待办的割接数量
	 * 
	 * @param userInfo
	 * @return
	 */
	public int getWaitHandleCutNumber(UserInfo userInfo) {
		int waitHandleCutNumber = 0;
		List list = cutManager.getHandWork(userInfo.getUserID(), "",
				"apply_approve_task,work_approve_task");
		if (list != null && !list.isEmpty()) {
			waitHandleCutNumber = list.size();
		}
		return waitHandleCutNumber;
	}

	/**
	 * 根据当前登录用户信息获取待办的隐患数量
	 * 
	 * @param userInfo
	 * @return
	 */
	public int getWaitHandleHideDangerNumber(UserInfo userInfo) {
		// int waitHandleHideDangerNumber = 0;
		// if ("1".equals(userInfo.getDeptype())) {
		// waitHandleHideDangerNumber = hideDangerWfBo.queryForHandleNumber(
		// userInfo.getUserID(), "", "");
		// }
		// if ("2".equals(userInfo.getDeptype())) {
		// waitHandleHideDangerNumber = hideDangerWfBo.queryForHandleNumber(
		// userInfo.getDeptID(), "", "");
		// }
		// return waitHandleHideDangerNumber;

		List<HiddangerRegist> list = hiddangerRegistManager.getToDoWork(
				userInfo, "");
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
		List list = troubleWfBo
				.findWaitReplys(userInfo, userid, "approve_task");
		if (list != null && list.size() > 0) {
			waitHandleTroubleNumber = list.size();
		}
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
		/*
		 * waitHandleTroubleNumber =
		 * maintenanceWFBO.queryForHandleNumber(userInfo .getUserID(), "", "");
		 */
		List list = testPlanBO.getWaitWork(userInfo, "");
		if (list != null) {
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
		// list = dispatchTaskBO.processHandleTaskList(list);
		if (list != null) {
			waitHandleSendTaskNumber = list.size();
		}
		return waitHandleSendTaskNumber;
	}
}
