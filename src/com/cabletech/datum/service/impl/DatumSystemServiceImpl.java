package com.cabletech.datum.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.services.DBService;
import com.cabletech.datum.bean.DatumSystem;
import com.cabletech.datum.dao.BaseDatumDao;
import com.cabletech.datum.dao.DatumSystemDao;
import com.cabletech.datum.service.DatumSystemService;
import com.cabletech.uploadfile.DelUploadFile;

public class DatumSystemServiceImpl implements DatumSystemService {
	private static Logger logger = Logger.getLogger("DatumSystemServiceImpl");
	private static BaseDatumDao dao = new DatumSystemDao();
	public boolean delDatumSystem(String id) {
		String sql = "delete datumsystem where id='"+id+"'";
		int count = DelUploadFile.delFiles(getDatumSystem(id).getAdjunct());//É¾³ý¸½¼þ
		return dao.updateData(sql);
	}

	public boolean saveDatumSystem(DatumSystem system) {
		system.setId(new DBService().getSeq("datumsystem",10));
		String sql = "insert into datumsystem (ID,FILENAME,DOCuMENTTYPE,DESCRIPTION,VALIDATETIME,ADJUNCT,USERID,CREATEDATE,REGIONID) "
					+" values ('"+system.getId()+"','"+system.getDocumentname()+"','"+system.getDocumenttype()+"','" +system.getDescription()+"',"
					+"to_date('"+system.getValidatetime()+"','YYYY/mm/dd hh24:mi:ss'),'"+system.getAdjunct()+"','"+system.getUserid()+"',sysdate,'"+system.getRegionid()+"')";
		
		return dao.updateData(sql);
	}

	public boolean updateDatumSystem(DatumSystem system) {
		String sql = "update datumsystem set filename='"+system.getDocumentname()+"',documenttype='"+system.getDocumenttype()+"',description='"+system.getDescription()+"',"
				+ " validatetime = to_date('"+system.getValidatetime()+"','YYYY/mm/dd hh24:mi:ss'),adjunct='"+system.getAdjunct()+"',createdate=sysdate where id='"+system.getId()+"'";
		return dao.updateData(sql);
	}

	public List queryDatumSystem(DatumSystem system, UserInfo user,String rootRegionid) {
		String sql = "select d.ID,d.FILENAME,d.DOCUMENTTYPE,dt.typename,d.DESCRIPTION, to_char(d.VALIDATETIME,'YYYY/mm/dd hh24:mi:ss') VALIDATETIME,d.ADJUNCT,d.USERID,  to_char(d.CREATEDATE,'YYYY/mm/dd hh24:mi:ss') CREATEDATE, d.REGIONID,r.regionname  from DATUMSYSTEM d,region r,documenttype dt where dt.code = d.documenttype and r.regionid = d.regionid and d.regionid in ('"+rootRegionid+"','"+user.getRegionid()+"')";
		if(!system.getDocumentname().equals("")){
			sql += " and d.filename like %'"+system.getDocumentname()+"'%";
		}
		if(!system.getDocumenttype().equals("")){
			sql += " and d.documenttype = '"+system.getDocumenttype()+"'";
		}
		
		logger.info("sql ->"+sql);
		return dao.queryList(sql);
	}

	public DatumSystem getDatumSystem(String id) {
		String sql = "select ID,FILENAME,DOCUMENTTYPE,DESCRIPTION, to_char(VALIDATETIME,'YYYY/mm/dd hh24:mi:ss') VALIDATETIME,ADJUNCT,USERID,  to_char(CREATEDATE,'YYYY/mm/dd hh24:mi:ss') CREATEDATE, REGIONID from DATUMSYSTEM where id='"+id+"'";
		
		return (DatumSystem) dao.queryData(sql);
	}

	

}
