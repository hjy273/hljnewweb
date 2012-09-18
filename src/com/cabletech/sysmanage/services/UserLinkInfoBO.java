package com.cabletech.sysmanage.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.DynaBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.sysmanage.beans.UserLinkInfoBean;
import com.cabletech.sysmanage.dao.UserLinkInfoDao;
import com.cabletech.sysmanage.domainobjects.UserLinkInfo;

@Service
@Transactional
public class UserLinkInfoBO extends EntityManager<UserLinkInfo, String> {
	public static final int LINK_MAX_NUM = 3;

	private static final Object COMMON_LINK_TYPE = "0";

	@Resource(name = "userLinkInfoDao")
	private UserLinkInfoDao dao;

	@Override
	protected HibernateDao<UserLinkInfo, String> getEntityDao() {
		return dao;
	}

	public boolean addUserLinkInfo(UserLinkInfoBean userLinkInfoBean,
			UserInfo userInfo) throws Exception {
		boolean flag = false;
		String orderNumberStr = userLinkInfoBean.getOrderNumberStr();
		int orderNumber = Integer.parseInt(orderNumberStr);
		userLinkInfoBean.setOrderNumber(orderNumber);
		UserLinkInfo userLinkInfo = new UserLinkInfo();
		BeanUtil.objectCopy(userLinkInfoBean, userLinkInfo);
		userLinkInfo.setLinkUserId(userInfo.getUserID());
		dao.save(userLinkInfo);
		flag = true;
		return flag;
	}

	public boolean updateUserLinkInfo(UserLinkInfoBean userLinkInfoBean,
			UserInfo userInfo) throws Exception {
		boolean flag = false;
		String orderNumberStr = userLinkInfoBean.getOrderNumberStr();
		int orderNumber = 0;
		if(orderNumberStr!=null || !"".equals(orderNumberStr)){
			orderNumber = Integer.parseInt(orderNumberStr);
		}
		userLinkInfoBean.setOrderNumber(orderNumber);
		UserLinkInfo userLinkInfo = new UserLinkInfo();
		BeanUtil.objectCopy(userLinkInfoBean, userLinkInfo);
		userLinkInfo.setLinkUserId(userInfo.getUserID());
		dao.save(userLinkInfo);
		flag = true;
		return flag;
	}

	public boolean deleteUserLinkInfo(String id, UserInfo userInfo)
			throws Exception {
		boolean flag = false;
		dao.delete(id);
		flag = true;
		return flag;
	}

	public UserLinkInfoBean viewUserLinkInfo(String id) throws Exception {
		UserLinkInfo userLinkInfo = dao.get(id);
		UserLinkInfoBean userLinkInfoBean = new UserLinkInfoBean();
		BeanUtil.objectCopy(userLinkInfo, userLinkInfoBean);
		return userLinkInfoBean;
	}

	public List queryUserLinkInfo(UserInfo userInfo) {
		String condition = " and link_user_id='" + userInfo.getUserID() + "' ";
		condition += " and link_type='1' ";
		List list = dao.queryForList(condition);
		return list;
	}

	public List queryUserCommonLinkInfo(UserInfo userInfo) {
		String condition = " and link_type='0' ";
		List list = dao.queryForList(condition);
		return list;
	}

	public String queryLinkLatestList(UserInfo userInfo) {
		// TODO Auto-generated method stub
		String condition = " and ( ";
		condition += " ( ";
		condition += " link_user_id='" + userInfo.getUserID() + "' ";
		condition += " and link_type='1' ";
		condition += " ) ";
		condition += " or (link_type='0') ";
		condition += " ) ";
		List linkList = dao.queryForList(condition);
		List list = new ArrayList();
		DynaBean bean;
		for (int i = 0, count = 0; linkList != null && i < linkList.size(); i++) {
			bean = (DynaBean) linkList.get(i);
			if (bean != null) {
				if (COMMON_LINK_TYPE.equals(bean.get("link_type"))) {
					list.add(bean);
				} else {
					count++;
					if (count <= LINK_MAX_NUM) {
						list.add(bean);
					}
				}
			}
		}
		StringBuffer outHtml = new StringBuffer("");
		for (int i = 0; list != null && i < list.size(); i++) {
			bean = (DynaBean) list.get(i);
			outHtml.append(".<a href=\"");
			outHtml.append(bean.get("link_address"));
			outHtml.append("\" target='_blank'>");
			outHtml.append(bean.get("link_name"));
			outHtml.append("</a>");
			outHtml.append("<br />");
		}
		return outHtml.toString();
	}
}
