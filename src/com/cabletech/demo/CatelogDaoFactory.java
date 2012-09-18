package com.cabletech.demo;

import javax.naming.InitialContext;

public class CatelogDaoFactory {
	/**

	* �������ƶ�һ���ر��������ʵ��DAOģʽ��
	* �������ඨ������J2EE�Ĳ����������С�
	*/

	public static CatalogDAO getDAO() {

		CatalogDAO catDao = null;

		try {

			InitialContext ic = new InitialContext();
			//��̬װ��CATALOG_DAO_CLASS
			//���Զ����Լ���CATALOG_DAO_CLASS���Ӷ���������̫�����
			//��ǰ���£����ϵͳ�ľ޴�����

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
