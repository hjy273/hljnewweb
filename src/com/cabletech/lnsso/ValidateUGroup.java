package com.cabletech.lnsso;

import java.util.ArrayList;
import java.util.List;

import com.cabletech.baseinfo.dao.UsergroupDAOImpl;
import com.cabletech.baseinfo.domainobjects.Usergroup;
import com.cabletech.commons.base.BaseBisinessObject;

public class ValidateUGroup extends BaseBisinessObject {
	UsergroupDAOImpl dao = new UsergroupDAOImpl();
	List userGroups = new ArrayList();

	public ValidateUGroup() throws Exception {
		userGroups = dao.getHibernateTemplate().find("From Usergroup");
		//Usergroup ug = new Usergroup();
		//ug.setGroupname("沈阳移动");
		//userGroups.add(ug);
	}

	public List valUGroup(List UGNameList) {
		List holdGroup = new ArrayList();
		for (int i = 0; i < userGroups.size(); i++) {
			Usergroup ug = (Usergroup) userGroups.get(i);
			for (int j = 0; j < UGNameList.size(); j++) {
				String ugName = (String) UGNameList.get(j);
				if (ugName.equals(ug.getGroupname())) {
					holdGroup.add(ug.getId());
				}
			}
		}
		return holdGroup;
	}

	public static void main(String[] args) throws Exception {
		List list = new ArrayList();
		list.add("鞍山移动");
		list.add("程序设计");
		ValidateUGroup vu = new ValidateUGroup();
		vu.valUGroup(list);

	}

}
