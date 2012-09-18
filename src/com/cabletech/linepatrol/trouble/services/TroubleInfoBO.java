package com.cabletech.linepatrol.trouble.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.process.bean.ProcessHistoryBean;
import com.cabletech.commons.process.service.ProcessHistoryBO;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.commons.upload.service.UploadFileService;
import com.cabletech.commons.util.Hanzi2PinyinUtil;
import com.cabletech.commons.util.StringUtils;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.dao.UserInfoDAOImpl;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.trouble.beans.TroubleInfoBean;
import com.cabletech.linepatrol.trouble.dao.TroubleInfoDAO;
import com.cabletech.linepatrol.trouble.dao.TroubleProcessUnitDAO;
import com.cabletech.linepatrol.trouble.module.TroubleInfo;
import com.cabletech.linepatrol.trouble.module.TroubleProcessUnit;
import com.cabletech.linepatrol.trouble.workflow.TroubleWorkflowBO;

@Service
@Transactional
public class TroubleInfoBO extends EntityManager<TroubleInfo, String> {
	@Resource(name = "userInfoDao")
	private UserInfoDAOImpl userInfoDao;

	@Resource(name = "troubleInfoDAO")
	private TroubleInfoDAO dao;

	@Resource(name = "troubleProcessUnitDAO")
	private TroubleProcessUnitDAO unitDAO;

	@Resource(name = "smHistoryDAO")
	private SmHistoryDAO historyDAO;
	@Resource(name = "uploadFileService")
	private UploadFileService uploadFile;
	@Resource(name = "processHistoryBO")
	private ProcessHistoryBO processHistoryBO;
	@Autowired
	private TroubleWorkflowBO workflowBo;

	public TroubleInfo addTroubleInfo(TroubleInfo trouble) {
		dao.save(trouble);
		return trouble;
	}
	/**
	 *  根据已有数据生成序列编号，并检查是否与重复
	 * @param deptName
	 * @param deptid
	 * @return
	 */
	@Transactional(readOnly = true)
	public String generateCode(String deptName,String userid){
		List<TroubleInfo> troubles = dao.getTroubles4Dept(userid);
		String code = Hanzi2PinyinUtil.generateCode4Prefix(deptName);
		int lenght = troubles.size()+1;
		String number = String.format("%04d", lenght); 
		for(TroubleInfo trouble:troubles){
			if(trouble.getTroubleId().indexOf(number) != -1){
				number = String.format("%04d", ++lenght);
			}
		}
		logger.info("业务ID："+code+number);
		
		return code+number;
	}

	/**
	 * 保存故障信息
	 * 
	 * @param bean
	 * @param userinfo
	 * @param conids
	 *            派发代维
	 * @param mobiles
	 *            派发单位人员电话
	 * @param files
	 *            附件
	 * @return
	 * @throws Exception
	 */
	public TroubleInfo addTroubleInfo(TroubleInfoBean bean, UserInfo userinfo,
			String conids, String deptids, String userids, String mobiles,
			List<FileItem> files) throws Exception {
		TroubleInfo trouble = new TroubleInfo();
		BeanUtil.objectCopy(bean, trouble);
		
		trouble.setSendManId(userinfo.getUserID());
		trouble.setTroubleSendTime(new Date());
		trouble.setRegionID(userinfo.getRegionID());
		String savestate = bean.getTempsave();
		if (savestate != null && savestate.equals("tempsaveTrouble")) {
			trouble.setTroubleState(TroubleConstant.TEMP_SAVE);// 临时保存
		} else {
			trouble.setTroubleId(generateCode(userinfo.getDeptName(),userinfo.getUserID()));
			trouble.setTroubleState(TroubleConstant.TROUBLE_WATI_REPLY);
		}
		dao.save(trouble);
		String troubleId = trouble.getId();
		List<String> conidList = StringUtils.string2List(conids, ",");
		if (conidList != null && conidList.size() > 0) {
			String processInstanceId;
			for (int i = 0; i < conidList.size(); i++) {
				TroubleProcessUnit unit = new TroubleProcessUnit();
				unit.setTroubleId(troubleId);
				unit.setProcessUnitId(conidList.get(i));
				unit.setState(TroubleConstant.PROCESSUNIT_WAIT);
				unitDAO.save(unit);
				String unitid = unit.getId();
				if (savestate == null || !savestate.equals("tempsaveTrouble")) {
					Map variables = new HashMap();
					variables.put("assignee", unit.getProcessUnitId());
					processInstanceId = workflowBo.createProcessInstance(
							"trouble", unitid, variables);
					System.out.println("故障已经登记派发！");
					addWorkFlowHistory(userinfo, processInstanceId, unitid,
							conidList.get(i));
				}
			}
		}
		if (savestate == null || !savestate.equals("tempsaveTrouble")) {
			sendMsg(trouble, deptids, userids, mobiles);
		}
		String userid = userinfo.getUserID();
		uploadFile.saveFiles(files, TroubleConstant.TROUBLE_MODULE, userinfo
				.getRegionName(), troubleId, TroubleConstant.LP_TROUBLE_INFO,
				userid);
		return trouble;
	}

	/**
	 * 保存历程历史
	 * 
	 * @param user
	 * @param processInstanceId
	 */
	public void addWorkFlowHistory(UserInfo user, String processInstanceId,
			String unitid, String conid) {
		try {
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			processHistoryBean.initial(null, user, processInstanceId,
					ModuleCatalog.TROUBLE);
			processHistoryBean.setHandledTaskName("start");
			processHistoryBean.setNextOperateUserId(conid);
			processHistoryBean.setObjectId(unitid);
			processHistoryBean.setProcessAction("故障派单");
			processHistoryBean.setTaskOutCome("dispatch");
			processHistoryBO.saveProcessHistory(processHistoryBean);
		} catch (Exception e) {
			logger.error("故障派单增加处理流程失败:" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 发送短信
	 */
	public void sendMsg(TroubleInfo trouble, String deptids, String userids,
			String mobiles) {
		String troubleId = trouble.getId();
		// 发送短信
		String troubleRemark = trouble.getTroubleRemark();
		String content = "【线路故障】您有一个类型为";
		content += trouble.getIsGreatTrouble().equals("1") ? "重大故障" : "一般故障";
		content += ",名称为\"" + trouble.getTroubleName() + "\"的故障单等待您的反馈。";
		if (troubleRemark != null && !troubleRemark.equals("")) {
			content += "  故障描述:" + trouble.getTroubleRemark();
		}
		String connector = trouble.getConnector();
		String connTel = trouble.getConnectorTel();
		content += "    平台:" + connector + "  平台电话:" + connTel;

		String mobileMsg = "【线路故障】有一个名称为\"" + trouble.getTroubleName() + "\"的";
		mobileMsg += trouble.getIsGreatTrouble().equals("1") ? "重大故障" : "一般故障";
		mobileMsg += "已经派单。";

		// 注意这里每个流程通知的派单人是不同的；注明短信通知了那些人
		// 故障处理指定的处理人员。
		List<String> mobileList = StringUtils.string2List(mobiles, ",");
		List<String> useridList = StringUtils.string2List(userids, ",");
		List<String> deptidList = StringUtils.string2List(deptids, ",");
		if (mobileList != null && mobileList.size() > 0) {
			for (int i = 0; i < mobileList.size(); i++) {
				String mobile = mobileList.get(i);
				String userid = useridList.get(i);
				String deptuser = deptidList.get(i);
				List<String> deptlist = StringUtils.string2List(deptuser, ";");
				String deptid = "";
				if (deptlist != null && deptlist.size() > 0) {
					deptid = deptlist.get(0);
				}
				if (mobile != null && !"null".equals(mobile)) {
					if (userid != null && !"".equals(userid)) {
						UserInfo user = dao.getUserByUserIdAndDeptId(userid,
								deptid);
						if (user != null) {// 移动用户
							logger.info("短信内容: " + mobile + ":" + mobileMsg);
							super.sendMessage(mobileMsg, mobile);
							// smSendProxy.simpleSend(mobile,mobileMsg, null,
							// null, true);
						} else {
							logger.info("短信内容: " + mobile + ":" + content);
							super.sendMessage(content, mobile);
							// smSendProxy.simpleSend(mobile,content, null,
							// null, true);
						}
					}
				}
			}
		}
		/*
		 * for(String mobile:mobileList){ logger.info("短信内容: "+mobile
		 * +":"+content); smSendProxy.simpleSend(mobile,content, null, null,
		 * true); }
		 */
		// 保存短信记录
		SMHistory history = new SMHistory();
		history.setSimIds(mobiles);
		history.setSendContent(content);
		history.setSendTime(new Date());
		history.setSmType(TroubleConstant.LP_TROUBLE_INFO);
		history.setObjectId(troubleId);
		history.setBusinessModule(TroubleConstant.TROUBLE_MODULE);
		historyDAO.save(history);
	}


	/**
	 * 得到临时保存的故障派单
	 * 
	 * @param user
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<TroubleInfo> getTempSaveTroubles(UserInfo user) {
		return dao.getTempSaveTroubles(user);
	}

	@Transactional(readOnly = true)
	public TroubleInfo getTroubleById(String id) {
		TroubleInfo trouble = dao.get(id);
		dao.initObject(trouble);
		String cancelUserName = userInfoDao.getUserName(trouble
				.getCancelUserId());
		trouble.setCancelUserName(cancelUserName);
		return trouble;
	}

	/**
	 * 保存故障处理单位信息
	 */
	public TroubleProcessUnit addTroubleUnit(TroubleProcessUnit unit) {
		unitDAO.save(unit);
		return unit;
	}

	/**
	 * 执行根据当前用户查询当前月份的故障单数量信息
	 * 
	 * @
	 */
	/*@Transactional(readOnly = true)
	public Integer getTroubleInfoNumber(UserInfo userinfo) {
		return dao.getTroubleInfoNumber(userinfo);
	}*/

	@Transactional(readOnly = true)
	public TroubleInfo loadTroubleInfo(String ID) throws ServiceException {
		return dao.findByUnique("id", ID);
	}

	/*
	 * @Transactional(readOnly=true) public List queryTroubleInfo(String
	 * operationName) throws ServiceException{ return
	 * dao.queryTaskOperation(operationName); }
	 */

	public void delTroubleInfo(String id) {
		dao.delete(id);
	}

	public void removeTroubleInfo(String id) throws ServiceException {
		dao.delete(id);
	}

	public TroubleInfo updateTroubleInfo(TroubleInfo troubleInfo)
			throws ServiceException {
		dao.save(troubleInfo);
		return troubleInfo;
	}

	/**
	 * 查询故障派单的代维公司与负责人
	 * 
	 * @param troubleid
	 * @return
	 */
	public List findTroubleUnitById(String troubleid) {
		return dao.findTroubleUnitById(troubleid);
	}

	/**
	 * 执行取消故障流程
	 * 
	 * @author yangjun 2010-6-23
	 * @param bean
	 * @param userInfo
	 */
	public void cancelTrouble(TroubleInfoBean bean, UserInfo userInfo) {
		// TODO Auto-generated method stub
		TroubleInfo troubleInfo = getTroubleById(bean.getId());
		troubleInfo.setTroubleState(TroubleInfo.CANCELED_STATE);
		troubleInfo.setCancelTime(new Date());
		troubleInfo.setCancelUserId(userInfo.getUserID());
		troubleInfo.setCancelReason(bean.getCancelReason());
		dao.save(troubleInfo);
		String hql = " from TroubleProcessUnit dept where troubleId=? ";
		List acceptDeptList = unitDAO.find(hql, bean.getId());
		TroubleProcessUnit acceptDept = null;
		String processInstanceId = "";
		ProcessHistoryBean processHistoryBean;
		for (int i = 0; acceptDeptList != null && i < acceptDeptList.size(); i++) {
			acceptDept = (TroubleProcessUnit) acceptDeptList.get(i);
			processInstanceId = TroubleWorkflowBO.TROUBLE_WORKFLOW + "."
					+ acceptDept.getId();
			workflowBo.endProcessInstance(processInstanceId);
			processHistoryBean = new ProcessHistoryBean();
			processHistoryBean.initial(null, userInfo, processInstanceId,
					ModuleCatalog.SENDTASK);
			processHistoryBean.setHandledTaskName("any");
			processHistoryBean.setNextOperateUserId("");
			processHistoryBean.setObjectId(acceptDept.getId());
			processHistoryBean.setProcessAction("故障流程取消");
			processHistoryBean.setTaskOutCome("cancel");
			processHistoryBO.saveProcessHistory(processHistoryBean);
		}
	}

	/**
	 * 查询所有的故障信息
	 * 
	 * @return
	 */
	public List<TroubleInfo> getTroubleInfos() {
		return dao.getAll();
	}

	/**
	 * 根据障碍原因id查询障碍名称
	 * 
	 * @param reasonid
	 * @return
	 */
	public String getTroubleReasonName(String reasonid) {
		return dao.getTroubleReasonName(reasonid);
	}

	@Override
	protected HibernateDao<TroubleInfo, String> getEntityDao() {
		return dao;
	}
}
