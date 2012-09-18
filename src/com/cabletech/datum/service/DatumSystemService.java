package com.cabletech.datum.service;

import java.util.List;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.datum.bean.DatumSystem;

public interface DatumSystemService {
	
	boolean saveDatumSystem(DatumSystem system);
	
	boolean updateDatumSystem(DatumSystem system);
	
	boolean  delDatumSystem(String id);

	List queryDatumSystem(DatumSystem system,UserInfo user,String rootRegionid);

	DatumSystem getDatumSystem(String id);
	
}
