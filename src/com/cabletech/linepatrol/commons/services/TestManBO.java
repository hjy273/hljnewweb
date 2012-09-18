package com.cabletech.linepatrol.commons.services;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.linepatrol.commons.dao.TestManDAO;
import com.cabletech.linepatrol.maintenance.module.TestCableData;
import com.cabletech.linepatrol.maintenance.module.TestStationData;

/**
 * ���ݲ��Լƻ�ѡ�������Ա
 * @author Administrator
 *
 */
@Service
@Transactional
public class TestManBO extends BaseBisinessObject {

	@Resource(name = "testManDAO")
	private TestManDAO dao;

	public TestCableData getLineDataById(String id){
		return dao.getLineDataById(id);
	}

	public TestStationData getStationDataById(String id){
		return dao.getStationDataById(id);
	}

	/**
	 * �õ��ƻ�������Ա
	 * @param user
	 * @return
	 */
	public List getUsers(UserInfo user,String userName){
		return dao.getUsers(user, userName);
	}
}
