package com.cabletech.watchinfo.util;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class UploadWatchFile {
    private String fileId;
    private String relativeURL;
    private String fileName;
    private int flag;
    private String remark;
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getRelativeURL() {
		return relativeURL;
	}
	public void setRelativeURL(String relativeURL) {
		this.relativeURL = relativeURL;
	}
	
    public String toString(){
        return new ToStringBuilder( this, ToStringStyle.MULTI_LINE_STYLE )
            .append( "fileId", this.fileId )
            .append( "relativeURL", this.relativeURL )
            .append( "fileName", this.fileName )
            .append( "remark", this.remark )
            .append( "flag", this.flag ).toString();

    }
}
