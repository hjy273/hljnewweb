package com.cabletech.linepatrol.material.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.util.StringUtils;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.material.beans.LinePatrolManagerBean;
import com.cabletech.linepatrol.material.dao.LinePatrolManagerDao;
import com.cabletech.linepatrol.material.domain.LinePatrolManager;
/**
 * 
 * @author Administrator
 * @deprecated
 */
@Service
@Transactional
public class LinePatrolManagerBo extends
		EntityManager<LinePatrolManager, String> {
	@Resource(name = "linePatrolManagerDao")
	private LinePatrolManagerDao linePatrolManagerDao;
	@Resource(name = "smHistoryDAO")
	private SmHistoryDAO historyDAO;

	@Transactional(readOnly = true)
	public List getContractList() throws ServiceException {
		return linePatrolManagerDao.getContractList();
	}

	@Transactional(readOnly = true)
	public List getLinePatrolList() throws ServiceException {
		return linePatrolManagerDao.getLinePatrolList();
	}

	@Transactional(readOnly = true)
	public List getAddressList(String contractorid) throws ServiceException {
		return linePatrolManagerDao.getAddressList(contractorid);
	}

	public boolean addLinePatrolInfo(LinePatrolManager linePatrolManager,
			UserInfo userinfo, String userids, String mobiles)
			throws ServiceException {
		if (linePatrolManagerDao.addLinePatrolInfo(linePatrolManager, userinfo,
				userids)) {
			String content = "【备料】您有一个名称为" + linePatrolManager.getTitle() + "的材料申请单等待您的及时处理。";
			List<String> mobileList = StringUtils.string2List(mobiles, ",");
			super.sendMessage(content, mobileList);
//			for (String mobile : mobileList) {
//				smSendProxy.simpleSend(mobile, content, null, null, true);
//				logger.info("短信内容: "+mobile +":"+content);
//			}
			// 保存短信记录
			SMHistory history = new SMHistory();
			history.setSimIds(mobiles);
			history.setSendContent(content);
			history.setSendTime(new Date());
			// history.setSendState(sendState);
			history.setSmType(LinePatrolManager.LP_MT_NEW);
			history.setObjectId(linePatrolManager.getId());
			history.setBusinessModule(LinePatrolManager.MATERIAL_MODULE);
			historyDAO.save(history);
			return true;
		} else {
			return false;
		}
	}

	@Transactional(readOnly = true)
	public List queryLinePatrol(LinePatrolManager linePatrolManager,
			String userid) throws ServiceException {
		return linePatrolManagerDao.queryLinePatrol(linePatrolManager, userid);
	}

	@Transactional(readOnly = true)
	public LinePatrolManagerBean viewLinePatrolById(String id,
			LinePatrolManagerBean bean) throws ServiceException {
		return linePatrolManagerDao.viewLinePatrolById(id, bean);
	}

	@Transactional(readOnly = true)
	public LinePatrolManagerBean getLineAssessDept1(LinePatrolManagerBean bean,
			String id) throws ServiceException {
		return linePatrolManagerDao.getLineAssessDept1(bean, id);
	}

	public boolean delLinePatrolById(String id) throws ServiceException {
		return linePatrolManagerDao.delLinePatrolById(id);
	}

	@Transactional(readOnly = true)
	public LinePatrolManagerBean getLinePatrolById(String id,
			LinePatrolManagerBean bean) throws ServiceException {
		return linePatrolManagerDao.getLinePatrolById(id, bean);
	}

	@Transactional(readOnly = true)
	public List getPatrolModellist() throws ServiceException {
		return linePatrolManagerDao.getPatrolModellist();
	}

	@Transactional(readOnly = true)
	public List getPatorlBaselist() throws ServiceException {
		return linePatrolManagerDao.getPatorlBaselist();
	}

	public boolean modLinePatrolInfo(LinePatrolManagerBean bean,
			UserInfo userinfo, String userids, String mobiles)
			throws ServiceException {
		if (linePatrolManagerDao.modLinePatrolInfo(bean, userinfo, userids)) {
			String content = "【备料】您有一个名称为" + bean.getTitle() + "的材料申请单等待您的及时处理。";
			List<String> mobileList = StringUtils.string2List(mobiles, ",");
			super.sendMessage(content, mobileList);
//			for (String mobile : mobileList) {
//				smSendProxy.simpleSend(mobile, content, null, null, true);
//				logger.info("短信内容: "+mobile +":"+content);
//			}
			// 保存短信记录
			SMHistory history = new SMHistory();
			history.setSimIds(mobiles);
			history.setSendContent(content);
			history.setSendTime(new Date());
			// history.setSendState(sendState);
			history.setSmType(LinePatrolManager.LP_MT_NEW);
			history.setObjectId(bean.getId());
			history.setBusinessModule(LinePatrolManager.MATERIAL_MODULE);
			historyDAO.save(history);
			return true;
		} else {
			return false;
		}
	}

	@Transactional(readOnly = true)
	public List getAssessByDep2(String userId) throws ServiceException {
		return linePatrolManagerDao.getAssessByDep2(userId);
	}

	@Transactional(readOnly = true)
	public List getAssessByDepart3(String departid) throws ServiceException {
		return linePatrolManagerDao.getAssessByDepart3(departid);
	}

	@Transactional(readOnly = true)
	public LinePatrolManagerBean getLineAssess(LinePatrolManagerBean bean,
			String id) throws ServiceException {
		return linePatrolManagerDao.getLineAssess(bean, id);
	}

	@Transactional(readOnly = true)
	public List getUserInfos() throws ServiceException {
		return linePatrolManagerDao.getUserInfos();
	}

	public boolean addLinePatrolAssessInfo(LinePatrolManagerBean bean,
			UserInfo userinfo, String userid) throws ServiceException {
		if (linePatrolManagerDao
				.addLinePatrolAssessInfo(bean, userinfo, userid)) {
			if (bean != null && !"1".equals(bean.getState())) {
				String mobile = linePatrolManagerDao.getUserMobile(bean.getCerator());
				String content = "【备料】您有一个名称为" + bean.getTitle() + "的材料申请单审核没有通过，等待您的及时处理。";
				// if(mobile.length() == 13){
				logger.info("短信内容: "+mobile +":"+content);
				super.sendMessage(content, mobile);
//				smSendProxy.simpleSend(mobile, content, null, null, true);
				// }
				// 保存短信记录
				SMHistory history = new SMHistory();
				history.setSimIds(mobile);
				history.setSendContent(content);
				history.setSendTime(new Date());
				// history.setSendState(sendState);
				history.setSmType(LinePatrolManager.LP_MT_NEW);
				history.setObjectId(bean.getId());
				history.setBusinessModule(LinePatrolManager.MATERIAL_MODULE);
				historyDAO.save(history);
			}
			return true;
		} else {
			return false;
		}
	}

	@Transactional(readOnly = true)
	public List getListByDepartFor1(LinePatrolManagerBean bean)
			throws ServiceException {
		return linePatrolManagerDao.getListByDepartFor1(bean);
	}

	@Transactional(readOnly = true)
	public List getListByDepart3(String departid, LinePatrolManagerBean bean)
			throws ServiceException {
		return linePatrolManagerDao.getListByDepart3(departid, bean);
	}

	@Transactional(readOnly = true)
	public List getLinePatrolModelByID(String id) throws ServiceException {
		return linePatrolManagerDao.getLinePatrolModelByID(id);
	}

	@Transactional(readOnly = true)
	public List getPatorlBaseById(String id) throws ServiceException {
		return linePatrolManagerDao.getPatorlBaseById(id);
	}

	@Override
	protected HibernateDao<LinePatrolManager, String> getEntityDao() {
		// TODO Auto-generated method stub
		return linePatrolManagerDao;
	}

	public LinePatrolManagerDao getLinePatrolManagerDao() {
		return linePatrolManagerDao;
	}

	public void setLinePatrolManagerDao(
			LinePatrolManagerDao linePatrolManagerDao) {
		this.linePatrolManagerDao = linePatrolManagerDao;
	}
}
