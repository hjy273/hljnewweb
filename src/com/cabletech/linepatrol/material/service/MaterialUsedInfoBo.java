package com.cabletech.linepatrol.material.service;


import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.material.dao.MaterialUsedInfoDao;
import com.cabletech.linepatrol.material.domain.MaterialUsedInfo;

@Service
@Transactional
public class MaterialUsedInfoBo extends EntityManager<MaterialUsedInfo,String> {
	
	@Resource(name="materialUsedInfoDao")
	private MaterialUsedInfoDao materialUsedInfoDao;
	
	/**
	 * 根据代维与材料id、获取材料信息
	 * @param conid
	 * @param mtid
	 * @return
	 */
	@Transactional(readOnly=true)
	public List getMarterialInfos(String conid,String mtid) throws ServiceException {
		return materialUsedInfoDao.getMarterialInfos(conid, mtid);
	}
	
	
	/**
	 * 根据材料id与地址id获取材料信息
	 * @param conid
	 * @param mtid
	 * @return
	 */
	@Transactional(readOnly=true)
	public BasicDynaBean getMarterialInfo(String mtid,String addrid) throws ServiceException {
		List list =  materialUsedInfoDao.getMarterialInfo(mtid, addrid);
		BasicDynaBean bean = null;
		if(list !=null && list.size()>0){
			bean = (BasicDynaBean) list.get(0);
		}
		return bean;
	}


	@Override
	protected HibernateDao<MaterialUsedInfo, String> getEntityDao() {
		// TODO Auto-generated method stub
		return materialUsedInfoDao;
	}


	public MaterialUsedInfoDao getMaterialUsedInfoDao() {
		return materialUsedInfoDao;
	}


	public void setMaterialUsedInfoDao(MaterialUsedInfoDao materialUsedInfoDao) {
		this.materialUsedInfoDao = materialUsedInfoDao;
	}
	
}
