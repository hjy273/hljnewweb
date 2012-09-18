package com.cabletech.sparepartmanage.dao;

import java.util.List;

import com.cabletech.commons.generatorID.GeneratorID;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.HibernateDaoSupport;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.sparepartmanage.beans.SparepartAuditingBean;
import com.cabletech.sparepartmanage.domainobjects.SparepartAuditing;

/**
 * SparepartStorageAuditingDAOImpl 备件申请审核的DAO操作类
 * 
 */
public class SparepartAuditingDAOImpl extends HibernateDaoSupport {
	private GeneratorID generatorID = new OracleIDImpl();
	
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
	
	
	public void add(SparepartAuditing au){
		try {
			this.getHibernateTemplate().save(au);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
    /**
     * 添加备件申请的审核信息
     * 
     * @param auditing
     *            SparepartStorageAuditing
     * @return SparepartStorageAuditing
     * @throws Exception
     */
    public SparepartAuditing insertSparepartAuditing(SparepartAuditing auditing)
            throws Exception {
        super.getHibernateTemplate().save(auditing);
        return auditing;
    }

    /**
     * 查询备件申请的审核记录列表
     * 
     * @return List
     */
    public List queryForSparepartAuditingList() {
        return null;
    }
   
    /**
     * 更新申请表审核结果
     */
    public boolean updateAuditngState(String tid,String state){
    	UpdateUtil util = null;
    	String sql = "update spare_part_apply_f apply set apply.AUDITING_STATE='"+state+"' where apply.tid='"+tid+"'";
		try {
			util = new UpdateUtil();
			util.executeUpdate(sql);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
        return false;
    }
    
    /**
     * 只改变备件状态
     */
    
    public boolean updateSparepartState(List serial,String state){
    	String serials = this.splitList(serial);
    	UpdateUtil util = null;
    	String sql = "update spare_part_storage set spare_part_state='"+state+"' " +
		" where serial_number in("+serials+")";
		try {
			util = new UpdateUtil();
			System.out.println("审核备件udpateSparestate=====:"+sql);
			util.executeUpdate(sql);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
        return false;
    }
    
    /**
     * 更新退还初始状态
     * @return
     */
    public boolean updateUsedSparepartState(List usedSerials,String position,String state){
    	String serials = this.splitList(usedSerials);
    	UpdateUtil util = null;
    	String sql = "update spare_part_storage set spare_part_state='"+state+"', taken_out_storage='' " +
		", storage_position=init_storage,depart_id=(select deptid from userinfo where userid=storage_person)" +
		"  where serial_number in("+serials+")";
		try {
			util = new UpdateUtil();
			util.executeUpdate(sql);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
        return false;
    }
    
    /**
     * 更新送修的状态
     * @return
     */
    public boolean updateUsedSparepartStateRepair(List usedSerials,String position,String state){
    	String serials = this.splitList(usedSerials);
    	UpdateUtil util = null;
    	String sql = "update spare_part_storage set spare_part_state='"+state+"', taken_out_storage='' " +
		", storage_position=init_storage where serial_number in("+serials+")";
		try {
			util = new UpdateUtil();
			util.executeUpdate(sql);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
        return false;
    }
    
    
    
    
    /**
     * 更新申请表审核结果
     */
    public boolean updateSparepartState(List usedSerials,String position,String state){
    	String serials = this.splitList(usedSerials);
    	UpdateUtil util = null;
    	String sql = "update spare_part_storage set spare_part_state='"+state+"', taken_out_storage=storage_position " +
		", storage_position='"+position+"' where serial_number in("+serials+")";
		try {
			util = new UpdateUtil();
			util.executeUpdate(sql);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
        return false;
    }
    
    /**
     * 被更换的备件信息
     */
    
    
}
