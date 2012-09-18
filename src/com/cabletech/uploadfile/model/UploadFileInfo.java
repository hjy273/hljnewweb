package com.cabletech.uploadfile.model;

import org.apache.commons.lang.builder.*;

public class UploadFileInfo{
    private String fileId;
    private String savePath;
    private String fileType;
    private long fileSize;
    private String originalName;
    private String description;
    private String catlog;
    public String getCatlog(){
        return catlog;
    }


    public String getDescription(){
        return description;
    }


    public String getFileId(){
        return fileId;
    }


    public long getFileSize(){
        return fileSize;
    }


    public String getFileType(){
        return fileType;
    }


    public String getOriginalName(){
        return originalName;
    }


    public String getSavePath(){
        return savePath;
    }


    public void setCatlog( String catlog ){
        this.catlog = catlog;
    }


    public void setDescription( String description ){
        this.description = description;
    }


    public void setFileId( String fileId ){
        this.fileId = fileId;
    }


    public void setFileSize( long fileSize ){
        this.fileSize = fileSize;
    }


    public void setFileType( String fileType ){
        this.fileType = fileType;
    }


    public void setOriginalName( String originalName ){
        this.originalName = originalName;
    }


    public void setSavePath( String savePath ){
        this.savePath = savePath;
    }


    public String toString(){
        return new ToStringBuilder( this, ToStringStyle.MULTI_LINE_STYLE )
            .append( "fileId", this.fileId )
            .append( "fileType", this.fileType )
            .append( "fileSize", this.fileSize )
            .append( "originalName", this.originalName )
            .append( "description", this.description )
            .append( "catlog", this.catlog )
            .append( "fileSize", this.fileSize ).toString();

    }

}
