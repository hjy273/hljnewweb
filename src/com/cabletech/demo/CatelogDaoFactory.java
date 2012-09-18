package com.cabletech.demo;

import javax.naming.InitialContext;

public class CatelogDaoFactory {
	/**

	* 本方法制定一个特别的子类来实现DAO模式。
	* 具体子类定义是在J2EE的部署描述器中。
	*/

	public static CatalogDAO getDAO() {

		CatalogDAO catDao = null;

		try {

			InitialContext ic = new InitialContext();
			//动态装入CATALOG_DAO_CLASS
			//可以定义自己的CATALOG_DAO_CLASS，从而在无需变更太多代码
			//的前提下，完成系统的巨大变更。

			String className = (String) ic.lookup(JNDINames.CATALOG_DAO_CLASS);

			catDao = (CatalogDAO) Class.forName(className).newInstance();

		} catch (Exception se) {
		}

		return catDao;

	}

}

class CatalogDAO {

}

class JNDINames {
	public static String CATALOG_DAO_CLASS = "";
}
