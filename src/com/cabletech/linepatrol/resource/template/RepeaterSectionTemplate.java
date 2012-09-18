package com.cabletech.linepatrol.resource.template;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;
import com.cabletech.linepatrol.resource.model.RepeaterSection;

/**
 *导出光缆中继段
 * @author fjj
 *
 */
public class RepeaterSectionTemplate extends Template {
	private static Logger logger = Logger.getLogger("Template");

	public RepeaterSectionTemplate(String urlPath) {
		super(urlPath);
	}

	/**
	 * 导出光缆信息
	 * @param list
	 * @param excelstyle
	 */
	public void doExportRepeaters(List list,Map<String,String> contractor,
			Map<String,String> places, Map<String,String> sections ,
			Map<String,String> cabletype,Map<String,String> layingmethod ,
			ExcelStyle excelstyle) {
		activeSheet(0);
		RepeaterSection repeat ;
		super.curStyle = excelstyle.defaultStyle(super.workbook);
		int r = 2; // 行索引
		try{
			if(list != null && list.size()>0) {
				Iterator iter = list.iterator();
				while (iter.hasNext()) {
					repeat = (RepeaterSection) iter.next();
					activeRow(r);
					if (repeat == null) {
						setCellValue(0, "");
					} else {
						setCellValue(0, repeat.getSegmentid());
					}
					if (repeat == null) {
						setCellValue(1, "");
					} else {
						setCellValue(1, repeat.getAssetno());
					}
					if (repeat == null) {
						setCellValue(2, "");
					} else {
						setCellValue(2, repeat.getSegmentname());
					}
					if (repeat == null) {
						setCellValue(3, "");
					} else {
						setCellValue(3, repeat.getFiberType());
					}
					String cableLevel = cabletype.get(repeat.getCableLevel());
					setCellValue(4, cableLevel);

					String laytype =  repeat.getLaytype();
					String m = "";
					if(laytype!=null && laytype.length()>0){
						String[] laytypes = laytype.split(",");
						for(int i = 0;laytypes!=null && i<laytypes.length;i++){
							m+="  "+layingmethod.get(laytypes[i]);
						}
					}else{
						m="";
					}
					setCellValue(5, m);
					String section = sections.get(repeat.getScetion());
					setCellValue(6, section);

					String place = places.get(repeat.getPlace());
					setCellValue(7, place);

					if (repeat == null) {
						setCellValue(8, "");
					} else {
						setCellValue(8, repeat.getGrossLength()+"");
					}

					if (repeat == null) {
						setCellValue(9, "");
					} else {
						setCellValue(9, repeat.getProducer());
					}

					if (repeat == null) {
						setCellValue(10, "");
					} else {
						Date finishDate = repeat.getFinishtime();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy/mm/dd");
						String time = sdf.format(finishDate);
						setCellValue(10, time);
					}
					String conName = contractor.get(repeat.getMaintenanceId());
					setCellValue(11, conName);
					r++;
				}
			}
		}catch(Exception e){
			logger.error("导出光缆失败:"+e.getMessage());
			e.getStackTrace();
		}
	}





}
