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
	 * 判断备件信息是否还有重复
	 */
	
	public boolean  judgeIsHaveSparePart(SparepartBaseInfoBean bean,String act){
		return dao.judgeIsHaveSparePart(bean,act);
	}
	/**
	 * 增加备件
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
	 * 根据选中的备件厂商获取名称
	 * @param fac
	 * @return
	 */
	public List getNameByFactory(String facName) {
		return dao.getNameByFactory(facName);
	}
	
	/**
	 * 根据选中的备件名称获取型号
	 * @param name
	 * @return
	 */
	public List getModelByName(String name, String fac) {
		return dao.getModelByName(name, fac);
	}
	
	/**
	 * 根据选中的型号获取软件版本
	 * @param model
	 * @return
	 */
	public List getVersionByModel(String model, String fac, String name) {
		return dao.getVersionByModel(model,fac,name);
	}
	
	/**
	 * 执行查询
	 * @param bean
	 * @return
	 */
	public List doQuery(SparepartBaseInfoBean bean, HttpSession session) {
		return dao.doQuery(bean,session);
	}
	
	/**
	 * 根据TID获取指定的备件详细信息
	 * @param tid
	 * @return
	 */
	public SparepartBaseInfoBean getOneSparepart(String tid) {
		return dao.getOneSparepart(tid);
	}
	
	/**
	 * 修改备件
	 * @param bean
	 * @return
	 */
	public boolean modifySeparepar(SparepartBaseInfoBean bean) {
		return dao.modifySeparepar(bean);
	}
	/**
	 * 判断库存表是否已经使用了改备件信息
	 */
	
	public boolean judgeStorageIsHave(String tid){
		return dao.judgeStorageIsHave(tid);
	}
	
	/**
	 * 根据TID删除指定的备件
	 * @param tid
	 * @return
	 */
	public boolean delSeparepar(String tid) {
		return dao.delSeparepar(tid);
	}
	
	/**
	 * 备件批量删除
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
	 * 更改备件基本状态
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
	 * 根据选中的备件名称获取型号
	 * @param name
	 * @return
	 */
	public List getModelByNameForStockSave(String name, String fac) {
		return dao.getModelByNameForStockSave(name, fac);
	}
	
	/**
	 * 根据选中的备件厂商获取名称及备件
	 * @param fac
	 * @return
	 */
	public List getNameByFacForStockQuery(String fac) {
		return dao.getNameByFactory(fac);
	}
	
	
	/**
	 * 获取备件列表
	 * @return
	 */
	public List getAllSeparepart() {
		return dao.getAllSeparepart();
	}
	
	
	
}
