package com.cabletech.partmanage.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import com.cabletech.commons.exceltemplates.ReadExcle;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.HibernateDaoSupport;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.fsmanager.bean.RouteInfoBean;
import com.cabletech.partmanage.beans.MaterialAddressBean;

public class MaterialAddressDao extends HibernateDaoSupport {
	private static Logger logger = Logger.getLogger(MaterialAddressDao.class
			.getName());

	/**
	 * ��Ӵ�ŵص���Ϣ
	 * 
	 * @param bean
	 * @return
	 */
	public boolean addPartAddress(MaterialAddressBean bean) {
		String sql = "insert into linepatrol_mt_addr(id,address,contractorid,remark,state) values(?,?,?,?,'1')";
		try {
			Connection conn = getSession().connection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, bean.getId());
			stmt.setString(2, bean.getAddress());
			stmt.setString(3, bean.getContractorid());
			stmt.setString(4, bean.getRemark());
			stmt.executeUpdate();
			conn.commit();
			return true;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}

	/**
	 * ��ѯ���д�ŵص���Ϣ��
	 * 
	 * @param bean
	 * @return
	 */
	public List getPartaddressBean(MaterialAddressBean bean) {
		List list = new ArrayList();
		String sql = "select a.id,a.address,b.contractorname contractorid ,a.remark from linepatrol_mt_addr a,contractorinfo b where a.contractorid=b.contractorid";
		if (bean != null) {
			if (bean.getId() != null && !"".equals(bean.getId())) {
				sql += " and a.id='" + bean.getId() + "'";
			}
			if (bean != null && !"".equals(bean.getAddress())) {
				sql += " and a.address like '" + "%" + bean.getAddress() + "%"
						+ "'";
			}
			if (bean.getContractorid() != null
					&& !"".equals(bean.getContractorid())) {
				sql += " and a.contractorid='" + bean.getContractorid() + "'";
			}
		}
		sql += " and a.state ='1' order by a.id desc";
		System.out.println("sql::::::::::" + sql);
		try {
			QueryUtil query = new QueryUtil();
			list = query.queryBeans(sql);
		} catch (Exception e) {
			logger.error("query linepatrol_mt_addr error:" + e);
		}
		return list;
	}

	/**
	 * ɾ����ŵص���Ϣ��
	 * 
	 * @param id
	 * @return
	 */
	public boolean deletePartaddressById(String id) {
		String sql = "update linepatrol_mt_addr a set a.state = '0' where id='"
				+ id + "'";
		try {
			UpdateUtil exec = new UpdateUtil();
			exec.setAutoCommitFalse();
			exec.executeUpdate(sql);
			exec.commit();
			exec.setAutoCommitTrue();
			return true;
		} catch (Exception e) {
			logger.error("delete linepatrol_mt_addr error:" + e);
			return false;
		}
	}

	/**
	 * �����ŵص���Ϣ
	 * 
	 * @param id
	 * @param bean
	 * @return
	 */
	public MaterialAddressBean getPartaddressById(String id,
			MaterialAddressBean bean) {
		String sql = "select a.id,a.address,a.contractorid,a.remark from linepatrol_mt_addr a where id='"
				+ id + "'";
		ResultSet rs = null;
		try {
			QueryUtil query = new QueryUtil();
			rs = query.executeQuery(sql);
			if (rs.next()) {
				bean.setId(rs.getString("id"));
				bean.setAddress(rs.getString("address"));
				bean.setContractorid(rs.getString("contractorid"));
				bean.setRemark(rs.getString("remark"));
			}
			rs.close();
		} catch (Exception e) {
			logger.error(e);
			try {
				rs.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return bean;
	}

	public boolean updatePartaddress(MaterialAddressBean bean) {
		String sql = "update linepatrol_mt_addr set address=?,contractorid=?,remark=? where id=?";
		try {
			Connection conn = getSession().connection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, bean.getAddress());
			stmt.setString(2, bean.getContractorid());
			stmt.setString(3, bean.getRemark());
			stmt.setString(4, bean.getId());
			stmt.executeUpdate();
			System.out.println("sql::::::::::::::::::::::::;" + sql);
			conn.commit();
			return true;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}

	// ����Ϊexcel��ʱ�ļ�
	private boolean saveFile(MaterialAddressBean hform, String path) {
		// �ж��ļ��Ƿ����
		String dir = path;
		FormFile file = hform.getFile();
		if (file == null) {
			return false;
		}
		// �ж��ļ��Ƿ���ڣ�����ɾ��
		File temfile = new File(dir + "\\moterialaddress.xls");
		if (temfile.exists()) {
			temfile.delete();
		}
		// �����ļ�
		try {
			InputStream streamIn = file.getInputStream();
			OutputStream streamOut = new FileOutputStream(dir
					+ "\\moterialaddress.xls");
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = streamIn.read(buffer, 0, 8192)) != -1) {
				streamOut.write(buffer, 0, bytesRead);
			}
			streamOut.close();
			streamIn.close();
			logger.error("����·����Ϣ�����ļ��ɹ�");
			return true;
		} catch (Exception e) {
			logger.error("����·����Ϣ�����ļ�ʱ����:" + e.getMessage());
			return false;
		}
	}

	private List getUpInfo(MaterialAddressBean hform, String path) {
		// ���ļ����뵽ָ������ʱ·��
		if (!this.saveFile(hform, path)) {
			return null;
		}

		// ȡ��Excel�ļ���·����Ϣ
		ReadExcle read = new ReadExcle(path + "\\moterialaddress.xls");
		return read.getExcleRouteInfo();

	}

	// ����excel���ݵ����ݿ�
	public boolean saveExcelRouteInfo(MaterialAddressBean hform, String path) {
		// ��ŷ���ֵ
		boolean returnFlg = false;
		// ȡ�õ����Excel�ļ�������
		List upDataInfo = getUpInfo(hform, path);
		Iterator ite = upDataInfo.iterator();
		if (upDataInfo == null || !ite.hasNext()) {
			return returnFlg;
		}

		String sql = null;
		Map rowMap = null;
		UpdateUtil up = null;
		OracleIDImpl ora = new OracleIDImpl();
		String[] id;
		try {
			up = new UpdateUtil();
			// ����
			up.setAutoCommitFalse();
			// ����ÿһ����Ϣ�����뵽���ݿ�
			id = ora.getSeqs("routeinfo", 20, upDataInfo.size());
			String regionid = "";
			for (int i = 0; i < upDataInfo.size(); i++) {
				// ȡ��·����Ϣ��id������ֵ
				rowMap = (HashMap) upDataInfo.get(i);
				sql = "insert into routeinfo (id,routename,regionid "
						+ " ) values ( '" + id[i] + "', '"
						+ rowMap.get("routename") + "' , '" + regionid + "')";
				up.executeUpdate(sql);
				logger.info("sql:" + sql);
			}
			up.commit();
			up.setAutoCommitTrue();
			logger.info("��Ϣ�������ݿ�ɹ�");
			returnFlg = true;
		} catch (Exception e) {
			System.out.println("ERROR sql : " + sql);
			if (up != null) {
				up.rollback();
				up.setAutoCommitTrue();
			}
			logger.warn("���浼���·����Ϣ����:" + e.getMessage());
		}

		return returnFlg;
	}

	/**
	 * ��ӵص�ʱ�жϵص��Ƿ����
	 * ���µص�ʱ�жϵص��Ƿ���ڣ����ǿ��Ը���Ϊ�Լ�
	 * @param bean
	 * @param flag
	 * @return
	 */
	public List judgeData(MaterialAddressBean bean, String flag){
		String contractorId = bean.getContractorid();
		String address = bean.getAddress();
		String id = bean.getId();
		List list = new ArrayList();
		String sql = "select a.address from linepatrol_mt_addr a where a.contractorid='" + contractorId + "' and a.address='" + address + "'";
		if("update".equals(flag)){
			sql += " and a.id<>'" + id +"'";
		}
		System.out.println("sql:" + sql);
		try {
			QueryUtil util = new QueryUtil();
			list = util.queryBeans(sql);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
