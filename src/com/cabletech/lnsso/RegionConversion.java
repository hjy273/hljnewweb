package com.cabletech.lnsso;

import java.util.List;

import com.cabletech.baseinfo.dao.RegionDAOImpl;
import com.cabletech.baseinfo.domainobjects.Region;

public class RegionConversion {
	RegionDAOImpl dao = new RegionDAOImpl();

	public String getRegionID(String city) throws Exception {
		List regions = dao.getHibernateTemplate().find("From Region where regionName like '%" + city + "%'");
		if (regions != null && regions.size() > 0) {
			Region region = (Region) regions.get(0);
			return region.getRegionID();
		} else
			return "210000";
	}
}
