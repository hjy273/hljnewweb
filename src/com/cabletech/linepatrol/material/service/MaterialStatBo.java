package com.cabletech.linepatrol.material.service;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.beanutils.DynaBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.config.ConfigPathUtil;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.dao.ContractorDAO;
import com.cabletech.linepatrol.material.beans.MaterialStatBean;
import com.cabletech.linepatrol.material.dao.MaterialInfoDao;
import com.cabletech.linepatrol.material.dao.MaterialStatDao;
import com.cabletech.linepatrol.material.domain.MaterialStock;
import com.cabletech.linepatrol.material.templates.MaterialStatTemplate;

@Service
@Transactional
public class MaterialStatBo extends EntityManager<MaterialStock, String> {
	@Resource(name = "materialStatDao")
	private MaterialStatDao materialStatDao;
	@Resource(name = "contractorDao")
	private ContractorDAO contractorDao;
	@Resource(name = "materialInfoDao")
	private MaterialInfoDao materialDao;

	@Override
	protected HibernateDao<MaterialStock, String> getEntityDao() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map materialStat(UserInfo userInfo, MaterialStatBean bean) {
		// TODO Auto-generated method stub
		String condition = ConditionGenerate.getUserQueryCondition(userInfo);
		if (bean != null) {
			condition += ConditionGenerate.getCondition("ci.contractorid", bean
					.getContractorId(), "ci.contractorid", "=");
			condition += ConditionGenerate.getCondition("mt.id", bean
					.getMaterialType(), "mt.id", "=");
			condition += ConditionGenerate.getCondition("mm.id", bean
					.getMaterialMode(), "mm.id", "=");
			condition += ConditionGenerate.getCondition("mb.id", bean
					.getMaterial(), "mb.id", "=");
			// 年度日期函数查询条件
			if (MaterialStatBean.YEAR_QUERY_MODE.equals(bean.getQueryMode())) {
				condition += ConditionGenerate.getCondition(
						"to_char(used_material_table.use_time,'yyyy')", bean
								.getYear(),
						"to_char(used_material_table.use_time,'yyyy')", "=");
			}
			// 月度日期函数查询条件
			if (MaterialStatBean.MONTH_QUERY_MODE.equals(bean.getQueryMode())) {
				condition += ConditionGenerate.getCondition(
						"to_char(used_material_table.use_time,'yyyy-mm')", bean
								.getMonth(),
						"to_char(used_material_table.use_time,'yyyy-mm')", "=");
			}
			// 季度日期函数查询条件
			if (MaterialStatBean.QUARTER_QUERY_MODE.equals(bean.getQueryMode())) {
//				condition += ConditionGenerate.getCondition(
//						"to_char(used_material_table.use_time,'Q')", bean
//								.getQuarter(),
//						"to_char(used_material_table.use_time,'Q')", "=");
				String quarter=bean.getQuarter();
				if(quarter!=null){
					condition+=" and to_char(used_material_table.use_time,'Q') in("+quarter+")";
				}
			}
			// 其他日期函数查询条件
			if (MaterialStatBean.OTHER_QUERY_MODE.equals(bean.getQueryMode())) {
				condition += ConditionGenerate.getDateCondition(
						"used_material_table.use_time", bean.getBeginDate(),
						"used_material_table.use_time", ">=", "00:00:00");
				condition += ConditionGenerate.getDateCondition(
						"used_material_table.use_time", bean.getEndDate(),
						"used_material_table.use_time", "<=", "23:59:59");
			}
		}
		List list = materialStatDao.getAllUsedMaterialNumberList(condition);
		String contractorId;
		String contractorName;
		String materialId;
		String materialName;
		DynaBean tmpBean;
		List contractorIdList = new ArrayList();
		List materialIdList = new ArrayList();
		Map contractorNameMap = new HashMap();
		List materialNameList = new ArrayList();
		Map map = new HashMap();
		Map resultMap = new HashMap();
		Map materialNumberMap = new HashMap();
		double materialNumber = 0.0;
		// <contractorId,<materialId,materialUseNumber>>
		for (int i = 0; list != null && i < list.size(); i++) {
			materialNumberMap = new HashMap();
			materialNumber = 0;
			tmpBean = (DynaBean) list.get(i);
			contractorId = (String) tmpBean.get("contractorid");
			materialId = (String) tmpBean.get("material_id");
			if (resultMap != null && resultMap.containsKey(contractorId)) {
				materialNumberMap = (Map) resultMap.get(contractorId);
			}
			if (materialNumberMap != null
					&& materialNumberMap.containsKey(materialId)) {
				materialNumber = (Double) materialNumberMap.get(materialId);
			}
			materialNumber += ((BigDecimal) tmpBean.get("used_number"))
					.doubleValue();
			materialNumberMap.put(materialId, new Double(materialNumber));
			resultMap.put(contractorId, materialNumberMap);

			// 保存统计的代维公司名称和材料名称列表
			if (!contractorIdList.contains(contractorId)) {
				contractorIdList.add(contractorId);
			}
			if (!materialIdList.contains(materialId)) {
				materialIdList.add(materialId);
			}
		}
		for (int i = 0; contractorIdList != null && i < contractorIdList.size(); i++) {
			contractorId = (String) contractorIdList.get(i);
			contractorName = contractorDao.getContractorName(contractorId);
			contractorNameMap.put(contractorId, contractorName);
		}
		for (int i = 0; materialIdList != null && i < materialIdList.size(); i++) {
			materialId = (String) materialIdList.get(i);
			materialName = materialDao.getMaterialName(materialId);
			materialNameList.add(materialName);
		}
		map.put("contractor_id_list", contractorIdList);
		map.put("material_id_list", materialIdList);
		map.put("contractor_name_map", contractorNameMap);
		map.put("material_name_list", materialNameList);
		map.put("result_map", resultMap);
		logger.info(map);
		return map;
	}

	public MaterialStatTemplate exportMaterialStatResult(
			Map materialStatResultMap) throws Exception {
		logger.info(materialStatResultMap);
		Properties config = getReportConfig();
		String urlPath = getUrlPath(config, "report.materialstatresult");
		MaterialStatTemplate template = new MaterialStatTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.doExportMaterialStat(materialStatResultMap, excelstyle);
		return template;
	}

}
