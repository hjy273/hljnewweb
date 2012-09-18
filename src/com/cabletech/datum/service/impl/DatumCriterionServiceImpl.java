package com.cabletech.datum.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.services.DBService;
import com.cabletech.datum.bean.DatumCriterion;
import com.cabletech.datum.bean.DatumSystem;
import com.cabletech.datum.dao.BaseDatumDao;
import com.cabletech.datum.dao.DatumCriterionDao;
import com.cabletech.datum.service.DatumCriterionService;
import com.cabletech.uploadfile.DelUploadFile;

public class DatumCriterionServiceImpl implements DatumCriterionService {
	private static Logger logger = Logger.getLogger("DatumCriterionServiceImpl");
	private static BaseDatumDao dao = new DatumCriterionDao();

	public boolean delDatumCriterion(String id) {
		String sql = "delete datumcriterion where id='"+id+"'";
		int count = DelUploadFile.delFiles(getDatumCriterion(id).getAdjunct());//É¾³ý¸½¼þ
		return dao.updateData(sql);
	}

	public boolean saveDatumCriterion(DatumCriterion data) {
		data.setId(new DBService().getSeq("datumcriterion",10));
		String sql = "insert into datumcriterion (ID,FILENAME,DOCuMENTTYPE,DESCRIPTION,VALIDATETIME,ADJUNCT,USERID,CREATEDATE,REGIONID) "
					+" values ('"+data.getId()+"','"+data.getDocumentname()+"','"+data.getDocumenttype()+"','" +data.getDescription()+"',"
					+"to_date('"+data.getValidatetime()+"','YYYY/mm/dd hh24:mi:ss'),'"+data.getAdjunct()+"','"+data.getUserid()+"',sysdate,'"+data.getRegionid()+"')";
		
		return dao.updateData(sql);
	}

	public boolean updateDatumCriterion(DatumCriterion data) {
		String sql = "update datumcriterion set filename='"+data.getDocumentname()+"',documenttype='"+data.getDocumenttype()+"',description='"+data.getDescription()+"',"
		+ " validatetime = to_date( '"+data.getValidatetime()+"','YYYY/mm/dd hh24:mi:ss'),adjunct='"+data.getAdjunct()+"',createdate=sysdate where id='"+data.getId()+"'";
		return dao.updateData(sql);
	}

	public DatumCriterion getDatumCriterion(String id) {
		String sql = "select ID,FILENAME,DOCUMENTTYPE,DESCRIPTION, to_char(VALIDATETIME,'YYYY/mm/dd hh24:mi:ss') VALIDATETIME,ADJUNCT,USERID,  to_char(CREATEDATE,'YYYY/mm/dd hh24:mi:ss') CREATEDATE, REGIONID from DatumCriterion where id='"+id+"'";
		
		return (DatumCriterion) dao.queryData(sql);
	}

	public List queryDatumCriterion(DatumCriterion bean, UserInfo user, String rootRegionid) {
		String sql = "select d.ID,d.FILENAME,d.DOCUMENTTYPE,dt.typename,d.DESCRIPTION, to_char(d.VALIDATETIME,'YYYY/mm/dd hh24:mi:ss') VALIDATETIME,d.ADJUNCT,d.USERID,  to_char(d.CREATEDATE,'YYYY/mm/dd hh24:mi:ss') CREATEDATE, d.REGIONID,r.regionname  from DatumCriterion d,region r,documenttype dt where dt.code = d.documenttype and r.regionid = d.regionid and d.regionid in ('"+rootRegionid+"','"+user.getRegionid()+"')";
		if(!bean.getDocumentname().equals("")){
			sql += " and d.filename like %'"+bean.getDocumentname()+"'%";
		}
		if(!bean.getDocumenttype().equals("")){
			sql += " and d.documenttype = '"+bean.getDocumenttype()+"'";
		}
		
		logger.info("sql ->"+sql);
		return dao.queryList(sql);
	}

}
