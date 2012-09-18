package com.cabletech.linepatrol.trouble.services;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.cglib.beans.BeanCopier;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.dao.ContractorDAO;
import com.cabletech.linepatrol.trouble.beans.TroubleNormGuideBean;
import com.cabletech.linepatrol.trouble.beans.TroubleYearQuotaBean;
import com.cabletech.linepatrol.trouble.dao.TroubleQuotaDAO;
import com.cabletech.linepatrol.trouble.dao.TroubleQuotaMonthDAO;
import com.cabletech.linepatrol.trouble.dao.TroubleQuotaYearDAO;
import com.cabletech.linepatrol.trouble.module.TroubleGuideMonth;
import com.cabletech.linepatrol.trouble.module.TroubleGuideYear;
import com.cabletech.linepatrol.trouble.module.TroubleNormGuide;

@Service
@Transactional
public class TroubleQuotaBO extends EntityManager<TroubleNormGuide, String> {
	@Resource(name = "troubleQuotaDAO")
	private TroubleQuotaDAO dao;

	@Resource(name = "troubleQuotaMonthDAO")
	private TroubleQuotaMonthDAO monthDAO;
	@Resource(name = "contractorDao")
	private ContractorDAO contractorDao;
    //������ָ��ͳ�Ʊ���
	@Resource(name = "troubleQuotaYearDAO")
	private TroubleQuotaYearDAO yearQuotaDAO;
	
//	private String str_interdiction_time = "interdiction_time";
//	private String str_trouble_times     = "trouble_times";
//	private String str_maintenance_length = "maintenance_length";
	private String str_interdiction_time="revise_interdiction_time";
	private String str_trouble_times    ="revise_trouble_times";
	private String str_maintenance_length="revise_maintenance_length";
	
	/**
	 * �������ָ�����
	 * @param bean
	 */
	public void saveQuotaInfo(TroubleNormGuideBean bean) {
		TroubleNormGuide quota = new TroubleNormGuide();
		try {
			BeanUtil.objectCopy(bean, quota);
			quota.setGuideType(TroubleConstant.QUOTA);
			quota.setGuideName("һ�ɹ���ָ��");
			dao.save(quota);
			TroubleNormGuide quotacity = new TroubleNormGuide();
			quotacity.CopyBean2Quota(bean);
			quotacity.setGuideType(TroubleConstant.QUOTA_CITY);
			quotacity.setGuideName("����������ָ��");
			dao.save(quotacity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �޸Ĺ���ָ��
	 * @param bean
	 */
	public void updateQuotaInfo(TroubleNormGuideBean bean) {
		try {
			String id = bean.getId();
			String idcity = bean.getIdCity();
			TroubleNormGuide quota = dao.get(id);
			BeanUtil.objectCopy(bean, quota);
			quota.setGuideType(TroubleConstant.QUOTA);
			quota.setGuideName("һ�ɹ���ָ��");
			dao.save(quota);
			TroubleNormGuide quotacity = dao.get(idcity);
			quotacity.CopyBean2Quota(bean);
			quotacity.setGuideType(TroubleConstant.QUOTA_CITY);
			quotacity.setGuideName("����������ָ��");
			dao.save(quotacity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ѯ����ָ�����
	 * @return
	 */
	public List<TroubleNormGuide> getQuotas() {
		return dao.getQuotas();
	}

	/**
	 * ��ѯ��ָ��
	 * @param type
	 * @param month
	 * @return
	 */
	public List<TroubleGuideMonth> getQuotaMonth(String type, String month) {
		return monthDAO.getQuotaMonth(type, month);
	}

	/**
	 * ��ѯ����ָ�����
	 * @return
	 */
	public TroubleNormGuide getQuotaByType(String type) {
		return dao.getQuotaByType(type);
	}

	/**
	 *  ���ɹ�����ָ��
	 * @param user
	 * @param guideTypeͳ������
	 * @param month
	 */
	public void createMonthQuota(UserInfo user, String guideType, String month) {
		String regionid = user.getRegionid();
		TroubleNormGuide guide = dao.getQuotaByType(guideType);
		List<Contractor> contractors = monthDAO.getContractorByType(regionid, guideType, month);
		TroubleGuideMonth monthQuota;
		for (int i = 0; contractors != null && i < contractors.size(); i++) {
			Contractor contractor = contractors.get(i);
			String contractorId = contractor.getContractorID();
			BasicDynaBean bean = monthDAO.getTroubleNumAndTime(contractorId, guideType, month);
			Object troublenum = 0;
			Object etime = 0;
			if (bean != null) {
				troublenum = bean.get("troublenum");
				etime = bean.get("etime");
			}
			
			double cableLength = monthDAO.getCableLength(contractorId, guideType, month);//���½�ά���еĳ��ȡ�;
			//��ά��׼ȷʱ��������ķ�ʽ��
			cableLength = monthDAO.getReviseCableLength(contractorId,guideType,month);//��ȡ�ϸ��µ��������ȡ�����ϸ���Ϊ0����ȡ���ϸ��µ��Դ����ơ�
			double growcableLength = monthDAO.getCableLength4CurrentMonth(contractorId, guideType, month);//ÿ����������
			cableLength = cableLength +growcableLength;
			
			double interdictiontime = Double.valueOf(etime.toString());
			double timesDareValue = calculateQuota(cableLength,guide.getInterdictionTimesDareValue());//ǧ������ϴ�����սֵ
			double timesNormValue = calculateQuota(cableLength,guide.getInterdictionTimesNormValue());//ǧ������ϴ�����׼ֵ
			double timeNormValue = calculateQuota(cableLength,guide.getInterdictionTimeNormValue());//ǧ���������ʱ��׼ֵ
			double timeDareValue = calculateQuota(cableLength,guide.getInterdictionTimeDareValue());//ǧ���������ʱ��սֵ
			
			double rtrInTime = monthDAO.troubleRepairRate(contractorId, guideType, month);//���Ϲ������޼�ʱ��
			double feedbackInTime = monthDAO.troubleReplyRate(contractorId, guideType, month);//���Ϸ�����ʱ��
			int outStandardNumber = monthDAO.getCityAreaOnceTroubleOutStandardNumber(contractorId, guideType, month);//����������ʱ������׼ֵ������
			monthQuota = new TroubleGuideMonth();
			monthQuota.setContractorId(contractorId);
			monthQuota.setGuideType(guideType);
			monthQuota.setStatMonth(DateUtil.parseDate(month, "yyyy/MM"));
			monthQuota.setTroubleTimes(Integer.parseInt(troublenum.toString()));
			monthQuota.setInterdictionTime(interdictiontime);
			monthQuota.setMaintenanceLength(cableLength);
			monthQuota.setReviseMaintenanceLength(cableLength);
			monthQuota.setInterdictionNormTimes(timesNormValue);
			monthQuota.setInterdictionDareTimes(timesDareValue);
			monthQuota.setInterdictionNormTime(timeNormValue);
			monthQuota.setInterdictionDareTime(timeDareValue);
			monthQuota.setRtrInTime(rtrInTime);
			monthQuota.setFeedbackInTime(feedbackInTime);
			monthQuota.setReviseInterdictionTime(interdictiontime);
			monthQuota.setReviseTroubleTimes(Integer.parseInt(troublenum.toString()));
			monthQuota.setCityAreaOutStandardNumber(outStandardNumber);
			if (isCurrentMonth(month)) {
				monthQuota.setFinishState(TroubleGuideMonth.NOT_FINISH_STATE);
			} else {
				monthQuota.setFinishState(TroubleGuideMonth.FINISH_STATE);
			}
			monthDAO.save(monthQuota);
		}
	}
	
	/**
	 * ǧ����ָ�����
	 * @param cableLength ���³���
	 * @param guideValue ��׼ָ��
	 * @return
	 */
	private double calculateQuota(double cableLength, double guideValue){
		 return cableLength / 1000 * guideValue / 12;
	}
	/**
	 *  ���ɹ�����ָ��
	 * @param user
	 * @param guideTypeͳ������
	 * @param month
	 */
	public void deleteMonthQuota(UserInfo user, String guideType, String month) {
		String regionid = user.getRegionid();
		String hql = " from TroubleGuideMonth ";
		hql += " where statMonth=to_date('" + month + "','yyyy/MM') ";
		hql += " and guideType='" + guideType + "' ";
		List list = monthDAO.getHibernateTemplate().find(hql);
		monthDAO.getHibernateTemplate().deleteAll(list);
	}

	/**
	 * ��ѯ�¹���ָ��
	 * @param type ͳ������
	 * @param month �·�
	 * @param guide ָ���׼ֵ
	 * @return
	 */
	public List getTroubleMonthQuotas(String type, String month, TroubleNormGuide guide) {
		List list = monthDAO.getTroubleMonthQuotas(type, month, guide);
		List sumList = monthDAO.getTroubleMonthSumQuotas(type, month, guide);
		list.addAll(sumList);
		return list;
	}

	/**
	 * ��ѯһ��ͳ�Ƶ���ָ��
	 * @param guideType
	 * @param year
	 * @return
	 */
	public List<TroubleGuideMonth> getMonthQuotaInYear(String guideType, String year) {
		return monthDAO.getMonthQuotaInYear(guideType, year);
	}

	/**
	 * ������ͳ��
	 * @param type  ָ������
	 * @param year  ͳ�����
	 * @param lastmonth year������ͳ�Ƶ�һ���·�
	 * @return
	 */
	public Map statYearTroubleMonthQuotas(String type, String year, TroubleNormGuide guide) {
		Map map = getTimeAreaTroubleQuotaList("", type, year + "/01", year + "/12");
		return map;
	}

	/**
	 * �������û��ͳ�ƣ�ά�����³���Ϊnull������Ҫ�ο���һ���·ݵĳ���.
	 * @param monthlen 
	 * @param lastmonth ��һ���·ݹ��³���
	 * @param bean
	 */
	public void setBean(String monthlen, Object lastmonth, BasicDynaBean bean) {
		//�·ݹ��³���
		Object m = bean.get(monthlen);
		if (m == null) {
			if (lastmonth != null) {
				bean.set(monthlen, lastmonth);
			} else {
				bean.set(monthlen, BigDecimal.valueOf(0));
			}
		}
	}

	@Override
	protected HibernateDao<TroubleNormGuide, String> getEntityDao() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @param contractorId
	 * @param guideType
	 * @param beginTime
	 * @param endTime
	 * @return Map
	 * [
	 *   [resultMap=[
	 *     ��ά��˾1=[
	 *       whole_year_standard_times=ȫ��ǧ������ϴ���ָ��ģ��ֵ,
	 *       whole_year_standard_time=ȫ��ǧ�������ʱ��ָ��ģ��ֵ,
	 *       trouble_times=���ϴ���,trouble_time=����ʱ��,
	 *       city_area_out_standard_number=������������׼ʱ������,
	 *       standard_times=������ǧ������ϴ���ָ���׼ֵ
	 *       standard_time=������ǧ�������ʱ��ָ���׼ֵ
	 *       dare_times=������ǧ������ϴ���ָ����սֵ
	 *       dare_time=������ǧ�������ʱ��ָ����սֵ
	 *       maintenance_length=����ά������
	 *       monthMap=[�·�1=DynaBean(ά������1,...),�·�2=DynaBean(ά������2,...),...]
	 *     ],
	 *     ��ά��˾2=[...],...
	 *     ]
	 *   ],
	 *   [sumResultMap=[
	 *     whole_year_standard_times=ȫ��ǧ������ϴ���ָ��ģ��ֵ,
	 *     whole_year_standard_time=ȫ��ǧ�������ʱ��ָ��ģ��ֵ,
	 *     trouble_times=���ϴ���,trouble_time=����ʱ��,avg_time=����ƽ������ʱ��
	 *     city_area_out_standard_number=������������׼ʱ������,
	 *     standard_times=������ǧ������ϴ���ָ���׼ֵ
	 *     standard_time=������ǧ�������ʱ��ָ���׼ֵ
	 *     dare_times=������ǧ������ϴ���ָ����սֵ
	 *     dare_time=������ǧ�������ʱ��ָ����սֵ
	 *     maintenance_length=ά������
	 *   ]],
	 *   [monthList=[�·�1,�·�2,...,�·�n]]
	 * ]
	 */
	public Map getTimeAreaTroubleQuotaList(String contractorId, String guideType, String beginTime, String endTime) {
		DecimalFormat df = new DecimalFormat("#0.00");
		Map map = new HashMap();
		Map contractorMap = new HashMap();
		Map resultMap = new HashMap();
		Map sumResultMap = new HashMap();
		Map monthMap = new LinkedHashMap();
		Calendar calendar = Calendar.getInstance();
		Date beginDate = DateUtil.Str2UtilDate(beginTime + "/01", "yyyy/MM/dd");
		Date endDate = DateUtil.Str2UtilDate(endTime + "/01", "yyyy/MM/dd");
		calendar.setTime(beginDate);
		String dateStr;
		List monthList = new ArrayList();
		while (calendar.getTime().compareTo(endDate) <= 0) {
			dateStr = DateUtil.DateToString(calendar.getTime(), "yyyy/MM");
			calendar.add(Calendar.MONTH, 1);
			monthList.add(dateStr);
		}
		map.put("monthList", monthList);

		TroubleNormGuide guide = getQuotaByType(guideType);
		String year = beginTime.split("/")[0];
		String condition = "";
		if (contractorId != null && !"".equals(contractorId)) {
			condition += " and contractorid='" + contractorId + "' ";
		}
		condition += " order by contractorid ";
		List contractorList = contractorDao.getContractorList(condition);
		List list = monthDAO.getTimeAreaTroubleQuotaList(contractorId, guideType, beginTime, endTime);
		List latestList;

		BasicDynaBean contractorBean;
		BasicDynaBean monthQuotaBean;
		BasicDynaBean lastMonthQuotaBean = null;
		String month;
		String contractorID;
		String contractorName;
		double troubleTimes = 0;//���ϴ���
		double troubleTime = 0;//����ʱ��
		double cityAreaOutStandardNumber = 0;//���������γ�ʱ����
		double standardTimes = 0;//
		double standardTime = 0;
		double dareTimes = 0;
		double dareTime = 0;
		double wholeYearTimes = 0;
		double wholeYearTime = 0;
		double maintenanceLength = 0;
		double troubleAllTimes = 0;
		double troubleAllTime = 0;
		double avgAllTime = 0;
		double cityAreaOutStandardAllNumber = 0;
		double standardAllTimes = 0;
		double standardAllTime = 0;
		double dareAllTimes = 0;
		double dareAllTime = 0;
		double wholeYearAllTimes = 0;
		double wholeYearAllTime = 0;
		double maintenanceAllLength = 0;
		boolean isExistGenerateData = false;
		
		
		for (int i = 0; contractorList != null && i < contractorList.size(); i++) {
			contractorBean = (BasicDynaBean) contractorList.get(i);
			contractorID = (String) contractorBean.get("departid");
			contractorName = (String) contractorBean.get("departname");
			latestList = monthDAO.getTimeAreaTroubleQuotaList(contractorID, guideType, "", beginTime);
			resultMap = new HashMap();
			monthMap = new LinkedHashMap();
			troubleTimes = 0;
			troubleTime = 0;
			cityAreaOutStandardNumber = 0;
			standardTimes = 0;
			standardTime = 0;
			dareTimes = 0;
			dareTime = 0;
			wholeYearTimes = 0;
			wholeYearTime = 0;
			maintenanceLength = 0;
			isExistGenerateData = false;
			for (int j = 0; monthList != null && j < monthList.size(); j++) {
				month = (String) monthList.get(j);
				for (int k = 0; list != null && k < list.size(); k++) {
					logger.debug("���д�������ָ�����ݵĴ���");
					monthQuotaBean = (BasicDynaBean) list.get(k);
					if (monthQuotaBean != null && monthQuotaBean.get("contractorid").equals(contractorID)
							&& monthQuotaBean.get("stat_month_str").equals(month)) {
						if ((BigDecimal) monthQuotaBean.get(str_trouble_times) != null) {
							troubleTimes += ((BigDecimal) monthQuotaBean.get(str_trouble_times)).doubleValue();
						}
						if ((BigDecimal) monthQuotaBean.get(str_interdiction_time) != null) {
							troubleTime += ((BigDecimal) monthQuotaBean.get(str_interdiction_time)).doubleValue();
						}
						cityAreaOutStandardNumber += ((BigDecimal) monthQuotaBean.get("city_area_out_standard_number"))
								.doubleValue();
						lastMonthQuotaBean = monthQuotaBean;
						isExistGenerateData = true;
					}
				}
				if (list != null && !list.isEmpty())  {
					if (isExistGenerateData==true) {
						logger.debug("���д�������ָ�����ݵĴ������û�У���ȡ���µ�ָ������");
						monthMap.put(month, lastMonthQuotaBean);
						maintenanceLength = ((BigDecimal) lastMonthQuotaBean.get(str_maintenance_length)).doubleValue();
						standardTimes += ((BigDecimal) lastMonthQuotaBean.get("interdiction_norm_times")).doubleValue();
						standardTime += ((BigDecimal) lastMonthQuotaBean.get("interdiction_norm_time")).doubleValue();
						dareTimes += ((BigDecimal) lastMonthQuotaBean.get("interdiction_dare_times")).doubleValue();
						dareTime += ((BigDecimal) lastMonthQuotaBean.get("interdiction_dare_time")).doubleValue();
					} else {
						monthMap.put(month, null);
					}
				} else {
					if (latestList != null && !latestList.isEmpty()) {
						logger.debug("���д��ڲ�����ָ�����ݵĴ���ȡ���һ�εĵ�������ָ������");
						lastMonthQuotaBean = (BasicDynaBean) latestList.get(latestList.size() - 1);
						maintenanceLength = ((BigDecimal) lastMonthQuotaBean.get(str_maintenance_length)).doubleValue();
						standardTimes += ((BigDecimal) lastMonthQuotaBean.get("interdiction_norm_times")).doubleValue();
						standardTime += ((BigDecimal) lastMonthQuotaBean.get("interdiction_norm_time")).doubleValue();
						dareTimes += ((BigDecimal) lastMonthQuotaBean.get("interdiction_dare_times")).doubleValue();
						dareTime += ((BigDecimal) lastMonthQuotaBean.get("interdiction_dare_time")).doubleValue();
						monthMap.put(month, lastMonthQuotaBean);
					} else {
						monthMap.put(month, null);
					}
				}
			}
			wholeYearTimes += (standardTimes * 12 / 12);
			wholeYearTime += (standardTime * 12 / 12);

			troubleAllTimes += troubleTimes;
			troubleAllTime += troubleTime;
			cityAreaOutStandardAllNumber += cityAreaOutStandardNumber;
			standardAllTimes += standardTimes;
			standardAllTime += standardTime;
			dareAllTimes += dareTimes;
			dareAllTime += dareTime;
			wholeYearAllTimes += wholeYearTimes;
			wholeYearAllTime += wholeYearTime;
			maintenanceAllLength += maintenanceLength;

			resultMap.put("maintenance_length", new Double(df.format(maintenanceLength)));
			resultMap.put("whole_year_standard_times", new Double(df.format(wholeYearTimes)));
			resultMap.put("whole_year_standard_time", new Double(df.format(wholeYearTime)));
			resultMap.put("trouble_times", new Double(df.format(troubleTimes)));
			resultMap.put("trouble_time", new Double(df.format(troubleTime)));
			resultMap.put("city_area_out_standard_number", new Double(df.format(cityAreaOutStandardNumber)));
			resultMap.put("standard_times", new Double(df.format(standardTimes)));
			resultMap.put("standard_time", new Double(df.format(standardTime)));
			resultMap.put("dare_times", new Double(df.format(dareTimes)));
			resultMap.put("dare_time", new Double(df.format(dareTime)));
			resultMap.put("monthMap", monthMap);
			contractorMap.put(contractorName, resultMap);
		}
		map.put("resultMap", contractorMap);

		if (troubleAllTimes == 0) {
			avgAllTime = 0;
		} else {
			avgAllTime = troubleAllTime / troubleAllTimes;
		}
		sumResultMap.put("maintenance_length", new Double(df.format(maintenanceAllLength)));
		sumResultMap.put("whole_year_standard_times", new Double(df.format(wholeYearAllTimes)));
		sumResultMap.put("whole_year_standard_time", new Double(df.format(wholeYearAllTime)));
		sumResultMap.put("trouble_times", new Double(df.format(troubleAllTimes)));
		sumResultMap.put("trouble_time", new Double(df.format(troubleAllTime)));
		sumResultMap.put("avg_time", new Double(df.format(avgAllTime)));
		sumResultMap.put("city_area_out_standard_number", new Double(df.format(cityAreaOutStandardAllNumber)));
		sumResultMap.put("standard_times", new Double(df.format(standardAllTimes)));
		sumResultMap.put("standard_time", new Double(df.format(standardAllTime)));
		sumResultMap.put("dare_times", new Double(df.format(dareAllTimes)));
		sumResultMap.put("dare_time", new Double(df.format(dareAllTime)));
		map.put("sumResultMap", sumResultMap);
		return map;
	}
	

	/**
	 * �ж��Ƿ�Ϊ��ǰ�·�
	 * @param month
	 * @return
	 */
	public boolean isCurrentMonth(String month) {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		int nowYear = calendar.get(Calendar.YEAR);
		int nowMonth = calendar.get(Calendar.MONTH);

		Date inputDate = DateUtil.Str2UtilDate(month, "yyyy/MM");
		calendar.setTime(inputDate);
		int inputYear = calendar.get(Calendar.YEAR);
		int inputMonth = calendar.get(Calendar.MONTH);

		if (nowYear == inputYear && nowMonth == inputMonth) {
			return true;
		}
		return false;
	}

	/**
	 * ��ѯ�Ѿ�����ָ�����ݵ��·�
	 * @param guideType
	 * @return
	 */
	public List getExistQuotaMonthsList(String guideType, String month) {
		// TODO Auto-generated method stub
		String sql = " select distinct to_char(stat_month,'yyyy/MM') as stat_month,finish_state from lp_trouble_guide_month ";
		sql += " where guide_type='" + guideType + "' ";
		sql += " and to_char(stat_month,'yyyy')=to_char(to_date('" + month + "','yyyy/mm'),'yyyy') ";
		sql += " order by stat_month ";
		List list = monthDAO.getJdbcTemplate().queryForBeans(sql);
		return list;
	}

	/**
	 * ͨ��id��ö���
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	public TroubleGuideMonth getTroubleGuideMonthById(String id) {
		return monthDAO.getTroubleGuideMonthById(id);
	}

	@Transactional
	public void updateTroubleGuideMonth(TroubleGuideMonth troubleGuideMonth) {
		monthDAO.updateTroubleGuideMonth(troubleGuideMonth);
	}
	
	/**
	 * ���ṩ����ָ����ж�ȡ���ݡ�Ϊ��ָ��ͼ����ʾ�ṩ������Դ��
	 * @param contractorId 
	 * @param guideType    
	 * @param beginYear  ��ʼ��
	 * @param numYear    0
	 * @return Map
	 */
	public Map getYearsTroubleQuotaList(String contractorId, String guideType, int beginYear, int numYear,
			String str_revise) {
		DecimalFormat df = new DecimalFormat("#0.00");
		Map map = new HashMap();

		Map sumResultMap = new HashMap();

		String str_interdiction_time = "interdiction_time";
		String str_trouble_times = "trouble_times";
		String str_maintenance_length = "maintenance_length";

		List list = yearQuotaDAO.getTimeAreaTroubleQuotaList(contractorId, guideType, beginYear, numYear);
		List latestList = yearQuotaDAO.getTimeAreaTroubleQuotaList(contractorId, guideType, beginYear - 1, numYear);

		BasicDynaBean yearQutoaBean;
		BasicDynaBean lastYearQuotaBean = null;

		double troubleTimes = 0;
		double troubleTime = 0;
		double standardTimes = 0;
		double standardTime = 0;
		double dareTimes = 0;
		double dareTime = 0;
		double cityAreaOutStandardNumber = 0;

		int year = 0;
		double rtrTimeNormValue = 0;
		double rtrTimeDareValue = 0;
		double rtrTimeFinishValue = 0;
		double maintenanceLength = 0;

		double wholeYearTimes = 0;
		double wholeYearTime = 0;
		for (int k = 0; list != null && k < list.size(); k++) {
			logger.info("���д�������ָ�����ݵĴ���");
			yearQutoaBean = (BasicDynaBean) list.get(k);
			troubleTimes = ((BigDecimal) yearQutoaBean.get(str_trouble_times)).doubleValue();
			troubleTime = ((BigDecimal) yearQutoaBean.get(str_interdiction_time)).doubleValue();
			maintenanceLength = ((BigDecimal) yearQutoaBean.get(str_maintenance_length)).doubleValue();
			standardTimes = ((BigDecimal) yearQutoaBean.get("interdiction_norm_times")).doubleValue();
			standardTime = ((BigDecimal) yearQutoaBean.get("interdiction_norm_time")).doubleValue();
			dareTimes = ((BigDecimal) yearQutoaBean.get("interdiction_dare_times")).doubleValue();
			dareTime = ((BigDecimal) yearQutoaBean.get("interdiction_dare_time")).doubleValue();
			year = Integer.parseInt((String) yearQutoaBean.get("year"));
			rtrTimeNormValue = ((BigDecimal) yearQutoaBean.get("rtr_time_norm_value")).doubleValue();
			rtrTimeDareValue = ((BigDecimal) yearQutoaBean.get("rtr_time_dare_value")).doubleValue();
			rtrTimeFinishValue = ((BigDecimal) yearQutoaBean.get("rtr_time_finish_value")).doubleValue();

		}
		//���ĳ��û������ ������ȡĳ�µ���������
		if ((list == null) || (list.isEmpty() == true)) {
			if (latestList != null && !latestList.isEmpty()) {
				logger.debug("���д��ڲ�����ָ�����ݵĴ���ȡ���һ�εĵ�������ָ������");

				for (int k = 0; latestList != null && k < latestList.size(); k++) {
					logger.debug("���д�������ָ�����ݵĴ���");
					lastYearQuotaBean = (BasicDynaBean) latestList.get(k);
					troubleTimes = ((BigDecimal) lastYearQuotaBean.get(str_trouble_times)).doubleValue();
					troubleTime = ((BigDecimal) lastYearQuotaBean.get(str_interdiction_time)).doubleValue();
					maintenanceLength = ((BigDecimal) lastYearQuotaBean.get(str_maintenance_length)).doubleValue();
					standardTimes = ((BigDecimal) lastYearQuotaBean.get("interdiction_norm_times")).doubleValue();
					standardTime = ((BigDecimal) lastYearQuotaBean.get("interdiction_norm_time")).doubleValue();
					dareTimes = ((BigDecimal) lastYearQuotaBean.get("interdiction_dare_times")).doubleValue();
					dareTime = ((BigDecimal) lastYearQuotaBean.get("interdiction_dare_time")).doubleValue();
					year = Integer.parseInt((String) lastYearQuotaBean.get("year"));
					rtrTimeNormValue = ((BigDecimal) lastYearQuotaBean.get("rtr_time_norm_value")).doubleValue();
					rtrTimeDareValue = ((BigDecimal) lastYearQuotaBean.get("rtr_time_dare_value")).doubleValue();
					rtrTimeFinishValue = ((BigDecimal) lastYearQuotaBean.get("rtr_time_finish_value")).doubleValue();
				}

			}
		}

		sumResultMap.put("maintenance_length", new Double(df.format(maintenanceLength)));
		sumResultMap.put("trouble_times", new Double(df.format(troubleTimes)));
		sumResultMap.put("trouble_time", new Double(df.format(troubleTime)));
		sumResultMap.put("city_area_out_standard_number", new Double(df.format(cityAreaOutStandardNumber)));
		sumResultMap.put("standard_times", new Double(df.format(standardTimes)));
		sumResultMap.put("standard_time", new Double(df.format(standardTime)));
		sumResultMap.put("dare_times", new Double(df.format(dareTimes)));
		sumResultMap.put("dare_time", new Double(df.format(dareTime)));
		sumResultMap.put("rtrTimeNormValue", new Double(df.format(rtrTimeNormValue)));
		sumResultMap.put("rtrTimeDareValue", new Double(df.format(rtrTimeDareValue)));
		sumResultMap.put("rtrTimeFinishValue", new Double(df.format(rtrTimeFinishValue)));
		map.put("sumResultMap", sumResultMap);
		return map;
	}

	/**
	 * ��������ֵ
	 * @param id
	 * @param reviseInterdictionTime
	 * @param reviseTroubleTimes
	 * @param reviseMaintenanceLength
	 * @return
	 */
	@Transactional
	public String updateReviseValueGuideMonth(String id, String reviseInterdictionTime, String reviseTroubleTimes,
			String reviseMaintenanceLength) {
		TroubleGuideMonth troubleGuideMonth=monthDAO.getTroubleGuideMonthById(id);
		TroubleNormGuide guide = dao.getQuotaByType(troubleGuideMonth.getGuideType());
		if(reviseInterdictionTime !=null && !"".equals(reviseInterdictionTime)){
			troubleGuideMonth.setReviseInterdictionTime(Double.valueOf(reviseInterdictionTime));
		}
		if(reviseTroubleTimes != null && !"".equals(reviseTroubleTimes)){
			troubleGuideMonth.setReviseTroubleTimes(Integer.valueOf(reviseTroubleTimes));
		}
		if(reviseMaintenanceLength != null && !"".equals(reviseMaintenanceLength)){
			double cableLength = Double.valueOf(reviseMaintenanceLength);
			troubleGuideMonth.setReviseMaintenanceLength (Double.valueOf(reviseMaintenanceLength));
			double timesDareValue = calculateQuota(cableLength,guide.getInterdictionTimesDareValue());//ǧ������ϴ�����սֵ
			double timesNormValue = calculateQuota(cableLength,guide.getInterdictionTimesNormValue());//ǧ������ϴ�����׼ֵ
			double timeNormValue = calculateQuota(cableLength,guide.getInterdictionTimeNormValue());//ǧ���������ʱ��׼ֵ
			double timeDareValue = calculateQuota(cableLength,guide.getInterdictionTimeDareValue());//ǧ���������ʱ��սֵ
			troubleGuideMonth.setInterdictionNormTimes(timesNormValue);
			troubleGuideMonth.setInterdictionDareTimes(timesDareValue);
			troubleGuideMonth.setInterdictionNormTime(timeNormValue);
			troubleGuideMonth.setInterdictionDareTime(timeDareValue);
		}
		
		monthDAO.updateTroubleGuideMonth(troubleGuideMonth);
		String result = getFormatValue(troubleGuideMonth.getInterdictionNormTimes())+","+getFormatValue(troubleGuideMonth.getInterdictionDareTimes())
		+","+getFormatValue(troubleGuideMonth.getInterdictionNormTime())+","+getFormatValue(troubleGuideMonth.getInterdictionDareTime());
		return result;
	}
	
	private String getFormatValue(double value){
		DecimalFormat df=new DecimalFormat("#0.00"); 
		return df.format(value);
	}
	/**
	 * �Զ�����ָ�걣��
	 * @param yearTroubleQuota
	 */
	@Transactional
	public String saveCustomYearTroubleQuota(TroubleYearQuotaBean bean){
		TroubleGuideYear quotaYear = new TroubleGuideYear();
		List<TroubleGuideYear> quotaList = yearQuotaDAO.isCreate(bean.getGuideType(),bean.getYear());
		
		BeanCopier copier = BeanCopier.create(TroubleYearQuotaBean.class, TroubleGuideYear.class, false);
		copier.copy(bean, quotaYear, null);
		if("".equals(quotaYear.getId())){
			quotaYear.setId(null);
		}else{
			quotaYear = yearQuotaDAO.get(quotaYear.getId());
			copier.copy(bean, quotaYear, null);
		}
		
		if(quotaYear.getId() != null){
			yearQuotaDAO.save(quotaYear);
			return "true";
		}
		if(quotaYear.getId() == null && !(quotaList.size()>0)){
			yearQuotaDAO.save(quotaYear);
			return "true";
		}else{
			return "false";
		}
	}
	/**
	 * �������е��Զ�����ָ��
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<TroubleGuideYear> queryCustonYearTroubleQuota(){
		return yearQuotaDAO.queryAllQuota();
	}
	
	@Transactional(readOnly=true)
	public TroubleGuideYear getCustomYearQuota(String id) {
		return yearQuotaDAO.queryYearQuota(id);
	}
	
}
