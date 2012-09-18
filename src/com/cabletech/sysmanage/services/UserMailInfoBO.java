package com.cabletech.sysmanage.services;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.DynaBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.sysmanage.beans.UserMailInfoBean;
import com.cabletech.sysmanage.dao.UserMailInfoDao;
import com.cabletech.sysmanage.domainobjects.UserMailInfo;

@Service
@Transactional
public class UserMailInfoBO extends EntityManager<UserMailInfo, String> {
	public static final int MAIL_MAX_NUM = 5;

	@Resource(name = "userMailInfoDao")
	private UserMailInfoDao dao;

	@Override
	protected HibernateDao<UserMailInfo, String> getEntityDao() {
		return dao;
	}

	public boolean addUserMailInfo(UserMailInfoBean userMailInfoBean,
			UserInfo userInfo) throws Exception {
		boolean flag = false;
		String orderNumberStr = userMailInfoBean.getOrderNumberStr();
		int orderNumber = Integer.parseInt(orderNumberStr);
		userMailInfoBean.setOrderNumber(orderNumber);
		UserMailInfo userMailInfo = new UserMailInfo();
		BeanUtil.objectCopy(userMailInfoBean, userMailInfo);
		userMailInfo.setMailUserId(userInfo.getUserID());
		dao.save(userMailInfo);
		flag = true;
		return flag;
	}

	public boolean updateUserMailInfo(UserMailInfoBean userMailInfoBean,
			UserInfo userInfo) throws Exception {
		boolean flag = false;
		String orderNumberStr = userMailInfoBean.getOrderNumberStr();
		int orderNumber = Integer.parseInt(orderNumberStr);
		userMailInfoBean.setOrderNumber(orderNumber);
		UserMailInfo userMailInfo = new UserMailInfo();
		BeanUtil.objectCopy(userMailInfoBean, userMailInfo);
		userMailInfo.setMailUserId(userInfo.getUserID());
		dao.save(userMailInfo);
		flag = true;
		return flag;
	}

	public boolean deleteUserMailInfo(String id, UserInfo userInfo)
			throws Exception {
		boolean flag = false;
		dao.delete(id);
		flag = true;
		return flag;
	}

	public UserMailInfoBean viewUserMailInfo(String id) throws Exception {
		UserMailInfo userMailInfo = dao.get(id);
		UserMailInfoBean userMailInfoBean = new UserMailInfoBean();
		BeanUtil.objectCopy(userMailInfo, userMailInfoBean);
		return userMailInfoBean;
	}

	public List queryUserMailInfo(UserInfo userInfo) {
		String condition = " and mail_user_id='" + userInfo.getUserID() + "' ";
		List list = dao.queryForList(condition);
		return list;
	}

	public String queryMailLatestList(UserInfo userInfo) {
		// TODO Auto-generated method stub
		List mailList = queryUserMailInfo(userInfo);
		List list;
		if (mailList != null && mailList.size() > MAIL_MAX_NUM) {
			list = mailList.subList(0, MAIL_MAX_NUM - 1);
		} else {
			list = mailList;
		}
		StringBuffer outHtml = new StringBuffer("");
		DynaBean bean;
		for (int i = 0; list != null && i < list.size(); i++) {
			bean = (DynaBean) list.get(i);
			outHtml.append(".<a href=\"mailto:");
			outHtml.append(bean.get("email_address"));
			outHtml.append("\">");
			outHtml.append(bean.get("mail_name"));
			outHtml.append("</a>");
			outHtml.append("<br />");
		}
		return outHtml.toString();
	}
}
