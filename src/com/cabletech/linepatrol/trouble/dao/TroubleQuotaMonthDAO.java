package com.cabletech.linepatrol.trouble.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaBean;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.trouble.module.TroubleGuideMonth;
import com.cabletech.linepatrol.trouble.module.TroubleNormGuide;
import com.cabletech.linepatrol.trouble.services.TroubleConstant;

@Repository
public class TroubleQuotaMonthDAO extends HibernateDao<TroubleGuideMonth, String> {
	/**
	 * 查询月指标
	 * @param type
	 * @param month
	 * @return
	 */
	public List<TroubleGuideMonth> getQuotaMonth(String type,String month){
		String hql = " from TroubleGuideMonth t where  " +
		" t.guideType=? and t.statMonth=to_date('"+month+"', 'yyyy/MM') ";
		//modify by xueyh  20110413 for " and t.reviseInterdictionTime is null" cause bug (repeat data) 
		return find(hql,type);
	}

	/**
	 * 查询月指标
	 * @param contractorId
	 * @param type
	 * @param month
	 * @return
	 */
	public TroubleGuideMonth getContractorQuotaMonth(String contractorId,String type,String month){
		String hql = " from TroubleGuideMonth t where t.contractorId=? " +
		" and t.guideType=? and t.statMonth=to_date('"+month+"', 'yyyy/MM') " +
		" and t.reviseInterdictionTime is null";
		//modify by xueyh  20110419 for " and t.reviseInterdictionTime is null" cause bug (repeat data) 
		hql = " from TroubleGuideMonth t where t.contractorId=? " +
		" and t.guideType=? and t.statMonth=to_date('"+month+"', 'yyyy/MM') " ;
		List list = find(hql,contractorId,type);
		if(list!=null && list.size()>0){
			return (TroubleGuideMonth) list.get(0);
		}
		return null;
	}


	/**
	 * 查询有光缆的代维用户
	 * @param regionid
	 * @param type 指标类型
	 * @return
	 */
	public List<Contractor> getContractorByType(String regionid,String type,String month){
		String hql=" from Contractor c where state is null and " +
		" c.regionid='"+regionid+"' and c.contractorID in(" +
		" select rep.maintenanceId from RepeaterSection rep" +
		" where  rep.grossLength>0 and rep.isCheckOut='y' and scrapState='false' and " +
		" rep.finishtime<=to_date('"+month+"', 'yyyy/MM')";
		//"to_char(rep.finishtime,'yyyy/MM')<='"+month+"'";
		if(type.equals(TroubleConstant.QUOTA)){
			hql+=" and rep.cableLevel='B01' )"; 
		}else{
			hql+=" and rep.cableLevel!='B01')"; 
		}
		List<Contractor> list =  getSession().createQuery(hql).list();
		return list;
	}

	/**
	 * 查询故障个数以及抢修历时(审核过的主办累加)
	 * @param type
	 * @param month
	 * @return
	 */
	public BasicDynaBean getTroubleNumAndTime(String contractorId,String type,String month){
		int day = DateUtil.getMonth4Days(month);
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select count(reply.id) troublenum,");
		sb.append(" nvl(sum(trunc((reply.renew_time - t.trouble_start_time)*24 ,2)),0) as etime");
		sb.append(" from lp_trouble_reply reply,lp_trouble_info t ");
		sb.append(" where reply.trouble_id = t.id ");
		sb.append(" and trouble_start_time>=to_date('"+month+"/01 00:00:00','yyyy/MM/dd hh24:mi:ss')");
		sb.append(" and trouble_start_time<=to_date('"+month+"/"+day+" 23:59:59','yyyy/MM/dd hh24:mi:ss')");
		sb.append(" and reply.approve_state=?");
		values.add(TroubleConstant.REPLY_APPROVE_PASS);
		sb.append(" and reply.id in(select cable.trouble_reply_id");
		sb.append(" from lp_trouble_cable_info cable,repeatersection rep  where");
		sb.append(" cable.trouble_cable_line_id=rep.kid");
		sb.append(queryCondition(contractorId,type));
//		values.add(TroubleConstant.QUOTA);
		values.add(contractorId);
		values.add(TroubleConstant.REPLY_ROLE_HOST);
		values.add(TroubleConstant.TEMP_SAVE);
		List list =  getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
		if(list!=null && list.size()>0){
			return (BasicDynaBean) list.get(0);
		}
		return null;
	}

	/**
	 * 通过计算交维表计算代维维护光缆长度
	 * @param contractorId
	 * @param type
	 * @param month
	 * @return
	 */
	public double getCableLength(String contractorId,String type,String month){
		String hql = "select sum(rep.grossLength) from RepeaterSection rep " +
		" where rep.maintenanceId='"+contractorId+"' and rep.isCheckOut='y' and scrapState='false'";
		if(type.equals(TroubleConstant.QUOTA)){
			hql+=" and rep.cableLevel='B01'"; 
		}else{
			hql+=" and rep.cableLevel!='B01'"; 
		}
		hql+=" and rep.finishtime<=to_date('"+month+"', 'yyyy/MM')";//统计月份
		//hql+=" and to_char(rep.finishtime,'yyyy/MM')<='"+month+"'";
		Object cablelen = getSession().createQuery(hql).uniqueResult();
		if(cablelen!=null){
			return Double.parseDouble(cablelen.toString());
		}
		return 0;
	}
	public double getCableLength4CurrentMonth(String contractorId, String guideType, String month) {
		String hql = "select sum(rep.grossLength) from RepeaterSection rep " +
		" where rep.maintenanceId='"+contractorId+"' and rep.isCheckOut='y' and scrapState='false'";
		if(guideType.equals(TroubleConstant.QUOTA)){
			hql+=" and rep.cableLevel='B01'"; 
		}else{
			hql+=" and rep.cableLevel!='B01'"; 
		}
		hql += " and rep.finishtime>=to_date('"+month+"', 'yyyy/MM')";
		Date date = DateUtil.parseDate(month, "yyyy/MM");
		hql+=" and rep.finishtime<to_date('"+DateUtil.getNextMonth(date)+"', 'yyyy/MM')";
		//hql+=" and to_char(rep.finishtime,'yyyy/MM')<='"+month+"'";
		Object cablelen = getSession().createQuery(hql).uniqueResult();
		if(cablelen!=null){
			return Double.parseDouble(cablelen.toString());
		}
		return 0;
		
	}
	//获取上月修正光缆长度
	public double getReviseCableLength(String contractorId, String guideType,String month) {
		String hql = "select reviseMaintenanceLength from TroubleGuideMonth where guideType=? and contractorId=? and statMonth<to_date('"+month+"', 'yyyy/MM') " +
				"order by statMonth desc";
		Query query = super.createQuery(hql, guideType,contractorId);
		List lengthList = query.list();
		double length = 0.0;
		for(int i =0;i<lengthList.size();i++){
			length = (Double) lengthList.get(i);
			if(length >0){
				break;
			}
		}
		return length;
	}
	

	/**
	 * 故障故障抢修及时率
	 */
	public double troubleRepairRate(String contractorId,String type,String month){
		int day = DateUtil.getMonth4Days(month);
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select ");
		sb.append(" nvl(trunc((reply.renew_time - reply.trouble_accept_time)*24 ,2),0) as etime");
		sb.append(" from lp_trouble_reply reply,lp_trouble_info t ");
		sb.append(" where reply.trouble_id = t.id ");
		sb.append(" and trouble_start_time>=to_date('"+month+"/01 00:00:00','yyyy/MM/dd hh24:mi:ss')");
		sb.append(" and trouble_start_time<=to_date('"+month+"/"+day+" 23:59:59','yyyy/MM/dd hh24:mi:ss')");
		sb.append(" and reply.approve_state=?");
		values.add(TroubleConstant.REPLY_APPROVE_PASS);
		sb.append(" and reply.id in(select cable.trouble_reply_id");
		sb.append(" from lp_trouble_cable_info cable,repeatersection rep  where");
		sb.append(" cable.trouble_cable_line_id=rep.kid");
		sb.append(queryCondition(contractorId,type));
//		values.add(TroubleConstant.QUOTA);
		values.add(contractorId);
		values.add(TroubleConstant.REPLY_ROLE_HOST);
		values.add(TroubleConstant.TEMP_SAVE);
		List list =  getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
		double replyrate = TroubleConstant.REPLY_RATE;
		if(list!=null && list.size()>0){
			int countnum = list.size();
			int num = 0;
			for(int i = 0;i<countnum;i++){
				BasicDynaBean bean = (BasicDynaBean)list.get(i);
				Object etime = bean.get("etime");
				double timeHour = Double.parseDouble(etime.toString());
				if(timeHour<replyrate){
					num++;
				}
			}
			if(num>0){
				return num/countnum;
			}
			return 0;
		}
		return 1;
	}

	/**
	 * 故障城域网单次抢修历时超出标准值的数量
	 */
	public int getCityAreaOnceTroubleOutStandardNumber(String contractorId,String type,String month){
		int day = DateUtil.getMonth4Days(month);
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select count(*) as num ");
		sb.append(" from lp_trouble_reply reply,lp_trouble_info t ");
		sb.append(" where reply.trouble_id = t.id ");
		sb.append(" and trouble_start_time>=to_date('"+month+"/01 00:00:00','yyyy/MM/dd hh24:mi:ss')");
		sb.append(" and trouble_start_time<=to_date('"+month+"/"+day+" 23:59:59','yyyy/MM/dd hh24:mi:ss')");
		sb.append(" and nvl(trunc((reply.renew_time - reply.trouble_accept_time)*24 ,2),0)>"+TroubleConstant.REPLY_RATE+"");
		sb.append(" and reply.approve_state=?");
		values.add(TroubleConstant.REPLY_APPROVE_PASS);
		sb.append(" and reply.id in(select cable.trouble_reply_id");
		sb.append(" from lp_trouble_cable_info cable,repeatersection rep  where");
		sb.append(" cable.trouble_cable_line_id=rep.kid");
		sb.append(queryCondition(contractorId,type));
//		values.add(TroubleConstant.QUOTA);
		values.add(contractorId);
		values.add(TroubleConstant.REPLY_ROLE_HOST);
		values.add(TroubleConstant.TEMP_SAVE);
		List list =  getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
		if(list!=null && list.size()>0){
			DynaBean bean=(DynaBean)list.get(0);
			return ((BigDecimal)bean.get("num")).intValue();
		}
		return 0;
	}

	/**
	 * 光缆级别
	 * @return
	 */
	public String queryCondition(String contractorId,String type){
		StringBuffer sb = new StringBuffer();
		if(type.equals(TroubleConstant.QUOTA)){
			sb.append(" and rep.cable_level='B01')"); 
		}else{
			sb.append(" and rep.cable_level!='B01')"); 
		}
		sb.append(" and reply.contractor_id=? and reply.confirm_result=? and reply.approve_state!=?");
		return sb.toString();
	}


	/**
	 * 故障反馈及时率
	 * @param contractorId
	 * @param type
	 * @param month
	 * @return
	 */
	public double troubleReplyRate(String contractorId,String type,String month){
		int day = DateUtil.getMonth4Days(month);
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select t.is_great_trouble,");
		sb.append(" nvl(trunc((reply.reply_submit_time - reply.renew_time)*24 ,2),0) as etime");
		sb.append(" from lp_trouble_reply reply,lp_trouble_info t");
		sb.append(" where reply.trouble_id=t.id ");
		sb.append(" and trouble_start_time>=to_date('"+month+"/01 00:00:00','yyyy/MM/dd hh24:mi:ss')");
		sb.append(" and trouble_start_time<=to_date('"+month+"/"+day+" 23:59:59','yyyy/MM/dd hh24:mi:ss')");
		sb.append(" and reply.approve_state=?");
		values.add(TroubleConstant.REPLY_APPROVE_PASS);
		sb.append(" and reply.id in(select cable.trouble_reply_id");
		sb.append(" from lp_trouble_cable_info cable,repeatersection rep  where");
		sb.append(" cable.trouble_cable_line_id=rep.kid");
		sb.append(queryCondition(contractorId,type));
//		values.add(TroubleConstant.QUOTA);
		values.add(contractorId);
		values.add(TroubleConstant.REPLY_ROLE_HOST);
		values.add(TroubleConstant.TEMP_SAVE);
		List list =  getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
		double greattime = TroubleConstant.GREAT_TROUBLE_TIME;
		double commontime = TroubleConstant.COMMON_TROUBLE_TIME;
		if(list!=null && list.size()>0){
			int countnum = list.size();
			int num = 0;
			for(int i = 0;i<countnum;i++){
				BasicDynaBean bean = (BasicDynaBean)list.get(i);
				Object etime = bean.get("etime");
				double timeHour = Double.parseDouble(etime.toString());
				String isgreat = (String)bean.get("is_great_trouble");
				if(isgreat.equals(TroubleConstant.IS_GREAT_TROUBLE_Y) && timeHour<=greattime){
					num++;
				}
				if(isgreat.equals(TroubleConstant.IS_GREAT_TROUBLE_N) && timeHour<=commontime){
					num++;
				}
			}
			if(num>0){
				return num/countnum;
			}
			return 0;
		}
		return 1;
	}

	/**
	 * 查询月故障指标
	 * @param type 统计类型
	 * @param month 月份
	 * @param normtimes 千公里阻断次数基准值
	 * @param normtime 千公里阻断时长基准值
	 * @return
	 */
	public List getTroubleMonthQuotas(String type,String month,TroubleNormGuide guide){
		double normtimes = guide.getInterdictionTimesNormValue();
		double normtime = guide.getInterdictionTimeNormValue();
		double daretimes = guide.getInterdictionTimesDareValue();
		double daretime = guide.getInterdictionTimeDareValue();
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer(); //add by xueyh 20110413 for 增加维护故障距离修正值   revise_maintenance_length
		sb.append(" select g.id,c.contractorname,g.trouble_times,g.revise_trouble_times as revise_trouble_times,g.revise_maintenance_length as revise_maintenance_length,g.revise_interdiction_time as revise_interdiction_time,");
		sb.append(" g.maintenance_length,g.interdiction_time,");
		sb.append(" (g.INTERDICTION_NORM_TIME) as standard_time,");
		sb.append(" (g.INTERDICTION_NORM_TIMES) as standard_times,");
		sb.append(" (g.INTERDICTION_DARE_TIME) as dare_time,");
		sb.append(" (g.INTERDICTION_DARE_TIMES) as dare_times,");
//		sb.append(" trunc(maintenance_length/1000*"+normtime+"/12,2) as standard_time,");
//		sb.append(" trunc(maintenance_length/1000*"+normtimes+"/12,2) as standard_times,");
//		sb.append(" trunc(maintenance_length/1000*"+daretime+"/12,2) as dare_time,");
//		sb.append(" trunc(maintenance_length/1000*"+daretimes+"/12,2) as dare_times,");
		sb.append(" (g.rtr_in_time*100 || '%') rtr_in_time,(g.feedback_in_time*100 || '%') feedback_in_time,");
		sb.append(" g.city_area_out_standard_number ");
		sb.append(" from lp_trouble_guide_month g,contractorinfo c");
		sb.append(" where g.contractor_id=c.contractorid");
		sb.append(" and to_char(stat_month,'yyyy/MM')=?");
		sb.append(" and guide_type=?");
		values.add(month);
		values.add(type);
		List list =  getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
		return list;
	}
	
	public List getTroubleMonthSumQuotas(String type,String month,TroubleNormGuide guide){
		double normtimes = guide.getInterdictionTimesNormValue();
		double normtime = guide.getInterdictionTimeNormValue();
		double daretimes = guide.getInterdictionTimesDareValue();
		double daretime = guide.getInterdictionTimeDareValue();
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb=new StringBuffer();
		sb.append(" select '0' as id,'小计' as contractorname,sum(g.trouble_times) as trouble_times,sum(g.revise_trouble_times) as revise_trouble_times,sum(g.revise_maintenance_length) as revise_maintenance_length,sum(g.revise_interdiction_time) as revise_interdiction_time,");
		sb.append(" sum(g.maintenance_length) as maintenance_length,sum(g.interdiction_time) as interdiction_time,");
		sb.append(" sum(g.INTERDICTION_NORM_TIME) as standard_time,");
		sb.append(" sum(g.INTERDICTION_NORM_TIMES) as standard_times,");
		sb.append(" sum(g.INTERDICTION_DARE_TIME) as dare_time,");
		sb.append(" sum(g.INTERDICTION_DARE_TIMES) as dare_times,");
		sb.append(" '' as rtr_in_time,'' as feedback_in_time,");
		sb.append(" sum(g.city_area_out_standard_number) as city_area_out_standard_number ");
		sb.append(" from lp_trouble_guide_month g");
		sb.append(" where to_char(stat_month,'yyyy/MM')=?");
		sb.append(" and guide_type=?");
		values.add(month);
		values.add(type);
		List list=getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
		return list;
	}

	/**
	 * 查询一年统计的月指标
	 * @param guideType
	 * @param year
	 * @return
	 */
	public List<TroubleGuideMonth> getMonthQuotaInYear(String guideType,String year){
		String hql=" from TroubleGuideMonth m where m.guideType=? and" +
		"  to_char(m.statMonth,'yyyy')=? order by statMonth";
		List<TroubleGuideMonth> list = find(hql,guideType,year);
		return list;
	}

	/**
	 * 故障年统计
	 * @param type  指标类型
	 * @param year  统计年份
	 * @return
	 */
	public List statYearTroubleMonthQuotas(String type,String year,TroubleNormGuide guide){
		double normtimes = guide.getInterdictionTimesNormValue();
		double normtime = guide.getInterdictionTimeNormValue();
		double daretimes = guide.getInterdictionTimesDareValue();
		double daretime = guide.getInterdictionTimeDareValue();
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		String sumlen = "sum(decode(ceil(to_char(g.stat_month,'yyyy')),'"+year+"',g.maintenance_length,0))";
		sb.append(" select distinct g.contractor_id, c.contractorname,");
		//	sb.append(" g.trouble_times,");
		//sb.append(" g.interdiction_time,");
		sb.append(" sum(case g.contractor_id when g.contractor_id then g.trouble_times else 0 end) trouble_times, ");
		sb.append(" sum(case g.contractor_id when g.contractor_id then g.interdiction_time else 0 end) interdiction_time, ");
		sb.append(" sum(case g.contractor_id when g.contractor_id then g.city_area_out_standard_number else 0 end) city_area_out_standard_number, ");
		sb.append(" 0.0 as whole_year_standard_time,0.0 as whole_year_standard_times, ");
		sb.append(" 0.0 as standard_time,0.0 as standard_times, ");
		sb.append(" 0.0 as dare_time,0.0 as dare_times, ");
//		sb.append(" sum(case g.contractor_id when g.contractor_id then g.interdiction_norm_time*12 else 0 end) as whole_year_standard_time,");
//		sb.append(" sum(case g.contractor_id when g.contractor_id then g.interdiction_norm_times*12 else 0 end) as whole_year_standard_times,");
//		sb.append(" sum(case g.contractor_id when g.contractor_id then g.interdiction_norm_time else 0 end) as standard_time,");
//		sb.append(" sum(case g.contractor_id when g.contractor_id then g.interdiction_norm_times else 0 end) as standard_times,");
//		sb.append(" sum(case g.contractor_id when g.contractor_id then g.interdiction_dare_time else 0 end) as dare_time,");
//		sb.append(" sum(case g.contractor_id when g.contractor_id then g.interdiction_dare_times else 0 end) as dare_times,");
//		sb.append(" trunc(("+sumlen+")/1000*"+normtime+"/12,2) as standard_time,");
//		sb.append(" trunc(("+sumlen+")/1000*"+normtimes+"/12,2) as standard_times,");
//		sb.append(" trunc(("+sumlen+")/1000*"+daretime+"/12,2) as dare_time,");
//		sb.append(" trunc(("+sumlen+")/1000*"+daretimes+"/12,2) as dare_times,");
		sb.append(" sum(decode(ceil(to_char(g.stat_month,'mm')),1,g.maintenance_length,null)) jan,");
		sb.append(" sum(decode(ceil(to_char(g.stat_month,'mm')),2,g.maintenance_length,null)) feb,");
		sb.append(" sum(decode(ceil(to_char(g.stat_month,'mm')),3,g.maintenance_length,null)) mar,");
		sb.append(" sum(decode(ceil(to_char(g.stat_month,'mm')),4,g.maintenance_length,null)) apr,");
		sb.append(" sum(decode(ceil(to_char(g.stat_month,'mm')),5,g.maintenance_length,null)) may,");
		sb.append(" sum(decode(ceil(to_char(g.stat_month,'mm')),6,g.maintenance_length,null)) june,");
		sb.append(" sum(decode(ceil(to_char(g.stat_month,'mm')),7,g.maintenance_length,null)) july,");
		sb.append(" sum(decode(ceil(to_char(g.stat_month,'mm')),8,g.maintenance_length,null)) aug,");
		sb.append(" sum(decode(ceil(to_char(g.stat_month,'mm')),9,g.maintenance_length,null)) sep,");
		sb.append(" sum(decode(ceil(to_char(g.stat_month,'mm')),10,g.maintenance_length,null)) oct,");
		sb.append(" sum(decode(ceil(to_char(g.stat_month,'mm')),11,g.maintenance_length,null)) nov,");
		sb.append(" sum(decode(ceil(to_char(g.stat_month,'mm')),12,g.maintenance_length,null)) dece");
		sb.append(" from lp_trouble_guide_month g,contractorinfo c");
		sb.append(" where g.contractor_id=c.contractorid");
		sb.append(" and to_char(stat_month,'yyyy')=?");
		sb.append(" and guide_type=?");
		values.add(year);
		values.add(type);
		sb.append(" group by contractor_id,contractorname");
		List list =  getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
		return list;
	}


	/**
	 * 故障年统计
	 * @param type  指标类型
	 * @param year  统计年份
	 * @return
	 */
	public List getTimeAreaTroubleQuotaList(String contractorId,String quotaType,String beginTime,String endTime){
		List values = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb.append(" select c.contractorid,c.contractorname," +
				"g.stat_month,to_char(g.stat_month,'yyyy/MM') as stat_month_str," +
				"g.maintenance_length," +
				"g.interdiction_norm_times," +
				"g.interdiction_dare_times," +
				"g.TROUBLE_TIMES trouble_times," +
				"g.interdiction_norm_time," +
				"g.interdiction_dare_time," +
				"g.interdiction_time interdiction_time, "+
				"g.city_area_out_standard_number," +
				"g.revise_trouble_times revise_trouble_times," +
				"g.revise_maintenance_length as revise_maintenance_length," +
				"g.revise_interdiction_time revise_interdiction_time " );
		sb.append(" from lp_trouble_guide_month g,contractorinfo c ");
		sb.append(" where g.contractor_id=c.contractorid ");
		if(contractorId!=null&&!"".equals(contractorId)){
			sb.append(" and g.contractor_id='"+contractorId+"' ");
		}
		sb.append(" and g.guide_type='"+quotaType+"' ");
		if(beginTime!=null&&!"".equals(beginTime)){
			sb.append(" and g.stat_month>=to_date('"+beginTime+"','yyyy/MM') ");
		}
		if(endTime!=null&&!"".equals(endTime)){
			sb.append(" and g.stat_month<to_date('"+endTime+"','yyyy/MM') ");
//			sb.append(" and g.stat_month<=to_date('"+endTime+"','yyyy/MM') ");//去掉等于的值，缩小范围
		}
		//sb.append(" group by contractor_id,contractorname ");
		sb.append(" order by contractor_id,g.stat_month ");
		List list =  getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
		return list;
	}


	
	
	/**
	 * 通过id获得对象
	 * @param id
	 * @return
	 */
	public TroubleGuideMonth getTroubleGuideMonthById(String id){
		return get(id);
	}
	
	public void updateTroubleGuideMonth(TroubleGuideMonth troubleGuideMonth){
		this.save(troubleGuideMonth);
	}

	

	
}
