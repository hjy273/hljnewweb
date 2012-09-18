package com.cabletech.sparepartmanage.services;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.sparepartmanage.beans.SparepartBaseInfoBean;
import com.cabletech.sparepartmanage.beans.SparepartStorageBean;
import com.cabletech.sparepartmanage.dao.SeparepartBaseInfoDAO;

public class SeparepartBaseInfoService extends BaseBisinessObject{
	SeparepartBaseInfoDAO dao = new SeparepartBaseInfoDAO();
	
	
	/**
	 * �жϱ�����Ϣ�Ƿ����ظ�
	 */
	
	public boolean  judgeIsHaveSparePart(SparepartBaseInfoBean bean,String act){
		return dao.judgeIsHaveSparePart(bean,act);
	}
	/**
	 * ���ӱ���
	 * @param sparepartBaseInfoBean
	 * @return
	 */
	public boolean addSeparepart(SparepartBaseInfoBean bean) {
		return dao.addSeparepart(bean);
	}
	
	public List getAllFactory() {
		String sql = "select distinct s.productFactory from SparepartBaseInfoBean s order by s.productFactory desc";
		return dao.getAllFactory(sql);
	}
	
	/**
	 * ����ѡ�еı������̻�ȡ����
	 * @param fac
	 * @return
	 */
	public List getNameByFactory(String facName) {
		return dao.getNameByFactory(facName);
	}
	
	/**
	 * ����ѡ�еı������ƻ�ȡ�ͺ�
	 * @param name
	 * @return
	 */
	public List getModelByName(String name, String fac) {
		return dao.getModelByName(name, fac);
	}
	
	/**
	 * ����ѡ�е��ͺŻ�ȡ����汾
	 * @param model
	 * @return
	 */
	public List getVersionByModel(String model, String fac, String name) {
		return dao.getVersionByModel(model,fac,name);
	}
	
	/**
	 * ִ�в�ѯ
	 * @param bean
	 * @return
	 */
	public List doQuery(SparepartBaseInfoBean bean, HttpSession session) {
		return dao.doQuery(bean,session);
	}
	
	/**
	 * ����TID��ȡָ���ı�����ϸ��Ϣ
	 * @param tid
	 * @return
	 */
	public SparepartBaseInfoBean getOneSparepart(String tid) {
		return dao.getOneSparepart(tid);
	}
	
	/**
	 * �޸ı���
	 * @param bean
	 * @return
	 */
	public boolean modifySeparepar(SparepartBaseInfoBean bean) {
		return dao.modifySeparepar(bean);
	}
	/**
	 * �жϿ����Ƿ��Ѿ�ʹ���˸ı�����Ϣ
	 */
	
	public boolean judgeStorageIsHave(String tid){
		return dao.judgeStorageIsHave(tid);
	}
	
	/**
	 * ����TIDɾ��ָ���ı���
	 * @param tid
	 * @return
	 */
	public boolean delSeparepar(String tid) {
		return dao.delSeparepar(tid);
	}
	
	/**
	 * ��������ɾ��
	 * @param tid
	 * @return
	 */
	public boolean delMore(String tid) {
		return dao.delMore(tid);
	}
	
	public List doQueryAfterMod(String sql) {
		return dao.doQueryAfterMod(sql);
	}
	
	public List doQueryAfterMod(List ids) {
		return dao.doQueryAfterMod(ids);
	}
	
	/**
	 * ���ı�������״̬
	 * @param tid
	 * @param state
	 * @return
	 */
	public boolean modState(String tid, String state) {
		return dao.modState(tid, state);
	}
	
	public boolean modState(String tid, String state,UpdateUtil exec) {
		return dao.modState(tid, state,exec);
	}
	
	public String getSpbName() {
		return dao.getSpbName();
	}
	
	/**
	 * ����ѡ�еı������ƻ�ȡ�ͺ�
	 * @param name
	 * @return
	 */
	public List getModelByNameForStockSave(String name, String fac) {
		return dao.getModelByNameForStockSave(name, fac);
	}
	
	/**
	 * ����ѡ�еı������̻�ȡ���Ƽ�����
	 * @param fac
	 * @return
	 */
	public List getNameByFacForStockQuery(String fac) {
		return dao.getNameByFactory(fac);
	}
	
	
	/**
	 * ��ȡ�����б�
	 * @return
	 */
	public List getAllSeparepart() {
		return dao.getAllSeparepart();
	}
	
	
	
}
