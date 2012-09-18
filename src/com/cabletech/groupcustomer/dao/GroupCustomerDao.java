package com.cabletech.groupcustomer.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.exceltemplates.ReadExcle;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.groupcustomer.bean.GroupCustomerBean;
import com.cabletech.partmanage.action.PartRequisitionAction;

public class GroupCustomerDao {

	// 日志
	private static Logger logger = Logger.getLogger(GroupCustomerDao.class
			.getName());

	/**
	 * 获得上传文件的集团客户内容(有效部分)
	 * 
	 * @param hform
	 *            页面提交的内容
	 * @param path
	 *            存放的临时路径
	 * @return List 文件的内容
	 */
	private List getUpInfo(GroupCustomerBean hform, String path) {
		// 将文件存入到指定的临时路径
		if (!this.saveFile(hform, path)) {
			return null;
		}

		// 取得Excel文件中客户资料
		ReadExcle read = new ReadExcle(path + "\\groupcustomer.xls");
		return read.getExcleGroupCustomer();

	}

	/**
	 * 将上传的文件保存为临时文件
	 * 
	 * @param hform
	 *            页面提交的内容
	 * @param path
	 *            存放的临时路径
	 * @return boolean 保存成功返回真,否则返回假
	 */
	private boolean saveFile(GroupCustomerBean hform, String path) {
		// 判断文件是否存在
		String dir = path;
		FormFile file = hform.getFile();
		if (file == null) {
			return false;
		}
		// 判断文件是否存在，存在删除
		File temfile = new File(dir + "\\groupcustomer.xls");
		if (temfile.exists()) {
			temfile.delete();
		}
		// 保存文件
		try {
			InputStream streamIn = file.getInputStream();
			OutputStream streamOut = new FileOutputStream(dir
					+ "\\groupcustomer.xls");
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = streamIn.read(buffer, 0, 8192)) != -1) {
				streamOut.write(buffer, 0, bytesRead);
			}
			streamOut.close();
			streamIn.close();
			return true;
		} catch (Exception e) {
			logger.error("导入客户资料保存文件时出错:" + e.getMessage());
			return false;
		}
	}

	/**
	 * 保存Excel的内容到数据库
	 * 
	 * @param hform
	 *            页面提交的内容
	 * @param path
	 *            存放的临时路径
	 * @return
	 */
	public boolean saveExcelGroupcustomerInfo(GroupCustomerBean hform,
			String path) {
		// 存放返回值
		boolean returnFlg = false;
		// 取得导入的Excel文件的内容
		List upDataInfo = getUpInfo(hform, path);
		if (upDataInfo == null) {
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
			// 遍历每一条客户信息，插入到数据库
			id = ora.getSeqs("groupcustomer", 18, upDataInfo.size());

			for (int i = 0; i < upDataInfo.size(); i++) {
				// 取得集团客户资料表id的序列值
				rowMap = (HashMap) upDataInfo.get(i);
				sql = "insert into groupcustomer (id, groupid, city, town, "
						+ " grouptype, groupname, groupaddress, x, y, "
						+ "regionid,operationtype,customermanager,tel,grouptel"
						+ " ) values ( " + "'" + id[i] + "', '"
						+ rowMap.get("groupid") + "' , '" + rowMap.get("city")
						+ "' , '" + rowMap.get("town") + "' , '"
						+ rowMap.get("grouptype") + "' , '"
						+ rowMap.get("groupname") + "' , '"
						+ rowMap.get("groupaddr") + "' , '" + rowMap.get("x")
						+ "' , '" + rowMap.get("y") + "' ,'"
						+ rowMap.get("regionid") + "' , '"
						+ rowMap.get("operationtype") + "' , '"
						+ rowMap.get("customermanager") + "' , '"
						+ rowMap.get("tel") + "' , '" + rowMap.get("grouptel")
						+ "')";
				up.executeUpdate(sql);
			}
			up.commit();
			up.setAutoCommitTrue();
			returnFlg = true;
		} catch (Exception e) {
			System.out.println("ERROR sql : " + sql);
			if (up != null) {
				up.rollback();
				up.setAutoCommitTrue();
			}
			logger.warn("保存导入的集团客户信息出错:" + e.getMessage());
		}

		return returnFlg;
	}

	/**
	 * 取得区域信息
	 * 
	 * @return List
	 */
	public List getRegionList() {
		// sql文
		String sql = "select regionid, regionname from region "
				+ " where SUBSTR(regionid,3,6) != '0000'";

		List regionList = null;
		try {
			QueryUtil qu = new QueryUtil();
			regionList = qu.queryBeans(sql);
		} catch (Exception ex) {
			logger.warn("获得受理人列表出错:" + ex.getMessage());
		}

		return regionList;
	}

	/**
	 * 保存单个客户资料
	 * 
	 * @param bean
	 *            页面提交的内容
	 * @return boolean 存在成功返回true,失败返回false
	 */
	public boolean addCustomer(GroupCustomerBean bean) {
		// 返回值变更
		boolean returnFlg = false;
		OracleIDImpl ora = new OracleIDImpl();
		// 取得集团客户资料表字段id的序列值
		String id = ora.getSeq("groupcustomer", 18);

		// 组成sql语句
		String sql = "insert into groupcustomer (id, groupid, city, town, "
				+ " grouptype, groupname, groupaddress,  x, y, regionid, "
				+ " operationtype, customermanager, tel, grouptel "
				+ " ) values ( " + "'" + id + "', '" + bean.getGroupid()
				+ "' , '" + bean.getCity() + "' , '" + bean.getTown() + "' , '"
				+ bean.getGrouptype() + "' , '" + bean.getGroupname() + "' , '"
				+ bean.getGroupaddr() + "' , '" + bean.getX() + "' , '"
				+ bean.getY() + "' , '" + bean.getRegionid() + "' , '"
				+ bean.getOperationtype() + "' , '" + bean.getCustomermanager()
				+ "' , '" + bean.getTel() + "' , '" + bean.getGrouptel() + "')";

		UpdateUtil up;
		try {
			up = new UpdateUtil();
			up.executeUpdate(sql);
			returnFlg = true;
		} catch (Exception e) {
			logger.warn("保存客户资料出错:" + e.getMessage());
		}
		return returnFlg;
	}

	/**
	 * 条件查询集团客户资料
	 * 
	 * @param bean
	 *            页面提交的内容
	 * @param userinfo
	 *            登录人员信息
	 * @param session
	 *            session会话
	 * @return List
	 */
	public List queryGroupCustomer(GroupCustomerBean bean, UserInfo userinfo,
			HttpSession session) {

		String sql = "select id, groupid,city,town,grouptype, groupname, x, y,"
				+ "groupaddress,operationtype,customermanager,tel,grouptel"
				+ " from groupcustomer";
		String con = "";

		// 客户名称
		String name = bean.getGroupname();
		if (name != null && !"".equals(name)) {
			con = " where groupname like '%" + name + "%' ";
		}
		// 集团类型
		String type = bean.getGrouptype();
		if (type != null && !"".equals(type)) {
			if ("".equals(con)) {
				con += " where (grouptype = '" + type + "' or grouptype = '"
						+ type.toLowerCase() + "') ";
			} else {
				con += " and (grouptype = '" + type + "' or grouptype = '"
						+ type.toLowerCase() + "') ";
			}
		}

		// 所属区域
		String city = bean.getCity();
		if (city != null && !"".equals(city)) {
			if ("".equals(con)) {
				con += " where city = '" + city + "' ";
			} else {
				con += " and city = '" + city + "' ";
			}
		}

		// 业务类型
		String operationtype = bean.getOperationtype();
		if (operationtype != null && !"".equals(operationtype)) {
			if ("".equals(con)) {
				con += " where operationtype = '" + operationtype + "' ";
			} else {
				con += " and operationtype = '" + operationtype + "' ";
			}
		}

		// 区域
		if (UserInfo.PROVINCE_MUSER_TYPE.equals(userinfo.getType())) {
			// 省移动
			String regionid = bean.getRegionid();
			if (regionid != null && !"".equals(regionid)) {
				if ("".equals(con)) {
					con += " where regionid = '" + regionid + "' ";
				} else {
					con += " and regionid = '" + regionid + "' ";
				}
			}
		} else if (UserInfo.CITY_MUSER_TYPE.equals(userinfo.getType())) {
			// 市移动
			if ("".equals(con)) {
				con += " where regionid = '" + userinfo.getRegionID() + "' ";
			} else {
				con += " and regionid = '" + userinfo.getRegionID() + "' ";
			}
		}

		sql = sql + con + " order by groupid";
		session.setAttribute("querysql", sql);

		List customerList = null;
		try {
			QueryUtil qu = new QueryUtil();
			customerList = qu.queryBeans(sql);
		} catch (Exception ex) {
			logger.warn("获得客户资料出错:" + ex.getMessage());
		}

		return customerList;
	}

	/**
	 * 取得客户资料的详细信息
	 * 
	 * @param customerId
	 *            客户Id
	 * @return GroupCustomerBean
	 */
	public GroupCustomerBean getOneCustomer(String customerId) {

		String sql = "select g.id, g.groupid,g.city,g.town,g.grouptype, g.groupname, "
				+ " g.groupaddress, g.x, g.y, g.operationtype, g.customermanager, "
				+ " g.tel,g.grouptel , g.regionid, r.regionname "
				+ " from groupcustomer g, region r "
				+ " where g.regionid = r.regionid(+) and g.id = '"
				+ customerId
				+ "'";

		ResultSet rst = null;
		QueryUtil qu = null;
		GroupCustomerBean bean = null;
		try {
			qu = new QueryUtil();
			rst = qu.executeQuery(sql);

			if (rst != null && rst.next()) {
				bean = new GroupCustomerBean();
				bean.setId(rst.getString("id"));
				bean.setGroupid(rst.getString("groupid"));
				bean.setCity(rst.getString("city"));
				bean.setTown(rst.getString("town"));
				bean.setGroupname(rst.getString("groupname"));
				bean.setGroupaddr(rst.getString("groupaddress"));
				bean.setX(rst.getString("x"));
				bean.setY(rst.getString("y"));
				bean.setOperationtype(rst.getString("operationtype"));
				bean.setCustomermanager(rst.getString("customermanager"));
				bean.setTel(rst.getString("tel"));
				bean.setGrouptel(rst.getString("grouptel"));
				bean.setRegionid(rst.getString("regionid"));
				bean.setRegionname(rst.getString("regionname"));
				bean.setGrouptype(rst.getString("grouptype"));
			}
			rst.close();
		} catch (Exception e) {
			logger.warn("获得单个客户资料详细信息出错:" + e.getMessage());
			if (rst != null) {
				try {
					rst.close();
				} catch (SQLException e1) {
				}
			}
		} finally {
			if (rst != null) {
				try {
					rst.close();
				} catch (SQLException e1) {
				}
			}
		}

		return bean;
	}

	/**
	 * 执行修改的客户资料
	 * 
	 * @param hform
	 *            页面提交内容
	 * @return boolean 执行成功返回true,失败返回false
	 */
	public boolean updateCustomer(GroupCustomerBean hform) {
		// 返回值
		boolean returnFlg = false;
		// sql语句
		String sql = "update groupcustomer set groupid = '"
				+ hform.getGroupid() + "' , " + " city = '" + hform.getCity()
				+ "' , town = '" + hform.getTown() + "', " + "grouptype = '"
				+ hform.getGrouptype() + "', groupname = '"
				+ hform.getGroupname() + "', groupaddress = '"
				+ hform.getGroupaddr() + "', x = '" + hform.getX() + "', y = '"
				+ hform.getY() + "', operationtype = '"
				+ hform.getOperationtype() + "', customermanager = '"
				+ hform.getCustomermanager() + "', tel = '" + hform.getTel()
				+ "', grouptel = '" + hform.getGrouptel() + "', regionid = '"
				+ hform.getRegionid() + "' where id = '" + hform.getId() + "'";
		UpdateUtil up;
		try {
			up = new UpdateUtil();
			up.executeUpdate(sql);
			returnFlg = true;
		} catch (Exception e) {
			logger.warn("修改客户资料出错:" + e.getMessage());
			e.printStackTrace();
		}

		return returnFlg;
	}

	/**
	 * 删除客户资料
	 * 
	 * @param hform
	 *            页面提交内容
	 * @return boolean 删除成功返回true, 失败返回false
	 */
	public boolean delCustomer(String id) {
		// 返回值
		boolean returnFlg = false;
		String sql = "delete from  groupcustomer " + " where id = '" + id + "'";
		UpdateUtil up;
		try {
			up = new UpdateUtil();
			up.executeUpdate(sql);
			returnFlg = true;
		} catch (Exception e) {
			logger.warn("删除单个客户资料出错:" + e.getMessage());
		}

		return returnFlg;
	}

	/**
	 * 批量删除客户资料
	 * 
	 * @param id
	 *            需要删除的客户的id
	 * @return boolean 删除成功返回true, 失败返回false
	 */
	public boolean delCustomers(String[] id) {
		// 返回值
		boolean returnFlg = false;
		// sql文
		String sql = "delete from  groupcustomer " + " where id in (";
		for (int i = 0; i < id.length; i++) {
			sql += "'" + id[i] + "',";
		}
		sql = sql.substring(0, sql.length() - 1) + ")";
		UpdateUtil up;
		try {
			up = new UpdateUtil();
			up.executeUpdate(sql);
			returnFlg = true;
		} catch (Exception e) {
			logger.warn("批量删除客户资料出错:" + e.getMessage());
		}

		return returnFlg;
	}

}
