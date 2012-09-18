package com.cabletech.sysmanage.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.commons.web.WebAppUtils;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.sysmanage.dao.AppConfigDAO;
import com.cabletech.sysmanage.domainobjects.AppConfig;
/**
 * ϵͳ�����������������£����������Զ���Ч
 * @author �Ż��
 *
 */
@Service
public class AppConfigBO extends EntityManager{
	@Resource(name="appConfigDAO")
	private AppConfigDAO appconfigDao;
	/**
	 * ��������
	 * @param config
	 */
	@Transactional
	public void save(AppConfig config){
		appconfigDao.save(config);
	}
	/**
	 * ���������������Ϣ
	 * @return
	 */
	@Transactional(readOnly=true)
	public Map<String,AppConfig> findByAllConfig(){
		List<AppConfig> appConfigs = appconfigDao.getAll();
		Map<String,AppConfig> appConfigMap = new HashMap<String,AppConfig>();
		for(AppConfig config:appConfigs){
			appConfigMap.put(config.getKey(),config);
		}
		return appConfigMap;
	}
	@Override
	protected HibernateDao getEntityDao() {
		return appconfigDao;
	}
	@Transactional
	public void save(String key, String value) {
		AppConfig appconfig = appconfigDao.findByUnique("key", key);
		appconfig.setValue(value);
		if("approverGroupId".equals(key)){
			WebAppUtils.approverGroupId=value;
		}else if("online_period".equals(key)){
			WebAppUtils.online_period = Integer.parseInt(value);
		}
		appconfigDao.save(appconfig);
	}
	@Transactional(readOnly=true)
	public AppConfig findById(String id){
		return appconfigDao.get(id);
	}
	@Transactional(readOnly=true)
	public String findByProperties(String key) {
		AppConfig appconfig = appconfigDao.findByUnique("key", key);
		return appconfig.getValue();
	}
}
