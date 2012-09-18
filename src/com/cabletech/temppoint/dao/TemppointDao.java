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
	// ��־
	private static Logger logger = Logger.getLogger(TemppointDao.class
			.getName());

	/**
	 * ��ȡ�ϴ��ļ�������
	 * 
	 * @param hform
	 *            ������
	 * @param path
	 *            �ϴ��ļ���·��
	 * @return
	 */
	private List getUpInfo(TemppointBean hform, String path) {
		// ���ļ����뵽ָ������ʱ·��
		if (!this.saveFile(hform, path)) {
			return null;
		}

		// ȡ��Excel�ļ��пͻ�����
		ReadExcle read = new ReadExcle(path + "\\temppoint.xls");
		return read.getExcelTemppoint();

	}

	/**
	 * �����ϴ��ļ�
	 * 
	 * @param hform
	 *            �ϴ��ļ��ı�
	 * @param path
	 *            �����ļ���·��
	 * @return
	 */
	private boolean saveFile(TemppointBean hform, String path) {
		// �ж��ļ��Ƿ����
		String dir = path;
		FormFile file = hform.getFile();
		if (file == null) {
			return false;
		}
		// �ж��ļ��Ƿ���ڣ�����ɾ��
		File temfile = new File(dir + "\\temppoint.xls");
		if (temfile.exists()) {
			temfile.delete();
		}
		// �����ļ�
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
			logger.error("������ʱ�㱣���ļ�ʱ����:" + e.getMessage());
			return false;
		}
	}

	/**
	 * ������ʱ���ļ����ݵ����ݿ���
	 * 
	 * @param hform
	 *            �ϴ��ļ��ı�
	 * @param path
	 *            �����ļ���·��
	 * @param rid
	 *            �������ʱ����������
	 * @return
	 */
	public boolean saveTemppointData(TemppointBean hform, String path,
			String rid) {
		// ��ŷ���ֵ
		boolean returnFlg = false;
		// ȡ�õ����Excel�ļ�������
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
			// ����
			up.setAutoCommitFalse();
			// ����ÿһ����ʱ�㣬���뵽���ݿ�
			// id = ora.getSeqs("temppointinfo", 12, 1);
			for (int i = 0; i < upDataInfo.size(); i++) {
				// ȡ����ʱ�����ϱ�id������ֵ
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
			logger.error("����excel���ݵ�����ʱ����", ex);
		}
		return returnFlg;
	}

}
