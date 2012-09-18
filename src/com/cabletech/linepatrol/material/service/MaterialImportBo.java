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
		// ���������Ϣ
		List<String> errorMsg = new ArrayList<String>();
		int lastRow = sheet.getPhysicalNumberOfRows();
		// lastRow��׼����ҪУ��
		for (int i = 2; i < lastRow; i++) {
			// ��Ч��־
			boolean flag = true;
			// �д�����Ϣ
			String rowErrorMsg = "";
			HSSFRow row = sheet.getRow(i);

			// ��֤����ÿһ�����Ϊ��
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
				// ��֤��������
				HSSFCell cell = row.getCell((short) 0);
				String type = cell.getRichStringCellValue().toString();
				if (StringUtils.isBlank(type)) {
					rowErrorMsg += "��" + (i + 1) + "�Ĳ���������ϢΪ�ա�<br>";
					errorMsg.add(rowErrorMsg);
					continue;
				}
				if (!isValidMaterailType(type)) {
					rowErrorMsg += "��" + (i + 1) + "�Ĳ���������Ϣ�����ڡ�<br>";
					errorMsg.add(rowErrorMsg);
					continue;
				}

				// ��֤���Ϲ��
				cell = row.getCell((short) 1);
				String model = cell.getRichStringCellValue().toString();
				if (StringUtils.isBlank(model)) {
					rowErrorMsg += "��" + (i + 1) + "�Ĳ��Ϲ����ϢΪ�ա�<br>";
					errorMsg.add(rowErrorMsg);
					continue;
				}
				if (!isValidMaterailModel(type, model)) {
					rowErrorMsg += "��" + (i + 1) + "�Ĳ��Ϲ����Ϣ�����ڡ�<br>";
					errorMsg.add(rowErrorMsg);
					continue;
				}

				// ��֤��������
				cell = row.getCell((short) 2);
				String name = cell.getRichStringCellValue().toString();
				if (StringUtils.isBlank(name)) {
					rowErrorMsg += "��" + (i + 1) + "�Ĳ���������ϢΪ�ա�<br>";
					errorMsg.add(rowErrorMsg);
					continue;
				}
				if (!isValidMaterail(type, model, name)) {
					rowErrorMsg += "��" + (i + 1) + "�Ĳ���������Ϣ�����ڡ�<br>";
					errorMsg.add(rowErrorMsg);
					continue;
				}

				// ��֤��ŵص�
				cell = row.getCell((short) 4);
				String address = cell.getRichStringCellValue().toString();
				if (StringUtils.isBlank(address)) {
					rowErrorMsg += "��" + (i + 1) + "�Ĳ��ϴ�ŵص���ϢΪ�ա�<br>";
					errorMsg.add(rowErrorMsg);
					continue;
				}
				if (!isValidAddress(address)) {
					rowErrorMsg += "��" + (i + 1) + "�Ĳ��ϴ�ŵص���Ϣ�����ڡ�<br>";
					errorMsg.add(rowErrorMsg);
					continue;
				}

				cell = row.getCell((short) 5);
				String value = cell.getRichStringCellValue().toString();
				if (!isValidNumber(value)) {
					rowErrorMsg += "��" + (i + 1) + "�Ĳ������������Ϣ���Ϸ���<br>";
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
			msg = errorMsg.size() + "��δ����<br/>";
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
	 * ��õ�ǰ�е�����
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
