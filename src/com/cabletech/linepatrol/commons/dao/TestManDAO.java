package com.cabletech.linepatrol.commons.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.maintenance.module.TestCableData;
import com.cabletech.linepatrol.maintenance.module.TestStationData;

@Repository
public class TestManDAO extends HibernateDao<TestCableData, String> {

	public TestCableData getLineDataById(String id){
		TestCableData data = get(id);
		initObject(data);
		return data;
	}
	
	public TestStationData getStationDataById(String id){
		String hql = " from TestStationData t where t.id='"+id+"'";
		List<TestStationData> datas = this.getHibernateTemplate().find(hql);
		if(datas!=null && datas.size()>0){
			TestStationData data = datas.get(0);
			return data;
		}
		return null;
	}

	/**
	 * 得到计划测试人员
	 * @param user
	 * @return
	 */
	public List getUsers(UserInfo user,String userName){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		/*sb.append("  select u.userid, u.username  ");
		sb.append("  from userinfo u ");
		sb.append("  where u.deptid=? ");
		values.add(user.getDeptID());
		sb.append("and u.deptype=2");
		if(userName!=null && !"".equals(userName.trim())){
			sb.append(" and u.username like '%"+userName+"%'");
		}
		sb.append(" and u.state is null");*/
		sb.append(" select c.id,c.name from contractorperson c");
		sb.append(" where c.contractorid=?");
		values.add(user.getDeptID());
		if(userName!=null && !"".equals(userName.trim())){
			sb.append(" and c.name like '%"+userName+"%'");
		}
		return  this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}
}
