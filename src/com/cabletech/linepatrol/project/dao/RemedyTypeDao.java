package com.cabletech.linepatrol.project.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.project.domain.ProjectRemedyType;

@Repository
public class RemedyTypeDao extends HibernateDao<ProjectRemedyType, Integer> {
	private static Logger logger = Logger.getLogger(RemedyTypeDao.class
			.getName());

	/**
	 * �����������
	 * 
	 * @param bean
	 * @return
	 */
	public boolean addType(ProjectRemedyType type) {
		boolean flag = true;
		super.initObject(type);
		super.getSession().save(type);
		return flag;
	}

	/**
	 * ����id��ѯ�������
	 * 
	 * @param id
	 * @return
	 */
	public ProjectRemedyType getTypeById(String id) {
		ProjectRemedyType type = super.get(new Integer(id));
		super.initObject(type);
		return type;
	}

	/**
	 * �޸�������Ŀ
	 * 
	 * @param bean
	 * @return
	 */
	public boolean editType(ProjectRemedyType type) {
		boolean flag = true;
		super.initObject(type);
		super.getSession().saveOrUpdate(type);
		return flag;
	}

	/**
	 * ����idɾ���������
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteType(String id) {
		boolean flag = true;
		super.delete(new Integer(id));
		return flag;
	}

	/**
	 * ִ�и��ݲ�ѯ������ȡ�����������б���Ϣ
	 * 
	 * @param condition
	 *            String ��ѯ����
	 * @return List �����������б���Ϣ
	 */
	public List queryList(String condition) {
		String sql = "select distinct t.id,t.remedyitemid,t.typename,to_char(t.price) as price,t.unit,tr.itemname ";
		sql += " from LP_REMEDYITEM_TYPE t,LP_REMEDYITEM tr,region r,contractorinfo c ";
		sql += " where t.remedyitemid=tr.id and tr.regionid=r.regionid ";
		sql += " and r.regionid=c.regionid and t.state='1' ";
		sql = sql + condition;
		logger.info("Execute sql:" + sql);
		return super.getJdbcTemplate().queryForBeans(sql);
	}

}
