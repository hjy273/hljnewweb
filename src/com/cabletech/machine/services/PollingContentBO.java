package com.cabletech.machine.services;

import com.cabletech.machine.beans.PollingContentBean;
import com.cabletech.machine.dao.PollingContentDAO;

public class PollingContentBO {
	PollingContentDAO dao = new PollingContentDAO();
	
	
	public boolean addPollingContent(PollingContentBean bean) {
		return dao.addPollingContent(bean);
	}
	
	public PollingContentBean getOneForm(String id) {
		return dao.getOneForm(id);
	}
}
