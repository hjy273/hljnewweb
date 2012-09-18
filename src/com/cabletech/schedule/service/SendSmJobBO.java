package com.cabletech.schedule.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.schedule.bean.SendSmJobInfoBean;
import com.cabletech.schedule.dao.SendSmJobDao;
import com.cabletech.schedule.module.SendSmJobInfo;
import com.cabletech.schedule.trigger.TriggerBuilder;

@Service
@Transactional
public class SendSmJobBO extends EntityManager<SendSmJobInfo, String> {
	@Resource(name = "sendSmJobDao")
	private SendSmJobDao dao;
	@Resource(name = "scheduleService")
	private ScheduleService scheduleService;

	/**
	 * 执行根据输入条件查询定时发送任务信息
	 * 
	 * @param userInfo
	 * @param sendType
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public List findByCondition(UserInfo userInfo, String sendType,
			String beginTime, String endTime) {
		String condition = "";
		condition += ConditionGenerate.getCondition("send_type", sendType,
				"send_type", "=");
		condition += ConditionGenerate.getDateCondition("create_date",
				beginTime, "create_date", ">=", "00:00:00");
		condition += ConditionGenerate.getDateCondition("create_date", endTime,
				"create_date", "<=", "23:59:59");
		condition += " and create_user_id='" + userInfo.getUserID() + "' ";
		List list = dao.findByCondition(condition);
		return list;
	}

	public SendSmJobInfoBean viewSendSmJobInfo(String id) throws Exception {
		SendSmJobInfo job = dao.get(id);
		dao.initObject(job);
		SendSmJobInfoBean jobBean = new SendSmJobInfoBean();
		BeanUtil.copyProperties(job, jobBean);
		Date prevDate;
		Date nextDate;
		String prevDateStr;
		String nextDateStr;
		if (jobBean != null) {
			Trigger trigger = null;
			String triggerName = jobBean.getSchedularName();
			if (SendSmJobInfo.ONLY_ONCE_SEND.equals(job.getSendType())) {
				trigger = scheduleService.getSimpleTrigger(triggerName);
			}
			if (SendSmJobInfo.LOOP_SEND.equals(job.getSendType())) {
				trigger = scheduleService.getSimpleTrigger(triggerName);
			}
			if (SendSmJobInfo.LOOP_SEND_CYCLE.equals(job.getSendType())) {
				trigger = scheduleService.getCronTrigger(triggerName);
			}
			if (trigger != null) {
				prevDate = trigger.getPreviousFireTime();
				nextDate = trigger.getNextFireTime();
				if (prevDate != null) {
					prevDateStr = DateUtil.UtilDate2Str(prevDate,
							"yyyy/MM/dd HH:mm:ss");
					jobBean.setPrevSentTime(prevDateStr);
				}
				if (nextDate != null) {
					nextDateStr = DateUtil.UtilDate2Str(nextDate,
							"yyyy/MM/dd HH:mm:ss");
					jobBean.setNextSentTime(nextDateStr);
				}
			}
		}
		return jobBean;
	}

	public void reloadSchedule() throws Exception {
		logger.info("重启之前的定时调度任务");
		String hql = " from SendSmJobInfo s ";
		hql += " where s.sendState='" + SendSmJobInfo.NOT_SENT_STATE + "' ";
		hql += " and (s.lastSendTime>sysdate or s.sendType='"
				+ SendSmJobInfo.LOOP_SEND_CYCLE + "') ";
		List list = dao.find(hql);
		SendSmJobInfo job;
		TriggerBuilder triggerBuilder = new TriggerBuilder();
		for (int i = 0; list != null && i < list.size(); i++) {
			job = (SendSmJobInfo) list.get(i);
			if (job != null) {
				Trigger trigger = null;
				String triggerName = job.getSchedularName();
				logger.info("执行重启之前的定时触发器任务：" + triggerName);
				scheduleService.setJobParameter(job.getSendContent(), job
						.getSimId());
				if (SendSmJobInfo.ONLY_ONCE_SEND.equals(job.getSendType())) {
					scheduleService.schedule(triggerName, job
							.getFirstSendTime(), job.getLastSendTime(), -1, job
							.getSendTimeSpace());
				}
				if (SendSmJobInfo.LOOP_SEND.equals(job.getSendType())) {
					scheduleService.schedule(triggerName, job
							.getFirstSendTime(), job.getLastSendTime(), Integer
							.parseInt(job.getSendTimeType()), job
							.getSendTimeSpace());
				}
				if (SendSmJobInfo.LOOP_SEND_CYCLE.equals(job.getSendType())) {
					scheduleService.schedule(triggerName, job
							.getCronExpressionString());
				}
			}
		}
	}

	@Override
	protected HibernateDao<SendSmJobInfo, String> getEntityDao() {
		return dao;
	}
}
