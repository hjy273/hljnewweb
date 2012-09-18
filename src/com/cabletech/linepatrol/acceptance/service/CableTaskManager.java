package com.cabletech.linepatrol.acceptance.service;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.commons.upload.service.UploadFileService;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.acceptance.beans.RcBean;
import com.cabletech.linepatrol.acceptance.dao.ApplyCableDao;
import com.cabletech.linepatrol.acceptance.dao.ApplyTaskDao;
import com.cabletech.linepatrol.acceptance.dao.CableResultDao;
import com.cabletech.linepatrol.acceptance.dao.CableTaskDao;
import com.cabletech.linepatrol.acceptance.dao.PayCableDao;
import com.cabletech.linepatrol.acceptance.model.ApplyCable;
import com.cabletech.linepatrol.acceptance.model.CableResult;
import com.cabletech.linepatrol.acceptance.model.CableTask;
import com.cabletech.linepatrol.acceptance.model.PayCable;
import com.cabletech.linepatrol.resource.dao.RepeaterSectionAddoneDao;
import com.cabletech.linepatrol.resource.dao.RepeaterSectionDao;
import com.cabletech.linepatrol.resource.model.RepeaterSection;
import com.cabletech.linepatrol.resource.model.RepeaterSectionAddone;

@Service
@Transactional
public class CableTaskManager extends EntityManager<CableTask, String>{
	@Resource(name="cableTaskDao")
	private CableTaskDao dao;
	@Resource(name="payCableDao")
	private PayCableDao payCableDao;
	@Resource(name="cableResultDao")
	private CableResultDao cableResultDao;
	@Resource(name="repeaterSectionDao")
	private RepeaterSectionDao trunkDao;
	@Resource(name="repeaterSectionAddoneDao")
	private RepeaterSectionAddoneDao trunkAddoneDao;
	@Resource(name="applyTaskDao")
	private ApplyTaskDao applyTaskDao;
	@Resource(name="uploadFileService")
	private UploadFileService uploadFile;
	@Resource(name="applyTaskManager")
	private ApplyTaskManager atm;
	@Resource(name="applyCableDao")
	private ApplyCableDao acd;
	@Resource(name="applyManager")
	private ApplyManager am;
	
	public void saveCableRecord(UserInfo userInfo, String subflowId, String objectId, RcBean rcBean, List<FileItem> files){
		String applyId = am.getSubflow(subflowId).getApplyId();
		
		//保存中继段
		RepeaterSection trunk = new RepeaterSection();
		rcBean.setLaytype(StringUtils.join(Arrays.asList(rcBean.getLaytypes()).iterator(), ","));
		BeanUtil.copyProperties(rcBean, trunk);
		if(StringUtils.isBlank(rcBean.getCableId())){
			trunk.setKid(null);  //初次
		}else{
			trunk.setKid(rcBean.getCableId());  //复验，修改
		}
		//设置是否通过验收
		if(rcBean.getResult().equals(AcceptanceConstant.PASSED)){
			trunk.setIsCheckOut(AcceptanceConstant.PASS);
		}else{
			trunk.setIsCheckOut(AcceptanceConstant.NOPASS);
		}
		if(trunk.getHavePicture().equals("y")){
			trunk.setIsMaintenance(AcceptanceConstant.PASS);
		}else{
			trunk.setIsMaintenance(AcceptanceConstant.NOPASS);
		}
		trunk.setOwner(AcceptanceConstant.NOPASS);
		trunk.setMaintenanceId(userInfo.getDeptID());
		trunk.setRegion(userInfo.getRegionid());
		trunkDao.save(trunk);
		
		//保存中继段附加信息
		RepeaterSectionAddone trunkAddone = new RepeaterSectionAddone();
		BeanUtil.copyProperties(rcBean, trunkAddone);
		if(StringUtils.isBlank(rcBean.getCableId())){
			trunkAddone.setId(null);  //初次
		}else{
			trunkAddone.setId(rcBean.getCableAddOneId());  //复验，修改
		}
		trunkAddone.setKid(trunk.getKid());
		trunkAddoneDao.save(trunkAddone);
		
		//保存交维信息
		PayCable payCable = new PayCable();
		BeanUtil.copyProperties(rcBean, payCable);
		
		CableResult cableResult = new CableResult();
		BeanUtil.copyProperties(rcBean, cableResult);
		
		if(StringUtils.isBlank(rcBean.getPayCableId())){
			payCable.setId(null);  //初次
		}else{
			payCable.setId(rcBean.getPayCableId());  //复验，修改
		}
		if(StringUtils.isBlank(rcBean.getCableResultId())){
			cableResult.setId(null);  //初次
		}else{
			cableResult.setId(rcBean.getCableResultId());  //修改
		}
		
		CableTask cableTask = dao.loadCableTaskFromApplyId(applyId, objectId);
		payCable.setTaskId(cableTask.getId());//保存CableTask的ID****
		payCable.setCableId(trunk.getKid());
		payCable.setAcceptanceDate(cableResult.getFactDate());
		payCable.setAcceptanceTimes(cableResult.getTimes());
		payCable.setPassed(cableResult.getResult());
		if(cableResult.getResult().equals(AcceptanceConstant.PASSED)){
			payCable.setPassedTime(cableResult.getFactDate());
		}else{
			payCable.setPassedTime(null);
		}
		payCableDao.save(payCable);
		
		cableResult.setPayCableId(payCable.getId());
		cableResult.setRemark(rcBean.getResultRemark());
		cableResultDao.save(cableResult);
		
		//保存附件
		saveFiles(files, trunk.getKid(), userInfo);
		
		//更新任务状态，表明此任务已完成
		atm.updateTaskState(cableTask.getTaskId());
		
		//更新光缆为已录入状态
		ApplyCable applyCable = acd.get(objectId);
		applyCable.setIsrecord(AcceptanceConstant.ISRECORD);
		
		//更新光缆验收状态
		if(cableResult.getResult().equals(AcceptanceConstant.PASSED)){
			applyCable.setIspassed(AcceptanceConstant.PASSED);
		}else{
			applyCable.setIspassed(AcceptanceConstant.NOTPASSED);
		}
		
		acd.save(applyCable);
	}
	
	public void saveFiles(List<FileItem> files, String id, UserInfo userInfo){
		uploadFile.saveFiles(files, ModuleCatalog.OPTICALCABLE, userInfo.getRegionName(), id, AcceptanceConstant.CABLEFILE, userInfo.getUserID(), "0");
	}
	
//	public String getAssinee(String applyId){
//		List<ApplyTask> applyTasks = applyTaskDao.getTasksList(applyId);
//		List<String> list = new ArrayList<String>();
//		for(ApplyTask task : applyTasks){
//			if(loadPayCable(task.getId()) == null && !list.contains(task.getContractorId())){
//				list.add(task.getContractorId());
//			}
//		}
//		return StringUtils.join(list.toArray(), ",");
//	}
	
//	public PayCable loadPayCable(String taskId){
//		return payCableDao.findUniqueByProperty("taskId", taskId);
//	}
	
	public PayCable loadPayCableFromCableId(String applyid, String cableId){
		CableTask cableTask = dao.loadCableTaskFromApplyId(applyid, cableId);
		
		return payCableDao.getPayCableFromTaskId(cableTask.getId());
	}
	
	public String loadCableResultFromCableId(String id, String cableId){
		int number = 0;
		PayCable payCable = loadPayCableFromCableId(id, cableId);
		if(payCable != null)
			number = getCableResults(payCable.getCableId()).size();
		number++;
		return String.valueOf(number);
	}
	
	public List<CableResult> loadCableResult(String payCableId){
		return cableResultDao.getCableResults(payCableId);
	}
	
	//提取数据
	public RcBean loadRcBean(String id, String cableId){
		RcBean rcBean = new RcBean();
		
		PayCable payCable = loadPayCableFromCableId(id, cableId);
		RepeaterSection trunk = loadCable(payCable.getCableId());
		RepeaterSectionAddone trunkAddone = loadCableAddone(payCable.getCableId());
		
		BeanUtil.copyProperties(payCable, rcBean);
		BeanUtil.copyProperties(trunk, rcBean);
		BeanUtil.copyProperties(trunkAddone, rcBean);
		
		rcBean.setCableId(trunk.getKid());
		rcBean.setCableAddOneId(trunkAddone.getId());
		rcBean.setPayCableId(payCable.getId());
		rcBean.setTimes(payCable.getAcceptanceTimes());
		return rcBean;
	}
	
	//提取全部数据
	public RcBean editRcBean(String id, String cableId){
		RcBean rcBean = new RcBean();
		
		PayCable payCable = loadPayCableFromCableId(id, cableId);
		RepeaterSection trunk = loadCable(payCable.getCableId());
		RepeaterSectionAddone trunkAddone = loadCableAddone(payCable.getCableId());
		CableResult cableResult = cableResultDao.loadCableResult(payCable.getId());
		
		BeanUtil.copyProperties(payCable, rcBean);
		BeanUtil.copyProperties(trunk, rcBean);
		BeanUtil.copyProperties(trunkAddone, rcBean);
		BeanUtil.copyProperties(cableResult, rcBean);
		
		rcBean.setCableId(trunk.getKid());
		rcBean.setCableAddOneId(trunkAddone.getId());
		rcBean.setPayCableId(payCable.getId());
		rcBean.setCableResultId(cableResult.getId());
		rcBean.setResultRemark(cableResult.getRemark());
		
		rcBean.setFactDate(DateUtil.UtilDate2Str(cableResult.getFactDate(), "yyyy/MM/dd"));
		rcBean.setPlanDate(DateUtil.UtilDate2Str(cableResult.getPlanDate(), "yyyy/MM/dd"));
		return rcBean;
	}
	
	public List<CableResult> getCableResults(String cableId){
		return cableResultDao.getCableResults(cableId);
	}
	
	public RepeaterSection loadCable(String id){
		return trunkDao.findUniqueByProperty("kid", id);
	}
	
	public RepeaterSectionAddone loadCableAddone(String cableId){
		return trunkAddoneDao.findUniqueByProperty("kid", cableId);
	}
	
	public CableResult findCableResult(List<CableResult> cableResults, String id){
		CableResult cableResult = new CableResult();
		for(CableResult c : cableResults){
			if(c.getId().equals(id)){
				cableResult = c;
			}
		}
		return cableResult;
	}
	
	//光缆设置为已交维
	void approveCable(String taskId){
		List<PayCable> payCables = payCableDao.getPayCablesByTaskId(taskId);
		for(PayCable payCable:payCables){
			if(payCable.getPassed().equals(AcceptanceConstant.PASSED)){
				RepeaterSection trunk = trunkDao.findByUnique("kid", payCable.getCableId());
				trunk.setIsCheckOut(AcceptanceConstant.PASS);
				trunk.setFinishtime(DateUtil.StringToDate(new DateUtil().getNextMonthFirst(), "yyyy-MM-dd"));
				trunkDao.save(trunk);
			}
		}
	}
	
	@Override
	protected HibernateDao<CableTask, String> getEntityDao(){
		return dao;
	}
}
