package com.cabletech.linepatrol.trouble.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.trouble.module.TroubleInfo;
import com.cabletech.linepatrol.trouble.module.TroubleProcessUnit;

@Repository
public class TroubleProcessUnitDAO extends HibernateDao<TroubleProcessUnit, String> {
	
	
	public TroubleProcessUnit getUnitById(String id){
		TroubleProcessUnit unit = get(id);
		initObject(unit);
		return unit;
	}
	
	/**
	 * 根据故障id删除故障处理单位信息
	 * @param troubleid
	 */
	public void deleteTroubleUnit(String troubleid){
		String sql=" delete LP_TROUBLE_PROCESS_UNIT  where trouble_id='"+troubleid+"'";
		this.getJdbcTemplate().execute(sql);
	}
	

	/**
	 * 根据反馈单id查询故障处理单位信息
	 * @param replyid
	 * @return
	 */
	public TroubleProcessUnit getProcessUnitByReplyId(String replyid){
		TroubleProcessUnit unit = new TroubleProcessUnit();
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select unit.id,unit.process_unit_id ");
		sb.append(" from lp_trouble_reply r,lp_trouble_process_unit unit ");
		sb.append(" where  r.trouble_id=unit.trouble_id ");
		sb.append(" and r.contractor_id= unit.process_unit_id and r.id=?");
		values.add(replyid);
		List list = this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
		if(list!=null && list.size()>0){
			BasicDynaBean bean = (BasicDynaBean) list.get(0);
			String unitid = (String)bean.get("process_unit_id");
			String id = (String)bean.get("id");
			unit.setId(id);
			unit.setProcessUnitId(unitid);
		}
		return unit;
	}
	
	
	/**
	 * 根据故障单id查询故障处理单位信息
	 * @param troubleid  故障单
	 * @return
	 */
	public List<TroubleProcessUnit> getProcessUnitByTroubleId(String troubleid,String userid){
		List<TroubleProcessUnit> units = new ArrayList<TroubleProcessUnit>();
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select unit.id,unit.process_unit_id,r.confirm_result cr ");
		sb.append(" from lp_trouble_reply r,lp_trouble_process_unit unit ");
		sb.append(",lp_reply_approver a");
		sb.append(" where r.trouble_id=unit.trouble_id ");
		sb.append(" and r.contractor_id= unit.process_unit_id and r.trouble_id=?");
		values.add(troubleid);
		sb.append(" and a.object_id=r.id and a.approver_id=?");
		values.add(userid);
		List list = this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
		if(list!=null && list.size()>0){
			for(int i = 0;i<list.size();i++){
				TroubleProcessUnit unit = new TroubleProcessUnit();
				BasicDynaBean bean = (BasicDynaBean) list.get(i);
				String unitid = (String)bean.get("process_unit_id");
				String id = (String)bean.get("id");
				String cr  =(String)bean.get("cr");
				unit.setId(id);
				unit.setProcessUnitId(unitid);
				unit.setState(cr);//判断是主办还是协办
				units.add(unit);
			}
		}
		return units;
	}
	

}
