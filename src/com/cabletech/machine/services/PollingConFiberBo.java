/***
 *
 * PollingConFiberBo.java
 * @auther <a href="kww@mail.cabletech.com.cn">kww</a>
 * обнГ05:16:30
 **/

package com.cabletech.machine.services;

import com.cabletech.machine.beans.PollingConFiberBean;
import com.cabletech.machine.dao.PollingConFiberDAO;

public class PollingConFiberBo {

	private PollingConFiberDAO dao = new PollingConFiberDAO();
	
	public boolean addPollingConFiberBean(PollingConFiberBean bean)
	{
		return dao.addPollingConFiber(bean);
	}
	
	public PollingConFiberBean getOneForm(String id){
		return dao.getOneForm(id);
	}
}
