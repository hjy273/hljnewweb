package com.cabletech.linepatrol.quest.dao;

import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.quest.module.QuestReviewObject;

@Repository
public class QuestReviewObjectDao extends HibernateDao<QuestReviewObject, String> {
	/**
	 * ������еĲ�������
	 * @return�����������б�
	 */
	public List getAllCompanyList(){
		String hql = "from QuestReviewObject";
		List list = getHibernateTemplate().find(hql);
		return list;
	}
	
	/**
	 * �ɲ�������ID��ò�����������
	 * @param companyIds����������ID����
	 * @return��������������
	 */
	public String getCompanyNames(String[] companyIds){
		String companyNames = "";
		if(companyIds != null && companyIds.length > 0){
			for (int i = 0; i < companyIds.length; i++) {
				QuestReviewObject questReviewObject = this.findByUnique("id", companyIds[i]);
				companyNames += questReviewObject.getObject() + ",";
			}
		}
		if(!"".equals(companyNames)){
			companyNames = companyNames.substring(0, companyNames.length() - 1);
		}
		return companyNames;
	}
	
	/**
	 * �ɲ�������ID��ò�����������
	 * @param comIds����������ID
	 * @return��������������
	 */
	public String getComNamesByComIds(String comIds){
		String comNames = "";
		if(comIds != null && comIds.length() > 0){
			String[] conId = comIds.split(",");
			for (int i = 0; i < conId.length; i++) {
				QuestReviewObject questReviewObject = this.findByUnique("id", conId[i]);
				comNames += questReviewObject.getObject() + ",";
			}
		}
		if(!"".equals(comNames)){
			comNames = comNames.substring(0, comNames.length() - 1);
		}
		return comNames;
	}
	
	/**
	 * ������в��������б�
	 * @return�����������б�
	 */
	public List questComManagerList(){
		int i = 1;
		String sql ="select com.id,'' as row_num,com.object com_name from quest_reviewobject com";
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
	 * �жϲ��������Ƿ����
	 * @param comName��������������
	 * @param comId����������ID
	 * @return�������ڷ���"exists"�����򷵻ؿ�
	 */
	public String judgeComExists(String comName, String comId){
		String flag = "";
		String sql = "select 1 from quest_reviewobject com where com.object=?";
		if(comId != null && !"".equals(comId)){
			sql += " and com.id<>'" + comId.trim() + "'";
		}
		List list = getJdbcTemplate().queryForBeans(sql, comName.trim());
		if(list != null && list.size() > 0){
			flag = "exists";
		}
		return flag;
	}
}
