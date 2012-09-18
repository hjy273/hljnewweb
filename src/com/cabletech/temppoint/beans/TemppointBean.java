package com.cabletech.temppoint.beans;

import org.apache.struts.upload.FormFile;

import com.cabletech.commons.base.BaseBean;

public class TemppointBean extends BaseBean {
	// 为上传文件添加
	private FormFile file = null;

	private String filename = "";

	private String filesize = "";

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilesize() {
		return filesize;
	}

	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}

}
