package com.cabletech.linepatrol.project.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.project.domain.ProjectRemedyApplyItem;

@Repository
public class RemedyApplyItemDao extends HibernateDao<ProjectRemedyApplyItem, String> {
	/**
	 * ִ���ж��������붨�����б��ж������Ƿ����
	 * 
	 * @param itemList
	 *            List �������붨�����б�
	 * @return boolean �������붨�����б��ж������Ƿ����
	 * @throws Exception
	 */
	public boolean judgeExistItem(List itemList) {
		String baseSql = "select id from LP_REMEDYITEM where state='1' ";
		String sql = "";
		List list;
		for (int i = 0; itemList != null && i < itemList.size(); i++) {
			sql = baseSql + " and id='" + itemList.get(i) + "' ";
			logger.info("Execute sql:" + sql);
			list = super.getJdbcTemplate().queryForBeans(sql);
			if (list == null || list.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * ִ���ж��������붨���������б��ж����������Ƿ����
	 * 
	 * @param itemTypeList
	 *            List �������붨���������б�
	 * @return boolean �������붨���������б��ж����������Ƿ����
	 * @throws Exception
	 */
	public boolean judgeExistItemType(List itemTypeList) {
		String baseSql = "select id from LP_REMEDYITEM_TYPE where state='1' ";
		String sql = "";
		List list;
		for (int i = 0; itemTypeList != null && i < itemTypeList.size(); i++) {
			sql = baseSql + " and id='" + itemTypeList.get(i) + "' ";
			list = super.getJdbcTemplate().queryForBeans(sql);
			if (list == null || list.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * ִ�и��ݲ�ѯ������ȡ�������붨������Ϣ�б�
	 * 
	 * @param condition
	 *            String ��ѯ����
	 * @return List �������붨������Ϣ�б�
	 * @throws Exception
	 */
	public List queryList(String condition) {
		// TODO Auto-generated method stub
		String sql = "select id,remedyid,typename,itemname,item_unit, ";//to_char(item_id) as item_id,
		sql += " to_char(price) as price, ";//to_char(remedyitemtypeid) as remedyitemtypeid,
		sql += " to_char(remedyload) as remedyload,to_char(remedyfee) as remedyfee ";
		sql += " from LP_REMEDY_ITEM where 1=1 ";
		sql = sql + condition;
		logger.info("Execute sql:" + sql);
		List list = super.getJdbcTemplate().queryForBeans(sql);
		if (list != null) {
			return list;
		} else {
			return new ArrayList();
		}
	}

	/**
	 * ִ�и������������ű����������붨������Ϣ
	 * 
	 * @param applyId
	 *            String ����������
	 * @param itemMap
	 *            Map �������붨������ϢMap
	 * @return String �����������붨������Ϣ����
	 * @throws Exception
	 */
	public boolean saveItemList(String applyId, Map itemMap) {
		//String[] itemId = (String[]) itemMap.get("item_id");
		String[] itemName = (String[]) itemMap.get("item_name");
		//String[] itemType = (String[]) itemMap.get("item_type");
		//String[] itemTypeId = (String[]) itemMap.get("item_type_id");
		//String[] itemUnit = (String[]) itemMap.get("item_unit");
		//String[] itemWorkNumber = (String[]) itemMap.get("item_work_number");
		if (itemName != null && itemName.length > 0) {
			for (int i = 0; i < itemName.length; i++) {
				ProjectRemedyApplyItem item = new ProjectRemedyApplyItem();
				item.setApplyId(applyId);
				//item.setItemId(Integer.valueOf(itemId[i]).intValue());
				item.setItemName(itemName[i]);
				//item.setItemTypeId(Integer.valueOf(itemTypeId[i]).intValue());
				//item.setItemTypeName(itemType[i]);
				//item.setItemUnit(itemUnit[i]);
				//item.setItemWorkNumber(Float.valueOf(itemWorkNumber[i]));
				save(item);
			}
		}
		return true;
	}

	/**
	 * ִ�и�������������ɾ���������붨������Ϣ
	 * 
	 * @param applyId
	 *            String ����������
	 * @return String ɾ���������붨������Ϣ����
	 * @throws Exception
	 */
	public boolean deleteItemList(String applyId) {
		String hql = "delete from ProjectRemedyApplyItem pa where pa.applyId = ? ";
		this.batchExecute(hql, applyId);
		return true;
	}
}
