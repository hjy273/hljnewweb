package com.cabletech.sysmanage.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.commons.upload.action.DownloadAction;
import com.cabletech.commons.upload.module.AnnexAddOne;
import com.cabletech.ctf.dao.HibernateDao;
@Repository("annexDao")
public class AnnexDao extends HibernateDao<AnnexAddOne,String>{
    private Logger logger = Logger.getLogger(AnnexDao.class.getName());
    /**
     * 获得模块类型
     * @return
     */
	public List<String> getModuleCatalog(){
		List<String> list=new ArrayList<String>();
		String hql="select distinct moduleCatalog from AnnexAddOne";
		list=this.getHibernateTemplate().find(hql);
		return list;
	}
	/**
	 * 按条件查询附件
	 * @param moduleCatalog
	 * @param uploader
	 * @param originalName
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public List getAnnex(String moduleCatalog, String uploader,
			String originalName, String beginTime, String endTime) {
		// TODO Auto-generated method stub
		
		List list=new ArrayList();
		String hql="";
		hql+="select a.fileId,a.id,a.module_catalog,f.originalName,f.fileType,u.userName,a.upload_Date,to_char(f.filesize/1024,'FM99999990.099') as filesize from Annex_Add_One a,UserInfo u ,FilepathInfo f where f.fileId=a.fileId and a.state='0' and u.userID=a.uploader";
		if(moduleCatalog!=null&&!moduleCatalog.equals("")){
			hql+=" and a.module_catalog='"+moduleCatalog+"'";
		}
		if(uploader!=null&&!uploader.equals("")){
			hql+=" and u.username='"+uploader+"'";
		}
		if(beginTime!=null&&!beginTime.equals("")){
			hql+=" and a.upload_Date>to_date('" + beginTime+ "','yyyy/MM/dd hh24:mi:ss')";
		}
		if(endTime!=null&&!endTime.equals("")){
			hql+=" and a.upload_Date<to_date('" + endTime+ "','yyyy/MM/dd hh24:mi:ss')";
		}
		if(originalName!=null&&!originalName.equals("")){
			hql+=" and f.originalName like '%" + originalName + "%' ";
		}
		hql+=" order by a.module_catalog,a.entity_type,a.entity_id";
		System.out.println(hql);
		list=super.getJdbcTemplate().queryForBeans(hql);
		return list;
	}
}
