package com.cabletech.station.services;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;

import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.config.ReportConfig;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.station.beans.RepeaterStationBean;
import com.cabletech.station.dao.RepeaterStationDAO;
import com.cabletech.station.dao.RepeaterStationInPlanDAO;
import com.cabletech.station.domainobjects.RepeaterStation;
import com.cabletech.station.template.BasicTemplate;
import com.cabletech.station.template.RepeaterStationListTemplate;
import com.cabletech.station.template.RepeaterStationTemplate;

/**
 * �м�վ��ҵ�������
 * 
 * @author yangjun
 * 
 */
public class RepeaterStationBO extends BaseBO {
	public RepeaterStationBO() {
		super.setBaseDao(new RepeaterStationDAO());
	}

	/**
	 * �����м�վ��ϵͳ����ж��м�վ�Ƿ����
	 * 
	 * @param objectId
	 *            String �м�վ��ϵͳ���
	 * @return boolean �����м�վ�Ƿ���ڵı��
	 */
	public boolean queryExistById(String objectId) {
		// TODO Auto-generated method stub
		boolean flag = false;

		try {
			RepeaterStation station = (RepeaterStation) baseDao.load(objectId, RepeaterStation.class);
			if (station != null && StationConstant.IS_ACTIVED.equals(station.getStationState())) {
				flag = true;
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * �����м�վ���ƺ��м�վ�����������ж��м�վ�Ƿ����
	 * 
	 * @param stationBean
	 *            RepeaterStationBean �м�վ������ϢBean
	 * @return boolean �����м�վ�Ƿ���ڵı��
	 */
	public boolean queryExistByName(RepeaterStationBean stationBean) {
		boolean flag = false;

		try {
			String condition = " and station_name='" + stationBean.getStationName() + "' and region_id='"
					+ stationBean.getRegionId() + "'";
			condition = condition + " and station_state='" + StationConstant.IS_ACTIVED + "' ";
			Object object = baseDao.find("RepeaterStation", condition);
			if (object != null) {
				flag = true;
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * �����м�վ������Ϣ
	 * 
	 * @param stationBean
	 *            RepeaterStationBean �м�վ������ϢBean
	 * @return String ����ִ�ж���״̬���
	 * @throws Exception
	 */
	public String insertRepeaterStation(RepeaterStationBean stationBean) throws Exception {
		super.setBaseDao(new RepeaterStationDAO());
		RepeaterStation station = new RepeaterStation();
		BeanUtil.objectCopy(stationBean, station);

		if (queryExistByName(stationBean)) {
			return ExecuteCode.EXIST_STATION_ERR_CODE;
		}

		return writeRepeaterStation(station, "insert");
	}

	/**
	 * �����м�վ������Ϣ
	 * 
	 * @param stationBean
	 *            RepeaterStationBean �м�վ������ϢBean
	 * @return String ����ִ�ж���״̬���
	 * @throws Exception
	 */
	public String updateRepeaterStation(RepeaterStationBean stationBean) throws Exception {
		super.setBaseDao(new RepeaterStationDAO());
		if (!queryExistById(stationBean.getTid())) {
			return ExecuteCode.NOT_EXIST_STATION_ERR_CODE;
		}
		// if (judgeExistPlanOnStation(stationBean.getTid())) {
		// return ExecuteCode.EXIST_PLAN_ERR_CODE;
		// }

		RepeaterStation station = new RepeaterStation();
		station = (RepeaterStation) super.baseDao.load(stationBean.getTid(), RepeaterStation.class);
		if (queryExistByName(stationBean)) {
			if (!station.getRegionId().equals(stationBean.getRegionId())) {
				return ExecuteCode.EXIST_STATION_ERR_CODE;
			}
			if (!station.getStationName().equals(stationBean.getStationName())) {
				return ExecuteCode.EXIST_STATION_ERR_CODE;
			}
		}
		Date createDate = station.getCreateDate();
		BeanUtil.objectCopy(stationBean, station);
		station.setCreateDate(createDate);

		return writeRepeaterStation(station, "update");
	}

	/**
	 * ɾ���м�վ������Ϣ
	 * 
	 * @param stationBean
	 *            RepeaterStationBean �м�վ������ϢBean
	 * @return String ����ִ�ж���״̬���
	 * @throws Exception
	 */
	public String deleteRepeaterStation(RepeaterStationBean stationBean) throws Exception {
		super.setBaseDao(new RepeaterStationDAO());
		if (!queryExistById(stationBean.getTid())) {
			return ExecuteCode.NOT_EXIST_STATION_ERR_CODE;
		}

		if (judgeExistPlanOnStation(stationBean.getTid())) {
			return ExecuteCode.EXIST_PLAN_ERR_CODE;
		}

		RepeaterStation station = new RepeaterStation();
		station = (RepeaterStation) super.baseDao.load(stationBean.getTid(), RepeaterStation.class);
		// BeanUtil.objectCopy(stationBean, station);
		station.setStationState(StationConstant.IS_DELETED);
		// Object object = super.baseDao.delete(station);
		Object object = super.baseDao.update(station);
		if (object != null) {
			return ExecuteCode.SUCCESS_CODE;
		}
		return ExecuteCode.FAIL_CODE;
	}

	/**
	 * �����м�վ��Ų鿴�м�վ������Ϣ
	 * 
	 * @param stationId
	 *            String �м�վ���
	 * @return RepeaterStationBean �����м�վ������ϢBean
	 * @throws Exception
	 */
	public RepeaterStationBean viewRepeaterStation(String stationId) throws Exception {
		super.setBaseDao(new RepeaterStationDAO());
		RepeaterStationBean stationBean = new RepeaterStationBean();
		RepeaterStation station = (RepeaterStation) super.baseDao.load(stationId, RepeaterStation.class);
		if (station != null) {
			BeanUtil.objectCopy(station, stationBean);
		} else {
			stationBean = null;
		}
		return stationBean;
	}

	/**
	 * ���ݲ�ѯ������ȡ�м�վ������Ϣ�б�
	 * 
	 * @param condition
	 *            String ��ѯ����
	 * @return List �����м�վ������Ϣ�б�
	 * @throws Exception
	 */
	public List queryRepeaterStationList(String condition) throws Exception {
		super.setBaseDao(new RepeaterStationDAO());
		List list = new ArrayList();
		List stationList = super.baseDao.queryByCondition(condition);
		DynaBean bean;
		boolean flag = false;
		for (int i = 0; stationList != null && i < stationList.size(); i++) {
			bean = (DynaBean) stationList.get(i);

			flag = judgeExistPlanOnStation((String) bean.get("tid"));
			if (flag) {
				bean.set("is_allow", "0");
			} else {
				bean.set("is_allow", "1");
			}
			list.add(bean);
		}
		return list;
	}

	/**
	 * �����м�վ������Ϣ����Ӧ��������м�վ������Ϣ�ĵ���
	 * 
	 * @param response
	 *            HttpServletResponse ��Ӧ����
	 * @param list
	 *            List �м�վ������Ϣ
	 * @throws Exception
	 */
	public void exportRepeaterStation(HttpServletResponse response, List list) throws Exception {
		initResponse(response, "�м�վ��Ϣ.xls");
		OutputStream out = response.getOutputStream();
		String urlPath = ReportConfig.getInstance().getUrlPath("report.repeaterstation");
		BasicTemplate template = new RepeaterStationTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.doExport(list, excelstyle);
		template.write(out);
	}

	/**
	 * �����м�վ������Ϣ�б����Ӧ��������м�վ������Ϣ�б�ĵ���
	 * 
	 * @param response
	 *            HttpServletResponse ��Ӧ����
	 * @param list
	 *            List �м�վ������Ϣ�б�
	 * @throws Exception
	 */
	public void exportRepeaterStationList(HttpServletResponse response, List list) throws Exception {
		initResponse(response, "�м�վ�б�.xls");
		OutputStream out = response.getOutputStream();
		String urlPath = ReportConfig.getInstance().getUrlPath("report.repeaterstationlist");
		BasicTemplate template = new RepeaterStationListTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.doExport(list, excelstyle);
		template.write(out);
	}

	/**
	 * �����м�վ������Ϣ�ı��涯��
	 * 
	 * @param station
	 *            RepeaterStation �м�վ������Ϣʵ��
	 * @param actionType
	 *            String ִ�еĶ���
	 * @return String ����ִ�ж���״̬���
	 * @throws Exception
	 */
	public String writeRepeaterStation(RepeaterStation station, String actionType) throws Exception {
		super.setBaseDao(new RepeaterStationDAO());
		String operationCode = ExecuteCode.FAIL_CODE;
		station.setStationState(StationConstant.IS_ACTIVED);

		Object object = null;
		if ("insert".equals(actionType)) {
			station.setTid(ora.getSeq("REPEATER_STATION_INFO", 18));
			station.setCreateDate(new Date());
			object = super.baseDao.insert(station);
		}
		if ("update".equals(actionType)) {
			object = super.baseDao.update(station);
		}
		if (object != null) {
			operationCode = ExecuteCode.SUCCESS_CODE;
		} else {
			operationCode = ExecuteCode.FAIL_CODE;
		}

		return operationCode;
	}

	/**
	 * �����м�վϵͳ��Ų�ѯ�Ƿ���ڸ��м�վ���м�վ�ƻ�
	 * 
	 * @param stationId
	 *            String �м�վϵͳ���
	 * @return boolean �����Ƿ���ڸ��м�վ���м�վ�ƻ��ı��
	 * @throws Exception
	 */
	public boolean judgeExistPlanOnStation(String stationId) throws Exception {
		super.setBaseDao(new RepeaterStationInPlanDAO());
		boolean flag = false;
		String hql = " and repeater_station_id='" + stationId + "' ";
		hql = hql + " and s.station_state='" + StationConstant.IS_ACTIVED + "' ";
		hql = hql + " and p.plan_state<>'" + StationConstant.FINISHED_STATE + "'";
		List list = super.baseDao.queryByCondition(hql);
		if (list != null && list.size() > 0) {
			flag = true;
		}
		return flag;
	}

}
