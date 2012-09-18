package com.cabletech.linepatrol.maintenance.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.fileupload.FileItem;
import org.springframework.stereotype.Repository;

import com.cabletech.commons.upload.module.UploadFileInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.maintenance.module.TestChipData;
import com.cabletech.linepatrol.maintenance.service.MainTenanceConstant;


@Repository
public class TestChipDataDAO extends HibernateDao<TestChipData, String> {
	
	
	public void mergeChipData(TestChipData data){
		this.getHibernateTemplate().merge(data);
	}
	
	
	public TestChipData saveChipData(TestChipData data){
		save(data);
		return data;
	}
	
	
	/**
	 * 删除纤序信息 
	 * @param cableDataId 备纤录入数据id
	 */
	public void deleteChipByCableDataId(String cableDataId){
		String sql="delete LP_TEST_CHIP_DATA l where l.test_cable_data_id='"+cableDataId+"'";
		this.getJdbcTemplate().execute(sql);
	}
	
	public Map<Object,TestChipData> getChipsByCableDataId(String cableDataId){
		String hql = " from TestChipData t where t.testCableDataId=?";
		List<TestChipData> lists = find(hql,cableDataId);
		Map<Object,TestChipData> map = new HashMap<Object,TestChipData>();
		for(TestChipData data :lists){
			String chipSeq = data.getChipSeq();
			int seq=0;
			if(chipSeq!=null && !"".equals(chipSeq)){
				seq = Integer.parseInt(chipSeq);
			}
			map.put(seq, data);
		}
		Object[]   chipKey   =     map.keySet().toArray();   
		Arrays.sort(chipKey);   
		return map;
	}
	
	public List<TestChipData> getCableChipsByCableDataId(String cableDataId){
		String hql = " from TestChipData t where t.testCableDataId=?";
		return find(hql,cableDataId);
	}
	
	/**
	 * 得到不合格的纤芯数量
	 * @param cableDataId
	 * @return
	 */
	public int getNonConformityChipNum(String cableDataId){
		String hql = " from TestChipData t where t.testCableDataId='"+cableDataId+"' and t.isEligible='0'";
		List<TestChipData> lists =this.getHibernateTemplate().find(hql);
		if(lists!=null){
			return lists.size();
		}
		return 0;
	}
	
	public List<UploadFileInfo> getFilesByIds(String entityId) {
		String hql = "select f from UploadFileInfo as f,AnnexAddOne as ao" +
				" where f.fileId = ao.fileId and ao.entityId='"+entityId+
						"' and ao.entityType='"+MainTenanceConstant.LP_TEST_CHIP_DATA+"'";
		return this.getHibernateTemplate().find(hql);
	}
	
	public void deleteChipsByCableDataId(String cableDataId){
		String sql = " delete lp_test_chip_data t where t.test_cable_data_id='"+cableDataId+"'";
		this.getJdbcTemplate().execute(sql);
	}
	
}
