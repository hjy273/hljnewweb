package com.cabletech.linepatrol.resource.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cabletech.sm.rmi.RmiSmProxyService;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.commons.upload.dao.AnnexAddOneDao;
import com.cabletech.commons.upload.service.UploadFileService;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.resource.beans.DatumInfoBean;
import com.cabletech.linepatrol.resource.dao.DatumInfoDao;
import com.cabletech.linepatrol.resource.dao.DatumTypeDao;
import com.cabletech.linepatrol.resource.model.DatumInfo;
import com.cabletech.linepatrol.resource.model.DatumType;

@Service
@Transactional
public class DatumManager extends EntityManager<DatumInfo, String> {
	
	public static String IS_PASS="1";
	public static String IS_NOT_PASS="0";
	@Resource(name="datumInfoDao")
	private DatumInfoDao dao;
	@Resource(name="datumTypeDao")
	private DatumTypeDao datumTypeDao;
	@Resource(name="uploadFileService")
	private UploadFileService uploadFile;
	@Resource(name="annexAddOneDao")
	private AnnexAddOneDao annexAddOneDao;
	@Resource(name="rmiSmProxyService")
	private RmiSmProxyService smSendProxy;
	
	public DatumInfo getDatumInfoById(String id){
		return dao.get(id);
	}
	
	public boolean hadDulicateName(String name){
		List<DatumType> list = datumTypeDao.hadDuplicateName(name);
		return list == null || list.isEmpty() ? false : true;
	}
	
	public void addType(String name){
		DatumType datumType = new DatumType();
		datumType.setName(name);
		datumTypeDao.save(datumType);
	}
	
	public String getTypeString(){
		List<DatumType> types = datumTypeDao.getAll();
		List<String> list = new ArrayList<String>();
		for(DatumType dt : types){
			list.add(dt.getId()+","+dt.getName());
		}
		return StringUtils.join(list.iterator(), "&");
	}
	
	public List<DatumType> getType(){
		List<DatumType> types = datumTypeDao.getAll();
		return types;
	}
	
	public void add(DatumInfoBean datumInfoBean, List<FileItem> files, UserInfo userInfo){
		DatumInfo datumInfo = new DatumInfo();
		BeanUtil.copyProperties(datumInfoBean, datumInfo);
		datumInfo.setId(null);
		datumInfo.setContractorId(userInfo.getDeptID());
		datumInfo.setRegionId(userInfo.getRegionID());
		datumInfo.setDatumState(DatumInfo.SUBMIT_STATE);
		dao.save(datumInfo);
		
		saveFiles(files, datumInfo.getId(), userInfo);
	}
	
	public List<DatumInfo> getDatumList(UserInfo userInfo){
		return dao.getDatumList(userInfo);
	}
	
	public DatumInfo edit(String id){
		return dao.findUniqueByProperty("id", id);
	}
	
	public void update(DatumInfoBean datumInfoBean, List<FileItem> files, UserInfo userInfo){
		DatumInfo datumInfo = dao.findUniqueByProperty("id", datumInfoBean.getId());
		datumInfo.setInfo(datumInfoBean.getInfo());
		datumInfo.setDatumState(DatumInfo.SUBMIT_STATE);
		dao.save(datumInfo);
		
		saveFiles(files, datumInfo.getId(), userInfo);
	}
	
	public List<Map> historyPass(String id){
		return annexAddOneDao.getAnnexAddOneList(id, DatumInfo.TYPE, ModuleCatalog.DATUM, IS_PASS);
	}
	
	public List<Map> historyNotPass(String id){
		return annexAddOneDao.getAnnexAddOneList(id, DatumInfo.TYPE, ModuleCatalog.DATUM, IS_NOT_PASS);
	}
	
	public void saveFiles(List<FileItem> files, String id, UserInfo userInfo){
		uploadFile.saveFiles(files, ModuleCatalog.DATUM, userInfo.getRegionName(), id, DatumInfo.TYPE, userInfo.getUserID(), IS_NOT_PASS);
	}
	
	public void approve(String id, String[] addOnes) throws Exception{
	    DatumInfo datum = dao.get(id);
	    dao.initObject(datum);
	    datum.setDatumState(DatumInfo.PASSED);
	    dao.save(datum);
		for(String oneId : addOnes){
			annexAddOneDao.setPass(oneId);
		}
	}
	
	public void approveNotPass(String id){
	    DatumInfo datum = dao.get(id);
        dao.initObject(datum);
        datum.setDatumState(DatumInfo.NOT_PASSED);
        dao.save(datum);
		List<Map> list = annexAddOneDao.getAnnexAddOneList(id, DatumInfo.TYPE, ModuleCatalog.DATUM, IS_NOT_PASS);
		for(Map map : list){
			String userId = (String)map.get("uploader");
			String phone = dao.getPhoneFromUserid(userId);
			String content = "您更新的名称为"+(String)map.get("ORIGINALNAME")+"的维护资料审核未通过，请修改。";
			smSendProxy.simpleSend(phone,content, new Date(),new Date(), true);
		    logger.info("短信内容: "+phone +":"+content);
		}
	}
	
	@Override
	protected HibernateDao<DatumInfo, String> getEntityDao() {
		return dao;
	}

	public void delType(String id) {
		datumTypeDao.delete(id);
		
	}

	public DatumType getTypeById(String id) {
		return datumTypeDao.get(id);
	}

	public void updateType(DatumType datumtype) {
		DatumType datumtype2=datumTypeDao.findUniqueByProperty("id", datumtype.getId());
		datumtype2.setName(datumtype.getName());
		datumTypeDao.save(datumtype2);
		
	}

}