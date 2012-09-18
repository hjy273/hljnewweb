package com.cabletech.commons.tags.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cabletech.commons.tags.module.Dictionary;
import com.cabletech.ctf.dao.HibernateDao;
@Repository
public class DictionaryDao extends HibernateDao<Dictionary,Integer> {

	public List<Dictionary> findByParentId(String code, String assortmentId,String regionid) {
		String hql="from Dictionary where parentId in (select id from Dictionary where code=? and assortmentId=?) ";
		return this.find(hql, code,assortmentId);
	}
	
	public String getDictionaryId(String assortId, String code){
		String hql="from Dictionary d where d.assortmentId = ? and d.code = ?";
		return findUnique(hql, assortId, code).getLable();
	}

	public List<Dictionary> findByAssort(String assortId,String regionid){
		String hql = "";
//		if(regionid == null){
			hql = "from Dictionary d where d.assortmentId = ?";
			return super.find(hql,assortId);
//		}else{
//			hql="from Dictionary d where d.assortmentId = ? and regionid=? ";
//			return super.find(hql,assortId,regionid);
//		}
		
	}
	public Map<Object, Object> queryByCode(String assort, String code,String regionid) {
		String sql = "select code,lable from dictionary_formitem where parentid in (select id from dictionary_formitem where assortment_id=? and code=?) ";
		 Map<Object, Object> map = super.getJdbcTemplate().queryForMap(sql, new Object[]{assort,code});
		 return map;
	}
	public Map<Object, Object> queryByCode(String assort) {
		String sql = "select code,lable from dictionary_formitem where assortment_id = ?";
		 Map<Object, Object> map = super.getJdbcTemplate().queryForMap(sql, new Object[]{assort});
		 return map;
	}

	public List<Dictionary> queryAllByRegion(String regionid) {
		String querySql =  "select t.assortment_id,t.code,t.id,t.lable,t.sort,b.parentname,a.assortment_desc from dictionary_formitem t,dictionary_assortment a,(select id parentid,lable parentname from dictionary_formitem)b where t.assortment_id= a.assortment_id  and b.parentid(+)=t.parentid and regionid=? order by t.assortment_id,t.sort";
		return super.getJdbcTemplate().query(querySql, new Object[]{regionid}, new DictionaryMapper());
	}
	protected class DictionaryMapper implements RowMapper {
		Dictionary dict= null;

		public Object mapRow(ResultSet rst, int rowNum) throws SQLException {
			dict = new Dictionary();
			dict.setAssortmentId(rst.getString("assortment_desc"));
			dict.setCode(rst.getString("code"));
			dict.setId(rst.getInt("id"));
			dict.setLable(rst.getString("lable"));
			dict.setParentId(rst.getString("parentname"));
			dict.setSort(rst.getInt("sort"));
			return dict;
		}
	}
	public List<Dictionary> findByValue(String assortmentId, String value,String regionid) {
		String querySql = "from Dictionary d where d.assortmentId = ? and lable like ?";
		return super.find(querySql, assortmentId,"%"+value+"%");
	}

}
