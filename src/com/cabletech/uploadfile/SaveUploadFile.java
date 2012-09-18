package com.cabletech.uploadfile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import com.cabletech.commons.config.GisConInfo;
import com.cabletech.uploadfile.dao.UploadDAO;

//import org.apache.commons.fileupload.FileItem;

public class SaveUploadFile {
	private static Logger logger = Logger.getLogger("SaveUploadFile");

	public static String saveFile(FormFile formFile) {
		String fileId = null;
		String uploadRoot = GisConInfo.newInstance().getUploadRoot();
		int fileSize = formFile.getFileSize();
		if (fileSize < 1)
			return null;
		String fileName = formFile.getFileName();

		logger.debug("FileName=" + fileName + " FileSize=" + fileSize);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/ddHHmmss");
		String strTemp = formatter.format(new Date());
		String fileSptr = File.separator;
		String saveAsName = strTemp.substring(8, 16) + FileSequence.getSeq() + fileName;
		String relativePath = strTemp.substring(0, 4) + fileSptr + strTemp.subSequence(5, 7);
		String relativePathFile = relativePath + fileSptr + saveAsName;
		String absolutePath = uploadRoot + fileSptr + relativePath;
		String absolutePathFile = absolutePath + fileSptr + saveAsName;

		boolean success = (new File(absolutePath)).mkdirs();
		if (!success) {
			logger.error("path create fail!!!");
		} else {
			logger.error("path create success!");
		}

		logger.info("Path=" + absolutePath);
		try {
			//获取输入流
			InputStream stream = formFile.getInputStream();
			//将上传文件写指定路径

			File file = new File(absolutePathFile);
			logger.info("File=" + absolutePathFile);
			// Create file if it does not exist
			success = file.createNewFile();
			if (success) {
				OutputStream bos = new FileOutputStream(absolutePathFile);
				int bytesRead = 0;
				byte[] buffer = new byte[8192];
				while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
					bos.write(buffer, 0, bytesRead);
				}
				bos.close();
				logger.info("The file has been written to \"" + absolutePathFile + "\"");
				//close the stream
				stream.close();
				//将文件路径及相关属性写入数据库表
				fileId = UploadDAO.saveFileInfo(relativePathFile, fileName, "type", fileSize, "desc", "xx");
			} else {
				logger.error("文件已经存在。");
			}

		} catch (FileNotFoundException fnfe) {
			logger.error("找不到文件", fnfe);
		} catch (IOException ioe) {
			logger.error("IO错误", ioe);
		}

		return fileId;
	}

	public static String saveFile(FormFile formFile, String regionid, String categoryname) {
		String fileId = null;
		String uploadRoot = GisConInfo.newInstance().getUploadRoot();
		int fileSize = formFile.getFileSize();
		if (fileSize < 1)
			return null;
		String fileName = formFile.getFileName();

		logger.debug("FileName=" + fileName + " FileSize=" + fileSize);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/ddHHmmss");
		String strTemp = formatter.format(new Date());
		String fileSptr = File.separator;
		logger.info("fileSptr:" + fileSptr);
		String saveAsName = strTemp.substring(8, 16) + FileSequence.getSeq() + fileName;
		logger.info("saveAsName:" + saveAsName);
		String relativePath = regionid;
		logger.info("relativePath:" + relativePath);
		if (!categoryname.equals("")) {
			relativePath += fileSptr + categoryname;
			logger.info("relativePath:" + relativePath);
		}
		String relativePathFile = relativePath + fileSptr + saveAsName;
		logger.info("relativePathFile" + relativePathFile);
		String absolutePath = uploadRoot + fileSptr + relativePath;
		logger.info("absolutePath" + absolutePath);
		String absolutePathFile = absolutePath + fileSptr + saveAsName;
		logger.info("absolutePathFile" + absolutePathFile);

		boolean success = (new File(absolutePath)).mkdirs();
		if (!success) {
			logger.error("path create fail!!!");
		} else {
			logger.error("path create success!");
		}

		logger.info("Path=" + absolutePath);
		try {
			//获取输入流
			InputStream stream = formFile.getInputStream();
			//将上传文件写指定路径

			File file = new File(absolutePathFile);
			logger.info("File=" + absolutePathFile);
			// Create file if it does not exist
			success = file.createNewFile();
			if (success) {
				OutputStream bos = new FileOutputStream(absolutePathFile);
				int bytesRead = 0;
				byte[] buffer = new byte[8192];
				while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
					bos.write(buffer, 0, bytesRead);
				}
				bos.close();
				logger.info("The file has been written to \"" + absolutePathFile + "\"");
				//close the stream
				stream.close();
				//将文件路径及相关属性写入数据库表
				fileId = UploadDAO.saveFileInfo(relativePathFile, fileName, "type", fileSize, "desc", "xx");
			} else {
				logger.error("文件已经存在。");
			}

		} catch (FileNotFoundException fnfe) {
			logger.error("找不到文件", fnfe);
		} catch (IOException ioe) {
			logger.error("IO错误", ioe);
		}

		return fileId;
	}

	public static String saveFile(FormFile formFile, String picDir) {
		String fileId = null;
		String uploadRoot = GisConInfo.newInstance().getUploadRoot();
		int fileSize = formFile.getFileSize();
		if (fileSize < 1)
			return null;
		String fileName = formFile.getFileName();

		logger.debug("FileName=" + fileName + " FileSize=" + fileSize);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/ddHHmmss");
		String strTemp = formatter.format(new Date());
		//        String fileSptr = File.separator;
		String fileSptr = "/"; //http统一访问方式，与平台无关
		String saveAsName = strTemp.substring(8, 16) + FileSequence.getSeq() + fileName;
		String relativePath = "";
		if (!picDir.equals("")) {
			relativePath += picDir;
		}
		String relativePathFile = relativePath + fileSptr + saveAsName;
		logger.info("relativePathFile" + relativePathFile);
		String absolutePath = uploadRoot + fileSptr + relativePath;
		logger.info("absolutePath" + absolutePath);
		String absolutePathFile = absolutePath + fileSptr + saveAsName;
		logger.info("absolutePathFile" + absolutePathFile);

		boolean success = (new File(absolutePath)).mkdirs();
		if (!success) {
			logger.error("path create fail!!!");
		} else {
			logger.error("path create success!");
		}

		logger.info("Path=" + absolutePath);
		try {
			//获取输入流
			InputStream stream = formFile.getInputStream();
			//将上传文件写指定路径

			File file = new File(absolutePathFile);
			logger.info("File=" + absolutePathFile);
			// Create file if it does not exist
			success = file.createNewFile();
			if (success) {
				OutputStream bos = new FileOutputStream(absolutePathFile);
				int bytesRead = 0;
				byte[] buffer = new byte[8192];
				while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
					bos.write(buffer, 0, bytesRead);
				}
				bos.close();
				logger.info("The file has been written to \"" + absolutePathFile + "\"");
				//close the stream
				stream.close();
				//将文件路径及相关属性写入数据库表
				//fileId = UploadDAO.saveFileInfo( relativePathFile, fileName, "type", fileSize, "desc", "xx" );
			} else {
				logger.error("文件已经存在。");
				relativePathFile = "";
			}

		} catch (FileNotFoundException fnfe) {
			logger.error("找不到文件", fnfe);
			relativePathFile = "";
		} catch (IOException ioe) {
			logger.error("IO错误", ioe);
			relativePathFile = "";
		}

		return relativePathFile;
	}
	/*
	     public static String saveFile( FileItem item ){
	    String fileId = null;
	    String uploadRoot = GisConInfo.newInstance().getUploadRoot();
	    long fileSize = item.getSize();
	    if( fileSize < 1 ){
	        return null;
	    }
	    String fileName = item.getName();

	    logger.debug( "FileName=" + fileName + " FileSize=" + fileSize );
	    SimpleDateFormat formatter = new SimpleDateFormat( "yyyy/MM/ddHHmmss" );
	    String strTemp = formatter.format( new Date() );
	    String fileSptr = File.separator;
	    String saveAsName = strTemp.substring( 8, 16 ) + FileSequence.getSeq() + fileName;
	    String relativePath = strTemp.substring( 0, 4 ) + fileSptr + strTemp.subSequence( 5, 7 );
	    String relativePathFile = relativePath + fileSptr + saveAsName;
	    String absolutePath = uploadRoot + fileSptr + relativePath;
	    String absolutePathFile = absolutePath + fileSptr + saveAsName;
	    File uploadPath=new File( absolutePath );
	    boolean isUploadPathExist=uploadPath.exists();
	    if(!isUploadPathExist){
	        try{
	            if( !uploadPath.mkdirs() ){
	                logger.error( "创建路径 "+absolutePath+" 失败!!!" );
	            }
	            else{
	                isUploadPathExist=true;
	                logger.info( "创建路径：" + absolutePath );
	            }
	        }catch(SecurityException secEx){
	            logger.error("对路径 "+absolutePath+" 没有写的权限!!!",secEx);

	        }
	    }else{
	        logger.info( "路径：" + absolutePath +" 已存在。");
	    }

	    logger.info( "Path=" + absolutePath );
	    //将上传文件写入指定路径
	    File uploadFile = new File(absolutePathFile );
	    logger.info( "File=" + absolutePathFile );
	    // Create file if it does not exist
	    boolean success =false;
	    try{
	        success=uploadFile.createNewFile();
	        //获取输入流
	        InputStream stream = item.getInputStream();

	        OutputStream bos = new FileOutputStream( absolutePathFile );
	        int bytesRead = 0;
	        byte[] buffer = new byte[8192];
	        while( ( bytesRead = stream.read( buffer, 0, 8192 ) ) != -1 ){
	            bos.write( buffer, 0, bytesRead );
	        }
	        bos.close();
	        logger.info( "The file has been written to \"" + absolutePathFile + "\"" );
	        //close the stream
	        stream.close();
	        fileId = UploadDAO.saveFileInfo( relativePathFile, fileName, "FILE", fileSize, "Desc", "000" );
	    }
	    catch( Exception ex ){
	        logger.error( "写入文件出错", ex );
	    }

	    return fileId;
	     }
	 */
}
