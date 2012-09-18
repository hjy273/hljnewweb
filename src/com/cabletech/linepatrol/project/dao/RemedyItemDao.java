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
	 * 保存修缮项目
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
	 * 根据id查询修缮项
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
	 * 修改修缮项目
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
	 * 根据id删除修缮项目,同时删除其下类别
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
	 * 执行根据查询条件获取定额项列表信息
	 * 
	 * @param condition
	 *            String 查询条件
	 * @return List 定额项列表信息
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
