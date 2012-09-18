package com.cabletech.lnsso;

import java.util.List;

import com.cabletech.baseinfo.dao.DepartDAOImpl;
import com.cabletech.baseinfo.domainobjects.Depart;
import com.cabletech.commons.base.BaseBisinessObject;

public class DeptConversion extends BaseBisinessObject {
	DepartDAOImpl dao = new DepartDAOImpl();

	public String getDept(String regionname) throws Exception {
		List depts = dao.getHibernateTemplate().find(
				"From Depart where deptName like '" + regionname + "%' and state is null");
		if (depts != null && depts.size() > 0) {
			Depart dept = (Depart) depts.get(0);
			return dept.getDeptID();
		} else
			return null;
	}
}
