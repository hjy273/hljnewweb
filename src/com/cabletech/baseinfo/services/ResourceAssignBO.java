package com.cabletech.baseinfo.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;

import com.cabletech.baseinfo.dao.LineDAOImpl;
import com.cabletech.baseinfo.dao.SublineDAOImpl;
import com.cabletech.baseinfo.dao.TerminalDAOImpl;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.util.StrReformSplit;

/**
 * ��Դ���·��䣬���ڰ���Ѳ���߶Ρ��豸
 * 
 * @author Administrator
 * 
 */
public class ResourceAssignBO {
	private SublineDAOImpl sublineDao = new SublineDAOImpl();
	private TerminalDAOImpl terminalDao = new TerminalDAOImpl();
	private LineDAOImpl lineDao = new LineDAOImpl();
	private static Logger logger = Logger.getLogger("ResourceAssignBO");

	/**
	 * �߶�����Ȩ�����·��䡣
	 * 
	 * @param sublineList
	 *            �����߶�id������
	 * @param toObject
	 *            �߶�������,ָѲ����
	 */
	public boolean assignSubline(String sublineStr, String toObject) {
		// 1��sublineList�û����Ÿ���
		// String sublineStr = StrReformSplit.compartStr(sublineList, ",");
		// 2��create sql
		String sql = "update sublineinfo set PATROLID='" + toObject
				+ "' where sublineid in (" + sublineStr + ")";
		logger.info("��" + sublineStr + " ����� " + toObject);
		logger.info("update sql : " + sql);
		// 3������
		return sublineDao.updateSublineInfo(sql);
		// return true;
	}

	/**
	 * �豸����Ȩ���䡣���豸�������ά��λ
	 * 
	 * @param terminalStr
	 *            �豸�б�
	 * @param toObject
	 *            Ŀ�����ָ��ά��λ
	 */
	public boolean assignTerminal(String terminalStr, String toObject) {
		// 1��terminalList �ö��ŷָ�
		// String terminalStr = StrReformSplit.compartStr(terminalList, ",");
		// 2��create sql
		String sql = "update TERMINALINFO set CONTRACTORID = '" + toObject
				+ "',OWNERID='' where TERMINALID in ('" + terminalStr + "')";
		// 3������
		logger.info("update sql : " + sql);
		return terminalDao.updateTerminal(sql);
		// return true;
	}

	/**
	 * ������·id���ָ���߶���Ϣ��id����·����
	 * 
	 * @param lineid
	 *            ��·id
	 * @return
	 */
	public List getSubline(String lineid) {
		String sql = "select sublineid,sublinename from sublineinfo where lineid="
				+ lineid + " order by sublinename";

		return sublineDao.querySublineInfo(sql);
	}

	/**
	 * ���ݴ�ά��λ��Ϣ������ά����·
	 * 
	 * @param contractor ��ά��λid
	 * @return
	 */
	public List getLine(String contractor) {
		String sql = "select distinct l.lineid,l.linename from lineinfo l,sublineinfo s ,patrolmaninfo p "
				+ "where l.lineid= s.lineid and p.patrolid= s.patrolid and p.parentid='"
				+ contractor + "' order by l.linename";
		return lineDao.getLine(sql);
	}

	/**
	 * ���ݴ�ά��λid����豸��Ϣ��
	 * 
	 * @param contractorid ��ά��λid
	 * @return
	 */
	public List getTerminal(String contractorid) {
		String sql = "select terminalid,simnumber,deviceid from terminalinfo where CONTRACTORID='"
				+ contractorid + "' and state is null order by simnumber";
		return terminalDao.queryTerminal(sql);
	}

	/**
	 * ��ý�Ҫ���·����ָ�����߶�
	 * 
	 * @param sublineStr �Զ��ŷָ���߶�id�ַ���
	 * @return
	 */
	public List getSpecifySubline(String sublineStr) {
		// String sublineStr = StrReformSplit.compartStr(sublineList, ",");
		if (sublineStr == null && "".equals(sublineStr)) {
			sublineStr = "''";
		}
		String sql = "select sl.sublinename,l.linename,d.name linetype,p.patrolname "
				+ " from sublineinfo sl,lineinfo l,patrolmaninfo p ,lineclassdic d "
				+ " where l.lineid=sl.lineid and p.patrolid=sl.patrolid and d.code=l.linetype"
				+ " and sl.sublineid in("
				+ sublineStr
				+ ")"
				+ " order by l.linename,sl.sublinename";
		logger.info("sql" + sql);
		return sublineDao.querySublineInfo(sql);
	}

	/**
	 * ��ý�Ҫ���·����ָ�����豸
	 * 
	 * @param terminalStr �Զ��ŷָ���豸id�ַ���
	 * @return 
	 */
	public List getSpecifyTerminal(String terminalStr) {
		// String terminalStr = StrReformSplit.compartStr(terminalList, ",");
		if (terminalStr == null || "".equals(terminalStr)) {
			terminalStr = "''";
		}
		terminalStr = "'" + terminalStr.replaceAll(",", "','") + "'";
		String sql = "select t.terminalID,t.terminalmodel,t.simnumber,p.patrolname,t.deviceid "
				+ "from terminalinfo t ,patrolmaninfo p "
				+ "where t.ownerid =p.patrolid"
				+ " and t.terminalID in ("
				+ terminalStr + ") order by p.patrolname ";
		logger.info("sql " + sql);
		return sublineDao.querySublineInfo(sql);
	}

	/**
	 * ���select�б���е����ݣ�������ʾ��ά��λ����ָ����·�µ��߶Ρ�
	 * @param lineid
	 *            ��·id
	 * @param contractorid
	 *            ��ά��λid
	 * @return 
	 */
	public String getSelectOptions(String lineid, String contractorid) {
		List list = getSubline4line(lineid, contractorid);
		int size = list.size();
		String options = "<select name=\"original\" style=\"width: 200px\" size=\"15\" id=\"original\" multiple=\"multiple\" >";
		for (int i = 0; i < size; i++) {
			BasicDynaBean bean = (BasicDynaBean) list.get(i);
			options += "<option value=\"" + bean.get("sublineid") + "\">"
					+ bean.get("sublinename") + "</option>\n";
		}
		options += "</select>";
		return options;
	}

	/**
	 * ��ô�ά��λ����ָ����·�µ��߶�
	 * 
	 * @param lineid
	 *            ��·id
	 * @param contractorid
	 *            ��ά��λid
	 * @return
	 */
	private List getSubline4line(String lineid, String contractorid) {
		String sql = "select s.sublineid,s.sublinename "
				+ "from sublineinfo s ,patrolmaninfo p "
				+ "where  p.patrolid= s.patrolid and p.parentid='"
				+ contractorid + "' and s.lineid='" + lineid + "'";
		logger.info("SQL :" + sql);
		return sublineDao.querySublineInfo(sql);
	}

}
