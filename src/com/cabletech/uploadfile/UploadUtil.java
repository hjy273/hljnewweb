package com.cabletech.uploadfile;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;

public class UploadUtil {
	public static String getFileIdList(List lst) {
		StringBuffer strBuf = new StringBuffer();
		Iterator itr = lst.iterator();
		String fileId;
		int i = 0;
		while (itr.hasNext()) {
			fileId = (String) itr.next();
			if (i < 1) {
				strBuf.append(fileId);
			} else {
				strBuf.append("," + fileId);
			}
			i++;
		}
		if (i < 1)
			return null;
		else
			return strBuf.toString();
	}

	public static String getFileIdsList(List lst, String columnName) {
		StringBuffer strBuf = new StringBuffer();
		Iterator itr = lst.iterator();
		String fileId;
		int i = 0;
		DynaBean bean;
		while (itr.hasNext()) {
			bean = (DynaBean) itr.next();
			fileId = (String) bean.get(columnName);
			if (i < 1) {
				strBuf.append(fileId);
			} else {
				strBuf.append("," + fileId);
			}
			i++;
		}
		if (i < 1)
			return "";
		else
			return strBuf.toString();
	}
}
