package com.cabletech.linepatrol.resource.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.resource.model.DatumInfo;

@Repository
public class DatumInfoDao extends HibernateDao<DatumInfo, String> {
	public List<DatumInfo> getDatumList(UserInfo userInfo){
		
		String hql="select distinct d from DatumInfo d,UploadFileInfo f,AnnexAddOne a" +
				" where d.id=a.entityId and f.fileId=a.fileId and a.state=0 and a.entityType=?";
		if(userInfo.getDeptype().equals("1")){//查询资料更新的
			return find(hql,DatumInfo.TYPE);
		}else{
			return this.findBy("contractorId", userInfo.getDeptID());
		}
	}
	
	public String getPhoneFromUserid(String userId){
		String sql = "select phone from userinfo where userid='"+userId+"'";
		return (String)this.getJdbcTemplate().queryForObject(sql, String.class);
	}
}
