package com.cabletech.temppoint.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import com.cabletech.commons.exceltemplates.ReadExcle;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.commons.util.GeolocTik;
import com.cabletech.temppoint.beans.TemppointBean;

public class TemppointDao {
	// 日志
	private static Logger logger = Logger.getLogger(TemppointDao.class
			.getName());

	/**
	 * 获取上传文件的内容
	 * 
	 * @param hform
	 *            表单内容
	 * @param path
	 *            上传文件的路径
	 * @return
	 */
	private List getUpInfo(TemppointBean hform, String path) {
		// 将文件存入到指定的临时路径
		if (!this.saveFile(hform, path)) {
			return null;
		}

		// 取得Excel文件中客户资料
		ReadExcle read = new ReadExcle(path + "\\temppoint.xls");
		return read.getExcelTemppoint();

	}

	/**
	 * 保存上传文件
	 * 
	 * @param hform
	 *            上传文件的表单
	 * @param path
	 *            保存文件的路径
	 * @return
	 */
	private boolean saveFile(TemppointBean hform, String path) {
		// 判断文件是否存在
		String dir = path;
		FormFile file = hform.getFile();
		if (file == null) {
			return false;
		}
		// 判断文件是否存在，存在删除
		File temfile = new File(dir + "\\temppoint.xls");
		if (temfile.exists()) {
			temfile.delete();
		}
		// 保存文件
		try {
			InputStream streamIn = file.getInputStream();
			OutputStream streamOut = new FileOutputStream(dir
					+ "\\temppoint.xls");
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = streamIn.read(buffer, 0, 8192)) != -1) {
				streamOut.write(buffer, 0, bytesRead);
			}
			streamOut.close();
			streamIn.close();
			return true;
		} catch (Exception e) {
			logger.error("导入临时点保存文件时出错:" + e.getMessage());
			return false;
		}
	}

	/**
	 * 保存临时点文件数据到数据库中
	 * 
	 * @param hform
	 *            上传文件的表单
	 * @param path
	 *            保存文件的路径
	 * @param rid
	 *            导入的临时点所在区域
	 * @return
	 */
	public boolean saveTemppointData(TemppointBean hform, String path,
			String rid) {
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
		String id;
		String gpscoordinate = "";
		GeolocTik geoloc = new GeolocTik();

		try {
			up = new UpdateUtil();
			// 事务
			up.setAutoCommitFalse();
			// 遍历每一条临时点，插入到数据库
			// id = ora.getSeqs("temppointinfo", 12, 1);
			for (int i = 0; i < upDataInfo.size(); i++) {
				// 取得临时点资料表id的序列值
				id = ora.getSeq("temppointinfo", 12);
				rowMap = (HashMap) upDataInfo.get(i);
				gpscoordinate = geoloc.setGpsString(rowMap.get("x").toString(),
						rowMap.get("y").toString());
				sql = "insert into temppointinfo(pointid,gpscoordinate,regionid,simid,receivetime,bedited,pointname,geoloc) values('"
						+ id
						+ "','"
						+ gpscoordinate
						+ "','"
						+ rid
						+ "','"
						+ rowMap.get("sim")
						+ "',sysdate,'0','"
						+ rowMap.get("pointname")
						+ "',STR_TO_GEO_POINT('"
						+ gpscoordinate + "'))";
				logger.info(sql);
				up.executeUpdate(sql);
			}
			up.commit();
			up.setAutoCommitTrue();
			returnFlg = true;
		} catch (Exception ex) {
			logger.error("保存excel数据到数据时出错：", ex);
		}
		return returnFlg;
	}

}
