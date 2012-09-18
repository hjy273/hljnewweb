package com.cabletech.sparepartmanage.services;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.config.ReportConfig;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.generatorID.GeneratorID;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.sparepartmanage.beans.SparepartBaseInfoBean;
import com.cabletech.sparepartmanage.beans.SparepartStorageBean;
import com.cabletech.sparepartmanage.dao.SeparepartBaseInfoDAO;
import com.cabletech.sparepartmanage.dao.SparepartStorageDAOImpl;
import com.cabletech.sparepartmanage.domainobjects.SparepartConstant;
import com.cabletech.sparepartmanage.domainobjects.SparepartStorage;
import com.cabletech.sparepartmanage.template.StorageTemplateInfo;

/**
 * SparepartStorageBO 备件库存的业务操作：备件入库、领用、使用、送修、报废、归还和重新入库以及备件库存的查询
 */
public class SparepartStorageBO extends BaseBisinessObject {
	private static Logger logger = Logger.getLogger(SeparepartBaseInfoDAO.class.getName());
	private SparepartStorageDAOImpl dao = new SparepartStorageDAOImpl();

	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	private GeneratorID generatorID = new OracleIDImpl();

	public void exportExcel(String urlPath, List list, HttpServletResponse response, OutputStream out) throws Exception {
		StorageTemplateInfo template = new StorageTemplateInfo(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.doExportExcel(list, excelstyle);
		template.write(out);
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

	/**
	 * 备件入库
	 * 
	 * @param storageBean
	 *            SparepartStorageBean
	 * @return boolean
	 * @throws Exception
	 */
	public boolean save(SparepartStorageBean storageBean) throws Exception {
		boolean flag = false;
		SparepartStorage storage = new SparepartStorage();
		BeanUtil.objectCopy(storageBean, storage);
		storage.setTid(generatorID.getSeq("spare_part_storage", 20));
		storage.setStorageDate(new Date());
		storage.setSparePartState(SparepartConstant.MOBILE_WAIT_USE);
		dao.insertSparepartStorage(storage);

		SeparepartBaseInfoService sbo = new SeparepartBaseInfoService();
		sbo.modState(storage.getSparePartId(), SparepartConstant.NOT_ALLOW_DELETE);

		flag = true;
		return flag;
	}

	/**
	 * 备件保存位置(移动)
	 */

	public List findPosition() {
		return dao.findPosition();
	}

	/**
	 * 备件保存位置（代维）
	 * @return
	 */
	public List findPositionForW() {
		return dao.findPositionForW();
	}

	/**
	 * 代维归还备件选择位置
	 * @return
	 */
	public List findPositionForWReturnSpare(String id) {
		return dao.findPositionForWReturnSpare(id);
	}

	/**
	 * 代维归还备件，更新备件状态为01，归还位置，操作人。
	 */
	public boolean updateSpareState(String[] serialnums, String person, String position) {
		return dao.updateSpareState(serialnums, person, position);
	}

	/**
	 * 根据条件查询备件库存列表
	 * 
	 * @param condition
	 *            String
	 * @return List
	 * @throws Exception
	 */
	public List listQuery(String condition) {
		List list = new ArrayList();
		String sql = "select * from (select tb.spare_part_id,"
				+ "max(case tb.spare_part_state when '01' then tb.num else 0 end) mobilenum,"
				+ "max(case tb.spare_part_state when '02' then tb.num else 0 end) connum,"
				+ "max(case tb.spare_part_state when '03' then tb.num else 0 end) runningnum,"
				+ "max(case tb.spare_part_state when '04' then tb.num else 0 end) repairnum,"
				+ "max(case tb.spare_part_state when '05' then tb.num else 0 end) scrapnum,"
				+ "max(case tb.spare_part_state when '06' then tb.num else 0 end) applyed,"
				+ "max(case tb.spare_part_state when '07' then tb.num else 0 end) changed,"
				+ "sum(tb.num) totalnum , tb.spare_part_id name from "
				+ "( select tab.spare_part_id, tab.spare_part_state ,sum(tab.storage_number) as num from "
				+ "( select part.tid ,storage.spare_part_id,storage.storage_number,storage.spare_part_state"
				+ " from spare_part_storage storage, spare_part_baseinfo part "
				+ "where storage.spare_part_id=part.tid ";
		sql += condition;
		sql += ") tab  group by tab.spare_part_id ,tab.spare_part_state order by tab.spare_part_id"
				+ ")  tb group by tb.spare_part_id order by tb.spare_part_id) a,spare_part_baseinfo b where a.spare_part_id=b.tid ";
		QueryUtil qu = null;
		try {
			qu = new QueryUtil();
			System.out.println("SparepartStorageBO->listQuery:" + sql);
			list = qu.queryBeans(sql);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return list;
	}

	/**
	 *  显示处于移动备用状态的备件列表，方便代维领用备件,库存数为0不显示
	 * @return
	 * @throws Exception
	 */
	public List listSparepartStorageForDisplay() throws Exception {
		List list = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from spare_part_baseinfo s right join ");
		sql.append("(select spare_part_id, count(*) storage_number from spare_part_storage  ");
		sql.append("where storage_number>0 and spare_part_state='01' group by spare_part_id) m ");
		sql.append("on s.tid = m.spare_part_id ");
		QueryUtil util = new QueryUtil();
		System.out.println("listSparepartStorageForDraw:" + sql.toString());
		list = util.queryBeans(sql.toString());
		return list;
	}

	/**
	 * 根据查询到的备件库存列表进行导出动作
	 * 
	 * @param list
	 *            List
	 * @param response
	 *            HttpServletResponse
	 * @throws IOException
	 */
	public void exportStorage(List list, HttpServletResponse response) {
		try {
			initResponse(response, "备件库存列表信息.xls");
			OutputStream out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath("report.sparepartstorageinfo");
			StorageTemplateInfo template = new StorageTemplateInfo(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.doExport(list, excelstyle);
			template.write(out);
		} catch (Exception e) {
			e.getStackTrace();
		}

	}

	public void exportAgainStorage(List list, HttpServletResponse response) {
		try {
			initResponse(response, "备件重新入库列表信息.xls");
			OutputStream out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath("report.sparepareagainstorage");
			StorageTemplateInfo template = new StorageTemplateInfo(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.doAgainStorageExport(list, excelstyle);
			template.write(out);
		} catch (Exception e) {
			e.getStackTrace();
		}

	}

	/**
	 * 备件领用一览表导出Excel
	 * @param list
	 * @param response
	 * @throws IOException
	 */

	public void exportStorageListForRe(List list, HttpServletResponse response) {
		try {
			initResponse(response, "备件领用信息列表.xls");
			OutputStream out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath("report.sparepartstorage");
			this.exportExcel(urlPath, list, response, out);
		} catch (Exception e) {
			e.getStackTrace();
		}

	}

	/**
	 * 备件领用一览表导出Excel
	 * @param list
	 * @param response
	 * @throws IOException
	 */

	public void exportStorageListForApply(List list, HttpServletResponse response) {
		try {
			initResponse(response, "备件申请列表.xls");
			OutputStream out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath("report.sparepartstorageapply");
			this.exportExcel(urlPath, list, response, out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 备件入库
	 * 
	 * @param storageBean
	 *            SparepartStorageBean
	 * @return boolean
	 * @throws Exception
	 */
	public boolean batchInsertSparepartStorage(List list) throws Exception {
		return dao.batchInsertSparepartStorage(list);
	}

	/**
	 * 判断备件序列号是否存在
	 */
	public String getSerialNumber(String id) {
		return dao.getSerialNumber(id);
	}

	/**
	 * 根据条件查询备件库存列表
	 * 
	 * @param condition
	 *            String
	 * @return List
	 * @throws Exception
	 */
	public List listForMobile(String condition) throws Exception {
		List list = new ArrayList();
		String sql = "select storage.tid,storage.spare_part_id,storage.serial_number,storage.storage_position,position.name,";
		sql += "storage.taken_out_storage,storage.spare_part_state,storage.storage_number,";
		sql += "part.spare_part_name,part.spare_part_model,part.software_version,part.product_factory ";
		sql += " from spare_part_storage storage,spare_part_baseinfo part,spare_part_save_position position";
		sql += " where storage.spare_part_id=part.tid and storage.spare_part_state=01 and storage.storage_number>0 and position.id=storage.storage_position ";
		sql += condition;
		sql += " order by storage.serial_number";
		QueryUtil util = new QueryUtil();
		logger.info("SparepartStorageBO->list:" + sql);
		list = util.queryBeans(sql);
		return list;
	}

	/**
	 * 根据库存编号载入库存信息
	 * 
	 * @param storageId
	 *            String
	 * @return SparepartStorage
	 * @throws Exception
	 */
	public SparepartStorage loadStorage(String storageId) throws Exception {
		return dao.loadSparepartStorage(storageId);
	}

	public SparepartBaseInfoBean getSparePart(String id) throws Exception {
		return dao.getSparePart(id);
	}

	public List findSparepartsByBaseId(String id) throws Exception {

		return dao.findSparepartsByBaseId(id);
	}

	public List findPositionByBaseId(String id) {
		return dao.findPositionByBaseId(id);
	}

	public String getPositionNameById(String id) {
		return dao.getPositionNameById(id);
	}

	/**
	 * 根据保存位置name查询到id
	 * @param name
	 * @return
	 */
	public String getPositionIdsByName(String name) {
		return dao.getPositionIdsByName(name);
	}

	public SparepartBaseInfoBean findSparepartInfoById(String id) throws Exception {

		return dao.findSparepartInfoById(id);
	}

	/**
	 * 备件入库的删除
	 * 
	 * @param storageBean
	 *            SparepartStorageBean
	 * @return boolean
	 * @throws Exception
	 */
	public boolean deleteSaved(SparepartStorage storage, String sparepartId) throws Exception {
		boolean flag = false;
		//SparepartStorage storage = new SparepartStorage();
		//BeanUtil.objectCopy(storageBean, storage);
		dao.deleteSparepartStorage(storage);
		SeparepartBaseInfoService service = new SeparepartBaseInfoService();
		List storages = dao.getStorageBySparepartId(sparepartId);
		if (storages == null || storages.size() == 0) {
			service.modState(sparepartId, SparepartConstant.ALLOW_DELETE);
		}
		flag = true;
		return flag;
	}

	/**
	 * 备件入库的修改
	 * 
	 * @param storageBean
	 *            SparepartStorageBean
	 * @return boolean
	 * @throws Exception
	 */
	public boolean updateSaved(SparepartStorageBean storageBean) throws Exception {
		boolean flag = false;
		SparepartStorage storage = new SparepartStorage();
		BeanUtil.objectCopy(storageBean, storage);
		storage.setSparePartState(SparepartConstant.MOBILE_WAIT_USE);
		storage.setStorageDate(new Date());
		dao.updateSparepartStorage(storage);
		flag = true;
		return flag;
	}

	/**
	 * 显示代维领用过的备件列表，方便代维申请使用备件,运行状态的既不可在被申请
	 * @param deptid
	 * @return
	 * @throws Exception
	 */
	public List SparepartStorageForRe(String deptid) throws Exception {
		List list = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from spare_part_baseinfo s right join ");
		sql.append("(select spare_part_id,count(*) storage_number from spare_part_storage  ");
		sql.append("where storage_number>0 and spare_part_state='02' and depart_id='" + deptid);
		sql.append("' group by spare_part_id) m " + " on s.tid = m.spare_part_id ");
		QueryUtil util = new QueryUtil();
		list = util.queryBeans(sql.toString());
		System.out.println("listSparepartStorageForRE:" + sql.toString());
		return list;
	}

	public List getRunSerialNmuByPositon(String option, String baseid, String state) {
		return dao.getRunSerialNmuByPositon(option, baseid, state);
	}

	public List getSerialNumByPositonByApply(String option, String baseid, String state, String applyFId) {
		return dao.getSerialNumByPositonByApply(option, baseid, state, applyFId);
	}

	public List getSerialNmuByPositon(String position, String baseid) {
		return dao.getSerialNmuByPositon(position, baseid);
	}

	/**
	 * 备件领用,改变保存位置与
	 * 
	 * @param storageBean
	 *            SparepartStorageBean
	 * @param fromStorageId
	 *            String
	 * @return boolean
	 * @throws Exception
	 */
	public boolean takeOut(SparepartStorageBean storageBean, String[] seriNums) {
		boolean flag = false;
		StringBuffer sql = new StringBuffer();
		String serials = "";
		for (int i = 0; i < seriNums.length; i++) {
			if (i == seriNums.length - 1) {
				serials += "'" + seriNums[i] + "'";
			} else {
				serials += "'" + seriNums[i] + "',";
			}
		}
		sql.append("update spare_part_storage s  set s.storage_position='" + storageBean.getStoragePosition() + "' ");
		sql.append(",s.storage_remark='" + storageBean.getStorageRemark() + "',s.taken_out_storage='"
				+ storageBean.getTakenOutStorage() + "' ");
		sql.append(",s.depart_id='" + storageBean.getDepartId() + "', s.draw_person='" + storageBean.getDrawPerson()
				+ "'");
		sql.append(",s.spare_part_state='02' where s.serial_number in(" + serials + ")");
		logger.info("SparepartStorageBO->takeOut:" + sql.toString());
		UpdateUtil util;
		try {
			util = new UpdateUtil();
			util.executeUpdate(sql.toString());
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 根据条件查询备件库存列表
	 * 
	 * @param condition
	 *            String
	 * @return List
	 * @throws Exception
	 */
	public List list(String condition) throws Exception {
		List list = new ArrayList();
		String sql = "select storage.tid,storage.spare_part_id,storage.serial_number,storage.storage_position,";
		sql += "storage.taken_out_storage,storage.spare_part_state,storage.storage_number,";
		sql += "part.spare_part_name,part.spare_part_model,part.software_version,part.product_factory ";
		sql += " from spare_part_storage storage,spare_part_baseinfo part";
		sql += " where storage.spare_part_id=part.tid";
		sql += condition;
		sql += " order by storage.spare_part_id,storage.spare_part_state";
		QueryUtil util = new QueryUtil();
		list = util.queryBeans(sql);
		System.out.println("SparepartStorageBO->list:" + sql);
		return list;
	}

	/**
	 * 根据条件查询备件库存列表
	 * 
	 * @param condition
	 *            String
	 * @return List
	 * @throws Exception
	 */
	public List listForCon(String condition, String state) throws Exception {
		List list = new ArrayList();
		String sql = "select storage.tid,storage.spare_part_id,storage.serial_number,storage.storage_position,position.name,";
		sql += "storage.taken_out_storage,storage.spare_part_state,storage.storage_number,";
		sql += "part.spare_part_name,part.spare_part_model,part.software_version,part.product_factory ";
		sql += " from spare_part_storage storage,spare_part_baseinfo part,spare_part_save_position position";
		sql += " where storage.spare_part_id=part.tid and storage.spare_part_state='" + state
				+ "' and storage.storage_number>0 and position.id=storage.storage_position ";
		sql += condition;
		sql += " order by storage.serial_number";
		QueryUtil util = new QueryUtil();
		System.out.println("SparepartStorageBO->list:" + sql);
		list = util.queryBeans(sql);
		return list;
	}

	/**
	 * 根据条件查询备件库存列表
	 * 
	 * @param condition
	 *            String
	 * @return List
	 * @throws Exception
	 */
	public List listForRun(String condition, String state) throws Exception {
		List list = new ArrayList();
		String sql = "select storage.tid,storage.spare_part_id,storage.serial_number,storage.storage_position name,";
		sql += "storage.taken_out_storage,storage.spare_part_state,storage.storage_number,";
		sql += "part.spare_part_name,part.spare_part_model,part.software_version,part.product_factory ";
		sql += " from spare_part_storage storage,spare_part_baseinfo part";
		sql += " where storage.spare_part_id=part.tid and storage.spare_part_state='" + state
				+ "' and storage.storage_number>0 ";
		sql += condition;
		sql += " order by storage.serial_number";
		QueryUtil util = new QueryUtil();
		System.out.println("SparepartStorageBO->list:" + sql);
		list = util.queryBeans(sql);
		return list;
	}

	public List listForScrap1(String condition, String state) throws Exception {
		List list = new ArrayList();
		String sql = "select storage.tid,storage.spare_part_id,storage.serial_number,decode(storage.storage_position,'',null) as name ,";
		sql += "storage.taken_out_storage,storage.spare_part_state,storage.storage_number,";
		sql += "part.spare_part_name,part.spare_part_model,part.software_version,part.product_factory ";
		sql += " from spare_part_storage storage,spare_part_baseinfo part";
		sql += " where storage.spare_part_id=part.tid and storage.spare_part_state='" + state
				+ "' and storage.storage_number>0 ";
		sql += condition;
		sql += " order by storage.serial_number";
		QueryUtil util = new QueryUtil();
		System.out.println("SparepartStorageBO->list:" + sql);
		list = util.queryBeans(sql);
		return list;
	}

	/**
	 * 根据条件查询备件库存列表
	 * 
	 * @param condition
	 *            String
	 * @return List
	 * @throws Exception
	 */
	public List listForRepair(String condition) throws Exception {
		List list = new ArrayList();
		String sql = "select storage.tid,storage.spare_part_id,storage.serial_number,storage.storage_position,";
		sql += "storage.taken_out_storage,storage.spare_part_state,storage.storage_number,";
		sql += "part.spare_part_name,part.spare_part_model,part.software_version,part.product_factory ";
		sql += " from spare_part_storage storage,spare_part_baseinfo part";
		sql += " where storage.spare_part_id=part.tid and storage.spare_part_state='04' and storage.storage_number>0 ";
		sql += condition;
		sql += " order by storage.serial_number";
		QueryUtil util = new QueryUtil();
		list = util.queryBeans(sql);
		System.out.println("SparepartStorageBO->list:" + sql);
		return list;
	}

	public List doQueryForRestored(String condition) throws Exception {
		StringBuffer sb = new StringBuffer();
		List list = new ArrayList();
		sb.append("select part.product_factory,part.spare_part_name,part.spare_part_model,storage.init_storage,position.name, ");
		sb.append("storage.tid stoid,part.tid baseid");
		sb.append(" from spare_part_baseinfo part,spare_part_storage storage,spare_part_save_position position ");
		sb.append(" where position.id=storage.init_storage and ");
		sb.append(" storage.spare_part_id = part.tid and storage.spare_part_state='" + SparepartConstant.IS_MENDING
				+ "' ");
		sb.append(condition);
		QueryUtil util = new QueryUtil();
		System.out.println("SparepartStorageBO->list:" + sb.toString());
		list = util.queryBeans(sb.toString());
		return list;
	}

	public boolean scrappedStorage(String storageId) {
		return dao.scrappedStorage(storageId);
	}

	public String stringToString(String str) {
		String serials = "";
		if (str != null && str.trim().length() > 0) {
			String[] split = str.split(",");
			for (int i = 0; i < split.length; i++) {
				if (i == split.length - 1) {
					serials += "'" + split[i] + "'";
				} else {
					serials += "'" + split[i] + "',";
				}
			}
			return serials;
		} else {
			return null;
		}

	}

}
