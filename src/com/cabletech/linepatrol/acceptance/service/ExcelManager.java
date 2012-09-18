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
	 * @param apply ����������Ϣ
	 * @param in   �ϴ��������ļ���
	 * @param type ָ�������
	 * @return
	 */
	public Apply importCable(Apply apply, InputStream in, String type) throws Exception{
		ExcelUtil excelUtil = new ExcelUtil(in);
		excelUtil.activeSheet(0);//���ǰ������
		HSSFSheet sheet = excelUtil.getCurrentSheet(); //��ù�����
		int lastRow = sheet.getPhysicalNumberOfRows();//lastRow��׼����ҪУ��

		List<String> errorMsg = new ArrayList<String>();//���������Ϣ
		
		List<Dictionary> lays = dictService.getDictByAssortment("layingmethod",null);//���跽ʽ
		List<Dictionary> types = dictService.getDictByAssortment("cabletype",null);//���¼���
		
		for(int i=2;i<lastRow;i++){
			//��Ч��־
			boolean flag = true;
			//�д�����Ϣ
			String rowErrorMsg = "";
			HSSFRow row = sheet.getRow(i);
			
			//��֤����ÿһ�����Ϊ��
			flag = excelUtil.validateIsNull(10, row);
			if(!flag){
				errorMsg.add("�ڵ�"+(i+1)+"�У����ڵ�Ԫ������Ϊ�գ��벹����ȫ��");
			}
			if(flag){
				//��֤���±��
				HSSFCell cell = row.getCell((short)5);
				String number = excelUtil.getCellStringValue(cell);
				if(StringUtils.isBlank(number))
					continue;
				
				if(type.equals("1")){ //����
					if(!validateCableNumber(apply, number)){
						flag = false;
						rowErrorMsg += "�ڵ�"+(i+1)+"�У����±��"+number+"�Ѵ��ڡ�";
					}
				}else{ //����
					String reinspectError = validateReinspectCableNumber(number);
					if(StringUtils.isNotBlank(reinspectError)){
						flag = false;
						rowErrorMsg += reinspectError;
					}
				}
				
				//��֤���¼���
				cell = row.getCell((short)2);
				String level = excelUtil.getCellStringValue(cell);
				if(!array2String(types).contains(level)){
					flag = false;
					rowErrorMsg += "�ڵ�"+(i+1)+"�У����¼����������󡣹��¼��������"+com.cabletech.commons.util.StringUtils.list2StringComma(array2String(types));
				}
				//��֤����
				cell = row.getCell((short)7);
				String date = cell.getRichStringCellValue().toString();
				String regex = "\\d{4}.\\d{2}.\\d{2}-\\d{4}.\\d{2}.\\d{2}";
				Pattern p=Pattern.compile(regex); 
				Matcher m=p.matcher(date);
				if(!m.find()){
					flag = false;
					rowErrorMsg += "�ڵ�"+(i+1)+"�У��ƻ��������ڸ�ʽ������2010.07.12-2010.07.16����";
				}
			}
			if(StringUtils.isNotBlank(rowErrorMsg)){
				rowErrorMsg = "�ڵ�"+(i+1)+"�У����±��Ϊ"+excelUtil.getCellValue(row, 1)+"�Ĺ��µ������" + rowErrorMsg;
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
			msg = errorMsg.size()+"��δ����<br/>";
		}
		apply.setErrorMsg(msg + StringUtils.join(errorMsg.toArray(),"<br/>"));
		apply.clearPipe();//???
		return apply;
	}
	
	public Apply importPipe(Apply apply, InputStream in, String type) throws Exception{
		//���Ӹ�ʽ��֤
		ExcelUtil excelUtil = new ExcelUtil(in);
		excelUtil.activeSheet(0);//���ǰ������
		
		HSSFSheet sheet = excelUtil.getCurrentSheet(); //��ù�����
		//���������Ϣ
		List<String> errorMsg = new ArrayList<String>();
		int lastRow = sheet.getPhysicalNumberOfRows();
		
		//��Ȩ����
		List<Dictionary> properties = dictService.getDictByAssortment("property_right",null);
		//�ܵ�����
		List<Dictionary> types = dictService.getDictByAssortment("pipe_type",null);
		String temp = "";
		for(int i=2;i<lastRow;i++){
			//��Ч��־
			boolean flag = true;
			//�д�����Ϣ
			String rowErrorMsg = "";
			HSSFRow row = sheet.getRow(i);
		
			//��֤����ÿһ�����Ϊ��
			flag = excelUtil.validateIsNull(13, row);

			if(!flag){
				errorMsg.add("�ڵ�"+i+"�У����ڵ�Ԫ������Ϊ�գ��벹����ȫ��");
			}
			if(flag){
//				//��֤�ܵ��ʲ����
//				HSSFCell cell = row.getCell((short)0);
//				String number = cell.getRichStringCellValue().toString();
//				if(StringUtils.isBlank(number))
//					continue;
//				
//				if(type.equals("1")){ //����
//					if(!validatePipeNumber(apply, number)){
//						flag = false;
//						rowErrorMsg += "�ܵ��ʲ����"+number+"�Ѵ��ڡ�";
//					}
//				}else{ //����
//					String reinspectError = validateReinspectPipeNumber(number);
//					if(StringUtils.isNotBlank(reinspectError)){
//						flag = false;
//						rowErrorMsg += reinspectError;
//					}
//				}
				
				//��֤�ܵ�����
				HSSFCell cell = row.getCell((short)2);
				temp = excelUtil.getCellStringValue(cell);
				if(!array2String(types).contains(temp)){
					flag = false;
					rowErrorMsg += "�ڵ�"+(i+1)+"�У��ܵ������������󡣹ܵ����԰�����"+com.cabletech.commons.util.StringUtils.list2StringComma(array2String(types));
				}
				
				//��֤��Ȩ����
				cell = row.getCell((short)5);
				temp = excelUtil.getCellStringValue(cell);
				if(!array2String(properties).contains(temp)){
					flag = false;
					rowErrorMsg += "�ڵ�"+(i+1)+"�У���Ȩ�����������󡣲�Ȩ���԰�����"+com.cabletech.commons.util.StringUtils.list2StringComma(array2String(properties));
				}
				//��֤�ܵ�����
				cell = row.getCell((short)7);
				temp = excelUtil.getCellStringValue(cell);
				if(temp.length()>12){
					flag = false;
					rowErrorMsg += "�ڵ�"+(i+1)+"�У��ܵ����ȹ�����"+temp +",���ȣ�"+temp.length();
				}
				//��֤���̹�ģ
				cell = row.getCell((short)8);
				temp = excelUtil.getCellStringValue(cell);
				if(temp.length()>12){
					flag = false;
					rowErrorMsg += "�ڵ�"+(i+1)+"�У����̹�ģ������"+temp +",���ȣ�"+temp.length();
				}
				//��֤����
				cell = row.getCell((short)11);
		
				temp = excelUtil.getCellStringValue(cell).toString();
				String regex = "\\d{4}/\\d{2}/\\d{2}";
				Pattern p=Pattern.compile(regex); 
				Matcher m=p.matcher(temp);
				if(!m.find()){
					flag = false;
					rowErrorMsg += "�ڵ�"+(i+1)+"�У��ƻ��������ڸ�ʽ����,���������Ƿ�Ϊ���ڸ�ʽ��������Ϊ�Զ������ڸ�ʽ���߲��ã���ʽ�磺2010/7/12����";
				}
			}
			if(StringUtils.isNotBlank(rowErrorMsg)){
				rowErrorMsg = "�ڵ�"+(i+1)+"�У����Ϊ"+excelUtil.getCellValue(row, 0)+"�Ĺܵ�δ�ܵ��룬ԭ��" + rowErrorMsg;
				errorMsg.add(rowErrorMsg);
			}
			
			if(flag){
				ApplyPipe pipe = new ApplyPipe();
				pipe.setProjectName(excelUtil.getCellValue(row, 1));//������Ŀ����
				pipe.setPipeType(getLabel(types, excelUtil.getCellValue(row, 2)));//�ܵ�����
				pipe.setPipeAddress(excelUtil.getCellValue(row, 3));//�ܵ��ص�
				pipe.setPipeRoute(excelUtil.getCellValue(row, 4));//��ϸ·��
				pipe.setPipeProperty(getLabel(properties, excelUtil.getCellValue(row, 5)));//��Ȩ����
				pipe.setWorkingDrawing(excelUtil.getCellValue(row, 6));//����ͼֽ
				
				pipe.setPipeLength0(excelUtil.getCellValue(row, 7));//�ܵ����ȣ�����������
				pipe.setPipeLength1(excelUtil.getCellValue(row, 8));//���̹�ģ���׹�������
//				pipe.setMoveScale0(excelUtil.getCellValue(row, 8));//�ƶ��ܵ�����
//				pipe.setMoveScale1(excelUtil.getCellValue(row, 9));//�ƶ����̹�ģ
				
				pipe.setBuilder(excelUtil.getCellValue(row, 9));//ʩ����λ
				pipe.setBuilderPhone(excelUtil.getCellValue(row, 10));//ʩ����λ�绰
				pipe.setPlanAcceptanceTime(DateUtil.Str2UtilDate(excelUtil.getCellValue(row, 11),"yyyy/MM/dd"));//����ʱ��
				pipe.setPcpm(excelUtil.getCellValue(row, 12));//�ܵ�������Ŀ����
				pipe.setRemark(excelUtil.getCellValue(row, 13));//��ע
				pipe.setRemark2(excelUtil.getCellValue(row,14));
				pipe.setRemark3(excelUtil.getCellValue(row,15));
				apply.addPipe(pipe);
			}
		}
		String msg = "";
		if(!errorMsg.isEmpty()){
			msg = errorMsg.size()+"��δ����<br/>";
		}
		apply.setErrorMsg(msg + StringUtils.join(errorMsg.toArray(),"<br/>"));
		apply.clearCable();
		return apply;
	}
	
	/**
	 * ��Dictionary������Dictionary�����lable����ת��ΪString���͵����顣
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
	 * ��dictionary�����в���lable��Ӧ��id
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
	 * ��֤���±��
	 * @param apply
	 * @param cableNumber
	 * @return
	 */
	public boolean validateCableNumber(Apply apply, String cableNumber){
		//��֤session����û���ظ����±��
		Set<ApplyCable> set = apply.getCables();
		for(ApplyCable cable : set){
			if(cable.getCableNo().equals(cableNumber)){
				return false;
			}
		}
		//��֤���±�����û���ظ��Ĺ��±��
		if(repeaterSectionDao.hasDuplicateTrunk(cableNumber)){
			return false;
		}
		//��֤���������û���ظ��Ĺ��±��
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
			msg = "����δ���й�����";
		}else{
			if(StringUtils.isNotBlank(trunk.getIsCheckOut()) && trunk.getIsCheckOut().equals(AcceptanceConstant.PASS)){
				msg = "����������ͨ��";
			}
		}
		return msg;
	}
	
	public String validateReinspectPipeNumber(String pipeNumber){
		String msg = "";
		Pipe pipe = pipeDao.findUniqueByProperty("assetno", pipeNumber);
		if(pipe == null){
			msg = "�ܵ�δ���й�����";
		}else{
			if(StringUtils.isNotBlank(pipe.getIsCheckOut()) && pipe.getIsCheckOut().equals(AcceptanceConstant.PASS)){
				msg = "�ܵ�������ͨ��";
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
	 * ��������ǽ������ֵ��key-value֮�����ת��������ô�򵥣�Ŷ��ֻ����key��value��ת�������������ǲ����Ե�Ŷ����
	 * ���������������ǰ�ķ��������ֻ��һ�ε����ݿ��ѯŶ���Ǹ��һ�д����Ҫsum��������¼��*��laying�ж��ŵĸ���+1����
	 * @param dicts  ���Ե��ֵ�����
	 * @param laying ʹ�ö��ŷָ���ַ������ַ���Ϊdict��code
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
	 * �������չ�����Ϣ
	 * @param apply ���չ�����Ϣ�������յĹ���
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
			excelUtil.activeSheet(0);//���ǰ������
			HSSFSheet sheet = excelUtil.getCurrentSheet(); //��ù�����
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
	 * ������Ҫ���յĹܵ���Ϣ
	 * @param apply �ܵ�������Ϣ
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
			excelUtil.activeSheet(0);//���ǰ������
			HSSFSheet sheet = excelUtil.getCurrentSheet(); //��ù�����
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
	 * ��δ��뵼�����ǹ��µ���Ϣ������֪�����嵼�������ݣ��Լ������õ���
	 * @param trunks
	 * @param response
	 */
	public void export(List<RepeaterSection> trunks, HttpServletResponse response){
		try{
			initResponse(response, "���½�ά��.xls");
			//initResponse(response, apply.getName()+".xls");
			OutputStream out = response.getOutputStream();
			
			String url = "ExcelTemplate\\acceptance\\result.xlt";
			String path = ConfigPathUtil.getClassPathConfigFile(url);
			ExcelUtil excelUtil = new ExcelUtil(path);
			HSSFWorkbook workbook = excelUtil.getWorkBook();
			excelUtil.activeSheet(0);//���ǰ������
			HSSFSheet sheet = excelUtil.getCurrentSheet(); //��ù�����
			HSSFCellStyle style = workbook.createCellStyle();
			HSSFFont font = workbook.createFont();
			font.setFontName("����");
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
						excelUtil.setCellStringValue(0, String.valueOf(index+1), row, style); //���
						excelUtil.setCellStringValue(1, "", row, style); //����
						
						//�ж�Ŀǰ״̬
						String result = lastCableResult.getResult();
						String state = "";
						if(result.equals(AcceptanceConstant.PASSED)){
							state = "����ͨ��";
							String isCheckOut = trunk.getIsCheckOut();
							if(isCheckOut.equals(AcceptanceConstant.PASS)){
								state += "�ѽ�ά";
							}else{
								state += "��δ��ά";
							}
						}else{
							state = "����δͨ��";
						}
						excelUtil.setCellStringValue(2, state, row, style);  //Ŀǰ״̬
						
						excelUtil.setCellStringValue(3, String.valueOf(cableResults.size())+"������", row, style);  //״̬˵��
						
						//�жϽ�ά��ʽ
						String havePicture = trunk.getHavePicture();
						String type = "";
						if(StringUtils.isNotBlank(havePicture)){
							if(havePicture.equals("y")){
								type = "ͼֽ��ά";
							}else{
								type = "���ս�ά";
							}
						}
						excelUtil.setCellStringValue(4, type, row, style);  //��ά��ʽ
						excelUtil.setCellStringValue(5, DateUtil.DateToString(payCable.getPassedTime()), row, style);  //����ͨ��ʱ��
						excelUtil.setCellStringValue(6, "", row, style);    //��������ʱ��
						excelUtil.setCellStringValue(7, trunk.getProjectName(), row, style);       //��������
						excelUtil.setCellStringValue(8, getLayingMethodName(cabletype, trunk.getCableLevel()), row, style);  //���¼���
						excelUtil.setCellStringValue(9, trunk.getSegmentname(), row, style);       //���ն���
						excelUtil.setCellStringValue(10, trunk.getSegmentid(), row, style);        //���±��
						excelUtil.setCellStringValue(11, trunk.getGrossLength().toString(), row, style);     //���鳤��
						
						Float length = trunk.getGrossLength() -  trunk.getReservedLength();
						excelUtil.setCellStringValue(12, length.toString(), row, style);            //���ճ���
						
						excelUtil.setCellStringValue(13, getLayingMethodName(layingmethod, trunk.getLaytype()), row, style);        //��Դ
						excelUtil.setCellStringValue(14, "", row, style);        //�Ƿ�TD
						excelUtil.setCellStringValue(15, payCable.getWorkUnit(), row, style);        //ʩ����λ
						
						ApplyCable cable = applyTaskManager.getApplyCable4ResId(trunk.getKid());
						excelUtil.setCellStringValue(16, cable.getBuilderPhone(), row, style);       //ʩ����λ��ϵ��ʽ
						excelUtil.setCellStringValue(17, cable.getPrcpm(), row, style);              //����������Ŀ����
						
						String deptName = dao.getDeptName(trunk.getMaintenanceId());
						excelUtil.setCellStringValue(18, deptName, row, style);              //ά����λ
						
						int j = 18;
						for(int k = 0 ; k < cableResults.size() ; k++){
							CableResult cableResult = cableResults.get(k);
							
							int begin = j + k * 13;
							excelUtil.setCellStringValue(begin+1, DateUtil.DateToString(cableResult.getPlanDate()), row, style);  //�ƻ���������
							excelUtil.setCellStringValue(begin+2, DateUtil.DateToString(cableResult.getFactDate()), row, style);  //ʵ����������
							
							String rt = cableResult.getResult();
							String st = "";
							if(rt.equals(AcceptanceConstant.PASSED)){
								st = "����ͨ��";
								String isCheckOut = trunk.getIsCheckOut();
								if(isCheckOut.equals(AcceptanceConstant.PASS)){
									st += "�ѽ�ά";
								}else{
									st += "��δ��ά";
								}
							}else{
								st = "����δͨ��";
							}
							
							excelUtil.setCellStringValue(begin+3, st, row, style);  //���ս��
							excelUtil.setCellStringValue(begin+4, cableResult.getRemark(), row, style);   //���ձ�ע
							excelUtil.setCellStringValue(begin+5, "", row, style);  //������Ƭ
							excelUtil.setCellStringValue(begin+6, getResult(cableResult.getIsEligible0()), row, style);  //���ϲ��ϸ�
							excelUtil.setCellStringValue(begin+7, getResult(cableResult.getIsEligible1()), row, style);  //���¶˱���ȷ
							excelUtil.setCellStringValue(begin+8, getResult(cableResult.getIsEligible2()), row, style);  //���ڹ��ղ��ϸ�
							excelUtil.setCellStringValue(begin+9, getResult(cableResult.getIsEligible3()), row, style);  //���⹤�ղ��ϸ�
							excelUtil.setCellStringValue(begin+10, getResult(cableResult.getIsEligible4()), row, style); //·��״�����ϸ�
							excelUtil.setCellStringValue(begin+11, getResult(cableResult.getIsEligible5()), row, style); //����������ϸ�
							excelUtil.setCellStringValue(begin+12, getResult(cableResult.getIsEligible6()), row, style); //��ǿоδ�ӵ�
							excelUtil.setCellStringValue(begin+13, "", row, style);                                      //�⻤��δ�ӵ�
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
				rt = "��";
			}
		}
		return rt;
	}
	
	@Override
	protected HibernateDao<Apply, String> getEntityDao() {
		return dao;
	}
}
