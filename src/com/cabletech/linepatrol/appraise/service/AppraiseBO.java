package com.cabletech.linepatrol.appraise.service;

import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.appraise.beans.AppraiseResultBean;
import com.cabletech.linepatrol.appraise.dao.AppraiseConfirmResultDao;
import com.cabletech.linepatrol.appraise.dao.AppraiseResultDao;
import com.cabletech.linepatrol.appraise.dao.AppraiseTableDao;
import com.cabletech.linepatrol.appraise.module.AppraiseConfirmResult;
import com.cabletech.linepatrol.appraise.module.AppraiseResult;
import com.cabletech.linepatrol.appraise.module.AppraiseRuleResult;
import com.cabletech.linepatrol.appraise.module.AppraiseTable;
import com.cabletech.linepatrol.appraise.template.AppraiseResultTemplate;
import com.cabletech.linepatrol.appraise.template.AppraiseTemplate;
import com.cabletech.linepatrol.appraise.util.AppraiseUtil;

public class AppraiseBO extends EntityManager<AppraiseResult, String> {
	@Resource(name="appraiseResultDao")
	AppraiseResultDao appraiseResultDao;
	@Resource(name="appraiseConfirmResultDao")
	AppraiseConfirmResultDao appraiseConfirmResultDao;
	@Autowired
	private AppraiseTableDao appraiseTableDao;
	@Override
	protected HibernateDao<AppraiseResult, String> getEntityDao() {
		return appraiseResultDao;
	}
	/**
	 * ���濼�˽��
	 * @param appraiseResult
	 */
	@Transactional
	public void saveAppraiseMonth(AppraiseResult appraiseResult) {
		if(StringUtils.isBlank(appraiseResult.getConfirmResult())){
			appraiseResult.setConfirmResult(AppraiseConstant.STATE_WAIT_SEND);
		}
		appraiseResultDao.saveAppraiseResult(appraiseResult);
		
	}
	/**
	 * ��ÿ���ҳ���вο�ָ������Ҫ����Ϣ
	 * @param contractorId
	 * @param theStartDate
	 * @return
	 */
	public List queryForAppraiseMonth(String contractorId,String theStartDate){
		return null;
	}
	/**
	 * ��ÿ���ҳ���вο�ָ��ĺϼ���Ϣ
	 * @param contractorId
	 * @param theStartDate
	 * @return
	 */
	public List queryForAppraiseMonthAll(String contractorId,String theStartDate){
		return null;
	}
	/**
	 * ͨ��bean�����ÿ��˽����list
	 * @param appraiseResultBean
	 * @return
	 */
	@Transactional
	public List<AppraiseResult> getAppraiseResultByBean(AppraiseResultBean appraiseResultBean){
		return appraiseResultDao.getAppraiseResultByBean(appraiseResultBean);
	}
	/**
	 * ��ѯͳ�ƿ��˱�
	 * @param appraiseResultBean
	 * @param userInfo
	 * @return
	 */
	@Transactional
	public List queryStat(AppraiseResultBean appraiseResultBean,UserInfo userInfo) {
		return null;
	}
	
	/**
	 * ��������ѯ���ڷ�Χ�ڿ��˽��list
	 * @param appraiseResultBean
	 * @return
	 */
	@Transactional
	public List<AppraiseResult> getAppraiseResultInDate(AppraiseResultBean appraiseResultBean){
		return appraiseResultDao.getAppraiseResultInDate(appraiseResultBean);
	}
	
	/**
	 * ���¿��˽��״̬
	 * @param appraiseResultBean
	 */
	@Transactional
	public void updateResultStateAndMark(AppraiseResultBean appraiseResultBean) {
		appraiseResultDao.updateResultStateAndMark(appraiseResultBean);
	}
	/**
	 * ���˽��ȷ��
	 * @param appraiseResultBean
	 */
	@Transactional
	public void verifyAppraiseResult(AppraiseResultBean appraiseResultBean,UserInfo userInfo) {
		// TODO Auto-generated method stub
		appraiseResultDao.updateResultStateAndMark(appraiseResultBean);
		AppraiseConfirmResult appraiseConfirmResult=getConfirmResultByResultBean(appraiseResultBean, userInfo);
		appraiseConfirmResultDao.save(appraiseConfirmResult);
	}
	/**
	 * ͨ��resultBean�����ʼ��appraiseConfirmResult����
	 * @param appraiseResultBean
	 * @param userInfo
	 * @return
	 */
	private AppraiseConfirmResult getConfirmResultByResultBean(AppraiseResultBean appraiseResultBean, UserInfo userInfo) {
		AppraiseConfirmResult appraiseConfirmResult=new AppraiseConfirmResult();
		appraiseConfirmResult.setContractorId(appraiseResultBean.getContractorId());
		appraiseConfirmResult.setResult(appraiseResultBean.getMark());
		appraiseConfirmResult.setResultId(appraiseResultBean.getId());
		appraiseConfirmResult.setConfirmDate(new Date());
		appraiseConfirmResult.setConfirmer(userInfo.getUserName());
		appraiseConfirmResult.setConfirmResult(appraiseResultBean.getConfirmResult());
		return appraiseConfirmResult;
	}
	/**
	 * ������д�άȷ�������һ����¼��map  keyΪ���˽�����id��valueΪ����ȷ�϶���
	 * @param appraiseResultBean
	 * @return
	 */
	@Transactional
	public Map<String,Object> getAllLastConfirmMapByResultBean(){
		Map<String,Object> appraiseConfirmResultMap=new HashMap<String, Object>();
		List<AppraiseConfirmResult> appraiseConfirmResults=appraiseConfirmResultDao.getAllLastConfirm();
		for(AppraiseConfirmResult appraiseConfirmResult:appraiseConfirmResults){
			appraiseConfirmResultMap.put(appraiseConfirmResult.getResultId(), appraiseConfirmResult);
		}
		return appraiseConfirmResultMap;
	}
	/**
	 * ���һ����¿�������
	 * @param appraiseResultBean
	 * @param type
	 * @return
	 */
	@Transactional
	public List<Map> getApprasieResultYear(AppraiseResultBean appraiseResultBean,String type){
		return appraiseResultDao.queryApprasieResultInYear(appraiseResultBean,type);
	}
	/**
	 * ���list�е��ܷ�
	 * @param appraiseResults
	 * @return
	 */
	@Transactional
	public Float getTotleResult(List<Map> appraiseResults){
		Float totle=0.0f;
		for(Map map :appraiseResults){
			totle+=Float.valueOf(map.get("result").toString());
		}
		return totle;
	}
	/**
	 * �������˽��
	 * @param id
	 * @param response
	 */
	@Transactional
	public void exprotTable(String id, HttpServletResponse response) {
		AppraiseResult appraiseResult=appraiseResultDao.get(id);
		String tableId=appraiseResult.getTableId();
		AppraiseTable table = appraiseTableDao.getTableById(tableId);
		try {
			AppraiseUtil.InitResponse(response, table.getTableName() + ".xls",AppraiseConstant.CONTENT_TYPE);
			OutputStream out = response.getOutputStream();
			Properties config = getReportConfig();
			String urlPath = getUrlPath(config, "report.appraiseTableMonth");
			AppraiseResultTemplate template = new AppraiseResultTemplate(urlPath);
			ExcelStyle excelStyle = new ExcelStyle(urlPath);
			template.doExportAppriaseTable(table, excelStyle,appraiseResult);
			template.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * ͨ��resultId��ÿ���ȷ���б�
	 * @param resultId
	 * @return
	 */
	@Transactional
	public List<AppraiseConfirmResult> queryConfirmResultByResultId(String resultId) {
		List<AppraiseConfirmResult> appraiseConfirmResults=appraiseConfirmResultDao.getConfirmResultByResultId(resultId);
		return appraiseConfirmResults;
	}
}
