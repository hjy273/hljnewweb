package com.cabletech.planinfo.services;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.planinfo.dao.StencilDAOImpl;
import com.cabletech.planinfo.domainobjects.StencilTaskPoint;

public class StencilTaskBO extends BaseBisinessObject{
	private Logger logger = Logger.getLogger("StencilTaskBO");
	/**
	 * �������ת��Ϊ��,�ָ���ַ���.��ҳ��ѡȡʹ��.
	 * @param s_taskpoint
	 * @return
	 */
	public String getStrTaskPoint(List s_taskpoint) {
		StringBuffer str_taskpoint = new StringBuffer();//��ŵ�id�ַ���
		for(int i=0;i<s_taskpoint.size();i++){
			StencilTaskPoint taskpoint = (StencilTaskPoint)s_taskpoint.get(i);
			str_taskpoint.append(taskpoint.getPointid().toString());
			str_taskpoint.append(",");
		}
		return str_taskpoint.toString();
	}
	/**
	 * ��ȡ�߶�id
	 * @param patrolid
	 * @param linelevel
	 * @return
	 */
	public List getSubLineId(String patrolid,String linelevel){
		TaskBO taskbo = new TaskBO();
		String sql = taskbo.getTypeSQL(patrolid, "2", linelevel);
		List sublineid = new ArrayList();
		try {
			QueryUtil query = new QueryUtil();
			ResultSet rs = query.executeQuery(sql);
			while(rs.next()){
				sublineid.add(rs.getString("sublineid"));
			}

			return sublineid;
		} catch (Exception e) {
			logger.error("��ȡsublineid ����"+e.getMessage());
			e.printStackTrace();
			return null;
		}
		
	}
	public String interceptStr(String patrolid,String linelevel,String taskid ,String str_subline){
		List sublineList = getSubLineId(patrolid,linelevel);
		logger.info("taskid "+taskid);
		logger.info("before delete "+str_subline);
		StringBuffer str = new StringBuffer(str_subline);
		// to  do delete "strsubline" �еĶ���
		for(int i=0;i<sublineList.size();i++){		
			String strTemp = sublineList.get(i).toString();
			int start_index=str.indexOf(strTemp);
			if(start_index!=-1){
				str = str.delete(start_index, start_index+strTemp.length()+1);
			}
		}	
		str_subline = str.toString();
		logger.info("after delete "+str_subline);
		return str_subline;
		
	}
}
