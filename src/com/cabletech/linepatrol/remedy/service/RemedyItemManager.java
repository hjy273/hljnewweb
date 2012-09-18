package com.cabletech.linepatrol.remedy.service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.config.ReportConfig;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.linepatrol.remedy.beans.RemedyItemBean;
import com.cabletech.linepatrol.remedy.dao.RemedyItemDao;
import com.cabletech.linepatrol.remedy.domain.RemedyItem;
import com.cabletech.linepatrol.remedy.templates.RemedyInfoTmplate;

public class RemedyItemManager extends BaseBisinessObject {
	private static Logger logger = Logger.getLogger(RemedyItemManager.class.getName());
	private RemedyItemDao itemDAO = new RemedyItemDao();
	private OracleIDImpl ora = new OracleIDImpl();
	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	/**
	 * �����û��õ�����
	 * @param user
	 * @return
	 */
	public List getRegions(UserInfo user) {
		return itemDAO.getRegions(user);
	}

	public String getRegionNameById(String regionId) {
		return itemDAO.getRegionNameById(regionId);
	}

	/**
	 * ����ʱ�ж�������Ŀ�����Ƿ��ظ�
	 * @param regionID
	 * @param itemName
	 * @return
	 */
	public boolean isHaveItem(String regionID, String itemName) {
		boolean flag = false;
		List list = itemDAO.getItemsByRIDAndIName(regionID, itemName);
		if (list == null || list.size() == 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * �޸�ʱ�ж�������Ŀ�����Ƿ��ظ�
	 * @param regionID
	 * @param itemName
	 * @return
	 */
	public boolean isHaveItem(RemedyItemBean bean) {
		boolean flag = false;
		List list = itemDAO.getItemsByBean(bean);
		if (list == null || list.size() == 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * ����������Ŀ
	 * @param bean
	 * @return
	 */
	public boolean addItem(RemedyItemBean bean) {
		RemedyItem item = new RemedyItem();
		try {
			int tid = ora.getIntSeq("remedy_item");
			BeanUtil.objectCopy(bean, item);
			bean.setId(Integer.toString(tid));
			item.setTid(tid);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return itemDAO.addItem(item);
	}

	/**
	 * ����������ѯ������Ŀ
	 */
	public List getItems(RemedyItemBean bean) {
		return itemDAO.getItems(bean);
	}

	/**
	 * ����id��ѯ������
	 * @param id
	 * @return
	 */
	public BasicDynaBean getItemById(String id) {
		BasicDynaBean bean = null;
		List list = itemDAO.getItemById(id);
		if (list != null && list.size() > 0) {
			bean = (BasicDynaBean) list.get(0);
		}
		return bean;
	}

	/**
	 * �޸�������Ŀ
	 * @param bean
	 * @return
	 */
	public boolean editItem(RemedyItemBean bean) {
		RemedyItem item = new RemedyItem();
		try {
			BeanUtil.objectCopy(bean, item);
			item.setTid(bean.getTid());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return itemDAO.editItem(item);
	}

	/**
	 * ɾ��������Ŀ
	 * @param bean
	 * @return
	 */
	public boolean deleteItem(String id) {
		return itemDAO.deleteItem(id);
	}

	/**
	 * �ж��������û��������
	 * @param id
	 * @return
	 */
	public boolean getItemByApply(String id) {
		List list = itemDAO.getItemByApply(id);
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
	 * @throws IOException
	 */
	public void exportStorage(List list, HttpServletResponse response) {
		try {
			initResponse(response, "�������б���Ϣ.xls");
			OutputStream out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath("report.remedyitemList");
			RemedyInfoTmplate template = new RemedyInfoTmplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.doExportItem(list, excelstyle);
			template.write(out);
		} catch (Exception e) {
			logger.info(" ������" + e.getMessage());
			e.getStackTrace();
		}

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

}
