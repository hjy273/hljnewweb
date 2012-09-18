package com.cabletech.linepatrol.quest.dao;

import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.quest.module.QuestGuidelineClass;

@Repository
public class QuestGuidelineClassDao extends HibernateDao<QuestGuidelineClass, String> {
	/**
	 * ���ʾ����Ͳ�ѯָ�����
	 * @param queryType���ʾ�����
	 * @return
	 */
	public List getClassByTypeId(String queryType){
		String hql = "from QuestGuidelineClass qclass where qclass.typeId=?";
		List list = this.getHibernateTemplate().find(hql, queryType);
		return list;
	}
	
	/**
	 * ��ѯ���е�ָ�����
	 * @return
	 */
	public List questClassManagerList(){
		int i = 1;
		String sql ="select type.type type_name,cl.id,'' as row_num,cl.class class_name " +
				"from quest_type type,quest_guideline_class cl where type.id=cl.type_id";
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
	 * �жϸ�ָ����������Ƿ����
	 * @param typeId���ʾ�����ID
	 * @param className��ָ���������
	 * @param classId��ָ�����ID
	 * @return�������ڷ���exists�����򷵻ؿ�
	 */
	public String judgeClassExists(String typeId, String className, String classId){
		String flag = "";
		String sql = "select 1 from quest_guideline_class t where t.type_id=? and t.class=?";
		if(classId != null && !"".equals(classId)){
			sql += " and t.id<>'" + classId.trim() + "'";
		}
		List list = getJdbcTemplate().queryForBeans(sql, typeId.trim(), className.trim());
		if(list != null && list.size() > 0){
			flag = "exists";
		}
		return flag;
	}
	
	/**
	 * ��ѯ���е�ָ�����
	 * @return
	 */
	public List getAllQuestClass(){
		String sql = "from QuestGuidelineClass cl ";
		List<QuestGuidelineClass> list = getHibernateTemplate().find(sql);
		return list;
	}
}
