package com.cabletech.linepatrol.specialplan.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.dao.jdbc.mapper.KeyValue;
import com.cabletech.ctf.dao.jdbc.mapper.KeyValueMapper;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.linepatrol.specialplan.module.SpecialPlan;
@Repository
public class SpecialPlanDao extends HibernateDao<SpecialPlan, String> {
	public List<SpecialPlan> getPlanByType(String regionid,String type){
		String hql = "from SpecialPlan where regionId=? and planType=?";
		return super.find(hql, regionid,type);
	}
	
	public List<SpecialPlan> getCompletedPlan(String id){
		String hql = "from SpecialPlan s where s.id = ? and s.endDate < sysdate";
		return find(hql, id);
	}
	
	public List<Map> getPlanForHiddangerStat(String hiddangerId){
//		String hql = "from SpecialPlan s where s.id in (select distinct h.planId from HiddangerPlan h where h.hiddangerId = ?) order by s.createDate desc";
//		return find(hql, hiddangerId);
		String sql = "select plan_id,plan_name,start_date,end_date,patrol_stat_state from lp_hiddanger_plan left join lp_special_plan sp on plan_id = sp.id  left join lp_spec_plan_stat ls on ls.spec_plan_id = sp.id where hiddanger_id='"+hiddangerId+"'";
		return getJdbcTemplate().queryForList(sql);
	}

	public List<KeyValue> getCableLevel() {
		String sql = "select code ,name from lineclassdic";
		List<KeyValue> cableLevels = getJdbcTemplate().query(sql,  new KeyValueMapper());
		return cableLevels;
	}
	
	/**
	 * 查询某个级别的路由段信息
	 * @param objects  查询条件｛区域，维护单位，线路级别，sublinename关键字｝
	 * @return
	 */
	public List<KeyValue> getTaskSublines(Object... objects) {
		//String sql = "select sublineid,sublinename from sublineinfo s,lineinfo l where s.lineid = l.lineid and l.regionid =? and s.ruledeptid=? and l.linetype =? and s.sublinename like ? order by l.lineid,s.sublineid";
		String sql = "select sublineid,sublinename from sublineinfo s,lineinfo l where s.lineid = l.lineid  and l.regionid =? and s.patrolid in (select p.patrolid from patrolmaninfo p,contractorinfo c where p.parentid = c.contractorid and p.parentid = ? ) and l.linetype =?" +
				" and s.sublinename like ? order by l.lineid,s.sublineid";
		return super.getJdbcTemplate().query(sql, objects, new KeyValueMapper());
	}
	
	public Map<Object, Object> getSublines(UserInfo user,Object... objects){
//		String sql = "select sublineid,sublinename from sublineinfo s,lineinfo l where s.lineid = l.lineid and l.regionid =? and s.ruledeptid=? and l.linetype =? order by l.lineid,s.sublineid";
		String sql = "";
		if("1".equals(user.getDeptype())){
			sql = "select sublineid,sublinename from sublineinfo s,lineinfo l where s.lineid = l.lineid and l.regionid =? and l.linetype =? order by l.lineid,s.sublineid";
		}else{
			sql = "select sublineid,sublinename from sublineinfo s,lineinfo l where s.lineid = l.lineid  and l.regionid =?  and l.linetype =? and s.patrolid in (select p.patrolid from patrolmaninfo p,contractorinfo c where p.parentid = c.contractorid and p.parentid = '"+user.getDeptID()+"' )" +
		" order by l.lineid,s.sublineid";
		}
		return super.getJdbcTemplate().queryForMap(sql, objects);
	}
	/**
	 * 盯防区域获取是通过当前30天内的该代维制定的盯防区域。而不是按照先前的不能够选择已经选择的；
	 * 这样能够增在区域的利用度
	 * polygonid not in (select area_id from lp_special_watch)
	 * @param objects
	 * @return
	 */
	public List<KeyValue> getTaskArea(Object... objects) {
		String sql = "select polygonId,polygonName from LP_WATCH_POLYGON  where  createtime >sysdate-30  and regionid=? and contractorid=?";
		logger.info(sql);
		return super.getJdbcTemplate().query(sql, objects, new KeyValueMapper());
	}

	public Map<Object, Object> getWatchArea(Object... objects) {
		String sql = "select polygonId,polygonName from LP_WATCH_POLYGON where regionid=? and contractorid=?";
		return super.getJdbcTemplate().queryForMap(sql, objects);
	}

	public List<KeyValue> getPatrolGroups(Object ... objects) {
		String sql = "select patrolid,patrolname from patrolmaninfo where state is null and regionid=? and parentid=? ";
		return super.getJdbcTemplate().query(sql, objects, new KeyValueMapper());
	}
	
	public void deleteSpecialPlan(String spplanId) throws ServiceException{
		String sql = "delete from lp_special_plan where id='" + spplanId + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	public Date getEndDateBySpId(String spId){
		String sql = "select * from lp_special_plan where id='" + spId + "'";
		List list = this.getJdbcTemplate().queryForBeans(sql);
		if(list != null && list.size() > 0){
			BasicDynaBean bean = (BasicDynaBean)list.get(0);
			return (Date)bean.get("end_date");
		}
		return null;
	}
	
	public void updateEndDateBySpId(String spId, Date endDate){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String endDateStr = sdf.format(endDate);
		String sql = "update lp_special_plan set end_date=to_date('" + endDateStr + "','yyyy/mm/dd hh24:mi:ss') where id='" + spId + "'";
		logger.info("sql:" + sql + "endDateStr:" + endDateStr + "spId:" + spId);
		this.getJdbcTemplate().update(sql);
	}
}
