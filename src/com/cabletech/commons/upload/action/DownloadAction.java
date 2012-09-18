package com.cabletech.commons.upload.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.config.MsgInfo;
import com.cabletech.commons.upload.module.UploadFileInfo;
import com.cabletech.commons.upload.service.UploadFileService;
import com.cabletech.commons.web.ClientException;

public class DownloadAction extends BaseDispatchAction{
    private Logger logger = Logger.getLogger(DownloadAction.class);
    public ActionForward download( ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response ) throws ClientException, Exception{
        String fileId = request.getParameter( "fileid" );
        WebApplicationContext ctx = getWebApplicationContext();
        UploadFileService uploadFileService = (UploadFileService)ctx.getBean("uploadFileService");
        
        UploadFileInfo fileInfo = null;
        String relativePathFile ="";
        String saveAsT ="";
        String saveAs="";
        //String fileSptr = File.separator;
        String fileSptr ="/";
        
        String absolutePathFile="";
        
        fileInfo = uploadFileService.getFileId( fileId );
        relativePathFile = fileInfo.getSavePath();
        saveAsT = fileInfo.getOriginalName();
        saveAs = new String( saveAsT.getBytes( "GB2312" ), "ISO8859_1" );
        absolutePathFile = uploadFileService.UPLOADROOT + fileSptr + relativePathFile;
        logger.info("filePath : "+absolutePathFile);
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
            errMsg.setInfo( saveAsT+"文件已经不存在，无法下载！" );
            errMsg.setLink( "返回" );
            request.setAttribute( "MESSAGEINFO", errMsg );
            return mapping.findForward( "errorMsg" );
        }
        
        


    }
}