package com.cabletech.sparepartmanage.services;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;

import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.config.ReportConfig;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.generatorID.GeneratorID;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.sparepartmanage.beans.SparepartAuditingBean;
import com.cabletech.sparepartmanage.dao.SparepartApplyDAOImpl;
import com.cabletech.sparepartmanage.domainobjects.SparepartApplyF;
import com.cabletech.sparepartmanage.domainobjects.SparepartApplyS;
import com.cabletech.sparepartmanage.template.ApplyTemplate;

/**
 * SparepartStorageApplyBO ���������ҵ������������������ӡ��༭��ɾ�����鿴�Լ���ѯ
 * 
 * ע�⣺�ڱ�������û�����ͨ��ʱ�����б��������޸ģ�����
 */
public class SparepartApplyBO extends BaseBisinessObject {
	private static Logger logger = Logger.getLogger(SparepartApplyBO.class.getName());
	private static String CONTENT_TYPE = "application/vnd.ms-excel";
	private SparepartApplyDAOImpl dao = new SparepartApplyDAOImpl();
	private GeneratorID generatorID = new OracleIDImpl();


	/**
	 * ��ѯͬһ�ͺŵĴ�ά���ù��ı������к�
	 * @param id
	 * @param deptid
	 * @return
	 */
	public List findSerialNumsByBaseId(String id,String deptid,String state,String position){
		List list = new ArrayList();		
		String sql = "select s.serial_number from spare_part_storage s " +
		"where s.spare_part_id='"+id+"' and s.depart_id='"+deptid+"' and s.spare_part_state='"+state+"'" +
				" and s.storage_position='"+position+"' order by s.serial_number";
		QueryUtil util;
		logger.info("spareAppleyBO->findSerialNumsByBaseId:"+sql);
		try {
			util = new QueryUtil();
			list = util.queryBeans(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public List getSerialNmuByPositon(String position,String baseid,List applySs){
		return dao.getSerialNmuByPositon(position,baseid,applySs);
	}

	/**
	 * ��ѯͬһ�ͺŵĴ�ά���ù��ı������к�
	 * @param id
	 * @param deptid
	 * @return
	 */
	public List findSerialNumsByBaseId(String id,String deptid,String state){
		List list = new ArrayList();		
		String sql = "select s.serial_number from spare_part_storage s " +
		"where s.spare_part_id='"+id+"' and s.depart_id='"+deptid+"' and s.spare_part_state='"+state+"' order by s.serial_number";
		QueryUtil util;
		logger.info("spareAppleyBOSql:"+sql);
		try {
			util = new QueryUtil();
			list = util.queryBeans(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * ������ʹ��λ��
	 * @return
	 */
	public List getusedPositions(String baseid){
		List list = new ArrayList();
		String sql="select distinct s.storage_position from spare_part_storage s where s.spare_part_state='03' and s.spare_part_id='"+baseid+"'";
		QueryUtil util;
		logger.info("spareAppleyBOSql:"+sql);
		try {
			util = new QueryUtil();
			list = util.queryBeans(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
		
	/**
	 *�޸�ʱ��ѯ����ʹ��λ�� 
	 * @param baseid
	 * @return
	 */
	
	public List getusedPositionsByEdit(String baseid,String tfid){
		List list = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct s.storage_position from spare_part_storage s");
		sql.append(" where s.spare_part_state = '07' ");
		sql.append(" and s.serial_number in (select applys.used_serial_number from spare_part_apply_s applys where applys.ftid='"+tfid+"') ");
		sql.append(" or s.spare_part_state='03' and s.spare_part_id='"+baseid+"'");
		sql.append("");
		QueryUtil util;
		logger.info("spareAppleyBO->getusedPositionsByEdit:"+sql.toString());
		try {
			util = new QueryUtil();
			list = util.queryBeans(sql.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * ���ݲ��ŵõ�Ѳ����
	 * @param deptid
	 * @return
	 */
	public List patrolgroupsByDeptID(String deptid){
		List list = new ArrayList();
		String sql = "select patrolid,patrolname from patrolmaninfo where parentid='"+deptid+"' and state IS NULL ";
		QueryUtil util;
		logger.info("spareAppleyBOSql:"+sql);
		try {
			util = new QueryUtil();
			list = util.queryBeans(sql);
		} catch (Exception e) {
			System.out.println("patrolgroupsByDeptID�����쳣��"+e.getMessage());
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 *�ڸ���ʱ�Ĳ�ѯ 
	 * @param deptid
	 * @return
	 */
	public List patrolgroupsByDeptIDChange(String deptid,String baseid){
		List list = new ArrayList();
		String sql = "select patrolid,patrolname from patrolmaninfo where parentid='"+deptid+"' and state IS NULL ";
		QueryUtil util;
		logger.info("patrolgroupsByDeptIDChange:"+sql);
		try {
			util = new QueryUtil();
			list = util.queryBeans(sql);
			System.out.println("befer:"+list.size());
			List is = new ArrayList();
			if(list !=null && list.size()>0){
				for(int i = 0 ;i<list.size();i++){
					BasicDynaBean bean = (BasicDynaBean) list.get(i);
					String patrolgroupId = (String) bean.get("patrolid");
					List options = this.getPositonsByPatrolgroupIdByChange(patrolgroupId,baseid);
					if(options ==null || options.size()==0){
						is.add(bean);
					}
				}
			}
			System.out.println("��Ҫɾ���Ŀյģ�"+is.size());
			if(is !=null && is.size()>0){
				for(int i = 0 ;i<is.size();i++){
					BasicDynaBean m = (BasicDynaBean) is.get(i);
					list.remove(m);
				}
			}
		} catch (Exception e) {
			System.out.println("patrolgroupsByDeptIDChange�����쳣��"+e.getMessage());
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * ����Ѳ����id�õ����븸����Ϣ
	 */
	public List getPositonsByPatrolgroupIdByChange(String patrolgroupId,String baseid){
		return dao.getPositonsByPatrolgroupIdByChange(patrolgroupId,baseid);
	}
	

	/**
	 * ���±���״̬Ϊ�ѱ�����
	 */
	public void updateSparepartState1(String[] seriNums,String position){
		dao.updateSparepartState(seriNums,position);		
	}
	
	public void updateSparepartState(String[] seriNums){
		dao.updateSparepartState(seriNums);		
	}


	/**
	 * 	�õ����е�ȡ��λ�ð����к�
	 */
	public List getSpareInfosBySerialNums(String[] serialNums){
		return dao.getSpareInfosBySerialNums(serialNums);
	}


	public void updateUsedSparepartState(String[] serialNumUses){
	/*	String state="";
		if(replaceType.equals("01")){//�˻�
			state=SparepartConstant.MOBILE_WAIT_USE;
		}else if(replaceType.equals("02")){//����
			state=SparepartConstant.IS_DISCARDED;
		}else if(replaceType.equals("03")){//����
			state=SparepartConstant.IS_MENDING;
		}*/
		dao.updateUsedSparepartState(serialNumUses);//�޸�Ϊ�ѱ��滻��״̬
	}

	public void updatSparepartState(String serialNumber,String state){
		dao.updatSparepartState(serialNumber, state);
	}
	
	/**
	 * ���±���״̬Ϊԭʼ״̬
	 */

	public void updatSparepartState(List serials,String state){
		dao.updatSparepartState(serials, state);
	}
	
	public void addSparePartApplyS(List applyS){
		for(int i = 0;i<applyS.size();i++){
			SparepartApplyS applys = (SparepartApplyS) applyS.get(i);
			applys.setTid(generatorID.getSeq("spart_part_apply_s", 20));
			dao.addSparePartApplyS(applys);
		}		
	}

	public String addSparePartApplyF(SparepartApplyF applyF){
		String id = generatorID.getSeq("spart_part_apply_f", 20);
		applyF.setTid(id);
		dao.addSparePartApplyF(applyF);
		return id;
	}
	
	/**
	 * �޸��������
	 * @param response
	 * @param fileName
	 * @throws UnsupportedEncodingException
	 */
	
	public void updateSparePartApplyF(SparepartApplyF applyF){
		dao.updateSparePartApplyF(applyF);	
	}
	
	/**
	 * �޸��ӱ�
	 * @param applyS
	 */
	public void updateSparePartApplyS(SparepartApplyS applyS){
		dao.updateSparePartApplyS(applyS);	
	}


	private void initResponse(HttpServletResponse response, String fileName)
	throws UnsupportedEncodingException {
		response.reset();
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(fileName.getBytes(), "iso-8859-1"));
	}

	/**
	 * ���ݲ�ѯ���ı��������б���е�������
	 * 
	 * @param list
	 *            List
	 * @param response
	 *            HttpServletResponse
	 * @throws IOException
	 */
	public void exportStorage(List list, HttpServletResponse response)
	throws IOException {
		initResponse(response, "���������б�.xls");
		OutputStream out = response.getOutputStream();
		String urlPath = ReportConfig.getInstance().getUrlPath( "report.sparepartapply");
		ApplyTemplate template = new ApplyTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.doExport(list, excelstyle);
		template.write(out);
	}

	/**
	 * ��ʾ��ѯ�ı���������Ϣ���б�
	 * @return
	 * @throws Exception
	 */
	public List doQueryforApply(String condition) throws Exception {
		List list = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb.append("select u.username username,base.product_factory,base.spare_part_name,to_char(appf.apply_date,'yyyy-mm-dd hh24:mi:ss') apply_date, ");
		sb.append("appf.apply_person,appf.auditing_state,apps.serial_number,appf.apply_use_position, ");
		sb.append("appf.use_mode,appf.replace_type,apps.used_serial_number,apps.tid");
		sb.append(" from spare_part_baseinfo base,spare_part_storage sto,spare_part_apply_f appf, spare_part_apply_s apps,userinfo u ");
		sb.append(" where apps.ftid = appf.tid and apps.serial_number=sto.serial_number and sto.spare_part_id=base.tid and u.userid=appf.apply_person");
		sb.append(condition);
		QueryUtil util = new QueryUtil();
		System.out.println("doQueryforApply:" + sb.toString());
		list = util.queryBeans(sb.toString());
		return list;
	}

	/**
	 * ���ݱ�������任���뱸������
	 * 
	 * @param applyId
	 *            String
	 * @return SparepartApply
	 * @throws Exception
	 */
	public SparepartApplyS getApplyS(String applyId) throws Exception {
		return dao.loadSparepartApplyS(applyId);
	}

	public SparepartApplyF getApplyF(String applyId) throws Exception {
		return dao.loadSparepartApplyF(applyId);
	}
	
	public void deleteApplyF(SparepartApplyF applyF){
		dao.deleteApplyF(applyF);
	}
	
	public void deleteApplyS(SparepartApplyS applyS){
		dao.deleteApplyS(applyS);
	}
	
	public void deleteApplyS(String id){
		dao.deleteApplyS(id);
	}


	public String getPatrolgroupNameById(String groupId){
		return dao.getPatrolgroupNameById(groupId);
	}
	/**
	 * �����������id�õ��ӱ����м�¼
	 * @param applyfid
	 * @return
	 */
	public List getApplySs(String applyfid){
		return dao.loadSparepartApplysByFId(applyfid);
	}
	
	public String getUserNameById(String userid){
		return dao.getUserNameById(userid);
	}

	/**
	 * ��ʾ����˵��б�
	 * @return
	 * @throws Exception
	 */
	public List listWaitAuditingApplyForAu() throws Exception {
		List list = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb.append("select * from spare_part_baseinfo s right join ");
		sb.append("(select appf.spare_part_id,appf.tid appfid, to_char(appf.apply_date,'yyyy-mm-dd hh24:mi:ss') apply_date, ");
		sb.append("u.username,appf.apply_person,appf.auditing_state, count(*) serial_number");
		sb.append(" from spare_part_apply_s apps, spare_part_apply_f appf,userinfo u ");
		sb.append("where apps.ftid =appf.tid and appf.auditing_state='00' and u.userid = appf.apply_person ");
		sb.append(" group by appf.spare_part_id ,u.username,apply_person,auditing_state,apply_date,appf.tid) m ");
		sb.append("on s.tid = m.spare_part_id");
	
		QueryUtil util = new QueryUtil();
		logger.info("��ʾ����˵��б�sql:"+sb.toString());
		list = util.queryBeans(sb.toString());
		return list;
	}


	/**
	    * ��ʾ��ѯ�ı��������Ϣ���б�
	    * @return
	    * @throws Exception
	    */
	public List doQueryforAu(String condition) throws Exception {
		List list = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb.append("select * from spare_part_baseinfo s right join ");
		sb.append("(select appf.spare_part_id,appf.tid appfid, to_char(appf.apply_date,'yyyy-mm-dd hh24:mi:ss') apply_date, ");
		sb.append("u.username,appf.apply_person,appf.auditing_state, count(*) serial_number");
		sb.append(" from spare_part_apply_s apps, spare_part_apply_f appf,userinfo u ");
		sb.append("where apps.ftid =appf.tid  and u.userid = appf.apply_person ");
		sb.append(condition);
		sb.append(" group by appf.spare_part_id ,u.username,apply_person,auditing_state,apply_date,appf.tid) m ");
		sb.append("on s.tid = m.spare_part_id order by apply_date desc");
		QueryUtil util = new QueryUtil();
		System.out.println("doQueryforAu:" + sb.toString());
		list = util.queryBeans(sb.toString());
		return list;
	}
	
	public SparepartAuditingBean findAuditByApplyId(String applyId){
		List list = new ArrayList();
		//SparepartAuditing audit = new SparepartAuditing();
		SparepartAuditingBean bean = new SparepartAuditingBean();
		String sql = "select auditing_remark, decode(auditing_result,'01','���ͨ��','02','��˲�ͨ��','�����') as auditing_result " +
				"from spare_part_auditingapply where apply_id='"+applyId+"'";
		QueryUtil util;
		try {
			util = new QueryUtil();
			System.out.println("doQueryforAu:" +sql);
			list = util.queryBeans(sql);
			if(list !=null && list.size()>0){
				BasicDynaBean basicbean = (BasicDynaBean) list.get(0);
				String result = (String) basicbean.get("auditing_result");
				String remak = (String) basicbean.get("auditing_remark");
				bean.setAuditingRemark(remak);
				bean.setAuditingResult(result);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	/**
	 * ����Ѳ����id�õ����븸����Ϣ
	 */
	/*public List getPositonsByPatrolgroupId(String patrolgroupId){
		return dao.getPositonsByPatrolgroupId(patrolgroupId);
	}*/

}
