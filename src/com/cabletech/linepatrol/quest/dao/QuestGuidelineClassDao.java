package com.cabletech.linepatrol.quest.dao;

import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.quest.module.QuestGuidelineClass;

@Repository
public class QuestGuidelineClassDao extends HibernateDao<QuestGuidelineClass, String> {
	/**
	 * 由问卷类型查询指标类别
	 * @param queryType：问卷类型
	 * @return
	 */
	public List getClassByTypeId(String queryType){
		String hql = "from QuestGuidelineClass qclass where qclass.typeId=?";
		List list = this.getHibernateTemplate().find(hql, queryType);
		return list;
	}
	
	/**
	 * 查询所有的指标类别
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
	 * 判断该指标类别名称是否存在
	 * @param typeId：问卷类型ID
	 * @param className：指标类别名称
	 * @param classId：指标类别ID
	 * @return：若存在返回exists，否则返回空
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
	 * 查询所有的指标类别
	 * @return
	 */
	public List getAllQuestClass(){
		String sql = "from QuestGuidelineClass cl ";
		List<QuestGuidelineClass> list = getHibernateTemplate().find(sql);
		return list;
	}
}
