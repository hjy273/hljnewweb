package com.cabletech.machine.services;

import com.cabletech.machine.beans.PollingConFsoBean;
import com.cabletech.machine.dao.PollingConFsoDAO;

public class PollingConFsoBO {
	PollingConFsoDAO dao = new PollingConFsoDAO();
	
	public boolean addPollingConFso(PollingConFsoBean bean) {
		return dao.addPollingConFso(bean);
	}
	
	public PollingConFsoBean getOneForm(String pid) {
		return dao.getOneForm(pid);
	}
}
