package com.cabletech.schedule.service;

import java.util.Date;

import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cabletech.schedule.trigger.TriggerBuilder;

@Service
public class ScheduleService {
	private Scheduler scheduler;
	private JobDetail jobDetail;

	@Autowired
	public void setJobDetail(@Qualifier("jobDetail") JobDetail jobDetail) {
		this.jobDetail = jobDetail;
	}

	@Autowired
	public void setScheduler(@Qualifier("quartzScheduler") Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	/**
	 * 执行根据定时任务名称回去定时任务触发器
	 * 
	 * @param triggerName
	 * @return
	 * @throws Exception
	 */
	public SimpleTrigger getSimpleTrigger(String triggerName) throws Exception {
		return (SimpleTrigger) scheduler.getTrigger(triggerName,
				Scheduler.DEFAULT_GROUP);
	}

	/**
	 * 执行根据定时任务名称回去定时任务触发器
	 * 
	 * @param triggerName
	 * @return
	 * @throws Exception
	 */
	public CronTrigger getCronTrigger(String triggerName) throws Exception {
		return (CronTrigger) scheduler.getTrigger(triggerName,
				Scheduler.DEFAULT_GROUP);
	}

	/**
	 * 设置定时任务处理参数信息
	 * 
	 * @param content
	 * @param sim
	 */
	public void setJobParameter(String content, String sim) {
		JobDataMap parameterMap = jobDetail.getJobDataMap();
		parameterMap.put("content", content);
		parameterMap.put("sim", sim);
		jobDetail.setJobDataMap(parameterMap);
	}

	/**
	 * 执行定时触发器任务
	 * 
	 * @param trigger
	 * @throws Exception
	 */
	public void schedule(Trigger trigger) throws Exception {
		scheduler.scheduleJob(trigger);
		scheduler.rescheduleJob(trigger.getName(), Scheduler.DEFAULT_GROUP,
				trigger);
	}

	/**
	 * 执行单次定时发送任务
	 * 
	 * @param triggerName
	 * @param beginDate
	 * @param endDate
	 * @throws Exception
	 */
	public void schedule(String triggerName, Date beginDate, Date endDate)
			throws Exception {
		schedule(triggerName, beginDate, endDate, -1, 0);
	}

	/**
	 * 执行根据输入日期按周期定时发送任务
	 * 
	 * @param triggerName
	 * @param date
	 * @throws Exception
	 */
	public void schedule(String triggerName, String[] date) throws Exception {
		jobDetail.setName(triggerName);
		scheduler.addJob(jobDetail, true);
		TriggerBuilder triggerBuilder = new TriggerBuilder();
		CronTrigger trigger = triggerBuilder.getCronTrigger(triggerName,
				Scheduler.DEFAULT_GROUP, jobDetail.getName(), date);
		scheduler.scheduleJob(trigger);
		scheduler.rescheduleJob(triggerName, Scheduler.DEFAULT_GROUP, trigger);
	}

	/**
	 * 执行根据输入日期字符串按周期定时发送任务
	 * 
	 * @param triggerName
	 * @param expression
	 * @throws Exception
	 */
	public void schedule(String triggerName, String expression)
			throws Exception {
		jobDetail.setName(triggerName);
		scheduler.addJob(jobDetail, true);
		TriggerBuilder triggerBuilder = new TriggerBuilder();
		CronTrigger trigger = triggerBuilder.getCronTrigger(triggerName,
				Scheduler.DEFAULT_GROUP, jobDetail.getName(), expression);
		scheduler.scheduleJob(trigger);
		scheduler.rescheduleJob(triggerName, Scheduler.DEFAULT_GROUP, trigger);
	}

	/**
	 * 执行根据输入的开始结束时间和时间间隔定时发送任务
	 * 
	 * @param triggerName
	 * @param beginDate
	 * @param endDate
	 * @param timeSpaceUnit
	 * @param timeSpace
	 * @throws Exception
	 */
	public void schedule(String triggerName, Date beginDate, Date endDate,
			int timeSpaceUnit, int timeSpace) throws Exception {
		jobDetail.setName(triggerName);
		scheduler.addJob(jobDetail, true);
		TriggerBuilder triggerBuilder = new TriggerBuilder();
		SimpleTrigger trigger = triggerBuilder.getSimpleTrigger(triggerName,
				Scheduler.DEFAULT_GROUP, jobDetail.getName(), beginDate,
				endDate, timeSpaceUnit, timeSpace);
		scheduler.scheduleJob(trigger);
		scheduler.rescheduleJob(triggerName, Scheduler.DEFAULT_GROUP, trigger);
	}

	/**
	 * 执行暂停定时发送任务
	 * 
	 * @param triggerName
	 * @throws Exception
	 */
	public void pauseSchedule(String triggerName) throws Exception {
		scheduler.pauseTrigger(triggerName, Scheduler.DEFAULT_GROUP);
	}

	/**
	 * 执行继续定时发送任务
	 * 
	 * @param triggerName
	 * @throws Exception
	 */
	public void resumeSchedule(String triggerName) throws Exception {
		scheduler.resumeTrigger(triggerName, Scheduler.DEFAULT_GROUP);
	}

	/**
	 * 执行取消定时发送任务
	 * 
	 * @param triggerName
	 * @throws Exception
	 */
	public void cancelSchedule(String triggerName) throws Exception {
		scheduler.pauseTrigger(triggerName, Scheduler.DEFAULT_GROUP);
		scheduler.unscheduleJob(triggerName, Scheduler.DEFAULT_GROUP);
	}
}
