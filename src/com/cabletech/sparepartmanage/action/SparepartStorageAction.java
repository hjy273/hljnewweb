package com.cabletech.sparepartmanage.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.cabletech.baseinfo.domainobjects.Depart;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.ContractorBO;
import com.cabletech.baseinfo.services.DepartBO;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.power.CheckPower;
import com.cabletech.sparepartmanage.beans.SparepartBaseInfoBean;
import com.cabletech.sparepartmanage.beans.SparepartStorageBean;
import com.cabletech.sparepartmanage.dao.SeparepartBaseInfoDAO;
import com.cabletech.sparepartmanage.domainobjects.SparepartConstant;
import com.cabletech.sparepartmanage.domainobjects.SparepartStorage;
import com.cabletech.sparepartmanage.services.SeparepartBaseInfoService;
import com.cabletech.sparepartmanage.services.SparepartStorageBO;
import com.cabletech.watchinfo.templates.WatchDetailTemplate;

/**
 * SparepartStorageAction 备件库存Action 完成备件的入库、领用、归还和重新入库以及备件库存的查询
 */
public class SparepartStorageAction extends BaseDispatchAction {
	private static Logger logger = Logger.getLogger(SeparepartBaseInfoDAO.class
			.getName());
	private SparepartStorageBO bo = new SparepartStorageBO();
	private SeparepartBaseInfoService basebo = new SeparepartBaseInfoService();
	private static Pattern numPattern = Pattern.compile("[1-9]\\d{0,}[.]{1}[0]{1}");
	/**
	 * 判断字符串是不是由数字组成
	 * @param num
	 * @return
	 */
	public  boolean isNumPattern(String num){
		Matcher s = numPattern.matcher(num);
		return s.matches();
	}

	/**
	 * 备件的入库表单
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward saveToStorageForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!CheckPower.checkPower(request.getSession(), "90701")) {
			return mapping.findForward("powererror");
		}
		List facList = basebo.getAllFactory();
		request.setAttribute("facList", facList);
		List savePosition = bo.findPosition();
		request.setAttribute("savePosition", savePosition);
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		request.setAttribute("user_name", user.getUserName());
		request.setAttribute("user_id", user.getUserID());
		request.setAttribute("user_dept_id", user.getDeptID());
		request.setAttribute("user_dept_name", user.getDeptName());
		return mapping.findForward("saveToStorageForm");
	}

	/**
	 * 备件的入库
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward saveToStorage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		if (!CheckPower.checkPower(request.getSession(), "90701")) {
			return mapping.findForward("powererror");
		}
		SparepartStorageBean bean = (SparepartStorageBean) form;
		bean.setInitStorage(bean.getStoragePosition());
		boolean flag = false;
		flag = bo.save(bean);
		if (flag) {
			return super.forwardInfoPage(mapping, request, "9070101ok");
		} else {
			return super.forwardErrorPage(mapping, request, "9070101err");
		}
	}

	/**
	 * 批量添加备件模板下载
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward downloadTemplet(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		try {
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String("备件模板.xls".getBytes(), "iso-8859-1"));
			OutputStream out = response.getOutputStream();

			WatchDetailTemplate template = new WatchDetailTemplate(servlet
					.getServletContext().getRealPath("/upload")
					+ "/spare.xls");
			template.write(out);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}


	/**
	 * 上传excel
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward upLoadShow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
		if (userinfo.getDeptype().equals("2")) { // 如果是代维公司是不允许添加备件入库的
			return forwardErrorPage(mapping, request, "partstockerror");
		}
		SparepartStorageBean bean = (SparepartStorageBean) form;

		String filename = bean.getFile().getFileName();
		if (filename.equals("") || filename == null) {
			return forwardErrorPage(mapping, request, "fileerror");
		}
		String path = servlet.getServletContext().getRealPath("/upload");
		List list = this.getUpInfo(bean, path, request);
		if (list == null || list.size() == 0) {
			return super.forwardErrorPage(mapping, request, "9070101expNull");
		} else {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).equals("exist")) {
					return super.forwardErrorPage(mapping, request,
					"9070101expFail");
				}
			}
		}
		boolean flag = false;
		flag = bo.batchInsertSparepartStorage(list);
		if (flag) {
			return super.forwardInfoPage(mapping, request, "9070101ok");
		} else {
			return super.forwardErrorPage(mapping, request, "9070101err");
		}
	}

	private List getUpInfo(SparepartStorageBean bean, String path,
			HttpServletRequest request) {
		List list = new ArrayList();
		if (!this.saveFile(bean, path)) {
			return null;
		}
		File file = new File(path + "\\spare_temp.xls");
		FileInputStream in = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		try {
			in = new FileInputStream(file);
			HSSFWorkbook workbook = new HSSFWorkbook(in);
			HSSFSheet sheet = workbook.getSheetAt(0);
			int lastRoNum = sheet.getLastRowNum();
			for (int rowIndex = 2; rowIndex < lastRoNum; rowIndex++) {
				SparepartStorageBean bean1 = new SparepartStorageBean();
				row = sheet.getRow(rowIndex);
				cell = row.getCell((short) 1);
				if (cell != null) {
					bean1.setStorageRemark(getCellValue(cell));
				}
				cell = row.getCell((short) 0);
				if (cell != null) {
					String serialNumber = getCellValue(cell);
					if (serialNumber.trim().equals("")) {
						continue;
					}
					String result = bo.getSerialNumber(serialNumber);// 1，已经存在
					// ,0不存在
					if (result.equals("1")) {
						list.add("exist");
						return list;
					}
					for (int i = 0; i < list.size(); i++) { // 判断excel中序列号是否重复
						SparepartStorageBean spartpart = (SparepartStorageBean) list
						.get(i);
						if (spartpart.getSerialNumber().equals(serialNumber)) {
							list.add("exist");
							return list;
						}
					}
					boolean isNum = this.isNumPattern(serialNumber.trim());
					if(isNum){
						double num  = Double.parseDouble(serialNumber);
						int serialN = (int)num;
						serialNumber = String.valueOf(serialN);
					}
					bean1.setSerialNumber(serialNumber);
					bean1.setStorageNumber(1);
					bean1.setDepartId(bean.getDepartId());
					bean1.setStoragePerson(bean.getStoragePerson());
					bean1.setSparePartId(bean.getSparePartId());
					bean1.setStoragePosition(bean.getStoragePosition());
					list.add(bean1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	protected String getCellValue(HSSFCell cell) {
		double numValue;
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		case HSSFCell.CELL_TYPE_NUMERIC:
			numValue = cell.getNumericCellValue();
			return String.valueOf(numValue);
		default:
			return "";

		}

	}

	/**
	 * 备件库存的查询表单
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward querySparepartStorageForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!CheckPower.checkPower(request.getSession(), "90703")) {
			return mapping.findForward("powererror");
		}
		List facList = basebo.getAllFactory();
		request.setAttribute("facList", facList);
		return mapping.findForward("querySparepartStorageForm");
	}

	/**
	 * 导入备件库查询所有的库存信息列表
	 */
	public ActionForward listSparepartStorageByReportBack(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String condition = "";	
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String regionId = user.getRegionid();
		String departId = user.getDeptID();
		String userType = user.getType();
		if (UserInfo.CITY_MUSER_TYPE.equals(userType)) {
			condition += (" and (storage.depart_id='" + departId + "' or exists(");
			condition += ("   select contractorid from contractorinfo ");
			condition += ("    where regionid='" + regionId + "' and contractorid=storage.depart_id");
			condition += (" )");
			condition += (")");
		}
		if (UserInfo.CITY_CUSER_TYPE.equals(userType)) {
			condition += (" and (storage.depart_id='" + departId + "' or exists(");
			condition += ("   select deptid from deptinfo ");
			condition += ("   where regionid='" + regionId + "' and deptid=storage.depart_id");
			condition += ("))");
		}
		List list = bo.listQuery(condition);
		request.getSession().setAttribute("QUERY_CONDITION",null);
		request.getSession().setAttribute("queryserialNumber", null);
		request.getSession().setAttribute("querypositionIds", null);
		request.getSession().setAttribute("STORAGE_LIST", list);
		request.getSession().setAttribute("EXPORT_STORAGE_LIST_S", list);
		return mapping.findForward("listSparepartStorage");
	}

	/**
	 * 备件库存的查询结果列表
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward listSparepartStorage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!CheckPower.checkPower(request.getSession(), "90703")) {
			return mapping.findForward("powererror");
		}
		String sparePartName = super.parseParameter(request
				.getParameter("sparePartName"), "part.spare_part_name", "'",
		"'");
		String sparePartModel = super.parseParameter(request
				.getParameter("sparePartModel"), "part.spare_part_model", "'",
		"'");
		String sparePartId = super.parseParameter(request
				.getParameter("spare_part_id"), "storage.spare_part_id", "'",
		"'");

		String sparePartState = super.parseParameter(request
				.getParameter("spare_part_state"), "storage.spare_part_state",
				"'", "'");
		String serialNumber = super.parseParameter(request
				.getParameter("serial_number"), "storage.serial_number", "'%",
		"%'");
		String storagePosition = request.getParameter("storage_position");
		String positionIds = "";
		if (storagePosition != null && !storagePosition.trim().equals("")) {
			positionIds = bo.getPositionIdsByName(storagePosition);// 仅查询库存位置
			if (positionIds == null || positionIds.trim().equals("")) {
				positionIds = null;
			}
		}
		String productFactory = super.parseParameter(request
				.getParameter("productFactory"), "part.product_factory", "'%",
		"%'");
		String condition = "";
		if ("1".equals(request.getParameter("reset_query"))) {
			condition += (" and storage.spare_part_id=" + sparePartId);
			condition += (" and part.spare_part_name=" + sparePartName);
			condition += (" and part.spare_part_model=" + sparePartModel);
			condition += (" and storage.spare_part_state=" + sparePartState);
			condition += (" and storage.serial_number like " + serialNumber);
			if (storagePosition != null && !storagePosition.trim().equals("")) {
				String positionId = bo.stringToString(positionIds);
				condition += (" and storage.storage_position in(" + positionId)+ ") ";
			}
			condition += (" and part.product_factory like " + productFactory);
			request.getSession().setAttribute("QUERY_CONDITION", condition);
		} else {
			condition = (String) request.getSession().getAttribute(
			"QUERY_CONDITION");
			if (condition == null || condition.equals("")) {
				condition = "";
			}
		}
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String regionId = user.getRegionid();
		String departId = user.getDeptID();
		String userType = user.getType();
		if (UserInfo.CITY_MUSER_TYPE.equals(userType)) {
			condition += (" and (storage.depart_id='" + departId + "' or exists(");
			condition += ("   select contractorid from contractorinfo ");
			condition += ("    where regionid='" + regionId + "' and contractorid=storage.depart_id");
			condition += (" )");
			condition += (")");
		}
		if (UserInfo.CITY_CUSER_TYPE.equals(userType)) {
			condition += (" and (storage.depart_id='" + departId + "' or exists(");
			condition += ("   select deptid from deptinfo ");
			condition += ("   where regionid='" + regionId + "' and deptid=storage.depart_id");
			condition += ("))");
		}
		List list = bo.listQuery(condition);
		String serialNumberQ = request.getParameter("serial_number");
		request.getSession().setAttribute("queryserialNumber", serialNumberQ);
		request.getSession().setAttribute("querypositionIds", positionIds);
		request.getSession().setAttribute("STORAGE_LIST", list);
		request.getSession().setAttribute("EXPORT_STORAGE_LIST_S", list);
		return mapping.findForward("listSparepartStorage");
	}

	/**
	 * 返回重新查找list列表
	 */
	public ActionForward listSparepartStorageByBack(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!CheckPower.checkPower(request.getSession(), "90703")) {
			return mapping.findForward("powererror");
		}
		String sparePartName = super.parseParameter(request
				.getParameter("sparePartName"), "part.spare_part_name", "'",
		"'");
		String sparePartModel = super.parseParameter(request
				.getParameter("sparePartModel"), "part.spare_part_model", "'",
		"'");
		String sparePartId = super.parseParameter(request
				.getParameter("spare_part_id"), "storage.spare_part_id", "'",
		"'");

		String sparePartState = super.parseParameter(request
				.getParameter("spare_part_state"), "storage.spare_part_state",
				"'", "'");
		String serialNumber = super.parseParameter(request
				.getParameter("serial_number"), "storage.serial_number", "'%",
		"%'");		
		String storagePosition = request.getParameter("storage_position");
		String positionIds = "";
		if (storagePosition != null && !storagePosition.trim().equals("")) {
			positionIds = bo.getPositionIdsByName(storagePosition);
			if (positionIds == null || positionIds.trim().equals("")) {
				positionIds = null;
			}
		}
		String productFactory = super.parseParameter(request
				.getParameter("productFactory"), "part.product_factory", "'%",
		"%'");
		String condition = "";
		if ("1".equals(request.getParameter("reset_query"))) {
			condition += (" and storage.spare_part_id=" + sparePartId);
			condition += (" and part.spare_part_name=" + sparePartName);
			condition += (" and part.spare_part_model=" + sparePartModel);
			condition += (" and storage.spare_part_state=" + sparePartState);
			condition += (" and storage.serial_number like " + serialNumber);
			if (storagePosition != null && !storagePosition.trim().equals("")) {
				String positionId = bo.stringToString(positionIds);
				condition += (" and storage.storage_position in(" + positionId)
				+ ") ";
			}
			condition += (" and part.product_factory like " + productFactory);
			request.getSession().setAttribute("QUERY_CONDITION", condition);
		} else {
			condition = (String) request.getSession().getAttribute(
			"QUERY_CONDITION");
			if (condition == null || condition.equals("")) {
				condition = "";
			}
		}

		UserInfo user = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
		String regionId = user.getRegionid();
		String departId = user.getDeptID();
		String userType = user.getType();
		if (UserInfo.PROVINCE_MUSER_TYPE.equals(userType)) {

		}
		if (UserInfo.CITY_MUSER_TYPE.equals(userType)) {
			condition += (" and (storage.depart_id='" + departId + "' or exists(");
			condition += ("   select contractorid from contractorinfo ");
			condition += ("    where regionid='" + regionId + "' and contractorid=storage.depart_id");
			condition += (" )");
			condition += (")");
		}
		if (UserInfo.PROVINCE_CUSER_TYPE.equals(userType)) {

		}
		if (UserInfo.CITY_CUSER_TYPE.equals(userType)) {
			condition += (" and (storage.depart_id='" + departId + "' or exists(");
			condition += ("   select deptid from deptinfo ");
			condition += ("   where regionid='" + regionId + "' and deptid=storage.depart_id");
			condition += ("))");
		}
		List list = bo.listQuery(condition);
		request.getSession().setAttribute("STORAGE_LIST", list);
		request.getSession().setAttribute("EXPORT_STORAGE_LIST_S", list);
		return mapping.findForward("listSparepartStorage");
	}

	/**
	 * 导出查询到的备件库存列表
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward exportStorageList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = (List) request.getSession().getAttribute("EXPORT_STORAGE_LIST");
		bo.exportStorage(list, response);
		return null;
	}

	public ActionForward exportStorageListForStorageList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = (List) request.getSession().getAttribute("EXPORT_STORAGE_LIST_S");
		bo.exportStorage(list, response);
		return null;
	}

	public ActionForward querySerialNumber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/json; charset=GBK");
		String num = request.getParameter("serialNumber");
		String str = "";
		str = bo.getSerialNumber(num);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(str);
			out.flush();
			logger.info(str + "名称");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return null;
	}

	/**
	 * 根据基本表去库存表查询
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward listSparepartStorageForOneByMobile(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!CheckPower.checkPower(request.getSession(), "90703")) {
			return mapping.findForward("powererror");
		}
		String bl = request.getParameter("bl");
		String id = request.getParameter("spare_part_id");
		String flag = request.getParameter("flag");
		if (bl == null || bl.trim().equals("")) {
			bl = (String) request.getSession().getAttribute("bl");
		}
		if (id == null || id.trim().equals("")) {
			id = (String) request.getSession().getAttribute("sparepartIdM");
		}
		if (flag == null || flag.trim().equals("")) {
			flag = (String) request.getSession().getAttribute("flagM");
		}
		String sparePartId = super.parseParameter(id, "storage.spare_part_id",
				"'", "'");
		String condition = "";

		String queryserialNumber = (String)request.getSession().getAttribute("queryserialNumber");

		if(queryserialNumber!=null && queryserialNumber.trim().length()>0){
			condition += " and storage.serial_number like'%" + queryserialNumber + "%'";
		}
		String querypositionIds = (String) request.getSession().getAttribute("querypositionIds");
		if(querypositionIds!=null && querypositionIds.trim().length()>0){
			String positionId = bo.stringToString(querypositionIds);
			condition += " and storage.storage_position in(" + positionId + ")";
		}

		if ("3".equals(flag)) {
			condition += (" and storage.spare_part_id=" + sparePartId);
			request.getSession().setAttribute("sqlCondition", condition);
		} else {
			condition = (String) request.getSession().getAttribute(
			"sqlCondition");
			if (condition == null || condition.equals("")) {
				condition = "";
			}
		}
		UserInfo user = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
		String regionId = user.getRegionid();
		String departId = user.getDeptID();
		String userType = user.getType();
		if (UserInfo.PROVINCE_MUSER_TYPE.equals(userType)) {

		}
		if (UserInfo.CITY_MUSER_TYPE.equals(userType)) {
			condition += (" and (storage.depart_id='" + departId + "' or exists(");
			condition += ("   select contractorid from contractorinfo ");
			condition += ("    where regionid='" + regionId + "' and contractorid=storage.depart_id");
			condition += (" )");
			condition += (")");
		}
		if (UserInfo.PROVINCE_CUSER_TYPE.equals(userType)) {

		}
		if (UserInfo.CITY_CUSER_TYPE.equals(userType)) {
			condition += (" and (storage.depart_id='" + departId + "' or exists(");
			condition += ("   select deptid from deptinfo ");
			condition += ("   where regionid='" + regionId + "' and deptid=storage.depart_id");
			condition += ("))");
		}
		List list = bo.listForMobile(condition);
		request.getSession().setAttribute("STORAGE_LIST", list);
		request.getSession().setAttribute("EXPORT_STORAGE_LIST", list);
		request.getSession().setAttribute("bl", bl);
		request.getSession().setAttribute("sparepartIdM", id);
		request.getSession().setAttribute("flagM", flag);
		return mapping.findForward("listSparepartStorageForOne");
	}

	/**
	 * 备件入库的修改表单
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward updateSavedStorageForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!CheckPower.checkPower(request.getSession(), "90704")) {
			return mapping.findForward("powererror");
		}
		List list = basebo.getAllSeparepart();
		request.setAttribute("part_list", list);
		String storageId = request.getParameter("storage_id");
		SparepartStorageBean bean = new SparepartStorageBean();
		SparepartStorage ss = bo.loadStorage(storageId);
		BeanUtil.objectCopy(ss, bean);
		DepartBO dbo = new DepartBO();
		ContractorBO cbo = new ContractorBO();
		Depart depart = dbo.loadDepart(bean.getDepartId());
		bean.setSparePartName(basebo.getOneSparepart(bean.getSparePartId())
				.getSparePartName());
		if (SparepartConstant.MOBILE_WAIT_USE.equals(bean.getSparePartState())) {
			bean.setDepartName(depart.getDeptName());
		} else {
			bean.setDepartName(cbo.loadContractor(bean.getDepartId())
					.getContractorName());
		}
		List saveOptions = bo.findPosition();
		request.setAttribute("saveOptions", saveOptions);
		request.setAttribute("one_storage", bean);
		request.setAttribute("SparepartStorageBean", bean);
		UserInfo user = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
		request.setAttribute("user_name", user.getUserName());
		return mapping.findForward("updateSaveToStorageForm");
	}

	/**
	 * 备件的入库删除
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward deleteSavedStorage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!CheckPower.checkPower(request.getSession(), "90705")) {
			return mapping.findForward("powererror");
		}
		String storageId = request.getParameter("storage_id");
		SparepartStorage storage = bo.loadStorage(storageId);
		String sparepartId = storage.getSparePartId();
		//SparepartStorageBean bean = new SparepartStorageBean();
		//BeanUtil.objectCopy(bo.loadStorage(storageId), bean);
		boolean flag = bo.deleteSaved(storage,sparepartId);
		if (flag) {
			return super.forwardInfoPage(mapping, request, "9070106ok");
		} else {
			return super.forwardErrorPage(mapping, request, "9070106err");
		}
	}

	/**
	 * 备件的入库修改
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward updateSavedStorage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!CheckPower.checkPower(request.getSession(), "90704")) {
			return mapping.findForward("powererror");
		}
		SparepartStorageBean bean = (SparepartStorageBean) form;
		bean.setInitStorage(bean.getStoragePosition());
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		bean.setStoragePerson(user.getUserID());
		boolean flag = bo.updateSaved(bean);
		if (flag) {
			return super.forwardInfoPage(mapping, request, "9070105ok");
		} else {
			Map param = new HashMap();
			param.put("storage_id", bean.getTid());
			return super.forwardErrorPage(mapping, request, "9070105err");
		}
	}

	/**
	 * 显示审核的查询表单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward querySparepartStorageFormForAu(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List facList = basebo.getAllFactory();
		request.setAttribute("facList", facList);
		return mapping.findForward("querySparepartStorageFormForAu");
	}

	/**
	 * 显示代维领用过的备件列表，方便代维申请使用备件
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listSparepartStorageForRe(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.getSession().setAttribute("STORAGE_LIST", null);
		request.getSession().setAttribute("EXPORT_STORAGE_LIST", null);
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
		String deptid = userinfo.getDeptID();
		List storageList = bo.SparepartStorageForRe(deptid);
		request.getSession().setAttribute("STORAGE_LIST", storageList);
		request.getSession().setAttribute("EXPORT_STORAGE_LIST", storageList);
		return mapping.findForward("listSparepartStorageForRe");
	}

	/**
	 * 备件申请导出excel
	 * 
	 */
	public ActionForward exportStorageListForApply(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = (List) request.getSession().getAttribute(
		"EXPORT_STORAGE_LIST");
		bo.exportStorageListForApply(list, response);
		return null;
	}

	/**
	 * 备件入库的详细信息查看
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward viewSavedStorage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!CheckPower.checkPower(request.getSession(), "90703")) {
			return mapping.findForward("powererror");
		}
		String id = request.getParameter("base_id");
		List spareStroages = bo.findSparepartsByBaseId(id);
		request.getSession().setAttribute("sparePartStroages", spareStroages);
		SparepartBaseInfoBean baseBean = bo.findSparepartInfoById(id);
		request.getSession().setAttribute("baseInfo", baseBean);
		request.getSession().setAttribute("number", spareStroages.size() + "");
		return mapping.findForward("viewSavedStorage");
	}

	/**
	 * 备件的领用表单
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward takeOutFromStorageForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!CheckPower.checkPower(request.getSession(), "90706")) {
			return mapping.findForward("powererror");
		}
		String baseid = request.getParameter("baseid");// 基本信息表id
		SparepartBaseInfoBean baseBean = bo.findSparepartInfoById(baseid);
		List options = bo.findPositionByBaseId(baseid);
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List saveOptions = bo.findPositionForW();
		String position = "";
		if (options != null && options.size() > 0) {
			BasicDynaBean positions = (BasicDynaBean) options.get(0);
			position = (String) positions.get("init_storage");
		}
		List initSerialNumbers = bo.getSerialNmuByPositon(position, baseid);

		request.setAttribute("SparepartBaseInfoBean", baseBean);
		request.setAttribute("initSerialNumbers", initSerialNumbers);
		request.setAttribute("user_name", user.getUserName());
		request.setAttribute("dept_name", user.getDeptName());
		request.setAttribute("sparePartoptions", options);
		request.setAttribute("saveOptions", saveOptions);
		request.setAttribute("baseid", baseid);
		return mapping.findForward("takeOutFromStorageForm");
	}

	/**
	 * 根据保存位置获取备件序列号
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getSerialNmuByPositon(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/json; charset=GBK");
		String position = request.getParameter("initStorage");
		String state = request.getParameter("state");
		if (position == null || position.trim().length() == 0) {
			position = request.getParameter("storagePosition");
		}
		if (position == null || position.trim().length() == 0) {
			position = request.getParameter("takenOutStorage");
		}
		String baseid = request.getParameter("baseid");
		String applyFId = request.getParameter("applyFId");
		List nameList = bo.getSerialNumByPositonByApply(position, baseid, state,applyFId);
		JSONArray ja = JSONArray.fromObject(nameList);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(ja.toString());
			out.flush();
			logger.info(ja.toString() + "名称");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return null;
	}

	/**
	 * 显示处于移动备用状态的备件列表，方便代维领用备件
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listSparepartStorageForDraw(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List storageList = bo.listSparepartStorageForDisplay();
		request.getSession().setAttribute("STORAGE_LIST", storageList);
		request.getSession().setAttribute("EXPORT_STORAGE_LIST", storageList);
		super.setPageReset(request);
		return mapping.findForward("listSparepartStorageForDraw");
	}

	/**
	 * 备件领用导出excel
	 * 
	 */
	public ActionForward exportStorageListForRe(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = (List) request.getSession().getAttribute(
		"EXPORT_STORAGE_LIST");
		bo.exportStorageListForRe(list, response);
		return null;
	}

	/**
	 * 备件领用
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward takeOutFromStorage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!CheckPower.checkPower(request.getSession(), "90706")) {
			return mapping.findForward("powererror");
		}
		String[] seriNums = request.getParameterValues("serialId");
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String deptid = user.getDeptID();
		String userid = user.getUserID();
		SparepartStorageBean bean = (SparepartStorageBean) form;
		bean.setTakenOutStorage(bean.getInitStorage());
		bean.setDepartId(deptid);
		bean.setDrawPerson(userid);
		boolean flag = bo.takeOut(bean, seriNums);
		if (flag) {
			return super.forwardInfoPage(mapping, request, "9070102ok");
		} else {
			Map param = new HashMap();
			param.put("serialId", seriNums);
			return super.forwardErrorPageWithParam(mapping, request,
					"9070102err", param);
		}
	}

	/**
	 * 备件的归还表单
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward giveBackToStorageForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!CheckPower.checkPower(request.getSession(), "90709")) {
			return mapping.findForward("powererror");
		}
		UserInfo user = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
		String id = request.getParameter("base_id");
		SparepartBaseInfoBean baseinfo = bo.findSparepartInfoById(id);

		// 移动位置
		List options = bo.findPosition();
		List optionsW = bo.findPositionForWReturnSpare(id);
		String position = "";
		if (optionsW != null && optionsW.size() > 0) {
			BasicDynaBean positions = (BasicDynaBean) optionsW.get(0);
			position = (String) positions.get("storage_position");
		}
		//List initSerialNumbers = bo.getSerialNmuByPositon(position, id);
		List initSerialNumbers = bo.getRunSerialNmuByPositon(position,id,SparepartConstant.CONTRACTOR_WAIT_USE);
		request.setAttribute("initSerialNumbers", initSerialNumbers);
		request.setAttribute("returnOptions", options);
		request.setAttribute("baseid", id);
		request.setAttribute("optionsW", optionsW);
		request.setAttribute("SparepartBaseInfoBean", baseinfo);
		request.setAttribute("user_name", user.getUserName());
		request.setAttribute("user_id", user.getUserID());
		request.setAttribute("dept_id", user.getDeptID());
		request.setAttribute("dept_name", user.getDeptName());
		return mapping.findForward("giveBackToStorageForm");
	}

	/**
	 * 备件归还
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward giveBackToStorage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!CheckPower.checkPower(request.getSession(), "90709")) {
			return mapping.findForward("powererror");
		}

		String[] serialnums = request.getParameterValues("serialId");
		SparepartStorageBean bean = (SparepartStorageBean) form;
		String person = bean.getStoragePerson();
		String position = bean.getInitStorage();
		boolean result = bo.updateSpareState(serialnums, person, position);
		if (result) {
			return super.forwardInfoPage(mapping, request, "9070103ok");
		} else {
			Map param = new HashMap();
			param.put("serialId", serialnums);
			return super.forwardErrorPageWithParam(mapping, request,
					"9070103err", param);
		}
	}

	/**
	 * 根据基本表去库存表查询
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward listSparepartStorageForOneByCon(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!CheckPower.checkPower(request.getSession(), "90703")) {
			return mapping.findForward("powererror");
		}
		String sparePartId = super.parseParameter(request
				.getParameter("spare_part_id"), "storage.spare_part_id", "'",
		"'");
		String condition = "";	
		String queryserialNumber = (String)request.getSession().getAttribute("queryserialNumber");

		if(queryserialNumber!=null && queryserialNumber.trim().length()>0){
			condition += " and storage.serial_number like'%" + queryserialNumber + "%'";
		}
		String querypositionIds = (String) request.getSession().getAttribute("querypositionIds");
		if(querypositionIds!=null && querypositionIds.trim().length()>0){
			String positionId = bo.stringToString(querypositionIds);
			condition += " and storage.storage_position in(" + positionId + ")";
		}
		if ("3".equals(request.getParameter("flag"))) {
			condition += (" and storage.spare_part_id=" + sparePartId);
			request.getSession().setAttribute("sqlCondition", condition);
		} else {
			condition = (String) request.getSession().getAttribute(
			"sqlCondition");
			if (condition == null || condition.equals("")) {
				condition = "";
			}
		}
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String regionId = user.getRegionid();
		String departId = user.getDeptID();
		String userType = user.getType();
		if (UserInfo.PROVINCE_MUSER_TYPE.equals(userType)) {

		}
		if (UserInfo.CITY_MUSER_TYPE.equals(userType)) {
			condition += (" and (storage.depart_id='" + departId + "' or exists(");
			condition += ("   select contractorid from contractorinfo ");
			condition += ("    where regionid='" + regionId + "' and contractorid=storage.depart_id");
			condition += (" )");
			condition += (")");
		}
		if (UserInfo.PROVINCE_CUSER_TYPE.equals(userType)) {

		}
		if (UserInfo.CITY_CUSER_TYPE.equals(userType)) {
			condition += (" and (storage.depart_id='" + departId + "' or exists(");
			condition += ("   select deptid from deptinfo ");
			condition += ("   where regionid='" + regionId + "' and deptid=storage.depart_id");
			condition += ("))");
		}
		String state = request.getParameter("state");
		List list = bo.listForCon(condition, state);
		request.getSession().setAttribute("STORAGE_LIST", list);
		request.getSession().setAttribute("EXPORT_STORAGE_LIST", list);
		return mapping.findForward("listSparepartStorageForOne");
	}

	/**
	 * 报废状态
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listSparepartStorageForOneByScrap(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!CheckPower.checkPower(request.getSession(), "90703")) {
			return mapping.findForward("powererror");
		}
		String sparePartId = super.parseParameter(request
				.getParameter("spare_part_id"), "storage.spare_part_id", "'",
		"'");
		String condition = "";
		String queryserialNumber = (String)request.getSession().getAttribute("queryserialNumber");

		if(queryserialNumber!=null && queryserialNumber.trim().length()>0){
			condition += " and storage.serial_number like'%" + queryserialNumber + "%'";
		}
		String querypositionIds = (String) request.getSession().getAttribute("querypositionIds");
		if(querypositionIds!=null && querypositionIds.trim().length()>0){
			String positionId = bo.stringToString(querypositionIds);
			condition += " and storage.storage_position in(" + positionId + ")";
		}
		if ("3".equals(request.getParameter("flag"))) {
			condition += (" and storage.spare_part_id=" + sparePartId);
			request.getSession().setAttribute("sqlCondition", condition);
		} else {
			condition = (String) request.getSession().getAttribute(
			"sqlCondition");
			if (condition == null || condition.equals("")) {
				condition = "";
			}
		}
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String regionId = user.getRegionid();
		String departId = user.getDeptID();
		String userType = user.getType();
		if (UserInfo.PROVINCE_MUSER_TYPE.equals(userType)) {

		}
		if (UserInfo.CITY_MUSER_TYPE.equals(userType)) {
			condition += (" and (storage.depart_id='" + departId + "' or exists(");
			condition += ("   select contractorid from contractorinfo ");
			condition += ("    where regionid='" + regionId + "' and contractorid=storage.depart_id");
			condition += (" )");
			condition += (")");
		}
		if (UserInfo.PROVINCE_CUSER_TYPE.equals(userType)) {

		}
		if (UserInfo.CITY_CUSER_TYPE.equals(userType)) {
			condition += (" and (storage.depart_id='" + departId + "' or exists(");
			condition += ("   select deptid from deptinfo ");
			condition += ("   where regionid='" + regionId + "' and deptid=storage.depart_id");
			condition += ("))");
		}
		String state = request.getParameter("state");
		List list = bo.listForScrap1(condition, state);
		request.getSession().setAttribute("STORAGE_LIST", list);
		request.getSession().setAttribute("EXPORT_STORAGE_LIST", list);
		return mapping.findForward("listSparepartStorageForOne");
	}

	/**
	 * 根据基本表去库存表查询
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward listSparepartStorageForOneByRun(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!CheckPower.checkPower(request.getSession(), "90703")) {
			return mapping.findForward("powererror");
		}
		String sparePartId = super.parseParameter(request
				.getParameter("spare_part_id"), "storage.spare_part_id", "'",
		"'");
		String condition = "";	
		String queryserialNumber = (String)request.getSession().getAttribute("queryserialNumber");

		if(queryserialNumber!=null && queryserialNumber.trim().length()>0){
			condition += " and storage.serial_number like'%" + queryserialNumber + "%'";
		}
		String querypositionIds = (String) request.getSession().getAttribute("querypositionIds");
		if(querypositionIds!=null && querypositionIds.trim().length()>0){
			String positionId = bo.stringToString(querypositionIds);
			condition += " and storage.storage_position in(" + positionId + ")";
		}
		if ("3".equals(request.getParameter("flag"))) {
			condition += (" and storage.spare_part_id=" + sparePartId);
			request.getSession().setAttribute("sqlCondition", condition);
		} else {
			condition = (String) request.getSession().getAttribute(
			"sqlCondition");
			if (condition == null || condition.equals("")) {
				condition = "";
			}
		}
		UserInfo user = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
		String regionId = user.getRegionid();
		String departId = user.getDeptID();
		String userType = user.getType();		
		if (UserInfo.CITY_MUSER_TYPE.equals(userType)) {
			condition += (" and (storage.depart_id='" + departId + "' or exists(");
			condition += ("   select contractorid from contractorinfo ");
			condition += ("    where regionid='" + regionId + "' and contractorid=storage.depart_id");
			condition += (" )");
			condition += (")");
		}

		if (UserInfo.CITY_CUSER_TYPE.equals(userType)) {
			condition += (" and (storage.depart_id='" + departId + "' or exists(");
			condition += ("   select deptid from deptinfo ");
			condition += ("   where regionid='" + regionId + "' and deptid=storage.depart_id");
			condition += ("))");
		}

		String state = request.getParameter("state");
		List list = new ArrayList();
		/*if (state.equals(SparepartConstant.IS_DISCARDED)) {
			list = bo.listForScrap(condition, state);
		} else {
			list = bo.listForRun(condition, state);
		}*/
		list = bo.listForRun(condition, state);
		request.getSession().setAttribute("STORAGE_LIST", list);
		request.getSession().setAttribute("EXPORT_STORAGE_LIST", list);
		request.getSession().setAttribute("bl", "false");
		return mapping.findForward("listSparepartStorageForOne");
	}

	/**
	 * 根据基本表去库存表查询
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward listSparepartStorageForOneByRepair(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!CheckPower.checkPower(request.getSession(), "90703")) {
			return mapping.findForward("powererror");
		}
		String sparePartId = super.parseParameter(request
				.getParameter("spare_part_id"), "storage.spare_part_id", "'",
		"'");
		String condition = "";
		if ("3".equals(request.getParameter("flag"))) {
			condition += (" and storage.spare_part_id=" + sparePartId);
			request.getSession().setAttribute("sqlCondition", condition);
		} else {
			condition = (String) request.getSession().getAttribute(
			"sqlCondition");
			if (condition == null || condition.equals("")) {
				condition = "";
			}
		}
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String regionId = user.getRegionid();
		String departId = user.getDeptID();
		String userType = user.getType();

		if (UserInfo.CITY_MUSER_TYPE.equals(userType)) {
			condition += (" and (storage.depart_id='" + departId + "' or exists(");
			condition += ("   select contractorid from contractorinfo ");
			condition += ("    where regionid='" + regionId + "' and contractorid=storage.depart_id");
			condition += (" )");
			condition += (")");
		}

		if (UserInfo.CITY_CUSER_TYPE.equals(userType)) {
			condition += (" and (storage.depart_id='" + departId + "' or exists(");
			condition += ("   select deptid from deptinfo ");
			condition += ("   where regionid='" + regionId + "' and deptid=storage.depart_id");
			condition += ("))");
		}
		List list = bo.listForRepair(condition);
		request.getSession().setAttribute("STORAGE_LIST", list);
		request.getSession().setAttribute("EXPORT_STORAGE_LIST", list);
		return mapping.findForward("listSparepartStorageForOne");
	}

	/**
	 * 显示备件重新入库的查询界面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showRestoredQuery(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SeparepartBaseInfoService bo = new SeparepartBaseInfoService();
		List facList = bo.getAllFactory();
		request.setAttribute("facList", facList);
		return mapping.findForward("showAgainRestoredQueryPage");
	}

	/**
	 * 重新入库的查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doQueryForAgainRestored(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!CheckPower.checkPower(request.getSession(), "90703")) {
			return mapping.findForward("powererror");
		}
		String sparePartName = super.parseParameter(request
				.getParameter("sparePartName"), "part.spare_part_name", "'",
		"'");
		String sparePartModel = super.parseParameter(request
				.getParameter("sparePartModel"), "part.spare_part_model", "'",
		"'");
		String sparePartId = super.parseParameter(request
				.getParameter("spare_part_id"), "storage.spare_part_id", "'",
		"'");

		String sparePartState = super.parseParameter(request
				.getParameter("spare_part_state"), "storage.spare_part_state",
				"'", "'");
		String serialNumber = super.parseParameter(request.getParameter(
		"serial_number").trim(), "storage.serial_number", "'%", "%'");
		String storagePosition = super.parseParameter(request
				.getParameter("init_storage"), "storage.init_storage", "'%",
		"%'");
		String productFactory = super.parseParameter(request
				.getParameter("productFactory"), "part.product_factory", "'%",
		"%'");
		String condition = "";
		if ("1".equals(request.getParameter("reset_query"))) {
			condition += (" and storage.spare_part_id=" + sparePartId);
			condition += (" and part.spare_part_name=" + sparePartName);
			condition += (" and part.spare_part_model=" + sparePartModel);
			condition += (" and storage.spare_part_state=" + sparePartState);
			condition += (" and storage.serial_number like " + serialNumber);
			condition += (" and storage.init_storage like " + storagePosition);
			condition += (" and part.product_factory like " + productFactory);
			request.getSession().setAttribute("QUERY_CONDITION", condition);
		} else {
			condition = (String) request.getSession().getAttribute(
			"QUERY_CONDITION");
			if (condition == null || condition.equals("")) {
				condition = "";
			}
		}
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String regionId = user.getRegionid();
		String departId = user.getDeptID();
		String userType = user.getType();

		if (UserInfo.CITY_MUSER_TYPE.equals(userType)) {
			condition += (" and (storage.depart_id='" + departId + "' or exists(");
			condition += ("   select contractorid from contractorinfo ");
			condition += ("    where regionid='" + regionId + "' and contractorid=storage.depart_id");
			condition += (" )");
			condition += (")");
		}

		if (UserInfo.CITY_CUSER_TYPE.equals(userType)) {
			condition += (" and (storage.depart_id='" + departId + "' or exists(");
			condition += ("   select deptid from deptinfo ");
			condition += ("   where regionid='" + regionId + "' and deptid=storage.depart_id");
			condition += ("))");
		}
		List list = bo.doQueryForRestored(condition);
		request.getSession().setAttribute("APPLY_LIST", list);
		request.getSession().setAttribute("EXPORT_APPLY_LIST", list);
		return mapping.findForward("againRestoredList");// doQueryForRestored
	}

	/**
	 * 转入到备件重新入库页面
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward againToStorageForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!CheckPower.checkPower(request.getSession(), "90701")) {
			return mapping.findForward("powererror");
		}

		String storageId = request.getParameter("stoid");
		System.out.println("storageId=========:" + storageId);
		SparepartStorage storage = bo.loadStorage(storageId);
		SparepartStorageBean bean = new SparepartStorageBean();
		bean.setStoragePosition(storage.getStoragePosition());
		String sparepartid = storage.getSparePartId();
		SparepartBaseInfoBean base = bo.getSparePart(sparepartid);
		String positon = bo.getPositionNameById(storage.getStoragePosition());
		request.setAttribute("SparepartBaseInfoBean", base);
		request.setAttribute("storage", storage);
		request.setAttribute("positon", positon);
		request.setAttribute("SparepartStorageBean", bean);
		List savePosition = bo.findPosition();
		request.setAttribute("savePosition", savePosition);
		UserInfo user = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
		request.setAttribute("user_name", user.getUserName());
		request.setAttribute("user_id", user.getUserID());
		request.setAttribute("user_dept_id", user.getDeptID());
		request.setAttribute("user_dept_name", user.getDeptName());	
		String act = request.getParameter("act");
		if (act != null && act.equals("view")) {
			return mapping.findForward("storageRepairInfo");
		}
		return mapping.findForward("againToStorageForm");
	}

	/**
	 * 备件的入库
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward ageinToStorage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		if (!CheckPower.checkPower(request.getSession(), "90701")) {
			return mapping.findForward("powererror");
		}
		String tid = request.getParameter("tid");
		SparepartStorageBean bean = (SparepartStorageBean) form;
		bean.setInitStorage(bean.getStoragePosition());		
		boolean flag = false;
		flag = bo.updateSaved(bean);
		if (flag) {
			return super.forwardInfoPage(mapping, request, "9070101againok");
		} else {
			return super.forwardErrorPage(mapping, request, "9070101againerr");
		}
	}

	/**
	 * 导出查询到的备件库存列表
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward exportAgainStorageList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = (List) request.getSession().getAttribute("EXPORT_APPLY_LIST");
		bo.exportAgainStorage(list, response);
		return null;
	}

	private boolean saveFile(SparepartStorageBean bean, String path) {
		FormFile file = bean.getFile();
		if (file == null) {
			return false;
		}
		File temfile = new File(path + "\\spare_temp.xls");
		if (temfile.exists()) {
			temfile.delete();
		}
		try {
			InputStream streamIn = file.getInputStream();
			OutputStream streamOut = new FileOutputStream(path + "\\spare_temp.xls");
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = streamIn.read(buffer, 0, 8192)) != -1) {
				streamOut.write(buffer, 0, bytesRead);
			}
			streamOut.close();
			streamIn.close();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	/**
	 * 报废备件
	 */
	public ActionForward scrappedStorage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String storageId = request.getParameter("stoid");
		boolean result = bo.scrappedStorage(storageId);
		if (result) {
			return super.forwardInfoPage(mapping, request, "909scrappedSucc");
		} else {
			return super.forwardErrorPage(mapping, request, "909scrappedFail");
		}
	}
}
