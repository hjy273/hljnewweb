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
import com.cabletech.linepatrol.project.beans.RemedyTypeBean;
import com.cabletech.linepatrol.project.dao.RemedyTypeDao;
import com.cabletech.linepatrol.project.domain.ProjectRemedyType;
import com.cabletech.linepatrol.project.templates.RemedyInfoTmplate;

@Service
@Transactional
public class RemedyTypeManager extends BaseBisinessObject {
	private static Logger logger = Logger.getLogger(RemedyTypeManager.class.getName());
	// private RemedyItemDAOImpl itemDAO = new RemedyItemDAOImpl();
	@Resource(name = "remedyTypeDao")
	private RemedyTypeDao dao;
	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	/**
	 * ִ�л�ȡ�����������б���Ϣ
	 * 
	 * @param request
	 *            HttpServletRequest �û���������
	 * @param userInfo
	 * @param form
	 *            ActionForm �û������
	 * @return List �����������б���Ϣ
	 * @throws Exception
	 */
	public List getRemedyItemTypeList(UserInfo userInfo) {
		ConditionGenerate conditionGenerate = new ConditionGenerate();
		String condition = conditionGenerate.getUserQueryCondition(userInfo);
		condition = condition + " order by t.remedyitemid,t.typename,t.id ";
		return dao.queryList(condition);
	}

	/**
	 * �����û�regionid�õ�������
	 * 
	 * @param user
	 * @return
	 */
	public List getItemsByRegionID(UserInfo user) {
		String sql = " select id,itemname ";
		sql += " from lp_remedyitem ";
		sql += " where regionid='" + user.getRegionid() + "' ";
		sql += " and state='1' ";
		logger.info("��ѯ����������sql��" + sql);
		List list = dao.getJdbcTemplate().queryForList(sql);
		return list;
	}

	/**
	 * ����������ѯ�������
	 * 
	 * @param itemID
	 *            ������ĿID
	 * @param typeName
	 *            �����������
	 * @return
	 */
	public boolean getTypessByIIDAndTName(int itemID, String typeName) {
		boolean flag = false;
		String condition = " and t.remedyitemid=" + itemID;
		condition += " and t.typename='" + typeName + "' ";
		List list = dao.queryList(condition);
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
		String condition = " and t.remedyitemid=" + bean.getItemID();
		condition += " and t.typename='" + bean.getTypeName() + "' ";
		condition += " and t.id<>" + bean.getTid() + " ";
		List list = dao.queryList(condition);
		if (list == null || list.size() == 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * �����������
	 * 
	 * @param bean
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public boolean addType(RemedyTypeBean bean) throws Exception {
		ProjectRemedyType type = new ProjectRemedyType();
		BeanUtil.objectCopy(bean, type);
		type.setState("1");
		return dao.addType(type);
	}

	/**
	 * ����������ѯ�������
	 */
	public List getTypes(RemedyTypeBean bean) {
		String condition = "";
		if (bean.getTypeName() != null && !bean.getTypeName().trim().equals("")) {
			condition += " and t.typename like '%" + bean.getTypeName() + "%' ";
		}
		if (bean.getItemID() != -1) {
			condition += " and t.remedyitemid='" + bean.getItemID() + "'";
		}
		return dao.queryList(condition);
	}

	/**
	 * ����������id��ѯ�������
	 */

	public List getTypeByItemID(String id) {
		String condition = " and t.remedyitemid=" + id;
		return dao.queryList(condition);
	}

	/**
	 * ����id��ѯ�������
	 * 
	 * @param id
	 * @return
	 */
	public RemedyTypeBean getTypeById(String id) throws Exception {
		ProjectRemedyType type = dao.get(new Integer(id));
		RemedyTypeBean bean = new RemedyTypeBean();
		BeanUtil.objectCopy(type, bean);
		return bean;
	}

	/**
	 * �޸��������
	 * 
	 * @param bean
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public boolean editType(RemedyTypeBean bean) throws Exception {
		ProjectRemedyType type = new ProjectRemedyType();
		BeanUtil.objectCopy(bean, type);
		type.setState("1");
		return dao.editType(type);
	}

	/**
	 * ɾ���������
	 * 
	 * @param bean
	 * @return
	 */
	public boolean deleteType(String id) {
		ProjectRemedyType type = dao.getTypeById(id);
		type.setState("0");
		return dao.editType(type);
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
	public void exportStorage(List list, HttpServletResponse response) throws Exception {
		initResponse(response, "��������б���Ϣ.xls");
		OutputStream out = response.getOutputStream();
		String urlPath = ReportConfig.getInstance().getUrlPath("report.remedyTypeList");
		RemedyInfoTmplate template = new RemedyInfoTmplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.doExportType(list, excelstyle);
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

}
