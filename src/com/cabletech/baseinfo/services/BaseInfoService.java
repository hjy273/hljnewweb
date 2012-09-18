package com.cabletech.baseinfo.services;

import java.util.*;

import javax.servlet.http.*;

import org.apache.commons.beanutils.DynaBean;

import com.cabletech.baseinfo.beans.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.base.*;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.fsmanager.domainobjects.RouteInfo;
import com.cabletech.linepatrol.maintenance.service.MainTenanceConstant;
import com.cabletech.linepatrol.remedy.domain.CountyInfo;
import com.cabletech.linepatrol.remedy.service.CountyInfoBO;

/**
 * Service Factory
 * 
 */
public class BaseInfoService extends BaseService {
	DepartBO bod;

	ContractorBO boc;

	PatrolManBO bom;

	PatrolManSonBO boms;

	LineBO bol;

	SublineBO bos;

	PointBO bop;

	TempPointBO botp;

	RegionBO bor;

	UserInfoBO bou;

	TerminalBO bot;

	CountyInfoBO bic;

	// zsh
	PointExtraBO poExBo;

	PatrolOpBO opBo;

	// alien
	TaskOperationBO boTo;

	AlertInfoBO boAi;

	AlertreceiverListBO boAl;

	UsergroupBO ugBo;

	ExportBO boexport;

	public BaseInfoService() {
		bod = new DepartBO();
		bic = new CountyInfoBO();
		boc = new ContractorBO();
		bom = new PatrolManBO();
		boms = new PatrolManSonBO();
		bol = new LineBO();
		bos = new SublineBO();
		bop = new PointBO();
		botp = new TempPointBO();
		bor = new RegionBO();
		bou = new UserInfoBO();
		bot = new TerminalBO();
		// zsh
		poExBo = new PointExtraBO();
		opBo = new PatrolOpBO();
		// alien
		boTo = new TaskOperationBO();
		boAi = new AlertInfoBO();
		boAl = new AlertreceiverListBO();

		ugBo = new UsergroupBO();

		boexport = new ExportBO();

	}

	/**
	 * 添加用户组信息
	 * 
	 * @param data
	 *            Usergroup
	 * @throws Exception
	 */
	public void addUsergroup(Usergroup data) throws Exception {
		ugBo.addUsergroup(data);
	}

	/**
	 * 添加用户组用户
	 * 
	 * @param data
	 *            UsergroupUserList
	 * @throws Exception
	 */
	public void addUgUList(UsergroupUserList data) throws Exception {
		ugBo.addUgUList(data);
	}

	public void clearUgUList(String userid) {
		ugBo.clearUgUList(userid);
	}

	/**
	 * 添加用户组权限
	 * 
	 * @param data
	 *            UsergroupModuleList
	 * @throws Exception
	 */
	public void addUgMList(UsergroupModuleList data) throws Exception {
		ugBo.addUgMList(data);
	}

	public void saveUserGroupModule(String menuString, String id)
	throws Exception {
		ugBo.saveUserGroupModule(menuString, id);
	}

	public void saveUserGroupUser(String userString, String id)
	throws Exception {
		ugBo.saveUserGroupUser(userString, id);
	}

	/**
	 * 载入用户组
	 * 
	 * @param id
	 * @return UserGroup对象
	 * @throws Exception
	 */
	public Usergroup loadUsergroup(String id) throws Exception {
		return ugBo.loadUsergroup(id);
	}

	/**
	 * 删除用户组
	 * 
	 * @param data
	 * @throws Exception
	 */
	public void removeUsergroup(Usergroup data) throws Exception {
		ugBo.removeUsergroup(data);
	}

	/**
	 * 更新用户组
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public Usergroup updateUsergroup(Usergroup data) throws Exception {
		return ugBo.updateUsergroup(data);
	}

	/**
	 * 取得当前登录用户所在区域下所有用户
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Vector getRegionAndUsers(HttpServletRequest request)
	throws Exception {
		return ugBo.getRegionAndUsers(request);
	}
	
	/**
	 * 获取菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getMouduleAndMenu(String type, String id) throws Exception {
		String relative = getRelativeModules(id);
		return ugBo.getMenuVct(type, relative);
	}

	/**
	 * 获得指定usergroupid的用户组的用户ID
	 * 
	 * @param usergroupId
	 * @return
	 * @throws Exception
	 */
	public String getRelativeUsers(String usergroupId) throws Exception {
		return ugBo.getRelativeUsers(usergroupId);
	}

	/**
	 * 获得指定用户组的模块ID
	 * 
	 * @param usergroupId
	 * @return
	 * @throws Exception
	 */
	public String getRelativeModules(String usergroupId) throws Exception {
		return ugBo.getRelativeModules(usergroupId);
	}

	/**
	 * 按用户删除用户用户组对应信息表
	 * 
	 * @param ugid
	 * @param userinfo
	 * @return
	 */
	public boolean deleteUserRelative(String ugid, UserInfo userinfo) {
		return ugBo.delUserRelative(ugid, userinfo);
	}

	/**
	 * 删除指定用户用户组对应表
	 * 
	 * @param ugId
	 * @throws Exception
	 */
	public void deleteUgRelative(String ugId) throws Exception {
		ugBo.deleteUgRelative(ugId);
	}

	/* 巡检操作码 */
	public int checkPatrolOpPk(String id) throws Exception {
		return opBo.checkPk(id);
	}

	public void addPatrolOp(PatrolOp data) throws Exception {
		opBo.addPatrolOp(data);
	}

	public PatrolOp loadPatrolOp(String id) throws Exception {
		return opBo.loadPatrolOp(id);
	}

	public PatrolOp updatePatrolOp(PatrolOp patrolOp) throws Exception {
		return opBo.updatePatrolOp(patrolOp);
	}

	public void removePatrolOp(PatrolOp patrolOp) throws Exception {
		opBo.removePatrolOp(patrolOp);
	}

	/* 点添加时 额外 操作 */
	public int checkPointOrder(String sublineid, String pointOrder)
	throws Exception {
		return poExBo.checkPointOrder(sublineid, pointOrder);
	}

	/* 点添加时 额外 操作 */
	public int updateSublineDym(String sublineid, String opFlag)
	throws Exception {
		return poExBo.updateSublineDym(sublineid, opFlag);
	}

	public TempPoint getTP(String id) throws Exception {
		return botp.getTP(id);
	}

	public TempPoint loadTP(String id) throws Exception {
		return botp.loadTP(id);
	}

	public void deleteTempPoint(TempPoint tPoint) throws Exception {
		botp.deleteTempPoint(tPoint);
	}

	// 县级单位
	public boolean judgeCountyExist(CountyInfo data) throws Exception {
		return bic.judgeCountyExist(data);
	}

	public void createCounty(CountyInfo data) throws Exception {
		bic.addCounty(data);
	}

	public CountyInfo loadCounty(String id) throws Exception {
		return bic.loadCounty(id);
	}

	public CountyInfo updateCounty(CountyInfo countyInfo) throws Exception {
		return bic.updateCounty(countyInfo);
	}

	public void removeCounty(CountyInfo countyInfo) throws Exception {
		bic.removeCounty(countyInfo);
	}

	/**
	 * 临时点存为巡检点之后，将临时点置位
	 * 
	 * @param id
	 *            String
	 * @throws Exception
	 */
	public void setTempEdit(String id) throws Exception {
		botp.setTempEdit(id);
	}

	/* 部门 */
	public void createDeaprt(Depart data) throws Exception {
		bod.addDepart(data);
	}

	public Depart loadDepart(String id) throws Exception {
		return bod.loadDepart(id);
	}

	public Depart updateDepart(Depart depart) throws Exception {
		return bod.updateDepart(depart);
	}

	public void removeDepart(Depart depart) throws Exception {
		bod.removeDepart(depart);
	}

	/* 代维单位 */
	public void createContractor(Contractor data) throws Exception {
		boc.addContractor(data);
	}

	public void removeContractor(Contractor data) throws Exception {
		boc.removeContractor(data);
	}

	public void removeSubContractor(String conid) throws Exception {
		boc.removeSubContractor(conid);
	}

	public Contractor loadContractor(String id) throws Exception {
		return boc.loadContractor(id);
	}

	public Contractor updateContractor(Contractor contractor) throws Exception {
		return boc.updateContractor(contractor);
	}

	/* 巡检小组 */
	public void createPatrolMan(PatrolMan data) throws Exception {
		bom.addPatrolMan(data);
	}

	public void removePatrolMan(PatrolMan data) throws Exception {
		bom.removePatrolMan(data);
	}

	public PatrolMan loadPatrolMan(String id) throws Exception {
		return bom.loadPatrolMan(id);
	}

	public PatrolMan updatePatrolMan(PatrolMan patrolMan) throws Exception {
		return bom.updatePatrolMan(patrolMan);
	}

	/* 巡检员 */
	public void addPatrolManSon(PatrolManSon data) throws Exception {
		boms.addPatrolManSon(data);
	}

	/**
	 * 功能:按人管理巡检人员中,添加巡检人员信息 参数:patrolManson PatrolManSon:巡检人员对象 返回:添加成功返回 true
	 * 否则返回 false
	 * 
	 */
	public boolean addPartrolManSonByNoGroup(PatrolManSon pSon) {
		return boms.addPartrolManSonByNoGroup(pSon);
	}

	public boolean removePatrolManSon(PatrolManSon data) throws Exception {
		return boms.removePatrolManSon(data);
	}

	public PatrolManSon loadPatrolManSon(String id) throws Exception {
		return boms.loadPatrolManSon(id);
	}

	public PatrolManSon updatePatrolMan(PatrolManSon data) throws Exception {
		return boms.updatePatrolManSon(data);

	}

	/**
	 * 功能:按人管理巡检人员中,更新巡检人员信息 参数:patrolManson PatrolManSon:巡检人员对象 返回:添加成功返回 true
	 * 否则返回 false
	 * 
	 */
	public boolean updatePatrolManByNoGroup(PatrolManSon pSon) {
		return boms.updatePatrolManByNoGroup(pSon);
	}

	/**
	 * 功能:按人管理巡检人员中,删除巡检人员信息 参数:patrolManson PatrolManSon:巡检人员对象 返回:删除成功返回 true
	 * 否则返回 false
	 * 
	 */
	public boolean removePatrolManSonByNoGroup(PatrolManSon pSon) {
		return boms.removePatrolManSonByNoGroup(pSon);
	}

	/* 线路 */
	public void createLine(Line data) throws Exception {
		bol.addLine(data);
	}

	//
	public boolean findByLineName(String linename, String type, String region) {
		return bol.validateLineName(linename, type, region);
	}

	public void removeLine(Line data) throws Exception {
		bol.removeLine(data);
	}

	public Line loadLine(String id) throws Exception {
		return bol.loadLine(id);
	}

	public Line updateLine(Line line) throws Exception {
		return bol.updateLine(line);
	}

	// 作废
	public boolean valiLineCanDele(String lineid) {
		return bol.equals(lineid);
	}

	// ///
	/**
	 * 验证line是否可以删除
	 * 
	 * @param id
	 *            String
	 * @return boolean 可以删除 true
	 */
	public boolean valiLineCanDel(String id) {
		return bos.findByLineid(id);
	}

	/* 巡检段 */
	public void createSubline(Subline data) throws Exception {
		bos.addSubline(data);
	}

	public boolean findBySubLineName(String sublinename, String type) {
		return bos.validateSubLineName(sublinename, type);
	}

	public void ExportSubline(List list, HttpServletResponse response)
	throws Exception {
		bos.ExportSubline(list, response);
	}

	public void addSublineCableList(SublineCableList data) throws Exception {
		bos.addSublineCableList(data);
	}

	public void removeSubline(Subline data) throws Exception {
		bos.removeSubline(data);
	}

	public void deleteBySublineID(String sublineid) throws Exception {
		bos.deleteBySublineID(sublineid);
	}

	public String[] getRelatedList(String sublineid) throws Exception {
		return bos.getRelatedList(sublineid);
	}

	public String[][] getCableList(String sublineid) throws Exception {
		return bos.getCableList(sublineid);
	}

	public Subline loadSubline(String id) throws Exception {
		return bos.loadSubline(id);
	}

	public Subline updateSubline(Subline subline) throws Exception {
		return bos.updateSubline(subline);
	}

	public boolean valiSubLineCanDele(String subLineid) {
		return bos.valiSubLineCanDele(subLineid);
	}

	/* 巡检点 */
	public void createPoint(Point data) throws Exception {
		bop.addPoint(data);
	}

	public void removePoint(Point data) throws Exception {
		bop.removePoint(data);
	}

	public Point loadPoint(String id) throws Exception {
		return bop.loadPoint(id);
	}

	public Point updatePoint(Point point) throws Exception {
		return bop.updatePoint(point);
	}

	/* 区域 */
	public void createRegion(Region data) throws Exception {
		bor.addRegion(data);
	}

	public void removeRegion(Region data) throws Exception {
		bor.removeRegion(data);
	}

	public void removeSubRegion(String regionid) throws Exception {
		bor.removeSubRegion(regionid);
	}

	public Region loadRegion(String id) throws Exception {
		return bor.loadRegion(id);
	}

	public Region updateRegion(Region region) throws Exception {
		return bor.updateRegion(region);
	}

	/* 用户 */
	public void createUserInfo(UserInfo data) throws Exception {
		bou.addUserInfo(data);
	}

	public void removeUserInfo(UserInfo data) throws Exception {
		bou.removeUserInfo(data);
	}

	/**
	 * 通过id加载用户信息
	 * 
	 * @param id
	 *            String
	 * @return UserInfo
	 * @throws Exception
	 */
	public UserInfo loadUserInfo(String id) throws Exception {
		return bou.loadUserInfo(id);
	}

	public UserInfo updateUserInfo(UserInfo userInfo) throws Exception {
		return bou.updateUserInfo(userInfo);
	}

	/* 手持设备 */
	public void createTerminal(Terminal data) throws Exception {
		bot.addTerminal(data);
	}

	public void removeTerminal(Terminal data) throws Exception {
		bot.removeTerminal(data);
	}

	public Terminal loadTerminal(String id) throws Exception {
		return bot.loadTerminal(id);
	}

	public Terminal updateTerminal(Terminal terminal) throws Exception {
		return bot.updateTerminal(terminal);
	}

	public int isNumberOccupied(Terminal terminal) throws Exception {
		return bot.isNumberOccupied(terminal);
	}

	public int isIdOccupied(Terminal terminal) throws Exception {
		return bot.isIdOccupied(terminal);
	}

	public int isIdOccupied4Edit(Terminal terminal) throws Exception {
		return bot.isIdOccupied4Edit(terminal);
	}

	public int isNumberOccupied4Edit(Terminal terminal, String oldSimnumber)
	throws Exception {
		return bot.isNumberOccupied4Edit(terminal, oldSimnumber);
	}

	public int isPatrolmanOccupied(Terminal terminal) throws Exception {
		return bot.isPatrolmanOccupied(terminal);
	}

	public int isPatrolmanOccupied4Edit(Terminal terminal, String oldOwnerid)
	throws Exception {
		return bot.isPatrolmanOccupied4Edit(terminal, oldOwnerid);
	}

	public String[][] getOldValues(Terminal terminal) throws Exception {
		return bot.getOldValues(terminal);
	}

	// 查找手持设备以及通讯录
	public List getPhoneBook(UserInfo userinfo) {
		return bot.getPhoneBook(userinfo);
	}

	public List getSimNumber(UserInfo userinfo) {
		return bot.getSimNumber(userinfo);
	}

	public boolean addUpPhoneBook(TerminalBean bean, String[] setnumber,
			String[] name, String[] phone, UserInfo userinfo) {
		return bot.addUpPhoneBook(bean, setnumber, name, phone, userinfo);
	}

	/**
	 * 任务操作码
	 * 
	 * @param data
	 *            TaskOperation
	 * @throws Exception
	 * @return TaskOperation
	 */
	public TaskOperation createTaskOperation(TaskOperation data)
	throws Exception {
		return boTo.addTaskOperation(data);
	}

	public void removeTaskOperation(TaskOperation data) throws Exception {
		boTo.removeTaskOperation(data);
	}

	public TaskOperation loadTaskOperation(String ID) throws Exception {
		return boTo.loadTaskOperation(ID);
	}

	public TaskOperation updateTaskOperation(TaskOperation data)
	throws Exception {
		return boTo.updateTaskOperation(data);
	}

	/**
	 * 警报信息查询
	 * 
	 * @param Id
	 *            String
	 * @return AlertInfo
	 * @throws Exception
	 */
	public AlertInfo loadAlertInfo(String Id) throws Exception {
		// logger.info("-----------------id:"+Id.toString());
		return boAi.loadAlertInfo(Id);
	}

	/**
	 * 报警代码信息
	 * 
	 * @param data
	 *            EquipInfo
	 * @return EquipInfo
	 * @throws Exception
	 */
	public void createAlertreceiverList(AlertreceiverList data)
	throws Exception {
		boAl.addAlertreceiverList(data);
	}

	public void removeAlertreceiverList(AlertreceiverList data)
	throws Exception {
		boAl.removeAlertreceiverList(data);
	}

	public AlertreceiverList loadAlertreceiverList(String id) throws Exception {
		return boAl.loadAlertreceiverList(id);
	}

	public AlertreceiverList updateAlertreceiverList(AlertreceiverList data)
	throws Exception {
		return boAl.updateAlertreceiverList(data);
	}

	/**
	 * 初始化设备
	 * 
	 * @param vct
	 *            Vector
	 * @param serverid
	 *            String
	 * @throws Exception
	 */
	public void initTerminal(List list, String serverid) throws Exception {
		bot.initTerminal(list, serverid);
	}

	/**
	 * getUserList
	 * 
	 * @param string
	 *            String
	 * @return List
	 */
	public List getUserList(UserInfo userInfo) {
		return bou.loadDeptUser(userInfo);
	}

	/**
	 * getAllUserList
	 * 
	 * @param string
	 *            String
	 * @return List
	 */
	public List getAllUserList(UserInfo userInfo) {
		return bou.loadAllUser(userInfo);
	}

	/**
	 * updatPsw
	 * 
	 * @param userid
	 *            String
	 * @param p1
	 *            String
	 * @return boolean
	 */
	public boolean updatePsw(UserInfo userinfo) {
		return bou.updatePsw(userinfo);
	}

	public boolean setuptime(UserInfoBean bean) {
		return bou.setuptime(bean);
	}

	/**
	 * loadLine
	 * 
	 * @param regionid
	 *            String
	 * @return List
	 */
	public List loadLineInfo(String regionid) {
		return bol.getLineInfo(regionid);
	}

	public List loadDept(String regionid) {
		return bod.getDept(regionid);
	}

	public void exportRegionResult(List list, HttpServletResponse response)
	throws Exception {
		boexport.exportRegionResult(list, response);
	}

	public void exportDepartResult(List list, HttpServletResponse response)
	throws Exception {
		boexport.exportDepartResult(list, response);
	}

	// 导出路由信息报表
	public void exportRouteInfoResult(List list, HttpServletResponse response)
	throws Exception {
		boexport.exportRouteInfoResult(list, response);
	}

	public void exportPatrolSonResult(List list, HttpServletResponse response)
	throws Exception {
		boexport.exportPatrolSonResult(list, response);
	}

	public void exportTerminalResult(List list, HttpServletResponse response)
	throws Exception {
		boexport.exportTerminalResult(list, response);
	}

	public void exportLineResult(List list, HttpServletResponse response)
	throws Exception {
		boexport.exportLineResult(list, response);
	}

	public void exportContractorResult(List list, HttpServletResponse response)
	throws Exception {
		boexport.exportContractorResult(list, response);
	}

	public void exportPointResult(List list, HttpServletResponse response)
	throws Exception {
		boexport.exportPointResult(list, response);
	}

	public void exportUserinfoResult(List list, HttpServletResponse response)
	throws Exception {
		boexport.exportUserinfoResult(list, response);
	}

	public void exportUsergroupResult(List list, HttpServletResponse response)
	throws Exception {
		boexport.exportUsergroupResult(list, response);
	}

	public void exportAlertreceiverListResult(List list,
			HttpServletResponse response) throws Exception {
		boexport.exportAlertreceiverListResult(list, response);
	}

	public void exportTaskOperationResult(List list,
			HttpServletResponse response) throws Exception {
		boexport.exportTaskOperationResult(list, response);
	}

	public void exportPatrolOpResult(List list, HttpServletResponse response)
	throws Exception {
		boexport.exportPatrolOpResult(list, response);
	}

	public void exportSysLog(List list, HttpServletResponse response)
	throws Exception {
		boexport.exportSysLog(list, response);
	}

	public void exportSmsReceiveLog(List list, HttpServletResponse response)
	throws Exception {
		boexport.exportSmsReceiveLog(list, response);
	}

	public void exportSmsSendLog(List list, HttpServletResponse response)
	throws Exception {
		boexport.exportSmsSendLog(list, response);
	}

	public void exportCableSegment(List list, HttpServletResponse response)
	throws Exception {
		boexport.exportCableSegment(list, response);
	}

	public void exportPipeSegment(List list, HttpServletResponse response)
	throws Exception {
		boexport.exportPipeSegment(list, response);
	}

	public void setingPassword(UserInfo data) throws Exception {
		bou.setingPassword(data);
	}

	public void exportPatrolMan(List list, HttpServletResponse response)
	throws Exception {
		boexport.exportPatrolMan(list, response);
	}

	public List getNewTerminal(UserInfo userinfo) {
		// TODO Auto-generated method stub
		return bot.getNewTerminal(userinfo);
	}

	public List getLowVoltage(UserInfo userinfo) {
		// TODO Auto-generated method stub
		return bot.getLowVoltage(userinfo);

	}

	public void exportPatrolPointInfo(String sql, HttpServletResponse response)
	throws Exception {
		boexport.exportPatrolPointInfoResult(sql, response);
	}

	public String queryCableList(String queryValue, String patrolId,
			UserInfo userInfo) {
		// TODO Auto-generated method stub
		StringBuffer buf = new StringBuffer();
		List list = bos.queryCableList(queryValue, patrolId, userInfo);
		DynaBean bean;
		for (int i = 0; list != null && i < list.size(); i++) {
			bean = (DynaBean) list.get(i);
			buf.append("<option value=\"");
			buf.append(bean.get("kid"));
			buf.append("\">");
			buf.append(bean.get("segmentname"));
			buf.append("</option>");
		}
		return buf.toString();
	}

	/**
	 * 查询巡检组
	 * @return
	 */
	public List getPatrolGroup(String contractorID){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select  p.patrolid, p.patrolname from patrolmaninfo p ");
		sb.append(" where p.parentid='"+contractorID+"' and p.state is null ");
		try{
			QueryUtil util = new QueryUtil();
			return util.queryBeans(sb.toString());
		}catch(Exception e){
			
		}
		return null;
	}

}
