/** create by jixf
 *  date: 2009-08-17
 */

package com.cabletech.pipemanage.dao;

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

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import com.cabletech.commons.exceltemplates.ReadExcle;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.HibernateDaoSupport;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.pipemanage.bean.PipeSegmentBean;

/**
 * 
 * @author Ding Hui Zhen date:2009-08-19
 * 
 */
public class PipeSegmentDao extends HibernateDaoSupport {
	private static Logger logger = Logger.getLogger(PipeSegmentDao.class
			.getName());

	/**
	 * 查询待代维列表
	 * 
	 * @param regoinid
	 * @return
	 */
	public List getContractor(String regoinid) {
		List list = new ArrayList();
		String sql = "select c.contractorid,c.contractorname from contractorinfo c where c.depttype='2' and c.regionid='"
				+ regoinid + "'and state is null ";
		logger.info("sql " + sql);
		try {
			QueryUtil query = new QueryUtil();
			list = query.queryBeans(sql);
		} catch (Exception e) {
			logger.error("query Contractor error::::::" + e);
		}

		return list;
	}

	/**
	 * 查询站点列表
	 * 
	 * @param regionid
	 * @return
	 */
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

	/**
	 * 新增管道端信息
	 * 
	 * @param bean
	 * @return
	 */
	public boolean addPipeSegment(PipeSegmentBean bean) {
		String sql = "insert into pipeinfo (id,pipeno,contractorid,county,area,town,pipename,isaccept,apoint,zpoint"
				+ ",length,material,right,pipehole,pipetype,subpipehole,unuserpipe,remark1,remark2,bluepointno,regionid) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			Connection conn = getSession().connection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, bean.getId());
			stmt.setString(2, bean.getPipeno());
			stmt.setString(3, bean.getContractorid());
			stmt.setString(4, bean.getCounty());
			stmt.setString(5, bean.getArea());
			stmt.setString(6, bean.getTown());
			stmt.setString(7, bean.getPipename());
			stmt.setString(8, bean.getIsaccept());
			stmt.setString(9, bean.getApoint());
			stmt.setString(10, bean.getZpoint());
			stmt.setString(11, bean.getLength());
			stmt.setString(12, bean.getMaterial());
			stmt.setString(13, bean.getRight());
			stmt.setString(14, bean.getPipehole());
			stmt.setString(15, bean.getPipetype());
			stmt.setString(16, bean.getSubpipehole());
			stmt.setString(17, bean.getUnuserpipe());
			stmt.setString(18, bean.getRemark1());
			stmt.setString(19, bean.getRemark2());
			stmt.setString(20, bean.getBluepointno());
			stmt.setString(21, bean.getRegionid());
			stmt.executeUpdate();
			conn.commit();
			return true;
		} catch (Exception e) {
			logger.info("sql:: " + sql);
			logger.error(e);
			return false;
		}

	}

	/**
	 * 查询管道段信息
	 * 
	 * @param bean
	 * @return
	 */
	public List getPipeSegmentBean(PipeSegmentBean bean) {
		List list = new ArrayList();
		String sql = "select  c.id,c.pipeno,ct.contractorname,c.pipename,c.county,c.area,c.pipehole,c.pipetype,c.subpipehole,c.unuserpipe " +
				"from pipeinfo c,contractorinfo ct where ct.depttype='2' and c.contractorid=ct.contractorid ";
		if (bean != null) {
			if (bean.getPipeno() != null && !"".equals(bean.getPipeno())) {
				sql += " and c.pipeno='" + bean.getPipeno() + "'";
			}
			if (bean.getContractorid() != null
					&& !"".equals(bean.getContractorid())) {
				sql += " and c.contractorid='" + bean.getContractorid() + "'";
			}
			if (bean.getPipename() != null && !"".equals(bean.getPipename())) {
				sql += " and c.pipename='" + bean.getPipename() + "'";
			}
		}
		sql += " order by c.pipeno desc";
		logger.info("PIPLE SQL:" + sql);
		try {
			QueryUtil query = new QueryUtil();
			list = query.queryBeans(sql);
		} catch (Exception e) {
			logger.info("sql " + sql);
			logger.error("query PipeSegment error::::::" + e);
		}
		return list;
	}

	/**
	 * 删除管道相关信息
	 * 
	 * @param id
	 * @return
	 */
	public boolean deletePipeSegmentById(String id) {
		String sql = "delete from pipeinfo where id='" + id + "'"; // 删除管道段路信息
		String psql = "delete from pipe2point where pipeid='" + id + "'"; // 删除管道段路由点
		try {
			UpdateUtil exec = new UpdateUtil();
			exec.setAutoCommitFalse();
			exec.executeUpdate(sql);
			exec.executeUpdate(psql);
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
	 * 通过ID查询管道段
	 * 
	 * @param id
	 * @param bean
	 * @return
	 */
	public PipeSegmentBean getPipeSegmentById(String id, PipeSegmentBean bean) {
		ResultSet rst = null;
		String sql = "select a.id,a.pipeno,a.contractorid,a.county,a.area,a.town,a.pipename,a.isaccept,a.apoint,a.zpoint"
				+ ",a.length,a.material,a.right,a.pipehole,a.pipetype,a.subpipehole,a.unuserpipe,a.remark1,a.remark2,a.bluepointno from pipeinfo a where id='"
				+ id + "'";
		try {
			QueryUtil query = new QueryUtil();
			rst = query.executeQuery(sql);
			if (rst.next()) {
				bean.setId(rst.getString("id"));
				bean.setPipeno(rst.getString("pipeno"));
				bean.setContractorid(rst.getString("contractorid"));
				bean.setCounty(rst.getString("county"));
				bean.setArea(rst.getString("area"));
				bean.setTown(rst.getString("town"));
				bean.setPipename(rst.getString("pipename"));
				bean.setIsaccept(rst.getString("isaccept"));
				bean.setApoint(getPointName(rst.getString("apoint")));
				bean.setZpoint(getPointName(rst.getString("zpoint")));
				bean.setLength(rst.getString("length"));
				bean.setMaterial(rst.getString("material"));
				bean.setRight(rst.getString("right"));
				bean.setPipehole(rst.getString("pipehole"));
				bean.setPipetype(rst.getString("pipetype"));
				bean.setSubpipehole(rst.getString("subpipehole"));
				bean.setUnuserpipe(rst.getString("unuserpipe"));
				bean.setRemark1(rst.getString("remark1"));
				bean.setRemark2(rst.getString("remark2"));
				bean.setBluepointno(rst.getString("bluepointno"));
			}

		} catch (Exception e) {
			logger.error("载入一站点信息时异常：" + e);
		} finally {
			try {
				rst.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		return bean;
	}

	/**
	 * 修改管道端信息
	 * 
	 * @param bean
	 * @return
	 */
	public boolean updatePipeSegment(PipeSegmentBean bean) {
		String sql = "update pipeinfo set pipeno=?,contractorid=?,county=?,area=?,town=?,pipename=?"
				+ ",isaccept=?,length=?,material=?,right=?,pipehole=?,pipetype=?,subpipehole=?,unuserpipe=?,remark1=?,remark2=?,bluepointno=? where id=?";
		try {
			Connection conn = getSession().connection();
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, bean.getPipeno());
			stmt.setString(2, bean.getContractorid());
			stmt.setString(3, bean.getCounty());
			stmt.setString(4, bean.getArea());
			stmt.setString(5, bean.getTown());
			stmt.setString(6, bean.getPipename());
			stmt.setString(7, bean.getIsaccept());
			stmt.setString(8, bean.getLength());
			stmt.setString(9, bean.getMaterial());
			stmt.setString(10, bean.getRight());
			stmt.setString(11, bean.getPipehole());
			stmt.setString(12, bean.getPipetype());
			stmt.setString(13, bean.getSubpipehole());
			stmt.setString(14, bean.getUnuserpipe());
			stmt.setString(15, bean.getRemark1());
			stmt.setString(16, bean.getRemark2());
			stmt.setString(17, bean.getBluepointno());
			stmt.setString(18, bean.getId());
			stmt.executeUpdate();
			conn.commit();
			return true;
		} catch (Exception e) {
			logger.info("sql:: " + sql);
			logger.error("update cableinfo error:::::" + e);
			return false;
		}
	}

	public List getExportInfo(PipeSegmentBean bean) {
		List list = new ArrayList();
		String sql = "select c.id,c.pipeno,ct.contractorname,c.area,c.county,c.town,c.pipename,p1.pointname apoint,p2.pointname zpoint,decode(c.isaccept,'1','是','0','否','否') isaccept,to_char(c.length) length"
				+ ",c.material,c.right,to_char(c.pipehole) pipehole,c.pipetype,to_char(c.subpipehole) subpipehole,to_char(c.unuserpipe) unuserpipe,c.remark1,c.remark2,c.bluepointno from pipeinfo c,contractorinfo ct,pointinfo p1,pointinfo p2 where c.contractorid=ct.contractorid and p1.pointid=c.apoint and p2.pointid=c.zpoint";
		if (bean != null) {
			if (bean.getPipeno() != null && !"".equals(bean.getPipeno())) {
				sql += " and c.pipeno='" + bean.getPipeno() + "'";
			}
			if (bean.getContractorid() != null
					&& !"".equals(bean.getContractorid())) {
				sql += " and c.contractorid='" + bean.getContractorid() + "'";
			}
			if (bean.getPipename() != null && !"".equals(bean.getPipename())) {
				sql += " and c.pipename='" + bean.getPipename() + "'";
			}
			if (bean.getApoint() != null && !"".equals(bean.getApoint())) {
				sql += " and c.apoint='" + bean.getApoint() + "'";
			}
			if (bean.getZpoint() != null && !"".equals(bean.getZpoint())) {
				sql += " and c.zpoint='" + bean.getZpoint() + "'";
			}
		}
		sql += " order by c.pipeno desc";
		System.out.println("sql::::::::::: " + sql);
		try {
			QueryUtil query = new QueryUtil();
			list = query.queryBeans(sql);
		} catch (Exception e) {
			logger.info("sql " + sql);
			logger.error("query PipeSegment error::::::" + e);
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
	public boolean saveExcelGroupcustomerInfo(PipeSegmentBean hform, String path) {
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
			id = ora.getSeqs("pipeinfo", 20, upDataInfo.size());
			String contractorid = "";
			String apoint = "";
			String zpoint = "";
			String isaccept = "0";
			String regionid = "";
			for (int i = 0; i < upDataInfo.size(); i++) {
				// 取得管道段id的序列值
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
				sql = "insert into pipeinfo (id,pipeno,contractorid,regionid,county,area,town,pipename,isaccept,apoint,zpoint"
						+ ",length,material,right,pipehole,pipetype,subpipehole,unuserpipe,remark1,remark2,bluepointno) values ('"
						+ id[i]
						+ "','"
						+ rowMap.get("pipeno")
						+ "','"
						+ contractorid
						+ "','"
						+ regionid
						+ "','"
						+ rowMap.get("county")
						+ "','"
						+ rowMap.get("area")
						+ "','"
						+ rowMap.get("town")
						+ "','"
						+ rowMap.get("pipename")
						+ "','"
						+ isaccept
						+ "','"
						+ apoint
						+ "','"
						+ zpoint
						+ "','"
						+ rowMap.get("length")
						+ "','"
						+ rowMap.get("material")
						+ "','"
						+ rowMap.get("right")
						+ "','"
						+ rowMap.get("pipehole")
						+ "','"
						+ rowMap.get("pipetype")
						+ "','"
						+ rowMap.get("subpipehole")
						+ "','"
						+ rowMap.get("unuserpipe")
						+ "','"
						+ rowMap.get("remark1")
						+ "','"
						+ rowMap.get("remark2")
						+ "','" + rowMap.get("bluepointno") + "')";
				up.executeUpdate(sql);
				System.out.println("sql is :::::::" + sql);
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
			logger.warn("保存导入的管道段信息出错:" + e.getMessage());
		}

		return returnFlg;
	}

	/**
	 * 获得上传文件的管道段信息(有效部分)
	 * 
	 * @param hform
	 *            页面提交的内容
	 * @param path
	 *            存放的临时路径
	 * @return List 文件的内容
	 */
	private List getUpInfo(PipeSegmentBean hform, String path) {
		// 将文件存入到指定的临时路径
		if (!this.saveFile(hform, path)) {
			return null;
		}

		// 取得Excel文件中客户资料
		try {
			ReadExcle read = new ReadExcle(path + "\\pipeinfo.xls");
			return read.getExclePipeSegmentList();
		} catch (Exception e) {
			logger.error("read error:" + e);
			return null;
		}

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
	private boolean saveFile(PipeSegmentBean hform, String path) {
		// 判断文件是否存在
		String dir = path;
		FormFile file = hform.getFile();
		if (file == null) {
			return false;
		}
		// 判断文件是否存在，存在删除
		File temfile = new File(dir + "\\pipeinfo.xls");
		if (temfile.exists()) {
			temfile.delete();
		}
		// 保存文件
		try {
			InputStream streamIn = file.getInputStream();
			OutputStream streamOut = new FileOutputStream(dir
					+ "\\pipeinfo.xls");
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = streamIn.read(buffer, 0, 8192)) != -1) {
				streamOut.write(buffer, 0, bytesRead);
			}
			streamOut.close();
			streamIn.close();
			return true;
		} catch (Exception e) {
			logger.error("导入管道段保存文件时出错:" + e.getMessage());
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
