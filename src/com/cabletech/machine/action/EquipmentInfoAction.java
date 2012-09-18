package com.cabletech.machine.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.config.MsgInfo;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.linecut.services.LineCutReService;
import com.cabletech.machine.beans.EquipmentInfoBean;
import com.cabletech.machine.services.EquipmentInfoBO;
import com.cabletech.watchinfo.templates.WatchDetailTemplate;

/**
 * �����豸��ACTION
 * 
 * @author haozi
 * 
 */
public class EquipmentInfoAction extends BaseDispatchAction {
	private EquipmentInfoBO bo = new EquipmentInfoBO();

	/**
	 * ��ʾ���ӻ����豸��ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showAddEqu(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LineCutReService service = new LineCutReService();
		List conDeptList = service.getAllCon();
		request.setAttribute("conDeptList", conDeptList);
		return mapping.findForward("showAddEqu");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showQueryEqu(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		EquipmentInfoBean bean = (EquipmentInfoBean) form;
		String equipmentName = bean.getEquipmentName();
		String contractorId = bean.getContractorId();
		String layer = bean.getLayer();

		String sql = null;

		String cacheSql = (String) request.getSession().getAttribute("eq_querysql");
		if (cacheSql == null || "".equals(cacheSql)) {
			sql = "select t.eid,t.equipment_name,con.contractorname,layer from equipment_info t, contractorinfo con where con.contractorid = t.contractor_id";

			String condition = "";
			if (!"0".equals(contractorId)) {
				condition = condition + " and t.contractor_id='" + contractorId
						+ "'";
			}

			if (!"0".equals(layer) && !"".equals(layer)) {
				condition = condition + " and t.layer='" + layer + "'";
			}

			if (equipmentName != null && !"".equals(equipmentName)) {
				condition = condition + " and t.equipment_name like '%"
						+ equipmentName + "%'";
			}

			sql = sql + condition;
		}else{
			sql = cacheSql;
		}

		List equipmentList = bo.queryEquipment(sql);
		request.getSession().setAttribute("eq_querysql", sql);
		request.getSession().setAttribute("equipmentList", equipmentList);
		return mapping.findForward("showQueryEqu");
	}

	/**
	 * ɾ������ʾ��Ҫǰ�ò�ѯ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showQueryEquOfDel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String sql = (String) request.getSession().getAttribute("eq_querysql");
		List equipmentList = bo.queryEquipment(sql);
		request.getSession().setAttribute("eq_querysql", sql);
		request.getSession().setAttribute("equipmentList", equipmentList);
		return mapping.findForward("showQueryEqu");
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showQueryForEqu(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LineCutReService service = new LineCutReService();
		List conDeptList = service.getAllCon();
		request.setAttribute("conDeptList", conDeptList);
		return mapping.findForward("showQueryForEqu");
	}

	/**
	 * �����豸��Ϣ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showUpdateForEqu(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String eid = request.getParameter("eid");
		EquipmentInfoBean bean = (EquipmentInfoBean)form;
		
		EquipmentInfoBean eqbean = bo.getEquipmentById(eid);
		
		bean.setEid(eqbean.getEid());
		bean.setContractorId(eqbean.getContractorId());
		bean.setEquipmentName(eqbean.getEquipmentName());
		bean.setLayer(eqbean.getLayer());
		
		LineCutReService service = new LineCutReService();
		List conDeptList = service.getAllCon();
		request.setAttribute("conDeptList", conDeptList);
		return mapping.findForward("showOneToEquipment");
	}
	
	/***
	 * ɾ��Ѳ���豸
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward deleteForEqu(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String eid = request.getParameter("eid");
		if(bo.delEquipment(eid))
		{
			return super.forwardInfoPage(mapping, request, "160105ok");
		}else{
			return super.forwardErrorPage(mapping, request, "160105error");
		}
	}
	
	/**
	 * ����Ѳ���豸
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return-2 ��ʾ�豸�Ѵ��ڣ�-1��ʾ��ѯʱ���ֵײ��쳣��1��ʾ���³ɹ�
	 */
	public ActionForward updateForEqu(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		EquipmentInfoBean bean = (EquipmentInfoBean)form;
			int flag = bo.updateEquipment(bean);
			if(flag == 1){
				return super.forwardInfoPage(mapping, request, "160104ok");
			}else if(flag == -2){
				return super.forwardErrorPage(mapping, request, "160104false");
			}else{
				return super.forwardErrorPage(mapping, request, "160104error");
			}
	}
	/**
	 * ����ģ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward downTemplet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		try {
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String("�����豸ģ��.xls".getBytes(), "iso-8859-1"));
			OutputStream out = response.getOutputStream();

			WatchDetailTemplate template = new WatchDetailTemplate(servlet
					.getServletContext().getRealPath("/upload")
					+ "/equipment.xls");
			template.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ���ӻ����豸
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addEquipment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		boolean bl = false;
		MsgInfo msg = new MsgInfo();
		EquipmentInfoBean bean = (EquipmentInfoBean) form;
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		if (userinfo.getDeptype().equals("2")) { // ����Ǵ�ά��˾�ǲ�������ӱ�������
			return forwardErrorPage(mapping, request, "partstockerror");
		}
		String filename = bean.getFile().getFileName();
		if (filename.equals("") || filename == null) {
			return forwardErrorPage(mapping, request, "fileerror");
		}
		String path = servlet.getServletContext().getRealPath("/upload");
		List list = this.getUpInfo(bean, path, request);
		
//		OracleIDImpl ora = new OracleIDImpl();
//		String[] eid = ora.getSeqs("equipment_info", 20, list.size());
//		
//		for(int i = 0; i < list.size(); i++) {
//			bl = bo.addEquipment((EquipmentInfoBean)list.get(i), eid[i]);
//		}
		
		bl = bo.addEquipment(list);
		if (bl) {
			msg.setInfo("���ӻ����豸�ɹ�");
			msg.setLink("/WebApp/EquipmentInfoAction.do?method=showAddEqu");
			request.setAttribute("MESSAGEINFO", msg);
			return mapping.findForward("sucessMsg");
		} else {
			msg.setInfo("���ӻ����豸ʧ��");
			msg.setLink("/WebApp/EquipmentInfoAction.do?method=showAddEqu");
			request.setAttribute("MESSAGEINFO", msg);
			return mapping.findForward("sucessMsg");
		}
	}


	/**
	 * ��ȡ�ϴ���excel�����ݣ���װ��bean
	 * 
	 * @param bean
	 * @param path
	 * @param request
	 * @return
	 */
	private List getUpInfo(EquipmentInfoBean bean, String path,
			HttpServletRequest request) {
		List list = new ArrayList();
		if (!this.saveFile(bean, path)) {
			return null;
		}
		EquipmentInfoBean equipmentInfoBean = null;
		File file = new File(path + "\\equipment.xls");
		FileInputStream in = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		try {
			in = new FileInputStream(file);
			HSSFWorkbook workbook = new HSSFWorkbook(in);
			HSSFSheet sheet = workbook.getSheetAt(0);
			int lastRoNum = sheet.getLastRowNum();
			for (int rowIndex = 2; rowIndex <= lastRoNum; rowIndex++) {
				row = sheet.getRow(rowIndex);
				cell = row.getCell((short) 0);
				if (cell != null) {
					equipmentInfoBean = new EquipmentInfoBean();
					equipmentInfoBean.setEquipmentName(cell
							.getStringCellValue());
					equipmentInfoBean.setContractorId(bean.getContractorId());
					equipmentInfoBean.setLayer(bean.getLayer());
				}
				list.add(equipmentInfoBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * �����ϴ��ļ�
	 * 
	 * @param bean
	 * @param path
	 * @return
	 */
	private boolean saveFile(EquipmentInfoBean bean, String path) {
		FormFile file = bean.getFile();
		if (file == null) {
			return false;
		}
		File temfile = new File(path + "\\equipment.xls");
		if (temfile.exists()) {
			temfile.delete();
		}
		try {
			InputStream streamIn = file.getInputStream();
			OutputStream streamOut = new FileOutputStream(path
					+ "\\equipment.xls");
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
}
