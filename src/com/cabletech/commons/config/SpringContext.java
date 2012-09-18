package com.cabletech.commons.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ͨ���÷������springcontext���л�����Ϊ��ǰ����·Ѳ�첿���ṩ����
 * �Ծ�̬��������Spring ApplicationContext,�������������ȡ��ApplicaitonContext.
 * @author Administrator
 *
 */
public class SpringContext {
	private static ApplicationContext ctx = null;

	public static ApplicationContext getApplicationContext() {
		if (ctx == null) {
			ctx = new ClassPathXmlApplicationContext("classpath*:applicationContext.xml");
		}
		return ctx;
	}
//	implements ApplicationContextAware
//	private static ApplicationContext applicationContext;
//
//	/**
//	 * ʵ��ApplicationContextAware�ӿڵ�contextע�뺯��, ������뾲̬����.
//	 */
//	public void setApplicationContext(ApplicationContext applicationContext) {
//		SpringContext.applicationContext =applicationContext;
//	}
//	
//
//	/**
//	 * ȡ�ô洢�ھ�̬�����е�ApplicationContext.
//	 */
//	public static ApplicationContext getApplicationContext() {
//		checkApplicationContext();
//		return applicationContext;
//	}
//	
//	/**
//	 * �Ӿ�̬����ApplicationContext��ȡ��Bean, �Զ�ת��Ϊ����ֵ���������.
//	 */
//	@SuppressWarnings("unchecked")
//	public static <T> T getBean(String name) {
//		checkApplicationContext();
//		return (T) applicationContext.getBean(name);
//	}
//
//	/**
//	 * �Ӿ�̬����ApplicationContext��ȡ��Bean, �Զ�ת��Ϊ����ֵ���������.
//	 */
//	@SuppressWarnings("unchecked")
//	public static <T> T getBean(Class<T> clazz) {
//		checkApplicationContext();
//		return (T) applicationContext.getBeansOfType(clazz);
//	}
//
//	private static void checkApplicationContext() {
//		if (applicationContext == null)
//			throw new IllegalStateException("applicaitonContextδע��,����applicationContext.xml�ж���SpringContextUtil");
//	}

}