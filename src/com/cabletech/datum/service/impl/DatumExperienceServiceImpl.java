package com.cabletech.datum.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.services.DBService;
import com.cabletech.datum.bean.DatumExperience;
import com.cabletech.datum.bean.DatumSystem;
import com.cabletech.datum.dao.BaseDatumDao;
import com.cabletech.datum.dao.DatumExperienceDao;
import com.cabletech.datum.service.DatumExperienceService;
import com.cabletech.uploadfile.DelUploadFile;

public class DatumExperienceServiceImpl implements DatumExperienceService {
	private static Logger logger = Logger.getLogger("DatumExperienceServiceImpl");
	private static BaseDatumDao dao = new DatumExperienceDao();

	public boolean delDatumExperience(String id) {
		String sql = "delete datumexperience where id='" + id + "'";
		int count = DelUploadFile.delFiles(getDatumExperience(id).getAdjunct());//É¾³ý¸½¼þ
		return dao.updateData(sql);
	}

	public boolean saveDatumExperience(DatumExperience data) {
		data.setId(new DBService().getSeq("datumexperience", 10));
		String sql = "insert into datumexperience (ID,FILENAME,DOCuMENTTYPE,DESCRIPTION,VALIDATETIME,ADJUNCT,USERID,CREATEDATE,REGIONID) "
				+ " values ('"
				+ data.getId()
				+ "','"
				+ data.getDocumentname()
				+ "','"
				+ data.getDocumenttype()
				+ "','"
				+ data.getDescription()
				+ "',"
				+ "to_date('"
				+ data.getValidatetime()
				+ "','YYYY/mm/dd hh24:mi:ss'),'"
				+ data.getAdjunct()
				+ "','"
				+ data.getUserid()
				+ "',sysdate,'" + data.getRegionid() + "')";

		return dao.updateData(sql);
	}

	public boolean updateDatumExperience(DatumExperience data) {
		String sql = "update datumexperience set filename='"
				+ data.getDocumentname() + "',documenttype='"
				+ data.getDocumenttype() + "',description='"
				+ data.getDescription() + "'," + " validatetime = to_date('"
				+ data.getValidatetime() + "','YYYY/mm/dd hh24:mi:ss'),createdate=sysdate,adjunct='" + data.getAdjunct()
				+ "' where id='" + data.getId() + "'";
		return dao.updateData(sql);
	}

	public DatumExperience getDatumExperience(String id) {
		String sql = "select ID,FILENAME,DOCUMENTTYPE,DESCRIPTION, to_char(VALIDATETIME,'YYYY/mm/dd hh24:mi:ss') VALIDATETIME,ADJUNCT,USERID,  to_char(CREATEDATE,'YYYY/mm/dd hh24:mi:ss') CREATEDATE, REGIONID from datumexperience where id='"+id+"'";
		
		return (DatumExperience) dao.queryData(sql);
	}

	public List queryDatumExperience(DatumExperience bean, UserInfo user, String rootRegionid) {
		String sql = "select d.ID,d.FILENAME,d.DOCUMENTTYPE,dt.typename,d.DESCRIPTION, to_char(d.VALIDATETIME,'YYYY/mm/dd hh24:mi:ss') VALIDATETIME,d.ADJUNCT,d.USERID,  to_char(d.CREATEDATE,'YYYY/mm/dd hh24:mi:ss') CREATEDATE, d.REGIONID,r.regionname  from datumexperience d,region r,documenttype dt where dt.code = d.documenttype and r.regionid = d.regionid and d.regionid in ('"+rootRegionid+"','"+user.getRegionid()+"')";
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
