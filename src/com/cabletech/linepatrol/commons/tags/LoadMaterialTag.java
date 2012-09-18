package com.cabletech.linepatrol.commons.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.beanutils.DynaBean;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.linepatrol.commons.dao.ReturnMaterialDAO;
import com.cabletech.linepatrol.commons.dao.UseMaterialDAO;
import com.cabletech.linepatrol.material.dao.MaterialInfoDao;
import com.cabletech.linepatrol.material.dao.MaterialModelDao;
import com.cabletech.linepatrol.material.dao.MaterialStockDao;
import com.cabletech.linepatrol.material.dao.MaterialTypeDao;
import com.cabletech.linepatrol.remedy.service.ConditionGenerate;

/**
 * 载入材料库存选择和输入数量列表的标签
 * 
 * @author Administrator
 * 
 */
public class LoadMaterialTag extends BodyTagSupport {
	public String USE_MATERIAL_TYPE = "Use";
	public String RECYCLE_MATERIAL_TYPE = "Recycle";
	public String INPUT_DISPLAY_TYPE = "input";
	public String VIEW_DISPLAY_TYPE = "view";
	public String VIEW_SIMPLE_DISPLAY_TYPE = "view_simple";
	private UserInfo userInfo;
	private ApplicationContext applicationContext;
	private List materialTypeList;
	private List materialModelList;
	private List materialList;
	private List materialStorageList;
	private DynaBean bean;
	private List useMaterialList;
	private int listLength;
	// 标签输入属性
	// 材料被使用的类型
	private String useType;
	// 材料被使用的关联对象编号
	private String objectId;
	// 材料的标签名称
	private String label = "";
	// 材料的使用类型（Use表示使用材料，Recycle表示回收材料）
	private String materialUseType = "";
	// 材料表格的HTML样式说明
	private String tbStyle = "";
	// 材料表格的HTML样式定义
	private String tbClass = "";
	// 材料的显示方式
	private String displayType = INPUT_DISPLAY_TYPE;

	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getMaterialUseType() {
		return materialUseType;
	}

	public void setMaterialUseType(String materialUseType) {
		this.materialUseType = materialUseType;
	}

	public String getTbStyle() {
		return tbStyle;
	}

	public void setTbStyle(String tbStyle) {
		this.tbStyle = tbStyle;
	}

	public String getTbClass() {
		return tbClass;
	}

	public void setTbClass(String tbClass) {
		this.tbClass = tbClass;
	}

	public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		StringBuffer buf = new StringBuffer();
		String contextPath = super.pageContext.getServletContext()
				.getContextPath();
		userInfo = (UserInfo) super.pageContext.getSession().getAttribute(
				"LOGIN_USER");
		String condition = this.getUserQueryCondition(userInfo);
		init(condition);
		if (VIEW_DISPLAY_TYPE.equals(displayType)) {
			buf.append("<table width='100%' ");
			buf.append(" class='" + tbClass + "' ");
			buf.append(" style='" + tbStyle + "'> ");
			writeLabelTr(buf);
			writeViewTables(buf);
		} else if (VIEW_SIMPLE_DISPLAY_TYPE.equals(displayType)) {
			writeViewSimpleMaterialList(buf);
		} else {
			buf.append("<script type=\"text/javascript\" src=\"" + contextPath
					+ "/js/tableoperate.js\">");
			buf.append("</script>");
			buf.append("<script type=\"text/javascript\" src=\"" + contextPath
					+ "/js/remedy/remedy_operate.js\">");
			buf.append("</script>");
			buf.append("<table width='100%' ");
			buf.append(" class='" + tbClass + "' ");
			buf.append(" style='" + tbStyle + "'> ");
			writeLabelTr(buf);
			writeAllMaterialSelect(buf);
			writeMainTables(buf);
			writeJavascript(buf);
			writeSampleTables(buf);
			buf.append("</table>");
		}
		try {
			super.pageContext.getOut().print(buf.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.EVAL_PAGE;
	}

	public void writeViewTables(StringBuffer buf) {
		buf.append("<tr class=trcolor><td style='padding:10px'>");
		buf.append("<table border=\"1\" cellpadding=\"0\"");
		buf
				.append(" cellspacing=\"0\" width=\"100%\" style='border-collapse:collapse;'>");
		buf.append("<tr>");
		buf.append("<td ");
		buf.append(" style=\"text-align: center;\">");
		buf.append("材料</td>");
		buf.append("<td ");
		buf.append(" style=\"text-align: center;\">");
		buf.append("存放地点</td>");
		buf.append("<td ");
		buf.append(" style=\"text-align: center;\">");
		buf.append("材料库存类型</td>");
		buf.append("<td ");
		buf.append(" style=\"text-align: center;\">");
		if (RECYCLE_MATERIAL_TYPE.equals(materialUseType)) {
			buf.append("回收数量");
		} else {
			buf.append("使用数量");
		}
		buf.append("</td></tr>");

		writeViewMaterialList(buf);

		buf.append("</table>");
		buf.append("</td></tr>");
		buf.append("</table>");
	}

	public void writeViewMaterialList(StringBuffer buf) {
		for (int i = 0; useMaterialList != null && i < useMaterialList.size(); i++) {
			bean = (DynaBean) useMaterialList.get(i);
			buf.append("<tr>");
			buf.append("<td style=\"text-align: center;\">");
			buf.append(bean.get("material_name"));
			buf.append("</td>");
			buf.append("<td style=\"text-align: center;\">");
			buf.append(bean.get("address"));
			buf.append("</td>");
			buf.append("<td style=\"text-align: center;\">");
			buf.append(bean.get("storage_type"));
			buf.append("</td>");
			buf.append("<td style=\"text-align: center;\">");
			buf.append(bean.get("use_number"));
			buf.append("</td>");
			buf.append("</tr>");
		}
	}

	public void writeViewSimpleMaterialList(StringBuffer buf) {
		for (int i = 0; useMaterialList != null && i < useMaterialList.size(); i++) {
			bean = (DynaBean) useMaterialList.get(i);
			buf.append("材料名称：");
			buf.append(bean.get("material_name"));
			buf.append("<br />存放地点：");
			buf.append(bean.get("address"));
			buf.append("<br />材料库存类型：");
			buf.append(bean.get("storage_type"));
			if (RECYCLE_MATERIAL_TYPE.equals(materialUseType)) {
				buf.append("<br />回收数量：");
			} else {
				buf.append("<br />使用数量：");
			}
			buf.append(bean.get("use_number"));
		}
	}

	public void writeJavascript(StringBuffer buf) {
		buf.append("<script type=\"text/javascript\">");
		if (listLength == 1) {
			bean = (DynaBean) useMaterialList.get(0);
			buf.append("document.forms[0].elements[\"materialType"
					+ materialUseType + "\"].value='" + bean.get("typeid")
					+ "';");
			buf.append("changeMaterialMode('materialType" + materialUseType
					+ "','sMaterialMode" + materialUseType + "','materialMode"
					+ materialUseType + "','1','" + materialUseType + "');");
			buf.append("document.forms[0].elements[\"materialMode"
					+ materialUseType + "\"].value='" + bean.get("modelid")
					+ "';");
			buf.append("changeMaterial('materialMode" + materialUseType
					+ "','sMaterial" + materialUseType + "','material"
					+ materialUseType + "','1','" + materialUseType + "');");
			buf.append("document.forms[0].elements[\"material"
					+ materialUseType + "\"].value='" + bean.get("materialid")
					+ "';");
			buf.append("changeMaterialStorage('material" + materialUseType
					+ "','sMaterialStorageAddr" + materialUseType
					+ "','materialStorageAddr" + materialUseType + "','1','"
					+ materialUseType + "');");
			buf.append("document.forms[0].elements[\"materialStorageAddr"
					+ materialUseType + "\"].value='" + bean.get("addressid")
					+ "';");
			buf.append("changeMaterialStorageType('1','" + materialUseType
					+ "');");
			buf.append("document.forms[0].elements[\"materialStorageType"
					+ materialUseType + "\"].value='" + bean.get("storagetype")
					+ "';");
			buf.append("changeMaterialStorageNumber('materialStorageType"
					+ materialUseType + "','1','" + materialUseType + "');");
			buf.append("document.forms[0].elements[\"materialStorageNumber"
					+ materialUseType + "\"].value='"
					+ bean.get("storage_number") + "';");
			buf.append("document.forms[0].elements[\"materialUseNumber"
					+ materialUseType + "\"].value='" + bean.get("use_number")
					+ "';");
		} else if (listLength >= 1) {
			for (int row = 1; row <= listLength; row++) {
				bean = (DynaBean) useMaterialList.get(row - 1);
				buf.append("document.forms[0].elements[\"materialType"
						+ materialUseType + "\"][" + (row - 1) + "].value='"
						+ bean.get("typeid") + "';");
				buf.append("changeMaterialMode('materialType" + materialUseType
						+ "','sMaterialMode" + materialUseType
						+ "','materialMode" + materialUseType + "','" + row
						+ "','" + materialUseType + "');");
				buf.append("document.forms[0].elements[\"materialMode"
						+ materialUseType + "\"][" + (row - 1) + "].value='"
						+ bean.get("modelid") + "';");
				buf.append("changeMaterial('materialMode" + materialUseType
						+ "','sMaterial" + materialUseType + "','material"
						+ materialUseType + "','" + row + "','"
						+ materialUseType + "');");
				buf.append("document.forms[0].elements[\"material"
						+ materialUseType + "\"][" + (row - 1) + "].value='"
						+ bean.get("materialid") + "';");
				buf.append("changeMaterialStorage('material" + materialUseType
						+ "','sMaterialStorageAddr" + materialUseType
						+ "','materialStorageAddr" + materialUseType + "','"
						+ row + "','" + materialUseType + "');");
				buf.append("document.forms[0].elements[\"materialStorageAddr"
						+ materialUseType + "\"][" + (row - 1) + "].value='"
						+ bean.get("addressid") + "';");
				buf.append("changeMaterialStorageType('" + row + "','"
						+ materialUseType + "');");
				buf.append("document.forms[0].elements[\"materialStorageType"
						+ materialUseType + "\"][" + (row - 1) + "].value='"
						+ bean.get("storagetype") + "';");
				buf.append("changeMaterialStorageNumber('materialStorageType"
						+ materialUseType + "','" + row + "','"
						+ materialUseType + "');");
				buf.append("document.forms[0].elements[\"materialStorageNumber"
						+ materialUseType + "\"][" + (row - 1) + "].value='"
						+ bean.get("storage_number") + "';");
				buf.append("document.forms[0].elements[\"materialUseNumber"
						+ materialUseType + "\"][" + (row - 1) + "].value='"
						+ bean.get("use_number") + "';");
			}
		}
		buf.append("</script>");
	}

	public void writeMainTables(StringBuffer buf) {
		buf.append("<tr class=trcolor><td style='padding:10px'>");
		buf.append("<table id=\"materialTable_");
		buf.append(materialUseType);
		buf.append("\"  border=\"1\" cellpadding=\"0\"");
		buf
				.append(" cellspacing=\"0\" style='border-collapse:collapse;' width=\"100%\">");
		buf.append("<tr>");
		buf.append("<td colspan=\"3\" ");
		buf.append(" style=\"text-align: center; width: 300px;\">");
		buf.append("材料</td>");
		buf.append("<td rowspan=\"2\"");
		buf.append(" style=\"text-align: center; width: 100px;\">");
		buf.append("存放地点</td>");
		buf.append("<td rowspan=\"2\"");
		buf.append(" style=\"text-align: center; width: 100px;\">");
		buf.append("材料库存类型</td>");
		buf.append("<td rowspan=\"2\"");
		buf.append(" style=\"text-align: center; width: 100px;\">");
		buf.append("库存量</td>");
		buf.append("<td rowspan=\"2\"");
		buf.append(" style=\"text-align: center; width: 100px;\">");
		if (RECYCLE_MATERIAL_TYPE.equals(materialUseType)) {
			buf.append("回收数量");
		} else {
			buf.append("使用数量");
		}
		buf.append("</td>");
		buf.append("<td rowspan=\"2\"");
		buf.append(" style=\"text-align: center; width: 50px;\">");
		buf.append("操作</td>");
		buf.append("</tr>");
		buf.append("<tr>");
		buf.append("<td style=\"text-align: center; width: 100px;\">");
		buf.append("类型</td>");
		buf.append("<td style=\"text-align: center; width: 100px;\">");
		buf.append("规格</td>");
		buf.append("<td style=\"text-align: center; width: 100px;\">");
		buf.append("名称</td>");
		buf.append("</tr>");

		for (int row = 1; row <= listLength; row++) {
			buf.append("<tr id=\"" + row + "\">");
			buf.append("<td style=\"text-align: center; width: 100px;\">");
			buf.append("<select id=\"materialType" + materialUseType + "_"
					+ row + "\"");
			buf.append(" name=\"materialType" + materialUseType + "\"");
			buf.append(" style=\"width: 95px;\"");
			buf.append(" onchange=\"changeMaterialMode");
			buf.append("('materialType" + materialUseType + "','sMaterialMode"
					+ materialUseType + "','materialMode" + materialUseType
					+ "',");
			buf.append("'" + row + "','" + materialUseType + "');\">");
			buf.append("<option value=\"\">");
			buf.append("请选择");
			buf.append("</option>");
			for (int i = 0; materialTypeList != null
					&& i < materialTypeList.size(); i++) {
				bean = (DynaBean) materialTypeList.get(i);
				buf.append("<option");
				buf.append(" value=\"" + bean.get("id") + "\">");
				buf.append(bean.get("typename"));
				buf.append("</option>");
			}
			buf.append("</select>");
			buf.append("</td>");
			buf.append("<td style=\"text-align: center; width: 100px;\">");
			buf.append("<select id=\"materialMode" + materialUseType + "_"
					+ row + "\"");
			buf.append(" name=\"materialMode" + materialUseType + "\"");
			buf.append(" style=\"width: 95px;\"");
			buf.append(" onchange=\"changeMaterial");
			buf.append("('materialMode" + materialUseType + "','sMaterial"
					+ materialUseType + "','material" + materialUseType + "',");
			buf.append("'" + row + "','" + materialUseType + "');\">");
			buf.append("<option value=\"\">");
			buf.append("请选择");
			buf.append("</option>");
			buf.append("</select>");
			buf.append("</td>");
			buf.append("<td style=\"text-align: center; width: 100px;\">");
			buf.append("<select id=\"material" + materialUseType + "_" + row
					+ "\" name=\"material" + materialUseType + "\"");
			buf.append(" style=\"width: 95px;\"");
			buf.append(" onchange=\"changeMaterialStorage");
			buf.append("('material" + materialUseType
					+ "','sMaterialStorageAddr" + materialUseType + "',");
			buf.append("'materialStorageAddr" + materialUseType + "','" + row
					+ "','" + materialUseType + "');\">");
			buf.append("<option value=\"\">");
			buf.append("请选择");
			buf.append("</option>");
			buf.append("</select>");
			buf.append("</td>");
			buf.append("<td style=\"text-align: center; width: 100px;\">");
			buf.append("<select id=\"materialStorageAddr" + materialUseType
					+ "_" + row + "\"");
			buf.append(" name=\"materialStorageAddr" + materialUseType
					+ "\" style=\"width: 95px;\"");
			buf.append(" onchange=\"changeMaterialStorageType");
			buf.append("('" + row + "','" + materialUseType + "');\">");
			buf.append("<option value=\"\">");
			buf.append("请选择");
			buf.append("</option>");
			buf.append("</select>");
			buf.append("</td>");
			buf.append("<td style=\"text-align: center; width: 100px;\">");
			buf.append("<select id=\"materialStorageType" + materialUseType
					+ "_" + row + "\"");
			buf.append(" name=\"materialStorageType" + materialUseType
					+ "\" style=\"width: 95px;\"");
			buf.append(" onchange=\"changeMaterialStorageNumber");
			buf.append("('materialStorageType" + materialUseType + "','" + row
					+ "','" + materialUseType + "');\">");
			buf.append("<option value=\"\">");
			buf.append("请选择");
			buf.append("</option>");
			buf.append("<option value=\"0\">");
			buf.append("利旧材料");
			buf.append("</option>");
			buf.append("<option value=\"1\">");
			buf.append("新增材料");
			buf.append("</option>");
			buf.append("</select>");
			buf.append("</td>");
			buf.append("<td style=\"text-align: center; width: 100px;\">");
			buf.append("<input id=\"materialStorageNumber" + materialUseType
					+ "_" + row + "\"");
			buf.append(" name=\"materialStorageNumber" + materialUseType
					+ "\" type=\"hidden\" />");
			buf.append("<span id=\"materialStorageNumberDis" + materialUseType
					+ "_");
			buf.append(row + "\"></span>");
			buf.append("</td>");
			buf.append("<td style=\"text-align: center; width: 100px;\">");
			buf.append("<input id=\"materialUseNumber" + materialUseType + "_");
			buf.append(row + "\" name=\"materialUseNumber" + materialUseType
					+ "\"");
			buf.append(" type=\"text\" style=\"width: 80px;\"");
			if (USE_MATERIAL_TYPE.equals(materialUseType)) {
				buf.append(" onblur=\"compareStorageNumber('" + row + "','"
						+ materialUseType + "');\"");
			}
			buf.append(" />");
			buf.append("</td>");
			buf.append("<td style=\"text-align: center;\">");
			buf.append("<input id=\"btnDel\" name=\"btnDel\"");
			buf.append(" value=\"删除\" type=\"button\"");
			buf.append(" onclick=\"deleteTbRow");
			buf.append("('materialTable_");
			buf.append(materialUseType);
			buf.append("','sampleMaterialTable_");
			buf.append(materialUseType);
			buf.append("',");
			buf.append(row + ");\" />");
			buf.append("</td>");
			buf.append("</tr>");

		}
		buf.append("</table>");
		buf.append("</td></tr>");
	}

	public void writeSampleTables(StringBuffer buf) {
		buf.append("<tr><td style='padding:10px'>");
		buf.append("<table id=\"sampleMaterialTable_");
		buf.append(materialUseType);
		buf.append("\" border=\"0\" cellpadding=\"0\" ");
		buf.append(" cellspacing=\"0\" width=\"100%\"");
		buf.append(" style=\"display: none;\"><tr>");
		buf.append("<td style=\"text-align: center; width: 100px;\">");
		buf.append("<select id=\"sample_materialType" + materialUseType
				+ "_row0\" ");
		buf.append(" name=\"sample_materialType" + materialUseType
				+ "\" style=\"width: 95px;\" ");
		buf.append(" onchange=\"changeMaterialMode");
		buf.append("('materialType" + materialUseType + "','sMaterialMode"
				+ materialUseType + "','materialMode" + materialUseType
				+ "','row0','" + materialUseType + "');\"");
		buf.append("><option value=\"\">");
		buf.append("请选择");
		buf.append("</option>");

		for (int i = 0; materialTypeList != null && i < materialTypeList.size(); i++) {
			bean = (DynaBean) materialTypeList.get(i);
			buf.append("<option");
			buf.append(" value=\"" + bean.get("id") + "\">");
			buf.append(bean.get("typename"));
			buf.append("</option>");
		}

		buf.append("</select>");
		buf.append("</td>");
		buf.append("<td style=\"text-align: center; width: 100px;\">");
		buf.append("<select id=\"sample_materialMode" + materialUseType
				+ "_row0\" ");
		buf.append(" name=\"sample_materialMode" + materialUseType
				+ "\" style=\"width: 95px;\"");
		buf.append(" onchange=\"changeMaterial");
		buf.append("('materialMode" + materialUseType + "','sMaterial"
				+ materialUseType + "','material" + materialUseType
				+ "','row0','" + materialUseType + "');\"");
		buf.append("><option value=\"\">");
		buf.append("请选择");
		buf.append("</option>");
		buf.append("</select>");
		buf.append("</td>");
		buf.append("<td style=\"text-align: center; width: 100px;\">");
		buf.append("<select id=\"sample_material" + materialUseType
				+ "_row0\" ");
		buf.append(" name=\"sample_material" + materialUseType
				+ "\" style=\"width: 95px;\" ");
		buf.append(" onchange=\"changeMaterialStorage");
		buf.append("('material" + materialUseType + "','sMaterialStorageAddr"
				+ materialUseType + "',");
		buf.append("'materialStorageAddr" + materialUseType + "','row0','"
				+ materialUseType + "');\"");
		buf.append("><option value=\"\">");
		buf.append("请选择");
		buf.append("</option>");
		buf.append("</select>");
		buf.append("</td>");
		buf.append("<td style=\"text-align: center; width: 100px;\">");
		buf.append("<select id=\"sample_materialStorageAddr" + materialUseType
				+ "_row0\"");
		buf.append(" name=\"sample_materialStorageAddr" + materialUseType
				+ "\" style=\"width: 95px;\"");
		buf.append(" onchange=\"changeMaterialStorageType('row0','"
				+ materialUseType + "');\">");
		buf.append("<option value=\"\">");
		buf.append("请选择");
		buf.append("</option>");
		buf.append("</select>");
		buf.append("</td>");
		buf.append("<td style=\"text-align: center; width: 100px;\">");
		buf.append("<select id=\"sample_materialStorageType" + materialUseType
				+ "_row0\"");
		buf.append(" name=\"sample_materialStorageType" + materialUseType
				+ "\" style=\"width: 95px;\"");
		buf.append(" onchange=\"changeMaterialStorageNumber");
		buf.append("('materialStorageType" + materialUseType + "','row0','"
				+ materialUseType + "');\">");
		buf.append("<option value=\"\">");
		buf.append("请选择");
		buf.append("</option>");
		buf.append("<option value=\"0\">");
		buf.append("利旧材料");
		buf.append("</option>");
		buf.append("<option value=\"1\">");
		buf.append("新增材料");
		buf.append("</option>");
		buf.append("</select>");
		buf.append("</td>");
		buf.append("<td style=\"text-align: center; width: 100px;\">");
		buf.append("<input id=\"sample_materialStorageNumber" + materialUseType
				+ "_row0\"");
		buf.append(" name=\"sample_materialStorageNumber" + materialUseType
				+ "\" type=\"hidden\" />");
		buf.append("<span id=\"sample_materialStorageNumberDis"
				+ materialUseType + "_row0\"></span>");
		buf.append("</td>");
		buf.append("			");
		buf.append("<td style=\"text-align: center; width: 100px;\">");
		buf.append("<input id=\"sample_materialUseNumber" + materialUseType
				+ "_row0\"");
		buf.append(" name=\"sample_materialUseNumber" + materialUseType + "\"");
		buf.append(" type=\"text\" style=\"width: 80px;\"");
		if (USE_MATERIAL_TYPE.equals(materialUseType)) {
			buf.append(" onblur=\"compareStorageNumber('row0','"
					+ materialUseType + "');\"");
		}
		buf.append(" />");
		buf.append("</td>");
		buf.append("			");
		buf.append("<td style=\"text-align: center; width: 50px;\">");
		buf.append("<input id=\"btnDel\" name=\"btnDel\" ");
		buf.append(" value=\"删除\" type=\"button\"");
		buf.append(" onclick=\"deleteTbRow");
		buf.append("('materialTable_");
		buf.append(materialUseType);
		buf.append("','sampleMaterialTable_");
		buf.append(materialUseType);
		buf.append("',row0);\" />");
		buf.append("</td>");
		buf.append("</tr>");
		buf.append("</table>");
		buf.append("</td></tr>");
	}

	public void writeAllMaterialSelect(StringBuffer buf) {
		buf.append("<tr style='display:none;'><td>");
		buf.append("<select id=\"sMaterialMode" + materialUseType
				+ "\" name=\"sMaterialMode" + materialUseType + "\"");
		buf.append(" style=\"width: 95px; display: none;\">");
		for (int i = 0; materialModelList != null
				&& i < materialModelList.size(); i++) {
			bean = (DynaBean) materialModelList.get(i);
			buf.append("<option");
			buf.append(" value=\"" + bean.get("id") + "\"");
			buf.append(" varRefId=\"" + bean.get("typeid") + "\">");
			buf.append(bean.get("modelname"));
			buf.append("</option>");
		}
		buf.append("</select>");

		buf.append("<select id=\"sMaterial" + materialUseType
				+ "\" name=\"sMaterial" + materialUseType + "\"");
		buf.append(" style=\"width: 95px; display: none;\">");
		for (int i = 0; materialList != null && i < materialList.size(); i++) {
			bean = (DynaBean) materialList.get(i);
			buf.append("<option value=\"" + bean.get("id") + "\"");
			buf.append(" varRefId=\"" + bean.get("modelid") + "\"");
			buf.append(" varUnitPrice=\"" + bean.get("price") + "\">");
			buf.append(bean.get("name"));
			buf.append("</option>");
		}
		buf.append("</select>");

		buf.append("<select id=\"sMaterialStorageAddr" + materialUseType
				+ "\" ");
		buf.append(" name=\"sMaterialStorageAddr" + materialUseType + "\" ");
		buf.append(" style=\"width: 95px; display: none;\">");
		for (int i = 0; materialStorageList != null
				&& i < materialStorageList.size(); i++) {
			bean = (DynaBean) materialStorageList.get(i);
			buf.append("<option");
			buf.append(" value=\"" + bean.get("addressid") + "\"");
			buf.append(" varRefId=\"" + bean.get("materialid") + "\"");
			buf.append(" varOldStorage=\"" + bean.get("oldstock") + "\"");
			buf.append(" varNewStorage=\"" + bean.get("newstock") + "\">");
			buf.append(bean.get("address"));
			buf.append("</option>");
		}
		buf.append("</select>");
		buf.append("</td></tr>");
	}

	public void init(String condition) {
		applicationContext = WebApplicationContextUtils
				.getWebApplicationContext(super.pageContext.getServletContext());
		MaterialTypeDao typeDao = (MaterialTypeDao) applicationContext
				.getBean("materialTypeDao");
		MaterialModelDao modelDao = (MaterialModelDao) applicationContext
				.getBean("materialModelDao");
		MaterialInfoDao materialDao = (MaterialInfoDao) applicationContext
				.getBean("materialInfoDao");
		MaterialStockDao stockDao = (MaterialStockDao) applicationContext
				.getBean("materialStockDao");
		UseMaterialDAO useDao = (UseMaterialDAO) applicationContext
				.getBean("useMaterialDAO");
		ReturnMaterialDAO returnDao = (ReturnMaterialDAO) applicationContext
				.getBean("returnMaterialDAO");
		String order = " order by t.typename,t.id ";
		materialTypeList = typeDao.queryList(condition + order);
		order = " order by t.typeid,t.modelname,t.id ";
		materialModelList = modelDao.queryList(condition + order);
		order = " order by tt.id,t.modelid,t.name,t.id ";
		materialList = materialDao.queryList(condition + order);
		order = " order by a.address,t.addressid ";
		materialStorageList = stockDao.queryList(condition + order);
		if (objectId == null) {
			objectId = "";
		}
		if (useType == null) {
			useType = "";
		}
		condition = " and t.object_id='" + objectId + "' and t.use_type='"
				+ useType + "' ";
		if (USE_MATERIAL_TYPE.equals(materialUseType)) {
			useMaterialList = useDao.queryList(condition);
		}
		if (RECYCLE_MATERIAL_TYPE.equals(materialUseType)) {
			useMaterialList = returnDao.queryList(condition);
		}
		if (useMaterialList != null) {
			listLength = useMaterialList.size();
		} else {
			listLength = 0;
		}
	}

	public void writeLabelTr(StringBuffer buf) {
		if (USE_MATERIAL_TYPE.equals(materialUseType)) {
			label = "使用材料";
		}
		if (RECYCLE_MATERIAL_TYPE.equals(materialUseType)) {
			label = "回收材料";
		}

		buf.append("<tr class=trcolor><td style='text-align:left;'>");
		buf.append(label);
		if (INPUT_DISPLAY_TYPE.equals(displayType)) {
			buf.append("<input id='btnAddMaterial' ");
			buf.append(" name='btnAddMaterial' ");
			buf.append(" value='添加" + label);
			buf.append("' type='button' onclick=");
			buf.append(" \"addTbRow('materialTable_");
			buf.append(materialUseType);
			buf.append("','sampleMaterialTable_");
			buf.append(materialUseType);
			buf.append("');\" /> ");
		}
		buf.append("</td></tr>");
	}

	/**
	 * 执行根据用户信息产生用户查询的限制条件
	 * 
	 * @param userInfo
	 *            UserInfo 用户信息
	 * @return String 用户查询的限制条件
	 */
	public String getUserQueryCondition(UserInfo userInfo) {
		StringBuffer buf = new StringBuffer("");
		if (ConditionGenerate.PROVINCE_CONTRACTOR_USER.equals(userInfo
				.getType())) {
			// buf.append(" start with c.contractorid");
		}
		if (ConditionGenerate.PROVINCE_SUPERVISE_USER
				.equals(userInfo.getType())) {
			// buf.append(" start with c.contractorid");
		}
		if (ConditionGenerate.CITY_MOBILE_USER.equals(userInfo.getType())) {
			buf.append(" and r.regionid='" + userInfo.getRegionid() + "' ");
		}
		if (ConditionGenerate.CITY_CONTRACTOR_USER.equals(userInfo.getType())) {
			buf.append(" and c.contractorid='" + userInfo.getDeptID() + "' ");
		}
		if (ConditionGenerate.CITY_SUPERVISE_USER.equals(userInfo.getType())) {
			buf.append(" and r.regionid='" + userInfo.getRegionid() + "' ");
		}

		return buf.toString();
	}
}
