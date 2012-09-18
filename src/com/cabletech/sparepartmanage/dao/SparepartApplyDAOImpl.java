package com.cabletech.sparepartmanage.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;

import com.cabletech.commons.hb.HibernateDaoSupport;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.sparepartmanage.domainobjects.SparepartApply;
import com.cabletech.sparepartmanage.domainobjects.SparepartApplyF;
import com.cabletech.sparepartmanage.domainobjects.SparepartApplyS;
import com.cabletech.sparepartmanage.domainobjects.SparepartConstant;

/**
 * SparepartStorageApplyDAOImpl 备件申请的DAO操作类
 * 
 */
public class SparepartApplyDAOImpl extends HibernateDaoSupport {
	/**
	 * 添加备件申请
	 * 
	 * @param apply
	 *            SparepartStorageApply
	 * @return SparepartStorageApply
	 * @throws Exception
	 */
	public SparepartApply insertSparepartApply(SparepartApply apply) throws Exception {
		if (!isExist(apply.getTid())) {
			super.getHibernateTemplate().save(apply);
			return apply;
		} else {
			return null;
		}
	}


	/**
	 * 根据申请编号载入备件申请信息
	 * 
	 * @param applyId
	 *            String
	 * @return SparepartStorageApply
	 * @throws Exception
	 */
	public SparepartApplyS loadSparepartApplyS(String applyId) throws Exception {
		if (isExist(applyId)) {
			return (SparepartApplyS) super.getHibernateTemplate().load(SparepartApplyS.class, applyId);
		} else {
			return null;
		}
	}

	public SparepartApplyF loadSparepartApplyF(String applyId) throws Exception {
		if (isExistF(applyId)) {
			return (SparepartApplyF) super.getHibernateTemplate().load(SparepartApplyF.class, applyId);
		} else {
			return null;
		}
	}

	public List loadSparepartApplysByFId(String applyFId){       
		List list = new ArrayList();
		String hql = "from SparepartApplyS s where s.ftid='"+applyFId+"' order by s.serialNumber";
		try {
			list = this.getHibernateTemplate().find(hql);
		} catch (Exception e) {
			System.out.println("loadSparepartApplysByFId出现异常:"+hql);
			e.printStackTrace();
		}
		return list;

	}

	public String getPatrolgroupNameById(String id){
		String name = "";
		String sql = "select patrolname from patrolmaninfo where patrolid='"+id+"' ";
		QueryUtil queryUtil = null;
		List list = new ArrayList();
		try{
			queryUtil = new QueryUtil();
			list = queryUtil.queryBeans(sql);
			if(list !=null &&list.size()>0){
				BasicDynaBean bean = (BasicDynaBean)list.get(0);
				name = (String) bean.get("patrolname");
			}

		}catch(Exception e){
			e.getStackTrace();
		}
		return name;
	}

	public String getUserNameById(String id){
		String name = "";
		String sql = "select username from userinfo where userid='"+id+"' ";
		QueryUtil queryUtil = null;
		List list = new ArrayList();
		try{
			queryUtil = new QueryUtil();
			list = queryUtil.queryBeans(sql);
			if(list !=null &&list.size()>0){
				BasicDynaBean bean = (BasicDynaBean)list.get(0);
				name = (String) bean.get("username");
			}

		}catch(Exception e){
			e.getStackTrace();
		}
		return name;
	}

	/**
	 * 根据查询条件查询备件申请列表
	 * 
	 * @return List
	 */
	public List queryForSparepartApplyList() {
		return null;
	}

	/**
	 * 根据系统编号查询是否存在该系统编号的备件申请
	 * 
	 * @param applyId
	 *            String
	 * @return boolean
	 * @throws Exception
	 */
	public boolean isExist(String applyId) throws Exception {
		String hql = "from SparepartApplyS a where a.tid='" + applyId + "'";
		List list = super.getHibernateTemplate().find(hql);
		if (list != null && list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isExistF(String applyId) throws Exception {
		String hql = "from SparepartApplyF a where a.tid='" + applyId + "'";
		List list = super.getHibernateTemplate().find(hql);
		if (list != null && list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 解析字符串数组为以‘，’分割的字符串
	 * @param seriNums
	 * @param position
	 */

	public String splitArry(String[] array){
		String serials = "";
		for(int i = 0;i<array.length;i++){
			if(i == array.length-1){
				serials+= "'"+array[i].trim()+"'";
			}else{
				serials+= "'"+array[i].trim()+"',";
			}
		}
		return serials;
	}

	public void updateSparepartState(String[] seriNums,String position){
		String serials = this.splitArry(seriNums);
		String sql = "update spare_part_storage set spare_part_state='06', taken_out_storage=storage_position " +
		", storage_position='"+position+"' where serial_number in("+serials+")";
		UpdateUtil updateUtil;
		try {
			updateUtil = new UpdateUtil();
			updateUtil.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateSparepartState(String[] seriNums){
		String serials = this.splitArry(seriNums);
		String sql = "update spare_part_storage set spare_part_state='06' " +
		" where serial_number in("+serials+")";
		UpdateUtil updateUtil;
		try {
			updateUtil = new UpdateUtil();
			System.out.println("updateSparepartState sql2==="+sql);
			updateUtil.executeUpdate(sql);
		} catch (Exception e) {
			System.out.println("updateSparepartState修改状态失败:"+e.getMessage());
			e.printStackTrace();
		}

	}
	public void updateUsedSparepartState(String[] serialUseNums,String state){
		String serials = this.splitArry(serialUseNums);
		String sql = "update spare_part_storage set spare_part_state='"+state+"' where serial_number in("+serials+")";
		UpdateUtil updateUtil;
		try {
			updateUtil = new UpdateUtil();
			updateUtil.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateUsedSparepartState(String[] serialUseNums){
		String serials = this.splitArry(serialUseNums);
		String sql = "update spare_part_storage set spare_part_state='"+SparepartConstant.IS_REPLACE+"' where serial_number in("+serials+")";
		UpdateUtil updateUtil;
		try {
			updateUtil = new UpdateUtil();
			updateUtil.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updatSparepartState(String serialNumber,String state){
		String sql = "update spare_part_storage set spare_part_state='"+state+"' where serial_number ='"+serialNumber+"'";
		UpdateUtil updateUtil;
		try {
			updateUtil = new UpdateUtil();
			updateUtil.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updatSparepartState(List serials,String state){
		String serialNums = this.splitList(serials);
		if(serialNums ==null || serialNums.trim().length()==0){
			serialNums = null;
		}
		String sql = "update spare_part_storage set spare_part_state='"+state+"' where serial_number in("+serialNums+")";
		UpdateUtil updateUtil;
		try {
			updateUtil = new UpdateUtil();
			System.out.println("updatSparepartState sql1:"+sql);
			updateUtil.executeUpdate(sql);
		} catch (Exception e) {
			System.out.println("updatSparepartState 出现异常1:"+e.getMessage());
			e.printStackTrace();
		}
	}

	public List getSerialNmuByPositon(String option,String baseid,List applySs){
		String state = SparepartConstant.IS_RUNNING;
		List nums = new ArrayList();
		StringBuffer hql = new StringBuffer();
		hql.append("select s.serialNumber from SparepartStorage s where s.storagePosition='"+option+"' ");
		hql.append("and s.sparePartId='"+baseid+"' and ");
		hql.append("s.sparePartState='"+state+"'");
		hql.append("order by s.serialNumber");
		try {
			nums = this.getHibernateTemplate().find(hql.toString());
			System.out.println("getRunSerialNmuByPositon sql=="+hql.toString());
		} catch (Exception e) {
			System.out.println("根据位置查询序列号出现异常： "+e.getMessage());
			e.printStackTrace();
		}
		return nums;
	}


	public List getSpareInfosBySerialNums(String[] serialNums){
		String serials = this.splitArry(serialNums);
		String sql = "select s.tid ,s.taken_out_storage,s.serial_number from spare_part_storage s  where s.serial_number in("+serials+")";
		QueryUtil queryUtil;
		List list = new ArrayList();
		try {
			queryUtil = new QueryUtil();
			list = queryUtil.queryBeans(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
/*	public List getPositonsByPatrolgroupId(String patrolgroupId){
		List list = new ArrayList();
		String queryString = "select distinct f.applyUsePosition from SparepartApplyF f where f.patrolgroupId='"+patrolgroupId+"'";
		try {
			list = this.getHibernateTemplate().find(queryString);
			System.out.println("getPositonsByPatrolgroupId sql:"+queryString);
		} catch (Exception e) {
			System.out.println("根据巡检组查询申请使用位置出现异常： "+e.getMessage());
			e.printStackTrace();
		}
		return list;
	}
	*/
	
	public List getPositonsByPatrolgroupIdByChange(String patrolgroupId,String baseid){
		List list = new ArrayList();
		String queryString = "select distinct f.applyUsePosition from SparepartApplyF f where f.patrolgroupId='"+patrolgroupId+"'" +
				" and f.sparePartId='"+baseid+"'";
		try {
			list = this.getHibernateTemplate().find(queryString);
			System.out.println("getPositonsByPatrolgroupId sql:"+queryString);
		} catch (Exception e) {
			System.out.println("根据巡检组查询申请使用位置出现异常： "+e.getMessage());
			e.printStackTrace();
		}
		return list;
	}
	

	public void addSparePartApplyS(SparepartApplyS applyS){
		try {
			super.getHibernateTemplate().save(applyS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addSparePartApplyF(SparepartApplyF applyF){
		try {
			super.getHibernateTemplate().save(applyF);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateSparePartApplyF(SparepartApplyF applyF){
		try{
			super.getHibernateTemplate().update(applyF);
		}catch(Exception e){
			System.out.println("updateSparePartApplyF出现异常："+e.getMessage());
			e.getStackTrace();
		}
	}

	public void updateSparePartApplyS(SparepartApplyS applyS){
		try{
			super.getHibernateTemplate().update(applyS);
		}catch(Exception e){
			e.getStackTrace();
		}
	}

	public void deleteApplyF(SparepartApplyF applyF){
		try {
			this.getHibernateTemplate().delete(applyF);
		} catch (Exception e) {
			System.out.println("deleteApplyF删除申请表父表失败=======");
			e.printStackTrace();
		}
	}

	public void deleteApplyS(SparepartApplyS applyS){
		try {
			this.getHibernateTemplate().delete(applyS);
		} catch (Exception e) {
			System.out.println("deleteApplyS删除申请表子表失败=======");
			e.printStackTrace();
		}
	}

	public void deleteApplyS(String id){
		String sql = "delete from  spare_part_apply_s where ftid='"+id+"'";
		UpdateUtil updateUtil;
		try {
			updateUtil = new UpdateUtil();
			System.out.println("deleteApplyS sql1:"+sql);
			updateUtil.executeUpdate(sql);
		} catch (Exception e) {
			System.out.println("deleteApplyS删除申请表子表失败======="+e.getMessage());
			e.printStackTrace();
		}
	}

	public String splitList(List list){
		String serials = "";
		for(int i =0;i<list.size();i++){
			if(i == list.size()-1){
				serials+= "'"+list.get(i)+"'";
			}else{
				serials+= "'"+list.get(i)+"',";
			}
		}
		return serials;
	}
	
	

}
