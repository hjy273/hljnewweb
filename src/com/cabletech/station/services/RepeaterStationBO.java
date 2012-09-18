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
 * 中继站的业务操作类
 * 
 * @author yangjun
 * 
 */
public class RepeaterStationBO extends BaseBO {
	public RepeaterStationBO() {
		super.setBaseDao(new RepeaterStationDAO());
	}

	/**
	 * 根据中继站的系统编号判断中继站是否存在
	 * 
	 * @param objectId
	 *            String 中继站的系统编号
	 * @return boolean 返回中继站是否存在的标记
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
	 * 根据中继站名称和中继站的所属地州判断中继站是否存在
	 * 
	 * @param stationBean
	 *            RepeaterStationBean 中继站基本信息Bean
	 * @return boolean 返回中继站是否存在的标记
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
	 * 插入中继站基本信息
	 * 
	 * @param stationBean
	 *            RepeaterStationBean 中继站基本信息Bean
	 * @return String 返回执行动作状态编号
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
	 * 更新中继站基本信息
	 * 
	 * @param stationBean
	 *            RepeaterStationBean 中继站基本信息Bean
	 * @return String 返回执行动作状态编号
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
	 * 删除中继站基本信息
	 * 
	 * @param stationBean
	 *            RepeaterStationBean 中继站基本信息Bean
	 * @return String 返回执行动作状态编号
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
	 * 根据中继站编号查看中继站基本信息
	 * 
	 * @param stationId
	 *            String 中继站编号
	 * @return RepeaterStationBean 返回中继站基本信息Bean
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
	 * 根据查询条件获取中继站基本信息列表
	 * 
	 * @param condition
	 *            String 查询条件
	 * @return List 返回中继站基本信息列表
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
	 * 根据中继站基本信息和响应对象进行中继站基本信息的导出
	 * 
	 * @param response
	 *            HttpServletResponse 响应对象
	 * @param list
	 *            List 中继站基本信息
	 * @throws Exception
	 */
	public void exportRepeaterStation(HttpServletResponse response, List list) throws Exception {
		initResponse(response, "中继站信息.xls");
		OutputStream out = response.getOutputStream();
		String urlPath = ReportConfig.getInstance().getUrlPath("report.repeaterstation");
		BasicTemplate template = new RepeaterStationTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.doExport(list, excelstyle);
		template.write(out);
	}

	/**
	 * 根据中继站基本信息列表和响应对象进行中继站基本信息列表的导出
	 * 
	 * @param response
	 *            HttpServletResponse 响应对象
	 * @param list
	 *            List 中继站基本信息列表
	 * @throws Exception
	 */
	public void exportRepeaterStationList(HttpServletResponse response, List list) throws Exception {
		initResponse(response, "中继站列表.xls");
		OutputStream out = response.getOutputStream();
		String urlPath = ReportConfig.getInstance().getUrlPath("report.repeaterstationlist");
		BasicTemplate template = new RepeaterStationListTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.doExport(list, excelstyle);
		template.write(out);
	}

	/**
	 * 进行中继站基本信息的保存动作
	 * 
	 * @param station
	 *            RepeaterStation 中继站基本信息实体
	 * @param actionType
	 *            String 执行的动作
	 * @return String 返回执行动作状态编号
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
	 * 根据中继站系统编号查询是否存在该中继站的中继站计划
	 * 
	 * @param stationId
	 *            String 中继站系统编号
	 * @return boolean 返回是否存在该中继站的中继站计划的标记
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
