package com.cabletech.commons.upload.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.commons.upload.module.UploadFileInfo;
import com.cabletech.ctf.dao.HibernateDao;
@Repository
public class UploadFileDao extends HibernateDao<UploadFileInfo,String>{
	//附件当前状态-正常状态为0（删除状态为1）
	private static String NORMAL_STATE="0";
	public void deleteFilePathByIds(List<String> ids){
		for(String id:ids){
			super.delete(id);
		}
	}

	public List<UploadFileInfo> getFilesByIds(String entityId, String entityType, String useable) {
		String hql = "select f from UploadFileInfo as f,AnnexAddOne as ao " +
				"where f.fileId = ao.fileId and ao.entityId=? and ao.entityType=? and ao.state=?" ;
		if(useable!=null && !"".equals(useable)){
			hql+=" and ao.isUsable =?";
			return super.find(hql, entityId,entityType,NORMAL_STATE, useable);
		}else{
			return super.find(hql, entityId,NORMAL_STATE,entityType);
		}
	}

	public List<UploadFileInfo> getImageFiles(String entityId, String entityType) {
		String hql = "select f from UploadFileInfo as f,AnnexAddOne as ao where f.fileId = ao.fileId and f.fileType='.jpg' and ao.entityId=? and ao.entityType=? and state=? ";
		return super.find(hql, entityId,entityType,NORMAL_STATE);
	}
	/**
	 * 通过附件在表annex_add_one 的id得到附件的上传路径
	 * @param id
	 * @return
	 */
	public String getSavePathByAnnexId(String id){
		String path="";
		List list = null;
		String hql="select u.savePath from UploadFileInfo u,AnnexAddOne a where u.fileId=a.fileId and a.id="+id ;
		list=this.getHibernateTemplate().find(hql);
		path=(String)list.get(0);
		return path;
	}
	
	/**
	 * 更新附件的使用状态 “1”是已经移除的，“0”为正常的
	 * @param id
	 */
	public void updateState(String id){
		String sql="update annex_add_one set state='1' where id="+id;
		super.getJdbcTemplate().update(sql);
	}
}
