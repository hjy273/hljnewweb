package com.cabletech.linecut.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.linecut.beans.LineCutBean;
import com.cabletech.linecut.dao.LineCutDao;

public class LineCutReService extends BaseBisinessObject {
	private LineCutDao dao = new LineCutDao();

	public LineCutReService() {
	}

	public List getLineList(UserInfo userinfo) {
		return dao.getLineList(userinfo);
	}

	public List getSubLineList(UserInfo userinfo) {
		return dao.getSubLineList(userinfo);
	}

	
	public LineCutBean getOneCutQueryInfo( String reid ){
		return dao.getOneCutQueryInfo(reid);
	}
	/**
	 * 获取线路级别
	 * @return
	 */
	public List getLineLevle() {
		return dao.getLineLevle();
	}

	/**
	 * 根据线路级别获取该用户管理的线路
	 * @param userinfo
	 * @param levelId
	 * @return
	 */
	public List getLineByLevel(UserInfo userinfo, String levelId) {
		return dao.getLineByLevel(userinfo, levelId);
	}

	/**
	 * 根据线路ID获取该用户管理的线段
	 * @param userinfo
	 * @param levelId
	 * @return
	 */
	public List getSubLineByLineId(UserInfo userinfo, String lineId) {
		return dao.getSubLineByLineId(userinfo, lineId);
	}

	public boolean addInfo(LineCutBean bean, String updoc) {
		return dao.addInfo(bean, updoc);
	}

	public List getAllOwnRe(HttpServletRequest request) {
		return dao.getAllOwnRe(request);
	}

	/*  public LineCutBean getOneUse( String reid ){
	      return dao.getOneUse( reid );
	  }*/

	public boolean valiCanUp(String reid) {
		return dao.valiCanUp(reid);
	}

	public boolean doUp(String[] delfileid, LineCutBean bean) {
		return dao.doUp(delfileid, bean);
	}

	public boolean doDel(String delfileid, String reid) {
		return dao.doDel(delfileid, reid);
	}

	public List getAllUsers(String contractorid) {
		return dao.getAllUsers(contractorid);
	}

	public List getAllNames(String contractorid) {
		return dao.getAllNames(contractorid);
	}

	public List getAllReasons(String contractorid) {
		return dao.getAllReasons(contractorid);
	}

	public List getAllAddresss(String contractorid) {
		return dao.getAllAddresss(contractorid);
	}

	public List getAllEfsystems(String contractorid) {
		return dao.getAllEfsystems(contractorid);
	}

	public List getAllSublineids(String contractorid) {
		return dao.getAllSublineids(contractorid);
	}

	public List getAllOwnReForSearch(LineCutBean bean, HttpSession session) {
		return dao.getAllOwnReForSearch(bean, session);
	}

	public List doQueryAfterMod(String sql) {
		return dao.doQueryAfterMod(sql);
	}

	/**
	 * 模糊查询割接原因
	 * @param searchCt
	 * @param contractorid
	 * @return
	 */
	public List getReasonByInput(String searchCt, String contractorid) {
		return dao.getReasonByInput(searchCt, contractorid);
	}

	public String getReasonByInputMod(String searchCt, String contractorid) {
		return dao.getReasonByInputMod(searchCt, contractorid);
	}

	public String getAllReason(String depid) {
		return dao.getAllReason(depid);
	}

	//===================审批管理=====================//
	public List getAllReForAu(String regionid) {
		return dao.getAllReForAu(regionid);
	}

	public boolean addAuInfo(LineCutBean bean) {
		return dao.addAuInfo(bean);
	}

	public List getAllOwnAu(HttpServletRequest request) {
		return dao.getAllOwnAu(request);
	}

	public List getReApprove(String reid) {
		return dao.getReApprove(reid);
	}

	public LineCutBean getOneUseForDMod(String reid) {
		return dao.getOneUseForDMod(reid);
	}

	public boolean valiApprove(String reid) {
		return dao.valiApprove(reid);
	}

	/*  public LineCutBean getOneAuInfo( String reid ){
	      return dao.getOneAuInfo( reid );
	  }*/

	public List getAllUsersForAu(String contractorid) {
		return dao.getAllUsersForAu(contractorid);
	}

	public List getAllNamesForAu(String contractorid) {
		return dao.getAllNamesForAu(contractorid);
	}

	public List getAllReasonsForAu(String contractorid) {
		return dao.getAllReasonsForAu(contractorid);
	}

	public List getAllAddresssForAu(String contractorid) {
		return dao.getAllAddresssForAu(contractorid);
	}

	public List getAllEfsystemsForAu(String contractorid) {
		return dao.getAllEfsystemsForAu(contractorid);
	}

	public List getAllSublineidsForAu(String contractorid) {
		return dao.getAllSublineidsForAu(contractorid);
	}

	public List getAllOwnReForAuSearch(LineCutBean bean, HttpSession session) {
		return dao.getAllOwnReForAuSearch(bean, session);
	}

	public List getConditionsReForSearch(LineCutBean bean,HttpServletRequest request)
	{
		return dao.getConditionsReForSearch(bean, request);
	}
	
	public List getConditionsReForQuery(LineCutBean bean,HttpServletRequest request)
	{
		return dao.getConditionsReForQuery(bean, request);
	}
	
	public List getAllOwnReForAuSearchAfterMod(String sql) {
		return dao.getAllOwnReForAuSearchAfterMod(sql);
	}

	public List getNameByClewId(String sublineid, String deptid) {
		return dao.getNameByClewId(sublineid, deptid);
	}

	public List getNameByClewId(String sublineid) {
		return dao.getNameByClewId(sublineid);
	}
	
	public List getNameBySublineid(String sublineid, UserInfo userinfo) {
		return dao.getNameBySublineid(sublineid, userinfo);
	}

	public List getNameBySublineidAndDeptid(String sublineid, String deptid,
			UserInfo userinfo) {
		return dao.getNameBySublineidAndDeptid(sublineid, deptid, userinfo);
	}

	public List getCutNameBySublineid(String sublineid, String contractorid) {
		return dao.getCutNameBySublineid(sublineid, contractorid);
	}

	///////////////////////验收///////////////
	public List getAllOwnAcc(HttpServletRequest request) {
		return dao.getAllOwnAcc(request);
	}

	public List getAllWorkForAcc(HttpServletRequest request) {
		return dao.getAllWorkForAcc(request);
	}

	public boolean addAcceptInfo(LineCutBean bean) {
		return dao.addAcceptInfo(bean);
	}

	public LineCutBean getOneAccMod(String reid) {
		return dao.getOneAccMod(reid);
	}

	public List queryAcc(LineCutBean bean, String deptType, HttpSession session) {
		return dao.queryAcc(bean, deptType, session);
	}

	public List queryAccAfterMod(String sql) {
		return dao.queryAccAfterMod(sql);
	}

	public List getAllCon() {
		return dao.getAllCon();
	}

	/**
	 * <br>
	 * 功能:获得指定割接申请单信息 <br>
	 * 参数:申请id <br>
	 * 返回值:获得成功返回对象,否则返回 NULL;
	 */
	public LineCutBean getOneUseMod(String reid) {
		return dao.getOneUseMod(reid);
	}

	public LineCutBean getOneAuInfoMod(String reid) {
		return dao.getOneAuInfoMod(reid);
	}

	//统计
	/**
	 * 根据割接类型，条件 统计割接次数
	 */
	public List doStatQueryForCountByCutType(String condition) {
		return dao.doStatQueryForCountByCutType(condition);
	}

	/**
	 * 根据线路级别，条件 统计割接次数
	 * @param condition
	 * @return
	 */
	public List doQueryForCountByLevel(String condition) {
		return dao.doQueryForCountByLevel(condition);
	}

	/**
	 * 根据割接类型 与 条件查询 割接耗时(暂时没用)
	 * @param condition
	 * @return
	 */
	public List doQueryForTimeByType(String condition) {
		return dao.doQueryForTimeByType(condition);
	}
	
	/**
	 * 根据线路级别 条件 统计割接时间
	 * @param condition
	 * @return
	 */
	public List doQueryForTimeByLevel(String condition) {
		return dao.doQueryForTimeByLevel(condition);
	}


	//导出EXCEL
	/**
	 * @param list
	 * @param response
	 */
	public boolean exportCountByType(List list, HttpServletResponse response) {
		return dao.exportCountByType(list, response);
	}
	
	public boolean exportCountByLevel(List list, HttpServletResponse response) {
		return dao.exportCountByLevel(list, response);
	}
	
	public boolean exportTimeByLevel(List list, HttpServletResponse response) {
		return dao.exportTimeByLevel(list, response);
	}
	
	public boolean exportTimeByType(List list, HttpServletResponse response) {
		return dao.exportTimeByType(list, response);
	}
	
	public boolean exportQueryStat(List list, HttpServletResponse response) {
		return dao.exportQueryStat(list, response);
	}
}
