package com.cabletech.linepatrol.quest.dao;

import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.quest.module.QuestType;

@Repository
public class QuestTypeDao extends HibernateDao<QuestType, String> {
	/**
	 * 获得所有的文件类型
	 * @return：问卷类型列表
	 */
	public List questTypeManagerList(){
		int i = 1;
		String sql ="select type.id,'' as row_num,type.type type_name from quest_type type";
		List<BasicDynaBean> list = getJdbcTemplate().queryForBeans(sql);
		if(list != null && list.size() > 0){
			for(BasicDynaBean bean:list){
				bean.set("row_num", Integer.toString(i));
				i++;
			}
		}
		return list;
	}
	
	/**
	 * 判断该问卷类型是否存在
	 * @param typeName：问卷类型名称
	 * @param typeId：问卷类型ID
	 * @return：若存在返回"exists"，否则返回空
	 */
	public String judgeTypeExists(String typeName, String typeId){
		String flag = "";
		String sql = "select 1 from quest_type type where type.type=?";
		if(typeId != null && !"".equals(typeId)) {
			sql += " and type.id<>'" + typeId.trim() + "'";
		}
		List list = getJdbcTemplate().queryForBeans(sql, typeName.trim());
		if(list != null && list.size() > 0){
			flag = "exists";
		}
		return flag;
	}
	
	/**
	 * 获得所有的问卷类型
	 * @return：问卷类型列表
	 */
	public List getAllQuestType(){
		String sql = "from QuestType type ";
		List<QuestType> list = getHibernateTemplate().find(sql);
		return list;
	}
}
