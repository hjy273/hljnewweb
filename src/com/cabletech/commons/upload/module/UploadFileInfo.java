package com.cabletech.commons.upload.module;

import org.apache.commons.lang.builder.*;

public class UploadFileInfo{
    private String fileId;
    private String savePath;
    private String fileType;
    private long fileSize;
    private String originalName;
    private String description;
    private String catlog;
    
    public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCatlog() {
		return catlog;
	}

	public void setCatlog(String catlog) {
		this.catlog = catlog;
	}

	public String toString(){
        return new ToStringBuilder( this, ToStringStyle.MULTI_LINE_STYLE )
            .append( "fileId", this.fileId )
            .append( "fileType", this.fileType )
            .append( "fileSize", this.fileSize )
            .append( "originalName", this.originalName )
            .append( "description", this.description)
            .append( "catlog", this.catlog )
            .append( "fileSize", this.fileSize ).toString();

    }

}
