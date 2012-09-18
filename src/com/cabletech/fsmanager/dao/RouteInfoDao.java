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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;


import com.cabletech.commons.exceltemplates.ReadExcle;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.HibernateDaoSupport;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.fsmanager.bean.RouteInfoBean;
import com.cabletech.fsmanager.domainobjects.RouteInfo;
import com.cabletech.groupcustomer.bean.GroupCustomerBean;

public class RouteInfoDao extends HibernateDaoSupport{
	 private static Logger logger =
	        Logger.getLogger( RouteInfoDao.class.getName() );
	 /**
	  * 添加路由信息
	  * @param bean
	  * @return
	  */
	 public boolean addRouteInfo(RouteInfoBean bean){
			String sql = "insert into routeinfo(id,routename,regionid) values(?,?,?)";
			try{
				Connection conn = getSession().connection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1,bean.getId());
				stmt.setString(2,bean.getRouteName());
				stmt.setString(3,bean.getRegionID());
				stmt.executeUpdate();
				conn.commit();
				return true;
			}catch(Exception e){
				logger.error(e);
				return false;
			}
		}
	 /**
	  * 获取路由信息列表
	  * @param bean
	  * @return
	  */
		public List getRouteInfoBean(RouteInfoBean bean){
			List list = new ArrayList();
			String sql = "select a.id,a.routename,b.regionname regionid from routeinfo a,region b where a.regionid=b.regionid(+)";
			if(bean!=null){
				if(bean.getId()!=null && !"".equals(bean.getId())){
					sql+=" and a.id='"+bean.getId()+"'";
				}
				if(bean.getRouteName()!=null && !"".equals(bean.getRouteName())){
					sql+=" and a.routename like '"+"%"+bean.getRouteName()+"%"+"'";
				}
				if(bean.getRegionID()!=null && !"".equals(bean.getRegionID())){
					sql+=" and a.regionid='"+bean.getRegionID()+"'";
				}
			}
			sql += " order by a.id desc";
			System.out.println("sql::::::::::"+sql);
			try{
				QueryUtil query = new QueryUtil();
				list = query.queryBeans(sql);
			}catch(Exception e){
				logger.error("query routeinfo error:"+e);
			}
			return list;
		}
		 /**
		  * 删除路由信息
		  * @param id
		  * @return
		  */
		public boolean deleteRouteInfoById(String id){
			String sql = "delete from routeinfo where id='"+id+"'";
			try{
				UpdateUtil exec = new UpdateUtil();
				exec.setAutoCommitFalse();
				exec.executeUpdate(sql);
				exec.commit();
				exec.setAutoCommitTrue();
				return true;
			}catch(Exception e){
				logger.error("delete routeinfo error:"+e);
				return false;
			}
		}
		/**
		 * 载入路由信息
		 * @param id
		 * @param bean
		 * @return
		 */
		public RouteInfoBean getRouteInfoById(String id,RouteInfoBean bean){
			String sql = "select a.id,a.routename,a.regionid from routeinfo a where id='"+id+"'";
			ResultSet rs=null;
			try{
				QueryUtil query = new QueryUtil();
				rs = query.executeQuery(sql);
				if(rs.next()){
					bean.setId(rs.getString("id"));
					bean.setRouteName(rs.getString("routename"));
					bean.setRegionID(rs.getString("regionid"));
				}
				rs.close();
			}catch(Exception e){
				logger.error(e);
				try{
					rs.close();
				}catch(SQLException e1){
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
			return bean;
		}
		/**
		 * 修改路由信息
		 * @param bean
		 * @return
		 */
		public boolean updateRouteInfo(RouteInfoBean bean){
			String sql="update routeinfo set routename=?,regionid=? where id=?";
			try{
				Connection conn = getSession().connection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1,bean.getRouteName());
				stmt.setString(2,bean.getRegionID());
				stmt.setString(3,bean.getId());
				stmt.executeUpdate();
				conn.commit();
				return true;
			}catch(Exception e){
				logger.error(e);
				return false;
			}
		}
	    //保存为excel临时文件
	    private boolean saveFile(RouteInfoBean hform, String path) {
			// 判断文件是否存在
			String dir = path;
			FormFile file = hform.getFile();
			if (file == null) {
				return false;
			}
			// 判断文件是否存在，存在删除
			File temfile = new File(dir + "\\routeinfo.xls");
			if (temfile.exists()) {
				temfile.delete();
			}
			// 保存文件
			try {
				InputStream streamIn = file.getInputStream();
				OutputStream streamOut = new FileOutputStream(dir
						+ "\\routeinfo.xls");
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
	    
	    private List getUpInfo(RouteInfoBean hform, String path) {
			// 将文件存入到指定的临时路径
			if (!this.saveFile(hform, path)) {
				return null;
			}

			// 取得Excel文件中路由信息
			ReadExcle read = new ReadExcle(path + "\\routeinfo.xls");
			return read.getExcleRouteInfo();

		}

	    //保存excel数据到数据库
	    public boolean saveExcelRouteInfo(RouteInfoBean hform,
				String path) {
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
				id = ora.getSeqs("routeinfo",20, upDataInfo.size());
				String regionid = "";
				for (int i = 0; i < upDataInfo.size(); i++) {
					// 取得路由信息表id的序列值
					rowMap = (HashMap) upDataInfo.get(i);
					regionid = this.getRegionidByName(rowMap.get("regionid").toString());
					sql = "insert into routeinfo (id,routename,regionid "
							+ " ) values ( '" + id[i] + "', '"
							+ rowMap.get("routename") + "' , '" + regionid
							+ "')";
					up.executeUpdate(sql);
					logger.info("sql:"+sql);
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

	    public String getRegionidByName(String name){
			 String regionid = "";
			 ResultSet rs = null;
			 String sql="select r.regionid from region r where r.regionname='"+name+"'";
			 try{
				 QueryUtil query = new QueryUtil();
				 rs  = query.executeQuery(sql);
				 if(rs.next()){
					 regionid = rs.getString("regionid");
				 }
			 }catch(Exception e){
				 logger.error("query regionid error::"+e);
			 }finally{
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
