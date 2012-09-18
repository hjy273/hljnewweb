package com.cabletech.analysis.services;

import org.apache.log4j.Logger;

import com.cabletech.analysis.beans.HistoryWorkInfoConditionBean;
import com.cabletech.analysis.beans.HistoryWorkInfoCreateBOParam;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.config.UserType;

/**
 * ����strategyģʽ�е�context������,ͨ������������֯WorkInfoHistoryBaseBO����
 */
public abstract class WorkInfoHistoryContext {
	protected HistoryWorkInfoCreateBOParam theBoParam; //���ڽ��մ�Action���д���Ĳ���
	private Logger logger =Logger.getLogger(this.getClass().getName()); // ����logger�������;
	/**
	 * ����BO����
	 * @param boParam ���ڽ��մ�Action���д���Ĳ���,�Ǹ������б����ϣ�����smRangeID,givenDate����Ϣ
	 * @return BO����
	 */
	public  WorkInfoHistoryBaseBO createBO(HistoryWorkInfoCreateBOParam boParam) {
		this.theBoParam = boParam;
		if (theBoParam == null){
			logger.info("boParam is null");
		}else{
			logger.info(theBoParam.getUserInfo().getRegionID());
		}
		//���ݲ�������BO����
		WorkInfoHistoryBaseBO bo = createBOByUserAndRange(theBoParam);
		if (bo == null){
			logger.info("bo is null");
		}
		bo.setBoParam(theBoParam);
		return bo;
	}

	/**
	 * ����ʵ���������WorkInfoHistoryBaseBO���������
	 * @param boParam ���ڽ��մ�Action���д���Ĳ���,�Ǹ������б����ϣ�����smRangeID,givenDate����Ϣ
	 * @return WorkInfoHistoryBaseBO���������
	 */
	public WorkInfoHistoryBaseBO createBOByUserAndRange(HistoryWorkInfoCreateBOParam boParam) {
		WorkInfoHistoryBaseBO bo = null;
		String id = boParam.getSmRangeID(); //���ŵ�RangeID
		UserInfo userInfo = boParam.getUserInfo();
		HistoryWorkInfoConditionBean bean = boParam.getBean(); //����Ľ����ѯbean
		String userType = userInfo.getType();
		if (id == null) { //������ŵ�RangeIDΪ�գ���ζ��������޹أ�������ʷ�����й�
			id = getRangeID(userType,bean); //�õ���������ʷ���ߵ�rangeID
		}
		if (UserType.PROVINCE.equals(userType)) { // �����ʡ�ƶ��û�
			if ("11".equals(id)) { // �����ѡ��Χ�ǡ�ȫʡ��Χ��
				bo =createBOProvinceMobile(null); //����ʡ�ƶ�BO������ʵ�ֵķ���
			} else {
				bo =createBOCityMobile(id); //�������ƶ�BO������ʵ�ֵķ���
			}
		} else if (UserType.SECTION.equals(userType)) { // ��������ƶ��û�
			// �����ѡ��ΧΪ�û����ڵ���
			if ("12".equals(id)) {
				bo =createBOCityMobile(userInfo.getRegionid()); //�������ƶ�BO������ʵ�ֵķ���
			} else {
				bo =createBOCityContractor(id); //�����д�άBO������ʵ�ֵķ���
			}
		} else if (UserType.CONTRACTOR.equals(userType)) { // ������д�ά�û�
			// �����ѡ��ΧΪ�û����ڴ�ά��˾
			if ("22".equals(id)) {
				bo =createBOCityContractor(userInfo.getDeptID()); //�����д�άBO������ʵ�ֵķ���
			} else {
				bo =createBOPatrolman(id); //����Ѳ����BO������ʵ�ֵķ���
			}
		}
		return bo;
	}
	
	/**
	 * ����bean�õ�rangeID
	 * @param userType �û�����
	 * @param bean ���������bean
	 * @return 
	 */
	public abstract String getRangeID(String userType,HistoryWorkInfoConditionBean bean);
	
	/**
	 * ����ʡ�ƶ�BO�������
	 * @param id rangeID��smRangeID
	 * @return
	 */
	public abstract WorkInfoHistoryBaseBO createBOProvinceMobile(String id);
	
	/**
	 * �������ƶ�BO�������
	 * @param id rangeID��smRangeID
	 * @return
	 */
	public abstract WorkInfoHistoryBaseBO createBOCityMobile(String id);
	
	/**
	 * �����д�άBO�������
	 * @param id rangeID��smRangeID
	 * @return
	 */
	public abstract WorkInfoHistoryBaseBO createBOCityContractor(String id);
	
	/**
	 * ����Ѳ����BO�������
	 * @param id rangeID��smRangeID
	 * @return
	 */
	public abstract WorkInfoHistoryBaseBO createBOPatrolman(String id);
}
