package com.cabletech.commons.tags.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.commons.tags.dao.DictionaryDao;
import com.cabletech.commons.tags.module.Dictionary;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
/**
 * 负责数据字典的管理，包括的操作有增删改查等。
 * @author zh
 *
 */
@Service(value = "dictionaryService")
@Transactional
public class DictionaryService extends EntityManager<Dictionary,Integer> {
	@Resource(name="dictionaryDao")
	private DictionaryDao dao;
	@Override
	protected HibernateDao<Dictionary, Integer> getEntityDao() {
		return dao;
	}
	
	public DictionaryDao getDao() {
		return dao;
	}

	public void setDao(DictionaryDao dao) {
		this.dao = dao;
	}

	/**
	 * 通过分类查询相应的词条
	 * @param assortmentId
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Dictionary> getDictByAssortment(String assortmentId,String regionid){
		return dao.findByAssort(assortmentId,regionid);
	}
	@Transactional(readOnly=true)
	public List<Dictionary> getDictBySubset(String code,String assortmentId,String regionid){
		return dao.findByParentId(code, assortmentId,regionid);
	}
	@Transactional(readOnly=true)
	public Map<String,String> loadDictByAssortment(String assortmentId,String regionid){
		List<Dictionary> dicts= dao.findByAssort(assortmentId,regionid);
		Map<String,String> dictMap = new HashMap<String,String>();
		for(Dictionary dict :dicts){
			dictMap.put(dict.getCode(),dict.getLable());
		}
		return dictMap;
	}
	@Transactional(readOnly=true)
	public Map<Object, Object> findByAssortAndCode(String assort,String code,String regionid){
		return dao.queryByCode(assort,code,regionid);
	}
	@Transactional(readOnly=true)
	public List<Dictionary> queryDictionary(String regionid) {
		return dao.queryAllByRegion(regionid);
	}
	@Transactional(readOnly=true)
	public Dictionary findById(String ids) {
		int id = Integer.parseInt(ids);
		return dao.findByUnique("id", id);
	}

	public boolean isUsable(String assortmentId,String value,String regionid) {
		List<Dictionary> result = dao.findByValue(assortmentId,value,regionid);
		if(result.size()>0){
			return true;
		}else{
			return false;
		}
		
	}
	
}
