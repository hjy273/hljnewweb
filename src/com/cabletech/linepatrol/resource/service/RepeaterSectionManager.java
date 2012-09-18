package com.cabletech.linepatrol.resource.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.fileupload.FileItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.commons.upload.service.UploadFileService;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.acceptance.service.AcceptanceConstant;
import com.cabletech.linepatrol.resource.beans.RepeaterSectionBean;
import com.cabletech.linepatrol.resource.dao.RepeaterSectionDao;
import com.cabletech.linepatrol.resource.model.RepeaterSection;

@Service
public class RepeaterSectionManager extends EntityManager {
	@Resource(name="repeaterSectionDao")
	private RepeaterSectionDao rsDao;
	@Resource(name="uploadFileService")
	private UploadFileService uploadFile;
	@Resource(name="trunkManager")
	private TrunkManager trunkManager;
	
	@Override
	protected HibernateDao getEntityDao() {
		return rsDao;
	}
	
	@Transactional
	public RepeaterSection save(RepeaterSection rs, List<FileItem> files, UserInfo userInfo)throws ServiceException{
		rsDao.save(rs);
		saveFiles(files, rs.getKid(), userInfo, ModuleCatalog.OPTICALCABLE);
		return rs;
	}
	
	@Transactional
	public void approve(String id, String type)throws ServiceException{
		trunkManager.updateApprove(id, type);
	}
	
	@Transactional
	public void saveFiles(List<FileItem> files, String id, UserInfo userInfo, String type)throws ServiceException{
		uploadFile.saveFiles(files, type, userInfo.getRegionName(), id, AcceptanceConstant.CABLEFILE, userInfo.getUserID(), "1");
	}
	
	@Transactional(readOnly=true)
	public List<RepeaterSection> getWaitApprove(){
		return rsDao.getWaitApprove(AcceptanceConstant.CABLEFILE, ModuleCatalog.OPTICALCABLE);
	}
	
	@Transactional(readOnly=true)
	public List<RepeaterSection> getAllByDept(UserInfo user,RepeaterSectionBean bean) throws ServiceException{
		return rsDao.getRepeaterSection(bean,user);
	}
	
	@Transactional(readOnly=true)
	public RepeaterSection getObject(String id) throws ServiceException{
		return rsDao.get(id);
	}
	
	@Transactional(readOnly=true)
	public boolean validateCode(String segmentid) throws ServiceException{
		segmentid = segmentid.substring(4, segmentid.length());
		int count = rsDao.countSegmentid(segmentid);
		if(count >=1){
			return true;
		}else{
			return false;
		}
	}

	public Map<Object, Object> loadContractor() throws ServiceException{
		return  rsDao.getJdbcTemplate().queryForMap("select contractorId,contractorname from contractorinfo where state is null", null);
	}
	@Transactional
	public RepeaterSection scrap(String id) throws ServiceException{
		RepeaterSection rs = rsDao.get(id);
		rs.setScrapState("true");
		rsDao.save(rs);
		return rs;
	}
	
	
	/**
	 * 根据光缆名称和代维单位编号查询光缆信息
	 * @param segmentname 光缆名称
	 * @param maintenanceId 代维单位编号
	 * @return
	 * @throws ServiceException
	 */
	@Transactional(readOnly=true)
	public String searchCable(String segmentname, String maintenanceId, String subline) throws ServiceException {
		StringBuffer sb = new StringBuffer("<select name=\"original\" style=\"width: 200px\" size=\"15\" id=\"original\" multiple=\"multiple\" >");
		List<RepeaterSection> list = rsDao.getRepeaterSectionByCondition(segmentname, maintenanceId, subline);
		if(list != null && list.size() > 0){
			for(RepeaterSection rs : list){
				sb.append("<option value=\"");
				sb.append(rs.getKid());
				sb.append("\">");
				sb.append(rs.getSegmentname());
				sb.append("</option>\n");
				sb.append("\n");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}
	
	/**
	 * 根据光缆编号查询光缆列表信息
	 * @param sbulineid 光缆编号
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<DynaBean> getCableInfoList(String sublineid) throws ServiceException {
		List<DynaBean> list = rsDao.getRepeaterSectionByKids(sublineid);
		return list;
	}
	
	/**
	 * 光缆重新分配
	 * @param bean
	 * @throws ServiceException
	 */
	@Transactional
	public void assignCable(RepeaterSectionBean bean) throws ServiceException {
		rsDao.assignCable(bean);
	}
	
	@Transactional
	public List<Map> getRepeaterSectionFromPDA(String segmentName,String finishTime,UserInfo userInfo,String contractorId){
		return rsDao.getRepeaterSectionFromPDA(segmentName, finishTime, userInfo,contractorId);
	}
	
}
