package com.cabletech.schedule.trigger;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.SimpleTrigger;

public class TriggerBuilder {
	private Logger logger = Logger.getLogger(TriggerBuilder.class);
	private int intervalType = INTERVAL_SECOND_TYPE;
	private long interval = 1;
	public static final int SECOND_LENGTH = 1000;
	public static final int MINUTE_LENGTH = 60 * SECOND_LENGTH;
	public static final int HOUR_LENGTH = 60 * MINUTE_LENGTH;
	public static final int DAY_LENGTH = 24 * HOUR_LENGTH;
	public static final int WEEK_LENGTH = 7 * DAY_LENGTH;

	public static final int ONLY_SEND_TYPE = -1;
	public static final int INTERVAL_SECOND_TYPE = 0;
	public static final int INTERVAL_MINUTE_TYPE = 1;
	public static final int INTERVAL_HOUR_TYPE = 2;
	public static final int INTERVAL_DAY_TYPE = 3;
	public static final int INTERVAL_WEEK_TYPE = 4;
	public static final int INTERVAL_MONTH_TYPE = 5;
	public static final int INTERVAL_QUARTER_TYPE = 6;
	public static final int INTERVAL_YEAR_TYPE = 7;

	/**
	 * 执行构造定时发送任务触发器
	 * 
	 * @param name
	 * @param group
	 * @param jobName
	 * @param beginDate
	 * @param endDate
	 * @param intervalType
	 * @param interval
	 * @return
	 */
	public SimpleTrigger getSimpleTrigger(String name, String group,
			String jobName, Date beginDate, Date endDate, int intervalType,
			int interval) {
		SimpleTrigger trigger = new SimpleTrigger(name, group);
		long timeLength = 0;
		int times = 0;
		switch (intervalType) {
		case INTERVAL_YEAR_TYPE:
			break;
		case INTERVAL_QUARTER_TYPE:
			break;
		case INTERVAL_MONTH_TYPE:
			break;
		case INTERVAL_WEEK_TYPE:
			timeLength = WEEK_LENGTH * interval;
			break;
		case INTERVAL_DAY_TYPE:
			timeLength = DAY_LENGTH * interval;
			break;
		case INTERVAL_HOUR_TYPE:
			timeLength = HOUR_LENGTH * interval;
			break;
		case INTERVAL_MINUTE_TYPE:
			timeLength = MINUTE_LENGTH * interval;
			break;
		case INTERVAL_SECOND_TYPE:
			timeLength = SECOND_LENGTH * interval;
			break;
		case ONLY_SEND_TYPE:
		default:
			timeLength = 0;
		}
		trigger.setJobName(jobName);
		trigger.setJobGroup(group);
		if (beginDate != null) {
			trigger.setStartTime(beginDate);
			logger.info("trigger first fire time:" + trigger.getStartTime());
		}
		if (endDate != null) {
			trigger.setEndTime(endDate);
			logger.info("trigger last fire time:" + trigger.getEndTime());
		}
		if (ONLY_SEND_TYPE != intervalType) {
			times = (int) ((endDate.getTime() - beginDate.getTime()) / timeLength);
			times += 1;
			trigger.setRepeatCount(times);
			trigger.setRepeatInterval(timeLength);
			logger.info("trigger repeat count:" + trigger.getRepeatCount());
			logger.info("trigger repeat interval:"
					+ trigger.getRepeatInterval());
		}
		return trigger;
	}

	/**
	 * 执行构造定时发送任务触发器
	 * 
	 * @param name
	 * @param group
	 * @param expression
	 * @return
	 * @throws Exception
	 */
	public CronTrigger getCronTrigger(String name, String group,
			String jobName, String expression) throws Exception {
		CronTrigger trigger = new CronTrigger(name, group);
		logger.info("trigger cron expression:" + expression);
		CronExpression cronExpression = new CronExpression(expression);
		trigger.setCronExpression(cronExpression);
		trigger.setJobName(jobName);
		return trigger;
	}

	/**
	 * 执行构造定时发送任务触发器
	 * 
	 * @param name
	 * @param group
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public CronTrigger getCronTrigger(String name, String group,
			String jobName, String[] date) throws Exception {
		CronTrigger trigger = new CronTrigger(name, group);
		String expression = "ss mm hh DD MM WW YY";
		if (date != null) {
			expression = getCronExpressionString(date, expression);
			logger.info("trigger cron expression:" + expression);
			CronExpression cronExpression = new CronExpression(expression);
			trigger.setCronExpression(cronExpression);
		}
		trigger.setJobName(jobName);
		return trigger;
	}

	public static String getCronExpressionString(String[] date, String expression) {
		if (date.length >= 1 && date[0] != null && !date[0].equals("")) {
			expression = expression.replaceAll("ss", date[0]);
		} else {
			expression = expression.replaceAll("ss", "*");
		}
		if (date.length >= 2 && date[1] != null && !date[1].equals("")) {
			expression = expression.replaceAll("mm", date[1]);
		} else {
			expression = expression.replaceAll("mm", "*");
		}
		if (date.length >= 3 && date[2] != null && !date[2].equals("")) {
			expression = expression.replaceAll("hh", date[2]);
		} else {
			expression = expression.replaceAll("hh", "*");
		}
		if (date.length >= 4 && date[3] != null && !date[3].equals("")) {
			expression = expression.replaceAll("DD", date[3]);
		} else {
			if (date.length >= 6 && date[5] != null && !date[5].equals("")) {
				expression = expression.replaceAll("DD", "?");
			} else {
				expression = expression.replaceAll("DD", "*");
			}
		}
		if (date.length >= 5 && date[4] != null && !date[4].equals("")) {
			expression = expression.replaceAll("MM", date[4]);
		} else {
			expression = expression.replaceAll("MM", "*");
		}
		if (date.length >= 6 && date[5] != null && !date[5].equals("")) {
			expression = expression.replaceAll("WW", date[5]);
		} else {
			expression = expression.replaceAll("WW", "?");
		}
		if (date.length >= 7 && date[6] != null && !date[6].equals("")) {
			expression = expression.replaceAll("YY", date[6]);
		} else {
			expression = expression.replaceAll("YY", "*");
		}
		return expression;
	}

	public int getIntervalType() {
		return intervalType;
	}

	public void setIntervalType(int intervalType) {
		this.intervalType = intervalType;
	}

	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}
}
