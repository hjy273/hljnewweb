package com.cabletech.linepatrol.trouble.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.trouble.module.TroubleInfo;
import com.cabletech.linepatrol.trouble.services.TroubleConstant;

@Repository
public class TroubleInfoDAO extends HibernateDao<TroubleInfo, String> {


	
	public TroubleInfo getTroubleById(String id){
		TroubleInfo troulble = get(id);
		initObject(troulble);
		return troulble;
	}
	
	/**
	 * �õ���ʱ����Ĺ����ɵ�
	 * @param user
	 * @return
	 */
	public List<TroubleInfo> getTempSaveTroubles(UserInfo user){
		String hql = " from TroubleInfo t where t.sendManId=? and troubleState=?";
		return find(hql,user.getUserID(),TroubleConstant.TEMP_SAVE);
	}
	
	/**
	 * ��ѯ��ǰ����ɵ�������Ϣ
	 * @return
	 */
	/*public Integer getTroubleInfoNumber(UserInfo userinfo) {
		StringBuffer buf = new StringBuffer();
		 ִ�и��ݵ�ǰ�û���ѯ��ǰ�·ݵĹ����ɵ�������Ϣ
		buf.append(" select count(*) num ");
		buf.append(" from lp_trouble_info where 1=1 ");
		buf.append(" and trouble_send_time>=trunc(sysdate,'mm') ");
		buf.append(" and trouble_send_time<trunc(add_months(sysdate,1),'mm') ");
		buf.append(" select count(*) num ");
		buf.append(" from lp_trouble_info t,userinfo u ");
		buf.append(" where u.userid=t.send_man_id ");
		buf.append(" and to_char(t.trouble_send_time,'yyyy') like to_char(sysdate,'yyyy')");
		buf.append(" and u.deptid='"+userinfo.getDeptID()+"'");
	//	buf.append(" and t.trouble_state !='"+TroubleConstant.TEMP_SAVE+"'");
		buf.append(" and (t.trouble_state is null or t.trouble_state !='"+TroubleConstant.TEMP_SAVE+"')");
		logger.info("��ѯ��ǰ����ɵ�������Ϣ:"+buf.toString());
		int num = this.getJdbcTemplate().queryForInt(buf.toString());
		return num+1;
	}*/
	public List<TroubleInfo> getTroubles4Dept(String userid){
		String hql = "from TroubleInfo where to_char(trouble_send_time,'yyyy') = to_char(sysdate,'yyyy') and send_man_id=?";
		return super.find(hql, userid);
	}

	/**
	 * ��������ɵ��Ĵ�ά��˾�ڸ�����
	 * @param troubleid
	 */
	public void saveUnitList(String troubleid){

	}

	/**
	 * ��ѯ�����ɵ��Ĵ�ά��˾�븺����
	 * @param troubleid
	 * @return
	 */
	public List findTroubleUnitById(String troubleid){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer(); 
		sb.append(" select c.contractorname ,unit.id unitid ");
		sb.append(" from lp_trouble_process_unit unit,contractorinfo c");	
		sb.append(" where unit.trouble_id=?");
		values.add(troubleid);
		sb.append(" and unit.process_unit_id=c.contractorid order by c.contractorid");
		return  this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}
	
	/**
	 * �����ϰ�ԭ��id��ѯ�ϰ�����
	 * @param reasonid
	 * @return
	 */
	public String getTroubleReasonName(String reasonid){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer(); 
		sb.append(" select lable from dictionary_formitem df where df.assortment_id=?");
		values.add(TroubleConstant.ASSORTMENT_TROUBLE_REASON);
		sb.append(" and  df.code=?");
		values.add(reasonid);
		List list= this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
		if(list!=null &&list.size()>0){
			BasicDynaBean bean = (BasicDynaBean) list.get(0);
			String reason = (String) bean.get("lable");
			return reason;
		}
		return "";
	}

	/**
	 * �����û�id��ѯ�û�
	 * @param userid
	 * @return
	 */
	public UserInfo getUserByUserIdAndDeptId(String userid,String deptid){
		String hql=" from UserInfo u where userID='"+userid+"' and deptID='"+deptid+"'";
		UserInfo user = (UserInfo)getSession().createQuery(hql).uniqueResult();
		return user;
	}


}
