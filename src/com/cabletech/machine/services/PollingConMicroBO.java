package com.cabletech.machine.services;

import com.cabletech.machine.beans.PollingConMicroBean;
import com.cabletech.machine.dao.PollingConMicroDAO;

public class PollingConMicroBO {
	PollingConMicroDAO dao = new PollingConMicroDAO();
	
	
	public boolean addPollingConMicro(PollingConMicroBean bean) {
		return dao.addPollingConMicro(bean);
	}
	
	public PollingConMicroBean getOneForm(String pid) {
		return dao.getOneForm(pid);
	}
}
