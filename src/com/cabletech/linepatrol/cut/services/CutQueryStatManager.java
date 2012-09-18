package com.cabletech.linepatrol.cut.services;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.dao.ContractorDAOImpl;
import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.config.ConfigPathUtil;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.dao.EvaluateDao;
import com.cabletech.linepatrol.commons.dao.UserInfoDAOImpl;
import com.cabletech.linepatrol.commons.module.Evaluate;
import com.cabletech.linepatrol.cut.dao.CutAcceptanceDao;
import com.cabletech.linepatrol.cut.dao.CutDao;
import com.cabletech.linepatrol.cut.dao.CutFeedbackDao;
import com.cabletech.linepatrol.cut.dao.CutHopRelDao;
import com.cabletech.linepatrol.cut.dao.CutQueryStatDao;
import com.cabletech.linepatrol.cut.module.Cut;
import com.cabletech.linepatrol.cut.module.CutAcceptance;
import com.cabletech.linepatrol.cut.module.CutConstant;
import com.cabletech.linepatrol.cut.module.CutFeedback;
import com.cabletech.linepatrol.cut.module.QueryCondition;
import com.cabletech.linepatrol.cut.templates.CutTemplates;


@Service
@Transactional
public class CutQueryStatManager extends EntityManager<Cut,String> {
	private static String CONTENT_TYPE = "application/vnd.ms-excel";
	
	@Resource(name = "userInfoDao")
	private UserInfoDAOImpl userInfoDao;

	@Resource(name="cutQueryStatDao")
	private CutQueryStatDao cutQueryStatDao;
	
	@Override
	protected HibernateDao<Cut, String> getEntityDao() {
		return cutQueryStatDao;
	}
	
	@Resource(name="cutDao")
	private CutDao cutDao;
	
	@Resource(name = "cutFeedbackDao")
	private CutFeedbackDao cutFeedbackDao;

	@Resource(name = "cutAcceptanceDao")
	private CutAcceptanceDao cutAcceptanceDao;
	
	@Resource(name = "cutHopRelDao")
	private CutHopRelDao cutHopRelDao;
	
	@Resource(name = "evaluateDao")
	private EvaluateDao evaluateDao;
	
	/**
	 * 根据前台条件查询割接申请信息
	 * @param queryCondition
	 * @return
	 */
	public List queryCutInfo(QueryCondition queryCondition, UserInfo userInfo) throws ServiceException {
		return cutQueryStatDao.queryByCondition(queryCondition, userInfo);
	}
	
	/**
	 * 获得所有的代维单位人员
	 * @return
	 */
	public List getAllContractor(){
		return cutDao.getAllContractor();
	}
	
	/**
	 * 由部门ID获得代维单位
	 * @param deptId：部门ID
	 * @return
	 */
	public BasicDynaBean getContractorById(String deptId){
		List list = cutDao.getContractorById(deptId);
		BasicDynaBean bean = (BasicDynaBean)list.get(0);
		return bean;
	}
	
	/**
	 * 统计前加载数据
	 * @param queryCondition：查询条件
	 * @return
	 */
	public List cutStatForm(QueryCondition queryCondition){
		return cutQueryStatDao.cutStatForm(queryCondition);
	}
	
	/**
	 * 导出查询列表信息
	 * @param list
	 * @param response
	 */
	public void exportCutList(List list, HttpServletResponse response){
		try{
			initResponse(response, "割接申请列表.xls");
			OutputStream out = response.getOutputStream();
			Properties config = getReportConfig();
			String urlPath = getUrlPath(config, "report.cutQueryList");
			CutTemplates template = new CutTemplates(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.doExportCutQuery(list, excelstyle);
			template.write(out);
		}catch(Exception e){
			logger.error("导出出现异常:"+e.getMessage());
			e.getStackTrace();
		}
	}
	
	/**
	 * 查询统计列表信息
	 * @param list
	 * @param response
	 */
	public void doExportCutStat(List list, HttpServletResponse response){
		try{
			initResponse(response, "割接统计列表.xls");
			OutputStream out = response.getOutputStream();
			Properties config = getReportConfig();
			String urlPath = getUrlPath(config, "report.cutStatList");
			CutTemplates template = new CutTemplates(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.doExportCutStat(list, excelstyle);
			template.write(out);
		}catch(Exception e){
			e.getStackTrace();
		}
	}
	
	@Transactional(readOnly=true)
	private void initResponse(HttpServletResponse response, String fileName)
	throws UnsupportedEncodingException {
		response.reset();
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(fileName.getBytes(), "iso-8859-1"));
	}
	
	
	/**
	 * 查看割接信息
	 * @param cutId
	 * @return
	 */
	public Map viewQueryCut(String cutId){
		Cut cut = cutDao.get(cutId);
		cutDao.initObject(cut);
		String cancelUserName = userInfoDao.getUserName(cut.getCancelUserId());
		cut.setCancelUserName(cancelUserName);
		CutFeedback cutFeedback = cutFeedbackDao.findByUnique("cutId", cutId);
		CutAcceptance cutAcceptance = cutAcceptanceDao.findByUnique("cutId", cutId);
		String sublineIds = cutHopRelDao.getSublineIds(cutId);
		Evaluate evaluate = evaluateDao.getEvaluate(cutId,CutConstant.LP_EVALUATE_CUT);
		List subline = cutHopRelDao.getRepeaterSection(sublineIds);
		String condition = "and approve.object_type='LP_CUT' and approve.object_id='" + cutId + "'";
		List approve_info_list = cutDao.queryApproveList(condition);
		String contractorId = userInfoDao.getDeptIdByUserId(cut.getProposer());
		Map map = new HashMap();
		map.put("cut", cut);
		map.put("cutFeedback", cutFeedback);
		map.put("cutAcceptance", cutAcceptance);
		map.put("sublineIds", sublineIds);
		map.put("evaluate", evaluate);
		map.put("subline", subline);
		map.put("approve_info_list", approve_info_list);
		map.put("contractorId", contractorId);
		return map;
	}
	
	/**
	 * 由快捷菜单查询割接信息
	 * @param state
	 * @param regionId
	 * @return
	 */
	public List queryCutInfoByMenu(String state, String regionId){
		return cutQueryStatDao.queryCutInfoByMenu(state, regionId);
	}
	
	
	/**
	 * 通过PDA查询待审割接申请
	 * @param userInfo
	 * @return
	 */
	public List<Map> queryCutApproveFromPDA(UserInfo userInfo){
		return cutQueryStatDao.queryCutApproveFromPDA(userInfo);
	}
	
	
	/**
	 * 通过PDA查询待反馈申请
	 * @param userInfo
	 * @return
	 */
	public List<Map> queryFeedbackFromPDA(UserInfo userInfo){
		return cutQueryStatDao.queryFeedbackFromPDA(userInfo);
	}
	
	/**
	 * 通过PDA查询待审验收
	 * @param userInfo
	 * @return
	 */
	public List<Map> queryAccecptanceFromPDA(UserInfo userInfo) {
		return cutQueryStatDao.queryAccecptanceFromPDA(userInfo);
	}
	
	
	public List<Map> queryCutNumFromPDA(UserInfo userInfo){
		List<Map> num=new ArrayList<Map>();
		ContractorDAOImpl contractorDAOImpl=new ContractorDAOImpl();
		
		List<Map<String,Object>> cutNums=cutQueryStatDao.queryCutNumFromPDA(userInfo);
		List<Contractor> contractors=contractorDAOImpl.getAllContractor(userInfo);
		Map<String,Integer> sumNum=new HashMap<String, Integer>();
		sumNum.put("currentCutNum", 0);
		sumNum.put("waitCutNum", 0);
		sumNum.put("monthCutApproveNum", 0);
		sumNum.put("finishCutNUm", 0);
		for(Contractor contractor:contractors){
			Map<String,Object> cut=new HashMap<String, Object>();
			setCutNum(cutNums, contractor, cut, num,sumNum);
			
		}
		num.add(sumNum);
		return num;
	}
	
	private void setCutNum(List<Map<String,Object>> cutNums, Contractor contractor, Map<String, Object> cut,List<Map> num,Map<String,Integer> sumNum) {
		int currentCutNum = 0;
		int waitCutNum = 0;
		int monthCutApproveNum = 0;
		int finishCutNUm=0;
		for (Map cutNum : cutNums) {
			if (contractor.getContractorName().equals(cutNum.get("contractorName"))) {
				currentCutNum = setNumber(cutNum, "0",currentCutNum);
				waitCutNum = setNumber(cutNum, "1",waitCutNum);
				monthCutApproveNum = setNumber(cutNum, "2",monthCutApproveNum);
				finishCutNUm = setNumber(cutNum, "3",finishCutNUm);
			}
		}
		cut.put("contractorName", contractor.getContractorName());
		cut.put("0", currentCutNum);
		cut.put("1", waitCutNum);
		cut.put("2", monthCutApproveNum);
		cut.put("3", finishCutNUm);
		num.add(cut);
		sumNum.put("currentCutNum", sumNum.get("currentCutNum")+currentCutNum);
		sumNum.put("waitCutNum", sumNum.get("waitCutNum")+waitCutNum);
		sumNum.put("monthCutApproveNum", sumNum.get("monthCutApproveNum")+monthCutApproveNum);
		sumNum.put("finishCutNUm", sumNum.get("finishCutNUm")+finishCutNUm);
	}

	private int setNumber(Map cutNum, String state,int value) {
		int needApprove = value;
		if (cutNum.get("state").equals(state)) {
			needApprove = Integer.valueOf(cutNum.get("num").toString());
		}
		return needApprove;
	}
}
