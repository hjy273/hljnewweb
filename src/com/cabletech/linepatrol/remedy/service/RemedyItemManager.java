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
	 * 根据用户得到区域
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
	 * 增加时判断修缮项目名称是否重复
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
	 * 修改时判断修缮项目名称是否重复
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
	 * 保存修缮项目
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
	 * 根据条件查询修缮项目
	 */
	public List getItems(RemedyItemBean bean) {
		return itemDAO.getItems(bean);
	}

	/**
	 * 根据id查询修缮项
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
	 * 修改修缮项目
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
	 * 删除修缮项目
	 * @param bean
	 * @return
	 */
	public boolean deleteItem(String id) {
		return itemDAO.deleteItem(id);
	}

	/**
	 * 判断申请表有没有修缮项
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
	 * 根据查询到的修缮项目列表进行导出动作
	 * 
	 * @param list
	 *            List
	 * @param response
	 *            HttpServletResponse
	 * @throws IOException
	 */
	public void exportStorage(List list, HttpServletResponse response) {
		try {
			initResponse(response, "修缮项列表信息.xls");
			OutputStream out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath("report.remedyitemList");
			RemedyInfoTmplate template = new RemedyInfoTmplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.doExportItem(list, excelstyle);
			template.write(out);
		} catch (Exception e) {
			logger.info(" 导出：" + e.getMessage());
			e.getStackTrace();
		}

	}

	/**
	 * 创建输出流的头
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
