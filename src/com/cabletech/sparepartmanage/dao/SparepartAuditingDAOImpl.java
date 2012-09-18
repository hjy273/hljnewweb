package com.cabletech.sparepartmanage.dao;

import java.util.List;

import com.cabletech.commons.generatorID.GeneratorID;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.HibernateDaoSupport;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.sparepartmanage.beans.SparepartAuditingBean;
import com.cabletech.sparepartmanage.domainobjects.SparepartAuditing;

/**
 * SparepartStorageAuditingDAOImpl ����������˵�DAO������
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
     * ��ӱ�������������Ϣ
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
     * ��ѯ�����������˼�¼�б�
     * 
     * @return List
     */
    public List queryForSparepartAuditingList() {
        return null;
    }
   
    /**
     * �����������˽��
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
     * ֻ�ı䱸��״̬
     */
    
    public boolean updateSparepartState(List serial,String state){
    	String serials = this.splitList(serial);
    	UpdateUtil util = null;
    	String sql = "update spare_part_storage set spare_part_state='"+state+"' " +
		" where serial_number in("+serials+")";
		try {
			util = new UpdateUtil();
			System.out.println("��˱���udpateSparestate=====:"+sql);
			util.executeUpdate(sql);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
        return false;
    }
    
    /**
     * �����˻���ʼ״̬
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
     * �������޵�״̬
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
     * �����������˽��
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
     * �������ı�����Ϣ
     */
    
    
}
