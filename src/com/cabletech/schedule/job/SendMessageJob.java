package com.cabletech.schedule.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cabletech.commons.util.StringUtils;
import com.cabletech.sysmanage.services.SendMessageBO;

import cabletech.sm.rmi.RmiSmProxyService;

public class SendMessageJob extends QuartzJobBean {
	private Logger logger = Logger.getLogger(SendMessageJob.class);
	private String content;
	private String sim;
	private RmiSmProxyService rmiSmProxyService;
	private SendMessageBO sendMessageBO;

	/**
	 * �����ֻ����Ͷ���
	 * 
	 * @param content
	 *            ��������
	 * @param mobiles
	 *            ���ն����ֻ�����,ʹ�ö��ŷָ�
	 */
	public void sendMessage(String content, String mobiles) {
		rmiSmProxyService.simpleSend(mobiles, content, null, null, true);
//		List<String> mobileList = new ArrayList<String>();
//		StringTokenizer st = new StringTokenizer(mobiles, ",");
//		while (st.hasMoreTokens()) {
//			mobileList.add(st.nextToken());
//		}
//		sendMessage(content, mobileList);
	}

	/**
	 * �����ֻ����Ͷ���
	 * 
	 * @param content
	 *            ����������
	 * @param mobileList
	 *            �����ն����ֻ�����
	 */
//	public void sendMessage(String content, List<String> mobileList) {
//	String mobiles = StringUtils.list2StringComma(mobileList);
//		rmiSmProxyService.simpleSend(mobiles, content, null, null, true);
//		for (String mobile : mobileList) {
//			if (isNumeric(mobile)) {
//				rmiSmProxyService.simpleSend(mobile, content, null, null, true);
//			}
//		}
//	}

	/**
	 * �ж������ַ����Ƿ�Ϊ����
	 * 
	 * @param str
	 * @return
	 */
	public boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * ִ�ж�ʱ����������Ϣ
	 * 
	 * @param jobExecutionContext
	 */
	@Override
	public void executeInternal(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		Trigger trigger = jobExecutionContext.getTrigger();
		logger.info(content + "===================content");
		logger.info(sim + "========================sim");
		logger.info(trigger.getName() + " ");
		logger.info(new Date() + " ");
		logger.info(trigger.getStartTime() + " ");
		logger.info(trigger.getEndTime() + " ");
		logger.info(trigger.mayFireAgain() + " ");
		if (!trigger.mayFireAgain()) {
			sendMessageBO.updateSendSmFinishState(trigger.getName());
		}
		sendMessage(content, sim);
		logger.info("���ŷ��ͳɹ���");
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSim() {
		return sim;
	}

	public void setSim(String sim) {
		this.sim = sim;
	}

	public RmiSmProxyService getRmiSmProxyService() {
		return rmiSmProxyService;
	}

	public void setRmiSmProxyService(RmiSmProxyService rmiSmProxyService) {
		this.rmiSmProxyService = rmiSmProxyService;
	}

	public SendMessageBO getSendMessageBO() {
		return sendMessageBO;
	}

	public void setSendMessageBO(SendMessageBO sendMessageBO) {
		this.sendMessageBO = sendMessageBO;
	}

}
