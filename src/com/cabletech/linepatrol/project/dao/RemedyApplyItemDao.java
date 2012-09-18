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
	 * 执行判断修缮申请定额项列表中定额项是否存在
	 * 
	 * @param itemList
	 *            List 修缮申请定额项列表
	 * @return boolean 修缮申请定额项列表中定额项是否存在
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
	 * 执行判断修缮申请定额项类型列表中定额项类型是否存在
	 * 
	 * @param itemTypeList
	 *            List 修缮申请定额项类型列表
	 * @return boolean 修缮申请定额项类型列表中定额项类型是否存在
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
	 * 执行根据查询条件获取修缮申请定额项信息列表
	 * 
	 * @param condition
	 *            String 查询条件
	 * @return List 修缮申请定额项信息列表
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
	 * 执行根据修缮申请编号保存修缮申请定额项信息
	 * 
	 * @param applyId
	 *            String 修缮申请编号
	 * @param itemMap
	 *            Map 修缮申请定额项信息Map
	 * @return String 保存修缮申请定额项信息编码
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
	 * 执行根据修缮申请编号删除修缮申请定额项信息
	 * 
	 * @param applyId
	 *            String 修缮申请编号
	 * @return String 删除修缮申请定额项信息编码
	 * @throws Exception
	 */
	public boolean deleteItemList(String applyId) {
		String hql = "delete from ProjectRemedyApplyItem pa where pa.applyId = ? ";
		this.batchExecute(hql, applyId);
		return true;
	}
}
