package com.cabletech.linepatrol.maintenance.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.maintenance.module.TestConnectorWaste;
import com.cabletech.linepatrol.maintenance.module.TestData;
import com.cabletech.linepatrol.maintenance.module.TestExceptionEvent;


@Repository
public class TestExceptionEventDAO extends HibernateDao<TestExceptionEvent, String> {

	public void saveList(List<TestExceptionEvent> list,String anaylseId){
		if(list!=null && list.size()>0){
			for(TestExceptionEvent e : list){
				if(e!=null){
					String station = e.getEventStation();
					if(station!=null && !"".equals(station)){
						e.setAnaylseId(anaylseId);
						e.setId(null);
						save(e);
					}
				}
			}
		}
	}
	public void deletEventByAnaylseId(String anaylseId){
		String sql = " delete LP_TEST_EXCEPTIONEVENT t where t.ANAYLSE_ID='"+anaylseId+"'";
		this.getJdbcTemplate().execute(sql);
	}

	public List<TestExceptionEvent> getExceptionEventByAnaylseId(String anaylseId){
		String hql=" from TestExceptionEvent c where c.anaylseId=?";
		List<TestExceptionEvent> list = find(hql,anaylseId);
		return list;
	}

}