package com.cabletech.sparepartmanage.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;

import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.HibernateDaoSupport;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.sparepartmanage.beans.SparepartBaseInfoBean;
import com.cabletech.sparepartmanage.beans.SparepartSavePositionBean;
import com.cabletech.sparepartmanage.beans.SparepartStorageBean;
import com.cabletech.sparepartmanage.domainobjects.SparepartApplyS;
import com.cabletech.sparepartmanage.domainobjects.SparepartConstant;
import com.cabletech.sparepartmanage.domainobjects.SparepartStorage;
import com.cabletech.sparepartmanage.services.SeparepartBaseInfoService;

/**
 * SparepartStorageDAOImpl 备件库存的DAO操作类
 * 
 */
public class SparepartStorageDAOImpl extends HibernateDaoSupport {
	private static Logger logger = Logger.getLogger(SeparepartBaseInfoDAO.class
			.getName());
	private SeparepartBaseInfoService sbo = new SeparepartBaseInfoService();
	private SparepartApplyDAOImpl applydao = new SparepartApplyDAOImpl();


	/**
	 * 插入备件库存
	 * 
	 * @param storage
	 *            SparepartStorage
	 * @return SparepartStorage
	 * @throws Exception
	 */
	public SparepartStorage insertSparepartStorage(SparepartStorage storage)
	throws Exception {
		if (!isExist(storage.getTid())) {
			super.getHibernateTemplate().save(storage);
			return storage;
		} else {
			return null;
		}
	}


	/**
	 * 根据系统编号查询是否存在该编号的库存
	 * 
	 * @param storageId
	 *            String
	 * @return boolean
	 * @throws Exception
	 */
	public boolean isExist(String storageId) throws Exception {
		String hql = "from SparepartStorage s where s.tid='" + storageId + "'";
		List list = super.getHibernateTemplate().find(hql);
		if (list != null && list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public List findPosition(){
		String sql ="from SparepartSavePositionBean s where s.deptType='1'";
		List positions = null;
		try {
			positions = this.getHibernateTemplate().find(sql);		
			logger.info("sql:"+sql);
		} catch (Exception e) {
			logger.info("获取备件存放位置出现异常："+e.getMessage());
			e.printStackTrace();
		}
		return positions;
	}

	public List findPositionForW(){
		String sql ="from SparepartSavePositionBean s where s.deptType='2'";
		List positions = null;
		try {
			positions = this.getHibernateTemplate().find(sql);		
			logger.info("sql:"+sql);
		} catch (Exception e) {
			logger.info("获取备件存放位置出现异常："+e.getMessage());
			e.printStackTrace();
		}
		return positions;
	}

	public List findPositionForWReturnSpare(String id){
		String sql = "select distinct s.storage_position,p.name" +
		" from spare_part_storage s,spare_part_save_position p " +
		"where s.spare_part_id='"+id+"' and s.spare_part_state='02' and p.id=s.storage_position";
		List positions = null;
		QueryUtil queryUtil = null;
		try {
			queryUtil = new QueryUtil();
			positions = queryUtil.queryBeans(sql);
			logger.info("sql:"+sql);
		} catch (Exception e) {
			logger.info("获取备件存放位置出现异常："+e.getMessage());
			e.printStackTrace();
		}
		return positions;
	}

	public boolean batchInsertSparepartStorage(List list) throws Exception {
		return batchSaveObject(list);
	}

	/**
	 * 保存一批list对象
	 * @param list
	 * @return
	 */
	private boolean batchSaveObject(List list) {
		logger.info("batchSaveObject==============");
		final Session session = this.getSession();
		final Iterator iter = list.iterator();
		try {
			final OracleIDImpl ora = new OracleIDImpl();
			final String[] pid = ora.getSeqs("spare_part_storage", 20, list.size());
			int i = 0;
			UpdateUtil exec = new UpdateUtil();
			//exec.setAutoCommitFalse();
			while (iter.hasNext()) {
				SparepartStorage toBean = new SparepartStorage();
				SparepartStorageBean fromBean = (SparepartStorageBean) iter.next();
				BeanUtil.objectCopy(fromBean, toBean);
				toBean.setTid(pid[i++]);
				toBean.setStorageDate(new Date());
				toBean.setSparePartState(SparepartConstant.MOBILE_WAIT_USE);
				//insertSparepartStorage(toBean,session);
				toBean.setInitStorage(toBean.getStoragePosition());
				insertSparepartStorage(toBean);

				sbo.modState(toBean.getSparePartId(), SparepartConstant.NOT_ALLOW_DELETE,exec);
			}

			exec.commit();
			session.flush();
			session.clear();
			return true;
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public String getSerialNumber(String id){
		String hql = "from SparepartStorage s where s.serialNumber='"+id+"'";
		try {
			List list = this.getHibernateTemplate().find(hql);
			if(list!= null && list.size()>0){
				return "1";
			}
		} catch (Exception e) {
			logger.info("查询移动入库的备件序列号:"+e.getMessage());
			e.printStackTrace();
		}
		return "0";
	}
	
	public List getStorageBySparepartId(String id){
		String sql = "from SparepartStorage s where s.sparePartId='"+id+"'";
		try {
			List list = this.getHibernateTemplate().find(sql);
			return list;
		} catch (Exception e) {
			logger.info("查询改备件的库存数量:"+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 载入备件库存
	 * 
	 * @param storageId
	 *            String
	 * @return SparepartStorage
	 * @throws Exception
	 */
	public SparepartStorage loadSparepartStorage(String storageId)
	throws Exception {
		if (isExist(storageId)) {
			return (SparepartStorage) super.getHibernateTemplate().load(
					SparepartStorage.class, storageId);
		} else {
			return null;
		}
	}

	public SparepartBaseInfoBean getSparePart(String id)	throws Exception {
		return (SparepartBaseInfoBean) super.getHibernateTemplate().load(SparepartBaseInfoBean.class, id);
	}

	public List findSparepartsByBaseId(String id) {
		List lists = new ArrayList();
		String sql = "select sto.serial_number,sto.storage_remark,pos.name,u.username" +
		" from spare_part_storage sto,spare_part_save_position pos,userinfo u " +
		"where pos.id=sto.storage_position and u.userid=sto.storage_person and sto.spare_part_state='01' and sto.spare_part_id='"+id+"'";
		QueryUtil query = null;
		logger.info("find sql=========== "+sql);
		try{
			query = new QueryUtil();
			lists = query.queryBeans(sql);
		}catch(Exception e){
			logger.info("在获取备件信息出现异常:"+e.getMessage());
			e.getStackTrace();
		}

		return lists;
	}


	public List findPositionByBaseId(String id) {
		String sql = "select distinct s.init_storage,p.name from spare_part_storage s,spare_part_save_position p " +
		"where s.spare_part_id='"+id+"' and s.spare_part_state='01' and p.id = s.init_storage";
		QueryUtil query = null;
		List list = null;
		try{
			query = new QueryUtil();
			list = query.queryBeans(sql);
		}catch( Exception ex ){
			logger.error("查询异常："+ex.getMessage());
			ex.printStackTrace();
		}
		return list;
	}


	public SparepartBaseInfoBean  findSparepartInfoById(String id){
		SparepartBaseInfoBean baseBean = new SparepartBaseInfoBean();
		try {
			baseBean = (SparepartBaseInfoBean)super.getHibernateTemplate().load(SparepartBaseInfoBean.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return baseBean;
	}

	public String getPositionNameById(String id){
		String name = "";
		try {
			SparepartSavePositionBean bean = (SparepartSavePositionBean)this.getHibernateTemplate().load(SparepartSavePositionBean.class, id);
			if(bean !=null){
				name = bean.getName();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}

	public String getPositionIdsByName(String name){
		String sql="select s.id id from spare_part_save_position s where s.name like '%"+name+"%'";//位置为移动或代维库存的位置
		QueryUtil query = null;
		List list = new ArrayList();
		String positonIds = "";
		try{
			query = new QueryUtil();
			list = query.queryBeans(sql);
			List lists = new ArrayList();
			if(list !=null && list.size()>0){
				for(int i = 0;i<list.size();i++){
					BasicDynaBean bean = (BasicDynaBean) list.get(i);
					String id = (String) bean.get("id");
					lists.add(id);
					
				}
			}
			positonIds = this.splitList(lists);
		}catch(Exception e){
			System.out.println("getPositionIdsByName出现异常:"+e.getMessage());
			e.getStackTrace();
		}
		return positonIds;
	}

	/**
	 * 删除备件库存
	 * 
	 * @param storage
	 *            SparepartStorage
	 * @return SparepartStorage
	 * @throws Exception
	 */
	public SparepartStorage deleteSparepartStorage(SparepartStorage delStorage)
	throws Exception {
		if (isExist(delStorage.getTid())) {
			SparepartStorage storage = loadSparepartStorage(delStorage.getTid());
			super.getHibernateTemplate().delete(storage);
			return storage;
		} else {
			return null;
		}
	}

	/**
	 * 修改备件库存
	 * 
	 * @param newStorage
	 *            SparepartStorage
	 * @return SparepartStorage
	 * @throws Exception
	 */
	public SparepartStorage updateSparepartStorage(SparepartStorage newStorage)
	throws Exception {
		if (isExist(newStorage.getTid())) {
			SparepartStorage storage = loadSparepartStorage(newStorage.getTid());
			storage.clone(newStorage);
			super.getHibernateTemplate().saveOrUpdate(storage);
			return storage;
		} else {
			return null;
		}
	}

	public List getRunSerialNmuByPositon(String option,String baseid,String state){
		List nums = new ArrayList();
		StringBuffer hql = new StringBuffer();
		hql.append("select s.serialNumber from SparepartStorage s where s.storagePosition='"+option+"' ");
		hql.append("and s.sparePartId='"+baseid+"' and ");
		if(state.equals("editform")){
			//hql.append("s.sparePartState in('"+SparepartConstant.IS_RUNNING+"','"+SparepartConstant.IS_REPLACE+"')");
			
		}else{
			hql.append("s.sparePartState='"+state+"'");
		}
		hql.append("order by s.serialNumber");
		try {
			nums = this.getHibernateTemplate().find(hql.toString());
			logger.info("getRunSerialNmuByPositon sql=="+hql.toString());
		} catch (Exception e) {
			logger.info("根据位置查询序列号出现异常： "+e.getMessage());
			e.printStackTrace();
		}
		return nums;
	}

	public List getSerialNumByPositonByApply(String option,String baseid,String state,String applyFId){
		List nums = new ArrayList();
		StringBuffer hql = new StringBuffer();
		hql.append("select s.serialNumber from SparepartStorage s where s.storagePosition='"+option+"' ");
		hql.append("and s.sparePartId='"+baseid+"' ");
		if(state.equals("editform")){
			hql.append(" and (s.sparePartState ='"+SparepartConstant.IS_RUNNING+"' ");
			List usedSerials = new ArrayList();
			if(applyFId !=null && applyFId.trim().length()>0){
				List applySs = applydao.loadSparepartApplysByFId(applyFId);
				for(int i = 0;i<applySs.size();i++){
					SparepartApplyS applys = (SparepartApplyS) applySs.get(i);
					usedSerials.add(applys.getUsedSerialNumber());
				}
				
			}
			if(usedSerials !=null && usedSerials.size()>0){
				String serial = this.splitListByEditApply(usedSerials);
				hql.append(" or s.serialNumber in("+serial+") )");
			}else{
				hql.append(" )");
			}
		}else{
			hql.append(" and s.sparePartState='"+state+"'");
		}
		hql.append(" order by s.serialNumber");
		try {
			nums = this.getHibernateTemplate().find(hql.toString());
			logger.info("getRunSerialNmuByPositon sql=="+hql.toString());
		} catch (Exception e) {
			logger.info("根据位置查询序列号出现异常： "+e.getMessage());
			e.printStackTrace();
		}
		return nums;
	}
	
	public List getSerialNmuByPositon(String option,String baseid){
		List nums = new ArrayList();
		String hql="select s.serialNumber from SparepartStorage s where s.storagePosition='"+option+"'" +
		" and s.sparePartId='"+baseid+"' and s.sparePartState='01' order by s.serialNumber ";
		try {
			nums = this.getHibernateTemplate().find(hql);
		} catch (Exception e) {
			logger.info("根据位置查询序列号出现异常： "+e.getMessage());
			e.printStackTrace();
		}
		return nums;
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
				serials+= "'"+array[i]+"'";
			}else{
				serials+= "'"+array[i]+"',";
			}
		}
		return serials;
	}

	public String splitListByEditApply(List list){
		String serials = "";
		if(list !=null && list.size()>0){
			for(int i = 0;i<list.size();i++){
				if(i == list.size()-1){
					serials+= "'"+list.get(i)+"'";
				}else{
					serials+= "'"+list.get(i)+"',";
				}
			}
		}
		return serials;
	}
	
	public String splitList(List list){
		String serials = "";
		if(list !=null && list.size()>0){
			for(int i = 0;i<list.size();i++){
				if(i == list.size()-1){
					serials+= list.get(i);
				}else{
					serials+= list.get(i)+",";
				}
			}
		}
		return serials;
	}


	public String getDeptIdByUserId(){
		String storagePerson = "select u.deptid from spare_part_storage s,userinfo u where u.userid=s.storage_person";
		QueryUtil query;
		String deptid = "";
		List list = new ArrayList();
		try {
			query = new QueryUtil();
			list = query.queryBeans(storagePerson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(list !=null && list.size()>0){
			BasicDynaBean bean =(BasicDynaBean)list.get(0); 
			deptid = (String) bean.get("deptid");
		}
		return deptid;
	}

	public boolean updateSpareState(String[] serialnums,String person,String position){
		String serials = this.splitArry(serialnums);
		String deptid = getDeptIdByUserId();
		System.out.println("deptid=============== "+deptid);
		String sql = "update spare_part_storage set spare_part_state='"+SparepartConstant.MOBILE_WAIT_USE+"',storage_position='"+position+"'," +
		" taken_out_storage='',init_storage='"+position+"',storage_remark='',draw_person='',depart_id='"+deptid+"'" +
		"where serial_number in("+serials+")";
		UpdateUtil updateUtil;
		boolean result = false;
		try{
			updateUtil = new UpdateUtil();
			updateUtil.executeUpdate(sql);
			result = true;
		}catch(Exception e){
			e.getStackTrace();
		}
		return result;
	}

	public boolean scrappedStorage(String storageId){
		String sql= "update spare_part_storage set spare_part_state='"+SparepartConstant.IS_DISCARDED+"' where tid='"+storageId+"'";
		UpdateUtil update = null;
		boolean falg = false;
		try{
			update = new UpdateUtil();
			logger.info("把备件状态修改为报废:"+sql);
			update.executeUpdate(sql);
			falg = true;
		}catch(Exception e){
			logger.info("执行备件为报废出现异常："+e.getMessage());
			e.getStackTrace();
		}
		return falg;
	}

}
