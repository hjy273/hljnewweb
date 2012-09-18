package com.cabletech.uploadfile;

import java.io.Serializable;

import org.apache.struts.upload.FormFile;

public class UploadFile implements Serializable {
	private FormFile file;
	private String remark;//

	public FormFile getFile() {
		//       //System.out.println( "run in uploadFile.getFile()" );
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
