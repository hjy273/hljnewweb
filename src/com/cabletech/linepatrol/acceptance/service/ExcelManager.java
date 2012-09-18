package com.cabletech.linepatrol.acceptance.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.commons.config.ConfigPathUtil;
import com.cabletech.commons.exceltemplates.ExcelUtil;
import com.cabletech.commons.tags.dao.DictionaryDao;
import com.cabletech.commons.tags.module.Dictionary;
import com.cabletech.commons.tags.services.DictionaryService;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.acceptance.dao.ApplyCableDao;
import com.cabletech.linepatrol.acceptance.dao.ApplyDao;
import com.cabletech.linepatrol.acceptance.dao.ApplyPipeDao;
import com.cabletech.linepatrol.acceptance.dao.CableResultDao;
import com.cabletech.linepatrol.acceptance.dao.PayCableDao;
import com.cabletech.linepatrol.acceptance.model.Apply;
import com.cabletech.linepatrol.acceptance.model.ApplyCable;
import com.cabletech.linepatrol.acceptance.model.ApplyPipe;
import com.cabletech.linepatrol.acceptance.model.CableResult;
import com.cabletech.linepatrol.acceptance.model.PayCable;
import com.cabletech.linepatrol.resource.dao.PipeDao;
import com.cabletech.linepatrol.resource.dao.RepeaterSectionDao;
import com.cabletech.linepatrol.resource.model.Pipe;
import com.cabletech.linepatrol.resource.model.RepeaterSection;

@Service
@Transactional
public class ExcelManager extends EntityManager<Apply, String>{
	@Resource(name="applyDao")
	private ApplyDao dao;
	@Resource(name="applyCableDao")
	private ApplyCableDao applyCableDao;
	@Resource(name="applyPipeDao")
	private ApplyPipeDao applyPipeDao;
	@Resource(name="repeaterSectionDao")
	private RepeaterSectionDao repeaterSectionDao;
	@Resource(name="pipeDao")
	private PipeDao pipeDao;
	@Resource(name="dictionaryDao")
	private DictionaryDao dictionaryDao;
	@Resource(name="payCableDao")
	private PayCableDao payCableDao;
	@Resource(name="cableResultDao")
	private CableResultDao cableResultDao;
	@Resource(name="applyTaskManager")
	private ApplyTaskManager applyTaskManager;
	@Resource(name="dictionaryService")
	private DictionaryService dictService;

	
	private static String CONTENT_TYPE = "application/vnd.ms-excel";
	/**
	 * 
	 * @param apply 验收申请信息
	 * @param in   上传附件的文件流
	 * @param type 指初验或复验
	 * @return
	 */
	public Apply importCable(Apply apply, InputStream in, String type) throws Exception{
		ExcelUtil excelUtil = new ExcelUtil(in);
		excelUtil.activeSheet(0);//激活当前工作表
		HSSFSheet sheet = excelUtil.getCurrentSheet(); //获得工作表
		int lastRow = sheet.getPhysicalNumberOfRows();//lastRow不准，需要校验

		List<String> errorMsg = new ArrayList<String>();//总体错误信息
		
		List<Dictionary> lays = dictService.getDictByAssortment("layingmethod",null);//铺设方式
		List<Dictionary> types = dictService.getDictByAssortment("cabletype",null);//光缆级别
		
		for(int i=2;i<lastRow;i++){
			//有效标志
			boolean flag = true;
			//行错误信息
			String rowErrorMsg = "";
			HSSFRow row = sheet.getRow(i);
			
			//验证此行每一项均不为空
			flag = excelUtil.validateIsNull(10, row);
			if(!flag){
				errorMsg.add("在第"+(i+1)+"行，存在单元格数据为空，请补充完全。");
			}
			if(flag){
				//验证光缆编号
				HSSFCell cell = row.getCell((short)5);
				String number = excelUtil.getCellStringValue(cell);
				if(StringUtils.isBlank(number))
					continue;
				
				if(type.equals("1")){ //初验
					if(!validateCableNumber(apply, number)){
						flag = false;
						rowErrorMsg += "在第"+(i+1)+"行，光缆编号"+number+"已存在。";
					}
				}else{ //复验
					String reinspectError = validateReinspectCableNumber(number);
					if(StringUtils.isNotBlank(reinspectError)){
						flag = false;
						rowErrorMsg += reinspectError;
					}
				}
				
				//验证光缆级别
				cell = row.getCell((short)2);
				String level = excelUtil.getCellStringValue(cell);
				if(!array2String(types).contains(level)){
					flag = false;
					rowErrorMsg += "在第"+(i+1)+"行，光缆级别内容有误。光缆级别包括："+com.cabletech.commons.util.StringUtils.list2StringComma(array2String(types));
				}
				//验证日期
				cell = row.getCell((short)7);
				String date = cell.getRichStringCellValue().toString();
				String regex = "\\d{4}.\\d{2}.\\d{2}-\\d{4}.\\d{2}.\\d{2}";
				Pattern p=Pattern.compile(regex); 
				Matcher m=p.matcher(date);
				if(!m.find()){
					flag = false;
					rowErrorMsg += "在第"+(i+1)+"行，计划验收日期格式有误（例2010.07.12-2010.07.16）。";
				}
			}
			if(StringUtils.isNotBlank(rowErrorMsg)){
				rowErrorMsg = "在第"+(i+1)+"行，光缆编号为"+excelUtil.getCellValue(row, 1)+"的光缆导入出错：" + rowErrorMsg;
				errorMsg.add(rowErrorMsg);
			}
			
			if(flag){
				ApplyCable cable = new ApplyCable();
				cable.setIssueNumber(excelUtil.getCellValue(row, 1));
				cable.setCableLevel(getLabel(types, excelUtil.getCellValue(row, 2)));
				cable.setFibercoreNo(excelUtil.getCellValue(row, 3));
				cable.setTrunk(excelUtil.getCellValue(row, 4));
				cable.setCableNo(excelUtil.getCellValue(row, 5));
				cable.setCableLength(excelUtil.getCellValue(row, 6));
				String [] appleTime = excelUtil.getCellValue(row,7).split("-");
				cable.setPlanAcceptanceTime(DateUtil.Str2UtilDate(appleTime[0],"yyyy.MM.dd"));
				cable.setPlanAcceptanceTime2(DateUtil.Str2UtilDate(appleTime[1],"yyyy.MM.dd"));
				cable.setBuilder(excelUtil.getCellValue(row, 8));
				cable.setBuilderPhone(excelUtil.getCellValue(row, 9));
				cable.setPrcpm(excelUtil.getCellValue(row, 10));
				cable.setRemark(excelUtil.getCellValue(row, 11));
				//cable.setRemark(excelUtil.getCellValue(row, 13));
				apply.addCable(cable);
				logger.info("cable:"+cable.toString());
			}
		}
		String msg = "";
		if(!errorMsg.isEmpty()){
			msg = errorMsg.size()+"条未导入<br/>";
		}
		apply.setErrorMsg(msg + StringUtils.join(errorMsg.toArray(),"<br/>"));
		apply.clearPipe();//???
		return apply;
	}
	
	public Apply importPipe(Apply apply, InputStream in, String type) throws Exception{
		//增加格式验证
		ExcelUtil excelUtil = new ExcelUtil(in);
		excelUtil.activeSheet(0);//激活当前工作表
		
		HSSFSheet sheet = excelUtil.getCurrentSheet(); //获得工作表
		//总体错误信息
		List<String> errorMsg = new ArrayList<String>();
		int lastRow = sheet.getPhysicalNumberOfRows();
		
		//产权属性
		List<Dictionary> properties = dictService.getDictByAssortment("property_right",null);
		//管道属性
		List<Dictionary> types = dictService.getDictByAssortment("pipe_type",null);
		String temp = "";
		for(int i=2;i<lastRow;i++){
			//有效标志
			boolean flag = true;
			//行错误信息
			String rowErrorMsg = "";
			HSSFRow row = sheet.getRow(i);
		
			//验证此行每一项均不为空
			flag = excelUtil.validateIsNull(13, row);

			if(!flag){
				errorMsg.add("在第"+i+"行，存在单元格数据为空，请补充完全。");
			}
			if(flag){
//				//验证管道资产编号
//				HSSFCell cell = row.getCell((short)0);
//				String number = cell.getRichStringCellValue().toString();
//				if(StringUtils.isBlank(number))
//					continue;
//				
//				if(type.equals("1")){ //初验
//					if(!validatePipeNumber(apply, number)){
//						flag = false;
//						rowErrorMsg += "管道资产编号"+number+"已存在。";
//					}
//				}else{ //复验
//					String reinspectError = validateReinspectPipeNumber(number);
//					if(StringUtils.isNotBlank(reinspectError)){
//						flag = false;
//						rowErrorMsg += reinspectError;
//					}
//				}
				
				//验证管道属性
				HSSFCell cell = row.getCell((short)2);
				temp = excelUtil.getCellStringValue(cell);
				if(!array2String(types).contains(temp)){
					flag = false;
					rowErrorMsg += "在第"+(i+1)+"行，管道属性内容有误。管道属性包括："+com.cabletech.commons.util.StringUtils.list2StringComma(array2String(types));
				}
				
				//验证产权属性
				cell = row.getCell((short)5);
				temp = excelUtil.getCellStringValue(cell);
				if(!array2String(properties).contains(temp)){
					flag = false;
					rowErrorMsg += "在第"+(i+1)+"行，产权属性内容有误。产权属性包括："+com.cabletech.commons.util.StringUtils.list2StringComma(array2String(properties));
				}
				//验证管道长度
				cell = row.getCell((short)7);
				temp = excelUtil.getCellStringValue(cell);
				if(temp.length()>12){
					flag = false;
					rowErrorMsg += "在第"+(i+1)+"行，管道长度过长。"+temp +",长度："+temp.length();
				}
				//验证工程规模
				cell = row.getCell((short)8);
				temp = excelUtil.getCellStringValue(cell);
				if(temp.length()>12){
					flag = false;
					rowErrorMsg += "在第"+(i+1)+"行，工程规模过长。"+temp +",长度："+temp.length();
				}
				//验证日期
				cell = row.getCell((short)11);
		
				temp = excelUtil.getCellStringValue(cell).toString();
				String regex = "\\d{4}/\\d{2}/\\d{2}";
				Pattern p=Pattern.compile(regex); 
				Matcher m=p.matcher(temp);
				if(!m.find()){
					flag = false;
					rowErrorMsg += "在第"+(i+1)+"行，计划验收日期格式有误,请检查日期是否为日期格式，不可以为自定义日期格式或者采用（格式如：2010/7/12）。";
				}
			}
			if(StringUtils.isNotBlank(rowErrorMsg)){
				rowErrorMsg = "在第"+(i+1)+"行，编号为"+excelUtil.getCellValue(row, 0)+"的管道未能导入，原因：" + rowErrorMsg;
				errorMsg.add(rowErrorMsg);
			}
			
			if(flag){
				ApplyPipe pipe = new ApplyPipe();
				pipe.setProjectName(excelUtil.getCellValue(row, 1));//工程项目名称
				pipe.setPipeType(getLabel(types, excelUtil.getCellValue(row, 2)));//管道属性
				pipe.setPipeAddress(excelUtil.getCellValue(row, 3));//管道地点
				pipe.setPipeRoute(excelUtil.getCellValue(row, 4));//详细路由
				pipe.setPipeProperty(getLabel(properties, excelUtil.getCellValue(row, 5)));//产权属性
				pipe.setWorkingDrawing(excelUtil.getCellValue(row, 6));//竣工图纸
				
				pipe.setPipeLength0(excelUtil.getCellValue(row, 7));//管道长度（沟公里数）
				pipe.setPipeLength1(excelUtil.getCellValue(row, 8));//工程规模（孔公里数）
//				pipe.setMoveScale0(excelUtil.getCellValue(row, 8));//移动管道长度
//				pipe.setMoveScale1(excelUtil.getCellValue(row, 9));//移动工程规模
				
				pipe.setBuilder(excelUtil.getCellValue(row, 9));//施工单位
				pipe.setBuilderPhone(excelUtil.getCellValue(row, 10));//施工单位电话
				pipe.setPlanAcceptanceTime(DateUtil.Str2UtilDate(excelUtil.getCellValue(row, 11),"yyyy/MM/dd"));//验收时间
				pipe.setPcpm(excelUtil.getCellValue(row, 12));//管道中心项目经理
				pipe.setRemark(excelUtil.getCellValue(row, 13));//备注
				pipe.setRemark2(excelUtil.getCellValue(row,14));
				pipe.setRemark3(excelUtil.getCellValue(row,15));
				apply.addPipe(pipe);
			}
		}
		String msg = "";
		if(!errorMsg.isEmpty()){
			msg = errorMsg.size()+"条未导入<br/>";
		}
		apply.setErrorMsg(msg + StringUtils.join(errorMsg.toArray(),"<br/>"));
		apply.clearCable();
		return apply;
	}
	
	/**
	 * 将Dictionary数组中Dictionary对象的lable属性转换为String类型的数组。
	 * @param dicts
	 * @return
	 */
	public List<String> array2String(List<Dictionary> dicts){
		List<String> lables = new ArrayList<String>();
		for(Dictionary d : dicts){
			lables.add(d.getLable());
		}
		return lables;
	}
	/**
	 * 从dictionary数组中查找lable对应的id
	 * @param list
	 * @param str
	 * @return
	 */
	public String getLabel(List<Dictionary> list, String str){
		List<String> id = new ArrayList<String>();
		List<String> label = Arrays.asList(str.split(","));
		for(Dictionary d : list){
			if(label.contains(d.getLable())){
				id.add(d.getCode());
			}
		}
		
		return StringUtils.join(id.toArray(),",");
	}
	/**
	 * 验证光缆编号
	 * @param apply
	 * @param cableNumber
	 * @return
	 */
	public boolean validateCableNumber(Apply apply, String cableNumber){
		//验证session里有没有重复光缆编号
		Set<ApplyCable> set = apply.getCables();
		for(ApplyCable cable : set){
			if(cable.getCableNo().equals(cableNumber)){
				return false;
			}
		}
		//验证光缆表里有没有重复的光缆编号
		if(repeaterSectionDao.hasDuplicateTrunk(cableNumber)){
			return false;
		}
		//验证申请表里有没有重复的光缆编号
		if(applyCableDao.hasDuplicateCableNo(cableNumber)){
			return false;
		}
		return true;
	}
	
	public boolean validateCableNumber(String cableNumber, String cableId){
		if(repeaterSectionDao.hasDuplicateTrunk(cableNumber, cableId)){
			return false;
		}
		return true;
	}
	
	public String validateReinspectCableNumber(String cableNumber){
		String msg = "";
		RepeaterSection trunk = repeaterSectionDao.findUniqueByProperty("segmentid", cableNumber);
		if(trunk == null){
			msg = "光缆未进行过验收";
		}else{
			if(StringUtils.isNotBlank(trunk.getIsCheckOut()) && trunk.getIsCheckOut().equals(AcceptanceConstant.PASS)){
				msg = "光缆验收已通过";
			}
		}
		return msg;
	}
	
	public String validateReinspectPipeNumber(String pipeNumber){
		String msg = "";
		Pipe pipe = pipeDao.findUniqueByProperty("assetno", pipeNumber);
		if(pipe == null){
			msg = "管道未进行过验收";
		}else{
			if(StringUtils.isNotBlank(pipe.getIsCheckOut()) && pipe.getIsCheckOut().equals(AcceptanceConstant.PASS)){
				msg = "管道验收已通过";
			}
		}
		return msg;
	}
	

	
	
	
	private void initResponse(HttpServletResponse response, String fileName)
		throws UnsupportedEncodingException {
		response.reset();
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "iso-8859-1"));
	}
	
	
	
	
	
	/**
	 * 这个方法是将属性字典的key-value之间进行转换，就这么简单，哦，只能是key到value的转换，反过来可是不可以的哦！！
	 * 对了这个方法照以前的方法少相比只做一次的数据库查询哦。那个家伙写的需要sum（导出记录数*（laying中逗号的个数+1））
	 * @param dicts  属性的字典数据
	 * @param laying 使用逗号分割的字符串，字符串为dict的code
	 * @return
	 */
	public String getLayingMethodName(List<Dictionary> dicts, String laying){
		if(StringUtils.isBlank(laying)){
			return "";
		}
		List<String> ids = Arrays.asList(laying.split(","));
		List<String> names = new ArrayList<String>();
		for(Dictionary d : dicts){
			if(ids.contains(d.getCode())){
				names.add(d.getLable());
			}
		}
		return StringUtils.join(names.iterator(), ",");
	}
	/**
	 * 导出验收光缆信息
	 * @param apply 验收光缆信息，及验收的光缆
	 * @param response
	 */
	public void exportCable(Apply apply, HttpServletResponse response){
		try{
			initResponse(response, apply.getName()+".xls");
			OutputStream out = response.getOutputStream();
			
			String url = "ExcelTemplate\\acceptance\\cable.xlt";
			String path = ConfigPathUtil.getClassPathConfigFile(url);
			ExcelUtil excelUtil = new ExcelUtil(path);
			HSSFWorkbook workbook = excelUtil.getWorkBook();
			excelUtil.activeSheet(0);//激活当前工作表
			HSSFSheet sheet = excelUtil.getCurrentSheet(); //获得工作表
			//POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(path));
			//HSSFWorkbook workbook = new HSSFWorkbook(fs);
			//HSSFSheet sheet = workbook.getSheet("DataSheet");
			HSSFCellStyle style = workbook.createCellStyle();
			List<Dictionary> layingmethod = dictService.getDictByAssortment("layingmethod",null);
			List<Dictionary> cabletype = dictService.getDictByAssortment("cabletype",null);
			Set<ApplyCable> cables = apply.getCables();
			
			int index = 0;
			for(ApplyCable cable : cables){
				HSSFRow row = sheet.createRow(index+2);
				excelUtil.setCellStringValue(0, cable.getSid(), row, style);
				excelUtil.setCellStringValue(1, cable.getCableNo(), row, style);
				excelUtil.setCellStringValue(2, cable.getIssueNumber(), row, style);
				excelUtil.setCellStringValue(3, cable.getA(), row, style);
				excelUtil.setCellStringValue(4, cable.getZ(), row, style);
				excelUtil.setCellStringValue(5, cable.getTrunk(), row, style);
				excelUtil.setCellStringValue(6, getLayingMethodName(layingmethod, cable.getLayingMethod()), row, style);
				excelUtil.setCellStringValue(7, cable.getFibercoreNo(), row, style);
				excelUtil.setCellStringValue(8, getLayingMethodName(cabletype, cable.getCableLevel()), row, style);
				excelUtil.setCellStringValue(9, cable.getCableLength(), row, style);
				excelUtil.setCellStringValue(10, cable.getBuilder(), row, style);
				excelUtil.setCellStringValue(11, cable.getBuilderPhone(), row, style);
				excelUtil.setCellStringValue(12, cable.getPrcpm(), row, style);
				excelUtil.setCellStringValue(13, cable.getRemark(), row, style);
				excelUtil.setCellStringValue(14, DateUtil.DateToString(cable.getPlanAcceptanceTime()), row, style);
				index++;
			}
			
			workbook.write(out);
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 导出需要验收的管道信息
	 * @param apply 管道申请信息
	 * @param response
	 */
	public void exportPipe(Apply apply, HttpServletResponse response){
		try{
			initResponse(response, apply.getName()+".xls");
			OutputStream out = response.getOutputStream();
			
			String url = "ExcelTemplate\\acceptance\\pipe.xlt";
			String path = ConfigPathUtil.getClassPathConfigFile(url);
			ExcelUtil excelUtil = new ExcelUtil(path);
			HSSFWorkbook workbook = excelUtil.getWorkBook();
			excelUtil.activeSheet(0);//激活当前工作表
			HSSFSheet sheet = excelUtil.getCurrentSheet(); //获得工作表
			HSSFCellStyle style = workbook.createCellStyle();
			List<Dictionary> properties = dictService.getDictByAssortment("property_right",null);
			List<Dictionary> types = dictService.getDictByAssortment("pipe_type",null);
			Set<ApplyPipe> pipes = apply.getPipes();
			
			int index = 0;
			for(ApplyPipe pipe : pipes){
				HSSFRow row = sheet.createRow(index+2);
				excelUtil.setCellStringValue(0, index+"", row, style);
				excelUtil.setCellStringValue(1, pipe.getProjectName(), row, style);
				excelUtil.setCellStringValue(2, pipe.getPipeAddress(), row, style);
				excelUtil.setCellStringValue(3, pipe.getPipeRoute(), row, style);
				excelUtil.setCellStringValue(4, getLayingMethodName(properties, pipe.getPipeProperty()), row, style);
				excelUtil.setCellStringValue(5, getLayingMethodName(types, pipe.getPipeType()), row, style);
				excelUtil.setCellStringValue(6, pipe.getPipeLength0(), row, style);
				excelUtil.setCellStringValue(7, pipe.getPipeLength1(), row, style);
				excelUtil.setCellStringValue(8, pipe.getMoveScale0(), row, style);
				excelUtil.setCellStringValue(9, pipe.getMoveScale1(), row, style);
				excelUtil.setCellStringValue(10, pipe.getWorkingDrawing(), row, style);
				excelUtil.setCellStringValue(11, pipe.getBuilder(), row, style);
				excelUtil.setCellStringValue(12, pipe.getBuilderPhone(), row, style);
				excelUtil.setCellStringValue(13, pipe.getPcpm(), row, style);
				excelUtil.setCellStringValue(14, pipe.getMaintenance(), row, style);
				excelUtil.setCellStringValue(15, pipe.getRemark(), row, style);
				excelUtil.setCellStringValue(16, DateUtil.DateToString(pipe.getPlanAcceptanceTime()), row, style);
				index++;
			}
			
			workbook.write(out);
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 这段代码导出的是光缆的信息，但不知到具体导出的内容，以及那里用到了
	 * @param trunks
	 * @param response
	 */
	public void export(List<RepeaterSection> trunks, HttpServletResponse response){
		try{
			initResponse(response, "光缆交维表.xls");
			//initResponse(response, apply.getName()+".xls");
			OutputStream out = response.getOutputStream();
			
			String url = "ExcelTemplate\\acceptance\\result.xlt";
			String path = ConfigPathUtil.getClassPathConfigFile(url);
			ExcelUtil excelUtil = new ExcelUtil(path);
			HSSFWorkbook workbook = excelUtil.getWorkBook();
			excelUtil.activeSheet(0);//激活当前工作表
			HSSFSheet sheet = excelUtil.getCurrentSheet(); //获得工作表
			HSSFCellStyle style = workbook.createCellStyle();
			HSSFFont font = workbook.createFont();
			font.setFontName("宋体");
			font.setFontHeightInPoints((short)10);
			style.setFont(font);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
			List<Dictionary> cabletype = dictService.getDictByAssortment("cabletype",null);
			List<Dictionary> layingmethod = dictService.getDictByAssortment("layingmethod",null);
			int index = 0;
			for(RepeaterSection trunk : trunks){
				String kid = trunk.getKid();
				PayCable payCable = payCableDao.getLastPayCable(kid);
				if(payCable != null){
					List<CableResult> cableResults = cableResultDao.getCableResults(payCable.getCableId());
					
					if(!cableResults.isEmpty()){
						CableResult lastCableResult = cableResults.get(cableResults.size()-1);
						
						HSSFRow row = sheet.createRow(index+2);
						excelUtil.setCellStringValue(0, String.valueOf(index+1), row, style); //序号
						excelUtil.setCellStringValue(1, "", row, style); //批次
						
						//判断目前状态
						String result = lastCableResult.getResult();
						String state = "";
						if(result.equals(AcceptanceConstant.PASSED)){
							state = "验收通过";
							String isCheckOut = trunk.getIsCheckOut();
							if(isCheckOut.equals(AcceptanceConstant.PASS)){
								state += "已交维";
							}else{
								state += "但未交维";
							}
						}else{
							state = "验收未通过";
						}
						excelUtil.setCellStringValue(2, state, row, style);  //目前状态
						
						excelUtil.setCellStringValue(3, String.valueOf(cableResults.size())+"次验收", row, style);  //状态说明
						
						//判断交维方式
						String havePicture = trunk.getHavePicture();
						String type = "";
						if(StringUtils.isNotBlank(havePicture)){
							if(havePicture.equals("y")){
								type = "图纸交维";
							}else{
								type = "验收交维";
							}
						}
						excelUtil.setCellStringValue(4, type, row, style);  //交维方式
						excelUtil.setCellStringValue(5, DateUtil.DateToString(payCable.getPassedTime()), row, style);  //验收通过时间
						excelUtil.setCellStringValue(6, "", row, style);    //接受资料时间
						excelUtil.setCellStringValue(7, trunk.getProjectName(), row, style);       //工程名称
						excelUtil.setCellStringValue(8, getLayingMethodName(cabletype, trunk.getCableLevel()), row, style);  //光缆级别
						excelUtil.setCellStringValue(9, trunk.getSegmentname(), row, style);       //验收段落
						excelUtil.setCellStringValue(10, trunk.getSegmentid(), row, style);        //光缆编号
						excelUtil.setCellStringValue(11, trunk.getGrossLength().toString(), row, style);     //提验长度
						
						Float length = trunk.getGrossLength() -  trunk.getReservedLength();
						excelUtil.setCellStringValue(12, length.toString(), row, style);            //验收长度
						
						excelUtil.setCellStringValue(13, getLayingMethodName(layingmethod, trunk.getLaytype()), row, style);        //资源
						excelUtil.setCellStringValue(14, "", row, style);        //是否TD
						excelUtil.setCellStringValue(15, payCable.getWorkUnit(), row, style);        //施工单位
						
						ApplyCable cable = applyTaskManager.getApplyCable4ResId(trunk.getKid());
						excelUtil.setCellStringValue(16, cable.getBuilderPhone(), row, style);       //施工单位联系方式
						excelUtil.setCellStringValue(17, cable.getPrcpm(), row, style);              //工程中心项目经理
						
						String deptName = dao.getDeptName(trunk.getMaintenanceId());
						excelUtil.setCellStringValue(18, deptName, row, style);              //维护单位
						
						int j = 18;
						for(int k = 0 ; k < cableResults.size() ; k++){
							CableResult cableResult = cableResults.get(k);
							
							int begin = j + k * 13;
							excelUtil.setCellStringValue(begin+1, DateUtil.DateToString(cableResult.getPlanDate()), row, style);  //计划验收日期
							excelUtil.setCellStringValue(begin+2, DateUtil.DateToString(cableResult.getFactDate()), row, style);  //实际验收日期
							
							String rt = cableResult.getResult();
							String st = "";
							if(rt.equals(AcceptanceConstant.PASSED)){
								st = "验收通过";
								String isCheckOut = trunk.getIsCheckOut();
								if(isCheckOut.equals(AcceptanceConstant.PASS)){
									st += "已交维";
								}else{
									st += "但未交维";
								}
							}else{
								st = "验收未通过";
							}
							
							excelUtil.setCellStringValue(begin+3, st, row, style);  //验收结果
							excelUtil.setCellStringValue(begin+4, cableResult.getRemark(), row, style);   //验收备注
							excelUtil.setCellStringValue(begin+5, "", row, style);  //验收照片
							excelUtil.setCellStringValue(begin+6, getResult(cableResult.getIsEligible0()), row, style);  //资料不合格
							excelUtil.setCellStringValue(begin+7, getResult(cableResult.getIsEligible1()), row, style);  //光缆端别不正确
							excelUtil.setCellStringValue(begin+8, getResult(cableResult.getIsEligible2()), row, style);  //室内工艺不合格
							excelUtil.setCellStringValue(begin+9, getResult(cableResult.getIsEligible3()), row, style);  //室外工艺不合格
							excelUtil.setCellStringValue(begin+10, getResult(cableResult.getIsEligible4()), row, style); //路由状况不合格
							excelUtil.setCellStringValue(begin+11, getResult(cableResult.getIsEligible5()), row, style); //测试情况不合格
							excelUtil.setCellStringValue(begin+12, getResult(cableResult.getIsEligible6()), row, style); //加强芯未接地
							excelUtil.setCellStringValue(begin+13, "", row, style);                                      //外护套未接地
						}
						
						index++;
					}
				}
			}
			
			workbook.write(out);
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public String getResult(String result){
		String rt = "";
		if(StringUtils.isNotBlank(result)){
			if(result.equals("1")){
				rt = "是";
			}
		}
		return rt;
	}
	
	@Override
	protected HibernateDao<Apply, String> getEntityDao() {
		return dao;
	}
}
