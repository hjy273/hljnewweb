package com.cabletech.datum.service;

import java.util.List;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.datum.bean.DatumCriterion;

public interface DatumCriterionService {
	boolean saveDatumCriterion(DatumCriterion data);
	
	boolean updateDatumCriterion(DatumCriterion data);
	
	boolean delDatumCriterion(String id);


	DatumCriterion getDatumCriterion(String id);

	List queryDatumCriterion(DatumCriterion bean, UserInfo user, String rootRegionid);
}
