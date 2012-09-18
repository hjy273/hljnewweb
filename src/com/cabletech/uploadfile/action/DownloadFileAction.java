package com.cabletech.uploadfile.action;

import java.io.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import com.cabletech.commons.base.*;
import com.cabletech.commons.config.*;
import com.cabletech.commons.web.*;
import com.cabletech.uploadfile.dao.*;
import com.cabletech.uploadfile.model.*;
import com.cabletech.watchinfo.util.UploadWatchFile;
import org.apache.log4j.*;

public class DownloadFileAction extends BaseAction{
    private Logger logger = Logger.getLogger(DownloadFileAction.class);
    public ActionForward executeAction( ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response ) throws ClientException, Exception{
        String fileId = request.getParameter( "fileid" );
        String isWatch = request.getParameter( "isWatch" );
        
        if (isWatch == null){
        	isWatch ="0";
        }    
        
        UploadFileInfo fileInfo = null;
        String relativePathFile ="";
        String saveAsT ="";
        String saveAs="";
        String uploadRoot="";
        //String fileSptr = File.separator;
        String fileSptr ="/";
        
        String absolutePathFile="";
        if ("1".equals(isWatch)){
        	UploadWatchFile watchFileInfo = UploadDAO.getWatchFileInfo( fileId );
        	
            relativePathFile = watchFileInfo.getRelativeURL();
            saveAsT = relativePathFile.substring(relativePathFile.lastIndexOf("/")+1);
            saveAs = new String( saveAsT.getBytes( "GB2312" ), "ISO8859_1" );
            GisConInfo gis = GisConInfo.newInstance();
            if (watchFileInfo.getFlag()==2){
            	uploadRoot = gis.getUploadRoot();
            	logger.info("uploadRoot:" + uploadRoot);
                absolutePathFile = uploadRoot + fileSptr + relativePathFile;
                logger.info("absolutePathFile:" + absolutePathFile);
            }else if (watchFileInfo.getFlag()==1){
            	uploadRoot = "http://" + gis.getWatchPicIP() + ":" + gis.getWatchPicPort() + "/"  + gis.getWatchPicDir();
            	logger.info("uploadRoot:" + uploadRoot);
                absolutePathFile = uploadRoot + fileSptr + relativePathFile;
                logger.info("absolutePathFile:" + absolutePathFile);
            }
        }else{
        	fileInfo = UploadDAO.getFileInfo( fileId );
            relativePathFile = fileInfo.getSavePath();
            saveAsT = fileInfo.getOriginalName();
            saveAs = new String( saveAsT.getBytes( "GB2312" ), "ISO8859_1" );
            uploadRoot = GisConInfo.newInstance().getUploadRoot();
            absolutePathFile = uploadRoot + fileSptr + relativePathFile;
        }
        
        File file = new java.io.File( absolutePathFile );
        if( file.exists() ){
            FileInputStream fInputStream = null;
            OutputStream output = null;
            try{
                fInputStream = new FileInputStream( absolutePathFile );
                //获得文件的长度
                long fileSize = file.length();
                //设置输出格式
                response.addHeader( "content-type", "application/x-msdownload;" );
                response.addHeader( "Content-Disposition", "attachment; filename=" + response.encodeURL( saveAs ) );
                response.addHeader( "content-length", Long.toString( fileSize ) );
                output = response.getOutputStream();
                byte[] b = new byte[1024 * 100];
                int j = 0;
                while( ( j = fInputStream.read( b ) ) > 0 ){
                    output.write( b );
                }
                output.close();
                fInputStream.close();
                return null;
            }
            catch( Exception e ){
                logger.error( "SocketException: " + e.getMessage() );
                output.close();
                fInputStream.close();
                return null;
            }
        }
        else{
            MsgInfo errMsg = new MsgInfo();
            errMsg.setInfo( "找不到要下载的文件！" );
            errMsg.setLink( "返回" );
            request.setAttribute( "MESSAGEINFO", errMsg );
            return mapping.findForward( "errorMsg" );
        }
        
        


    }
}
