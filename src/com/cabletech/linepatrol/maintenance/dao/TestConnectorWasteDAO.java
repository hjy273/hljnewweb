package com.cabletech.linepatrol.maintenance.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.maintenance.module.TestConnectorWaste;
import com.cabletech.linepatrol.maintenance.module.TestOtherAnalyse;


@Repository
public class TestConnectorWasteDAO extends HibernateDao<TestConnectorWaste, String> {

	public void saveList(List<TestConnectorWaste> list,String anaylseId){
		if(list!=null && list.size()>0){
			for(TestConnectorWaste waste : list){
				if(waste!=null){
					String station =  waste.getConnectorStation();
					if(station!=null && !"".equals(station)){
						waste.setId(null);
						waste.setAnaylseId(anaylseId);	
						save(waste);
					}
				}
			}
		}
	}

	public void deleteWasteByAnaylseId(String anaylseId){
		String sql = " delete LP_TEST_CONNECTORWASTE t where t.ANAYLSE_ID='"+anaylseId+"'";
		this.getJdbcTemplate().execute(sql);
	}
	
	public List<TestConnectorWaste> getConnectorWasteByAnaylseId(String anaylseId){
		String hql=" from TestConnectorWaste c where c.anaylseId=?";
		List<TestConnectorWaste> list = find(hql,anaylseId);
		return list;
	}

}