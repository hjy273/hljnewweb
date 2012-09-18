package com.cabletech.lnsso;

import java.util.List;

import com.cabletech.baseinfo.dao.ContractorDAOImpl;
import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.commons.base.BaseBisinessObject;

public class ContractorConversion extends BaseBisinessObject {

	ContractorDAOImpl dao = new ContractorDAOImpl();

	/***
	 * 
	 * @param conName 所在城市名称
	 * @return
	 * @throws Exception
	 */

	public String getConversionCon(String city) throws Exception {
		List cons = dao.getHibernateTemplate().find("From Contractor where contractorName like '%" + city + "%'");
		if (cons != null && cons.size() > 0) {
			Contractor con = (Contractor) cons.get(0);
			return con.getContractorID();
		} else
			return null;
	}
}
