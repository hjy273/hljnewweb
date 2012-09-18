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
import com.cabletech.linepatrol.remedy.beans.RemedyTypeBean;
import com.cabletech.linepatrol.remedy.dao.RemedyTypeDao;
import com.cabletech.linepatrol.remedy.domain.RemedyType;
import com.cabletech.linepatrol.remedy.templates.RemedyInfoTmplate;

public class RemedyTypeManager extends BaseBisinessObject {
	private static Logger logger = Logger.getLogger(RemedyTypeManager.class.getName());
	//private RemedyItemDAOImpl itemDAO = new RemedyItemDAOImpl();
	private RemedyTypeDao dao = new RemedyTypeDao();
	private OracleIDImpl ora = new OracleIDImpl();
	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	/**
	 * �����û�regionid�õ�������
	 * @param user
	 * @return
	 */
	public List getItemsByRegionID(UserInfo user) {
		return dao.getItemsByRegionID(user);
	}

	/**
	 * ����������ѯ�������
	 * @param itemID ������ĿID
	 * @param typeName �����������
	 * @return
	 */
	public boolean getTypessByIIDAndTName(int itemID, String typeName) {
		boolean flag = false;
		List list = dao.getTypessByIIDAndTName(itemID, typeName);
		if (list == null || list.size() == 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * �޸�ʱ�ж�������������Ƿ��ظ�
	 *
	 * @return
	 */
	public boolean getTypeByBean(RemedyTypeBean bean) {
		boolean flag = false;
		List list = dao.getTypeByBean(bean);
		if (list == null || list.size() == 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * �����������
	 * @param bean
	 * @return
	 */
	public boolean addType(RemedyTypeBean bean) {
		RemedyType type = new RemedyType();
		try {
			int tid = ora.getIntSeq("remedy_type");
			BeanUtil.objectCopy(bean, type);
			type.setTid(tid);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return dao.addType(type);
	}

	/**
	 * ����������ѯ�������
	 */
	public List getTypes(RemedyTypeBean bean) {
		return dao.getTypes(bean);
	}

	/**
	 * ����������id��ѯ�������
	 */

	public List getTypeByItemID(String id) {
		return dao.getTypeByItemID(id);
	}

	/**
	 * ����id��ѯ�������
	 * @param id
	 * @return
	 */
	public BasicDynaBean getTypeById(String id) {
		BasicDynaBean bean = null;
		List list = dao.getTypeById(id);
		if (list != null && list.size() > 0) {
			bean = (BasicDynaBean) list.get(0);
		}
		return bean;
	}

	/**
	 * �޸��������
	 * @param bean
	 * @return
	 */
	public boolean editType(RemedyTypeBean bean) {
		RemedyType type = new RemedyType();
		try {
			BeanUtil.objectCopy(bean, type);
			type.setTid(bean.getTid());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return dao.editType(type);
	}

	/**
	 * ɾ���������
	 * @param bean
	 * @return
	 */
	public boolean deleteType(String id) {
		return dao.deleteType(id);
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
			initResponse(response, "��������б���Ϣ.xls");
			OutputStream out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.remedyTypeList");
			RemedyInfoTmplate template = new RemedyInfoTmplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.doExportType(list, excelstyle);
			template.write(out);
		} catch (Exception e) {
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
