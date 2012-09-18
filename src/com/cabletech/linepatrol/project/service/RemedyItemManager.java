package com.cabletech.linepatrol.project.service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.config.ReportConfig;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.linepatrol.project.beans.RemedyItemBean;
import com.cabletech.linepatrol.project.dao.RemedyItemDao;
import com.cabletech.linepatrol.project.domain.ProjectRemedyItem;
import com.cabletech.linepatrol.project.templates.RemedyInfoTmplate;

@Service
@Transactional
public class RemedyItemManager extends BaseBisinessObject {
	private static Logger logger = Logger.getLogger(RemedyItemManager.class.getName());
	@Resource(name = "remedyItemDao")
	private RemedyItemDao dao = new RemedyItemDao();
	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	/**
	 * ִ�л�ȡ�������б���Ϣ
	 * 
	 * @param request
	 *            HttpServletRequest �û���������
	 * @param userInfo
	 * @param form
	 *            ActionForm �û������
	 * @return List �������б���Ϣ
	 * @throws Exception
	 */
	public List getRemedyItemList(UserInfo userInfo) {
		ConditionGenerate conditionGenerate = new ConditionGenerate();
		String condition = conditionGenerate.getUserQueryCondition(userInfo);
		condition = condition + " order by t.itemname,t.id ";
		return dao.queryList(condition);
	}

	/**
	 * �����û��õ�����
	 * 
	 * @param user
	 * @return
	 */
	public List getRegions(UserInfo user) {
		String sql = " select regionid,regionname ";
		sql += " from region where regionid='" + user.getRegionid() + "'";
		logger.info("��ѯ��������sql��" + sql);
		List regionList = dao.getJdbcTemplate().queryForList(sql);
		return regionList;
	}

	/**
	 * ����ʱ�ж�������Ŀ�����Ƿ��ظ�
	 * 
	 * @param regionID
	 * @param itemName
	 * @return
	 */
	public boolean isHaveItem(String regionID, String itemName) {
		boolean flag = false;
		String condition = " and r.regionid='" + regionID + "' ";
		condition += " and itemname='" + itemName + "' ";
		List list = dao.queryList(condition);
		if (list == null || list.size() == 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * �޸�ʱ�ж�������Ŀ�����Ƿ��ظ�
	 * 
	 * @param regionID
	 * @param itemName
	 * @return
	 */
	public boolean isHaveItem(RemedyItemBean bean) {
		boolean flag = false;
		String condition = " and r.regionid='" + bean.getRegionID() + "' ";
		condition += " and itemname='" + bean.getItemName() + "' ";
		condition += " and t.id<>" + bean.getTid() + " ";
		List list = dao.queryList(condition);
		if (list == null || list.size() == 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * ����������Ŀ
	 * 
	 * @param bean
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public boolean addItem(RemedyItemBean bean) throws Exception {
		ProjectRemedyItem item = new ProjectRemedyItem();
		BeanUtil.objectCopy(bean, item);
		item.setState("1");
		bean.setTid(item.getTid());
		return dao.addItem(item);
	}

	/**
	 * ����������ѯ������Ŀ
	 */
	public List getItems(RemedyItemBean bean) {
		String condition = "";
		if (bean.getItemName() != null && !bean.getItemName().trim().equals("")) {
			condition += " and t.itemname like '%" + bean.getItemName() + "%' ";
		}
		if (bean.getRegionID() != null && !bean.getRegionID().trim().equals("")) {
			condition += " and t.regionid='" + bean.getRegionID() + "'";
		}
		return dao.queryList(condition);
	}

	/**
	 * ����id��ѯ������
	 * 
	 * @param id
	 * @return
	 */
	public RemedyItemBean getItemById(String id) throws Exception {
		ProjectRemedyItem item = dao.getItemById(id);
		RemedyItemBean bean = new RemedyItemBean();
		BeanUtil.objectCopy(item, bean);
		return bean;
	}

	/**
	 * �޸�������Ŀ
	 * 
	 * @param bean
	 * @return
	 */
	public boolean editItem(RemedyItemBean bean) throws Exception {
		ProjectRemedyItem item = new ProjectRemedyItem();
		BeanUtil.objectCopy(bean, item);
		item.setState("1");
		return dao.editItem(item);
	}

	/**
	 * ɾ��������Ŀ
	 * 
	 * @param bean
	 * @return
	 */
	public boolean deleteItem(String id) {
		ProjectRemedyItem item = dao.getItemById(id);
		item.setState("0");
		return dao.editItem(item);
	}

	/**
	 * �ж��������û��������
	 * 
	 * @param id
	 * @return
	 */
	public boolean getItemByApply(String id) {
		String condition = " and exists( ";
		condition += " select apply.id from lp_remedy apply,lp_remedy_item item ";
		condition += " where apply.id=item.remedyid and item.item_id='" + id + "' ";
		condition += " ) ";
		List list = dao.queryList(condition);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * ���ݲ�ѯ����������Ŀ�б���е�������
	 * 
	 * @param list
	 *            List
	 * @param response
	 *            HttpServletResponse
	 * @throws Exception
	 * @throws IOException
	 */
	public void exportStorage(List list, HttpServletResponse response) throws Exception {
		initResponse(response, "�������б���Ϣ.xls");
		OutputStream out = response.getOutputStream();
		String urlPath = ReportConfig.getInstance().getUrlPath( "report.remedyitemList");
		RemedyInfoTmplate template = new RemedyInfoTmplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.doExportItem(list, excelstyle);
		template.write(out);
	}

	/**
	 * �����������ͷ
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param fileName
	 *            String
	 * @throws UnsupportedEncodingException
	 */
	private void initResponse(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
		response.reset();
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(fileName.getBytes(), "iso-8859-1"));
	}

	public String getRegionNameById(String regionId) {
		// TODO Auto-generated method stub
		String sql = " select regionname ";
		sql += " from region where regionid='" + regionId + "' ";
		logger.info("��ѯ��������sql��" + sql);
		List regionList = dao.getJdbcTemplate().queryForList(sql, String.class);
		if (regionList != null && !regionList.isEmpty()) {
			String regionName = (String) regionList.get(0);
			return regionName;
		}
		return "";
	}

}
