package com.cabletech.datum.service;

import java.util.List;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.datum.bean.DatumExperience;

public interface DatumExperienceService {
	boolean saveDatumExperience(DatumExperience experience);
	
	boolean updateDatumExperience(DatumExperience experience);
	
	boolean delDatumExperience(String id);

	List queryDatumExperience(DatumExperience bean, UserInfo user, String rootRegionid);

	DatumExperience getDatumExperience(String id);

}
