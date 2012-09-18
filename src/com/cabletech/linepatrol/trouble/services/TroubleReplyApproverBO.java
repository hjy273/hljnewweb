package com.cabletech.linepatrol.trouble.services;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.module.ReplyApprover;
import com.cabletech.linepatrol.trouble.dao.TroubleReplyApproverDAO;
@Service
@Transactional
public class TroubleReplyApproverBO extends EntityManager<ReplyApprover,String> {

	@Resource(name="troubleReplyApproverDAO")
	private TroubleReplyApproverDAO dao;

	public void addReplyApprover(ReplyApprover replyApprover) throws ServiceException {
		dao.save(replyApprover);
	}

	@Transactional(readOnly=true)
	public ReplyApprover loadReplyApprover(String ID) throws ServiceException {
		return dao.findByUnique("id",ID);
	}

	/*@Transactional(readOnly=true)
	public List queryTroubleInfo(String operationName) throws ServiceException{
		return dao.queryTaskOperation(operationName);
	}*/

	public void delReplyApprover(String id) {
		dao.delete(id);
	}


	public void updateReplyApprover(ReplyApprover replyApprover) throws ServiceException {
		dao.save(replyApprover);
	}



	@Override
	protected HibernateDao<ReplyApprover, String> getEntityDao() {
		return dao;
	}
}
