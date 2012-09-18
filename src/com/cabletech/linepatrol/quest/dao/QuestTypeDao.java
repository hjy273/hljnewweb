package com.cabletech.linepatrol.quest.dao;

import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.quest.module.QuestType;

@Repository
public class QuestTypeDao extends HibernateDao<QuestType, String> {
	/**
	 * ������е��ļ�����
	 * @return���ʾ������б�
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
	 * �жϸ��ʾ������Ƿ����
	 * @param typeName���ʾ���������
	 * @param typeId���ʾ�����ID
	 * @return�������ڷ���"exists"�����򷵻ؿ�
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
	 * ������е��ʾ�����
	 * @return���ʾ������б�
	 */
	public List getAllQuestType(){
		String sql = "from QuestType type ";
		List<QuestType> list = getHibernateTemplate().find(sql);
		return list;
	}
}
