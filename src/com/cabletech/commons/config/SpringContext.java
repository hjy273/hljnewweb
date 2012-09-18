package com.cabletech.commons.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 通过该方法获得springcontext运行环境，为以前的线路巡检部分提供服务。
 * 以静态变量保存Spring ApplicationContext,可在任意代码中取出ApplicaitonContext.
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
//	 * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
//	 */
//	public void setApplicationContext(ApplicationContext applicationContext) {
//		SpringContext.applicationContext =applicationContext;
//	}
//	
//
//	/**
//	 * 取得存储在静态变量中的ApplicationContext.
//	 */
//	public static ApplicationContext getApplicationContext() {
//		checkApplicationContext();
//		return applicationContext;
//	}
//	
//	/**
//	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
//	 */
//	@SuppressWarnings("unchecked")
//	public static <T> T getBean(String name) {
//		checkApplicationContext();
//		return (T) applicationContext.getBean(name);
//	}
//
//	/**
//	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
//	 */
//	@SuppressWarnings("unchecked")
//	public static <T> T getBean(Class<T> clazz) {
//		checkApplicationContext();
//		return (T) applicationContext.getBeansOfType(clazz);
//	}
//
//	private static void checkApplicationContext() {
//		if (applicationContext == null)
//			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextUtil");
//	}

}