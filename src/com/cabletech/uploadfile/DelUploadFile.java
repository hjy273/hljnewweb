package com.cabletech.uploadfile;

import java.io.File;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import com.cabletech.commons.config.GisConInfo;
import com.cabletech.uploadfile.dao.UploadDAO;
import com.cabletech.uploadfile.model.UploadFileInfo;

public class DelUploadFile {
	private static Logger logger = Logger.getLogger("DelUploadFile");

	public static boolean delFile(String fileid) {
		String uploadRoot = GisConInfo.newInstance().getUploadRoot();
		if (fileid != null && !fileid.equals("")) {
			UploadFileInfo uploadfile = UploadDAO.getFileInfo(fileid);
			if (uploadfile == null)
				return false;
			//String filePath = uploadfile.getSavePath();
			logger.info("fileid " + fileid + "  filepath " + uploadfile.getSavePath());
			File file = new File(uploadRoot + "\\" + uploadfile.getSavePath());
			UploadDAO.delFileInfo(fileid);
			if (file.exists()) {
				file.delete();
				logger.info("delete file success,the file : " + uploadfile.getSavePath());
				return true;
			} else {
				logger.info("File Not Found ! delete file faile ,the file : " + uploadfile.getSavePath());
				return false;
			}

		} else
			return false;
	}

	public static int delFiles(String[] fileid) {
		int count = 0;
		for (String element : fileid) {
			boolean b = delFile(element);
			if (b) {
				count++;
			}
		}
		return count;
	}

	public static int delFiles(String fileids) {
		int count = 0;
		if (fileids != null && fileids.equals("")) {
			StringTokenizer st = new StringTokenizer(fileids, ",");
			while (st.hasMoreTokens()) {
				delFile(st.nextToken());
				count++;
			}
		}
		return count;
	}
}
