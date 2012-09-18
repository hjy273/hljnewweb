package com.cabletech.commons.tags;

import java.io.*;
import java.util.*;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import com.cabletech.commons.config.GisConInfo;
import com.cabletech.uploadfile.dao.*;
import com.cabletech.uploadfile.model.*;
import com.cabletech.watchinfo.util.UploadWatchFile;

public class AttachmentTag extends TagSupport{

    private String fileIdList;
    private String showdele = "false";
    private String deleteSuffix="";
    

    /**
     * Generate the required input tag.
     * <p>
     * Support for indexed property since Struts 1.1
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException{
        StringBuffer results = new StringBuffer();
        String linkItem="";
        List fileList = null;
        UploadFileInfo fileInfo;
        System.out.println("in doStartTag,fileIdList is:" + fileIdList);
        if (fileIdList.endsWith("*")){
        	if (fileIdList.length() > 1){
        		fileIdList = fileIdList.substring(0, fileIdList.length() -2);
            	System.out.println("fileIdList is:" + fileIdList);
            	fileList = UploadDAO.getWatchFileInfos( fileIdList );
        	}
	        if( fileList == null || fileList.size() < 1 ){
	            results.append( "<div>ÎÞ¸½¼þ</div>" );
	        }
	        else{
	            Iterator iter = fileList.iterator();
	            int i = 0;
	            UploadWatchFile watchFileInfo = null;
                String url="";
	            while( iter.hasNext() ){
	            	watchFileInfo = ( UploadWatchFile )iter.next();
	            	url = watchFileInfo.getRelativeURL();
	                if( showdele.equals( "false" ) || showdele.equals( "" ) || showdele == null ){
	                    linkItem = "<a href='/WebApp/downloadAction.do?isWatch=1&fileid="
	                               + watchFileInfo.getFileId()
	                               + "'><img border='0' src='/WebApp/images/attachment.gif' width='15' height='15'>"
	                               + url.substring(url.lastIndexOf("/")+1) + "</a>";
	                }
	                else{
	                	if (watchFileInfo.getFlag() == 2){
	                		linkItem = "<input type='checkbox'  name='delfileid"+deleteSuffix+"'  value=" + watchFileInfo.getFileId() + " />É¾³ý"
                            + " &nbsp&nbsp&nbsp&nbsp<a href='/WebApp/downloadAction.do?isWatch=1&fileid="
                            + watchFileInfo.getFileId()
                            + "'><img border='0' src='/WebApp/images/attachment.gif' width='15' height='15'>"
                            + url.substring(url.lastIndexOf("/")+1) + "</a>";
	                	}else if (watchFileInfo.getFlag() == 1){
	                        String uploadRoot="";
	                        String fileSptr = File.separator;
	                        String absolutePathFile="";
	                		GisConInfo gis = GisConInfo.newInstance();
	                		uploadRoot = "http://" + gis.getWatchPicIP() + ":" + gis.getWatchPicPort() + "/"  + gis.getWatchPicDir();
	                        absolutePathFile = uploadRoot + fileSptr + url;
	                        System.out.println("absolutePathFile:" + absolutePathFile);
	                		linkItem = "<input type='checkbox'  name='delfileid"+deleteSuffix+"'  value=" + watchFileInfo.getFileId() + " />É¾³ý"
		                            + " &nbsp&nbsp&nbsp&nbsp<a href='" + absolutePathFile + "' target='_blank'><img border='0' src='/WebApp/images/attachment.gif' width='15' height='15'>"
		                            + url.substring(url.lastIndexOf("/")+1) + "</a>";
	                	}
	                    
	                }
	                if( i < 1 ){
	                    results.append( linkItem );
	                }
	                else{
	                    results.append( "<br/>" + linkItem );
	                }
	                i++;
	            }
	        }
        }else{
	        fileList = UploadDAO.getFileInfos( fileIdList );
	        if( fileList == null || fileList.size() < 1 ){
	            results.append( "<div>ÎÞ¸½¼þ</div>" );
	        }
	        else{
	            Iterator iter = fileList.iterator();
	            int i = 0;
	            while( iter.hasNext() ){
	                fileInfo = ( UploadFileInfo )iter.next();
	                if( showdele.equals( "false" ) || showdele.equals( "" ) || showdele == null ){
	                    linkItem = "<a href='/WebApp/downloadAction.do?fileid="
	                               + fileInfo.getFileId()
	                               + "'><img border='0' src='/WebApp/images/attachment.gif' width='15' height='15'>"
	                               + fileInfo.getOriginalName() + "</a>";
	                }
	                else{
	                    linkItem = "<input type='checkbox'  name='delfileid"+deleteSuffix+"'  value=" + fileInfo.getFileId() + " />É¾³ý"
	                               + " &nbsp&nbsp&nbsp&nbsp<a href='/WebApp/downloadAction.do?fileid="
	                               + fileInfo.getFileId()
	                               + "'><img border='0' src='/WebApp/images/attachment.gif' width='15' height='15'>"
	                               + fileInfo.getOriginalName() + "</a>";
	                }
	                if( i < 1 ){
	                    results.append( linkItem );
	                }
	                else{
	                    results.append( "<br/>" + linkItem );
	                }
	                i++;
	            }
	        }
        }
        try{
            this.pageContext.getOut().print( results.toString() );
        }
        catch( IOException ex ){
            ex.printStackTrace();
        }
        return this.SKIP_BODY;
    }


    /**
     * Release any acquired resources.
     */
    public void release(){
        super.release();
    }


    public void setFileIdList( String fileIdList ){
        this.fileIdList = fileIdList;
    }


    public void setShowdele( String showdele ){
        this.showdele = showdele;
    }


    public String getFileIdList(){
        return fileIdList;
    }


    public String getShowdele(){
        return showdele;
    }


    public String getDeleteSuffix() {
        return deleteSuffix;
    }


    public void setDeleteSuffix(String deleteSuffix) {
        this.deleteSuffix = deleteSuffix;
    }

}
