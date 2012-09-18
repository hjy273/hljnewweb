package com.cabletech.linepatrol.quest.dao;

import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.quest.module.QuestGuidelineSort;

@Repository
public class QuestGuidelineSortDao extends HibernateDao<QuestGuidelineSort, String> {
	/**
	 * ��ָ�����ID���ָ�����
	 * @param classId��ָ�����ID
	 * @return��ָ�����ʵ��
	 */
	public List getSortListByClassId(String classId){
		String hql = "from QuestGuidelineSort sort where sort.classId=?";
		List list = this.getHibernateTemplate().find(hql, classId);
		return list;
	}
	
	/**
	 * ������е�ָ�����
	 * @return��ָ������б�
	 */
	public List questSortManagerList(){
		int i = 1;
		String sql ="select cl.class class_name,sort.id,'' as row_num,sort.sort sort_name " +
				"from quest_guideline_sort sort,quest_guideline_class cl where sort.class_id=cl.id";
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
	 * �ж�ָ������Ƿ����
	 * @param classId��ָ�����ID
	 * @param sortName��ָ���������
	 * @param sortId��ָ�����ID
	 * @return�������ڷ���"exists"�����򷵻ؿ�
	 */
	public String judgeSortExists(String classId, String sortName, String sortId){
		String flag = "";
		String sql = "select 1 from quest_guideline_sort t where t.class_id=? and t.sort=?";
		if(sortId != null && !"".equals(sortId)){
			sql += " and t.id<>'" + sortId.trim() + "'";
		}
		List list = getJdbcTemplate().queryForBeans(sql, classId.trim(), sortName.trim());
		if(list != null && list.size() > 0){
			flag = "exists";
		}
		return flag;
	}
	
	/**
	 * ������е�ָ�����
	 * @return��ָ������б�
	 */
	public List getAllQuestSort(){
		String sql = "from QuestGuidelineSort sort ";
		List<QuestGuidelineSort> list = getHibernateTemplate().find(sql);
		return list;
	}
}
