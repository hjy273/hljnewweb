package com.cabletech.linepatrol.cut.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.cut.module.CutHopRel;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * 割接与中间段关系操作类
 * @author Administrator
 *
 */
@Repository
public class CutHopRelDao extends HibernateDao<CutHopRel, String> {
	/**
	 * 获得中继段ID
	 * @param cutId：割接ID
	 * @return
	 */
	public String getSublineIds(String cutId){
		List list = this.findByProperty("cutId", cutId);
		String sublineIds = "";
		if(list.size() > 0){
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				sublineIds += ((CutHopRel) iterator.next()).getSublineId() + ",";
			}
			sublineIds = sublineIds.substring(0, sublineIds.length()-1);
		}
		logger.info("*************sublineIds:" + sublineIds);
		return sublineIds;
	}
	
	/**
	 * 删除割接与中继段关系数据
	 * @param cutId：割接ID
	 */
	public void deleteHopRelByCutId(String cutId){
		List list = this.findByProperty("cutId", cutId);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			CutHopRel cutHopRel = (CutHopRel) iterator.next();
			this.delete(cutHopRel);
		}
	}
	
	/**
	 * 保存中继段与割接关系
	 * @param trunks：中继段IDS
	 * @param cutId：割接申请ID
	 */
	public void saveTrunks(String trunks, String cutId){
		String hql = "from CutHopRel cutHopRel where cutHopRel.cutId=?";
		List list = this.find(hql, cutId);
		if(list != null){
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				CutHopRel cutHopRel = (CutHopRel) iterator.next();
				this.delete(cutHopRel);
			}
		}
		List<String> trunkList = new ArrayList<String>();
		if(StringUtils.isNotBlank(trunks)){
			trunkList = Arrays.asList(trunks.split(","));
		}
		if (trunks != null) {
			for (Iterator iterator = trunkList.iterator(); iterator.hasNext();) {
				String sublineId = (String) iterator.next();
				CutHopRel cutHopRel = new CutHopRel();
				cutHopRel.setCutId(cutId);
				cutHopRel.setSublineId(sublineId);
				this.save(cutHopRel);
			}
		}
	}
	
	/**
	 * 获得中继段信息
	 * @param sublineIds：中继段ID
	 * @return
	 */
	public List getRepeaterSection(String sublineIds){
		List list = new ArrayList();
		if(sublineIds != null && !"".equals(sublineIds)){
			String[] sublineId = sublineIds.split(",");
			for(int i = 0; i < sublineId.length; i++){
				String sql = "select rs.kid,rs.segmentname from repeatersection rs where rs.kid='" + sublineId[i] + "'";
				List list2 = this.getJdbcTemplate().queryForBeans(sql);
				if(list2 != null && list2.size() > 0){
					BasicDynaBean bdb = (BasicDynaBean)list2.get(0);
					String[] subline = new String[2];
					subline[0] = (String)bdb.get("kid");
					subline[1] = (String)bdb.get("segmentname");
					list.add(subline);
				}
			}
		}
		return list;
	}
}
