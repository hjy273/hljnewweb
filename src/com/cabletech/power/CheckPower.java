package com.cabletech.power;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.services.DBService;
import com.cabletech.menu.dao.MenuDaoImpl;

public class CheckPower {
	private static Logger logger = Logger.getLogger(CheckPower.class);

	public CheckPower() {
	}

	/**
	 *<p> �� �ܣ���ѯָ���û���ϵͳ��ӵ��Ȩ�޵�����ģ�顣</p>     *
	 *<p> �� �����û���ID</p>
	 *<p> ����ֵ:�û�ӵ��Ȩ��ģ���idֵ�Ͷ�Ӧ��ģ������id��ģ������֮���ÿո��������id֮���ö��Ÿ�����</p>
	 **/
	public static String getMoudules(UserInfo userinfo) {
		String strMoud = "";
		String strTemp = "";
		String userId = userinfo.getUserID();
		String result[][];
		MenuDaoImpl dao = new MenuDaoImpl();
		String type = dao.getGroupType(userinfo);
		String sql = "select distinct sonmenu.ID,sonmenu.LABLENAME "
				+ " from usergroupmaster,usergroupmoudulelist,sonmenu "
				+ " where usergroupmaster.ID=usergroupmoudulelist.USERGROUPID "
				+ " and sonmenu.ID=usergroupmoudulelist.SONMENUID " + " and (INSTR(SONMENU.POWER,'" + type + "') !=0) "
				+ " and usergroupmaster.ID in (select distinct usergroupid from usergourpuserlist " + " where userid='"
				+ userId + "')" + " order by sonmenu.ID";
		logger.info("SQL:" + sql);
		try {
			DBService dbservice = new DBService();
			result = dbservice.queryArray(sql, strTemp);
			for (String[] element : result) {
				strMoud = strMoud + element[0] + "  " + element[1] + ",";
			}
			return strMoud.trim();
		} catch (Exception e) {
			logger.error("ȡ���û�Ȩ�޴���:" + e.getMessage());
			return " ";
		}

	}

	public static String getMoudules(String userType, List ugroups) {
		String strMoud = "";
		String strTemp = "";
		String ugs_str = "";
		for (int i = 0; i < ugroups.size(); i++) {
			ugs_str += "'" + ugroups.get(0) + "',";
		}
		String result[][];
		MenuDaoImpl dao = new MenuDaoImpl();
		String sql = "select distinct sonmenu.ID,sonmenu.LABLENAME "
				+ " from usergroupmaster,usergroupmoudulelist,sonmenu "
				+ " where usergroupmaster.ID=usergroupmoudulelist.USERGROUPID "
				+ " and sonmenu.ID=usergroupmoudulelist.SONMENUID " + " and (INSTR(SONMENU.POWER,'" + userType
				+ "') !=0) " + " and usergroupmaster.ID in (" + ugs_str.substring(0, (ugs_str.length() - 1)) + ")"
				+ " order by sonmenu.ID";
		logger.info("SQL:" + sql);
		try {
			DBService dbservice = new DBService();
			result = dbservice.queryArray(sql, strTemp);
			for (String[] element : result) {
				strMoud = strMoud + element[0] + "  " + element[1] + ",";
			}
			return strMoud.trim();
		} catch (Exception e) {
			logger.error("ȡ���û�Ȩ�޴���:" + e.getMessage());
			return " ";
		}

	}

	/***
	 * <p> �� �ܣ���鵱ǰ�û���ָ����ģ���Ƿ����ָ����Ȩ�ޡ�
	 * <p> �� ����1��session�� 2�������˵�ģ��id�����Դ�sonmenu���в鿴����
	 *           3����������λ��Ȩ��ֵ��һ��涨��1����ѯ���ܣ�2�����ӹ��ܣ�3���߼���ѯ���ܣ�4���޸Ĺ��ܣ�5��ɾ������</p>
	 * <p>����ֵ��ӵ��Ȩ�޷�����ֵ��û��Ȩ�޷��ؼ�ֵ��</p>
	 * **/
	public static boolean checkPower(HttpSession session, String secMoudcode, String power) {
		String strUserPower = ""; //��ǰ�û�ӵ��Ȩ�޵�����ģ��id�����Ƶ��б�
		String strMoudcode = ""; //��Ҫ��ѯ�Ĺ���ģ��id
		int posit = 0;
		if (power.length() >= 3) {
			logger.info("��������룺Ȩ�޵ĳ��ȳ�����λ");
			return false;
		}
		if (power.length() == 1) {
			power = "0" + power;
		}

		strUserPower = (String) session.getAttribute("USERCURRENTPOWER");
		strMoudcode = secMoudcode + power;
		posit = strUserPower.indexOf(strMoudcode);
		if (posit < 0)
			return false;
		return true;
	}

	/***
	 * <p>  ��  �ܣ���鵱ǰ�û���ָ����ģ���Ƿ����ָ����Ȩ�ޡ�
	 * <p>  ��  ����1��session�� 2�������˵�ģ��id�����Դ�sonmenu���в鿴�����������˵��������λһ��涨��01����ѯ���ܣ�02�����ӹ��ܣ�03���߼���ѯ���ܣ�04���޸Ĺ��ܣ�05��ɾ������
	 * <p>  ����ֵ��ӵ��Ȩ�޷�����ֵ��û��Ȩ�޷��ؼ�ֵ��
	 * **/

	public static boolean checkPower(HttpSession session, String moudcode) {
		String strUserPower = ""; //��ǰ�û�ӵ��Ȩ�޵�����ģ��id�����Ƶ��б�
		String strMoudcode = ""; //��Ҫ��ѯ��ģ��id
		int posit = 0;

		strUserPower = (String) session.getAttribute("USERCURRENTPOWER");
		strMoudcode = moudcode;
		posit = strUserPower.indexOf(strMoudcode);
		// logger.info("POWER:" + strMoudcode);
		//logger.info("POWERPSIT:" + posit);
		if (posit < 0)
			return false;
		return true;
	}

	/***
	 * <p>  ��  �ܣ���鵱ǰ�û���ָ����ģ���Ƿ����ָ����Ȩ�ޡ�
	 * <p>  ��  ����1��session�� 2�������˵�ģ�����ƣ����Դ�sonmenu���в鿴����
	 * <p>  ����ֵ��ӵ��Ȩ�޷�����ֵ��û��Ȩ�޷��ؼ�ֵ��
	 * **/

	public static boolean checkPowerByName(HttpSession session, String moudname) {
		String strUserPower = ""; //��ǰ�û�ӵ��Ȩ�޵�����ģ��id�����Ƶ��б�
		int posit = 0;

		strUserPower = (String) session.getAttribute("USERCURRENTPOWER");
		posit = strUserPower.indexOf(moudname);
		if (posit < 0)
			return false;
		return true;
	}

}
