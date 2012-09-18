package com.cabletech.linepatrol.material.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;
import org.springframework.stereotype.Repository;

import com.cabletech.commons.exceltemplates.ReadExcle;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.material.beans.MaterialAddressBean;
import com.cabletech.linepatrol.material.domain.MaterialAddress;

@Repository
public class MaterialAddressDao extends HibernateDao<MaterialAddress, Integer> {
	private static Logger logger = Logger.getLogger(MaterialAddressDao.class
			.getName());

	/**
	 * 添加存放地点信息
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public void addPartAddress(MaterialAddress materialAddress) {
		String sql = "insert into lp_mt_addr(id,address,contractorid,remark,state) values('"
				+ materialAddress.getId()
				+ "','"
				+ materialAddress.getAddress()
				+ "','"
				+ materialAddress.getContractorid()
				+ "','"
				+ materialAddress.getRemark() + "','1')";
		logger.info("添加材料地址：" + sql);
		this.getJdbcTemplate().execute(sql);
	}

	/**
	 * 查询所有存放地点信息；
	 * 
	 * @param bean
	 * @return
	 */
	public List getPartaddressBean(MaterialAddress materialAddress) {
		String sql = "select a.id,a.address,b.contractorname contractorid ,a.remark from LP_mt_addr a,contractorinfo b where a.contractorid=b.contractorid";
		if (materialAddress != null) {
			if (materialAddress.getId() != null
					&& !"".equals(materialAddress.getId())) {
				sql += " and a.id='" + materialAddress.getId() + "'";
			}
			if (materialAddress != null
					&& !"".equals(materialAddress.getAddress())) {
				sql += " and a.address like '" + "%"
						+ materialAddress.getAddress() + "%" + "'";
			}
			if (materialAddress.getContractorid() != null
					&& !"".equals(materialAddress.getContractorid())) {
				sql += " and a.contractorid='"
						+ materialAddress.getContractorid() + "'";
			}
		}
		sql += " and a.state ='1' order by a.id desc";
		logger.info("" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * 删除存放地点信息；
	 * 
	 * @param id
	 * @return
	 */
	public void deletePartaddressById(String id) {
		String sql = "update LP_mt_addr a set a.state = '0' where id='" + id
				+ "'";
		this.getJdbcTemplate().execute(sql);
	}

	/**
	 * 载入存放地点信息
	 * 
	 * @param id
	 * @param bean
	 * @return
	 */
	public MaterialAddress getPartaddressById(String id,
			MaterialAddress materialAddress) {
		String sql = "select a.id,a.address,a.contractorid,a.remark from LP_mt_addr a where id='"
				+ id + "'";
		ResultSet rs = null;
		try {
			QueryUtil query = new QueryUtil();
			rs = query.executeQuery(sql);
			if (rs.next()) {
				materialAddress.setId(rs.getString("id"));
				materialAddress.setAddress(rs.getString("address"));
				materialAddress.setContractorid(rs.getString("contractorid"));
				materialAddress.setRemark(rs.getString("remark"));
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
		return materialAddress;
	}

	public void updatePartaddress(MaterialAddress materialAddress) {
		String sql = "update LP_mt_addr set address='"
				+ materialAddress.getAddress() + "',contractorid='"
				+ materialAddress.getContractorid() + "',remark='"
				+ materialAddress.getRemark() + "' where id='"
				+ materialAddress.getId() + "'";
		this.getJdbcTemplate().execute(sql);
	}

	// 保存为excel临时文件
	private boolean saveFile(MaterialAddressBean hform, String path) {
		// 判断文件是否存在
		String dir = path;
		FormFile file = hform.getFile();
		if (file == null) {
			return false;
		}
		// 判断文件是否存在，存在删除
		File temfile = new File(dir + "\\moterialaddress.xls");
		if (temfile.exists()) {
			temfile.delete();
		}
		// 保存文件
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
			logger.error("导入路由信息保存文件成功");
			return true;
		} catch (Exception e) {
			logger.error("导入路由信息保存文件时出错:" + e.getMessage());
			return false;
		}
	}

	private List getUpInfo(MaterialAddressBean hform, String path) {
		// 将文件存入到指定的临时路径
		if (!this.saveFile(hform, path)) {
			return null;
		}

		// 取得Excel文件中路由信息
		ReadExcle read = new ReadExcle(path + "\\moterialaddress.xls");
		return read.getExcleRouteInfo();

	}

	// 保存excel数据到数据库
	public boolean saveExcelRouteInfo(MaterialAddressBean hform, String path) {
		// 存放返回值
		boolean returnFlg = false;
		// 取得导入的Excel文件的内容
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
			// 事务
			up.setAutoCommitFalse();
			// 遍历每一条信息，插入到数据库
			id = ora.getSeqs("routeinfo", 20, upDataInfo.size());
			String regionid = "";
			for (int i = 0; i < upDataInfo.size(); i++) {
				// 取得路由信息表id的序列值
				rowMap = (HashMap) upDataInfo.get(i);
				sql = "insert into routeinfo (id,routename,regionid "
						+ " ) values ( '" + id[i] + "', '"
						+ rowMap.get("routename") + "' , '" + regionid + "')";
				up.executeUpdate(sql);
				logger.info("sql:" + sql);
			}
			up.commit();
			up.setAutoCommitTrue();
			logger.info("信息存入数据库成功");
			returnFlg = true;
		} catch (Exception e) {
			System.out.println("ERROR sql : " + sql);
			if (up != null) {
				up.rollback();
				up.setAutoCommitTrue();
			}
			logger.warn("保存导入的路由信息出错:" + e.getMessage());
		}

		return returnFlg;
	}

	/**
	 * 添加地点时判断地点是否存在 更新地点时判断地点是否存在，但是可以更改为自己
	 * 
	 * @param bean
	 * @param flag
	 * @return
	 */
	public List judgeData(MaterialAddress materialAddress, String flag) {
		String contractorId = materialAddress.getContractorid();
		String address = materialAddress.getAddress();
		String id = materialAddress.getId();
		String sql = "select a.address from LP_mt_addr a where a.contractorid='"
				+ contractorId + "' and a.address='" + address + "'";
		if ("update".equals(flag)) {
			sql += " and a.id<>'" + id + "'";
		}
		logger.info("" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	public List queryList(String condition) {
		// TODO Auto-generated method stub
		String sql = "select a.id,a.address from LP_mt_addr a ";
		sql = sql + " where a.state='1' ";
		sql = sql + condition;
		logger.info("Execute sql:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}
}
