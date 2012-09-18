package com.cabletech.linepatrol.material.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.material.dao.MaterialAddressDao;
import com.cabletech.linepatrol.material.dao.MaterialInfoDao;
import com.cabletech.linepatrol.material.dao.MaterialModelDao;
import com.cabletech.linepatrol.material.dao.MaterialTypeDao;
import com.cabletech.linepatrol.material.domain.LinePatrolManager;

@Service
@Transactional
public class MaterialImportBo extends EntityManager<LinePatrolManager, String> {
	@Resource(name = "materialAddressDao")
	private MaterialAddressDao addressDao;
	@Resource(name = "materialInfoDao")
	private MaterialInfoDao materialDao;
	@Resource(name = "materialModelDao")
	private MaterialModelDao modelDao;
	@Resource(name = "materialTypeDao")
	private MaterialTypeDao typeDao;

	public Map importMaterialApplyNumber(InputStream in) {
		HSSFWorkbook wb = new HSSFWorkbook();
		try {
			wb = new HSSFWorkbook(in);
		} catch (IOException e) {
			logger.error(e);
		}

		HSSFSheet sheet = wb.getSheetAt(0);

		List dataList = new ArrayList();
		Map oneDataMap = new HashMap();
		// 总体错误信息
		List<String> errorMsg = new ArrayList<String>();
		int lastRow = sheet.getPhysicalNumberOfRows();
		// lastRow不准，需要校验
		for (int i = 2; i < lastRow; i++) {
			// 有效标志
			boolean flag = true;
			// 行错误信息
			String rowErrorMsg = "";
			HSSFRow row = sheet.getRow(i);

			// 验证此行每一项均不为空
			for (int j = 0; j < 6; j++) {
				HSSFCell cell = row.getCell((short) j);
				if (cell == null) {
					flag = false;
					break;
				}
				String ss = null;
				ss = getCellStringValue(cell);
				if (ss == null || ss.equals("")) {
					flag = false;
					break;
				}
			}

			if (flag) {
				// 验证材料类型
				HSSFCell cell = row.getCell((short) 0);
				String type = cell.getRichStringCellValue().toString();
				if (StringUtils.isBlank(type)) {
					rowErrorMsg += "第" + (i + 1) + "的材料类型信息为空。<br>";
					errorMsg.add(rowErrorMsg);
					continue;
				}
				if (!isValidMaterailType(type)) {
					rowErrorMsg += "第" + (i + 1) + "的材料类型信息不存在。<br>";
					errorMsg.add(rowErrorMsg);
					continue;
				}

				// 验证材料规格
				cell = row.getCell((short) 1);
				String model = cell.getRichStringCellValue().toString();
				if (StringUtils.isBlank(model)) {
					rowErrorMsg += "第" + (i + 1) + "的材料规格信息为空。<br>";
					errorMsg.add(rowErrorMsg);
					continue;
				}
				if (!isValidMaterailModel(type, model)) {
					rowErrorMsg += "第" + (i + 1) + "的材料规格信息不存在。<br>";
					errorMsg.add(rowErrorMsg);
					continue;
				}

				// 验证材料名称
				cell = row.getCell((short) 2);
				String name = cell.getRichStringCellValue().toString();
				if (StringUtils.isBlank(name)) {
					rowErrorMsg += "第" + (i + 1) + "的材料名称信息为空。<br>";
					errorMsg.add(rowErrorMsg);
					continue;
				}
				if (!isValidMaterail(type, model, name)) {
					rowErrorMsg += "第" + (i + 1) + "的材料名称信息不存在。<br>";
					errorMsg.add(rowErrorMsg);
					continue;
				}

				// 验证存放地点
				cell = row.getCell((short) 4);
				String address = cell.getRichStringCellValue().toString();
				if (StringUtils.isBlank(address)) {
					rowErrorMsg += "第" + (i + 1) + "的材料存放地点信息为空。<br>";
					errorMsg.add(rowErrorMsg);
					continue;
				}
				if (!isValidAddress(address)) {
					rowErrorMsg += "第" + (i + 1) + "的材料存放地点信息不存在。<br>";
					errorMsg.add(rowErrorMsg);
					continue;
				}

				cell = row.getCell((short) 5);
				String value = cell.getRichStringCellValue().toString();
				if (!isValidNumber(value)) {
					rowErrorMsg += "第" + (i + 1) + "的材料入库数量信息不合法。<br>";
					errorMsg.add(rowErrorMsg);
					continue;
				}
			}

			if (flag) {
				oneDataMap.put("material_type_name", getValue(row, 0));
				oneDataMap.put("material_model_name", getValue(row, 1));
				oneDataMap.put("material_name", getValue(row, 2));
				oneDataMap.put("material_unit_name", getValue(row, 3));
				oneDataMap.put("material_address_name", getValue(row, 4));
				oneDataMap.put("material_apply_number", getValue(row, 5));

				String condition = " and t.typename='" + getValue(row, 0)
						+ "' ";
				List list = typeDao.queryList(condition);
				if (list != null && !list.isEmpty()) {
					String typeId = ((BigDecimal) ((DynaBean) list.get(0))
							.get("id")).toString();
					oneDataMap.put("material_type_id", typeId);
				}
				condition = " and t.modelname='" + getValue(row, 1) + "' ";
				condition += " and mt.typename='" + getValue(row, 0) + "'";
				list = modelDao.queryList(condition);
				if (list != null && !list.isEmpty()) {
					String modelId = ((BigDecimal) ((DynaBean) list.get(0))
							.get("id")).toString();
					oneDataMap.put("material_model_id", modelId);
				}
				condition = " and t.name='" + getValue(row, 2) + "' ";
				condition += " and mt.modelname='" + getValue(row, 1) + "' ";
				condition += " and tt.typename='" + getValue(row, 0) + "' ";
				list = materialDao.queryList(condition);
				if (list != null && !list.isEmpty()) {
					String materialId = ((BigDecimal) ((DynaBean) list.get(0))
							.get("id")).toString();
					oneDataMap.put("material_material_id", materialId);
				}
				condition = " and a.address='" + getValue(row, 4) + "' ";
				list = addressDao.queryList(condition);
				if (list != null && !list.isEmpty()) {
					String addressId = ((BigDecimal) ((DynaBean) list.get(0))
							.get("id")).toString();
					oneDataMap.put("material_address_id", addressId);
				}
				dataList.add(oneDataMap);
			}
		}
		String msg = "";
		if (!errorMsg.isEmpty()) {
			msg = errorMsg.size() + "条未导入<br/>";
		}
		msg = msg + StringUtils.join(errorMsg.toArray(), "<br/>");
		Map map = new HashMap();
		map.put("error_msg", msg);
		map.put("data_list", dataList);
		return map;
	}

	private boolean isValidMaterailType(String type) {
		String condition = " and t.typename='" + type + "' ";
		List list = typeDao.queryList(condition);
		if (list != null && !list.isEmpty()) {
			return true;
		}
		return false;
	}

	private boolean isValidMaterailModel(String type, String model) {
		String condition = " and t.modelname='" + model + "' ";
		condition += " and mt.typename='" + type + "' ";
		List list = modelDao.queryList(condition);
		if (list != null && !list.isEmpty()) {
			return true;
		}
		return false;
	}

	private boolean isValidMaterail(String type, String model, String name) {
		String condition = " and t.name='" + name + "' ";
		condition += " and mt.modelname='" + model + "' ";
		condition += " and tt.typename='" + type + "' ";
		List list = materialDao.queryList(condition);
		if (list != null && !list.isEmpty()) {
			return true;
		}
		return false;
	}

	private boolean isValidAddress(String address) {
		String condition = " and a.address='" + address + "' ";
		List list = addressDao.queryList(condition);
		if (list != null && !list.isEmpty()) {
			return true;
		}
		return false;
	}

	private boolean isValidNumber(String value) {
		Pattern pattern = Pattern.compile("[0-9]*(\\.?)[0-9]*");
		return pattern.matcher(value).matches();
	}

	public String getValue(HSSFRow row, int i) {
		HSSFCell cell = row.getCell((short) i);
		return getCellStringValue(cell);
	}

	/**
	 * 获得当前行的数据
	 * 
	 * @param cell
	 * @return
	 */
	public String getCellStringValue(HSSFCell cell) {
		String cellValue = null;
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			cellValue = cell.getRichStringCellValue().toString();
			if (cellValue.trim().equals("") || cellValue.trim().length() <= 0)
				cellValue = "";
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			cellValue = String.valueOf(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cellValue = String.valueOf(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			cellValue = "&nbsp;";
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			break;
		case HSSFCell.CELL_TYPE_ERROR:
			break;
		default:
			break;
		}
		return cellValue;
	}

	@Override
	protected HibernateDao<LinePatrolManager, String> getEntityDao() {
		// TODO Auto-generated method stub
		return null;
	}

}
