/** create by jixf
 *  date: 2009-08-17
 */

package com.cabletech.fsmanager.dao;

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
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.exceltemplates.ReadExcle;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.HibernateDaoSupport;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.fsmanager.bean.CableBean;

public class CableDao extends HibernateDaoSupport {
	private static Logger logger = Logger.getLogger(CableDao.class.getName());

	public List getContractor(String regoinid) {
		List list = new ArrayList();
		String sql = "select c.contractorid,c.contractorname from contractorinfo c where c.regionid='"
				+ regoinid + "' and state is null ";

		try {
			QueryUtil query = new QueryUtil();
			list = query.queryBeans(sql);
		} catch (Exception e) {
			logger.info("sql " + sql);
			logger.error("query Contractor error::::::" + e);
		}

		return list;
	}

	public List getPointInfo(String regionid) {
		List list = new ArrayList();
		String sql = "select p.pointid,p.pointname from pointinfo p where p.regionid='"
				+ regionid + "'";
		try {
			QueryUtil query = new QueryUtil();
			list = query.queryBeans(sql);
		} catch (Exception e) {
			logger.info("sql " + sql);
			logger.error("query PointInfo error::::::" + e);
		}
		return list;
	}

	public boolean addCable(CableBean bean) {
		String sql = "insert into cableinfo (id,cableno,contractorid,area,county,systemname,cablename,cablelinename,apoint,zpoint"
				+ ",laytype,company,construct,property,cabletype,createtime,fibertype,fibernumber,cablelength,unusecable,remark,isaccept,"
				+ "blueprintno,fiberlength,regionid) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			Connection conn = getSession().connection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, bean.getId());
			stmt.setString(2, bean.getCableno());
			stmt.setString(3, bean.getContractorid());
			stmt.setString(4, bean.getArea());
			stmt.setString(5, bean.getCounty());
			stmt.setString(6, bean.getSystemname());
			stmt.setString(7, bean.getCablename());
			stmt.setString(8, bean.getCablelinename());
			stmt.setString(9, bean.getApoint());
			stmt.setString(10, bean.getZpoint());
			stmt.setString(11, bean.getLaytype());
			stmt.setString(12, bean.getCompany());
			stmt.setString(13, bean.getConstruct());
			stmt.setString(14, bean.getProperty());
			stmt.setString(15, bean.getCabletype());
			stmt.setString(16, bean.getCreatetime());
			stmt.setString(17, bean.getFibertype());
			stmt.setString(18, bean.getFibernumber());
			stmt.setString(19, bean.getCablelength());
			stmt.setString(20, bean.getUnusecable());
			stmt.setString(21, bean.getRemark());
			stmt.setString(22, bean.getIsaccept());
			stmt.setString(23, bean.getBlueprintno());
			stmt.setString(24, bean.getFiberlength());
			stmt.setString(25, bean.getRegionId());

			stmt.executeUpdate();
			conn.commit();
			return true;
		} catch (Exception e) {
			logger.info("sql:: " + sql);
			logger.error(e);
			return false;
		}

	}

	public List getCableBean(CableBean bean) {
		List list = new ArrayList();
		String sql = "select  c.id,c.cableno,c.contractorid,ct.contractorname,c.cablename,c.cablelinename,c.fibertype,c.fibernumber"
				+ " from cableinfo c,contractorinfo ct where c.contractorid=ct.contractorid ";
		if (bean != null) {
			if (bean.getCableno() != null && !"".equals(bean.getCableno())) {
				sql += " and c.cableno like '" + "%" + bean.getCableno() + "%"
						+ "'";
			}
			if (bean.getContractorid() != null
					&& !"".equals(bean.getContractorid())) {
				sql += " and c.contractorid='" + bean.getContractorid() + "'";
			}
			if (bean.getCablename() != null && !"".equals(bean.getCablename())) {
				sql += " and c.cablename like '" + "%" + bean.getCablename()
						+ "%" + "'";
			}
			if (bean.getCablelinename() != null
					&& !"".equals(bean.getCablelinename())) {
				sql += " and c.cablelinename like '" + "%"
						+ bean.getCablelinename() + "%" + "'";
			}
		}
		sql += " order by c.cableno desc";
		System.out.println("sql::::::::::::::::::::::　　" + sql);
		try {
			QueryUtil query = new QueryUtil();
			list = query.queryBeans(sql);
		} catch (Exception e) {
			logger.info("sql " + sql);
			logger.error("query Cable error::::::" + e);
		}
		return list;
	}

	public boolean deleteCableById(String id) {
		String sql = "delete from cableinfo where id='" + id + "'";
		String csql = "delete from cable2point where cableid='" + id + "'";
		try {
			UpdateUtil exec = new UpdateUtil();
			exec.setAutoCommitFalse();
			exec.executeUpdate(sql);
			exec.executeUpdate(csql);
			exec.commit();
			exec.setAutoCommitTrue();
			return true;
		} catch (Exception e) {
			logger.error("delete cableinfo by id error：" + e);
			return false;
		}
	}

	/**
	 * 
	 * @param pid
	 * @return
	 */
	public String getPointName(String pid) {
		ResultSet rst = null;
		String sql = "select p.pointname from pointinfo p where p.pointid ='"
				+ pid + "'";
		try {
			QueryUtil query = new QueryUtil();
			rst = query.executeQuery(sql);
			if (rst.next()) {
				return rst.getString("pointname");
				// rst.close();
			}

		} catch (Exception e) {
			logger.error("载入一站点信息时异常：" + e);
		} finally {
			try {
				rst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.error("关闭记录时异常：" + e);
			}
		}
		return "";
	}

	/**
	 * 
	 * @param id
	 * @param bean
	 * @return CableBean
	 */
	public CableBean getCableById(String id, CableBean bean) {
		ResultSet rst = null;
		String sql = "select c.id,c.cableno,c.contractorid,c.area,c.county,c.systemname,c.cablename,c.cablelinename,c.apoint,c.zpoint"
				+ ",c.laytype,c.company,c.construct,c.property,c.cabletype,c.createtime,c.fibertype,c.fibernumber,c.cablelength,c.unusecable,c.remark,c.isaccept,"
				+ "c.blueprintno,c.fiberlength from cableinfo c where id='"
				+ id + "'";
		System.out.println("sql::::::::::: " + sql);
		try {
			QueryUtil query = new QueryUtil();
			rst = query.executeQuery(sql);
			if (rst.next()) {
				bean.setId(rst.getString("id"));
				bean.setCableno(rst.getString("cableno"));
				bean.setContractorid(rst.getString("contractorid"));
				bean.setArea(rst.getString("area"));
				bean.setCounty(rst.getString("county"));
				bean.setSystemname(rst.getString("systemname"));
				bean.setCablename(rst.getString("cablename"));
				bean.setCablelinename(rst.getString("cablelinename"));
				bean.setApoint(getPointName(rst.getString("apoint")));
				bean.setZpoint(getPointName(rst.getString("zpoint")));
				bean.setLaytype(rst.getString("laytype"));
				bean.setCompany(rst.getString("company"));
				bean.setConstruct(rst.getString("construct"));
				bean.setProperty(rst.getString("property"));
				bean.setCabletype(rst.getString("cabletype"));
				bean.setCreatetime(rst.getString("createtime"));
				bean.setFibertype(rst.getString("fibertype"));
				bean.setFibernumber(rst.getString("fibernumber"));
				bean.setCablelength(rst.getString("cablelength"));
				bean.setUnusecable(rst.getString("unusecable"));
				bean.setRemark(rst.getString("remark"));
				bean.setIsaccept(rst.getString("isaccept"));
				bean.setBlueprintno(rst.getString("blueprintno"));
				bean.setFiberlength(rst.getString("fiberlength"));
			}
			rst.close();

		} catch (Exception e) {
			logger.error("载入一站点信息时异常：" + e);
			try {
				rst.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();

		}
		return bean;
	}

	public boolean updateCableInfo(CableBean bean) {
		String sql = "update cableinfo set cableno=?,contractorid=?,area=?,county=?,systemname=?,cablename=?,cablelinename=?"
				+ ",laytype=?,company=?,construct=?,property=?,cabletype=?,createtime=?,fibertype=?,fibernumber=?,cablelength=?,unusecable=?,remark=?,isaccept=?,"
				+ "blueprintno=?,fiberlength=? where id=?";
		try {
			Connection conn = getSession().connection();
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, bean.getCableno());
			stmt.setString(2, bean.getContractorid());
			stmt.setString(3, bean.getArea());
			stmt.setString(4, bean.getCounty());
			stmt.setString(5, bean.getSystemname());
			stmt.setString(6, bean.getCablename());
			stmt.setString(7, bean.getCablelinename());
			stmt.setString(8, bean.getLaytype());
			stmt.setString(9, bean.getCompany());
			stmt.setString(10, bean.getConstruct());
			stmt.setString(11, bean.getProperty());
			stmt.setString(12, bean.getCabletype());
			stmt.setString(13, bean.getCreatetime());
			stmt.setString(14, bean.getFibertype());
			stmt.setString(15, bean.getFibernumber());
			stmt.setString(16, bean.getCablelength());
			stmt.setString(17, bean.getUnusecable());
			stmt.setString(18, bean.getRemark());
			stmt.setString(19, bean.getIsaccept());
			stmt.setString(20, bean.getBlueprintno());
			stmt.setString(21, bean.getFiberlength());
			stmt.setString(22, bean.getId());
			stmt.executeUpdate();
			conn.commit();
			return true;
		} catch (Exception e) {
			logger.info("sql:: " + sql);
			logger.error("update cableinfo error:::::" + e);
			return false;
		}
	}

	public List getExportInfo(CableBean bean) {
		List list = new ArrayList();
		String sql = "select c.id,c.cableno,ct.contractorname,c.area,c.county,c.systemname,c.cablename,c.cablelinename,p1.pointname apoint,p2.pointname zpoint"
				+ ",c.laytype,c.company,c.construct,c.property,c.cabletype,c.createtime,c.fibertype,c.fibernumber,to_char(c.cablelength) cablelength,to_char(c.unusecable) unusecable,c.remark,decode(c.isaccept,'1','是','0','否','否') isaccept,"
				+ "c.blueprintno,to_char(c.fiberlength) fiberlength from cableinfo c,contractorinfo ct,pointinfo p1,pointinfo p2 where c.contractorid=ct.contractorid and p1.pointid=c.apoint and p2.pointid=c.zpoint";
		if (bean != null) {
			if (bean.getCableno() != null && !"".equals(bean.getCableno())) {
				sql += " and c.cableno='" + bean.getCableno() + "'";
			}
			if (bean.getContractorid() != null
					&& !"".equals(bean.getContractorid())) {
				sql += " and c.contractorid='" + bean.getContractorid() + "'";
			}
			if (bean.getCablename() != null && !"".equals(bean.getCablename())) {
				sql += " and c.cablename='" + bean.getCablename() + "'";
			}
			if (bean.getCablelinename() != null
					&& !"".equals(bean.getCablelinename())) {
				sql += " and c.cablelinename='" + bean.getCablelinename() + "'";
			}
			if (bean.getApoint() != null && !"".equals(bean.getApoint())) {
				sql += " and c.apoint='" + bean.getApoint() + "'";
			}
			if (bean.getZpoint() != null && !"".equals(bean.getZpoint())) {
				sql += " and c.zpoint='" + bean.getZpoint() + "'";
			}
		}
		sql += " order by c.cableno desc";
		System.out.println("sql::::::::::: " + sql);
		try {
			QueryUtil query = new QueryUtil();
			list = query.queryBeans(sql);
		} catch (Exception e) {
			logger.info("sql " + sql);
			logger.error("query Cable error::::::" + e);
		}
		return list;
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
	public boolean saveExcelGroupcustomerInfo(CableBean hform, String path) {
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
			id = ora.getSeqs("cableinfo", 20, upDataInfo.size());
			String contractorid = "";
			String apoint = "";
			String zpoint = "";
			String isaccept = "0";
			String regionid = "";
			if (upDataInfo.size() == 0) {
				return false;
			}
			for (int i = 0; i < upDataInfo.size(); i++) {
				// 取得集团客户资料表id的序列值
				rowMap = (HashMap) upDataInfo.get(i);
				regionid = this.getRegionidByName(rowMap.get("regionid")
						.toString());
				contractorid = this.getContractoridByName(rowMap.get(
						"contractorid").toString());
				apoint = this.getPointidByName(rowMap.get("apoint").toString());
				zpoint = this.getPointidByName(rowMap.get("zpoint").toString());
				if ("是".equals(rowMap.get("isaccept").toString())) {
					isaccept = "1";
				}

				sql = "insert into cableinfo (id,cableno,contractorid,regionid,area,county,systemname,cablename,cablelinename,apoint,zpoint"
						+ ",laytype,company,construct,property,cabletype,createtime,fibertype,fibernumber,cablelength,unusecable,remark,isaccept,"
						+ "blueprintno,fiberlength) values ('"
						+ id[i]
						+ "','"
						+ rowMap.get("cableno")
						+ "','"
						+ contractorid
						+ "','"
						+ regionid
						+ "','"
						+ rowMap.get("area")
						+ "','"
						+ rowMap.get("county")
						+ "','"
						+ rowMap.get("systemname")
						+ "','"
						+ rowMap.get("cablename")
						+ "','"
						+ rowMap.get("cablelinename")
						+ "','"
						+ apoint
						+ "','"
						+ zpoint
						+ "','"
						+ rowMap.get("laytype")
						+ "','"
						+ rowMap.get("company")
						+ "','"
						+ rowMap.get("construct")
						+ "','"
						+ rowMap.get("property")
						+ "','"
						+ rowMap.get("cabletype")
						+ "','"
						+ rowMap.get("createtime")
						+ "','"
						+ rowMap.get("fibertype")
						+ "','"
						+ rowMap.get("fibernumber")
						+ "','"
						+ rowMap.get("cablelength")
						+ "','"
						+ rowMap.get("unusecable")
						+ "','"
						+ rowMap.get("remark")
						+ "','"
						+ isaccept
						+ "','"
						+ rowMap.get("blueprintno")
						+ "','"
						+ rowMap.get("fiberlength") + "')";
				up.executeUpdate(sql);
				System.out.println("sql is :::::::" + sql);
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
			logger.warn("保存导入的光缆段信息出错:" + e.getMessage());
		}

		return returnFlg;
	}

	/**
	 * 获得上传文件的集团客户内容(有效部分)
	 * 
	 * @param hform
	 *            页面提交的内容
	 * @param path
	 *            存放的临时路径
	 * @return List 文件的内容
	 */
	private List getUpInfo(CableBean hform, String path) {
		// 将文件存入到指定的临时路径
		if (!this.saveFile(hform, path)) {
			return null;
		}

		// 取得Excel文件中客户资料
		ReadExcle read = new ReadExcle(path + "\\fscable.xls");
		return read.getExcleCableList();

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
	private boolean saveFile(CableBean hform, String path) {
		// 判断文件是否存在
		String dir = path;
		FormFile file = hform.getFile();
		if (file == null) {
			return false;
		}
		// 判断文件是否存在，存在删除
		File temfile = new File(dir + "\\fscable.xls");
		if (temfile.exists()) {
			temfile.delete();
		}
		// 保存文件
		try {
			InputStream streamIn = file.getInputStream();
			OutputStream streamOut = new FileOutputStream(dir + "\\fscable.xls");
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

	public String getContractoridByName(String name) {
		String id = "";
		ResultSet rst = null;
		String sql = "select c.contractorid from contractorinfo c where c.contractorname='"
				+ name + "'";
		try {
			QueryUtil query = new QueryUtil();
			rst = query.executeQuery(sql);
			if (rst.next()) {
				id = rst.getString("contractorid");
			}
			rst.close();

		} catch (Exception e) {
			logger.error("query contractorid error:::：" + e);
			try {
				rst.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();

		}
		return id;
	}

	public String getPointidByName(String name) {
		String id = "";
		ResultSet rst = null;
		String sql = "select p.pointid from pointinfo p where p.pointname='"
				+ name + "'";
		try {
			QueryUtil query = new QueryUtil();
			rst = query.executeQuery(sql);
			if (rst.next()) {
				id = rst.getString("pointid");
			}
			rst.close();

		} catch (Exception e) {
			logger.error("query contractorid error:::：" + e);
			try {
				rst.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();

		}
		return id;
	}

	public String getRegionidByName(String name) {
		String regionid = "";
		ResultSet rs = null;
		String sql = "select r.regionid from region r where r.regionname='"
				+ name + "'";
		try {
			QueryUtil query = new QueryUtil();
			rs = query.executeQuery(sql);
			if (rs.next()) {
				regionid = rs.getString("regionid");
			}
		} catch (Exception e) {
			logger.error("query regionid error::" + e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return regionid;
	}
}
