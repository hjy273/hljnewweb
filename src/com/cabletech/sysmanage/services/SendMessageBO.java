package com.cabletech.sysmanage.services;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.schedule.dao.SendSmJobDao;
import com.cabletech.schedule.module.SendSmJobInfo;
import com.cabletech.schedule.service.ScheduleService;
import com.cabletech.schedule.trigger.TriggerBuilder;

@Service
@Transactional
public class SendMessageBO extends EntityManager<SendSmJobInfo, String> {
	@Resource(name = "sendSmJobDao")
	private SendSmJobDao dao;
	@Resource(name = "scheduleService")
	private ScheduleService scheduleService;

	@Override
	protected HibernateDao<SendSmJobInfo, String> getEntityDao() {
		return dao;
	}

	public void sendMessage(String content, String sim, String sendMethod,
			String beginDate, String endDate, String intervalType,
			String interval, String createUserId, String[] inputDate,
			String sendObjectName, String sendCycleRule) throws Exception {
		OracleIDImpl idGenerate = new OracleIDImpl();
		String seqId = idGenerate.getSeq("SEND_MESSAGE", 20);
		sendMessage("message." + seqId, content, sim, sendMethod, beginDate,
				endDate, intervalType, interval, createUserId, inputDate,
				sendObjectName, sendCycleRule);
	}

	public void sendMessage(String key, String content, String sim,
			String sendMethod, String beginDate, String endDate,
			String intervalType, String interval, String createUserId,
			String[] inputDate, String sendObjectName, String sendCycleRule)
			throws Exception {
		if ("0".equals(sendMethod)) {
			logger.info("即时发送手机：" + sim);
			logger.info("即时发送内容：" + content);
			super.sendMessage(content, sim);
		} else if ("1".equals(sendMethod)) {
			SendSmJobInfo job = new SendSmJobInfo();
			job.setFirstSendTime(DateUtil.Str2UtilDate(beginDate,
					"yyyy/MM/dd HH:mm:ss"));
			job.setLastSendTime(DateUtil.Str2UtilDate(endDate,
					"yyyy/MM/dd HH:mm:ss"));
			job.setSendContent(content);
			job.setSchedularName("trigger." + key);
			job.setSendObjectId(key);
			job.setCreateDate(new Date());
			job.setCreateUserId(createUserId);
			job.setSendState(SendSmJobInfo.NOT_SENT_STATE);
			job.setSendObjectName(sendObjectName);
			job.setSendCycleRule(sendCycleRule);
			job.setCronExpressionString("");
			if ("-1".equals(intervalType)) {
				job.setLastSendTime(DateUtil.Str2UtilDate(beginDate,
						"yyyy/MM/dd HH:mm:ss"));
				job.setSendType(SendSmJobInfo.ONLY_ONCE_SEND);
				job.setSendTimeType("");
				job.setSendTimeSpace(0);
			} else {
				job.setSendType(SendSmJobInfo.LOOP_SEND);
				job.setSendTimeType(intervalType);
				if (interval != null && !interval.equals("")) {
					job.setSendTimeSpace(Integer.parseInt(interval));
				} else {
					job.setSendTimeSpace(1);
				}
			}
			job.setSimId(sim);
			dao.save(job);
			scheduleService.setJobParameter(content, sim);
			scheduleService.schedule(job.getSchedularName(), job
					.getFirstSendTime(), job.getLastSendTime(), Integer
					.parseInt(intervalType), job.getSendTimeSpace());
		} else {
			String expression = "ss mm hh DD MM WW YY";
			String cronExpressionString = TriggerBuilder
					.getCronExpressionString(inputDate, expression);
			SendSmJobInfo job = new SendSmJobInfo();
			job.setFirstSendTime(null);
			job.setLastSendTime(null);
			job.setSendContent(content);
			job.setSchedularName("trigger." + key);
			job.setSendObjectId(key);
			job.setCreateDate(new Date());
			job.setCreateUserId(createUserId);
			job.setSendState(SendSmJobInfo.NOT_SENT_STATE);
			job.setSendObjectName(sendObjectName);
			job.setSendCycleRule(sendCycleRule);
			job.setCronExpressionString(cronExpressionString);
			job.setSimId(sim);
			job.setSendTimeType("");
			job.setSendType(SendSmJobInfo.LOOP_SEND_CYCLE);
			job.setSendTimeSpace(0);

			dao.save(job);
			scheduleService.setJobParameter(content, sim);
			scheduleService.schedule(job.getSchedularName(), inputDate);
		}
	}

	public void cancelSendMessageSchedule(String key) throws Exception {
		List jobList = dao.findByKey(key);
		SendSmJobInfo job;
		for (int i = 0; jobList != null && i < jobList.size(); i++) {
			job = (SendSmJobInfo) jobList.get(i);
			job.setSendState(SendSmJobInfo.CANCEL_SENT_STATE);
			dao.save(job);
			scheduleService.cancelSchedule(job.getSchedularName());
		}
	}

	public void cancelSendMessageScheduleByName(String scheduleName)
			throws Exception {
		List jobList = dao.findByName(scheduleName);
		SendSmJobInfo job;
		for (int i = 0; jobList != null && i < jobList.size(); i++) {
			job = (SendSmJobInfo) jobList.get(i);
			job.setSendState(SendSmJobInfo.CANCEL_SENT_STATE);
			dao.save(job);
			scheduleService.cancelSchedule(job.getSchedularName());
		}
	}

	public void updateSendSmFinishState(String name) {
		// TODO Auto-generated method stub
		dao.updateSendSmFinishState(name);
	}
}
