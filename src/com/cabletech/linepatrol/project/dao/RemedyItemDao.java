package com.cabletech.linepatrol.project.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.project.domain.ProjectRemedyItem;

@Repository
public class RemedyItemDao extends HibernateDao<ProjectRemedyItem, Integer> {
	private static Logger logger = Logger.getLogger(RemedyItemDao.class
			.getName());

	/**
	 * ����������Ŀ
	 * 
	 * @param bean
	 * @return
	 */
	public boolean addItem(ProjectRemedyItem item) {
		boolean flag = true;
		super.initObject(item);
		super.getSession().save(item);
		return flag;
	}

	/**
	 * ����id��ѯ������
	 * 
	 * @param id
	 * @return
	 */
	public ProjectRemedyItem getItemById(String id) {
		ProjectRemedyItem item = super.get(new Integer(id));
		super.initObject(item);
		return item;
	}

	/**
	 * �޸�������Ŀ
	 * 
	 * @param bean
	 * @return
	 */
	public boolean editItem(ProjectRemedyItem item) {
		boolean flag = true;
		super.initObject(item);
		super.getSession().saveOrUpdate(item);
		return flag;
	}

	/**
	 * ����idɾ��������Ŀ,ͬʱɾ���������
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteItem(String id) {
		boolean flag = true;
		super.delete(new Integer(id));
		return flag;
	}

	/**
	 * ִ�и��ݲ�ѯ������ȡ�������б���Ϣ
	 * 
	 * @param condition
	 *            String ��ѯ����
	 * @return List �������б���Ϣ
	 */
	public List queryList(String condition) {
		// TODO Auto-generated method stub
		String sql = "select distinct t.id,t.itemname,t.remark,r.regionname, ";
		sql += " ( ";
		sql += " select count(*) from lp_remedyitem_type lrt ";
		sql += " where lrt.remedyitemid=t.id  and lrt.state='1'";
		sql += " ) as typenum ";
		sql += " from LP_REMEDYITEM t,region r,contractorinfo c ";
		sql += " where t.regionid=r.regionid ";
		sql += " and r.regionid=c.regionid and t.state='1' ";
		sql = sql + condition;
		logger.info("Execute sql:" + sql);
		List list = super.getJdbcTemplate().queryForBeans(sql);
		return list;
	}

}
