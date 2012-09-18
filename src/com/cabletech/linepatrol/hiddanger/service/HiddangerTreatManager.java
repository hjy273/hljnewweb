package com.cabletech.linepatrol.hiddanger.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.fileupload.FileItem;
import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.commons.upload.service.UploadFileService;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.hiddanger.beans.TreatBean;
import com.cabletech.linepatrol.hiddanger.dao.HiddangerRegistDao;
import com.cabletech.linepatrol.hiddanger.dao.HiddangerTreatDao;
import com.cabletech.linepatrol.hiddanger.model.HiddangerRegist;
import com.cabletech.linepatrol.hiddanger.model.HiddangerTreat;
import com.cabletech.linepatrol.hiddanger.workflow.HiddangerWorkflowBO;

@Service
@Transactional
public class HiddangerTreatManager extends EntityManager<HiddangerTreat, String>{
	
	@Resource(name="hiddangerTreatDao")
	private HiddangerTreatDao dao;
	@Resource(name="hiddangerTrunkManager")
	private HiddangerTrunkManager hiddangerTrunkManager;
	@Resource(name="hiddangerRegistDao")
	private HiddangerRegistDao registDao;
	@Resource(name="uploadFileService")
	private UploadFileService uploadFile;
	@Autowired
	private HiddangerWorkflowBO workflowBo;
	@Resource(name="smHistoryDAO")
	private SmHistoryDAO historyDAO;
	
	/**
	 * 保存自处理信息
	 * @param treatBean 处理bean
	 * @param trunks 中继段链表
	 * @param userInfo 用户信息
	 */
	public void saveTreat(TreatBean treatBean, List<String> trunks, UserInfo userInfo, List<FileItem> files){
		HiddangerTreat treat = new HiddangerTreat();
		BeanUtil.copyProperties(treatBean, treat);
		
		treat.setId(null);
		treat.setAuthorId(userInfo.getUserID());
		
		//保存处理对象
		dao.save(treat);
		
		//保存附件
		saveFiles(files, treatBean.getHiddangerId(), userInfo);
		
		//保存中继段
		for(String trunkId : trunks){
			hiddangerTrunkManager.saveTrunk(treat.getHiddangerId(), trunkId);
		}
		
		//更新隐患主表状态
		saveState(treatBean.getHiddangerId(), HiddangerConstant.COMPLETE);
		
		//流程
		Task task = workflowBo.getHandleTaskForId(userInfo.getDeptID(), treatBean.getHiddangerId());
		workflowBo.completeTask(task.getId(), "end");
		
		//流程历史
		HiddangerRegist regist = registDao.findUniqueByProperty("id", treatBean.getHiddangerId());
		workflowBo.saveProcessHistory(userInfo, regist, task, regist.getTreatDepartment(), "end", "自行处理", "");
	}
	
	/**
	 * 保存附件
	 * @param files 附件链表
	 * @param id 隐患id
	 * @param userInfo 用户信息
	 */
	public void saveFiles(List<FileItem> files, String id, UserInfo userInfo){
		uploadFile.saveFiles(files, ModuleCatalog.HIDDTROUBLEWATCH, userInfo.getRegionName(), id, "LP_HIDDANGER_TREAT", userInfo.getUserID());
	}
	
	public TreatBean getTreatInfo(String id){
		HiddangerTreat treat = dao.getTreatFromHiddangerId(id);
		TreatBean treatBean = null;
		if(treat != null){
			treatBean = new TreatBean();
			BeanUtil.copyProperties(treat, treatBean);
			treatBean.setTrunkIdsString(hiddangerTrunkManager.getTrunkFormHiddangerId(treat.getHiddangerId()));
		}
		return treatBean;
	}
	
	public void saveState(String id, String state){
		HiddangerRegist regist = registDao.get(id);
		regist.setHiddangerState(state);
		registDao.save(regist);
	}

	@Override
	protected HibernateDao<HiddangerTreat, String> getEntityDao() {
		return dao;
	}
}
