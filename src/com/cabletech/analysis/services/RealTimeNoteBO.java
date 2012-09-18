package com.cabletech.analysis.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cabletech.analysis.beans.NoteInfoBean;
import com.cabletech.analysis.dao.RealTimeNoteDAO;
import com.cabletech.baseinfo.domainobjects.UserInfo;

/**
 * ���ݲ�ͬ�û�����ͬ������ȡ�ö��ŷ���������Ϣ��
 */
public class RealTimeNoteBO {
	private RealTimeNoteDAO noteDao;
	private Logger logger = Logger.getLogger("RealTimeNoteBO");
	private String commonsql = " r.simid,to_char(max(r.arrivetime),'yyyy-mm-dd hh24:mi:ss') arrivetime,"
			+ " r.handlestate, count(r.id) as item,to_char(min(r.arrivetime), 'yyyy-mm-dd hh24:mi:ss') minarrivetime "
			+ " from recievemsglog r,terminalinfo tl,patrolmaninfo t,contractorinfo c ,region d"
			+ " where c.contractorid = t.parentid and t.patrolid = tl.ownerid "
			+ "and tl.simnumber = r.simid and c.regionid = d.regionid  "
			+ " and c.state is null and t.state is null and (tl.state <> '1' or tl.state is null)"
			+ " and r.arrivetime between trunc(sysdate) and sysdate ";

	// + " and r.arrivetime > to_date('2007-10-18','YYYY-MM-DD hh24:mi:ss') ";
	/**
	 * ��ʼ��noteDaoʵ����
	 */
	public RealTimeNoteBO() {
		noteDao = new RealTimeNoteDAO();
	}

	/**
	 * Ĭ�ϲ�ѯ�û�������������Ѳ��Ա���͵Ķ�����Ϣ�������ǰѡ������currentRegion��ΪNull ������ظ�����Ķ�����Ϣ��
	 * ע���÷�������ʡ���û���ѯ��
	 * 
	 * @param currentRegion
	 *            ��ǰѡ��鿴������ ������Ϊnullʱ�������û���ѯ���û��µ�����������Ϣ�����򣬲�ѯ�����������д�ά��λ����Ϣ
	 * @param user
	 *            ��ǰ��¼�û�
	 * @return List ���ز�ѯ����ָ�������µ����ж��ż�¼���
	 */
	public List getAllNoteNum(String currentRegion, UserInfo user) {
		String sql = "";
		if (currentRegion == null) {
			sql = "select d.regionname titlename,d.regionid titleid,";
			sql += commonsql;
			sql += " group by d.regionid,d.regionname,r.simid, r.handlestate ";
			sql += " order by d.regionid,r.simid,r.handlestate";
		} else {
			sql = "select c.contractorname titlename,c.contractorid titleid,";
			sql += commonsql;
			sql += " and c.regionid='" + currentRegion + "'";
			sql += " group by c.contractorname,c.contractorid,r.simid, r.handlestate ";
			sql += " order by c.contractorid,r.simid,r.handlestate";
		}
		logger.info("sql" + sql);
		// todo1 ��ִ�в�ѯ
		ResultSet rs = noteDao.queryNoteNum(sql);

		// for{
		// todo2�������������������Ѳ�졢�������ɼ�������������������Ϣ������map
		// list.add(map);
		// }
		return sortStatForName(rs);
	}

	/**
	 * �÷����ṩ���ƶ��û���ѯ��������ά��λ���ŷ��������Ĭ���ǲ鿴������Ķ�����Ϣ �����鿴��άid��Ϊnull���ѯ�ô�ά��λ�Ķ��ŷ��������
	 * ע���������ƶ��û���ѯ
	 * 
	 * @param connid
	 *            ��ǰѡ��鿴�Ĵ�ά��λid������ά��λidΪnullʱ�������û���ѯ��
	 *            �û��������������������򣬲�ѯ�ô�ά��λ������Ѳ����Ķ��������
	 * @param user
	 *            ��ǰ��¼�û���Ϣ
	 * @return List ���ر�������ά��λ���ŷ������
	 */
	public List getAreaNoteNum(String connid, UserInfo user) {
		String sql = "";
		if (connid == null) {
			sql = "select c.contractorname titlename,c.contractorid titleid,";
			sql += commonsql;
			sql += " and c.regionid='" + user.getRegionid() + "'";
			sql += " group by c.contractorname,c.contractorid,r.simid, r.handlestate ";
			sql += " order by c.contractorid,r.simid,r.handlestate";
		} else {
			sql = "select t.patrolid titleid,t.patrolname titlename,";
			sql += commonsql;
			sql += " and c.contractorid='" + connid + "'";
			sql += " group by t.patrolid, t.patrolname,r.simid, r.handlestate ";
			sql += " order by t.patrolid,r.simid,r.handlestate";
		}
		logger.info("sql" + sql);
		// todo1 ��ִ�в�ѯ
		ResultSet rs = noteDao.queryNoteNum(sql);
		// for{
		// todo2�������������������Ѳ�졢�������ɼ�������������������Ϣ������map
		// list.add(map);
		// }
		if (connid != null) {
			logger.info("connid = " + connid);
			return sortStatForCon(rs);
		} else {
			return sortStatForName(rs);
		}
	}

	/**
	 * �÷����ṩ�˴�ά�û���ѯ����Ҫ�Ĺ��ܣ�Ĭ�ϲ�ѯ����ά��λ��Ѳ��Ա�Ķ��ŷ�������� ����ѯ��Ѳ����id
	 * ��patrolid����Ϊnullʱ���鿴��Ѳ����Ķ��ŷ��������
	 * 
	 * @param patrolid
	 *            Ѳ����id ����Ѳ����idΪnullʱ�������û���ѯ���û����ڴ�ά��λ������������򣬲�ѯ��Ѳ�����������豸�Ķ������
	 * @param user
	 *            ��ǰ��¼�û���Ϣ
	 * @return List ���ر���ά��λ��õ�λ��Ѳ�������Ϣ
	 */
	public List getConNoteNum(String patrolid, UserInfo user) {
		String sql = "";
		sql = "select t.patrolid titleid,t.patrolname titlename,";
		sql += commonsql;
		if (patrolid == null) {
			sql += " and c.contractorid='" + user.getDeptID() + "'";
		} else {
			sql += "and t.patrolid = '" + patrolid + "'";
		}
		sql += " group by t.patrolid, t.patrolname,r.simid, r.handlestate ";
		sql += " order by t.patrolid,r.simid,r.handlestate";
		logger.info("sql" + sql);
		// todo1 ��ִ�в�ѯ
		ResultSet rs = noteDao.queryNoteNum(sql);
		return sortStatForCon(rs);
	}

	/**
	 * ����ÿ��sim���ŷ��͵Ķ�����������Ѳ�졢�������ɼ����¹������ȣ� ͳ�Ƶ�ÿ��sim������Ϣ�ŵ�map����list���ء�
	 * 
	 * @param rs
	 *            ��ѯ���ݿⷵ�صĽ����
	 * @return list ͳ�Ƶ�ÿ��sim������Ϣ�ŵ�map����list����
	 */
	private List sortStatForCon(ResultSet rs) {
		int intItems = 0;
		NoteInfoBean note = new NoteInfoBean();
		if (rs == null) {
			return null;
		}
		List resultList = new ArrayList();
		try {
			String prevValue = "";
			String currentValue = "";
			String prevtitleName = "";
			while (rs.next()) {
				note.setTitleId(rs.getString("titleid"));
				String handleState = rs.getString("handlestate");
				currentValue = rs.getString("simid");
				if (!currentValue.equals(prevValue) && !"".equals(prevValue)) {
					note.setTitleName(prevtitleName);
					note.setSimid(prevValue);
					resultList.add(setMap(note, ""));
					note.clear();
				}
				/* �����������Ͷ��Ÿ��� */
				intItems = rs.getInt("item");
				note = countNoteNumForType(intItems, handleState, note);
				// ȡ�����һ������ʱ��͵����һ������ʱ��
				if ("".equals(note.getLastTime())
						&& "".equals(note.getFirstTime())) {
					note.setLastTime(rs.getString("arrivetime"));
					note.setFirstTime(rs.getString("minarrivetime"));
				} else {
					if (rs.getString("arrivetime")
							.compareTo(note.getLastTime()) > 0) {
						note.setLastTime(rs.getString("arrivetime"));
					}
					if (rs.getString("minarrivetime").compareTo(
							note.getFirstTime()) < 0) {
						note.setFirstTime(rs.getString("minarrivetime"));
					}
				}
				prevValue = currentValue;
				prevtitleName = rs.getString("titlename");
			}
			note.setTitleName(prevtitleName);
			note.setSimid(prevValue);
			resultList.add(setMap(note, ""));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultList;
	}

	/**
	 * �������ƣ��������򡢴�ά��λ��ͳ�ƶ��ŷ������� ������������Ѳ�졢�������ɼ����¹������ȣ� ͳ�Ƶ�ÿ��sim������Ϣ�ŵ�map����list���ء�
	 * 
	 * @param rs
	 *            ResultSet ��ѯ�Ľ����
	 * @return list ͳ�Ƶ�ÿ��sim������Ϣ�ŵ�map����list����
	 */
	private List sortStatForName(ResultSet rs) {
		int intItems = 0;
		NoteInfoBean note = new NoteInfoBean();
		if (rs == null) {
			return null;
		}
		int k = 0;
		List resultList = new ArrayList();
		try {
			String prevValue = "";
			String currentValue = "";
			while (rs.next()) {
				k++;
				String handleState = rs.getString("handlestate");
				note.setTitleId(rs.getString("titleid"));
				currentValue = rs.getString("titlename");
				if (!currentValue.equals(prevValue) && !"".equals(prevValue)) {
					note.setTitleName(prevValue);
					resultList.add(setMap(note, null));
					note.clear();
				}
				/* �����������Ͷ��Ÿ��� */
				intItems = rs.getInt("item");
				note = countNoteNumForType(intItems, handleState, note);
				prevValue = currentValue;
			}
			note.setTitleName(prevValue);
			resultList.add(setMap(note, null));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultList;
	}

	/**
	 * �����������Ͷ��Ÿ�����Ȼ����NoteInfoBean����
	 * 
	 * @param intItems
	 *            ϵͳ���յĶ�����Ŀ��
	 * @param handleState
	 *            �豸״̬ �����ɼ���������������ƥ�估������
	 * @param note
	 *            ������Ϣ
	 * @return NoteInfoBean
	 */
	private NoteInfoBean countNoteNumForType(int intItems, String handleState,
			NoteInfoBean note) {
		if (handleState.equals("3")) { // �ɼ�
			note.setIntCollect(intItems);
		} else if (handleState.equals("20")) { // ����
			note.setIntTrouble(intItems);
		} else if (handleState.equals("6") || handleState.equals("9")
				|| handleState.equals("11")) { // ����
			note.setIntWatch(intItems);
		} else if (handleState.equals("7") || handleState.equals("8")
				|| handleState.equals("4") || handleState.equals("12")
				|| handleState.equals("10")) { // ƥ��
			note.setIntPatrol(intItems);
		} else {
			// ����
			note.setIntOther(intItems);
		}
		// ��������
		note.setIntTotal(intItems);
		return note;
	}

	/**
	 * ��noteinfo��Ϣװ�ص�map�С�
	 * 
	 * @param note
	 *            ������Ϣ
	 * @param type
	 *            Ϊnullʱ��ʾ ����Ҫsimid��firsttime��lasttime��Ϣ��������Ҫ��
	 * @return Map ����ָ�����ƵĶ��ŷ���.
	 */
	private Map setMap(NoteInfoBean note, String type) {
		Map map = new HashMap();

		if (type != null) {
			map.put("simid", note.getSimid());
			map.put("firsttime", note.getFirstTime());
			map.put("lasttime", note.getLastTime());
		}
		map.put("titlename", note.getTitleName());
		map.put("total", new Integer(note.getIntTotal()));
		map.put("patrol", new Integer(note.getIntPatrol()));
		map.put("collect", new Integer(note.getIntCollect()));
		map.put("watch", new Integer(note.getIntWatch()));
		map.put("trouble", new Integer(note.getIntWatch()));
		map.put("other", new Integer(note.getIntOther()));
		return map;
	}

}
